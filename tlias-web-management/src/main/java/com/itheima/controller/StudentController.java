package com.itheima.controller;

import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.pojo.Student;
import com.itheima.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 添加学生
     */
    @PostMapping
    public Result save(@RequestBody Student student){
        studentService.save(student);
        return Result.success();
    }

    /**
     * 条件分页查询
     */
    @GetMapping
    public Result page(String name ,
                       Integer degree,
                       Integer clazzId,
                       @RequestParam(defaultValue = "1") Integer page ,
                       @RequestParam(defaultValue = "10") Integer pageSize){
        PageResult pageResult = studentService.page(name,degree,clazzId,page,pageSize);
        return Result.success(pageResult);
    }

    /**
     * 根据ID查询学生信息
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        Student student = studentService.getInfo(id);
        return Result.success(student);
    }

    /**
     * 修改学生信息
     */
    @PutMapping
    public Result update(@RequestBody Student student){
        studentService.update(student);
        return Result.success();
    }

    /**
     * 删除学生信息
     */
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        studentService.delete(ids);
        return Result.success();
    }

    /**
     * 违纪处理
     */
    @PutMapping("/violation/{id}/{score}")
    public Result violationHandle(@PathVariable Integer id , @PathVariable Integer score){
        studentService.violationHandle(id, score);
        return Result.success();
    }

}
