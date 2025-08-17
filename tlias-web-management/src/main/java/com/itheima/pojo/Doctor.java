package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
