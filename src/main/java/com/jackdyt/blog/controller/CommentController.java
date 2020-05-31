package com.jackdyt.blog.controller;

import com.jackdyt.blog.dto.CommentGetDTO;
import com.jackdyt.blog.dto.ResultDTO;
import com.jackdyt.blog.exception.CustomizeErrorCode;
import com.jackdyt.blog.model.Comment;
import com.jackdyt.blog.model.User;
import com.jackdyt.blog.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object comment(@RequestBody CommentGetDTO commentGetDTO, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorCausedBy(CustomizeErrorCode.NOT_LOGIN);
        }
        if (commentGetDTO == null || StringUtils.isBlank(commentGetDTO.getContent())){
            return ResultDTO.errorCausedBy(CustomizeErrorCode.CONTENT_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentGetDTO.getParentId());
        comment.setContent(commentGetDTO.getContent());
        comment.setType(commentGetDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);

        return ResultDTO.success();

    }
}
