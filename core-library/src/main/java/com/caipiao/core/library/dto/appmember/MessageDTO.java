package com.caipiao.core.library.dto.appmember;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzy
 * @create 2018-10-15 17:18
 **/
public class MessageDTO {

    /**
     * 信息的id
     */
    private Integer messageId;

    /**
     * 信息的id集合
     */
    private List<Integer> messageIdList = new ArrayList<>();

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public List<Integer> getMessageIdList() {
        return messageIdList;
    }

    public void setMessageIdList(List<Integer> messageIdList) {
        this.messageIdList = messageIdList;
    }
}
