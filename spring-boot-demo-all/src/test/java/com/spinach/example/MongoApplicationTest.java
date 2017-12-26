package com.spinach.example;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.junit4.SpringRunner;

import com.spinach.example.datajpa.entity.TestUserLog;
import com.spinach.example.mongo.dao.MongoJpaDao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoApplicationTest {
	@Autowired
	private MongoJpaDao testUserLogMongoDao;

	@Test
	public void insert() {
		for(int i=0;i<1000;i++){
		TestUserLog entity = new TestUserLog();
		entity.setId(i);
		entity.setUserName("春秋");
		entity.setUserIp("192.168.0.1");
		entity.setCreateTime(new Date());
		testUserLogMongoDao.save(entity);
		}
	}

	@Test
	public void delete() {
		testUserLogMongoDao.delete(1);
	}

	@Test
	public void update() {
		TestUserLog entity = new TestUserLog();
		entity.setId(2);
		entity.setUserName("春秋2");
		entity.setUserIp("192.168.0.1");
		entity.setCreateTime(new Date());
		testUserLogMongoDao.save(entity);
	}

	@Test
	public void select() {
		TestUserLog result = testUserLogMongoDao.findOne(2);
		System.out.println(result);
	}

	@Test
	public void select2() {
		TestUserLog result = testUserLogMongoDao.findByUserName("春秋");
		System.out.println(result);
		
		TestUserLog result2 = testUserLogMongoDao.findByUserNameAndUserIp("春秋","192.168.0.1");
		System.out.println(result2);
		
		TestUserLog result3 = testUserLogMongoDao.findByUserIp("春秋");
		System.out.println(result3);
		
		TestUserLog result4 = testUserLogMongoDao.findByUserIpAndUserName("192.168.0.1","春秋");
		System.out.println(result4);
	}

	// 分页
	@Test
	public void queryForPage() {
		Pageable pageable = new PageRequest(0, 20, new Sort(new Order(Direction.DESC, "id")));
		Page<TestUserLog> result = testUserLogMongoDao.findAll(pageable);
		System.out.println(result.getContent());
		
		Page<TestUserLog> result2 = testUserLogMongoDao.findByUserName("春秋", pageable);
		System.out.println(result2.getContent());
	}


}
