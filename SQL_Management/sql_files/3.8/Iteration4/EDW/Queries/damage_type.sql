set @qry = concat("
#START QUERY

select stuff 

#OUTFILE
into outfile 'D:/EDW/damage_type_", date_format(now(), '%Y%m%d'), ".csv' 
from (
select concat_ws('|','H',date_format(now(), '%Y%m%d'), date_format(now(), '%Y%m%d')) stuff, 1 as seq
union
select '10|Minor' stuff, 2 as seq 
union
select '11|Major' stuff, 2 as seq 
union
select '12|Complete' stuff, 2 as seq
union
select 'T|3' stuff, 3 as seq 

#ROOT QUERY
#NOTHING
) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
