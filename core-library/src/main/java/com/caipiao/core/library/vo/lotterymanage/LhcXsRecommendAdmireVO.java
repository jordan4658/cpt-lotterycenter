package com.caipiao.core.library.vo.lotterymanage;



/**推荐点赞
 * @author lzy
 * @create 2018-09-08 16:34
 **/
public class LhcXsRecommendAdmireVO {
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
     * 字段: lhc_xs_recommend_comment.times<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 次数
     *
     * @mbggenerated
     */
    private Integer times;
    
	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
