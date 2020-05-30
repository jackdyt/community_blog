package com.jackdyt.blog.service;

import com.jackdyt.blog.enums.CommentType;
import com.jackdyt.blog.exception.CustomizeErrorCode;
import com.jackdyt.blog.exception.CustomizeException;
import com.jackdyt.blog.mapper.CommentMapper;
import com.jackdyt.blog.mapper.EssayMapper;
import com.jackdyt.blog.mapper.EssayMapperExtension;
import com.jackdyt.blog.model.Comment;
import com.jackdyt.blog.model.Essay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private EssayMapper essayMapper;
    @Autowired
    private EssayMapperExtension essayMapperExtension;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.PARENT_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentType.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARENT_WRONG);
        }
        if (comment.getType() == CommentType.COMMENT.getType()){
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);

        }else{
            Essay essay = essayMapper.selectByPrimaryKey(comment.getParentId());
            if (essay == null){
                throw new CustomizeException(CustomizeErrorCode.PARENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            essay.setCommentCount(1);
            essayMapperExtension.incComment(essay);

        }
    }
}
