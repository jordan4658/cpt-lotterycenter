package com.caipiao.core.library.vo.system;


/**
 * 系统管理员
 * @author admin
 * @date 2017年9月22日 上午10:27:39
 */
public class AdminOperaterVO {
    
    //id
    private int id;

    //帐号
    private String account;
    
    //角色id
    private int roleId;
    
    //角色
    private String role;
    
    //帐号的状态，0正常、1冻结
    private int status;
    
    //最后登录时间
    private long lastLoginTime;

    //最后登录ip
    private String lastLoginIp;
    
    //创建时间
    private long createTime;

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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
