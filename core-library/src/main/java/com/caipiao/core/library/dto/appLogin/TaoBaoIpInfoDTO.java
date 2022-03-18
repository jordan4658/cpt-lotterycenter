package com.caipiao.core.library.dto.appLogin;

/**
 * @Author: admin
 * @Description: ip地址信息DTO
 * @Version: 1.0.0
 * @Date; 2018/7/2 002 14:35
 */
public class TaoBaoIpInfoDTO {

    //状态码
    private int code;

    //ip信息
    private TaoBaoIpInfoData data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public TaoBaoIpInfoData getData() {
        return data;
    }

    public void setData(TaoBaoIpInfoData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TaoBaoIpInfoDTO{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}
