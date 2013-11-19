alter table fsclaim add createagent_id int;

CREATE TABLE attachment 
(id integer NOT NULL identity,
claim_id integer DEFAULT NULL,
agent_id integer DEFAULT NULL,
createdate datetime DEFAULT NULL,
attachment_id integer DEFAULT NULL,
description varchar(200) DEFAULT NULL, 
PRIMARY KEY (id));

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (605,'Shared Attachments','Allow Sharing Files on FS',59,'',0,0,0);
update properties set valueStr='NTServices_1_0/ClaimClientBeanV3/remote' where keyStr='fraud.server.name';

create table subcompany (id integer not null identity, subcompanycode varchar(3) not null, company_id varchar(2) not null, name varchar(50),
  email_subject varchar(200), email_path varchar(200), email_notice_1 varchar(200), email_notice_2 varchar(200), auto_close_low  integer, 
  auto_close_high integer, primary key(id));
  
create table subcompanystation (id integer not null identity, subcompany_id integer not null,station_id integer not null, primary key(id));

alter table agent add subcompany_id integer;

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (625,'Maintain Subcompanies','Maintain Subcompanies' ,39,'subCompAdmin.do',1,3   ,0);

insert into group_component_policy (component_id,group_id) VALUES (625,1);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
VALUES (801,'Fraud Forum Search','Fraud Forum Search Page',59,'fraud_forum_search.do',1,98,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
VALUES (802,'Fraud Forum Create Thread','Fraud Forum Create Page',59,'fraud_forum_create.do',1,99,0);

alter table audit_address alter column zip varchar(11);#cound not run on dev-fs, table locked
alter table audit_companycode alter column zip varchar(11);
alter table audit_lost_found alter column customer_zip varchar(11);
alter table audit_ohd_address alter column zip varchar(11);
alter table audit_station alter column zip varchar(11);
alter table address alter column zip varchar(11);
alter table companycode alter column zip varchar(11);
alter table lost_found alter column customer_zip varchar(11);
alter table ohd_address alter column zip varchar(11);
alter table station alter column zip varchar(11);
alter table z_b6_claim_settlement alter column zip varchar(11);
alter table z_b6_audit_claim_settlement alter column zip varchar(11);

insert into properties (keyStr,valueStr) VALUES ('lf.tracing.name','80');
insert into properties (keyStr,valueStr) VALUES ('lf.tracing.address','60');
insert into properties (keyStr,valueStr) VALUES ('lf.tracing.serial','85');
insert into properties (keyStr,valueStr) VALUES ('lf.tracing.phone','75');
insert into properties (keyStr,valueStr) VALUES ('lf.tracing.email','85');


alter table passenger add languageKey varchar(50);
alter table passenger add languageFreeFlow varchar(50);
alter table lost_found add languageKey varchar(50);
alter table lost_found add languageFreeFlow varchar(50);
insert into properties (keystr,valuestr) values ('spoken.language.list','english,mandarin,spanish,portuguese,french,arabic,other');

insert into properties (keystr,valuestr) values ('tracing.status.block.wt','0');
alter table incident add tracingStarted datetime;
alter table incident add tracingComplete datetime;
alter table incident add tracing_agent_ID integer;
alter table audit_incident add tracingStarted datetime;
alter table audit_incident add tracingComplete datetime;
alter table audit_incident add tracing_agent_ID integer;

alter table item add childrestraint integer default 0;
GO
update item set childrestraint=0;
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (629,'Child Restraint System','Adds an option for a Child Restraint System Giveaway',15,'',0,5,0);

alter table item add externaldesc varchar(50);
alter table ohd add externaldesc varchar(50);
insert into table1 (rn  ,gtsv  ,minlen  ,maxlen  ,v10  ,v9  ,v8  ,v7  ,v6  ,v5  ,v4  ,v3  ,v2  ,v1  ,v0) VALUES ('BAGDESC',0 ,0 ,0 ,50,50,50  ,40  ,40  ,30  ,20  ,0  ,0  ,0  ,0);

insert into lookup_airline_codes (airline_2_character_code, airline_3_digit_ticketing_code) values ('VX','984');

insert into status (Status_ID, description,table_ID) VALUES (613,'Staged for Shipping',17);

insert into properties (valueStr,keyStr) values ('0','convert.bagtag');

update company_specific_variable set auto_close_days_back = 0 where auto_close_days_back is null;
update company_specific_variable set auto_close_ld_code = 0 where auto_close_ld_code is null;
update company_specific_variable set auto_close_dam_code = 0 where auto_close_dam_code is null;
update company_specific_variable set auto_close_pil_code = 0 where auto_close_pil_code is null;
update company_specific_variable set auto_close_ld_station = 0 where auto_close_ld_station is null;
update company_specific_variable set auto_close_dam_station = 0 where auto_close_dam_station is null;
update company_specific_variable set auto_close_pil_station = 0 where auto_close_pil_station is null;

update audit_company_specific_variable set auto_close_days_back = 0 where auto_close_days_back is null;
update audit_company_specific_variable set auto_close_ld_code = 0 where auto_close_ld_code is null;
update audit_company_specific_variable set auto_close_dam_code = 0 where auto_close_dam_code is null;
update audit_company_specific_variable set auto_close_pil_code = 0 where auto_close_pil_code is null;
update audit_company_specific_variable set auto_close_ld_station = 0 where auto_close_ld_station is null;
update audit_company_specific_variable set auto_close_dam_station = 0 where auto_close_dam_station is null;
update audit_company_specific_variable set auto_close_pil_station = 0 where auto_close_pil_station is null;


--testing environment
update properties set valuestr='testing' where keystr='lock.cache.cluster';
--training environment
update properties set valuestr='training' where keystr='lock.cache.cluster';
--production environment
update properties set valuestr='production' where keystr='lock.cache.cluster';
