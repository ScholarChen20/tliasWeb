// Feedback.java
package com.itheima.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    private Integer id;
    private Integer userId;
    /**
     * 反馈标题
     */
    private String title;
    /**
     * 反馈内容
     */
    private String content;
    /**
     * 联系方式
     */
    private String contact;
    private Integer status; // 0:未处理 1:处理中 2:处理完成

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
