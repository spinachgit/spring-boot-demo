package com.spinach.example.rediscache.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spinach.example.datajpa.entity.TestUserLog;
import com.spinach.example.rediscache.service.TestUserLogCache;

/**
 * @author whh
 */
@RestController
@RequestMapping(value = "/cache", method = RequestMethod.GET)
public class TestCacheController {

	@Autowired
	private TestUserLogCache userLogCache;

	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public TestUserLog get(@RequestParam(defaultValue = "1") Integer id) {
		return userLogCache.selectById(id);
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public TestUserLog update(@RequestParam(defaultValue = "1") Integer id,String name) {
		TestUserLog bean = userLogCache.selectById(id);
		bean.setUserName(name);
		//bean.setCreateTime(new Date());
		userLogCache.updateById(bean);
		bean = userLogCache.selectById(id);
		return bean;
	}

	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String del(@RequestParam(defaultValue = "1") Integer id) {
		return userLogCache.deleteById(id);
	}

}
