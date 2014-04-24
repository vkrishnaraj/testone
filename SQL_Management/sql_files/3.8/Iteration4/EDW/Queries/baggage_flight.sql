set @qry = concat("
#START QUERY

select formatted_output 

#OUTFILE
from (
select concat_ws('|','H',date_format(now(), '%Y%m%d'), date_format(date_add(now(), INTERVAL -1 DAY), '%Y%m%d')) formatted_output, 1 as seq
union
select concat_ws('|',

#COLUMNS
c1,
c2,
c3,
c4,
c5,
c6,
c7,
c8,
c9,
c10
) formatted_output, 2 as seq 

#ROOT QUERY
from (select
i.incident_ID as c1,
seq.row as c2,
ifnull(timestamp(i.arrivedate,ifnull(i.actarrivetime, '00:00')), '') as c3,
ifnull(i.airline, '') as c4,
timestamp(inc.createdate, inc.createtime) as c5,
ifnull(i.flightnum, '') as c6,
inc.lastupdated as c7,
ifnull(i.legfrom, '') as c8,
ifnull(i.legto, '') as c9,
'0.00' as c10
from itinerary i
left outer join incident inc on inc.Incident_ID = i.incident_ID 
left outer join
(
   select 
   case when (i2.incident_ID = @incCheck) then (@row:=@row+1) ELSE (@row:=1) END as row,
   case when (i2.incident_ID = @incCheck) then i2.incident_ID ELSE (@incCheck:=i2.incident_ID) END as incident_ID, 
   i2.Itinerary_ID
   from itinerary i2, 
   (SELECT @row:=1, @incCheck:='0') as row_count
   where i2.itinerarytype = 1
   order by i2.incident_ID desc, i2.Itinerary_ID
) seq on seq.Itinerary_ID = i.Itinerary_ID
where inc.lastupdated >= date(date_add(now(), INTERVAL -1 DAY))
and i.itinerarytype = 1
order by i.incident_ID, seq.row) temp
union
select concat_ws('|','T',

#COUNT
count(i.Itinerary_ID)
) formatted_output, 3 as seq 

#ROOT QUERY
from itinerary i
left outer join incident inc on inc.Incident_ID = i.incident_ID 
where inc.lastupdated >= date(date_add(now(), INTERVAL -1 DAY))
and i.itinerarytype = 1
) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
