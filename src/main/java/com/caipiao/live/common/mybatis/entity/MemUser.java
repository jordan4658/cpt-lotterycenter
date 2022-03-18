package com.caipiao.live.common.mybatis.entity;

import java.io.Serializable;
import java.util.Date;

public class MemUser implements Serializable {
    /**
     * 字段: mem_user.id<br/>
     * 主键: 自动增长<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 字段: mem_user.password<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 64<br/>
     * 说明: 登录密码（用户与主播共用）
     *
     * @mbggenerated
     */
    private String password;

    /**
     * 字段: mem_user.salt<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 32<br/>
     * 说明: 密码盐值（用户与主播共用）
     *
     * @mbggenerated
     */
    private String salt;

    /**
     * 字段: mem_user.country_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 所属地区id（用户与主播共用）
     *
     * @mbggenerated
     */
    private Long countryId;

    /**
     * 字段: mem_user.nick_name<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 昵称（用户与主播共用）
     *
     * @mbggenerated
     */
    private String nickName;

    /**
     * 字段: mem_user.remark<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 500<br/>
     * 说明: 备注（用户与主播共用）
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 字段: mem_user.sex<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 性别 0保密 1男 2女（用户与主播共用）
     *
     * @mbggenerated
     */
    private Integer sex;

    /**
     * 字段: mem_user.user_account<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 用户登录账号（用户与主播共用）
     *
     * @mbggenerated
     */
    private String userAccount;

    /**
     * 字段: mem_user.focus_num<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 关注人数（用户与主播共用）
     *
     * @mbggenerated
     */
    private Integer focusNum;

    /**
     * 字段: mem_user.fans_num<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 粉丝数（用户与主播共用）
     *
     * @mbggenerated
     */
    private Integer fansNum;

    /**
     * 字段: mem_user.is_frozen<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 说明: 是否冻结 0否1是（用户与主播共用）
     *
     * @mbggenerated
     */
    private Boolean isFrozen;

    /**
     * 字段: mem_user.user_level<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 用户等级（用户与主播共用）
     *
     * @mbggenerated
     */
    private Integer userLevel;

    /**
     * 字段: mem_user.avatar<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 128<br/>
     * 说明: 用户头像（相对路径）（用户与主播共用）
     *
     * @mbggenerated
     */
    private String avatar;

    /**
     * 字段: mem_user.is_anchor<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 说明: 是否是主播，0否1是，默认值是0
     *
     * @mbggenerated
     */
    private Boolean isAnchor;

    /**
     * 字段: mem_user.mobile_phone<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 注册的手机号
     *
     * @mbggenerated
     */
    private String mobilePhone;

    /**
     * 字段: mem_user.register_area_code<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 注册的区号
     *
     * @mbggenerated
     */
    private String registerAreaCode;

    /**
     * 字段: mem_user.accno<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 用户唯一标识
     *
     * @mbggenerated
     */
    private String accno;

    /**
     * 字段: mem_user.group_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 用户层级id
     *
     * @mbggenerated
     */
    private Long groupId;

    /**
     * 字段: mem_user.birthday<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 32<br/>
     * 说明: 生日日期
     *
     * @mbggenerated
     */
    private String birthday;

    /**
     * 字段: mem_user.register_ip<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 注册ip
     *
     * @mbggenerated
     */
    private String registerIp;

    /**
     * 字段: mem_user.register_time<br/>
     * 必填: false<br/>
     * 缺省: CURRENT_TIMESTAMP<br/>
     * 长度: 19<br/>
     * 说明: 注册时间
     *
     * @mbggenerated
     */
    private Date registerTime;

    /**
     * 字段: mem_user.created_by<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 32<br/>
     * 说明: 创建人(后台可创建用户)
     *
     * @mbggenerated
     */
    private String createdBy;

    /**
     * 字段: mem_user.register_area<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 注册地区
     *
     * @mbggenerated
     */
    private String registerArea;

    /**
     * 字段: mem_user.register_source<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 注册来源 ios、android、pc
     *
     * @mbggenerated
     */
    private String registerSource;

