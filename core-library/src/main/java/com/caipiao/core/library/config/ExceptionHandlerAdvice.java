package com.caipiao.core.library.config;

import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.tool.ExceptionAdviceTool;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * rest请求全局异常处理
     * @param exception
     * @return
     */
    @ResponseStatus(HttpStatus.OK)//返回一个指定的http response状态码
    @ExceptionHandler(Exception.class)
    public ResultInfo<String> ExceptionHandler(Exception exception) {
        return ExceptionAdviceTool.getExceptionAdviceResultInfo(exception);
    }

}
