

sp_rename lfreservation, lflossinfo;
GO
alter table lflossinfo add lossdate datetime;

alter table lflossinfo drop column pickuplocation_station_ID;
alter table lflossinfo add origin_station_ID integer;

alter table lflossinfo drop column dropofflocation_station_ID;
alter table lflossinfo add destination_station_ID integer;

alter table lflost drop column reservation_id;
alter table lflost add lossInfo_id bigint;
GO

alter table lfitem add longDescription varchar(2047);
alter table lfitem add caseColor varchar(255);
alter table lfitem add model varchar(255);
alter table lfitem add itemCondition varchar(255);
alter table lfitem add size varchar(255);

alter table lfphone add item_id integer;
alter table lfitem add phone_id integer;

alter table lfdelivery add found_id bigint;

alter table lfcategory add companycode varchar(8);
GO
update lfcategory set companycode = 'AB';

alter table lffound add barcode varchar(255);
alter table lfphone alter column person_id bigint;

create table lfremark(
  id numeric(19,0) identity not null,
  calltime bigint,
  outcome integer,
  found_id bigint,
  lost_id bigint,
  agent_ID integer,
  remarkDate datetime,
  type integer,
  remarktext varchar(2047),
  primary key(id)
);

GO


alter table lflost add email1 bit default 0;
alter table lflost add email2 bit default 0;

update systemcomponents set sort_order = 99 where component_name = 'Administration';

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
VALUES (614,'LFC Item Entry Workflow','view fraud results',614,'',1,12,0);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
VALUES (615,'Enter Items','enter items',614,'enter_items.do',1,0,0);

insert into properties (keyStr,valueStr) values ('history.queue.size','20');
insert into properties (keyStr,valueStr) values ('lfc.item.entry.display.count','3');

alter table lfitem add value integer default 0;
alter table lffound add itemLocation integer default 0;
alter table lffound add binId varchar(255);

insert into properties (keyStr,valueStr) values ('lf.server.location', 'LFC_SERVER_LOCATION');
GO


create table generallog(
  id numeric(19,0) identity not null,
trans varchar(20),
entrydate datetime,
refId varchar(20),
description varchar(255),
elapseTime numeric(19,0) default 0,
primary key(id)
);

update properties set valueStr = 'NTServices_1_0/ClaimBean/publicProductionRemoteSSL' where keyStr = 'fraud.server.name';
update properties set valueStr = 'NTServices_1_0/PrivacyPermissionsBean/publicProductionRemoteSSL' where keyStr = 'fraud.permissions.server.name';


alter table lffound add receivedDate datetime;
GO
update lffound set receivedDate = foundDate;

alter table dispute alter column disputeExplanation varchar(1550);
alter table dispute alter column resolutionRemarks varchar(1550);

create table passwordhistory(
  id numeric(19,0) identity not null,
  pcount integer not null default 1,
  agent_id bigint,
  password varchar(512),
  primary key(id)
);

alter table company_specific_variable add min_pass_size integer default 8;
alter table company_specific_variable add pass_x_history integer default 20;
alter table audit_company_specific_variable add min_pass_size integer default 0;
alter table audit_company_specific_variable add pass_x_history integer default 0;
GO
update company_specific_variable set min_pass_size=8;
update company_specific_variable set pass_x_history=20;
update audit_company_specific_variable set min_pass_size=0;
update audit_company_specific_variable set pass_x_history=0;

insert into agent (firstname, lastname, username, companycode_ID, shift_id, dateformat_ID, timeformat_ID, active, timeout, usergroup_id, defaultlocale, currentlocale, defaultcurrency, reset_password, station_id, is_online) 
select 'junit', 'test', 'junitagent', companycode_ID, shift_id, dateformat_ID, timeformat_ID, active, timeout, usergroup_id, defaultlocale, currentlocale, defaultcurrency, reset_password, station_id, is_online
from agent where username = 'ogadmin';

insert into agent (firstname, lastname, username, companycode_ID, shift_id, dateformat_ID, timeformat_ID, active, timeout, usergroup_id, defaultlocale, currentlocale, defaultcurrency, reset_password, station_id, is_online) 
select 'web', 'agent', 'webagent', companycode_ID, shift_id, dateformat_ID, timeformat_ID, active, timeout, usergroup_id, defaultlocale, currentlocale, defaultcurrency, reset_password, station_id, is_online
from agent where username = 'ntadmin' and companycode_ID not in ('LF', 'AB');


