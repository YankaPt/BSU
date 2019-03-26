drop table if exists persons;

create table persons (
  id BIGINT AUTO_INCREMENT primary key,
  firstName VARCHAR(254) NOT NULL,
  lastName VARCHAR(254) NOT NULL,
  bio VARCHAR(4096)
);

create table person2trait (
  personId BIGINT,
  trait VARCHAR(254),
  CONSTRAINT FK_person2trait_personId FOREIGN KEY (personId) REFERENCES persons (id) ON DELETE CASCADE ON UPDATE CASCADE
)