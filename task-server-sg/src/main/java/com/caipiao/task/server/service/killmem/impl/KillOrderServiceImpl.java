package com.caipiao.task.server.service.killmem.impl;

import com.caipiao.core.library.dto.order.OrderBetKillDto;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.tool.RandomUtil;
import com.caipiao.core.library.tool.TimeHelper;
import com.caipiao.core.library.utils.RedisKeys;
import com.caipiao.task.server.service.killmem.*;
import com.caipiao.task.server.util.BetKsUtil;
import com.caipiao.task.server.util.BetPceggUtil;
import com.caipiao.task.server.util.BetSscUtil;
import com.caipiao.task.server.util.BetXyftUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class KillOrderServiceImpl implements KillOrderService {
    private static final Logger logger = LoggerFactory.getLogger(KillOrderServiceImpl.class);

    private final List<String> LHCONEODDS = Lists.newArrayList("01", "03", "05", "06", "07", "08", "09", "10", "13", "14", "15", "21", "22", "23", "24", "25", "26"
            , "29", "30", "32", "34", "36", "37", "39", "41", "43", "44");
    private final List<String> LHCTWOODDS = Lists.newArrayList("11", "12", "31", "33", "35", "38", "40", "42");
    private final List<String> LHCMANYODDS = Lists.newArrayList("02", "03 ", "04", "05", "06", "07", "08", "09", "10", "16", "17", "18", "19", "20", "27", "28");
    private final List<String> SSC15 = Lists.newArrayList("03", "05", "06", "07", "08", "09");

    private final List<String> XYFT_LMGYA = Lists.newArrayList("01", "02");
    private final List<String> XYFT_PLAY_IDS_110 = Lists.newArrayList("03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14");   //1-5名，6-10名， 第1名。。。。到第10名


    private final String PCEGG_PLAY_ID_TM = "05";  //pc蛋蛋 特码玩法
    private final String PCEGG_PLAY_ID_SB = "02"; //pc蛋蛋 色波玩法
    private final String PCEGG_PLAY_ID_TMBS = "04";  //pc蛋蛋 特码包三玩法
    private final String PCEGG_PLAY_ID_HH = "01";  //pc蛋蛋 混合玩法
    private final String PCEGG_PLAY_ID_BZ = "03";  //pc蛋蛋 豹子玩法

    private final String KS_PLAY_ID_LM = "01";     //快三 两面
    private final String KS_PLAY_ID_DD = "02";     //快三 独胆
    private final String KS_PLAY_ID_LH = "03";     //快三 连号
    private final String KS_PLAY_ID_EBT = "04";    //快三 二不同号
    private final String KS_PLAY_ID_EBT_DT = "05"; //快三 二不同号-胆拖
    private final String KS_PLAY_ID_ET_DX = "06";  //快三 二同号-单选
    private final String KS_PLAY_ID_ET_FX = "07";  //快三 二同号-复选
    private final String KS_PLAY_ID_SBT = "08";    //快三 三不同号
    private final String KS_PLAY_ID_SBT_DT = "09"; //快三 三不同号-胆拖
    private final String KS_PLAY_ID_ST_DX = "10";  //快三 三同号-单选
    private final String KS_PLAY_ID_ST_TX = "11";  //快三 三同号-复选
    private final List<String> KS_PLAY_IDS_SBT_ST = Lists.newArrayList(KS_PLAY_ID_SBT, KS_PLAY_ID_SBT_DT, KS_PLAY_ID_ST_DX, KS_PLAY_ID_ST_TX);
    private final List<String> KS_PLAY_IDS_EBT_ET = Lists.newArrayList(KS_PLAY_ID_EBT, KS_PLAY_ID_EBT_DT, KS_PLAY_ID_ET_DX, KS_PLAY_ID_ET_FX);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BetLhcService betLhcService;

    @Autowired
    private BetBjpksService betBjpksService;


    @Autowired
    private BetNnKlService betNnKlService;

    @Autowired
    private BetNnJsService betNnJsService;

    @Autowired
    private BetFtJspksService betFtJspksService;
    @Autowired
    private BetFtSscService betFtSscService;


    @Override
    public String getLhcKillNumber(String issue, String lotteryid) {
        try {
            //  Set<String> keys  = redisTemplate.keys(RedisKeys.KILL_ORDER + lotteryid + issue + "_*");//所有要杀号的订单
            List<OrderBetKillDto> list = redisTemplate.opsForList().range(RedisKeys.KILL_ORDER + lotteryid + issue, 0, -1);

            logger.info("杀号keys：{}", RedisKeys.KILL_ORDER + lotteryid + issue);
            String numberStr = this.getLhcNum();
            long start = System.currentTimeMillis();
            long waittime = 5000;//等待5秒
            double winratio = 0.1;//杀号比例

            winratio = getWinratio(lotteryid);
            if (redisTemplate.hasKey(RedisKeys.KILLORDERTIME)) {
                waittime = Integer.parseInt((String) redisTemplate.opsForValue().get(RedisKeys.KILLORDERTIME)) * 1000;
            }
            String datestr = TimeHelper.date(TimeHelper.TIME_FORMAT);
            if (!CollectionUtils.isEmpty(list)) {
                BigDecimal countbet = new BigDecimal(0); //总投注额
                BigDecimal countwin = new BigDecimal(0); //总中奖额

                int i = 0;
                //保存一个号码， 当杀号失败时就用这个号码，（保证开这个号码至少赚钱为正数，不管多少）
                String numberStrZh = null;

                do {
                    i++;
                    if (start + waittime < System.currentTimeMillis()) { //超过预计时间 跳出 直接返回随机号码
                        logger.info("六合彩某彩种杀号失败生成号码numberStrZh：{}，numberStr：{}，lotteryId：{}", numberStrZh, numberStr, lotteryid);
                        if (StringUtils.isNotEmpty(numberStrZh)) {
                            return numberStrZh;
                        } else {
                            return numberStr;
                        }
                    }
                    numberStr = this.getLhcNum();
                    countbet = new BigDecimal(0);
                    countwin = new BigDecimal(0);
                    for (OrderBetKillDto order : list) {
                        // OrderBetKillDto order = (OrderBetKillDto) redisTemplate.opsForValue().get(orderkey);
                        countbet = countbet.add(order.getBetAmount().multiply(BigDecimal.valueOf(order.getBetCount())));
                        //判断打和情况
                        if (LHCONEODDS.contains(String.format("%02d", order.getPlayId() % 100))) {
                            if (String.valueOf(order.getPlayId() % 100).equals(BetLhcServiceImpl.PLAY_ID_LX_LXLZ) ||
                                    String.valueOf(order.getPlayId() % 100).equals(BetLhcServiceImpl.PLAY_ID_LX_LXLBZ)) {
                                // 如果是六肖连中或者六肖连不中玩法,特肖开出49为和值
                                if ("49".equals(numberStr.split(",")[6])) {
                                    countwin = countwin.add(order.getBetAmount().multiply(new BigDecimal(order.getBetCount())));
                                    continue;
                                }
                            }
                            // 判断是否中奖
                            int wincounts = betLhcService.isWinByOnePlayOneOdds(order.getBetNumber(), numberStr, order.getPlayId(), datestr, order.getLotteryId());
                            if (wincounts > 0) {
                                double odd = 0d;

                                String st = order.getOdds();
                                double thisOdd = 0d;
                                if (st.contains(":")) {
                                    String oddsArray[] = st.split(",");
                                    double odddouble[] = new double[oddsArray.length];
                                    for (int j = 0; j < odddouble.length; j++) {
                                        odddouble[j] = Double.valueOf(oddsArray[j].split(":")[1]);
                                    }
                                    for (int j = 0; j < odddouble.length; j++) {
                                        thisOdd = odddouble[j];
                                    }
                                }

                                if (order.getOdds().contains(":")) {
                                    odd = thisOdd;
                                } else {
                                    odd = Double.parseDouble(order.getOdds());
                                }
                                BigDecimal winAmount = order.getBetAmount().multiply(BigDecimal.valueOf(odd));
                                winAmount = winAmount.multiply(BigDecimal.valueOf(wincounts));
                                countwin = countwin.add(winAmount);
                            }
                        }
                        if (LHCTWOODDS.contains(String.format("%02d", order.getPlayId() % 100))) {
                            BigDecimal winAmount = new BigDecimal(0);
                            // 判断是否中奖,获取中奖注数
                            List<Integer> twoOdds = betLhcService.isWinByOnePlayTwoOdds(order.getBetNumber(), numberStr, order.getPlayId(), datestr, order.getLotteryId());
                            if (twoOdds.get(0) > 0 || twoOdds.get(1) > 0) {
                                // 获取总注数/中奖注数
                                String[] oddsarr = order.getOdds().split("/");

                                int bigOddsWins = twoOdds.get(0);
                                int smallOddsWins = twoOdds.get(1);

                                double big = 0.0;
                                double small = 1000000000.0;
                                String st = oddsarr[0];
                                if (oddsarr[0].contains(":")) {
                                    String oddsArray[] = st.split(",");
                                    double odd[] = new double[oddsArray.length];
                                    for (int j = 0; j < odd.length; j++) {
                                        odd[j] = Double.valueOf(oddsArray[j].split(":")[1]);
                                    }
                                    for (int j = 0; j < odd.length; j++) {
                                        if (odd[j] > big) {
                                            big = odd[j];
                                        }
                                        if (odd[j] < small) {
                                            small = odd[j];
                                        }
                                    }
                                }

                                if (bigOddsWins > 0) {
                                    // 计算赔率
                                    double odd = 0d;
                                    if (oddsarr[0].contains(":")) {
                                        odd = big;
                                    } else {
                                        odd = Double.parseDouble(oddsarr[0]);
                                    }

                                    // 一注的中奖额
                                    BigDecimal bigWins = order.getBetAmount().multiply(BigDecimal.valueOf(odd));
                                    winAmount = bigWins.multiply(BigDecimal.valueOf(bigOddsWins));
                                    countwin = countwin.add(winAmount);
                                }

                                if (smallOddsWins > 0) {
                                    // 计算赔率
                                    double odd = 0d;
                                    if (oddsarr[0].contains(":")) {
                                        odd = small;
                                    } else {
                                        odd = Double.parseDouble(oddsarr[1]);
                                    }
                                    // 一注的中奖额
                                    BigDecimal smallWins = order.getBetAmount().multiply(BigDecimal.valueOf(odd));
                                    winAmount = winAmount.add(smallWins.multiply(BigDecimal.valueOf(smallOddsWins)));
                                    countwin = countwin.add(winAmount);
                                }
                            }
                        }
                        if (LHCMANYODDS.contains(String.format("%02d", order.getPlayId() % 100))) {
                            if (String.format("%02d", order.getPlayId() % 100).equals(BetLhcServiceImpl.PLAY_ID_TM_TMA_LM)) {
                                // 如果是特码A两面玩法,部分投注,特肖开出49为和值
                                if ("49".equals(numberStr.split(",")[6])) {
                                    countwin = countwin.add(order.getBetAmount().multiply(new BigDecimal(order.getBetCount())));
                                    continue;
                                }
                            }
                            // 正码1-6(正1-6特两面)的和值情况
                            if (numberStr.indexOf("49") >= 0) {
                                Boolean ishe = false;
                                String[] sgArr = numberStr.split(",");
                                if (sgArr[0].equals("49") && (order.getBetNumber().indexOf("正码一") >= 0 || (String.valueOf(order.getPlayId() % 100).equals(BetLhcServiceImpl.PLAY_ID_ZT_ONE)))) {
                                    ishe = true;
                                } else if (sgArr[1].equals("49") && (order.getBetNumber().indexOf("正码二") >= 0 || (String.valueOf(order.getPlayId() % 100).equals(BetLhcServiceImpl.PLAY_ID_ZT_TWO)))) {
                                    ishe = true;
                                } else if (sgArr[2].equals("49") && (order.getBetNumber().indexOf("正码三") >= 0 || (String.valueOf(order.getPlayId() % 100).equals(BetLhcServiceImpl.PLAY_ID_ZT_THREE)))) {
                                    ishe = true;
                                } else if (sgArr[3].equals("49") && (order.getBetNumber().indexOf("正码四") >= 0 || (String.valueOf(order.getPlayId() % 100).equals(BetLhcServiceImpl.PLAY_ID_ZT_FOUR)))) {
                                    ishe = true;
                                } else if (sgArr[4].equals("49") && (order.getBetNumber().indexOf("正码五") >= 0 || (String.valueOf(order.getPlayId() % 100).equals(BetLhcServiceImpl.PLAY_ID_ZT_FIVE)))) {
                                    ishe = true;
                                } else if (sgArr[5].equals("49") && (order.getBetNumber().indexOf("正码六") >= 0 || (String.valueOf(order.getPlayId() % 100).equals(BetLhcServiceImpl.PLAY_ID_ZT_SIX)))) {
                                    ishe = true;
                                }
                                if (ishe == true) {
                                    countwin = countwin.add(order.getBetAmount().multiply(new BigDecimal(order.getBetCount())));
                                    continue;
                                }
                            }
                            //判断中奖情况
                            String winNum = betLhcService.isWinByOnePlayManyOdds(order.getBetNumber(), numberStr, order.getPlayId(), datestr, order.getLotteryId());
                            if (StringUtils.isNotBlank(winNum)) {
                                String[] winStrArr = winNum.split(",");
                                for (String winStr : winStrArr) {
                                    BigDecimal winAmount = new BigDecimal(0);
                                    // 获取赔率信息
                                    double odd = 0d;
                                    String[] oddarr = order.getOdds().split(",");
                                    for (String oddsrt : oddarr) {
                                        if (oddsrt.contains(":")) {
                                            if (winStr.equals(oddsrt.split(":")[0])) {
                                                odd = Double.parseDouble(oddsrt.split(":")[1]);
                                            }
                                        } else {
                                            odd = Double.parseDouble(oddsrt);
                                        }
                                    }
                                    //中奖额
                                    winAmount = winAmount.add(order.getBetAmount().multiply(BigDecimal.valueOf(odd)));
                                    countwin = countwin.add(winAmount);
                                }

                            }


                        }
                    }
                    if (StringUtils.isEmpty(numberStrZh) && countbet.compareTo(countwin) > 0) {
                        numberStrZh = numberStr;
                        logger.info("六合彩某彩种生成赚钱号码成功：numberStr:{},lotteryId:{}", numberStr, lotteryid);
                    }
                    logger.info("六合彩某彩种第{}次开奖：issue:{}, lotteryId:{}， countbet:{}, winratio:{}, countwin: {}", i, issue, lotteryid, countbet, winratio, countwin);
                } while ((countbet.multiply(new BigDecimal(1 - winratio))).compareTo(countwin) < 0);
            } else {
                logger.info("六合彩某彩种不用杀号开奖：issue:{}, lotteryId:{}", issue, lotteryid);
                return this.getLhcNum();
            }
            return numberStr;
        } catch (Exception e) {
            logger.info("六合彩某彩种杀号开奖出错：issue:{}, lotteryId:{}，{}", issue, lotteryid, e);
            return this.getLhcNum();
        }

    }

//    public static void main(String[] args) {
//        double big = 0.0;
//        double small = 1000000000.0;
//        String st = "鼠:4.0,蛇:4.5,猴:4.5";
//        String oddsArray[] = st.split(",");
//        double odd[] = new double[oddsArray.length];
//        for(int i=0;i<odd.length;i++){
//            odd[i] = Double.valueOf(oddsArray[i].split(":")[1]);
//        }
//        for(int i=0;i<odd.length;i++){
//            if(odd[i] > big){
//                big = odd[i];
//            }
//            if(odd[i] < small){
//                small = odd[i];
//            }
//        }
//        System.out.println(big);
//        System.out.println(small);

//        String st = "鼠:1.999,虎:1.999,兔:1.999,龙:1.999,蛇:1.999,猴:1.999";
//        double thisOdd = 0d;
//        if (st.contains(":")) {
//            String oddsArray[] = st.split(",");
//            double odddouble[] = new double[oddsArray.length];
//            for(int j=0;j<odddouble.length;j++){
//                odddouble[j] = Double.valueOf(oddsArray[j].split(":")[1]);
//            }
//            for(int j=0;j<odddouble.length;j++){
//                thisOdd = odddouble[j];
//            }
//        }
//        System.out.println(thisOdd);

//    }

    private double getWinratio(String lotteryid) {
        double winratio = -1d;   //(-1表示不杀号)
        if (CaipiaoTypeEnum.JSSSCFT.getTagType().equals(lotteryid)) {
            if (redisTemplate.hasKey(RedisKeys.SICAIONESSCRATE)) {
                winratio = Double.parseDouble((String) redisTemplate.opsForValue().get(RedisKeys.SICAIONESSCRATE));
            }
        } else if (CaipiaoTypeEnum.ONELHC.getTagType().equals(lotteryid)) {
            if (redisTemplate.hasKey(RedisKeys.SICAIONELHCRATE)) {
                winratio = Double.parseDouble((String) redisTemplate.opsForValue().get(RedisKeys.SICAIONELHCRATE));
            }
        } else if (CaipiaoTypeEnum.FIVELHC.getTagType().equals(lotteryid)) {
            if (redisTemplate.hasKey(RedisKeys.SICAIFIVELHCRATE)) {
                winratio = Double.parseDouble((String) redisTemplate.opsForValue().get(RedisKeys.SICAIFIVELHCRATE));
            }
        } else if (CaipiaoTypeEnum.AMLHC.getTagType().equals(lotteryid)) {
            if (redisTemplate.hasKey(RedisKeys.SICAITENLHCRATE)) {
                winratio = Double.parseDouble((String) redisTemplate.opsForValue().get(RedisKeys.SICAITENLHCRATE));
            }
        } else if (CaipiaoTypeEnum.JSSSC.getTagType().equals(lotteryid)) {
            if (redisTemplate.hasKey(RedisKeys.SICAIONESSCRATE)) {
                winratio = Double.parseDouble((String) redisTemplate.opsForValue().get(RedisKeys.SICAIONESSCRATE));
            }
        } else if (CaipiaoTypeEnum.FIVESSC.getTagType().equals(lotteryid)) {
            if (redisTemplate.hasKey(RedisKeys.SICAIFIVESSCRATE)) {
                winratio = Double.parseDouble((String) redisTemplate.opsForValue().get(RedisKeys.SICAIFIVESSCRATE));
            }
        } else if (CaipiaoTypeEnum.TENSSC.getTagType().equals(lotteryid)) {
            if (redisTemplate.hasKey(RedisKeys.SICAITENSSCRATE)) {
                winratio = Double.parseDouble((String) redisTemplate.opsForValue().get(RedisKeys.SICAITENSSCRATE));
            }
        } else if (CaipiaoTypeEnum.JSPKS.getTagType().equals(lotteryid)) {
            if (redisTemplate.hasKey(RedisKeys.SICAIONEPKSRATE)) {
                winratio = Double.parseDouble((String) redisTemplate.opsForValue().get(RedisKeys.SICAIONEPKSRATE));
            }
        } else if (CaipiaoTypeEnum.FIVEPKS.getTagType().equals(lotteryid)) {
            if (redisTemplate.hasKey(RedisKeys.SICAIFIVEPKSRATE)) {
                winratio = Double.parseDouble((String) redisTemplate.opsForValue().get(RedisKeys.SICAIFIVEPKSRATE));
            }
        } else if (CaipiaoTypeEnum.TENPKS.getTagType().equals(lotteryid)) {
            if (redisTemplate.hasKey(RedisKeys.SICAITENPKSRATE)) {
                winratio = Double.parseDouble((String) redisTemplate.opsForValue().get(RedisKeys.SICAITENPKSRATE));
            }
        } else if (CaipiaoTypeEnum.TXFFC.getTagType().equals(lotteryid)) {
            if (redisTemplate.hasKey(RedisKeys.SICAITXFFCRATE)) {
                winratio = Double.parseDouble((String) redisTemplate.opsForValue().get(RedisKeys.SICAITXFFCRATE));
            }
        }
        if (CaipiaoTypeEnum.AZKS.getTagType().equals(lotteryid)) {
            if (redisTemplate.hasKey(RedisKeys.SICAIAZKSRATE)) {
                winratio = Double.parseDouble((String) redisTemplate.opsForValue().get(RedisKeys.SICAIAZKSRATE));
            }
        }else if(CaipiaoTypeEnum.DZKS.getTagType().equals(lotteryid)){
            if (redisTemplate.hasKey(RedisKeys.SICAIDZKSRATE)) {
                winratio = Double.parseDouble((String) redisTemplate.opsForValue().get(RedisKeys.SICAIDZKSRATE));
            }
        }else if(CaipiaoTypeEnum.DZPCEGG.getTagType().equals(lotteryid)){
            if (redisTemplate.hasKey(RedisKeys.SICAIDZPCEGGRATE)) {
                winratio = Double.parseDouble((String) redisTemplate.opsForValue().get(RedisKeys.SICAIDZPCEGGRATE));
            }
        }else if(CaipiaoTypeEnum.DZXYFT.getTagType().equals(lotteryid)){
            if (redisTemplate.hasKey(RedisKeys.SICAIDZXYFTRATE)) {
                winratio = Double.parseDouble((String) redisTemplate.opsForValue().get(RedisKeys.SICAIDZXYFTRATE));
            }
        }else if(CaipiaoTypeEnum.XJPLHC.getTagType().equals(lotteryid)){
            if (redisTemplate.hasKey(RedisKeys.SICAIXJPLHCRATE)) {
                winratio = Double.parseDouble((String) redisTemplate.opsForValue().get(RedisKeys.SICAIXJPLHCRATE));
            }
        }

        return winratio;
    }

    @Override
    public String getSscKillNumber(String issue, String lotteryid) {
        try {
            //  Set<String> keys  = redisTemplate.keys(RedisKeys.KILL_ORDER + lotteryid + issue + "_*");
            List<OrderBetKillDto> list = redisTemplate.opsForList().range(RedisKeys.KILL_ORDER + lotteryid + issue, 0, -1);
            //极速时时彩跟极速时时彩番摊一起杀号
            if (CaipiaoTypeEnum.JSSSC.getTagType().equals(lotteryid)) {
                //  keys.addAll(redisTemplate.keys(RedisKeys.KILL_ORDER + CaipiaoTypeEnum.JSSSCFT.getTagType() + issue + "_*"));
                list.addAll(redisTemplate.opsForList().range(RedisKeys.KILL_ORDER + CaipiaoTypeEnum.JSSSCFT.getTagType() + issue, 0, -1));
            }
            //五分时时彩跟快乐牛牛一起杀号
            if (CaipiaoTypeEnum.FIVESSC.getTagType().equals(lotteryid)) {
                //  keys.addAll(redisTemplate.keys(RedisKeys.KILL_ORDER + CaipiaoTypeEnum.KLNIU.getTagType() + issue + "_*"));
                list.addAll(redisTemplate.opsForList().range(RedisKeys.KILL_ORDER + CaipiaoTypeEnum.KLNIU.getTagType() + issue, 0, -1));
            }
            logger.info("时时彩lotteryid=" + lotteryid + "杀号keys：" + RedisKeys.KILL_ORDER + lotteryid + issue);
            String numberStr = "";
            long start = System.currentTimeMillis();
            long waittime = 5000;//等待5秒
            double winratio = 0.1;//杀号比例
            winratio = getWinratio(lotteryid);
            if (redisTemplate.hasKey(RedisKeys.KILLORDERTIME)) {
                waittime = Integer.parseInt((String) redisTemplate.opsForValue().get(RedisKeys.KILLORDERTIME)) * 1000;
            }
            if (!CollectionUtils.isEmpty(list)) {
                BigDecimal countbet = new BigDecimal(0); //总投注额
                BigDecimal countwin = new BigDecimal(0); //总中奖额

                int i = 0;
                //保存一个号码， 当杀号失败时就用这个号码，（保证开这个号码至少赚钱为正数，不管多少）
                String numberStrZh = null;

                do {
                    i++;
                    if (start + waittime < System.currentTimeMillis()) { //超过预计时间 跳出 直接返回随机号码
                        logger.info("时时彩某彩种杀号失败生成号码numberStrZh：{}，numberStr：{}, lotteryId：{}", numberStrZh, numberStr, lotteryid);
                        if (StringUtils.isNotEmpty(numberStrZh)) {
                            return numberStrZh;
                        } else {
                            return numberStr;
                        }
                    }

                    numberStr = RandomUtil.getRandomStringSame(5, 0, 10);
                    countbet = new BigDecimal(0);
                    countwin = new BigDecimal(0);
                    for (OrderBetKillDto order : list) {
                        //   OrderBetKillDto order = (OrderBetKillDto) redisTemplate.opsForValue().get(orderkey);
                        countbet = countbet.add(order.getBetAmount());
                        // 判断是否中奖
                        Boolean win = false;
                        //时时彩快乐牛牛玩法
                        if (CaipiaoTypeEnum.KLNIU.getTagType().equals(order.getLotteryId().toString())) {
                            try {
                                win = betNnKlService.isWin(order.getBetNumber(), numberStr) != null;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else //时时彩番摊玩法
                            if (CaipiaoTypeEnum.JSSSCFT.getTagType().equals(order.getLotteryId().toString())) {
                                try {
                                    Integer re = betFtSscService.isWin(order.getBetNumber(), numberStr);
                                    //打赢
                                    if (BetFtSscServiceImpl.PLAY_ID_FH_ONE.equals(re)) {
                                        win = true;
                                        //打和的情况
                                    } else if (BetFtSscServiceImpl.PLAY_ID_FH_TWO.equals(re)) {
                                        countwin = countwin.add(order.getBetAmount());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }


                            //时时彩玩法
                            else {
                                if (order.getPlayId().equals(Integer.parseInt(order.getLotteryId() + "01"))) {
                                    win = BetSscUtil.isWinBylm(order.getBetNumber(), numberStr);
                                } else if (order.getPlayId().equals(Integer.parseInt(order.getLotteryId() + "02"))) {
                                    win = BetSscUtil.isWinByDN(order.getBetNumber(), numberStr);
                                } else if (SSC15.contains(String.format("%02d", order.getPlayId() % 100))) {
                                    win = BetSscUtil.isWinBy15(order.getBetNumber(), numberStr);
                                } else if (order.getPlayId().equals(Integer.parseInt(order.getLotteryId() + "04"))) {
                                    win = BetSscUtil.isWinByqzh(order.getBetNumber(), numberStr);
                                }
                            }
                        if (win) {
                            countwin = countwin.add(order.getBetAmount().multiply(new BigDecimal(order.getOdds())));
                        }

                    }
                    if (StringUtils.isEmpty(numberStrZh) && countbet.compareTo(countwin) > 0) {
                        numberStrZh = numberStr;
                        logger.info("时时彩某彩种生成赚钱号码成功：numberStr:{}, lotteryid:{}", numberStr, lotteryid);
                    }
                    logger.info("时时彩某彩种第{}次开奖：issue:{}, lotteryId:{}， countbet:{}, winratio:{}, countwin: {}", i, issue, lotteryid, countbet, winratio, countwin);
                } while ((countbet.multiply(new BigDecimal(1 - winratio))).compareTo(countwin) < 0);
            } else {
                logger.info("时时彩某彩种不用杀号开奖：issue:{}, lotteryId:{}", issue, lotteryid);
                return RandomUtil.getRandomStringSame(5, 0, 10);
            }
            return numberStr;
        } catch (Exception e) {
            logger.info("时时彩某彩种杀号开奖出错：issue:{}, lotteryId:{}，{}", issue, lotteryid, e);
            return RandomUtil.getRandomStringSame(5, 0, 10);
        }

    }

    @Override
    public String getBjpksKillNumber(String issue, String lotteryid) {
        try {
//  Set<String> keys  = redisTemplate.keys(RedisKeys.KILL_ORDER + lotteryid + issue + "_*");
            List<OrderBetKillDto> list = redisTemplate.opsForList().range(RedisKeys.KILL_ORDER + lotteryid + issue, 0, -1);
            //极速pks跟极速PKs番摊，极速牛牛一起杀号
            if (CaipiaoTypeEnum.JSPKS.getTagType().equals(lotteryid)) {
                // keys.addAll(redisTemplate.keys(RedisKeys.KILL_ORDER + CaipiaoTypeEnum.JSPKFT.getTagType() + issue + "_*"));
                //  keys.addAll(redisTemplate.keys(RedisKeys.KILL_ORDER + CaipiaoTypeEnum.JSNIU.getTagType() + issue + "_*"));
                list.addAll(redisTemplate.opsForList().range(RedisKeys.KILL_ORDER + CaipiaoTypeEnum.JSPKFT.getTagType() + issue, 0, -1));
                list.addAll(redisTemplate.opsForList().range(RedisKeys.KILL_ORDER + CaipiaoTypeEnum.JSNIU.getTagType() + issue, 0, -1));

            }
            logger.info("pk拾lotteryid=" + lotteryid + "杀号keys：" + RedisKeys.KILL_ORDER + lotteryid + issue);
            String numberStr = this.getBjpks();
            long start = System.currentTimeMillis();
            long waittime = 5000;//等待5秒
            double winratio = 0.1;//杀号比例
            winratio = getWinratio(lotteryid);
            if (redisTemplate.hasKey(RedisKeys.KILLORDERTIME)) {
                waittime = Integer.parseInt((String) redisTemplate.opsForValue().get(RedisKeys.KILLORDERTIME)) * 1000;
            }
            if (!CollectionUtils.isEmpty(list)) {
                BigDecimal countbet = new BigDecimal(0); //总投注额
                BigDecimal countwin = new BigDecimal(0); //总中奖额

                int i = 0;
                //保存一个号码， 当杀号失败时就用这个号码，（保证开这个号码至少赚钱为正数，不管多少）
                String numberStrZh = null;

                do {
                    i++;
                    if (start + waittime < System.currentTimeMillis()) { //超过预计时间 跳出 直接返回随机号码
                        logger.info("PK系列某彩种杀号失败生成号码numberStrZh：{}，numberStr：{}, lotteryId:{}", numberStrZh, numberStr, lotteryid);
                        if (StringUtils.isNotEmpty(numberStrZh)) {
                            return numberStrZh;
                        } else {
                            return numberStr;
                        }
                    }
                    numberStr = this.getBjpks();
                    countbet = new BigDecimal(0);
                    countwin = new BigDecimal(0);
                    for (OrderBetKillDto order : list) {
                        // OrderBetKillDto order = (OrderBetKillDto) redisTemplate.opsForValue().get(orderkey);
                        countbet = countbet.add(order.getBetAmount());
                        // 判断是否中奖
                        Boolean win = false;
                        //pk10快乐牛牛玩法
                        if (CaipiaoTypeEnum.JSNIU.getTagType().equals(order.getLotteryId().toString())) {
                            try {
                                win = betNnJsService.isWin(order.getBetNumber(), numberStr) != null;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else
                            //极速PK10番摊玩法
                            if (CaipiaoTypeEnum.JSPKFT.getTagType().equals(order.getLotteryId().toString())) {
                                Integer re = betFtJspksService.isWin(order.getBetNumber(), numberStr);
                                //打赢
                                if (BetFtJspksServiceImpl.PLAY_ID_FH_ONE.equals(re)) {
                                    win = true;
                                    //打和的情况
                                } else if (BetFtJspksServiceImpl.PLAY_ID_FH_TWO.equals(re)) {
                                    countwin = countwin.add(order.getBetAmount());
                                }
                            } else {
                                if (order.getPlayId().equals(Integer.parseInt(order.getLotteryId() + "01"))) {
                                    win = betBjpksService.isWinLmAndGyh(order.getBetNumber(), numberStr, order.getPlayId(), order.getLotteryId()) != null;
                                } else if (order.getPlayId().equals(Integer.parseInt(order.getLotteryId() + "02"))) {
                                    win = betBjpksService.isWinLmAndGyh(order.getBetNumber(), numberStr, order.getPlayId(), order.getLotteryId()) != null;
                                } else if (order.getPlayId().equals(Integer.parseInt(order.getLotteryId() + "03"))) {
                                    win = betBjpksService.isWin(order.getBetNumber(), numberStr, order.getPlayId(), order.getLotteryId()) != null;
                                } else {
                                    win = betBjpksService.isWin(order.getBetNumber(), numberStr, order.getPlayId(), order.getLotteryId()) != null;
                                }
                            }
                        if (win) {
                            countwin = countwin.add(order.getBetAmount().multiply(new BigDecimal(order.getOdds())));
                        }

                    }
                    if (StringUtils.isEmpty(numberStrZh) && countbet.compareTo(countwin) > 0) {
                        numberStrZh = numberStr;
                        logger.info("PK系列某彩种生成赚钱号码成功 numberStr:{}, lotteryId:{}", numberStr, lotteryid);
                    }
                    logger.info("PK系列某彩种第{}次开奖：issue:{}, lotteryId:{}， countbet:{}, winratio:{}, countwin: {}", i, issue, lotteryid, countbet, winratio, countwin);
                } while ((countbet.multiply(new BigDecimal(1 - winratio))).compareTo(countwin) < 0);

            } else {
                logger.info("PK系列某彩种不用杀号开奖：issue:{}, lotteryId:{}", issue, lotteryid);
                return getBjpks();
            }
            return numberStr;
        } catch (Exception e) {
            logger.info("PK系列某彩种杀号开奖出错：issue:{}, lotteryId:{}，{}", issue, lotteryid, e);
            return getBjpks();
        }

    }

    @Override
    public String getXyftKillNumber(String issue, String lotteryid) {
        try {
            List<OrderBetKillDto> list = redisTemplate.opsForList().range(RedisKeys.KILL_ORDER + lotteryid + issue, 0, -1);

//        //测试开始
//        list = new ArrayList<>();
//        for(int i = 0;i < 1; i++){
//            OrderBetKillDto dto = new OrderBetKillDto();
//            dto.setBackAmount(new BigDecimal("0"));
//            dto.setBetAmount(new BigDecimal("1"));
//            dto.setBetCount(1);
//            dto.setBetNumber("亚军@单");
//            dto.setId(5518196);
//            dto.setIsDelete(false);
//            dto.setIssue("20200516032");
//            dto.setLotteryId(1403);
//            dto.setOdds("9.99");
//            dto.setOrderId(254247);
//            dto.setPlayId(140306);
//            dto.setSettingId(373);
//            dto.setTbStatus("WAIT");
//            dto.setUserId(29490);
//            dto.setWinAmount(new BigDecimal("0"));
//            list.add(dto);
//        }
//        //测试结束

            logger.info("幸运飞艇私彩lkeys：{}", RedisKeys.KILL_ORDER + lotteryid + issue);
            String numberStr = null;
            long start = System.currentTimeMillis();
            long waittime = 5000;//等待5秒
            double winratio = 0.1;//杀号比例
            winratio = getWinratio(lotteryid);
            if (redisTemplate.hasKey(RedisKeys.KILLORDERTIME)) {
                waittime = Integer.parseInt((String) redisTemplate.opsForValue().get(RedisKeys.KILLORDERTIME)) * 1000;
            }
            if (!CollectionUtils.isEmpty(list)) {

                BigDecimal countbet = new BigDecimal(0); //总投注额
                BigDecimal countwin = new BigDecimal(0); //总中奖额

                int i = 0;
                do {
                    i++;
                    numberStr = RandomUtil.getRandomStringNoSame(10, 1, 11);

                    //测试屏蔽开始
                    if (start + waittime < System.currentTimeMillis()) { //超过预计时间 跳出 直接返回随机号码
                        logger.info("幸运飞艇私彩开奖超时，杀号失效：issue:{}, lotteryId:{}", issue, lotteryid);
                        break;
                    }
                    //测试屏蔽结束

//                //测试开始
//                numberStr = "08,07,08,09,10,01,07,03,05,06";
//                //测试结束

                    countbet = new BigDecimal(0);
                    countwin = new BigDecimal(0);
                    for (OrderBetKillDto order : list) {
                        countbet = countbet.add(order.getBetAmount());

                        // 判断是否中奖
                        String win = null;
                        if (XYFT_LMGYA.contains(String.format("%02d", order.getPlayId() % 100))) {
                            win = BetXyftUtil.isWinLmAndGyh(order.getBetNumber(), numberStr, order.getPlayId());
                        } else if (XYFT_PLAY_IDS_110.contains(String.format("%02d", order.getPlayId() % 100))) {
                            win = BetXyftUtil.isWin(order.getBetNumber(), numberStr, order.getPlayId());
                        }


                        if (com.caipiao.core.library.tool.StringUtils.isNotBlank(win)) {
                            if (order.getOdds().contains(",")) {
                                String[] winStrArr = win.split(",");
                                for (String winStr : winStrArr) {
                                    BigDecimal winAmount = new BigDecimal(0);
                                    // 获取赔率信息
                                    double odd = 0d;
                                    String[] oddarr = order.getOdds().split(",");
                                    for (String oddsrt : oddarr) {
                                        if (oddsrt.contains(":")) {
                                            if (winStr.equals(oddsrt.split(":")[0])) {
                                                odd = Double.parseDouble(oddsrt.split(":")[1]);
                                            }
                                        } else {
                                            odd = Double.parseDouble(oddsrt);
                                        }
                                    }
                                    //中奖额
                                    winAmount = winAmount.add(order.getBetAmount().multiply(BigDecimal.valueOf(odd)));
                                    countwin = countwin.add(winAmount);
                                }

                            } else {
                                // 判断是否中奖
                                double odd = Double.valueOf(order.getOdds());
                                BigDecimal winAmount = order.getBetAmount().multiply(BigDecimal.valueOf(odd));
                                countwin = countwin.add(winAmount);

                            }
                        }

                    }
                    logger.info("幸运飞艇私彩第{}次开奖：issue:{}, lotteryId:{}， countbet:{}, winratio:{}, countwin: {}", i, issue, lotteryid, countbet, winratio, countwin);
                } while ((countbet.multiply(new BigDecimal(1 - winratio))).compareTo(countwin) < 0);

            } else {
                logger.info("幸运飞艇私彩不用杀号开奖：issue:{}, lotteryId:{}", issue, lotteryid);
                return RandomUtil.getRandomStringNoSame(10, 1, 11);
            }

            return numberStr;
        } catch (Exception e) {
            logger.info("幸运飞艇私彩杀号开奖出错：issue:{}, lotteryId:{}，{}", issue, lotteryid, e);
            return RandomUtil.getRandomStringNoSame(10, 1, 11);
        }

    }


    private String getLhcNum() {
        List<String> numberList = new ArrayList<String>();
        for (int j = 1; j <= 49; j++) {
            numberList.add(j < 10 ? ("0" + j) : String.valueOf(j));
        }
        Collections.shuffle(numberList);
        String numberStr = numberList.get(0) + "," + numberList.get(1) + "," + numberList.get(2) + ","
                + numberList.get(3) + "," + numberList.get(4) + "," + numberList.get(5) + "," + numberList.get(6);
        return numberStr;
    }

    private String getBjpks() {
        List<String> numberList = new ArrayList<String>();
        numberList.add("01");
        numberList.add("02");
        numberList.add("03");
        numberList.add("04");
        numberList.add("05");
        numberList.add("06");
        numberList.add("07");
        numberList.add("08");
        numberList.add("09");
        numberList.add("10");
        Collections.shuffle(numberList);
        String numberStr = numberList.toString().replace("[", "").replace("]", "").replace(" ", "");
        return numberStr;
    }


    @Override
    public String getAzksKillNumber(String issue, String lotteryid) {
        try {
            List<OrderBetKillDto> list = redisTemplate.opsForList().range(RedisKeys.KILL_ORDER + lotteryid + issue, 0, -1);

//        //测试开始
//        list = new ArrayList<>();
//        for(int i = 0;i < 1; i++){
//            OrderBetKillDto dto = new OrderBetKillDto();
//            dto.setBackAmount(new BigDecimal("0"));
//            dto.setBetAmount(new BigDecimal("1"));
//            dto.setBetCount(1);
//            dto.setBetNumber("第7名@07");
//            dto.setId(5518196);
//            dto.setIsDelete(false);
//            dto.setIssue("20200109016");
//            dto.setLotteryId(1402);
//            dto.setOdds("9.99");
//            dto.setOrderId(2692194);
//            dto.setPlayId(140111);
//            dto.setSettingId(163);
//            dto.setTbStatus("WAIT");
//            dto.setUserId(33669);
//            dto.setWinAmount(new BigDecimal("0"));
//            list.add(dto);
//        }
//        //测试结束

            logger.info("澳洲快三lkeys：{}", RedisKeys.KILL_ORDER + lotteryid + issue);
            String numberStr = null;
            long start = System.currentTimeMillis();
            long waittime = 5000;//等待5秒
            double winratio = 0.1;//杀号比例
            winratio = getWinratio(lotteryid);
            if (redisTemplate.hasKey(RedisKeys.KILLORDERTIME)) {
                waittime = Integer.parseInt((String) redisTemplate.opsForValue().get(RedisKeys.KILLORDERTIME)) * 1000;
            }
            if (!CollectionUtils.isEmpty(list)) {

                BigDecimal countbet = new BigDecimal(0); //总投注额
                BigDecimal countwin = new BigDecimal(0); //总中奖额

                int i = 0;
                do {
                    i++;
                    numberStr = RandomUtil.getRandomStringSame(3, 1, 7);

                    //测试屏蔽开始
                    if (start + waittime < System.currentTimeMillis()) { //超过预计时间 跳出 直接返回随机号码
                        logger.info("澳洲三开奖超时，杀号失效：issue:{}, lotteryId:{}", issue, lotteryid);
                        break;
                    }
                    //测试屏蔽结束

//                //测试开始
//                numberStr = "08,07,08,09,10,01,07,03,05,06";
//                //测试结束

                    countbet = new BigDecimal(0);
                    countwin = new BigDecimal(0);
                    for (OrderBetKillDto order : list) {
                        countbet = countbet.add(order.getBetAmount());

                        // 判断是否中奖
                        String win = null;
                        if (KS_PLAY_ID_LM.contains(String.format("%02d", order.getPlayId() % 100))) {
                            win = BetKsUtil.judgeWinLm(order.getBetNumber(), numberStr, order.getPlayId(), order.getLotteryId());
                        } else if (KS_PLAY_ID_DD.contains(String.format("%02d", order.getPlayId() % 100))) {
                            win = BetKsUtil.judgeWinDd(order.getBetNumber(), numberStr, order.getPlayId(), order.getLotteryId());
                        } else if (KS_PLAY_ID_LH.contains(String.format("%02d", order.getPlayId() % 100))) {
                            win = BetKsUtil.judgeWinLh(order.getBetNumber(), numberStr, order.getPlayId(), order.getLotteryId());
                        } else if (KS_PLAY_IDS_SBT_ST.contains(String.format("%02d", order.getPlayId() % 100))) {
                            win = BetKsUtil.judgeWinSbThAndSthPlay(order.getBetNumber(), numberStr, order.getPlayId(), order.getLotteryId());
                        } else if (KS_PLAY_IDS_EBT_ET.contains(String.format("%02d", order.getPlayId() % 100))) {
                            win = BetKsUtil.judgeWinEbThAndEthPlay(order.getBetNumber(), numberStr, order.getPlayId(), order.getLotteryId());
                        }


                        if (com.caipiao.core.library.tool.StringUtils.isNotBlank(win)) {
                            if (KS_PLAY_ID_LM.contains(String.format("%02d", order.getPlayId() % 100)) || KS_PLAY_ID_DD.contains(String.format("%02d", order.getPlayId() % 100))
                                    || KS_PLAY_ID_LH.contains(String.format("%02d", order.getPlayId() % 100))) {
                                if (order.getOdds().contains(",")) {
                                    String[] winStrArr = win.split("_");
                                    for (String winStr : winStrArr) {
                                        BigDecimal winAmount = new BigDecimal(0);
                                        // 获取赔率信息
                                        double odd = 0d;
                                        String[] oddarr = order.getOdds().split(",");
                                        for (String oddsrt : oddarr) {
                                            if (oddsrt.contains(":")) {
                                                if (winStr.equals(oddsrt.split(":")[0])) {
                                                    odd = Double.parseDouble(oddsrt.split(":")[1]);
                                                }
                                            } else {
                                                odd = Double.parseDouble(oddsrt);
                                            }
                                        }
                                        //中奖额
                                        winAmount = winAmount.add(order.getBetAmount().multiply(BigDecimal.valueOf(odd)));
                                        countwin = countwin.add(winAmount);
                                    }

                                } else {
                                    // 判断是否中奖
                                    double odd = Double.valueOf(order.getOdds());
                                    BigDecimal winAmount = order.getBetAmount().multiply(BigDecimal.valueOf(odd));
                                    countwin = countwin.add(winAmount);
                                }
                            } else if (KS_PLAY_IDS_SBT_ST.contains(String.format("%02d", order.getPlayId() % 100)) ||
                                    KS_PLAY_IDS_EBT_ET.contains(String.format("%02d", order.getPlayId() % 100))) { //默认取第一个赔率
                                if (order.getOdds().contains(",")) {
                                    String[] winStrArr = win.split("_");
                                    for (String winStr : winStrArr) {
                                        BigDecimal winAmount = new BigDecimal(0);
                                        // 获取赔率信息
                                        double odd = 0d;
                                        String[] oddarr = order.getOdds().split(",");
                                        for (String oddsrt : oddarr) {
                                            if (oddsrt.contains(":")) {
                                                odd = Double.parseDouble(oddsrt.split(":")[1]);
                                                break;
                                            }
                                        }
                                        //中奖额
                                        winAmount = winAmount.add(order.getBetAmount().multiply(BigDecimal.valueOf(odd)));
                                        countwin = countwin.add(winAmount);
                                    }

                                } else {
                                    // 判断是否中奖
                                    double odd = Double.valueOf(order.getOdds());
                                    BigDecimal winAmount = order.getBetAmount().multiply(BigDecimal.valueOf(odd));
                                    countwin = countwin.add(winAmount);
                                }
                            }

                        }

                    }
                    logger.info("澳洲快三第{}次开奖：issue:{}, lotteryId:{}， countbet:{}, winratio:{}, countwin: {}", i, issue, lotteryid, countbet, winratio, countwin);
                } while ((countbet.multiply(new BigDecimal(1 - winratio))).compareTo(countwin) < 0);

            } else {
                logger.info("澳洲快三不用杀号开奖：issue:{}, lotteryId:{}", issue, lotteryid);
                return RandomUtil.getRandomStringSame(3, 1, 7);
            }

            return numberStr;
        } catch (Exception e) {
            logger.info("澳洲快三杀号开奖出错：issue:{}, lotteryId:{}，{}", issue, lotteryid, e);
            return RandomUtil.getRandomStringSame(3, 1, 7);
        }

    }

    @Override
    public String getDzksKillNumber(String issue, String lotteryid) {
        try {
            List<OrderBetKillDto> list = redisTemplate.opsForList().range(RedisKeys.KILL_ORDER + lotteryid + issue, 0, -1);

//        //测试开始
//        list = new ArrayList<>();
//        for(int i = 0;i < 1; i++){
//            OrderBetKillDto dto = new OrderBetKillDto();
//            dto.setBackAmount(new BigDecimal("0"));
//            dto.setBetAmount(new BigDecimal("1"));
//            dto.setBetCount(1);
//            dto.setBetNumber("第7名@07");
//            dto.setId(5518196);
//            dto.setIsDelete(false);
//            dto.setIssue("20200109016");
//            dto.setLotteryId(1402);
//            dto.setOdds("9.99");
//            dto.setOrderId(2692194);
//            dto.setPlayId(140111);
//            dto.setSettingId(163);
//            dto.setTbStatus("WAIT");
//            dto.setUserId(33669);
//            dto.setWinAmount(new BigDecimal("0"));
//            list.add(dto);
//        }
//        //测试结束

            logger.info("德州快三lkeys：{}", RedisKeys.KILL_ORDER + lotteryid + issue);
            String numberStr = null;
            long start = System.currentTimeMillis();
            long waittime = 5000;//等待5秒
            double winratio = 0.1;//杀号比例
            winratio = getWinratio(lotteryid);
            if (redisTemplate.hasKey(RedisKeys.KILLORDERTIME)) {
                waittime = Integer.parseInt((String) redisTemplate.opsForValue().get(RedisKeys.KILLORDERTIME)) * 1000;
            }
            if (!CollectionUtils.isEmpty(list)) {

                BigDecimal countbet = new BigDecimal(0); //总投注额
                BigDecimal countwin = new BigDecimal(0); //总中奖额

                int i = 0;
                do {
                    i++;
                    numberStr = RandomUtil.getRandomStringSame(3, 1, 7);

                    //测试屏蔽开始
                    if (start + waittime < System.currentTimeMillis()) { //超过预计时间 跳出 直接返回随机号码
                        logger.info("德州快三开奖超时，杀号失效：issue:{}, lotteryId:{}", issue, lotteryid);
                        break;
                    }
                    //测试屏蔽结束

//                //测试开始
//                numberStr = "08,07,08,09,10,01,07,03,05,06";
//                //测试结束

                    countbet = new BigDecimal(0);
                    countwin = new BigDecimal(0);
                    for (OrderBetKillDto order : list) {
                        countbet = countbet.add(order.getBetAmount());

                        // 判断是否中奖
                        String win = null;
                        if (KS_PLAY_ID_LM.contains(String.format("%02d", order.getPlayId() % 100))) {
                            win = BetKsUtil.judgeWinLm(order.getBetNumber(), numberStr, order.getPlayId(), order.getLotteryId());
                        } else if (KS_PLAY_ID_DD.contains(String.format("%02d", order.getPlayId() % 100))) {
                            win = BetKsUtil.judgeWinDd(order.getBetNumber(), numberStr, order.getPlayId(), order.getLotteryId());
                        } else if (KS_PLAY_ID_LH.contains(String.format("%02d", order.getPlayId() % 100))) {
                            win = BetKsUtil.judgeWinLh(order.getBetNumber(), numberStr, order.getPlayId(), order.getLotteryId());
                        } else if (KS_PLAY_IDS_SBT_ST.contains(String.format("%02d", order.getPlayId() % 100))) {
                            win = BetKsUtil.judgeWinSbThAndSthPlay(order.getBetNumber(), numberStr, order.getPlayId(), order.getLotteryId());
                        } else if (KS_PLAY_IDS_EBT_ET.contains(String.format("%02d", order.getPlayId() % 100))) {
                            win = BetKsUtil.judgeWinEbThAndEthPlay(order.getBetNumber(), numberStr, order.getPlayId(), order.getLotteryId());
                        }


                        if (com.caipiao.core.library.tool.StringUtils.isNotBlank(win)) {
                            if (KS_PLAY_ID_LM.contains(String.format("%02d", order.getPlayId() % 100)) || KS_PLAY_ID_DD.contains(String.format("%02d", order.getPlayId() % 100))
                                    || KS_PLAY_ID_LH.contains(String.format("%02d", order.getPlayId() % 100))) {
                                if (order.getOdds().contains(",")) {
                                    String[] winStrArr = win.split("_");
                                    for (String winStr : winStrArr) {
                                        BigDecimal winAmount = new BigDecimal(0);
                                        // 获取赔率信息
                                        double odd = 0d;
                                        String[] oddarr = order.getOdds().split(",");
                                        for (String oddsrt : oddarr) {
                                            if (oddsrt.contains(":")) {
                                                if (winStr.equals(oddsrt.split(":")[0])) {
                                                    odd = Double.parseDouble(oddsrt.split(":")[1]);
                                                }
                                            } else {
                                                odd = Double.parseDouble(oddsrt);
                                            }
                                        }
                                        //中奖额
                                        winAmount = winAmount.add(order.getBetAmount().multiply(BigDecimal.valueOf(odd)));
                                        countwin = countwin.add(winAmount);
                                    }

                                } else {
                                    // 判断是否中奖
                                    double odd = Double.valueOf(order.getOdds());
                                    BigDecimal winAmount = order.getBetAmount().multiply(BigDecimal.valueOf(odd));
                                    countwin = countwin.add(winAmount);
                                }
                            } else if (KS_PLAY_IDS_SBT_ST.contains(String.format("%02d", order.getPlayId() % 100)) ||
                                    KS_PLAY_IDS_EBT_ET.contains(String.format("%02d", order.getPlayId() % 100))) { //默认取第一个赔率
                                if (order.getOdds().contains(",")) {
                                    String[] winStrArr = win.split("_");
                                    for (String winStr : winStrArr) {
                                        BigDecimal winAmount = new BigDecimal(0);
                                        // 获取赔率信息
                                        double odd = 0d;
                                        String[] oddarr = order.getOdds().split(",");
                                        for (String oddsrt : oddarr) {
                                            if (oddsrt.contains(":")) {
                                                odd = Double.parseDouble(oddsrt.split(":")[1]);
                                                break;
                                            }
                                        }
                                        //中奖额
                                        winAmount = winAmount.add(order.getBetAmount().multiply(BigDecimal.valueOf(odd)));
                                        countwin = countwin.add(winAmount);
                                    }

                                } else {
                                    // 判断是否中奖
                                    double odd = Double.valueOf(order.getOdds());
                                    BigDecimal winAmount = order.getBetAmount().multiply(BigDecimal.valueOf(odd));
                                    countwin = countwin.add(winAmount);
                                }
                            }

                        }

                    }
                    logger.info("德州快三第{}次开奖：issue:{}, lotteryId:{}， countbet:{}, winratio:{}, countwin: {}", i, issue, lotteryid, countbet, winratio, countwin);
                } while ((countbet.multiply(new BigDecimal(1 - winratio))).compareTo(countwin) < 0);

            } else {
                logger.info("德州快三不用杀号开奖：issue:{}, lotteryId:{}", issue, lotteryid);
                return RandomUtil.getRandomStringSame(3, 1, 7);
            }

            return numberStr;
        } catch (Exception e) {
            logger.info("德州快三杀号开奖出错：issue:{}, lotteryId:{}，{}", issue, lotteryid, e);
            return RandomUtil.getRandomStringSame(3, 1, 7);
        }

    }

//    @Override
//    public String getDzxyftKillNumber(String issue, String lotteryid) {
//        String numberStr = RandomUtil.getRandomStringNoSame(10, 1, 11);
//        return numberStr;
//    }

    @Override
    public String getDzxyftKillNumber(String issue, String lotteryid) {
        try {
            List<OrderBetKillDto> list = redisTemplate.opsForList().range(RedisKeys.KILL_ORDER + lotteryid + issue, 0, -1);

//        //测试开始
//        list = new ArrayList<>();
//        for(int i = 0;i < 1; i++){
//            OrderBetKillDto dto = new OrderBetKillDto();
//            dto.setBackAmount(new BigDecimal("0"));
//            dto.setBetAmount(new BigDecimal("1"));
//            dto.setBetCount(1);
//            dto.setBetNumber("第7名@07");
//            dto.setId(5518196);
//            dto.setIsDelete(false);
//            dto.setIssue("20200109016");
//            dto.setLotteryId(1402);
//            dto.setOdds("9.99");
//            dto.setOrderId(2692194);
//            dto.setPlayId(140111);
//            dto.setSettingId(163);
//            dto.setTbStatus("WAIT");
//            dto.setUserId(33669);
//            dto.setWinAmount(new BigDecimal("0"));
//            list.add(dto);
//        }
//        //测试结束

            logger.info("德州幸运飞艇lkeys：{}", RedisKeys.KILL_ORDER + lotteryid + issue);
            String numberStr = null;
            long start = System.currentTimeMillis();
            long waittime = 5000;//等待5秒
            double winratio = 0.1;//杀号比例
            winratio = getWinratio(lotteryid);
            if (redisTemplate.hasKey(RedisKeys.KILLORDERTIME)) {
                waittime = Integer.parseInt((String) redisTemplate.opsForValue().get(RedisKeys.KILLORDERTIME)) * 1000;
            }
            if (!CollectionUtils.isEmpty(list)) {

                BigDecimal countbet = new BigDecimal(0); //总投注额
                BigDecimal countwin = new BigDecimal(0); //总中奖额

                int i = 0;
                do {
                    i++;
                    numberStr = RandomUtil.getRandomStringNoSame(10, 1, 11);

                    //测试屏蔽开始
                    if (start + waittime < System.currentTimeMillis()) { //超过预计时间 跳出 直接返回随机号码
                        logger.info("德州幸运飞艇开奖超时，杀号失效：issue:{}, lotteryId:{}", issue, lotteryid);
                        break;
                    }
                    //测试屏蔽结束

//                //测试开始
//                numberStr = "08,07,08,09,10,01,07,03,05,06";
//                //测试结束

                    countbet = new BigDecimal(0);
                    countwin = new BigDecimal(0);
                    for (OrderBetKillDto order : list) {
                        countbet = countbet.add(order.getBetAmount());

                        // 判断是否中奖
                        String win = null;
                        if (XYFT_LMGYA.contains(String.format("%02d", order.getPlayId() % 100))) {
                            win = BetXyftUtil.isWinLmAndGyh(order.getBetNumber(), numberStr, order.getPlayId());
                        } else if (XYFT_PLAY_IDS_110.contains(String.format("%02d", order.getPlayId() % 100))) {
                            win = BetXyftUtil.isWin(order.getBetNumber(), numberStr, order.getPlayId());
                        }


                        if (com.caipiao.core.library.tool.StringUtils.isNotBlank(win)) {
                            if (order.getOdds().contains(",")) {
                                String[] winStrArr = win.split(",");
                                for (String winStr : winStrArr) {
                                    BigDecimal winAmount = new BigDecimal(0);
                                    // 获取赔率信息
                                    double odd = 0d;
                                    String[] oddarr = order.getOdds().split(",");
                                    for (String oddsrt : oddarr) {
                                        if (oddsrt.contains(":")) {
                                            if (winStr.equals(oddsrt.split(":")[0])) {
                                                odd = Double.parseDouble(oddsrt.split(":")[1]);
                                            }
                                        } else {
                                            odd = Double.parseDouble(oddsrt);
                                        }
                                    }
                                    //中奖额
                                    winAmount = winAmount.add(order.getBetAmount().multiply(BigDecimal.valueOf(odd)));
                                    countwin = countwin.add(winAmount);
                                }

                            } else {
                                // 判断是否中奖
                                double odd = Double.valueOf(order.getOdds());
                                BigDecimal winAmount = order.getBetAmount().multiply(BigDecimal.valueOf(odd));
                                countwin = countwin.add(winAmount);

                            }
                        }

                    }
                    logger.info("德州幸运飞艇第{}次开奖：issue:{}, lotteryId:{}， countbet:{}, winratio:{}, countwin: {}", i, issue, lotteryid, countbet, winratio, countwin);
                } while ((countbet.multiply(new BigDecimal(1 - winratio))).compareTo(countwin) < 0);

            } else {
                logger.info("德州幸运飞艇不用杀号开奖：issue:{}, lotteryId:{}", issue, lotteryid);
                return RandomUtil.getRandomStringNoSame(10, 1, 11);
            }

            return numberStr;
        } catch (Exception e) {
            logger.info("德州幸运飞艇杀号开奖出错：issue:{}, lotteryId:{}，{}", issue, lotteryid, e);
            return RandomUtil.getRandomStringNoSame(10, 1, 11);
        }

    }


    @Override
    public String getDzpceggKillNumber(String issue, String lotteryid) {
        try {
            //  Set<String> keys  = redisTemplate.keys(RedisKeys.KILL_ORDER + lotteryid + issue + "_*");
            List<OrderBetKillDto> list = redisTemplate.opsForList().range(RedisKeys.KILL_ORDER + lotteryid + issue, 0, -1);
            //测试开始
//            list = new ArrayList<>();
//            for (int i = 0; i < 1; i++) {
//                OrderBetKillDto dto = new OrderBetKillDto();
//                dto.setBackAmount(new BigDecimal("0"));
//                dto.setBetAmount(new BigDecimal("1"));
//                dto.setBetCount(4);
//                dto.setBetNumber("三中二@05,09,07,08");
//                dto.setId(5518196);
//                dto.setIsDelete(false);
//                dto.setIssue("423185");
//                dto.setLotteryId(1202);
//                dto.setOdds("110/20");
//                dto.setOrderId(2692194);
//                dto.setPlayId(120211);
//                dto.setSettingId(336);
//                dto.setTbStatus("WAIT");
//                dto.setUserId(33669);
//                dto.setWinAmount(new BigDecimal("0"));
//                list.add(dto);
//            }
            //测试结束

            logger.info("德州pc蛋蛋lkeys：{}", RedisKeys.KILL_ORDER + lotteryid + issue);
            String numberStr = "";
            long start = System.currentTimeMillis();
            long waittime = 5000;//等待5秒
            double winratio = 0.1;//杀号比例
            winratio = getWinratio(lotteryid);
            if (redisTemplate.hasKey(RedisKeys.KILLORDERTIME)) {
                waittime = Integer.parseInt((String) redisTemplate.opsForValue().get(RedisKeys.KILLORDERTIME)) * 1000;
            }
            if (!CollectionUtils.isEmpty(list)) {

                BigDecimal countbet = new BigDecimal(0); //总投注额
                BigDecimal countwin = new BigDecimal(0); //总中奖额

                int i = 0;
                do {
                    i++;
                    numberStr = RandomUtil.getRandomStringSame(3, 0, 10);

                    //测试屏蔽开始
                    if (start + waittime < System.currentTimeMillis()) { //超过预计时间 跳出 直接返回随机号码
                        logger.info("德州pc蛋蛋开奖超时，杀号失效：issue:{}, lotteryId:{}", issue, lotteryid);
                        break;
                    }
                    //测试屏蔽结束

//                //测试开始
//                numberStr = "04,07,08,09,10,11,02";
//                //测试结束

                    countbet = new BigDecimal(0);
                    countwin = new BigDecimal(0);
                    for (OrderBetKillDto order : list) {

                        //   OrderBetKillDto order = (OrderBetKillDto) redisTemplate.opsForValue().get(orderkey);
                        countbet = countbet.add(order.getBetAmount());

                        // 判断是否中奖
                        String win = null;
                        win = BetPceggUtil.isWin(order.getBetNumber(), numberStr, order.getPlayId());

                        if (com.caipiao.core.library.tool.StringUtils.isNotEmpty(win)) {
                            if (String.format("%02d", order.getPlayId() % 100).equals(PCEGG_PLAY_ID_TM) || String.format("%02d", order.getPlayId() % 100).equals(PCEGG_PLAY_ID_SB) ||
                                    String.format("%02d", order.getPlayId() % 100).equals(PCEGG_PLAY_ID_HH) || String.format("%02d", order.getPlayId() % 100).equals(PCEGG_PLAY_ID_BZ)) {
                                if (!order.getOdds().contains(",")) {
                                    countwin = countwin.add(order.getBetAmount().multiply(new BigDecimal(order.getOdds())));
                                } else {
                                    String[] winStrArr = win.split(",");
                                    for (String winStr : winStrArr) {
                                        BigDecimal winAmount = new BigDecimal(0);
                                        // 获取赔率信息
                                        double odd = 0d;
                                        String[] oddarr = order.getOdds().split(",");
                                        for (String oddsrt : oddarr) {
                                            if (oddsrt.contains(":")) {
                                                if (winStr.equals(oddsrt.split(":")[0])) {
                                                    odd = Double.parseDouble(oddsrt.split(":")[1]);
                                                }
                                            } else {
                                                odd = Double.parseDouble(oddsrt);
                                            }
                                        }
                                        //中奖额
                                        winAmount = winAmount.add(order.getBetAmount().multiply(BigDecimal.valueOf(odd)));
                                        countwin = countwin.add(winAmount);
                                    }
                                }

                            } else if (String.format("%02d", order.getPlayId() % 100).equals(PCEGG_PLAY_ID_TMBS)) {
                                int countWin = Integer.valueOf(win); //赢得注数
                                if (order.getOdds().contains(",")) {
                                    String oddArray[] = order.getOdds().split(",");
                                    if (oddArray.length > 0) {
                                        double odd = Double.valueOf(oddArray[0].split(":")[1]);
                                        countwin = countwin.add(order.getBetAmount().divide(new BigDecimal(order.getBetCount())).multiply(new BigDecimal(odd)).multiply(new BigDecimal(countWin)));
                                    }
                                }
                            }
                        }
                    }
                    logger.info("德州pc蛋蛋第{}次开奖：issue:{}, lotteryId:{}， countbet:{}, winratio:{}, countwin: {}", i, issue, lotteryid, countbet, winratio, countwin);
                } while ((countbet.multiply(new BigDecimal(1 - winratio))).compareTo(countwin) < 0);

            } else {
                logger.info("德州pc蛋蛋不用杀号开奖：issue:{}, lotteryId:{}", issue, lotteryid);
                return RandomUtil.getRandomStringSame(3, 0, 10);
            }
            return numberStr;
        } catch (Exception e) {
            logger.info("德州pc蛋蛋杀号开奖出错：issue:{}, lotteryId:{}，{}", issue, lotteryid, e);
            return RandomUtil.getRandomStringSame(3, 0, 10);
        }
    }

//    @Override
//    public String getXjplhcKillNumber(String issue, String lotteryid) {
//        String numberStr = RandomUtil.getRandomStringNoSame(7, 1, 49);
//        String numberArray[] = numberStr.split(",");
//        StringBuilder numbers = new StringBuilder();
//        for (String num : numberArray) {
//            if (Integer.valueOf(num) < 10) {
//                numbers.append("0").append(String.valueOf(num)).append(",");
//            } else {
//                numbers.append(String.valueOf(num)).append(",");
//            }
//        }
//        return numbers.toString().substring(0, numbers.length() - 1);
//    }


    @Override
    public String getXjplhcKillNumber(String issue, String lotteryid) {
        try {
            //  Set<String> keys  = redisTemplate.keys(RedisKeys.KILL_ORDER + lotteryid + issue + "_*");//所有要杀号的订单
            List<OrderBetKillDto> list = redisTemplate.opsForList().range(RedisKeys.KILL_ORDER + lotteryid + issue, 0, -1);

//        //测试开始
//        list = new ArrayList<>();
//        for(int i = 0;i < 1; i++){
//            OrderBetKillDto dto = new OrderBetKillDto();
//            dto.setBackAmount(new BigDecimal("0"));
//            dto.setBetAmount(new BigDecimal("1"));
//            dto.setBetCount(4);
//            dto.setBetNumber("三中二@05,09,07,08");
//            dto.setId(5518196);
//            dto.setIsDelete(false);
//            dto.setIssue("423185");
//            dto.setLotteryId(1202);
//            dto.setOdds("110/20");
//            dto.setOrderId(2692194);
//            dto.setPlayId(120211);
//            dto.setSettingId(336);
//            dto.setTbStatus("WAIT");
//            dto.setUserId(33669);
//            dto.setWinAmount(new BigDecimal("0"));
//            list.add(dto);
//        }
//        //测试结束


            logger.info("新加坡六合彩杀号keys：{}", RedisKeys.KILL_ORDER + lotteryid + issue);
            String numberStr = null;
            long start = System.currentTimeMillis();
            long waittime = 5000;//等待5秒
            double winratio = 0.1;//杀号比例

            winratio = getWinratio(lotteryid);
            if (redisTemplate.hasKey(RedisKeys.KILLORDERTIME)) {
                waittime = Integer.parseInt((String) redisTemplate.opsForValue().get(RedisKeys.KILLORDERTIME)) * 1000;
            }
            String datestr = TimeHelper.date(TimeHelper.TIME_FORMAT);
            if (!CollectionUtils.isEmpty(list)) {
                BigDecimal countbet = new BigDecimal(0); //总投注额
                BigDecimal countwin = new BigDecimal(0); //总中奖额
                int i = 0;
                do {
                    i++;
                    numberStr = this.getLhcNum();

                    //测试屏蔽开始
                    if (start + waittime < System.currentTimeMillis()) { //超过预计时间 跳出 直接返回随机号码
                        logger.info("新加坡六合彩开奖超时，杀号失效：issue:{}, lotteryId:{}", issue, lotteryid);
                        break;
                    }
                    //测试屏蔽结束


//                //测试开始
//                numberStr = "04,07,08,09,10,11,02";
//                //测试结束

                    countbet = new BigDecimal(0);
                    countwin = new BigDecimal(0);
                    for (OrderBetKillDto order : list) {
                        countbet = countbet.add(order.getBetAmount().multiply(BigDecimal.valueOf(order.getBetCount())));
                        //判断打和情况
                        if (LHCONEODDS.contains(String.format("%02d", order.getPlayId() % 100))) {
                            if (String.valueOf(order.getPlayId() % 100).equals(BetLhcServiceImpl.PLAY_ID_LX_LXLZ) ||
                                    String.valueOf(order.getPlayId() % 100).equals(BetLhcServiceImpl.PLAY_ID_LX_LXLBZ)) {
                                // 如果是六肖连中或者六肖连不中玩法,特肖开出49为和值
                                if ("49".equals(numberStr.split(",")[6])) {
                                    countwin = countwin.add(order.getBetAmount().multiply(new BigDecimal(order.getBetCount())));
                                    continue;
                                }
                            }
                            // 判断是否中奖
                            int wincounts = betLhcService.isWinByOnePlayOneOdds(order.getBetNumber(), numberStr, order.getPlayId(), datestr, order.getLotteryId());
                            if (wincounts > 0) {
                                double odd = 0d;
                                if (order.getOdds().contains(":")) {
                                    odd = Double.parseDouble(order.getOdds().split(":")[1]);
                                } else {
                                    odd = Double.parseDouble(order.getOdds());
                                }
                                BigDecimal winAmount = order.getBetAmount().multiply(BigDecimal.valueOf(odd));
                                winAmount = winAmount.multiply(BigDecimal.valueOf(wincounts));
                                countwin = countwin.add(winAmount);
                            }
                        }
                        if (LHCTWOODDS.contains(String.format("%02d", order.getPlayId() % 100))) {
                            BigDecimal winAmount = new BigDecimal(0);
                            // 判断是否中奖,获取中奖注数
                            List<Integer> twoOdds = betLhcService.isWinByOnePlayTwoOdds(order.getBetNumber(), numberStr, order.getPlayId(), datestr, order.getLotteryId());
                            if (twoOdds.get(0) > 0 || twoOdds.get(1) > 0) {
                                // 获取总注数/中奖注数
                                String[] oddsarr = order.getOdds().split("/");
                                double bigodd = 0.0;
                                double smallodd = 10000.0;
                                if (oddsarr.length > 1) {
                                    bigodd = Double.valueOf(oddsarr[0]);
                                    smallodd = Double.valueOf(oddsarr[1]);
                                } else {
                                    String oddArray[] = order.getOdds().split(",");
                                    for (String odd : oddArray) {
                                        String odds[] = odd.split(":");
                                        double oddThis = Double.valueOf(odds[1]);
                                        if (oddThis > bigodd) {
                                            bigodd = oddThis;
                                        }
                                        if (oddThis < smallodd) {
                                            smallodd = oddThis;
                                        }
                                    }
                                }

                                int bigOddsWins = twoOdds.get(0);
                                int smallOddsWins = twoOdds.get(1);

                                if (bigOddsWins > 0) {
                                    // 计算赔率
                                    double odd = 0d;
//                                if (oddsarr[0].contains(":")) {
//                                    odd = Double.parseDouble(oddsarr[0].split(":")[1]);
//                                } else {
//                                    odd = Double.parseDouble(oddsarr[0]);
//                                }

                                    odd = bigodd;
                                    // 一注的中奖额
                                    BigDecimal bigWins = order.getBetAmount().multiply(BigDecimal.valueOf(odd));
                                    winAmount = bigWins.multiply(BigDecimal.valueOf(bigOddsWins));
                                    countwin = countwin.add(winAmount);
                                }

                                if (smallOddsWins > 0) {
                                    // 计算赔率
                                    double odd = 0d;

//                                if (oddsarr[0].contains(":")) {
//                                    odd = Double.parseDouble(oddsarr[0].split(":")[1]);
//                                } else {
//                                    odd = Double.parseDouble(oddsarr[1]);
//                                }

                                    odd = smallodd;
                                    // 一注的中奖额
                                    BigDecimal smallWins = order.getBetAmount().multiply(BigDecimal.valueOf(odd));
                                    winAmount = winAmount.add(smallWins.multiply(BigDecimal.valueOf(smallOddsWins)));
                                    countwin = countwin.add(winAmount);
                                }
                            }
                        }
                        if (LHCMANYODDS.contains(String.format("%02d", order.getPlayId() % 100))) {
                            if (String.format("%02d", order.getPlayId() % 100).equals(BetLhcServiceImpl.PLAY_ID_TM_TMA_LM)) {
                                // 如果是特码A两面玩法,部分投注,特肖开出49为和值
                                if ("49".equals(numberStr.split(",")[6])) {
                                    countwin = countwin.add(order.getBetAmount().multiply(new BigDecimal(order.getBetCount())));
                                    continue;
                                }
                            }
                            // 正码1-6(正1-6特两面)的和值情况
                            if (numberStr.indexOf("49") >= 0) {
                                Boolean ishe = false;
                                String[] sgArr = numberStr.split(",");
                                if (sgArr[0].equals("49") && (order.getBetNumber().indexOf("正码一") >= 0 || (String.valueOf(order.getPlayId() % 100).equals(BetLhcServiceImpl.PLAY_ID_ZT_ONE)))) {
                                    ishe = true;
                                } else if (sgArr[1].equals("49") && (order.getBetNumber().indexOf("正码二") >= 0 || (String.valueOf(order.getPlayId() % 100).equals(BetLhcServiceImpl.PLAY_ID_ZT_TWO)))) {
                                    ishe = true;
                                } else if (sgArr[2].equals("49") && (order.getBetNumber().indexOf("正码三") >= 0 || (String.valueOf(order.getPlayId() % 100).equals(BetLhcServiceImpl.PLAY_ID_ZT_THREE)))) {
                                    ishe = true;
                                } else if (sgArr[3].equals("49") && (order.getBetNumber().indexOf("正码四") >= 0 || (String.valueOf(order.getPlayId() % 100).equals(BetLhcServiceImpl.PLAY_ID_ZT_FOUR)))) {
                                    ishe = true;
                                } else if (sgArr[4].equals("49") && (order.getBetNumber().indexOf("正码五") >= 0 || (String.valueOf(order.getPlayId() % 100).equals(BetLhcServiceImpl.PLAY_ID_ZT_FIVE)))) {
                                    ishe = true;
                                } else if (sgArr[5].equals("49") && (order.getBetNumber().indexOf("正码六") >= 0 || (String.valueOf(order.getPlayId() % 100).equals(BetLhcServiceImpl.PLAY_ID_ZT_SIX)))) {
                                    ishe = true;
                                }
                                if (ishe == true) {
                                    countwin = countwin.add(order.getBetAmount().multiply(new BigDecimal(order.getBetCount())));
                                    continue;
                                }
                            }
                            //判断中奖情况
                            String winNum = betLhcService.isWinByOnePlayManyOdds(order.getBetNumber(), numberStr, order.getPlayId(), datestr, order.getLotteryId());
                            if (com.caipiao.core.library.tool.StringUtils.isNotBlank(winNum)) {
                                String[] winStrArr = winNum.split(",");
                                for (String winStr : winStrArr) {
                                    BigDecimal winAmount = new BigDecimal(0);
                                    // 获取赔率信息
                                    double odd = 0d;
                                    String[] oddarr = order.getOdds().split(",");
                                    for (String oddsrt : oddarr) {
                                        if (oddsrt.contains(":")) {
                                            if (winStr.equals(oddsrt.split(":")[0])) {
                                                odd = Double.parseDouble(oddsrt.split(":")[1]);
                                            }
                                        } else {
                                            odd = Double.parseDouble(oddsrt);
                                        }
                                    }
                                    //中奖额
                                    winAmount = winAmount.add(order.getBetAmount().multiply(BigDecimal.valueOf(odd)));
                                    countwin = countwin.add(winAmount);
                                }

                            }


                        }
                    }
                    logger.info("新加坡六合彩第{}次开奖：issue:{}, lotteryId:{}， countbet:{}, winratio:{}, countwin: {}", i, issue, lotteryid, countbet, winratio, countwin);
                } while ((countbet.multiply(new BigDecimal(1 - winratio))).compareTo(countwin) < 0);
            } else {
                logger.info("新加坡六合彩不用杀号开奖：issue:{}, lotteryId:{}", issue, lotteryid);
                return this.getLhcNum();
            }
            return numberStr;
        } catch (Exception e) {
            logger.info("新加坡六合彩杀号开奖出错：issue:{}, lotteryId:{}，{}", issue, lotteryid, e);
            return this.getLhcNum();
        }
    }


}
