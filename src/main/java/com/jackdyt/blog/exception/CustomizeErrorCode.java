package com.jackdyt.blog.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    ESSAY_NOT_FOUND(2001,"The post doesn't exist any longer."),
    PARENT_NOT_FOUND(2002,"No post or comment is chosen."),
    NOT_LOGIN(2003,"Please login first."),
    SYSTEM_ERROR(2004,"Something happened in servers."),
    TYPE_PARENT_WRONG(2005, "Comment type is wrong."),
    COMMENT_NOT_FOUND(2006, "Comment doesn't exist."),
    CONTENT_EMPTY(2007, "Content cannot be empty."),
    NOTIFICATION_NOT_FOUND(2008, "The notification doesn't exist any longer."),
    WRONG_USER(2009, "Wrong user.");
    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }


    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;

    }
}
