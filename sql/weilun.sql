CREATE DATABASE weilun CHARACTER SET "utf8"

CREATE TABLE db_version (
 id INT NOT NULL AUTO_INCREMENT ,
 version INT,
 primary key (id)
)
INSERT INTO `weilun`.`db_version`(`id`, `version`) VALUES (1, 1);