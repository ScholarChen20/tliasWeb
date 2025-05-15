package com.itheima.controller;


import com.itheima.pojo.ClazzCountOption;
import com.itheima.pojo.JobOption;
import com.itheima.pojo.Result;
import com.itheima.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @RequestMapping("/empJobData")
    public Result getEmpJobData(){
        log.info("统计员工职位人数");
        JobOption jobOption = reportService.getEmpJobData();
        return Result.success(jobOption);
    }

    //性别统计
    @RequestMapping("/empGenderData")
    public Result getEmpGenderData(){
        log.info("统计员工性别人数");
        List<Map> genderList = reportService.getEmpGenderData();
        return Result.success(genderList);
    }

    //班级人数统计
    @RequestMapping("/studentCountData")
    public Result getStudentCountData(){
        log.info("统计班级人数");
        ClazzCountOption clazzCountOption = reportService.getStudentCountData();
        return Result.success(clazzCountOption);
    }

    //统计学员学历统计
    @RequestMapping("/studentDegreeData")
    public Result getStudentDegreeData(){
        log.info("统计学员学历统计");
        List<Map> degreeList = reportService.getStudentDegreeData();
        return Result.success(degreeList);
    }

}
