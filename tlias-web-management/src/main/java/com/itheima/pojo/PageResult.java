package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageResult<T> { //引入T类型，表示返回的结果集中包含的对象类型
    private Long total;
    private List<T> rows;
}
