package com.spinach.example.base.dao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.spinach.example.base.bean.TestUserLog;

public interface TestUserLogMongoDao extends MongoRepository<TestUserLog, Integer>{

	TestUserLog findByUserName(String string);
	
	TestUserLog findByUserNameAndUserIp(String string, String ip);

	Page<TestUserLog> findByUserName(String string, Pageable pageable);
}
