package com.caipiao.task.server.util;

import com.caipiao.core.library.tool.CountUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ShaoMing
 * @version 1.0.0
 * @date 2018/12/6 11:08
 */
public class BetPceggUtil {

    //红波
    public static final List<Integer> RED = Lists.newArrayList(3, 6, 9, 12, 15, 18, 21, 24);
    //蓝波
    public static final List<Integer> BLUE = Lists.newArrayList(2, 5, 8, 11, 17, 20, 23, 26);
    //绿波
    public static final List<Integer> GREEN = Lists.newArrayList(1, 4, 7, 10, 16, 19, 22, 25);

    // 混合玩法id   末两位
    public static final String PLAY_ID_HH = "01";
    // 色波玩法id
    public static final String PLAY_ID_SB = "02";
    // 豹子玩法id
    public static final String PLAY_ID_BZ = "03";
    // 特码包三玩法id
    public static final String PLAY_ID_TMBS = "04";
    // 特码玩法id
    public static final String PLAY_ID_TM = "05";

    /**
     * 判断PC蛋蛋是否中奖,中奖返回中奖信息,不中则返回null
     *
     * @param betNum 投注号码
     * @param sg     赛果
     * @return
     */
    public static String isWin(String betNum, String sg, Integer playId) {
        String[] sgArr = sg.split(",");
        Integer num1 = Integer.valueOf(sgArr[0]);
        Integer num2 = Integer.valueOf(sgArr[1]);
        Integer num3 = Integer.valueOf(sgArr[2]);

        // 不从数据库查玩法,写死中奖方式
        Integer tmNum = num1 + num2 + num3;
        String[] betNums = betNum.split(",");
        String playIdLastTow = String.valueOf(playId).substring(4, 6);
        switch (playIdLastTow) {
            case PLAY_ID_TM:
                // 特码
                for (String num : betNums) {
                    if (tmNum.equals(Integer.valueOf(num))) {
                        return num;
                    }
                }
                break;
            case PLAY_ID_TMBS:
                String num = null;
                boolean flag = Arrays.asList(betNums).contains(tmNum.toString());
                if (flag) {
                    num = String.valueOf(CountUtils.countCnm(betNums.length - 1, 2));
                }
                return num;
            case PLAY_ID_BZ:
                // 豹子
                if (num1.equals(num2) && num2.equals(num3)) {
                    return betNum;
                }
                break;
            case PLAY_ID_SB:
                // 色波
                return isWinByBs(betNum, sg);
            case PLAY_ID_HH:
                // 混合
                return isWinByHh(betNum, sg);
            default:
                break;
        }
        return null;
    }

    /**
     * 判断色波是否中奖,中奖返回中奖信息,不中则返回null
     *
     * @param betNum 投注号码
     * @param sg     赛果
     * @return
     */
    private static String isWinByBs(String betNum, String sg) {
        String[] sgArr = sg.split(",");
        // 不从数据库查玩法,写死中奖方式
        Integer winNum = Integer.valueOf(sgArr[0]) + Integer.valueOf(sgArr[1]) + Integer.valueOf(sgArr[2]);
        String bose;
        if (RED.contains(winNum)) {
            bose = "红波";
        } else if (BLUE.contains(winNum)) {
            bose = "蓝波";
        } else if (GREEN.contains(winNum)) {
            bose = "绿波";
        } else {
            return null;
        }
        if (bose.equals(betNum)) {
            return betNum;
        }
        return null;
    }


    /**
     * 判断混合是否中奖,中奖返回中奖信息,不中则返回null
     *
     * @param betNum 投注号码
     * @param sg     赛果
     * @return
     */
    private static String isWinByHh(String betNum, String sg) {
        String[] sgArr = sg.split(",");
        // 不从数据库查玩法,写死中奖方式
        int winNum = Integer.valueOf(sgArr[0]) + Integer.valueOf(sgArr[1]) + Integer.valueOf(sgArr[2]);
        List<String> winStrList = new ArrayList<>();
        String dx = winNum >= 14 ? "大" : "小";
        String ds = winNum % 2 == 0 ? "双" : "单";
        winStrList.add(dx);
        winStrList.add(ds);
        winStrList.add(dx + ds);
        if (winNum >= 0 && winNum <= 5) {
            winStrList.add("极小");
        } else if (winNum >= 22 && winNum <= 27) {
            winStrList.add("极大");
        }
        String winStr = "";
        if (winStrList.contains(betNum)) {
            winStr += betNum + ",";
        }
        if (StringUtils.isNotBlank(winStr)) {
            return winStr.substring(0, winStr.length() - 1);
        }
        return null;
    }

}
