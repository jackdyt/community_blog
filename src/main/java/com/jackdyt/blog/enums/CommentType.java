package com.jackdyt.blog.enums;

import com.jackdyt.blog.model.Comment;

public enum CommentType {
    QUESTION(1),
    COMMENT(2)
    ;
    private Integer type;

    CommentType(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentType commentType: CommentType.values()){
            if (commentType.getType() == type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }
}
