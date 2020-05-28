package com.jackdyt.blog.mapper;

import com.jackdyt.blog.dto.EssayDTO;
import com.jackdyt.blog.model.Essay;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EssayMapper {
    @Insert("insert into COMMUNITY_BLOG.BLOG.ESSAY(title, description, gmt_create, gmt_modified, creator) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator})")
    public void create(Essay essay);

    @Select("select * from COMMUNITY_BLOG.BLOG.ESSAY limit #{offset}, #{size}")
    List<Essay> list(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from COMMUNITY_BLOG.BLOG.ESSAY")
    Integer count();

    @Select("select * from COMMUNITY_BLOG.BLOG.ESSAY where creator=#{userId} limit #{offset}, #{size}")
    List<Essay> listByUserId(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from COMMUNITY_BLOG.BLOG.ESSAY where creator=#{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from COMMUNITY_BLOG.BLOG.ESSAY where id=#{id}")
    Essay getById(@Param("id") Integer id);
}
