package com.itheima.service;


import com.itheima.pojo.Emp;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Reserve;
import com.itheima.pojo.ReserveQueryParam;

import java.util.List;

public interface ReserveService {
    List<Reserve> doctorList(Integer id);

    List<Reserve> userList(Integer id);

    void update(Reserve reserve);

    void save(Reserve reserve);

    void delete(Integer id);

    List<Reserve> getAdvice(Integer id);

    List<Reserve> list();

    PageResult<Reserve> page(ReserveQueryParam reserveQueryParam);

    Reserve getById(Integer id);
}
