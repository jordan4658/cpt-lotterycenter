package com.caipiao.core.library.vo.lotterymanage;

import java.util.List;

/**
 * @author lzy
 * @create 2018-08-21 11:06
 **/
public class LhcHandicapPageVO {

    private LhcHandicapVO nowHandicap;

    private LhcHandicapVO nextHandicap;

    private List<LhcHandicapVO> handicapVOList;

    public LhcHandicapVO getNowHandicap() {
        return nowHandicap;
    }

    public void setNowHandicap(LhcHandicapVO nowHandicap) {
        this.nowHandicap = nowHandicap;
    }

    public LhcHandicapVO getNextHandicap() {
        return nextHandicap;
    }

    public void setNextHandicap(LhcHandicapVO nextHandicap) {
        this.nextHandicap = nextHandicap;
    }

    public List<LhcHandicapVO> getHandicapVOList() {
        return handicapVOList;
    }

    public void setHandicapVOList(List<LhcHandicapVO> handicapVOList) {
        this.handicapVOList = handicapVOList;
    }
}
