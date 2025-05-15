package com.itheima.service;

import com.itheima.pojo.Clazz;
import com.itheima.pojo.PageResult;
import java.time.LocalDate;
import java.util.List;

public interface ClazzService {
    /**
     * 添加班级信息
     * @param clazz
     */
    void save(Clazz clazz);

    /**
     * 条件分页查询
     * @param name
     * @param begin
     * @param end
     * @param page
     * @param pageSize
     * @return
     */
    PageResult page(String name, LocalDate begin, LocalDate end, Integer page, Integer pageSize);

    /**
     * 根据ID查询班级详情
     * @param id
     * @return
     */
    Clazz getInfo(Integer id);

    /**
     * 修改班级信息
     * @param clazz
     */
    void update(Clazz clazz);

    /**
     * 删除班级
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 查询全部班级
     * @return
     */
    List<Clazz> findAll();
}
