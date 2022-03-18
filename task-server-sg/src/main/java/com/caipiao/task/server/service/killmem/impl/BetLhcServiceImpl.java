package com.caipiao.task.server.service.killmem.impl;


import com.caipiao.core.library.rule.LhcPlayRule;
import com.caipiao.core.library.tool.CountUtils;
import com.caipiao.core.library.tool.LhcUtils;
import com.caipiao.task.server.service.killmem.BetLhcService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author lzy
 * @create 2018-09-18 15:21
 **/
@Service
public class BetLhcServiceImpl implements BetLhcService {
    // 彩种id：4 六合彩
   // private final Integer lotteryId = 4;



    // 六合彩特码特码A玩法id
    private final String PLAY_ID_TM_TMA = "01";
    // 六合彩特码特码A两面玩法id
    public static  final String PLAY_ID_TM_TMA_LM = "02";
    // 六合彩正码正码A玩法id
    private final String PLAY_ID_ZM_ZMA = "03";
    // 六合彩正码正码A两面玩法id
    //private String PLAY_ID_ZM_ZMA_LM = 244;
    // 六合彩正码正码1-6玩法id集合
    //private final List<Integer> PLAY_IDS_ZM_OTS = Lists.newArrayList(
    	//	120405, 120406, 120407, 120408, 120409, 120410);
    // 六合彩正码正码1-6玩法id集合
    private final String PLAY_IDS_ZM = "04";
    // 六合彩正码正码1玩法id
    //private String PLAY_ID_ZM_ONE = 120405;
    // 六合彩正码正码2玩法id
    //private String PLAY_ID_ZM_TWO = 120406;
    // 六合彩正码正码3玩法id
   //private String PLAY_ID_ZM_THREE = 120407;
    // 六合彩正码正码4玩法id
    //private String PLAY_ID_ZM_FOUR = 120408;
    // 六合彩正码正码5玩法id
    //private String PLAY_ID_ZM_FIVE = 120409;
    // 六合彩正码正码6玩法id
    //private String PLAY_ID_ZM_SIX = 120410;
    // 六合彩正特正(1-6)特玩法id集合
    private final List<String> PLAY_IDS_ZT_OTS = Lists.newArrayList("05", "06", "07", "08", "09", "10");
    // 六合彩正特正一特玩法id
    public static final String PLAY_ID_ZT_ONE = "05";
    // 六合彩正特正一特玩法id
    public static final String PLAY_ID_ZT_TWO = "06";
    // 六合彩正特正一特玩法id
    public static final String PLAY_ID_ZT_THREE = "07";
    // 六合彩正特正一特玩法id
    public static final String PLAY_ID_ZT_FOUR = "08";
    // 六合彩正特正一特玩法id
    public static final String PLAY_ID_ZT_FIVE = "09";
    // 六合彩正特正一特玩法id
    public static final String PLAY_ID_ZT_SIX = "10";

    // 六合彩正特正(1-6)特两面玩法id集合
    private final List<Integer> PLAY_IDS_ZT_OTS_LM = Lists.newArrayList(338, 339, 331, 335, 336, 337);
    // 六合彩正特正一特两面玩法id
   // private String PLAY_ID_ZT_ONE_LM = 338;
    // 六合彩正特正一特两面玩法id
    //private String PLAY_ID_ZT_TWO_LM = 339;
    // 六合彩正特正一特两面玩法id
    //private String PLAY_ID_ZT_THREE_LM = 331;
    // 六合彩正特正一特两面玩法id
    //private String PLAY_ID_ZT_FOUR_LM = 335;
    // 六合彩正特正一特两面玩法id
    //private String PLAY_ID_ZT_FIVE_LM = 336;
    // 六合彩正特正一特两面玩法id
    //private String PLAY_ID_ZT_SIX_LM = 337;

    // 六合彩连码三全中,二全中,特串玩法id集合
    private final List<String> PLAY_IDS_LM_QZ = Lists.newArrayList("15", "14", "13");
    // 六合彩连码特串玩法id
    private final String PLAY_ID_LM_TC = "13";
    // 六合彩连码二全中玩法id
    private final String PLAY_ID_LM_EQZ = "14";
    // 六合彩连码三全中玩法id
    private final String PLAY_ID_LM_SQZ = "15";

    // 六合彩连码三中二,二中特玩法id集合
    private final List<String> PLAY_IDS_LM_EZ = Lists.newArrayList("11", "12");
    // 六合彩连码二中特玩法id
    private final String PLAY_ID_LM_EZT = "12";
    // 六合彩连码三中二玩法id
    private final String PLAY_ID_LM_SZE = "11";

    // 六合彩半波红波玩法id
    private final String PLAY_ID_BB_RED = "16";
    // 六合彩半波蓝波玩法id
    private final String PLAY_ID_BB_BLUE = "17";
    // 六合彩半波绿波玩法id
    private final String PLAY_ID_BB_GREEN = "18";

