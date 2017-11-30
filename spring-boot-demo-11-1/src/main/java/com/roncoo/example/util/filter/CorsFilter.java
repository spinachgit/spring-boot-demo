package com.roncoo.example.util.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

@WebFilter(urlPatterns = "/*")
public class CorsFilter implements Filter {

	private String allowOrigin;
	private String allowMethods;
	private String allowCredentials;
	private String allowHeaders;
	private String exposeHeaders;

	/**
	 * web.xml，跨域的FILETER配置：
		<filter>
		    <filter-name>corsFilter</filter-name>
		    <filter-class>com.xxx.api.cors.CorsFilter</filter-class>
		    <init-param>
		        <param-name>allowOrigin</param-name>
		        <param-value>http://web.xxx.com</param-value>
		    </init-param>
		    <init-param>
		        <param-name>allowMethods</param-name>
		        <param-value>GET,POST,PUT,DELETE,OPTIONS</param-value>
		    </init-param>
		    <init-param>
		        <param-name>allowCredentials</param-name>
		        <param-value>true</param-value>
		    </init-param>
		    <init-param>
		        <param-name>allowHeaders</param-name>
		        <param-value>Content-Type</param-value>
		    </init-param>
		</filter>
		<filter-mapping>
		    <filter-name>corsFilter</filter-name>
		    <url-pattern>/*</url-pattern>
		</filter-mapping>
	 * @author:whh
	 * @date:2017年11月23日下午2:18:17
	 * @param filterConfig
	 * @throws ServletException
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
//		allowOrigin = "http://localhost:8080/api/*";
//		allowMethods = "GET,POST,PUT,DELETE,OPTIONS"; //Access-Control-Allow-Methods
//		allowCredentials = "true"; //Access-Control-Allow-Credentials
//		allowHeaders = "Content-Type";//Access-Control-Allow-Headers
//		exposeHeaders = "X-My-Custom-Header, X-Another-Custom-Header";//Access-Control-Expose-Headers
		
		/*		
 		allowOrigin = filterConfig.getInitParameter("allowOrigin");
		allowMethods = filterConfig.getInitParameter("allowMethods");
		allowCredentials = filterConfig.getInitParameter("allowCredentials");
		allowHeaders = filterConfig.getInitParameter("allowHeaders");
		exposeHeaders = filterConfig.getInitParameter("exposeHeaders");
		*/
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (!StringUtils.isEmpty(allowOrigin)) {
			List<String> allowOriginList = Arrays.asList(allowOrigin.split(","));
			if (allowOriginList != null && allowOriginList.size() > 0) {
				String currentOrigin = request.getHeader("Origin");
				if (allowOriginList.contains(currentOrigin)) {
					response.setHeader("Access-Control-Allow-Origin", currentOrigin);
				}
			}
		}
		if (!StringUtils.isEmpty(allowMethods)) {
			response.setHeader("Access-Control-Allow-Methods", allowMethods);
		}
		if (!StringUtils.isEmpty(allowCredentials)) {
			response.setHeader("Access-Control-Allow-Credentials", allowCredentials);
		}
		if (!StringUtils.isEmpty(allowHeaders)) {
			response.setHeader("Access-Control-Allow-Headers", allowHeaders);
		}
		if (!StringUtils.isEmpty(exposeHeaders)) {
			response.setHeader("Access-Control-Expose-Headers", exposeHeaders);
		}
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
	}
}