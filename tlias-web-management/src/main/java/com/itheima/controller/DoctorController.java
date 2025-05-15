package com.itheima.controller;


import com.itheima.pojo.Doctor;
import com.itheima.pojo.Result;
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
