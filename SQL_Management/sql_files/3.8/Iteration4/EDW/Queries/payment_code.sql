set @qry = concat("
#START QUERY

select formatted_output 

#OUTFILE
from (
select concat_ws('|','H',date_format(@end, '%Y%m%d'), date_format(@end, '%Y%m%d')) formatted_output, 1 as seq
union all
select concat_ws('|',

#COLUMNS
Expensetype_ID,description
) formatted_output, 2 as seq 

#ROOT QUERY
from expensetype
union all
select concat_ws('|','T',

#COUNT
count(Expensetype_ID)
) formatted_output, 3 as seq 

#ROOT QUERY
from expensetype
) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
