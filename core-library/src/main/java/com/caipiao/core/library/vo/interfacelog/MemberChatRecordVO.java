package com.caipiao.core.library.vo.interfacelog;

/**
 * @author lzy
 * @create 2018-06-20 10:16
 **/
public class MemberChatRecordVO {

    private Integer id;

    /**
     * 说明: 会员id
     */
    private Integer memberId;

    /**
     * 说明: 会员账号
     */
    private String account;

    /**
     * 说明: 会员昵称
     */
    private String nickname;

    /**
     * 说明: 内容
     */
    private String message;

    /**
     * 说明: 操作时间
     */
    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