    // 六合彩不中玩法id集合
    private final List<String> PLAY_IDS_NO_OPEN = Lists.newArrayList("21", "22", "23", "24", "25", "26");
    // 六合彩五不中玩法id
    private final String PLAY_ID_NO_OPEN_FIVE = "21";
    // 六合彩六不中玩法id
    private final String PLAY_ID_NO_OPEN_SIX = "22";
    // 六合彩七不中玩法id
    private final String PLAY_ID_NO_OPEN_SEVEN = "23";
    // 六合彩八不中玩法id
    private final String PLAY_ID_NO_OPEN_EIGHT = "24";
    // 六合彩九不中玩法id
    private final String PLAY_ID_NO_OPEN_NINE = "25";
    // 六合彩十不中玩法id
    private final String PLAY_ID_NO_OPEN_TEN = "26";

    // 六合彩尾数全尾玩法id
    private final String PLAY_ID_WS_QW = "19";
    // 六合彩尾数特尾玩法id
    private final String PLAY_ID_WS_TW = "20";
    // 六合彩平特玩法id
    private final String PLAY_ID_PT_PT = "27";
    // 六合彩特肖玩法id
    private final String PLAY_ID_TX_TX = "28";
    // 六合彩六肖连中玩法id
    public static final String PLAY_ID_LX_LXLZ = "29";
    // 六合彩六肖连不中玩法id
    public static final String PLAY_ID_LX_LXLBZ = "30";

    // 六合彩连肖中玩法id集合
    private final List<String> PLAY_IDS_LX_WIN = Lists.newArrayList("31", "33", "35");
    // 六合彩二连肖中玩法id
    private final String PLAY_ID_LX_TWO_WIN = "31";
    // 六合彩三连肖中玩法id
    private final String PLAY_ID_LX_THREE_WIN = "33";
    // 六合彩四连肖中玩法id
    private String PLAY_ID_LX_FOUR_WIN = "35";
    // 六合彩连肖不中玩法id集合
    private final List<String> PLAY_IDS_LX_NO_WIN = Lists.newArrayList("32", "34", "36");
    // 六合彩二连肖不中玩法id
    private final String PLAY_ID_LX_TWO_NO_WIN = "32";
    // 六合彩三连肖不中玩法id
    private final String PLAY_ID_LX_THREE_NO_WIN = "34";
    // 六合彩四连肖不中玩法id
    private final String PLAY_ID_LX_FOUR_NO_WIN = "36";

    // 六合彩连尾中玩法id集合
    private final List<String> PLAY_IDS_LW_WIN = Lists.newArrayList("37", "39", "41");
    // 六合彩二连尾中玩法id
    private final String PLAY_ID_LW_TWO_WIN = "37";
    // 六合彩三连尾中玩法id
    private final String PLAY_ID_LW_THREE_WIN = "39";
    // 六合彩四连尾中玩法id
    private final String PLAY_ID_LW_FOUR_WIN = "41";
    // 六合彩连尾不中玩法id集合
    private final List<String> PLAY_IDS_LW_NO_WIN = Lists.newArrayList("38", "40", "42");
    // 六合彩二连尾不中玩法id
    private final String PLAY_ID_LW_TWO_NO_WIN = "38";
    // 六合彩三连尾不中玩法id
    private final String PLAY_ID_LW_THREE_NO_WIN = "40";
    // 六合彩四连尾不中玩法id
    private final String PLAY_ID_LW_FOUR_NO_WIN = "42";

    // 六合彩1-6龙虎玩法id
    private final String PLAY_ID_ONE_SIX_LH = "43";
    // 六合彩五行玩法id
    private final String PLAY_ID_WUXING = "44";

    // 可能打和的投注信息
    private final List<String> MAYBE_HE = Lists.newArrayList("大", "小", "单", "双", "合单", "合双", "尾大", "尾小");



    /**
     * 获取六合彩中奖注数(一种玩法,有高低两种赔率)
     * (适用玩法:连肖中,连尾不中,连码三中二,连码二中特)
     *
     * @param betNumber 投注号码
     * @param sgNumber  开奖号码
     * @param playId    玩法id
     * @param dateStr   开奖时间 yyyy-MM-dd
     * @return
     */
    public List<Integer> isWinByOnePlayTwoOdds(String betNumber, String sgNumber, Integer playId, String dateStr,Integer lotteryId) {
        if (this.generationLHCPlayIdList(PLAY_IDS_LX_WIN,lotteryId).contains(playId)) {
            // 连肖中玩法
            return isWinByLxWin(betNumber, sgNumber, playId, dateStr, lotteryId);
        } else if (this.generationLHCPlayIdList(PLAY_IDS_LW_NO_WIN,lotteryId).contains(playId)) {
            // 连尾不中玩法
            return isWinByLwNoWin(betNumber, sgNumber, playId, lotteryId);
        } else if (this.generationLHCPlayIdList(PLAY_IDS_LM_EZ,lotteryId).contains(playId)) {
            // 连码三中二,连码二中特
            return isWinLianMaEz(betNumber, sgNumber, playId, lotteryId);
        }
        return null;
    }

