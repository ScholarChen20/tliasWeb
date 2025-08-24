package com.itheima.mapper;

import com.itheima.pojo.Knowledge;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.dto.KnowledgeQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface KnowledgeMapper {
    @Insert("insert into knowledge(author, title, text, publish_time, image) values(#{author}, #{title}, #{text}, #{publishTime}, #{image})")
    void insert(Knowledge knowledge);

    void update(Knowledge knowledge);

    @Delete("delete from knowledge where id= #{id}")
    void delete(Integer id);

    @Select("select * from knowledge where id = #{id}")
    Knowledge getInfo(Integer id);

    @Select("select * from knowledge order by id desc")
    List<Knowledge> getAll();

    /**
     * 分页模糊查询健康文章记录
     */
    List<Knowledge> listPage(KnowledgeQueryParam knowledgeQueryParam);
}
