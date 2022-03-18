package com.caipiao.core.library.vo.lotterymanage;



/**
 * @author lzy
 * @create 2018-09-08 16:34
 **/
public class LhcXsRecommendCommentVO {
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
     * 字段: lhc_xs_recommend_comment.issue<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 内容
     *
     * @mbggenerated
     */
    private String content;

    /**
     * 字段: lhc_xs_recommend_comment.recommend_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 推荐正文id
     *
     * @mbggenerated
     */
    private Integer recommendId;

    /**
     * 字段: lhc_xs_recommend_comment.recommend_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 用户id
     *
     * @mbggenerated
     */
    private Integer memberId;
    /**
     * 字段: lhc_xs_recommend_comment.parentId<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 回复评论Id
     *
     * @mbggenerated
     */
    private Integer parentId;
    

    /**
     * 字段: lhc_xs_recommend_comment.create_time<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    private String createTime;
    
    /**
     * 字段: lhc_xs_recommend_comment.nick<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 用户昵称
     *
     * @mbggenerated
     */
    private String nickname;
    
    
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
     * 字段: lhc_xs_recommend_comment.deleted<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 是否删除
     *
     * @mbggenerated
     */
    private Integer deleted;
    
    private String heads;//昵称
    
    private String title;//标题
    
    private Integer referrerId;//后台心水推荐用户
    
    private Integer recommendReferrerId;//后台心水推荐发帖用户

	/**
	 * 说明: 心水推荐人封禁状态 0封禁1解封2不是app用户不显示
	 */
	private String xsfj;

	public String getXsfj() {
		return xsfj;
	}

	public void setXsfj(String xsfj) {
		this.xsfj = xsfj;
	}
	public Integer getReferrerId() {
		return referrerId;
	}

	public void setReferrerId(Integer referrerId) {
		this.referrerId = referrerId;
	}

	public Integer getRecommendReferrerId() {
		return recommendReferrerId;
	}

	public void setRecommendReferrerId(Integer recommendReferrerId) {
		this.recommendReferrerId = recommendReferrerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHeads() {
		return heads;
	}

	public void setHeads(String heads) {
		this.heads = heads;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
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

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
    

}
