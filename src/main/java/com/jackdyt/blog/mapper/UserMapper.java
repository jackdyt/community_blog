package com.jackdyt.blog.mapper;

import com.jackdyt.blog.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    @Insert("insert into blog.user(name, account_id, token, gmt_create, gmt_modified, avatar_url) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified}, #{avatarUrl})")
    void insert(User user);

    @Select("select * from  blog.user where token= #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from  blog.user where id= #{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from  blog.user where account_id= #{accountId}")
    User findByAccoountId(@Param("accountId")String accountId);

    @Update("update blog.user set name = #{name}, token=#{token}, gmt_modified = #{gmtModified}, avatar_url = #{avatarUrl} where account_id=#{accountId} ")
    void update(User user);
}
