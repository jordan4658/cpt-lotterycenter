package com.mapper.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class AppMember implements Serializable {
    /**
     * 字段: app_member.id<br/>
     * 主键: 自动增长<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 字段: app_member.account<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 账号
     *
     * @mbggenerated
     */
    private String account;

    /**
     * 字段: app_member.pay_password<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 支付密码
     *
     * @mbggenerated
     */
    private String payPassword;

    /**
     * 字段: app_member.password<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 密码
     *
     * @mbggenerated
     */
    private String password;

    /**
     * 字段: app_member.salt<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 加密盐
     *
     * @mbggenerated
     */
    private String salt;

    /**
     * 字段: app_member.qq<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: qq
     *
     * @mbggenerated
     */
    private String qq;

    /**
     * 字段: app_member.sex<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 性别:0,女;1,男
     *
     * @mbggenerated
     */
    private Integer sex;

    /**
     * 字段: app_member.heads<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 会员头像
     *
     * @mbggenerated
     */
    private String heads;

    /**
     * 字段: app_member.real_name<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 真实姓名
     *
     * @mbggenerated
     */
    private String realName;

    /**
     * 字段: app_member.vip_id<br/>
     * 必填: false<br/>
     * 缺省: 1<br/>
     * 长度: 10<br/>
     * 说明: vip等级,对应vip_grade的id,默认1
     *
     * @mbggenerated
     */
    private Integer vipId;

    /**
     * 字段: app_member.top_up_grade_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 充值层级,对应top_up_grade的id
     *
     * @mbggenerated
     */
    private Integer topUpGradeId;

    /**
     * 字段: app_member.bet_amount<br/>
     * 必填: true<br/>
     * 缺省: 0.00<br/>
     * 长度: 10<br/>
     * 说明: 累计投注（元）
     *
     * @mbggenerated
     */
    private BigDecimal betAmount;

    /**
     * 字段: app_member.pay_amount<br/>
     * 必填: true<br/>
     * 缺省: 0.00<br/>
     * 长度: 10<br/>
     * 说明: 累计充值（元）
     *
     * @mbggenerated
     */
    private BigDecimal payAmount;

    /**
     * 字段: app_member.withdrawal_amount<br/>
     * 必填: true<br/>
     * 缺省: 0.00<br/>
     * 长度: 10<br/>
     * 说明: 累计提现（元）
     *
     * @mbggenerated
     */
    private BigDecimal withdrawalAmount;

    /**
     * 字段: app_member.no_withdrawal_amount<br/>
     * 必填: true<br/>
     * 缺省: 0.00<br/>
     * 长度: 10<br/>
     * 说明: 当前不可提现金额（元）
     *
     * @mbggenerated
     */
    private BigDecimal noWithdrawalAmount;

    /**
     * 字段: app_member.balance<br/>
     * 必填: true<br/>
     * 缺省: 0.00<br/>
     * 长度: 10<br/>
     * 说明: 当前余额（元）
     *
     * @mbggenerated
     */
    private BigDecimal balance;

    /**
     * 字段: app_member.id_card<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 身份证号
     *
     * @mbggenerated
     */
    private String idCard;

    /**
     * 字段: app_member.phone<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 手机号码
     *
     * @mbggenerated
     */
    private String phone;

    /**
     * 字段: app_member.birthday<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 生日
     *
     * @mbggenerated
     */
    private String birthday;

    /**
     * 字段: app_member.nickname<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 昵称
     *
     * @mbggenerated
     */
    private String nickname;

    /**
     * 字段: app_member.promotion_code<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 推广码
     *
     * @mbggenerated
     */
    private String promotionCode;

    /**
     * 字段: app_member.create_time<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 注册时间 yyyy-MM-dd HH:mm:ss
     *
     * @mbggenerated
     */
    private String createTime;

    /**
     * 字段: app_member.register_ip<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 注册ip
     *
     * @mbggenerated
     */
    private String registerIp;

    /**
     * 字段: app_member.register_address<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 注册地址
     *
     * @mbggenerated
     */
    private String registerAddress;

    /**
     * 字段: app_member.share_account<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 分享人,对应app_member的account
     *
     * @mbggenerated
     */
    private String shareAccount;

    /**
     * 字段: app_member.login_type<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 登录类型，1：QQ, 2: 微信, 3：微博, 4：账号
     *
     * @mbggenerated
     */
    private Integer loginType;

    /**
     * 字段: app_member.login_time<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 20<br/>
     * 说明: 最后登录时间 yyyy-MM-dd HH:mm:ss
     *
     * @mbggenerated
     */
    private String loginTime;

    /**
     * 字段: app_member.openId<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 128<br/>
     * 说明: 第三方登录时返回的唯一标识
     *
     * @mbggenerated
     */
    private String openid;

    /**
     * 字段: app_member.token<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 系统生成唯一ID
     *
     * @mbggenerated
     */
    private String token;

    /**
     * 字段: app_member.token_time<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 19<br/>
     * 说明: token生成的时间
     *
     * @mbggenerated
     */
    private Long tokenTime;

    /**
     * 字段: app_member.bet_status<br/>
     * 必填: false<br/>
     * 缺省: 1<br/>
     * 长度: 10<br/>
     * 说明: 投注状态: 0,不允许;1,允许
     *
     * @mbggenerated
     */
    private Integer betStatus;

    /**
     * 字段: app_member.backwater_status<br/>
     * 必填: false<br/>
     * 缺省: 1<br/>
     * 长度: 10<br/>
     * 说明: 返水状态: 0,不允许;1,允许
     *
     * @mbggenerated
     */
    private Integer backwaterStatus;

    /**
     * 字段: app_member.chat_status<br/>
     * 必填: false<br/>
     * 缺省: 1<br/>
     * 长度: 10<br/>
     * 说明: 聊天状态: 0,不允许;1,允许
     *
     * @mbggenerated
     */
    private Integer chatStatus;

    /**
     * 字段: app_member.share_order_status<br/>
     * 必填: false<br/>
     * 缺省: 1<br/>
     * 长度: 10<br/>
     * 说明: 晒单状态: 0,不允许;1,允许
     *
     * @mbggenerated
     */
    private Integer shareOrderStatus;

    /**
     * 字段: app_member.payment_status<br/>
     * 必填: false<br/>
     * 缺省: 1<br/>
     * 长度: 10<br/>
     * 说明: 充值状态0禁止充值1允许充值
     *
     * @mbggenerated
     */
    private Integer paymentStatus;

    /**
     * 字段: app_member.withdraw_status<br/>
     * 必填: false<br/>
     * 缺省: 1<br/>
     * 长度: 10<br/>
     * 说明: 提现状态0禁止1允许
     *
     * @mbggenerated
     */
    private Integer withdrawStatus;

    /**
     * 字段: app_member.freeze_status<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 冻结状态: 0,不冻结;1,冻结
     *
     * @mbggenerated
     */
    private Integer freezeStatus;

    /**
     * 字段: app_member.address<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 256<br/>
     * 说明: 登录地址
     *
     * @mbggenerated
     */
    private String address;

    /**
     * 字段: app_member.login_ip<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 128<br/>
     * 说明: 登录ip
     *
     * @mbggenerated
     */
    private String loginIp;

    /**
     * 字段: app_member.lhcxs_status<br/>
     * 必填: true<br/>
     * 缺省: 1<br/>
     * 长度: 10<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    private Integer lhcxsStatus;

    /**
     * 字段: app_member.deleted<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 是否删除
     *
     * @mbggenerated
     */
    private Integer deleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table app_member
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return app_member.id: 
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 字段: app_member.id<br/>
     * 主键: 自动增长<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return app_member.account: 账号
     *
     * @mbggenerated
     */
    public String getAccount() {
        return account;
    }

    /**
     * 字段: app_member.account<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 账号
     *
     * @mbggenerated
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return app_member.pay_password: 支付密码
     *
     * @mbggenerated
     */
    public String getPayPassword() {
        return payPassword;
    }

    /**
     * 字段: app_member.pay_password<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 支付密码
     *
     * @mbggenerated
     */
    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    /**
     * @return app_member.password: 密码
     *
     * @mbggenerated
     */
    public String getPassword() {
        return password;
    }

    /**
     * 字段: app_member.password<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 密码
     *
     * @mbggenerated
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return app_member.salt: 加密盐
     *
     * @mbggenerated
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 字段: app_member.salt<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 加密盐
     *
     * @mbggenerated
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * @return app_member.qq: qq
     *
     * @mbggenerated
     */
    public String getQq() {
        return qq;
    }

    /**
     * 字段: app_member.qq<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: qq
     *
     * @mbggenerated
     */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /**
     * @return app_member.sex: 性别:0,女;1,男
     *
     * @mbggenerated
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 字段: app_member.sex<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 性别:0,女;1,男
     *
     * @mbggenerated
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * @return app_member.heads: 会员头像
     *
     * @mbggenerated
     */
    public String getHeads() {
        return heads;
    }

    /**
     * 字段: app_member.heads<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 会员头像
     *
     * @mbggenerated
     */
    public void setHeads(String heads) {
        this.heads = heads;
    }

    /**
     * @return app_member.real_name: 真实姓名
     *
     * @mbggenerated
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 字段: app_member.real_name<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 真实姓名
     *
     * @mbggenerated
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * @return app_member.vip_id: vip等级,对应vip_grade的id,默认1
     *
     * @mbggenerated
     */
    public Integer getVipId() {
        return vipId;
    }

    /**
     * 字段: app_member.vip_id<br/>
     * 必填: false<br/>
     * 缺省: 1<br/>
     * 长度: 10<br/>
     * 说明: vip等级,对应vip_grade的id,默认1
     *
     * @mbggenerated
     */
    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    /**
     * @return app_member.top_up_grade_id: 充值层级,对应top_up_grade的id
     *
     * @mbggenerated
     */
    public Integer getTopUpGradeId() {
        return topUpGradeId;
    }

    /**
     * 字段: app_member.top_up_grade_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 充值层级,对应top_up_grade的id
     *
     * @mbggenerated
     */
    public void setTopUpGradeId(Integer topUpGradeId) {
        this.topUpGradeId = topUpGradeId;
    }

    /**
     * @return app_member.bet_amount: 累计投注（元）
     *
     * @mbggenerated
     */
    public BigDecimal getBetAmount() {
        return betAmount;
    }

    /**
     * 字段: app_member.bet_amount<br/>
     * 必填: true<br/>
     * 缺省: 0.00<br/>
     * 长度: 10<br/>
     * 说明: 累计投注（元）
     *
     * @mbggenerated
     */
    public void setBetAmount(BigDecimal betAmount) {
        this.betAmount = betAmount;
    }

    /**
     * @return app_member.pay_amount: 累计充值（元）
     *
     * @mbggenerated
     */
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    /**
     * 字段: app_member.pay_amount<br/>
     * 必填: true<br/>
     * 缺省: 0.00<br/>
     * 长度: 10<br/>
     * 说明: 累计充值（元）
     *
     * @mbggenerated
     */
    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    /**
     * @return app_member.withdrawal_amount: 累计提现（元）
     *
     * @mbggenerated
     */
    public BigDecimal getWithdrawalAmount() {
        return withdrawalAmount;
    }

    /**
     * 字段: app_member.withdrawal_amount<br/>
     * 必填: true<br/>
     * 缺省: 0.00<br/>
     * 长度: 10<br/>
     * 说明: 累计提现（元）
     *
     * @mbggenerated
     */
    public void setWithdrawalAmount(BigDecimal withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    /**
     * @return app_member.no_withdrawal_amount: 当前不可提现金额（元）
     *
     * @mbggenerated
     */
    public BigDecimal getNoWithdrawalAmount() {
        return noWithdrawalAmount;
    }

    /**
     * 字段: app_member.no_withdrawal_amount<br/>
     * 必填: true<br/>
     * 缺省: 0.00<br/>
     * 长度: 10<br/>
     * 说明: 当前不可提现金额（元）
     *
     * @mbggenerated
     */
    public void setNoWithdrawalAmount(BigDecimal noWithdrawalAmount) {
        this.noWithdrawalAmount = noWithdrawalAmount;
    }

    /**
     * @return app_member.balance: 当前余额（元）
     *
     * @mbggenerated
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 字段: app_member.balance<br/>
     * 必填: true<br/>
     * 缺省: 0.00<br/>
     * 长度: 10<br/>
     * 说明: 当前余额（元）
     *
     * @mbggenerated
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * @return app_member.id_card: 身份证号
     *
     * @mbggenerated
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * 字段: app_member.id_card<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 身份证号
     *
     * @mbggenerated
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * @return app_member.phone: 手机号码
     *
     * @mbggenerated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 字段: app_member.phone<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 手机号码
     *
     * @mbggenerated
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return app_member.birthday: 生日
     *
     * @mbggenerated
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * 字段: app_member.birthday<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 生日
     *
     * @mbggenerated
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * @return app_member.nickname: 昵称
     *
     * @mbggenerated
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 字段: app_member.nickname<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 昵称
     *
     * @mbggenerated
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return app_member.promotion_code: 推广码
     *
     * @mbggenerated
     */
    public String getPromotionCode() {
        return promotionCode;
    }

    /**
     * 字段: app_member.promotion_code<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 推广码
     *
     * @mbggenerated
     */
    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    /**
     * @return app_member.create_time: 注册时间 yyyy-MM-dd HH:mm:ss
     *
     * @mbggenerated
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 字段: app_member.create_time<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 注册时间 yyyy-MM-dd HH:mm:ss
     *
     * @mbggenerated
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * @return app_member.register_ip: 注册ip
     *
     * @mbggenerated
     */
    public String getRegisterIp() {
        return registerIp;
    }

    /**
     * 字段: app_member.register_ip<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 注册ip
     *
     * @mbggenerated
     */
    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    /**
     * @return app_member.register_address: 注册地址
     *
     * @mbggenerated
     */
    public String getRegisterAddress() {
        return registerAddress;
    }

    /**
     * 字段: app_member.register_address<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 注册地址
     *
     * @mbggenerated
     */
    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    /**
     * @return app_member.share_account: 分享人,对应app_member的account
     *
     * @mbggenerated
     */
    public String getShareAccount() {
        return shareAccount;
    }

    /**
     * 字段: app_member.share_account<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 分享人,对应app_member的account
     *
     * @mbggenerated
     */
    public void setShareAccount(String shareAccount) {
        this.shareAccount = shareAccount;
    }

    /**
     * @return app_member.login_type: 登录类型，1：QQ, 2: 微信, 3：微博, 4：账号
     *
     * @mbggenerated
     */
    public Integer getLoginType() {
        return loginType;
    }

    /**
     * 字段: app_member.login_type<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 登录类型，1：QQ, 2: 微信, 3：微博, 4：账号
     *
     * @mbggenerated
     */
    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    /**
     * @return app_member.login_time: 最后登录时间 yyyy-MM-dd HH:mm:ss
     *
     * @mbggenerated
     */
    public String getLoginTime() {
        return loginTime;
    }

    /**
     * 字段: app_member.login_time<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 20<br/>
     * 说明: 最后登录时间 yyyy-MM-dd HH:mm:ss
     *
     * @mbggenerated
     */
    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * @return app_member.openId: 第三方登录时返回的唯一标识
     *
     * @mbggenerated
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * 字段: app_member.openId<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 128<br/>
     * 说明: 第三方登录时返回的唯一标识
     *
     * @mbggenerated
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
     * @return app_member.token: 系统生成唯一ID
     *
     * @mbggenerated
     */
    public String getToken() {
        return token;
    }

    /**
     * 字段: app_member.token<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 系统生成唯一ID
     *
     * @mbggenerated
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return app_member.token_time: token生成的时间
     *
     * @mbggenerated
     */
    public Long getTokenTime() {
        return tokenTime;
    }

    /**
     * 字段: app_member.token_time<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 19<br/>
     * 说明: token生成的时间
     *
     * @mbggenerated
     */
    public void setTokenTime(Long tokenTime) {
        this.tokenTime = tokenTime;
    }

    /**
     * @return app_member.bet_status: 投注状态: 0,不允许;1,允许
     *
     * @mbggenerated
     */
    public Integer getBetStatus() {
        return betStatus;
    }

    /**
     * 字段: app_member.bet_status<br/>
     * 必填: false<br/>
     * 缺省: 1<br/>
     * 长度: 10<br/>
     * 说明: 投注状态: 0,不允许;1,允许
     *
     * @mbggenerated
     */
    public void setBetStatus(Integer betStatus) {
        this.betStatus = betStatus;
    }

    /**
     * @return app_member.backwater_status: 返水状态: 0,不允许;1,允许
     *
     * @mbggenerated
     */
    public Integer getBackwaterStatus() {
        return backwaterStatus;
    }

    /**
     * 字段: app_member.backwater_status<br/>
     * 必填: false<br/>
     * 缺省: 1<br/>
     * 长度: 10<br/>
     * 说明: 返水状态: 0,不允许;1,允许
     *
     * @mbggenerated
     */
    public void setBackwaterStatus(Integer backwaterStatus) {
        this.backwaterStatus = backwaterStatus;
    }

    /**
     * @return app_member.chat_status: 聊天状态: 0,不允许;1,允许
     *
     * @mbggenerated
     */
    public Integer getChatStatus() {
        return chatStatus;
    }

    /**
     * 字段: app_member.chat_status<br/>
     * 必填: false<br/>
     * 缺省: 1<br/>
     * 长度: 10<br/>
     * 说明: 聊天状态: 0,不允许;1,允许
     *
     * @mbggenerated
     */
    public void setChatStatus(Integer chatStatus) {
        this.chatStatus = chatStatus;
    }

    /**
     * @return app_member.share_order_status: 晒单状态: 0,不允许;1,允许
     *
     * @mbggenerated
     */
    public Integer getShareOrderStatus() {
        return shareOrderStatus;
    }

    /**
     * 字段: app_member.share_order_status<br/>
     * 必填: false<br/>
     * 缺省: 1<br/>
     * 长度: 10<br/>
     * 说明: 晒单状态: 0,不允许;1,允许
     *
     * @mbggenerated
     */
    public void setShareOrderStatus(Integer shareOrderStatus) {
        this.shareOrderStatus = shareOrderStatus;
    }

    /**
     * @return app_member.payment_status: 充值状态0禁止充值1允许充值
     *
     * @mbggenerated
     */
    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * 字段: app_member.payment_status<br/>
     * 必填: false<br/>
     * 缺省: 1<br/>
     * 长度: 10<br/>
     * 说明: 充值状态0禁止充值1允许充值
     *
     * @mbggenerated
     */
    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * @return app_member.withdraw_status: 提现状态0禁止1允许
     *
     * @mbggenerated
     */
    public Integer getWithdrawStatus() {
        return withdrawStatus;
    }

    /**
     * 字段: app_member.withdraw_status<br/>
     * 必填: false<br/>
     * 缺省: 1<br/>
     * 长度: 10<br/>
     * 说明: 提现状态0禁止1允许
     *
     * @mbggenerated
     */
    public void setWithdrawStatus(Integer withdrawStatus) {
        this.withdrawStatus = withdrawStatus;
    }

    /**
     * @return app_member.freeze_status: 冻结状态: 0,不冻结;1,冻结
     *
     * @mbggenerated
     */
    public Integer getFreezeStatus() {
        return freezeStatus;
    }

    /**
     * 字段: app_member.freeze_status<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 冻结状态: 0,不冻结;1,冻结
     *
     * @mbggenerated
     */
    public void setFreezeStatus(Integer freezeStatus) {
        this.freezeStatus = freezeStatus;
    }

    /**
     * @return app_member.address: 登录地址
     *
     * @mbggenerated
     */
    public String getAddress() {
        return address;
    }

    /**
     * 字段: app_member.address<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 256<br/>
     * 说明: 登录地址
     *
     * @mbggenerated
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return app_member.login_ip: 登录ip
     *
     * @mbggenerated
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * 字段: app_member.login_ip<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 128<br/>
     * 说明: 登录ip
     *
     * @mbggenerated
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    /**
     * @return app_member.lhcxs_status: 
     *
     * @mbggenerated
     */
    public Integer getLhcxsStatus() {
        return lhcxsStatus;
    }

    /**
     * 字段: app_member.lhcxs_status<br/>
     * 必填: true<br/>
     * 缺省: 1<br/>
     * 长度: 10<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    public void setLhcxsStatus(Integer lhcxsStatus) {
        this.lhcxsStatus = lhcxsStatus;
    }

    /**
     * @return app_member.deleted: 是否删除
     *
     * @mbggenerated
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * 字段: app_member.deleted<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 是否删除
     *
     * @mbggenerated
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_member
     *
     * @mbggenerated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AppMember other = (AppMember) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAccount() == null ? other.getAccount() == null : this.getAccount().equals(other.getAccount()))
            && (this.getPayPassword() == null ? other.getPayPassword() == null : this.getPayPassword().equals(other.getPayPassword()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getSalt() == null ? other.getSalt() == null : this.getSalt().equals(other.getSalt()))
            && (this.getQq() == null ? other.getQq() == null : this.getQq().equals(other.getQq()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getHeads() == null ? other.getHeads() == null : this.getHeads().equals(other.getHeads()))
            && (this.getRealName() == null ? other.getRealName() == null : this.getRealName().equals(other.getRealName()))
            && (this.getVipId() == null ? other.getVipId() == null : this.getVipId().equals(other.getVipId()))
            && (this.getTopUpGradeId() == null ? other.getTopUpGradeId() == null : this.getTopUpGradeId().equals(other.getTopUpGradeId()))
            && (this.getBetAmount() == null ? other.getBetAmount() == null : this.getBetAmount().equals(other.getBetAmount()))
            && (this.getPayAmount() == null ? other.getPayAmount() == null : this.getPayAmount().equals(other.getPayAmount()))
            && (this.getWithdrawalAmount() == null ? other.getWithdrawalAmount() == null : this.getWithdrawalAmount().equals(other.getWithdrawalAmount()))
            && (this.getNoWithdrawalAmount() == null ? other.getNoWithdrawalAmount() == null : this.getNoWithdrawalAmount().equals(other.getNoWithdrawalAmount()))
            && (this.getBalance() == null ? other.getBalance() == null : this.getBalance().equals(other.getBalance()))
            && (this.getIdCard() == null ? other.getIdCard() == null : this.getIdCard().equals(other.getIdCard()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getBirthday() == null ? other.getBirthday() == null : this.getBirthday().equals(other.getBirthday()))
            && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
            && (this.getPromotionCode() == null ? other.getPromotionCode() == null : this.getPromotionCode().equals(other.getPromotionCode()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getRegisterIp() == null ? other.getRegisterIp() == null : this.getRegisterIp().equals(other.getRegisterIp()))
            && (this.getRegisterAddress() == null ? other.getRegisterAddress() == null : this.getRegisterAddress().equals(other.getRegisterAddress()))
            && (this.getShareAccount() == null ? other.getShareAccount() == null : this.getShareAccount().equals(other.getShareAccount()))
            && (this.getLoginType() == null ? other.getLoginType() == null : this.getLoginType().equals(other.getLoginType()))
            && (this.getLoginTime() == null ? other.getLoginTime() == null : this.getLoginTime().equals(other.getLoginTime()))
            && (this.getOpenid() == null ? other.getOpenid() == null : this.getOpenid().equals(other.getOpenid()))
            && (this.getToken() == null ? other.getToken() == null : this.getToken().equals(other.getToken()))
            && (this.getTokenTime() == null ? other.getTokenTime() == null : this.getTokenTime().equals(other.getTokenTime()))
            && (this.getBetStatus() == null ? other.getBetStatus() == null : this.getBetStatus().equals(other.getBetStatus()))
            && (this.getBackwaterStatus() == null ? other.getBackwaterStatus() == null : this.getBackwaterStatus().equals(other.getBackwaterStatus()))
            && (this.getChatStatus() == null ? other.getChatStatus() == null : this.getChatStatus().equals(other.getChatStatus()))
            && (this.getShareOrderStatus() == null ? other.getShareOrderStatus() == null : this.getShareOrderStatus().equals(other.getShareOrderStatus()))
            && (this.getPaymentStatus() == null ? other.getPaymentStatus() == null : this.getPaymentStatus().equals(other.getPaymentStatus()))
            && (this.getWithdrawStatus() == null ? other.getWithdrawStatus() == null : this.getWithdrawStatus().equals(other.getWithdrawStatus()))
            && (this.getFreezeStatus() == null ? other.getFreezeStatus() == null : this.getFreezeStatus().equals(other.getFreezeStatus()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getLoginIp() == null ? other.getLoginIp() == null : this.getLoginIp().equals(other.getLoginIp()))
            && (this.getLhcxsStatus() == null ? other.getLhcxsStatus() == null : this.getLhcxsStatus().equals(other.getLhcxsStatus()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_member
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAccount() == null) ? 0 : getAccount().hashCode());
        result = prime * result + ((getPayPassword() == null) ? 0 : getPayPassword().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getSalt() == null) ? 0 : getSalt().hashCode());
        result = prime * result + ((getQq() == null) ? 0 : getQq().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getHeads() == null) ? 0 : getHeads().hashCode());
        result = prime * result + ((getRealName() == null) ? 0 : getRealName().hashCode());
        result = prime * result + ((getVipId() == null) ? 0 : getVipId().hashCode());
        result = prime * result + ((getTopUpGradeId() == null) ? 0 : getTopUpGradeId().hashCode());
        result = prime * result + ((getBetAmount() == null) ? 0 : getBetAmount().hashCode());
        result = prime * result + ((getPayAmount() == null) ? 0 : getPayAmount().hashCode());
        result = prime * result + ((getWithdrawalAmount() == null) ? 0 : getWithdrawalAmount().hashCode());
        result = prime * result + ((getNoWithdrawalAmount() == null) ? 0 : getNoWithdrawalAmount().hashCode());
        result = prime * result + ((getBalance() == null) ? 0 : getBalance().hashCode());
        result = prime * result + ((getIdCard() == null) ? 0 : getIdCard().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getBirthday() == null) ? 0 : getBirthday().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getPromotionCode() == null) ? 0 : getPromotionCode().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getRegisterIp() == null) ? 0 : getRegisterIp().hashCode());
        result = prime * result + ((getRegisterAddress() == null) ? 0 : getRegisterAddress().hashCode());
        result = prime * result + ((getShareAccount() == null) ? 0 : getShareAccount().hashCode());
        result = prime * result + ((getLoginType() == null) ? 0 : getLoginType().hashCode());
        result = prime * result + ((getLoginTime() == null) ? 0 : getLoginTime().hashCode());
        result = prime * result + ((getOpenid() == null) ? 0 : getOpenid().hashCode());
        result = prime * result + ((getToken() == null) ? 0 : getToken().hashCode());
        result = prime * result + ((getTokenTime() == null) ? 0 : getTokenTime().hashCode());
        result = prime * result + ((getBetStatus() == null) ? 0 : getBetStatus().hashCode());
        result = prime * result + ((getBackwaterStatus() == null) ? 0 : getBackwaterStatus().hashCode());
        result = prime * result + ((getChatStatus() == null) ? 0 : getChatStatus().hashCode());
        result = prime * result + ((getShareOrderStatus() == null) ? 0 : getShareOrderStatus().hashCode());
        result = prime * result + ((getPaymentStatus() == null) ? 0 : getPaymentStatus().hashCode());
        result = prime * result + ((getWithdrawStatus() == null) ? 0 : getWithdrawStatus().hashCode());
        result = prime * result + ((getFreezeStatus() == null) ? 0 : getFreezeStatus().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getLoginIp() == null) ? 0 : getLoginIp().hashCode());
        result = prime * result + ((getLhcxsStatus() == null) ? 0 : getLhcxsStatus().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_member
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", account=").append(account);
        sb.append(", payPassword=").append(payPassword);
        sb.append(", password=").append(password);
        sb.append(", salt=").append(salt);
        sb.append(", qq=").append(qq);
        sb.append(", sex=").append(sex);
        sb.append(", heads=").append(heads);
        sb.append(", realName=").append(realName);
        sb.append(", vipId=").append(vipId);
        sb.append(", topUpGradeId=").append(topUpGradeId);
        sb.append(", betAmount=").append(betAmount);
        sb.append(", payAmount=").append(payAmount);
        sb.append(", withdrawalAmount=").append(withdrawalAmount);
        sb.append(", noWithdrawalAmount=").append(noWithdrawalAmount);
        sb.append(", balance=").append(balance);
        sb.append(", idCard=").append(idCard);
        sb.append(", phone=").append(phone);
        sb.append(", birthday=").append(birthday);
        sb.append(", nickname=").append(nickname);
        sb.append(", promotionCode=").append(promotionCode);
        sb.append(", createTime=").append(createTime);
        sb.append(", registerIp=").append(registerIp);
        sb.append(", registerAddress=").append(registerAddress);
        sb.append(", shareAccount=").append(shareAccount);
        sb.append(", loginType=").append(loginType);
        sb.append(", loginTime=").append(loginTime);
        sb.append(", openid=").append(openid);
        sb.append(", token=").append(token);
        sb.append(", tokenTime=").append(tokenTime);
        sb.append(", betStatus=").append(betStatus);
        sb.append(", backwaterStatus=").append(backwaterStatus);
        sb.append(", chatStatus=").append(chatStatus);
        sb.append(", shareOrderStatus=").append(shareOrderStatus);
        sb.append(", paymentStatus=").append(paymentStatus);
        sb.append(", withdrawStatus=").append(withdrawStatus);
        sb.append(", freezeStatus=").append(freezeStatus);
        sb.append(", address=").append(address);
        sb.append(", loginIp=").append(loginIp);
        sb.append(", lhcxsStatus=").append(lhcxsStatus);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}