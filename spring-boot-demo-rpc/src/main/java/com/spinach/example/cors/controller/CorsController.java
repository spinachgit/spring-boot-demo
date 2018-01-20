package com.spinach.example.cors.controller;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spinach.example.base.bean.TestUserLog;
import com.spinach.example.base.component.TestJavaMailComponent;
import com.spinach.example.cache.service.TestUserLogCache;

@RestController
@RequestMapping("/cors")
@CrossOrigin(maxAge = 3600)
public class CorsController {

	@Autowired
	private TestUserLogCache UserLogCache;

	@Autowired
	private TestJavaMailComponent component;
	
	@RequestMapping(value = "mail")
	public String mail(String email) {
		component.sendMail(email);
		return "success";
	}
	/**
	 * 测试：仅仅使用@CrossOrigin不能跨域访问
	 * RequestMapping:不指定method的时候，默认所以方法都支持。
	 * @author:whh
	 * @date:2018年1月19日下午5:24:52
	 * @param name
	 * @return
	 */
	@CrossOrigin(origins = "http://localhost:8001")
	@RequestMapping(value = "/get", method = {RequestMethod.POST,RequestMethod.GET})
	public HashMap<String, Object> get(@RequestParam(required=false,defaultValue="默认名称") String name) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("title", "hello world");
		map.put("name", name);
		return map;
	}
	
	/**
	 * 当全局设置和方法级别上都是设置：*的时候，表示所有域都可以访问。
	 * @author:whh
	 * @date:2018年1月20日上午10:57:15
	 * @param id
	 * @return
	 */
	@CrossOrigin(origins="*")
	@RequestMapping(value = "/select",method=RequestMethod.GET)
	public TestUserLog get(@RequestParam(defaultValue = "1") Integer id) {
		return UserLogCache.selectById(id);	
	}
	
	/**
	 * 测试发现：指定了，在方法级别上指定了具体的域名后，只能该域名可以访问。
	 * @author:whh
	 * @date:2018年1月20日上午10:55:08
	 */
	@CrossOrigin(origins = "http://www.runoob.com", maxAge = 3000)
	@RequestMapping(value = "/update")
	public TestUserLog update(@RequestParam(defaultValue = "1") Integer id) {
		TestUserLog bean = UserLogCache.selectById(id);
		bean.setUserName("测试");
		bean.setCreateTime(new Date());
		UserLogCache.updateById(bean);
		return bean;
	}
	/**
	 * 同上
	 * 测试发现：8001/ 端口后加“/”后不能访问。
	 * @author:whh
	 * @date:2018年1月20日上午10:56:21
	 */
	@CrossOrigin(origins="http://localhost:8001")
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String del(@RequestParam(defaultValue = "1") Integer id) {
		return UserLogCache.deleteById(id);
	}

}
