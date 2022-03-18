package com.mapper.domain;

import java.io.Serializable;

public class AdminAppMember implements Serializable {
    /**
	 * 
	 */
   private static final long serialVersionUID = 1L;

	/**
     * 字段: lhc_xs_recommend_comment.id<br/>
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
     * 字段: app_member.memberId<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 用户id
     *
     * @mbggenerated
     */
    private Integer memberId;
    
    private String  createTime;
    
    

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
	
	

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_comment
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMemberId() == null) ? 0 : getMemberId().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_comment
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
        sb.append("]");
        return sb.toString();
    }
}