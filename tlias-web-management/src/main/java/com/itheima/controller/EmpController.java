package com.itheima.controller;

import com.github.pagehelper.PageHelper;
import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {
    @Autowired
    private EmpService empService;

//    @GetMapping
//    public Result page(@RequestParam(defaultValue = "1") Integer page,
//                       @RequestParam(defaultValue = "10") Integer pageSize) {
//        log.info("分页查询page:{},pageSize:{}", page, pageSize);
//        PageResult<Emp> pageResult = empService.page(page, pageSize);
//        return Result.success(pageResult);
//    }
    // 实现分页模糊查询
    @GetMapping
    public Result page(EmpQueryParam empQueryParam) {
        log.info("分页模糊查询page:{},pageSize:{},name:{},gender:{},begin:{},end:{}",
                empQueryParam.getPage(), empQueryParam.getPageSize(), empQueryParam.getName(), empQueryParam.getGender(),
                empQueryParam.getBegin(), empQueryParam.getEnd());
        PageResult<Emp> pageResult = empService.page(empQueryParam);
        return Result.success(pageResult);
    }

    @PostMapping
    public Result save(@RequestBody Emp emp) { // RequestBody将请求体中json格式的数据转换为java对象
        log.info("新增员工:{}", emp);
        empService.save(emp);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids) {
        log.info("删除员工id:{}", ids);
        empService.delete(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        log.info("查询员工id:{}", id);
        Emp emp = empService.getById(id);
        return Result.success(emp);
    }

    @PutMapping
    public Result update(@RequestBody Emp emp) {
        log.info("更新员工:{}", emp);
        empService.update(emp);
        return Result.success();
    }

    /**
     * 查询全体员工
     */
    @GetMapping("/list")
    public Result findAll() {
        log.info("查询全体员工");
        List<Emp> emps = empService.findAll();
        return Result.success(emps);
    }
}
