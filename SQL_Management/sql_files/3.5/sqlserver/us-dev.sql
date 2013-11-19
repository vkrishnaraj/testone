

alter table address drop constraint DF_address_is_permanent;
alter table address alter column is_permanent tinyint;
alter table address alter column addresstype integer; 
GO
ALTER TABLE address ADD DEFAULT ((0)) FOR is_permanent;
GO

alter table audit_claim alter column Claim_ID bigint;
GO
ALTER TABLE audit_claim ADD DEFAULT ((0)) FOR Claim_ID;

alter table audit_ohd_inventory alter column OHD_Inventory_ID bigint;
GO
ALTER TABLE audit_ohd_inventory ADD DEFAULT ((0)) FOR OHD_Inventory_ID;

alter table bdo drop constraint DF__bdo__canceled__5C02A283;
alter table bdo alter column canceled bit;
GO
ALTER TABLE bdo ADD DEFAULT ((0)) FOR canceled;

ALTER TABLE currency drop CONSTRAINT PK_currency;
alter table currency alter column currency_id varchar(3) not null;
ALTER TABLE currency add constraint PK_currency primary key (currency_id);
alter table currency alter column description varchar(20);

alter table expensepayout drop CONSTRAINT DF__expensepa__bdo_i__75F77EB0;
alter table expensepayout alter column bdo_id integer;
alter table expensepayout alter column mileageamt integer;
GO
ALTER TABLE expensepayout ADD DEFAULT ((0)) FOR mileageamt;
alter table expensepayout alter column expensetype_ID integer;
alter table expensepayout alter column status_ID integer;
alter table expensepayout alter column agent_ID integer;
alter table expensepayout alter column station_ID integer;

alter table incident_range alter column companycode_id varchar(3);

alter table agent alter column is_wt_user integer;
alter table agent alter column is_online integer;
GO
ALTER TABLE agent ADD DEFAULT ((0)) FOR is_wt_user;
ALTER TABLE agent ADD DEFAULT ((0)) FOR is_online;

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

alter table passenger alter column salutation integer;
alter table passenger alter column isprimary integer;

alter table item alter column lvlofdamage integer;
alter table item alter column xdescelement_ID_1 integer;
alter table item alter column xdescelement_ID_2 integer;
alter table item alter column xdescelement_ID_3 integer;

drop index remarktype_ind on remark;
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
alter table company_specific_variable drop constraint DF__company_s__wt_wr__02925FBF;
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


alter table incident_assoc alter column itemtype_ID integer;


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
