create table user
(
    id            bigint auto_increment
        primary key,
    user_name     varchar(32) default ''                not null comment '登录用户名',
    password      varchar(100) default ''                not null comment '密码',
    phone         varchar(16) default ''                null comment '手机号',
    email         varchar(32) default ''                null comment '邮箱',
    nick_name     varchar(16) default ''                null comment '昵称',
    register_time timestamp   default CURRENT_TIMESTAMP not null comment '注册时间',
    comment_num   int         default 0                 null comment '回复数量',
    post_num      int         default 0                 null comment '帖子数量',
    head_photo    varchar(64) default ''                null comment '头像',
    creator       varchar(64) default ''                not null comment '创建人',
    create_time   timestamp   default CURRENT_TIMESTAMP null comment '创建时间',
    modifier      varchar(64) default ''                null comment '修改人',
    city          varchar(100) default ''                null comment '城市',
    sign          varchar(200) default ''                null comment '签名',
    modify_time   timestamp   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
    status        int         default 1                 null comment '状态，1：正常，0：禁止登录，-1：禁止发言',
    sex           int         default 0                 null comment '状态，1：男，0：未知，2：女',
    yn            tinyint     default 0                 null comment '删除标识，1：已删除，0：未删除',
    admin         int         default 0                 null comment '管理员标识,1:管理员，0：普通用户'
);

INSERT INTO bbs.user (id, user_name, password, phone, email, nick_name, register_time, comment_num, post_num, creator, create_time, modifier, modify_time, status, yn, admin) VALUES (1, 'root', '123456', '18812345678', null, 'zs', '2020-10-18 21:20:05', 0, 0, '', '2020-11-07 08:37:52', '', '2020-11-07 08:37:52', 1, 0, 0);