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
    private String author;
    private String title;
    private String text;
    private LocalDateTime publishTime;
    private String image;

}
