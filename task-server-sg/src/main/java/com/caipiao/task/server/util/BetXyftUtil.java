package com.caipiao.task.server.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ShaoMing
 * @version 1.0.0
 * @date 2018/12/6 11:08
 */
public class BetXyftUtil {

    // PK10两面
    private static final String PLAY_ID_LM = "01";
    // PK10冠亚和
    private static final String PLAY_ID_GYH = "02";

    // PK10 1-5名
    private static final String PLAY_ID_15 = "03";
    // PK10 6-10名
    private static final String PLAY_ID_610 = "04";
    // PK10 第一名
    private static final String PLAY_ID_1 = "05";
    // PK10 第二名
    private static final String PLAY_ID_2 = "06";
    // PK10 第三名
    private static final String PLAY_ID_3 = "07";
    // PK10 第四名
    private static final String PLAY_ID_4 = "08";
    // PK10 第五名
    private static final String PLAY_ID_5 = "09";
    // PK10 第六名
    private static final String PLAY_ID_6 = "10";
    // PK10 第七名
    private static final String PLAY_ID_7 = "11";
    // PK10 第八名
    private static final String PLAY_ID_8 = "12";
    // PK10 第九名
    private static final String PLAY_ID_9 = "13";
    // PK10 第十名
    private static final String PLAY_ID_10 = "14";

