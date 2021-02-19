CREATE TABLE roles
(
  id   serial primary key not null,
  name VARCHAR(255)       not null
);

CREATE TABLE persons
(
  id       serial primary key          not null,
  login    varchar(255)                UNIQUE not null,
  name     varchar(255),
  surname  varchar(255),
  password varchar(255),
  enabled boolean default true,
  created  bigint not null,
  role_id int not null references roles(id)
);

CREATE TABLE rooms
(
  id          serial primary key not NULL,
  name        VARCHAR(255)       not null,
  description VARCHAR(255)
);

CREATE table messages
(
  id        serial primary key not null,
  text      varchar(10000),
  created  timestamp without time zone not null default now(),
  id_person int                not null references persons (id),
  room_id int not null references rooms(id)
);

CREATE TABLE person_room
(
  person_id INT  NOT NULL,
  room_id   INT NOT NULL,
  PRIMARY KEY (person_id , room_id),
 CONSTRAINT FK_PERSONS FOREIGN KEY (person_id) REFERENCES persons (id),
 CONSTRAINT FK_ROOMS FOREIGN KEY (room_id) REFERENCES rooms (id)
);
