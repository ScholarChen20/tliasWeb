package com.itheima.service;


import com.itheima.pojo.Reserve;

import java.util.List;

public interface ReserveService {
    List<Reserve> doctorList(Integer id);

    List<Reserve> userList(Integer id);

    void update(Reserve reserve);

    void save(Reserve reserve);

    void delete(Integer id);

    List<Reserve> getAdvice(Integer id);

    List<Reserve> list();
}
