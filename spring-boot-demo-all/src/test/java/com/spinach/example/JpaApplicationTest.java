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

import com.spinach.example.datajpa.dao.TestUserLogJpaDao;
import com.spinach.example.datajpa.entity.TestUserLog;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaApplicationTest {
	@Autowired
	private TestUserLogJpaDao jpaDao;

	@Test
	public void insert() {
		TestUserLog entity = new TestUserLog();
		entity.setUserName("春秋");
		entity.setUserIp("192.168.0.1");
		entity.setCreateTime(new Date());
		//entity.setAddCol1("add");
		jpaDao.save(entity);
	}

	@Test
	public void delete() {
		jpaDao.delete(1);
	}

	@Test
	public void update() {
		TestUserLog entity = new TestUserLog();
		entity.setId(2);
		entity.setUserName("春秋2");
		entity.setUserIp("192.168.0.1");
		entity.setCreateTime(new Date());
		jpaDao.save(entity);
	}

	@Test
	public void select() {
		TestUserLog result = jpaDao.findOne(2);
		System.out.println(result);
	}

	@Test
	public void select2() {
		TestUserLog result = jpaDao.findByUserName("春秋2");
		System.out.println(result);
	}

	// 分页
	@Test
	public void queryForPage() {
		Pageable pageable = new PageRequest(0, 20, new Sort(new Order(Direction.DESC, "id")));
		// Page<TestUserLog> result = jpaDao.findByUserName("春秋2",
		// pageable);
		Page<TestUserLog> result = jpaDao.findAll(pageable);
		System.out.println(result.getContent());
	}

}
