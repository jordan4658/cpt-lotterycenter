package com.caipiao.core.library.dto.money;

import com.mapper.domain.SupportBank;

/**
 * @author lzy
 * @create 2018-07-04 11:16
 **/
public class SupportBankDTO {

    private Integer id;

    /**
     * 说明: 银行名称
     */
    private String name;

    /**
     * 说明: 图标
     */
    private String icon;

    /**
     * 说明: 绑卡支持:0,禁用;1,启用
     */
    private Integer binding;

    /**
     * 说明: 支付支持:0,禁用;1,启用
     */
    private Integer pay;

    public static SupportBank getSupportBank(SupportBankDTO supportBankDTO) {
        if (supportBankDTO != null) {
            SupportBank supportBank = new SupportBank();
            supportBank.setId(supportBankDTO.getId());
            supportBank.setName(supportBankDTO.getName());
            supportBank.setIcon(supportBankDTO.getIcon());
            supportBank.setBinding(supportBankDTO.getBinding());
            supportBank.setPay(supportBankDTO.getPay());
            supportBank.setDeleted(1);
            return supportBank;
        }

        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getBinding() {
        return binding;
    }

    public void setBinding(Integer binding) {
        this.binding = binding;
    }

    public Integer getPay() {
        return pay;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }
}
