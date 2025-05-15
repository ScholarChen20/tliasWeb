package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.StudentMapper;
import com.itheima.pojo.ClazzCountOption;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Student;
import com.itheima.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public void save(Student student) {
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.insert(student);
    }


    @Override
    public PageResult page(String name, Integer degree, Integer clazzId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);

        List<Student> studentList = studentMapper.list(name,degree,clazzId);
        Page<Student> p = (Page<Student>) studentList;

        return new PageResult(p.getTotal(), p.getResult());
    }


    @Override
    public Student getInfo(Integer id) {
        return studentMapper.getById(id);
    }


    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.update(student);
    }


    @Override
    public void delete(List<Integer> ids) {
        studentMapper.delete(ids);
    }


    @Override
    public void violationHandle(Integer id, Integer score) {
        studentMapper.updateViolation(id, score);
    }


    @Override
    public List<Map> getStudentDegreeData() {
        return studentMapper.countStudentDegreeData();
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
        }
        return null;
    }

}
