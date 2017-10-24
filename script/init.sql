create database auth character set utf8;
use auth;
CREATE TABLE authority
(
id bigint NOT NULL AUTO_INCREMENT,
name varchar(255),
target varchar(255),
magic_key varchar(255),
explains text,
PRIMARY KEY (id)
);

CREATE TABLE role
(
id bigint NOT NULL AUTO_INCREMENT,
name varchar(255),
target varchar(255),
magic_key varchar(255),
explains text,
PRIMARY KEY (id)
);

CREATE TABLE auth_role
(
id bigint NOT NULL AUTO_INCREMENT,
auth_id bigint,
role_id bigint,
magic_key varchar(255),
PRIMARY KEY (id)
);

CREATE TABLE auth_person
(
id bigint NOT NULL AUTO_INCREMENT,
auth_id bigint,
user_id varchar(255),
magic_key varchar(255),
PRIMARY KEY (id)
);

CREATE TABLE role_person
(
id bigint NOT NULL AUTO_INCREMENT,
role_id bigint,
user_id varchar(255),
magic_key varchar(255),
PRIMARY KEY (id)
);

CREATE TABLE auth_shop
(
id bigint NOT NULL AUTO_INCREMENT,
auth_id bigint,
shop_uuid varchar(255),
magic_key varchar(255),
PRIMARY KEY (id)
);

CREATE TABLE role_shop
(
id bigint NOT NULL AUTO_INCREMENT,
role_id bigint,
shop_uuid varchar(255),
magic_key varchar(255),
PRIMARY KEY (id)
);

CREATE TABLE token
(
id bigint NOT NULL AUTO_INCREMENT,
active_time int DEFAULT '1',
token varchar(255),
userID varchar(255),
magic_key varchar(255),
token_time DATETIME,
PRIMARY KEY (id)
);

alter table token add index token_index1(`token`,`magic_key`);
alter table token add index token_index2(`userID`,`magic_key`);
alter table token add index token_index3(`token`);
alter table token add index token_index4(`magic_key`);

alter table authority add index authority_index1(name,magic_key);
alter table role add index role_index1(name,magic_key);

CREATE TABLE login
(
id bigint NOT NULL AUTO_INCREMENT,
username varchar(255),
password varchar(255),
phone varchar(255),
email varchar(255),
openid varchar(255),
magic_key varchar(255),
PRIMARY KEY (id)
);

alter table login add index login1(username,magic_key);
alter table login add index login2(email,magic_key);
alter table login add index login3(phone,magic_key);
alter table login add index login4(openid,magic_key);

CREATE TABLE user
(
id bigint,
memberlevel int,
isartist int,
sex varchar(255), 
username varchar(255),
nickname varchar(255),
phone varchar(255),
email varchar(255),
percardnum varchar(255),
realname varchar(255),
address varchar(255),
magic_key varchar(255),
introduce text,
registertime DATETIME,
PRIMARY KEY (id)
);

alter table user add index user1(username,magic_key);
alter table user add index user2(email,magic_key);
alter table user add index user3(phone,magic_key);

CREATE TABLE headpic
(
id bigint NOT NULL AUTO_INCREMENT,
userid bigint,
pic_path varchar(255),
magic_key varchar(255),
PRIMARY KEY (id)
);

CREATE TABLE store_login
(
id bigint NOT NULL AUTO_INCREMENT,
password varchar(255),
phone varchar(255),
account_type varchar(255),
magic_key varchar(255),
PRIMARY KEY (id)
);

alter table store_login add index store_login(phone,magic_key);
alter table store_login add index store_login2(phone);
alter table store_login add column account_type varchar(255);

CREATE TABLE wx_info
(
id bigint NOT NULL AUTO_INCREMENT,
app_id varchar(255),
app_secret varchar(255),
magic_key varchar(255),
PRIMARY KEY (id)
);

alter table wx_info add index wx_info1(magic_key);