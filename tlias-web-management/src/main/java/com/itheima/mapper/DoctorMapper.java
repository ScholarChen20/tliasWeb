package com.itheima.mapper;

import com.itheima.pojo.Doctor;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DoctorMapper {
    @Select("select * from doctor where id=#{id}")
    Doctor list(Integer id);

    void update(Doctor doctor);

    @Delete("delete from doctor where id=#{id}")
    void delete(Integer id);

    @Insert("insert into doctor(name, gender, dept, title, introduce, hospital, status, phone,avatar,create_time) " +
            "values(#{name}, #{gender}, #{dept}, #{title}, #{introduce},#{hospital}, #{status}, #{phone},#{avatar},#{createTime})")
    void insert(Doctor doctor);

    @Select("select * from doctor where status=1")
    List<Doctor> listAll();

//    List<Doctor> listPage(DoctorQueryParam doctorQueryParam);

    List<Doctor> listPage(@Param("name") String name, @Param("dept") String dept, @Param("gender") String gender, @Param("title") String title);
}
