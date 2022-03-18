package com.caipiao.core.library.vo.appmember;

/**
 * 会员站内信
 *
 * @author lzy
 * @create 2018-10-15 16:25
 **/
public class MemberInstationMessageVO {

    private Integer id;

    /**
     * 说明: 标题
     */
    private String title;

    /**
     * 说明: 简介
     */
    private String intro;

    /**
     * 说明: 内容
     */
    private String message;

    /**
     * 说明: 状态
     */
    private Integer status;

    /**
     * 说明: 创建时间
     */
    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
