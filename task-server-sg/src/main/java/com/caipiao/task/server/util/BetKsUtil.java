package com.caipiao.task.server.util;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ShaoMing
 * @version 1.0.0
 * @date 2018/12/6 11:08
 */
public class BetKsUtil {
    private static final String PLAY_ID_LM = "01";
    private static final String PLAY_ID_DD = "02";
    private static final String PLAY_ID_LH = "03";
    private static final String PLAY_ID_EBT = "04";
    private static final String PLAY_ID_EBT_DT = "05";
    private static final String PLAY_ID_ET_DX = "06";
    private static final String PLAY_ID_ET_FX = "07";
    private static final String PLAY_ID_SBT = "08";
    private static final String PLAY_ID_SBT_DT = "09";
    private static final String PLAY_ID_ST_DX = "10";
    private static final String PLAY_ID_ST_TX = "11";
    private final List<String> PLAY_IDS_SBT_ST = Lists.newArrayList(PLAY_ID_SBT, PLAY_ID_SBT_DT, PLAY_ID_ST_DX, PLAY_ID_ST_TX);
    private final List<String> PLAY_IDS_EBT_ET = Lists.newArrayList(PLAY_ID_EBT, PLAY_ID_EBT_DT, PLAY_ID_ET_DX, PLAY_ID_ET_FX);

    /*
     *@Title:(适用玩法:两面)
     *@Description:判断是否中奖,中奖返回中奖信息,不中则返回null
     * @Param  betnum 用户下注号码，sg，开奖号码 playname 玩法名称
     */
    public static String judgeWinLm(String betNum, String sg, Integer playId, int lotteryId) {
        String[] betNumArr = betNum.split(",");//3,4 ,大，小
        String[] sgNumArr = sg.split(",");
        Integer num1 = Integer.valueOf(sgNumArr[0]);
        Integer num2 = Integer.valueOf(sgNumArr[1]);
        Integer num3 = Integer.valueOf(sgNumArr[2]);
        int he = num1 + num2 + num3;
        String heString = he + "";
        if (heString.isEmpty()) {
            heString = "0";
        }
        StringBuilder winStr = new StringBuilder();
        if (playId.equals(generationKsPlayId(PLAY_ID_LM, lotteryId))) {
            for (String betStr : betNumArr) {
                switch (betStr) {
                    case "大":
                        if (he > 10) { //需根据玩法规则改动
                            winStr.append(betStr).append("_");
                        }
                        break;
                    case "小":
                        if (he <= 10) {
                            winStr.append(betStr).append("_");
                        }
                        break;
                    case "单":
                        if (he % 2 == 1) {
                            winStr.append(betStr).append("_");
                        }
                        break;
                    case "双":
                        if (he % 2 == 0) {
                            winStr.append(betStr).append("_");
                        }
                        break;
                    default:
                        if (heString.equals(betStr)) {
                            winStr.append(he).append("_");
                        }
                        break;
                }


            }
        }
        if (winStr.length() > 0) {
//            winStr = new StringBuilder(winStr.toString().replaceAll("和", ""));
            return winStr.substring(0, winStr.length() - 1);
        }
        return null;
    }

    /*
     *@Title:(适用玩法:独胆)
     *@Description:判断是否中奖,中奖返回中奖信息,不中则返回null
     * @Param  betnum 用户下注号码，sg，开奖号码 playname 玩法名称 eg: 3,4,5
     */
    public static String judgeWinDd(String betNum, String sg, Integer playId, int lotteryId) {
        String[] sgNumArr = sg.split(",");
        StringBuilder winStr = new StringBuilder();
        if (playId.equals(generationKsPlayId(PLAY_ID_DD, lotteryId))) {
            String[] betDdNumber = betNum.split(",");
            for (String number : betDdNumber) {
                if (sgNumArr[0].equals(number) || sgNumArr[1].equals(number) || sgNumArr[2].equals(number)) {
                    winStr.append(number + "_");
                }
            }
        }
        if (winStr.length() > 0) {
            return winStr.substring(0, winStr.length() - 1);
        }
        return null;
    }


    /*
     *@Title:(适用玩法:二连号三连号)二连号  12,23,14,15   三连号 123,234,345
     *@Description:判断是否中奖,中奖返回中奖信息,不中则返回null      sg：3,4,5
     * @Param  betnum 用户下注号码，sg，开奖号码 playname 玩法名称
     */
    public static String judgeWinLh(String betNumber, String sg, Integer playId, Integer lotteryId) {
        StringBuilder winStr = new StringBuilder();
        String[] sgArr = sg.split(",");
        int[] intSgArr = Stream.of(sgArr).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(intSgArr);
        String sgStr = intSgArr[0] + "," + intSgArr[1] + "," + intSgArr[2];
        int length = betNumber.split(",")[0].length();
        if (playId.equals(generationKsPlayId(PLAY_ID_LH, lotteryId)) && length == 3) {
            if ("1,2,3".equals(sgStr) || "2,3,4".equals(sgStr) || "3,4,5".equals(sgStr) || "4,5,6".equals(sgStr)) {
                winStr.append("123,234,345,456" + "_");
            }
        } else if (playId.equals(generationKsPlayId(PLAY_ID_LH, lotteryId)) && length == 2) {
            String[] betElhNumber = betNumber.split(",");
            for (String bet : betElhNumber) {
                String firstNumber = bet.charAt(0) + "";
                String secondNumber = bet.charAt(1) + "";
                if (sg.contains(firstNumber) && sg.contains(secondNumber)) {
                    winStr.append(bet + "_");
                }
            }
        }
        if (winStr.length() > 0) {
            return winStr.substring(0, winStr.length() - 1);
        }
        return null;
    }


