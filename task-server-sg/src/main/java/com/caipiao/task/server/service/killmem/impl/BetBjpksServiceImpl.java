package com.caipiao.task.server.service.killmem.impl;


import com.caipiao.task.server.service.killmem.BetBjpksService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lzy
 * @create 2018-09-15 17:07
 **/
@Service
@Transactional
public class BetBjpksServiceImpl implements BetBjpksService {





    // PK10猜名次猜前几
    private final List<Integer> PLAY_IDS_CMC_CQJ = Lists.newArrayList(136,  142, 144);
    // PK10单式猜前几
    private final List<Integer> PLAY_IDS_DS_CQJ = Lists.newArrayList(138, 141, 143, 145);
    // PK10单式定位胆
    private final List<Integer> PLAY_IDS_DWD = Lists.newArrayList(146, 147);

    // PK10两面
    private final String PLAY_ID_LM = "01";
    // PK10冠亚和
    private final String PLAY_ID_GYH = "02";
    // PK10 1-5名
    private final String PLAY_ID_15 = "03";
    // PK10 6-10名
    private final String PLAY_ID_610 = "04";

    // PK10 第一名
    private final String PLAY_ID_1 = "05";
    // PK10 第二名
    private final String PLAY_ID_2 = "06";
    // PK10 第三名
    private final String PLAY_ID_3 = "07";
    // PK10 第四名
    private final String PLAY_ID_4 = "08";
    // PK10 第五名
    private final String PLAY_ID_5 = "09";
    // PK10 第六名
    private final String PLAY_ID_6 = "10";
    // PK10 第七名
    private final String PLAY_ID_7 = "11";
    // PK10 第八名
    private final String PLAY_ID_8 = "12";
    // PK10 第九名
    private final String PLAY_ID_9 = "13";
    // PK10 第十名
    private final String PLAY_ID_10 = "14";

  //  private final List<String> PLAY_IDS_110 = Lists.newArrayList("03", "04");