    /**
     * 字段: mem_user.register_device<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 100<br/>
     * 说明: 注册设备
     *
     * @mbggenerated
     */
    private String registerDevice;

    /**
     * 字段: mem_user.register_country_code<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 32<br/>
     * 说明: 注册时候的国家code值
     *
     * @mbggenerated
     */
    private String registerCountryCode;

    /**
     * 字段: mem_user.last_login_ip<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 最后登录ip
     *
     * @mbggenerated
     */
    private String lastLoginIp;

    /**
     * 字段: mem_user.last_login_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 最后登录时间
     *
     * @mbggenerated
     */
    private Date lastLoginTime;

    /**
     * 字段: mem_user.last_login_source<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 最后登录来源  ios、android、pc
     *
     * @mbggenerated
     */
    private String lastLoginSource;

    /**
     * 字段: mem_user.last_login_area<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 100<br/>
     * 说明: 最后登录地区
     *
     * @mbggenerated
     */
    private String lastLoginArea;

    /**
     * 字段: mem_user.last_login_device<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 100<br/>
     * 说明: 最后登录设备
     *
     * @mbggenerated
     */
    private String lastLoginDevice;

    /**
     * 字段: mem_user.is_commission<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 说明: 是否返点 0否1是
     *
     * @mbggenerated
     */
    private Boolean isCommission;

    /**
     * 字段: mem_user.is_bet<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 说明: 是否允许投注 0否1是
     *
     * @mbggenerated
     */
    private Boolean isBet;

    /**
     * 字段: mem_user.is_dispensing<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 说明: 是否允许出款 0否1是
     *
     * @mbggenerated
     */
    private Boolean isDispensing;

    /**
     * 字段: mem_user.is_super_live_manage<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 说明: 是否是直播超级管理员 0否1是
     *
     * @mbggenerated
     */
    private Boolean isSuperLiveManage;

    /**
     * 字段: mem_user.is_online<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 说明: 是否在线 0否1是
     *
     * @mbggenerated
     */
    private Boolean isOnline;

    /**
     * 字段: mem_user.update_by<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 32<br/>
     * 说明: 更新人
     *
     * @mbggenerated
     */
    private String updateBy;

    /**
     * 字段: mem_user.update_time<br/>
     * 必填: false<br/>
     * 缺省: CURRENT_TIMESTAMP<br/>
     * 长度: 19<br/>
     * 说明: 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 字段: mem_user.merchant_code<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 32<br/>
     * 说明: 商户code值，默认为0
     *
     * @mbggenerated
     */
    private String merchantCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table mem_user
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return mem_user.id: id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * 字段: mem_user.id<br/>
     * 主键: 自动增长<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return mem_user.password: 登录密码（用户与主播共用）
     *
     * @mbggenerated
     */
    public String getPassword() {
        return password;
    }

    /**
     * 字段: mem_user.password<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 64<br/>
     * 说明: 登录密码（用户与主播共用）
     *
     * @mbggenerated
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return mem_user.salt: 密码盐值（用户与主播共用）
     *
     * @mbggenerated
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 字段: mem_user.salt<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 32<br/>
     * 说明: 密码盐值（用户与主播共用）
     *
     * @mbggenerated
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * @return mem_user.country_id: 所属地区id（用户与主播共用）
     *
     * @mbggenerated
     */
    public Long getCountryId() {
        return countryId;
    }

    /**
     * 字段: mem_user.country_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 所属地区id（用户与主播共用）
     *
     * @mbggenerated
     */
    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    /**
     * @return mem_user.nick_name: 昵称（用户与主播共用）
     *
     * @mbggenerated
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 字段: mem_user.nick_name<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 昵称（用户与主播共用）
     *
     * @mbggenerated
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * @return mem_user.remark: 备注（用户与主播共用）
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 字段: mem_user.remark<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 500<br/>
     * 说明: 备注（用户与主播共用）
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return mem_user.sex: 性别 0保密 1男 2女（用户与主播共用）
     *
     * @mbggenerated
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 字段: mem_user.sex<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 性别 0保密 1男 2女（用户与主播共用）
     *
     * @mbggenerated
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * @return mem_user.user_account: 用户登录账号（用户与主播共用）
     *
     * @mbggenerated
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * 字段: mem_user.user_account<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 用户登录账号（用户与主播共用）
     *
     * @mbggenerated
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * @return mem_user.focus_num: 关注人数（用户与主播共用）
     *
     * @mbggenerated
     */
    public Integer getFocusNum() {
        return focusNum;
    }

