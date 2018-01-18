package com.spinach.example.mybatisplus.dao;

import com.spinach.example.mybatisplus.entity.User;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author spinach123
 * @since 2018-01-16
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
	/**
	 * 自定义注入方法
	 */
	Integer deleteAll();

    @Select("select test_id as id, name, age, test_type from user")
    List<User> selectListBySQL();
    
    void createTableSql(Map<String,Object> map);
}