    /*
     *@Title:(适用玩法:三不同号，胆拖，三同号单选 三同号通选)
     *@Description:判断是否中奖,中奖返回中奖信息,不中则返回null
     * @Param  betnum 用户下注号码，sg，开奖号码 playname 玩法名称
     */
    public static String judgeWinSbThAndSthPlay(String betNumber, String sg, Integer playId, Integer lotteryId) {
        StringBuilder winStr = new StringBuilder();// betnumber 1,2,3，5,6,9,10   sg 4，5，6
        String[] sgNum = sg.split(",");
        if (playId.equals(generationKsPlayId(PLAY_ID_SBT, lotteryId)) && !sgNum[0].equals(sgNum[1]) && !sgNum[1].equals(sgNum[2]) && !sgNum[0].equals(sgNum[2])) {// betnumber 1,2,3，5,6,9,10   sg 4，5，6
            //判断所选择的号码当中是否包含赛果的三个数据    无论选多少个号码  只有一注会中  就是 所选号码中包含sg  sg必须是三个不同的号码
            String[] betSbtNumberSplit = betNumber.split(",");
            List<String> betSbtNumberList = Arrays.asList(betSbtNumberSplit);
            if (betSbtNumberList.contains(sgNum[0]) && betSbtNumberList.contains(sgNum[1]) && betSbtNumberList.contains(sgNum[2])) {
                winStr.append(sg + "_");
            }
        } else if (playId.equals(generationKsPlayId(PLAY_ID_SBT_DT, lotteryId)) && !sgNum[0].equals(sgNum[1]) && !sgNum[1].equals(sgNum[2]) && !sgNum[0].equals(sgNum[2])) {
            //判断所选择的号码当中是否包含赛果的三个数据    无论选多少个号码  只有一注会中  就是 所选号码中包含sg  sg必须是三个不同的号码
            //两种情况两个胆码和一个胆码    betnumber格式 1，2_ 3 ,4 ,5      1_3,4,5    1 4  5
            String[] betSbtDtNumber = betNumber.split("_");
            String betDm = betSbtDtNumber[0];
            String[] betDmsplit = betDm.split(",");
            String betTm = betSbtDtNumber[1];
            String[] betTmsplit = betTm.split(",");
            List<String> sgNumList = Stream.of(sgNum).collect(Collectors.toList());
            List<String> betTmsplitList = Arrays.asList(betTmsplit);
            //一个胆码情况查下  判断是否 sg中存在胆码 ，然后去掉该胆码  判断拖码中是否包含剩下的两个sg 数字
            if (betDmsplit.length == 1) {
                if (sgNumList.contains(betDmsplit[0])) {
                    sgNumList.remove(betDmsplit[0]);
                    if (betTmsplitList.contains(sgNumList.get(0)) && betTmsplitList.contains(sgNumList.get(1))) {
                        winStr.append(sg + "_");
                    }
                }
            }
            //两个胆码情况查下  判断是否 sg中存在这两个胆码 ，然后去掉这两个胆码  判断拖码中是否包含剩下的1个sg 数字
            else if (betDmsplit.length == 2) {
                if (sgNumList.contains(betDmsplit[0]) && sgNumList.contains(betDmsplit[1])) {
                    sgNumList.remove(betDmsplit[0]);
                    sgNumList.remove(betDmsplit[1]);
                    if (betTmsplitList.contains(sgNumList.get(0))) {
                        winStr.append(sg + "_");
                    }
                }
            }
        } else if (playId.equals(generationKsPlayId(PLAY_ID_ST_DX, lotteryId)) && sgNum[0].equals(sgNum[1]) && sgNum[1].equals(sgNum[2])) {
            //三同号单选  例如 1 ，2 ， 3   sg 3,3,3        只要  只有sg都相同才能进入   循环遍历  1 . 2  . 3 看是否有相同
            String[] betSthsingleNumberSplit = betNumber.split(",");
            for (String number : betSthsingleNumberSplit) {
                if (number.equals(sgNum[0])) {
                    winStr.append(number + "_");
                }
            }

        } else if (playId.equals(generationKsPlayId(PLAY_ID_ST_TX, lotteryId))) {
            if ("1,1,1".equals(sg) || "2,2,2".equals(sg) || "3,3,3".equals(sg) || "4,4,4".equals(sg) || "5,5,5".equals(sg) || "6,6,6".equals(sg)) {
                winStr.append(betNumber + "_");
            }
        }

        if (winStr.length() > 0) {
            return winStr.substring(0, winStr.length() - 1);
        }
        return null;
    }

