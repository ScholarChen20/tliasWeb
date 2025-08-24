package com.itheima.mapper;

import com.itheima.pojo.UserHbp;
import com.itheima.pojo.dto.RecordQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserHbpMapper {
    @Insert("insert into user_healthyinfo(userid, sbp, dbp, heart, write_time, writetype, situation,update_time) " +
            "values(#{userid}, #{sbp}, #{dbp}, #{heart}, #{writeTime}, #{writeType}, #{situation}, #{updateTime})")
    void insert(UserHbp userHbp);

    /**
     * 删除用户健康信息
     */
    @Delete("delete from user_healthyinfo where id = #{id}")
    void delete(Integer id);

    /**
     * 更新用户健康信息
     */
    void update(UserHbp userHbp);

    @Select("select * from user_healthyinfo where id = #{id} order by write_time desc")
    UserHbp  getInfo(Integer id);

    /**
     * 根据用户id查询所有记录（小程序使用）
     */
    @Select("select id, userid, sbp, dbp,heart,write_time,situation,writeType from user_healthyinfo where userid = #{userid} order by write_time desc")
    List<UserHbp> getInfoByUserId(Integer userId);
    /**
     * 返回最近一条记录
     */
    @Select("select id, userid, sbp, dbp,heart,situation,write_time,writeType from user_healthyinfo where userid = #{userid} order by id desc limit 1")
    UserHbp getInfoById(@Param("userid") Integer id);

    /**
     * 分页查看所有血压记录
     */
    List<UserHbp> list();

    /**
     * 分页条件查询所有记录
     */
    List<UserHbp> listPage(RecordQueryParam recordQueryParam);

    /**
     * 批量删除
     */
    void deleteByIds(List<Integer> ids);
}
