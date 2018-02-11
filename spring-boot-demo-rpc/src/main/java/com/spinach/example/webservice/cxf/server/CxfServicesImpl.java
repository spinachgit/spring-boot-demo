package com.spinach.example.webservice.cxf.server;

import org.springframework.stereotype.Component;

import javax.jws.WebService;

/**
 *  web services 接口实现
 */
@WebService(serviceName = "cxfServices"// 服务名
		, targetNamespace = "http://server.cxf.webservice.example.spinach.com"// 包名倒序，并且和接口定义保持一致
		, endpointInterface = "com.spinach.example.webservice.cxf.server.CxfServices") // 包名
@Component
public class CxfServicesImpl implements CxfServices {
	@Override
	public String sayHello(String name) {
		return "hello , " + name;
	}
}