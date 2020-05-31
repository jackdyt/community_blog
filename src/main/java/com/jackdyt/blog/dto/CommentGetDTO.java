package com.jackdyt.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentGetDTO {
    private Long parentId;
    private String content;
    private Integer type;

}
