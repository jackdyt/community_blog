package com.jackdyt.blog.mapper;

import com.jackdyt.blog.model.Essay;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EssayMapper {
    @Insert("insert into COMMUNITY_BLOG.BLOG.ESSAY(title, description, gmt_create, gmt_modified, creator) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator})")
    public void create(Essay essay);
}
