package com.spinach.example.jdbctemplate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spinach.example.jdbctemplate.base.Page;
import com.spinach.example.jdbctemplate.service.JdbcTemplateService;
import com.spinach.example.mybatis.entity.TestUser;

/**
 * 		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>
 * @author:whh
 * @date:2017年12月25日上午10:46:03
 */
@RestController
@RequestMapping("/jdbc")
public class JdbcTemplateController {

	@Autowired
	private JdbcTemplateService jdbcTemplateService;

	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public List<TestUser> insert(@RequestParam String name) {
		jdbcTemplateService.insert();
		return jdbcTemplateService.selectAll();
	}

	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public TestUser get(@RequestParam(defaultValue = "1") Integer id) {
		return jdbcTemplateService.selectById(id);
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public int update(@RequestParam(defaultValue = "1") Integer id) {
		return jdbcTemplateService.update();
	}

	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public int del(@RequestParam(defaultValue = "1") Integer id) {
		return jdbcTemplateService.delete();
	}
	
	@RequestMapping(value = "/selectpage", method = RequestMethod.GET)
	public Page<TestUser> queryForPage() {
		Page<TestUser> result = jdbcTemplateService.queryForPage();
		return result;
	}

}
