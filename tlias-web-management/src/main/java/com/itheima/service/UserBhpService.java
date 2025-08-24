package com.itheima.service;

import com.itheima.pojo.vo.PageResult;
import com.itheima.pojo.UserHbp;
import com.itheima.pojo.dto.RecordQueryParam;

import java.util.List;

public interface UserBhpService {
    List<UserHbp> list();

    UserHbp  getInfo(Integer id);

    void insert(UserHbp userHbp);

    void update(UserHbp userHbp);

    void delete(Integer id);

    void deleteByIds(List<Integer> ids);

    UserHbp getInfoById(Integer id);

    PageResult<UserHbp> page(RecordQueryParam recordQueryParam);
}
