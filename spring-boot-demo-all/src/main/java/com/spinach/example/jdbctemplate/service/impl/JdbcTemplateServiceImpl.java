package com.spinach.example.jdbctemplate.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.spinach.example.jdbctemplate.base.Page;
import com.spinach.example.jdbctemplate.dao.TestUserDao;
import com.spinach.example.jdbctemplate.service.JdbcTemplateService;
import com.spinach.example.mybatis.entity.TestUser;

@Service
public class JdbcTemplateServiceImpl implements JdbcTemplateService{
	@Autowired
	@Qualifier("testUserDaoImpl2")
	private TestUserDao testUserDao;
	
	@Override
	public int insert() {
		TestUser roncooUser = new TestUser();
		roncooUser.setUserName("测试");
		roncooUser.setCreateTime(new Date());
		int result = testUserDao.insert(roncooUser);
		return result;
	}
	@Override
	public int delete(Integer id ) {
		int result = testUserDao.deleteById(id);
		return result;
	}
	@Override
	public int update(Integer id ) {
		TestUser roncooUser = new TestUser();
		roncooUser.setId(id);
		roncooUser.setUserName("测试2");
		roncooUser.setCreateTime(new Date());
		int result = testUserDao.updateById(roncooUser);
		return result;
	}

	// 分页测试
	@Override
	public Page<TestUser> queryForPage() {
		Page<TestUser> result = testUserDao.queryForPage(1, 20, "测试");
		return result;
	}
	@Override
	public List<TestUser> selectAll() {
		List<TestUser> result = testUserDao.selectAll();
		return result;
	}
	@Override
	public TestUser selectById(Integer id) {
		TestUser result = testUserDao.selectById(id);
		return result;
	}
}
