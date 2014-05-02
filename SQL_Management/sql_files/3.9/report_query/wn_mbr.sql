select * from 
(
select 
  s.stationcode as station_code,
  s.countrycode_ID as country_code,
  s.goal as station_goal, 
  r.name as region_name, 
  r.director as region_director, 
  r.target as region_goal, 
  r.active as region_active  
from station s
left outer join region r on s.Station_ID = r.id
where s.companycode_ID = 'WN'
) stationinfo

left outer join 
(
select 
  s.stationcode, 
  sum(case when it.losscode = 10 then 1 else 0 end) lc10,
  sum(case when it.losscode = 12 then 1 else 0 end) lc12,
  sum(case when it.losscode = 18 then 1 else 0 end) lc18,
  sum(case when it.losscode = 25 then 1 else 0 end) lc25,
  sum(case when it.losscode = 32 then 1 else 0 end) lc32,
  sum(case when it.losscode = 33 then 1 else 0 end) lc33,
  sum(case when it.losscode = 42 then 1 else 0 end) lc42,
  sum(case when it.losscode = 51 then 1 else 0 end) lc51,
  sum(case when it.losscode = 54 then 1 else 0 end) lc54,
  sum(case when it.losscode = 55 then 1 else 0 end) lc55,
  sum(case when it.losscode = 56 then 1 else 0 end) lc56,
  sum(case when it.losscode = 63 then 1 else 0 end) lc63,
  sum(case when it.losscode = 64 then 1 else 0 end) lc64,
  sum(case when it.losscode = 65 then 1 else 0 end) lc65,
  sum(case when it.losscode = 74 then 1 else 0 end) lc74,
  sum(case when it.losscode = 75 then 1 else 0 end) lc75,
  sum(case when it.losscode = 76 then 1 else 0 end) lc76,
  sum(case when it.losscode = 77 then 1 else 0 end) lc77,
  sum(case when it.losscode = 78 then 1 else 0 end) lc78,
  sum(case when it.losscode = 80 then 1 else 0 end) lc80,
  sum(case when it.losscode = 90 then 1 else 0 end) lc90,
  sum(case when it.losscode = 91 then 1 else 0 end) lc91,
  sum(case when it.losscode = 92 then 1 else 0 end) lc92,
  sum(case when it.losscode = 93 then 1 else 0 end) lc93
from incident i
left outer join station s on i.stationcreated_ID = s.station_id
left outer join item it on it.incident_ID = i.Incident_ID
left outer join item it2 on (it2.incident_id = i.incident_id and it.item_id > it2.item_id)
where i.createdate between '2013-01-01' and '2014-01-21'
and it2.item_id is null

group by s.stationcode) losscodes

on stationinfo.station_code = losscodes.stationcode
order by stationinfo.station_code asc
;