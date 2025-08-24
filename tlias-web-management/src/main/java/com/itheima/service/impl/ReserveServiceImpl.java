package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.mapper.ReserveMapper;
import com.itheima.pojo.vo.PageResult;
import com.itheima.pojo.Reserve;
import com.itheima.pojo.dto.ReserveQueryParam;
import com.itheima.service.ReserveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        reserve.setUpdateTime(LocalDateTime.now());
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
    public void deleteByIds(List<Integer> ids) {
        reserveMapper.deleteByIds(ids);
    }

    @Override
    public List<Reserve> getAdvice(Integer id) {
        return reserveMapper.adviceListById(id);
    }

    @Override
    public List<Reserve> list() {
        return reserveMapper.list();
    }

    @Override
    public PageResult<Reserve> page(ReserveQueryParam reserveQueryParam) {
        PageHelper.startPage(reserveQueryParam.getPage(), reserveQueryParam.getPageSize());
        List<Reserve> reserves  = reserveMapper.listPage(reserveQueryParam);
        PageInfo<Reserve> pageInfo = new PageInfo<Reserve>(reserves);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public Reserve getById(Integer id) {
        Reserve res = reserveMapper.getById(id);
        return res;
    }


}
