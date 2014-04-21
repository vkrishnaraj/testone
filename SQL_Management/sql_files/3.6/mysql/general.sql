#create table lfshipping (id int primary key auto_increment, hashKey varchar(50), lost_id bigint,client_id bigint ,phone_id bigint ,shippingaddress_id bigint ,billingaddress_id bigint ,shippingoption varchar(50) ,totalpayment varchar(50),transaction_id bigint(20) ,authorizationcode varchar(100), declaredValue double default 0, shippingName varchar(200) );
alter table lflost add column shipment_id bigint;
alter table lfitem add column weight float default 1.0;


alter table lfphone add column countryNumber varchar(511);
alter table lfphone add column areaNumber varchar(511);
alter table lfphone add column exchangeNumber varchar(511);
alter table lfphone add column lineNumber varchar(511);
create table if not exists fsnamealias like fs_production.fsnamealias;
create table lfnamealias like fsnamealias;

alter table lflost add column firstName varchar(50);
alter table lflost add column middleName varchar(50);
alter table lflost add column lastName varchar(50);

create table lftransaction (id int primary key auto_increment, amt varchar(100), swchKey varchar(100), authCode varchar(100), transactionDate datetime, stan varchar(100), tranNum varchar(100), rspCode varchar(2), shipment_id bigint);
alter table lfmatchhistory add column matchTimeStamp datetime;
alter table lfshipping add column paidTimeStamp datetime;
alter table lflost add column feedback varchar(500);
insert into systemcomponents (component_id,component_name ,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (631,'Items Ready for Shipping','View items ready for shipping',15,'view_to_be_shipped.do',1,5,5);

insert into systemcomponents (component_id,component_name ,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (707,'Add New Contents','Add contents to a bag to incidents that are open and can have remarks added',15,'',0,99,0);
insert into systemcomponents (component_id,component_name ,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (708,'Delete Bagtags to Bags','Allows to delete bags by deleting the corresponding bagtag numbers when creating Incidents',15,'',0,99,0);
insert into systemcomponents (component_id,component_name ,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (511,'Clone OHDs','Allows to clone passenger information into a new OHD',1,'',0,17,0);

alter table lflost add column email3 bit default 0;
alter table lflost add column email4 bit default 0;
alter table lflost add column email5 bit default 0;

alter table subcompany add column email_notice_3 int(11) default 0;
alter table subcompany add column email_notice_4 int(11) default 0;
alter table subcompany add column email_notice_5 int(11) default 0;

alter table subcompany modify COLUMN email_notice_1 int(11) default 0;
alter table subcompany modify COLUMN email_notice_2 int(11) default 0;

alter table subcompany add column shippingSurcharge integer default 5;

update properties set valueStr = 'http://service1.nettracer.aero/NTServices_1_0/services/ReservationService_1_0' where keyStr = 'booking.nt.endpoint';
update properties set valueStr = 'http://service1.nettracer.aero/NTServices_1_0/services/WorldTracerService' where keyStr = 'nt.central.endpoint';

update agent set web_enabled = 0;
update agent set password = '80B66C2EEDA92E6BFD59AEC4551DFE40888312594A133ED47BC3E7B23B1FE586', account_locked = 0, reset_password = 0, last_pass_reset_date = now(), web_enabled = 1 where username in ('ntadmin', 'ogadmin', 'ntagent');

--ADD DATAPLAN BOOLEAN TO SUBCOMPANY TABLE
alter table subcompany add sendDataplanEmails tinyint(1) not null default 0;