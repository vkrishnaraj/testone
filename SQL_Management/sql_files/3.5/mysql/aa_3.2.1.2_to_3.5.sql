insert into countrycode (
   CountryCode_ID
  ,country
) VALUES 
   ('RS', 'Serbia');
   
insert into countrycode (
   CountryCode_ID
  ,country
) VALUES 
   ('ME', 'Montenegro');
   

alter table company_specific_variable add auto_close_days_back integer(11) default 0;
alter table company_specific_variable add auto_close_ld_code integer(11) default 0;
alter table company_specific_variable add auto_close_dam_code integer(11) default 0;
alter table company_specific_variable add auto_close_pil_code integer(11) default 0;
alter table company_specific_variable add auto_close_ld_station integer(11) default 0;
alter table company_specific_variable add auto_close_dam_station integer(11) default 0;
alter table company_specific_variable add auto_close_pil_station integer(11) default 0;

alter table audit_company_specific_variable add auto_close_days_back integer(11) default 0;
alter table audit_company_specific_variable add auto_close_ld_code integer(11) default 0;
alter table audit_company_specific_variable add auto_close_dam_code integer(11) default 0;
alter table audit_company_specific_variable add auto_close_pil_code integer(11) default 0;
alter table audit_company_specific_variable add auto_close_ld_station integer(11) default 0;
alter table audit_company_specific_variable add auto_close_dam_station integer(11) default 0;
alter table audit_company_specific_variable add auto_close_pil_station integer(11) default 0;

alter table ohd add column warehouseReceivedDate dateTime;
alter table ohd add column warehouseSentDate dateTime;

insert into systemcomponents VALUES (704,'Assign Warehouse Dates','Allow to assign Warehouse Dates for OHDs',1,'',0,99,0);
create index warehouserecindex on ohd (warehouseReceivedDate, ohd_id);
create index warehousesentindex on ohd (warehouseSentDate, ohd_id);

alter table lost_found add column category_id integer;
update lost_found set category_id =17;

create table salvage_remark (
	remark_id int(11) not null auto_increment,
	agent_id int(11) not null,
	createtime datetime,
	remarktext text,
	remarktype tinyint(1),
	primary key (remark_id)
);

alter table salvage add remark_id int(11);

alter table incident add tracing_status_id tinyint(1) default 0;
alter table audit_incident add tracing_status_id tinyint(1) default 0;

insert into systemcomponents VALUES (705,'Edit Tracing Status','Allows a user to edit the Current Tracing Status of an Incident',6,'',0,99,0);

create table delivery_instructions (id int auto_increment,instructions text, PRIMARY KEY (id));

alter table incident add column deliveryInstructions_ID int;

alter table audit_incident add column instructions text;
alter table bdo_passenger add column hotelphone varchar(100);

alter table company_specific_variable add incident_lock_mins integer(11) default 0;
alter table audit_company_specific_variable add incident_lock_mins integer(11) default 0;

#production
insert into properties (keyStr,valueStr) values ('lock.cache.cluster','');


alter table address add column hotelphone varchar(50);

alter table fsclaim add column claimRemark text;
alter table address add column hotelphone varchar(50);

update properties set valueStr='NTServices_1_0/ClaimClientBeanV2/remote' where keyStr='fraud.server.name';

create table FsIPAddress (
	id int(11) not null auto_increment,
	ipAddress varchar(20),
	claim_id int(11) not null,
	primary key (id));
	
ALTER TABLE FsIPAddress ADD INDEX claim_id_index (claim_id);
	
alter table phone add column association varchar(255);
alter table phone add column claim_id int(11);
ALTER TABLE phone ADD INDEX claim_id_index (claim_id);
alter table fsipaddress add column association varchar(255);

update reservation set ccNumber = '';
update properties set valueStr='NTServices_1_0/ClaimClientBeanV3/remote' where keyStr='fraud.server.name';

alter table fsclaim add column createagent_id int;

CREATE TABLE attachment (id int(11) NOT NULL AUTO_INCREMENT,claim_id int(11) DEFAULT NULL,agent_id int(11) DEFAULT NULL,createdate date DEFAULT NULL,attachment_id int(11) DEFAULT NULL,description varchar(200) DEFAULT NULL, PRIMARY KEY (`id`));
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (706,'Shared Attachments','Allow Sharing Files on FS',59,'',0,0,0);
update properties set valueStr='NTServices_1_0/ClaimClientBeanV3/remote' where keyStr='fraud.server.name';

