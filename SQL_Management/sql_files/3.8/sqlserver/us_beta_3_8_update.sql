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

insert into properties (keyStr,valueStr) VALUES ('bsdi.address.endpoint','https://test.rbags.com/BDO.WebService/BaggageDeliveryStatusInfoService.asmx');

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



alter table passenger add passportNumber varchar(255) null;
alter table passenger add passportIssuer varchar(255) null;
alter table audit_passenger add passportNumber varchar(255) null;
alter table audit_passenger add passportIssuer varchar(255) null;

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (636,'Passport_Collect','Collect passport information on the incident pages',39,null,0,99,0);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (637,'Passport_View_Edit','View and edit passport information on the incident pages',39,null,0,99,0);

update systemcomponents set component_name = 'Drivers_License_Collect' where component_id = 632;
update systemcomponents set component_name = 'Drivers_License_View_Edit' where component_id = 633;

insert into systemcomponents (component_id, component_name, component_desc, parent_component_id, component_action_link, display, sort_order, sort_group)
values (638,'External Links','External Links',39,'links.do',1,21,0);

create table links (
id integer not null identity,
description varchar(100) not null,
link_address varchar(255) not null,
image varchar(50) not null,
companycode_id varchar(3),
primary key (id)
);

alter table incident_claimcheck alter column claimchecknum_bagnumber varchar(8);
alter table item modify alter column claimchecknum_bagnumber varchar(8);
alter table ohd modify alter column claimchecknum_bagnumber varchar(8);

alter table item add expediteTagNum varchar(12) null;
alter table audit_item add expediteTagNum varchar(12) null;

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (639,'Expedite_Tag_Num_Collect','Collect expedite tag number for damaged Incidents',8,null,0,99,0);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
VALUES (1100,'General','General Permissions that universally affect the NetTracer Application',1100,'',0,1  ,0 );
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
values (335,'Secure_Remarks','Generate and View Secure Remarks in Incidents and OHDs',1100,null,0,0,0);

GO

alter table remark add secure tinyint default 0;
alter table Audit_Remark add secure tinyint default 0;
GO
update remark set secure=0;
update Audit_Remark set secure=0;

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (640,'Receive_Timestamp_Collect','Collect the receive time of a damaged bag upon arrival to LZ',8,null,0,99,0);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (641,'Receive_Timestamp_Delete','Delete the receive time to LZ of a damaged bag',8,null,0,99,0);

alter table incident add rxTimestamp datetime default null;
alter table audit_incident add rxTimestamp datetime default null;

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (643,'Issuance_Item_Global_Admin','Administration for Issuance Categories (Global Privileges for Station Admin also)',39,'editIssuanceCategory.do',1,21,0);

SET IDENTITY_INSERT status ON;
insert into status (status_ID, description, table_ID) values ('700','Available','19');
insert into status (status_ID, description, table_ID) values ('701','On Loan','19');
insert into status (status_ID, description, table_ID) values ('702','Issued','19');
insert into status (status_ID, description, table_ID) values ('703','Discarded','19');
SET IDENTITY_INSERT status OFF;


alter table issuance_item add active bit not null default 1;

alter table issuance_category add active bit not null default 1;
alter table issuance_category add lostdelay bit not null default 1;
alter table issuance_category add damage bit not null default 1;
alter table issuance_category add missing bit not null default 1;
alter table issuance_category add inventory bit not null default 0;
alter table issuance_category add document_id bigint not null default 0;
GO
update issuance_item set active = 1;
update issuance_category set active = 1;
update issuance_category set lostdelay = 1;
update issuance_category set damage = 1;
update issuance_category set missing = 1;
update issuance_category set inventory = 0;
update issuance_category set document_id = 0;


create table issuance_item_inventory (
	id bigint not null identity,
	trade_type integer not null,
    description varchar(100),
	barcode varchar(20),
	inventory_status_id integer not null, 
	station_id bigint not null,
	issuance_item_id bigint not null,
	incident_id varchar(13),
	issuedate datetime,
	primary key (id)
);

create table audit_issuance_item_inventory (
	audit_id bigint not null identity,
	id bigint not null,
	trade_type integer not null,
    description varchar(100),
	barcode varchar(20),
	inventory_status_id integer not null, 
	station_id bigint not null,
	issuance_item_id bigint not null,
	incident_id varchar(13),
	issuedate datetime,
	reason varchar(150),
	editdate datetime,
	editagent_id bigint not null,
	primary key (audit_id)
);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (642,'Special_Conditions','Collect overweight and oversized bag attributes',8,null,0,99,0);

alter table item add specialCondition int default 0;
alter table audit_item add specialCondition int default 0;
alter table ohd add specialCondition int default 0;
alter table audit_ohd add specialCondition int default 0;
GO
update item set specialCondition = 0;
update audit_item set specialCondition = 0;
update ohd set specialCondition = 0;
update audit_ohd set specialCondition = 0;

SET IDENTITY_INSERT status ON;
insert into status (Status_ID,description,table_id) values (800,'Returned',20);
insert into status (Status_ID,description,table_id) values (801,'Damaged',20);
insert into status (Status_ID,description,table_id) values (802,'Missing',20);
SET IDENTITY_INSERT status OFF;

alter table item_inventory add enteredDate date default null;
alter table audit_item_inventory add enteredDate date default null;
alter table item_inventory add purchaseDate date default null;
alter table audit_item_inventory add purchaseDate date default null;
alter table item_inventory add invItemCost float default 0;
alter table audit_item_inventory add invItemCost float default 0;
alter table item_inventory add invItemCurrency varchar(3) default null;
alter table audit_item_inventory add invItemCurrency varchar(3) default null;
alter table item_inventory add itemStatusId int default 0;
alter table audit_item_inventory add itemStatusId int default 0;
GO
update item_inventory set invItemCost=0;
update audit_item_inventory set invItemCost=0;
update item_inventory set itemStatusId=0;
update audit_item_inventory set itemStatusId=0;

