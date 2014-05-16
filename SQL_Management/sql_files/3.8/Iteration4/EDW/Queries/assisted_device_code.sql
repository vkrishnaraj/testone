set @qry = concat("
#START QUERY

select formatted_output 

#OUTFILE
from (
select concat_ws('|','H',date_format(@end, '%Y%m%d'), date_format(@end, '%Y%m%d')) formatted_output, 1 as seq
union all
select concat_ws('|',

#COLUMNS
id,description
) formatted_output, 2 as seq 

#ROOT QUERY
from category where type = 2
union all
select concat_ws('|','T',

#COUNT
count(id)
) formatted_output, 3 as seq 

#ROOT QUERY
from category where type = 2
) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
