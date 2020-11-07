create table category
(
    id             int auto_increment
        primary key,
    category_name  varchar(64) default ''                null comment '分类名称',
    order_num      int         default 0                 not null comment '排序，越大越靠前',
    post_num       int         default 0                 null comment '帖子数量',
    creator        varchar(64) default ''                not null comment '创建人',
    create_time    timestamp   default CURRENT_TIMESTAMP null comment '创建时间',
    modifier       varchar(64) default ''                null comment '创建人',
    modify_time    timestamp   default CURRENT_TIMESTAMP null comment '创建时间',
    yn             tinyint     default 1                 null comment '删除标识，1：已删除，0：未删除',
    moderator_num  int         default 0                 null comment '版主数量',
    last_post_time timestamp   default CURRENT_TIMESTAMP null comment '最后发帖时间'
)
    comment '分类';

