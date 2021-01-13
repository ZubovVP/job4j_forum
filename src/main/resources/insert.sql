insert into authorities (authority) values ('ROLE_USER');
insert into authorities (authority) values ('ROLE_ADMIN');

-- password - 123456
insert into users (username, password, authority_id)
values ('user', '$2a$10$5s/0M5JsZdYsMQdbp2rR1urEg.0gvEBEJDO09ACMPOJBI159G8FkK',
(select id from authorities where authority = 'ROLE_ADMIN'));