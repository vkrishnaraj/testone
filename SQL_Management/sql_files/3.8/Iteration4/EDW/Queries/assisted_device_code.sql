set @qry = concat("
#START QUERY

select stuff 

#OUTFILE
into outfile 'D:/EDW/assisted_device_code_", date_format(now(), '%Y%m%d'), ".csv' 
from (
select concat_ws('|','H',date_format(now(), '%Y%m%d'), date_format(now(), '%Y%m%d')) stuff, 1 as seq
union
select concat_ws('|',

#COLUMNS
id,description
) stuff, 2 as seq 

#ROOT QUERY
from category where type = 2
union
select concat_ws('|','T',

#COUNT
count(id)
) stuff, 3 as seq 

#ROOT QUERY
from category where type = 2
) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