update item_inventory set invItemCurrency = 'USD';
update audit_item_inventory set invItemCurrency = 'USD';

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (644,'Additional_Item_Information_Collect','Collect additional fields for baggage contents in damaged bags',8,null,0,99,0);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (645,'Issuance_Item_Lostdelay','Issuance Items for Lost/Delay Incidents',6,null,0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (646,'Issuance_Item_Damage','Issuance Items for Damage Incidents',8,null,0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (647,'Issuance_Item_Missing','Issuance Items for Missing Incidents',12,null,0,99,0);

SET IDENTITY_INSERT status ON;
insert into status (status_id,description,table_id) values
(900,'Other',21),
(901,'Outside 4-Hour',21),
(902,'Claim Check on Another Carrier',21),
(903,'No Claim Check',21),
(1000,'Conditional Acceptance',22),
(1100,'Voluntary Separation',23),
(1101,'Late Check',23),
(1200, 'Both', 24),
(1201, 'Active', 24),
(1202, 'Inactive', 24);
SET IDENTITY_INSERT status OFF;

alter table incident add courtesyReasonId int default 0;
alter table audit_incident add courtesyReasonId int default 0;
alter table incident add courtesyDescription varchar(100) default null;
alter table audit_incident add courtesyDescription varchar(100) default null;
GO
update incident set courtesyReasonId=0;
update audit_incident set courtesyReasonId = 0;

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
values (648,'Incident_Courtesy_Reason_Collect','Collect courtesy reason information on all incident types',1100,'',0,2,0 );



alter table item add noAddFees tinyint default 0;
alter table item add other int default 0;
alter table item add oversize tinyint default 0;
alter table item add overweight tinyint default 0;
alter table ohd add noAddFees tinyint default 0;
alter table ohd add other int default 0;
alter table ohd add oversize tinyint default 0;
alter table ohd add overweight tinyint default 0;
alter table bdo add origDelivCost float default 0.0;
GO
update item set noAddFees=0;
update item set other=0;
update item set oversize=0;
update item set overweight=0;
update ohd set noAddFees=0;
update ohd set other=0;
update ohd set oversize=0;
update ohd set overweight=0;
update bdo set origDelivCost=0.0;


create TABLE category (
id bigint identity not null, 
description varchar(100), 
type integer, 
primary key (id)
);
GO
insert into category (description, type) values ('Skis',1);
insert into category (description, type) values ('Golf Clubs',1);
insert into category (description, type) values ('Military Duffle',1);



create table issuance_item_incident (
	id bigint not null identity,
	quantity integer not null,
	returned bit not null default 0,
	returndate datetime,
	issuance_item_quantity_id bigint,
	issuance_item_inventory_id bigint,
	incident_id varchar(13),
	issuedate datetime,
	issueagent_id bigint not null,
	primary key (id)
);

alter table company_specific_variable add auto_close_ohd_days_back int default 0;
GO
update company_specific_variable set auto_close_ohd_days_back=0;

insert into properties (keyStr,valueStr) VALUES ('auto.close.ohd.remark','This OHD was automatically closed after being in the LZ for {autoCloseDays} days.');

alter table articles add enteredDate date default null;
alter table audit_articles add enteredDate date default null;
alter table articles add statusId int default 0;
alter table audit_articles add statusId int default 0;
GO
update articles set statusId=0;
update audit_articles set statusId=0;


insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (649,'Additional_Missing_Item_Information_Collect','Collect additional fields for contents missing from baggage',12,null,0,99,0);

alter table item add assistDeviceType int default 0;
GO
update item set assistDeviceType=0;
alter table item add assistDeviceCheck varchar(5);
alter table audit_item add assistDeviceType varchar(100);
GO
update  audit_item  set assistDeviceType=0;
alter table  audit_item  add  assistDeviceCheck varchar(5);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
values (336,'Capture ACAA Information','Captures Assist Device Information for Bags Types 94 and 95',1100,null,0,0,0);
insert into category (description, type) values ('Breathing Machine',2);
insert into category (description, type) values ('Cane',2);
insert into category (description, type) values ('Hearing Aid',2);
insert into category (description, type) values ('Wheelchair (Electric)',2);
insert into category (description, type) values ('Wheelchair (Manual)',2);
insert into category (description, type) values ('Other',2);
insert into category (description, type) values ('Prosthesis',2);
insert into category (description, type) values ('Scooter',2);
insert into category (description, type) values ('Walker',2);

create table template (
  id bigint not null identity,
  name varchar(256) not null,
  description varchar(256),
  active bit default 0,
  createDate datetime,
  lastUpdated datetime,
  data text,
  primary key (id)
);

create table template_type (
  id bigint not null identity,
  ordinal integer not null,
  defaultName varchar(255) not null,
  primary key (id)
);

create table template_type_mapping (
  templateId bigint not null,
  templateTypeId bigint not null,
  primary key (templateId,templateTypeId),
  constraint fk_template_id foreign key (templateId) references template (id),
  constraint fk_template_type_id foreign key (templateTypeId) references template_type (id)
);
GO
create index fk_template_id on template_type_mapping(
templateTypeId
);

create table template_var (
  id bigint identity not null,
  displayTag varchar(256) not null,
  associatedClass varchar(256) not null,
  primary key (id),
  constraint unique_tag_class unique (displayTag,associatedClass)
);

create table template_var_dependency (
  id bigint identity not null,
  associatedClass varchar(256) not null,
  dependentClass varchar(256) not null,
  primary key (id),
  constraint unique_dependency unique (associatedClass,dependentClass)
);

create table template_var_mapping (
  templateId bigint not null,
  templateVarId bigint not null,
  primary key (templateId, templateVarId),
  constraint fk_templateId foreign key (templateId) references template (id),
  constraint fk_templateVarId foreign key (templateVarId) references template_var (id)
);
GO
create index fk_template_var on template_var_mapping(
templateVarId
);

create table document (
  id bigint identity not null,
  templateId bigint not null,
  content text default null,
  primary key (id, templateId),
  constraint fk_template foreign key (templateId) references template (id)
);
GO
create index fk_templateId on document(
templateId
);

insert into template_var (displayTag,associatedClass) values 
('FirstName','Agent'),
('LastName','Agent'),
('Initials','Agent'),
('Id','Claim'),
('Type','Claim'),
('FirstName','Claim'),
('LastName','Claim'),
('Address1','Claim'),
('Address2','Claim'),
('City','Claim'),
('State','Claim'),
('Zip','Claim'),
('HomePhone','Claim'),
('BusinessPhone','Claim'),
('MobilePhone','Claim'),
('Id','FoundItem'),
('Color','FoundItem'),
('Item','FoundItem'),
('Description','FoundItem'),
('CaseColor','FoundItem'),
('FirstName','FoundItem'),
('LastName','FoundItem'),
('Address1','FoundItem'),
('Address2','FoundItem'),
('City','FoundItem'),
('State','FoundItem'),
('Zip','FoundItem'),
('HomePhone','FoundItem'),
('BusinessPhone','FoundItem'),
('MobilePhone','FoundItem'),
('Id','Incident'),
('Type','Incident'),
('FirstName','Incident'),
('LastName','Incident'),
('Address1','Incident'),
('Address2','Incident'),
('City','Incident'),
('State','Incident'),
('Zip','Incident'),
('HomePhone','Incident'),
('BusinessPhone','Incident'),
('MobilePhone','Incident'),
('Option1','UserProvided'),
('Option2','UserProvided'),
('Option3','UserProvided'),
('Option4','UserProvided'),
('Today','Date');

insert into template_var_dependency (associatedClass,dependentClass) values
('Claim','Claim'),
('FoundItem','FoundItem'),
('Incident','Incident');

insert into template_type (ordinal,defaultName) values 
(0,'Incident'),
(1,'Claim'),
(2,'FoundItem'),
(3,'Static'),
(4,'Invalid');

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (651,'Document_Templates_Manage','Manage document templates',39,'searchTemplate.do',1,99,0);

create table claim_type (id integer not null identity,description varchar(100),PRIMARY KEY (id));

insert into claim_type (description) VALUES ('Domestic - Max $3400 USD');
insert into claim_type (description) VALUES ('Montreal Convention - Max 1131 SDR');
insert into claim_type (description) VALUES ('Warsaw Convention - Per Kilogram');

CREATE TABLE generaldepreciationrules (
  id integer NOT NULL identity,
  lessTwentyDeprec float DEFAULT NULL,
  twentyOnefiftyDeprec float DEFAULT NULL,
  onefiftyDeprec float DEFAULT NULL,
  noDates integer DEFAULT NULL,
  companyCode varchar(2) DEFAULT NULL,
  PRIMARY KEY (id)
);
GO
create index generaldepreciationrules_idx on generaldepreciationrules(
id,
companyCode
);



create table depreciation_category (
id integer not null identity, 
name varchar(30) not null, 
calcMethod integer, 
notCoveredCoc tinyint, 
flatRate float, 
firstYear float,
secondYear float,  
thirdYear float,  
eachYear float,  
maxDeprec float, 
companyCode varchar(2), 
PRIMARY key(ID));

GO
create index depreciation_category_idx on depreciation_category(
id,
companyCode
);


insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (656,'Claim Depreciation Calculator','Use a Depreciation Calculator to Determine Payouts for Claims',59,'claim_deprec_calc.do',0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (657,'Depreciation Calculator Administration','Manage Depreciation Calculator variables and categories',39,'deprecCalcAdmin.do',1,99,0);


create table claim_depreciation (
id integer not null identity, 
claim_id bigint, 
dateCalculate date, 
claimType_id integer, 
totalWeight float, 
weightMetric varchar(3), 
currency varchar(3), 
totalApprovedPayout float default 0, 
primary key(id)
);
GO
create index claim_depreciation_idx on claim_depreciation(
id,
claim_id
);



create table depreciation_item (
id integer not null identity, 
description varchar(200), 
amountClaimed float, 
datePurchase date, 
category_id integer, 
proofOwnership integer, 
notCoveredCoc tinyint, 
calcValue float, 
claimValue float, 
claim_depreciation_id bigint, 
primary key(id)
);
GO
create index depreciation_item_idx on depreciation_item(
id,
claim_depreciation_id
);


insert into properties (keyStr,valueStr) VALUES ('domestic.payout.value','3400');


insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (652,'NTLF_Enter_Items','Add Found Items',64,'ntlf_enter_items.do',1,7,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (653,'NTLF_Found_Items','Found Item Page',64,'ntlf_create_found_item.do?createNew=1',0,8,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (654,'NTLF_Search_Items','Search Found Items',64,'ntlf_search_lost_found.do',1,9,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (655,'NTLF_TM_Open_HV_Items','Task Manager High Value Items Number',15,'ntlf_search_lost_found.do?tmhvSearch=1',1,99,5);
GO

SET IDENTITY_INSERT status ON;
insert into status (Status_ID, description, table_ID) values (614, 'Sent to LFC', 17);
insert into status (Status_ID, description, table_ID) values (615, 'Removed', 17);
insert into status (Status_ID, description, table_ID) values (100, 'expired', 13);
SET IDENTITY_INSERT status OFF;


insert into manufacturer (description) values ('Athalon');
insert into manufacturer (description) values ('Ciao');
insert into manufacturer (description) values ('Eddie Bauer');
insert into manufacturer (description) values ('Everest & Jennings');
insert into manufacturer (description) values ('Eiffel Tower');
insert into manufacturer (description) values ('Fifth Avenue');
insert into manufacturer (description) values ('Guardian');
insert into manufacturer (description) values ('Hercules');
insert into manufacturer (description) values ('Jaguar');
insert into manufacturer (description) values ('Jordache');
insert into manufacturer (description) values ('J.C. Penny');
insert into manufacturer (description) values ('Jansport');
insert into manufacturer (description) values ('Kiva');
insert into manufacturer (description) values ('Leisure');
insert into manufacturer (description) values ('London Fog');
insert into manufacturer (description) values ('Maestro');
insert into manufacturer (description) values ('Mercury');
insert into manufacturer (description) values ('Monarch');
insert into manufacturer (description) values ('Nova');
insert into manufacturer (description) values ('Oleg Cassini');
insert into manufacturer (description) values ('Paragon');
insert into manufacturer (description) values ('Pride Jazzy');
insert into manufacturer (description) values ('Piel Leather');
insert into manufacturer (description) values ('Rimowa');
insert into manufacturer (description) values ('Tommy Hilfiger');
insert into manufacturer (description) values ('Travelers Club');
insert into manufacturer (description) values ('Tuff Care');
insert into manufacturer (description) values ('Ventura');
insert into manufacturer (description) values ('Yukon');
insert into manufacturer (description) values ('El Dorado');
insert into manufacturer (description) values ('Voyager');
insert into manufacturer (description) values ('Carnegie');
insert into manufacturer (description) values ('Berlin');
insert into manufacturer (description) values ('Generic');
insert into manufacturer (description) values ('Golden Pacific');
insert into manufacturer (description) values ('IT Luggage');
insert into manufacturer (description) values ('LL Bean');
insert into manufacturer (description) values ('Polo');
insert into manufacturer (description) values ('Club Glove');
insert into manufacturer (description) values ('Ping');
insert into manufacturer (description) values ('Taylor Made');
insert into manufacturer (description) values ('Jessica Simpson');
insert into manufacturer (description) values ('Pasadena');
insert into manufacturer (description) values ('Rome');
insert into manufacturer (description) values ('Huntington');
insert into manufacturer (description) values ('Betsey Johnson - Betseyville');
insert into manufacturer (description) values ('Forecast');
insert into manufacturer (description) values ('Olympia');
insert into manufacturer (description) values ('IN2IT');
insert into manufacturer (description) values ('Callaway');
insert into manufacturer (description) values ('Wilson');
insert into manufacturer (description) values ('G Loomis');
insert into manufacturer (description) values ('ResMed');
insert into manufacturer (description) values ('Cosco');
insert into manufacturer (description) values ('Evenflo');
insert into manufacturer (description) values ('Kolcraft');
insert into manufacturer (description) values ('Pelican');
insert into manufacturer (description) values ('Lucas');
insert into manufacturer (description) values ('Nautica');
insert into manufacturer (description) values ('Golf Guard');
insert into manufacturer (description) values ('REI');
insert into manufacturer (description) values ('Liz Claiborne');
insert into manufacturer (description) values ('Respironics');
insert into manufacturer (description) values ('Vera Bradley');
insert into manufacturer (description) values ('Graco');
insert into manufacturer (description) values ('Maclaren');
insert into manufacturer (description) values ('Kirkland');
insert into manufacturer (description) values ('Coleman');
insert into manufacturer (description) values ('Concourse');
insert into manufacturer (description) values ('Embark');
insert into manufacturer (description) values ('Dockers');
insert into manufacturer (description) values ('Kathy van Zeeland');
insert into manufacturer (description) values ('Jeep');
insert into manufacturer (description) values ('Protege');
insert into manufacturer (description) values ('Britax');
insert into manufacturer (description) values ('Combi');
insert into manufacturer (description) values ('Victoria''s Secret');
insert into manufacturer (description) values ('North Face');
insert into manufacturer (description) values ('American Flyer');
insert into manufacturer (description) values ('SKB');
insert into manufacturer (description) values ('Pacific Gear');
insert into manufacturer (description) values ('Beverly Hills');
insert into manufacturer (description) values ('Bag Boy');
insert into manufacturer (description) values ('Chicco');
insert into manufacturer (description) values ('Safety First');
insert into manufacturer (description) values ('Invacare');
insert into manufacturer (description) values ('TAG');

alter table company_specific_variable add status_message varchar(255) default null;

alter table task add generic_timestamp datetime;
GO
update task set generic_timestamp = deferment_timestamp;

alter table task drop column deferment_timestamp;

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (658,'Manage_CSS_Daily_Calls','Task Manager CS&S Daily Calls Display',15,'css_calls.do',1,99,1);

alter table bdo add pickupdate date default null;
alter table bdo add pickuptime time default null;


insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (660,'NTLF_TM_Ship_To_LFC','Task Manager Ship to LFC',15,'ntlf_search_lost_found.do?stLFCSearch=1',1,99,5);

alter table audit_company_specific_variable add status_message varchar(255) default null;


alter table ohd alter column posId varchar(8);
alter table audit_ohd alter column posId varchar(8);
alter table item alter column posId varchar(8);
alter table audit_item alter column posId varchar(8); 

insert into generaldepreciationrules (lessTwentyDeprec,twentyOnefiftyDeprec,onefiftyDeprec,noDates,companyCode) VALUES (0,0,0,0,'US');

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (
   661,'Loss Codes at Per Bag Level','Changes loss codes to be assignable at a bag level',1100,'',0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (
   662,'Edit Non Closed and Non Delivered Bags in Same Station','Edit Non Closed and Non Delivered Bags and update their loss codes and fault stations',1100,'',0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (
   663,'Edit Closed or Delivered Bags in Same Station','Edit Closed or Delivered Bags in Same Station and update their loss codes and fault stations',1100,'',0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (
   664,'Edit Non Closed and Non Delivered Bags in Other Stations','Edit Non Closed and Non Delivered in Other Station and update their loss codes and fault stations',1100,'',0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (
   665,'Edit Closed or Delivered Bags in Other Stations','Edit Closed or Delivered Bags in Other Stations and update their loss codes and fault stations',1100,'',0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (
   675,'Edit Loss Codes for Any Bags','Edit Any Closed or Delivered Bags and update their loss codes and fault stations',1100,'',0,99,0);


alter table item add lossCode integer default 0;
alter table item add faultStation_id integer default 0;

alter table audit_item add lossCode integer default 0;
alter table audit_item add faultStation_id integer default 0;

GO

update item set lossCode = 0, faultStation_id = 0;
update audit_item set lossCode = 0, faultStation_id = 0;

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (666,'Passenger Pick Up for Lost/Delay Incidents','Mark a Lost/Delay Item as Passenger Pick Up',6,null,0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (672,'Passenger Pick Up for Missing Incidents','Mark a Missing Item as Passenger Pick Up',12,null,0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (673,'Passenger Pick Up for Damaged Incidents','Mark a Damaged Item as Passenger Pick Up',8,null,0,99,0);

SET IDENTITY_INSERT status ON;
insert into status (Status_ID,description,table_ID) VALUES (59,'Passenger Pick Up',10);
insert into status (Status_ID,description,table_ID) values (1301,'Postal Mail',25);
insert into status (Status_ID,description,table_ID) values (1302,'Web Portal',25);
SET IDENTITY_INSERT status OFF;

insert into properties (keyStr,valueStr) VALUES ('bag.level.loss.codes.itin.check','-1');


alter table incident add custCommId integer default 1301;

GO

update incident set custCommId = 1301;

alter table issuance_category alter column document_id bigint;

GO

update Issuance_Category set document_id=null where document_id='0';

create table activity (
  id bigint identity not null,
  code bigint not null,
  description varchar(255) not null,
  primary key (id)
);

GO

create table incident_activity (
  id bigint identity not null,
  incident varchar(13) not null,
  createDate datetime not null,
  publishedDate datetime,
  agent integer,
  approvalAgent integer,
  document bigint,
  status integer,
  activity bigint,
  description varchar(255),
  custCommId bigint not null,
  primary key (id),
  constraint fk_activity_agent foreign key (agent) references agent (Agent_ID),
  constraint fk_activity_approval_agent foreign key (approvalAgent) references agent (Agent_ID),
  constraint fk_activity_document foreign key (document) references document (id),//TODO
  constraint fk_activity_status foreign key (status) references status (Status_ID),
  constraint fk_activity_activity foreign key (activity) references activity (id)
);


SET IDENTITY_INSERT status ON;
insert into status (Status_ID,description,table_id) values (1400,'Pending',26);
insert into status (Status_ID,description,table_id) values (1401,'Approved',26);
insert into status (Status_ID,description,table_id) values (1402,'Denied',26);
insert into status (Status_ID,description,table_id) values (1403,'Published',26);
insert into status (Status_ID,description,table_id) values (1404,'Pending Print',26);
insert into status (Status_ID,description,table_id) values (1405,'Pending Publish to Web Portal',26);
SET IDENTITY_INSERT status OFF;

GO

alter table template add typeAvailableFor integer default 0;

alter table document add title varchar(255);
alter table document add fileName varchar(255);

GO

update template set typeAvailableFor = 0;
insert into activity (code,description) values (1500,'Customer Communication');

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
values (667,'Customer_Communications_Create','View the customer communications section on incidents and create communications.',1100,'',0,3,0 );
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
values (668,'Customer_Communications_Edit','View/Edit existing customer communications that have yet to be sent to the customer.',1100,'',0,4,0 );
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
values (669,'Customer_Communications_Delete','Delete customer communications that have yet to be sent to the customer.',1100,'',0,5,0 );
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
values (670,'Customer_Communications_View_Published','View customer communications which have already been published and sent to the customer.',1100,'',0,6,0 );
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
values (671,'Customer_Communications_Approval','Approve/Deny customer communications which have been submitted for approval.',1100,'',0,7,0 );

alter table issuance_item_incident add document_id bigint;

GO

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (674,'Claims Report and Adjustment Prediction','Record Special fields in claims and print out a Claims Report & Adjustment Prediction ',59,null,0,99,0);

alter table fsclaim add totalLiability float default 0;
alter table fsclaim add excessValueAmt float default 0;
alter table fsclaim add ix tinyInt default 0;
alter table fsclaim add carryon tinyInt default 0;
alter table fsclaim add claimCheck varchar(50);

GO

update fsclaim set totalLiability=0;
update fsclaim set excessValueAmt=0;
update fsclaim set ix=0;
update fsclaim set carryon=0;

insert into category (description,type) VALUES ('NO',3);
insert into category (description,type) VALUES ('YES',3);
insert into category (description,type) VALUES ('WAIVE',3);


alter table ohd add inventoryDate datetime default null;
alter table audit_ohd add inventoryDate datetime default null;

alter table wt_transaction add agent_id integer;

insert into systemcomponents (component_id, component_name, component_desc, parent_component_id, component_action_link,display ,sort_order,sort_group) 
VALUES (412, 'To Be Inventoried', 'To Be Inventoried', 15, 'toBeInventoried.do', 1, 7 , 2);

GO

create index wt_transaction_createDate_idx on wt_transaction (createDate);
create index wt_transaction_result_idx on wt_transaction (result);
create index wt_transaction_txType_idx on wt_transaction (txType);
create index wt_transaction_agent_id_idx on wt_transaction (agent_id);
create index wt_transaction_incident_id_idx on wt_transaction (incident_id);
create index wt_transaction_ohd_id_idx on wt_transaction (ohd_id);


GO

alter table activity alter column code varchar(8);

GO

alter table activity add constraint unique_activity_code unique(code);

GO

delete from incident_activity where incident_activity.activity = (select id from activity where code = '1500' and description = 'CUSTOMER COMMUNICATION');
delete from activity where code = '1500' and description = 'CUSTOMER COMMUNICATION';

insert into activity (code,description) values ('AS','ASSIGNED TO');
insert into activity (code,description) values ('55','CREATE LETTER');
insert into activity (code,description) values ('253','RESOLVE');
insert into activity (code,description) values ('99','INBOUND MAIL');
insert into activity (code,description) values ('256','SECONDARY CORRESPONDENCE');
insert into activity (code,description) values ('93','CIM/BCCFS COMPLETE');
insert into activity (code,description) values ('10','RECEIVED DAMAGE ITEM');
insert into activity (code,description) values ('182','AHL - SENT');
insert into activity (code,description) values ('ZRR','RYNNS-REPAIR/REPLACE');
insert into activity (code,description) values ('563','TO BE ASSIGNED BY DAMAGE');
insert into activity (code,description) values ('84','WAITING FOR CUSTOMERS CORRESP');
insert into activity (code,description) values ('53','DAMAGE - LZ TRASHED');
insert into activity (code,description) values ('95','SENT TO LFC FOR RESEARCH');
insert into activity (code,description) values ('CBP2','CLOSE BY PHONE - NO FILE');
insert into activity (code,description) values ('83LZ','BAGS RETURNED BY LZ');
insert into activity (code,description) values ('100','REVIEWED CORR. - NO RESPONSE');
insert into activity (code,description) values ('Z10','TO BE ASSIGNED TO CTR');
insert into activity (code,description) values ('570','TO BE ASSIGNED BY DAMAGE');
insert into activity (code,description) values ('CBP1','CLOSE BY PHONE - WITH FILE');
insert into activity (code,description) values ('84B','WAITING FOR CUSTOMERS BAG');
insert into activity (code,description) values ('562','TO BE ASSIGNED BY DAMAGE');
insert into activity (code,description) values ('210','IX PYMT - 100% REIMBURSMENT');
insert into activity (code,description) values ('100D','REVIEWED CORRESPONDENCE - DUP');
insert into activity (code,description) values ('500','FAIR MARKET VALUE');
insert into activity (code,description) values ('220','REG PYMT-CHECK BEING PROCESSED');
insert into activity (code,description) values ('84V','WAITING FOR VENDOR RESPONSE');
insert into activity (code,description) values ('222','REG PYMT-LESS VALUABLES');
insert into activity (code,description) values ('01F','FAXED CLAIM FORM');
insert into activity (code,description) values ('224','IX PYMT-GENERIC');
insert into activity (code,description) values ('85','STATION SETTLED');
insert into activity (code,description) values ('89','LAWSUIT');
insert into activity (code,description) values ('77R','FWD TO CCA FOR HANDLING');
insert into activity (code,description) values ('578','GENERIC-DEPRECIATION LINE');
insert into activity (code,description) values ('76C','CURE SETTLE');
insert into activity (code,description) values ('79','BCCFS COPY RCVD');
insert into activity (code,description) values ('00','BCCFS SENT - AWAITING FILE(S)');
insert into activity (code,description) values ('91','MAIL RTND BY POST OFFICE');
insert into activity (code,description) values ('579','GENERIC-NO DEPRECIATION LINE');
insert into activity (code,description) values ('77C','FWD TO CURE FOR HANDLING');
insert into activity (code,description) values ('302','REG PYMT-MA CLAIM');
insert into activity (code,description) values ('77S','FWD TO STATION FOR HANDLING');
insert into activity (code,description) values ('94','RETURNED FROM LZ');
insert into activity (code,description) values ('84M','WAITING MERCHANT RESPONSE');
insert into activity (code,description) values ('85S','STATION SETTLED & TRASHED');
insert into activity (code,description) values ('98','OUTBOUND TO TRACING');
insert into activity (code,description) values ('99F','INBOUND FAX');
insert into activity (code,description) values ('221','MAX PYMT-CHECK BEING PROCESSED');
insert into activity (code,description) values ('01','SEND CLAIM FORM (REGULAR)');
insert into activity (code,description) values ('238','REG PYMT-GENERIC');
insert into activity (code,description) values ('244','REG PYMT-NO DOCUMENTATION');
insert into activity (code,description) values ('587','WATER DAMAGE');
insert into activity (code,description) values ('235','REG PYMT-LESS IX PAID LOCALLY');
insert into activity (code,description) values ('102','SPECIALIZE LETTER CREATED');
insert into activity (code,description) values ('84F','WAITING FOR FILE FROM OAL');
insert into activity (code,description) values ('289','IX PYMT-INCLUDES LOCAL PYMT');
insert into activity (code,description) values ('301','IX PYMT-100% PAY INCLUDE LOCAL');
insert into activity (code,description) values ('597','MISC-ACKNOWLEDGE BAG TO VENDOR');
insert into activity (code,description) values ('232','REG PYMT-PLUS TC');
insert into activity (code,description) values ('97A','FILE RETURNED TO HDQ');
insert into activity (code,description) values ('77E','FWD TO EXEC OFC FOR HANDLING');
insert into activity (code,description) values ('83W','BAG RETURNED WORLDTRACER');
insert into activity (code,description) values ('223','AVERAGE PYMT-GENERIC');
insert into activity (code,description) values ('83','EXPEDITED TO STN FOR RETURN');
insert into activity (code,description) values ('9206','ACAA REPAIR AUTHORIZATION-VEND');
insert into activity (code,description) values ('312','IX PYMT-FOR CONSEQUENTIAL EXPE');
insert into activity (code,description) values ('252','REG PYMT-NO DOC& LESS VALUABLE');
insert into activity (code,description) values ('592','CHECK BASED ON RECEIPT');
insert into activity (code,description) values ('278','IX PYMT+TC-100% REIMBURSEMENT');
insert into activity (code,description) values ('580','DMG/MISS ART-BAG HNDLD BY BSO');
insert into activity (code,description) values ('315','REG PYMT-UNDOC MA CLAIM - VALU');
insert into activity (code,description) values ('85V','VENDOR AUTHORIZATION SETTLE');
insert into activity (code,description) values ('208','IX PYMT + TC');
insert into activity (code,description) values ('288','AVERAGE OFFER');
insert into activity (code,description) values ('58V','VENDOR REPAIR/REPLACE');
insert into activity (code,description) values ('601','BCCF MATCH');
insert into activity (code,description) values ('590','CHECK LESS EXCLUDED ITEMS');
insert into activity (code,description) values ('9216','VENDOR INVOICE');
insert into activity (code,description) values ('107','AUTHORIZE REPAIR');
insert into activity (code,description) values ('99C','INBOUND MAIL FROM CURE');
insert into activity (code,description) values ('82','REQUESTED BAG- OTHER AIRLINE');
insert into activity (code,description) values ('112','REQUEST ADDL INFO');
insert into activity (code,description) values ('311','REG PYMT-UNDOC MA CLAIM');

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (676,'Immediate Fulfillment','Issue a Voucher for Immediate Fulfillment',1100,null,0,99,0);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (677,'Email Fulfillment','Issue a Voucher for Email Fulfillment',1100,null,0,99,0);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (678,'Mail Fulfillment','Issue a Voucher for Mail Fulfillment',1100,null,0,99,0);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (679,'Cancel a Voucher','Cancel a Voucher',1100,null,0,99,0);

alter table ExpensePayout add distributemethod varchar(5);

GO

create table bagdrop (
  id bigint identity not null,
  createDate datetime not null,
  lastUpdated datetime not null,
  arrivalStationCode varchar(3),
  originStationCode varchar(3),
  entryMethod integer,
  bagDropTime datetime,
  schArrivalDate datetime,
  actArrivalDate datetime,
  airline varchar(3),
  flight varchar(10),
  createAgent_ID integer,
  primary key (id)
);

alter table ExpensePayout add paytype varchar(5);

insert into properties (keyStr,valueStr) values ('document.location.letters','letters');
insert into properties (keyStr,valueStr) values ('document.location.receipts','receipts');
insert into properties (keyStr,valueStr) values ('document.location.temp','temp');

GO

alter table issuance_item_inventory add verified_incident tinyInt default 0;
alter table audit_issuance_item_inventory add verified_incident tinyInt default 0;
alter table audit_issuance_item_quantity add verified_incident tinyInt default 0;

GO

update issuance_item_inventory set verified_incident=0;
update audit_issuance_item_inventory set verified_incident=0;
update audit_issuance_item_quantity set verified_incident=0;
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (680,'BSO Expense Process','Allows users to create Expense Payouts based on Established BSO Process',59,'',0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (681,'BSO Expense Admin','Allows Administrator Usergroup to assign BSO Process and Limit to other Usergroups',39,'',0,99,0);
alter table usergroup add bsoLimit float default 0;

GO

update usergroup set bsoLimit=0;

alter table category add categoryVal integer default 0;

GO

update category set categoryVal=0;

insert into category (description ,type,categoryVal) VALUES ('Curb Side',4,1);
insert into category (description ,type,categoryVal) VALUES ('Ticket Counter',4,2);
insert into category (description ,type,categoryVal) VALUES ('Gate',4,3);
insert into category (description ,type,categoryVal) VALUES ('Remote',4,4);
insert into category (description ,type,categoryVal) VALUES ('Plane-side',4,5);
insert into category (description ,type,categoryVal) VALUES ('Unchecked',4,6);
insert into category (description ,type,categoryVal) VALUES ('Kiosk',4,7);

update systemcomponents set component_name='LUV Immediate Fulfillment' where component_id=676;
update systemcomponents set component_name='LUV Email Fulfillment' where component_id=677;
update systemcomponents set component_name='LUV Mail Fulfillment' where component_id=678;
update systemcomponents set component_name='LUV Cancel a Voucher' where component_id=679;

alter table expensepayout add printcount integer default 0;

alter table expensetype alter column description varchar(30); 

GO

update expensepayout set printcount=0;
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (
   1101,'Cust_Comm_Approval_Queue','Manage approval queue for customer communiations.',15,'customerCommunicationsApp.do',1,99,3);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (
   1102,'Cust_Comm_Rejection_Queue','Manage rejection queue for customer communiations.',15,'customerCommunicationsRejected.do',1,100,3);

GO

alter table task add incidentActivityId bigint default null;
alter table task add active bit default 1;

alter table expensepayout add ordernum varchar(10);
alter table expensepayout alter column paytype varchar(10);
alter table expensepayout add cancelreason varchar(10);
alter table expensepayout add cancelcount integer default 0;
alter table expensepayout add slvnum varchar(20);
alter table expensepayout add seccode varchar(10);
alter table expensepayout add issuanceItem integer default 0;

GO

update task set active = 1;
update expensepayout set cancelcount = 0,issuanceItem = 0;
SET IDENTITY_INSERT status ON;
insert into status (Status_ID,description,table_ID) VALUES (94,'Cancelled',11);
SET IDENTITY_INSERT status OFF;

insert into properties ( keyStr, valueStr) VALUES ('label.queue', '1');
insert into systemcomponents (component_id, component_name, component_desc, parent_component_id, component_action_link,display ,sort_order,sort_group) 
VALUES (1500, 'Label Queue', 'Label Queue', 15, 'label.do', 1, 100, 4);

create table label (
  id bigint not null identity,
  agent_id integer not null,
  text varchar(100) not null,
  lastUpdate datetime not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,//TODO
  primary key (id),
  constraint fk_label_agent foreign key (agent_id) references agent (Agent_ID)
);

create table incident_activity_remark (
  id bigint identity not null,
  agent bigint not null,
  createDate datetime not null,
  remarkText varchar(255),
  incidentActivity bigint not null,
  primary key (id)
);

GO

alter table incident_activity drop fk_activity_status;
alter table incident_activity drop column status;

alter table usergroup add luvLimit float default 0;

GO

update usergroup set luvLimit=0;

create table audit_bagdrop (
  id bigint identity not null,
  bagdrop_id bigint not null,
  entryDate datetime not null,
  entryMethod integer,
  bagDropTime datetime,
  schArrivalDate datetime,
  actArrivalDate datetime,
  modifyAgent_ID integer,
  primary key (id)
);

create index bagdrop_id on audit_bagdrop (bagdrop_id);

GO

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (
   750,'Bag Drop','View and update Bag Drops',39,'bagDrop.do?reset=1',1,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (
   751,'Bag Drop Admin','Bag Drop Admin',39,null,0,99,0);
   
alter table ohd alter column storage_location varchar(125);
alter table ohd alter column firstname varchar(25);
alter table ohd alter column lastname varchar(25);
alter table ohd_passenger alter column firstname varchar(25);
alter table ohd_passenger alter column lastname varchar(25);

alter table ohd alter column claimchecknum_bagnumber varchar(12);
alter table incident_claimcheck alter column claimchecknum_bagnumber varchar(12);   
   
alter table company_specific_variable add bagdrop_autorefresh_mins integer default 0;
alter table audit_company_specific_variable add bagdrop_autorefresh_mins integer default 0;

GO

update company_specific_variable set bagdrop_autorefresh_mins=0;
update audit_company_specific_variable set bagdrop_autorefresh_mins=0;

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (688,'Payment Approval Process Admin'  ,'Allows Admin User to assign Payment Approval Process to other Usergroups', 39 ,'' ,0  ,104  ,3);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (687,'Payment Approval Process Create'  ,'Allows User to create finances that need to go through the Approval process'  , 59 ,'paymentApproval.do'  ,1  ,104  ,3);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (682,'Payment Approval Process Approve'  ,'Allows User to View and Approve or Reject Tasks associated with Awaiting Disbursement Tasks'  ,15  ,'paymentApproval.do'  ,1  ,104  ,3);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (683,'Disbursement Rejection View'  ,'Allows User to view rejections for issued Disbursements'  ,15  ,'rejectedDisbursements.do' ,1  ,101  ,3);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (684,'Fraud Review Task Queue'  ,'Allows User to view Fraud Review Tasks that need Approval or Rejection'  ,15  ,'fraudReview.do' ,1  ,102  ,3);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (685,'Supervisor Review Task Queue'  ,'Allows User to view Supervisor Review Tasks that need Approval or Rejection'  ,15  ,'supervisorReview.do' ,1  ,103  ,3);

alter table expensepayout add maildate date;
alter table company_specific_variable add fraudReview tinyint  default 0;

GO

update company_specific_variable set fraudReview=0;

alter table incident_activity add expensepayout_id integer;
alter table incident_activity add lastPrinted date;
SET IDENTITY_INSERT status ON;
insert into status (Status_ID,description,table_ID) VALUES (1411,'Fraud Review',26);
insert into status (Status_ID,description,table_ID) VALUES (1412,'Supervisor Review',26);
insert into status (Status_ID,description,table_ID) VALUES (1413,'Awaiting Disbursement',26);
insert into status (Status_ID,description,table_ID) VALUES (1414,'Fraud Rejected',26);
insert into status (Status_ID,description,table_ID) VALUES (1415,'Supervisor Rejected',26);
insert into status (Status_ID,description,table_ID) VALUES (1416,'Finance Rejected',26);
insert into status (Status_ID,description,table_ID) VALUES (1417,'Finance Approved',26);
insert into status (Status_ID,description,table_ID) VALUES (1418,'Fraud Approved',26);
insert into status (Status_ID,description,table_ID) VALUES (1419,'Supervisor Approved',26);
SET IDENTITY_INSERT status OFF;

insert into activity (code, description) VALUES ('55C','CREATE CLAIM SETTLEMENT LETTER');
insert into template_type (ordinal,defaultName) VALUES (5,'Expense');

insert into properties ( keyStr, valueStr) VALUES ('mishandling.attachment.at.creation','1');

insert into activity (code,description) VALUES ('99E','INBOUND WEB PORTAL MESSAGE');
insert into activity (code,description) VALUES ('99O','OUTBOUND WEB PORTAL MESSAGE');

GO

insert into properties ( keyStr, valueStr) VALUES ('document.print.queue','1');
insert into systemcomponents (component_id, component_name, component_desc, parent_component_id, component_action_link,display ,sort_order,sort_group) 
VALUES (1501, 'Document Print Queue', 'Approved incident activity documents pending print', 15, 'documentPrintCommunications.do', 1, 101, 4);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
VALUES (1103,'Tasks View Pending','View tasks not in work.',39,'viewPendingTasks.do',1,100,0);

insert into template_var (displayTag,associatedClass) VALUES ('TotalAmount','Expense');
insert into template_var_dependency (associatedClass,dependentClass) VALUES ('Expense'  ,'Expense');

SET IDENTITY_INSERT status ON;
insert into status (Status_ID,description,table_ID) values (1500,'Claim In Progress',27);
insert into status (Status_ID,description,table_ID) values (1501,'Correspondence Received',27);
insert into status (Status_ID,description,table_ID) values (1502,'Final Review',27);
insert into status (Status_ID,description,table_ID) values (1503,'Pending Customer Response',27);
insert into status (Status_ID,description,table_ID) values (1504,'Claim Finalized',27);
SET IDENTITY_INSERT status OFF;

alter table incident add claimStatus integer default 0;

GO

update incident set claimStatus = 0;

alter table audit_usergroup add luvLimit float default 0;
alter table audit_usergroup add bsoLimit float default 0;

alter table issuance_item_inventory add cost float default 0;
alter table audit_issuance_item_inventory add cost float default 0;

GO

update audit_usergroup set luvLimit = 0, bsoLimit = 0;
update issuance_item_inventory set cost = 0;
update audit_issuance_item_inventory set cost = 0;

alter table issuance_item_inventory add firstName varchar(25);
alter table issuance_item_inventory add lastName varchar(25);
alter table issuance_item_inventory add country varchar(2);
alter table issuance_item_inventory add phoneNumber varchar(20);
alter table issuance_item_inventory add address1 varchar(100);
alter table issuance_item_inventory add address2 varchar(100);
alter table issuance_item_inventory add city varchar(50);
alter table issuance_item_inventory add state varchar(2);
alter table issuance_item_inventory add province varchar(20);
alter table issuance_item_inventory add zip varchar(12);
alter table issuance_item_inventory add specialNeedDescription varchar(255);

alter table audit_issuance_item_inventory add firstName varchar(25);
alter table audit_issuance_item_inventory add lastName varchar(25);
alter table audit_issuance_item_inventory add country varchar(2);
alter table audit_issuance_item_inventory add phoneNumber varchar(20);
alter table audit_issuance_item_inventory add address1 varchar(100);
alter table audit_issuance_item_inventory add address2 varchar(100);
alter table audit_issuance_item_inventory add city varchar(50);
alter table audit_issuance_item_inventory add state varchar(2);
alter table audit_issuance_item_inventory add province varchar(20);
alter table audit_issuance_item_inventory add zip varchar(12);
alter table audit_issuance_item_inventory add specialNeedDescription varchar(255);

GO


create table UsergroupNameMap (
  id bigint identity not null,
  ldapName varchar(50),
  ntName varchar(50),
  ntGroupId integer,
  primary key (id));
 
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
VALUES (1150,'Unassigned Inbound Queue','View tasks that have not been assigned',15,'unassignedInboundQueue.do',1,50,4);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
VALUES (1151,'Assigned Personal Tasks','View assigned tasks',15,'personalTasks.do?reset=1',1,51,4);

alter table agent add inboundQueue tinyint default 0;
alter table audit_agent add inboundQueue tinyint default 0;

GO

update agent set inboundQueue = 0;
update audit_agent set inboundQueue = 0;

alter table task alter column agent_id integer;

alter table task add inboundqueue_id bigint;

create table inboundqueue(
    id bigint identity not null,
    incident_id varchar(13),
    activity_id bigint,
    incidentActivityId bigint,
    primary key (id));

GO


insert into template_var (displayTag,associatedClass) VALUES 
   ('FullAddress' ,'Incident'), 
   ('FullAddress','Claim'),
   ('FullAddress','FoundItem'),
   ('Country','Incident'), 
   ('Country','Claim'), 
   ('Country','FoundItem'),
   ('Province','Incident'), 
   ('Province','Claim'), 
   ('Province','FoundItem');
   
    
alter table issuance_category add limitByPassenger bit not null default 0;

GO

update issuance_category set limitByPassenger = 0;

update status set description = 'Letter Pending Approval' where Status_ID = '1400';
update status set description = 'Letter Rejected' where Status_ID = '1402';

create table task_type (
  id bigint not null IDENTITY,
  code integer not null,
  description varchar(255) not null,
  primary key(id)
);

GO

insert into task_type (code,description) values
(1,'Inbound Physical'),
(2,'Inbound Electronic'),
(3,'Customer Communication'),
(4,'Disbursement'),
(5,'Fraud Review'),
(6,'Supervisor Review'),
(7,'Inbound Damaged');

alter table task add task_type_id bigint default null;

alter table bdo add distance float default 0;

GO

update bdo set distance=0;

insert into properties (keyStr,valueStr) VALUES ('issue.voucher.expiration.offset.days','365');

alter table expensepayout add firstname varchar(20) DEFAULT NULL;
alter table expensepayout add middlename varchar(20) DEFAULT NULL;   
alter table expensepayout add lastname varchar(20) DEFAULT NULL;  
alter table expensepayout add address1 varchar(50) DEFAULT NULL;   
alter table expensepayout add address2 varchar(50) DEFAULT NULL;   
alter table expensepayout add city varchar(50) DEFAULT NULL;   
alter table expensepayout add state_ID varchar(2) DEFAULT NULL;   
alter table expensepayout add province varchar(100) DEFAULT '';   
alter table expensepayout add zip varchar(11) DEFAULT NULL;   
alter table expensepayout add homephone varchar(50) DEFAULT NULL;
alter table expensepayout add workphone varchar(50) DEFAULT NULL;
alter table expensepayout add mobile varchar(50) DEFAULT NULL;
alter table expensepayout add email varchar(100) DEFAULT NULL;     
alter table expensepayout add countrycode_ID varchar(3) DEFAULT NULL; 

GO
update expensepayout set province='';

alter table company_irregularity_codes add departStation tinyint default 0;
alter table company_irregularity_codes add transferStation tinyint default 0;
alter table company_irregularity_codes add destinationStation tinyint default 0;
alter table company_irregularity_codes add anyStation tinyint default 0;

GO

update company_irregularity_codes set departStation=0;
update company_irregularity_codes set transferStation=0;
update company_irregularity_codes set destinationStation=0;
update company_irregularity_codes set anyStation=0;

update task set task_type = '2DAYTASK' where task_type = 'TWODAYTASK';
update task set task_type = '3DAYTASK' where task_type = 'THREEDAYTASK';
update task set task_type = '4DAYTASK' where task_type = 'FOURDAYTASK';
update task set task_type = '5DAYTASK' where task_type = 'FIVEDAYTASK';


alter table ohd alter column firstname varchar(30);
alter table ohd alter column lastname varchar(30);
alter table ohd_passenger alter column firstname varchar(30);
alter table ohd_passenger alter column lastname varchar(30);
alter table audit_ohd alter column firstname varchar(30);
alter table audit_ohd alter column lastname varchar(30);
alter table audit_ohd_passenger alter column firstname varchar(30);
alter table audit_ohd_passenger alter column lastname varchar(30);

GO

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (1502,'Import WT AHL','Allows user to Import WorldTracer AHL to create new Incidents',93,'',0,0,0);

alter table issuance_category add copyDescription bit not null default 0;
alter table issuance_category alter column description varchar(100);
alter table issuance_item alter column description varchar(100);
alter table issuance_item add cost float default 0;

alter table ohd_log alter column expeditenum varchar(12);

GO

update issuance_category set copyDescription = 0;
update issuance_item set cost = 0;

insert into task_type (code, description ) values (8,'Secondary Correspondence');

alter table activity add display bit not null default 1;

GO

update activity set display = 1;
update activity set display = 0 where code in ('55C', '99E');

alter table audit_ohd alter column storage_location varchar(125);

create index wt_transaction_createDate_idx on wt_transaction (createDate);
create index wt_transaction_result_idx on wt_transaction (result);
create index wt_transaction_txType_idx on wt_transaction (txType);
create index wt_transaction_agent_id_idx on wt_transaction (agent_id);
create index wt_transaction_incident_id_idx on wt_transaction (incident_id);
create index wt_transaction_ohd_id_idx on wt_transaction (ohd_id);

update systemcomponents set component_action_link = 'unassignedInboundQueue.do?loadList=1' where component_id = 1150;

alter table agent_logger add expired bit not null default 0;

GO

update agent_logger set expired=0;
update agent_logger set expired=1 where log_off_time is null;

alter table lffound add receiptFile_id bigint;

alter table bdo add pickuptz_id integer default 0;

GO

update bdo set pickuptz_id=0;


create index inboundqueue_idx on inboundqueue (incident_id);
create index task_idx on task (inboundqueue_id);


alter table template add marginTop varchar(5) default '1.0';
alter table template add marginBottom varchar(5) default '1.0';
alter table template add marginLeft varchar(5) default '0.75';
alter table template add marginRight varchar(5) default '0.75';

alter table document add marginTop varchar(5) default '1.0';
alter table document add marginBottom varchar(5) default '1.0';
alter table document add marginLeft varchar(5) default '0.75';
alter table document add marginRight varchar(5) default '0.75';

GO

update template set marginTop = '1.0', marginBottom = '1.0', marginLeft = '0.75', marginRight = '0.75';
update document set marginTop = '1.0', marginBottom = '1.0', marginLeft = '0.75', marginRight = '0.75';

GO

insert into category (description,type,categoryVal) values ('Bagbuzz', 100, 1);
alter table bagbuzz add category_ID bigint;

GO

update bagbuzz set category_ID = 0;--ADD to deployment plan determine correct value

update systemcomponents set component_action_link = 'bagbuzzsearch.do?admin_view=1', display = 1, sort_order = 30 where component_name = 'BagBuzzAdmin' and component_id = 504;
insert into properties (keyStr,valueStr) VALUES ('bagbuzz.max.categories','5');

GO
alter table bagbuzz alter column category_ID bigint not null;