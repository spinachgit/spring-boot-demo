package com.spinach.example.cache.service;

import com.spinach.example.bean.TestUserLog;

/**
 * @author whh
 */
public interface TestUserLogCache {

	/**
	 * 查询
	 * 
	 * @param id
	 * @return
	 */
	TestUserLog selectById(Integer id);

	/**
	 * 更新
	 * 
	 * @param UserLog
	 * @return
	 */
	TestUserLog updateById(TestUserLog UserLog);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	String deleteById(Integer id);
}
