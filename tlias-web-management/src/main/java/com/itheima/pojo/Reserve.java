package com.itheima.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reserve {
    private Integer id; // 预约id
    private Integer userId; // 预约的用户id
    private Integer did;  // 预约的医生id
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime appointTime; // 预约时间
    private LocalDateTime updateTime; // 预约时间
    private String status; // 预约状态
    private String reason; // 预约原因
    private String dietAdvise; // 饮食建议
    private String exerciseAdvise; // 锻炼建议
    private String medicationAdvise; // 药物建议

    private String dName; // 预约的医生名字
    private String userName; // 预约的用户名字
    private String dept; // 预约的科室
}
