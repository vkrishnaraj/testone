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
