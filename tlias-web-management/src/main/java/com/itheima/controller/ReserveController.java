package com.itheima.controller;


import com.itheima.pojo.Reserve;
import com.itheima.pojo.Result;
import com.itheima.pojo.UserHbp;
import com.itheima.service.ReserveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reserves")
public class ReserveController {

    @Autowired
    private ReserveService reserveService;

    /**
     * 查询用户预约信息
     */
    @GetMapping("/user/{id}")
    public Result getUserInfo(@PathVariable Integer id){
        List<Reserve> reserve = reserveService.userList(id);
        log.info("查询用户的预约记录：{}",reserve);
        return Result.success(reserve);
    }
    /**
     * 查询用户信息
     */
    @GetMapping("/doctor/{d_id}")
    public Result getDoctorInfo(@PathVariable Integer d_id){
        List<Reserve> reserve = reserveService.doctorList(d_id);
        log.info("查询医生的预约记录：{}",reserve);
        return Result.success(reserve);
    }
    /**
     * 更新用户信息
     */
    @PutMapping
    public Result updateInfo(@RequestBody Reserve reserve){
        reserveService.update(reserve);
        log.info("更新用户预约信息：{}",reserve);
        return  Result.success();
    }
    /**
     * 插入用户信息
     */
    @PostMapping
    public Result save(@RequestBody Reserve reserve){
        reserveService.save(reserve);
        log.info("插入用户预约信息：{}",reserve);
        return  Result.success();
    }

    /**
     * 删除用户信息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        reserveService.delete(id);
        log.info("删除预约信息，id：{}",id);
        return  Result.success();
    }
    /*
     * 根据用户id查询医生建议信息
     */
    @GetMapping("/advice/{id}")
    public Result getAdvice(@PathVariable Integer id){
        log.info("查询患者的预约医生信息");
        List<Reserve> reserves = reserveService.getAdvice(id);
        return Result.success(reserves);
    }



}
