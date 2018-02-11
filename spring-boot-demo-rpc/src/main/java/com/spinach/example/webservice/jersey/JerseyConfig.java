package com.spinach.example.webservice.jersey;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		register(RequestContextFilter.class);
		// 配置restful package.
		packages("com.spinach.example.webservice.jersey");
	}

	@Bean
	public ServletRegistrationBean jersetServlet() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/jersey/*");
		// our rest resources will be available in the path /jersey/*
		registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());
		return registration;
	}
}