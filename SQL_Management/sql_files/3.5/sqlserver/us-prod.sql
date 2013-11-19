alter table fsclaim add claimRemark varchar(MAX);
GO

update properties set valueStr='NTServices_1_0/ClaimClientBeanV2/remote' where keyStr='fraud.server.name';
GO

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


alter table fsclaim add createagent_id integer;

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

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (630,'Maintain Subcompanies','Maintain Subcompanies' ,39,'subCompAdmin.do',1,3   ,0);

insert into group_component_policy (component_id,group_id) VALUES (630,1);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
VALUES (801,'Fraud Forum Search','Fraud Forum Search Page',59,'fraud_forum_search.do',1,98,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
VALUES (802,'Fraud Forum Create Thread','Fraud Forum Create Page',59,'fraud_forum_create.do',1,99,0);

alter table audit_address alter column zip varchar(11);
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

SET IDENTITY_INSERT status ON;
insert into status (Status_ID, description,table_ID) VALUES (613,'Staged for Shipping',17);
SET IDENTITY_INSERT status OFF;

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


update properties set valuestr='production' where keystr='lock.cache.cluster';



update properties set valuestr = 'english,mandarin,hindi,spanish,russian,bengali,portuguese,german,french,japanese,korean,cantonese,vietnamese,hebrew,italian,dutch,other' where keystr = 'spoken.language.list';


update properties set valueStr = 'http://bagweb/ws/ScanPoints4' where keyStr = 'scan.history.endpoint';

update properties set valueStr = '1' where keyStr = 'convert.bagtag';

update properties set valuestr='ejb:ntfs_internal_test/nt_fs//ClaimClientBeanV3!aero.nettracer.selfservice.fraud.client.ClaimClientRemote' where keystr='fraud.server.name';
update properties set valuestr='ejb:ntfs_internal_test/nt_fs//PrivacyPermissionsBean!aero.nettracer.selfservice.fraud.PrivacyPermissionsRemote' where keystr='fraud.permissions.server.name';
insert into properties (keyStr, valueStr) values ('dup.claim.cache.expire.time', '4');
GO

alter table oc_claim add bagReceivedDate datetime;
alter table oc_content add quantity integer default 0;
GO
update oc_content set quantity=0;

alter table oc_claim add foreignCurrencyEmail varchar(100);
alter table oc_claim add requestForeignCurrency integer default 0;
GO
update oc_claim set requestForeignCurrency = 0;



DROP STATISTICS address._dta_stat_706101556_20_16;
GO
alter table address drop constraint DF_address_is_permanent;
alter table address alter column is_permanent tinyint;
alter table address alter column addresstype integer; 
GO
ALTER TABLE [address] ADD  CONSTRAINT [DF_address_is_permanent]  DEFAULT ((0)) FOR [is_permanent]
GO
CREATE STATISTICS [_dta_stat_706101556_20_16] ON address([is_permanent], [passenger_ID])
GO

alter table audit_claim alter column Claim_ID bigint;
GO

alter table audit_ohd_inventory alter column OHD_Inventory_ID bigint;
GO

alter table bdo drop constraint DF__bdo__canceled__7A7D0802;
GO
alter table bdo alter column canceled bit;
GO
ALTER TABLE [bdo] ADD  DEFAULT ((0)) FOR [canceled]
GO

ALTER TABLE currency drop CONSTRAINT PK_currency;
GO
alter table currency alter column currency_id varchar(3) not null;
ALTER TABLE currency add constraint PK_currency primary key (currency_id);
GO
alter table currency alter column description varchar(20);
GO

DROP STATISTICS expensepayout._dta_stat_1922105888_1_17_16_13;
GO
DROP STATISTICS expensepayout._dta_stat_1922105888_1_23_17;
GO
DROP STATISTICS expensepayout._dta_stat_1922105888_1_24_17_16_13;
GO
DROP STATISTICS expensepayout._dta_stat_1922105888_13_24_1_17;
GO
DROP STATISTICS expensepayout._dta_stat_1922105888_14_17_16_13_15_23_3;
GO
DROP STATISTICS expensepayout._dta_stat_1922105888_16_23_1_17_13;
GO
DROP STATISTICS expensepayout._dta_stat_1922105888_16_24_1;
GO
DROP STATISTICS expensepayout._dta_stat_1922105888_23_14_17_16_13;
GO
DROP STATISTICS expensepayout._dta_stat_1922105888_3_23;
GO
DROP STATISTICS expensepayout._dta_stat_1922105888_7_13_15;
GO

DROP INDEX [_dta_index_expensepayout_5_1922105888__K13_K15_16] ON expensepayout;
GO
DROP INDEX [_dta_index_expensepayout_5_1922105888__K15_K16] ON expensepayout;
GO
DROP INDEX [bdo_index] ON expensepayout;
GO

ALTER TABLE expensepayout drop CONSTRAINT DF__expensepa__bdo_i__10E14A6D;
GO

alter table expensepayout alter column bdo_id integer;
alter table expensepayout alter column mileageamt integer;
GO
ALTER TABLE expensepayout ADD DEFAULT ((0)) FOR mileageamt;
GO
alter table expensepayout alter column expensetype_ID integer;
alter table expensepayout alter column status_ID integer;
alter table expensepayout alter column agent_ID integer;
alter table expensepayout alter column station_ID integer;
GO

ALTER TABLE [expensepayout] ADD  DEFAULT (NULL) FOR [bdo_id]
GO

CREATE NONCLUSTERED INDEX [_dta_index_expensepayout_5_1922105888__K13_K15_16] ON [expensepayout] 
(
	[expenselocation_ID] ASC,
	[status_ID] ASC
)
INCLUDE ( [agent_ID]) WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [_dta_index_expensepayout_5_1922105888__K15_K16] ON [expensepayout] 
(
	[status_ID] ASC,
	[agent_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [bdo_index] ON [expensepayout] 
(
	[bdo_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 100) ON [PRIMARY]
GO

CREATE STATISTICS [_dta_stat_1922105888_1_17_16_13] ON [expensepayout]([Expensepayout_ID], [station_ID], [agent_ID], [expenselocation_ID])
GO
CREATE STATISTICS [_dta_stat_1922105888_1_23_17] ON [expensepayout]([Expensepayout_ID], [incident_ID], [station_ID])
GO
CREATE STATISTICS [_dta_stat_1922105888_1_24_17_16_13] ON [expensepayout]([Expensepayout_ID], [bdo_id], [station_ID], [agent_ID], [expenselocation_ID])
GO
CREATE STATISTICS [_dta_stat_1922105888_13_24_1_17] ON [expensepayout]([expenselocation_ID], [bdo_id], [Expensepayout_ID], [station_ID])
GO
CREATE STATISTICS [_dta_stat_1922105888_14_17_16_13_15_23_3] ON [expensepayout]([expensetype_ID], [station_ID], [agent_ID], [expenselocation_ID], [status_ID], [incident_ID], [createdate])
GO
CREATE STATISTICS [_dta_stat_1922105888_16_23_1_17_13] ON [expensepayout]([agent_ID], [incident_ID], [Expensepayout_ID], [station_ID], [expenselocation_ID])
GO
CREATE STATISTICS [_dta_stat_1922105888_16_24_1] ON [expensepayout]([agent_ID], [bdo_id], [Expensepayout_ID])
GO
CREATE STATISTICS [_dta_stat_1922105888_23_14_17_16_13] ON [expensepayout]([incident_ID], [expensetype_ID], [station_ID], [agent_ID], [expenselocation_ID])
GO
CREATE STATISTICS [_dta_stat_1922105888_3_23] ON [expensepayout]([createdate], [incident_ID])
GO
CREATE STATISTICS [_dta_stat_1922105888_7_13_15] ON [expensepayout]([currency_ID], [expenselocation_ID], [status_ID])
GO


alter table incident_range alter column companycode_id varchar(3);
GO

DROP INDEX [_dta_index_agent_6_1125579048__K9_K8_K17_K16_K18_K1_2_3_4_5_6_7_10_11_12_

13_14_15_19_20_21_22_23_24_25_26_27_28_29] ON agent;
GO
ALTER TABLE agent drop CONSTRAINT DF__agent__is_wt_use__47DBAE45;
GO
alter table agent alter column is_wt_user integer;
alter table agent alter column is_online integer;
GO
ALTER TABLE [agent] ADD  DEFAULT ((0)) FOR [is_wt_user]
GO

CREATE NONCLUSTERED INDEX [_dta_index_agent_6_1125579048__K9_K8_K17_K16_K18_K1_2_3_4_5_6_7_10_11_12_

13_14_15_19_20_21_22_23_24_25_26_27_28_29] ON [agent] 
(
	[station_ID] ASC,
	[active] ASC,
	[timeformat_ID] ASC,
	[dateformat_ID] ASC,
	[shift_id] ASC,
	[Agent_ID] ASC
)
INCLUDE ( [firstname],
[mname],
[lastname],
[timeout],
[username],
[password],
[companycode_ID],
[defaultlocale],
[currentlocale],
[defaultcurrency],
[defaulttimezone],
[currenttimezone],
[usergroup_id],
[last_logged_on],
[is_online],
[last_pass_reset_date],
[ws_enabled],
[max_ws_sessions],
[web_enabled],
[is_wt_user],
[reset_password],
[account_locked],
[failed_logins]) WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO


DROP INDEX [_dta_index_incident_6_2050106344__K4_K10_K3_K1_2_5_6_7_8_9_11_12_13_14_15_16_17_18_19_20_21_22_23_24_25_26_27_28_29_30_31] ON incident;
GO
DROP INDEX [_dta_index_incident_6_2050106344__K29_1_2_3_4_5_6_7_8_9_10_11_12_13_14_15

_16_17_18_19_20_21_22_23_24_25_26_27_28_30_31] ON incident;
GO
DROP INDEX [_dta_index_incident_6_2050106344__K2_K10_K1_3_4_5_6_7_8_9_11_12_13_14_15_

16_17_18_19_20_21_22_23_24_25_26_27_28_29_30_31] ON incident;
GO
DROP INDEX [_dta_index_incident_5_2050106344__K4_K1_K3_K10_2_5_6_7_8_9_11_12_13_14_15_16_17_18_19_20_21_22_23_24_25_26_27_28_29_30_31_33_] ON incident;
GO
DROP INDEX [_dta_index_incident_5_2050106344__K3_K1_2_4_5_6_7_8_9_10_11_12_13_14_15_16_17_18_19_20_21_22_23_24_25_26_27_28_29_30_31_33_34_] ON incident;
GO
DROP INDEX [_dta_index_incident_5_2050106344__K24_K25_K2_K1_K10_K3_K4_5_6_7_8_9_11_12_13_14_15_16_17_18_19_20_21_22_23_26_27_28_29_30_31_] ON incident;
GO

alter table incident alter column tracing_status_id integer;
alter table incident alter column customcleared integer;
alter table incident alter column courtesyreport integer;
alter table incident alter column tsachecked integer;
alter table incident alter column voluntaryseparation integer;
alter table incident alter column nonrevenue integer;
alter table incident alter column numpassengers integer;
alter table incident alter column numbagchecked integer;
alter table incident alter column numbagreceived integer;
alter table incident alter column reportmethod integer;
GO

CREATE NONCLUSTERED INDEX [_dta_index_incident_6_2050106344__K4_K10_K3_K1_2_5_6_7_8_9_11_12_13_14_15_16_17_18_19_20_21_22_23_24_25_26_27_28_29_30_31] ON [incident] 
(
	[stationassigned_ID] ASC,
	[status_ID] ASC,
	[stationcreated_ID] ASC,
	[Incident_ID] ASC
)
INCLUDE ( [itemtype_ID],
[faultstation_ID],
[loss_code],
[agent_ID],
[recordlocator],
[manualreportnum],
[ticketnumber],
[reportmethod],
[checkedlocation],
[numpassengers],
[numbagchecked],
[numbagreceived],
[voluntaryseparation],
[courtesyreport],
[tsachecked],
[customcleared],
[nonrevenue],
[lastupdated],
[ohd_lasttraced],
[createdate],
[createtime],
[close_date],
[version],
[agentassigned_ID],
[wt_id],
[printedreceipt],
[wt_status],
[osi_id],
[language],
[checklist_version],
[overall_weight],
[overall_weight_unit]) WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO


CREATE NONCLUSTERED INDEX [_dta_index_incident_6_2050106344__K29_1_2_3_4_5_6_7_8_9_10_11_12_13_14_15

_16_17_18_19_20_21_22_23_24_25_26_27_28_30_31] ON [incident] 
(
	[wt_id] ASC
)
INCLUDE ( [Incident_ID],
[itemtype_ID],
[stationcreated_ID],
[stationassigned_ID],
[faultstation_ID],
[loss_code],
[agent_ID],
[recordlocator],
[manualreportnum],
[status_ID],
[ticketnumber],
[reportmethod],
[checkedlocation],
[numpassengers],
[numbagchecked],
[numbagreceived],
[voluntaryseparation],
[courtesyreport],
[tsachecked],
[customcleared],
[nonrevenue],
[lastupdated],
[ohd_lasttraced],
[createdate],
[createtime],
[close_date],
[version],
[agentassigned_ID],
[printedreceipt],
[wt_status]) WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO


CREATE NONCLUSTERED INDEX [_dta_index_incident_6_2050106344__K2_K10_K1_3_4_5_6_7_8_9_11_12_13_14_15_

16_17_18_19_20_21_22_23_24_25_26_27_28_29_30_31] ON [incident] 
(
	[itemtype_ID] ASC,
	[status_ID] ASC,
	[Incident_ID] ASC
)
INCLUDE ( [stationcreated_ID],
[stationassigned_ID],
[faultstation_ID],
[loss_code],
[agent_ID],
[recordlocator],
[manualreportnum],
[ticketnumber],
[reportmethod],
[checkedlocation],
[numpassengers],
[numbagchecked],
[numbagreceived],
[voluntaryseparation],
[courtesyreport],
[tsachecked],
[customcleared],
[nonrevenue],
[lastupdated],
[ohd_lasttraced],
[createdate],
[createtime],
[close_date],
[version],
[agentassigned_ID],
[wt_id],
[printedreceipt],
[wt_status]) WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [_dta_index_incident_5_2050106344__K4_K1_K3_K10_2_5_6_7_8_9_11_12_13_14_15_16_17_18_19_20_21_22_23_24_25_26_27_28_29_30_31_33_] ON [incident] 
(
	[stationassigned_ID] ASC,
	[Incident_ID] ASC,
	[stationcreated_ID] ASC,
	[status_ID] ASC
)
INCLUDE ( [itemtype_ID],
[faultstation_ID],
[loss_code],
[agent_ID],
[recordlocator],
[manualreportnum],
[ticketnumber],
[reportmethod],
[checkedlocation],
[numpassengers],
[numbagchecked],
[numbagreceived],
[voluntaryseparation],
[courtesyreport],
[tsachecked],
[customcleared],
[nonrevenue],
[lastupdated],
[ohd_lasttraced],
[createdate],
[createtime],
[close_date],
[version],
[agentassigned_ID],
[wt_id],
[printedreceipt],
[wt_status],
[language],
[checklist_version],
[overall_weight],
[overall_weight_unit],
[locked],
[oc_claim_id],
[revenue_code]) WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [_dta_index_incident_5_2050106344__K3_K1_2_4_5_6_7_8_9_10_11_12_13_14_15_16_17_18_19_20_21_22_23_24_25_26_27_28_29_30_31_33_34_] ON [incident] 
(
	[stationcreated_ID] ASC,
	[Incident_ID] ASC
)
INCLUDE ( [itemtype_ID],
[stationassigned_ID],
[faultstation_ID],
[loss_code],
[agent_ID],
[recordlocator],
[manualreportnum],
[status_ID],
[ticketnumber],
[reportmethod],
[checkedlocation],
[numpassengers],
[numbagchecked],
[numbagreceived],
[voluntaryseparation],
[courtesyreport],
[tsachecked],
[customcleared],
[nonrevenue],
[lastupdated],
[ohd_lasttraced],
[createdate],
[createtime],
[close_date],
[version],
[agentassigned_ID],
[wt_id],
[printedreceipt],
[wt_status],
[language],
[checklist_version],
[overall_weight],
[overall_weight_unit],
[locked],
[oc_claim_id],
[revenue_code]) WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [_dta_index_incident_5_2050106344__K24_K25_K2_K1_K10_K3_K4_5_6_7_8_9_11_12_13_14_15_16_17_18_19_20_21_22_23_26_27_28_29_30_31_] ON [incident] 
(
	[createdate] ASC,
	[createtime] ASC,
	[itemtype_ID] ASC,
	[Incident_ID] ASC,
	[status_ID] ASC,
	[stationcreated_ID] ASC,
	[stationassigned_ID] ASC
)
INCLUDE ( [faultstation_ID],
[loss_code],
[agent_ID],
[recordlocator],
[manualreportnum],
[ticketnumber],
[reportmethod],
[checkedlocation],
[numpassengers],
[numbagchecked],
[numbagreceived],
[voluntaryseparation],
[courtesyreport],
[tsachecked],
[customcleared],
[nonrevenue],
[lastupdated],
[ohd_lasttraced],
[close_date],
[version],
[agentassigned_ID],
[wt_id],
[printedreceipt],
[wt_status],
[language],
[checklist_version],
[overall_weight],
[overall_weight_unit],
[locked],
[oc_claim_id],
[revenue_code]) WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO

DROP INDEX [_dta_index_passenger_6_562101043__K1_K2] ON [passenger];
GO

alter table passenger alter column salutation integer;
GO
alter table passenger alter column isprimary integer;
GO

CREATE NONCLUSTERED INDEX [_dta_index_passenger_6_562101043__K1_K2_3_4_5_6_7_8_9_10_11_12_13_14_15_15_16]
ON [passenger]
(
 [Passenger_ID] , [incident_ID] 
)
INCLUDE (
 [membership_ID] , [jobtitle] , [salutation] , [firstname] , [middlename] , [lastname] , [commonnum] , [countryofissue] , [dlstate] , [driverslicense] , [isprimary] , [numRonKitsIssued] , [languageKey] , [languageFreeFlow]
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


alter table item alter column lvlofdamage integer;
alter table item alter column xdescelement_ID_1 integer;
alter table item alter column xdescelement_ID_2 integer;
alter table item alter column xdescelement_ID_3 integer;
GO

alter table remark alter column remarktype integer;
GO



alter table itinerary alter column itinerarytype integer;
alter table itinerary alter column legfrom_type integer;
alter table itinerary alter column legto_type integer;
GO


alter table company_specific_variable drop constraint DF__company_s__wt_en__019E3B86;
GO
alter table company_specific_variable drop constraint DF__company_s__webs___10566F31;
GO
alter table company_specific_variable drop constraint DF__company_s__wt_wr__02925FBF;
GO
alter table company_specific_variable drop constraint DF__company_s__oal_o__54968AE5;
GO
alter table company_specific_variable drop constraint DF__company_s__oal_i__558AAF1E;
GO
drop index [_dta_index_company_specific_variable_6_178099675__K2_K1_3_4_5_6_7_8_9_10_11_12_13_14_15_16_17_18_19_20_21_22_23_25_26_27_28_29_] on company_specific_variable;
GO
alter table company_specific_variable alter column webs_enabled integer;
alter table company_specific_variable alter column seconds_wait integer;
alter table company_specific_variable alter column wt_enabled integer;  
alter table company_specific_variable alter column wt_write_enabled integer; 
alter table company_specific_variable alter column oal_ohd_hours integer not null; 
alter table company_specific_variable alter column oal_inc_hours integer not null;
GO
ALTER TABLE [company_specific_variable] ADD  DEFAULT ('0') FOR [wt_enabled]
GO
ALTER TABLE [company_specific_variable] ADD  DEFAULT ((0)) FOR [webs_enabled]
GO
ALTER TABLE [company_specific_variable] ADD  DEFAULT ('0') FOR [wt_write_enabled]
GO
ALTER TABLE [company_specific_variable] ADD  DEFAULT ((0)) FOR [oal_ohd_hours]
GO
ALTER TABLE [company_specific_variable] ADD  DEFAULT ((0)) FOR [oal_inc_hours]
GO

CREATE NONCLUSTERED INDEX [_dta_index_company_specific_variable_6_178099675__K2_K1_3_4_5_6_7_8_9_10_11_12_13_14_15_16_17_18_19_20_21_22_23_25_26_27_28_29_] ON [company_specific_variable] 
(
	[companycode_ID] ASC,
	[ID] ASC
)
INCLUDE ( [total_threads],
[seconds_wait],
[MIN_MATCH_PERCENT],
[mbr_to_lz_days],
[ohd_to_lz_days],
[report_method],
[default_station_code],
[email_customer],
[email_host],
[email_port],
[email_from],
[email_to],
[audit_ohd],
[audit_lost_found],
[audit_lost_delayed],
[audit_damaged],
[audit_missing_articles],
[audit_agent],
[audit_group],
[audit_company],
[audit_shift],
[audit_station],
[audit_loss_codes],
[audit_claims],
[audit_airport],
[min_interim_approval_check],
[min_interim_approval_voucher],
[min_interim_approval_miles],
[max_image_file_size],
[pass_expire_days],
[damaged_to_lz_days],
[miss_to_lz_days],
[default_loss_code],
[audit_delivery_companies],
[webs_enabled],
[max_failed_logins],
[secure_password],
[mbr_to_wt_days],
[ohd_to_wt_hours],
[wt_user],
[wt_pass],
[ohd_lz],
[lz_mode],
[bak_nttracer_data_days],
[bak_nttracer_ohd_data_days],
[bak_nttracer_lostfound_data_days],
[retrieve_actionfile_interval],
[wt_url],
[wt_airlinecode],
[wt_enabled],
[wt_write_enabled],
[scannerDefaultBack],
[scannerDefaultForward],
[blind_cc],
[oal_ohd_hours],
[oal_inc_hours],
[auto_wt_amend],
[audit_permission],
[min_interim_approval_incidental],
[min_interim_approval_cc_refund],
[autocloseohd]) WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO

alter table audit_ohd alter column xdescelement_ID_1 integer;
alter table audit_ohd alter column xdescelement_ID_2 integer;
alter table audit_ohd alter column xdescelement_ID_3 integer;
alter table audit_ohd alter column ohd_type integer;
GO

alter table audit_ohd_itinerary alter column itinerarytype integer;
alter table audit_ohd_itinerary alter column legfrom_type integer;
alter table audit_ohd_itinerary alter column legto_type integer;
GO

alter table audit_ohd_passenger alter column isprimary integer not null;
GO

alter table audit_ohd_address alter column addresstype integer;
GO

DROP STATISTICS incident_assoc._dta_stat_818101955_1_2_3_4;
DROP STATISTICS incident_assoc._dta_stat_818101955_1_3_4;
DROP STATISTICS incident_assoc._dta_stat_818101955_2_1;
DROP STATISTICS incident_assoc._dta_stat_818101955_2_3_4;
DROP STATISTICS incident_assoc._dta_stat_818101955_3_4;
DROP INDEX [_dta_index_incident_assoc_6_818101955__K3_1_2_4] ON incident_assoc;
GO
alter table incident_assoc alter column itemtype_ID integer;
CREATE STATISTICS [_dta_stat_818101955_1_2_3_4] ON [incident_assoc]([ID], [assoc_ID], [incident_ID], [itemtype_ID])
GO
CREATE STATISTICS [_dta_stat_818101955_1_3_4] ON [incident_assoc]([ID], [incident_ID], [itemtype_ID])
GO
CREATE STATISTICS [_dta_stat_818101955_2_1] ON [incident_assoc]([assoc_ID], [ID])
GO
CREATE STATISTICS [_dta_stat_818101955_2_3_4] ON [incident_assoc]([assoc_ID], [incident_ID], [itemtype_ID])
GO
CREATE STATISTICS [_dta_stat_818101955_3_4] ON [incident_assoc]([incident_ID], [itemtype_ID])
GO
CREATE NONCLUSTERED INDEX [_dta_index_incident_assoc_6_818101955__K3_1_2_4] ON [incident_assoc] 
(
	[incident_ID] ASC
)
INCLUDE ( [ID],
[assoc_ID],
[itemtype_ID]) WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO

alter table audit_incident alter column reportmethod integer;
alter table audit_incident alter column numpassengers integer;
alter table audit_incident alter column numbagchecked integer;
alter table audit_incident alter column numbagreceived integer;
alter table audit_incident alter column voluntaryseparation integer;
alter table audit_incident alter column courtesyreport integer;
alter table audit_incident alter column tsachecked integer;
alter table audit_incident alter column nonrevenue integer;
alter table audit_incident alter column checkedlocation varchar(1);
alter table audit_incident alter column customcleared integer;
alter table audit_incident alter column tracing_status_id integer;
GO

alter table audit_remark alter column remarktype integer;
GO



alter table audit_agent drop constraint DF__audit_age__is_wt__607251E5;
GO
alter table audit_agent alter column is_online integer; 
alter table audit_agent alter column is_wt_user integer; 
GO
ALTER TABLE [audit_agent] ADD  DEFAULT ((0)) FOR [is_wt_user]
GO

alter table claimprorate alter column pir_attached integer;
alter table claimprorate alter column claim_attached integer;
alter table claimprorate alter column confirmpayment_attached integer;
alter table claimprorate alter column all_prorate integer;
alter table claimprorate alter column remit integer;
alter table claimprorate alter column clearing_bill integer;
GO
