package com.itheima.service.impl;

import com.itheima.mapper.ReserveMapper;
import com.itheima.pojo.Reserve;
import com.itheima.service.ReserveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class ReserveServiceImpl implements ReserveService {
    @Autowired
    private ReserveMapper reserveMapper;

    @Override
    public List<Reserve> doctorList(Integer id) {
        return reserveMapper.doctorList(id);
    }

    @Override
    public List<Reserve> userList(Integer id) {
        return reserveMapper.userList(id);
    }

    @Override
    public void update(Reserve reserve) {
        reserveMapper.update(reserve);
    }

    @Override
    public void save(Reserve reserve) {
        reserveMapper.insert(reserve);
    }

    @Override
    public void delete(Integer id) {
        reserveMapper.delete(id);
    }

    @Override
    public List<Reserve> getAdvice(Integer id) {
        return reserveMapper.adviceListById(id);
    }

    @Override
    public List<Reserve> list() {
        return reserveMapper.list();
    }


}