    /**
     * 字段: mem_user.focus_num<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 关注人数（用户与主播共用）
     *
     * @mbggenerated
     */
    public void setFocusNum(Integer focusNum) {
        this.focusNum = focusNum;
    }

    /**
     * @return mem_user.fans_num: 粉丝数（用户与主播共用）
     *
     * @mbggenerated
     */
    public Integer getFansNum() {
        return fansNum;
    }

    /**
     * 字段: mem_user.fans_num<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 粉丝数（用户与主播共用）
     *
     * @mbggenerated
     */
    public void setFansNum(Integer fansNum) {
        this.fansNum = fansNum;
    }

    /**
     * @return mem_user.is_frozen: 是否冻结 0否1是（用户与主播共用）
     *
     * @mbggenerated
     */
    public Boolean getIsFrozen() {
        return isFrozen;
    }

    /**
     * 字段: mem_user.is_frozen<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 说明: 是否冻结 0否1是（用户与主播共用）
     *
     * @mbggenerated
     */
    public void setIsFrozen(Boolean isFrozen) {
        this.isFrozen = isFrozen;
    }

    /**
     * @return mem_user.user_level: 用户等级（用户与主播共用）
     *
     * @mbggenerated
     */
    public Integer getUserLevel() {
        return userLevel;
    }

    /**
     * 字段: mem_user.user_level<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 用户等级（用户与主播共用）
     *
     * @mbggenerated
     */
    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    /**
     * @return mem_user.avatar: 用户头像（相对路径）（用户与主播共用）
     *
     * @mbggenerated
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 字段: mem_user.avatar<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 128<br/>
     * 说明: 用户头像（相对路径）（用户与主播共用）
     *
     * @mbggenerated
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * @return mem_user.is_anchor: 是否是主播，0否1是，默认值是0
     *
     * @mbggenerated
     */
    public Boolean getIsAnchor() {
        return isAnchor;
    }

    /**
     * 字段: mem_user.is_anchor<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 说明: 是否是主播，0否1是，默认值是0
     *
     * @mbggenerated
     */
    public void setIsAnchor(Boolean isAnchor) {
        this.isAnchor = isAnchor;
    }

    /**
     * @return mem_user.mobile_phone: 注册的手机号
     *
     * @mbggenerated
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * 字段: mem_user.mobile_phone<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 注册的手机号
     *
     * @mbggenerated
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * @return mem_user.register_area_code: 注册的区号
     *
     * @mbggenerated
     */
    public String getRegisterAreaCode() {
        return registerAreaCode;
    }

    /**
     * 字段: mem_user.register_area_code<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 注册的区号
     *
     * @mbggenerated
     */
    public void setRegisterAreaCode(String registerAreaCode) {
        this.registerAreaCode = registerAreaCode;
    }

    /**
     * @return mem_user.accno: 用户唯一标识
     *
     * @mbggenerated
     */
    public String getAccno() {
        return accno;
    }

    /**
     * 字段: mem_user.accno<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 用户唯一标识
     *
     * @mbggenerated
     */
    public void setAccno(String accno) {
        this.accno = accno;
    }

    /**
     * @return mem_user.group_id: 用户层级id
     *
     * @mbggenerated
     */
    public Long getGroupId() {
        return groupId;
    }

    /**
     * 字段: mem_user.group_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 用户层级id
     *
     * @mbggenerated
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /**
     * @return mem_user.birthday: 生日日期
     *
     * @mbggenerated
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * 字段: mem_user.birthday<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 32<br/>
     * 说明: 生日日期
     *
     * @mbggenerated
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * @return mem_user.register_ip: 注册ip
     *
     * @mbggenerated
     */
    public String getRegisterIp() {
        return registerIp;
    }

