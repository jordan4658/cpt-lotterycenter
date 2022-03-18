package com.caipiao.core.library.dto.system;


import com.caipiao.core.library.model.base.BaseDTO;

/**
 * 管理员接收对象
 * @author admin
 */
public class AdminOperaterDTO extends BaseDTO {
    
    //id
    private int id;
    
    //帐号
    private String account;
    
    //密码
    private String password;

    //状态
    private int status=-1;
    
    //角色id
    private int roleId;
    
    //搜索条件--开始时间(YYYY-MM-DD hh:mm:ss)
    private String startDate;
    
    //结束时间
    private String endDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "AdminOperaterDTO{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", roleId=" + roleId +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
