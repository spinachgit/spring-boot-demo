package com.spinach.example.jdbctemplate.dao;

import java.util.List;

import com.spinach.example.jdbctemplate.base.Page;
import com.spinach.example.mybatis.entity.TestUser;

public interface TestUserDao {

	int insert(TestUser testUser);

	int deleteById(int id);

	int updateById(TestUser testUser);

	TestUser selectById(int id);

	Page<TestUser> queryForPage(int i, int j, String string);

	List<TestUser> selectAll();

}
