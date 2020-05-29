package com.jackdyt.blog.mapper;

import com.jackdyt.blog.dto.EssayDTO;
import com.jackdyt.blog.model.Essay;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EssayMapper {
    @Insert("insert into blog.essay(title, description, gmt_create, gmt_modified, creator, tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator}, #{tag})")
    public void create(Essay essay);

    @Select("select * from blog.essay limit #{offset}, #{size}")
    List<Essay> list(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from blog.essay")
    Integer count();

    @Select("select * from blog.essay where creator=#{userId} limit #{offset}, #{size}")
    List<Essay> listByUserId(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from blog.essay where creator=#{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from blog.essay where id=#{id}")
    Essay getById(@Param("id") Integer id);

    @Update("update blog.essay set title = #{title}, description = #{description}, tag = #{tag}, gmt_modified = #{gmtModified} where id = #{id}")
    void update(Essay essay);
}
