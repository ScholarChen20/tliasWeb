package com.itheima.service;


import com.itheima.pojo.Doctor;
import com.itheima.pojo.dto.DoctorQueryParam;
import com.itheima.pojo.vo.PageResult;

import java.util.List;

public interface DoctorService {
    Doctor getInfo(Integer id);

    void update(Doctor doctor);

    void save(Doctor doctor);

    void delete(Integer id);

    void deleteByIds(List<Integer> ids);

    List<Doctor> list();

    PageResult<Doctor> page(DoctorQueryParam doctorQueryParam);
}
