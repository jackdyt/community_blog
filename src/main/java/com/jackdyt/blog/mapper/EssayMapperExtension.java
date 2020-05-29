package com.jackdyt.blog.mapper;

import com.jackdyt.blog.model.Essay;
import com.jackdyt.blog.model.EssayExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EssayMapperExtension {
    int incView(Essay record);
}