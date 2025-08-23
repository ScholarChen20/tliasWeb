package com.itheima.controller;


import com.itheima.pojo.Knowledge;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.pojo.dto.KnowledgeQueryParam;
import com.itheima.service.KnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {
    @Autowired
    private KnowledgeService knowledgeService;

    /**
     * 条件分页查询列表
     */
    @GetMapping
    public Result page(KnowledgeQueryParam knowledgeQueryParam) {
        log.info("分页模糊查询血压记录page:{},pageSize:{},author:{},text:{},begin:{},end:{}",
                knowledgeQueryParam.getPage(), knowledgeQueryParam.getPageSize(), knowledgeQueryParam.getAuthor(),knowledgeQueryParam.getText(),
                knowledgeQueryParam.getBegin(), knowledgeQueryParam.getEnd());
        PageResult<Knowledge> knowledge = knowledgeService.page(knowledgeQueryParam);
        log.info("knowledge: {}", knowledge);
        return Result.success(knowledge);
    }
    /**
     * 获取知识列表
     */
    @GetMapping("/list")
    public Result list() {
        List<Knowledge> knowledgeList = knowledgeService.list();
        log.info("knowledgeList: {}", knowledgeList);
        return Result.success(knowledgeList);
    }

    /**
     * 根据id获取知识详情
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        Knowledge knowledge = knowledgeService.getInfo(id);
        log.info("获取的知识详情: {}", knowledge);
        return Result.success(knowledge);
    }

    /**
     * 删除知识
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("删除知识, id: {}", id);
        knowledgeService.delete(id);
        return Result.success();
    }

    /**
     * 添加或修改知识
     */
    @PostMapping("/save")
    public Result save(@RequestBody Knowledge knowledge) {
        log.info("添加或修改知识: {}", knowledge);
        knowledgeService.save(knowledge);
        return Result.success();
    }
    /**
     * 修改知识
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Knowledge knowledge) {
        log.info("修改知识: {}", knowledge);
        knowledgeService.update(knowledge);
        return Result.success();
    }
}
