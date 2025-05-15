package com.itheima.mapper;

import com.itheima.pojo.Doctor;
import com.itheima.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DoctorMapper {
    @Select("select * from doctor where id=#{id}")
    Doctor list(Integer id);

    @Update("update doctor set name=#{name}, dept=#{dept}, title=#{title}, avatar=#{avatar}, " +
            "introduce=#{introduce}, status=#{status}, phone=#{phone} where id=#{id}")
    void update(Doctor doctor);

    @Delete("delete from doctor where id=#{id}")
    void delete(Integer id);

    @Insert("insert into doctor(name, dept, title, introduce, status, phone,avatar) " +
            "values(#{name}, #{dept}, #{title},, #{introduce}, #{status}, #{phone}),#{avatar}")
    void insert(Doctor doctor);

    @Select("select * from doctor where status=1")
    List<Doctor> listAll();
}