alter table lflost add foundEmail bit default 0;
GO
update lflost set foundEmail=0;
alter table lffound add deliveredDate datetime default null;
alter table lffound add checkNumber varchar(255) default null;
alter table lffound add checkAmount double precision default 0;
GO
update lffound set checkAmount=0;



insert into systemcomponents values (618,'Reopen Lost and Found','Reopen Lost Reports and Found Items',39,'',0,18,0);

insert into properties (keyStr,valueStr) values ('lf.high.value.salvage.days','60');
insert into properties (keyStr,valueStr) values ('lf.low.value.salvage.days','30');

create table LFSalvage (
    id bigint not null identity,
    closedDate datetime,
    createdDate datetime,
    agent_ID integer not null,
    status_ID integer not null,
    primary key (id)
);

GO

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
VALUES (619,'LFC Salvage Workflow','',619,'',1,13,0);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
VALUES (620,'Create Salvage','Create new salvage',619,'lf_salvage.do?createNew=1',1,0,0);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
VALUES (621,'Search Salvage','Search for an existing salvage',619,'lf_search_salvage.do?clear=1',1,1,0);

GO



alter table lffound add salvage_id bigint;

alter table lfsalvage add station_id bigint;

alter table audit_incident alter column oc_claim_id bigint;
GO
alter table audit_incident add default 0 for oc_claim_id;

GO

update audit_incident set oc_claim_id=0, modify_time = modify_time where oc_claim_id is null;

insert into systemcomponents values (624,'Update LF Remarks','Update lost and found remarks',604,'',0,5,0);

GO



alter TABLE fsclaim add amountPaidCurrency varchar(255);
GO
update fsclaim SET amountPaidCurrency = amountClaimedCurrency;


alter table passenger add numRonKitsIssued int default 0;
alter table item add replacementBagIssued int default 0;

insert into systemcomponents values 
(625,'Save Button at Top of Page','Add save button to the top of the incident page.',39,'',0,99,0),
(626,'Issue RON Kits','Issue RON kits to passengers.',6,'',0,99,0),
(627,'Issue Replacement Bags','Issue replacement bags to passengers.',8,'',0,99,0);

insert into systemcomponents VALUES (700,'Split Code and Station Lock','Divide the Lock Fault Information Button into two Separate Buttons for Station and Code',15,'',0,99,0);
insert into systemcomponents VALUES (701,'Get Next Dispute','Retrieve Next Dispute in List',15,'',0,99,0);

insert into report (number,resource_key) values (95,'header.customreportnum.95'),(96,'header.customreportnum.96');

delete from countrycode where CountryCode_ID='FX';
update address set CountryCode_ID='FR' where CountryCode_ID='FX';
update audit_address set CountryCode_ID='FR' where CountryCode_ID='FX';
update audit_ohd_address set CountryCode_ID='FR' where CountryCode_ID='FX';
update fraud_address set CountryCode_ID='FR' where CountryCode_ID='FX';
update ohd_address set CountryCode_ID='FR' where CountryCode_ID='FX';
update bdo_passenger set CountryCode_ID='FR' where CountryCode_ID='FX';
update companycode set CountryCode_ID='FR' where CountryCode_ID='FX';
update station set CountryCode_ID='FR' where CountryCode_ID='FX';
update z_b6_audit_claim_settlement set CountryCode_ID='FR' where CountryCode_ID='FX';
update z_b6_claim_settlement set CountryCode_ID='FR' where CountryCode_ID='FX';

insert into properties (keyStr,valueStr) VALUES ('incident.dispute.pleaseselect','1');
insert into properties (keyStr,valueStr) VALUES ('incident.dispute.getnext','1');
insert into properties (keyStr,valueStr) VALUES ('incident.dispute.faultlock','1');

ALTER TABLE dispute ADD Lock_ID int;
ALTER TABLE incident ADD codeLocked tinyint not null default 0;
ALTER TABLE incident ADD stationLocked tinyint not null default 0;
GO


insert into systemcomponents values 
(702,'Limited Loss Codes','Allows Limited Loss Code configuration on closed Incidents',15,'',0,99,0);

update systemcomponents set sort_order = 4 where component_id = 14;


alter table oc_claim add claimType integer default 1;
GO
update oc_claim set claimType = 1;
update oc_claim set claimType = 2 where incident_id in (select incident_id from incident where itemtype_id = 2);
update oc_claim set claimType = 4 where incident_id in (select incident_id from incident where itemtype_id = 3);
update oc_claim set claimType = claimType + 8 where claimid in (select distinct claimid from oc_file where interim = 1);


