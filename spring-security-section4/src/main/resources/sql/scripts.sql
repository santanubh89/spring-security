create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);

insert into users values ('user', '{noop}12345', '1');
insert into users values ('admin', '{bcrypt}$2a$10$X04W4W.7C8IfHEjNRqQWNOcBOtHFUjIY3v0Xnt3.8GTau/d.9x.tO', '1');
insert into authorities values ('user', 'read');
insert into authorities values ('admin', 'admin');

create table customer
(
    id int generated always as identity primary key,
    email varchar(50) not null,
    pwd varchar(50) not null,
    role varchar(50) not null
);

insert into customer (email, pwd, role) values ('user@example.com', '{noop}12345', 'read');
insert into customer (email, pwd, role) values ('admin@example.com', '{bcrypt}$2a$10$X04W4W.7C8IfHEjNRqQWNOcBOtHFUjIY3v0Xnt3.8GTau/d.9x.tO', 'admin');