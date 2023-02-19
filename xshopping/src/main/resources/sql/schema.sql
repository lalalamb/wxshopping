CREATE DATABASE if NOT EXISTS wx_shop CHARACTER SET 'utf8';
USE wx_shop;
-- 商品类别表
CREATE TABLE if NOT EXISTS type_info(
id BIGINT(20) PRIMARY KEY auto_increment,
name VARCHAR(255) UNIQUE,
description VARCHAR(1000)
);
-- 商品详情表
CREATE TABLE if NOT EXISTS goods_info(
id BIGINT(20) PRIMARY KEY auto_increment,
name VARCHAR(255) UNIQUE,
description TEXT(0),
price DOUBLE(10,2),
discount DOUBLE(10,2),
sales INT(10),
hot INT(10),
recommend VARCHAR(255),
count INT(10),
type_id BIGINT(20),
user_id BIGINT(20),
FOREIGN KEY(type_id) REFERENCES type_info(id)
);
-- 商品订单信息表
CREATE TABLE if NOT EXISTS order_info(
id BIGINT(20) PRIMARY KEY auto_increment,
order_id BIGINT(20),
link_phone VARCHAR(255),
link_man VARCHAR(255),
create_time VARCHAR(255),
state VARCHAR(255),
total_price DOUBLE(10,2),
link_address VARCHAR(255),
user_id BIGINT(20),
business BIGINT(20)
);

-- 文件信息表
CREATE TABLE if NOT EXISTS nx_system_file_info(
id BIGINT(20) PRIMARY KEY auto_increment,
origin_name VARCHAR(255),
file_name VARCHAR(255),
goods_id BIGINT(20),
FOREIGN KEY(goods_id) REFERENCES goods_info(id)
);
-- 订单商品关系映射表
CREATE TABLE if NOT EXISTS order_goods_rel(
id BIGINT(20) PRIMARY KEY auto_increment,
goods_id BIGINT(20),
order_id BIGINT(20),
count INT(10),
total_price DOUBLE(10,2),
FOREIGN KEY(order_id) REFERENCES order_info(id)
);
-- 购物车信息表
CREATE TABLE if NOT EXISTS cart_info(
id BIGINT(20) PRIMARY KEY auto_increment,
goods_id BIGINT(20),
user_id BIGINT(20),
count INT(10),
create_time VARCHAR(255)
);
-- 商品评价表
CREATE TABLE if NOT EXISTS comment_info(
id BIGINT(20) PRIMARY KEY auto_increment,
goods_id BIGINT(20),
user_id BIGINT(20),
content TEXT(0),
seller_id BIGINT(20),
create_time VARCHAR(255),
FOREIGN KEY(goods_id) REFERENCES goods_info(id)
);
-- 用户信息表
CREATE TABLE if NOT EXISTS user_info(
id BIGINT(20) PRIMARY KEY auto_increment,
name VARCHAR(255) UNIQUE,
balance DOUBLE(10,2),
age INT(10),
password VARCHAR(255),
nick_name VARCHAR(255),
sex VARCHAR(255),
birthday VARCHAR(255),
addreess VARCHAR(255),
code VARCHAR(255),
phone VARCHAR(255),
card_id VARCHAR(255),
level INT(10)
);
-- 公告
CREATE TABLE if NOT EXISTS advertiser_info(
id BIGINT(20) PRIMARY KEY auto_increment,
name VARCHAR(255) UNIQUE,
create_time VARCHAR(255),
content LONGTEXT
);
