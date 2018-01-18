package com.spinach.example.mybatisplus.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.spinach.example.mybatisplus.dao.UserMapper;
import com.spinach.example.mybatisplus.entity.User;
import com.spinach.example.mybatisplus.service.IUserService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author spinach123
 * @since 2018-01-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
	@Override
	public boolean deleteAll() {
		return retBool(baseMapper.deleteAll());
	}

	@Override
	public List<User> selectListBySQL() {
		return baseMapper.selectListBySQL();
	}
	@Override
	public boolean createTableSql(Map<String,Object>map) {
		baseMapper.createTableSql(map);
		return true;
	}

}
