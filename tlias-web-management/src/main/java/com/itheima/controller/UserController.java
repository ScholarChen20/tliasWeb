package com.itheima.controller;


import com.itheima.pojo.Clazz;
import com.itheima.pojo.Result;
import com.itheima.pojo.User;
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
     * 删除用户信息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        userService.delete(id);
        log.info("删除用户信息，id：{}",id);
        return  Result.success();
    }


}
