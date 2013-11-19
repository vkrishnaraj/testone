create table lfshipping (id integer  IDENTITY primary key, hashKey varchar(50), lost_id integer,client_id integer ,phone_id integer ,shippingaddress_id integer ,billingaddress_id integer ,shippingoption varchar(50) ,totalpayment varchar(50), transaction_id integer,authorizationcode varchar(100), declaredValue decimal default 0, shippingName varchar(200),paidTimeStamp datetime );

alter table lflost add shipment_id integer;
alter table lfitem add weight float default 1.0;


alter table lfphone add countryNumber varchar(511);
alter table lfphone add areaNumber varchar(511);
alter table lfphone add exchangeNumber varchar(511);
alter table lfphone add lineNumber varchar(511);
select * into lfnamealias from fsnamealias;

alter table lflost add firstName varchar(50);
alter table lflost add middleName varchar(50);
alter table lflost add lastName varchar(50);
create table lftransaction (id integer primary key identity, amt varchar(100), swchKey varchar(100), authCode varchar(100), transactionDate datetime, stan varchar(100), tranNum varchar(100), rspCode varchar(2), shipment_id integer);
alter table lfmatchhistory add matchTimeStamp datetime;
alter table lflost add feedback varchar(500);
insert into systemcomponents (component_id,component_name ,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (631,'Items Ready for Shipping','View items ready for shipping',15,'view_to_be_shipped.do',1,5,5);

insert into systemcomponents (component_id,component_name ,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (707,'Add New Contents','Add contents to a bag to incidents that are open and can have remarks added',15,'',0,99,0);
insert into systemcomponents (component_id,component_name ,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (708,'Delete Bagtags to Bags','Allows to delete bags by deleting the corresponding bagtag numbers when creating Incidents',15,'',0,99,0);
insert into systemcomponents (component_id,component_name ,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (511,'Clone OHDs','Allows to clone passenger information into a new OHD',1,'',0,17,0);

alter table lflost add email3 bit default 0;
alter table lflost add email4 bit default 0;
alter table lflost add email5 bit default 0;
GO
update lflost set email3 = 0;
update lflost set email4 = 0;
update lflost set email5 = 0;

alter table subcompany add email_notice_3 integer default 0;
alter table subcompany add email_notice_4 integer default 0;
alter table subcompany add email_notice_5 integer default 0;
GO
update subcompany set email_notice_3 = 0;
update subcompany set email_notice_4 = 0;
update subcompany set email_notice_5 = 0;

alter table subcompany alter COLUMN email_notice_1 integer;
alter table subcompany alter COLUMN email_notice_2 integer;
GO
alter table subcompany ADD DEFAULT ((0)) FOR email_notice_1;
alter table subcompany ADD DEFAULT ((0)) FOR email_notice_2;

alter table subcompany add shippingSurcharge integer default 5;
GO
update subcompany set shippingSurcharge = 5;