package com.jackdyt.blog.enums;

public enum NotificationType {
    REPLY_POST(1, "replyed to the post"),
    REPLY_COMMENT(2,"replyed to the comment");
    private int type;
    private String typeName;

    NotificationType(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public int getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    public static String nameOfType(int type){
        for (NotificationType notificationType:NotificationType.values()){
            if (notificationType.getType() == type){
                return notificationType.getTypeName();
            }
        }
        return "";
    }
}
