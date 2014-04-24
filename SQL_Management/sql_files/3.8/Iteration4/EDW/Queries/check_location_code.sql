set @qry = concat("
#START QUERY

select formatted_output 

#OUTFILE
from (
select concat_ws('|','H',date_format(now(), '%Y%m%d'), date_format(now(), '%Y%m%d')) formatted_output, 1 as seq
union
select concat_ws('|',

#COLUMNS
(categoryVal + 10),
description
) formatted_output, 2 as seq 

#ROOT QUERY
from category where type = 4
union
select concat_ws('|','T',

#COUNT
count(id)
) formatted_output, 3 as seq 

#ROOT QUERY
from category where type = 4
) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
