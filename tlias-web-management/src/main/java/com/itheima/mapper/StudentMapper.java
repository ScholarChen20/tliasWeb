package com.itheima.mapper;


import com.itheima.pojo.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {
    /**
     * 添加学生
     */
    @Insert("insert into student(name, no, gender, phone,id_card, is_college, address, degree, graduation_date,clazz_id, create_time, update_time) VALUES " +
            "(#{name},#{no},#{gender},#{phone},#{idCard},#{isCollege},#{address},#{degree},#{graduationDate},#{clazzId},#{createTime},#{updateTime})")
    void insert(Student student);

    /**
     * 动态条件查询
     */
    List<Student> list(String name, Integer degree, Integer clazzId);

    /**
     * 根据ID查询学生信息
     */
    @Select("select * from student where id = #{id}")
    Student getById(Integer id);

    /**
     * 修改学生信息
     */
    void update(Student student);

    /**
     * 批量删除学生信息
     */
    void delete(List<Integer> ids);

    /**
     * 违纪处理
     */
    @Update("update student set violation_count = violation_count + 1 , violation_score = violation_score + #{score} , update_time = now() where id = #{id}")
    void updateViolation(Integer id, Integer score);

    /**
     * 统计班级人数
     */
    @Select("select c.name cname , count(s.id) scount from clazz c  left join student s on s.clazz_id = c.id group by c.name order by count(s.id) desc ")
    List<Map<String,Object>> getStudentCount();

    /**
     * 统计学员学历
     */
    @MapKey("name")
    List<Map> countStudentDegreeData();

    @Select("select count(*) from student where clazz_id = #{id}")
    Integer countByClazzId(Integer id);

    /**
     * 批量插入
     */
    void insertBatch(List<Student> studentList);
}
