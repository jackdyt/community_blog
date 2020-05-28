package com.jackdyt.blog.mapper;

import com.jackdyt.blog.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    @Insert("insert into COMMUNITY_BLOG.BLOG.USER(name, account_id, token, gmt_create, gmt_modified, avatar_url) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified}, #{avatarUrl})")
    void insert(User user);

    @Select("select * from  COMMUNITY_BLOG.BLOG.USER where token= #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from  COMMUNITY_BLOG.BLOG.USER where id= #{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from  COMMUNITY_BLOG.BLOG.USER where account_id= #{accountId}")
    User findByAccoountId(@Param("accountId")String accountId);

    @Update("update COMMUNITY_BLOG.BLOG.USER set name = #{name}, token=#{token}, gmt_modified = #{gmtModified}, avatar_url = #{avatarUrl} where account_id=#{accountId} ")
    void update(User user);
}
