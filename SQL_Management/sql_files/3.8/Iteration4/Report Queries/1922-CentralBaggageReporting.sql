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
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#bagType - array of bagtypes (22, 23, etc...)
select ep.incident_id, s.stationcode, ep.lastname,ep.firstname, itin.legfrom, 
(case inc.courtesyreport when 1 then "Y" else "N" end) as CA, it.bagtype, ep.paytype, et.description,
(case ep.paytype when 'DRAFT' or 'INVOICE' or 'PSO' then ep.checkamt when 'VOUCH' then ep.voucheramt else ep.checkamt end) as amount,
(CASE ep.paytype WHEN 'MILE' THEN ep.mileageamt else 0 END) as Mileage_Amount
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
#itintype - the type of station on the flight itinerary - O for Origin station, T for Transfer Station, D for Destination Station. Single character variable
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#stationcode - the code of the Station to check against. 3 character varchar
# as a note: This query seems oddly similar to the one in Corporate Security Reporting, despite the different columns of data.
select inc.Incident_ID, (case inc.itemtype_ID when 1 then "L" when 2 then "M" when 3 then "D" else "" end) as reportType, p.lastname,p.firstname,
inc.createdate as reportDate, art.article as itemName, art.description as articleDescription, :itintype as flightType,
itinroutes.route as Route, itinroutes.flightnums as FlightNumber
from incident inc 
inner join itinerary itin  on inc.Incident_ID = itin.incident_ID and itin.itinerarytype=0
inner join passenger p on p.incident_ID = inc.Incident_ID and p.isprimary=true
inner join articles art on inc.Incident_ID = art.incident_ID
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
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#lossCodes - Array of lossCodes (chargeback codes) to check against
#controllable - To return controllable lossCodes (1), uncontrollable lossCodes (0), or Both (2)
select s.stationcode, i.lossCode, c.description, count(*) 
from item i inner join incident inc on i.incident_ID = inc.Incident_ID 
  inner join station s on s.Station_ID = inc.stationassigned_ID inner join Company_irregularity_codes c on i.lossCode= c.loss_code 
  where inc.createdate >= :startDate and inc.createdate <=:endDate and find_in_set(c.loss_Code, :lossCodes)
  and c.companycode_ID='WN' and c.report_type=i.itemtype_ID and
  (case :controllable when 0 then c.controllable=0 when 1 then c.controllable=1 else 1=1 end)
  group by s.stationcode, c.loss_Code;
 
#------------------------------------------------------------------------------------

#Incident Return Type Report Detail
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#stationcodes - Array of 3 character station codes to check against. 3 character varchar
select inc.createDate as IncidentDate, inc.incident_id, st.description as ClosureType
  from incident inc 
  inner join station s on inc.stationassigned_ID=s.Station_ID 
  inner join item it on it.item_id = (select min(ii.item_id) from item ii where ii.incident_id=inc.Incident_ID limit 1)
  inner join status st on st.Status_ID = it.status_ID
    where inc.status_id=13 and (it.status_ID=50 or it.status_ID=59) 
    and inc.close_date>=:startDate and inc.close_date<=:endDate
    and find_in_set(s.stationcode, :stationcodes);
      
#Incident Return Type Report Summary
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#stationcodes - Array of 3 character station codes to check against. 3 character varchar
select s.stationcode, count(*) closedIncidents, 
  sum(case it.status_id when 50 then 1 else 0 end) as closedByDelivery, 
  sum(case it.status_id when 59 then 1 else 0 end) as closedByPPU
  from incident inc 
  inner join station s on inc.stationassigned_ID=s.Station_ID 
  inner join item it on it.item_id = (select min(ii.item_id) from item ii where ii.incident_id=inc.Incident_ID limit 1)
    where inc.status_id=13 and (it.status_ID=50 or it.status_ID=59)
    and inc.close_date>=:startDate and inc.close_date<=:endDate and find_in_set(s.stationcode, :stationcodes)
  group by s.stationCode;
  
#------------------------------------------------------------------------------------
      
#Bag Drop Compliance Summary 
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#stationcodes - Array of 3 character station codes to check against. 3 character varchar
select b.arrivalStationCode as Station, count(*) as numberOfArrivals,
sum(case when not isNull(b.bagDropTime) and b.bagDropTime!='' then 1 else 0 end) as arrivalsWithDropTimes,
(count(distinct case when not isNull(b.bagDropTime) and b.bagDropTime!='' then 1 else 0 end)/count(*)) * 100 as dropTimePercentage,
 avg(timestampdiff(MINUTE, case when actArrivalDate is null then schArrivalDate else actArrivalDate end, bagdroptime)) avgDropTime
  from bagdrop b 
  	where b.schArrivalDate>=:startDate 
  	and b.schArrivalDate<=:endDate 
  	and find_in_set(b.arrivalStationCode, :stationCodes) 
  		group by b.arrivalStationCode;
 
#Bag Drop Compliance Detail 
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#stationcodes - Array of 3 character station codes to check against. 3 character varchar
select b.schArrivalDate as FlightDate, b.flight as FlightNumber, b.actArrivalDate as ActualArrivalTime, 
  timestampdiff(MINUTE, case when actArrivalDate is null then schArrivalDate else actArrivalDate end, bagdroptime) as timeToCarousel 
  from bagdrop b 
  	where b.schArrivalDate>=:startDate and b.schArrivalDate<=:endDate 
  	and find_in_set(b.arrivalStationCode, :stationCodes) 
  	and bagDropTime!='' 
  		group by b.id;