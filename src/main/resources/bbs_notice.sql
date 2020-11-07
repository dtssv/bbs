create table notice
(
    id            int auto_increment
        primary key,
    category_id   int          default 0                 null comment '分类',
    category_name varchar(32)  default ''                null comment '分类名称',
    notice_body   varchar(256) default ''                not null comment '公告内容',
    creator       varchar(64)  default ''                not null comment '创建人',
    create_time   timestamp    default CURRENT_TIMESTAMP null comment '创建时间',
    modifier      varchar(64)  default ''                null comment '修改人',
    modify_time   timestamp    default CURRENT_TIMESTAMP null comment '修改时间',
    start_time    timestamp    default CURRENT_TIMESTAMP null comment '公告开始时间',
    end_time      timestamp                              null comment '公告结束时间，为空则为一直有效',
    yn            tinyint      default 0                 null comment '删除标识，1：已删除，0：未删除',
    status        int          default 1                 null comment '状态，1：有效，0：失效',
    link_url      varchar(256) default ''                null comment '跳转地址'
)
    comment '公告';

