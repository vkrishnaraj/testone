update properties set valuestr = 'english,mandarin,hindi,spanish,russian,bengali,portuguese,german,french,japanese,korean,cantonese,vietnamese,hebrew,italian,dutch,other' where keystr = 'spoken.language.list';


--##Please review to see if this is the correct use of a covering index

USE [nettracer_prod];
GO

DROP INDEX [_dta_index_passenger_6_562101043__K1_K2] ON [dbo].[passenger];

CREATE NONCLUSTERED INDEX [_dta_index_passenger_6_562101043__K1_K2_3_4_5_6_7_8_9_10_11_12_13_14_15_15_16]
ON [dbo].[passenger]
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

update properties set valueStr = 'http://bagweb/ws/ScanPoints4' where keyStr = 'scan.history.endpoint';

update properties set valueStr = '1' where keyStr = 'convert.bagtag';