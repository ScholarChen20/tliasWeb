package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.KnowledgeMapper;
import com.itheima.pojo.Knowledge;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.dto.KnowledgeQueryParam;
import com.itheima.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {
    @Autowired
    private KnowledgeMapper knowledgeMapper;
    @Override
    public List<Knowledge> list() {
        return knowledgeMapper.getAll();
    }

    @Override
    public Knowledge getInfo(Integer id) {
        return knowledgeMapper.getInfo(id);
    }

    @Override
    public void delete(Integer id) {
        knowledgeMapper.delete(id);
    }

    @Override
    public void save(Knowledge knowledge) {
        knowledgeMapper.insert(knowledge);
    }

    @Override
    public void update(Knowledge knowledge) {
        knowledge.setUpdateTime(LocalDateTime.now());
        knowledgeMapper.update(knowledge);
    }

    @Override
    public PageResult<Knowledge> page(KnowledgeQueryParam knowledgeQueryParam) {
        PageHelper.startPage(knowledgeQueryParam.getPage(), knowledgeQueryParam.getPageSize());// 开启分页
        List<Knowledge> list = knowledgeMapper.listPage(knowledgeQueryParam); // 查询分页数据
        Page<Knowledge> p = (Page<Knowledge>) list; // 强转为Page类型
        return new PageResult<>(p.getTotal(), p.getResult()); // 返回分页结果
    }

}
