
DROP STATISTICS address._dta_stat_706101556_20_16;
alter table address drop constraint DF__address__is_perm__3CBFCCAB;
alter table address alter column is_permanent tinyint;
alter table address alter column addresstype integer; 
GO
ALTER TABLE address ADD DEFAULT ((0)) FOR is_permanent;
GO
CREATE STATISTICS [_dta_stat_706101556_20_16] ON address([is_permanent], [passenger_ID])
GO

alter table audit_claim alter column Claim_ID bigint;
GO
ALTER TABLE audit_claim ADD DEFAULT ((0)) FOR Claim_ID;

alter table audit_ohd_inventory alter column OHD_Inventory_ID bigint;
GO
ALTER TABLE audit_ohd_inventory ADD DEFAULT ((0)) FOR OHD_Inventory_ID;

alter table bdo drop constraint DF__bdo__canceled__7A7D0802;
alter table bdo alter column canceled bit;
GO
ALTER TABLE bdo ADD DEFAULT ((0)) FOR canceled;

ALTER TABLE currency drop CONSTRAINT PK_currency;
alter table currency alter column currency_id varchar(3) not null;
ALTER TABLE currency add constraint PK_currency primary key (currency_id);
alter table currency alter column description varchar(20);


alter table expensepayout alter column bdo_id integer;
alter table expensepayout alter column mileageamt integer;
GO
ALTER TABLE expensepayout ADD DEFAULT ((0)) FOR mileageamt;
alter table expensepayout alter column expensetype_ID integer;
alter table expensepayout alter column status_ID integer;
alter table expensepayout alter column agent_ID integer;
alter table expensepayout alter column station_ID integer;

alter table incident_range alter column companycode_id varchar(3);

DROP INDEX [_dta_index_agent_6_1125579048__K9_K8_K17_K16_K18_K1_2_3_4_5_6_7_10_11_12_13_14_15_19_20_21_22_23_24_25_26_27_28_29] ON agent;//my have to manullay drop index
alter table agent alter column is_wt_user integer;
alter table agent alter column is_online integer;
GO
ALTER TABLE agent ADD DEFAULT ((0)) FOR is_wt_user;
ALTER TABLE agent ADD DEFAULT ((0)) FOR is_online;
CREATE NONCLUSTERED INDEX _dta_index_agent_6_1125579048__K9_K8_K17_K16_K18_K1_2_3_4_5_6_7_10_11_12_13_14_15_19_20_21_22_23_24_25_26_27_28_29 ON agent 
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


drop index _dta_index_incident_6_2050106344__K4_K10_K3_K1_2_5_6_7_8_9_11_12_13_14_15_16_17_18_19_20_21_22_23_24_25_26_27_28_29_30_31 on incident;
DROP INDEX [_dta_index_incident_6_2050106344__K29_1_2_3_4_5_6_7_8_9_10_11_12_13_14_15

_16_17_18_19_20_21_22_23_24_25_26_27_28_30_31] ON incident;

GO
DROP INDEX [_dta_index_incident_6_2050106344__K2_K10_K1_3_4_5_6_7_8_9_11_12_13_14_15_

16_17_18_19_20_21_22_23_24_25_26_27_28_29_30_31] ON incident;

GO
alter table incident alter column tracing_status_id integer;
alter table incident alter column customcleared integer;
GO
ALTER TABLE incident ADD DEFAULT ((0)) FOR tracing_status_id;
ALTER TABLE incident ADD DEFAULT ((0)) FOR customcleared;
alter table incident alter column courtesyreport integer;
alter table incident alter column tsachecked integer;
alter table incident alter column voluntaryseparation integer;
alter table incident alter column nonrevenue integer;
alter table incident alter column numpassengers integer;
alter table incident alter column numbagchecked integer;
alter table incident alter column numbagreceived integer;
alter table incident alter column reportmethod integer;
CREATE NONCLUSTERED INDEX [_dta_index_incident_6_2050106344__K4_K10_K3_K1_2_5_6_7_8_9_11_12_13_14_15_16_17_18_19_20_21_22_23_24_25_26_27_28_29_30_31] ON incident
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
CREATE NONCLUSTERED INDEX [_dta_index_incident_6_2050106344__K29_1_2_3_4_5_6_7_8_9_10_11_12_13_14_15_16_17_18_19_20_21_22_23_24_25_26_27_28_30_31] ON incident 
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
CREATE NONCLUSTERED INDEX [_dta_index_incident_6_2050106344__K2_K10_K1_3_4_5_6_7_8_9_11_12_13_14_15_16_17_18_19_20_21_22_23_24_25_26_27_28_29_30_31] ON incident
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

alter table passenger alter column salutation integer;
alter table passenger alter column isprimary integer;

alter table item alter column lvlofdamage integer;
alter table item alter column xdescelement_ID_1 integer;
alter table item alter column xdescelement_ID_2 integer;
alter table item alter column xdescelement_ID_3 integer;

