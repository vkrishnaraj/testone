USE [NetTracer_Prod]
GO
/****** Object:  Table [dbo].[bccfs]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[bccfs](
	[BCCFS_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[claim_ID] [int] NULL,
	[msgtype] [varchar](3) NULL,
	[firstname] [varchar](20) NULL,
	[middlename] [varchar](20) NULL,
	[lastname] [varchar](20) NULL,
	[ssn] [varchar](9) NULL,
	[phone] [varchar](15) NULL,
	[common] [varchar](255) NULL,
	[address] [varchar](50) NULL,
	[city] [varchar](50) NULL,
	[state] [varchar](2) NULL,
	[zip] [varchar](12) NULL,
	[extrasearch] [varchar](255) NULL,
	[remark] [varchar](255) NULL,
	[result] [varchar](255) NULL,
 CONSTRAINT [PK_bccfs] PRIMARY KEY CLUSTERED 
(
	[BCCFS_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[bagbuzz]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[bagbuzz](
	[bagbuzz_id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[created_timestamp] [datetime] NULL,
	[data] [text] NULL,
	[description] [varchar](256) NULL,
	[status_ID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[bagbuzz_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Bag]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Bag](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[bagColor] [varchar](255) NULL,
	[bagType] [varchar](255) NULL,
	[description] [varchar](255) NULL,
	[manufacturer] [varchar](255) NULL,
	[incident_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_work_shift]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_work_shift](
	[audit_shift_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[shift_id] [int] NULL,
	[shift_code] [varchar](30) NULL,
	[shift_description] [varchar](50) NOT NULL,
	[companycode_ID] [varchar](3) NULL,
	[locale] [varchar](2) NULL,
	[modifying_agent_ID] [int] NULL,
	[time_modified] [datetime] NOT NULL,
	[reason_modified] [varchar](255) NULL,
 CONSTRAINT [PK_audit_work_shift] PRIMARY KEY CLUSTERED 
(
	[audit_shift_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_usergroup]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_usergroup](
	[audit_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[UserGroup_ID] [int] NULL,
	[companycode_ID] [varchar](3) NULL,
	[description2] [varchar](50) NULL,
	[description] [varchar](50) NULL,
	[modifying_agent_ID] [int] NULL,
	[time_modified] [datetime] NOT NULL,
	[reason_modified] [varchar](255) NULL,
 CONSTRAINT [PK_audit_usergroup] PRIMARY KEY CLUSTERED 
(
	[audit_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_station]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_station](
	[audit_station_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[Station_ID] [int] NULL,
	[stationcode] [varchar](10) NULL,
	[companycode_ID] [varchar](3) NULL,
	[stationdesc] [varchar](255) NULL,
	[address1] [varchar](50) NULL,
	[address2] [varchar](50) NULL,
	[city] [varchar](50) NULL,
	[state_ID] [varchar](2) NULL,
	[countrycode_ID] [varchar](3) NULL,
	[zip] [varchar](11) NULL,
	[phone] [varchar](50) NULL,
	[airport_location] [varchar](100) NULL,
	[operation_hours] [varchar](100) NULL,
	[locale] [varchar](2) NULL,
	[associated_airport] [varchar](3) NULL,
	[modifying_agent_ID] [int] NULL,
	[time_modified] [datetime] NOT NULL,
	[reason_modified] [varchar](255) NULL,
	[station_region] [varchar](100) NULL,
	[station_region_mgr] [varchar](100) NULL,
	[goal] [float] NOT NULL,
	[is_lz] [tinyint] NULL,
	[active] [int] NULL,
	[region_goal] [float] NOT NULL,
 CONSTRAINT [PK_audit_station] PRIMARY KEY CLUSTERED 
(
	[audit_station_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_remark]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_remark](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[audit_ohd_id] [int] NULL,
	[audit_incident_id] [int] NULL,
	[Remark_ID] [int] NOT NULL,
	[agent_ID] [int] NULL,
	[createtime] [datetime] NOT NULL,
	[remarktext] [varchar](1500) NULL,
	[remarktype] [int] NULL,
 CONSTRAINT [PK_audit_remark] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_prorate_itinerary]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_prorate_itinerary](
	[audit_prorate_itinerary_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[prorate_itinerary_id] [int] NOT NULL,
	[audit_claimprorate_id] [int] NOT NULL,
	[legfrom] [varchar](3) NULL,
	[legto] [varchar](3) NULL,
	[departdate] [datetime] NULL,
	[flightnum] [varchar](4) NULL,
	[airline] [varchar](3) NULL,
	[miles] [float] NULL,
	[percentage] [float] NULL,
	[share] [float] NULL,
	[currency_ID] [varchar](3) NULL,
 CONSTRAINT [PK_audit_prorate_itinerary] PRIMARY KEY CLUSTERED 
(
	[audit_prorate_itinerary_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_passenger]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_passenger](
	[audit_passenger_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[Passenger_ID] [int] NOT NULL,
	[audit_incident_id] [int] NOT NULL,
	[incident_ID] [varchar](13) NULL,
	[audit_membership_id] [int] NULL,
	[jobtitle] [varchar](25) NULL,
	[salutation] [smallint] NULL,
	[firstname] [varchar](20) NULL,
	[middlename] [varchar](20) NULL,
	[lastname] [varchar](20) NULL,
	[commonnum] [varchar](20) NULL,
	[countryofissue] [varchar](3) NULL,
	[dlstate] [char](2) NULL,
	[driverslicense] [varchar](15) NULL,
	[isprimary] [smallint] NULL,
 CONSTRAINT [PK_audit_passenger] PRIMARY KEY CLUSTERED 
(
	[audit_passenger_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_other_system_information]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_other_system_information](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[incident_id] [varchar](13) NULL,
	[info] [text] NULL,
	[modifying_agent_ID] [int] NULL,
	[time_modified] [datetime] NOT NULL,
	[reason_modified] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_ohd_photo]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_ohd_photo](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[audit_ohd_id] [int] NOT NULL,
	[Photo_ID] [int] NOT NULL,
	[thumbpath] [varchar](255) NULL,
	[picpath] [varchar](255) NULL,
	[ohd_id] [varchar](50) NULL,
 CONSTRAINT [PK_audit_ohd_photo] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_ohd_passenger]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_ohd_passenger](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[audit_ohd_id] [int] NOT NULL,
	[passenger_id] [int] NOT NULL,
	[firstname] [varchar](20) NULL,
	[middlename] [varchar](20) NULL,
	[lastname] [varchar](20) NULL,
	[isprimary] [int] NOT NULL,
 CONSTRAINT [PK_audit_ohd_passenger] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_ohd_itinerary]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_ohd_itinerary](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[audit_ohd_id] [int] NOT NULL,
	[Itinerary_ID] [int] NOT NULL,
	[itinerarytype] [int] NULL,
	[legfrom] [varchar](3) NULL,
	[legto] [varchar](3) NULL,
	[legfrom_type] [int] NULL,
	[legto_type] [int] NULL,
	[departdate] [datetime] NULL,
	[arrivedate] [datetime] NULL,
	[flightnum] [varchar](4) NULL,
	[schdeparttime] [datetime] NULL,
	[scharrivetime] [datetime] NULL,
	[actdeparttime] [datetime] NULL,
	[actarrivetime] [datetime] NULL,
	[airline] [varchar](3) NULL,
 CONSTRAINT [PK_audit_ohd_itinerary] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_ohd_inventory]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_ohd_inventory](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[audit_ohd_id] [int] NOT NULL,
	[OHD_Inventory_ID] [bigint] NULL,
	[OHD_categorytype_ID] [int] NOT NULL,
	[description] [varchar](4000) NULL,
 CONSTRAINT [PK_audit_ohd_inventory] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_ohd_address]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_ohd_address](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[audit_ohd_passenger_id] [int] NOT NULL,
	[Address_ID] [int] NOT NULL,
	[address1] [varchar](50) NULL,
	[address2] [varchar](50) NULL,
	[city] [varchar](50) NULL,
	[state_ID] [varchar](2) NULL,
	[province] [varchar](100) NULL,
	[zip] [varchar](11) NULL,
	[homephone] [varchar](25) NULL,
	[workphone] [varchar](25) NULL,
	[mobile] [varchar](25) NULL,
	[pager] [varchar](25) NULL,
	[altphone] [varchar](25) NULL,
	[email] [varchar](50) NULL,
	[addresstype] [int] NULL,
	[countrycode_ID] [varchar](3) NULL,
	[passenger_id] [int] NULL,
 CONSTRAINT [PK_audit_ohd_address] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_ohd]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_ohd](
	[audit_ohd_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[OHD_ID] [varchar](13) NOT NULL,
	[found_station_ID] [int] NULL,
	[holding_station_ID] [int] NULL,
	[audit_membership_id] [int] NULL,
	[record_locator] [varchar](10) NULL,
	[agent_ID] [int] NULL,
	[founddate] [datetime] NULL,
	[foundtime] [datetime] NULL,
	[claimnum] [varchar](13) NULL,
	[color] [varchar](2) NULL,
	[bagarrivedate] [datetime] NULL,
	[type] [varchar](2) NULL,
	[xdescelement_ID_1] [int] NULL,
	[xdescelement_ID_2] [int] NULL,
	[xdescelement_ID_3] [int] NULL,
	[manufacturer_ID] [int] NULL,
	[manufacturer_other] [varchar](100) NULL,
	[storage_location] [varchar](50) NULL,
	[close_date] [datetime] NULL,
	[status_ID] [int] NULL,
	[firstname] [varchar](20) NULL,
	[lastname] [varchar](20) NULL,
	[middlename] [varchar](20) NULL,
	[ohd_type] [int] NULL,
	[time_modified] [datetime] NOT NULL,
	[modifying_agent_ID] [int] NULL,
	[reason_modified] [varchar](255) NULL,
	[disposal_status_id] [int] NULL,
	[wt_id] [varchar](15) NULL,
	[faultstation_ID] [int] NOT NULL,
	[loss_code] [int] NOT NULL,
	[matched_incident] [varchar](13) NULL,
 CONSTRAINT [PK_audit_ohd] PRIMARY KEY CLUSTERED 
(
	[audit_ohd_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_lost_found_photo]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_lost_found_photo](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[audit_lost_found_id] [int] NULL,
	[Photo_ID] [int] NULL,
	[thumbpath] [varchar](255) NULL,
	[picpath] [varchar](255) NULL,
	[file_ref_number] [varchar](50) NULL,
	[modifying_agent_ID] [int] NULL,
	[time_modified] [datetime] NULL,
	[reason_modified] [varchar](255) NULL,
 CONSTRAINT [PK_audit_lost_found_photo] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_lost_found]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_lost_found](
	[audit_lost_found_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[file_ref_number] [varchar](13) NOT NULL,
	[filing_agent_id] [int] NULL,
	[finding_agent_name] [varchar](100) NULL,
	[customer_firstname] [varchar](20) NULL,
	[customer_mname] [varchar](20) NULL,
	[customer_lastname] [varchar](20) NULL,
	[customer_address1] [varchar](50) NULL,
	[customer_address2] [varchar](50) NULL,
	[customer_city] [varchar](50) NULL,
	[customer_state_ID] [varchar](2) NULL,
	[customer_province] [varchar](100) NULL,
	[customer_countrycode_ID] [varchar](3) NULL,
	[customer_zip] [varchar](11) NULL,
	[customer_tel] [varchar](25) NULL,
	[customer_email] [varchar](100) NULL,
	[create_date] [datetime] NOT NULL,
	[location] [varchar](255) NULL,
	[item_description] [varchar](255) NULL,
	[disposal_status_id] [int] NULL,
	[create_station_id] [int] NULL,
	[close_date] [datetime] NULL,
	[report_status_id] [int] NULL,
	[closing_agent_id] [varchar](100) NULL,
	[remark] [varchar](1500) NULL,
	[report_type] [smallint] NOT NULL,
	[dateFoundLost] [datetime] NOT NULL,
	[time_modified] [datetime] NOT NULL,
	[modifying_agent_ID] [int] NULL,
	[reason_modified] [varchar](255) NULL,
 CONSTRAINT [PK_audit_lost_found] PRIMARY KEY CLUSTERED 
(
	[audit_lost_found_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_itinerary]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_itinerary](
	[audit_itinerary_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[Itinerary_ID] [int] NOT NULL,
	[audit_incident_id] [int] NOT NULL,
	[itinerarytype] [smallint] NOT NULL,
	[legfrom] [varchar](3) NULL,
	[legto] [varchar](3) NULL,
	[legfrom_type] [smallint] NULL,
	[legto_type] [smallint] NULL,
	[departdate] [datetime] NULL,
	[arrivedate] [datetime] NULL,
	[flightnum] [varchar](4) NULL,
	[schdeparttime] [datetime] NULL,
	[scharrivetime] [datetime] NULL,
	[actdeparttime] [datetime] NULL,
	[actarrivetime] [datetime] NULL,
	[incident_ID] [varchar](13) NULL,
	[airline] [varchar](3) NULL,
 CONSTRAINT [PK_audit_itinerary] PRIMARY KEY CLUSTERED 
(
	[audit_itinerary_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_item_photo]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_item_photo](
	[audit_photo_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[audit_item_id] [int] NOT NULL,
	[Photo_ID] [int] NOT NULL,
	[thumbpath] [varchar](255) NULL,
	[picpath] [varchar](255) NULL,
	[item_ID] [int] NULL,
 CONSTRAINT [PK_audit_item_photo] PRIMARY KEY CLUSTERED 
(
	[audit_photo_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_item_inventory]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_item_inventory](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[inventory_ID] [int] NOT NULL,
	[audit_item_ID] [int] NOT NULL,
	[categorytype_ID] [int] NOT NULL,
	[description] [varchar](4000) NULL,
 CONSTRAINT [PK_audit_item_inventory] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_item]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_item](
	[audit_item_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[Item_ID] [int] NOT NULL,
	[audit_incident_id] [int] NOT NULL,
	[status_ID] [int] NULL,
	[bagnumber] [int] NOT NULL,
	[incident_ID] [varchar](13) NULL,
	[itemtype_ID] [int] NULL,
	[OHD_ID] [varchar](13) NULL,
	[BDO_ID] [int] NULL,
	[claimchecknum] [varchar](13) NULL,
	[color] [varchar](2) NULL,
	[bagtype] [varchar](2) NULL,
	[xdescelement_ID_1] [smallint] NULL,
	[xdescelement_ID_2] [smallint] NULL,
	[xdescelement_ID_3] [smallint] NULL,
	[manufacturer_ID] [int] NULL,
	[manufacturer_other] [varchar](100) NULL,
	[lvlofdamage] [smallint] NULL,
	[damage] [varchar](255) NULL,
	[resolution_ID] [int] NULL,
	[resolutiondesc] [varchar](255) NULL,
	[cost] [float] NULL,
	[drafts] [varchar](30) NULL,
	[currency_ID] [varchar](3) NULL,
	[fnameonbag] [varchar](50) NULL,
	[mnameonbag] [varchar](50) NULL,
	[lnameonbag] [varchar](50) NULL,
	[arrivedonairline_ID] [varchar](3) NULL,
	[arrivedondate] [datetime] NULL,
	[arrivedonflightnum] [varchar](5) NULL,
	[bag_weight] [float] NOT NULL,
	[bag_weight_unit] [varchar](3) NULL,
 CONSTRAINT [PK_audit_item] PRIMARY KEY CLUSTERED 
(
	[audit_item_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_incident_claimcheck]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_incident_claimcheck](
	[audit_claimcheck_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[claimcheck_ID] [int] NOT NULL,
	[audit_incident_id] [int] NOT NULL,
	[incident_ID] [varchar](13) NULL,
	[claimchecknum] [varchar](13) NULL,
	[OHD_ID] [varchar](13) NULL,
 CONSTRAINT [PK_audit_incident_claimcheck] PRIMARY KEY CLUSTERED 
(
	[audit_claimcheck_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[dateformat]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[dateformat](
	[Dateformat_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[format] [varchar](20) NULL,
 CONSTRAINT [PK_dateformat] PRIMARY KEY CLUSTERED 
(
	[Dateformat_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[customer_viewable_comments]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[customer_viewable_comments](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[incident_id] [varchar](13) NULL,
	[comment] [text] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[currency]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[currency](
	[Currency_ID] [varchar](3) NOT NULL,
	[description] [varchar](20) NULL,
 CONSTRAINT [PK_currency] PRIMARY KEY CLUSTERED 
(
	[Currency_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 80) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Crm]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Crm](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[crm_key] [varchar](128) NULL,
	[status] [varchar](255) NULL,
	[incident_id] [varchar](13) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [uniq_inc] UNIQUE NONCLUSTERED 
(
	[incident_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CreditCard]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CreditCard](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[ccExpMonth] [int] NOT NULL,
	[ccExpYear] [int] NOT NULL,
	[ccNumLastFour] [varchar](255) NULL,
	[ccNumber] [varchar](255) NULL,
	[ccType] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[countrycode]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[countrycode](
	[CountryCode_ID] [varchar](3) NOT NULL,
	[country] [varchar](20) NULL,
 CONSTRAINT [PK_countrycode] PRIMARY KEY CLUSTERED 
(
	[CountryCode_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[companycode]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[companycode](
	[CompanyCode_ID] [varchar](3) NOT NULL,
	[companydesc] [varchar](255) NULL,
	[address1] [varchar](50) NULL,
	[address2] [varchar](50) NULL,
	[city] [varchar](50) NULL,
	[state_ID] [varchar](2) NULL,
	[countrycode_ID] [varchar](3) NULL,
	[zip] [varchar](11) NULL,
	[variablecode] [varchar](3) NULL,
	[phone] [varchar](50) NULL,
	[email_address] [varchar](255) NULL,
 CONSTRAINT [PK_companycode] PRIMARY KEY CLUSTERED 
(
	[CompanyCode_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[company_specific_variable]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[company_specific_variable](
	[ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[companycode_ID] [varchar](3) NULL,
	[total_threads] [int] NULL,
	[seconds_wait] [int] NULL,
	[MIN_MATCH_PERCENT] [float] NULL,
	[mbr_to_lz_days] [int] NULL,
	[ohd_to_lz_days] [int] NULL,
	[report_method] [int] NULL,
	[default_station_code] [int] NULL,
	[email_customer] [smallint] NULL,
	[email_host] [varchar](100) NULL,
	[email_port] [int] NULL,
	[email_from] [varchar](100) NULL,
	[email_to] [varchar](255) NULL,
	[audit_ohd] [int] NULL,
	[audit_lost_found] [bigint] NULL,
	[audit_lost_delayed] [int] NULL,
	[audit_damaged] [int] NULL,
	[audit_missing_articles] [int] NULL,
	[audit_agent] [int] NULL,
	[audit_group] [int] NULL,
	[audit_company] [int] NULL,
	[audit_shift] [int] NULL,
	[audit_permission] [int] NULL,
	[audit_station] [int] NULL,
	[audit_loss_codes] [int] NULL,
	[audit_claims] [int] NULL,
	[audit_airport] [int] NULL,
	[min_interim_approval_check] [float] NULL,
	[min_interim_approval_voucher] [float] NULL,
	[min_interim_approval_miles] [float] NULL,
	[max_image_file_size] [int] NULL,
	[pass_expire_days] [int] NULL,
	[damaged_to_lz_days] [int] NULL,
	[miss_to_lz_days] [int] NULL,
	[default_loss_code] [int] NULL,
	[audit_delivery_companies] [int] NULL,
	[webs_enabled] [int] NULL,
	[max_failed_logins] [int] NULL,
	[secure_password] [int] NULL,
	[mbr_to_wt_days] [int] NULL,
	[ohd_to_wt_hours] [int] NULL,
	[wt_user] [varchar](100) NULL,
	[wt_pass] [varchar](100) NULL,
	[ohd_lz] [int] NOT NULL,
	[lz_mode] [int] NOT NULL,
	[bak_nttracer_data_days] [int] NOT NULL,
	[bak_nttracer_ohd_data_days] [int] NOT NULL,
	[bak_nttracer_lostfound_data_days] [int] NOT NULL,
	[retrieve_actionfile_interval] [int] NOT NULL,
	[wt_url] [varchar](100) NULL,
	[wt_airlinecode] [varchar](100) NULL,
	[wt_enabled] [int] NULL,
	[wt_write_enabled] [int] NULL,
	[scannerDefaultBack] [int] NOT NULL,
	[scannerDefaultForward] [int] NOT NULL,
	[blind_cc] [varchar](100) NULL,
	[oal_ohd_hours] [int] NOT NULL,
	[oal_inc_hours] [int] NOT NULL,
	[auto_wt_amend] [smallint] NOT NULL,
	[min_interim_approval_incidental] [float] NULL,
	[min_interim_approval_cc_refund] [float] NULL,
	[autocloseohd] [smallint] NULL,
	[min_pass_size] [int] NULL,
	[pass_x_history] [int] NULL,
	[auto_close_days_back] [int] NULL,
	[auto_close_ld_code] [int] NULL,
	[auto_close_dam_code] [int] NULL,
	[auto_close_pil_code] [int] NULL,
	[auto_close_ld_station] [int] NULL,
	[auto_close_dam_station] [int] NULL,
	[auto_close_pil_station] [int] NULL,
	[incident_lock_mins] [int] NULL,
 CONSTRAINT [PK_company_specific_variable] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[company_irregularity_codes]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[company_irregularity_codes](
	[code_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[loss_code] [int] NOT NULL,
	[description] [varchar](150) NOT NULL,
	[companycode_ID] [varchar](3) NOT NULL,
	[report_type] [smallint] NOT NULL,
	[SHOW_TO_LIMITED_USERS] [int] NOT NULL,
 CONSTRAINT [PK_company_irregularity_codes] PRIMARY KEY CLUSTERED 
(
	[code_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[claimprorate]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[claimprorate](
	[Claimprorate_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[companycode_ID] [varchar](3) NULL,
	[createdate] [datetime] NULL,
	[file_reference] [varchar](20) NULL,
	[pir_attached] [int] NULL,
	[claim_attached] [int] NULL,
	[confirmpayment_attached] [int] NULL,
	[all_prorate] [int] NULL,
	[all_prorate_reason] [varchar](255) NULL,
	[remit] [int] NULL,
	[remit_amount] [float] NULL,
	[currency_ID] [varchar](3) NULL,
	[remit_to] [varchar](255) NULL,
	[clearing_bill] [int] NULL,
	[prorate_officer] [varchar](255) NULL,
	[sita_address] [varchar](255) NULL,
	[fax_number] [varchar](50) NULL,
	[total_percentage] [float] NULL,
	[total_share] [float] NULL,
 CONSTRAINT [PK_claimprorate] PRIMARY KEY CLUSTERED 
(
	[Claimprorate_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[claimactivity]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[claimactivity](
	[Claimactivity_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[claim_ID] [int] NULL,
	[createtime] [datetime] NULL,
	[agent_ID] [int] NULL,
	[code] [varchar](10) NULL,
	[description] [varchar](255) NULL,
 CONSTRAINT [PK_claimactivity] PRIMARY KEY CLUSTERED 
(
	[Claimactivity_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[claim]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[claim](
	[Claim_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[incident_ID] [varchar](13) NULL,
	[claimamount] [float] NULL,
	[currency_ID] [varchar](3) NULL,
	[status_ID] [int] NULL,
	[claimprorate_ID] [int] NULL,
	[total] [float] NULL,
	[ssn] [varchar](9) NULL,
	[driverslicense] [varchar](15) NULL,
	[dlstate] [varchar](2) NULL,
	[commonnum] [varchar](20) NULL,
	[countryofissue] [varchar](3) NULL,
	[subclass_type] [varchar](16) NOT NULL,
	[claimProrateId] [int] NOT NULL,
	[ntIncidentId] [varchar](255) NULL,
	[statusId] [int] NOT NULL,
	[swapId] [bigint] NOT NULL,
 CONSTRAINT [PK_claim] PRIMARY KEY CLUSTERED 
(
	[Claim_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CHECKLIST_VERSION]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CHECKLIST_VERSION](
	[version_id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[active] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[version_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[audit_incident]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_incident](
	[audit_incident_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[Incident_ID] [varchar](13) NOT NULL,
	[itemtype_ID] [int] NULL,
	[stationcreated_ID] [int] NULL,
	[stationassigned_ID] [int] NULL,
	[faultstation_ID] [int] NULL,
	[loss_code] [int] NULL,
	[agent_ID] [int] NULL,
	[recordlocator] [varchar](10) NULL,
	[manualreportnum] [varchar](20) NULL,
	[status_ID] [int] NULL,
	[ticketnumber] [varchar](14) NULL,
	[reportmethod] [int] NULL,
	[checkedlocation] [varchar](1) NULL,
	[numpassengers] [int] NULL,
	[numbagchecked] [int] NULL,
	[numbagreceived] [int] NULL,
	[voluntaryseparation] [int] NULL,
	[courtesyreport] [int] NULL,
	[tsachecked] [int] NULL,
	[customcleared] [int] NULL,
	[nonrevenue] [int] NULL,
	[ohd_lasttraced] [datetime] NULL,
	[createdate] [datetime] NULL,
	[createtime] [datetime] NULL,
	[close_date] [datetime] NULL,
	[version] [int] NULL,
	[modify_time] [datetime] NOT NULL,
	[modify_agent_id] [int] NULL,
	[modify_reason] [varchar](255) NULL,
	[agentassigned_ID] [int] NULL,
	[wt_id] [varchar](15) NULL,
	[language] [varchar](2) NULL,
	[checklist_version] [numeric](19, 0) NOT NULL,
	[overall_weight] [float] NOT NULL,
	[overall_weight_unit] [varchar](3) NULL,
	[locked] [tinyint] NOT NULL,
	[revenue_code] [varchar](4) NULL,
	[oc_claim_id] [bigint] NULL,
	[tracing_status_id] [int] NULL,
	[instructions] [varchar](max) NULL,
	[tracingStarted] [datetime] NULL,
	[tracingComplete] [datetime] NULL,
	[tracing_agent_ID] [int] NULL,
 CONSTRAINT [PK_audit_incident] PRIMARY KEY CLUSTERED 
(
	[audit_incident_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_group_component_policy]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[audit_group_component_policy](
	[audit_policy_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[policy_id] [int] NULL,
	[component_id] [int] NULL,
	[audit_group_id] [int] NULL,
 CONSTRAINT [PK_audit_group_component_policy] PRIMARY KEY CLUSTERED 
(
	[audit_policy_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[audit_fs_claim]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[audit_fs_claim](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[accessTime] [datetime] NOT NULL,
	[actionType] [int] NOT NULL,
	[agentId] [int] NOT NULL,
	[itemId] [bigint] NOT NULL,
	[itemType] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[audit_file_control]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_file_control](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[control_id] [int] NOT NULL,
	[audit_ohd_id] [int] NOT NULL,
	[file_ref_number] [varchar](100) NULL,
	[start_date] [datetime] NOT NULL,
	[end_date] [datetime] NULL,
	[controlling_station_id] [int] NOT NULL,
 CONSTRAINT [PK_audit_file_control] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_expensepayout]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_expensepayout](
	[audit_expensepayout_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[Expensepayout_ID] [int] NOT NULL,
	[audit_claim_id] [int] NULL,
	[createdate] [datetime] NULL,
	[paycode] [varchar](5) NULL,
	[mileageamt] [int] NULL,
	[voucheramt] [float] NULL,
	[currency_ID] [varchar](3) NULL,
	[draft] [varchar](30) NULL,
	[draftreqdate] [datetime] NULL,
	[draftpaiddate] [datetime] NULL,
	[checkamt] [float] NULL,
	[comments] [varchar](2000) NULL,
	[expenselocation_ID] [int] NULL,
	[expensetype_ID] [int] NULL,
	[status_ID] [int] NULL,
	[agent_ID] [int] NULL,
	[station_ID] [int] NULL,
	[modify_reason] [varchar](255) NULL,
	[approval_date] [datetime] NULL,
	[incidental_amount_auth] [float] NULL,
	[incidental_amount_claim] [float] NULL,
	[creditcard_refund] [float] NULL,
	[voucher_exp] [smalldatetime] NULL,
	[modify_time] [datetime] NULL,
 CONSTRAINT [PK_audit_expensepayout] PRIMARY KEY CLUSTERED 
(
	[audit_expensepayout_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_delivercompany]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_delivercompany](
	[audit_delivercompany_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[delivercompany_ID] [int] NOT NULL,
	[name] [varchar](50) NULL,
	[address] [varchar](50) NULL,
	[custom_ID] [varchar](3) NULL,
	[active] [int] NULL,
	[companycode_ID] [varchar](3) NULL,
	[modifying_agent_ID] [int] NULL,
	[time_modified] [datetime] NOT NULL,
	[reason_modified] [varchar](255) NULL,
	[phone] [varchar](50) NULL,
	[delivery_integration_type] [varchar](10) NULL,
	[integration_key] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[audit_delivercompany_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_deliverco_station]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_deliverco_station](
	[audit_deliverco_station_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[deliverco_station_ID] [int] NOT NULL,
	[delivercompany_ID] [int] NOT NULL,
	[station_ID] [int] NOT NULL,
	[modifying_agent_ID] [int] NULL,
	[time_modified] [datetime] NOT NULL,
	[reason_modified] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[audit_deliverco_station_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_deliver_servicelevel]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_deliver_servicelevel](
	[audit_servicelevel_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[servicelevel_ID] [int] NOT NULL,
	[delivercompany_ID] [int] NOT NULL,
	[description] [varchar](50) NULL,
	[active] [int] NULL,
	[modifying_agent_ID] [int] NULL,
	[time_modified] [datetime] NOT NULL,
	[reason_modified] [varchar](255) NULL,
	[service_code] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[audit_servicelevel_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_customer_viewable_comments]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_customer_viewable_comments](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[incident_id] [varchar](13) NULL,
	[comment] [text] NULL,
	[modifying_agent_ID] [int] NULL,
	[time_modified] [datetime] NOT NULL,
	[reason_modified] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_companycode]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_companycode](
	[audit_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[CompanyCode_ID] [varchar](3) NULL,
	[companydesc] [varchar](255) NULL,
	[address1] [varchar](50) NULL,
	[address2] [varchar](50) NULL,
	[city] [varchar](50) NULL,
	[state_ID] [varchar](2) NULL,
	[countrycode_ID] [varchar](3) NULL,
	[zip] [varchar](11) NULL,
	[audit_variablecode] [int] NULL,
	[phone] [varchar](50) NULL,
	[modifying_agent_ID] [int] NULL,
	[time_modified] [datetime] NOT NULL,
	[reason_modified] [varchar](255) NULL,
	[email_address] [varchar](255) NULL,
 CONSTRAINT [PK_audit_companycode] PRIMARY KEY CLUSTERED 
(
	[audit_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_company_specific_variable]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_company_specific_variable](
	[ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[total_threads] [int] NULL,
	[seconds_wait] [int] NULL,
	[MIN_MATCH_PERCENT] [float] NULL,
	[mbr_to_lz_days] [int] NULL,
	[ohd_to_lz_days] [int] NULL,
	[report_method] [int] NULL,
	[default_station_code] [int] NULL,
	[email_customer] [smallint] NULL,
	[email_host] [varchar](100) NULL,
	[email_port] [int] NULL,
	[email_from] [varchar](100) NULL,
	[email_to] [varchar](255) NULL,
	[audit_ohd] [int] NULL,
	[audit_lost_found] [int] NULL,
	[audit_lost_delayed] [int] NULL,
	[audit_damaged] [int] NULL,
	[audit_missing_articles] [int] NULL,
	[audit_agent] [int] NULL,
	[audit_group] [int] NULL,
	[audit_company] [int] NULL,
	[audit_shift] [int] NULL,
	[audit_station] [int] NULL,
	[audit_claims] [int] NULL,
	[audit_airport] [int] NULL,
	[audit_loss_codes] [int] NULL,
	[min_interim_approval_check] [float] NULL,
	[min_interim_approval_voucher] [float] NULL,
	[min_interim_approval_miles] [float] NULL,
	[max_image_file_size] [int] NULL,
	[damaged_to_lz_days] [int] NULL,
	[miss_to_lz_days] [int] NULL,
	[default_loss_code] [int] NULL,
	[audit_delivery_companies] [int] NULL,
	[pass_expire_days] [int] NULL,
	[ws_enabled] [int] NULL,
	[max_failed_logins] [int] NULL,
	[secure_password] [int] NULL,
	[mbr_to_wt_days] [int] NULL,
	[ohd_to_wt_hours] [int] NULL,
	[wt_user] [varchar](100) NULL,
	[wt_pass] [varchar](100) NULL,
	[ohd_lz] [int] NOT NULL,
	[lz_mode] [int] NOT NULL,
	[bak_nttracer_data_days] [int] NOT NULL,
	[bak_nttracer_ohd_data_days] [int] NOT NULL,
	[bak_nttracer_lostfound_data_days] [int] NOT NULL,
	[scannerDefaultBack] [int] NOT NULL,
	[scannerDefaultForward] [int] NOT NULL,
	[blind_cc] [varchar](100) NULL,
	[oal_ohd_hours] [int] NOT NULL,
	[oal_inc_hours] [int] NOT NULL,
	[auto_wt_amend] [smallint] NOT NULL,
	[min_interim_approval_incidental] [float] NULL,
	[min_interim_approval_cc_refund] [float] NULL,
	[autocloseohd] [smallint] NULL,
	[min_pass_size] [int] NULL,
	[pass_x_history] [int] NULL,
	[auto_close_days_back] [int] NULL,
	[auto_close_ld_code] [int] NULL,
	[auto_close_dam_code] [int] NULL,
	[auto_close_pil_code] [int] NULL,
	[auto_close_ld_station] [int] NULL,
	[auto_close_dam_station] [int] NULL,
	[auto_close_pil_station] [int] NULL,
	[incident_lock_mins] [int] NULL,
 CONSTRAINT [PK_audit_company_specific_variable] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_company_irregularity_codes]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_company_irregularity_codes](
	[audit_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[code_id] [int] NULL,
	[loss_code] [int] NOT NULL,
	[description] [varchar](150) NOT NULL,
	[locale] [varchar](2) NOT NULL,
	[companycode_ID] [varchar](3) NOT NULL,
	[report_type] [smallint] NOT NULL,
	[modifying_agent_ID] [int] NULL,
	[time_modified] [datetime] NOT NULL,
	[reason_modified] [varchar](255) NULL,
	[SHOW_TO_LIMITED_USERS] [int] NOT NULL,
 CONSTRAINT [PK_audit_company_irregularity_codes] PRIMARY KEY CLUSTERED 
(
	[audit_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_claimprorate]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_claimprorate](
	[audit_claimprorate_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[Claimprorate_ID] [int] NOT NULL,
	[companycode_ID] [varchar](3) NULL,
	[createdate] [datetime] NULL,
	[file_reference] [varchar](20) NULL,
	[pir_attached] [smallint] NULL,
	[claim_attached] [smallint] NULL,
	[confirmpayment_attached] [smallint] NULL,
	[all_prorate] [smallint] NULL,
	[all_prorate_reason] [varchar](255) NULL,
	[remit] [smallint] NULL,
	[remit_amount] [float] NULL,
	[currency_ID] [varchar](3) NULL,
	[remit_to] [varchar](255) NULL,
	[clearing_bill] [smallint] NULL,
	[prorate_officer] [varchar](255) NULL,
	[sita_address] [varchar](255) NULL,
	[fax_number] [varchar](50) NULL,
	[total_percentage] [float] NULL,
	[total_share] [float] NULL,
 CONSTRAINT [PK_audit_claimprorate] PRIMARY KEY CLUSTERED 
(
	[audit_claimprorate_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_claim]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_claim](
	[audit_claim_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[Claim_ID] [bigint] NULL,
	[incident_ID] [varchar](13) NULL,
	[claimamount] [float] NULL,
	[currency_ID] [varchar](3) NULL,
	[status_ID] [int] NULL,
	[audit_claimprorate_id] [int] NULL,
	[total] [float] NULL,
	[ssn] [varchar](9) NULL,
	[driverslicense] [varchar](15) NULL,
	[dlstate] [char](2) NULL,
	[commonnum] [varchar](50) NULL,
	[countryofissue] [varchar](3) NULL,
	[modify_time] [datetime] NOT NULL,
	[modify_agent_id] [int] NULL,
	[modify_reason] [varchar](255) NULL,
 CONSTRAINT [PK_audit_claim] PRIMARY KEY CLUSTERED 
(
	[audit_claim_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_articles]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_articles](
	[audit_articles_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[Articles_ID] [int] NOT NULL,
	[audit_incident_id] [int] NOT NULL,
	[item_ID] [int] NULL,
	[article] [varchar](50) NULL,
	[description] [varchar](255) NULL,
	[purchasedate] [datetime] NULL,
	[cost] [float] NULL,
	[incident_ID] [varchar](13) NULL,
	[currency_ID] [varchar](3) NULL,
 CONSTRAINT [PK_audit_articles] PRIMARY KEY CLUSTERED 
(
	[audit_articles_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_airport]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_airport](
	[audit_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[id] [int] NULL,
	[airport_code] [varchar](3) NOT NULL,
	[airport_desc] [varchar](255) NULL,
	[locale] [varchar](2) NOT NULL,
	[companyCode_ID] [varchar](3) NOT NULL,
	[time_modified] [datetime] NOT NULL,
	[reason_modified] [varchar](255) NULL,
	[modifying_agent_ID] [int] NULL,
	[country] [int] NULL,
 CONSTRAINT [PK_audit_airport] PRIMARY KEY CLUSTERED 
(
	[audit_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_airlinemembership]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_airlinemembership](
	[audit_membership_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[Membership_ID] [int] NOT NULL,
	[companycode_ID] [varchar](3) NULL,
	[membershipnum] [varchar](20) NULL,
	[membershipstatus] [varchar](20) NULL,
 CONSTRAINT [PK_audit_airlinemembership] PRIMARY KEY CLUSTERED 
(
	[audit_membership_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_agent]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_agent](
	[audit_agent_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[Agent_ID] [int] NULL,
	[firstname] [varchar](20) NULL,
	[mname] [varchar](20) NULL,
	[lastname] [varchar](20) NULL,
	[timeout] [int] NULL,
	[username] [varchar](20) NULL,
	[password] [varchar](100) NULL,
	[active] [smallint] NULL,
	[station_ID] [int] NULL,
	[companycode_ID] [varchar](3) NULL,
	[defaultlocale] [varchar](2) NULL,
	[currentlocale] [varchar](2) NULL,
	[defaultcurrency] [varchar](3) NULL,
	[defaulttimezone] [varchar](10) NULL,
	[currenttimezone] [varchar](10) NULL,
	[dateformat_ID] [int] NULL,
	[timeformat_ID] [int] NULL,
	[shift_id] [int] NULL,
	[usergroup_id] [int] NULL,
	[last_logged_on] [datetime] NULL,
	[is_online] [int] NULL,
	[modifying_agent_ID] [int] NULL,
	[time_modified] [datetime] NOT NULL,
	[reason_modified] [varchar](255) NULL,
	[ws_enabled] [int] NULL,
	[max_ws_sessions] [int] NULL,
	[web_enabled] [int] NULL,
	[is_wt_user] [int] NULL,
	[reset_password] [smallint] NULL,
	[account_locked] [smallint] NULL,
 CONSTRAINT [PK_audit_agent] PRIMARY KEY CLUSTERED 
(
	[audit_agent_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[audit_address]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[audit_address](
	[audit_address_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[Address_ID] [int] NOT NULL,
	[audit_passenger_id] [int] NOT NULL,
	[address1] [varchar](50) NULL,
	[address2] [varchar](50) NULL,
	[city] [varchar](50) NULL,
	[state_ID] [varchar](2) NULL,
	[province] [varchar](100) NULL,
	[zip] [varchar](11) NULL,
	[hotel] [varchar](50) NULL,
	[homephone] [varchar](50) NULL,
	[workphone] [varchar](50) NULL,
	[mobile] [varchar](50) NULL,
	[pager] [varchar](50) NULL,
	[altphone] [varchar](50) NULL,
	[email] [varchar](100) NULL,
	[addresstype] [smallint] NULL,
	[passenger_ID] [int] NULL,
	[countrycode_ID] [varchar](3) NULL,
	[valid_bdate] [datetime] NULL,
	[valid_edate] [datetime] NULL,
	[is_permanent] [tinyint] NOT NULL,
 CONSTRAINT [PK_audit_address] PRIMARY KEY CLUSTERED 
(
	[audit_address_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[attachment]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[attachment](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[claim_id] [int] NULL,
	[agent_id] [int] NULL,
	[createdate] [datetime] NULL,
	[attachment_id] [int] NULL,
	[description] [varchar](200) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 80) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[articles]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[articles](
	[Articles_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[item_ID] [int] NULL,
	[article] [varchar](50) NULL,
	[description] [varchar](255) NULL,
	[purchasedate] [datetime] NULL,
	[cost] [float] NULL,
	[incident_ID] [varchar](13) NULL,
	[currency_ID] [varchar](3) NULL,
 CONSTRAINT [PK_articles] PRIMARY KEY CLUSTERED 
(
	[Articles_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[archive_message_copies]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[archive_message_copies](
	[message_copy_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[message_id] [int] NULL,
	[station_id] [int] NULL,
	[status_id] [int] NULL,
	[subject] [varchar](4000) NULL,
	[agent_ID] [int] NOT NULL,
	[body] [varchar](4000) NULL,
PRIMARY KEY CLUSTERED 
(
	[message_copy_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[archive_message]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[archive_message](
	[message_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[station_id] [int] NOT NULL,
	[file_ref_number] [varchar](13) NOT NULL,
	[agent_id] [int] NOT NULL,
	[file_type] [int] NOT NULL,
	[message] [varchar](4000) NULL,
	[send_date] [datetime] NOT NULL,
	[subject] [varchar](4000) NULL,
PRIMARY KEY CLUSTERED 
(
	[message_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[app_tables]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[app_tables](
	[TABLE_ID] [int] NOT NULL,
	[TABLE_NAME] [varchar](30) NULL,
	[TABLE_DESCRIPTION] [varchar](30) NULL,
 CONSTRAINT [PK_app_tables] PRIMARY KEY CLUSTERED 
(
	[TABLE_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[airport]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[airport](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[airport_code] [varchar](3) NOT NULL,
	[airport_desc] [varchar](255) NULL,
	[locale] [varchar](2) NOT NULL,
	[companyCode_ID] [varchar](3) NOT NULL,
	[country] [int] NULL,
 CONSTRAINT [PK_airport] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[airlinemembership]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[airlinemembership](
	[Membership_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[companycode_ID] [varchar](3) NULL,
	[membershipnum] [varchar](20) NULL,
	[membershipstatus] [varchar](20) NULL,
 CONSTRAINT [PK_airlinemembership] PRIMARY KEY CLUSTERED 
(
	[Membership_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[agent_logger]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[agent_logger](
	[ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[agent_ID] [int] NOT NULL,
	[companycode_ID] [char](3) NOT NULL,
	[log_in_time] [datetime] NULL,
	[log_off_time] [datetime] NULL,
 CONSTRAINT [PK_agent_logger] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[agent]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[agent](
	[Agent_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[firstname] [varchar](20) NULL,
	[mname] [varchar](20) NULL,
	[lastname] [varchar](20) NULL,
	[timeout] [int] NULL,
	[username] [varchar](20) NULL,
	[password] [varchar](100) NULL,
	[active] [smallint] NULL,
	[station_ID] [int] NULL,
	[companycode_ID] [varchar](3) NULL,
	[defaultlocale] [varchar](2) NULL,
	[currentlocale] [varchar](2) NULL,
	[defaultcurrency] [varchar](3) NULL,
	[defaulttimezone] [varchar](10) NULL,
	[currenttimezone] [varchar](10) NULL,
	[dateformat_ID] [int] NULL,
	[timeformat_ID] [int] NULL,
	[shift_id] [int] NULL,
	[usergroup_id] [int] NULL,
	[last_logged_on] [datetime] NULL,
	[is_online] [int] NULL,
	[last_pass_reset_date] [datetime] NULL,
	[ws_enabled] [int] NULL,
	[max_ws_sessions] [int] NULL,
	[web_enabled] [int] NULL,
	[is_wt_user] [int] NULL,
	[reset_password] [smallint] NULL,
	[account_locked] [smallint] NULL,
	[failed_logins] [int] NULL,
	[subcompany_id] [int] NULL,
 CONSTRAINT [PK_agent] PRIMARY KEY CLUSTERED 
(
	[Agent_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[AddressWhiteList]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[AddressWhiteList](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[address] [varchar](255) NULL,
	[description] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[address]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[address](
	[Address_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[address1] [varchar](50) NULL,
	[address2] [varchar](50) NULL,
	[city] [varchar](50) NULL,
	[state_ID] [varchar](2) NULL,
	[province] [varchar](100) NULL,
	[zip] [varchar](11) NULL,
	[hotel] [varchar](50) NULL,
	[homephone] [varchar](50) NULL,
	[workphone] [varchar](50) NULL,
	[mobile] [varchar](50) NULL,
	[pager] [varchar](50) NULL,
	[altphone] [varchar](50) NULL,
	[email] [varchar](100) NULL,
	[addresstype] [int] NULL,
	[passenger_ID] [int] NULL,
	[countrycode_ID] [varchar](3) NULL,
	[valid_bdate] [datetime] NULL,
	[valid_edate] [datetime] NULL,
	[is_permanent] [tinyint] NULL,
	[homephone_norm] [varchar](50) NULL,
	[workphone_norm] [varchar](50) NULL,
	[mobile_norm] [varchar](50) NULL,
	[pager_norm] [varchar](50) NULL,
	[altphone_norm] [varchar](50) NULL,
	[hotelphone] [varchar](50) NULL,
 CONSTRAINT [PK_address] PRIMARY KEY CLUSTERED 
(
	[Address_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ActionFileStation]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ActionFileStation](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[company_code] [varchar](2) NULL,
	[lastUpdated] [datetime] NULL,
	[station_code] [varchar](3) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[match_history]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[match_history](
	[match_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[match_type] [smallint] NULL,
	[mbr_number] [varchar](15) NOT NULL,
	[ohd_id] [varchar](13) NOT NULL,
	[bagnumber] [int] NULL,
	[claimchecknum] [varchar](13) NULL,
	[match_percent] [float] NOT NULL,
	[match_made_on] [datetime] NOT NULL,
	[status_ID] [smallint] NOT NULL,
	[category] [int] NOT NULL,
 CONSTRAINT [PK_match_history] PRIMARY KEY CLUSTERED 
(
	[match_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[match_detail]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[match_detail](
	[Matchdetail_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[match_ID] [int] NOT NULL,
	[item] [varchar](50) NULL,
	[mbr_info] [varchar](198) NULL,
	[ohd_info] [varchar](138) NULL,
	[percentage] [float] NULL,
 CONSTRAINT [PK_match_detail] PRIMARY KEY CLUSTERED 
(
	[Matchdetail_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[manufacturer]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[manufacturer](
	[Manufacturer_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[description] [varchar](50) NULL,
 CONSTRAINT [PK_manufacturer] PRIMARY KEY CLUSTERED 
(
	[Manufacturer_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[lz]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[lz](
	[lz_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[station_id] [int] NOT NULL,
	[is_default] [smallint] NOT NULL,
	[percent_load] [float] NOT NULL,
	[companyCode_ID] [varchar](3) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[lz_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[lostandfound_range]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[lostandfound_range](
	[current_num] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[companyCode_ID] [char](3) NULL,
 CONSTRAINT [PK_lostandfound_range] PRIMARY KEY CLUSTERED 
(
	[current_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[lost_found_photo]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[lost_found_photo](
	[Photo_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[thumbpath] [varchar](255) NULL,
	[picpath] [varchar](255) NULL,
	[file_ref_number] [varchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[lost_found]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[lost_found](
	[file_ref_number] [varchar](13) NOT NULL,
	[filing_agent_id] [int] NULL,
	[finding_agent_name] [varchar](100) NULL,
	[customer_firstname] [varchar](20) NULL,
	[customer_mname] [varchar](20) NULL,
	[customer_lastname] [varchar](20) NULL,
	[customer_address1] [varchar](50) NULL,
	[customer_address2] [varchar](50) NULL,
	[customer_city] [varchar](50) NULL,
	[customer_state_ID] [varchar](2) NULL,
	[customer_province] [varchar](100) NULL,
	[customer_countrycode_ID] [varchar](3) NULL,
	[customer_zip] [varchar](11) NULL,
	[customer_tel] [varchar](25) NULL,
	[customer_email] [varchar](100) NULL,
	[create_date] [datetime] NOT NULL,
	[location] [varchar](255) NULL,
	[item_description] [varchar](255) NULL,
	[disposal_status_id] [int] NULL,
	[create_station_id] [int] NULL,
	[close_date] [datetime] NULL,
	[report_status_id] [int] NULL,
	[closing_agent_id] [varchar](100) NULL,
	[remark] [varchar](1500) NULL,
	[report_type] [smallint] NOT NULL,
	[dateFoundLost] [datetime] NOT NULL,
	[category_id] [int] NULL,
	[languageKey] [varchar](50) NULL,
	[languageFreeFlow] [varchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[LOOKUP_AIRLINE_CODES]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LOOKUP_AIRLINE_CODES](
	[Airline_2_Character_Code] [varchar](2) NOT NULL,
	[Airline_3_Digit_Ticketing_Code] [varchar](3) NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[logs]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[logs](
	[log_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[log_level] [varchar](7) NOT NULL,
	[message] [varchar](255) NOT NULL,
	[class_name] [varchar](255) NOT NULL,
	[file_name] [varchar](255) NOT NULL,
	[line_number] [varchar](255) NOT NULL,
	[method_name] [varchar](255) NOT NULL,
	[logger_name] [varchar](255) NOT NULL,
	[log_date] [datetime] NOT NULL,
	[log_time] [datetime] NOT NULL,
	[thread_name] [varchar](255) NOT NULL,
 CONSTRAINT [PK_logs] PRIMARY KEY CLUSTERED 
(
	[log_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[logger_throwable]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[logger_throwable](
	[throwable_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[log_ID] [int] NOT NULL,
	[t_position] [int] NULL,
	[message] [varchar](255) NULL,
 CONSTRAINT [PK_logger_throwable] PRIMARY KEY CLUSTERED 
(
	[throwable_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[localereceipt]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[localereceipt](
	[locale_id] [varchar](2) NOT NULL,
	[locale_description] [varchar](30) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[locale_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[locale]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[locale](
	[locale_id] [varchar](2) NOT NULL,
	[locale_description] [varchar](30) NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[lftransaction]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[lftransaction](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[amt] [varchar](100) NULL,
	[swchKey] [varchar](100) NULL,
	[authCode] [varchar](100) NULL,
	[transactionDate] [datetime] NULL,
	[stan] [varchar](100) NULL,
	[tranNum] [varchar](100) NULL,
	[rspCode] [varchar](2) NULL,
	[shipment_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 80) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[LFSubCategory]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LFSubCategory](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[description] [varchar](255) NULL,
	[parent_id] [bigint] NOT NULL,
	[score] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[lfshipping]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[lfshipping](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[hashKey] [varchar](50) NULL,
	[lost_id] [int] NULL,
	[client_id] [int] NULL,
	[phone_id] [int] NULL,
	[shippingaddress_id] [int] NULL,
	[billingaddress_id] [int] NULL,
	[shippingoption] [varchar](50) NULL,
	[totalpayment] [varchar](50) NULL,
	[transaction_id] [int] NULL,
	[authorizationcode] [varchar](100) NULL,
	[declaredValue] [decimal](18, 0) NULL,
	[shippingName] [varchar](200) NULL,
	[paidTimeStamp] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 80) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[LFSalvage]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LFSalvage](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[closedDate] [datetime] NULL,
	[createdDate] [datetime] NULL,
	[agent_ID] [int] NOT NULL,
	[status_ID] [int] NOT NULL,
	[station_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[lfremark]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[lfremark](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[calltime] [bigint] NULL,
	[outcome] [int] NULL,
	[found_id] [bigint] NULL,
	[lost_id] [bigint] NULL,
	[agent_ID] [int] NULL,
	[remarkDate] [datetime] NULL,
	[type] [int] NULL,
	[remarktext] [varchar](2047) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[LFPhone]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LFPhone](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[extension] [varchar](255) NULL,
	[numberType] [int] NOT NULL,
	[phoneNumber] [varchar](511) NULL,
	[phoneType] [int] NOT NULL,
	[person_id] [bigint] NULL,
	[item_id] [int] NULL,
	[countryNumber] [varchar](511) NULL,
	[areaNumber] [varchar](511) NULL,
	[exchangeNumber] [varchar](511) NULL,
	[lineNumber] [varchar](511) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[LFPerson]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LFPerson](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[email] [varchar](511) NULL,
	[firstName] [varchar](255) NULL,
	[lastName] [varchar](255) NULL,
	[middleName] [varchar](255) NULL,
	[address_id] [bigint] NULL,
	[vantiveNumber] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[lfnamealias]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[lfnamealias](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[alias] [varchar](255) NULL,
	[name] [varchar](255) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[LFMatchHistory]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LFMatchHistory](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[found_id] [bigint] NULL,
	[lost_id] [bigint] NULL,
	[status_Status_ID] [int] NULL,
	[score] [float] NOT NULL,
	[matchTimeStamp] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [lostandfound_id_score_unq] UNIQUE NONCLUSTERED 
(
	[lost_id] ASC,
	[found_id] ASC,
	[score] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LFMatchDetail]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LFMatchDetail](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[description] [varchar](255) NULL,
	[matchHistory_id] [bigint] NULL,
	[score] [float] NOT NULL,
	[lostValue] [varchar](2047) NULL,
	[foundValue] [varchar](2047) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[LFLost]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LFLost](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[closeDate] [datetime] NULL,
	[emailSentDate] [datetime] NULL,
	[openDate] [datetime] NULL,
	[remarks] [varchar](255) NULL,
	[agent_ID] [int] NOT NULL,
	[client_id] [bigint] NULL,
	[station_ID] [int] NOT NULL,
	[status_ID] [int] NOT NULL,
	[closeagent_id] [int] NULL,
	[lossInfo_id] [bigint] NULL,
	[email1] [bit] NULL,
	[email2] [bit] NULL,
	[foundEmail] [bit] NULL,
	[shipment_id] [int] NULL,
	[firstName] [varchar](50) NULL,
	[middleName] [varchar](50) NULL,
	[lastName] [varchar](50) NULL,
	[feedback] [varchar](500) NULL,
	[email3] [bit] NULL,
	[email4] [bit] NULL,
	[email5] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[lflossinfo]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[lflossinfo](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[agreementNumber] [varchar](255) NULL,
	[mvaNumber] [varchar](255) NULL,
	[lossdate] [datetime] NULL,
	[origin_station_ID] [int] NULL,
	[destination_station_ID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[LFLog]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LFLog](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[stamp] [timestamp] NOT NULL,
	[agent] [varchar](50) NULL,
	[stationcode] [varchar](10) NULL,
	[event] [varchar](50) NULL,
	[lflost_id] [int] NULL,
	[lffound_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[LFItem]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LFItem](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[brand] [varchar](255) NULL,
	[category] [bigint] NULL,
	[color] [varchar](255) NULL,
	[description] [varchar](255) NULL,
	[serialNumber] [varchar](255) NULL,
	[subCategory] [bigint] NULL,
	[disposition_status_ID] [int] NULL,
	[lost_id] [bigint] NULL,
	[status_ID] [int] NOT NULL,
	[found_id] [bigint] NULL,
	[trackingNumber] [varchar](255) NULL,
	[type] [int] NOT NULL,
	[longDescription] [varchar](2047) NULL,
	[caseColor] [varchar](255) NULL,
	[model] [varchar](255) NULL,
	[itemCondition] [varchar](255) NULL,
	[size] [varchar](255) NULL,
	[phone_id] [int] NULL,
	[value] [int] NULL,
	[weight] [float] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[LFFound]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LFFound](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[foundDate] [datetime] NULL,
	[mvaNumber] [varchar](255) NULL,
	[agent_ID] [int] NOT NULL,
	[client_id] [bigint] NULL,
	[item_id] [bigint] NULL,
	[station_ID] [int] NOT NULL,
	[status_ID] [int] NOT NULL,
	[agreementNumber] [varchar](255) NULL,
	[barcode] [varchar](255) NULL,
	[itemLocation] [int] NULL,
	[binId] [varchar](255) NULL,
	[receivedDate] [datetime] NULL,
	[deliveredDate] [datetime] NULL,
	[checkNumber] [int] NULL,
	[checkAmount] [float] NULL,
	[salvage_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[LFDelivery]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LFDelivery](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[trackingNumber] [varchar](255) NULL,
	[lost_id] [bigint] NULL,
	[found_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[LFCategory]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LFCategory](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[description] [varchar](255) NULL,
	[score] [int] NOT NULL,
	[companycode] [varchar](8) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[LFAddress]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LFAddress](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[address1] [varchar](511) NULL,
	[address2] [varchar](511) NULL,
	[city] [varchar](255) NULL,
	[country] [varchar](255) NULL,
	[province] [varchar](255) NULL,
	[state] [varchar](255) NULL,
	[zip] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[jdbc_sessions]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[jdbc_sessions](
	[session_id] [varchar](100) NOT NULL,
	[valid_session] [char](1) NOT NULL,
	[max_inactive] [int] NOT NULL,
	[last_access] [bigint] NOT NULL,
	[app_name] [varchar](255) NOT NULL,
	[session_data] [image] NULL,
 CONSTRAINT [PK_jdbc_sessions] PRIMARY KEY CLUSTERED 
(
	[session_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[itinerary]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[itinerary](
	[Itinerary_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[itinerarytype] [int] NULL,
	[legfrom] [varchar](3) NULL,
	[legto] [varchar](3) NULL,
	[legfrom_type] [int] NULL,
	[legto_type] [int] NULL,
	[departdate] [datetime] NULL,
	[arrivedate] [datetime] NULL,
	[flightnum] [varchar](4) NULL,
	[schdeparttime] [datetime] NULL,
	[scharrivetime] [datetime] NULL,
	[actdeparttime] [datetime] NULL,
	[actarrivetime] [datetime] NULL,
	[incident_ID] [varchar](13) NULL,
	[airline] [varchar](3) NULL,
 CONSTRAINT [PK_itinerary] PRIMARY KEY CLUSTERED 
(
	[Itinerary_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[itemtype]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[itemtype](
	[ItemType_ID] [int] NULL,
	[description] [varchar](20) NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[item_photo]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[item_photo](
	[Photo_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[thumbpath] [varchar](255) NULL,
	[picpath] [varchar](255) NULL,
	[item_ID] [int] NULL,
 CONSTRAINT [PK_item_photo] PRIMARY KEY CLUSTERED 
(
	[Photo_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[item_inventory]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[item_inventory](
	[inventory_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[item_ID] [int] NOT NULL,
	[categorytype_ID] [int] NOT NULL,
	[description] [varchar](4000) NULL,
 CONSTRAINT [PK_item_inventory] PRIMARY KEY CLUSTERED 
(
	[inventory_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[item_bdo]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[item_bdo](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[canceled] [tinyint] NOT NULL,
	[bdo_ID] [int] NOT NULL,
	[item_ID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[item]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[item](
	[Item_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[status_ID] [int] NULL,
	[bagnumber] [int] NOT NULL,
	[incident_ID] [varchar](13) NULL,
	[itemtype_ID] [int] NULL,
	[OHD_ID] [varchar](13) NULL,
	[claimchecknum] [varchar](13) NULL,
	[color] [varchar](2) NULL,
	[bagtype] [varchar](2) NULL,
	[xdescelement_ID_1] [int] NULL,
	[xdescelement_ID_2] [int] NULL,
	[xdescelement_ID_3] [int] NULL,
	[manufacturer_ID] [int] NULL,
	[manufacturer_other] [varchar](100) NULL,
	[lvlofdamage] [int] NULL,
	[damage] [varchar](255) NULL,
	[resolution_ID] [int] NULL,
	[resolutiondesc] [varchar](255) NULL,
	[cost] [float] NULL,
	[drafts] [varchar](30) NULL,
	[currency_ID] [varchar](3) NULL,
	[fnameonbag] [varchar](50) NULL,
	[mnameonbag] [varchar](50) NULL,
	[lnameonbag] [varchar](50) NULL,
	[arrivedonairline_ID] [varchar](3) NULL,
	[arrivedondate] [datetime] NULL,
	[arrivedonflightnum] [varchar](5) NULL,
	[wt_bag_selected] [int] NULL,
	[purchase_date] [smalldatetime] NULL,
	[bag_weight] [float] NOT NULL,
	[bag_weight_unit] [varchar](3) NULL,
	[replacementBagIssued] [int] NULL,
	[claimchecknum_leading] [varchar](1) NULL,
	[claimchecknum_ticketingcode] [varchar](3) NULL,
	[claimchecknum_carriercode] [varchar](2) NULL,
	[claimchecknum_bagnumber] [varchar](6) NULL,
	[childrestraint] [int] NULL,
	[externaldesc] [varchar](50) NULL,
 CONSTRAINT [PK_item] PRIMARY KEY CLUSTERED 
(
	[Item_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[irregularity_codes]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[irregularity_codes](
	[locale] [varchar](2) NOT NULL,
	[loss_code] [int] NOT NULL,
	[description] [varchar](150) NOT NULL,
 CONSTRAINT [PK_irregularity_codes] PRIMARY KEY CLUSTERED 
(
	[loss_code] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[InternalSummary]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[InternalSummary](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[description] [varchar](255) NULL,
	[claim_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[incident_types]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[incident_types](
	[INCIDENT_TYPE_ID] [smallint] NOT NULL,
	[TYPE_NAME] [varchar](25) NULL,
	[MODIFY_ACTION] [varchar](30) NULL,
 CONSTRAINT [PK_incident_types] PRIMARY KEY CLUSTERED 
(
	[INCIDENT_TYPE_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[incident_range]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[incident_range](
	[companycode_ID] [varchar](3) NULL,
	[current_num] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
 CONSTRAINT [PK_incident_range] PRIMARY KEY CLUSTERED 
(
	[current_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[INCIDENT_CONTROL]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[INCIDENT_CONTROL](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[assignedDate] [datetime] NULL,
	[Incident_ID] [varchar](13) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[incident_claimcheck]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[incident_claimcheck](
	[claimcheck_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[incident_ID] [varchar](13) NOT NULL,
	[claimchecknum] [varchar](13) NULL,
	[OHD_ID] [varchar](13) NULL,
	[claimchecknum_leading] [varchar](1) NULL,
	[claimchecknum_ticketingcode] [varchar](3) NULL,
	[claimchecknum_carriercode] [varchar](2) NULL,
	[claimchecknum_bagnumber] [varchar](6) NULL,
 CONSTRAINT [PK_incident_claimcheck] PRIMARY KEY CLUSTERED 
(
	[claimcheck_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[incident_assoc]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[incident_assoc](
	[ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[assoc_ID] [varchar](13) NOT NULL,
	[incident_ID] [varchar](13) NOT NULL,
	[itemtype_ID] [int] NULL,
 CONSTRAINT [PK_incident_assoc] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[group_component_policy]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[group_component_policy](
	[policy_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[component_id] [int] NULL,
	[group_id] [int] NULL,
 CONSTRAINT [PK_group_component_policy] PRIMARY KEY CLUSTERED 
(
	[policy_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[GreyListAddress]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[GreyListAddress](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[address] [varchar](255) NULL,
	[city] [varchar](255) NULL,
	[country] [varchar](255) NULL,
	[description] [varchar](255) NULL,
	[geoCodeType] [int] NOT NULL,
	[latitude] [float] NOT NULL,
	[longitude] [float] NOT NULL,
	[state] [varchar](255) NULL,
	[streetName] [varchar](255) NULL,
	[streetNumber] [varchar](255) NULL,
	[zip] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[GLOBAL_LOCK]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[GLOBAL_LOCK](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[createDate] [datetime] NULL,
	[expirationDate] [datetime] NULL,
	[lockKey] [varchar](20) NULL,
	[lockType] [varchar](20) NULL,
	[owner] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[lockType] ASC,
	[lockKey] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[generallog]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[generallog](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[trans] [varchar](20) NULL,
	[entrydate] [datetime] NULL,
	[refId] [varchar](20) NULL,
	[description] [varchar](255) NULL,
	[elapseTime] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[FsReceipt]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[FsReceipt](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[ccExpMonth] [int] NULL,
	[ccExpYear] [int] NULL,
	[ccLastFour] [varchar](4) NULL,
	[ccType] [varchar](2) NULL,
	[company] [varchar](255) NULL,
	[claim_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[FsNameAlias]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[FsNameAlias](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[alias] [varchar](255) NULL,
	[name] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[FsIPAddress]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[FsIPAddress](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[ipAddress] [varchar](20) NULL,
	[claim_id] [bigint] NOT NULL,
	[association] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 80) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[FsIncident]    Script Date: 04/23/2014 09:38:36 ******/
