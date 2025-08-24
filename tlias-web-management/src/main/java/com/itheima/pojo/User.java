package com.itheima.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    private String openid;  //微信openid
    private String nickname; //微信昵称

    private String userId;
    private String password; //密码
    private String image;
    private String username;//用户名
    private String name;//姓名
    private String sex;
    private LocalDate birthDate;
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
