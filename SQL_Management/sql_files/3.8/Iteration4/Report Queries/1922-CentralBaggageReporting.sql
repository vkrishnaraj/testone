####Worked on by Peter Kibaki

# Mishandling DOT-Summary
#startdatetime - Beginning of Date Range. Datetime value
#enddatetime - End of Date Range. Datetime value
select s.stationcode, 
  sum(case when inc.itemtype_id=1 then 1 else 0 end) as Loss, 
  sum(case when inc.itemtype_id=3 then 1 else 0 end) as Damage, 
  sum(case when inc.itemtype_id=2 then 1 else 0 end) as Missing,
  count(*) as Total
    from incident inc
    inner join station s on s.Station_ID=inc.stationassigned_ID
      where inc.createdate between CAST(:startdatetime AS DATETIME)
      and CAST(:enddatetime AS DATETIME) group by inc.stationassigned_ID;
      
#------------------------------------------------------------------------------------

#Station Recovery Rate Report
#startdatetime - Beginning of Date Range. Datetime value
#enddatetime - End of Date Range. Datetime value
 select s.StationCode, 
  count(*) as reports_taken,
  sum(case when inc.status_id=13 and s.Station_ID!=l.station_id then 1 else 0 end) as station_returned, 
  sum(case when inc.status_id=13 and s.Station_ID=l.station_id then 1 else 0 end) as cbs_returned,
  sum(case when inc.status_id!=13 then 1 else 0 end) as open_reports,
  sum(case when not isnull(ep.Expensepayout_ID) then 1 else 0 end) as reports_with_payments,
  ROUND(sum(ep.checkamt+ep.voucheramt+ep.creditcard_refund),2) as total_payments,
  ROUND(100 * (count(*) - sum(case when inc.status_id!=13 then 1 else 0 end)) / count(*), 2) as Recovery_ratio
    from incident inc
    left outer join expensepayout ep on ep.incident_ID = inc.Incident_ID and ep.status_ID in (53,55)
    inner join station s on s.station_id=inc.stationcreated_ID 
    left outer join Lz l on l.station_id = s.Station_ID
    where
    inc.createdate between CAST(:startdatetime AS DATETIME) and CAST(:enddatetime AS DATETIME) group by inc.stationcreated_id;
 
#------------------------------------------------------------------------------------
 
#Report Expenses by Payment Code
#startdatetime - Beginning of Date Range. Datetime value
#enddatetime - End of Date Range. Datetime value
#stationCodes - Array of 3 Character stationCodes to check against
#expenseCodes - Array of Code Descriptions (Interim, Delivery, etc...) to check against
select ep.incident_ID, s.stationCode, ep.lastname, ep.firstname, ep.middlename,
  ep.createdate, et.description,
  (CASE ep.paytype WHEN 'DRAFT' THEN ep.checkamt WHEN 'VOUCH' THEN ep.voucheramt WHEN 'INVOICE' THEN ep.checkamt else 0 END) as Amount,
  (CASE ep.paytype WHEN 'MILE' THEN ep.mileageamt else 0 END) as Mileage_Amount
  from expensepayout ep 
  inner join station s on s.Station_ID=ep.station_ID 
  inner join expensetype et on et.Expensetype_ID = ep.expensetype_ID
    where createdate
      between CAST(:startdatetime AS DATETIME) 
      and CAST(:enddatetime AS DATETIME)
      and find_in_set(s.stationCode,:stationCodes)
      and find_in_set(et.description,:expenseCodes) 
        group by ep.incident_id, ep.Expensepayout_ID;
 
#------------------------------------------------------------------------------------
      
#Tested the remaining manually with Toad - Worked on by Sean Fine
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
      and (ep.status_id = 53 or ep.status_id=55)
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
  when 'D' then 
    itin.legto_type=3 and itin.legto=:stationcode
  when 'O' then
   itin.legfrom_type=1 and itin.legfrom=:stationcode
  when 'T' then 
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