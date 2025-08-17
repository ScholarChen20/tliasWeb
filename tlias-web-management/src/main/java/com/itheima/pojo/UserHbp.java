package com.itheima.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHbp {
    private Integer id;
    private Integer userid;
    private String userName;
    private String sbp;  // 收缩压
    private String dbp;  // 舒张压
    private String heart;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime writeTime;
    private String writeType;
    private String situation;
    private LocalDateTime updateTime;

}
