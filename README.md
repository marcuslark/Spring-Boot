# Spring-Boot
How to use --> Clone project -> root/src/main/java/resources/application.properties -> SETUP DATABASE -->
table of Users
primary key int id,
varchar(255) firstName,
varchar(255) lastName


MSSQL SYNTAX -->
                CREATE TABLE users (
                    id int IDENTITY (1,1) PRIMARY KEY,
                    first_name varchar(255) not null,
                    last_name varchar(255) not null
                );

TO RUN TESTS-->
Setup H2 Database -->
test/resources/data.sql & application.properties
![](https://i.pinimg.com/564x/78/a4/83/78a48347330a540794acb5bec0bb6ad4.jpg)
