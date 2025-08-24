package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.UserHbpMapper;
import com.itheima.pojo.vo.PageResult;
import com.itheima.pojo.UserHbp;
import com.itheima.pojo.dto.RecordQueryParam;
import com.itheima.service.UserBhpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class UserBhpServiceImpl implements UserBhpService {
    @Autowired
    private UserHbpMapper userHbpMapper;

    @Override
    public List<UserHbp> list() {
        return userHbpMapper.list();
    }

    @Override
    public UserHbp  getInfo(Integer id) {
        return userHbpMapper.getInfo(id);
    }

    @Override
    public List<UserHbp> getInfoByUserId(Integer userId) {
        return userHbpMapper.getInfoByUserId(userId);
    }


    @Override
    public void insert(UserHbp userHbp) {
        userHbp.setWriteTime(LocalDateTime.now());
        userHbp.setUpdateTime(LocalDateTime.now());
        userHbpMapper.insert(userHbp);
    }

    @Override
    public void update(UserHbp userHbp) {
        userHbp.setUpdateTime(LocalDateTime.now());
        userHbpMapper.update(userHbp);
    }

    @Override
    public void delete(Integer id) {
        userHbpMapper.delete(id);
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        userHbpMapper.deleteByIds(ids);
    }

    @Override
    public UserHbp getInfoById(Integer id) {
        return userHbpMapper.getInfoById(id);
    }

    @Override
    public PageResult<UserHbp> page(RecordQueryParam recordQueryParam) {
        PageHelper.startPage(recordQueryParam.getPage(), recordQueryParam.getPageSize());// 开启分页
        List<UserHbp> list = userHbpMapper.listPage(recordQueryParam); // 查询分页数据
        Page<UserHbp> p = (Page<UserHbp>) list; // 强转为Page类型
        return new PageResult<>(p.getTotal(), p.getResult()); // 返回分页结果
    }



}
