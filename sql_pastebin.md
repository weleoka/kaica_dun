# The following is the DDL for testing hibernate.
SET FOREIGN_KEY_CHECKS=0;

DELETE FROM dungeon;
DELETE FROM fighter;
DELETE FROM inventory;
DELETE FROM item;
DELETE FROM room;
DELETE FROM room_direction;
DELETE FROM user;

SET FOREIGN_KEY_CHECKS=1;



CREATE DATABASE kaica_dun_db;
CREATE USER 'kaica_dun_db_user'@'localhost' IDENTIFIED BY 'enter112';
GRANT ALL ON kaica_dun_db.* TO 'kaica_dun_db_user'@'localhost';

ALTER USER 'kaica_dun_db_user'@'localhost' IDENTIFIED BY 'enter112';

CREATE TABLE `employee` (                                
`id` int(11) NOT NULL AUTO_INCREMENT,                  
`emp_name` varchar(100) DEFAULT NULL,                  
`emp_address` varchar(500) DEFAULT NULL,               
`emp_mobile_nos` varchar(100) DEFAULT NULL,            
PRIMARY KEY (`id`)                                     
) ENGINE=InnoDB DEFAULT CHARSET=latin1;