    /**
     * 判断是否中奖,中奖返回中奖信息,不中则返回null
     * (适用玩法:两面, 冠亚和)
     *
     * @param betNum 投注号码
     * @param sg     开奖号码
     * @param playId 玩法id
     * @return
     */
    public static String isWinLmAndGyh(String betNum, String sg, Integer playId) {
        String[] betNumArr = betNum.split(",");
        String[] sgNumArr = sg.split(",");
        Integer num1 = Integer.valueOf(sgNumArr[0]);
        Integer num2 = Integer.valueOf(sgNumArr[1]);
        int he = num1 + num2;
        StringBuilder winStr = new StringBuilder();
        String betName = null;
        if (String.format("%02d", playId % 100).equals(PLAY_ID_LM)) {
            for (String betStr : betNumArr) {
                if (betStr.contains("@"))
                    betName = betStr.split("@")[0];
                betStr = betStr.split("@")[1];
                if ("冠亚大".equals(betStr) && he > 11) {
                    winStr.append(betStr).append(",");
                } else if ("冠亚小".equals(betStr) && he <= 11) {
                    winStr.append(betStr).append(",");
                } else if ("冠亚单".equals(betStr) && he % 2 == 1) {
                    winStr.append(betStr).append(",");
                } else if ("冠亚双".equals(betStr) && he % 2 == 0) {
                    winStr.append(betStr).append(",");
                } else if ("冠军大".equals(betName + betStr) && num1 > 5) {
                    winStr.append(betStr).append(",");
                } else if ("冠军小".equals(betName + betStr) && num1 <= 5) {
                    winStr.append(betStr).append(",");
                } else if ("冠军单".equals(betName + betStr) && num1 % 2 == 1) {
                    winStr.append(betStr).append(",");
                } else if ("冠军双".equals(betName + betStr) && num1 % 2 == 0) {
                    winStr.append(betStr).append(",");
                } else if ("冠军龙".equals(betName + betStr) && num1 > Integer.valueOf(sgNumArr[9])) {
                    winStr.append(betStr).append(",");
                } else if ("冠军虎".equals(betName + betStr) && num1 < Integer.valueOf(sgNumArr[9])) {
                    winStr.append(betStr).append(",");
                } else if ("亚军大".equals(betName + betStr) && num2 > 5) {
                    winStr.append(betStr).append(",");
                } else if ("亚军小".equals(betName + betStr) && num2 <= 5) {
                    winStr.append(betStr).append(",");
                } else if ("亚军单".equals(betName + betStr) && num2 % 2 == 1) {
                    winStr.append(betStr).append(",");
                } else if ("亚军双".equals(betName + betStr) && num2 % 2 == 0) {
                    winStr.append(betStr).append(",");
                } else if ("亚军龙".equals(betName + betStr) && num2 > Integer.valueOf(sgNumArr[8])) {
                    winStr.append(betStr).append(",");
                } else if ("亚军虎".equals(betName + betStr) && num2 < Integer.valueOf(sgNumArr[8])) {
                    winStr.append(betStr).append(",");
                } else if ("第三名大".equals(betName + betStr) && daString(sgNumArr[2])) {
                    winStr.append(betStr).append(",");
                } else if ("第三名小".equals(betName + betStr) && !daString(sgNumArr[2])) {
                    winStr.append(betStr).append(",");
                } else if ("第三名单".equals(betName + betStr) && danString(sgNumArr[2])) {
                    winStr.append(betStr).append(",");
                } else if ("第三名双".equals(betName + betStr) && !danString(sgNumArr[2])) {
                    winStr.append(betStr).append(",");
                } else if ("第三名龙".equals(betName + betStr) && !hu(sgNumArr[2], sgNumArr[7])) {
                    winStr.append(betStr).append(",");
                } else if ("第三名虎".equals(betName + betStr) && hu(sgNumArr[2], sgNumArr[7])) {
                    winStr.append(betStr).append(",");
                } else if ("第四名大".equals(betName + betStr) && daString(sgNumArr[3])) {
                    winStr.append(betStr).append(",");
                } else if ("第四名小".equals(betName + betStr) && !daString(sgNumArr[3])) {
                    winStr.append(betStr).append(",");
                } else if ("第四名单".equals(betName + betStr) && danString(sgNumArr[3])) {
                    winStr.append(betStr).append(",");
                } else if ("第四名双".equals(betName + betStr) && !danString(sgNumArr[3])) {
                    winStr.append(betStr).append(",");
                } else if ("第四名龙".equals(betName + betStr) && !hu(sgNumArr[3], sgNumArr[6])) {
                    winStr.append(betStr).append(",");
                } else if ("第四名虎".equals(betName + betStr) && hu(sgNumArr[3], sgNumArr[6])) {
                    winStr.append(betStr).append(",");
                } else if ("第五名大".equals(betName + betStr) && daString(sgNumArr[4])) {
                    winStr.append(betStr).append(",");
                } else if ("第五名小".equals(betName + betStr) && !daString(sgNumArr[4])) {
                    winStr.append(betStr).append(",");
                } else if ("第五名单".equals(betName + betStr) && danString(sgNumArr[4])) {
                    winStr.append(betStr).append(",");
                } else if ("第五名双".equals(betName + betStr) && !danString(sgNumArr[4])) {
                    winStr.append(betStr).append(",");
                } else if ("第五名龙".equals(betName + betStr) && !hu(sgNumArr[4], sgNumArr[5])) {
                    winStr.append(betStr).append(",");
                } else if ("第五名虎".equals(betName + betStr) && hu(sgNumArr[4], sgNumArr[5])) {
                    winStr.append(betStr).append(",");
                } else if ("第六名大".equals(betName + betStr) && daString(sgNumArr[5])) {
                    winStr.append(betStr).append(",");
                } else if ("第六名小".equals(betName + betStr) && !daString(sgNumArr[5])) {
                    winStr.append(betStr).append(",");
                } else if ("第六名单".equals(betName + betStr) && danString(sgNumArr[5])) {
                    winStr.append(betStr).append(",");
                } else if ("第六名双".equals(betName + betStr) && !danString(sgNumArr[5])) {
                    winStr.append(betStr).append(",");
                } else if ("第七名大".equals(betName + betStr) && daString(sgNumArr[6])) {
                    winStr.append(betStr).append(",");
                } else if ("第七名小".equals(betName + betStr) && !daString(sgNumArr[6])) {
                    winStr.append(betStr).append(",");
                } else if ("第七名单".equals(betName + betStr) && danString(sgNumArr[6])) {
                    winStr.append(betStr).append(",");
                } else if ("第七名双".equals(betName + betStr) && !danString(sgNumArr[6])) {
                    winStr.append(betStr).append(",");
                } else if ("第八名大".equals(betName + betStr) && daString(sgNumArr[7])) {
                    winStr.append(betStr).append(",");
                } else if ("第八名小".equals(betName + betStr) && !daString(sgNumArr[7])) {
                    winStr.append(betStr).append(",");
                } else if ("第八名单".equals(betName + betStr) && danString(sgNumArr[7])) {
                    winStr.append(betStr).append(",");
                } else if ("第八名双".equals(betName + betStr) && !danString(sgNumArr[7])) {
                    winStr.append(betStr).append(",");
                } else if ("第九名大".equals(betName + betStr) && daString(sgNumArr[8])) {
                    winStr.append(betStr).append(",");
                } else if ("第九名小".equals(betName + betStr) && !daString(sgNumArr[8])) {
                    winStr.append(betStr).append(",");
                } else if ("第九名单".equals(betName + betStr) && danString(sgNumArr[8])) {
                    winStr.append(betStr).append(",");
                } else if ("第九名双".equals(betName + betStr) && !danString(sgNumArr[8])) {
                    winStr.append(betStr).append(",");
                } else if ("第十名大".equals(betName + betStr) && daString(sgNumArr[9])) {
                    winStr.append(betStr).append(",");
                } else if ("第十名小".equals(betName + betStr) && !daString(sgNumArr[9])) {
                    winStr.append(betStr).append(",");
                } else if ("第十名单".equals(betName + betStr) && danString(sgNumArr[9])) {
                    winStr.append(betStr).append(",");
                } else if ("第十名双".equals(betName + betStr) && !danString(sgNumArr[9])) {
                    winStr.append(betStr).append(",");
                }
            }
        } else if (String.format("%02d", playId % 100).equals(PLAY_ID_GYH)) {
            for (String betStr : betNumArr) {
                if (betStr.contains("@"))
                    betStr = betStr.split("@")[1];
                switch (betStr) {
                    case "冠亚大":
                        if (he > 11) {
                            winStr.append(betStr).append(",");
                        }
                        break;
                    case "冠亚小":
                        if (he <= 11) {
                            winStr.append(betStr).append(",");
                        }
                        break;
                    case "冠亚单":
                        if (he % 2 == 1) {
                            winStr.append(betStr).append(",");
                        }
                        break;
                    case "冠亚双":
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
            winStr = new StringBuilder(winStr.toString().replaceAll("冠亚和", ""));
            return winStr.substring(0, winStr.length() - 1);
        }
        return null;
    }


    /**
     * 判断是否中奖,中奖返回中奖信息,不中则返回null
     * (适用玩法:猜名次猜前几,单式猜前几,定位胆)
     *
     * @param betNum
     * @param sg
     * @return
     */
    public static String isWin(String betNum, String sg, Integer playIdThis) {
        String[] betNumArrs = betNum.split(",");
        String[] sgNumArr = sg.split(",");
        Integer playId = playIdThis;
        if (String.format("%02d", playId % 100).equals(PLAY_ID_15) || String.format("%02d", playId % 100).equals(PLAY_ID_610)) {
            // 1-5名玩法
            StringBuffer winNum = new StringBuffer();
            for (int i = 0, len = betNumArrs.length; i < len; i++) {
                String[] betNumArr = betNumArrs[i].split(" ");
                for (String betNumber : betNumArr) {
                    if (StringUtils.isBlank(betNumber)) {
                        continue;
                    }
                    String betn = betNumber;
                    if (betNumber.contains("@"))
                        betn = betNumber.split("@")[1];
                    if (betNumber.contains("冠军") || betNumber.contains("第一名") || betNumber.contains("第1名")) {
                        if (Integer.parseInt(betn) == Integer.parseInt(sgNumArr[0])) {
                            winNum.append(betNumber).append(",");
                        }

                    } else if (betNumber.contains("亚军") || betNumber.contains("第二名") || betNumber.contains("第2名")) {
                        if (Integer.parseInt(betn) == Integer.parseInt(sgNumArr[1])) {
                            winNum.append(betNumber).append(",");
                        }

                    } else if (betNumber.contains("第三名") || betNumber.contains("第3名")) {
                        if (Integer.parseInt(betn) == Integer.parseInt(sgNumArr[2])) {
                            winNum.append(betNumber).append(",");
                        }

                    } else if (betNumber.contains("第四名") || betNumber.contains("第4名")) {
                        if (Integer.parseInt(betn) == Integer.parseInt(sgNumArr[3])) {
                            winNum.append(betNumber).append(",");
                        }

                    } else if (betNumber.contains("第五名") || betNumber.contains("第5名")) {
                        if (Integer.parseInt(betn) == Integer.parseInt(sgNumArr[4])) {
                            winNum.append(betNumber).append(",");
                        }

                    } else if (betNumber.contains("第六名") || betNumber.contains("第6名")) {
                        if (Integer.parseInt(betn) == Integer.parseInt(sgNumArr[5])) {
                            winNum.append(betNumber).append(",");
                        }

                    } else if (betNumber.contains("第七名") || betNumber.contains("第7名")) {
                        if (Integer.parseInt(betn) == Integer.parseInt(sgNumArr[6])) {
                            winNum.append(betNumber).append(",");
                        }

                    } else if (betNumber.contains("第八名") || betNumber.contains("第8名")) {
                        if (Integer.parseInt(betn) == Integer.parseInt(sgNumArr[7])) {
                            winNum.append(betNumber).append(",");
                        }

                    } else if (betNumber.contains("第九名") || betNumber.contains("第9名")) {
                        if (Integer.parseInt(betn) == Integer.parseInt(sgNumArr[8])) {
                            winNum.append(betNumber).append(",");
                        }

                    } else if (betNumber.contains("第十名") || betNumber.contains("第10名")) {
                        if (Integer.parseInt(betn) == Integer.parseInt(sgNumArr[9])) {
                            winNum.append(betNumber).append(",");
                        }

                    }
                }
            }
            if (winNum.length() > 0) {
                return winNum.substring(0, winNum.length() - 1);
            }
        } else if (String.format("%02d", playId % 100).equals(PLAY_ID_1) || String.format("%02d", playId % 100).equals(PLAY_ID_2) || String.format("%02d", playId % 100).equals(PLAY_ID_3) ||
                String.format("%02d", playId % 100).equals(PLAY_ID_4) || String.format("%02d", playId % 100).equals(PLAY_ID_5) || String.format("%02d", playId % 100).equals(PLAY_ID_6) ||
                String.format("%02d", playId % 100).equals(PLAY_ID_7) || String.format("%02d", playId % 100).equals(PLAY_ID_8) || String.format("%02d", playId % 100).equals(PLAY_ID_9) ||
                String.format("%02d", playId % 100).equals(PLAY_ID_10)) {
            // 第一名-第十名玩法
//            StringBuffer winNum = new StringBuffer();
            String winNum = null;
            for (int i = 0, len = betNumArrs.length; i < len; i++) {

                String betn = null;
                if (betNum.contains("@")) {
                    betn = betNum.split("@")[1];
                }
                if (betn.equals("大") || betn.equals("小") || betn.equals("单") || betn.equals("双") || betn.equals("龙") || betn.equals("虎")) {
                    if (String.format("%02d", playId % 100).equals(PLAY_ID_1)) {
                        if (betn.equals("大") && Integer.parseInt(sgNumArr[0]) > 5) {
                            winNum = betNum;
                        } else if (betn.equals("小") && Integer.parseInt(sgNumArr[0]) <= 5) {
                            winNum = betNum;
                        } else if (betn.equals("单") && Integer.parseInt(sgNumArr[0]) % 2 != 0) {
                            winNum = betNum;
                        } else if (betn.equals("双") && Integer.parseInt(sgNumArr[0]) % 2 == 0) {
                            winNum = betNum;
                        } else if (betn.equals("龙") && Integer.parseInt(sgNumArr[0]) > Integer.parseInt(sgNumArr[9])) {
                            winNum = betNum;
                        } else if (betn.equals("虎") && Integer.parseInt(sgNumArr[0]) < Integer.parseInt(sgNumArr[9])) {
                            winNum = betNum;
                        }
                    } else if (String.format("%02d", playId % 100).equals(PLAY_ID_2)) {
                        if (betn.equals("大") && Integer.parseInt(sgNumArr[1]) > 5) {
                            winNum = betNum;
                        } else if (betn.equals("小") && Integer.parseInt(sgNumArr[1]) <= 5) {
                            winNum = betNum;
                        } else if (betn.equals("单") && Integer.parseInt(sgNumArr[1]) % 2 != 0) {
                            winNum = betNum;
                        } else if (betn.equals("双") && Integer.parseInt(sgNumArr[1]) % 2 == 0) {
                            winNum = betNum;
                        } else if (betn.equals("龙") && Integer.parseInt(sgNumArr[1]) > Integer.parseInt(sgNumArr[8])) {
                            winNum = betNum;
                        } else if (betn.equals("虎") && Integer.parseInt(sgNumArr[1]) < Integer.parseInt(sgNumArr[8])) {
                            winNum = betNum;
                        }
                    } else if (String.format("%02d", playId % 100).equals(PLAY_ID_3)) {
                        if (betn.equals("大") && Integer.parseInt(sgNumArr[2]) > 5) {
                            winNum = betNum;
                        } else if (betn.equals("小") && Integer.parseInt(sgNumArr[2]) <= 5) {
                            winNum = betNum;
                        } else if (betn.equals("单") && Integer.parseInt(sgNumArr[2]) % 2 != 0) {
                            winNum = betNum;
                        } else if (betn.equals("双") && Integer.parseInt(sgNumArr[2]) % 2 == 0) {
                            winNum = betNum;
                        } else if (betn.equals("龙") && Integer.parseInt(sgNumArr[2]) > Integer.parseInt(sgNumArr[7])) {
                            winNum = betNum;
                        } else if (betn.equals("虎") && Integer.parseInt(sgNumArr[2]) < Integer.parseInt(sgNumArr[7])) {
                            winNum = betNum;
                        }
                    } else if (String.format("%02d", playId % 100).equals(PLAY_ID_4)) {
                        if (betn.equals("大") && Integer.parseInt(sgNumArr[3]) > 5) {
                            winNum = betNum;
                        } else if (betn.equals("小") && Integer.parseInt(sgNumArr[3]) <= 5) {
                            winNum = betNum;
                        } else if (betn.equals("单") && Integer.parseInt(sgNumArr[3]) % 2 != 0) {
                            winNum = betNum;
                        } else if (betn.equals("双") && Integer.parseInt(sgNumArr[3]) % 2 == 0) {
                            winNum = betNum;
                        } else if (betn.equals("龙") && Integer.parseInt(sgNumArr[3]) > Integer.parseInt(sgNumArr[6])) {
                            winNum = betNum;
                        } else if (betn.equals("虎") && Integer.parseInt(sgNumArr[3]) < Integer.parseInt(sgNumArr[6])) {
                            winNum = betNum;
                        }
                    } else if (String.format("%02d", playId % 100).equals(PLAY_ID_5)) {
                        if (betn.equals("大") && Integer.parseInt(sgNumArr[4]) > 5) {
                            winNum = betNum;
                        } else if (betn.equals("小") && Integer.parseInt(sgNumArr[4]) <= 5) {
                            winNum = betNum;
                        } else if (betn.equals("单") && Integer.parseInt(sgNumArr[4]) % 2 != 0) {
                            winNum = betNum;
                        } else if (betn.equals("双") && Integer.parseInt(sgNumArr[4]) % 2 == 0) {
                            winNum = betNum;
                        } else if (betn.equals("龙") && Integer.parseInt(sgNumArr[4]) > Integer.parseInt(sgNumArr[5])) {
                            winNum = betNum;
                        } else if (betn.equals("虎") && Integer.parseInt(sgNumArr[4]) < Integer.parseInt(sgNumArr[5])) {
                            winNum = betNum;
                        }
                    } else if (String.format("%02d", playId % 100).equals(PLAY_ID_6)) {
                        if (betn.equals("大") && Integer.parseInt(sgNumArr[5]) > 5) {
                            winNum = betNum;
                        } else if (betn.equals("小") && Integer.parseInt(sgNumArr[5]) <= 5) {
                            winNum = betNum;
                        } else if (betn.equals("单") && Integer.parseInt(sgNumArr[5]) % 2 != 0) {
                            winNum = betNum;
                        } else if (betn.equals("双") && Integer.parseInt(sgNumArr[5]) % 2 == 0) {
                            winNum = betNum;
                        }
                    } else if (String.format("%02d", playId % 100).equals(PLAY_ID_7)) {
                        if (betn.equals("大") && Integer.parseInt(sgNumArr[6]) > 5) {
                            winNum = betNum;
                        } else if (betn.equals("小") && Integer.parseInt(sgNumArr[6]) <= 5) {
                            winNum = betNum;
                        } else if (betn.equals("单") && Integer.parseInt(sgNumArr[6]) % 2 != 0) {
                            winNum = betNum;
                        } else if (betn.equals("双") && Integer.parseInt(sgNumArr[6]) % 2 == 0) {
                            winNum = betNum;
                        }
                    } else if (String.format("%02d", playId % 100).equals(PLAY_ID_8)) {
                        if (betn.equals("大") && Integer.parseInt(sgNumArr[7]) > 5) {
                            winNum = betNum;
                        } else if (betn.equals("小") && Integer.parseInt(sgNumArr[7]) <= 5) {
                            winNum = betNum;
                        } else if (betn.equals("单") && Integer.parseInt(sgNumArr[7]) % 2 != 0) {
                            winNum = betNum;
                        } else if (betn.equals("双") && Integer.parseInt(sgNumArr[7]) % 2 == 0) {
                            winNum = betNum;
                        }
                    } else if (String.format("%02d", playId % 100).equals(PLAY_ID_9)) {
                        if (betn.equals("大") && Integer.parseInt(sgNumArr[8]) > 5) {
                            winNum = betNum;
                        } else if (betn.equals("小") && Integer.parseInt(sgNumArr[8]) <= 5) {
                            winNum = betNum;
                        } else if (betn.equals("单") && Integer.parseInt(sgNumArr[8]) % 2 != 0) {
                            winNum = betNum;
                        } else if (betn.equals("双") && Integer.parseInt(sgNumArr[8]) % 2 == 0) {
                            winNum = betNum;
                        }
                    } else if (String.format("%02d", playId % 100).equals(PLAY_ID_10)) {
                        if (betn.equals("大") && Integer.parseInt(sgNumArr[9]) > 5) {
                            winNum = betNum;
                        } else if (betn.equals("小") && Integer.parseInt(sgNumArr[9]) <= 5) {
                            winNum = betNum;
                        } else if (betn.equals("单") && Integer.parseInt(sgNumArr[9]) % 2 != 0) {
                            winNum = betNum;
                        } else if (betn.equals("双") && Integer.parseInt(sgNumArr[9]) % 2 == 0) {
                            winNum = betNum;
                        }
                    }

                } else {
                    if (betNum.contains("冠军") || betNum.contains("第一名") || betNum.contains("第1名")) {
                        if (Integer.parseInt(betn) == Integer.parseInt(sgNumArr[0])) {
                            winNum = betNum;
                        }

                    } else if (betNum.contains("亚军") || betNum.contains("第二名") || betNum.contains("第2名")) {
                        if (Integer.parseInt(betn) == Integer.parseInt(sgNumArr[1])) {
                            winNum = betNum;
                        }

                    } else if (betNum.contains("第三名") || betNum.contains("第3名")) {
                        if (Integer.parseInt(betn) == Integer.parseInt(sgNumArr[2])) {
                            winNum = betNum;
                        }

                    } else if (betNum.contains("第四名") || betNum.contains("第4名")) {
                        if (Integer.parseInt(betn) == Integer.parseInt(sgNumArr[3])) {
                            winNum = betNum;
                        }

                    } else if (betNum.contains("第五名") || betNum.contains("第5名")) {
                        if (Integer.parseInt(betn) == Integer.parseInt(sgNumArr[4])) {
                            winNum = betNum;
                        }

                    } else if (betNum.contains("第六名") || betNum.contains("第6名")) {
                        if (Integer.parseInt(betn) == Integer.parseInt(sgNumArr[5])) {
                            winNum = betNum;
                        }

                    } else if (betNum.contains("第七名") || betNum.contains("第7名")) {
                        if (Integer.parseInt(betn) == Integer.parseInt(sgNumArr[6])) {
                            winNum = betNum;
                        }

                    } else if (betNum.contains("第八名") || betNum.contains("第8名")) {
                        if (Integer.parseInt(betn) == Integer.parseInt(sgNumArr[7])) {
                            winNum = betNum;
                        }

                    } else if (betNum.contains("第九名") || betNum.contains("第9名")) {
                        if (Integer.parseInt(betn) == Integer.parseInt(sgNumArr[8])) {
                            winNum = betNum;
                        }

                    } else if (betNum.contains("第十名") || betNum.contains("第10名")) {
                        if (Integer.parseInt(betn) == Integer.parseInt(sgNumArr[9])) {
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


    /**
     * 判断号码是否为虎
     *
     * @param num1
     * @param num2
     * @return
     */
    public static boolean hu(String num1, String num2) {
        return Integer.valueOf(num1) < Integer.valueOf(num2);
    }

    /**
     * 判断号码是否为单
     *
     * @param num
     * @return
     */
    public static boolean danString(String num) {
        return Integer.valueOf(num) % 2 == 1;
    }

    /**
     * 判断号码是否为大
     *
     * @param num
     * @return
     */
    public static boolean daString(String num) {
        return Integer.valueOf(num) > 5;
    }
}
