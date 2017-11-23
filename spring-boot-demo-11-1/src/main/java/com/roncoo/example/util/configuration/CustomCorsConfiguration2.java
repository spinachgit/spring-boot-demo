package com.roncoo.example.util.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class CustomCorsConfiguration2 extends WebMvcConfigurerAdapter {
	
	//@Override
	public void addCorsMappings(CorsRegistry registry) {
		//registry.addMapping("/api/**").allowedOrigins("http://localhost:8080");
		registry.addMapping("/api/**")
        .allowedOrigins("http://domain2.com")
        .allowedMethods("PUT", "DELETE")
        .allowedHeaders("header1", "header2", "header3")
        .exposedHeaders("header1", "header2")
        .allowCredentials(false).maxAge(3600);
	}
	
}
