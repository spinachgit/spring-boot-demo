package com.spinach.example.cache.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.spinach.example.bean.TestUserLog;
import com.spinach.example.cache.service.TestUserLogCache;
import com.spinach.example.dao.TestUserLogDao;

/**
 * redis提供缓存：相关引用
 * <dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-data-redis</artifactId>
	</dependency>
	注意：经过测试：@Cacheable(key="#p0")：第一个参数：只能为#p0
	https://stackoverflow.com/questions/41319555/spring-cache-null-key-returned-for-cache-operation
	@Cacheable(value="byUserId",key="#p0")
    List<UserApi> findByUserId(String userId);

    @CacheEvict(value="byUserId",key="#p0.userId")
    void save(UserApi userApi);
 * @author:whh
 * @date:2018年1月19日下午2:49:46
 */
@CacheConfig(cacheNames = "redisCache_userLog")
@Repository
public class TestUserLogCacheImpl implements TestUserLogCache {

	@Autowired
	private TestUserLogDao UserLogDao;
	
	@Cacheable(key="#p0")
	@Override
	public TestUserLog selectById(Integer id) {
		System.out.println("查询功能，缓存找不到，直接读库, id=" + id);
		return UserLogDao.findOne(id);
	}

	@CachePut(key = "#p0")
	@Override
	public TestUserLog updateById(TestUserLog UserLog) {
		System.out.println("更新功能，更新缓存，直接写库, id=" + UserLog);
		return UserLogDao.save(UserLog);
	}

	@CacheEvict(key = "#p0")
	@Override
	public String deleteById(Integer id) {
		System.out.println("删除功能，删除缓存，直接写库, id=" + id);
		return "清空缓存成功";
	}

}
