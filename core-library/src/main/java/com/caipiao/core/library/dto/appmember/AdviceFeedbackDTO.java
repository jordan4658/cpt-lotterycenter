package com.caipiao.core.library.dto.appmember;

import java.util.List;

/**
 * @author TFG
 * @create 2018-12-05 15:52
 **/
public class AdviceFeedbackDTO {
	 private Integer id;

	    /**
	     * 字段: advice_feedback.account<br/>
	     * 必填: false<br/>
	     * 缺省: <br/>
	     * 长度: 255<br/>
	     * 说明: 会员账号
	     *
	     * @mbggenerated
	     */
	    private String account;

	    /**
	     * 字段: advice_feedback.member_id<br/>
	     * 必填: false<br/>
	     * 缺省: <br/>
	     * 长度: 10<br/>
	     * 说明: 会员id
	     *
	     * @mbggenerated
	     */
	    private Integer memberId;

	    /**
	     * 字段: advice_feedback.client<br/>
	     * 必填: false<br/>
	     * 缺省: <br/>
	     * 长度: 255<br/>
	     * 说明: 客户端
	     *
	     * @mbggenerated
	     */
	    private String client;

	    /**
	     * 字段: advice_feedback.problem<br/>
	     * 必填: false<br/>
	     * 缺省: <br/>
	     * 长度: 255<br/>
	     * 说明: 问题
	     *
	     * @mbggenerated
	     */
	    private String problem;

	    /**
	     * 字段: advice_feedback.message<br/>
	     * 必填: false<br/>
	     * 缺省: <br/>
	     * 长度: 255<br/>
	     * 说明: 内容
	     *
	     * @mbggenerated
	     */
	    private String message;

	    /**
	     * 字段: advice_feedback.create_time<br/>
	     * 必填: false<br/>
	     * 缺省: <br/>
	     * 长度: 20<br/>
	     * 说明: 创建时间
	     *
	     * @mbggenerated
	     */
	    private String createTime;
	    private List<String> photo;
	    private String type;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getAccount() {
			return account;
		}
		public void setAccount(String account) {
			this.account = account;
		}
		public Integer getMemberId() {
			return memberId;
		}
		public void setMemberId(Integer memberId) {
			this.memberId = memberId;
		}
		public String getClient() {
			return client;
		}
		public void setClient(String client) {
			this.client = client;
		}
		public String getProblem() {
			return problem;
		}
		public void setProblem(String problem) {
			this.problem = problem;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getCreateTime() {
			return createTime;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
		public List<String> getPhoto() {
			return photo;
		}
		public void setPhoto(List<String> photo) {
			this.photo = photo;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	    
}
