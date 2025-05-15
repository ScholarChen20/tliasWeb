package com.itheima.service;

import com.itheima.pojo.Dept;

import java.util.List;

public interface DeptService {
    void save(Dept dept);
    List<Dept> findAll();
    void delete(Integer id);
    void update(Dept dept);

    Dept getById(Integer id);
}
