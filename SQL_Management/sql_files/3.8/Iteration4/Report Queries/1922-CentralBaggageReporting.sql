####tested with: nettracer_demo, ad/b6/*nk*/ws_hudson

#sample datetime with 24 hours format for time example 21:59
set @startdatetime = '2000/06/23'; 
set @enddatetime   = '2014/7/17 23:59';


# Mishandling DOT-Summary
select * from ( #if ordering required

select (select stationcode from station where station.Station_ID = mhs.stationassigned_ID) Station, 
	(select count(itemtype_ID) from incident where itemtype_ID = 1 and stationassigned_ID = mhs.stationassigned_ID) Loss,
	(select count(itemtype_ID) from incident where itemtype_ID = 3 and stationassigned_ID = mhs.stationassigned_ID) Damage,
	(select count(itemtype_ID) from incident where itemtype_ID = 2 and stationassigned_ID = mhs.stationassigned_ID) Missing,
	Total
from 
  (select stationassigned_ID, count(*) as Total
    from incident
    where createdate between CAST(@startdatetime AS DATETIME) and CAST(@enddatetime AS DATETIME)
    group by stationassigned_ID
    /*order by stationassigned_ID*/) as mhs
 
 ) report order by Station; #if ordering required
 
#------------------------------------------------------------------------------------

#Station Recovery Rate Report
select * from ( #if ordering required

select *, CONCAT(ROUND(100 * (Reports_Taken - Open_Reports) / Reports_Taken, 2), '%') Recovery_Ratio from ( #if Recovery_Ratio required

  select (select stationcode from station where station.Station_ID = mhs.stationcreated_ID) Station, 
    Reports_Taken,
  	(select count(*) from incident where status_id = 13 and stationcreated_ID = mhs.stationcreated_ID and stationassigned_ID not in (select station_id from lz)) Station_Returned,
  	(select count(*) from incident where status_id = 13 and stationcreated_ID = mhs.stationcreated_ID and stationassigned_ID in (select station_id from lz)) CBS_Return,
  	(select count(*) from incident where status_id != 13 and stationcreated_ID = mhs.stationcreated_ID) Open_Reports,
  	
    (select sum(amountPaid) from fsclaim where ntIncidentId in (select incident_id from incident where stationcreated_ID = mhs.stationcreated_ID)) Reports_with_Payment
  from 
    (select stationcreated_ID, stationassigned_ID, count(*) as Reports_Taken
      from incident
      where createdate between CAST(@startdatetime AS DATETIME) and CAST(@enddatetime AS DATETIME)
      group by stationcreated_ID
      /*order by stationcreated_ID*/) as mhs
      
 ) WithRecoveryRatio #if Recovery_Ratio required
 
 ) report order by Station; #if ordering required
 
 
#------------------------------------------------------------------------------------
 
#Report Expenses by Payment Code
select incident_id Report_Number, 
  (select stationcode from station where station.Station_ID = epo.station_id) Station,
  (select CONCAT(lastname, ', ', firstname, if (middlename is not null and middlename <> '', CONCAT(', ', middlename), '')) from passengerexp where expensepayout_ID = epo.expensepayout_ID limit 1) Primary_Name,
  (select DATE_FORMAT(createdate, '%c/%e/%y') from incident where incident_ID = epo.Incident_ID) Taken_Date,
  (select description from expensetype where expensetype_id = epo.expensetype_id) Code,
  Type, Amount, Mileage_Amount
from
    (select incident_id, station_id, expensepayout_ID, expensetype_id, paytype Type, 
      (SELECT CASE paytype WHEN 'DRAFT' THEN draft WHEN 'VOUCH' THEN voucheramt WHEN 'INVOICE' THEN checkamt END) Amount,
      (SELECT CASE paytype WHEN 'MILE' THEN mileageamt END) Mileage_Amount
    from expensepayout
      where createdate between CAST(@startdatetime AS DATETIME) and CAST(@enddatetime AS DATETIME)
    
      and station_id in (select station_id from station where stationcode in ('LZ', 'ATL'))
    
      and expensetype_id in (select expensetype_id  from expensetype where description in ('other', 'Delivery'))    
      ) epo
      
      
      #where 0 < Amount or 0 < Mileage_Amount  and expensetype_id!=3
      #order by Mileage_Amount desc, Mileage_Amount desc , code
 
#------------------------------------------------------------------------------------
      
   