package com.caipiao.core.library.vo.operater;

import com.mapper.domain.OperaterRole;

import java.util.List;

/**
 * @author lzy
 * @create 2018-06-05 14:39
 **/
public class OperaterVO {

    private int id;

    private String account;

    private Integer status;

    private Integer googleVerify;

    private Integer roleId;

    private List<OperaterRole> roles;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGoogleVerify() {
        return googleVerify;
    }

    public void setGoogleVerify(Integer googleVerify) {
        this.googleVerify = googleVerify;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<OperaterRole> getRoles() {
        return roles;
    }

    public void setRoles(List<OperaterRole> roles) {
        this.roles = roles;
    }
}
