

create database web_files;

-- 表示用户基本信息
create  table o_user (
    id unsigned int auto_increment primary key, // id
    key char(6) not null qu, // 用于认证
    pwd varchar(32), // 用于认证
    aplt varchar(64), // 名称
    regt datetime not null // 注册时间
)

-- 用户访问信息
create table u_visit (
    last datetime not null, // 最后访问时间
    userid unsigned int re o_user.id
)

// 表示文件基本信息
create table f_file (
    id unsigned int auto_increment primary key,
    length not null  char(12)
    md5 not null  char(32)
    key not null varchar(64) qu // md5-length
    crt not null datetime // 创建时间
)

// 用户和文件的关联
create table f_useradfile (
    userid unsigned int re o_user.id

)