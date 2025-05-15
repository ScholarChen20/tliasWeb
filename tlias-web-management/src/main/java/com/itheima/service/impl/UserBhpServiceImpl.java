package com.itheima.service.impl;

import com.itheima.mapper.UserHbpMapper;
import com.itheima.pojo.UserHbp;
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
    public List<UserHbp>  getInfo(Integer id) {
        return userHbpMapper.getInfo(id);
    }

    @Override
    public void insert(UserHbp userHbp) {
        userHbp.setWriteTime(LocalDateTime.now());
//        userHbp.setUpdateTime(LocalDateTime.now());
        userHbpMapper.insert(userHbp);
    }

    @Override
    public void update(UserHbp userHbp) {
        userHbp.setUpdateTime(LocalDateTime.now());
//        userHbp.setWriteTime(LocalDateTime.now());
        userHbpMapper.update(userHbp);
    }

    @Override
    public void delete(Integer id) {
        userHbpMapper.delete(id);
    }

    @Override
    public UserHbp getInfoById(Integer id) {
        return userHbpMapper.getInfoById(id);
    }


}