    /**
     * 获取六合彩中奖注数(一种玩法,只有一种赔率)
     * (适用玩法: 连码(三全中,二全中,特串,不中,连肖不中), 连尾中, 1-6龙虎, 五行)
     *
     * @param betNumber 投注号码
     * @param sgNumber  开奖号码
     * @param playId    玩法id
     * @param dateStr   开奖时间 yyyy-MM-dd
     * @return
     */
    public int isWinByOnePlayOneOdds(String betNumber, String sgNumber, Integer playId, String dateStr,Integer lotteryId) {
    	if (this.generationLHCPlayIdList(PLAY_IDS_LX_NO_WIN,lotteryId).contains(playId)) {
            // 连肖不中玩法
            return isWinByLxNoWin(betNumber, sgNumber, playId, dateStr,lotteryId);
        } else if (this.generationLHCPlayIdList(PLAY_IDS_LW_WIN,lotteryId).contains(playId)) {
            // 连尾中玩法
            return isWinByLwWin(betNumber, sgNumber, playId,lotteryId);
        } else if (this.generationLHCPlayId(PLAY_ID_ONE_SIX_LH,lotteryId).equals(playId)) {
            // 1-6龙虎玩法
            return isWinByOneSixLh(betNumber, sgNumber);
        } else if (this.generationLHCPlayId(PLAY_ID_WUXING,lotteryId).equals(playId)) {
            // 五行玩法
            return isWinByWx(betNumber, sgNumber, dateStr);
        } else if (this.generationLHCPlayId(PLAY_ID_LX_LXLZ,lotteryId).equals(playId) || 
        		this.generationLHCPlayId(PLAY_ID_LX_LXLBZ,lotteryId).equals(playId)) {
            // 六肖连中 或者 六肖连不中
            return isWinByLiuXiao(betNumber, sgNumber, playId, dateStr,lotteryId);
        } else if (this.generationLHCPlayIdList(PLAY_IDS_NO_OPEN, lotteryId).contains(playId)) {
            // 不中,如五不中,六不中...
            return isWinByNoOpen(betNumber, sgNumber, playId,lotteryId);
        } else if (this.generationLHCPlayIdList(PLAY_IDS_LM_QZ, lotteryId).contains(playId)) {
            // 连码三全中,二全中,特串
            return isWinLianMaQz(betNumber, sgNumber, playId,lotteryId);
        } else if (this.generationLHCPlayIdList(PLAY_IDS_ZT_OTS, lotteryId).contains(playId)) {
            // 正特正(1-6)特
            return isWinZhengTeOneToSix(betNumber, sgNumber, playId,lotteryId);
        } else if (this.generationLHCPlayId(PLAY_ID_TM_TMA,lotteryId).equals(playId)) {
            // 特码特码A
            return isWinZhengTeOneToSix(betNumber, sgNumber, playId,lotteryId);
        } else if (this.generationLHCPlayId(PLAY_ID_ZM_ZMA,lotteryId).equals(playId)) {
            // 正码正码A
            return isWinByNum(betNumber, sgNumber);
        }
        return 0;
    }

