set @qry = concat("
#START QUERY

select formatted_output 

#OUTFILE
into outfile 'D:/EDW/bag_description_", date_format(now(), '%Y%m%d'), ".csv' 
from (
select concat_ws('|','H',date_format(now(), '%Y%m%d'), date_format(date_add(now(), INTERVAL -1 DAY), '%Y%m%d')) formatted_output, 1 as seq
union
select concat_ws('|',

#COLUMNS
i.incident_ID,
(i.bagnumber + 1),
(inc.checkedlocation + 10),
ifnull(i.assistDeviceType, ''),
i.bagtype,
concat(x1.code,x2.code,x3.code),
case when inc.itemtype_ID = 1 then 'Y' else 'N' end,
s.stationcode,
ifnull(c.claimchecknum, ''),
timestamp(inc.createdate, inc.createtime),
ifnull(case when i.manufacturer_ID = 71 then i.manufacturer_other else m.description end, ''),
inv.description,
case when ifnull(i.assistDeviceCheck, '') = 'Yes' then 'Y' else 'N',
ifnull(i.posId, ''),
case when isnull(bi.bdo_id) and i.status_ID != 50 then 'N' else 'Y' end, 
ifnull(i.lnameonbag, ''),
ifnull(i.fnameonbag, ''),
ifnull(i.mnameonbag, ''),
inc.lastupdated,
case when isnull(i.OHD_ID) then 'N' else 'Y' end
) formatted_output, 2 as seq 

#ROOT QUERY
from item i
left outer join incident inc on  i.incident_ID = inc.Incident_ID 
left outer join station s on inc.stationassigned_ID = s.Station_ID
left outer join manufacturer m on i.manufacturer_ID = m.Manufacturer_ID
left outer join xdescelement x1 on i.xdescelement_ID_1 = x1.XDesc_ID
left outer join xdescelement x2 on i.xdescelement_ID_2 = x2.XDesc_ID
left outer join xdescelement x3 on i.xdescelement_ID_3 = x3.XDesc_ID
left outer join 
(
   select 
   case when (ic.incident_ID = @incCheck) then (@row:=@row+1) ELSE (@row:=0) END as row,
   case when (ic.incident_ID = @incCheck) then ic.incident_ID ELSE (@incCheck:=ic.incident_ID) END as incident_ID, 
   ic.claimchecknum
   from incident_claimcheck ic, 
   (SELECT @row:=0, @incCheck:='0') as row_count
) c on inc.Incident_ID = c.incident_ID and i.bagnumber = c.row
left outer join
(
   select
   inv2.item_ID as item_ID,
   group_concat(inv2.description separator '/') as description
   from item_inventory inv2 group by inv2.item_ID
) inv on i.Item_ID = inv.item_ID
left outer join
(
   select
   bi2.bdo_ID,
   bi2.item_ID
   from item_bdo bi2 where bi2.canceled = 0 group by bi2.item_ID
) bi on i.Item_ID = bi.item_ID
where 
inc.lastupdated >= date(date_add(now(), INTERVAL -1 DAY))
and inc.itemtype_ID = 1

union
select concat_ws('|','T',

#COUNT
count(i.Item_ID)
) formatted_output, 3 as seq 

#ROOT QUERY
from item i
left outer join incident inc on  i.incident_ID = inc.Incident_ID 
where inc.lastupdated >= date(date_add(now(), INTERVAL -1 DAY)) 
and inc.itemtype_ID = 1
) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
