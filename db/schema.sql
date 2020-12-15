CREATE TABLE authorities (
  id serial primary key,
  authority VARCHAR(50) NOT NULL unique
);

CREATE TABLE users (
  id serial primary key,
  username VARCHAR(50) NOT NULL unique,
  password VARCHAR(100) NOT NULL,
  enabled boolean default true,
  authority_id int not null references authorities(id)
);

create table posts (
  id serial primary key,
  name varchar(2000) not null,
  description text,
  created timestamp without time zone not null default now(),
  id_user int not null references users(id)
);

create table comments (
  id serial primary key,
  comment text not null,
  created timestamp without time zone not null default now(),
  id_post int not null references posts(id),
  id_user int not null references users(id)
);

insert into authorities (authority) values ('ROLE_USER');
insert into authorities (authority) values ('ROLE_ADMIN');

-- password - 123456
insert into users (username, password, authority_id)
values ('user', '$2a$10$5s/0M5JsZdYsMQdbp2rR1urEg.0gvEBEJDO09ACMPOJBI159G8FkK',
(select id from authorities where authority = 'ROLE_ADMIN'));


