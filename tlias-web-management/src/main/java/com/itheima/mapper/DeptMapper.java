package com.itheima.mapper;

import com.itheima.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {
    @Insert("insert into dept(name,create_time,update_time) values(#{name},#{createTime},#{updateTime})")
    void save(Dept dept);

    // 手动结果查询
//    @Results({
//            @Result(column = "create_time",property = "createTime"),
//            @Result(column = "update_time",property = "updateTime")
//    })
    // 取别名
//    @Select("select id,name,create_time createTime,update_time updateTime from dept order by update_time desc")
    // 驼峰命名
    @Select("select id,name,create_time,update_time from dept order by update_time desc")
    List<Dept> findAll();

    @Update("update dept set name = #{name},update_time = #{updateTime} where id = #{id}")
    void updateById(Dept dept);

    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from dept where id = #{id}")
    Dept getById(Integer id);
}
