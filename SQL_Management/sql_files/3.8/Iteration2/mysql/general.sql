# Iteration 2, Sprint 1
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
id int(11) auto_increment not null,
description varchar(100) not null,
link_address varchar(255) not null,
image varchar(50) not null,
companycode_id varchar(3),
primary key (id)
);

alter table incident_claimcheck modify column claimchecknum_bagnumber varchar(8);
alter table item modify column claimchecknum_bagnumber varchar(8);
alter table ohd modify column claimchecknum_bagnumber varchar(8);

# Iteration 2, Sprint 2
alter table item add expediteTagNum varchar(12) null;
alter table audit_item add expediteTagNum varchar(12) null;

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (639,'Expedite_Tag_Num_Collect','Collect expedite tag number for damaged Incidents',8,null,0,99,0);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
VALUES (1100,'General','General Permissions that universally affect the NetTracer Application',1100,'',0,1  ,0 );
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
values (335,'Secure_Remarks','Generate and View Secure Remarks in Incidents and OHDs',1100,null,0,0,0);

alter table remark add secure tinyint default false;
alter table Audit_Remark add secure tinyint default false;

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (640,'Receive_Timestamp_Collect','Collect the receive time of a damaged bag upon arrival to LZ',8,null,0,99,0);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (641,'Receive_Timestamp_Delete','Delete the receive time to LZ of a damaged bag',8,null,0,99,0);

alter table incident add rxTimestamp datetime default null;
alter table audit_incident add rxTimestamp datetime default null;

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (643,'Issuance_Item_Global_Admin','Administration for Issuance Categories (Global Privileges for Station Admin also)',39,'editIssuanceCategory.do',1,21,0);

insert into status (status_ID, description, table_ID) values ('700','Available','19');
insert into status (status_ID, description, table_ID) values ('701','On Loan','19');
insert into status (status_ID, description, table_ID) values ('702','Issued','19');
insert into status (status_ID, description, table_ID) values ('703','Discarded','19');

alter table issuance_item add active bit not null default 1;

alter table issuance_category add active bit not null default 1;
alter table issuance_category add lostdelay bit not null default 1;
alter table issuance_category add damage bit not null default 1;
alter table issuance_category add missing bit not null default 1;
alter table issuance_category add inventory bit not null default 0;
alter table issuance_category add document_id bigint not null default 0;