    /**
     * 判断北京pk10是否中奖,中奖返回中奖信息,不中则返回null
     * (适用玩法:猜名次猜前几,单式猜前几,定位胆)
     *
     * @param betNum
     * @param sg
     * @return
     */
    public String isWin(String betNum, String sg, Integer playId, Integer lotteryId) {
//        String section = play.getSection();
        Integer start = 1;
        Integer end =10;
//        if(section!=null){
//        	   String[] sections = section.split(",");
//                start = Integer.parseInt(sections[0]);
//                end = Integer.parseInt(sections[1]);
//        }

        String[] betNumArrs = betNum.split(",");
        String[] sgNumArr = sg.split(",");
    //    Integer playId = play.getId();
        Integer playTagId = playId;
        if (PLAY_IDS_CMC_CQJ.contains(playId)) {
            int count = 0;
            for (int i = start - 1; i < end; i++) {
                String[] betNumArr = betNumArrs[i].split(" ");
                String sgNum = sgNumArr[i];
                for (String betNumber : betNumArr) {
                    if (betNumber.equals(sgNum)) {
                        count++;
                    }
                }
            }
            //中奖
            if (count == (end - start + 1)) {
                return betNum;
            }
        } else if (PLAY_IDS_DS_CQJ.contains(playId)) {
            for (int i = 0, len = betNumArrs.length; i < len; i++) {
                int count = 0;
                String[] betNumArr = betNumArrs[i].split(" ");
                for (int j = start - 1; j < end; j++) {
                    String sgNum = sgNumArr[j];
                    if (betNumArr[j - start + 1].equals(sgNum)) {
                        count++;
                    }

                }
                //中奖
                if (count == (end - start + 1)) {
                    return betNum;
                }
            }
        } else if (PLAY_IDS_DWD.contains(playId)) {
            // 定位胆前五,后五的玩法
            StringBuffer winNum = new StringBuffer();
            for (int i = 0, len = betNumArrs.length; i < len; i++) {
                String[] betNumArr = betNumArrs[i].split(" ");
                for (String betNumber : betNumArr) {
                    if (StringUtils.isBlank(betNumber)) {
                        continue;
                    }
                    if (Integer.valueOf(sgNumArr[i + start - 1]).equals(Integer.valueOf(betNumber))) {
                        winNum.append(betNumber).append(",");
                    }
                }
            }
            if (winNum.length() > 0) {
                return winNum.substring(0, winNum.length() - 1);
            }
        }else if (this.generationSSCPlayId(PLAY_ID_15, lotteryId).equals(playTagId) ||
                this.generationSSCPlayId(PLAY_ID_610, lotteryId).equals(playTagId)) {
            // 1-5名玩法
            StringBuffer winNum = new StringBuffer();
            for (int i = 0, len = betNumArrs.length; i < len; i++) {
                String[] betNumArr = betNumArrs[i].split(" ");
                for (String betNumber : betNumArr) {
                    if (StringUtils.isBlank(betNumber)) {
                        continue;
                    }
                    String betn=betNumber;
                    if(betNumber.contains("@"))
                        betn=	betNumber.split("@")[1];
                    if (betNumber.contains("冠军")) {
                        if(Integer.parseInt(betn)==Integer.parseInt(sgNumArr[0])){
                            winNum.append(betNumber).append(",");
                        }

                    }else if (betNumber.contains("亚军")) {
                        if(Integer.parseInt(betn)==Integer.parseInt(sgNumArr[1])){
                            winNum.append(betNumber).append(",");
                        }

                    }else if (betNumber.contains("第三名")) {
                        if(Integer.parseInt(betn)==Integer.parseInt(sgNumArr[2])){
                            winNum.append(betNumber).append(",");
                        }

                    }else if (betNumber.contains("第四名")) {
                        if(Integer.parseInt(betn)==Integer.parseInt(sgNumArr[3])){
                            winNum.append(betNumber).append(",");
                        }

                    }else if (betNumber.contains("第五名")) {
                        if(Integer.parseInt(betn)==Integer.parseInt(sgNumArr[4])){
                            winNum.append(betNumber).append(",");
                        }

                    }else if (betNumber.contains("第六名")) {
                        if(Integer.parseInt(betn)==Integer.parseInt(sgNumArr[5])){
                            winNum.append(betNumber).append(",");
                        }

                    }else if (betNumber.contains("第七名")) {
                        if(Integer.parseInt(betn)==Integer.parseInt(sgNumArr[6])){
                            winNum.append(betNumber).append(",");
                        }

                    }else if (betNumber.contains("第八名")) {
                        if(Integer.parseInt(betn)==Integer.parseInt(sgNumArr[7])){
                            winNum.append(betNumber).append(",");
                        }

                    }else if (betNumber.contains("第九名")) {
                        if(Integer.parseInt(betn)==Integer.parseInt(sgNumArr[8])){
                            winNum.append(betNumber).append(",");
                        }

                    }else if (betNumber.contains("第十名")) {
                        if(Integer.parseInt(betn)==Integer.parseInt(sgNumArr[9])){
                            winNum.append(betNumber).append(",");
                        }

                    }
                }
            }
            if (winNum.length() > 0) {
                return winNum.substring(0, winNum.length() - 1);
            }
        }else if (this.generationSSCPlayId(PLAY_ID_1, lotteryId).equals(playTagId) || this.generationSSCPlayId(PLAY_ID_2, lotteryId).equals(playTagId) || this.generationSSCPlayId(PLAY_ID_3, lotteryId).equals(playTagId) ||
                this.generationSSCPlayId(PLAY_ID_4, lotteryId).equals(playTagId) || this.generationSSCPlayId(PLAY_ID_5, lotteryId).equals(playTagId) || this.generationSSCPlayId(PLAY_ID_6, lotteryId).equals(playTagId) ||
                this.generationSSCPlayId(PLAY_ID_7, lotteryId).equals(playTagId) || this.generationSSCPlayId(PLAY_ID_8, lotteryId).equals(playTagId) || this.generationSSCPlayId(PLAY_ID_9, lotteryId).equals(playTagId) ||
                this.generationSSCPlayId(PLAY_ID_10, lotteryId).equals(playTagId)) {
            // 第一名-第十名玩法
//            StringBuffer winNum = new StringBuffer();
            String winNum = null;
            for (int i = 0, len = betNumArrs.length; i < len; i++) {
//                String[] betNumArr = betNumArrs[i].split(" ")
//                for (String betNumber : betNumArr) {
//                if (StringUtils.isBlank(betNumber)) {
//                    continue;
//                }
                String betn=null;
                if(betNum.contains("@")){
                    betn=	betNum.split("@")[1];
                }
                if(betn.equals("大") || betn.equals("小") || betn.equals("单") || betn.equals("双") || betn.equals("龙") || betn.equals("虎")){
                    if(this.generationSSCPlayId(PLAY_ID_1, lotteryId).equals(playTagId)){
                        if(betn.equals("大") && Integer.parseInt(sgNumArr[0]) > 5){
                            winNum = betNum;
                        }else if(betn.equals("小") && Integer.parseInt(sgNumArr[0]) <= 5){
                            winNum = betNum;
                        }else if(betn.equals("单") && Integer.parseInt(sgNumArr[0]) %2 != 0){
                            winNum = betNum;
                        }else if(betn.equals("双") && Integer.parseInt(sgNumArr[0]) %2 == 0){
                            winNum = betNum;
                        }else if(betn.equals("龙") && Integer.parseInt(sgNumArr[0]) > Integer.parseInt(sgNumArr[9])){
                            winNum = betNum;
                        }else if(betn.equals("虎") && Integer.parseInt(sgNumArr[0]) < Integer.parseInt(sgNumArr[9])){
                            winNum = betNum;
                        }
                    }else if(this.generationSSCPlayId(PLAY_ID_2, lotteryId).equals(playTagId)){
                        if(betn.equals("大") && Integer.parseInt(sgNumArr[1]) > 5){
                            winNum = betNum;
                        }else if(betn.equals("小") && Integer.parseInt(sgNumArr[1]) <= 5){
                            winNum = betNum;
                        }else if(betn.equals("单") && Integer.parseInt(sgNumArr[1]) %2 != 0){
                            winNum = betNum;
                        }else if(betn.equals("双") && Integer.parseInt(sgNumArr[1]) %2 == 0){
                            winNum = betNum;
                        }else if(betn.equals("龙") && Integer.parseInt(sgNumArr[1]) > Integer.parseInt(sgNumArr[8])){
                            winNum = betNum;
                        }else if(betn.equals("虎") && Integer.parseInt(sgNumArr[1]) < Integer.parseInt(sgNumArr[8])){
                            winNum = betNum;
                        }
                    }else if(this.generationSSCPlayId(PLAY_ID_3, lotteryId).equals(playTagId)){
                        if(betn.equals("大") && Integer.parseInt(sgNumArr[2]) > 5){
                            winNum = betNum;
                        }else if(betn.equals("小") && Integer.parseInt(sgNumArr[2]) <= 5){
                            winNum = betNum;
                        }else if(betn.equals("单") && Integer.parseInt(sgNumArr[2]) %2 != 0){
                            winNum = betNum;
                        }else if(betn.equals("双") && Integer.parseInt(sgNumArr[2]) %2 == 0){
                            winNum = betNum;
                        }else if(betn.equals("龙") && Integer.parseInt(sgNumArr[2]) > Integer.parseInt(sgNumArr[7])){
                            winNum = betNum;
                        }else if(betn.equals("虎") && Integer.parseInt(sgNumArr[2]) < Integer.parseInt(sgNumArr[7])){
                            winNum = betNum;
                        }
                    }else if(this.generationSSCPlayId(PLAY_ID_4, lotteryId).equals(playTagId)){
                        if(betn.equals("大") && Integer.parseInt(sgNumArr[3]) > 5){
                            winNum = betNum;
                        }else if(betn.equals("小") && Integer.parseInt(sgNumArr[3]) <= 5){
                            winNum = betNum;
                        }else if(betn.equals("单") && Integer.parseInt(sgNumArr[3]) %2 != 0){
                            winNum = betNum;
                        }else if(betn.equals("双") && Integer.parseInt(sgNumArr[3]) %2 == 0){
                            winNum = betNum;
                        }else if(betn.equals("龙") && Integer.parseInt(sgNumArr[3]) > Integer.parseInt(sgNumArr[6])){
                            winNum = betNum;
                        }else if(betn.equals("虎") && Integer.parseInt(sgNumArr[3]) < Integer.parseInt(sgNumArr[6])){
                            winNum = betNum;
                        }
                    }else if(this.generationSSCPlayId(PLAY_ID_5, lotteryId).equals(playTagId)){
                        if(betn.equals("大") && Integer.parseInt(sgNumArr[4]) > 5){
                            winNum = betNum;
                        }else if(betn.equals("小") && Integer.parseInt(sgNumArr[4]) <= 5){
                            winNum = betNum;
                        }else if(betn.equals("单") && Integer.parseInt(sgNumArr[4]) %2 != 0){
                            winNum = betNum;
                        }else if(betn.equals("双") && Integer.parseInt(sgNumArr[4]) %2 == 0){
                            winNum = betNum;
                        }else if(betn.equals("龙") && Integer.parseInt(sgNumArr[4]) > Integer.parseInt(sgNumArr[5])){
                            winNum = betNum;
                        }else if(betn.equals("虎") && Integer.parseInt(sgNumArr[4]) < Integer.parseInt(sgNumArr[5])){
                            winNum = betNum;
                        }
                    }else if(this.generationSSCPlayId(PLAY_ID_6, lotteryId).equals(playTagId)){
                        if(betn.equals("大") && Integer.parseInt(sgNumArr[5]) > 5){
                            winNum = betNum;
                        }else if(betn.equals("小") && Integer.parseInt(sgNumArr[5]) <= 5){
                            winNum = betNum;
                        }else if(betn.equals("单") && Integer.parseInt(sgNumArr[5]) %2 != 0){
                            winNum = betNum;
                        }else if(betn.equals("双") && Integer.parseInt(sgNumArr[5]) %2 == 0){
                            winNum = betNum;
                        }
                    }else if(this.generationSSCPlayId(PLAY_ID_7, lotteryId).equals(playTagId)){
                        if(betn.equals("大") && Integer.parseInt(sgNumArr[6]) > 5){
                            winNum = betNum;
                        }else if(betn.equals("小") && Integer.parseInt(sgNumArr[6]) <= 5){
                            winNum = betNum;
                        }else if(betn.equals("单") && Integer.parseInt(sgNumArr[6]) %2 != 0){
                            winNum = betNum;
                        }else if(betn.equals("双") && Integer.parseInt(sgNumArr[6]) %2 == 0){
                            winNum = betNum;
                        }
                    }else if(this.generationSSCPlayId(PLAY_ID_8, lotteryId).equals(playTagId)){
                        if(betn.equals("大") && Integer.parseInt(sgNumArr[7]) > 5){
                            winNum = betNum;
                        }else if(betn.equals("小") && Integer.parseInt(sgNumArr[7]) <= 5){
                            winNum = betNum;
                        }else if(betn.equals("单") && Integer.parseInt(sgNumArr[7]) %2 != 0){
                            winNum = betNum;
                        }else if(betn.equals("双") && Integer.parseInt(sgNumArr[7]) %2 == 0){
                            winNum = betNum;
                        }
                    }else if(this.generationSSCPlayId(PLAY_ID_9, lotteryId).equals(playTagId)){
                        if(betn.equals("大") && Integer.parseInt(sgNumArr[8]) > 5){
                            winNum = betNum;
                        }else if(betn.equals("小") && Integer.parseInt(sgNumArr[8]) <= 5){
                            winNum = betNum;
                        }else if(betn.equals("单") && Integer.parseInt(sgNumArr[8]) %2 != 0){
                            winNum = betNum;
                        }else if(betn.equals("双") && Integer.parseInt(sgNumArr[8]) %2 == 0){
                            winNum = betNum;
                        }
                    }else if(this.generationSSCPlayId(PLAY_ID_10, lotteryId).equals(playTagId)){
                        if(betn.equals("大") && Integer.parseInt(sgNumArr[9]) > 5){
                            winNum = betNum;
                        }else if(betn.equals("小") && Integer.parseInt(sgNumArr[9]) <= 5){
                            winNum = betNum;
                        }else if(betn.equals("单") && Integer.parseInt(sgNumArr[9]) %2 != 0){
                            winNum = betNum;
                        }else if(betn.equals("双") && Integer.parseInt(sgNumArr[9]) %2 == 0){
                            winNum = betNum;
                        }
                    }

                }else{
                    if (betNum.contains("冠军")) {
                        if(Integer.parseInt(betn)==Integer.parseInt(sgNumArr[0])){
                            winNum = betNum;
                        }

                    }else if (betNum.contains("亚军")) {
                        if(Integer.parseInt(betn)==Integer.parseInt(sgNumArr[1])){
                            winNum = betNum;
                        }

                    }else if (betNum.contains("第三名")) {
                        if(Integer.parseInt(betn)==Integer.parseInt(sgNumArr[2])){
                            winNum = betNum;
                        }

                    }else if (betNum.contains("第四名")) {
                        if(Integer.parseInt(betn)==Integer.parseInt(sgNumArr[3])){
                            winNum = betNum;
                        }

                    }else if (betNum.contains("第五名")) {
                        if(Integer.parseInt(betn)==Integer.parseInt(sgNumArr[4])){
                            winNum = betNum;
                        }

                    }else if (betNum.contains("第六名")) {
                        if(Integer.parseInt(betn)==Integer.parseInt(sgNumArr[5])){
                            winNum = betNum;
                        }

                    }else if (betNum.contains("第七名")) {
                        if(Integer.parseInt(betn)==Integer.parseInt(sgNumArr[6])){
                            winNum = betNum;
                        }

                    }else if (betNum.contains("第八名")) {
                        if(Integer.parseInt(betn)==Integer.parseInt(sgNumArr[7])){
                            winNum = betNum;
                        }

                    }else if (betNum.contains("第九名")) {
                        if(Integer.parseInt(betn)==Integer.parseInt(sgNumArr[8])){
                            winNum = betNum;
                        }

                    }else if (betNum.contains("第十名")) {
                        if(Integer.parseInt(betn)==Integer.parseInt(sgNumArr[9])){
                            winNum = betNum;
                        }
                    }
                }

            }
            if (winNum != null && winNum.length() > 0) {
                return winNum.substring(0, winNum.length() - 1);
            }

        }

        return null;
    }

