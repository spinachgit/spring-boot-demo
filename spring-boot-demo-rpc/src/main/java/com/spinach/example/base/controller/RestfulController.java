package com.spinach.example.base.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.spinach.example.base.bean.TestUserLog;
import com.spinach.example.cache.service.TestUserLogCache;

/**
 * @author whh
 */
@RestController
@RequestMapping(value = "/rest", method = RequestMethod.POST)
public class RestfulController {

	@Autowired
	private TestUserLogCache UserLogCache;

	@RequestMapping(value = "/update")
	public TestUserLog update(@RequestBody JsonNode jsonNode) {
		System.out.println("jsonNode=" + jsonNode);
		TestUserLog bean = UserLogCache.selectById(jsonNode.get("id").asInt(1));
		if(bean == null){
			bean = new TestUserLog();
		}
		bean.setUserName("测试");
		bean.setCreateTime(new Date());
		bean.setUserIp("192.168.1.1");
		UserLogCache.updateById(bean);
		return bean;
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public TestUserLog update2(@PathVariable(value = "id") Integer id) {
		TestUserLog bean = UserLogCache.selectById(id);
		if(bean == null){
			bean = new TestUserLog();
		}
		bean.setUserName("测试");
		bean.setCreateTime(new Date());
		bean.setUserIp("192.168.1.1");
		UserLogCache.updateById(bean);
		return bean;
	}

}
