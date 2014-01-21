#Missing Article Detail Report by Flight Type
select inc.Incident_ID, concat(p.lastname,", ",p.firstname) as name,
inc.createdate, oc.categorytype , iv.description, 
itinroutes.route, itinroutes.flightnums, itinroutes.departdates, itinRoutes.initialDepartDate
from incident inc 
inner join itinerary itin  on inc.Incident_ID = itin.incident_ID and itin.itinerarytype=0
inner join passenger p on p.incident_ID = inc.Incident_ID and p.isprimary=true
inner join station s on (s.stationCode = itin.legfrom or s.stationcode=itin.legto) and s.companycode_ID='WN'
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
                WHERE it.itinerarytype=0 
                  ORDER BY it.incident_id, it.itinerary_id ASC) itin1 
                  GROUP BY incident_id) as itinRoutes on itinRoutes.incident_id=inc.incident_id
  where inc.createdate>=:startDate and inc.createdate<=:endDate and
  s.stationCode=:stationcode and  
  inc.itemtype_ID=2;