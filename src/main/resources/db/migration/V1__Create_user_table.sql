create table BLOG.USER
(
    ID           INTEGER default NEXT VALUE FOR "BLOG"."SYSTEM_SEQUENCE_835B7EE1_35D2_4FD7_BB54_9F1AFD2665EC" auto_increment,
    ACCOUNT_ID   VARCHAR(100),
    NAME         VARCHAR(50),
    TOKEN        CHAR(36),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    constraint USER_PK
        primary key (ID)
);