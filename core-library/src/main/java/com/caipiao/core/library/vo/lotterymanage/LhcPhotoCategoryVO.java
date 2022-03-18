package com.caipiao.core.library.vo.lotterymanage;

/**
 * @author lzy
 * @create 2018-08-13 14:33
 **/
public class LhcPhotoCategoryVO {

    private Integer id;

    private String name;

    private Integer parentId;

    private String parentName;

    private String createTime;
    
    private Integer sort;
    

    public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
