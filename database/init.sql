DROP database IF EXISTS IRPC;
create database IRPC;
use IRPC;
create table employees (
 employeeID int auto_increment primary key,
 firstName varchar(30) not null,
 lastName varchar(30) not null
);