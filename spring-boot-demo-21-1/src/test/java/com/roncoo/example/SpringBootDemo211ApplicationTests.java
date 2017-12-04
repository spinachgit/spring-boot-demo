package com.roncoo.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.roncoo.example.component.RoncooJmsComponent;
import com.roncoo.example.component.RoncooRedisComponent;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDemo211ApplicationTests {
	@Autowired
	private RoncooJmsComponent roncooJmsComponent;
	@Autowired
	private RoncooRedisComponent redisComponent;

	@Test
	public void jmsSendTest() {
		roncooJmsComponent.send("hello world");
	}
	
	@Test
	public void redisTest() {
		redisComponent.set("spinach","hello world");
		String result = redisComponent.get("spinach");
		System.out.println(result);
	}



}
