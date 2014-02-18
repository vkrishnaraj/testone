set @qry = concat("
#START QUERY

select formatted_output 

#OUTFILE
into outfile 'D:/EDW/iata_description_code_", date_format(now(), '%Y%m%d'), ".csv' 
from (
select concat_ws('|','H',date_format(now(), '%Y%m%d'), date_format(now(), '%Y%m%d')) formatted_output, 1 as seq
union
select concat_ws('|',

#COLUMNS
concat(a.code,b.code,c.code),
concat(a.description,'/',b.description,'/',c.description)
) formatted_output, 2 as seq

#ROOT QUERY
from xdescelement a, xdescelement b, xdescelement c

union
select concat_ws('|','T',

#COUNT
count(*)
) formatted_output, 3 as seq 

#ROOT QUERY
from xdescelement a, xdescelement b, xdescelement c
) temp
order by seq, formatted_output");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
