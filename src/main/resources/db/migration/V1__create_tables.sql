create table USERS(
    id bigserial primary key,
    name varchar(30) not null ,
    email varchar(30) not null unique,
    password varchar(100) not null
);

create table PROFILES(
    id bigserial primary key,
    age int,
    cash double precision,
    user_email varchar(30) not null,
    FOREIGN KEY (user_email) references USERS(email)
);

create table PHONES(
     id bigserial primary key,
     user_email varchar(30) unique,
     value varchar(12) not null,
     FOREIGN KEY (user_email) references USERS(email)
);