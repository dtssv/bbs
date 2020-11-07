create table moderator
(
    id            int auto_increment
        primary key,
    category_id   int         default 0                 not null comment '分类id',
    category_name varchar(64) default ''                null comment '分类名称',
    user_id       int         default 0                 null comment '用户id',
    nick_name     varchar(64) default ''                null comment '用户昵称',
    creator       varchar(64) default ''                not null comment '添加人',
    create_time   timestamp   default CURRENT_TIMESTAMP null comment '新增时间',
    modifier      varchar(64) default ''                null comment '修改人',
    modify_time   timestamp   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
    status        int         default 1                 not null comment '状态：1，有效，0：失效',
    yn            tinyint     default 0                 not null comment '删除标识,1:已删除，0：未删除'
)
    comment '版主';

