package com.spinach.example.mybatis.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import com.spinach.example.mybatis.base.MybatisBaseDao;
import com.spinach.example.mybatis.entity.Person;


/**
 * Mapper类
 * 方法二：使用注解实现
 * @author spinach
 */
@Mapper
public interface PersonDao extends MybatisBaseDao<Person> {
	@Insert(value = "insert into person (name,age, address) values (#{name,jdbcType=VARCHAR},#{age,jdbcType=NUMERIC},#{address,jdbcType=VARCHAR}) ")
	int insert(Person person);

	@Select(value = "select * from person where id = #{id,jdbcType=INTEGER}")
	@Results(value = { @Result(column = "age", property = "age", jdbcType = JdbcType.VARCHAR) })
	Person selectByPrimaryKey(Integer id);
}