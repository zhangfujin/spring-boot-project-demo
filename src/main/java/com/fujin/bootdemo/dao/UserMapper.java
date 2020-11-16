package com.fujin.bootdemo.dao;

import com.fujin.bootdemo.entity.User;

public interface UserMapper {
    User selectByPrimaryKey(Integer id);

    User queryByOid(String openId);

    Integer insertSelective(User user);

    Integer updateByPrimaryKeySelective(User user);
}
