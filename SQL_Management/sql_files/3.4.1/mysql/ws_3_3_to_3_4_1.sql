update agent SET web_enabled=0 where username not in ('ntadmin', 'ogadmin','Sarah','4178','2817AP','2817IA','2817SU','2817CB','2817A','12401','12401IA','12401SU','12401CB','12401IT');

insert into lfcategory (description ,score ,companycode) VALUES ('Laptop/Computer', 0,'WS');
insert into lfcategory (description ,score ,companycode) VALUES ('Camera', 0,'WS');
insert into lfcategory (description ,score ,companycode) VALUES ('Cell Phone', 0,'WS');
insert into lfcategory (description ,score ,companycode) VALUES ('Tablet/E-Reader', 0,'WS');
insert into lfcategory (description ,score ,companycode) VALUES ('Head Phones', 0,'WS');
insert into lfcategory (description ,score ,companycode) VALUES ('Wallet/Purse', 0,'WS');
insert into lfcategory (description ,score ,companycode) VALUES ('Currency/Gift Card/Credit Card', 0,'WS');
insert into lfcategory (description ,score ,companycode) VALUES ('Book', 0,'WS');
insert into lfcategory (description ,score ,companycode) VALUES ('Coat/Jacket', 0,'WS');
insert into lfcategory (description ,score ,companycode) VALUES ('Misc/ Electronic Items', 0,'WS');
insert into lfcategory (description ,score ,companycode) VALUES ('Music', 0,'WS');
insert into lfcategory (description ,score ,companycode) VALUES ('Papers', 0,'WS');
insert into lfcategory (description ,score ,companycode) VALUES ('Jewelry', 0,'WS');
insert into lfcategory (description ,score ,companycode) VALUES ('Handheld Games', 0,'WS');
insert into lfcategory (description ,score ,companycode) VALUES ('Optics/Sunglasses', 0,'WS');
insert into lfcategory (description ,score ,companycode) VALUES ('Keys', 0,'WS');
insert into lfcategory (description ,score ,companycode) VALUES ('OTHER', 0,'WS');

insert into properties (keyStr,valueStr) VALUES ('default.report.seven','1');

insert into report (number,resource_key) values (210,'header.customreportnum.210');
insert into report (number,resource_key) values (96,'header.customreportnum.96');

update properties set valueStr='http://10.8.185.136:8680/NTServices_1_0/services/WorldTracerService' where keyStr = 'nt.central.endpoint';
update properties set valueStr='http://10.8.185.136:8680/NTServices_1_0/services/ReservationService_1_0' where keyStr = 'booking.nt.endpoint';