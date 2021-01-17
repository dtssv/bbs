create table comment
(
    id                int auto_increment
        primary key,
    user_id           int         default 0                 not null comment '用户id',
    nick_name         varchar(64) default ''                null comment '用户昵称',
    floor_num         int         default 2                 null comment '楼层',
    comment_user_id   int         default 0                 null comment '回复用户id',
    comment_nick_name varchar(64) default ''                null comment '回复用户昵称',
    comment_body      text                                  null comment '回复内容',
    post_id           int         default 0                 not null comment '帖子ID',
    comment_title     varchar(64) default ''                null comment '回复标题',
    creator           varchar(64) default ''                not null comment '创建人',
    create_time       timestamp   default CURRENT_TIMESTAMP not null comment '创建时间',
    modifier          varchar(64) default ''                null comment '修改人',
    modify_time       timestamp   default CURRENT_TIMESTAMP null comment '修改时间',
    yn                tinyint     default 0                 null comment '删除标识，1：已删除，0：未删除'
)
    comment '回复';

