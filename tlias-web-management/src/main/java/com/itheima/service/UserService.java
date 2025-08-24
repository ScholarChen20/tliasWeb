package com.itheima.service;


import com.itheima.pojo.User;
import com.itheima.pojo.dto.UserLoginInfo;
import com.itheima.pojo.dto.UserQueryParam;
import com.itheima.pojo.dto.WxLoginInfo;
import com.itheima.pojo.vo.PageResult;

import java.util.List;

public interface UserService {
    User getInfo(Integer id);

    void update(User user);

    void save(User user);

    void delete(Integer id);

    UserLoginInfo login(User user);

    WxLoginInfo getWxLoginInfo(User user);

    List<User> list();

    PageResult<User> page(UserQueryParam userQueryParam);

    void deleteByIds(List<Integer> ids);
}
