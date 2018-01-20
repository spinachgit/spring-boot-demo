package com.spinach.example.base.dao;

import com.spinach.example.base.bean.TestUser;
import com.spinach.example.util.base.Page;

public interface TestUserDao {

	int insert(TestUser testUser);

	int deleteById(int id);

	int updateById(TestUser testUser);

	TestUser selectById(int id);

	Page<TestUser> queryForPage(int i, int j, String string);

}
