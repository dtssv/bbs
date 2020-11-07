create table operator_log
(
    id            int auto_increment
        primary key,
    operator_key  varchar(64) default ''                null comment '操作内容',
    operator_type int         default 0                 null comment '操作类型',
    operator      varchar(64) default ''                not null comment '操作人',
    creator       varchar(64) default ''                not null comment '添加人',
    create_time   timestamp   default CURRENT_TIMESTAMP not null comment '添加时间',
    modifier      varchar(64) default ''                null comment '修改人',
    modify_time   timestamp   default CURRENT_TIMESTAMP null comment '修改时间',
    yn            tinyint     default 0                 null comment '删除标识，1：已删除，0：未删除'
)
    comment '操作日志';

