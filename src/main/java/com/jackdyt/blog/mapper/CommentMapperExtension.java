package com.jackdyt.blog.mapper;

import com.jackdyt.blog.model.Comment;
import org.springframework.stereotype.Repository;



@Repository
public interface CommentMapperExtension {
    int incComment(Comment record);
}