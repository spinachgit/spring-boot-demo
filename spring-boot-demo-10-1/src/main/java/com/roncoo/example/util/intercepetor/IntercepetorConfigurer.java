package com.roncoo.example.util.intercepetor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class IntercepetorConfigurer extends WebMvcConfigurerAdapter {
//	@Autowired
//    private LogInterceptor logInterceptor;
//	@Autowired
//	private DebugInterceprot debugInterceprot;
//	@Autowired
//	private TimeCostInterceptor timeCostInterceptor;
//	@Autowired
//	private ResponseInterceptor responseInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 多个拦截器组成一个拦截器链
		// addPathPatterns 用于添加拦截规则
		// excludePathPatterns 用户排除拦截
		//registry.addInterceptor(new MyInterceptor1()).addPathPatterns("/**");
		registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
		//registry.addInterceptor(debugInterceprot).addPathPatterns("/**");
		//registry.addInterceptor(timeCostInterceptor).addPathPatterns("/**");
		//registry.addInterceptor(new ResponseInterceptor()).addPathPatterns("/**").excludePathPatterns("/identifyCode/generate");
		super.addInterceptors(registry);
	}

}