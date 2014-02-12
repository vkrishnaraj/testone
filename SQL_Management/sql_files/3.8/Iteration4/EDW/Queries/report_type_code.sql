set @qry = concat("
#START QUERY

select stuff 

#OUTFILE
into outfile 'D:/EDW/report_type_code_", date_format(now(), '%Y%m%d'), ".csv' 
from (
select concat_ws('|','H',date_format(now(), '%Y%m%d'), date_format(now(), '%Y%m%d')) stuff, 1 as seq
union
select concat_ws('|',

#COLUMNS
ItemType_ID,description
) stuff, 2 as seq 

#ROOT QUERY
from itemtype
union
select concat_ws('|','T',

#COUNT
count(ItemType_ID)
) stuff, 3 as seq 

#ROOT QUERY
from itemtype
) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
