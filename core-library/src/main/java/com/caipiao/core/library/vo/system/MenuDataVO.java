package com.caipiao.core.library.vo.system;

/**
 * 菜单数据VO
 * @author admin
 * @date 2017年10月11日 下午5:51:26
 *
 */
public class MenuDataVO {
    
    //菜单id
    private int f_ModuleId;
    //父级菜单id
    private int f_ParentId;
    //菜单名字
    private String f_FullName;
    //菜单图标
    private String f_Icon;
    //url
    private String f_UrlAddress;
    
    public int getF_ModuleId() {
        return f_ModuleId;
    }
    public void setF_ModuleId(int f_ModuleId) {
        this.f_ModuleId = f_ModuleId;
    }
    public int getF_ParentId() {
        return f_ParentId;
    }
    public void setF_ParentId(int f_ParentId) {
        this.f_ParentId = f_ParentId;
    }
    public String getF_FullName() {
        return f_FullName;
    }
    public void setF_FullName(String f_FullName) {
        this.f_FullName = f_FullName;
    }
    public String getF_Icon() {
        return f_Icon;
    }
    public void setF_Icon(String f_Icon) {
        this.f_Icon = f_Icon;
    }
    public String getF_UrlAddress() {
        return f_UrlAddress;
    }
    public void setF_UrlAddress(String f_UrlAddress) {
        this.f_UrlAddress = f_UrlAddress;
    }

}
