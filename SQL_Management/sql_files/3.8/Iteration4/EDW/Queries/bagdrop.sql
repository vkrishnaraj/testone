set @qry = concat("
#START QUERY

select stuff 

#OUTFILE
into outfile 'D:/EDW/bagdrop_", date_format(now(), '%Y%m%d'), ".csv' 
from (
select concat_ws('|','H',date_format(now(), '%Y%m%d'), date_format(date_add(now(), INTERVAL -1 DAY), '%Y%m%d')) stuff, 1 as seq
union
select concat_ws('|',

#COLUMNS
ifnull(airline, ''),
ifnull(originStationCode, ''),
ifnull(arrivalStationCode, ''),
ifnull(flight, ''),
ifnull(schArrivalDate, ''),
ifnull(actArrivalDate, ''),
ifnull(bagDropTime, '')
) stuff, 2 as seq 

#ROOT QUERY
from bagdrop where lastUpdated >= date_add(now(), INTERVAL -1 DAY)
union
select concat_ws('|','T',

#COUNT
count(id)
) stuff, 3 as seq 

#ROOT QUERY
from bagdrop where lastUpdated >= date_add(now(), INTERVAL -1 DAY)
) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
