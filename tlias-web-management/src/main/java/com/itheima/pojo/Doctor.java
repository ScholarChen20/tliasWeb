package com.itheima.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    private Integer id;
    private String name;
    private String dept; // 科室
    /**
     * 职称
     */
    private String title;
    /**
     * 所属医院
     */
    private String hospital;
    private String gender; // 0:女，1：男
    private String introduce; // 简介
    private Integer status;//0.表示无法预约，1.表示可预约
    private String phone;
    private String avatar;  //头像

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
