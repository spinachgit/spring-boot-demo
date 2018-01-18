package com.spinach.example.mybatisplus.dao;

import com.spinach.example.mybatisplus.entity.SystemUser;

import org.apache.ibatis.annotations.Mapper;

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
public interface SystemUserMapper extends BaseMapper<SystemUser> {

}
