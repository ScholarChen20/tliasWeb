package com.itheima.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartDataDTO {
    private List<String> dates;
    private List<Double> sbp;
    private List<Double> dbp;
    private List<Double> heart;
}
