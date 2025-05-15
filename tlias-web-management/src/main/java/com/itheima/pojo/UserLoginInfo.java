package com.itheima.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginInfo {
    private Integer id; // 编号
    private String username;  ///  用户名
    private String name; // 真实姓名
    private String token;
}
