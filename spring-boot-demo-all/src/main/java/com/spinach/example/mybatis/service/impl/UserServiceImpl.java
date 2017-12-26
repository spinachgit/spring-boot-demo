package com.spinach.example.mybatis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spinach.example.mybatis.dao.PersonDao;
import com.spinach.example.mybatis.dao.TestUserDao;
import com.spinach.example.mybatis.entity.Person;
import com.spinach.example.mybatis.entity.TestUser;
import com.spinach.example.mybatis.service.UserService;

@Service("mybatisUser")
public class UserServiceImpl implements UserService{
	@Autowired
	private TestUserDao testUserDao ; 
	@Autowired
	private PersonDao personDao ; 
	
	@Override
	public void insert(TestUser user){
		Person person = new Person();
		person.setAddress("aaaaaaa");
		person.setAge(11);
		person.setName("test");
		this.personDao.insert(person);
		this.testUserDao.insert(user);
	}
	@Override
	public void delete(TestUser user){
		this.testUserDao.delete(user);
	}
	@Override
	public void update(TestUser user){
		this.testUserDao.update(user);
	}
	@Override
	public TestUser selectOne(TestUser user){
		Person person = personDao.selectByPrimaryKey(1);
		System.out.println(person);
		return this.testUserDao.selectOne(user);
	}
	@Override
	public List<TestUser> selectList(TestUser user){
		return this.testUserDao.selectList(user);
	}
	

}
