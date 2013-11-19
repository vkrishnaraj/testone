
--3.2.1.2
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

insert into status (status_id, description, table_ID) values (96,'paused',13);
insert into status (status_id, description, table_ID) values (97,'working',13);

insert into status (Status_ID,description,table_ID) VALUES ( 98,'VIP Pickup',9);

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

--3.3
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

-- END ALREADY RAN FOR DEV
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

insert into properties (keyStr,valueStr) values ('lock.cache.cluster','lockfile-cache-testing');


--3.3.1
alter table address add  hotelphone varchar(50);

--3.4.
alter table fsclaim add claimRemark text;

update properties set valueStr='NTServices_1_0/ClaimClientBeanV2/remote' where keyStr='fraud.server.name';

create table FsIPAddress (
	id bigint not null identity,
	ipAddress varchar(20),
	claim_id bigint not null,
	primary key (id));


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

--3.4.1

--3.4.2
update reservation set ccNumber = '';

update properties set valueStr='NTServices_1_0/ClaimClientBeanV3/remote' where keyStr='fraud.server.name';