create table LFSegment (
    id bigint not null identity,
    lost_id bigint not null,
    flightNumber varchar(20),
    destination_station_ID integer not null,
    origin_station_ID integer not null,
    primary key (id)
);

alter table lffound add flightNumber varchar(20);


update properties set valuestr='NTServices_1_0/ClaimClientBeanV1/publicProductionRemoteSSL' where keystr = 'fraud.server.name';
alter TABLE fsclaim add amountPaidCurrency varchar(255);
GO
update fsclaim SET amountPaidCurrency = amountClaimedCurrency;

update systemcomponents set component_action_link='fraudRequests.do?resetFilter=1' where component_name = 'Fraud Requests';


alter table audit_expensepayout alter column comments varchar(2000);

alter table bagbuzz alter column description varchar(256);

GO

update state set state_id = 'KS' where state_id = 'KA';
update address set state_id = 'KS' where state_id = 'KA';
update audit_address set state_id = 'KS' where state_id = 'KA';
update bdo_passenger set state_id = 'KS' where state_id = 'KA';
update companycode set state_id = 'KS' where state_id = 'KA';
update audit_companycode set state_id = 'KS' where state_id = 'KA';
update lost_found set customer_state_id = 'KS' where customer_state_id = 'KA';
update audit_lost_found set customer_state_id = 'KS' where customer_state_id = 'KA';
update ohd_address set state_id = 'KS' where state_id = 'KA';
update audit_ohd_address set state_id = 'KS' where state_id = 'KA';
update station  set state_id = 'KS' where state_id = 'KA';
update audit_station  set state_id = 'KS' where state_id = 'KA';
update fsaddress set state='KS' where state='KA';

SET IDENTITY_INSERT status ON;
insert into status (status_id, description, table_ID) values (96,'paused',13);
insert into status (status_id, description, table_ID) values (97,'working',13);

insert into status (Status_ID,description,table_ID) VALUES ( 98,'VIP Pickup',9);
SET IDENTITY_INSERT status OFF;

GO

alter table incident_claimcheck add claimchecknum_leading varchar(1);
alter table incident_claimcheck add claimchecknum_ticketingcode varchar(3);
alter table incident_claimcheck add claimchecknum_carriercode varchar(2);
alter table incident_claimcheck add claimchecknum_bagnumber varchar(6);

GO

alter table item add claimchecknum_leading varchar(1);
alter table item add claimchecknum_ticketingcode varchar(3);
alter table item add claimchecknum_carriercode varchar(2);
alter table item add claimchecknum_bagnumber varchar(6);

GO

alter table ohd add claimchecknum_leading varchar(1);
alter table ohd add claimchecknum_ticketingcode varchar(3);
alter table ohd add claimchecknum_carriercode varchar(2);
alter table ohd add claimchecknum_bagnumber varchar(6);

GO

create index incident_scanquery1_idx on incident (Incident_ID, itemtype_ID, createdate, createtime, recordlocator);
create index incident_scanquery2_idx on incident (recordlocator, createdate, createtime, Incident_ID);
create index incident_scanquery3_idx on incident (Incident_ID, createdate, createtime);

create index ic_scanquery1_idx on incident_claimcheck (claimchecknum_bagnumber, claimchecknum_ticketingcode, claimchecknum_carriercode, claimchecknum_leading, incident_ID);
create index ic_scanquery2_idx on incident_claimcheck (claimchecknum_bagnumber, claimchecknum_ticketingcode, claimchecknum_leading, incident_ID);
create index ic_scanquery3_idx on incident_claimcheck (claimchecknum_bagnumber, claimchecknum_ticketingcode, incident_ID);
create index ic_scanquery4_idx on incident_claimcheck (claimchecknum_bagnumber, claimchecknum_carriercode, incident_ID);

create index item_scanquery1_idx on item (claimchecknum_bagnumber, claimchecknum_ticketingcode, claimchecknum_carriercode, claimchecknum_leading, incident_ID);
create index item_scanquery2_idx on item (claimchecknum_bagnumber, claimchecknum_ticketingcode, claimchecknum_leading, incident_ID);
create index item_scanquery3_idx on item (claimchecknum_bagnumber, claimchecknum_ticketingcode, incident_ID);
create index item_scanquery4_idx on item (claimchecknum_bagnumber, claimchecknum_carriercode, incident_ID);

GO

