package com.itheima.service;

import com.itheima.pojo.Knowledge;
import com.itheima.pojo.vo.PageResult;
import com.itheima.pojo.dto.KnowledgeQueryParam;

import java.util.List;

public interface KnowledgeService {
    List<Knowledge> list();

    Knowledge getInfo(Integer id);

    void delete(Integer id);

    void save(Knowledge knowledge);

    void update(Knowledge knowledge);


    PageResult<Knowledge> page(KnowledgeQueryParam knowledgeQueryParam);
}
