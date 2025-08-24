// FeedbackMapper.java
package com.itheima.mapper;

import com.itheima.pojo.Feedback;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedbackMapper {

    @Insert("INSERT INTO feedback(user_id, title, content, contact) " +
            "VALUES(#{userId}, #{title}, #{content}, #{contact})")
    void insert(Feedback feedback);
}
