package com.itheima.controller;


import com.itheima.pojo.Clazz;
import com.itheima.pojo.Knowledge;
import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.pojo.dto.KnowledgeQueryParam;
import com.itheima.pojo.dto.UserQueryParam;
import com.itheima.pojo.vo.PageResult;
import com.itheima.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService  userService;

    /**
     * 条件分页查询列表
     */
    @GetMapping
    public Result page(UserQueryParam userQueryParam) {
        log.info("分页模糊查询用户记录page:{},pageSize:{},name:{},sex:{},phone:{},begin:{},end:{}",
                userQueryParam.getPage(), userQueryParam.getPageSize(), userQueryParam.getName(),userQueryParam.getSex(),
                userQueryParam.getPhone(),userQueryParam.getBegin(), userQueryParam.getEnd());
        PageResult<User> user = userService.page(userQueryParam);
        log.info("user: {}", user);
        return Result.success(user);
    }
    /**
     * 查询所有用户信息
     */
    @GetMapping("/list")
    public Result getUser(){
        List<User> user = userService.list();
        log.info("查询所有的用户");
        return  Result.success(user);
    }
    /**
     * 查询用户信息
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        User user = userService.getInfo(id);
        log.info("查询用户信息，用户对象：{}",user);
        return Result.success(user);
    }
    /**
     * 更新用户信息
     */
    @PutMapping
    public Result updateInfo(@RequestBody User user){
        userService.update(user);
        log.info("更新用户信息，用户对象：{}",user);
        return  Result.success();
    }
    /**
     * 插入用户信息
     */
    @PostMapping
    public Result save(@RequestBody User user){
        userService.save(user);
        log.info("保存用户信息，用户对象：{}",user);
        return  Result.success();
    }

    /**
     * 批量删除用户信息
     */
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids){
        userService.delete(ids);
        log.info("删除用户信息，id：{}",ids);
        return  Result.success();
    }


}
