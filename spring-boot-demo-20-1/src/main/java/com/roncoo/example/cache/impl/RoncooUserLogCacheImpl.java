package com.roncoo.example.cache.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.roncoo.example.bean.RoncooUserLog;
import com.roncoo.example.cache.RoncooUserLogCache;
import com.roncoo.example.dao.RoncooUserLogDao;

/**
 * @CacheConfig:类注解
 * @Cacheable:注解在使用的方法中使用。
 * @author:whh
 * @date:2017年12月4日下午3:19:14
 */
@CacheConfig(cacheNames = "roncooCache")
@Repository
public class RoncooUserLogCacheImpl implements RoncooUserLogCache {

	@Autowired
	private RoncooUserLogDao roncooUserLogDao;
	
	@Override
	public RoncooUserLog add(RoncooUserLog log) {
		return roncooUserLogDao.save(log);
	}
	
	@Cacheable(key = "#p0")
	@Override
	public RoncooUserLog selectById(Integer id) {
		System.out.println("查询功能，缓存找不到，直接读库, id=" + id);
		RoncooUserLog result = roncooUserLogDao.findOne(id);
		return result;
	}

	@CachePut(key = "#p0")
	@Override
	public RoncooUserLog updateById(RoncooUserLog roncooUserLog) {
		System.out.println("更新功能，更新缓存，直接写库, id=" + roncooUserLog);
		return roncooUserLogDao.save(roncooUserLog);
	}

	@CacheEvict(key = "#p0")
	@Override
	public String deleteById(Integer id) {
		System.out.println("删除功能，删除缓存，直接写库, id=" + id);
		return "清空缓存成功";
	}

}
