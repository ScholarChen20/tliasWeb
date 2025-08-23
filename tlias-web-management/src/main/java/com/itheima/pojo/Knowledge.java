package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Knowledge {
    private int id;
    /**
     * 作者
     */
    private String author;
    /**
     * 主题
     */
    private String title;
    /**
     * 内容
     */
    private String text;
    /**
     * 创建时间
     */
    private LocalDateTime publishTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
    /**
     * 链接地址
     */
    private String image;

}
