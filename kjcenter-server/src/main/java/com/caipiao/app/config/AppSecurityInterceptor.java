package com.caipiao.app.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.caipiao.core.library.security.SecurityInterceptor;

/**
 * app拦截器
 */
public class AppSecurityInterceptor extends SecurityInterceptor {

	// 拦截器执行拦截,true表示放过，false表示拦截
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws IOException{
		return super.preHandle(request, response, object);
	}

	// 执行完Controller的方法后执行此方法
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {
	}

	// 执行完postHandler后执行此方法
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3) throws Exception {
	}


}
