package com.westernstory.api.servlet;

import com.westernstory.api.util.XSSRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 防止XSS攻击
 * @author fedor
 */
public class XSSFilter implements Filter {

	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		String method = req.getMethod();
		if(method != null && !"get".equals(method.toLowerCase())) {
			chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
		} else {
			chain.doFilter(request, response);
		}
    }
}