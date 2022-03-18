package com.caipiao.core.library.filter;

import com.caipiao.core.library.model.RequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤器
 */
@WebFilter(filterName="BaseFilter",urlPatterns="/*")
public class BaseFilter implements Filter {
	private static final Logger LOG = LoggerFactory.getLogger(BaseFilter.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 如果是application/x-www-form-urlencoded, 参数值在request body中以 a=1&b=2&c=3...形式存在，
	 * 若直接构造BodyReaderHttpServletRequestWrapper，在将流读取并存到copy字节数组里之后,
	 * httpRequest.getParameterMap()将返回空值！
	 * 若运行一下 httpRequest.getParameterMap(), body中的流将为空! 所以两者是互斥的！
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		RequestWrapper requestWrapper = null;
		if(request instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			String contentType = httpRequest.getContentType();
			if (contentType != null && contentType.contains("application/x-www-form-urlencoded")) {
				httpRequest.getParameterMap();
			}
			requestWrapper = new RequestWrapper(httpRequest);
			requestWrapper.setAttribute("jsonParam", requestWrapper.getBody());
		}
		chain.doFilter(requestWrapper, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		LOG.info("过滤器初始化");
		
	}

}
