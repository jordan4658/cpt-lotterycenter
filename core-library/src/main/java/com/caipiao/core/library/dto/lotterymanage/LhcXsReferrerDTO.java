package com.caipiao.core.library.dto.lotterymanage;

import com.caipiao.core.library.tool.TimeHelper;
import com.mapper.domain.LhcXsReferrer;

/**
 * 六合彩心水推荐人
 *
 * @author lzy
 * @create 2018-09-08 14:32
 **/
public class LhcXsReferrerDTO {

    /**
     * 说明: 名称
     */
    private String name;

    /**
     * 说明: 微信号
     */
    private String wxh;

    /**
     * 说明: 头像
     */
    private String heads;

    /**
     * 说明: 二维码
     */
    private String qrCode;

    public LhcXsReferrer getLhcXsReferrer() {
        LhcXsReferrer lhcXsReferrer = new LhcXsReferrer();
        lhcXsReferrer.setName(this.name);
        lhcXsReferrer.setWxh(this.wxh);
        lhcXsReferrer.setHeads(this.heads);
        lhcXsReferrer.setQrCode(this.qrCode);
        lhcXsReferrer.setCreateTime(TimeHelper.date());
        return lhcXsReferrer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWxh() {
        return wxh;
    }

    public void setWxh(String wxh) {
        this.wxh = wxh;
    }

    public String getHeads() {
        return heads;
    }

    public void setHeads(String heads) {
        this.heads = heads;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
