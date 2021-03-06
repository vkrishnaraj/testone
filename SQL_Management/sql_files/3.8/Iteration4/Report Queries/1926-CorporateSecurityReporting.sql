#Missing Article Detail Report by Flight Type
#itintype - the type of station on the flight itinerary - O for Origin station, T for Transfer Station, D for Destination Station. Single character variable
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#stationcode - the code of the Station to check against. 3 character varchar

select inc.Incident_ID, p.lastname ,p.firstname,
inc.createdate as reportDate, art.article as itemName, art.description as articleDescription, 
itinroutes.route as Route, itinroutes.flightnums as FlightNums, itinroutes.departdates as DepDates, itinRoutes.initialDepartDate as depDate
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
   inc.itemtype_ID=2 group by art.Articles_ID;