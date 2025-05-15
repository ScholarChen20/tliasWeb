package com.itheima.service.impl;

import com.itheima.mapper.EmpMapper;
import com.itheima.mapper.StudentMapper;
import com.itheima.pojo.ClazzCountOption;
import com.itheima.pojo.JobOption;
import com.itheima.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public JobOption getEmpJobData() {
        List<Map<String, Object>> list = empMapper.countEmpJobData();
        //  职位名称和数量转换为JobOption对象
        List<Object> jobList = list.stream().map(dataMap -> dataMap.get("pos")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("num")).toList();
        return new JobOption(jobList, dataList);
    }

    @Override
    public List<Map> getEmpGenderData() {
        List<Map> genderList = empMapper.countEmpGenderData();
        return genderList;
    }

    @Override
    public ClazzCountOption getStudentCountData() {
        List<Map<String, Object>> countList = studentMapper.getStudentCount();
        if(!CollectionUtils.isEmpty(countList)){
            List<Object> clazzList = countList.stream().map(map -> {
                return map.get("cname");
            }).toList();
            List<Object> dataList = countList.stream().map(map -> {
                return map.get("scount");
            }).toList();
            return new ClazzCountOption(clazzList, dataList);
        }else{
            return null;
        }
    }

    @Override
    public List<Map> getStudentDegreeData() {
        List<Map> degreeList = studentMapper.countStudentDegreeData();
        return degreeList;
    }
}
