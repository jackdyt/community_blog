package com.jackdyt.blog.controller;

import com.jackdyt.blog.dto.NotificationDTO;
import com.jackdyt.blog.dto.ResultDTO;
import com.jackdyt.blog.enums.NotificationType;
import com.jackdyt.blog.exception.CustomizeErrorCode;
import com.jackdyt.blog.model.Notification;
import com.jackdyt.blog.model.User;
import com.jackdyt.blog.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String notification(@PathVariable(name = "id") Long id, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:index";
        }
        NotificationDTO notificationDTO = notificationService.read(id, user);
        if (NotificationType.REPLY_COMMENT.getType() == notificationDTO.getType() ||
        NotificationType.REPLY_POST.getType() == notificationDTO.getType()){
            return "redirect:/essay/" + notificationDTO.getCorrespondingId();
        }else{
            return "redirect:index";
        }
    }
}
