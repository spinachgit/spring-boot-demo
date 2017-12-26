package com.spinach.example.rediscache.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spinach.example.datajpa.entity.TestUserLog;

public interface TestUserLogDao extends JpaRepository<TestUserLog, Integer>{

	@Query(value="select u from TestUserLog u where u.userName=?1")
	TestUserLog findByUserName(String string);
	
	TestUserLog findByUserNameAndUserIp(String string, String ip);

	Page<TestUserLog> findByUserName(String string, Pageable pageable);
}
