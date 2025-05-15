package com.itheima.exception;

import com.itheima.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 全局异常处理，拦截所有异常，统一处理
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler //ExceptionHandler 拦截所有异常
    public Result handleException(Exception e) {
        log.error("程序出错了：{}", e);
        return Result.error("系统异常，请联系管理员");
    }

    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        log.error("程序出错了，参数错误：{}", e);
        String message = e.getMessage();
        int i = message.indexOf("Duplicate entry");
        String errorMsg = message.substring(i);
        String[] arr = errorMsg.split(" ");
        return Result.error(arr[2]+"已存在");
    }
}
