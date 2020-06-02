package com.jackdyt.blog.service;

import com.jackdyt.blog.dto.NotificationDTO;
import com.jackdyt.blog.dto.PageDTO;
import com.jackdyt.blog.enums.NotificationStatus;
import com.jackdyt.blog.enums.NotificationType;
import com.jackdyt.blog.exception.CustomizeErrorCode;
import com.jackdyt.blog.exception.CustomizeException;
import com.jackdyt.blog.mapper.NotificationMapper;
import com.jackdyt.blog.model.Notification;
import com.jackdyt.blog.model.NotificationExample;
import com.jackdyt.blog.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    public PageDTO list(Long userId, Integer page, Integer size){
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        Integer total = (int) notificationMapper.countByExample(notificationExample);
        PageDTO<NotificationDTO> pageDTO = new PageDTO();
        Integer pageNeed;
        if (total % size == 0) {
            pageNeed = total / size;
        } else {
            pageNeed = total / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > pageNeed) {
            page = pageNeed;
        }
        pageDTO.setPageInit(total, page, size);
        Integer offset = size * (page - 1);
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

        if (notifications.size() == 0) {
            return pageDTO;
        }

        List<NotificationDTO> notificationDTOS = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationType.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        pageDTO.setData(notificationDTOS);
        return pageDTO;
    }

    public NotificationDTO read(Long notificationId, User user){
        Notification notification = notificationMapper.selectByPrimaryKey(notificationId);
        if (notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if(user.getId() != notification.getReceiver()){
            throw new CustomizeException(CustomizeErrorCode.WRONG_USER);
        }
        notification.setStatus(NotificationStatus.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationType.nameOfType(notificationDTO.getType()));
        return notificationDTO;
    }

    public Long NotRead(Long userId){
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatus.UNREAD.getStatus());
        Long count = notificationMapper.countByExample(notificationExample);
        return count;
    }
}
