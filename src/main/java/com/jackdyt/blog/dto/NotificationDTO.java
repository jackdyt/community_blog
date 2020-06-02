package com.jackdyt.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private Long id;
    private Long notifier;
    private Long receiver;
    private Long correspondingId;
    private Integer type;
    private String typeName;
    private Integer status;
    private Long gmtCreate;
    private String notifierName;
    private String title;


}
