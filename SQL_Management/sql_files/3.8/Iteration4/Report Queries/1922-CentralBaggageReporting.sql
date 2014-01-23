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
      
#Tested the remaining manually with Toad
#Damage Report Expense by Bag Type. Takes in and returns GMT Date
select ep.incident_id, s.stationcode, concat(ep.lastname," ",ep.firstname) as name, itin.legfrom, 
(case inc.courtesyreport when 1 then "Y" else "N" end) as CA, it.bagtype, ep.paytype, et.description,
(case ep.paytype when 'DRAFT' or 'INVOICE' or 'PSO' then ep.checkamt when 'VOUCH' then ep.voucheramt when 'MILE' then ep.mileageamt else 0 end) as amount
  from expensepayout ep 
    inner join incident inc on inc.Incident_ID = ep.incident_ID
    inner join item it on it.Item_ID = (select min(ite.item_id) from item ite where ite.incident_id=inc.Incident_ID limit 1)
    inner join station s on s.Station_ID=inc.stationcreated_ID
    inner join itinerary itin on itin.incident_ID = ep.incident_ID and itin.itinerarytype=0 and itin.legfrom_type=1
    inner join expensetype et on et.Expensetype_ID = ep.expensetype_ID
      where find_in_set(it.bagtype,:bagtype) and ep.createdate >=:startDate and ep.createdate<=:endDate 
      and (ep.status_id = 54 or ep.status_id=55)
      and inc.itemtype_ID = 3;
  
#------------------------------------------------------------------------------------
          
#Missing Article Detail Report by Flight Type. Takes in and returns GMT Date.
# as a note: This query seems oddly similar to the one in Corporate Security Reporting, despite the different columns of data.
select inc.Incident_ID, (case inc.itemtype_ID when 1 then "L" when 2 then "M" when 3 then "D" else "" end) as incType, concat(p.lastname,", ",p.firstname) as name,
inc.createdate, oc.categorytype , iv.description, (case :itintype when '1' then 'O' when '2' then 'T' when '3' then 'D' end) as flightType,
itinroutes.route, itinroutes.flightnums
from incident inc 
inner join itinerary itin  on inc.Incident_ID = itin.incident_ID and itin.itinerarytype=0
inner join passenger p on p.incident_ID = inc.Incident_ID and p.isprimary=true
inner join item ite on ite.incident_ID = inc.Incident_ID 
inner join item_inventory iv on iv.item_ID = ite.Item_ID
inner join ohd_categorytype oc on iv.categorytype_ID = oc.OHD_CategoryType_ID
left outer join 
        (SELECT incident_id, departdate as initialDepartDate, 
          GROUP_CONCAT(legs ORDER BY incident_id ASC, itinerary_id ASC) route, 
          group_concat(departdate ORDER BY incident_id ASC, itinerary_id ASC) departdates, 
          group_concat(flightnum ORDER BY incident_id ASC, itinerary_id ASC) flightnums 
            FROM (SELECT it.incident_id,it.itinerary_id,it.departdate, it.flightnum, 
            concat(it.legfrom, '-', it.legto) legs
              FROM itinerary it inner join incident i on i.incident_id=it.incident_ID 
                WHERE it.itinerarytype=0 and i.createdate>=:startDate and i.createdate<=:endDate 
                  ORDER BY it.incident_id, it.itinerary_id ASC) itin1 
                  GROUP BY incident_id) as itinRoutes on itinRoutes.incident_id=inc.incident_id
  where inc.createdate>=:startDate and inc.createdate<=:endDate and
  (case :itintype 
  when '3' then 
    itin.legto_type=3 and itin.legto=:stationcode
  when '1' then
   itin.legfrom_type=1 and itin.legfrom=:stationcode
  when '2' then 
    ((itin.legto_type=2 and itin.legto=:stationcode) or (itin.legfrom_type=2 and itin.legfrom=:stationcode))
  end) and
   inc.itemtype_ID=2 group by iv.inventory_ID;
 
#------------------------------------------------------------------------------------
      
#Chargeback by Station. Takes in and returns GMT Date
select s.stationcode, i.lossCode, c.description, count(*) 
from item i inner join incident inc on i.incident_ID = inc.Incident_ID 
  inner join station s on s.Station_ID = inc.stationassigned_ID inner join Company_irregularity_codes c on i.lossCode= c.loss_code 
  where inc.createdate >= :startDate and inc.createdate <=:endDate and find_in_set(c.loss_Code, :lossCodes)
  and c.companycode_ID='WN' and c.report_type=i.itemtype_ID and
  (case :controllable when 0 then c.controllable=0 when 1 then c.controllable=1 else 1=1 end)
  group by s.stationcode, c.loss_Code;
 
#------------------------------------------------------------------------------------

#Incident Return Type Report Detail
select inc.createDate, inc.incident_id, st.description
  from incident inc 
  inner join station s on inc.stationassigned_ID=s.Station_ID 
  inner join item it on it.item_id = (select min(ii.item_id) from item ii where ii.incident_id=inc.Incident_ID limit 1)
  inner join status st on st.Status_ID = it.status_ID
    where inc.status_id=13 and (it.status_ID=50 or it.status_ID=59) and inc.close_date>=:startDate and inc.close_date<=:endDate and find_in_set(s.stationcode, :stationcodes);
      
#Incident Return Type Report Summary
select s.stationcode, count(*) totalClosed, 
  sum(case it.status_id when 50 then 1 else 0 end) as deliveryCount, 
  sum(case it.status_id when 59 then 1 else 0 end) as ppuCount 
  from incident inc 
  inner join station s on inc.stationassigned_ID=s.Station_ID 
  inner join item it on it.item_id = (select min(ii.item_id) from item ii where ii.incident_id=inc.Incident_ID limit 1)
    where inc.status_id=13 and (it.status_ID=50 or it.status_ID=59) and inc.close_date>=:startDate and inc.close_date<=:endDate and find_in_set(s.stationcode, :stationcodes)
  group by s.stationCode;
  
#------------------------------------------------------------------------------------
      
#Bag Drop Compliance Summary 
select b.arrivalStationCode, count(*), sum(case when not isNull(b.bagDropTime) and b.bagDropTime!='' then 1 else 0 end) as dropTimes,
(count(distinct case when not isNull(b.bagDropTime) and b.bagDropTime!='' then 1 else 0 end)/count(*)) * 100 as dropPercent,
 avg(timestampdiff(MINUTE, case when actArrivalDate is null then schArrivalDate else actArrivalDate end, bagdroptime)) avgtime
  from bagdrop b 
  where b.schArrivalDate>=:startDate and b.schArrivalDate<=:endDate and find_in_set(b.arrivalStationCode, :stationCodes) group by b.arrivalStationCode;
 
#Bag Drop Compliance Detail 
select b.arrivalStationCode, b.schArrivalDate, b.flight, b.actArrivalDate, b.bagDropTime,
  timestampdiff(MINUTE, case when actArrivalDate is null then schArrivalDate else actArrivalDate end, bagdroptime) as timeInMinToCarousel 
  from bagdrop b 
  where b.schArrivalDate>=:startDate and b.schArrivalDate<=:endDate and find_in_set(b.arrivalStationCode, :stationCodes) and bagDropTime!='' group by b.id;