package com.itheima.mapper;

import com.itheima.pojo.Emp;
import com.itheima.pojo.dto.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {
    /**
     * @author: 18040
     * 员工基本信息映射接口
     */
    // 查询员工总数以及查询员工列表
    @Select("SELECT COUNT(*) FROM emp e left join dept d on e.dept_id = d.id")
    Long count();
    @Select("SELECT e.*,d.name deptName FROM emp e left join dept d on e.dept_id = d.id " +
            "order by e.update_time desc limit #{start},#{pageSize}")
    List<Emp> list(Integer start, Integer pageSize);

    // 使用pageHelper分页插件
    //@Select("SELECT e.*,d.name deptName FROM emp e left join dept d on e.dept_id = d.id " +
            //"order by e.update_time desc")
    List<Emp> listAll(EmpQueryParam empQueryParam);

    // 新增员工
    @Options(useGeneratedKeys = true, keyProperty = "id") //获取生成的
    @Insert("INSERT INTO emp(username,name,gender,phone,job,salary,image,entry_date,dept_id,create_time,update_time) " +
            "VALUES(#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void save(Emp emp);
    // 根据id删除员工
    void deleteByIds(List<Integer> ids);

    // 使用连接查询员工信息和工作经历
    Emp getById(Integer id);

    // 修改员工信息
    void updateById(Emp emp);

    @MapKey("pos")
    List<Map<String, Object>> countEmpJobData();

    @MapKey("name")
    List<Map> countEmpGenderData();

    @Select("SELECT id,username,name FROM emp WHERE username = #{username} AND password = #{password}")
    Emp selectByUsernameAndPassword(Emp emp);

    @Select("SELECT id,name FROM emp")
    List<Emp> findAll();
}
