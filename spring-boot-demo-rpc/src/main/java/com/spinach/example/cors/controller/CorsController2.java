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

/**
 * 类级别的跨域，表示当前类下的所有接口都可以跨域。
 * @author:whh
 * @date:2018年1月19日下午5:06:19
 */
@RestController
@RequestMapping("/corsClass")
@CrossOrigin(value = "http://www.runoob.com", maxAge = 3000)
//@CrossOrigin(origins = "http://www.runoob.com", maxAge = 3000) //origins和value作用一样。
public class CorsController2 {

	@Autowired
	private TestUserLogCache UserLogCache;

	@Autowired
	private TestJavaMailComponent component;

	@RequestMapping(value = "mail")
	public String mail(String email) {
		component.sendMail(email);
		return "success";
	}

	@RequestMapping(value = "/get", method = {RequestMethod.POST,RequestMethod.GET})
	public HashMap<String, Object> get(@RequestParam String name) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("title", "hello world");
		map.put("name", name);
		return map;
	}
	
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public TestUserLog get(@RequestParam(defaultValue = "1") Integer id) {
		return UserLogCache.selectById(id);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public TestUserLog update(@RequestParam(defaultValue = "1") Integer id) {
		TestUserLog bean = UserLogCache.selectById(id);
		bean.setUserName("测试");
		bean.setCreateTime(new Date());
		UserLogCache.updateById(bean);
		return bean;
	}

	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String del(@RequestParam(defaultValue = "1") Integer id) {
		return UserLogCache.deleteById(id);
	}

}
