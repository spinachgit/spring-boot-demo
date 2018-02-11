package com.spinach.example.webservice.cxf.server;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
//import org.apache.cxf.endpoint.EndpointImpl;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注意引入的包路径：
 * 是：org.apache.cxf.jaxws.EndpointImpl 不是：import org.apache.cxf.endpoint.EndpointImpl
 * 还有：import javax.xml.ws.Endpoint;
 * @author:spinach
 * @date:2018年2月1日下午4:56:02
 */
@Configuration
public class CxfConfig {
	@Autowired
    private Bus bus;
    @Autowired
    private CxfServices cxfServices;

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus,cxfServices);
        endpoint.publish("/cxfServices");//接口发布在目录下
        //打印报文日志拦截器
        //endpoint.getInInterceptors().add(new LoggingInInterceptor());
        //endpoint.getInInterceptors().add(new LoggingOutInterceptor());

        //通过拦截器校验用户名与密码
        endpoint.getInInterceptors().add(new AuthInterceptor());
        return endpoint;
    }
    
    @Bean
    public ServletRegistrationBean dispatcherServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/cxf/*");
    }
    
    /* 方法二：方法二为@Autowired自动注入
    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }
    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), userService());
        endpoint.publish("/user");
        return endpoint;
    }*/
}