    /**
     * 字段: mem_user.register_ip<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 注册ip
     *
     * @mbggenerated
     */
    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    /**
     * @return mem_user.register_time: 注册时间
     *
     * @mbggenerated
     */
    public Date getRegisterTime() {
        return registerTime;
    }

    /**
     * 字段: mem_user.register_time<br/>
     * 必填: false<br/>
     * 缺省: CURRENT_TIMESTAMP<br/>
     * 长度: 19<br/>
     * 说明: 注册时间
     *
     * @mbggenerated
     */
    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * @return mem_user.created_by: 创建人(后台可创建用户)
     *
     * @mbggenerated
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * 字段: mem_user.created_by<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 32<br/>
     * 说明: 创建人(后台可创建用户)
     *
     * @mbggenerated
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return mem_user.register_area: 注册地区
     *
     * @mbggenerated
     */
    public String getRegisterArea() {
        return registerArea;
    }

    /**
     * 字段: mem_user.register_area<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 注册地区
     *
     * @mbggenerated
     */
    public void setRegisterArea(String registerArea) {
        this.registerArea = registerArea;
    }

    /**
     * @return mem_user.register_source: 注册来源 ios、android、pc
     *
     * @mbggenerated
     */
    public String getRegisterSource() {
        return registerSource;
    }

    /**
     * 字段: mem_user.register_source<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 注册来源 ios、android、pc
     *
     * @mbggenerated
     */
    public void setRegisterSource(String registerSource) {
        this.registerSource = registerSource;
    }

    /**
     * @return mem_user.register_device: 注册设备
     *
     * @mbggenerated
     */
    public String getRegisterDevice() {
        return registerDevice;
    }

    /**
     * 字段: mem_user.register_device<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 100<br/>
     * 说明: 注册设备
     *
     * @mbggenerated
     */
    public void setRegisterDevice(String registerDevice) {
        this.registerDevice = registerDevice;
    }

    /**
     * @return mem_user.register_country_code: 注册时候的国家code值
     *
     * @mbggenerated
     */
    public String getRegisterCountryCode() {
        return registerCountryCode;
    }

    /**
     * 字段: mem_user.register_country_code<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 32<br/>
     * 说明: 注册时候的国家code值
     *
     * @mbggenerated
     */
    public void setRegisterCountryCode(String registerCountryCode) {
        this.registerCountryCode = registerCountryCode;
    }

    /**
     * @return mem_user.last_login_ip: 最后登录ip
     *
     * @mbggenerated
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * 字段: mem_user.last_login_ip<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 最后登录ip
     *
     * @mbggenerated
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    /**
     * @return mem_user.last_login_time: 最后登录时间
     *
     * @mbggenerated
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 字段: mem_user.last_login_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 最后登录时间
     *
     * @mbggenerated
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * @return mem_user.last_login_source: 最后登录来源  ios、android、pc
     *
     * @mbggenerated
     */
    public String getLastLoginSource() {
        return lastLoginSource;
    }

    /**
     * 字段: mem_user.last_login_source<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 最后登录来源  ios、android、pc
     *
     * @mbggenerated
     */
    public void setLastLoginSource(String lastLoginSource) {
        this.lastLoginSource = lastLoginSource;
    }

    /**
     * @return mem_user.last_login_area: 最后登录地区
     *
     * @mbggenerated
     */
    public String getLastLoginArea() {
        return lastLoginArea;
    }

    /**
     * 字段: mem_user.last_login_area<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 100<br/>
     * 说明: 最后登录地区
     *
     * @mbggenerated
     */
    public void setLastLoginArea(String lastLoginArea) {
        this.lastLoginArea = lastLoginArea;
    }

    /**
     * @return mem_user.last_login_device: 最后登录设备
     *
     * @mbggenerated
     */
    public String getLastLoginDevice() {
        return lastLoginDevice;
    }

    /**
     * 字段: mem_user.last_login_device<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 100<br/>
     * 说明: 最后登录设备
     *
     * @mbggenerated
     */
    public void setLastLoginDevice(String lastLoginDevice) {
        this.lastLoginDevice = lastLoginDevice;
    }

