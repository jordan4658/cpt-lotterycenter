package com.caipiao.core.library.vo.system;

/**
 * 资源树结构数据VO
 * @author admin
 * @date 2017年10月17日 下午4:47:01
 */
public class ResourcesZtreeVO {
    
    //节点id
    private int id;

    //节点父id
    private int pId;
    
    //节点名字
    private String name;
    
    //节点是否开启(选中的就展开)
    private boolean open;
    
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

    public boolean getOpen() {
        return checked==0?false:true;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

}
