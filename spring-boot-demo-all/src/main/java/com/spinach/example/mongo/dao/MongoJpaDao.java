package com.spinach.example.mongo.dao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.spinach.example.datajpa.entity.TestUserLog;

public interface MongoJpaDao extends MongoRepository<TestUserLog, Integer>{

	TestUserLog findByUserName(String string);
	
	TestUserLog findByUserIp(String string);
	
	TestUserLog findByUserNameAndUserIp(String user, String ip);
	
	TestUserLog findByUserIpAndUserName(String ip, String user);

	Page<TestUserLog> findByUserName(String string, Pageable pageable);
}
