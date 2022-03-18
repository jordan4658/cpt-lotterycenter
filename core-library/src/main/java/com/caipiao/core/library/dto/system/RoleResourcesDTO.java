package com.caipiao.core.library.dto.system;

import java.util.List;

/**
 * 角色资源对象
 * @author admin
 */
public class RoleResourcesDTO {
    
    //角色id
    private int roleId;

    /**
     * 资源id的集合
     */
    private List<Integer> resources;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public List<Integer> getResources() {
        return resources;
    }

    public void setResources(List<Integer> resources) {
        this.resources = resources;
    }

}
