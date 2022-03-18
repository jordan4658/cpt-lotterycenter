package com.caipiao.app.config;

import com.caipiao.core.library.security.SecurityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author: admin
 * @Description: 注册拦截器
 * @Version: 1.0.0
 * @Date; 2018/5/28 028 11:02
 */
@Configuration
public class AppWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Bean
    public SecurityInterceptor securityInterceptor(){
        return new SecurityInterceptor();
    }

    /**
     * 拦截器，配置放行 excludePathPatterns("/xxx/yyy","/aaa/bbb")
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new AppSecurityInterceptor()).excludePathPatterns("");

        registry.addInterceptor(securityInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}
