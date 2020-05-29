package com.jackdyt.blog.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    ESSAY_NOT_FOUND("The post doesn't exist any longer.");
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(String message) {
        this.message = message;
    }

}
