package com.itheima.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class DoctorQueryParam {
    private Integer page=1;
    private Integer pageSize=10;
    private String name;
    private String dept; // 科室id
    private String gender; // 性别
    private String title; // 职称
}