update incident_claimcheck set claimchecknum_carriercode = SUBSTRING(claimchecknum, 1, 2), claimchecknum_bagnumber = SUBSTRING(claimchecknum, 3, 6) where LEN(claimchecknum) = 8;
update incident_claimcheck set claimchecknum_ticketingcode = SUBSTRING(claimchecknum, 1, 3), claimchecknum_bagnumber = SUBSTRING(claimchecknum, 4, 6) where LEN(claimchecknum) = 9;
update incident_claimcheck set claimchecknum_ticketingcode = SUBSTRING(claimchecknum, 2, 3), claimchecknum_bagnumber = SUBSTRING(claimchecknum, 5, 6) where LEN(claimchecknum) = 10;
update incident_claimcheck set claimchecknum_leading = SUBSTRING(claimchecknum, 1, 1) where LEN(claimchecknum) = 10 and PATINDEX('[0-9]', SUBSTRING(claimchecknum, 1, 1)) > 0;
update c set c.claimchecknum_ticketingcode = a.Airline_3_Digit_Ticketing_Code from incident_claimcheck c 
join lookup_airline_codes a on c.claimchecknum_carriercode = a.Airline_2_Character_Code where c.claimchecknum_carriercode is not null;
update c set c.claimchecknum_carriercode = a.Airline_2_Character_Code from incident_claimcheck c 
join lookup_airline_codes a on c.claimchecknum_ticketingcode = a.Airline_3_Digit_Ticketing_Code where c.claimchecknum_ticketingcode is not null and c.claimchecknum_carriercode is null;

GO

update item set claimchecknum_carriercode = SUBSTRING(claimchecknum, 1, 2), claimchecknum_bagnumber = SUBSTRING(claimchecknum, 3, 6) where LEN(claimchecknum) = 8;
update item set claimchecknum_ticketingcode = SUBSTRING(claimchecknum, 1, 3), claimchecknum_bagnumber = SUBSTRING(claimchecknum, 4, 6) where LEN(claimchecknum) = 9;
update item set claimchecknum_ticketingcode = SUBSTRING(claimchecknum, 2, 3), claimchecknum_bagnumber = SUBSTRING(claimchecknum, 5, 6) where LEN(claimchecknum) = 10;
update item set claimchecknum_leading = SUBSTRING(claimchecknum, 1, 1) where LEN(claimchecknum) = 10 and PATINDEX('[0-9]', SUBSTRING(claimchecknum, 1, 1)) > 0;
update c set c.claimchecknum_ticketingcode = a.Airline_3_Digit_Ticketing_Code from item c 
join lookup_airline_codes a on c.claimchecknum_carriercode = a.Airline_2_Character_Code where c.claimchecknum_carriercode is not null;
update c set c.claimchecknum_carriercode = a.Airline_2_Character_Code from item c 
join lookup_airline_codes a on c.claimchecknum_ticketingcode = a.Airline_3_Digit_Ticketing_Code where c.claimchecknum_ticketingcode is not null and c.claimchecknum_carriercode is null;

GO

update ohd set claimchecknum_carriercode = SUBSTRING(claimnum, 1, 2), claimchecknum_bagnumber = SUBSTRING(claimnum, 3, 6) where LEN(claimnum) = 8;
update ohd set claimchecknum_ticketingcode = SUBSTRING(claimnum, 1, 3), claimchecknum_bagnumber = SUBSTRING(claimnum, 4, 6) where LEN(claimnum) = 9;
update ohd set claimchecknum_ticketingcode = SUBSTRING(claimnum, 2, 3), claimchecknum_bagnumber = SUBSTRING(claimnum, 5, 6) where LEN(claimnum) = 10;
update ohd set claimchecknum_leading = SUBSTRING(claimnum, 1, 1) where LEN(claimnum) = 10 and PATINDEX('[0-9]', SUBSTRING(claimnum, 1, 1)) > 0; 
update c set c.claimchecknum_ticketingcode = a.Airline_3_Digit_Ticketing_Code from ohd c 
join lookup_airline_codes a on c.claimchecknum_carriercode = a.Airline_2_Character_Code where c.claimchecknum_carriercode is not null;
update c set c.claimchecknum_carriercode = a.Airline_2_Character_Code from ohd c 
join lookup_airline_codes a on c.claimchecknum_ticketingcode = a.Airline_3_Digit_Ticketing_Code where c.claimchecknum_ticketingcode is not null and c.claimchecknum_carriercode is null;

