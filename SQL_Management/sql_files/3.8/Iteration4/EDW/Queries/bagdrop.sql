set @qry = concat("
#START QUERY

select formatted_output 

#OUTFILE
from (
select concat_ws('|','H',date_format(@end, '%Y%m%d'), date_format(@start, '%Y%m%d')) formatted_output, 1 as seq
union all
select concat_ws('|',

#COLUMNS
ifnull(airline, ''),
ifnull(originStationCode, ''),
ifnull(arrivalStationCode, ''),
ifnull(flight, ''),
ifnull(schArrivalDate, ''),
ifnull(actArrivalDate, ''),
ifnull(bagDropTime, '')
) formatted_output, 2 as seq 

#ROOT QUERY
from bagdrop where lastUpdated >= @start and lastUpdated <= @end 
union all
select concat_ws('|','T',

#COUNT
count(id)
) formatted_output, 3 as seq 

#ROOT QUERY
from bagdrop where lastUpdated >= @start and lastUpdated <= @end 
) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