    /**
     * @return mem_user.is_commission: 是否返点 0否1是
     *
     * @mbggenerated
     */
    public Boolean getIsCommission() {
        return isCommission;
    }

    /**
     * 字段: mem_user.is_commission<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 说明: 是否返点 0否1是
     *
     * @mbggenerated
     */
    public void setIsCommission(Boolean isCommission) {
        this.isCommission = isCommission;
    }

    /**
     * @return mem_user.is_bet: 是否允许投注 0否1是
     *
     * @mbggenerated
     */
    public Boolean getIsBet() {
        return isBet;
    }

    /**
     * 字段: mem_user.is_bet<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 说明: 是否允许投注 0否1是
     *
     * @mbggenerated
     */
    public void setIsBet(Boolean isBet) {
        this.isBet = isBet;
    }

    /**
     * @return mem_user.is_dispensing: 是否允许出款 0否1是
     *
     * @mbggenerated
     */
    public Boolean getIsDispensing() {
        return isDispensing;
    }

    /**
     * 字段: mem_user.is_dispensing<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 说明: 是否允许出款 0否1是
     *
     * @mbggenerated
     */
    public void setIsDispensing(Boolean isDispensing) {
        this.isDispensing = isDispensing;
    }

    /**
     * @return mem_user.is_super_live_manage: 是否是直播超级管理员 0否1是
     *
     * @mbggenerated
     */
    public Boolean getIsSuperLiveManage() {
        return isSuperLiveManage;
    }

    /**
     * 字段: mem_user.is_super_live_manage<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 说明: 是否是直播超级管理员 0否1是
     *
     * @mbggenerated
     */
    public void setIsSuperLiveManage(Boolean isSuperLiveManage) {
        this.isSuperLiveManage = isSuperLiveManage;
    }

    /**
     * @return mem_user.is_online: 是否在线 0否1是
     *
     * @mbggenerated
     */
    public Boolean getIsOnline() {
        return isOnline;
    }

    /**
     * 字段: mem_user.is_online<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 说明: 是否在线 0否1是
     *
     * @mbggenerated
     */
    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    /**
     * @return mem_user.update_by: 更新人
     *
     * @mbggenerated
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 字段: mem_user.update_by<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 32<br/>
     * 说明: 更新人
     *
     * @mbggenerated
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * @return mem_user.update_time: 更新时间
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 字段: mem_user.update_time<br/>
     * 必填: false<br/>
     * 缺省: CURRENT_TIMESTAMP<br/>
     * 长度: 19<br/>
     * 说明: 更新时间
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return mem_user.merchant_code: 商户code值，默认为0
     *
     * @mbggenerated
     */
    public String getMerchantCode() {
        return merchantCode;
    }

