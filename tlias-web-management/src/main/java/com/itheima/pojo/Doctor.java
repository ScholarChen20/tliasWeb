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
    private String dept;
    private String title;
    private String hospital;
    private String introduce;
    private Integer status;//0.表示无法预约，1.表示可预约
    private String phone;
    private String avatar;  //头像
}
