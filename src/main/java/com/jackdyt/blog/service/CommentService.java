package com.jackdyt.blog.service;

import com.jackdyt.blog.dto.CommentDTO;
import com.jackdyt.blog.enums.CommentType;
import com.jackdyt.blog.enums.NotificationStatus;
import com.jackdyt.blog.enums.NotificationType;
import com.jackdyt.blog.exception.CustomizeErrorCode;
import com.jackdyt.blog.exception.CustomizeException;
import com.jackdyt.blog.mapper.*;
import com.jackdyt.blog.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private EssayMapper essayMapper;
    @Autowired
    private EssayMapperExtension essayMapperExtension;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapperExtension commentMapperExtension;
    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment, User commentator) {
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
            Essay essay = essayMapper.selectByPrimaryKey(dbComment.getParentId());
            if (essay == null){
                throw new CustomizeException(CustomizeErrorCode.PARENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            //increase the comment count
            Comment parentComment = new Comment();
            parentComment.setCommentCount(1);
            parentComment.setId(comment.getParentId());
            commentMapperExtension.incComment(parentComment);
            addNotification(comment,dbComment.getCommentator(),commentator.getName(), essay.getTitle(),NotificationType.REPLY_COMMENT,essay.getId());
        }else{
            Essay essay = essayMapper.selectByPrimaryKey(comment.getParentId());
            if (essay == null){
                throw new CustomizeException(CustomizeErrorCode.PARENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            addNotification(comment,essay.getCreator(),commentator.getName(), essay.getTitle(),NotificationType.REPLY_POST,essay.getId());
            essay.setCommentCount(1);
            essayMapperExtension.incComment(essay);

        }
    }

    public List<CommentDTO> listByTargetId(Long id, CommentType type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size() == 0){
            return new ArrayList<>();
        }
        //get commentators' id
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userId = new ArrayList<>();
        userId.addAll(commentators);
        //get commentator and convert them to map
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userId);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //convert to comment to commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }

    public void addNotification(Comment comment, Long receiverId, String notifierName,
                                String title, NotificationType notificationType, Long notifierId){
        Notification notification = new Notification();
        notification.setNotifier(notifierId);
        notification.setNotifierName(notifierName);
        notification.setCorrespondingId(comment.getParentId());
        notification.setReceiver(receiverId);
        notification.setTitle(title);
        notification.setType(notificationType.getType());
        notification.setStatus(NotificationStatus.UNREAD.getStatus());
        notification.setGmtCreate(System.currentTimeMillis());
        notificationMapper.insert(notification);

    }
}
