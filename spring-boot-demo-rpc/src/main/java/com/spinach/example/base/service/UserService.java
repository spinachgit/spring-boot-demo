package com.spinach.example.base.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spinach.example.base.bean.TestUser;
import com.spinach.example.base.bean.TestUserLog;
import com.spinach.example.base.dao.TestUserDao;
import com.spinach.example.base.dao.TestUserLogDao;

@Service
public class UserService {

	@Autowired
	private TestUserDao UserDao;

	@Autowired
	private TestUserLogDao UserLogDao;

	/**
	 * 用户注册
	 * 
	 * @return
	 */
	@Transactional
	public String register(String name, String ip) {
		// 1.添加用户
		TestUser User = new TestUser();
		User.setName(name);
		User.setCreateTime(new Date());
		UserDao.insert(User);
		
		// 测试使用
		//boolean flag = true;
		//if (flag) {
		//	throw new RuntimeException();
		//}

		// 2.添加注册日志
		TestUserLog UserLog = new TestUserLog();
		UserLog.setUserName(name);
		UserLog.setUserIp(ip);
		UserLog.setCreateTime(new Date());
		UserLogDao.save(UserLog);

		return "success";
	}

}