create table subcompany (id int not null auto_increment, subcompanycode varchar(3) not null, company_id varchar(2) not null, name varchar(50),
  email_subject varchar(200), email_path varchar(200), email_notice_1 varchar(200), email_notice_2 varchar(200), auto_close_low  int, 
  auto_close_high int, primary key(id));
  
create table subcompanystation (id int not null auto_increment, subcompany_id int not null,station_id int not null, primary key(id));

alter table agent add subcompany_id int;

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (630,'Maintain Subcompanies','Maintain Subcompanies' ,39,'subCompAdmin.do',1,3   ,0);

insert into group_component_policy (component_id,group_id) VALUES (630,1);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
VALUES (801,'Fraud Forum Search','Fraud Forum Search Page',59,'fraud_forum_search.do',1,98,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
VALUES (802,'Fraud Forum Create Thread','Fraud Forum Create Page',59,'fraud_forum_create.do',1,99,0);

alter table audit_address modify column zip varchar(11);
alter table audit_companycode modify column zip varchar(11);
alter table audit_lost_found modify column customer_zip varchar(11);
alter table audit_ohd_address modify column zip varchar(11);
alter table audit_station modify column zip varchar(11);
alter table address modify column zip varchar(11);
alter table companycode modify column zip varchar(11);
alter table lost_found modify column customer_zip varchar(11);
alter table ohd_address modify column zip varchar(11);
alter table station modify column zip varchar(11);
alter table z_b6_claim_settlement modify column zip varchar(11);
alter table z_b6_audit_claim_settlement modify column zip varchar(11);

insert into lookup_airline_codes (airline_2_character_code, airline_3_digit_ticketing_code) values ('VX','984');

alter table incident modify column lastupdated timestamp NOT NULL default CURRENT_TIMESTAMP;

insert into properties (keyStr,valueStr) VALUES ('lf.tracing.name','80');
insert into properties (keyStr,valueStr) VALUES ('lf.tracing.address','60');
insert into properties (keyStr,valueStr) VALUES ('lf.tracing.serial','85');
insert into properties (keyStr,valueStr) VALUES ('lf.tracing.phone','75');
insert into properties (keyStr,valueStr) VALUES ('lf.tracing.email','85');

insert into systemcomponents (component_id, component_name ,component_desc ,parent_component_id ,component_action_link ,display ,sort_order ,sort_group) VALUES (628,'Delivered Found','Maintain Delivered Found Items',604,'lf_delivered_found.do',1,10,0);

alter table item add childrestraint int default 0;
update item set childrestraint=0;
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (629,'Child Restraint System','Adds an option for a Child Restraint System Giveaway',15,'',0,5,0);
alter table item add externaldesc varchar(50);
alter table ohd add externaldesc varchar(50);
insert into table1 (rn  ,gtsv  ,minlen  ,maxlen  ,v10  ,v9  ,v8  ,v7  ,v6  ,v5  ,v4  ,v3  ,v2  ,v1  ,v0) VALUES ('BAGDESC',0 ,0 ,0 ,50,50,50  ,40  ,40  ,30  ,20  ,0  ,0  ,0  ,0);

insert into status (Status_ID, description,table_ID) VALUES (613,'Staged for Shipping',17);

alter table passenger add column languageKey varchar(50);
alter table passenger add column languageFreeFlow varchar(50);

alter table lost_found add column languageKey varchar(50);
alter table lost_found add column languageFreeFlow varchar(50);

insert into properties (keystr,valuestr) values ('spoken.language.list','english,mandarin,spanish,portuguese,french,arabic,other');
insert into properties (keystr,valuestr) values ('tracing.status.block.wt','0');

alter table incident add column tracingStarted datetime;
alter table incident add column tracingComplete datetime;
alter table incident add column tracing_agent_ID integer;
alter table audit_incident add column tracingStarted datetime;
alter table audit_incident add column tracingComplete datetime;
alter table audit_incident add column tracing_agent_ID integer;

update properties set valuestr='ejb:ntfs_internal_prod/nt_fs//ClaimClientBeanV3!aero.nettracer.selfservice.fraud.client.ClaimClientRemote' where keystr = 'fraud.server.name';
update properties set valuestr='ejb:ntfs_internal_prod/nt_fs//PrivacyPermissionsBean!aero.nettracer.selfservice.fraud.PrivacyPermissionsRemote' where keystr = 'fraud.permissions.server.name';
insert into properties (keystr,valuestr) values ('lf.server.name','ejb:tracer/lfc//LFServiceBean!aero.nettracer.lf.services.LFServiceRemote');

insert into properties (valueStr,keyStr) values ('0','convert.bagtag');

alter table address modify column is_permanent bit default false; 
alter table audit_claim modify column Claim_ID bigint(20) default 0;
alter table audit_ohd_inventory modify column OHD_Inventory_ID bigint(20) default 0;
alter table bdo modify column canceled bit default false;
alter table currency modify column currency_id varchar(3);
alter table currency modify column description varchar(20);
alter table expensepayout modify column bdo_id int(13);
alter table fsipaddress modify column id bigint(20) not null auto_increment;
alter table fsipaddress modify column claim_id bigint(20);
alter table incident_range modify column companycode_id varchar(3);
alter table agent modify column is_wt_user int(11) default 0;
alter table incident modify column tracing_status_id int(11) default 0;
alter table incident modify column courtesyreport int(11);
alter table incident modify column tsachecked int(11);
alter table incident modify column customcleared int(11) default 0;
alter table incident modify column voluntaryseparation int(11);
alter table incident modify column nonrevenue int(11);
alter table incident modify column numpassengers int(11);
alter table incident modify column numbagchecked int(11);
alter table incident modify column numbagreceived int(11);
alter table incident modify column reportmethod int(11);
alter table agent modify column is_online int(11) default 0;
alter table passenger modify column salutation int(11);
alter table passenger modify column isprimary int(11);
alter table item modify column lvlofdamage int(11);
alter table item modify column xdescelement_ID_1 int(11);
alter table item modify column xdescelement_ID_2 int(11);
alter table item modify column xdescelement_ID_3 int(11);
alter table remark modify column remarktype int(11) default 1;
alter table itinerary modify column itinerarytype int(11) default 0;
alter table itinerary modify column legfrom_type int(11) default 0;
alter table itinerary modify column legto_type int(11) default 0;
alter table expensepayout modify column mileageamt int(11) default 0;
alter table expensepayout modify column expensetype_ID int(11);
alter table expensepayout modify column status_ID int(11);
alter table expensepayout modify column agent_ID int(11);
alter table expensepayout modify column station_ID int(11);
alter table company_specific_variable modify column seconds_wait int(11) default 0;
alter table company_specific_variable modify column webs_enabled int(11);
alter table company_specific_variable modify column wt_enabled int(11) default 0;  
alter table company_specific_variable modify column wt_write_enabled int(11) default 0; 
alter table company_specific_variable modify column oal_ohd_hours int(11) not null default 0; 
alter table company_specific_variable modify column oal_inc_hours int(11) not null default 0; 
alter table address modify column addresstype int(11); 
alter table audit_ohd modify column xdescelement_ID_1 int(11);
alter table audit_ohd modify column xdescelement_ID_2 int(11);
alter table audit_ohd modify column xdescelement_ID_3 int(11);
alter table audit_ohd modify column ohd_type int(11) default 0;
alter table audit_ohd_itinerary modify column itinerarytype int(11) default 0;
alter table audit_ohd_itinerary modify column legfrom_type int(11) default 0;
alter table audit_ohd_itinerary modify column legto_type int(11) default 0;
alter table audit_ohd_passenger modify column isprimary int(11) not null default 0;
alter table audit_ohd_address modify column addresstype int(11);
alter table incident_assoc modify column itemtype_ID int(11);
alter table audit_incident modify column reportmethod int(11);
alter table audit_incident modify column numpassengers int(11);
alter table audit_incident modify column numbagchecked int(11);
alter table audit_incident modify column numbagreceived int(11);
alter table audit_incident modify column voluntaryseparation int(11);
alter table audit_incident modify column courtesyreport int(11);
alter table audit_incident modify column tsachecked int(11);
alter table audit_incident modify column customcleared int(11) default 0;
alter table audit_incident modify column nonrevenue int(11);
alter table audit_incident modify column tracing_status_id int(11) default 0;
alter table audit_incident modify column checkedlocation varchar(1);
alter table audit_remark modify column remarktype int(11) default 1;
alter table audit_agent modify column is_online int(11) default 0; 
alter table audit_agent modify column is_wt_user int(11) default 0; 
alter table claimprorate modify column pir_attached int(11);
alter table claimprorate modify column claim_attached int(11);
alter table claimprorate modify column confirmpayment_attached int(11);
alter table claimprorate modify column all_prorate int(11);
alter table claimprorate modify column remit int(11);
alter table claimprorate modify column clearing_bill int(11);

update properties set valuestr='production' where keystr='lock.cache.cluster';
