package com.fujin.bootdemo.service;

import com.fujin.bootdemo.entity.User;


public interface UserService {
    /**
    * @Description: 查询用户
    * @Param: id
    * @return: User
    */
    public User findUserInfo(Integer id);

    /**
     * @Description: 根据openid查询用户
     * @Param: openid
     * @return: User
     */
    public User queryByOid(String openid);


    /**
    * @Description: 新增用户
    * @Param:
    * @return:
    */
    public Integer insertSelective(User user);


    /**
    * @Description: 更新用户
    * @Param:  user
    * @return:  int
    */
    public Integer updateById(User user);
}
