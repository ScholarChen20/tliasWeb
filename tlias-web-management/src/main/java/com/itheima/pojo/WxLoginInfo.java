package com.itheima.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxLoginInfo {
    private Integer id; // 编号
    private String openid;  ///  openid
    private String token;
}
