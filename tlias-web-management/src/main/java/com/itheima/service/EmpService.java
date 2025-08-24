package com.itheima.service;

import com.itheima.pojo.Emp;
import com.itheima.pojo.dto.EmpQueryParam;
import com.itheima.pojo.dto.LoginInfo;
import com.itheima.pojo.vo.PageResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface EmpService {
    PageResult<Emp> page(EmpQueryParam empQueryParam);

    void save(Emp emp);

    void delete(List<Integer> ids);

    Emp getById(Integer id);

    void update(Emp emp);

    LoginInfo login(Emp emp);

    List<Emp> findAll();
}
