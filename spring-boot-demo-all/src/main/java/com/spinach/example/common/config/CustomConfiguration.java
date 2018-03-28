package com.spinach.example.common.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
public class CustomConfiguration extends WebMvcConfigurerAdapter {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		//registry.addMapping("/api/**").allowedOrigins("http://localhost:8080");
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		// TODO Auto-generated method stub
		super.configurePathMatch(configurer);
	}

	/**
	 * 注册各种各样的视图解析器的，包括自己定义的
	 * @author:whh
	 * @date:2018年3月20日下午2:49:00
	 * @param configurer
	 */
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		//super.configureContentNegotiation(configurer);
		/* 是否通过请求Url的扩展名来决定media type */
		configurer.favorPathExtension(true)
				/* 不检查Accept请求头 */
				.ignoreAcceptHeader(true)
				.parameterName("mediaType")
				/* 设置默认的media yype */
				.defaultContentType(MediaType.TEXT_HTML)
				/* 请求以.html结尾的会被当成MediaType.TEXT_HTML*/
				.mediaType("html", MediaType.TEXT_HTML)
				/* 请求以.json结尾的会被当成MediaType.APPLICATION_JSON*/
				.mediaType("json", MediaType.APPLICATION_JSON);
	}

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		// TODO Auto-generated method stub
		super.configureAsyncSupport(configurer);
	}

	/**
	 * 默认静态资源处理器
	 * @author:whh
	 * @date:2018年3月20日下午2:50:30
	 * @param configurer
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// TODO Auto-generated method stub
		//super.configureDefaultServletHandling(configurer);
		configurer.enable();
		configurer.enable("defaultServletName");
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		// TODO Auto-generated method stub
		super.addFormatters(registry);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		super.addInterceptors(registry);
	}

	/**
	 * 静态资源处理
	 * 如果项目的一些资源文件放在/WEB-INF/resources/下面
	 * 在浏览器访问的地址就是类似：https://host:port/projectName/WEB-INF/resources/xxx.css
	 * 但是加了如下定义之后就可以这样访问：
	 * https://host:port/projectName/resources/xxx.css
	 * 非必须
	 * @author:whh
	 * @date:2018年3月20日下午2:32:50
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/public/**").addResourceLocations("classpath:/public/");
		super.addResourceHandlers(registry);
	}

	/**
	 * 只是想通过一个URL Mapping然后不经过Controller处理直接跳转到页面上的需求！
	 * @author:whh
	 * @date:2018年3月20日下午2:42:41
	 * @param registry
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		super.addViewControllers(registry);
		//registry.addViewController("/login")..setViewName("forward:/index.html");
		registry.addViewController("/login").setViewName("login");
	}

	/**
	 * 这里配置视图解析器
	 * @author:whh
	 * @date:2018年3月20日下午2:49:34
	 * @param registry
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// TODO Auto-generated method stub
		//super.configureViewResolvers(registry);
		registry.jsp("/WEB-INF/jsp/", ".jsp");
		registry.enableContentNegotiation(new MappingJackson2JsonView());
	}

	/**
	 * 参数解析
	 * @author:whh
	 * @date:2018年3月20日下午2:51:42
	 * @param argumentResolvers
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		// TODO Auto-generated method stub
		super.addArgumentResolvers(argumentResolvers);
	}

	/**
	 * 返回值解析
	 * @author:whh
	 * @date:2018年3月20日下午2:51:15
	 * @param returnValueHandlers
	 */
	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		// TODO Auto-generated method stub
		super.addReturnValueHandlers(returnValueHandlers);
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// TODO Auto-generated method stub
		super.configureMessageConverters(converters);
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		// TODO Auto-generated method stub
		super.extendMessageConverters(converters);
	}

	/**
	 * 异常处理解析器
	 * @author:whh
	 * @date:2018年3月20日下午2:50:55
	 * @param exceptionResolvers
	 */
	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		// TODO Auto-generated method stub
		super.configureHandlerExceptionResolvers(exceptionResolvers);
	}

	@Override
	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		// TODO Auto-generated method stub
		super.extendHandlerExceptionResolvers(exceptionResolvers);
	}

	@Override
	public Validator getValidator() {
		// TODO Auto-generated method stub
		return super.getValidator();
	}

	@Override
	public MessageCodesResolver getMessageCodesResolver() {
		// TODO Auto-generated method stub
		return super.getMessageCodesResolver();
	}
}