package com.caipiao.core.library.vo.operater;

import java.util.List;

/**
 * @author lzy
 * @create 2018-06-05 10:37
 **/
public class RoleVO {

    private int id;

    private String name;

    private List<RoleResourceVO> resourceVOList;

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

    public List<RoleResourceVO> getResourceVOList() {
        return resourceVOList;
    }

    public void setResourceVOList(List<RoleResourceVO> resourceVOList) {
        this.resourceVOList = resourceVOList;
    }
}
