alter table company_specific_variable add pnr_last_x_days integer default 0;
alter table audit_company_specific_variable add pnr_last_x_days integer default 0;
GO
update company_specific_variable set pnr_last_x_days = 0;
update audit_company_specific_variable set pnr_last_x_days = 0;

alter table ohd alter column storage_location varchar(100);
alter table audit_ohd alter column storage_location varchar(100);

alter table company_irregularity_codes add active tinyint default 1;
alter table company_irregularity_codes add controllable tinyint default 0;
GO
update company_irregularity_codes set active=1, controllable=0;

alter table passenger add dlprovince varchar(255) default null;
alter table passenger add dlcountry varchar(2) default null;
alter table passenger alter column driverslicense varchar(511);
alter table audit_passenger add dlprovince varchar(255) default null;
alter table audit_passenger add dlcountry varchar(2) default null;
alter table audit_passenger alter column driverslicense varchar(511);
GO


insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (632,'Collect_Drivers_License','Enter driver''s license on the Incident pages',39,null,0,99,0);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (633,'View_Edit_Drivers_License','View and edit the driver''s license field on the Incident pages',39,null,0,99,0);

insert into properties (keyStr,valueStr) VALUES ('bsdi.address.endpoint','http://test.rbags.com/BDO.WebService/BaggageDeliveryStatusInfoService.asmx');

alter table ohd add creationMethod integer default 0;
GO
update ohd set creationMethod = 0;


alter table company_specific_variable add issuance_edit_last_x_days integer default 0;
alter table audit_company_specific_variable add issuance_edit_last_x_days integer default 0;
GO
update company_specific_variable set issuance_edit_last_x_days = 0;
update audit_company_specific_variable set issuance_edit_last_x_days = 0;


insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (634,'Issuance_Item_Station_Admin','Maintenance page for issuance items under Admin (Station Level)',39,'issuanceItemAdmin.do',1,20,0);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (635,'Collect_Pos_Id','Collect position Id',39,null,0,99,0);

create table issuance_category (
	id bigint not null identity,
	description varchar(255) not null,
	company_code_id varchar(3) not null,
	primary key (id)
);

create table issuance_item (
	id bigint not null identity,
	issuance_category_id bigint not null,
	description varchar(50),
	primary key (id)
);

create table issuance_item_quantity (
	id bigint not null identity,
	quantity integer default 0,
	minimum_active_quantity integer default 0,
	station_id bigint not null,
	issuance_item_id bigint not null,
	primary key (id)
);

create table audit_issuance_item_quantity (
	audit_id bigint not null identity,
	id bigint not null,
	quantity integer default 0,
	minimum_active_quantity integer default 0,
	station_id bigint not null,
	issuance_item_id bigint not null,
	quantity_change integer not null,
	incident_id varchar(13),
	editdate datetime,
	editagent_id bigint not null,
	primary key (audit_id)
);

SET IDENTITY_INSERT status ON;
insert into status (status_ID, description, table_ID) values ('99','Other','9');
insert into status (status_ID, description, table_ID) values ('58','TBI','2');
SET IDENTITY_INSERT status OFF;

alter table ohd add modifiedBy varchar(20) default null;
alter table ohd add modifiedDate datetime default null;
alter table ohd add posId varchar(6) default null;
alter table ohd add lateCheckInd bit default 0;
alter table audit_ohd add posId varchar(6) default null;
alter table audit_ohd add lateCheckInd bit default 0;
alter table item add posId varchar(6) default null;
alter table audit_item add posId varchar(6) default null;
GO
update ohd set lateCheckInd = 0;
update audit_ohd set lateCheckInd = 0;

insert into properties (keyStr,valueStr) VALUES ('max.pnr.last.days','14');
insert into properties (keyStr,valueStr) VALUES ('bso.ws.timeout','5000');