set @qry = concat("
#START QUERY

select stuff 

#OUTFILE
into outfile 'D:/EDW/iata_description_code_", date_format(now(), '%Y%m%d'), ".csv' 
from (
select concat_ws('|','H',date_format(now(), '%Y%m%d'), date_format(now(), '%Y%m%d')) stuff, 1 as seq
union
select concat_ws('|',

#COLUMNS
concat(a.code,b.code,c.code),
concat(a.description,'/',b.description,'/',c.description)
) stuff, 2 as seq

#ROOT QUERY
from xdescelement a, xdescelement b, xdescelement c

union
select concat_ws('|','T',

#COUNT
count(*)
) stuff, 3 as seq 

#ROOT QUERY
from xdescelement a, xdescelement b, xdescelement c
) temp
order by seq, stuff");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