create table issuance_item_inventory (
	id bigint auto_increment not null,
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
	audit_id bigint auto_increment not null,
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

insert into status (Status_ID,description,table_id) values (800,'Returned',20);
insert into status (Status_ID,description,table_id) values (801,'Damaged',20);
insert into status (Status_ID,description,table_id) values (802,'Missing',20);

alter table item_inventory add enteredDate date default null;
alter table audit_item_inventory add enteredDate date default null;
alter table item_inventory add purchaseDate date default null;
alter table audit_item_inventory add purchaseDate date default null;
alter table item_inventory add invItemCost double default 0;
alter table audit_item_inventory add invItemCost double default 0;
alter table item_inventory add invItemCurrency varchar(3) default null;
alter table audit_item_inventory add invItemCurrency varchar(3) default null;
alter table item_inventory add itemStatusId int default 0;
alter table audit_item_inventory add itemStatusId int default 0;

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

alter table incident add courtesyReasonId int default 0;
alter table audit_incident add courtesyReasonId int default 0;
alter table incident add courtesyDescription varchar(100) default null;
alter table audit_incident add courtesyDescription varchar(100) default null;

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
values (648,'Incident_Courtesy_Reason_Collect','Collect courtesy reason information on all incident types',1100,'',0,2,0 );



alter table item add column noAddFees tinyint default false;
alter table item add column other int default 0;
alter table item add column oversize tinyint default false;
alter table item add column overweight tinyint default false;
alter table ohd add column noAddFees tinyint default false;
alter table ohd add column other int default 0;
alter table ohd add column oversize tinyint default false;
alter table ohd add column overweight tinyint default false;
alter table bdo add column origDelivCost double default 0.0;
create TABLE category (id bigint auto_increment not null, description varchar(100), type int, primary key (id));
insert into category (description, type) values ("Skis",1);
insert into category (description, type) values ("Golf Clubs",1);
insert into category (description, type) values ("Military Duffle",1);

update item set other=0;
update ohd set other=0;

create table issuance_item_incident (
	id bigint auto_increment not null,
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

alter table company_specific_variable add column auto_close_ohd_days_back int default 0;
update company_specific_variable set auto_close_ohd_days_back=0;
insert into properties (keyStr,valueStr) VALUES ('auto.close.ohd.remark','This OHD was automatically closed after being in the LZ for {autoCloseDays} days.');

alter table articles add enteredDate date default null;
alter table audit_articles add enteredDate date default null;
alter table articles add statusId int default 0;
alter table audit_articles add statusId int default 0;

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (649,'Additional_Missing_Item_Information_Collect','Collect additional fields for contents missing from baggage',12,null,0,99,0);

alter table item add column assistDeviceType int default 0;
update item set assistDeviceType=0;
alter table item add column assistDeviceCheck varchar(5);
alter table audit_item add column assistDeviceType varchar(100);
update  audit_item  set assistDeviceType=0;
alter table  audit_item  add column assistDeviceCheck varchar(5);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
values (336,'Capture ACAA Information','Captures Assist Device Information for Bags Types 94 and 95',1100,null,0,0,0);
insert into category (description, type) values ("Breathing Machine",2);
insert into category (description, type) values ("Cane",2);
insert into category (description, type) values ("Hearing Aid",2);
insert into category (description, type) values ("Wheelchair (Electric)",2);
insert into category (description, type) values ("Wheelchair (Manual)",2);
insert into category (description, type) values ("Other",2);
insert into category (description, type) values ("Prosthesis",2);
insert into category (description, type) values ("Scooter",2);
insert into category (description, type) values ("Walker",2);

create table template (
  id bigint auto_increment not null,
  name varchar(256) not null,
  description varchar(256),
  active bit default 0,
  createDate timestamp,
  lastUpdated timestamp,
  data text,
  primary key (id)
);

create table template_type (
  id bigint auto_increment not null,
  ordinal int not null,
  defaultName varchar(255) not null,
  primary key (id)
);

create table template_type_mapping (
  templateId bigint not null,
  templateTypeId bigint not null,
  primary key (templateId,templateTypeId),
  index fk_template_id (templateTypeId),
  constraint fk_template_id foreign key (templateId) references template (id),
  constraint fk_template_type_id foreign key (templateTypeId) references template_type (id)
);

create table template_var (
  id bigint auto_increment not null,
  displayTag varchar(256) not null,
  associatedClass varchar(256) not null,
  primary key (id),
  constraint unique_tag_class unique (displayTag,associatedClass)
);

create table template_var_dependency (
  id bigint auto_increment not null,
  associatedClass varchar(256) not null,
  dependentClass varchar(256) not null,
  primary key (id),
  constraint unique_dependency unique (associatedClass,dependentClass)
);

create table template_var_mapping (
  templateId bigint not null,
  templateVarId bigint not null,
  primary key (templateId, templateVarId),
  index fk_template_var (templateVarId),
  constraint fk_templateId foreign key (templateId) references template (id),
  constraint fk_templateVarId foreign key (templateVarId) references template_var (id)
);

create table document (
  id bigint auto_increment not null,
  templateId bigint not null,
  content text default null,
  primary key (id, templateId),
  index fk_templateId (templateId),
  constraint fk_template foreign key (templateId) references template (id)
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

create table claim_type (id int not null auto_increment,description varchar(100),PRIMARY KEY (id));

insert into claim_type (description) VALUES ('Domestic - Max $3400 USD');
insert into claim_type (description) VALUES ('Montreal Convention - Max 1131 SDR');
insert into claim_type (description) VALUES ('Warsaw Convention - Per Kilogram');

CREATE TABLE generaldepreciationrules (
  id int(11) NOT NULL AUTO_INCREMENT,
  lessTwentyDeprec double DEFAULT NULL,
  twentyOnefiftyDeprec double DEFAULT NULL,
  onefiftyDeprec double DEFAULT NULL,
  noDates int(11) DEFAULT NULL,
  companyCode varchar(2) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY id (id,companyCode)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
## Default generaldepreciationrules that need to be set for each client.
## insert into generaldepreciationrules (lessTwentyDeprec,twentyOnefiftyDeprec,onefiftyDeprec,noDates,companyCode) VALUES (0,0,0,0,'WN');



create table depreciation_category (id int not null auto_increment, name varchar(30) not null, calcMethod int, notCoveredCoc tinyint, flatRate double, firstYear double,
 secondYear double,  thirdYear double,  eachYear double,  maxDeprec double, companyCode varchar(2), PRIMARY key(ID));
ALTER TABLE depreciation_category
  ADD INDEX (id, companyCode);


insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (656,'Claim Depreciation Calculator','Use a Depreciation Calculator to Determine Payouts for Claims',59,'claim_deprec_calc.do',0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (657,'Depreciation Calculator Administration','Manage Depreciation Calculator variables and categories',39,'deprecCalcAdmin.do',1,99,0);
create table claim_depreciation (id int not null auto_increment, claim_id bigint, dateCalculate date, claimType_id int, totalWeight double, weightMetric varchar(3), currency varchar(3), totalApprovedPayout double default 0, primary key(id));
ALTER TABLE claim_depreciation
  ADD INDEX (id, claim_id);


create table depreciation_item (id int not null auto_increment, description varchar(200), amountClaimed double, datePurchase date, 
category_id int, proofOwnership int, notCoveredCoc tinyint, calcValue double, claimValue double, claim_depreciation_id bigint, primary key(id));
ALTER TABLE depreciation_item
  ADD INDEX (id, claim_depreciation_id);

insert into properties (keyStr,valueStr) VALUES ('domestic.payout.value','3400');


insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (652,'NTLF_Enter_Items','Add Found Items',64,'ntlf_enter_items.do',1,7,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (653,'NTLF_Found_Items','Found Item Page',64,'ntlf_create_found_item.do?createNew=1',0,8,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (654,'NTLF_Search_Items','Search Found Items',64,'ntlf_search_lost_found.do',1,9,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (655,'NTLF_TM_Open_HV_Items','Task Manager High Value Items Number',15,'ntlf_search_lost_found.do?tmhvSearch=1',1,99,5);

insert into status values (614, 'Sent to LFC', 17);
insert into status values (615, 'Removed', 17);

## Manufacturer SQL

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
update task set generic_timestamp = deferment_timestamp;
alter table task drop column deferment_timestamp;
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (658,'Manage_CSS_Daily_Calls','Task Manager CS&S Daily Calls Display',15,'css_calls.do',1,99,1);
insert into status values (100, 'expired', 13);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (659,'Manage Tasks','Task Manager CS & S Daily Calls Display',39,null,0,99,0);

alter table bdo add pickupdate date default null;
alter table bdo add pickuptime time default null;

##For Hudson, must be updated to reflect B6_Testing, B6_Training, and B6_Production
create table if not exists z_b6_audit_claim_settlement like b6_training.z_b6_audit_claim_settlement;
create table if not exists z_b6_audit_claim_settlement_bag like b6_training.z_b6_audit_claim_settlement_bag;
create table if not exists z_b6_audit_claim_settlement_inventory like b6_training.z_b6_audit_claim_settlement_inventory;
create table if not exists z_b6_claim_settlement like b6_training.z_b6_claim_settlement;
create table if not exists z_b6_claim_settlement_bag like b6_training.z_b6_claim_settlement_bag;
create table if not exists z_b6_claim_settlement_inventory like b6_training.z_b6_claim_settlement_inventory;

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (660,'NTLF_TM_Ship_To_LFC','Task Manager Ship to LFC',15,'ntlf_search_lost_found.do?stLFCSearch=1',1,99,5);

alter table audit_company_specific_variable add status_message varchar(255) default null;

alter table ohd modify column posId varchar(8) default null;
alter table audit_ohd modify column posId varchar(8) default null;
alter table item modify column posId varchar(8) default null;
alter table audit_item modify column posId varchar(8) default null; 