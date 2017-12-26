package com.spinach.example.mybatis.service;

import java.util.List;

import com.spinach.example.mybatis.entity.TestUser;

public interface UserService {

	List<TestUser> selectList(TestUser user);

	TestUser selectOne(TestUser user);

	void update(TestUser user);

	void delete(TestUser user);

	void insert(TestUser user);
	
	

}
