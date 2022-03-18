package com.caipiao.core.library.dto.lotterymanage;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.caipiao.core.library.tool.TimeHelper;
import com.mapper.domain.LhcXsRecommendFollow;

/**
 * 心水推荐关注
 **/
public class LhcXsRecommendFollowDTO {

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

	// 贴子Id
	private Integer recommendId;

	private List<Integer> recommendIdList;

	public List<Integer> getRecommendIdList() {
		return recommendIdList;
	}

	public void setRecommendIdList(List<Integer> recommendIdList) {
		this.recommendIdList = recommendIdList;
	}

	public Integer getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
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

	public LhcXsRecommendFollowDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LhcXsRecommendFollow getLhcXsRecommendFollow() {
		LhcXsRecommendFollow lhcXsRecommendComment = new LhcXsRecommendFollow();
		BeanUtils.copyProperties(this, lhcXsRecommendComment);
//        if (this.id == null) {
//            lhcXsRecommend.setCreateTime(TimeHelper.date());
//        }
		if (null != this.getCreateTime()) {
			lhcXsRecommendComment.setCreateTime(this.getCreateTime());
		} else {
			lhcXsRecommendComment.setCreateTime(TimeHelper.date());
		}

		return lhcXsRecommendComment;
	}

}
