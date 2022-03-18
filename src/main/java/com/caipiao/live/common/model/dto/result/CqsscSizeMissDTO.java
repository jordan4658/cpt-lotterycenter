package com.caipiao.live.common.model.dto.result;

/**
 * 重庆时时彩资讯返回类
 */
public class CqsscSizeMissDTO implements Comparable<CqsscSizeMissDTO> {
    //遗漏值
    private Integer missValue;
    //遗漏数量
    private Integer missCount;

    public Integer getMissValue() {
        return missValue;
    }

    public void setMissValue(Integer missValue) {
        this.missValue = missValue;
    }

    public Integer getMissCount() {
        return missCount;
    }

    public void setMissCount(Integer missCount) {
        this.missCount = missCount;
    }

    @Override
    public int compareTo(CqsscSizeMissDTO o) {
        return this.missValue > o.getMissValue() ? -1 : 1;
    }
}
