package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.DoctorMapper;
import com.itheima.pojo.Doctor;
import com.itheima.pojo.dto.DoctorQueryParam;
import com.itheima.pojo.PageResult;
import com.itheima.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    public Doctor getInfo(Integer id) {
        return doctorMapper.list(id);
    }

    @Override
    public void update(Doctor doctor) {
        doctorMapper.update(doctor);
    }

    @Override
    public void save(Doctor doctor) {
        doctorMapper.insert(doctor);
    }

    @Override
    public void delete(Integer id) {
        doctorMapper.delete(id);
    }

    @Override
    public List<Doctor> list() {
        return doctorMapper.listAll();
    }

    @Override
    public PageResult<Doctor> page(DoctorQueryParam doctorQueryParam) {
        int page = doctorQueryParam.getPage();
        int pageSize = doctorQueryParam.getPageSize();
        PageHelper.startPage(page, pageSize);
        List<Doctor> list = doctorMapper.listPage(
                doctorQueryParam.getName(),
                doctorQueryParam.getDept(),
                doctorQueryParam.getGender(),
                doctorQueryParam.getTitle()); // 查询分页数据
        Page<Doctor> p = (Page<Doctor>) list; // 强转为Page类型
        return new PageResult(p.getTotal(), p.getResult()); // 返回分页结果
    }


}