    /*
     *@Title:(适用玩法:二不同号，胆拖，二同号单选 二同号复选 )
     *@Description:判断是否中奖,中奖返回中奖信息,不中则返回null
     * @Param    每个订单的  betNumber 用户下注号码，sg，开奖号码 playname 玩法名称
     */
    public static String judgeWinEbThAndEthPlay(String betNumber, String sg, Integer playId, Integer lotteryId) {
        // sg 1,2,3
        StringBuilder winStr = new StringBuilder();
        String betNumberreplace = betNumber.replaceAll("_", ",");
        String[] betNum = betNumberreplace.split(",");
        String[] sgNum = sg.split(",");
        //转为int数组 进行排序
        int[] betNumInt = Stream.of(betNum).mapToInt(Integer::parseInt).toArray();
        int[] sgNumInt = Stream.of(sgNum).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(betNumInt);
        Arrays.sort(sgNumInt);//从小到大排序
        List<String> betList = new ArrayList<String>();
        List<String> sgList = new ArrayList<String>();

        // 赛果三个数里有两个就赢   eg:  betnumber = 1,2,3,4  sg = 1,3,4// 1,2,  2,3     赛果三个数里有两个就赢
        if (playId.equals(generationKsPlayId(PLAY_ID_EBT, lotteryId))) {
            String[] betNumberArr = betNumber.split(",");
            //对 二不同betnumber 进行两两排列，列出所有下注 放入集合 ，
            for (int i = 0; i < betNumberArr.length - 1; i++) {
                for (int j = i + 1; j < betNumberArr.length; j++) {
                    betList.add(betNumberArr[i] + "," + betNumberArr[j]);
                }
            }
            //遍历集合，判断两个数字是否都在 sg中 是则该注赢了
            for (String num : betList) {
                String[] numSplit = num.split(",");
                if (sg.contains(numSplit[0]) && sg.contains(numSplit[1])) {
                    winStr.append(num + "_");
                }
            }

        } else if (playId.equals(generationKsPlayId(PLAY_ID_EBT_DT, lotteryId))) {
            String[] split = betNumber.split("_");
            String betDm = split[0];
            String betTmSplit = split[1];
            String[] betTm = betTmSplit.split(",");
            for (String tmNumber : betTm) {
                if (betDm.equals(tmNumber)) {
                    continue;
                }
                if (sg.contains(tmNumber) && sg.contains(betDm)) {
                    String betWin = betDm + tmNumber;
                    winStr.append(betWin + "_");
                }
            }
        } else if (playId.equals(generationKsPlayId(PLAY_ID_ET_DX, lotteryId))) {
            String[] splitbetNumber = betNumber.split("_");
            String sameBet = splitbetNumber[0];
            String differentBet = splitbetNumber[1];
            String[] sameBetArr = sameBet.split(",");
            String[] differentBetArr = differentBet.split(",");
            ArrayList<String> List = new ArrayList<>();
            for (String sameNumber : sameBetArr) {
                for (String differentNumber : differentBetArr) {
                    if (sameNumber.equals(differentNumber)) {
                        continue;
                    }
                    String number = sameNumber + "," + sameNumber + "," + differentNumber;
                    String[] numbersplit = number.split(",");
                    int[] intBetnumbers = Stream.of(numbersplit).mapToInt(Integer::parseInt).toArray();//转为int数组 进行排序
                    Arrays.sort(intBetnumbers);
                    if (Arrays.equals(intBetnumbers, sgNumInt)) {
                        winStr.append(number + "_");
                    }
                }
            }
        } //二同号复选     betnumber 1,2，3  只要sg里有两个数是 betnumber 则赢
        else if (playId.equals(generationKsPlayId(PLAY_ID_ET_FX, lotteryId))) {
            String[] betNumberSplit = betNumber.split(",");
            Boolean flag;
            for (String number : betNumberSplit) {
                int sameNumber = Integer.parseInt(number);
                flag = false;
                if (sgNumInt[0] == sameNumber && sgNumInt[1] == sameNumber) {
                    flag = true;
                } else if (sgNumInt[0] == sameNumber && sgNumInt[2] == sameNumber) {
                    flag = true;
                } else if (sgNumInt[1] == sameNumber && sgNumInt[2] == sameNumber) {
                    flag = true;
                }
                if (flag) {
                    winStr.append(number + "_");
                }
            }
        }
        if (winStr.length() > 0) {
            return winStr.substring(0, winStr.length() - 1);
        }
        return null;
    }

    /**
     * 快三玩法ID生成
     *
     * @param playNumber
     * @param
     * @return
     */
    private static Integer generationKsPlayId(String playNumber, Integer lotteryId) {
        return Integer.parseInt(lotteryId + playNumber);
    }
}
