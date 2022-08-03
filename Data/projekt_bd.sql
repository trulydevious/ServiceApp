create database service;
use service;

create table Clients(
id int auto_increment primary key,
name char(100),
phone_number char(12),
email char(100)
);

create table Reclamations(
id int auto_increment primary key,
price decimal(6,2),
reclamation_date date,
client_id int,
FOREIGN KEY (client_id) REFERENCES Clients(id),
related_device char(30),
FOREIGN KEY (related_device) REFERENCES Device(serial_no),
reclamation_status char(30)
);

create table Device(
serial_no char(30) primary key,
device_name char(50),
type char(50),
device_price decimal(6,2),
bought_date date
);

create table login_client(
user_id int,
FOREIGN KEY (user_id) REFERENCES Clients(id),
password blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create table login_service(
admin_id varchar(30),
password blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO login_client (user_id, password) VALUES (1, AES_ENCRYPT('pass1', 'secret'));
INSERT INTO login_client (user_id, password) VALUES (2, AES_ENCRYPT('pass2', 'secret'));
INSERT INTO login_client (user_id, password) VALUES (3, AES_ENCRYPT('pass3', 'secret'));
INSERT INTO login_client (user_id, password) VALUES (4, AES_ENCRYPT('pass4', 'secret'));
INSERT INTO login_client (user_id, password) VALUES (5, AES_ENCRYPT('pass5', 'secret'));

INSERT INTO login_service (admin_id, password) VALUES ('admin1', AES_ENCRYPT('password1', 'secret'));
INSERT INTO login_service (admin_id, password) VALUES ('admin2', AES_ENCRYPT('password2', 'secret'));

select * from login_client;
-- select from_base64( (select password FROM login_service where admin_id = 'admin1') );
SELECT AES_DECRYPT(password, 'secret') AS pass FROM login_service where admin_id = 'admin1';


drop view reclamation_status_view;
create view reclamation_status_view as 
select rec.client_id, device_name, serial_no, price, reclamation_status
from Reclamations rec
join Device dev on rec.related_device = dev.serial_no
join Clients cl on rec.client_id = cl.id;


drop trigger before_reclamations_insert;
DELIMITER $$
create trigger before_reclamations_insert
before insert
on Reclamations for each row
begin 
   set new.reclamation_status = 'reported';
end $$
DELIMITER ;


drop function calculate_rec_price;
DELIMITER //
create function calculate_rec_price(device_number char(30))
returns decimal(6,2)
DETERMINISTIC
begin

   declare calculated_price decimal(6,2);
   declare passed_time int;
   declare price_for_device decimal(6,2);
   declare date_for_device date;
   
   set price_for_device = (select d.device_price from Device d where serial_no like device_number);
   set date_for_device = (select bought_date from Device where serial_no like device_number);
   set passed_time = abs( (select TIMESTAMPDIFF(year, date_for_device, now()) ) );
   
   if (passed_time > 1) then set calculated_price = 0.1 * price_for_device;
   else set calculated_price = 0;
   end if;
   
   return calculated_price;
end; //


DELIMITER ;
drop procedure check_admin_user;
DELIMITER //
CREATE PROCEDURE check_admin_user(in login varchar(30), in pass varchar(30))
BEGIN

declare admin_login varchar(30);
declare admin_pass varchar(30);

set admin_login = (select admin_id from login_service where admin_id = login);
set admin_pass = (SELECT AES_DECRYPT(password, 'secret') FROM login_service where admin_id = login);

if (admin_login = login and admin_pass = pass) then set @iftrue_admin = 1;
else set @iftrue_admin = 0;
end if;

select @iftrue_admin;
set @iftrue_admin = 0;

END; //
DELIMITER ;


DELIMITER ;
drop procedure check_client_user;
DELIMITER //
CREATE PROCEDURE check_client_user(in login int, in pass varchar(30))
BEGIN

declare client_login int;
declare client_pass varchar(30);

set client_login = (select user_id from login_client where user_id = login);
set client_pass = (SELECT AES_DECRYPT(password, 'secret') FROM login_client where user_id = login);

if (client_login = login and client_pass = pass) then set @iftrue_client = 1;
else set @iftrue_client = 0;
end if;

select @iftrue_client;
set @iftrue_client = 0;

END; //
DELIMITER ;

call check_admin_user('admin1','password1');
call check_client_user(1,'pass1');


SET FOREIGN_KEY_CHECKS = 0;
SET FOREIGN_KEY_CHECKS = 1;


create user 'access'@'localhost' identified by 'access';
grant execute on procedure check_admin_user to 'access'@'localhost';
grant execute on procedure check_client_user to 'access'@'localhost';

create user 'client'@'localhost' identified by 'client';
grant select on reclamation_status_view to 'client'@'localhost';
grant insert on reclamation_status_view to 'client'@'localhost';
grant select on Clients to 'client'@'localhost';
grant insert on Clients to 'client'@'localhost';
grant insert on Device to 'client'@'localhost';
grant insert on Reclamations to 'client'@'localhost';
grant execute on function calculate_rec_price to 'client'@'localhost';
grant trigger on service.Reclamations to 'client'@'localhost';
grant insert on login_client to 'client'@'localhost';

create user 'admin'@'localhost' identified by 'admin';
grant select, update on Reclamations to 'admin'@'localhost';
grant select on Clients to 'admin'@'localhost';