    // ################################# 使用了redis start ###################################################

    /**
     * 根据期号获取赛果
     *
     * @param issue 期号
     * @return
     */
//    private BjpksLotterySg getLotterySg(String issue) {
//        BjpksLotterySg sg = null;
//        // 获取该期赛果信息
//        if (redisTemplate.hasKey(BJPKS_LOTTERY_SG + issue)) {
//            sg = (BjpksLotterySg) redisTemplate.opsForValue().get(BJPKS_LOTTERY_SG + issue);
//        }
//        if (sg == null) {
//            BjpksLotterySgExample sgExample = new BjpksLotterySgExample();
//            BjpksLotterySgExample.Criteria sgCriteria = sgExample.createCriteria();
//            sgCriteria.andIssueEqualTo(issue);
//            sg = bjpksLotterySgMapper.selectOneByExample(sgExample);
//            redisTemplate.opsForValue().set(BJPKS_LOTTERY_SG + issue, sg, 2, TimeUnit.MINUTES);
//        }
//        return sg;
//    }


    // ################################# 使用了redis end ###################################################

    /**
     * 判断北京pk10是否中奖,中奖返回中奖信息,不中则返回null
     * (适用玩法:两面, 冠亚和)
     *
     * @param betNum 投注号码
     * @param sg     开奖号码
     * @param playId 玩法id
     * @return
     */
    public  String isWinLmAndGyh(String betNum, String sg, Integer playId,Integer lotteryId) {
        String[] betNumArr = betNum.split(",");
        String[] sgNumArr = sg.split(",");
        Integer num1 = Integer.valueOf(sgNumArr[0]);
        Integer num2 = Integer.valueOf(sgNumArr[1]);
        int he = num1 + num2;
        StringBuilder winStr = new StringBuilder();
        String betStr0="";
        if (this.generationSSCPlayId(PLAY_ID_LM, lotteryId).equals(playId)) {
            for (String betStr : betNumArr) {
            	if(betStr.contains("@")){
            		betStr0=betStr.split("@")[0];
                	betStr=betStr.split("@")[1];
            	}
            		
                if ("冠亚大".equals(betStr) && he > 11) {
                    winStr.append(betStr).append(",");
                } else if ("冠亚小".equals(betStr) && he <= 11) {
                    winStr.append(betStr).append(",");
                } else if ("冠亚单".equals(betStr) && he % 2 == 1) {
                    winStr.append(betStr).append(",");
                } else if ("冠亚双".equals(betStr) && he % 2 == 0) {
                    winStr.append(betStr).append(",");
                } else if ("冠军大".equals(betStr0+betStr) && num1 > 5) {
                    winStr.append(betStr).append(",");
                } else if ("冠军小".equals(betStr0+betStr) && num1 <= 5) {
                    winStr.append(betStr).append(",");
                } else if ("冠军单".equals(betStr0+betStr) && num1 % 2 == 1) {
                    winStr.append(betStr).append(",");
                } else if ("冠军双".equals(betStr0+betStr) && num1 % 2 == 0) {
                    winStr.append(betStr).append(",");
                } else if ("冠军龙".equals(betStr0+betStr) && num1 > Integer.valueOf(sgNumArr[9])) {
                    winStr.append(betStr).append(",");
                } else if ("冠军虎".equals(betStr0+betStr) && num1 < Integer.valueOf(sgNumArr[9])) {
                    winStr.append(betStr).append(",");
                } else if ("亚军大".equals(betStr0+betStr) && num2 > 5) {
                    winStr.append(betStr).append(",");
                } else if ("亚军小".equals(betStr0+betStr) && num2 <= 5) {
                    winStr.append(betStr).append(",");
                } else if ("亚军单".equals(betStr0+betStr) && num2 % 2 == 1) {
                    winStr.append(betStr).append(",");
                } else if ("亚军双".equals(betStr0+betStr) && num2 % 2 == 0) {
                    winStr.append(betStr).append(",");
                } else if ("亚军龙".equals(betStr0+betStr) && num2 > Integer.valueOf(sgNumArr[8])) {
                    winStr.append(betStr).append(",");
                } else if ("亚军虎".equals(betStr0+betStr) && num2 < Integer.valueOf(sgNumArr[8])) {
                    winStr.append(betStr).append(",");
                } else if ("第三名大".equals(betStr0+betStr) && da(sgNumArr[2])) {
                    winStr.append(betStr).append(",");
                } else if ("第三名小".equals(betStr0+betStr) && !da(sgNumArr[2])) {
                    winStr.append(betStr).append(",");
                } else if ("第三名单".equals(betStr0+betStr) && dan(sgNumArr[2])) {
                    winStr.append(betStr).append(",");
                } else if ("第三名双".equals(betStr0+betStr) && !dan(sgNumArr[2])) {
                    winStr.append(betStr).append(",");
                } else if ("第三名龙".equals(betStr0+betStr) && !hu(sgNumArr[2], sgNumArr[7])) {
                    winStr.append(betStr).append(",");
                } else if ("第三名虎".equals(betStr0+betStr) && hu(sgNumArr[2], sgNumArr[7])) {
                    winStr.append(betStr).append(",");
                } else if ("第四名大".equals(betStr0+betStr) && da(sgNumArr[3])) {
                    winStr.append(betStr).append(",");
                } else if ("第四名小".equals(betStr0+betStr) && !da(sgNumArr[3])) {
                    winStr.append(betStr).append(",");
                } else if ("第四名单".equals(betStr0+betStr) && dan(sgNumArr[3])) {
                    winStr.append(betStr).append(",");
                } else if ("第四名双".equals(betStr0+betStr) && !dan(sgNumArr[3])) {
                    winStr.append(betStr).append(",");
                } else if ("第四名龙".equals(betStr0+betStr) && !hu(sgNumArr[3], sgNumArr[6])) {
                    winStr.append(betStr).append(",");
                } else if ("第四名虎".equals(betStr0+betStr) && hu(sgNumArr[3], sgNumArr[6])) {
                    winStr.append(betStr).append(",");
                } else if ("第五名大".equals(betStr0+betStr) && da(sgNumArr[4])) {
                    winStr.append(betStr).append(",");
                } else if ("第五名小".equals(betStr0+betStr) && !da(sgNumArr[4])) {
                    winStr.append(betStr).append(",");
                } else if ("第五名单".equals(betStr0+betStr) && dan(sgNumArr[4])) {
                    winStr.append(betStr).append(",");
                } else if ("第五名双".equals(betStr0+betStr) && !dan(sgNumArr[4])) {
                    winStr.append(betStr).append(",");
                } else if ("第五名龙".equals(betStr0+betStr) && !hu(sgNumArr[4], sgNumArr[5])) {
                    winStr.append(betStr).append(",");
                } else if ("第五名虎".equals(betStr0+betStr) && hu(sgNumArr[4], sgNumArr[5])) {
                    winStr.append(betStr).append(",");
                } else if ("第六名大".equals(betStr0+betStr) && da(sgNumArr[5])) {
                    winStr.append(betStr).append(",");
                } else if ("第六名小".equals(betStr0+betStr) && !da(sgNumArr[5])) {
                    winStr.append(betStr).append(",");
                } else if ("第六名单".equals(betStr0+betStr) && dan(sgNumArr[5])) {
                    winStr.append(betStr).append(",");
                } else if ("第六名双".equals(betStr0+betStr) && !dan(sgNumArr[5])) {
                    winStr.append(betStr).append(",");
                } else if ("第七名大".equals(betStr0+betStr) && da(sgNumArr[6])) {
                    winStr.append(betStr).append(",");
                } else if ("第七名小".equals(betStr0+betStr) && !da(sgNumArr[6])) {
                    winStr.append(betStr).append(",");
                } else if ("第七名单".equals(betStr0+betStr) && dan(sgNumArr[6])) {
                    winStr.append(betStr).append(",");
                } else if ("第七名双".equals(betStr0+betStr) && !dan(sgNumArr[6])) {
                    winStr.append(betStr).append(",");
                } else if ("第八名大".equals(betStr0+betStr) && da(sgNumArr[7])) {
                    winStr.append(betStr).append(",");
                } else if ("第八名小".equals(betStr0+betStr) && !da(sgNumArr[7])) {
                    winStr.append(betStr).append(",");
                } else if ("第八名单".equals(betStr0+betStr) && dan(sgNumArr[7])) {
                    winStr.append(betStr).append(",");
                } else if ("第八名双".equals(betStr0+betStr) && !dan(sgNumArr[7])) {
                    winStr.append(betStr).append(",");
                } else if ("第九名大".equals(betStr0+betStr) && da(sgNumArr[8])) {
                    winStr.append(betStr).append(",");
                } else if ("第九名小".equals(betStr0+betStr) && !da(sgNumArr[8])) {
                    winStr.append(betStr).append(",");
                } else if ("第九名单".equals(betStr0+betStr) && dan(sgNumArr[8])) {
                    winStr.append(betStr).append(",");
                } else if ("第九名双".equals(betStr0+betStr) && !dan(sgNumArr[8])) {
                    winStr.append(betStr).append(",");
                } else if ("第十名大".equals(betStr0+betStr) && da(sgNumArr[9])) {
                    winStr.append(betStr).append(",");
                } else if ("第十名小".equals(betStr0+betStr) && !da(sgNumArr[9])) {
                    winStr.append(betStr).append(",");
                } else if ("第十名单".equals(betStr0+betStr) && dan(sgNumArr[9])) {
                    winStr.append(betStr).append(",");
                } else if ("第十名双".equals(betStr0+betStr) && !dan(sgNumArr[9])) {
                    winStr.append(betStr).append(",");
                }
            }
        } else if (this.generationSSCPlayId(PLAY_ID_GYH, lotteryId).equals(playId)) {
            for (String betStr : betNumArr) {
            	if(betStr.contains("@"))
            	betStr=betStr.split("@")[1];
                switch (betStr) {
                    case "冠亚大" :
                        if (he > 11) {
                            winStr.append(betStr).append(",");
                        }
                        break;
                    case "冠亚小" :
                        if (he <= 11) {
                            winStr.append(betStr).append(",");
                        }
                        break;
                    case "冠亚单" :
                        if (he % 2 == 1) {
                            winStr.append(betStr).append(",");
                        }
                        break;
                    case "冠亚双" :
                        if (he % 2 == 0) {
                            winStr.append(betStr).append(",");
                        }
                        break;
                    default:
                        int betInt = Integer.valueOf(betStr);
                        if (he == betInt) {
                            winStr.append(he).append(",");
                        }
                        break;
                }
            }
        }

        if (winStr.length() > 0) {
        
            return winStr.substring(0, winStr.length() - 1);
        }
        return null;
    }

    /**
     * 判断号码是否为单
     *
     * @param num
     * @return
     */
    public boolean dan(String num) {
        return Integer.valueOf(num) % 2 == 1;
    }

    /**
     * 判断号码是否为大
     *
     * @param num
     * @return
     */
    public boolean da(String num) {
        return Integer.valueOf(num) > 5;
    }

    /**
     * 判断号码是否为虎
     *
     * @param num1
     * @param num2
     * @return
     */
    public boolean hu(String num1, String num2) {
        return Integer.valueOf(num1) < Integer.valueOf(num2);
    }
    
    /**
     *  -时时彩 玩法ID生成
     * @param playNumber
     * @param
     * @return
     */
    private Integer generationSSCPlayId(String playNumber, Integer lotteryId) {
    	return Integer.parseInt(lotteryId+playNumber);
    }
    

    



}
