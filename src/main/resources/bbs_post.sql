create table post
(
    id                int auto_increment
        primary key,
    title             varchar(64) default ''                not null comment '帖子标题',
    post_body         text                                  not null comment '文章内容',
    user_id           int         default 0                 not null comment '发表人',
    nick_name         varchar(32) default ''                null comment '发表人昵称',
    creator           varchar(32) default ''                not null comment '创建人',
    create_time       timestamp   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '创建时间',
    modifier          varchar(32) default ''                null comment '修改人',
    modify_time       timestamp   default CURRENT_TIMESTAMP null comment '修改人',
    status            int         default 0                 not null comment '帖子状态，：0：正常，1：禁止回复，2：版主审核退回修改',
    yn                tinyint     default 0                 null comment '删除标识，1：已删除，0：未删除',
    comment_num       int         default 0                 null comment '回复数量',
    read_num          int         default 0                 null comment '阅读数量',
    category_id       int         default 0                 not null comment '分类',
    category_name     varchar(64) default ''                null comment '分类名称',
    last_comment_time timestamp   default CURRENT_TIMESTAMP null comment '最后回复时间',
    cream             tinyint     default 0                 null comment '加精标识，1：加精，0：未加精'
)
    comment '帖子';

