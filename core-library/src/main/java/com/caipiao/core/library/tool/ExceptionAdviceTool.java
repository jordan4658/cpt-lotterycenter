package com.caipiao.core.library.tool;

import com.caipiao.core.library.constant.StatusCode;
import com.caipiao.core.library.model.ResultInfo;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.nio.file.AccessDeniedException;

/**
 * @Author: admin
 * @Description:
 * @Version: 1.0.0
 * @Date; 2018/5/25 025 17:51
 */
public class ExceptionAdviceTool {

    public static ResultInfo<String> getExceptionAdviceResultInfo(Exception exception) {
        ResultInfo<String> resultInfo = new ResultInfo<>();
        resultInfo.setStatus(StatusCode.UNKNOW_ERROR.getCode());
        if (exception instanceof BindException) {
            BindException bindException = (BindException) exception;
            resultInfo.setInfo(bindException.getBindingResult().getFieldError().getDefaultMessage());
            return resultInfo;
        } else if (exception instanceof MissingServletRequestParameterException) {
            resultInfo.setStatus(StatusCode.PARAM_ERROR.getCode());
            resultInfo.setInfo(StatusCode.PARAM_ERROR.getValue());
            return resultInfo;
        } else if (exception instanceof HystrixBadRequestException) {
            HystrixBadRequestException requestException = (HystrixBadRequestException) exception;
            resultInfo.setInfo(requestException.getMessage());
            return resultInfo;
        } else if (exception instanceof AccessDeniedException) {
            resultInfo.setInfo(exception.getMessage());
            return resultInfo;
        } else {
            resultInfo.setInfo(exception.getMessage());
            return resultInfo;
        }
    }

}
