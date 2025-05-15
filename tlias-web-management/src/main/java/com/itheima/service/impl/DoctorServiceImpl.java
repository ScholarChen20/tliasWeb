package com.itheima.service.impl;

import com.itheima.mapper.DoctorMapper;
import com.itheima.pojo.Doctor;
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


}
