-- --------------------------------
-- 账户表
-- --------------------------------
create table TB_ACCOUNT(
    ID varchar(32) not null comment '主键',
    USER_ID varchar(32) not null default '' comment '用户id',
    DOLLAR decimal(10,4) not null default '0' comment '美元',
    RMB decimal(10,4) not null default '0' comment '人民币',
    create_time datetime comment '创建时间',
    update_time datetime comment '更新时间',
    primary key (ID)
)engine = InnoDB default charset = utf8mb4 comment = '账户表';

-- --------------------------------
-- 账户冻结表
-- --------------------------------
create table tb_freeze_account(
    ID varchar(32) not null comment '主键',
    ACCOUNT_ID varchar(32) not null default '' comment '用户id',
    DOLLAR decimal(10,4) not null default '0' comment '美元',
    RMB decimal(10,4) not null default '0' comment '人民币',
    DELETE_FLAG int(1) not null default '0' comment '删除标识(0表示存在1删除)',
    create_time datetime comment '创建时间',
    update_time datetime comment '更新时间',
    primary key (ID)
)engine = InnoDB default charset = utf8mb4 comment = '账户冻结表';