drop index remarktype_ind on remark;
alter table remark drop constraint DF__remark__remarkty__5B4453CB;
alter table remark alter column remarktype integer;
GO
ALTER TABLE remark ADD DEFAULT ((1)) FOR remarktype;
CREATE NONCLUSTERED INDEX [remarktype_ind] ON remark
(
	[remarktype] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO

alter table itinerary alter column itinerarytype integer;
alter table itinerary alter column legfrom_type integer;
alter table itinerary alter column legto_type integer;
GO
ALTER TABLE itinerary ADD DEFAULT ((0)) FOR itinerarytype;
ALTER TABLE itinerary ADD DEFAULT ((0)) FOR legfrom_type;
ALTER TABLE itinerary ADD DEFAULT ((0)) FOR legto_type;

alter table company_specific_variable drop constraint DF__company_s__wt_en__019E3B86;
alter table company_specific_variable drop constraint DF__company_s__wt_en__60FD2D21;
drop index _dta_index_company_specific_variable_6_178099675__K2_K1_3_4_5_6_7_8_9_10_11_12_13_14_15_16_17_18_19_20_21_22_23_25_26_27_28_29_ on company_specific_variable;
alter table company_specific_variable alter column webs_enabled integer;
alter table company_specific_variable alter column seconds_wait integer;
alter table company_specific_variable alter column wt_enabled integer;  
alter table company_specific_variable alter column wt_write_enabled integer; 
alter table company_specific_variable alter column oal_ohd_hours integer not null; 
alter table company_specific_variable alter column oal_inc_hours integer not null;
GO
ALTER TABLE company_specific_variable ADD DEFAULT ((0)) FOR seconds_wait;
ALTER TABLE company_specific_variable ADD DEFAULT ((0)) FOR wt_enabled;
ALTER TABLE company_specific_variable ADD DEFAULT ((0)) FOR wt_write_enabled;
ALTER TABLE company_specific_variable ADD DEFAULT ((0)) FOR oal_ohd_hours;
ALTER TABLE company_specific_variable ADD DEFAULT ((0)) FOR oal_inc_hours;
CREATE NONCLUSTERED INDEX [_dta_index_company_specific_variable_6_178099675__K2_K1_3_4_5_6_7_8_9_10_11_12_13_14_15_16_17_18_19_20_21_22_23_25_26_27_28_29_] ON company_specific_variable
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
ALTER TABLE audit_ohd ADD DEFAULT ((0)) FOR ohd_type;

alter table audit_ohd_itinerary alter column itinerarytype integer;
alter table audit_ohd_itinerary alter column legfrom_type integer;
alter table audit_ohd_itinerary alter column legto_type integer;
GO
ALTER TABLE audit_ohd_itinerary ADD DEFAULT ((0)) FOR itinerarytype;
ALTER TABLE audit_ohd_itinerary ADD DEFAULT ((0)) FOR legfrom_type;
ALTER TABLE audit_ohd_itinerary ADD DEFAULT ((0)) FOR legto_type;

alter table audit_ohd_passenger alter column isprimary integer not null;
GO
ALTER TABLE audit_ohd_passenger ADD DEFAULT ((0)) FOR isprimary;

alter table audit_ohd_address alter column addresstype integer;

DROP STATISTICS incident_assoc._dta_stat_818101955_3_4;
DROP STATISTICS incident_assoc._dta_stat_818101955_2_3_4;
DROP STATISTICS incident_assoc._dta_stat_818101955_1_3_4;
DROP STATISTICS incident_assoc._dta_stat_818101955_1_2_3_4;
DROP INDEX [_dta_index_incident_assoc_6_818101955__K3_1_2_4] ON incident_assoc;
GO
alter table incident_assoc alter column itemtype_ID integer;
CREATE STATISTICS [_dta_stat_818101955_3_4] ON incident_assoc([incident_ID], [itemtype_ID]);
CREATE STATISTICS [_dta_stat_818101955_2_3_4] ON [dbo].[incident_assoc]([assoc_ID], [incident_ID], [itemtype_ID]);
CREATE STATISTICS [_dta_stat_818101955_1_3_4] ON [dbo].[incident_assoc]([ID], [incident_ID], [itemtype_ID]);
CREATE STATISTICS [_dta_stat_818101955_1_2_3_4] ON [dbo].[incident_assoc]([ID], [assoc_ID], [incident_ID], [itemtype_ID]);
CREATE NONCLUSTERED INDEX [_dta_index_incident_assoc_6_818101955__K3_1_2_4] ON incident_assoc
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
ALTER TABLE audit_incident ADD DEFAULT ((0)) FOR customcleared;
ALTER TABLE audit_incident ADD DEFAULT ((0)) FOR tracing_status_id;

alter table audit_remark alter column remarktype integer;
GO
ALTER TABLE audit_remark ADD DEFAULT ((1)) FOR remarktype;

alter table audit_agent alter column is_online integer; 
alter table audit_agent alter column is_wt_user integer; 
GO
ALTER TABLE audit_agent ADD DEFAULT ((0)) FOR is_online;
ALTER TABLE audit_agent ADD DEFAULT ((0)) FOR is_wt_user;

alter table claimprorate alter column pir_attached integer;
alter table claimprorate alter column claim_attached integer;
alter table claimprorate alter column confirmpayment_attached integer;
alter table claimprorate alter column all_prorate integer;
alter table claimprorate alter column remit integer;
alter table claimprorate alter column clearing_bill integer;
