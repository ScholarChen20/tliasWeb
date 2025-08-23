package com.itheima.pojo.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ReserveQueryParam {
    private Integer page=1;
    private Integer pageSize=10;
    private Integer userId;
    private Integer dId; //
    private String status; //就诊状态
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate begin;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate end;
}
