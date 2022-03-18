package com.caipiao.core.library.dto.lotterymanage;

import org.springframework.beans.BeanUtils;

import com.caipiao.core.library.tool.TimeHelper;
import com.mapper.domain.LhcXsRecommendComment;


/**
 * 推荐评论
 **/
public class LhcXsRecommendCommentDTO {

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
     * 说明: 回复评论id
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
     * 字段: lhc_xs_recommend_comment.deleted<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 是否删除
     *
     * @mbggenerated
     */
    private Integer deleted;
    
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
     * 字段: lhc_xs_recommend_comment.referrerId<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 后台心水推荐用户
     *
     * @mbggenerated
     */
    private Integer referrerId;
    
    
	public Integer getReferrerId() {
		return referrerId;
	}

	public void setReferrerId(Integer referrerId) {
		this.referrerId = referrerId;
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
	

	public LhcXsRecommendCommentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LhcXsRecommendComment getLhcXsRecommendComment() {
		LhcXsRecommendComment lhcXsRecommendComment = new LhcXsRecommendComment();
        BeanUtils.copyProperties(this, lhcXsRecommendComment);
//        if (this.id == null) {
//            lhcXsRecommend.setCreateTime(TimeHelper.date());
//        }
        	 if(null!=this.getCreateTime()){
        		 lhcXsRecommendComment.setCreateTime(this.getCreateTime());
             }else{
            	 lhcXsRecommendComment.setCreateTime(TimeHelper.date());
             }
       
        return lhcXsRecommendComment;
    }
    
}
