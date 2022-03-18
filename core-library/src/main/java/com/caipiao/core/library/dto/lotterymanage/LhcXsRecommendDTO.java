package com.caipiao.core.library.dto.lotterymanage;

import com.caipiao.core.library.tool.TimeHelper;
import com.mapper.domain.LhcXsRecommend;

import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 六合彩心水推荐
 * @author lzy
 * @create 2018-09-08 15:18
 **/
public class LhcXsRecommendDTO {

    private Integer id;

    /**
     * 说明: 操作员账号
     */
    private String operater;

    /**
     * 说明: 序号
     */
    private Integer sort;

    /**
     * 说明: 推荐人
     */
    private String referrer;
    
    
    /**
     * 说明: 推荐人Id
     */
    private Integer referrerId;

    /**
     * 说明: 类型
     */
    private String type;

    /**
     * 说明: 标题
     */
    private String title;

    /**
     * 说明: 正文
     */
    private String content;
    
    /**
     * 说明: 二维码是否显示
     */
    private Integer qrShow;

    /**
     * 说明: 真实的阅读数
     */
    private Integer realViews;

    /**
     * 说明: 增加的阅读数
     */
    private Integer riseViews;
    
    /**
     * 说明: 真实的点赞数
     */
    private Integer realAdmire;

    /**
     * 说明: 增加的点赞数
     */
    private Integer riseAdmire;
    
    
    /**
     * 说明: 总点赞数
     */
    private Integer totalAdmire;
    
    
    /**
     * 说明: 起点Id
     */
    private Integer startId;
    
    //大神类型
    private Integer godtype;
    
    //头像
    private String heads;

    
	public String getHeads() {
		return heads;
	}

	public void setHeads(String heads) {
		this.heads = heads;
	}

	public Integer getGodtype() {
		return godtype;
	}

	public void setGodtype(Integer godtype) {
		this.godtype = godtype;
	}

	/**
     * 说明: 评论数
     */
    private Integer commentCount;
    
    private String commentTime;//最新的评论时间
    
    /**
     * 字段: lhc_xs_recommend.createTime<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    private String createTime;
    
    
    /**
     * 数据是否是追加关系
     */
    private Integer isAppend;
    
    /**
     * 说明: 用戶Id
     */
    private Integer memberId;
    /**
     * 说明: 0否 1.是 默认0
     */
    private Integer locked;
    /**
     * 说明: 数据来源  1.普通用户，2.后台用户
     */
    private Integer dataSources;
    /**
     * 说明: 审核状态  1.未审核 2.审核通过  3.审核拒绝
     */
    private Integer auditStatus;

    /**
     * 说明: 推荐内容
     */
    private List<LhcXsRecommendContentDTO> contentDTOList;

    
    
    
    public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}

	public Integer getReferrerId() {
		return referrerId;
	}

	public void setReferrerId(Integer referrerId) {
		this.referrerId = referrerId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public Integer getDataSources() {
		return dataSources;
	}

	public void setDataSources(Integer dataSources) {
		this.dataSources = dataSources;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public LhcXsRecommend getLhcXsRecommend() {
        LhcXsRecommend lhcXsRecommend = new LhcXsRecommend();
        BeanUtils.copyProperties(this, lhcXsRecommend);
//        if (this.id == null) {
//            lhcXsRecommend.setCreateTime(TimeHelper.date());
//        }
        	 if(null!=this.getCreateTime()){
        		 lhcXsRecommend.setCreateTime(this.getCreateTime());
             }else{
            	 lhcXsRecommend.setCreateTime(TimeHelper.date());
             }
       
        return lhcXsRecommend;
    }
	
	

    public Integer getStartId() {
		return startId;
	}

	public void setStartId(Integer startId) {
		this.startId = startId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperater() {
        return operater;
    }

    public void setOperater(String operater) {
        this.operater = operater;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRealViews() {
        return realViews;
    }

    public void setRealViews(Integer realViews) {
        this.realViews = realViews;
    }

    public Integer getRiseViews() {
        return riseViews;
    }

    public void setRiseViews(Integer riseViews) {
        this.riseViews = riseViews;
    }

    public Integer getRealAdmire() {
		return realAdmire;
	}

	public void setRealAdmire(Integer realAdmire) {
		this.realAdmire = realAdmire;
	}

	public Integer getRiseAdmire() {
		return riseAdmire;
	}

	public void setRiseAdmire(Integer riseAdmire) {
		this.riseAdmire = riseAdmire;
	}

	public List<LhcXsRecommendContentDTO> getContentDTOList() {
        return contentDTOList;
    }

    public void setContentDTOList(List<LhcXsRecommendContentDTO> contentDTOList) {
        this.contentDTOList = contentDTOList;
    }

	public Integer getQrShow() {
		return qrShow;
	}

	public void setQrShow(Integer qrShow) {
		this.qrShow = qrShow;
	}

	public Integer getIsAppend() {
		return isAppend;
	}

	public void setIsAppend(Integer isAppend) {
		this.isAppend = isAppend;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getTotalAdmire() {
		return totalAdmire;
	}

	public void setTotalAdmire(Integer totalAdmire) {
		this.totalAdmire = totalAdmire;
	}

	public LhcXsRecommendDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
