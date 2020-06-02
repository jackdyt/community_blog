package com.jackdyt.blog.enums;

public enum NotificationStatus {
    READ(1),
    UNREAD(0);
    private int status;

    NotificationStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
