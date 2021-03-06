set @qry = concat("
#START QUERY

select formatted_output 

#OUTFILE
from (
select concat_ws('|','H',date_format(@end, '%Y%m%d'), date_format(@end, '%Y%m%d')) formatted_output, 1 as seq
union all
select concat_ws('|',

#COLUMNS
Status_ID,
description,
CASE WHEN Status_ID IN (801,802) THEN 'N' ELSE 'Y' END,
'Y'
) formatted_output, 2 as seq 

#ROOT QUERY
from status where table_id = 20
union all
select concat_ws('|','T',

#COUNT
count(Status_ID)
) formatted_output, 3 as seq 

#ROOT QUERY
from status where table_id = 20
) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
