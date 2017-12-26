package com.spinach.example;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.spinach.example.mybatis.entity.TestUser;
import com.spinach.example.mybatis.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisApplicationTest {
	@Resource(name="mybatisUser")
	private UserService userService;

	@Test
	public void insert() {
		TestUser user = new TestUser();
		user.setUserName("mybatis");
		user.setCreateTime(new Date());
		userService.insert(user);
	}

	@Test
	public void delete() {
		TestUser user = new TestUser();
		user.setId(4);
		userService.delete(user);
	}

	@Test
	public void update() {
		TestUser entity = new TestUser();
		entity.setId(2);
		entity.setUserName("春秋2");
		entity.setCreateTime(new Date());
		userService.update(entity);
	}

	@Test
	public void select() {
		TestUser entity = new TestUser();
		entity.setId(2);
		TestUser result = userService.selectOne(entity);
		System.out.println(result);
	}

	@Test
	public void selectList() {
		TestUser entity = new TestUser();
		List<TestUser> result = userService.selectList(entity);
		System.out.println(result);
	}

}
