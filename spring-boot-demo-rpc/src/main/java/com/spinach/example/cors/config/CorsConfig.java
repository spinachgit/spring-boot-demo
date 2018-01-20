package com.spinach.example.cors.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 全局跨域设置。
 * 参考：https://docs.spring.io/spring/docs/4.3.x/spring-framework-reference/htmlsingle/
 * 27.3 Global CORS configuration
 * @author:whh
 * @date:2018年1月19日下午5:00:07
 */
//@Configuration
//@EnableWebMvc
public class CorsConfig  {//extends WebMvcConfigurerAdapter
    //Override
    public void addCorsMappings(CorsRegistry registry) {
    	/**
    	 * 注意：这个是全局配置，allowedOrigins("*")允许所有，
    	 * 同时在设置@CrossOrigin(origins = "http://www.runoob.com")：表示只能在http://www.runoob.com这个域名里可以访问。
    	 */
        /*registry.addMapping("/**").allowedOrigins("http://www.runoob.com")
                .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
                .allowCredentials(false).maxAge(3600);*/
        
        //registry.addMapping("/api/**").allowedOrigins("http://localhost:8080");
    	
        /*registry.addMapping("/cors/**")
	          .allowedOrigins("http://localhost:8081")
	          .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
	          .allowedHeaders("header1", "header2", "header3")
	          .exposedHeaders("header1", "header2")
	          .allowCredentials(false).maxAge(3600);*/
      		
    }
}