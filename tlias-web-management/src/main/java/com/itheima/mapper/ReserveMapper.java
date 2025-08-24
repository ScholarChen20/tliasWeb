package com.itheima.mapper;

import com.itheima.pojo.Reserve;
import com.itheima.pojo.dto.ReserveQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReserveMapper {
    /**
     * 查询用户预约列表
     */
    List<Reserve> userList(Integer id);

    /**
     * 查询医生预约详情
     */
    List<Reserve> doctorList(Integer id);


    void update(Reserve reserve);

    @Delete("delete from reserve where id=#{id}")
    void delete(Integer id);

    /**
     * 新增预约
     */
    @Insert("insert into reserve(user_id, d_id, appoint_time, reason) " +
            "values(#{userId},#{did},#{appointTime}, #{reason})")
    void insert(Reserve reserve);

    /**
     * 查询医生建议列表通过患者id查询的
     */

    List<Reserve> adviceListById(Integer id);//Param 注解用于给参数取别名，只有一个参数时，别名可以忽略

    /**
     * 查询所有预约记录
     * @return
     */
    List<Reserve> list();

    /**
     * id查询预约记录
     */
    Reserve getById(Integer id);

    List<Reserve> listPage(ReserveQueryParam reserveQueryParam);

    void deleteByIds(List<Integer> ids);
}
