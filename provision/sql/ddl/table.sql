/*/
  테이블 Create SQL
 */

-- user
CREATE TABLE user(
    user_id varchar(20) NOT NULL primary key,
    user_name varchar(50) NOT NULL,
    nickname varchar(50) NOT NULL,
    email varchar(50),
    phone_number varchar(50),
    login_type char(1),
    access_token text,
    refresh_token text,
    active bool NOT NULL default true,
    reg_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    reg_id varchar(20) NOT NULL,
    mod_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    mod_id varchar(20) NOT NULL
);

-- wish
CREATE TABLE wish(
    seq int NOT NULL AUTO_INCREMENT primary key,
    user_id varchar(20) NOT NULL,
    artice_id varchar(20) NOT NULL,
    active bool NOT NULL default true,
    reg_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    reg_id varchar(20) NOT NULL,
    mod_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    mod_id varchar(20) NOT NULL
);

-- category
CREATE TABLE category(
    seq int NOT NULL AUTO_INCREMENT primary key,
    artice_id varchar(20) NOT NULL,
    category varchar(50) NOT NULL,
    active bool NOT NULL default true,
    reg_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    reg_id varchar(20) NOT NULL,
    mod_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    mod_id varchar(20) NOT NULL
);

-- notice
CREATE TABLE notice(
    notice_id varchar(20) NOT NULL primary key,
    user_id varchar(20) NOT NULL,
    message text NOT NULL,
    type char(1) NOT NULL,
    active bool NOT NULL default true,
    reg_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    reg_id varchar(20) NOT NULL,
    mod_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    mod_id varchar(20) NOT NULL
);

-- notice_keword
CREATE TABLE notice_keyword(
    notice_keyword_id varchar(20) NOT NULL primary key,
    user_id varchar(20) NOT NULL,
    keyword varchar(50) NOT NULL,
    active bool NOT NULL default true,
    reg_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    reg_id varchar(20) NOT NULL,
    mod_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    mod_id varchar(20) NOT NULL
);

-- user_region
CREATE TABLE user_region(
    seq int NOT NULL auto_increment primary key,
    user_id varchar(20) NOT NULL,
    region_id varchar(20) NOT NULL,
    represent_region_status char(1) NOT NULL,
    region_range_status char(1) NOT NULL,
    longitude double NOT NULL,
    latitude double NOT NULL,
    active bool NOT NULL default true,
    reg_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    reg_id varchar(20) NOT NULL,
    mod_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    mod_id varchar(20) NOT NULL
);

-- region
CREATE TABLE region(
    region_id varchar(20) NOT NULL primary key,
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
    reg_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    reg_id varchar(20) NOT NULL,
    mod_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    mod_id varchar(20) NOT NULL
);

-- article
CREATE TABLE article(
    article_id varchar(20) NOT NULL primary key,
    user_id varchar(20) NOT NULL,
    comment_id varchar(20),
    region_id varchar(20) NOT NULL,
    group_id varchar(20),
    subject varchar(200) NOT NULL,
    context text NOT NULL,
    article_gb int NOT NULL,
    price int,
    active bool NOT NULL default true,
    reg_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    reg_id varchar(20) NOT NULL,
    mod_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    mod_id varchar(20) NOT NULL
);

-- comment
CREATE TABLE comment(
    comment_id varchar(20) NOT NULL primary key,
    article_id varchar(20) NOT NULL,
    user_id varchar(20) NOT NULL,
    comment text NOT NULL,
    active bool NOT NULL default true,
    reg_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    reg_id varchar(20) NOT NULL,
    mod_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    mod_id varchar(20) NOT NULL
);

-- community
CREATE TABLE community(
    community_id varchar(20) NOT NULL primary key,
    room_id varchar(20) NOT NULL,
    community_name varchar(200) NOT NULL,
    community_introduce text,
    active bool NOT NULL default true,
    reg_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    reg_id varchar(20) NOT NULL,
    mod_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    mod_id varchar(20) NOT NULL
);

-- community_user
CREATE TABLE community_user(
    seq int NOT NULL AUTO_INCREMENT primary key,
    community_id varchar(20) NOT NULL,
    user_id varchar(20) NOT NULL,
    position char(1) NOT NULL,
    join_yn char(1) NOT NULL,
    active bool NOT NULL default true,
    reg_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    reg_id varchar(20) NOT NULL,
    mod_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    mod_id varchar(20) NOT NULL
);

-- chat_room
CREATE TABLE chat_room(
    chat_room_id varchar(20) NOT NULL primary key,
    article_id varchar(20),
    chat_room_name varchar(200),
    active bool NOT NULL default true,
    reg_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    reg_id varchar(20) NOT NULL,
    mod_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    mod_id varchar(20) NOT NULL
);

-- chat_room_user
CREATE TABLE chat_room_user(
    seq int NOT NULL AUTO_INCREMENT primary key,
    chat_room_id varchar(20) NOT NULL,
    user_id varchar(20) NOT NULL,
    active bool NOT NULL default true,
    reg_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    reg_id varchar(20) NOT NULL,
    mod_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    mod_id varchar(20) NOT NULL
);

-- chat
CREATE TABLE chat(
    chat_id varchar(20) NOT NULL primary key,
    chat_room_id varchar(20) NOT NULL,
    user_id varchar(20) NOT NULL,
    message text NOT NULL,
    active bool NOT NULL default true,
    reg_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    reg_id varchar(20) NOT NULL,
    mod_dt datetime NOT NULL default CURRENT_TIMESTAMP,
    mod_id varchar(20) NOT NULL
);