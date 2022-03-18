package com.caipiao.core.library.dto.appmember;

/**
 * @author lzy
 * @create 2018-08-28 15:52
 **/
public class AppMemberDTO {

    private Integer id;

    /**
     * 昵称
     */
    private String nickname;
    /**
     * 性别
     */
    private Integer sex;

    public Integer getChatStatus() {
		return chatStatus;
	}

	public void setChatStatus(Integer chatStatus) {
		this.chatStatus = chatStatus;
	}

	public Integer getShareOrderStatus() {
		return shareOrderStatus;
	}

	public void setShareOrderStatus(Integer shareOrderStatus) {
		this.shareOrderStatus = shareOrderStatus;
	}

	/**
     * 说明: 会员头像
     */
    private String heads;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 生日
     */
    private String birthday;

    private String qq;
    private String  phone ;
    
    
    private Integer  betStatus ;
    private Integer freezeStatus ;
    private Integer lhcxsStatus ;
    
    private Integer  paymentStatus ;
    private Integer  withdrawStatus ;
    private Integer  chatStatus ;
    private Integer  shareOrderStatus ;
    private Integer topUpGradeId ;
    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	private String  password ;
	private String  paypassword ;
    public String getPaypassword() {
		return paypassword;
	}

	public void setPaypassword(String paypassword) {
		this.paypassword = paypassword;
	}

	
    
    

    public Integer getLhcxsStatus() {
		return lhcxsStatus;
	}

	public void setLhcxsStatus(Integer lhcxsStatus) {
		this.lhcxsStatus = lhcxsStatus;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getBetStatus() {
		return betStatus;
	}

	public void setBetStatus(Integer betStatus) {
		this.betStatus = betStatus;
	}

	public Integer getFreezeStatus() {
		return freezeStatus;
	}

	public void setFreezeStatus(Integer freezeStatus) {
		this.freezeStatus = freezeStatus;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Integer getWithdrawStatus() {
		return withdrawStatus;
	}

	public void setWithdrawStatus(Integer withdrawStatus) {
		this.withdrawStatus = withdrawStatus;
	}

	public Integer getTopUpGradeId() {
		return topUpGradeId;
	}

	public void setTopUpGradeId(Integer topUpGradeId) {
		this.topUpGradeId = topUpGradeId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getHeads() {
        return heads;
    }

    public void setHeads(String heads) {
        this.heads = heads;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
