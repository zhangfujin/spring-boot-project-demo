package com.fujin.bootdemo.service.impl;

import com.fujin.bootdemo.dao.UserMapper;
import com.fujin.bootdemo.entity.User;
import com.fujin.bootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserInfo(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User queryByOid(String openid) {
        return userMapper.queryByOid(openid);
    }

    @Override
    public Integer insertSelective(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public Integer updateById(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