GO

update fsclaim set claimDate = DATEADD(hour, 5, claimDate); 

insert into properties (keyStr, valueStr) values ('scanquery.days.back', '11');

GO


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
   

alter table company_specific_variable add auto_close_days_back integer default 0;
alter table company_specific_variable add auto_close_ld_code integer default 0;
alter table company_specific_variable add auto_close_dam_code integer default 0;
alter table company_specific_variable add auto_close_pil_code integer default 0;
alter table company_specific_variable add auto_close_ld_station integer default 0;
alter table company_specific_variable add auto_close_dam_station integer default 0;
alter table company_specific_variable add auto_close_pil_station integer default 0;

alter table audit_company_specific_variable add auto_close_days_back integer default 0;
alter table audit_company_specific_variable add auto_close_ld_code integer default 0;
alter table audit_company_specific_variable add auto_close_dam_code integer default 0;
alter table audit_company_specific_variable add auto_close_pil_code integer default 0;
alter table audit_company_specific_variable add auto_close_ld_station integer default 0;
alter table audit_company_specific_variable add auto_close_dam_station integer default 0;
alter table audit_company_specific_variable add auto_close_pil_station integer default 0;

GO

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


alter table ohd add warehouseReceivedDate dateTime;
alter table ohd add warehouseSentDate dateTime;

USE [NetTracer];
GO

CREATE NONCLUSTERED INDEX [_dta_index_ohd_6_1954106002__K21_K2_K25_K30_K27_K7_K8_1_3_4_5_6_9_10_11_12_13_14_15_16_17_18_19_20_22_23_24_26_28_29_31_32_39_40]
ON [dbo].[ohd]
(
 [status_ID] , [found_station_ID] , [ohd_type] , [wt_status] , [wt_id] , [founddate] , [foundtime] 
)
INCLUDE (
 [OHD_ID], [holding_station_ID], [membership_ID], [record_locator], [agent_ID], [claimnum], [color], [bagarrivedate], [type], [xdescelement_ID_1], [xdescelement_ID_2], [xdescelement_ID_3], [manufacturer_ID], [manufacturer_other], [storage_location], [close_date], [lastupdated], [firstname], [lastname], [middlename], [disposal_status_id], [faultstation_ID], [loss_code], [earlyBag], [matched_incident], [warehouseSentDate], [warehouseReceivedDate]
)
WITH
(
PAD_INDEX = OFF,
FILLFACTOR = 100,
IGNORE_DUP_KEY = OFF,
STATISTICS_NORECOMPUTE = OFF,
ONLINE = OFF,
ALLOW_ROW_LOCKS = ON,
ALLOW_PAGE_LOCKS = ON
)
ON [PRIMARY];
GO


CREATE NONCLUSTERED INDEX [_dta_index_ohd_6_1954106002__K7_K20_K21_1_2_3_4_5_6_8_9_10_11_12_13_14_15_16_17_18_19_22_23_24_25_26_27_28_29_30_39_40]
ON [dbo].[ohd]
([founddate] , [lastupdated] , [status_ID])
INCLUDE (
 [OHD_ID], [found_station_ID], [holding_station_ID], [membership_ID], [record_locator], [agent_ID], [foundtime], [claimnum], [color], [bagarrivedate], [type], [xdescelement_ID_1], [xdescelement_ID_2], [xdescelement_ID_3], [manufacturer_ID], [manufacturer_other], [storage_location], [close_date], [firstname], [lastname], [middlename], [ohd_type], [disposal_status_id], [wt_id], [faultstation_ID], [loss_code], [wt_status], [warehouseSentDate], [warehouseReceivedDate] 
)
WITH
(
PAD_INDEX = OFF,
FILLFACTOR = 100,
IGNORE_DUP_KEY = OFF,
STATISTICS_NORECOMPUTE = OFF,
ONLINE = OFF,
ALLOW_ROW_LOCKS = ON,
ALLOW_PAGE_LOCKS = ON
)
ON [PRIMARY];
GO


