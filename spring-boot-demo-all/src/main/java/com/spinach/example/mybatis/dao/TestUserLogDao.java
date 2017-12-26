package com.spinach.example.mybatis.dao;

import org.apache.ibatis.annotations.Mapper;

import com.spinach.example.mybatis.base.MybatisBaseDao;
import com.spinach.example.mybatis.entity.TestUserLog;


/**
 * Mapper类
 * 
 * @author spinach
 */
@Mapper
public interface TestUserLogDao extends MybatisBaseDao<TestUserLog> {

}