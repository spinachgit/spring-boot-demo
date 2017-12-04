package com.roncoo.example;

import java.util.Date;
import java.util.List;

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

import com.roncoo.example.bean.RoncooUserLog;
import com.roncoo.example.dao.RoncooUserLogMongoDao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDemo181ApplicationTests {
	@Autowired
	private RoncooUserLogMongoDao roncooUserLogMongoDao;

	@Test
	public void insert() {
		for(int i=0;i<1000;i++){
		RoncooUserLog entity = new RoncooUserLog();
		entity.setId(i);
		entity.setUserName("无境");
		entity.setUserIp("192.168.0.1");
		entity.setCreateTime(new Date());
		roncooUserLogMongoDao.save(entity);
		}
	}

	@Test
	public void delete() {
		roncooUserLogMongoDao.delete(1);
	}

	@Test
	public void update() {
		RoncooUserLog entity = new RoncooUserLog();
		entity.setId(2);
		entity.setUserName("无境2");
		entity.setUserIp("192.168.0.1");
		entity.setCreateTime(new Date());
		roncooUserLogMongoDao.save(entity);
	}

	@Test
	public void select() {
		RoncooUserLog result = roncooUserLogMongoDao.findOne(2);
		System.out.println(result);
	}

	@Test
	public void select2() {
		RoncooUserLog result = roncooUserLogMongoDao.findByUserName("无境");
		System.out.println(result);
		
		RoncooUserLog result2 = roncooUserLogMongoDao.findByUserNameAndUserIp("无境","192.168.0.1");
		System.out.println(result2);
		
		RoncooUserLog result3 = roncooUserLogMongoDao.findByUserIp("无境");
		System.out.println(result3);
		
		List<RoncooUserLog> result4 = roncooUserLogMongoDao.findByUserIpAndUserName("192.168.0.1",null);
		System.out.println(result4);
	}

	// 分页
	@Test
	public void queryForPage() {
		Pageable pageable = new PageRequest(0, 20, new Sort(new Order(Direction.DESC, "id")));
		Page<RoncooUserLog> result = roncooUserLogMongoDao.findAll(pageable);
		System.out.println(result.getContent());
		
		Page<RoncooUserLog> result2 = roncooUserLogMongoDao.findByUserName("无境", pageable);
		System.out.println(result2.getContent());
	}


}
