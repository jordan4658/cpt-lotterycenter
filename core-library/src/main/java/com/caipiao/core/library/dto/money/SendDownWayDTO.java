package com.caipiao.core.library.dto.money;

import com.mapper.domain.SendDownWay;

/**
 * @author lzy
 * @create 2018-07-04 14:07
 **/
public class SendDownWayDTO {

    private Integer id;

    /**
     * 说明: 方式名称
     */
    private String name;

    /**
     * 说明: 商户号
     */
    private String mchid;

    /**
     * 说明: 密钥
     */
    private String key;

    /**
     * 说明: 模块名称
     */
    private String moduleName;

    /**
     * 说明: 状态: 0,禁用;1,启用
     */
    private Integer status;

    public static SendDownWay getSendDownWay(SendDownWayDTO sendDownWayDTO) {
        if (sendDownWayDTO != null) {
            SendDownWay sendDownWay = new SendDownWay();
            sendDownWay.setId(sendDownWayDTO.getId());
            sendDownWay.setName(sendDownWayDTO.getName());
            sendDownWay.setMchid(sendDownWayDTO.getMchid());
            sendDownWay.setKey(sendDownWayDTO.getKey());
            sendDownWay.setModuleName(sendDownWayDTO.getModuleName());
            sendDownWay.setStatus(sendDownWayDTO.getStatus());
            return sendDownWay;
        }
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
