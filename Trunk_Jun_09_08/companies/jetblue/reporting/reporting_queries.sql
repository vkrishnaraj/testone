select * from address
select * from Articles
select * from bdo
select * from company_irregularity_codes
select * from companyCode
select * from expensepayout
select * from incident_assoc
SELECT * FROM INCIDENT_CLAIMCHECK
select * from item
select * from itinerary
select * from passenger
select * from Station
SELECT * FROM Status
select * from z_b6_claim_settlement
 
 
 
 
select ADDTIME(CREATEDATE,CREATETIME) as CreateDateTime, Incident_ID, itemtype_ID, stationcreated_ID, stationassigned_ID, faultstation_ID, loss_code, agent_ID, agentassigned_ID, recordlocator, 
                      manualreportnum, status_ID, ticketnumber, reportmethod, checkedlocation, numpassengers, numbagchecked, numbagreceived, voluntaryseparation, 
                      courtesyreport, tsachecked, customcleared, nonrevenue, lastupdated, ohd_lasttraced, createdate, createtime, close_date, version, wt_id, 
                      printedreceipt, wt_status from incident;
 
 
 
 
 
 
select
  concat(p0.lastname, ', ', p0.firstname) name,
  i.incident_ID 'pawob #',
ADDTIME(i.CREATEDATE,i.CREATETIME) as 'date created',
  i.createtime 'time created',
  group_concat(concat(itin0.airline, itin0.flightnum)) flights,
  routes0.dest destination,
  routes0.origin origin,
  station1.stationcode 'created station',
  status.description status,
  station2.stationcode 'charge city',
  i.loss_code 'charge code',
  i.recordlocator pnr,
  cc.claimchecknums 'bag tags',
  CASE i.checkedlocation
    WHEN 0 THEN '?'
    when 1 then 'curbside'
    when 2 then 'ticket counter'
    when 3 then 'gate'
    when 4 then 'remote'
    when 5 then 'plane side'
    when 6 then 'online'
    when 7 then 'kiosk'
    when 8 then 'dme'
    else '?'
  END 'check-in location',
  item1.bagcolors 'bag colors',
  item1.bagtypes 'bag types'
from
  incident i left outer join
    (select lastname, firstname, incident_id from passenger where passenger_ID in (select min(passenger_ID) passenger_ID from passenger where lastname is not null and lastname != '' group by incident_id)
    ) p0 on i.incident_Id = p0.incident_id
  left outer join
    (select airline, flightnum, incident_ID from itinerary where itinerarytype = 0
    ) itin0 on i.incident_id = itin0.incident_id
  left outer join
    (select incident_id, route,
       if(route not like '-%' and CHAR_LENGTH(route) > 1, SUBSTR(route, 1, 3), '?') origin,
       if(route not like '%-' and CHAR_LENGTH(route) > 1, SUBSTR(route, -3), '?') dest
     from (SELECT incident_id, GROUP_CONCAT(legs) route
           FROM (select incident_id, concat(legfrom,'-',legto) legs
                 from itinerary
                 where itinerarytype = 0
                 order by incident_id, departdate asc, schdeparttime asc, itinerary_id asc
                ) itin1
           GROUP BY incident_id
          ) routes
    ) routes0 on i.incident_id = routes0.incident_ID
  left outer join station station2 on i.faultstation_ID = station2.station_id
  left outer join (select incident_id, group_concat(claimchecknum) claimchecknums from incident_claimcheck group by incident_id) cc on i.incident_id = cc.incident_id
  left outer join (select incident_id, group_concat(color) bagcolors, group_concat(bagtype) bagtypes from item group by incident_id) item1 on i.incident_id = item1.incident_id
  left outer join station station1 on station1.station_id = i.stationcreated_id,
  status
where
  i.status_id = status.status_ID
 
group by
  i.incident_ID,
  i.createdate,
  i.createtime,
  routes0.dest,
  routes0.origin,
  station1.stationcode,
  status.description,
  station2.stationcode,
  i.loss_code,
  i.recordlocator,
  i.checkedlocation;