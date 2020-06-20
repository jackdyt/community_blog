package com.jackdyt.blog.controller;

import com.jackdyt.blog.dto.CommentDTO;
import com.jackdyt.blog.dto.CommentGetDTO;
import com.jackdyt.blog.dto.ResultDTO;
import com.jackdyt.blog.enums.CommentType;
import com.jackdyt.blog.exception.CustomizeErrorCode;
import com.jackdyt.blog.model.Comment;
import com.jackdyt.blog.model.User;
import com.jackdyt.blog.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


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
        commentService.insert(comment,user);

        return ResultDTO.success();

    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id:\\d*}", method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> secondComment(@PathVariable(name = "id") Long id){
        List<CommentDTO> commentDTOs = commentService.listByTargetId(id, CommentType.COMMENT);
        return ResultDTO.success(commentDTOs);

    }
}
