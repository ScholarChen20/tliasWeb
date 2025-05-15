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
    private String sbp;
    private String dbp;
    private String heart;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime writeTime;
    private String writeType;
    private String situation;
    private LocalDateTime updateTime;

}
