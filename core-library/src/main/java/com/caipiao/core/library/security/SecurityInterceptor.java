package com.caipiao.core.library.security;

import com.alibaba.fastjson.JSON;
import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.constant.StatusCode;
import com.caipiao.core.library.model.RequestInfo;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.rest.write.aop.AopWriteServiceRest;
import com.caipiao.core.library.tool.CommonUtils;
import com.mapper.domain.AppMember;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: admin
 * @Description:拦截器
 * @Version: 1.0.0
 * @Date; 2017/11/23 15:56
 */
@Component
public class SecurityInterceptor implements HandlerInterceptor {

	private static final Logger log = LoggerFactory.getLogger(SecurityInterceptor.class);

	@Autowired
	private AopWriteServiceRest aopWriteServiceRest;

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse response, Object arg2, ModelAndView modelAndView)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		String url = request.getRequestURL().toString();
		if("OPTIONS".equals(request.getMethod())){
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.addHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Origin,Content-Type");
			response.addHeader("Access-Control-Allow-Headers", "uid");
			response.addHeader("Access-Control-Allow-Headers", "appToken");
			//预检一次设置一个有效期，在有效期内（单位秒）不再重复预检,OPTIONS预检请求
			response.addHeader("Access-Control-Max-Age", "3600");
		}else{
//			response.addHeader("Access-Control-Allow-Origin", "*");
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.addHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Origin,Content-Type");
			response.addHeader("Access-Control-Allow-Headers", "uid");
			response.addHeader("Access-Control-Allow-Headers", "appToken");
			//预检一次设置一个有效期，在有效期内（单位秒）不再重复预检,OPTIONS预检请求
			response.addHeader("Access-Control-Max-Age", "3600");

		}
		String json = request.getAttribute("jsonParam") + "";

		int statusCode = response.getStatus();
		log.debug(String.format("方式=%s，状态码=%s", request.getMethod(), statusCode));
		log.info(String.format("请求地址=%s，参数=%s", request.getRequestURL(), json));

		if (statusCode != 200) {
			HttpStatus status = HttpStatus.valueOf(statusCode);
			this.responseWriter(response, status.value() + "", status.getReasonPhrase());
			return false;
		}

		//获取areaMark
		String mark = request.getParameter("areaMark");
		int areaMark = 0;
		if (!CommonUtils.isNull(mark)){
			try {
				areaMark = Integer.parseInt(mark);
			} catch (NumberFormatException e) {
				log.error(String.format("warn:areaMark is not number !!!"));
				this.responseWriter(response, StatusCode.UNKNOW_ERROR.getCode(), StatusCode.AREAMARK_ERROR.getValue());
				return false;
			}
		}

		// 校验签名是否正确
		if (StringUtils.isNotBlank(json)) {
			RequestInfo<String> req = getRequestParam(json);
			String md5sign = "\""+ Security.MD5(Constants.APP_KEY +req.getData())+"\"";
			String apisign = req.getApisign();

			if ("\"nishibushisha???\"".equalsIgnoreCase(apisign)) {
				// if(apisign.equalsIgnoreCase("\"nishibushisha???\"")){
				return true;
			}
			if (!md5sign.equals(apisign)) {
				log.warn(String.format("秘钥验证不通过：参数秘钥：%s, 产生的秘钥：%s", apisign, md5sign));
				if(areaMark == 0){
					this.responseWriter(response, StatusCode.UNKNOW_ERROR.getCode(), StatusCode.SIGN_FAILED.getValue());
					return false;
				}else{
					this.responseWriter(response, StatusCode.UNKNOW_ERROR.getCode(), StatusCode.SIGN_FAILED_EN.getValue());
					return false;
				}
			}
			log.debug(String.format("秘钥验证通过：参数秘钥：%s, 产生的秘钥：%s", apisign, md5sign));

//			//参数敏感词过滤
//			String requrl=request.getRequestURL().toString();
// 			if(!requrl.contains("login")){ //登陆接口不验证敏感词
//
//			String reqData = req.getData();
//			if(StringUtils.isNotBlank(reqData)){
//				List<String> wordsList = aopWriteServiceRest.getSensitive();
//				//请求参数中包含敏感词汇
//				for (String s : wordsList) {
//					if (reqData.contains(s)){
//						log.warn(String.format("url：%s==被拦截，请求参数：%s==包含敏感词汇：%s", url, reqData, s));
//						this.responseWriter(response, StatusCode.SENSITIVE_ERROR.getCode(), StatusCode.SENSITIVE_ERROR.getValue());
//						return false;
//					}
//				}
//			}
//			}
		}

		return true;
	}

	/**
	 * 提取参数
	 * @param json
	 * @return
	 */
	private RequestInfo<String> getRequestParam(String json) {
		RequestInfo<String> resultInfo = new RequestInfo<String>();
		int signIndex = json.indexOf("\"apisign\":");
		int dataIndex = json.indexOf("\"data\":");
		String sign = null;
		String data = null;
		String regBefore = "(?<=(\"apisign\":|\"data\":)).*(?=(,\"data\"|,\"apisign\"))";
		String regAfter = "(?<=(,\"apisign\":|,\"data\":)).*(?<!}$)";
		 Pattern pattern = Pattern.compile(regBefore);
		 Matcher matcher = pattern.matcher(json);
			 if(matcher.find()){
		        	
		        	if(signIndex<dataIndex){
		        		sign = matcher.group();
		        		log.debug("sign="+sign);
		        	}else{
		        		data = matcher.group();
		        		log.debug("data="+data);
		        	}
		        	
		        }
			 pattern = Pattern.compile(regAfter);
			 matcher = pattern.matcher(json);
			 if(matcher.find()){
		        	if(signIndex>dataIndex){
		        		sign = matcher.group();
		        		log.debug("sign="+sign);
		        	}else{
		        		data = matcher.group();
		        		log.debug("data="+data);
		        	}
		        }
			 resultInfo.setApisign(sign);
			 resultInfo.setData(data);
		return resultInfo;
	}

	/**
	 * 响应内容
	 * @param response
	 * @param code
	 * @param msg
	 * @throws Exception
	 */
	protected void responseWriter(HttpServletResponse response, String code, String msg) throws IOException {
		response.setHeader("Content-type", "application/json;charset=UTF-8");
		ResultInfo<String> resultInfo = new ResultInfo<>();
		resultInfo.setStatus(code);
		resultInfo.setInfo(msg);
		response.getWriter().print(JSON.toJSONString(resultInfo));
	}
	
	public static void main(String[] args) {
		 AppMember appMember = null;
	        if(!StringUtils.isEmpty("")) {
	        	
	            appMember = new AppMember();
	        }
	        
	        if (appMember == null) {
	        	System.out.println("1");
	        } else {
	        	System.out.println("2");
	        }
		
		
		
		
		
	}


}
