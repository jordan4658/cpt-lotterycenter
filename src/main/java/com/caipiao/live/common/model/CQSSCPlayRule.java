package com.caipiao.live.common.model;

import java.util.List;

/**
 * 重庆时时彩玩法规则
 *
 * @author lzy
 * @create 2018-07-11 11:08
 **/
public class CQSSCPlayRule {

    //号码对应的大小单双
    public static final String[] DAXIAO_DANSHUANG = {"小双", "小单", "小双", "小单", "小双", "大单", "大双", "大单", "大双", "大单"};

    //后三组合类型
    public static final String[] HOU_SAN = {"组三", "组六", "豹子"};

    /**
     * 获取开奖号码对应的后三组合类型
     *
     * @param winNums 开奖号码
     * @return
     */
    public static String houSan(List<Integer> winNums) {
        int bai = winNums.get(2);
        int shi = winNums.get(3);
        int ge = winNums.get(4);
        return houSan(bai, shi, ge);
    }

    /**
     * 获取开奖号码对应的后三组合类型
     *
     * @return
     */
    public static String houSan(Integer bai, Integer shi, Integer ge) {
        if (bai == null || shi == null || ge == null) {
            return "";
        }

        if (bai.equals(shi) && shi.equals(ge)) {
            return HOU_SAN[2];
        } else if (bai.equals(shi) || shi.equals(ge) || bai.equals(ge)) {
            return HOU_SAN[0];
        } else {
            return HOU_SAN[1];
        }
    }

    public static String daxiaoDanShuang(Integer num) {
        if (num == null) {
            return "";
        }
        if (num == 0 || num == 2 || num == 4) {
            return "小双";
        } else if (num == 1 || num == 3) {
            return "小单";
        } else if (num == 5 || num == 7 || num == 9) {
            return "大单";
        } else if (num == 6 || num == 8) {
            return "大双";
        }
        return "";
    }
}
