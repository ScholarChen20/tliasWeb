package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 班级人数统计
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClazzCountOption {
    private List clazzList; //班级列表
    private List dataList; //人数列表
}