alter table company_specific_variable add column pnr_last_x_days integer default 0;
alter table audit_company_specific_variable add column pnr_last_x_days integer default 0;

alter table ohd modify storage_location varchar(100);
alter table audit_ohd modify storage_location varchar(100);

alter table company_irregularity_codes add column active tinyint default 1;
alter table company_irregularity_codes add column controllable tinyint default 0;
update company_irregularity_codes set active=1, controllable=0;

alter table passenger add column dlprovince varchar(255) default null;
alter table passenger add column dlcountry varchar(2) default null;
alter table passenger modify driverslicense varchar(511);
alter table audit_passenger add column dlprovince varchar(255) default null;
alter table audit_passenger add column dlcountry varchar(2) default null;
alter table audit_passenger modify driverslicense varchar(511);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (632,'Collect_Drivers_License','Enter driver''s license on the Incident pages',39,null,0,99,0);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (633,'View_Edit_Drivers_License','View and edit the driver''s license field on the Incident pages',39,null,0,99,0);

insert into properties (keyStr,valueStr) VALUES ('bsdi.address.endpoint','http://test.rbags.com/BDO.WebService/BaggageDeliveryStatusInfoService.asmx');
alter table ohd add creationMethod int(11) default 0;

alter table company_specific_variable add column issuance_edit_last_x_days integer default 0;
alter table audit_company_specific_variable add column issuance_edit_last_x_days integer default 0;

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (634,'Issuance_Item_Station_Admin','Maintenance page for issuance items under Admin (Station Level)',39,'issuanceItemAdmin.do',1,20,0);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (635,'Collect_Pos_Id','Collect position Id',39,null,0,99,0);

create table issuance_category (
	id bigint auto_increment not null,
	description varchar(255) not null,
	company_code_id varchar(3) not null,
	primary key (id)
);

create table issuance_item (
	id bigint auto_increment not null,
	issuance_category_id bigint not null,
	description varchar(50),
	primary key (id)
);

create table issuance_item_quantity (
	id bigint auto_increment not null,
	quantity integer default 0,
	minimum_active_quantity integer default 0,
	station_id bigint not null,
	issuance_item_id bigint not null,
	primary key (id)
);

create table audit_issuance_item_quantity (
	audit_id bigint auto_increment not null,
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

insert into status (status_ID, description, table_ID) values ('99','Other','9');

alter table ohd add column modifiedBy varchar(20) default null;
alter table ohd add column modifiedDate datetime default null;
alter table ohd add column posId varchar(6) default null;
alter table ohd add column lateCheckInd bit default 0;
alter table audit_ohd add column posId varchar(6) default null;
alter table audit_ohd add column lateCheckInd bit default 0;
alter table item add column posId varchar(6) default null;
alter table audit_item add column posId varchar(6) default null; 

insert into status (status_ID, description, table_ID) values ('58','TBI','2');