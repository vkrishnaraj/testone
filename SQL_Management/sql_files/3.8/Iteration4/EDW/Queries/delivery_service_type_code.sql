set @qry = concat("
#START QUERY

select formatted_output 

#OUTFILE
into outfile 'D:/EDW/delivery_service_type_code_", date_format(now(), '%Y-%m-%d'), ".csv' 
from (
select concat_ws('|','H',date_format(now(), '%Y%m%d'), date_format(now(), '%Y%m%d')) formatted_output, 1 as seq
union
select concat_ws('|',

#COLUMNS
servicelevel_ID,description
) formatted_output, 2 as seq 

#ROOT QUERY
from deliver_servicelevel
union
select concat_ws('|','T',

#COUNT
count(*)
) formatted_output, 3 as seq 

#ROOT QUERY
from deliver_servicelevel
) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
