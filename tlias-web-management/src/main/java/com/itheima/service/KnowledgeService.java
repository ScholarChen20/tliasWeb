package com.itheima.service;

import com.itheima.pojo.Knowledge;
import com.itheima.pojo.PageResult;

import java.util.List;

public interface KnowledgeService {
    List<Knowledge> list();

    Knowledge getInfo(Integer id);

    void delete(Integer id);

    void save(Knowledge knowledge);

    void update(Knowledge knowledge);

    PageResult page(Integer page, Integer pageSize);
}
