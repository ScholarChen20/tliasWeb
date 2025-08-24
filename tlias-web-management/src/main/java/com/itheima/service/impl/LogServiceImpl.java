package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.LogMapper;
import com.itheima.pojo.OperateLog;
import com.itheima.pojo.vo.PageResult;
import com.itheima.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;

    @Override
    public PageResult page(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<OperateLog> logList = logMapper.list();//获取分页查询结果
        Page<OperateLog> p = (Page<OperateLog>) logList;//强制类型转换，获取总记录数和当前页数据
        return new PageResult(p.getTotal(), p.getResult());
    }
}
