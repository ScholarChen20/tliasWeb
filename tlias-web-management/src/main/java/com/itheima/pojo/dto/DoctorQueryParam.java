package com.itheima.pojo.dto;

import lombok.Data;

@Data
public class DoctorQueryParam {
    private Integer page=1;
    private Integer pageSize=10;
    private String name;
    private String dept; // 科室id
    private String gender; // 性别
    private String title; // 职称
}
