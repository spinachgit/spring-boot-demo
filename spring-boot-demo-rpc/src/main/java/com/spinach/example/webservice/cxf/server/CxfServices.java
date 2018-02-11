package com.spinach.example.webservice.cxf.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * web services 接口
 */
@WebService(targetNamespace = "http://server.cxf.webservice.example.spinach.com") // 命名空间,一般是接口的包名倒序
public interface CxfServices {
	@WebMethod
	String sayHello(@WebParam(name = "userName") String name);
}