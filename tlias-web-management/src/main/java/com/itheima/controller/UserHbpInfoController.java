package com.itheima.controller;

import com.itheima.mapper.UserHbpMapper;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.pojo.UserHbp;
import com.itheima.pojo.dto.RecordQueryParam;
import com.itheima.service.UserBhpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/userHbpInfos")
@RestController
public class UserHbpInfoController {
    @Autowired
    private UserBhpService userHbpService;

    /**
     * 条件分页查询列表
     */
    @GetMapping
    public Result page(RecordQueryParam recordQueryParam) {
        log.info("分页模糊查询血压记录page:{},pageSize:{},userid:{},,begin:{},end:{}",
                recordQueryParam.getPage(), recordQueryParam.getPageSize(), recordQueryParam.getUserId(),
                recordQueryParam.getBegin(), recordQueryParam.getEnd());
        PageResult<UserHbp> record = userHbpService.page(recordQueryParam);
        log.info("血压记录列表: {}", record);
        return Result.success(record);
    }
    /**
     * 查询血压记录信息
     */
    @GetMapping("/list")
    public Result list(){
        log.info("查询所有血压记录信息");
        List<UserHbp> userHbp = userHbpService.list();
        return Result.success(userHbp);
    }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("查询用户信息");
        UserHbp userHbp = userHbpService.getInfo(id);
        return Result.success(userHbp);
    }
    @GetMapping("/latest/{id}")
    public Result getLatestInfo(@PathVariable Integer id){
        log.info("查询患者最新血压信息：{}",id);
        UserHbp userHbp = userHbpService.getInfoById(id);
        return Result.success(userHbp);
    }


    @PostMapping
    public Result save(@RequestBody UserHbp userHbp){
        log.info("新增用户信息:{}",userHbp);
        userHbpService.insert(userHbp);
        return Result.success();
    }

    @PutMapping()
    public Result update(@RequestBody UserHbp userHbp){
        log.info("更新用户信息:{}",userHbp);
        userHbpService.update(userHbp);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("删除用户信息:{}",id);
        userHbpService.delete(id);
        return Result.success();
    }


}
