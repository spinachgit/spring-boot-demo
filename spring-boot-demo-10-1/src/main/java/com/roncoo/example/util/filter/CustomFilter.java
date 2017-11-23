package com.roncoo.example.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * 自定义filter
 * 
 * @author wujing
 */
@WebFilter(urlPatterns="/*")
public class CustomFilter implements Filter {
	private FilterConfig filterConfig;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		System.out.println("init filter");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("do filter");
		
		//---------下面代码用于对用户请求执行预处理---------
		//获取ServletContext对象，用于记录日志
		ServletContext context = this.filterConfig.getServletContext(); 
		long before = System.currentTimeMillis();
		System.out.println("开始过滤...");
		//将请求转换成HttpServletRequest请求
		HttpServletRequest hrequest = (HttpServletRequest)request;
		//记录日志
		context.log("Filter已经截获到用户的请求地址： " + hrequest.getServletPath());
		//Filter只是链式处理，请求依然放行到目的地址
		chain.doFilter(request, response); 
		//---------下面代码用于对服务器响应执行后处理---------
		long after = System.currentTimeMillis();
		//记录日志
		context.log("过滤结束");
		//再次记录日志
		context.log("请求被定位到" + hrequest.getRequestURI() + "所花的时间为: " + (after - before)); 
	}

	@Override
	public void destroy() {
		System.out.println("destroy filter");
	}

}
