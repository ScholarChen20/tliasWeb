package com.itheima.mapper;

import com.itheima.pojo.UserHbp;
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
    @Update("update user_healthyinfo set sbp = #{sbp}, dbp = #{dbp}, heart = #{heart}, write_time = #{writeTime}, " +
            "writetype = #{writeType}, situation = #{situation}, update_time = #{updateTime} where id = #{id}")
    void update(UserHbp userHbp);

    @Select("select * from user_healthyinfo where userid = #{id} order by write_time desc")
    List<UserHbp>  getInfo(Integer id);

    /*
    返回最近一条记录
     */
    @Select("select id, userid, sbp, dbp,heart,write_time from user_healthyinfo where userid = #{userid} order by id desc limit 1")
    UserHbp getInfoById(@Param("userid") Integer id);

    @Select("select * from user_healthyinfo order by id desc")
    List<UserHbp> list();

}