SET ARITHABORT ON
GO
SET CONCAT_NULL_YIELDS_NULL ON
GO
SET ANSI_NULLS ON
GO
SET ANSI_PADDING ON
GO
SET ANSI_WARNINGS ON
GO
SET NUMERIC_ROUNDABORT OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
SET ARITHABORT ON
GO
CREATE TABLE [dbo].[FsIncident](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[airline] [varchar](255) NULL,
	[airlineIncidentId] [varchar](255) NULL,
	[incidentCreated] [datetime] NULL,
	[incidentDescription] [varchar](255) NULL,
	[incidentType] [int] NOT NULL,
	[itinComplexity] [int] NOT NULL,
	[numberDaysOpen] [int] NOT NULL,
	[numberOfBdos] [int] NOT NULL,
	[remarks] [varchar](255) NULL,
	[swapId] [bigint] NOT NULL,
	[timestampClosed] [datetime] NULL,
	[timestampOpen] [datetime] NULL,
	[claim_id] [bigint] NULL,
	[file_id] [bigint] NULL,
	[airlineIncidentIdUniqueCheck]  AS (case when [airlineIncidentId] IS NULL then CONVERT([varchar](255),[id],0) else [airlineIncidentId] end),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [fsIncident_airIncID_unq] UNIQUE NONCLUSTERED 
(
	[airlineIncidentIdUniqueCheck] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[FsFile]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[FsFile](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[statusId] [int] NULL,
	[swapId] [bigint] NOT NULL,
	[validatingCompanycode] [varchar](2) NOT NULL,
	[createDate] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[FsClaim]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[FsClaim](
	[subclass_type] [varchar](16) NOT NULL,
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[airline] [varchar](255) NULL,
	[airlineClaimId] [varchar](13) NULL,
	[amountClaimed] [float] NOT NULL,
	[amountClaimedCurrency] [varchar](255) NULL,
	[amountPaid] [float] NOT NULL,
	[claimDate] [datetime] NULL,
	[claimProrateId] [int] NOT NULL,
	[claimType] [int] NOT NULL,
	[denied] [bit] NOT NULL,
	[fraudStatus] [int] NOT NULL,
	[ntIncidentId] [varchar](255) NULL,
	[privateReasonForDenial] [varchar](255) NULL,
	[publicReasonForDenial] [varchar](255) NULL,
	[statusId] [int] NOT NULL,
	[swapId] [bigint] NOT NULL,
	[travelDate] [datetime] NULL,
	[blacklist_id] [bigint] NULL,
	[file_id] [bigint] NULL,
	[Claimprorate_ID] [int] NULL,
	[ntIncident_Incident_ID] [varchar](13) NULL,
	[Status_ID] [int] NULL,
	[incident_id] [bigint] NULL,
	[amountPaidCurrency] [varchar](255) NULL,
	[claimRemark] [varchar](max) NULL,
	[createagent_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[FsAddress]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[FsAddress](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[address1] [varchar](255) NULL,
	[address2] [varchar](255) NULL,
	[city] [varchar](255) NULL,
	[country] [varchar](255) NULL,
	[lattitude] [float] NOT NULL,
	[longitude] [float] NOT NULL,
	[province] [varchar](255) NULL,
	[state] [varchar](255) NULL,
	[zip] [varchar](255) NULL,
	[person_id] [bigint] NULL,
	[reservation_id] [bigint] NULL,
	[receipt_id] [bigint] NULL,
	[normAddress] [varchar](255) NULL,
	[whitelist_id] [int] NULL,
	[geocodeType] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[fraud_address]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[fraud_address](
	[Address_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[address1] [varchar](50) NULL,
	[address2] [varchar](50) NULL,
	[city] [varchar](50) NULL,
	[state_ID] [varchar](2) NULL,
	[zip] [varchar](12) NULL,
	[countrycode_ID] [varchar](3) NULL,
	[code] [varchar](10) NULL,
	[comments] [varchar](255) NULL,
 CONSTRAINT [PK_fraud_address] PRIMARY KEY CLUSTERED 
(
	[Address_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[central_message]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[central_message](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[message] [varchar](4095) NULL,
	[messageContext] [int] NULL,
	[senderAgentId] [bigint] NOT NULL,
	[senderName] [varchar](255) NULL,
	[timestamp] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Blacklist]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Blacklist](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[description] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[billing]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[billing](
	[billing_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[companyCode] [varchar](3) NOT NULL,
	[station_id] [int] NOT NULL,
	[report_num] [varchar](13) NOT NULL,
	[create_date_time] [datetime] NULL,
	[status_change_time] [datetime] NULL,
	[agent_id] [int] NOT NULL,
 CONSTRAINT [PK_billing] PRIMARY KEY CLUSTERED 
(
	[billing_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[bdo_passenger]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[bdo_passenger](
	[bdo_passenger_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[bdo_ID] [int] NOT NULL,
	[firstname] [varchar](100) NULL,
	[middlename] [varchar](100) NULL,
	[lastname] [varchar](100) NULL,
	[address1] [varchar](100) NULL,
	[address2] [varchar](100) NULL,
	[city] [varchar](100) NULL,
	[state_ID] [varchar](2) NULL,
	[province] [varchar](100) NULL,
	[countrycode_ID] [varchar](3) NULL,
	[zip] [varchar](100) NULL,
	[hotel] [varchar](100) NULL,
	[homephone] [varchar](100) NULL,
	[workphone] [varchar](100) NULL,
	[mobile] [varchar](100) NULL,
	[pager] [varchar](100) NULL,
	[altphone] [varchar](100) NULL,
	[email] [varchar](100) NULL,
	[valid_bdate] [datetime] NULL,
	[valid_edate] [datetime] NULL,
	[hotelphone] [varchar](100) NULL,
 CONSTRAINT [PK_bdo_passenger] PRIMARY KEY CLUSTERED 
(
	[bdo_passenger_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[file_control]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[file_control](
	[control_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[file_ref_number] [varchar](100) NULL,
	[start_date] [datetime] NOT NULL,
	[end_date] [datetime] NULL,
	[controlling_station_id] [int] NOT NULL,
 CONSTRAINT [PK_file_control] PRIMARY KEY CLUSTERED 
(
	[control_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[expensetype]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[expensetype](
	[Expensetype_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[description] [varchar](20) NULL,
	[companycode_ID] [varchar](3) NULL,
 CONSTRAINT [PK_expensetype] PRIMARY KEY CLUSTERED 
(
	[Expensetype_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[expensepayout]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[expensepayout](
	[Expensepayout_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[createdate] [datetime] NULL,
	[paycode] [varchar](5) NULL,
	[mileageamt] [int] NULL,
	[voucheramt] [float] NULL,
	[currency_ID] [varchar](3) NULL,
	[draft] [varchar](30) NULL,
	[draftreqdate] [datetime] NULL,
	[draftpaiddate] [datetime] NULL,
	[checkamt] [float] NULL,
	[comments] [varchar](255) NULL,
	[expenselocation_ID] [int] NULL,
	[expensetype_ID] [int] NULL,
	[status_ID] [int] NULL,
	[agent_ID] [int] NULL,
	[station_ID] [int] NULL,
	[approval_date] [datetime] NULL,
	[incidental_amount_auth] [float] NULL,
	[incidental_amount_claim] [float] NULL,
	[creditcard_refund] [float] NULL,
	[voucher_exp] [smalldatetime] NULL,
	[incident_ID] [varchar](13) NULL,
	[bdo_id] [int] NULL,
	[ohd_id] [varchar](13) NULL,
 CONSTRAINT [PK_expensepayout] PRIMARY KEY CLUSTERED 
(
	[Expensepayout_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[expenselocation]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[expenselocation](
	[Expenselocation_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[description] [varchar](20) NULL,
	[locale] [varchar](2) NULL,
	[companycode_ID] [varchar](3) NULL,
 CONSTRAINT [PK_expenselocation] PRIMARY KEY CLUSTERED 
(
	[Expenselocation_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[expense_comment]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[expense_comment](
	[expensepayout_id] [int] NOT NULL,
	[agent_id] [int] NOT NULL,
	[createDate] [datetime] NOT NULL,
	[content] [varchar](255) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[dispute]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[dispute](
	[dispute_res_id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[beforeDisputeLossCode] [int] NOT NULL,
	[created_timestamp] [datetime] NULL,
	[determinedLossCode] [int] NOT NULL,
	[disputeExplanation] [varchar](1550) NULL,
	[resolutionRemarks] [varchar](1550) NULL,
	[suggestedLossCode] [int] NOT NULL,
	[typeOfChange] [varchar](255) NULL,
	[before_dispute_fault_station_ID] [int] NOT NULL,
	[determined_station_ID] [int] NOT NULL,
	[dispute_agent_ID] [int] NOT NULL,
	[Incident_ID] [varchar](13) NOT NULL,
	[resolution_agent_ID] [int] NOT NULL,
	[status_ID] [int] NOT NULL,
	[suggested_station_ID] [int] NOT NULL,
	[resolution_timestamp] [datetime] NULL,
	[Lock_ID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[dispute_res_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[delivery_instructions]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[delivery_instructions](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[instructions] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 80) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[delivercompany]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[delivercompany](
	[delivercompany_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[name] [varchar](50) NULL,
	[address] [varchar](50) NULL,
	[active] [int] NULL,
	[companycode_ID] [varchar](3) NULL,
	[phone] [varchar](50) NULL,
	[delivery_integration_type] [varchar](10) NULL,
	[integration_key] [varchar](20) NULL,
 CONSTRAINT [PK_delivercompany] PRIMARY KEY CLUSTERED 
(
	[delivercompany_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[z_us_24hrs_email]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[z_us_24hrs_email](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[incident_ID] [varchar](13) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[z_task_manager_notice]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[z_task_manager_notice](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[station_id] [int] NOT NULL,
	[lastUpdated] [datetime] NOT NULL,
	[boolKey1] [tinyint] NOT NULL,
	[boolKey2] [tinyint] NOT NULL,
	[textArea1] [text] NULL,
	[textArea2] [text] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[z_crm_map]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[z_crm_map](
	[CountryCode_ID] [varchar](3) NOT NULL,
	[country] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[CountryCode_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[z_b6_claim_settlement_inventory]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[z_b6_claim_settlement_inventory](
	[inventoryId] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[categoryType_ID] [int] NOT NULL,
	[description] [varchar](255) NULL,
	[position] [int] NOT NULL,
	[bagId] [numeric](19, 0) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[inventoryId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[z_b6_claim_settlement_bag]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[z_b6_claim_settlement_bag](
	[bagId] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[color] [varchar](2) NULL,
	[manufacturer] [varchar](100) NULL,
	[position] [int] NOT NULL,
	[type] [varchar](2) NULL,
	[claimSettlementId] [numeric](19, 0) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[bagId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[z_b6_claim_settlement]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[z_b6_claim_settlement](
	[claimSettlementId] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[address1] [varchar](40) NULL,
	[address2] [varchar](40) NULL,
	[businessPhone] [varchar](25) NULL,
	[city] [varchar](50) NULL,
	[claimAgent] [varchar](25) NULL,
	[claimType] [varchar](25) NULL,
	[comments] [varchar](255) NULL,
	[countryCode_ID] [varchar](2) NULL,
	[dateStatusChange] [datetime] NULL,
	[dateTakeover] [datetime] NULL,
	[depreciationComplete] [datetime] NULL,
	[depreciationDue] [datetime] NULL,
	[email] [varchar](50) NULL,
	[fax] [varchar](25) NULL,
	[firstContact] [datetime] NULL,
	[firstName] [varchar](20) NULL,
	[homePhone] [varchar](25) NULL,
	[language] [varchar](25) NULL,
	[lastName] [varchar](20) NULL,
	[membership] [varchar](20) NULL,
	[mobilePhone] [varchar](25) NULL,
	[newComment] [varchar](255) NULL,
	[offerDue] [datetime] NULL,
	[offerSent] [datetime] NULL,
	[offerSentVia] [varchar](20) NULL,
	[pager] [varchar](25) NULL,
	[pplcDue] [datetime] NULL,
	[pplcReceived] [datetime] NULL,
	[pplcSent] [datetime] NULL,
	[pplcVia] [varchar](20) NULL,
	[province] [varchar](100) NULL,
	[releaseDue] [datetime] NULL,
	[revisitRequested] [datetime] NULL,
	[revisitedBy] [varchar](20) NULL,
	[salutation] [int] NOT NULL,
	[secondContact] [datetime] NULL,
	[state_ID] [varchar](2) NULL,
	[verifyAddress] [tinyint] NOT NULL,
	[verifyBagColor] [tinyint] NOT NULL,
	[verifyBrand] [tinyint] NOT NULL,
	[verifyContents] [tinyint] NOT NULL,
	[verifyEmail] [tinyint] NOT NULL,
	[verifyFraudCC] [tinyint] NOT NULL,
	[verifyFraudName] [tinyint] NOT NULL,
	[verifyFraudPhone] [tinyint] NOT NULL,
	[verifyPhone] [tinyint] NOT NULL,
	[verifyTrace1] [tinyint] NOT NULL,
	[verifyTrace2] [tinyint] NOT NULL,
	[verifyTrace3] [tinyint] NOT NULL,
	[zip] [varchar](11) NULL,
	[incident_id] [varchar](13) NOT NULL,
	[overall_weight] [float] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[claimSettlementId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[z_b6_audit_claim_settlement_inventory]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[z_b6_audit_claim_settlement_inventory](
	[auditInventoryId] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[categoryType_ID] [int] NOT NULL,
	[description] [varchar](255) NULL,
	[position] [int] NOT NULL,
	[auditBagId] [numeric](19, 0) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[auditInventoryId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[z_b6_audit_claim_settlement_bag]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[z_b6_audit_claim_settlement_bag](
	[auditBagId] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[color] [varchar](2) NULL,
	[manufacturer] [varchar](100) NULL,
	[position] [int] NOT NULL,
	[type] [varchar](2) NULL,
	[auditClaimSettlementId] [numeric](19, 0) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[auditBagId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[z_b6_audit_claim_settlement]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[z_b6_audit_claim_settlement](
	[auditClaimSettlementId] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[address1] [varchar](40) NULL,
	[address2] [varchar](40) NULL,
	[businessPhone] [varchar](25) NULL,
	[city] [varchar](50) NULL,
	[claimAgent] [varchar](25) NULL,
	[claimSettlementId] [numeric](19, 0) NOT NULL,
	[claimType] [varchar](25) NULL,
	[comments] [varchar](255) NULL,
	[countryCode_ID] [varchar](2) NULL,
	[dateStatusChange] [datetime] NULL,
	[dateTakeover] [datetime] NULL,
	[depreciationComplete] [datetime] NULL,
	[depreciationDue] [datetime] NULL,
	[email] [varchar](50) NULL,
	[fax] [varchar](25) NULL,
	[firstContact] [datetime] NULL,
	[firstName] [varchar](20) NULL,
	[homePhone] [varchar](25) NULL,
	[language] [varchar](25) NULL,
	[lastName] [varchar](20) NULL,
	[membership] [varchar](20) NULL,
	[mobilePhone] [varchar](25) NULL,
	[newComment] [varchar](255) NULL,
	[offerDue] [datetime] NULL,
	[offerSent] [datetime] NULL,
	[offerSentVia] [varchar](20) NULL,
	[pager] [varchar](25) NULL,
	[pplcDue] [datetime] NULL,
	[pplcReceived] [datetime] NULL,
	[pplcSent] [datetime] NULL,
	[pplcVia] [varchar](20) NULL,
	[province] [varchar](100) NULL,
	[reason_modified] [varchar](255) NULL,
	[releaseDue] [datetime] NULL,
	[revisitRequested] [datetime] NULL,
	[revisitedBy] [varchar](20) NULL,
	[salutation] [int] NOT NULL,
	[secondContact] [datetime] NULL,
	[state_ID] [varchar](2) NULL,
	[time_modified] [datetime] NULL,
	[verifyAddress] [tinyint] NOT NULL,
	[verifyBagColor] [tinyint] NOT NULL,
	[verifyBrand] [tinyint] NOT NULL,
	[verifyContents] [tinyint] NOT NULL,
	[verifyEmail] [tinyint] NOT NULL,
	[verifyFraudCC] [tinyint] NOT NULL,
	[verifyFraudName] [tinyint] NOT NULL,
	[verifyFraudPhone] [tinyint] NOT NULL,
	[verifyPhone] [tinyint] NOT NULL,
	[verifyTrace1] [tinyint] NOT NULL,
	[verifyTrace2] [tinyint] NOT NULL,
	[verifyTrace3] [tinyint] NOT NULL,
	[zip] [varchar](11) NULL,
	[incident_id] [varchar](13) NOT NULL,
	[modifyingAgent] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[auditClaimSettlementId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[xdescelement]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[xdescelement](
	[XDesc_ID] [smallint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[code] [char](1) NULL,
	[description] [varchar](50) NULL,
 CONSTRAINT [PK_xdescelement] PRIMARY KEY CLUSTERED 
(
	[XDesc_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[wtq_teletype]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wtq_teletype](
	[wt_queue_id] [numeric](19, 0) NOT NULL,
	[ttype_address] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[wt_queue_id] ASC,
	[ttype_address] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[WTQ_SEGMENT]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[WTQ_SEGMENT](
	[segment_id] [numeric](19, 0) NOT NULL,
	[airline] [varchar](255) NULL,
	[arrivalTime] [datetime] NULL,
	[arrivedate] [datetime] NULL,
	[departdate] [datetime] NULL,
	[departureTime] [datetime] NULL,
	[flightnum] [varchar](255) NULL,
	[legfrom] [varchar](255) NULL,
	[legto] [varchar](255) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[wtq_pxf]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wtq_pxf](
	[ID] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[airline_1] [varchar](255) NULL,
	[airline_2] [varchar](255) NULL,
	[airline_3] [varchar](255) NULL,
	[airline_4] [varchar](255) NULL,
	[airline_5] [varchar](255) NULL,
	[area_1] [varchar](255) NULL,
	[area_2] [varchar](255) NULL,
	[area_3] [varchar](255) NULL,
	[area_4] [varchar](255) NULL,
	[area_5] [varchar](255) NULL,
	[destination] [int] NOT NULL,
	[station_1] [varchar](3) NULL,
	[station_2] [varchar](3) NULL,
	[station_3] [varchar](3) NULL,
	[station_4] [varchar](3) NULL,
	[station_5] [varchar](3) NULL,
	[wt_queue_id] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[WTQ_OHD_TAG]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[WTQ_OHD_TAG](
	[wt_queue_id] [numeric](19, 0) NOT NULL,
	[ohdTags_OHD_ID] [varchar](255) NOT NULL,
UNIQUE NONCLUSTERED 
(
	[ohdTags_OHD_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[wtq_name]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wtq_name](
	[wt_queue_id] [numeric](19, 0) NOT NULL,
	[pax_name] [varchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[wt_queue_id] ASC,
	[pax_name] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[wtcompany]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wtcompany](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[wtCompanyCode] [varchar](3) NULL,
	[company_id] [varchar](3) NULL,
	[companyName] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 80) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[WT_WS_TRANS_LOG]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[WT_WS_TRANS_LOG](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[gmttime] [datetime] NULL,
	[description] [varchar](60) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[WT_WEB_TRANS_LOG]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[WT_WEB_TRANS_LOG](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[gmttime] [datetime] NOT NULL,
	[username] [varchar](20) NOT NULL,
	[description] [varchar](60) NOT NULL,
	[uri] [varchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[wt_tty]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wt_tty](
	[tty_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[teleType_address1] [varchar](100) NOT NULL,
	[teleType_address2] [varchar](100) NOT NULL,
	[teleType_address3] [varchar](100) NOT NULL,
	[teleType_address4] [varchar](100) NOT NULL,
	[origin_address] [varchar](100) NOT NULL,
	[airline_code] [varchar](100) NOT NULL,
	[file_type1] [varchar](100) NULL,
	[file_type2] [varchar](100) NULL,
	[file_type3] [varchar](100) NULL,
	[file_type4] [varchar](100) NULL,
	[file_reference1] [varchar](100) NULL,
	[file_reference2] [varchar](100) NULL,
	[file_reference3] [varchar](100) NULL,
	[file_reference4] [varchar](100) NULL,
	[tty_status] [int] NOT NULL,
	[tty_station_id] [int] NOT NULL,
	[tty_agent_id] [int] NOT NULL,
	[send_time] [datetime] NOT NULL,
	[text] [text] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[tty_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[wt_transaction]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wt_transaction](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[createDate] [datetime] NULL,
	[duration] [numeric](19, 0) NOT NULL,
	[error] [varchar](100) NULL,
	[result] [varchar](20) NULL,
	[txInputData] [varchar](1020) NULL,
	[txOutputData] [varchar](1020) NULL,
	[txType] [varchar](40) NOT NULL,
	[incident_id] [varchar](255) NULL,
	[ohd_id] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[wt_roh]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wt_roh](
	[wt_roh_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[wt_ahl_id] [varchar](13) NOT NULL,
	[wt_ohd_id] [varchar](13) NOT NULL,
	[fi] [varchar](100) NOT NULL,
	[ag] [varchar](100) NOT NULL,
	[teletype_address1] [varchar](100) NOT NULL,
	[teletype_address2] [varchar](100) NOT NULL,
	[teletype_address3] [varchar](100) NOT NULL,
	[teletype_address4] [varchar](100) NOT NULL,
	[roh_status] [int] NOT NULL,
	[roh_station_id] [int] NOT NULL,
	[roh_agent_id] [int] NOT NULL,
	[lname] [varchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[wt_roh_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[wt_queue]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wt_queue](
	[wtq_action] [varchar](31) NOT NULL,
	[wt_queue_id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[attempts] [int] NOT NULL,
	[createdate] [datetime] NULL,
	[wtq_status] [varchar](20) NOT NULL,
	[af_id] [varchar](20) NULL,
	[fwdDestinationAirline] [varchar](3) NULL,
	[fwdDestinationStation] [varchar](4) NULL,
	[fwdExpediteNum] [varchar](20) NULL,
	[fwdHandleCopy] [varchar](1) NULL,
	[matchingAhl] [varchar](20) NULL,
	[supInfo] [varchar](60) NULL,
	[lossCode] [int] NULL,
	[lossComments] [varchar](255) NULL,
	[furtherInfo] [varchar](3000) NULL,
	[wt_ohd] [varchar](15) NULL,
	[agent_id] [int] NOT NULL,
	[replacement_id] [numeric](19, 0) NULL,
	[ohd_id] [varchar](255) NULL,
	[incident_id] [varchar](255) NULL,
	[bdo_id] [int] NULL,
	[bag_tag] [varchar](11) NULL,
	[from_station] [varchar](5) NULL,
	[from_airline] [varchar](5) NULL,
	[pxf_ID] [numeric](19, 0) NULL,
	[fwdTagNum] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[wt_queue_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[WT_ISHARES_TRANS_LOG]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[WT_ISHARES_TRANS_LOG](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[gmttime] [datetime] NULL,
	[username] [varchar](20) NOT NULL,
	[description] [varchar](60) NOT NULL,
	[uri] [varchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[AccessRequest]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[AccessRequest](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[requestedAgent] [varchar](255) NULL,
	[requestedAirline] [varchar](255) NULL,
	[requestedDate] [datetime] NULL,
	[responseAgent] [varchar](255) NULL,
	[responseDate] [datetime] NULL,
	[status] [varchar](20) NOT NULL,
	[file_id] [bigint] NULL,
	[matchHistory_id] [bigint] NULL,
	[message_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[oc_phone]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[oc_phone](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[phoneNumber] [varchar](20) NULL,
	[phoneType] [varchar](10) NULL,
	[claimId] [numeric](19, 0) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[oc_passenger]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[oc_passenger](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[claimId] [int] NULL,
	[accept] [varchar](20) NULL,
	[firstName] [varchar](20) NULL,
	[lastName] [varchar](20) NULL,
	[middleInitial] [varchar](1) NULL,
	[salutation] [varchar](10) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[oc_itinerary]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[oc_itinerary](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[airline] [varchar](20) NULL,
	[arrivalCity] [varchar](3) NULL,
	[date] [datetime] NULL,
	[departureCity] [varchar](3) NULL,
	[flightNum] [varchar](5) NULL,
	[claimId] [numeric](19, 0) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[oc_file]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[oc_file](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[dateUploaded] [datetime] NULL,
	[filename] [varchar](50) NULL,
	[claimId] [numeric](19, 0) NOT NULL,
	[interim] [bit] NULL,
	[path] [varchar](1024) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[oc_content]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[oc_content](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[article] [varchar](50) NULL,
	[brand] [varchar](50) NULL,
	[color] [varchar](50) NULL,
	[currency] [varchar](3) NULL,
	[male] [int] NULL,
	[price] [float] NOT NULL,
	[purchasedAt] [varchar](50) NULL,
	[purchasedDate] [varchar](50) NULL,
	[size] [varchar](50) NULL,
	[bagId] [numeric](19, 0) NOT NULL,
	[contentOwner] [varchar](50) NULL,
	[quantity] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[oc_claim]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[oc_claim](
	[claimId] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[attemptToClaimAtArrival] [tinyint] NOT NULL,
	[bagClearCustoms] [tinyint] NOT NULL,
	[baggageReroutedEnRoute] [tinyint] NOT NULL,
	[bagsDelayed] [int] NOT NULL,
	[bagsStillMissing] [int] NOT NULL,
	[bagsTravelWith] [int] NOT NULL,
	[businessName] [varchar](50) NULL,
	[chargedForExcessBaggage] [tinyint] NOT NULL,
	[checkedLocation] [varchar](20) NULL,
	[comments] [varchar](1500) NULL,
	[declaredCurrency] [varchar](3) NULL,
	[declaredExcessValue] [tinyint] NOT NULL,
	[declaredValue] [varchar](20) NULL,
	[differentClaimCheck] [tinyint] NOT NULL,
	[emailAddress] [varchar](40) NULL,
	[filedPreviousAirline] [varchar](50) NULL,
	[filedPreviousClaim] [tinyint] NOT NULL,
	[filedPreviousClaimant] [varchar](50) NULL,
	[filedPrevoiusDate] [datetime] NULL,
	[frequentFlierNumber] [varchar](20) NULL,
	[haveToRecheck] [tinyint] NOT NULL,
	[lastSawBaggage] [varchar](50) NULL,
	[occupation] [varchar](50) NULL,
	[passengersTravelingWithYou] [int] NOT NULL,
	[reportedToAnotherAirline] [tinyint] NOT NULL,
	[reroutedAirlineCity] [varchar](20) NULL,
	[reroutedReason] [varchar](50) NULL,
	[socialSecurity] [varchar](20) NULL,
	[status] [varchar](20) NULL,
	[submitDate] [datetime] NULL,
	[ticketWithAnotherAirline] [tinyint] NOT NULL,
	[tsaInspected] [tinyint] NOT NULL,
	[tsaInspectionLocation] [varchar](50) NULL,
	[tsaNotePresent] [tinyint] NOT NULL,
	[wasBagInspected] [tinyint] NOT NULL,
	[whereDidYouFileReport] [varchar](50) NULL,
	[whereWasBaggageChecked] [varchar](20) NULL,
	[incident_id] [varchar](13) NOT NULL,
	[mailingAddress_id] [numeric](19, 0) NULL,
	[permanentAddress_id] [numeric](19, 0) NULL,
	[paxClaimAmount] [varchar](255) NULL,
	[paxClaimDate] [varchar](255) NULL,
	[paxIpAddress] [varchar](255) NULL,
	[bagWeight] [varchar](20) NULL,
	[paxClaimAmountCurrency] [varchar](3) NULL,
	[checkedLocationDescription] [varchar](20) NULL,
	[reportedAirline] [varchar](2) NULL,
	[reportedCity] [varchar](20) NULL,
	[reportedFileNumber] [varchar](20) NULL,
	[privateInsurance] [tinyint] NULL,
	[privateInsuranceName] [varchar](50) NULL,
	[privateInsuranceAddr] [varchar](100) NULL,
	[ticketNumber] [varchar](20) NULL,
	[claimType] [int] NULL,
	[bagReceivedDate] [datetime] NULL,
	[foreignCurrencyEmail] [varchar](100) NULL,
	[requestForeignCurrency] [int] NULL,
	[reasonForTravel] [varchar](10) NULL,
	[lengthOfStay] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[claimId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[oc_bag]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[oc_bag](
	[bagId] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[bagArrive] [tinyint] NOT NULL,
	[bagColor] [varchar](2) NULL,
	[bagType] [varchar](2) NULL,
	[brand] [varchar](25) NULL,
	[externalMarkings] [varchar](50) NULL,
	[feet] [tinyint] NOT NULL,
	[hardSided] [tinyint] NOT NULL,
	[locks] [tinyint] NOT NULL,
	[nameOnBag] [varchar](40) NULL,
	[nameTag] [tinyint] NOT NULL,
	[pockets] [tinyint] NOT NULL,
	[pullStrap] [tinyint] NOT NULL,
	[purchaseDate] [datetime] NULL,
	[retractibleHandle] [tinyint] NOT NULL,
	[ribbonsOrMarkings] [tinyint] NOT NULL,
	[softSided] [tinyint] NOT NULL,
	[tag] [varchar](10) NULL,
	[trim] [tinyint] NOT NULL,
	[wheels] [tinyint] NOT NULL,
	[zippers] [tinyint] NOT NULL,
	[claimId] [numeric](19, 0) NOT NULL,
	[bagOwner] [varchar](50) NULL,
	[bagCurrency] [varchar](3) NULL,
	[bagValue] [varchar](20) NULL,
	[leather] [bit] NULL,
	[metal] [bit] NULL,
	[trimDescription] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[bagId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[oc_audit_phone]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[oc_audit_phone](
	[aid] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[phoneNumber] [varchar](20) NULL,
	[phoneType] [varchar](10) NULL,
	[auditClaimId] [numeric](19, 0) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[aid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[oc_audit_itinerary]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[oc_audit_itinerary](
	[aid] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[airline] [varchar](20) NULL,
	[arrivalCity] [varchar](3) NULL,
	[date] [datetime] NULL,
	[departureCity] [varchar](3) NULL,
	[flightNum] [varchar](5) NULL,
	[auditClaimId] [numeric](19, 0) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[aid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[oc_audit_file]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[oc_audit_file](
	[aid] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[dateUploaded] [datetime] NULL,
	[filename] [varchar](50) NULL,
	[auditClaimId] [numeric](19, 0) NOT NULL,
	[path] [varchar](1024) NULL,
PRIMARY KEY CLUSTERED 
(
	[aid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[oc_audit_content]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[oc_audit_content](
	[aid] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[article] [varchar](50) NULL,
	[brand] [varchar](50) NULL,
	[color] [varchar](50) NULL,
	[currency] [varchar](3) NULL,
	[male] [tinyint] NOT NULL,
	[price] [float] NOT NULL,
	[purchasedAt] [varchar](50) NULL,
	[purchasedDate] [varchar](50) NULL,
	[size] [varchar](50) NULL,
	[abagId] [numeric](19, 0) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[aid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[oc_audit_claim]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[oc_audit_claim](
	[auditClaimId] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[accept] [varchar](20) NULL,
	[attemptToClaimAtArrival] [tinyint] NOT NULL,
	[bagClearCustoms] [tinyint] NOT NULL,
	[baggageReroutedEnRoute] [tinyint] NOT NULL,
	[bagsDelayed] [int] NOT NULL,
	[bagsStillMissing] [int] NOT NULL,
	[bagsTravelWith] [int] NOT NULL,
	[businessName] [varchar](50) NULL,
	[chargedForExcessBaggage] [tinyint] NOT NULL,
	[checkedLocation] [varchar](20) NULL,
	[claimId] [numeric](19, 0) NOT NULL,
	[comments] [varchar](1500) NULL,
	[declaredCurrency] [varchar](3) NULL,
	[declaredExcessValue] [tinyint] NOT NULL,
	[declaredValue] [varchar](20) NULL,
	[differentClaimCheck] [tinyint] NOT NULL,
	[emailAddress] [varchar](40) NULL,
	[filedPreviousAirline] [varchar](50) NULL,
	[filedPreviousClaim] [tinyint] NOT NULL,
	[filedPreviousClaimant] [varchar](50) NULL,
	[filedPrevoiusDate] [datetime] NULL,
	[firstName] [varchar](20) NULL,
	[frequentFlierNumber] [varchar](20) NULL,
	[haveToRecheck] [tinyint] NOT NULL,
	[lastName] [varchar](20) NULL,
	[lastSawBaggage] [varchar](50) NULL,
	[middleInitial] [varchar](1) NULL,
	[occupation] [varchar](50) NULL,
	[passengersTravelingWithYou] [int] NOT NULL,
	[reportedToAnotherAirline] [tinyint] NOT NULL,
	[reroutedAirlineCity] [varchar](20) NULL,
	[reroutedReason] [varchar](50) NULL,
	[socialSecurity] [varchar](20) NULL,
	[status] [varchar](20) NULL,
	[submitDate] [datetime] NULL,
	[ticketWithAnotherAirline] [tinyint] NOT NULL,
	[time_modified] [datetime] NULL,
	[tsaInspected] [tinyint] NOT NULL,
	[tsaInspectionLocation] [varchar](50) NULL,
	[tsaNotePresent] [tinyint] NOT NULL,
	[wasBagInspected] [tinyint] NOT NULL,
	[whereDidYouFileReport] [varchar](50) NULL,
	[whereWasBaggageChecked] [varchar](20) NULL,
	[agent_id] [int] NULL,
	[incident_id] [varchar](13) NOT NULL,
	[mailingAddress_aid] [numeric](19, 0) NULL,
	[permanentAddress_aid] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[auditClaimId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[oc_audit_bag]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[oc_audit_bag](
	[abagId] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[bagArrive] [tinyint] NOT NULL,
	[bagColor] [varchar](2) NULL,
	[bagType] [varchar](2) NULL,
	[brand] [varchar](10) NULL,
	[externalMarkings] [varchar](50) NULL,
	[feet] [tinyint] NOT NULL,
	[hardSided] [tinyint] NOT NULL,
	[locks] [tinyint] NOT NULL,
	[nameOnBag] [varchar](40) NULL,
	[nameTag] [tinyint] NOT NULL,
	[pockets] [tinyint] NOT NULL,
	[pullStrap] [tinyint] NOT NULL,
	[purchaseDate] [datetime] NULL,
	[retractibleHandle] [tinyint] NOT NULL,
	[ribbonsOrMarkings] [tinyint] NOT NULL,
	[softSided] [tinyint] NOT NULL,
	[tag] [varchar](10) NULL,
	[trim] [tinyint] NOT NULL,
	[wheels] [tinyint] NOT NULL,
	[zippers] [tinyint] NOT NULL,
	[auditClaimId] [numeric](19, 0) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[abagId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[oc_audit_address]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[oc_audit_address](
	[aid] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[address1] [varchar](50) NULL,
	[address2] [varchar](50) NULL,
	[city] [varchar](50) NULL,
	[country] [varchar](3) NULL,
	[postalCode] [varchar](20) NULL,
	[stateProvince] [varchar](50) NULL,
	[auditClaimId] [numeric](19, 0) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[aid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[oc_address]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[oc_address](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[address1] [varchar](50) NULL,
	[address2] [varchar](50) NULL,
	[city] [varchar](50) NULL,
	[country] [varchar](3) NULL,
	[postalCode] [varchar](20) NULL,
	[stateProvince] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[worldtracer_actionfiles]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[worldtracer_actionfiles](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[action_file_type] [varchar](2) NOT NULL,
	[action_file_text] [text] NOT NULL,
	[day] [int] NOT NULL,
	[station] [varchar](3) NOT NULL,
	[airline] [varchar](2) NOT NULL,
	[wt_incident_id] [varchar](20) NOT NULL,
	[wt_ohd_id] [varchar](20) NOT NULL,
	[delete_trigger] [smallint] NULL,
	[percent_match] [float] NULL,
	[item_number] [int] NULL,
	[summary] [varchar](512) NULL,
	[seq] [varchar](3) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[world_tracer_account]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[world_tracer_account](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[companyCode] [varchar](2) NOT NULL,
	[instanceName] [varchar](20) NOT NULL,
	[lastReset] [datetime] NULL,
	[password] [varchar](20) NOT NULL,
	[username] [varchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[work_shift]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[work_shift](
	[shift_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[shift_code] [varchar](30) NULL,
	[shift_description] [varchar](50) NOT NULL,
	[companycode_ID] [varchar](3) NULL,
	[locale] [varchar](2) NULL,
 CONSTRAINT [PK_work_shift] PRIMARY KEY CLUSTERED 
(
	[shift_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Whitelist]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Whitelist](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[description] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[webservice_session]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[webservice_session](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[username] [varchar](15) NOT NULL,
	[companycode_id] [char](3) NOT NULL,
	[session_id] [varchar](255) NULL,
	[date_active] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[usergroup]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[usergroup](
	[UserGroup_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[companycode_ID] [varchar](3) NULL,
	[description2] [varchar](50) NULL,
	[description] [varchar](50) NULL,
 CONSTRAINT [PK_usergroup] PRIMARY KEY CLUSTERED 
(
	[UserGroup_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[timezone]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[timezone](
	[locale] [varchar](2) NOT NULL,
	[description] [varchar](150) NOT NULL,
	[timezone] [varchar](150) NOT NULL,
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
 CONSTRAINT [PK_timezone] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[timeformat]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[timeformat](
	[Timeformat_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[format] [varchar](10) NULL,
 CONSTRAINT [PK_timeformat] PRIMARY KEY CLUSTERED 
(
	[Timeformat_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tempIncident]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tempIncident](
	[incident_id] [varchar](13) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[incident_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tempBdoIncident]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tempBdoIncident](
	[incident_id] [varchar](13) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[incident_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tasks]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tasks](
	[task_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[file_ref_number] [varchar](13) NULL,
	[description] [varchar](255) NOT NULL,
	[reminder_date_time] [datetime] NULL,
	[due_date_time] [datetime] NULL,
	[priority_id] [int] NOT NULL,
	[assigningAgent] [int] NOT NULL,
	[task_status_id] [int] NOT NULL,
	[remarks] [varchar](1500) NULL,
	[station_id] [int] NULL,
	[file_type] [smallint] NULL,
	[assigned_agent_ID] [int] NULL,
 CONSTRAINT [PK_tasks] PRIMARY KEY CLUSTERED 
(
	[task_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[taskactivity]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[taskactivity](
	[taskactivity_id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[agent_ID] [int] NULL,
	[completetime] [datetime] NULL,
	[duration] [numeric](19, 0) NOT NULL,
	[resolution] [varchar](255) NULL,
	[task_id] [numeric](19, 0) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[taskactivity_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[task]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[task](
	[task_type] [varchar](16) NOT NULL,
	[task_id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[closed_timestamp] [datetime] NULL,
	[opened_timestamp] [datetime] NULL,
	[agent_ID] [int] NOT NULL,
	[status_ID] [int] NOT NULL,
	[bagbuzz_id] [numeric](19, 0) NULL,
	[dispute_res_id] [numeric](19, 0) NULL,
	[incident_id] [varchar](13) NULL,
	[deferment_timestamp] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[task_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[TABLE1]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TABLE1](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[rn] [varchar](50) NOT NULL,
	[gtsv] [float] NOT NULL,
	[minlen] [int] NOT NULL,
	[maxlen] [int] NOT NULL,
	[v10] [float] NOT NULL,
	[v9] [float] NOT NULL,
	[v8] [float] NOT NULL,
	[v7] [float] NOT NULL,
	[v6] [float] NOT NULL,
	[v5] [float] NOT NULL,
	[v4] [float] NOT NULL,
	[v3] [float] NOT NULL,
	[v2] [float] NOT NULL,
	[v1] [float] NOT NULL,
	[v0] [float] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[systemcomponents]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[systemcomponents](
	[component_id] [smallint] NULL,
	[component_name] [varchar](100) NOT NULL,
	[component_desc] [varchar](100) NULL,
	[parent_component_id] [smallint] NULL,
	[component_action_link] [varchar](100) NULL,
	[display] [smallint] NOT NULL,
	[sort_order] [smallint] NULL,
	[sort_group] [int] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[svc_profile]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[svc_profile](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[airline] [varchar](2) NULL,
	[name] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[subcompanystation]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[subcompanystation](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[subcompany_id] [int] NOT NULL,
	[station_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 80) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[subcompany]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[subcompany](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[subcompanycode] [varchar](3) NOT NULL,
	[company_id] [varchar](2) NOT NULL,
	[name] [varchar](50) NULL,
	[email_subject] [varchar](200) NULL,
	[email_path] [varchar](200) NULL,
	[email_notice_1] [int] NULL,
	[email_notice_2] [int] NULL,
	[auto_close_low] [int] NULL,
	[auto_close_high] [int] NULL,
	[email_notice_3] [int] NULL,
	[email_notice_4] [int] NULL,
	[email_notice_5] [int] NULL,
	[shippingSurcharge] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 80) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[status]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[status](
	[Status_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[description] [varchar](100) NULL,
	[table_ID] [int] NULL,
 CONSTRAINT [PK_status] PRIMARY KEY CLUSTERED 
(
	[Status_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[station]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[station](
	[Station_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[stationcode] [varchar](10) NULL,
	[companycode_ID] [varchar](3) NULL,
	[stationdesc] [varchar](255) NULL,
	[address1] [varchar](50) NULL,
	[address2] [varchar](50) NULL,
	[city] [varchar](50) NULL,
	[state_ID] [varchar](2) NULL,
	[countrycode_ID] [varchar](3) NULL,
	[zip] [varchar](11) NULL,
	[phone] [varchar](50) NULL,
	[airport_location] [varchar](100) NULL,
	[operation_hours] [varchar](100) NULL,
	[associated_airport] [varchar](3) NULL,
	[station_region] [varchar](100) NULL,
	[station_region_mgr] [varchar](100) NULL,
	[goal] [float] NOT NULL,
	[active] [int] NULL,
	[lz_id] [int] NOT NULL,
	[wt_stationcode] [varchar](10) NULL,
	[email_language] [varchar](3) NULL,
	[priority] [int] NULL,
	[region_id] [numeric](19, 0) NULL,
 CONSTRAINT [PK_station] PRIMARY KEY CLUSTERED 
(
	[Station_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[state]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[state](
	[state_id] [varchar](6) NOT NULL,
	[state] [varchar](100) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[special_flag]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[special_flag](
	[keyStr] [varchar](10) NOT NULL,
	[valueStr] [varchar](100) NULL,
	[flagResetTimestamp] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[keyStr] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Segment]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Segment](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[airline] [varchar](255) NULL,
	[arrival] [varchar](255) NULL,
	[date] [datetime] NULL,
	[departure] [varchar](255) NULL,
	[flight] [varchar](255) NULL,
	[claim_id] [bigint] NULL,
	[incident_id] [bigint] NULL,
	[reservation_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[salvage_remark]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[salvage_remark](
	[remark_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[agent_id] [int] NOT NULL,
	[createtime] [datetime] NULL,
	[remarktext] [varchar](max) NULL,
	[remarktype] [tinyint] NULL,
PRIMARY KEY CLUSTERED 
(
	[remark_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 80) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[salvage_ohd_reference]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[salvage_ohd_reference](
	[ohd_ref_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[ohd_id] [varchar](13) NOT NULL,
	[salvage_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ohd_ref_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[salvage_item]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[salvage_item](
	[item_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[description] [varchar](255) NULL,
	[lostandfound_id] [varchar](13) NULL,
	[quantity] [int] NULL,
	[type] [int] NULL,
	[box_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[item_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[salvage_box]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[salvage_box](
	[box_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[description] [varchar](255) NULL,
	[display_id] [int] NOT NULL,
	[type] [int] NULL,
	[salvage_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[box_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[salvage]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[salvage](
	[salvage_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[companycode_id] [varchar](2) NULL,
	[pickedupby_fname] [varchar](30) NULL,
	[pickedupby_lname] [varchar](30) NULL,
	[salvage_date] [datetime] NULL,
	[status] [int] NULL,
	[remark_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[salvage_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[resolution]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[resolution](
	[Resolution_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[status] [char](10) NULL,
	[locale] [char](2) NULL,
	[companycode_ID] [char](3) NULL,
 CONSTRAINT [PK_resolution] PRIMARY KEY CLUSTERED 
(
	[Resolution_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Reservation]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Reservation](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[airline] [varchar](255) NULL,
	[ccExpMonth] [int] NULL,
	[ccExpYear] [int] NULL,
	[ccNumLastFour] [varchar](255) NULL,
	[ccNumber] [varchar](255) NULL,
	[ccType] [varchar](255) NULL,
	[formOfPayment] [varchar](255) NULL,
	[itinComplexity] [int] NOT NULL,
	[recordLocator] [varchar](255) NULL,
	[ticketAmount] [float] NULL,
	[travelDate] [datetime] NULL,
	[tripLength] [int] NOT NULL,
	[ccWhitelist_id] [bigint] NULL,
	[incident_id] [bigint] NULL,
	[purchaser_id] [bigint] NULL,
	[ccFName] [varchar](255) NULL,
	[ccLName] [varchar](255) NULL,
	[ccMName] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[report]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[report](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[number] [int] NOT NULL,
	[resource_key] [varchar](36) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[remark]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[remark](
	[Remark_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[agent_ID] [int] NULL,
	[createtime] [datetime] NOT NULL,
	[remarktext] [varchar](1500) NULL,
	[Incident_ID] [varchar](13) NULL,
	[OHD_ID] [varchar](13) NULL,
	[remarktype] [int] NULL,
 CONSTRAINT [PK_remark] PRIMARY KEY CLUSTERED 
(
	[Remark_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[region]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[region](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[companycode_id] [varchar](2) NULL,
	[name] [varchar](255) NULL,
	[director] [varchar](255) NULL,
	[target] [float] NOT NULL,
	[active] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[recipient_list]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[recipient_list](
	[recipient_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[message_id] [int] NOT NULL,
	[station_id] [int] NOT NULL,
 CONSTRAINT [PK_recipient_list] PRIMARY KEY CLUSTERED 
(
	[recipient_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[prorate_itinerary]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[prorate_itinerary](
	[Prorate_Itinerary_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[claimprorate_ID] [int] NULL,
	[flightnum] [varchar](4) NULL,
	[departdate] [datetime] NULL,
	[legfrom] [varchar](3) NULL,
	[legto] [varchar](3) NULL,
	[miles] [float] NULL,
	[percentage] [float] NULL,
	[shared] [float] NULL,
	[currency_ID] [varchar](3) NULL,
	[airline] [varchar](3) NULL,
 CONSTRAINT [PK_prorate_itinerary] PRIMARY KEY CLUSTERED 
(
	[Prorate_Itinerary_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[PROPERTIES]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PROPERTIES](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[keyStr] [varchar](50) NOT NULL,
	[valueStr] [text] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[proactiveNotification]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[proactiveNotification](
	[pcn_id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[locator] [varchar](6) NULL,
	[missedFlightAirline] [varchar](2) NULL,
	[missedFlightDate] [datetime] NULL,
	[missedFlightDestination] [varchar](255) NULL,
	[missedFlightNumber] [varchar](255) NULL,
	[name] [varchar](40) NULL,
	[destinationStation_ID] [int] NOT NULL,
	[incident_ID] [varchar](13) NULL,
	[status_ID] [int] NOT NULL,
	[oia_ID] [int] NULL,
	[membershipAirline] [varchar](255) NULL,
	[membershipNumber] [varchar](255) NULL,
	[passIndex] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[pcn_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[priority]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[priority](
	[priority_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[description] [varchar](10) NOT NULL,
 CONSTRAINT [PK_priority] PRIMARY KEY CLUSTERED 
(
	[priority_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[policycomponentpermissions]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[policycomponentpermissions](
	[comp_policy_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[policy_id] [int] NULL,
	[permission_id] [int] NULL,
 CONSTRAINT [PK_policycomponentpermissions] PRIMARY KEY CLUSTERED 
(
	[comp_policy_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PnrData]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PnrData](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[airline] [varchar](255) NULL,
	[pnrData] [text] NULL,
	[recordLocator] [varchar](255) NULL,
	[reservation_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[PhoneWhiteList]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PhoneWhiteList](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[phoneNumber] [varchar](255) NULL,
	[description] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Phone]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Phone](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[phoneNumber] [varchar](255) NULL,
	[type] [int] NOT NULL,
	[incident_id] [bigint] NULL,
	[person_id] [bigint] NULL,
	[reservation_id] [bigint] NULL,
	[receipt_id] [bigint] NULL,
	[whitelist_id] [int] NULL,
	[association] [varchar](255) NULL,
	[claim_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Person]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Person](
	[id] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[ccContact] [bit] NOT NULL,
	[dateOfBirth] [datetime] NULL,
	[description] [varchar](255) NULL,
	[driversLicenseCountry] [varchar](255) NULL,
	[driversLicenseNumber] [varchar](255) NULL,
	[emailAddress] [varchar](255) NULL,
	[ffAirline] [varchar](255) NULL,
	[ffNumber] [varchar](255) NULL,
	[firstName] [varchar](255) NULL,
	[firstNameDmp] [varchar](255) NULL,
	[firstNameSoundex] [varchar](255) NULL,
	[lastName] [varchar](255) NULL,
	[lastNameDmp] [varchar](255) NULL,
	[lastNameSoundex] [varchar](255) NULL,
	[middleName] [varchar](255) NULL,
	[passportIssuer] [varchar](255) NULL,
	[passportNumber] [varchar](255) NULL,
	[socialSecurity] [varchar](255) NULL,
	[whiteListed] [bit] NOT NULL,
	[claim_id] [bigint] NULL,
	[incident_id] [bigint] NULL,
	[reservation_id] [bigint] NULL,
	[driversLicenseState] [varchar](255) NULL,
	[driversLicenseProvince] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[permission]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[permission](
	[permission_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[permission_name] [varchar](25) NULL,
	[permission_desc] [varchar](50) NULL,
 CONSTRAINT [PK_permission] PRIMARY KEY CLUSTERED 
(
	[permission_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[pax_message_trigger]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[pax_message_trigger](
	[TRIGGER_KEY] [varchar](16) NOT NULL,
	[email_content_text] [varchar](1020) NULL,
	[LANGUAGE] [varchar](2) NULL,
	[SMS_CONTENT_TEXT] [varchar](65) NULL,
	[SUBJECT_LINE] [varchar](65) NULL,
PRIMARY KEY CLUSTERED 
(
	[TRIGGER_KEY] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[pax_communication]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[pax_communication](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[acknowledge_timestamp] [datetime] NULL,
	[comment] [varchar](1500) NOT NULL,
	[createdate] [datetime] NULL,
	[status] [varchar](12) NULL,
	[acknowledge_agent_Agent_ID] [int] NULL,
	[agent_Agent_ID] [int] NULL,
	[incident_Incident_ID] [varchar](13) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[passwordhistory]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[passwordhistory](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[pcount] [int] NOT NULL,
	[agent_id] [bigint] NULL,
	[password] [varchar](512) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[passenger]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[passenger](
	[Passenger_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[incident_ID] [varchar](13) NOT NULL,
	[membership_ID] [int] NULL,
	[jobtitle] [varchar](25) NULL,
	[salutation] [int] NULL,
	[firstname] [varchar](20) NULL,
	[middlename] [varchar](20) NULL,
	[lastname] [varchar](20) NULL,
	[commonnum] [varchar](20) NULL,
	[countryofissue] [varchar](3) NULL,
	[dlstate] [char](2) NULL,
	[driverslicense] [varchar](15) NULL,
	[isprimary] [int] NULL,
	[numRonKitsIssued] [int] NULL,
	[languageKey] [varchar](50) NULL,
	[languageFreeFlow] [varchar](50) NULL,
 CONSTRAINT [PK_passenger] PRIMARY KEY CLUSTERED 
(
	[Passenger_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[other_system_information]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[other_system_information](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[incident_id] [varchar](13) NULL,
	[info] [text] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[online_incident_authorization]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[online_incident_authorization](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[expire_time] [datetime] NULL,
	[first_name] [varchar](20) NULL,
	[incident_ID] [varchar](13) NULL,
	[last_name] [varchar](20) NULL,
	[pnr] [varchar](10) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ohd_request]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ohd_request](
	[ohd_request_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[requesting_agent_id] [int] NULL,
	[forward_to_station_ID] [int] NULL,
	[incident_ID] [varchar](13) NULL,
	[ohd_id] [varchar](13) NULL,
	[requestTime] [datetime] NULL,
	[match_id] [int] NOT NULL,
	[status_id] [int] NOT NULL,
	[forward_id] [int] NULL,
	[denied] [smallint] NOT NULL,
	[denialReason] [text] NULL,
	[denialDate] [datetime] NULL,
	[reason] [varchar](1500) NULL,
 CONSTRAINT [PK_ohd_request] PRIMARY KEY CLUSTERED 
(
	[ohd_request_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ohd_range]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ohd_range](
	[companycode_ID] [char](3) NULL,
	[current_num] [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
 CONSTRAINT [PK_ohd_range] PRIMARY KEY CLUSTERED 
(
	[current_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ohd_photo]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ohd_photo](
	[Photo_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[thumbpath] [varchar](255) NULL,
	[picpath] [varchar](255) NULL,
	[ohd_id] [varchar](50) NOT NULL,
 CONSTRAINT [PK_ohd_photo] PRIMARY KEY CLUSTERED 
(
	[Photo_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ohd_passenger]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ohd_passenger](
	[passenger_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[firstname] [varchar](20) NULL,
	[middlename] [varchar](20) NULL,
	[lastname] [varchar](20) NULL,
	[OHD_ID] [varchar](13) NOT NULL,
	[isprimary] [smallint] NOT NULL,
 CONSTRAINT [PK_ohd_passenger] PRIMARY KEY CLUSTERED 
(
	[passenger_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ohd_log_itinerary]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ohd_log_itinerary](
	[Itinerary_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[itinerarytype] [smallint] NOT NULL,
	[legfrom] [varchar](3) NULL,
	[legto] [varchar](3) NULL,
	[legfrom_type] [smallint] NULL,
	[legto_type] [smallint] NULL,
	[departdate] [datetime] NULL,
	[arrivedate] [datetime] NULL,
	[schdeparttime] [datetime] NULL,
	[scharrivetime] [datetime] NULL,
	[flightnum] [varchar](4) NULL,
	[OHDLog_ID] [int] NULL,
	[airline] [varchar](3) NULL,
 CONSTRAINT [PK_ohd_log_itinerary] PRIMARY KEY CLUSTERED 
(
	[Itinerary_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ohd_log]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ohd_log](
	[OHDLog_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[expeditenum] [varchar](10) NULL,
	[message] [varchar](4000) NULL,
	[send_agent_id] [int] NOT NULL,
	[destStationCode] [int] NOT NULL,
	[ohd_id] [varchar](13) NOT NULL,
	[ohd_request_id] [int] NOT NULL,
	[forward_time] [datetime] NULL,
	[log_status] [tinyint] NOT NULL,
	[pcn] [numeric](19, 0) NULL,
 CONSTRAINT [PK_ohd_log] PRIMARY KEY CLUSTERED 
(
	[OHDLog_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ohd_itinerary]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ohd_itinerary](
	[Itinerary_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[itinerarytype] [smallint] NOT NULL,
	[legfrom] [varchar](3) NULL,
	[legto] [varchar](3) NULL,
	[legfrom_type] [smallint] NULL,
	[legto_type] [smallint] NULL,
	[departdate] [datetime] NULL,
	[arrivedate] [datetime] NULL,
	[flightnum] [varchar](4) NULL,
	[schdeparttime] [datetime] NULL,
	[scharrivetime] [datetime] NULL,
	[actdeparttime] [datetime] NULL,
	[actarrivetime] [datetime] NULL,
	[OHD_ID] [varchar](13) NOT NULL,
	[airline] [varchar](3) NULL,
 CONSTRAINT [PK_ohd_itinerary] PRIMARY KEY CLUSTERED 
(
	[Itinerary_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ohd_itemtype]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ohd_itemtype](
	[OHD_ItemType_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[itemtype] [varchar](20) NULL,
	[locale] [varchar](2) NULL,
 CONSTRAINT [PK_ohd_itemtype] PRIMARY KEY CLUSTERED 
(
	[OHD_ItemType_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ohd_inventory]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ohd_inventory](
	[OHD_Inventory_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[OHD_ID] [varchar](13) NOT NULL,
	[OHD_categorytype_ID] [int] NOT NULL,
	[description] [varchar](4000) NULL,
 CONSTRAINT [PK_ohd_inventory] PRIMARY KEY CLUSTERED 
(
	[OHD_Inventory_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ohd_categorytype]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ohd_categorytype](
	[OHD_CategoryType_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[categorytype] [varchar](50) NULL,
	[wt_category] [varchar](20) NOT NULL,
 CONSTRAINT [PK_ohd_categorytype] PRIMARY KEY CLUSTERED 
(
	[OHD_CategoryType_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ohd_address]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ohd_address](
	[Address_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[address1] [varchar](50) NULL,
	[address2] [varchar](50) NULL,
	[city] [varchar](50) NULL,
	[state_ID] [varchar](2) NULL,
	[province] [varchar](100) NULL,
	[zip] [varchar](11) NULL,
	[homephone] [varchar](25) NULL,
	[workphone] [varchar](25) NULL,
	[mobile] [varchar](25) NULL,
	[pager] [varchar](25) NULL,
	[altphone] [varchar](25) NULL,
	[email] [varchar](50) NULL,
	[addresstype] [smallint] NULL,
	[countrycode_ID] [varchar](3) NULL,
	[passenger_id] [int] NOT NULL,
 CONSTRAINT [PK_ohd_address] PRIMARY KEY CLUSTERED 
(
	[Address_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ohd]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ohd](
	[OHD_ID] [varchar](13) NOT NULL,
	[found_station_ID] [int] NULL,
	[holding_station_ID] [int] NULL,
	[membership_ID] [varchar](100) NULL,
	[record_locator] [varchar](10) NULL,
	[agent_ID] [int] NULL,
	[founddate] [datetime] NULL,
	[foundtime] [datetime] NULL,
	[claimnum] [varchar](13) NULL,
	[color] [varchar](2) NULL,
	[bagarrivedate] [datetime] NULL,
	[type] [varchar](2) NULL,
	[xdescelement_ID_1] [smallint] NULL,
	[xdescelement_ID_2] [smallint] NULL,
	[xdescelement_ID_3] [smallint] NULL,
	[manufacturer_ID] [int] NULL,
	[manufacturer_other] [varchar](100) NULL,
	[storage_location] [varchar](50) NULL,
	[close_date] [datetime] NULL,
	[lastupdated] [datetime] NOT NULL,
	[status_ID] [int] NULL,
	[firstname] [varchar](20) NULL,
	[lastname] [varchar](20) NULL,
	[middlename] [varchar](20) NULL,
	[ohd_type] [smallint] NULL,
	[disposal_status_id] [int] NULL,
	[wt_id] [varchar](15) NULL,
	[faultstation_ID] [int] NOT NULL,
	[loss_code] [int] NOT NULL,
	[wt_status] [varchar](20) NULL,
	[earlyBag] [smallint] NULL,
	[matched_incident] [varchar](13) NULL,
	[tagSentToWt] [tinyint] NOT NULL,
	[tagSentToWtStationId] [int] NOT NULL,
	[claimchecknum_leading] [varchar](1) NULL,
	[claimchecknum_ticketingcode] [varchar](3) NULL,
	[claimchecknum_carriercode] [varchar](2) NULL,
	[claimchecknum_bagnumber] [varchar](6) NULL,
	[warehouseReceivedDate] [datetime] NULL,
	[warehouseSentDate] [datetime] NULL,
	[externaldesc] [varchar](50) NULL,
 CONSTRAINT [PK_ohd] PRIMARY KEY CLUSTERED 
(
	[OHD_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[svc_world_tracer_web_account]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[svc_world_tracer_web_account](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[companyCode] [varchar](2) NULL,
	[host] [varchar](128) NULL,
	[isCaptchaRequired] [tinyint] NOT NULL,
	[isFrontend] [tinyint] NOT NULL,
	[isTrainingMode] [tinyint] NOT NULL,
	[lastReset] [datetime] NULL,
	[password] [varchar](20) NULL,
	[username] [varchar](20) NULL,
	[profile_id] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[svc_world_tracer_ishares_account]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[svc_world_tracer_ishares_account](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[companyCode] [varchar](2) NULL,
	[host] [varchar](128) NULL,
	[lastReset] [datetime] NULL,
	[password] [varchar](20) NULL,
	[sine] [varchar](20) NULL,
	[username] [varchar](20) NULL,
	[profile_id] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[svc_user]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[svc_user](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[password] [varchar](20) NULL,
	[username] [varchar](20) NULL,
	[profile_id] [numeric](19, 0) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[svc_sabre_connection]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[svc_sabre_connection](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[company] [varchar](2) NULL,
	[domain] [varchar](20) NULL,
	[endpoint] [varchar](255) NULL,
	[organization] [varchar](20) NULL,
	[password] [varchar](20) NULL,
	[pseudoCityCode] [varchar](20) NULL,
	[username] [varchar](20) NULL,
	[profile_id] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[svc_profile_permission]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[svc_profile_permission](
	[permission_id] [numeric](19, 0) NOT NULL,
	[element] [tinyint] NULL,
	[permission_type] [varchar](30) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[permission_id] ASC,
	[permission_type] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[svc_profile_parameters]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[svc_profile_parameters](
	[profile_id] [numeric](19, 0) NOT NULL,
	[element] [varchar](255) NULL,
	[parameter_type] [varchar](30) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[profile_id] ASC,
	[parameter_type] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[deliverco_station]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[deliverco_station](
	[deliverco_station_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[delivercompany_ID] [int] NOT NULL,
	[station_ID] [int] NOT NULL,
 CONSTRAINT [PK_deliverco_station] PRIMARY KEY CLUSTERED 
(
	[deliverco_station_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[deliver_servicelevel]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[deliver_servicelevel](
	[servicelevel_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[delivercompany_ID] [int] NOT NULL,
	[description] [varchar](50) NULL,
	[active] [int] NULL,
	[service_code] [varchar](20) NULL,
 CONSTRAINT [PK_deliver_servicelevel] PRIMARY KEY CLUSTERED 
(
	[servicelevel_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CHECKLIST_TASK]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CHECKLIST_TASK](
	[Task_ID] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[description] [varchar](255) NULL,
	[order_id] [numeric](19, 0) NOT NULL,
	[version_id] [numeric](19, 0) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Task_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ForwardNotice]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ForwardNotice](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[status] [int] NOT NULL,
	[ohd_log_id] [int] NULL,
	[station_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[incident]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[incident](
	[Incident_ID] [varchar](13) NOT NULL,
	[itemtype_ID] [int] NULL,
	[stationcreated_ID] [int] NULL,
	[stationassigned_ID] [int] NULL,
	[faultstation_ID] [int] NULL,
	[loss_code] [int] NULL,
	[agent_ID] [int] NULL,
	[recordlocator] [varchar](10) NULL,
	[manualreportnum] [varchar](20) NULL,
	[status_ID] [int] NULL,
	[ticketnumber] [varchar](14) NULL,
	[reportmethod] [int] NULL,
	[checkedlocation] [char](1) NULL,
	[numpassengers] [int] NULL,
	[numbagchecked] [int] NULL,
	[numbagreceived] [int] NULL,
	[voluntaryseparation] [int] NULL,
	[courtesyreport] [int] NULL,
	[tsachecked] [int] NULL,
	[customcleared] [int] NULL,
	[nonrevenue] [int] NULL,
	[lastupdated] [datetime] NOT NULL,
	[ohd_lasttraced] [datetime] NULL,
	[createdate] [datetime] NULL,
	[createtime] [datetime] NULL,
	[close_date] [datetime] NULL,
	[version] [int] NULL,
	[agentassigned_ID] [int] NULL,
	[wt_id] [varchar](15) NULL,
	[printedreceipt] [datetime] NULL,
	[wt_status] [varchar](20) NULL,
	[osi_id] [int] NULL,
	[language] [varchar](2) NULL,
	[checklist_version] [numeric](19, 0) NOT NULL,
	[overall_weight] [float] NOT NULL,
	[overall_weight_unit] [varchar](3) NULL,
	[locked] [tinyint] NOT NULL,
	[oc_claim_id] [numeric](19, 0) NULL,
	[revenue_code] [varchar](4) NULL,
	[codeLocked] [tinyint] NOT NULL,
	[stationLocked] [tinyint] NOT NULL,
	[tracing_status_id] [int] NULL,
	[deliveryInstructions_ID] [int] NULL,
	[tracingStarted] [datetime] NULL,
	[tracingComplete] [datetime] NULL,
	[tracing_agent_ID] [int] NULL,
	[wtCompanyId] [varchar](3) NULL,
	[wtStationId] [varchar](3) NULL,
 CONSTRAINT [PK_incident] PRIMARY KEY CLUSTERED 
(
	[Incident_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[message]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[message](
	[message_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[station_id] [int] NOT NULL,
	[file_ref_number] [varchar](13) NOT NULL,
	[agent_id] [int] NOT NULL,
	[file_type] [int] NOT NULL,
	[message] [varchar](4000) NULL,
	[send_date] [datetime] NOT NULL,
	[subject] [varchar](4000) NULL,
 CONSTRAINT [PK_message] PRIMARY KEY CLUSTERED 
(
	[message_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  StoredProcedure [dbo].[ntFixNullRemarks]    Script Date: 04/23/2014 09:38:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[ntFixNullRemarks] 
AS
BEGIN
update remark set remarktext = 'null2' where remarktext is null and ohd_id is not null and agent_id = 24483;
END
GO
/****** Object:  Table [dbo].[actionfile_station_counts]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[actionfile_station_counts](
	[af_station_id] [numeric](19, 0) NOT NULL,
	[dayFive] [int] NOT NULL,
	[dayFiveLoaded] [tinyint] NOT NULL,
	[dayFour] [int] NOT NULL,
	[dayFourLoaded] [tinyint] NOT NULL,
	[dayOne] [int] NOT NULL,
	[dayOneLoaded] [tinyint] NOT NULL,
	[daySeven] [int] NOT NULL,
	[daySevenLoaded] [tinyint] NOT NULL,
	[daySix] [int] NOT NULL,
	[daySixLoaded] [tinyint] NOT NULL,
	[dayThree] [int] NOT NULL,
	[dayThreeLoaded] [tinyint] NOT NULL,
	[dayTwo] [int] NOT NULL,
	[dayTwoLoaded] [tinyint] NOT NULL,
	[af_type] [varchar](3) NOT NULL,
	[af_seq] [varchar](3) NOT NULL,
 CONSTRAINT [PK__actionfile_stati__53584DE9] PRIMARY KEY CLUSTERED 
(
	[af_station_id] ASC,
	[af_type] ASC,
	[af_seq] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 100) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CHECKLIST_TASK_OPTION]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CHECKLIST_TASK_OPTION](
	[Option_ID] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[description] [varchar](255) NULL,
	[order_id] [numeric](19, 0) NOT NULL,
	[task_id] [numeric](19, 0) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Option_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  StoredProcedure [dbo].[myLoopProc]    Script Date: 04/23/2014 09:38:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE Procedure [dbo].[myLoopProc]

AS


DECLARE @IncidentID VARCHAR(13)

DECLARE @wanted_incident_id VARCHAR(13)
DECLARE @last_used varchar(13)
DECLARE @wanted_audit_incident_id int
DECLARE @wanted_assign_change_date datetime
DECLARE @most_recent_station_change int
DECLARE @inc_csr CURSOR 
SET @inc_csr = CURSOR FOR 

SELECT incident_ID FROM incident where incident_id not in (select incident_id from incident_control) order by createdate desc, createtime desc;

SET @last_used = 'ABC123';

OPEN @inc_csr
FETCH NEXT
FROM @inc_csr INTO @IncidentID
WHILE @@FETCH_STATUS = 0

BEGIN
	FETCH NEXT
	FROM @inc_csr INTO @IncidentID
	
					SET @most_recent_station_change = 
					(select max(audit_incident_id) from audit_incident 
					where incident_id = @IncidentID and stationassigned_ID != (select stationassigned_ID from incident where incident_id = @IncidentID));
					
					IF @most_recent_station_change is null 
						SET @most_recent_station_change = 0;
			
					SET @wanted_audit_incident_id =
					(select min(audit_incident_id) from audit_incident 
					where incident_id = @IncidentID and audit_incident_id > @most_recent_station_change 
					and stationassigned_ID = (select stationassigned_ID from incident where incident_id = @IncidentID));
					
					select 
						@wanted_incident_id = incident_ID, @wanted_assign_change_date = modify_time
					from audit_incident where audit_incident_id = @wanted_audit_incident_id;
					
					IF @last_used != @wanted_incident_id
						insert into incident_control (assignedDate,Incident_ID) values (@wanted_assign_change_date,@wanted_incident_id);
						
					SET @last_used = @IncidentID;
					SET @most_recent_station_change = null;
					
	
END
CLOSE @inc_csr
DEALLOCATE @inc_csr
GO
/****** Object:  Table [dbo].[message_copies]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[message_copies](
	[message_copy_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[message_id] [int] NULL,
	[station_id] [int] NULL,
	[status_id] [int] NULL,
	[subject] [varchar](4000) NULL,
	[agent_ID] [int] NOT NULL,
	[body] [varchar](4000) NULL,
 CONSTRAINT [PK_message_copies] PRIMARY KEY CLUSTERED 
(
	[message_copy_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[bdo]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[bdo](
	[BDO_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[incident_ID] [varchar](13) NULL,
	[OHD_ID] [varchar](13) NULL,
	[agent_ID] [int] NULL,
	[createdate] [datetime] NULL,
	[createtime] [datetime] NULL,
	[delivercompany_ID] [int] NULL,
	[servicelevel_ID] [int] NULL,
	[station_ID] [int] NULL,
	[companycode_ID] [varchar](3) NULL,
	[deliverydate] [datetime] NULL,
	[delivery_integration_type] [varchar](10) NULL,
	[delivery_integration_id] [varchar](20) NULL,
	[integrationdelivercompany_ID] [int] NULL,
	[delivery_comments] [text] NULL,
	[canceled] [bit] NULL,
	[lastDeliveryUpdate] [datetime] NULL,
	[deliveryStatus] [varchar](15) NULL,
 CONSTRAINT [PK_bdo] PRIMARY KEY CLUSTERED 
(
	[BDO_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  StoredProcedure [dbo].[ntsp_returnrates]    Script Date: 04/23/2014 09:38:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[ntsp_returnrates]

AS 

 

delete from z_task_manager_notice;

 

 

declare @myvariabletest as varchar(2500);

SET @myvariabletest = (select 

 

      

      '<table class="form2" cellspacing="0" cellpadding="0"><tr><td class="header" colspan="4"><span style="float: left"><b>System Return Rate Statistics (updated on the hour)</b></span><span style="float:right; margin-right: 10px"><b>24 Hour Goal: 95%</b></span></td></tr><tr><td class="header" title="Percentage of delayed files created within the last 12 hours that have been closed." align="center">12 Hour Return Rate</td><td class="header" title="Percentage of delayed files created within the last 24 hours that have been closed." align="center">24 Hour Return Rate</td><td class="header" title="Percentage of delayed files created this month that were closed within 12 hours creation." align="center">MTD 12 Hour Return Rate</td><td class="header" title="Percentage of delayed files created this month that were closed within 24 hours creation." align="center">MTD 24 Hour Return Rate</td></tr><tr><td title="Percentage of delayed files created within the last 12 hours that have been closed." align="center">' 

      + CASE WHEN (CAST(ROUND(((SUM(pvt4.J)) / (SUM(pvt4.J) +SUM(pvt4.L) + .0))*100,1) AS VARCHAR) IS NOT NULL) 

      THEN CAST(ROUND(((SUM(pvt4.J)) / (SUM(pvt4.J) +SUM(pvt4.L) + .0))*100,1) AS VARCHAR) ELSE 'n/a' END + 

      '%</td><td title="Percentage of delayed files created within the last 24 hours that have been closed." align="center">' 

      

      + CASE WHEN (CAST(ROUND(((SUM(pvt3.G)) / (SUM(pvt3.G) +SUM(pvt3.I) + .0))*100,1) AS VARCHAR) IS NOT NULL) THEN CAST(ROUND(((SUM(pvt3.G)) / (SUM(pvt3.G) +SUM(pvt3.I) + .0))*100,1) AS VARCHAR) ELSE 'n/a' END        +

      '%</td><td title="Percentage of delayed files created this month that were closed within 12 hours creation." align="center">'

      + CASE WHEN (CAST(ROUND(((SUM(pvt2.D)) / (SUM(pvt2.D) +SUM(pvt2.F) + .0))*100,1) AS VARCHAR) IS NOT NULL) THEN CAST(ROUND(((SUM(pvt2.D)) / (SUM(pvt2.D) +SUM(pvt2.F) + .0))*100,1) AS VARCHAR) ELSE 'n/a' END  +

      '%</td><td title="Percentage of delayed files created this month that were closed within 24 hours creation." align="center">' 

      

      + CASE WHEN (CAST(ROUND(((SUM(pvt1.A)) / (SUM(pvt1.A) +SUM(pvt1.C) + .0))*100,1) AS VARCHAR) IS NOT NULL) THEN CAST(ROUND(((SUM(pvt1.A)) / (SUM(pvt1.A) +SUM(pvt1.C) + .0))*100,1) AS VARCHAR) ELSE 'n/a' END + 

      '%</td></tr></table>' as HTML2

      

      

            

from

 

--- MTD 24 HOUR, SET IN STONE

 

(SELECT 

station.stationcode as Station, 

      CASE 

            WHEN datediff(hh, incident.createdate + incident.createtime - '1/1/1970',incident.close_date) < 24 THEN 'A'

 

            ELSE 'C'

      END as range

FROM incident INNER JOIN station ON incident.stationcreated_id=station.Station_ID

WHERE incident.createdate + incident.createtime - '1/1/1970' >=  (CAST(FLOOR( CAST( GETUTCDATE() AS FLOAT ) )AS DATETIME) - DAY(GETUTCDATE()) + DAY(0))

and incident.createdate + incident.createtime - '1/1/1970' < (DATEADD(Hour, -24, GETUTCDATE()))

--and station.stationcode = 'PHX'

and incident.itemtype_ID=1) as p1

PIVOT(COUNT(range) FOR range IN (A, C))AS pvt1 left outer join

 

--- MTD 12 HOUR, SET IN STONE

(SELECT 

station.stationcode as Station, 

      CASE 

            WHEN datediff(hh, incident.createdate + incident.createtime - '1/1/1970',incident.close_date) < 12 THEN 'D'

            ELSE 'F'

      END as range

FROM incident INNER JOIN station ON incident.stationcreated_id=station.Station_ID

WHERE incident.createdate + incident.createtime - '1/1/1970' >=  (CAST(FLOOR( CAST( GETUTCDATE() AS FLOAT ) )AS DATETIME) - DAY(GETUTCDATE()) + DAY(0))

and incident.createdate + incident.createtime - '1/1/1970' < (DATEADD(Hour, -12, GETUTCDATE()))

--and station.stationcode = 'PHX'

and incident.itemtype_ID=1) as p2

PIVOT(COUNT(range) FOR range IN (D, F))AS pvt2 on pvt1.station = pvt2.station left outer join

 

--- LIVE 24 HOUR

(SELECT 

station.stationcode as Station, 

      CASE 

            WHEN datediff(hh, incident.createdate + incident.createtime - '1/1/1970',incident.close_date) < 24 THEN 'G'

            ELSE 'I'

      END as range

FROM incident INNER JOIN station ON incident.stationcreated_id=station.Station_ID

WHERE incident.createdate + incident.createtime - '1/1/1970' >=  (DATEADD(Hour, -24, GETUTCDATE()))

-- REMOVED FOR LIVE and incident.createdate < (CAST(FLOOR( CAST( GETUTCDATE() AS FLOAT ) )AS DATETIME))

--and station.stationcode = 'PHX'

and incident.itemtype_ID=1) as p

PIVOT(COUNT(range) FOR range IN (G, I))AS pvt3 on pvt1.station = pvt3.station left outer join 

 

 

--- LIVE 12 HOUR

(SELECT 

station.stationcode as Station, 

      CASE 

            WHEN datediff(hh, incident.createdate + incident.createtime - '1/1/1970',incident.close_date) < 12 THEN 'J'

            ELSE 'L'

      END as range

FROM incident INNER JOIN station ON incident.stationcreated_id=station.Station_ID

WHERE incident.createdate + incident.createtime - '1/1/1970' >=  (DATEADD(Hour, -12, GETUTCDATE()))

-- REMOVED FOR LIVE and incident.createdate < (DATEADD(Hour, -24, CAST(FLOOR( CAST( GETUTCDATE() AS FLOAT ) )AS DATETIME)))

--and station.stationcode = 'PHX'

and incident.itemtype_ID=1) as p

PIVOT(COUNT(range) FOR range IN (J,L))AS pvt4 on pvt1.station = pvt4.station);

 

 

insert into z_task_manager_notice (station_id, lastUpdated, textArea1, textArea2) 

select s.station_id as station_id, getutcdate() as lastUpdated, CASE WHEN s.stationcode in ('HDQ', 'HQ1','HQ2','HQ3','HQ4','HQ5') THEN null ELSE

      '<table class="form2" cellspacing="0" cellpadding="0"><tr><td class="header" colspan="4"><span style="float: left"><b>' + pvt1.station + ' Return Rate Statistics  (updated on the hour)</b></span><span style="float:right; margin-right: 10px"><b>24 Hour Goal: 95%</b></span></td></tr><tr><td class="header" title="Percentage of delayed files created within the last 12 hours that have been closed." align="center">12 Hour Return Rate</td><td class="header" title="Percentage of delayed files created within the last 24 hours that have been closed." align="center">24 Hour Return Rate</td><td class="header" title="Percentage of delayed files created this month that were closed within 12 hours creation." align="center">MTD 12 Hour Return Rate</td><td class="header" title="Percentage of delayed files created this month that were closed within 24 hours creation." align="center">MTD 24 Hour Return Rate</td></tr><tr><td title="Percentage of delayed files created within the last 12 hours that have been closed." align="center">' 

      + CASE WHEN (CAST(ROUND(((pvt4.J) / (pvt4.J +pvt4.L + .0))*100,1) AS VARCHAR) IS NOT NULL) THEN CAST(ROUND(((pvt4.J) / (pvt4.J +pvt4.L + .0))*100,1) AS VARCHAR) ELSE 'n/a' END + 

      '%</td><td title="Percentage of delayed files created within the last 24 hours that have been closed." align="center">' 

      + CASE WHEN (CAST(ROUND(((pvt3.G) / (pvt3.G +pvt3.I + .0))*100,1) AS VARCHAR) IS NOT NULL) THEN CAST(ROUND(((pvt3.G) / (pvt3.G +pvt3.I + .0))*100,1) AS VARCHAR) ELSE 'n/a' END        +

      '%</td><td title="Percentage of delayed files created this month that were closed within 12 hours creation." align="center">'

      + CASE WHEN (CAST(ROUND(((pvt2.D) / (pvt2.D +pvt2.F + .0))*100,1) AS VARCHAR) IS NOT NULL) THEN CAST(ROUND(((pvt2.D) / (pvt2.D +pvt2.F + .0))*100,1) AS VARCHAR) ELSE 'n/a' END  +

      '%</td><td title="Percentage of delayed files created this month that were closed within 24 hours creation." align="center">' 

      + CASE WHEN (CAST(ROUND(((pvt1.A) / (pvt1.A +pvt1.C + .0))*100,1) AS VARCHAR) IS NOT NULL) THEN CAST(ROUND(((pvt1.A) / (pvt1.A +pvt1.C + .0))*100,1) AS VARCHAR) ELSE 'n/a' END + 

      '%</td></tr></table>' END as textArea1,  @myvariabletest as textArea2

from

station s left outer join

--- MTD 24 HOUR, SET IN STONE

(SELECT 

station.stationcode as Station, station.station_id,

      CASE 

            WHEN datediff(hh, incident.createdate + incident.createtime - '1/1/1970',incident.close_date) < 24 THEN 'A'

            ELSE 'C'

      END as range

FROM incident INNER JOIN station ON incident.stationcreated_id=station.Station_ID

WHERE incident.createdate + incident.createtime - '1/1/1970' >=  (CAST(FLOOR( CAST( GETUTCDATE() AS FLOAT ) )AS DATETIME) - DAY(GETUTCDATE()) + DAY(0))

and incident.createdate + incident.createtime - '1/1/1970' < (DATEADD(Hour, -24, GETUTCDATE()))

--and station.stationcode = 'PHX'

and incident.itemtype_ID=1) as p1

PIVOT(COUNT(range) FOR range IN (A, C))AS pvt1 on pvt1.station_id = s.station_id left outer join

 

--- MTD 12 HOUR, SET IN STONE

(SELECT 

station.stationcode as Station, station.station_id,

      CASE 

            WHEN datediff(hh, incident.createdate + incident.createtime - '1/1/1970',incident.close_date) < 12 THEN 'D'

            ELSE 'F'

      END as range

FROM incident INNER JOIN station ON incident.stationcreated_id=station.Station_ID

WHERE incident.createdate + incident.createtime - '1/1/1970' >=  (CAST(FLOOR( CAST( GETUTCDATE() AS FLOAT ) )AS DATETIME) - DAY(GETUTCDATE()) + DAY(0))

and incident.createdate + incident.createtime - '1/1/1970' < (DATEADD(Hour, -12, GETUTCDATE()))

--and station.stationcode = 'PHX'

and incident.itemtype_ID=1) as p2

PIVOT(COUNT(range) FOR range IN (D, F))AS pvt2 on s.station_id = pvt2.station_ID left outer join

 

--- LIVE 24 HOUR

(SELECT 

station.stationcode as Station, station.station_id,

      CASE 

            WHEN datediff(hh, incident.createdate + incident.createtime - '1/1/1970',incident.close_date) < 24 THEN 'G'

            ELSE 'I'

      END as range

FROM incident INNER JOIN station ON incident.stationcreated_id=station.Station_ID

WHERE incident.createdate + incident.createtime - '1/1/1970' >=  (DATEADD(Hour, -24, GETUTCDATE()))

-- REMOVED FOR LIVE and incident.createdate < (CAST(FLOOR( CAST( GETUTCDATE() AS FLOAT ) )AS DATETIME))

--and station.stationcode = 'PHX'

and incident.itemtype_ID=1) as p

PIVOT(COUNT(range) FOR range IN (G, I))AS pvt3 on s.station_ID = pvt3.station_ID left outer join 

 

 

--- LIVE 12 HOUR

(SELECT 

station.stationcode as Station, station.station_id,

      CASE 

            WHEN datediff(hh, incident.createdate + incident.createtime - '1/1/1970',incident.close_date) < 12 THEN 'J'

            ELSE 'L'

      END as range

FROM incident INNER JOIN station ON incident.stationcreated_id=station.Station_ID

WHERE incident.createdate + incident.createtime - '1/1/1970' >=  (DATEADD(Hour, -12, GETUTCDATE()))

-- REMOVED FOR LIVE and incident.createdate < (DATEADD(Hour, -24, CAST(FLOOR( CAST( GETUTCDATE() AS FLOAT ) )AS DATETIME)))

--and station.stationcode = 'PHX'

and incident.itemtype_ID=1) as p

PIVOT(COUNT(range) FOR range IN (J,L))AS pvt4 on s.station_ID = pvt4.station_ID  where s.companycode_ID = 'US';
GO
/****** Object:  Table [dbo].[wt_fwd_log]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wt_fwd_log](
	[wt_fwd_log_id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[ohd_id] [varchar](13) NOT NULL,
	[fwd_agent_id] [int] NOT NULL,
	[forward_date] [datetime] NULL,
	[place_in_file] [varchar](100) NULL,
	[fwd_station_id] [varchar](100) NOT NULL,
	[bagtag] [varchar](100) NULL,
	[ebagtag] [varchar](100) NULL,
	[expeditenum] [varchar](100) NOT NULL,
	[passenger1] [varchar](100) NULL,
	[passenger2] [varchar](100) NULL,
	[passenger3] [varchar](100) NULL,
	[loss_code] [int] NOT NULL,
	[fault_station] [char](3) NULL,
	[fault_terminal] [char](3) NULL,
	[reason_for_loss] [varchar](100) NOT NULL,
	[supplementary_information] [varchar](255) NULL,
	[teletype_address1] [varchar](7) NULL,
	[teletype_address2] [varchar](7) NULL,
	[teletype_address3] [varchar](7) NULL,
	[teletype_address4] [varchar](7) NULL,
	[fwd_status] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[wt_fwd_log_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[wt_fwd_log_itinerary]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wt_fwd_log_itinerary](
	[Itinerary_ID] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[itinerarytype] [int] NOT NULL,
	[legfrom] [varchar](100) NULL,
	[legto] [varchar](100) NULL,
	[departdate] [datetime] NULL,
	[flightnum] [varchar](100) NULL,
	[airline] [varchar](100) NULL,
	[wt_fwd_log_ID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[Itinerary_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[INCIDENT_CHECKLIST]    Script Date: 04/23/2014 09:38:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[INCIDENT_CHECKLIST](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[timestamp] [datetime] NULL,
	[agent_Agent_ID] [int] NULL,
	[Task_ID] [numeric](19, 0) NOT NULL,
	[Option_ID] [numeric](19, 0) NOT NULL,
	[Incident_ID] [varchar](13) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[AUDIT_INCIDENT_CHECKLIST]    Script Date: 04/23/2014 09:38:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[AUDIT_INCIDENT_CHECKLIST](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[description] [varchar](255) NULL,
	[timestamp] [datetime] NULL,
	[agent_Agent_ID] [int] NULL,
	[Task_ID] [numeric](19, 0) NOT NULL,
	[Option_ID] [numeric](19, 0) NOT NULL,
	[Incident_ID] [varchar](13) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Default [DF__actionfil__af_se__4F5D9FD3]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[actionfile_station_counts] ADD  CONSTRAINT [DF__actionfil__af_se__4F5D9FD3]  DEFAULT ('') FOR [af_seq]
GO
/****** Object:  Default [DF_address_is_permanent]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[address] ADD  CONSTRAINT [DF_address_is_permanent]  DEFAULT ((0)) FOR [is_permanent]
GO
/****** Object:  Default [DF__agent__ws_enable__44FF419A]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[agent] ADD  DEFAULT ((0)) FOR [ws_enabled]
GO
/****** Object:  Default [DF__agent__max_ws_se__45F365D3]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[agent] ADD  DEFAULT ((1)) FOR [max_ws_sessions]
GO
/****** Object:  Default [DF__agent__web_enabl__46E78A0C]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[agent] ADD  DEFAULT ((1)) FOR [web_enabled]
GO
/****** Object:  Default [DF__agent__is_wt_use__5C786F26]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[agent] ADD  DEFAULT ((0)) FOR [is_wt_user]
GO
/****** Object:  Default [DF__agent__reset_pas__0E391C95]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[agent] ADD  DEFAULT ((0)) FOR [reset_password]
GO
/****** Object:  Default [DF__agent__account_l__10216507]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[agent] ADD  DEFAULT ((0)) FOR [account_locked]
GO
/****** Object:  Default [DF__agent__failed_lo__1209AD79]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[agent] ADD  DEFAULT ((0)) FOR [failed_logins]
GO
/****** Object:  Default [DF__attachmen__claim__4C42075D]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[attachment] ADD  DEFAULT (NULL) FOR [claim_id]
GO
/****** Object:  Default [DF__attachmen__agent__4D362B96]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[attachment] ADD  DEFAULT (NULL) FOR [agent_id]
GO
/****** Object:  Default [DF__attachmen__creat__4E2A4FCF]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[attachment] ADD  DEFAULT (NULL) FOR [createdate]
GO
/****** Object:  Default [DF__attachmen__attac__4F1E7408]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[attachment] ADD  DEFAULT (NULL) FOR [attachment_id]
GO
/****** Object:  Default [DF__attachmen__descr__50129841]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[attachment] ADD  DEFAULT (NULL) FOR [description]
GO
/****** Object:  Default [DF_audit_address_is_permanent]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_address] ADD  CONSTRAINT [DF_audit_address_is_permanent]  DEFAULT ((0)) FOR [is_permanent]
GO
/****** Object:  Default [DF__audit_age__ws_en__5D95E53A]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_agent] ADD  DEFAULT ((0)) FOR [ws_enabled]
GO
/****** Object:  Default [DF__audit_age__max_w__5E8A0973]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_agent] ADD  DEFAULT ((1)) FOR [max_ws_sessions]
GO
/****** Object:  Default [DF__audit_age__web_e__5F7E2DAC]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_agent] ADD  DEFAULT ((1)) FOR [web_enabled]
GO
/****** Object:  Default [DF__audit_age__is_wt__6231487C]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_agent] ADD  DEFAULT ((0)) FOR [is_wt_user]
GO
/****** Object:  Default [DF__audit_age__reset__0F2D40CE]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_agent] ADD  DEFAULT ((0)) FOR [reset_password]
GO
/****** Object:  Default [DF__audit_age__accou__11158940]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_agent] ADD  DEFAULT ((0)) FOR [account_locked]
GO
/****** Object:  Default [DF__audit_com__SHOW___25DB9BFC]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_irregularity_codes] ADD  DEFAULT ((1)) FOR [SHOW_TO_LIMITED_USERS]
GO
/****** Object:  Default [DF__audit_com__damag__08EA5793]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [damaged_to_lz_days]
GO
/****** Object:  Default [DF__audit_com__miss___09DE7BCC]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [miss_to_lz_days]
GO
/****** Object:  Default [DF__audit_com__defau__0AD2A005]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [default_loss_code]
GO
/****** Object:  Default [DF__audit_com__ws_en__0BC6C43E]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [ws_enabled]
GO
/****** Object:  Default [DF__audit_com__max_f__13F1F5EB]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [max_failed_logins]
GO
/****** Object:  Default [DF__audit_com__secur__15DA3E5D]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [secure_password]
GO
/****** Object:  Default [DF__audit_com__mbr_t__17C286CF]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [mbr_to_wt_days]
GO
/****** Object:  Default [DF__audit_com__ohd_t__19AACF41]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [ohd_to_wt_hours]
GO
/****** Object:  Default [DF__audit_com__wt_us__1C873BEC]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ('') FOR [wt_user]
GO
/****** Object:  Default [DF__audit_com__wt_pa__1D7B6025]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ('') FOR [wt_pass]
GO
/****** Object:  Default [DF__audit_com__ohd_l__5A846E65]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ('0') FOR [ohd_lz]
GO
/****** Object:  Default [DF__audit_com__lz_mo__5C6CB6D7]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ('0') FOR [lz_mode]
GO
/****** Object:  Default [DF__audit_com__bak_n__6319B466]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ('0') FOR [bak_nttracer_data_days]
GO
/****** Object:  Default [DF__audit_com__bak_n__6501FCD8]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ('0') FOR [bak_nttracer_ohd_data_days]
GO
/****** Object:  Default [DF__audit_com__bak_n__66EA454A]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ('0') FOR [bak_nttracer_lostfound_data_days]
GO
/****** Object:  Default [DF__audit_com__scann__0C1BC9F9]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [scannerDefaultBack]
GO
/****** Object:  Default [DF__audit_com__scann__0D0FEE32]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [scannerDefaultForward]
GO
/****** Object:  Default [DF__audit_com__oal_o__5772F790]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [oal_ohd_hours]
GO
/****** Object:  Default [DF__audit_com__oal_i__58671BC9]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [oal_inc_hours]
GO
/****** Object:  Default [DF__audit_com__auto___595B4002]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [auto_wt_amend]
GO
/****** Object:  Default [DF__audit_com__min_i__7C8F6DA6]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((-1.0)) FOR [min_interim_approval_incidental]
GO
/****** Object:  Default [DF__audit_com__min_i__7E77B618]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((-1.0)) FOR [min_interim_approval_cc_refund]
GO
/****** Object:  Default [DF__audit_com__autoc__0DB9F9A8]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [autocloseohd]
GO
/****** Object:  Default [DF__audit_com__min_p__5FD3FEBE]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [min_pass_size]
GO
/****** Object:  Default [DF__audit_com__pass___60C822F7]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [pass_x_history]
GO
/****** Object:  Default [DF__audit_com__auto___20CD9973]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [auto_close_days_back]
GO
/****** Object:  Default [DF__audit_com__auto___22B5E1E5]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [auto_close_ld_code]
GO
/****** Object:  Default [DF__audit_com__auto___249E2A57]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [auto_close_dam_code]
GO
/****** Object:  Default [DF__audit_com__auto___268672C9]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [auto_close_pil_code]
GO
/****** Object:  Default [DF__audit_com__auto___286EBB3B]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [auto_close_ld_station]
GO
/****** Object:  Default [DF__audit_com__auto___2A5703AD]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [auto_close_dam_station]
GO
/****** Object:  Default [DF__audit_com__auto___2C3F4C1F]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [auto_close_pil_station]
GO
/****** Object:  Default [DF__audit_com__incid__3B818FAF]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_company_specific_variable] ADD  DEFAULT ((0)) FOR [incident_lock_mins]
GO
/****** Object:  Default [DF__audit_com__email__108B795B]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_companycode] ADD  DEFAULT ('') FOR [email_address]
GO
/****** Object:  Default [DF__audit_cus__modif__52AE4273]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_customer_viewable_comments] ADD  DEFAULT (NULL) FOR [modifying_agent_ID]
GO
/****** Object:  Default [DF__audit_cus__reaso__53A266AC]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_customer_viewable_comments] ADD  DEFAULT (NULL) FOR [reason_modified]
GO
/****** Object:  Default [DF__audit_del__descr__6B24EA82]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_deliver_servicelevel] ADD  DEFAULT (NULL) FOR [description]
GO
/****** Object:  Default [DF__audit_del__activ__6C190EBB]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_deliver_servicelevel] ADD  DEFAULT ((1)) FOR [active]
GO
/****** Object:  Default [DF__audit_del__modif__6D0D32F4]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_deliver_servicelevel] ADD  DEFAULT (NULL) FOR [modifying_agent_ID]
GO
/****** Object:  Default [DF__audit_del__reaso__6E01572D]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_deliver_servicelevel] ADD  DEFAULT (NULL) FOR [reason_modified]
GO
/****** Object:  Default [DF__audit_del__servi__7E57BA87]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_deliver_servicelevel] ADD  DEFAULT ('') FOR [service_code]
GO
/****** Object:  Default [DF__audit_del__deliv__1AD3FDA4]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_deliverco_station] ADD  DEFAULT ('0') FOR [delivercompany_ID]
GO
/****** Object:  Default [DF__audit_del__stati__1BC821DD]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_deliverco_station] ADD  DEFAULT ('0') FOR [station_ID]
GO
/****** Object:  Default [DF__audit_del__modif__1CBC4616]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_deliverco_station] ADD  DEFAULT (NULL) FOR [modifying_agent_ID]
GO
/****** Object:  Default [DF__audit_del__reaso__1DB06A4F]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_deliverco_station] ADD  DEFAULT (NULL) FOR [reason_modified]
GO
/****** Object:  Default [DF__audit_deli__name__7B5B524B]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_delivercompany] ADD  DEFAULT ('') FOR [name]
GO
/****** Object:  Default [DF__audit_del__addre__7C4F7684]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_delivercompany] ADD  DEFAULT ('') FOR [address]
GO
/****** Object:  Default [DF__audit_del__custo__7D439ABD]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_delivercompany] ADD  DEFAULT ('') FOR [custom_ID]
GO
/****** Object:  Default [DF__audit_del__activ__7E37BEF6]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_delivercompany] ADD  DEFAULT ('1') FOR [active]
GO
/****** Object:  Default [DF__audit_del__compa__7F2BE32F]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_delivercompany] ADD  DEFAULT (NULL) FOR [companycode_ID]
GO
/****** Object:  Default [DF__audit_del__modif__00200768]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_delivercompany] ADD  DEFAULT (NULL) FOR [modifying_agent_ID]
GO
/****** Object:  Default [DF__audit_del__reaso__01142BA1]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_delivercompany] ADD  DEFAULT (NULL) FOR [reason_modified]
GO
/****** Object:  Default [DF__audit_exp__incid__6B64E1A4]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_expensepayout] ADD  DEFAULT ((0)) FOR [incidental_amount_auth]
GO
/****** Object:  Default [DF__audit_exp__incid__6D4D2A16]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_expensepayout] ADD  DEFAULT ((0)) FOR [incidental_amount_claim]
GO
/****** Object:  Default [DF__audit_exp__credi__6F357288]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_expensepayout] ADD  DEFAULT ((0)) FOR [creditcard_refund]
GO
/****** Object:  Default [DF__audit_inc__agent__20C1E124]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_incident] ADD  DEFAULT (NULL) FOR [agentassigned_ID]
GO
/****** Object:  Default [DF__audit_inc__wt_id__21B6055D]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_incident] ADD  DEFAULT (NULL) FOR [wt_id]
GO
/****** Object:  Default [DF__audit_inc__langu__2DBCB4E6]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_incident] ADD  DEFAULT ('en') FOR [language]
GO
/****** Object:  Default [DF__audit_inc__check__40CF895A]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_incident] ADD  DEFAULT ((1)) FOR [checklist_version]
GO
/****** Object:  Default [DF__audit_inc__overa__1BDDFBCD]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_incident] ADD  DEFAULT ((0)) FOR [overall_weight]
GO
/****** Object:  Default [DF__audit_inc__locke__46C859D2]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_incident] ADD  DEFAULT ((0)) FOR [locked]
GO
/****** Object:  Default [D_dbo_audit_incident_1]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_incident] ADD  CONSTRAINT [D_dbo_audit_incident_1]  DEFAULT ((0)) FOR [oc_claim_id]
GO
/****** Object:  Default [DF__audit_ite__bag_w__2196D523]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[audit_item] ADD  DEFAULT ((0)) FOR [bag_weight]
GO
/****** Object:  Default [DF__audit_ohd__dispo__398D8EEE]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[audit_ohd] ADD  DEFAULT (NULL) FOR [disposal_status_id]
GO
/****** Object:  Default [DF__audit_ohd__wt_id__3A81B327]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[audit_ohd] ADD  DEFAULT (NULL) FOR [wt_id]
GO
/****** Object:  Default [DF__audit_ohd__fault__0662F0A3]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[audit_ohd] ADD  DEFAULT ((0)) FOR [faultstation_ID]
GO
/****** Object:  Default [DF__audit_ohd__loss___075714DC]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[audit_ohd] ADD  DEFAULT ((0)) FOR [loss_code]
GO
/****** Object:  Default [DF__audit_ohd__match__09E968C4]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[audit_ohd] ADD  DEFAULT (NULL) FOR [matched_incident]
GO
/****** Object:  Default [DF__audit_oth__modif__4C0144E4]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[audit_other_system_information] ADD  DEFAULT (NULL) FOR [modifying_agent_ID]
GO
/****** Object:  Default [DF__audit_oth__reaso__4CF5691D]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[audit_other_system_information] ADD  DEFAULT (NULL) FOR [reason_modified]
GO
/****** Object:  Default [DF_audit_station_goal]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[audit_station] ADD  CONSTRAINT [DF_audit_station_goal]  DEFAULT ((0)) FOR [goal]
GO
/****** Object:  Default [DF__audit_sta__regio__5185DF67]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[audit_station] ADD  DEFAULT ((0)) FOR [region_goal]
GO
/****** Object:  Default [DF__bdo__integration__0E04126B]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[bdo] ADD  DEFAULT ((0)) FOR [integrationdelivercompany_ID]
GO
/****** Object:  Default [DF__bdo__delivery_co__0EF836A4]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[bdo] ADD  DEFAULT (NULL) FOR [delivery_comments]
GO
/****** Object:  Default [DF__bdo__canceled__58A7DE42]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[bdo] ADD  DEFAULT ((0)) FOR [canceled]
GO
/****** Object:  Default [DF__claim__subclass___5E55CAA0]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[claim] ADD  DEFAULT ('FsClaim') FOR [subclass_type]
GO
/****** Object:  Default [DF__claim__claimPror__5F49EED9]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[claim] ADD  DEFAULT ((0)) FOR [claimProrateId]
GO
/****** Object:  Default [DF__claim__statusId__603E1312]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[claim] ADD  DEFAULT ((0)) FOR [statusId]
GO
/****** Object:  Default [DF__claim__swapId__6132374B]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[claim] ADD  DEFAULT ((0)) FOR [swapId]
GO
/****** Object:  Default [DF__company_i__SHOW___24E777C3]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_irregularity_codes] ADD  DEFAULT ((1)) FOR [SHOW_TO_LIMITED_USERS]
GO
/****** Object:  Default [DF__company_s__damag__0C85DE4D]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((0)) FOR [damaged_to_lz_days]
GO
/****** Object:  Default [DF__company_s__miss___0D7A0286]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((0)) FOR [miss_to_lz_days]
GO
/****** Object:  Default [DF__company_s__defau__0E6E26BF]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((0)) FOR [default_loss_code]
GO
/****** Object:  Default [DF__company_s__audit__0F624AF8]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((1)) FOR [audit_delivery_companies]
GO
/****** Object:  Default [DF__company_s__webs___5E60B798]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((0)) FOR [webs_enabled]
GO
/****** Object:  Default [DF__company_s__max_f__12FDD1B2]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((0)) FOR [max_failed_logins]
GO
/****** Object:  Default [DF__company_s__secur__14E61A24]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((0)) FOR [secure_password]
GO
/****** Object:  Default [DF__company_s__mbr_t__16CE6296]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((0)) FOR [mbr_to_wt_days]
GO
/****** Object:  Default [DF__company_s__ohd_t__18B6AB08]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((0)) FOR [ohd_to_wt_hours]
GO
/****** Object:  Default [DF__company_s__wt_us__1A9EF37A]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ('') FOR [wt_user]
GO
/****** Object:  Default [DF__company_s__wt_pa__1B9317B3]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ('') FOR [wt_pass]
GO
/****** Object:  Default [DF__company_s__ohd_l__59904A2C]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ('0') FOR [ohd_lz]
GO
/****** Object:  Default [DF__company_s__lz_mo__5B78929E]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ('0') FOR [lz_mode]
GO
/****** Object:  Default [DF__company_s__bak_n__6225902D]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ('0') FOR [bak_nttracer_data_days]
GO
/****** Object:  Default [DF__company_s__bak_n__640DD89F]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ('0') FOR [bak_nttracer_ohd_data_days]
GO
/****** Object:  Default [DF__company_s__bak_n__65F62111]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ('0') FOR [bak_nttracer_lostfound_data_days]
GO
/****** Object:  Default [DF__company_s__retri__68D28DBC]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((1440)) FOR [retrieve_actionfile_interval]
GO
/****** Object:  Default [DF__company_s__wt_ur__7FB5F314]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ('') FOR [wt_url]
GO
/****** Object:  Default [DF__company_s__wt_ai__00AA174D]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ('') FOR [wt_airlinecode]
GO
/****** Object:  Default [DF__company_s__wt_en__5D6C935F]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ('0') FOR [wt_enabled]
GO
/****** Object:  Default [DF__company_s__wt_wr__5F54DBD1]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ('0') FOR [wt_write_enabled]
GO
/****** Object:  Default [DF__company_s__scann__0A338187]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((0)) FOR [scannerDefaultBack]
GO
/****** Object:  Default [DF__company_s__scann__0B27A5C0]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((0)) FOR [scannerDefaultForward]
GO
/****** Object:  Default [DF__company_s__oal_o__6049000A]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((0)) FOR [oal_ohd_hours]
GO
/****** Object:  Default [DF__company_s__oal_i__613D2443]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((0)) FOR [oal_inc_hours]
GO
/****** Object:  Default [DF__company_s__auto___567ED357]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((0)) FOR [auto_wt_amend]
GO
/****** Object:  Default [DF__company_s__min_i__78BEDCC2]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((-1.0)) FOR [min_interim_approval_incidental]
GO
/****** Object:  Default [DF__company_s__min_i__7AA72534]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((-1.0)) FOR [min_interim_approval_cc_refund]
GO
/****** Object:  Default [DF__company_s__autoc__0BD1B136]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((0)) FOR [autocloseohd]
GO
/****** Object:  Default [DF__company_s__min_p__5DEBB64C]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((8)) FOR [min_pass_size]
GO
/****** Object:  Default [DF__company_s__pass___5EDFDA85]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((20)) FOR [pass_x_history]
GO
/****** Object:  Default [DF__company_s__auto___13739E55]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((0)) FOR [auto_close_days_back]
GO
/****** Object:  Default [DF__company_s__auto___155BE6C7]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((0)) FOR [auto_close_ld_code]
GO
/****** Object:  Default [DF__company_s__auto___17442F39]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((0)) FOR [auto_close_dam_code]
GO
/****** Object:  Default [DF__company_s__auto___192C77AB]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((0)) FOR [auto_close_pil_code]
GO
/****** Object:  Default [DF__company_s__auto___1B14C01D]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((0)) FOR [auto_close_ld_station]
GO
/****** Object:  Default [DF__company_s__auto___1CFD088F]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((0)) FOR [auto_close_dam_station]
GO
/****** Object:  Default [DF__company_s__auto___1EE55101]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((0)) FOR [auto_close_pil_station]
GO
/****** Object:  Default [DF__company_s__incid__3999473D]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[company_specific_variable] ADD  DEFAULT ((0)) FOR [incident_lock_mins]
GO
/****** Object:  Default [DF__companyco__email__5AEE82B9]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[companycode] ADD  DEFAULT ('') FOR [email_address]
GO
/****** Object:  Default [DF__deliver_s__servi__7C6F7215]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[deliver_servicelevel] ADD  DEFAULT ('') FOR [service_code]
GO
/****** Object:  Default [DF__deliverco__activ__6DCC4D03]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[delivercompany] ADD  DEFAULT ((1)) FOR [active]
GO
/****** Object:  Default [DF__expensepa__milea__5A9026B4]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[expensepayout] ADD  DEFAULT ((0)) FOR [mileageamt]
GO
/****** Object:  Default [DF__expensepa__incid__7211DF33]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[expensepayout] ADD  DEFAULT ((0)) FOR [incidental_amount_auth]
GO
/****** Object:  Default [DF__expensepa__incid__73FA27A5]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[expensepayout] ADD  DEFAULT ((0)) FOR [incidental_amount_claim]
GO
/****** Object:  Default [DF__expensepa__credi__75E27017]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[expensepayout] ADD  DEFAULT ((0)) FOR [creditcard_refund]
GO
/****** Object:  Default [DF__expensepa__incid__4BB72C21]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[expensepayout] ADD  DEFAULT ('') FOR [incident_ID]
GO
/****** Object:  Default [DF__expensepa__bdo_i__5B844AED]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[expensepayout] ADD  DEFAULT (NULL) FOR [bdo_id]
GO
/****** Object:  Default [DF__expensepa__ohd_i__68536ACF]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[expensepayout] ADD  DEFAULT (NULL) FOR [ohd_id]
GO
/****** Object:  Default [DF__FsAddress__geoco__0A344CDE]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[FsAddress] ADD  DEFAULT ((0)) FOR [geocodeType]
GO
/****** Object:  Default [DF__generallo__elaps__5A1B2568]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[generallog] ADD  DEFAULT ((0)) FOR [elapseTime]
GO
/****** Object:  Default [DF__incident__agenta__7C1A6C5A]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[incident] ADD  DEFAULT (NULL) FOR [agentassigned_ID]
GO
/****** Object:  Default [DF__incident__wt_id__7D0E9093]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[incident] ADD  DEFAULT (NULL) FOR [wt_id]
GO
/****** Object:  Default [DF__incident__printe__589C25F3]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[incident] ADD  DEFAULT (NULL) FOR [printedreceipt]
GO
/****** Object:  Default [DF__incident__osi_id__4DE98D56]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[incident] ADD  DEFAULT (NULL) FOR [osi_id]
GO
/****** Object:  Default [DF__incident__langua__2CC890AD]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[incident] ADD  DEFAULT ('en') FOR [language]
GO
/****** Object:  Default [DF__incident__checkl__3FDB6521]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[incident] ADD  DEFAULT ((1)) FOR [checklist_version]
GO
/****** Object:  Default [DF__incident__overal__19F5B35B]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[incident] ADD  DEFAULT ((0)) FOR [overall_weight]
GO
/****** Object:  Default [DF__incident__locked__44E01160]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[incident] ADD  DEFAULT ((0)) FOR [locked]
GO
/****** Object:  Default [DF__incident__oc_cla__4D755761]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[incident] ADD  DEFAULT ((0)) FOR [oc_claim_id]
GO
/****** Object:  Default [DF__incident__codeLo__7094766C]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[incident] ADD  DEFAULT ((0)) FOR [codeLocked]
GO
/****** Object:  Default [DF__incident__statio__727CBEDE]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[incident] ADD  DEFAULT ((0)) FOR [stationLocked]
GO
/****** Object:  Default [DF__item__purchase_d__04308F6E]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[item] ADD  DEFAULT (NULL) FOR [purchase_date]
GO
/****** Object:  Default [DF__item__bag_weight__1FAE8CB1]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[item] ADD  DEFAULT ((0)) FOR [bag_weight]
GO
/****** Object:  Default [DF__item__replacemen__764D4FC2]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[item] ADD  DEFAULT ((0)) FOR [replacementBagIssued]
GO
/****** Object:  Default [DF__item__childrestr__54D74D5E]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[item] ADD  DEFAULT ((0)) FOR [childrestraint]
GO
/****** Object:  Default [DF__item_bdo__cancel__7E4D98E6]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[item_bdo] ADD  DEFAULT ((0)) FOR [canceled]
GO
/****** Object:  Default [DF__LFCategor__score__0EF901FB]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[LFCategory] ADD  DEFAULT ((3)) FOR [score]
GO
/****** Object:  Default [DF__LFFound__agreeme__0FED2634]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[LFFound] ADD  DEFAULT ('') FOR [agreementNumber]
GO
/****** Object:  Default [DF__LFFound__itemLoc__573EB8BD]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[LFFound] ADD  DEFAULT ((0)) FOR [itemLocation]
GO
/****** Object:  Default [DF__LFFound__deliver__62B06B69]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[LFFound] ADD  DEFAULT (NULL) FOR [deliveredDate]
GO
/****** Object:  Default [DF__LFFound__checkNu__63A48FA2]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[LFFound] ADD  DEFAULT ((0)) FOR [checkNumber]
GO
/****** Object:  Default [DF__LFFound__checkAm__6498B3DB]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[LFFound] ADD  DEFAULT ((0)) FOR [checkAmount]
GO
/****** Object:  Default [DF__LFItem__value__564A9484]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[LFItem] ADD  DEFAULT ((0)) FOR [value]
GO
/****** Object:  Default [DF__LFItem__weight__76C2510E]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[LFItem] ADD  DEFAULT ((1.0)) FOR [weight]
GO
/****** Object:  Default [DF__LFLost__email1__54624C12]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[LFLost] ADD  DEFAULT ((0)) FOR [email1]
GO
/****** Object:  Default [DF__LFLost__email2__5556704B]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[LFLost] ADD  DEFAULT ((0)) FOR [email2]
GO
/****** Object:  Default [DF__LFLost__foundEma__61BC4730]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[LFLost] ADD  DEFAULT ((0)) FOR [foundEmail]
GO
/****** Object:  Default [DF__LFLost__email3__041C4C2C]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[LFLost] ADD  DEFAULT ((0)) FOR [email3]
GO
/****** Object:  Default [DF__LFLost__email4__0604949E]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[LFLost] ADD  DEFAULT ((0)) FOR [email4]
GO
/****** Object:  Default [DF__LFLost__email5__07ECDD10]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[LFLost] ADD  DEFAULT ((0)) FOR [email5]
GO
/****** Object:  Default [DF__lfshippin__decla__74DA089C]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[lfshipping] ADD  DEFAULT ((0)) FOR [declaredValue]
GO
/****** Object:  Default [DF__LFSubCate__score__0E04DDC2]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[LFSubCategory] ADD  DEFAULT ((10)) FOR [score]
GO
/****** Object:  Default [DF__localerec__local__024846FC]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[localereceipt] ADD  DEFAULT ('') FOR [locale_id]
GO
/****** Object:  Default [DF__localerec__local__033C6B35]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[localereceipt] ADD  DEFAULT ('') FOR [locale_description]
GO
/****** Object:  Default [DF__lz__is_default__603D47BB]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[lz] ADD  DEFAULT ('0') FOR [is_default]
GO
/****** Object:  Default [DF__lz__percent_load__61316BF4]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[lz] ADD  DEFAULT ('0') FOR [percent_load]
GO
/****** Object:  Default [DF__oc_claim__claimT__78359834]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[oc_claim] ADD  DEFAULT ((1)) FOR [claimType]
GO
/****** Object:  Default [DF__oc_claim__reques__56BF95D0]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[oc_claim] ADD  DEFAULT ((0)) FOR [requestForeignCurrency]
GO
/****** Object:  Default [DF__oc_conten__quant__55CB7197]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[oc_content] ADD  DEFAULT ((0)) FOR [quantity]
GO
/****** Object:  Default [DF__ohd__disposal_st__76619304]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[ohd] ADD  DEFAULT (NULL) FOR [disposal_status_id]
GO
/****** Object:  Default [DF__ohd__wt_id__7755B73D]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[ohd] ADD  DEFAULT (NULL) FOR [wt_id]
GO
/****** Object:  Default [DF__ohd__faultstatio__047AA831]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[ohd] ADD  DEFAULT ((0)) FOR [faultstation_ID]
GO
/****** Object:  Default [DF__ohd__loss_code__056ECC6A]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[ohd] ADD  DEFAULT ((0)) FOR [loss_code]
GO
/****** Object:  Default [DF__ohd__earlyBag__697C9932]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[ohd] ADD  DEFAULT ((0)) FOR [earlyBag]
GO
/****** Object:  Default [DF__ohd__matched_inc__08012052]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[ohd] ADD  DEFAULT (NULL) FOR [matched_incident]
GO
/****** Object:  Default [DF__ohd__tagSentToWt__25676607]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[ohd] ADD  DEFAULT ((0)) FOR [tagSentToWt]
GO
/****** Object:  Default [DF__ohd__tagSentToWt__274FAE79]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[ohd] ADD  DEFAULT ((0)) FOR [tagSentToWtStationId]
GO
/****** Object:  Default [DF__ohd_categ__wt_ca__0FEC5ADD]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[ohd_categorytype] ADD  DEFAULT ('') FOR [wt_category]
GO
/****** Object:  Default [DF__ohd_log__log_sta__634EBE90]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[ohd_log] ADD  DEFAULT ((0)) FOR [log_status]
GO
/****** Object:  Default [DF__passenger__numRo__74650750]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[passenger] ADD  DEFAULT ((0)) FOR [numRonKitsIssued]
GO
/****** Object:  Default [DF__passwordh__pcoun__5CF79213]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[passwordhistory] ADD  DEFAULT ((1)) FOR [pcount]
GO
/****** Object:  Default [DF__pax_messa__email__359DCDD0]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[pax_message_trigger] ADD  DEFAULT (NULL) FOR [email_content_text]
GO
/****** Object:  Default [DF__pax_messa__LANGU__3691F209]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[pax_message_trigger] ADD  DEFAULT ('en') FOR [LANGUAGE]
GO
/****** Object:  Default [DF__pax_messa__SMS_C__37861642]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[pax_message_trigger] ADD  DEFAULT (NULL) FOR [SMS_CONTENT_TEXT]
GO
/****** Object:  Default [DF__pax_messa__SUBJE__387A3A7B]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[pax_message_trigger] ADD  DEFAULT (NULL) FOR [SUBJECT_LINE]
GO
/****** Object:  Default [DF__proactive__passI__158603F9]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[proactiveNotification] ADD  DEFAULT ((0)) FOR [passIndex]
GO
/****** Object:  Default [DF__PROPERTIE__keySt__22FF2F51]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[PROPERTIES] ADD  DEFAULT ('') FOR [keyStr]
GO
/****** Object:  Default [DF__PROPERTIE__value__23F3538A]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[PROPERTIES] ADD  DEFAULT ('') FOR [valueStr]
GO
/****** Object:  Default [DF_station_goal]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[station] ADD  CONSTRAINT [DF_station_goal]  DEFAULT ((0)) FOR [goal]
GO
/****** Object:  Default [DF__station__lz_id__5D60DB10]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[station] ADD  DEFAULT ((0)) FOR [lz_id]
GO
/****** Object:  Default [DF__station__wt_stat__69C6B1F5]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[station] ADD  DEFAULT ('') FOR [wt_stationcode]
GO
/****** Object:  Default [DF__station__email_l__0618D7E0]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[station] ADD  DEFAULT (NULL) FOR [email_language]
GO
/****** Object:  Default [DF__station__priorit__12A9974E]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[station] ADD  DEFAULT ((5)) FOR [priority]
GO
/****** Object:  Default [DF__subcompan__email__1176474A]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[subcompany] ADD  DEFAULT ((0)) FOR [email_notice_1]
GO
/****** Object:  Default [DF__subcompan__email__126A6B83]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[subcompany] ADD  DEFAULT ((0)) FOR [email_notice_2]
GO
/****** Object:  Default [DF__subcompan__email__09D52582]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[subcompany] ADD  DEFAULT ((0)) FOR [email_notice_3]
GO
/****** Object:  Default [DF__subcompan__email__0BBD6DF4]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[subcompany] ADD  DEFAULT ((0)) FOR [email_notice_4]
GO
/****** Object:  Default [DF__subcompan__email__0DA5B666]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[subcompany] ADD  DEFAULT ((0)) FOR [email_notice_5]
GO
/****** Object:  Default [DF__subcompan__shipp__135E8FBC]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[subcompany] ADD  DEFAULT ((5)) FOR [shippingSurcharge]
GO
/****** Object:  Default [DF__systemcom__sort___084B3915]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[systemcomponents] ADD  DEFAULT ((0)) FOR [sort_group]
GO
/****** Object:  Default [DF__TABLE1__rn__12C8C788]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[TABLE1] ADD  DEFAULT ('') FOR [rn]
GO
/****** Object:  Default [DF__TABLE1__gtsv__13BCEBC1]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[TABLE1] ADD  DEFAULT ((0)) FOR [gtsv]
GO
/****** Object:  Default [DF__TABLE1__minlen__14B10FFA]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[TABLE1] ADD  DEFAULT ((0)) FOR [minlen]
GO
/****** Object:  Default [DF__TABLE1__maxlen__15A53433]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[TABLE1] ADD  DEFAULT ((0)) FOR [maxlen]
GO
/****** Object:  Default [DF__TABLE1__v10__1699586C]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[TABLE1] ADD  DEFAULT ((0)) FOR [v10]
GO
/****** Object:  Default [DF__TABLE1__v9__178D7CA5]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[TABLE1] ADD  DEFAULT ((0)) FOR [v9]
GO
/****** Object:  Default [DF__TABLE1__v8__1881A0DE]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[TABLE1] ADD  DEFAULT ((0)) FOR [v8]
GO
/****** Object:  Default [DF__TABLE1__v7__1975C517]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[TABLE1] ADD  DEFAULT ((0)) FOR [v7]
GO
/****** Object:  Default [DF__TABLE1__v6__1A69E950]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[TABLE1] ADD  DEFAULT ((0)) FOR [v6]
GO
/****** Object:  Default [DF__TABLE1__v5__1B5E0D89]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[TABLE1] ADD  DEFAULT ((0)) FOR [v5]
GO
/****** Object:  Default [DF__TABLE1__v4__1C5231C2]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[TABLE1] ADD  DEFAULT ((0)) FOR [v4]
GO
/****** Object:  Default [DF__TABLE1__v3__1D4655FB]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[TABLE1] ADD  DEFAULT ((0)) FOR [v3]
GO
/****** Object:  Default [DF__TABLE1__v2__1E3A7A34]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[TABLE1] ADD  DEFAULT ((0)) FOR [v2]
GO
/****** Object:  Default [DF__TABLE1__v1__1F2E9E6D]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[TABLE1] ADD  DEFAULT ((0)) FOR [v1]
GO
/****** Object:  Default [DF__TABLE1__v0__2022C2A6]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[TABLE1] ADD  DEFAULT ((0)) FOR [v0]
GO
/****** Object:  Default [DF__webservic__sessi__208CD6FA]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[webservice_session] ADD  DEFAULT (NULL) FOR [session_id]
GO
/****** Object:  Default [DF__worldtrac__actio__2057CCD0]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[worldtracer_actionfiles] ADD  DEFAULT ('') FOR [action_file_type]
GO
/****** Object:  Default [DF__worldtracer__day__214BF109]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[worldtracer_actionfiles] ADD  DEFAULT ('0') FOR [day]
GO
/****** Object:  Default [DF__worldtrac__stati__22401542]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[worldtracer_actionfiles] ADD  DEFAULT ('') FOR [station]
GO
/****** Object:  Default [DF__worldtrac__airli__2334397B]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[worldtracer_actionfiles] ADD  DEFAULT ('') FOR [airline]
GO
/****** Object:  Default [DF__worldtrac__wt_in__24285DB4]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[worldtracer_actionfiles] ADD  DEFAULT ('') FOR [wt_incident_id]
GO
/****** Object:  Default [DF__worldtrac__wt_oh__251C81ED]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[worldtracer_actionfiles] ADD  DEFAULT ('') FOR [wt_ohd_id]
GO
/****** Object:  Default [DF__worldtrac__delet__67DE6983]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[worldtracer_actionfiles] ADD  DEFAULT ('0') FOR [delete_trigger]
GO
/****** Object:  Default [DF__worldtrac__perce__038683F8]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[worldtracer_actionfiles] ADD  DEFAULT (NULL) FOR [percent_match]
GO
/****** Object:  Default [DF__worldtrac__item___5A4F643B]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[worldtracer_actionfiles] ADD  DEFAULT ((0)) FOR [item_number]
GO
/****** Object:  Default [DF__worldtracer__seq__5145E845]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[worldtracer_actionfiles] ADD  DEFAULT ('') FOR [seq]
GO
/****** Object:  Default [DF__wt_fwd_lo__ohd_i__27F8EE98]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log] ADD  DEFAULT ('') FOR [ohd_id]
GO
/****** Object:  Default [DF__wt_fwd_lo__fwd_a__28ED12D1]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log] ADD  DEFAULT ('0') FOR [fwd_agent_id]
GO
/****** Object:  Default [DF__wt_fwd_lo__forwa__29E1370A]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log] ADD  DEFAULT (NULL) FOR [forward_date]
GO
/****** Object:  Default [DF__wt_fwd_lo__place__2AD55B43]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log] ADD  DEFAULT ('') FOR [place_in_file]
GO
/****** Object:  Default [DF__wt_fwd_lo__fwd_s__2BC97F7C]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log] ADD  DEFAULT ('') FOR [fwd_station_id]
GO
/****** Object:  Default [DF__wt_fwd_lo__bagta__2CBDA3B5]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log] ADD  DEFAULT ('0') FOR [bagtag]
GO
/****** Object:  Default [DF__wt_fwd_lo__ebagt__2DB1C7EE]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log] ADD  DEFAULT ('') FOR [ebagtag]
GO
/****** Object:  Default [DF__wt_fwd_lo__exped__2EA5EC27]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log] ADD  DEFAULT ('') FOR [expeditenum]
GO
/****** Object:  Default [DF__wt_fwd_lo__passe__2F9A1060]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log] ADD  DEFAULT ('') FOR [passenger1]
GO
/****** Object:  Default [DF__wt_fwd_lo__passe__308E3499]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log] ADD  DEFAULT ('') FOR [passenger2]
GO
/****** Object:  Default [DF__wt_fwd_lo__passe__318258D2]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log] ADD  DEFAULT ('') FOR [passenger3]
GO
/****** Object:  Default [DF__wt_fwd_lo__loss___32767D0B]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log] ADD  DEFAULT ('0') FOR [loss_code]
GO
/****** Object:  Default [DF__wt_fwd_lo__fault__336AA144]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log] ADD  DEFAULT ('') FOR [fault_station]
GO
/****** Object:  Default [DF__wt_fwd_lo__fault__345EC57D]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log] ADD  DEFAULT ('') FOR [fault_terminal]
GO
/****** Object:  Default [DF__wt_fwd_lo__reaso__3552E9B6]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log] ADD  DEFAULT ('') FOR [reason_for_loss]
GO
/****** Object:  Default [DF__wt_fwd_lo__suppl__36470DEF]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log] ADD  DEFAULT ('') FOR [supplementary_information]
GO
/****** Object:  Default [DF__wt_fwd_lo__telet__373B3228]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log] ADD  DEFAULT ('') FOR [teletype_address1]
GO
/****** Object:  Default [DF__wt_fwd_lo__telet__382F5661]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log] ADD  DEFAULT ('') FOR [teletype_address2]
GO
/****** Object:  Default [DF__wt_fwd_lo__telet__39237A9A]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log] ADD  DEFAULT ('') FOR [teletype_address3]
GO
/****** Object:  Default [DF__wt_fwd_lo__telet__3A179ED3]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log] ADD  DEFAULT ('') FOR [teletype_address4]
GO
/****** Object:  Default [DF__wt_fwd_lo__fwd_s__3B0BC30C]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log] ADD  DEFAULT ('0') FOR [fwd_status]
GO
/****** Object:  Default [DF__wt_fwd_lo__itine__3EDC53F0]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log_itinerary] ADD  DEFAULT ('0') FOR [itinerarytype]
GO
/****** Object:  Default [DF__wt_fwd_lo__legfr__3FD07829]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log_itinerary] ADD  DEFAULT ('') FOR [legfrom]
GO
/****** Object:  Default [DF__wt_fwd_lo__legto__40C49C62]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log_itinerary] ADD  DEFAULT ('') FOR [legto]
GO
/****** Object:  Default [DF__wt_fwd_lo__depar__41B8C09B]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log_itinerary] ADD  DEFAULT (NULL) FOR [departdate]
GO
/****** Object:  Default [DF__wt_fwd_lo__fligh__42ACE4D4]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log_itinerary] ADD  DEFAULT ('') FOR [flightnum]
GO
/****** Object:  Default [DF__wt_fwd_lo__airli__43A1090D]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log_itinerary] ADD  DEFAULT ('') FOR [airline]
GO
/****** Object:  Default [DF__wt_fwd_lo__wt_fw__44952D46]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log_itinerary] ADD  DEFAULT ('0') FOR [wt_fwd_log_ID]
GO
/****** Object:  Default [DF__wt_roh__wt_ahl_i__6CA31EA0]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_roh] ADD  DEFAULT ('') FOR [wt_ahl_id]
GO
/****** Object:  Default [DF__wt_roh__wt_ohd_i__6D9742D9]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_roh] ADD  DEFAULT ('') FOR [wt_ohd_id]
GO
/****** Object:  Default [DF__wt_roh__fi__6E8B6712]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_roh] ADD  DEFAULT ('') FOR [fi]
GO
/****** Object:  Default [DF__wt_roh__ag__6F7F8B4B]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_roh] ADD  DEFAULT ('') FOR [ag]
GO
/****** Object:  Default [DF__wt_roh__teletype__7073AF84]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_roh] ADD  DEFAULT ('') FOR [teletype_address1]
GO
/****** Object:  Default [DF__wt_roh__teletype__7167D3BD]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_roh] ADD  DEFAULT ('') FOR [teletype_address2]
GO
/****** Object:  Default [DF__wt_roh__teletype__725BF7F6]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_roh] ADD  DEFAULT ('') FOR [teletype_address3]
GO
/****** Object:  Default [DF__wt_roh__teletype__73501C2F]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_roh] ADD  DEFAULT ('') FOR [teletype_address4]
GO
/****** Object:  Default [DF__wt_roh__roh_stat__74444068]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_roh] ADD  DEFAULT ('0') FOR [roh_status]
GO
/****** Object:  Default [DF__wt_roh__roh_stat__753864A1]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_roh] ADD  DEFAULT ('0') FOR [roh_station_id]
GO
/****** Object:  Default [DF__wt_roh__roh_agen__762C88DA]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_roh] ADD  DEFAULT ('0') FOR [roh_agent_id]
GO
/****** Object:  Default [DF__wt_roh__lname__7720AD13]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_roh] ADD  DEFAULT ('') FOR [lname]
GO
/****** Object:  Default [DF__wt_tty__teleType__4865BE2A]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_tty] ADD  DEFAULT ('') FOR [teleType_address1]
GO
/****** Object:  Default [DF__wt_tty__teleType__4959E263]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_tty] ADD  DEFAULT ('') FOR [teleType_address2]
GO
/****** Object:  Default [DF__wt_tty__teleType__4A4E069C]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_tty] ADD  DEFAULT ('') FOR [teleType_address3]
GO
/****** Object:  Default [DF__wt_tty__teleType__4B422AD5]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_tty] ADD  DEFAULT ('') FOR [teleType_address4]
GO
/****** Object:  Default [DF__wt_tty__origin_a__4C364F0E]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_tty] ADD  DEFAULT ('') FOR [origin_address]
GO
/****** Object:  Default [DF__wt_tty__airline___4D2A7347]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_tty] ADD  DEFAULT ('') FOR [airline_code]
GO
/****** Object:  Default [DF__wt_tty__file_typ__4E1E9780]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_tty] ADD  DEFAULT ('') FOR [file_type1]
GO
/****** Object:  Default [DF__wt_tty__file_typ__4F12BBB9]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_tty] ADD  DEFAULT ('') FOR [file_type2]
GO
/****** Object:  Default [DF__wt_tty__file_typ__5006DFF2]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_tty] ADD  DEFAULT ('') FOR [file_type3]
GO
/****** Object:  Default [DF__wt_tty__file_typ__50FB042B]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_tty] ADD  DEFAULT ('') FOR [file_type4]
GO
/****** Object:  Default [DF__wt_tty__file_ref__51EF2864]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_tty] ADD  DEFAULT ('') FOR [file_reference1]
GO
/****** Object:  Default [DF__wt_tty__file_ref__52E34C9D]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_tty] ADD  DEFAULT ('') FOR [file_reference2]
GO
/****** Object:  Default [DF__wt_tty__file_ref__53D770D6]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_tty] ADD  DEFAULT ('') FOR [file_reference3]
GO
/****** Object:  Default [DF__wt_tty__file_ref__54CB950F]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_tty] ADD  DEFAULT ('') FOR [file_reference4]
GO
/****** Object:  Default [DF__wt_tty__tty_stat__55BFB948]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_tty] ADD  DEFAULT ('0') FOR [tty_status]
GO
/****** Object:  Default [DF__wt_tty__tty_stat__56B3DD81]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_tty] ADD  DEFAULT ('0') FOR [tty_station_id]
GO
/****** Object:  Default [DF__wt_tty__tty_agen__57A801BA]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_tty] ADD  DEFAULT ('0') FOR [tty_agent_id]
GO
/****** Object:  Default [DF__z_b6_clai__overa__2B203F5D]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[z_b6_claim_settlement] ADD  DEFAULT ((0)) FOR [overall_weight]
GO
/****** Object:  Default [DF__z_task_ma__boolK__71E7C201]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[z_task_manager_notice] ADD  DEFAULT ((0)) FOR [boolKey1]
GO
/****** Object:  Default [DF__z_task_ma__boolK__72DBE63A]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[z_task_manager_notice] ADD  DEFAULT ((0)) FOR [boolKey2]
GO
/****** Object:  Default [DF__z_task_ma__textA__73D00A73]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[z_task_manager_notice] ADD  DEFAULT ('') FOR [textArea1]
GO
/****** Object:  Default [DF__z_task_ma__textA__74C42EAC]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[z_task_manager_notice] ADD  DEFAULT ('') FOR [textArea2]
GO
/****** Object:  Default [DF__z_us_24hr__incid__2FE4F47A]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[z_us_24hrs_email] ADD  DEFAULT ('') FOR [incident_ID]
GO
/****** Object:  ForeignKey [FK9FA315DCEC006225]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[actionfile_station_counts]  WITH CHECK ADD  CONSTRAINT [FK9FA315DCEC006225] FOREIGN KEY([af_station_id])
REFERENCES [dbo].[ActionFileStation] ([id])
GO
ALTER TABLE [dbo].[actionfile_station_counts] CHECK CONSTRAINT [FK9FA315DCEC006225]
GO
/****** Object:  ForeignKey [FK6FFA0D5D5BD9E471]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[AUDIT_INCIDENT_CHECKLIST]  WITH CHECK ADD  CONSTRAINT [FK6FFA0D5D5BD9E471] FOREIGN KEY([Option_ID])
REFERENCES [dbo].[CHECKLIST_TASK_OPTION] ([Option_ID])
GO
ALTER TABLE [dbo].[AUDIT_INCIDENT_CHECKLIST] CHECK CONSTRAINT [FK6FFA0D5D5BD9E471]
GO
/****** Object:  ForeignKey [FK6FFA0D5DE323FE0C]    Script Date: 04/23/2014 09:38:35 ******/
ALTER TABLE [dbo].[AUDIT_INCIDENT_CHECKLIST]  WITH CHECK ADD  CONSTRAINT [FK6FFA0D5DE323FE0C] FOREIGN KEY([Task_ID])
REFERENCES [dbo].[CHECKLIST_TASK] ([Task_ID])
GO
ALTER TABLE [dbo].[AUDIT_INCIDENT_CHECKLIST] CHECK CONSTRAINT [FK6FFA0D5DE323FE0C]
GO
/****** Object:  ForeignKey [fk_bdo_delivercompany]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[bdo]  WITH CHECK ADD  CONSTRAINT [fk_bdo_delivercompany] FOREIGN KEY([delivercompany_ID])
REFERENCES [dbo].[delivercompany] ([delivercompany_ID])
GO
ALTER TABLE [dbo].[bdo] CHECK CONSTRAINT [fk_bdo_delivercompany]
GO
/****** Object:  ForeignKey [fk_bdo_servicelevel]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[bdo]  WITH CHECK ADD  CONSTRAINT [fk_bdo_servicelevel] FOREIGN KEY([servicelevel_ID])
REFERENCES [dbo].[deliver_servicelevel] ([servicelevel_ID])
GO
ALTER TABLE [dbo].[bdo] CHECK CONSTRAINT [fk_bdo_servicelevel]
GO
/****** Object:  ForeignKey [FK2D0B089E946FFF08]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[CHECKLIST_TASK]  WITH CHECK ADD  CONSTRAINT [FK2D0B089E946FFF08] FOREIGN KEY([version_id])
REFERENCES [dbo].[CHECKLIST_VERSION] ([version_id])
GO
ALTER TABLE [dbo].[CHECKLIST_TASK] CHECK CONSTRAINT [FK2D0B089E946FFF08]
GO
/****** Object:  ForeignKey [FK73C32336E323FE0C]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[CHECKLIST_TASK_OPTION]  WITH CHECK ADD  CONSTRAINT [FK73C32336E323FE0C] FOREIGN KEY([task_id])
REFERENCES [dbo].[CHECKLIST_TASK] ([Task_ID])
GO
ALTER TABLE [dbo].[CHECKLIST_TASK_OPTION] CHECK CONSTRAINT [FK73C32336E323FE0C]
GO
/****** Object:  ForeignKey [fk_service_deliverco]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[deliver_servicelevel]  WITH CHECK ADD  CONSTRAINT [fk_service_deliverco] FOREIGN KEY([delivercompany_ID])
REFERENCES [dbo].[delivercompany] ([delivercompany_ID])
GO
ALTER TABLE [dbo].[deliver_servicelevel] CHECK CONSTRAINT [fk_service_deliverco]
GO
/****** Object:  ForeignKey [fk_station_deliverco]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[deliverco_station]  WITH CHECK ADD  CONSTRAINT [fk_station_deliverco] FOREIGN KEY([delivercompany_ID])
REFERENCES [dbo].[delivercompany] ([delivercompany_ID])
GO
ALTER TABLE [dbo].[deliverco_station] CHECK CONSTRAINT [fk_station_deliverco]
GO
/****** Object:  ForeignKey [FKF655DDD10E61C46]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[ForwardNotice]  WITH CHECK ADD  CONSTRAINT [FKF655DDD10E61C46] FOREIGN KEY([ohd_log_id])
REFERENCES [dbo].[ohd_log] ([OHDLog_ID])
GO
ALTER TABLE [dbo].[ForwardNotice] CHECK CONSTRAINT [FKF655DDD10E61C46]
GO
/****** Object:  ForeignKey [FKF655DDDFB5D9E6]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[ForwardNotice]  WITH CHECK ADD  CONSTRAINT [FKF655DDDFB5D9E6] FOREIGN KEY([station_id])
REFERENCES [dbo].[station] ([Station_ID])
GO
ALTER TABLE [dbo].[ForwardNotice] CHECK CONSTRAINT [FKF655DDDFB5D9E6]
GO
/****** Object:  ForeignKey [FK_incident_agent]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[incident]  WITH NOCHECK ADD  CONSTRAINT [FK_incident_agent] FOREIGN KEY([agent_ID])
REFERENCES [dbo].[agent] ([Agent_ID])
GO
ALTER TABLE [dbo].[incident] CHECK CONSTRAINT [FK_incident_agent]
GO
/****** Object:  ForeignKey [FK_incident_agent1]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[incident]  WITH NOCHECK ADD  CONSTRAINT [FK_incident_agent1] FOREIGN KEY([agentassigned_ID])
REFERENCES [dbo].[agent] ([Agent_ID])
GO
ALTER TABLE [dbo].[incident] CHECK CONSTRAINT [FK_incident_agent1]
GO
/****** Object:  ForeignKey [FK_incident_station]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[incident]  WITH NOCHECK ADD  CONSTRAINT [FK_incident_station] FOREIGN KEY([stationcreated_ID])
REFERENCES [dbo].[station] ([Station_ID])
GO
ALTER TABLE [dbo].[incident] CHECK CONSTRAINT [FK_incident_station]
GO
/****** Object:  ForeignKey [FK2AA4B2595BD9E471]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[INCIDENT_CHECKLIST]  WITH CHECK ADD  CONSTRAINT [FK2AA4B2595BD9E471] FOREIGN KEY([Option_ID])
REFERENCES [dbo].[CHECKLIST_TASK_OPTION] ([Option_ID])
GO
ALTER TABLE [dbo].[INCIDENT_CHECKLIST] CHECK CONSTRAINT [FK2AA4B2595BD9E471]
GO
/****** Object:  ForeignKey [FK2AA4B259E323FE0C]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[INCIDENT_CHECKLIST]  WITH CHECK ADD  CONSTRAINT [FK2AA4B259E323FE0C] FOREIGN KEY([Task_ID])
REFERENCES [dbo].[CHECKLIST_TASK] ([Task_ID])
GO
ALTER TABLE [dbo].[INCIDENT_CHECKLIST] CHECK CONSTRAINT [FK2AA4B259E323FE0C]
GO
/****** Object:  ForeignKey [FK_message_station]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[message]  WITH NOCHECK ADD  CONSTRAINT [FK_message_station] FOREIGN KEY([station_id])
REFERENCES [dbo].[station] ([Station_ID])
GO
ALTER TABLE [dbo].[message] CHECK CONSTRAINT [FK_message_station]
GO
/****** Object:  ForeignKey [FK__message_c__messa__77A09B57]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[message_copies]  WITH CHECK ADD FOREIGN KEY([message_id])
REFERENCES [dbo].[message] ([message_id])
GO
/****** Object:  ForeignKey [FK_ohd_agent]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[ohd]  WITH NOCHECK ADD  CONSTRAINT [FK_ohd_agent] FOREIGN KEY([agent_ID])
REFERENCES [dbo].[agent] ([Agent_ID])
GO
ALTER TABLE [dbo].[ohd] CHECK CONSTRAINT [FK_ohd_agent]
GO
/****** Object:  ForeignKey [FK_ohd_station]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[ohd]  WITH NOCHECK ADD  CONSTRAINT [FK_ohd_station] FOREIGN KEY([found_station_ID])
REFERENCES [dbo].[station] ([Station_ID])
GO
ALTER TABLE [dbo].[ohd] CHECK CONSTRAINT [FK_ohd_station]
GO
/****** Object:  ForeignKey [FK_ohd_station1]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[ohd]  WITH NOCHECK ADD  CONSTRAINT [FK_ohd_station1] FOREIGN KEY([holding_station_ID])
REFERENCES [dbo].[station] ([Station_ID])
GO
ALTER TABLE [dbo].[ohd] CHECK CONSTRAINT [FK_ohd_station1]
GO
/****** Object:  ForeignKey [FK5212467F1344B8D4]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[svc_profile_parameters]  WITH CHECK ADD  CONSTRAINT [FK5212467F1344B8D4] FOREIGN KEY([profile_id])
REFERENCES [dbo].[svc_profile] ([id])
GO
ALTER TABLE [dbo].[svc_profile_parameters] CHECK CONSTRAINT [FK5212467F1344B8D4]
GO
/****** Object:  ForeignKey [FK17E04A64FFA542EE]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[svc_profile_permission]  WITH CHECK ADD  CONSTRAINT [FK17E04A64FFA542EE] FOREIGN KEY([permission_id])
REFERENCES [dbo].[svc_profile] ([id])
GO
ALTER TABLE [dbo].[svc_profile_permission] CHECK CONSTRAINT [FK17E04A64FFA542EE]
GO
/****** Object:  ForeignKey [FKF75345151344B8D4]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[svc_sabre_connection]  WITH CHECK ADD  CONSTRAINT [FKF75345151344B8D4] FOREIGN KEY([profile_id])
REFERENCES [dbo].[svc_profile] ([id])
GO
ALTER TABLE [dbo].[svc_sabre_connection] CHECK CONSTRAINT [FKF75345151344B8D4]
GO
/****** Object:  ForeignKey [FKBAA7E24A1344B8D4]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[svc_user]  WITH CHECK ADD  CONSTRAINT [FKBAA7E24A1344B8D4] FOREIGN KEY([profile_id])
REFERENCES [dbo].[svc_profile] ([id])
GO
ALTER TABLE [dbo].[svc_user] CHECK CONSTRAINT [FKBAA7E24A1344B8D4]
GO
/****** Object:  ForeignKey [FK813E1A651344B8D4]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[svc_world_tracer_ishares_account]  WITH CHECK ADD  CONSTRAINT [FK813E1A651344B8D4] FOREIGN KEY([profile_id])
REFERENCES [dbo].[svc_profile] ([id])
GO
ALTER TABLE [dbo].[svc_world_tracer_ishares_account] CHECK CONSTRAINT [FK813E1A651344B8D4]
GO
/****** Object:  ForeignKey [FKB780019C1344B8D4]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[svc_world_tracer_web_account]  WITH CHECK ADD  CONSTRAINT [FKB780019C1344B8D4] FOREIGN KEY([profile_id])
REFERENCES [dbo].[svc_profile] ([id])
GO
ALTER TABLE [dbo].[svc_world_tracer_web_account] CHECK CONSTRAINT [FKB780019C1344B8D4]
GO
/****** Object:  ForeignKey [FK__wt_fwd_lo__ohd_i__3BFFE745]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log]  WITH CHECK ADD FOREIGN KEY([ohd_id])
REFERENCES [dbo].[ohd] ([OHD_ID])
GO
/****** Object:  ForeignKey [FK__wt_fwd_lo__wt_fw__4589517F]    Script Date: 04/23/2014 09:38:36 ******/
ALTER TABLE [dbo].[wt_fwd_log_itinerary]  WITH CHECK ADD FOREIGN KEY([wt_fwd_log_ID])
REFERENCES [dbo].[wt_fwd_log] ([wt_fwd_log_id])
GO
