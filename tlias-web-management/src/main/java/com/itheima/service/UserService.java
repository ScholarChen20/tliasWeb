package com.itheima.service;


import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.pojo.UserLoginInfo;
import com.itheima.pojo.WxLoginInfo;

import java.util.List;

public interface UserService {
    User getInfo(Integer id);

    void update(User user);

    void save(User user);

    void delete(Integer id);

    UserLoginInfo login(User user);

    WxLoginInfo getWxLoginInfo(User user);

    List<User> list();
}
