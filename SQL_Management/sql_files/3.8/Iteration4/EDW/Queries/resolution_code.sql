set @qry = concat("
#START QUERY

select formatted_output 

#OUTFILE
from (
select concat_ws('|','H',date_format(@end, '%Y%m%d'), date_format(@end, '%Y%m%d')) formatted_output, 1 as seq
union all
select concat_ws('|','T',

#COUNT
0
) formatted_output, 3 as seq 

#ROOT QUERY
) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
