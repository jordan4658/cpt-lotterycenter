package com.caipiao.live.common.exception;

import com.caipiao.live.common.enums.StatusCode;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -3876502758804606346L;

    private Integer code = StatusCode.SYSTEM_ERROR.getCode();

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(StatusCode statusCode) {
        super(statusCode.getMsg());
        this.code = statusCode.getCode();
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message, Throwable th) {
        super(message, th);
    }

    public BusinessException(Integer code, String message, Throwable th) {
        super(message, th);
        this.code = code;
    }

    public BusinessException(Throwable th) {
        super(th);
    }

    public String getMessage() {
        return super.getMessage();
    }

    /**
     * @return the code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(Integer code) {
        this.code = code;
    }

}
