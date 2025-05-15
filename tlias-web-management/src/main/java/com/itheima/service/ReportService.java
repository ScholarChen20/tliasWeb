package com.itheima.service;

import com.itheima.pojo.ClazzCountOption;
import com.itheima.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {
    JobOption getEmpJobData();

    List<Map> getEmpGenderData();

    ClazzCountOption getStudentCountData();

    List<Map> getStudentDegreeData();
}
