/**
  테이블 Create SQL
 */

-- user
drop table if exists user CASCADE;
CREATE TABLE user(
    user_id int NOT NULL AUTO_INCREMENT primary key,
    user_name varchar(50) NOT NULL,
    nickname varchar(50) NOT NULL,
    email varchar(50),
    phone_number varchar(50),
    login_type char(1),
    access_token text,
    refresh_token text,
    active bool NOT NULL default true,
    registered_date datetime NOT NULL default CURRENT_TIMESTAMP,
    registered_id varchar(20) NOT NULL,
    modified_date datetime NOT NULL default CURRENT_TIMESTAMP,
    modified_id varchar(20) NOT NULL
);

-- wish
drop table if exists wish CASCADE;
CREATE TABLE wish(
    seq int NOT NULL AUTO_INCREMENT primary key,
    user_id int NOT NULL,
    artice_id int NOT NULL,
    active bool NOT NULL default true,
    registered_date datetime NOT NULL default CURRENT_TIMESTAMP,
    registered_id varchar(20) NOT NULL,
    modified_date datetime NOT NULL default CURRENT_TIMESTAMP,
    modified_id varchar(20) NOT NULL
);

-- category
drop table if exists category CASCADE;
CREATE TABLE category(
    seq int NOT NULL AUTO_INCREMENT primary key,
    artice_id int NOT NULL,
    category varchar(50) NOT NULL,
    active bool NOT NULL default true,
    registered_date datetime NOT NULL default CURRENT_TIMESTAMP,
    registered_id varchar(20) NOT NULL,
    modified_date datetime NOT NULL default CURRENT_TIMESTAMP,
    modified_id varchar(20) NOT NULL
);

-- notice
drop table if exists notice CASCADE;
CREATE TABLE notice(
    notice_id int NOT NULL AUTO_INCREMENT primary key,
    user_id int NOT NULL,
    message text NOT NULL,
    type char(1) NOT NULL,
    active bool NOT NULL default true,
    registered_date datetime NOT NULL default CURRENT_TIMESTAMP,
    registered_id varchar(20) NOT NULL,
    modified_date datetime NOT NULL default CURRENT_TIMESTAMP,
    modified_id varchar(20) NOT NULL
);

-- notice_keword
drop table if exists notice_keyword CASCADE;
CREATE TABLE notice_keyword(
    notice_keyword_id int NOT NULL AUTO_INCREMENT primary key,
    user_id int NOT NULL,
    keyword varchar(50) NOT NULL,
    active bool NOT NULL default true,
    registered_date datetime NOT NULL default CURRENT_TIMESTAMP,
    registered_id varchar(20) NOT NULL,
    modified_date datetime NOT NULL default CURRENT_TIMESTAMP,
    modified_id varchar(20) NOT NULL
);

-- user_region
drop table if exists user_region CASCADE;
CREATE TABLE user_region(
    seq int NOT NULL auto_increment primary key,
    user_id int NOT NULL,
    region_id int NOT NULL,
    represent_region_status char(1) NOT NULL,
    region_range_status char(1) NOT NULL,
    longitude double NOT NULL,
    latitude double NOT NULL,
    active bool NOT NULL default true,
    registered_date datetime NOT NULL default CURRENT_TIMESTAMP,
    registered_id varchar(20) NOT NULL,
    modified_date datetime NOT NULL default CURRENT_TIMESTAMP,
    modified_id varchar(20) NOT NULL
);

-- region
drop table if exists region CASCADE;
CREATE TABLE region(
    region_id int NOT NULL AUTO_INCREMENT primary key,
    region_type varchar(200),
    address_name varchar(200),
    region_1depth_name varchar(200),
    region_2depth_name varchar(200),
    region_3depth_name varchar(200),
    region_4depth_name varchar(200),
    code varchar(200),
    longitude double NOT NULL,
    latitude double NOT NULL,
    active bool NOT NULL default true,
    registered_date datetime NOT NULL default CURRENT_TIMESTAMP,
    registered_id varchar(20) NOT NULL,
    modified_date datetime NOT NULL default CURRENT_TIMESTAMP,
    modified_id varchar(20) NOT NULL
);

