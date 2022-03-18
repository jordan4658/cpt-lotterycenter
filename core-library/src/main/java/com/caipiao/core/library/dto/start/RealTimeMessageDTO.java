package com.caipiao.core.library.dto.start;

import com.caipiao.core.library.tool.TimeHelper;
import com.mapper.domain.RealTimeMessage;

import java.util.Date;

/**
 * @author lzy
 * @create 2018-06-13 14:29
 **/
public class RealTimeMessageDTO {

    /**
     * 说明: 内容
     */
    private String message;

    /**
     * 说明: 是否推送ios:0,否;1,是
     */
    private Integer ios;

    /**
     * 说明: 是否推送android:0,否;1,是
     */
    private Integer android;

    /**
     * 说明: 操作员账号
     */
    private String account;

    public static RealTimeMessage getRealTimeMessage(RealTimeMessageDTO realTimeMessageDTO) {
        if (realTimeMessageDTO == null) {
            return null;
        }
        RealTimeMessage realTimeMessage = new RealTimeMessage();
        realTimeMessage.setAccount(realTimeMessageDTO.getAccount());
        realTimeMessage.setMessage(realTimeMessageDTO.getMessage());
        realTimeMessage.setAndroid(realTimeMessageDTO.getAndroid());
        realTimeMessage.setIos(realTimeMessageDTO.getIos());
        realTimeMessage.setCreateTime(TimeHelper.date());
        return realTimeMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getIos() {
        return ios;
    }

    public void setIos(Integer ios) {
        this.ios = ios;
    }

    public Integer getAndroid() {
        return android;
    }

    public void setAndroid(Integer android) {
        this.android = android;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
