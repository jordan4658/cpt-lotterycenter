package com.caipiao.core.library.vo.lotterymanage;

public class LhcXsRecommendFollowVO {

	private Integer pageNum; // 第几页

	private Integer pageSize; // 页大小

	private Integer count; // 通用型Integer参数

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

	private String nickname;// 昵称

	private String heads;// 昵称

	private Integer recommendId;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeads() {
		return heads;
	}

	public void setHeads(String heads) {
		this.heads = heads;
	}

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

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	LhcXsRecommendAppVO lhcXsRecommendAppVO;

	public LhcXsRecommendAppVO getLhcXsRecommendAppVO() {
		return lhcXsRecommendAppVO;
	}

	public void setLhcXsRecommendAppVO(LhcXsRecommendAppVO lhcXsRecommendAppVO) {
		this.lhcXsRecommendAppVO = lhcXsRecommendAppVO;
	}

	public Integer getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
	}

}
