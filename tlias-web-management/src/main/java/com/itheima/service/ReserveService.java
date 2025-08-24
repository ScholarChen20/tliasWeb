package com.itheima.service;


import com.itheima.pojo.vo.PageResult;
import com.itheima.pojo.Reserve;
import com.itheima.pojo.dto.ReserveQueryParam;

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

    void deleteByIds(List<Integer> ids);
}
