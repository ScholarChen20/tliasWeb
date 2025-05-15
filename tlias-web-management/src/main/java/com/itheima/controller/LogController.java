package com.itheima.controller;

import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.service.LogService;
//import com.mysql.jdbc.log.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    private LogService logService;

    //  日志记录
    @RequestMapping("/page")
    public Result page(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        log.info("日志记录");
        PageResult pageResult = logService.page(page, pageSize);
        return Result.success(pageResult);
    }
}
