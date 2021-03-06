package com.mapper.domain;

import java.io.Serializable;

public class LhcXsRecommendFollow implements Serializable {
    /**
     * 字段: lhc_xs_recommend_follow.id<br/>
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
     * 字段: lhc_xs_recommend_follow.recommend_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 用户id
     *
     * @mbggenerated
     */
    private Integer memberId;
    
    
    /**
     * 字段: lhc_xs_recommend_follow.referrer_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 关注用户id
     *
     * @mbggenerated
     */
    private Integer referrerId;
    
    /**
     * 字段: lhc_xs_recommend_follow.parent_Member_Id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 后台用户id
     *
     * @mbggenerated
     */
    private Integer parentMemberId;
    

    /**
     * 字段: lhc_xs_recommend_follow.create_time<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    private String createTime;

    /**
     * 字段: lhc_xs_recommend_follow.deleted<br/>
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
     * This field corresponds to the database table lhc_xs_recommend_follow
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    
    
    

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getReferrerId() {
		return referrerId;
	}

	public void setReferrerId(Integer referrerId) {
		this.referrerId = referrerId;
	}

	public Integer getParentMemberId() {
		return parentMemberId;
	}

	public void setParentMemberId(Integer parentMemberId) {
		this.parentMemberId = parentMemberId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_follow
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMemberId() == null) ? 0 : getMemberId().hashCode());
        result = prime * result + ((getParentMemberId() == null) ? 0 : getParentMemberId().hashCode());
        result = prime * result + ((getReferrerId() == null) ? 0 : getReferrerId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_follow
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
        sb.append(", memberId=").append(memberId);
        sb.append(", parentMemberId=").append(parentMemberId);
        sb.append(", referrerId=").append(referrerId);
        sb.append(", createTime=").append(createTime);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}