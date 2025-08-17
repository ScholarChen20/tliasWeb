package com.itheima.service;


import com.itheima.pojo.Doctor;
import com.itheima.pojo.DoctorQueryParam;
import com.itheima.pojo.Emp;
import com.itheima.pojo.PageResult;

import java.util.List;
//import com.itheima.pojo.User;

public interface DoctorService {
    Doctor getInfo(Integer id);

    void update(Doctor doctor);

    void save(Doctor doctor);

    void delete(Integer id);

    List<Doctor> list();

    PageResult<Doctor> page(DoctorQueryParam doctorQueryParam);
}
