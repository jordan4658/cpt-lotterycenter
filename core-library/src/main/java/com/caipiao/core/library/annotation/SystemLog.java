package com.caipiao.core.library.annotation;

import java.lang.annotation.*;

/**
 * @Author: admin
 * @Description:系统操作日志注解
 * @Version: 1.0.0
 * @Date; 2017-10-30 17:49
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {

    /** 模块名 */
    public String module()  default "";
    /** 方法名 */
    public String methods()  default "";
}