    /**
     * 判断六合彩是否中奖,中奖返回中奖信息,不中则返回null(一种玩法,多种赔率)
     * (适用玩法:尾数,平特,特肖,半波)
     *
     * @param betNumber 投注号码
     * @param sgNumber  开奖号码
     * @param playId    玩法id
     * @param dateStr   开奖时间 yyyy-MM-dd
     * @return
     */
    public String isWinByOnePlayManyOdds(String betNumber, String sgNumber, Integer playId, String dateStr,Integer lotteryId) {
        String[] betNumArr = betNumber.split("@")[1].split(",");
        String[] sgArr = sgNumber.split(",");
        StringBuffer winStr = new StringBuffer();
        if (this.generationLHCPlayId(PLAY_ID_WS_QW,lotteryId).equals(playId)) {
            // 尾数全尾
            List<Integer> sgList = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                sgList.add(Integer.valueOf(sgArr[i]) % 10);
            }
            for (String betNum : betNumArr) {
                if (sgList.contains(Integer.parseInt(betNum.replace("尾", "")))) {
                    winStr.append(betNum).append(",");
                }
            }
        } else if (this.generationLHCPlayId(PLAY_ID_WS_TW,lotteryId).equals(playId)) {
            // 尾数特尾
            int tw = Integer.valueOf(sgArr[6]) % 10;
            for (String betNum : betNumArr) {
                if (tw == Integer.parseInt(betNum.replace("尾", ""))) {
                    winStr.append(betNum).append(",");
                }
            }
        } else if (this.generationLHCPlayId(PLAY_ID_PT_PT,lotteryId).equals(playId)) {
            // 平特
            List<String> sgList = LhcUtils.getNumberShengXiao(sgNumber, dateStr);
            for (String betNum : betNumArr) {
                if (sgList.contains(betNum)) {
                    winStr.append(betNum).append(",");
                }
            }
        } else if (this.generationLHCPlayId(PLAY_ID_TX_TX,lotteryId).equals(playId)) {
            // 特肖
            String tx = LhcUtils.getShengXiao(Integer.valueOf(sgArr[6]), dateStr);
            for (String betNum : betNumArr) {
                if (tx.equals(betNum)) {
                    winStr.append(betNum).append(",");
                }
            }
        } else if (this.generationLHCPlayId(PLAY_ID_BB_RED,lotteryId).equals(playId) || 
        		this.generationLHCPlayId(PLAY_ID_BB_BLUE,lotteryId).equals(playId) || 
        				this.generationLHCPlayId(PLAY_ID_BB_GREEN,lotteryId).equals(playId)) {
            // 半波红波,蓝波,绿波
            List<String> numBanboList = LhcPlayRule.getNumBanboList(Integer.valueOf(sgArr[6]));
            for (String betNum : betNumArr) {
                if (numBanboList.contains(betNum)) {
                    winStr.append(betNum).append(",");
                }
            }
        } else if (this.generationLHCPlayIdList(PLAY_IDS_ZT_OTS,lotteryId).contains(playId) || this.generationLHCPlayId(PLAY_IDS_ZM,lotteryId).equals(playId)) {
            // 正特正(1-6)特 两面 或者 正码1-6
            int index = 6;
            /**if (PLAY_ID_ZT_ONE_LM.equals(playId) || PLAY_ID_ZM_ONE.equals(playId)) {
                index = 0;
            } else if (PLAY_ID_ZT_TWO_LM.equals(playId) || PLAY_ID_ZM_TWO.equals(playId)) {
                index = 1;
            } else if (PLAY_ID_ZT_THREE_LM.equals(playId) || PLAY_ID_ZM_THREE.equals(playId)) {
                index = 2;
            } else if (PLAY_ID_ZT_FOUR_LM.equals(playId) || PLAY_ID_ZM_FOUR.equals(playId)) {
                index = 3;
            } else if (PLAY_ID_ZT_FIVE_LM.equals(playId) || PLAY_ID_ZM_FIVE.equals(playId)) {
                index = 4;
            } else if (PLAY_ID_ZT_SIX_LM.equals(playId) || PLAY_ID_ZM_SIX.equals(playId)) {
                index = 5;
            }**/
            
            if (this.generationLHCPlayId(PLAY_ID_ZT_ONE,lotteryId).equals(playId) || betNumber.indexOf("正码一") >= 0) {
                index = 0;
            } else if (this.generationLHCPlayId(PLAY_ID_ZT_TWO,lotteryId).equals(playId) || betNumber.indexOf("正码二") >= 0) {
                index = 1;
            } else if (this.generationLHCPlayId(PLAY_ID_ZT_THREE,lotteryId).equals(playId) || betNumber.indexOf("正码三") >= 0) {
                index = 2;
            } else if (this.generationLHCPlayId(PLAY_ID_ZT_FOUR,lotteryId).equals(playId) || betNumber.indexOf("正码四") >= 0) {
                index = 3;
            } else if (this.generationLHCPlayId(PLAY_ID_ZT_FIVE,lotteryId).equals(playId) || betNumber.indexOf("正码五") >= 0) {
                index = 4;
            } else if (this.generationLHCPlayId(PLAY_ID_ZT_SIX,lotteryId).equals(playId) || betNumber.indexOf("正码六") >= 0) {
                index = 5;
            }
            List<String> numLiangMianList = LhcPlayRule.getNumLiangMianList(Integer.valueOf(sgArr[index]));
            for (String betNum : betNumArr) {
                if (numLiangMianList.contains(betNum)) {
                    winStr.append(betNum).append(",");
                }
            }
        } else if (this.generationLHCPlayId(PLAY_ID_ZM_ZMA,lotteryId).equals(playId)) {
            // 正码正码A两面
            List<String> totalLiangMian = LhcUtils.getTotalLiangMian(sgNumber);
            for (String betNum : betNumArr) {
                if (totalLiangMian.contains(betNum)) {
                    winStr.append(betNum).append(",");
                }
            }
        } else if (this.generationLHCPlayId(PLAY_ID_TM_TMA_LM,lotteryId).equals(playId)) {
            // 特码特码A两面
            List<String> temaLiangMian = LhcUtils.getTemaLiangMian(Integer.valueOf(sgArr[6]), dateStr);
            for (String betNum : betNumArr) {
                if (temaLiangMian.contains(betNum)) {
                    winStr.append(betNum).append(",");
                }
            }
        }
        if (winStr.length() > 0) {
            return winStr.substring(0, winStr.length() - 1);
        }
        return null;
    }

    /**
     * 获取六合彩中奖注数
     * (适用玩法:连码三中二,连码二中特)
     *
     * @param betNumber 投注号码
     * @param sgNumber  开奖号码
     * @param playId    玩法id
     * @return
     */
    private List<Integer> isWinLianMaEz(String betNumber, String sgNumber, Integer playId,Integer lotteryId) {
        String[] betNumArr = betNumber.split("@")[1].split(",");
        String[] sgArr = sgNumber.split(",");
        List<Integer> sgList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            sgList.add(Integer.valueOf(sgArr[i]));
        }
        int tema = Integer.valueOf(sgArr[6]);
        boolean tag = false;
        List<Integer> openNum = new ArrayList<>();
        for (String betNum : betNumArr) {
            if (sgList.contains(Integer.valueOf(betNum))) {
                openNum.add(Integer.valueOf(betNum));
            }
            if (tema == Integer.valueOf(betNum)) {
                tag = true;
            }
        }

        List<Integer> wins = new ArrayList<>(2); //默认第一个值是高赔率的中奖注数
        int size = openNum.size();
        int win1 = 0; // 高赔率的中奖注数
        int win2 = 0; // 低赔率的中奖注数
        if (this.generationLHCPlayId(PLAY_ID_LM_EZT,lotteryId).equals(playId)) {
            // 连码二中特
            if (tag) {
                win1 = size;
            }
            win2 = CountUtils.countCnm(size, 2);
        } else if (this.generationLHCPlayId(PLAY_ID_LM_SZE,lotteryId).equals(playId)) {
            // 连码三中二
            win1 = CountUtils.countCnm(size, 3);
            win2 = CountUtils.countCnm(size, 2) * (betNumArr.length - size);
        }
        wins.add(win1);
        wins.add(win2);
        return wins;
    }


    /**
     * 获取六合彩中奖注数
     * (适用玩法:正码正码A)
     *
     * @param betNumber 投注号码
     * @param sgNumber  开奖号码
     * @return
     */
    private int isWinByNum(String betNumber, String sgNumber) {
        String[] betNumArr = betNumber.split("@")[1].split(",");
        String[] sgArr = sgNumber.split(",");
        int winCount = 0;

        // 正码正码A
        List<Integer> sgList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            sgList.add(Integer.valueOf(sgArr[i]));
        }
        for (String betNum : betNumArr) {
            if(betNumber.contains("两面")){
                List<String> openlist = LhcUtils.getTotalLiangMian(sgNumber.substring(0, sgNumber.lastIndexOf(",")));
                for (String open:openlist){
                    if(betNum.contains(open)){
                        ++winCount;
                    }
                }
            }else {
                if (sgList.contains(Integer.valueOf(betNum))) {
                    ++winCount;
                }
            }
        }

        return winCount;
    }


    /**
     * 获取六合彩中奖注数
     * (适用玩法:正特正(1-6)特, 特码特码A)
     *
     * @param betNumber 投注号码
     * @param sgNumber  开奖号码
     * @param playId    玩法id
     * @return
     */
    private int isWinZhengTeOneToSix(String betNumber, String sgNumber, Integer playId,Integer lotteryId) {
        String[] sgArr = sgNumber.split(",");
        int index = 6;
        if (this.generationLHCPlayId(PLAY_ID_ZT_ONE,lotteryId).equals(playId)) {
            index = 0;
        } else if (this.generationLHCPlayId(PLAY_ID_ZT_TWO,lotteryId).equals(playId)) {
            index = 1;
        } else if (this.generationLHCPlayId(PLAY_ID_ZT_THREE,lotteryId).equals(playId)) {
            index = 2;
        } else if (this.generationLHCPlayId(PLAY_ID_ZT_FOUR,lotteryId).equals(playId)) {
            index = 3;
        } else if (this.generationLHCPlayId(PLAY_ID_ZT_FIVE,lotteryId).equals(playId)) {
            index = 4;
        } else if (this.generationLHCPlayId(PLAY_ID_ZT_SIX,lotteryId).equals(playId)) {
            index = 5;
        } else if (this.generationLHCPlayId(PLAY_ID_TM_TMA,lotteryId).equals(playId)) {
            index = 6;
        }
        int winCount = 0;
        if(betNumber.contains("两面")){
            int number = Integer.valueOf(sgArr[index]);
            String open=LhcUtils.numDetalis(number+"");
            String betNum = String.valueOf(betNumber.split("@")[1]);

            if (open.contains(betNum) ) {
                winCount = 1;
            }
        }else{
            int number = Integer.valueOf(sgArr[index]);
            String betNum = betNumber.split("@")[1];
            String[] betarr=betNum.split(",");
            for (String betn:betarr) {
                if (number == Integer.valueOf( betn)) {
                    winCount = 1;
                    return winCount;
                }
            }
        }



        return winCount;
    }

    /**
     * 获取六合彩中奖注数
     * (适用玩法:连码三全中,二全中,特串)
     *
     * @param betNumber 投注号码
     * @param sgNumber  开奖号码
     * @param playId    玩法id
     * @return
     */
    private int isWinLianMaQz(String betNumber, String sgNumber, Integer playId,Integer lotteryId) {
        String[] betNumArr = betNumber.split("@")[1].split(",");
        String[] sgArr = sgNumber.split(",");
        List<Integer> sgList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            sgList.add(Integer.valueOf(sgArr[i]));
        }
        int tema = Integer.valueOf(sgArr[6]);
        boolean tag = false;
        List<Integer> openNum = new ArrayList<>();
        for (String betNum : betNumArr) {
            if (sgList.contains(Integer.valueOf(betNum))) {
                openNum.add(Integer.valueOf(betNum));
            }
            if (tema == Integer.valueOf(betNum)) {
                tag = true;
            }
        }
        if (this.generationLHCPlayId(PLAY_ID_LM_TC,lotteryId).equals(playId) && tag) {
            return openNum.size();
        } else if (this.generationLHCPlayId(PLAY_ID_LM_EQZ,lotteryId).equals(playId)) {
            return CountUtils.countCnm(openNum.size(), 2);
        } else if (this.generationLHCPlayId(PLAY_ID_LM_SQZ,lotteryId).equals(playId)) {
            return CountUtils.countCnm(openNum.size(), 3);
        }
        return 0;
    }

    /**
     * 获取六合彩中奖注数
     * (适用玩法:不中)
     *
     * @param betNumber 投注号码
     * @param sgNumber  开奖号码
     * @param playId    玩法id
     * @return
     */
    private int isWinByNoOpen(String betNumber, String sgNumber, Integer playId,Integer lotteryId) {
        String[] betNumArr = betNumber.split("@")[1].split(",");
        String[] sgArr = sgNumber.split(",");
        List<Integer> sgList = new ArrayList<>();
        for (String sg : sgArr) {
            sgList.add(Integer.valueOf(sg));
        }
        List<String> noOpenNum = new ArrayList<>();
        for (String betNum : betNumArr) {
            if (!sgList.contains(Integer.valueOf(betNum))) {
                noOpenNum.add(betNum);
            }
        }
        int m = 50; //初始化m
        if (this.generationLHCPlayId(PLAY_ID_NO_OPEN_FIVE,lotteryId).equals(playId)) {
            m = 5;
        } else if (this.generationLHCPlayId(PLAY_ID_NO_OPEN_SIX,lotteryId).equals(playId)) {
            m = 6;
        } else if (this.generationLHCPlayId(PLAY_ID_NO_OPEN_SEVEN,lotteryId).equals(playId)) {
            m = 7;
        } else if (this.generationLHCPlayId(PLAY_ID_NO_OPEN_EIGHT,lotteryId).equals(playId)) {
            m = 8;
        } else if (this.generationLHCPlayId(PLAY_ID_NO_OPEN_NINE,lotteryId).equals(playId)) {
            m = 9;
        } else if (this.generationLHCPlayId(PLAY_ID_NO_OPEN_TEN,lotteryId).equals(playId)) {
            m = 10;
        }
        return CountUtils.countCnmIsLong(noOpenNum.size(), m);
    }

    /**
     * 获取六合彩中奖注数
     * (适用玩法:六肖)
     *
     * @param betNumber 投注号码
     * @param sgNumber  开奖号码
     * @param playId    玩法id
     * @param dateStr   开奖时间 yyyy-MM-dd
     * @return
     */
    private int isWinByLiuXiao(String betNumber, String sgNumber, Integer playId, String dateStr,Integer lotteryId) {



            String teXiao = LhcUtils.getShengXiao(Integer.valueOf(sgNumber.split(",")[6]), dateStr);
            String[] betNumArr = betNumber.split("@")[1].split(",");
            List<String> noOpenSx = new ArrayList<>();
            for (String betNum : betNumArr) {
                if (!teXiao.equals(betNum)) {
                    noOpenSx.add(betNum);
                }
            }
            if (betNumArr.length != noOpenSx.size() && this.generationLHCPlayId(PLAY_ID_LX_LXLZ,lotteryId).equals(playId)) {
                // return CountUtils.countCnm(betNumArr.length, noOpenSx.size() + 1);
                return CountUtils.countCnm(betNumArr.length, 6) - CountUtils.countCnm(betNumArr.length -1 , 6);
            } else if (this.generationLHCPlayId(PLAY_ID_LX_LXLBZ,lotteryId).equals(playId)) {
                return CountUtils.countCnm(noOpenSx.size(), 6);
            }
            return 0;

    }

    /**
     * 获取六合彩中奖注数
     * (适用玩法:连肖中)
     *
     * @param betNumber 投注号码
     * @param sgNumber  开奖号码
     * @param playId    玩法id
     * @param dateStr   开奖时间 yyyy-MM-dd
     * @return
     */
    private List<Integer> isWinByLxWin(String betNumber, String sgNumber, Integer playId, String dateStr,Integer lotteryId) {
        // 当期开出的生肖
        List<String> numberShengXiao = LhcUtils.getNumberShengXiao(sgNumber, dateStr);
        // 当期的低赔率的生肖
        String shengXiao = LhcUtils.getShengXiao(dateStr);
        String[] betNumArr = betNumber.split("@")[1].split(",");

        List<String> openSx = new ArrayList<>();
        for (String betNum : betNumArr) {
            if (numberShengXiao.contains(betNum)) {
                openSx.add(betNum);
            }
        }

        List<Integer> wins = new ArrayList<>(2); //默认第一个值是高赔率的中奖注数
        int size = openSx.size();
        int win1 = 0; // 高赔率的中奖注数
        int win2 = 0; // 低赔率的中奖注数

        int playWins = 2;
        if (this.generationLHCPlayId(PLAY_ID_LX_TWO_WIN,lotteryId).equals(playId)) {
            playWins = 2;
        } else if (this.generationLHCPlayId(PLAY_ID_LX_THREE_WIN,lotteryId).equals(playId)) {
            playWins = 3;
        } else if (this.generationLHCPlayId(PLAY_ID_LX_FOUR_WIN,lotteryId).equals(playId)) {
            playWins = 4;
        }

        if (size >= playWins) {
            // 中奖了
            if (openSx.contains(shengXiao)) {
                // 中了低赔率的
                win1 = CountUtils.countCnm(size - 1, playWins);
                win2 = CountUtils.countCnm(size - 1, playWins - 1);
            } else {
                win1 = CountUtils.countCnm(size, playWins);
            }
        }

        wins.add(win1);
        wins.add(win2);

        return wins;
    }

    /**
     * 获取六合彩中奖注数
     * (适用玩法:连肖不中)
     *
     * @param betNumber 投注号码
     * @param sgNumber  开奖号码
     * @param playId    玩法id
     * @param dateStr   开奖时间 yyyy-MM-dd
     * @return
     */
    private int isWinByLxNoWin(String betNumber, String sgNumber, Integer playId, String dateStr,Integer lotteryId) {
        List<String> numberShengXiao = LhcUtils.getNumberShengXiao(sgNumber, dateStr);
        String[] betNumArr = betNumber.split("@")[1].split(",");
        List<String> noOpenSx = new ArrayList<>();
        for (String betNum : betNumArr) {
            if (!numberShengXiao.contains(betNum)) {
                noOpenSx.add(betNum);
            }
        }
        if (this.generationLHCPlayId(PLAY_ID_LX_TWO_NO_WIN,lotteryId).equals(playId)) {
            return CountUtils.countCnm(noOpenSx.size(), 2);
        } else if (this.generationLHCPlayId(PLAY_ID_LX_THREE_NO_WIN,lotteryId).equals(playId)) {
            return CountUtils.countCnm(noOpenSx.size(), 3);
        } else if (this.generationLHCPlayId(PLAY_ID_LX_FOUR_NO_WIN,lotteryId).equals(playId)) {
            return CountUtils.countCnm(noOpenSx.size(), 4);
        }
        return 0;
    }

    /**
     * 获取六合彩中奖注数
     * (适用玩法:连尾中)
     *
     * @param betNumber 投注号码
     * @param sgNumber  开奖号码
     * @param playId    玩法id
     * @return
     */
    private int isWinByLwWin(String betNumber, String sgNumber, Integer playId,Integer lotteryId) {
        String[] betNumArr = betNumber.split("@")[1].split(",");
        String[] sgArr = sgNumber.split(",");
        List<Integer> sgList = new ArrayList<>();
        for (String sg : sgArr) {
            sgList.add(Integer.valueOf(sg) % 10);
        }
        List<String> openNum = new ArrayList<>();
        for (String betNum : betNumArr) {
            if (sgList.contains(Integer.valueOf(betNum.replace("尾", "")))) {
                openNum.add(betNum);
            }
        }
        if (this.generationLHCPlayId(PLAY_ID_LW_TWO_WIN,lotteryId).equals(playId)) {
            return CountUtils.countCnm(openNum.size(), 2);
        } else if (this.generationLHCPlayId(PLAY_ID_LW_THREE_WIN,lotteryId).equals(playId)) {
            return CountUtils.countCnm(openNum.size(), 3);
        } else if (this.generationLHCPlayId(PLAY_ID_LW_FOUR_WIN,lotteryId).equals(playId)) {
            return CountUtils.countCnm(openNum.size(), 4);
        }
        return 0;
    }

    /**
     * 获取六合彩中奖注数
     * (适用玩法:连尾不中)
     *
     * @param betNumber 投注号码
     * @param sgNumber  开奖号码
     * @param playId    玩法id
     * @return
     */
    private List<Integer> isWinByLwNoWin(String betNumber, String sgNumber, Integer playId,Integer lotteryId) {
        String[] betNumArr = betNumber.split("@")[1].split(",");
        String[] sgArr = sgNumber.split(",");
        List<Integer> sgList = new ArrayList<>();
        for (String sg : sgArr) {
            sgList.add(Integer.valueOf(sg) % 10);
        }
        List<String> noOpenNum = new ArrayList<>();
        for (String betNum : betNumArr) {
            if (!sgList.contains(Integer.valueOf(betNum.replace("尾", "")))) {
                noOpenNum.add(betNum);
            }
        }

        List<Integer> wins = new ArrayList<>(2); //默认第一个值是高赔率的中奖注数
        int size = noOpenNum.size();
        int win1 = 0; // 高赔率的中奖注数
        int win2 = 0; // 低赔率的中奖注数

        int playWins = 2;
        if (this.generationLHCPlayId(PLAY_ID_LW_TWO_NO_WIN,lotteryId).equals(playId)) {
            playWins = 2;
        } else if (this.generationLHCPlayId(PLAY_ID_LW_THREE_NO_WIN,lotteryId).equals(playId)) {
            playWins = 3;
        } else if (this.generationLHCPlayId(PLAY_ID_LW_FOUR_NO_WIN,lotteryId).equals(playId)) {
            playWins = 4;
        }

        if (size >= playWins) {
            // 中奖了
            if (noOpenNum.contains(0)) {
                // 中了高赔率的
                win1 = CountUtils.countCnm(size - 1, playWins);
                // 中了低赔率的
                win2 = CountUtils.countCnm(size - 1, playWins - 1);
            } else {
                // 中了高赔率的
                win1 = CountUtils.countCnm(size, playWins);
            }
        }

        wins.add(win1);
        wins.add(win2);

        return wins;

    }

    /**
     * 获取六合彩中奖注数
     * (适用玩法:1-6龙虎)
     *
     * @param betNumber 投注号码
     * @param sgNumber  开奖号码
     * @return
     */
    private int isWinByOneSixLh(String betNumber, String sgNumber) {
        // 校验参数
        if (StringUtils.isBlank(betNumber) || StringUtils.isBlank(sgNumber)) {
            return 0;
        }
        // 分析投注内容
        String[] bet = betNumber.split("@");
        String numStr = bet[1].substring(1, 4);
        String betType = bet[1].substring(0,1);
        String[] num = numStr.split("-");
        String[] sgNum = sgNumber.split(",");
        int one = Integer.valueOf(num[0]);
        int two = Integer.valueOf(num[1]);
        String str;
        if (sgNum[one - 1].compareTo(sgNum[two - 1]) > 0) {
            str = "龙";
        } else {
            str = "虎";
        }
        return str.equals(betType) ? 1 : 0;

//        String[] betNumArr = betNumber.split(";");
//        String longStr = betNumArr[0].length() > 2 ? betNumArr[0].substring(2) : "";
//        String huStr = betNumArr[1].length() > 2 ? betNumArr[1].substring(2) : "";
//        String[] sgArr = sgNumber.split(",");
//        List<Integer> sgList = new ArrayList<>();
//        for (String sg : sgArr) {
//            sgList.add(Integer.valueOf(sg));
//        }
//        int count = 0;
//        if (StringUtils.isNotBlank(longStr)) {
//            String[] longArr = longStr.split(",");
//            for (String betStr : longArr) {
//                int one = Integer.valueOf(betStr.charAt(0) + "");
//                int two = Integer.valueOf(betStr.charAt(2) + "");
//                if (sgList.get(one - 1) > sgList.get(two - 1)) {
//                    ++count;
//                }
//            }
//        }
//
//        if (StringUtils.isNotBlank(huStr)) {
//            String[] huArr = huStr.split(",");
//            for (String betStr : huArr) {
//                int one = Integer.valueOf(betStr.charAt(0) + "");
//                int two = Integer.valueOf(betStr.charAt(2) + "");
//                if (sgList.get(one - 1) < sgList.get(two - 1)) {
//                    ++count;
//                }
//            }
//        }
//
//        return count;
    }

    /**
     * 获取六合彩中奖注数
     * (适用玩法:五行)
     *
     * @param betNumber 投注号码
     * @param sgNumber  开奖号码
     * @param dateStr   开奖时间 yyyy-MM-dd
     * @return
     */
    private int isWinByWx(String betNumber, String sgNumber, String dateStr) {
        String wuXing = LhcUtils.getNumWuXing(Integer.valueOf(sgNumber.split(",")[6]), dateStr);
        String[] betNumArr = betNumber.split("@")[1].split(",");
        for (String betNum : betNumArr) {
            if (wuXing.equals(betNum)) {
                return 1;
            }
        }
        return 0;
    }

    /**
     *  1分、5分、时时、香港六合彩 玩法ID生成
     * @param playNumber
     * @param
     * @return
     */
    private Integer generationLHCPlayId(String playNumber, Integer lotteryId) {
    	return Integer.parseInt(lotteryId+playNumber);
    }
    
    /**
     *  1分、5分、时时、香港六合彩 玩法ID生成
     * @param
     * @param
     * @return
     */
    private List<Integer> generationLHCPlayIdList(List<String> playNumbers, Integer lotteryId) {
    	List<Integer> replaceToNewPlayId = new ArrayList<Integer>();
        for (String number : playNumbers) {
			replaceToNewPlayId.add(Integer.parseInt(lotteryId+number));
		}
    	return replaceToNewPlayId;
    }
    
    /**
     * 根据期号获取赛果
     *
     * @param issue 期号
     * @return
     */
//    private LhcLotterySg getLotterySg(String issue) {
//        // 获取该期赛果信息
//        LhcLotterySg sg = null;
//        if (redisTemplate.hasKey(LHC_LOTTERY_SG + issue)) {
//            sg = (LhcLotterySg) redisTemplate.opsForValue().get(LHC_LOTTERY_SG + issue);
//        }
//        if (sg == null) {
//            LhcLotterySgExample sgExample = new LhcLotterySgExample();
//            LhcLotterySgExample.Criteria sgCriteria = sgExample.createCriteria();
//            sgCriteria.andYearEqualTo(issue.substring(0, 4));
//            sgCriteria.andIssueEqualTo(issue.substring(4));
//            sg = lhcLotterySgMapper.selectOneByExample(sgExample);
//            redisTemplate.opsForValue().set(LHC_LOTTERY_SG + issue, sg, 5, TimeUnit.MINUTES);
//        }
//        return sg;
//    }
}
