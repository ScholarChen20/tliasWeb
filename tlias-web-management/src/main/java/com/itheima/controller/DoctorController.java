package com.itheima.controller;


import com.itheima.pojo.*;
import com.itheima.pojo.dto.DoctorQueryParam;
import com.itheima.pojo.vo.PageResult;
import com.itheima.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public Result page(DoctorQueryParam doctorQueryParam) {
        log.info("分页模糊查询page:{},pageSize:{},name:{},gender:{},dept:{},title:{}",
                doctorQueryParam.getPage(), doctorQueryParam.getPageSize(), doctorQueryParam.getName(), doctorQueryParam.getGender(),
                doctorQueryParam.getDept(), doctorQueryParam.getTitle());
        PageResult<Doctor> pageResult = doctorService.page(doctorQueryParam);
        return Result.success(pageResult);
    }
    /**
     * 查询医生信息
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        Doctor doctor = doctorService.getInfo(id);
        log.info("查询医生信息，医生对象：{}",doctor);
        return Result.success(doctor);
    }
    /**
     * 更新医生信息
     */
    @PutMapping
    public Result updateInfo(@RequestBody Doctor doctor){
        doctorService.update(doctor);
        log.info("更新医生信息，医生对象：{}",doctor);
        return  Result.success();
    }
    /**
     * 插入医生信息
     */
    @PostMapping
    public Result save(@RequestBody Doctor doctor){
        doctorService.save(doctor);
        log.info("保存医生信息，医生对象：{}",doctor);
        return  Result.success();
    }

    /**
     * 删除医生信息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        doctorService.delete(id);
        log.info("删除医生信息，id：{}",id);
        return  Result.success();
    }

    @GetMapping("/list")
    public Result list(){
        List<Doctor> doctors = doctorService.list();
        log.info("查询所有医生信息");
        return Result.success(doctors);
    }


}
