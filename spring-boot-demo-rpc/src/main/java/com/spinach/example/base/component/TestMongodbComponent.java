package com.spinach.example.base.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.spinach.example.base.bean.TestUser;

/**
 * @author whh
 */
@Component
public class TestMongodbComponent {

	@Autowired
	private MongoTemplate mongoTemplate;

	public void insert(TestUser User) {
		mongoTemplate.insert(User);
	}

	public void deleteById(int id) {
		Criteria criteria = Criteria.where("id").in(id);
		Query query = new Query(criteria);
		mongoTemplate.remove(query, TestUser.class);
	}
	
	public void updateById(TestUser User) {
		Criteria criteria = Criteria.where("id").in(User.getId());
		Query query = new Query(criteria);
		Update update = new Update();
		update.set("name", User.getName());
		update.set("createTime", User.getCreateTime());
		mongoTemplate.updateMulti(query, update, TestUser.class);
	}
	
	public TestUser selectById(int id) {
		Criteria criteria = Criteria.where("id").in(id);
		Query query = new Query(criteria);
		return mongoTemplate.findOne(query, TestUser.class);
	}
}
