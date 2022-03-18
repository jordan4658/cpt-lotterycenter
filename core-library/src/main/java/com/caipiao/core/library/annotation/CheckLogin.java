package com.caipiao.core.library.annotation;

import java.lang.annotation.*;

/**
 * @Author: admin
 * @Description:校验app是否登录
 * @Version: 1.0.0
 * @Date; 2017-11-17 17:03
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckLogin {
}