    /**
     * 字段: mem_user.merchant_code<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 32<br/>
     * 说明: 商户code值，默认为0
     *
     * @mbggenerated
     */
    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mem_user
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
        MemUser other = (MemUser) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getSalt() == null ? other.getSalt() == null : this.getSalt().equals(other.getSalt()))
            && (this.getCountryId() == null ? other.getCountryId() == null : this.getCountryId().equals(other.getCountryId()))
            && (this.getNickName() == null ? other.getNickName() == null : this.getNickName().equals(other.getNickName()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getUserAccount() == null ? other.getUserAccount() == null : this.getUserAccount().equals(other.getUserAccount()))
            && (this.getFocusNum() == null ? other.getFocusNum() == null : this.getFocusNum().equals(other.getFocusNum()))
            && (this.getFansNum() == null ? other.getFansNum() == null : this.getFansNum().equals(other.getFansNum()))
            && (this.getIsFrozen() == null ? other.getIsFrozen() == null : this.getIsFrozen().equals(other.getIsFrozen()))
            && (this.getUserLevel() == null ? other.getUserLevel() == null : this.getUserLevel().equals(other.getUserLevel()))
            && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
            && (this.getIsAnchor() == null ? other.getIsAnchor() == null : this.getIsAnchor().equals(other.getIsAnchor()))
            && (this.getMobilePhone() == null ? other.getMobilePhone() == null : this.getMobilePhone().equals(other.getMobilePhone()))
            && (this.getRegisterAreaCode() == null ? other.getRegisterAreaCode() == null : this.getRegisterAreaCode().equals(other.getRegisterAreaCode()))
            && (this.getAccno() == null ? other.getAccno() == null : this.getAccno().equals(other.getAccno()))
            && (this.getGroupId() == null ? other.getGroupId() == null : this.getGroupId().equals(other.getGroupId()))
            && (this.getBirthday() == null ? other.getBirthday() == null : this.getBirthday().equals(other.getBirthday()))
            && (this.getRegisterIp() == null ? other.getRegisterIp() == null : this.getRegisterIp().equals(other.getRegisterIp()))
            && (this.getRegisterTime() == null ? other.getRegisterTime() == null : this.getRegisterTime().equals(other.getRegisterTime()))
            && (this.getCreatedBy() == null ? other.getCreatedBy() == null : this.getCreatedBy().equals(other.getCreatedBy()))
            && (this.getRegisterArea() == null ? other.getRegisterArea() == null : this.getRegisterArea().equals(other.getRegisterArea()))
            && (this.getRegisterSource() == null ? other.getRegisterSource() == null : this.getRegisterSource().equals(other.getRegisterSource()))
            && (this.getRegisterDevice() == null ? other.getRegisterDevice() == null : this.getRegisterDevice().equals(other.getRegisterDevice()))
            && (this.getRegisterCountryCode() == null ? other.getRegisterCountryCode() == null : this.getRegisterCountryCode().equals(other.getRegisterCountryCode()))
            && (this.getLastLoginIp() == null ? other.getLastLoginIp() == null : this.getLastLoginIp().equals(other.getLastLoginIp()))
            && (this.getLastLoginTime() == null ? other.getLastLoginTime() == null : this.getLastLoginTime().equals(other.getLastLoginTime()))
            && (this.getLastLoginSource() == null ? other.getLastLoginSource() == null : this.getLastLoginSource().equals(other.getLastLoginSource()))
            && (this.getLastLoginArea() == null ? other.getLastLoginArea() == null : this.getLastLoginArea().equals(other.getLastLoginArea()))
            && (this.getLastLoginDevice() == null ? other.getLastLoginDevice() == null : this.getLastLoginDevice().equals(other.getLastLoginDevice()))
            && (this.getIsCommission() == null ? other.getIsCommission() == null : this.getIsCommission().equals(other.getIsCommission()))
            && (this.getIsBet() == null ? other.getIsBet() == null : this.getIsBet().equals(other.getIsBet()))
            && (this.getIsDispensing() == null ? other.getIsDispensing() == null : this.getIsDispensing().equals(other.getIsDispensing()))
            && (this.getIsSuperLiveManage() == null ? other.getIsSuperLiveManage() == null : this.getIsSuperLiveManage().equals(other.getIsSuperLiveManage()))
            && (this.getIsOnline() == null ? other.getIsOnline() == null : this.getIsOnline().equals(other.getIsOnline()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getMerchantCode() == null ? other.getMerchantCode() == null : this.getMerchantCode().equals(other.getMerchantCode()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mem_user
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getSalt() == null) ? 0 : getSalt().hashCode());
        result = prime * result + ((getCountryId() == null) ? 0 : getCountryId().hashCode());
        result = prime * result + ((getNickName() == null) ? 0 : getNickName().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getUserAccount() == null) ? 0 : getUserAccount().hashCode());
        result = prime * result + ((getFocusNum() == null) ? 0 : getFocusNum().hashCode());
        result = prime * result + ((getFansNum() == null) ? 0 : getFansNum().hashCode());
        result = prime * result + ((getIsFrozen() == null) ? 0 : getIsFrozen().hashCode());
        result = prime * result + ((getUserLevel() == null) ? 0 : getUserLevel().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getIsAnchor() == null) ? 0 : getIsAnchor().hashCode());
        result = prime * result + ((getMobilePhone() == null) ? 0 : getMobilePhone().hashCode());
        result = prime * result + ((getRegisterAreaCode() == null) ? 0 : getRegisterAreaCode().hashCode());
        result = prime * result + ((getAccno() == null) ? 0 : getAccno().hashCode());
        result = prime * result + ((getGroupId() == null) ? 0 : getGroupId().hashCode());
        result = prime * result + ((getBirthday() == null) ? 0 : getBirthday().hashCode());
        result = prime * result + ((getRegisterIp() == null) ? 0 : getRegisterIp().hashCode());
        result = prime * result + ((getRegisterTime() == null) ? 0 : getRegisterTime().hashCode());
        result = prime * result + ((getCreatedBy() == null) ? 0 : getCreatedBy().hashCode());
        result = prime * result + ((getRegisterArea() == null) ? 0 : getRegisterArea().hashCode());
        result = prime * result + ((getRegisterSource() == null) ? 0 : getRegisterSource().hashCode());
        result = prime * result + ((getRegisterDevice() == null) ? 0 : getRegisterDevice().hashCode());
        result = prime * result + ((getRegisterCountryCode() == null) ? 0 : getRegisterCountryCode().hashCode());
        result = prime * result + ((getLastLoginIp() == null) ? 0 : getLastLoginIp().hashCode());
        result = prime * result + ((getLastLoginTime() == null) ? 0 : getLastLoginTime().hashCode());
        result = prime * result + ((getLastLoginSource() == null) ? 0 : getLastLoginSource().hashCode());
        result = prime * result + ((getLastLoginArea() == null) ? 0 : getLastLoginArea().hashCode());
        result = prime * result + ((getLastLoginDevice() == null) ? 0 : getLastLoginDevice().hashCode());
        result = prime * result + ((getIsCommission() == null) ? 0 : getIsCommission().hashCode());
        result = prime * result + ((getIsBet() == null) ? 0 : getIsBet().hashCode());
        result = prime * result + ((getIsDispensing() == null) ? 0 : getIsDispensing().hashCode());
        result = prime * result + ((getIsSuperLiveManage() == null) ? 0 : getIsSuperLiveManage().hashCode());
        result = prime * result + ((getIsOnline() == null) ? 0 : getIsOnline().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getMerchantCode() == null) ? 0 : getMerchantCode().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mem_user
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
        sb.append(", password=").append(password);
        sb.append(", salt=").append(salt);
        sb.append(", countryId=").append(countryId);
        sb.append(", nickName=").append(nickName);
        sb.append(", remark=").append(remark);
        sb.append(", sex=").append(sex);
        sb.append(", userAccount=").append(userAccount);
        sb.append(", focusNum=").append(focusNum);
        sb.append(", fansNum=").append(fansNum);
        sb.append(", isFrozen=").append(isFrozen);
        sb.append(", userLevel=").append(userLevel);
        sb.append(", avatar=").append(avatar);
        sb.append(", isAnchor=").append(isAnchor);
        sb.append(", mobilePhone=").append(mobilePhone);
        sb.append(", registerAreaCode=").append(registerAreaCode);
        sb.append(", accno=").append(accno);
        sb.append(", groupId=").append(groupId);
        sb.append(", birthday=").append(birthday);
        sb.append(", registerIp=").append(registerIp);
        sb.append(", registerTime=").append(registerTime);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", registerArea=").append(registerArea);
        sb.append(", registerSource=").append(registerSource);
        sb.append(", registerDevice=").append(registerDevice);
        sb.append(", registerCountryCode=").append(registerCountryCode);
        sb.append(", lastLoginIp=").append(lastLoginIp);
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", lastLoginSource=").append(lastLoginSource);
        sb.append(", lastLoginArea=").append(lastLoginArea);
        sb.append(", lastLoginDevice=").append(lastLoginDevice);
        sb.append(", isCommission=").append(isCommission);
        sb.append(", isBet=").append(isBet);
        sb.append(", isDispensing=").append(isDispensing);
        sb.append(", isSuperLiveManage=").append(isSuperLiveManage);
        sb.append(", isOnline=").append(isOnline);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", merchantCode=").append(merchantCode);
        sb.append("]");
        return sb.toString();
    }
}