package com.caipiao.core.library.vo.system;

import com.caipiao.core.library.tool.TimeHelper;

/**
 * 角色资源VO
 * @author admin
 * @date 2017年9月21日 上午10:06:15
 *
 */
public class RolesVO {
    
    //角色id
    private int id;
    
    //角色名字
    private String name;
    
    //角色备注名
    private String remark;
    
    //状态：'1:删除，0未删除'
    private int isDelete;
    
    //创建时间
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
