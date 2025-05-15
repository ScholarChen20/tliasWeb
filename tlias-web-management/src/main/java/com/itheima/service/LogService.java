package com.itheima.service;

import com.itheima.pojo.PageResult;

public interface LogService {
    PageResult page(Integer page, Integer pageSize);
}
