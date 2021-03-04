DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name varchar(255) not null,
    last_name varchar(255) not null
);

insert into users values (1,'Marcus','Lärk');
insert into users values (2,'Emmy','Lööf');
insert into users values (3,'Thom','Jones');