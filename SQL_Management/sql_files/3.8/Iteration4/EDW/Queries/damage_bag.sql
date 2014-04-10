set @qry = concat("
#START QUERY

select formatted_output 

#OUTFILE
into outfile 'D:/EDW/damage_bag_", date_format(now(), '%Y-%m-%d'), ".csv' 
from (
select concat_ws('|','H',date_format(now(), '%Y%m%d'), date_format(date_add(now(), INTERVAL -1 DAY), '%Y%m%d')) formatted_output, 1 as seq
union
select concat_ws('|',

#COLUMNS
i.incident_ID,
(i.bagnumber + 1),
ifnull(i.resolutiondesc, ''),
i.color,
(i.lvlofdamage + 10),
i.bagtype,
'',
concat(x1.code,x2.code,x3.code),
i.claimchecknum,
case when s.stationcode = 'RYN' then 'Rynn''s' else '' end,
ifnull(i.damage, ''),
timestamp(inc.createdate, inc.createtime),
inc.lastupdated,
'Y',
ifnull(case when i.manufacturer_ID = 71 then i.manufacturer_other else m.description end, ''),
ifnull(i.assistDeviceType, ''),
case when ifnull(i.assistDeviceCheck, '') = 'Yes' then 'Y' else 'N' end,
ifnull(inc.close_date, ''),
ifnull(inc.close_date, ''),
ifnull(e.total, '0'),
'',
(inc.checkedlocation + 10),
'',
''
) formatted_output, 2 as seq 

#ROOT QUERY
from 
item i
left outer join incident inc on  i.incident_ID = inc.Incident_ID 
left outer join station s on inc.stationassigned_ID = s.Station_ID
left outer join manufacturer m on i.manufacturer_ID = m.Manufacturer_ID
left outer join xdescelement x1 on i.xdescelement_ID_1 = x1.XDesc_ID
left outer join xdescelement x2 on i.xdescelement_ID_2 = x2.XDesc_ID
left outer join xdescelement x3 on i.xdescelement_ID_3 = x3.XDesc_ID
left outer join
(
   select 
   sum(e2.voucheramt + e2.mileageamt + checkamt) as total,
   e2.incident_ID
   from expensepayout e2 group by e2.incident_ID
) e on i.incident_ID = e.incident_ID
where inc.createdate >= date(date_add(now(), INTERVAL -1 DAY)) 
and inc.itemtype_ID = 3 

union
select concat_ws('|','T',

#COUNT
count(i.Item_ID)
) formatted_output, 3 as seq 

#ROOT QUERY
from
item i
left outer join incident inc on i.incident_ID = inc.Incident_ID 
where inc.createdate >= date(date_add(now(), INTERVAL -1 DAY)) 
and inc.itemtype_ID = 3 
) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
