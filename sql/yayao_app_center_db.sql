# 数据库 
#创建数据库
DROP DATABASE IF EXISTS yayao_app_center_db;
CREATE DATABASE yayao_app_center_db;

#使用数据库 
use yayao_app_center_db;

#创建角色表
CREATE TABLE role_tb(
role_id int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
name varchar(255) COMMENT '角色名',
duty varchar(255) COMMENT '角色职责',
update_date datetime COMMENT '更新时间',
PRIMARY KEY (role_id)
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='角色表';

#创建账户表 
CREATE TABLE acount_tb(
acount_id int(11) NOT NULL AUTO_INCREMENT COMMENT '账户id',
nickname varchar(255) COMMENT '昵称',
icon varchar(255) COMMENT '图像',
scale decimal(11,2) DEFAULT 0 COMMENT '合伙人收益比例',
openid varchar(255) COMMENT 'openid',
uuid varchar(255) COMMENT 'uuid',
sex tinyint(4) DEFAULT 0 COMMENT '性别,默认为0未知，为1男性，为2女性',
country varchar(255) COMMENT '国家',
province varchar(255) COMMENT '省',
city varchar(255) COMMENT '城市',
realname varchar(255) COMMENT '真实姓名',
phone varchar(255) COMMENT '电话',
email varchar(255) COMMENT 'email',
password varchar(255) COMMENT '密码',
identity_cards varchar(255) COMMENT '身份证',
qq varchar(255) COMMENT 'QQ',
wechat varchar(255) COMMENT '微信号',
microblog varchar(255) COMMENT '微博',
alipay varchar(255) COMMENT '支付宝账号',
create_date datetime COMMENT '创建时间',
login_date datetime COMMENT '登陆时间',
status tinyint DEFAULT 0 COMMENT '状态，默认0正常，1锁定，2，异常',
spread_id int(11) COMMENT '推广id',
master_id int(11) COMMENT '师傅id',
role_id int(11) COMMENT '角色id外键',
PRIMARY KEY (acount_id),
INDEX INDEX_SPREADID (spread_id) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='账户表';

#创建财务表 
CREATE TABLE finance_tb(
finance_id int(11) NOT NULL AUTO_INCREMENT COMMENT '财务id',
recharge decimal(11,2) DEFAULT 0 COMMENT '充值金额',
update_date datetime COMMENT '更新时间',
acount_id int(11) COMMENT '账户id外键',
PRIMARY KEY (finance_id)
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='财务表';

#创建应用表 
CREATE TABLE app_tb(
app_id int(11) NOT NULL AUTO_INCREMENT COMMENT '应用id',
platform tinyint(4) COMMENT '平台，1安卓，2IOS，3，H5',
source varchar(255) COMMENT '应用来源',
version varchar(255) COMMENT '版本',
title varchar(255) COMMENT '应用名称',
capacity decimal(11,2) COMMENT '容量，单位MB',
divide_into_proportion decimal(11,2) COMMENT '分成比例，默认0.5,',
img_address varchar(255) COMMENT '封面图片',
content longtext COMMENT '应用内容',
register_number bigint(20) DEFAULT 0 COMMENT '注册次数',
recharge  decimal(11,2) DEFAULT 0 COMMENT '充值金额',
url varchar(255) COMMENT '跳转url',
status tinyint(4) DEFAULT 1 COMMENT '下架0,上架1',
create_date datetime  COMMENT '创建时间',
update_date datetime  COMMENT '更新时间',
PRIMARY KEY (app_id),
INDEX INDEX_PLATFORM (platform) USING BTREE,
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_UPDATEDATE (update_date) USING BTREE,
INDEX INDEX_STATUS (status) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='应用表';

#创建应用图片表 
CREATE TABLE app_img_tb(
app_img_id int(11) NOT NULL AUTO_INCREMENT COMMENT '应用图片id',
img_address varchar(255) COMMENT '应用图地址',
order_num int(11) COMMENT '排序数字',
update_date datetime  COMMENT '应用图片更新时间',
app_id int(11) COMMENT '应用id,外键',
PRIMARY KEY (app_img_id),
INDEX INDEX_APPID (app_id) USING BTREE,
INDEX INDEX_ORDERNUM (order_num) USING BTREE,
INDEX INDEX_UPDATEDATE (update_date) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='应用图片表';


#创建每日数据表 
CREATE TABLE daily_data_tb(
daily_data_id int(11) NOT NULL AUTO_INCREMENT COMMENT '数据id',
register_number bigint(20) COMMENT '注册数',
recharge  decimal(11,2) DEFAULT 0 COMMENT '充值金额',
record_date datetime COMMENT '记录时间',
create_date datetime COMMENT '创建时间',
app_id int(11) COMMENT '应用id外键',
acount_id int(11) COMMENT '账户id外键',
PRIMARY KEY (daily_data_id),
INDEX INDEX_RECORDDATE (record_date) USING BTREE,
INDEX INDEX_APPID (app_id) USING BTREE,
INDEX INDEX_ACOUNTID (acount_id) USING BTREE,
UNIQUE INDEX DAY_DATA (record_date,app_id,acount_id) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='每日数据表';

#设置初始角色
INSERT IGNORE INTO role_tb (name,duty,update_date) 
VALUES ("超级管理员","超级管理员",now());
INSERT IGNORE INTO role_tb (name,duty,update_date) 
VALUES ("运营管理员","运营管理员",now());

#设置初始管理员密码MD5加密123456
INSERT IGNORE INTO acount_tb (nickname,scale,phone,email,password,create_date,login_date,role_id) 
VALUES ("聂跃",0,"15111336587","278076304@qq.com","11874bb6149dd45428da628c9766b252",now(),now(),"1000"); 
#财务
INSERT IGNORE INTO finance_tb (recharge,update_date,acount_id) 
VALUES (0,now(),1000); 