CREATE NONCLUSTERED INDEX [_dta_index_ohd_6_1954106002__K9_K21_1_2_3_4_5_6_7_8_10_11_12_13_14_15_16_17_18_19_20_22_23_24_25_26_27_28_29_30_31_32_39_40]
ON [dbo].[ohd]
([claimnum] , [status_ID])
INCLUDE (
 [OHD_ID], [found_station_ID], [holding_station_ID], [membership_ID], [record_locator], [agent_ID], [founddate], [foundtime], [color], [bagarrivedate], [type], [xdescelement_ID_1], [xdescelement_ID_2], [xdescelement_ID_3], [manufacturer_ID], [manufacturer_other], [storage_location], [close_date], [lastupdated], [firstname], [lastname], [middlename], [ohd_type], [disposal_status_id], [wt_id], [faultstation_ID], [loss_code], [wt_status], [earlyBag], [matched_incident], [warehouseSentDate], [warehouseReceivedDate]
)
WITH
(
PAD_INDEX = OFF,
FILLFACTOR = 100,
IGNORE_DUP_KEY = OFF,
STATISTICS_NORECOMPUTE = OFF,
ONLINE = OFF,
ALLOW_ROW_LOCKS = ON,
ALLOW_PAGE_LOCKS = ON
)
ON [PRIMARY];
GO

insert into systemcomponents VALUES (704,'Assign Warehouse Dates','Allow to assign Warehouse Dates for OHDs',1,'',0,99,0);
create index warehouserecindex on ohd (warehouseReceivedDate, ohd_id);
create index warehousesentindex on ohd (warehouseSentDate, ohd_id);

alter table lost_found add category_id integer;
GO

update lost_found set category_id =17;

GO

create table salvage_remark (
	remark_id integer not null identity,
	agent_id integer not null,
	createtime datetime,
	remarktext varchar(max),
	remarktype tinyint,
	primary key (remark_id)
);

alter table salvage add remark_id integer;

GO

alter table incident add tracing_status_id tinyint;
alter table audit_incident add tracing_status_id tinyint;

insert into systemcomponents VALUES (705,'Edit Tracing Status','Allows a user to edit the Current Tracing Status of an Incident',6,'',0,99,0);

GO

update incident set tracing_status_id = 0;
update audit_incident set tracing_status_id = 0;


create table delivery_instructions (id int identity,instructions varchar(max), PRIMARY KEY (id));
alter table incident add deliveryInstructions_ID int;

alter table audit_incident add instructions varchar(max);
alter table bdo_passenger add hotelphone varchar(100);

alter table company_specific_variable add incident_lock_mins integer default 0;
alter table audit_company_specific_variable add incident_lock_mins integer default 0;
GO
update company_specific_variable set incident_lock_mins=0;
update audit_company_specific_variable set incident_lock_mins=0;

insert into properties (keyStr,valueStr) values ('lock.cache.cluster','lockfile-cache-production');



alter table address add  hotelphone varchar(50);


alter table fsclaim add claimRemark text;

update properties set valueStr='NTServices_1_0/ClaimClientBeanV2/remote' where keyStr='fraud.server.name';

create table FsIPAddress (
	id bigint not null identity,
	ipAddress varchar(20),
	claim_id bigint not null,
	primary key (id));

GO

alter table phone add association varchar(255);
alter table phone add claim_id integer;
alter table fsipaddress add association varchar(255);

GO
CREATE NONCLUSTERED INDEX [claim_id_index]
ON Phone
([claim_id])
WITH
(
PAD_INDEX = OFF,
FILLFACTOR = 100,
IGNORE_DUP_KEY = OFF,
STATISTICS_NORECOMPUTE = OFF,
ONLINE = OFF,
ALLOW_ROW_LOCKS = ON,
ALLOW_PAGE_LOCKS = ON
)
ON [PRIMARY];
GO

CREATE NONCLUSTERED INDEX [claim_id_index]
ON FsIPAddress
([claim_id])
WITH
(
PAD_INDEX = OFF,
FILLFACTOR = 100,
IGNORE_DUP_KEY = OFF,
STATISTICS_NORECOMPUTE = OFF,
ONLINE = OFF,
ALLOW_ROW_LOCKS = ON,
ALLOW_PAGE_LOCKS = ON
)
ON [PRIMARY];
GO


update reservation set ccNumber = '';

update properties set valueStr='NTServices_1_0/ClaimClientBeanV3/remote' where keyStr='fraud.server.name';
GO
insert into lfcategory (description ,score ,companycode) VALUES ('Laptop/Computer', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Camera', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Cell Phone', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Tablet/E-Reader', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Head Phones', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Wallet/Purse', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Currency/Gift Card/Credit 

Card', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Book', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Coat/Jacket', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Misc/ Electronic Items', 

0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Music', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Papers', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Jewelry', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Handheld Games', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Optics/Sunglasses', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Keys', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('OTHER', 0,'AD');
GO
