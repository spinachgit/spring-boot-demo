package com.spinach.example.mybatisplus.service.impl;

import com.spinach.example.mybatisplus.entity.SystemUser;
import com.spinach.example.mybatisplus.dao.SystemUserMapper;
import com.spinach.example.mybatisplus.service.ISystemUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author spinach123
 * @since 2018-01-16
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements ISystemUserService {

}
