set @qry = concat("
#START QUERY

select stuff 

#OUTFILE
into outfile 'D:/EDW/baggage_return_info_", date_format(now(), '%Y%m%d'), ".csv' 
from (
select concat_ws('|','H',date_format(now(), '%Y%m%d'), date_format(date_add(now(), INTERVAL -1 DAY), '%Y%m%d')) stuff, 1 as seq
union
select concat_ws('|',

#COLUMNS
i.incident_ID,
(i.bagnumber + 1),
case when ifnull(i.lossCode, 0) < 1 then '' else i.lossCode end,
ifnull(s.stationcode, ''),
ifnull(case when b.canceled = 0 then timestamp(b.createdate, b.createtime) else inc.close_date end, ''),
ifnull(case when b.canceled = 0 then ba.username else '' end, ''),
case when b.canceled = 0 then 'DELIVERED' when i.status_ID = 50 then 'PPU' else '' end,
inc.lastupdated,
ifnull(case when b.canceled = 0 then ba.username else a.username end, ''),
ifnull(i.posId, ''),
'N',
case when (isnull(b.canceled) or b.canceled = 1) and i.status_ID != 50 then 'N' else 'Y' end
) stuff, 2 as seq 

#ROOT QUERY
from item i
left outer join incident inc on inc.Incident_ID = i.incident_ID
left outer join station s on s.Station_ID = i.faultStation_id
left outer join item_bdo ib on i.Item_ID = ib.item_ID
left outer join 
(
	select b2.*, ib.item_ID from
	bdo b2
	left outer join item_bdo ib on ib.bdo_ID = b2.BDO_ID
	where b2.canceled = 0
) b on i.Item_ID = b.item_ID
left outer join agent ba on b.agent_ID = ba.Agent_ID
left outer join agent a on inc.agent_ID = a.Agent_ID
where inc.lastupdated >= date(date_add(now(), INTERVAL -1 DAY))
and inc.itemtype_ID = 1

union
select concat_ws('|','T',

#COUNT
count(i.Item_ID)
) stuff, 3 as seq 

#ROOT QUERY
from item i
left outer join incident inc on inc.Incident_ID = i.incident_ID
where inc.lastupdated >= date(date_add(now(), INTERVAL -1 DAY))
and inc.itemtype_ID = 1
) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
