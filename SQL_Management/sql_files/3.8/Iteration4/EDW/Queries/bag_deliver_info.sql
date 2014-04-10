set @qry = concat("
#START QUERY

select formatted_output 

#OUTFILE
into outfile 'D:/EDW/bag_deliver_info_", date_format(now(), '%Y-%m-%d'), ".csv' 
from (
select concat_ws('|','H',date_format(now(), '%Y%m%d'), date_format(date_add(now(), INTERVAL -1 DAY), '%Y%m%d')) formatted_output, 1 as seq
union all
select concat_ws('|',

#COLUMNS
b.incident_ID,
CASE i.itemtype_ID WHEN 2 THEN '0' ELSE (it.bagnumber + 1) END,
b.servicelevel_ID,
timestamp(b.createdate,b.createtime),
b.BDO_ID,
ifnull(b.deliverydate,''),
ifnull(timestamp(b.pickupdate,b.pickuptime), ''),
concat(ifnull(concat(p.lastname, ', '),''), ifnull(p.firstname, '')),
b.servicelevel_ID,
ifnull(b.delivery_comments, ''),
ifnull(b.origDelivCost, ''),
s.stationcode,
a.username,
timestamp(b.createdate,b.createtime),
CASE i.itemtype_ID WHEN 2 THEN (it.bagnumber + 1) ELSE '0' END
) formatted_output, 2 as seq 

#ROOT QUERY
from bdo b 
left outer join bdo_passenger p on p.bdo_ID = b.BDO_ID
left outer join incident i on i.Incident_ID = b.incident_ID
left outer join item it on it.incident_ID = b.incident_ID
left outer join station s on s.Station_ID = b.station_ID
left outer join agent a on a.Agent_ID = b.agent_ID
where b.createdate >= date(date_add(now(), INTERVAL -1 DAY))
and b.incident_ID is not null

union all
select concat_ws('|','T',

#COUNT
count(*)
) formatted_output, 3 as seq 

#ROOT QUERY
from bdo b 
left outer join bdo_passenger p on p.bdo_ID = b.BDO_ID
left outer join incident i on i.Incident_ID = b.incident_ID
left outer join item it on it.incident_ID = b.incident_ID
left outer join station s on s.Station_ID = b.station_ID
left outer join agent a on a.Agent_ID = b.agent_ID
where b.createdate >= date(date_add(now(), INTERVAL -1 DAY))
and b.incident_ID is not null
) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
