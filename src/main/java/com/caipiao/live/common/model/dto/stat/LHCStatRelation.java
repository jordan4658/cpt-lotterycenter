package com.caipiao.live.common.model.dto.stat;

/**
 * 六合彩资讯统计辅助类
 */
public class LHCStatRelation {
    /** 统计名称 */
    private String statKey;
    /** 统计结果长度 */
    private int statLength;
    /** 返回前端名称 */
    private String resultKey;

    public static LHCStatRelation newInstance(String statKey, int statLength, String resultKey) {
        return new LHCStatRelation(statKey, statLength, resultKey);
    }

    public LHCStatRelation(String statKey, int statLength, String resultKey) {
        this.statKey = statKey;
        this.statLength = statLength;
        this.resultKey = resultKey;
    }

    public String getStatKey() {
        return statKey;
    }

    public int getStatLength() {
        return statLength;
    }

    public String getResultKey() {
        return resultKey;
    }

}
