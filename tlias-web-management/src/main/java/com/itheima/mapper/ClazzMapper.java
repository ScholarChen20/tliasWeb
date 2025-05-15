package com.itheima.mapper;


import com.itheima.pojo.Clazz;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ClazzMapper {
    /**
     * 查询班级列表
     */
    List<Clazz> list(String name, LocalDate begin, LocalDate end);

    /**
     * 添加班级信息
     */
    @Insert("insert into clazz VALUES (null,#{name},#{room},#{beginDate},#{endDate},#{masterId}, #{subject},#{createTime},#{updateTime})")
    void insert(Clazz clazz);

    /**
     * 更新班级信息
     */
    void update(Clazz clazz);

    @Delete("delete from clazz where id = #{id}")
    void deleteByIds(Integer id);

    /**
     * 根据id查询班级信息
     */
    @Select("select * from clazz where id = #{id}")
    Clazz getInfo(Integer id);

    /**
     * 查询全部班级
     */
    @Select("SELECT c.*,e.name as masterName from clazz c left join emp e on c.master_id = e.id")
    List<Clazz> findAll();

    /**
     * 删除班级信息
     */
    @Delete("delete from clazz where id = #{id}")
    void deleteById(Integer id);

}
