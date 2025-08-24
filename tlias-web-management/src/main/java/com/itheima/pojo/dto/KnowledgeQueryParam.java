package com.itheima.pojo.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class KnowledgeQueryParam {
    private Integer page=1;
    private Integer pageSize=10;
    private String author; //作者
    private String text; //内容
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate begin;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate end;
}
