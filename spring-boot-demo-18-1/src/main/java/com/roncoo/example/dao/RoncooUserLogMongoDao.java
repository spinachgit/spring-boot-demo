package com.roncoo.example.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.roncoo.example.bean.RoncooUserLog;

/**
 * 自测结果：只有接口，没有实现，实现查询，按对应的方法名里的映射字段查询。
 * @author:whh
 * @date:2017年12月4日下午1:35:17
 */
public interface RoncooUserLogMongoDao extends MongoRepository<RoncooUserLog, Integer> {
	
	RoncooUserLog findByUserName(String string);

	RoncooUserLog findByUserNameAndUserIp(String string, String ip);
	
	RoncooUserLog findByUserIp(String ip);
	List<RoncooUserLog> findByUserIpAndUserName(String ip,String temp);

	Page<RoncooUserLog> findByUserName(String string, String string2, Pageable pageable);
	
	Page<RoncooUserLog> findByUserName(String string, Pageable pageable);
}
