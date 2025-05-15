package com.itheima.mapper;

import com.itheima.pojo.User;
import com.itheima.pojo.UserLoginInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user_info where id=#{id}")
    User list(Integer id);

    void update(User user);

    @Delete("delete from user_info where id=#{id}")
    void delete(Integer id);

    @Insert("insert into user_info(userid, image, username, name, sex, birthdate, phone) " +
            "values(#{userId}, #{image}, #{username}, #{name}, #{sex}, #{birthDate}, #{phone})")
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
}
