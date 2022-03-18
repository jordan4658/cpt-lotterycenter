package com.caipiao.core.library.vo.operater;

/**
 * @author lzy
 * @create 2018-06-05 10:30
 **/
public class RoleResourceVO {
    //节点id
    private int id;

    //节点父id
    private int pId;

    //节点名字
    private String name;

    //节点是否选中(0没选中，1选中)
    private int checked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }
}
