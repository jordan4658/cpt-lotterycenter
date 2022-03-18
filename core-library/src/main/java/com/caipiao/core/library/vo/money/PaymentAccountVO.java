package com.caipiao.core.library.vo.money;

public class PaymentAccountVO {
    private Integer accountId;
    /**
     * 字段: payment_account.account<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 姓名
     *
     * @mbggenerated
     */
    private String account;

    /**
     * 字段: payment_account.name<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 账号
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 字段: payment_account.skf<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 收款方
     *
     * @mbggenerated
     */
    private String skf;

    /**
     * 字段: payment_account.instructions<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10000<br/>
     * 说明: 说明
     *
     * @mbggenerated
     */
    private String instructions;

    private Integer fuyan;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkf() {
        return skf;
    }

    public void setSkf(String skf) {
        this.skf = skf;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Integer getFuyan() {
        return fuyan;
    }

    public void setFuyan(Integer fuyan) {
        this.fuyan = fuyan;
    }
}
