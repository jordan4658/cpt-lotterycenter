package com.caipiao.core.library.vo.result;

import com.caipiao.core.library.model.dao.SscModel.LotterySgModel;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 彩种赛果结果类
 */
public class LotterySgResult {

    private Map<String, LotterySgModel> cpk;
    private Map<String, LotterySgModel> kcw;
    private Map<String, LotterySgModel> byw;

    public LotterySgResult adjustResult() {
        if (CollectionUtils.isEmpty(kcw)) {
            this.kcw = new HashMap<>();
        }
        if (CollectionUtils.isEmpty(cpk)) {
            this.cpk = new HashMap<>();
        }
        if (CollectionUtils.isEmpty(byw)) {
            this.byw = new HashMap<>();
        }
        return this;
    }

    public Map<String, LotterySgModel> getKcw() {
        return kcw;
    }

    public void setKcw(Map<String, LotterySgModel> kcw) {
        this.kcw = kcw;
    }

    public Map<String, LotterySgModel> getCpk() {
        return cpk;
    }

    public void setCpk(Map<String, LotterySgModel> cpk) {
        this.cpk = cpk;
    }

    public Map<String, LotterySgModel> getByw() {
        return byw;
    }

    public void setByw(Map<String, LotterySgModel> byw) {
        this.byw = byw;
    }
}
