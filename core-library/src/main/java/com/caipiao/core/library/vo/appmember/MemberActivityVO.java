package com.caipiao.core.library.vo.appmember;

/**
 * 用户活动记录VO
 *
 * @author lzy
 * @create 2018-08-10 11:23
 **/
public class MemberActivityVO {

    private String createTime;

    private Integer money;

    private String status;

    private String title;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
