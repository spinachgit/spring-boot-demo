package com.spinach.example.rediscache.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.spinach.example.datajpa.entity.TestUserLog;
import com.spinach.example.rediscache.dao.TestUserLogDao;
import com.spinach.example.rediscache.service.TestUserLogCache;

/**
 * @author whh
 */
@CacheConfig(cacheNames = "Cache")
@Repository
public class TestUserLogCacheImpl implements TestUserLogCache {

	@Autowired
	private TestUserLogDao UserLogDao;

	@Cacheable(key = "#p0")
	@Override
	public TestUserLog selectById(Integer id) {
		System.out.println("查询功能，缓存找不到，直接读库, id=" + id);
		TestUserLog log = UserLogDao.findOne(id);
		return log;
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
