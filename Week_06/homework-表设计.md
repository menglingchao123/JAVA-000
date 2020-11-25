-- --------------------------------
-- 商品表
-- --------------------------------
create table t_product(
	id int(11) not null comment '主键',
	product_name varchar(32) not null DEFAULT '' comment '商品名称',
	image_url VARCHAR(64) not null DEFAULT '' comment '商品图片路径',
	product_no VARCHAR(32) not null DEFAULT comment '商品编号'
	category_id int(11) not null DEFAULT '0' comment '商品分类id',
	create_time datetime comment '创建时间',
	update_time bigint(20) comment '更新时间',
	primary key (id),
	UNIQUE uk_product_no (product_no)
)engine = INNODB DEFAULT charset = utf8mb4 comment = '商品表';

-- -------------------------------
-- 商品分类表
-- -------------------------------
create table t_category(
	id int(11) not null comment '主键',
	parent_id int(11) not null DEFAULT '0' comment '父级id',
	category_name VARCHAR(32) not null DEFAULT '' comment '商品分类名称',
	sort_value TINYINT(2) not null DEFAULT '0' comment '排序序号',
	create_time datetime comment '创建时间',
	update_time bigint(20) comment '更新时间',
	PRIMARY key (id)
)engine = INNODB DEFAULT charset = utf8mb4 comment '商品分类表';

-- -------------------------------
-- 商品规格参数表
-- -------------------------------
create table t_specs_parameters(
	id int(11) not null comment '主键',
	product_id int(11) not null DEFAULT '0' comment '商品id',
	product_specs varchar(255) not null DEFAULT '' comment '商品规格参数json模版形式',
	sort_value TINYINT(2) not null DEFAULT '0' comment '排序序号',
	product_stock int(11) not null DEFAULT '0' comment '商品库存',
	product_price DECIMAL(10,4) not null DEFAULT '0' comment '商品价格',
	create_time datetime comment '创建时间',
	update_time bigint(20) comment '更新时间',
	PRIMARY key (id)
)engine = INNODB DEFAULT charset = utf8mb4 comment '商品规格参数表';

-- -------------------------------
-- 商品快照表
-- -------------------------------
create table t_product_snapshoot(
	id int(11) not null comment '主键',
	product_id int(11) not null DEFAULT '0' comment '商品id',
	image_url VARCHAR(64) not null DEFAULT '' comment '商品图片路径',
	product_no VARCHAR(32) not null DEFAULT comment '商品编号',
	product_version int(11) not null DEFAULT '0' comment '商品版本号',
	product_specs varchar(255) not null DEFAULT '' comment '商品规格参数json模版形式',
	product_price DECIMAL(10,4) not null DEFAULT '0' comment '商品价格',
	category_id int(11) not null DEFAULT '0' comment '商品分类',
	order_no VARCHAR(32) not null DEFAULT '' comment '订单编号',
	sort_value TINYINT(2) not null DEFAULT '0' comment '排序序号',
	create_time datetime comment '创建时间',
	update_time bigint(20) comment '更新时间',
	PRIMARY key (id)
)engine = INNODB DEFAULT charset = utf8mb4 comment '商品快照表';

-- --------------------------------
-- 订单表
-- --------------------------------
create table t_order(
	id int(11) not null comment '主键',
	order_no VARCHAR(32) not null DEFAULT '' comment '订单编号',
	user_id int(11) not null DEFAULT '0' comment '用户id'
	total_price DECIMAL(10,4) not null DEFAULT '0' comment '订单总价格',
	create_time datetime comment '创建时间',
	update_time bigint(20) comment '更新时间',
	PRIMARY key (id)
)engine = INNODB DEFAULT charset = utf8mb4 comment '订单表';

-- ---------------------------------
-- 用户表
-- ---------------------------------
create table t_user(
	id int(11) not null comment '主键',
	name varchar(10) not null DEFAULT '' comment '名称',
	username VARCHAR(32) not null DEFAULT '' comment '用户名',
	password VARCHAR(32) not null DEFAULT '' comment '密码',
	phone VARCHAR(12) not null DEFAULT '' comment '手机号',
	email VARCHAR(12) not null DEFAULT '' comment '邮箱号',
	address VARCHAR(32) not null DEFAULT '' comment '用户地址',
	create_time datetime comment '创建时间',
	update_time bigint(20) comment '更新时间',
	PRIMARY key (id)
)ENGINE = INNODB DEFAULT charset = utf8mb4 comment '用户表';