-- article
drop table if exists article CASCADE;
CREATE TABLE article(
    article_id int NOT NULL AUTO_INCREMENT primary key,
    user_id int NOT NULL,
    comment_id int,
    region_id int NOT NULL,
    group_id int,
    subject varchar(200) NOT NULL,
    context text NOT NULL,
    article_type int NOT NULL,
    price int,
    active bool NOT NULL default true,
    registered_date datetime NOT NULL default CURRENT_TIMESTAMP,
    registered_id varchar(20) NOT NULL,
    modified_date datetime NOT NULL default CURRENT_TIMESTAMP,
    modified_id varchar(20) NOT NULL
);

-- comment
drop table if exists comment CASCADE;
CREATE TABLE comment(
    comment_id int NOT NULL AUTO_INCREMENT primary key,
    article_id int NOT NULL,
    user_id int NOT NULL,
    comment text NOT NULL,
    active bool NOT NULL default true,
    registered_date datetime NOT NULL default CURRENT_TIMESTAMP,
    registered_id varchar(20) NOT NULL,
    modified_date datetime NOT NULL default CURRENT_TIMESTAMP,
    modified_id varchar(20) NOT NULL
);

-- community
drop table if exists community CASCADE;
CREATE TABLE community(
    community_id int NOT NULL AUTO_INCREMENT primary key,
    room_id int NOT NULL,
    community_name varchar(200) NOT NULL,
    community_introduce text,
    active bool NOT NULL default true,
    registered_date datetime NOT NULL default CURRENT_TIMESTAMP,
    registered_id varchar(20) NOT NULL,
    modified_date datetime NOT NULL default CURRENT_TIMESTAMP,
    modified_id varchar(20) NOT NULL
);

-- community_user
drop table if exists community_user CASCADE;
CREATE TABLE community_user(
    seq int NOT NULL AUTO_INCREMENT primary key,
    community_id int NOT NULL,
    user_id int NOT NULL,
    position char(1) NOT NULL,
    join_yn char(1) NOT NULL,
    active bool NOT NULL default true,
    registered_date datetime NOT NULL default CURRENT_TIMESTAMP,
    registered_id varchar(20) NOT NULL,
    modified_date datetime NOT NULL default CURRENT_TIMESTAMP,
    modified_id varchar(20) NOT NULL
);

-- chat_room
drop table if exists chat_room CASCADE;
CREATE TABLE chat_room(
    chat_room_id int NOT NULL AUTO_INCREMENT primary key,
    article_id int,
    chat_room_name varchar(200),
    active bool NOT NULL default true,
    registered_date datetime NOT NULL default CURRENT_TIMESTAMP,
    registered_id varchar(20) NOT NULL,
    modified_date datetime NOT NULL default CURRENT_TIMESTAMP,
    modified_id varchar(20) NOT NULL
);

-- chat_room_user
drop table if exists chat_room_user CASCADE;
CREATE TABLE chat_room_user(
    seq int NOT NULL AUTO_INCREMENT primary key,
    chat_room_id int NOT NULL,
    user_id int NOT NULL,
    active bool NOT NULL default true,
    registered_date datetime NOT NULL default CURRENT_TIMESTAMP,
    registered_id varchar(20) NOT NULL,
    modified_date datetime NOT NULL default CURRENT_TIMESTAMP,
    modified_id varchar(20) NOT NULL
);

-- chat
drop table if exists chat CASCADE;
CREATE TABLE chat(
    chat_id int NOT NULL AUTO_INCREMENT primary key,
    chat_room_id int NOT NULL,
    user_id int NOT NULL,
    message text NOT NULL,
    active bool NOT NULL default true,
    registered_date datetime NOT NULL default CURRENT_TIMESTAMP,
    registered_id varchar(20) NOT NULL,
    modified_date datetime NOT NULL default CURRENT_TIMESTAMP,
    modified_id varchar(20) NOT NULL
);

-- sequences
drop table if exists sequences CASCADE;
CREATE TABLE sequences(
    name varchar(32) DEFAULT NULL,
    currval bigint unsigned DEFAULT NULL,
);
