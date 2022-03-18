package com.caipiao.core.library.vo.system;

import com.caipiao.core.library.tool.TimeHelper;

public class MenuPermissionVO {

    /**
     * 字段: admin_permission_resource.id<br/>
     * 主键: 自动增长<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 主键id
     *
     * @mbggenerated
     */
    private int id;

    /**
     * 字段: admin_permission_resource.name<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 资源名称
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 字段: admin_permission_resource.parent_id<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 上一级id(最顶级为0)
     *
     * @mbggenerated
     */
    private int parentId;

    /**
     * 字段: admin_permission_resource.permission<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 权限，例如：user:add
     *
     * @mbggenerated
     */
    private String permission;

    /**
     * 字段: admin_permission_resource.type<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 类型，1:菜单，2:按钮，3:其它
     *
     * @mbggenerated
     */
    private int type;

    /**
     * 字段: admin_permission_resource.icon<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 菜单的图标
     *
     * @mbggenerated
     */
    private String icon;

    /**
     * 字段: admin_permission_resource.node_status<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 节点状态，0关闭，1打开
     *
     * @mbggenerated
     */
    private int nodeStatus;

    /**
     * 字段: admin_permission_resource.url<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 链接
     *
     * @mbggenerated
     */
    private String url;

    /**
     * 字段: admin_permission_resource.resource_order<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 权限资源展示排序
     *
     * @mbggenerated
     */
    private int resourceOrder;

    /**
     * 字段: admin_permission_resource.is_deleted<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 1删除, 0未删除
     *
     * @mbggenerated
     */
    private int isDeleted;

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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(int nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getResourceOrder() {
        return resourceOrder;
    }

    public void setResourceOrder(int resourceOrder) {
        this.resourceOrder = resourceOrder;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

}
