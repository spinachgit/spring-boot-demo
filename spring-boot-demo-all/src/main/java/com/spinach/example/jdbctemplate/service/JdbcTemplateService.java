package com.spinach.example.jdbctemplate.service;

import java.util.List;

import com.spinach.example.jdbctemplate.base.Page;
import com.spinach.example.mybatis.entity.TestUser;

public interface JdbcTemplateService {

	int insert();

	int delete();

	int update();

	List<TestUser> selectAll();
	
	TestUser selectById(Integer id);

	Page<TestUser> queryForPage();

}
