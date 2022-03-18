package com.caipiao.core.library.config;

import com.caipiao.core.library.constant.StatusCode;
import com.caipiao.core.library.exception.ExceptionInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;

import java.io.IOException;

/**
 * @Date 2017/9/15 16:36
 */
public class FeignClientExceptionErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder delegate = new Default();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() >= 500) {
            return delegate.decode(methodKey, response);
        }

        try {
            ExceptionInfo info = objectMapper.readValue(Util.toString(response.body().asReader()), ExceptionInfo.class);
            //// 这里只封装4开头的请求异常
            if (response.status() >= 400 && response.status() < 500) {
                return new HystrixBadRequestException(StatusCode.UNKNOW_ERROR.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return delegate.decode(methodKey, response);
    }
}
