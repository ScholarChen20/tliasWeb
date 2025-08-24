package com.itheima.mapper;

import com.itheima.pojo.User;
import com.itheima.pojo.dto.UserQueryParam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user_info where id = #{id}")
    User list(Integer id);

    void update(User user);

    @Delete("delete from user_info where id=#{id}")
    void delete(Integer ids);

    @Insert("insert into user_info(userid, image, username, name, sex, birthdate, phone, create_time) " +
            "values(#{userId}, #{image}, #{username}, #{name}, #{sex}, #{birthDate}, #{phone}, #{createTime})")
    void insert(User user);

    /**
     * 用户名密码登录
     */
    @Select("select id,name,username from user_info where username=#{username} and password=#{password}")
    User selectByUsernameAndPassword(User user);

    /**
     * 微信ipenid和nickname授权登录
     */
    @Select("select * from user_info where openid=#{openid} and nickname=#{nickname}")
    User selectByOpenidAndNickname(User user);

    /**
     * 查询所有用户
     */
    @Select("select * from user_info")
    List<User> getList();

    /**
     * 分页查询
     */
    List<User> listPage(UserQueryParam userQueryParam);

    /**
     * 批量删除
     * @param ids
     */
    void deleteByIds(List<Integer> ids);
}
