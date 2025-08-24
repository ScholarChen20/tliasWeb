package com.itheima.pojo.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UserQueryParam {
    private Integer page=1;
    private Integer pageSize=10;
    private String name;
    private String sex; // 性别
    private String phone; // 手机号
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate begin;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate end;
}
