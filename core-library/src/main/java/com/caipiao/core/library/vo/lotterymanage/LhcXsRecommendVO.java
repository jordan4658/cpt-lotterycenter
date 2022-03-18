package com.caipiao.core.library.vo.lotterymanage;

import com.caipiao.core.library.dto.lotterymanage.LhcXsRecommendContentDTO;

import java.util.List;

/**
 * @author lzy
 * @create 2018-09-08 16:34
 **/
public class LhcXsRecommendVO {
	
	private Integer godtype;
	
	

    public Integer getGodtype() {
		return godtype;
	}

	public void setGodtype(Integer godtype) {
		this.godtype = godtype;
	}

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
     * 说明: 评论数
     */
    private Integer commentCount;
    /**
     * 说明: 创建时间
     */
    private String createTime;
    
    
    
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
     * 说明: 审核状态   未审核 审核通过  审核拒绝   中文返回
     */
    private String auditStatus_desc;

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

    /**
     * 说明: 推荐内容
     */
    private List<List<LhcXsRecommendContentDTO>> contentDTOLists;

    /**
     * 说明: 推荐内容拼接后的字符串
     */
    private String contentListStr;
    
    
    
    
    public Integer getReferrerId() {
		return referrerId;
	}

	public void setReferrerId(Integer referrerId) {
		this.referrerId = referrerId;
	}

	public Integer getTotalAdmire() {
		return totalAdmire;
	}

	public void setTotalAdmire(Integer totalAdmire) {
		this.totalAdmire = totalAdmire;
	}


	public String getAuditStatus_desc() {
		return auditStatus_desc;
	}

	public void setAuditStatus_desc(String auditStatus_desc) {
		this.auditStatus_desc = auditStatus_desc;
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

	public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<List<LhcXsRecommendContentDTO>> getContentDTOLists() {
        return contentDTOLists;
    }

    public void setContentDTOLists(List<List<LhcXsRecommendContentDTO>> contentDTOLists) {
        this.contentDTOLists = contentDTOLists;
    }

    public String getContentListStr() {
        return contentListStr;
    }

    public void setContentListStr(String contentListStr) {
        this.contentListStr = contentListStr;
    }

	public Integer getQrShow() {
		return qrShow;
	}

	public void setQrShow(Integer qrShow) {
		this.qrShow = qrShow;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
    
    
}
