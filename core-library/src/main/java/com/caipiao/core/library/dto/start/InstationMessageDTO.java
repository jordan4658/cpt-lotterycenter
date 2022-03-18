package com.caipiao.core.library.dto.start;

import com.mapper.domain.InstationMessage;

/**
 * @author lzy
 * @create 2018-06-13 15:32
 **/
public class InstationMessageDTO {

    private Integer id;

    /**
     * 说明: 标题
     */
    private String title;

    /**
     * 说明: 描述
     */
    private String describe;

    /**
     * 说明: 内容
     */
    private String message;

    /**
     * 说明: 操作员账号
     */
    private String account;

    private Integer jg;

    public static InstationMessage getInstationMessage(InstationMessageDTO instationMessageDTO) {
        if (instationMessageDTO != null) {
            InstationMessage instationMessage = new InstationMessage();
            instationMessage.setId(instationMessageDTO.getId());
            instationMessage.setAccount(instationMessageDTO.getAccount());
            instationMessage.setTitle(instationMessageDTO.getTitle());
            instationMessage.setDescribe(instationMessageDTO.getDescribe());
            instationMessage.setMessage(instationMessageDTO.getMessage());
            return instationMessage;
        }
        return null;
    }

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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getJg() {
        return jg;
    }

    public void setJg(Integer jg) {
        this.jg = jg;
    }
}
