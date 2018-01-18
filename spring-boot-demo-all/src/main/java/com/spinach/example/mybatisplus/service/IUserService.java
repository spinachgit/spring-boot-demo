package com.spinach.example.mybatisplus.service;

import com.spinach.example.mybatisplus.entity.User;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author spinach123
 * @since 2018-01-16
 */
public interface IUserService extends IService<User> {

	boolean deleteAll();

	Object selectListBySQL();

	boolean createTableSql(Map<String, Object> map);

}
