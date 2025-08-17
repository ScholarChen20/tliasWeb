package com.itheima.service;

import com.itheima.pojo.PageResult;
import com.itheima.pojo.UserHbp;

import java.util.List;

public interface UserBhpService {
    List<UserHbp> list();

    UserHbp  getInfo(Integer id);

    void insert(UserHbp userHbp);

    void update(UserHbp userHbp);

    void delete(Integer id);

    UserHbp getInfoById(Integer id);

    PageResult page(Integer page, Integer pageSize);
}
