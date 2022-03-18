package com.caipiao.core.library.vo.system;

/**
 * @Author: admin
 * @Description:
 * @Version: 1.0.0
 * @Date; 2018/6/12 012 17:14
 */
public class MenuVO {

    /**
     * ID
     */
    private int id;

    /**
     * 名称
     */
    private String name;

    /**
     * 权限
     */
    private String permission;

    /**
     * 类型
     */
    private int type;

    /**
     * 地址
     */
    private String url;

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

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "MenuVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", permission='" + permission + '\'' +
                ", type=" + type +
                ", url='" + url + '\'' +
                '}';
    }
}
