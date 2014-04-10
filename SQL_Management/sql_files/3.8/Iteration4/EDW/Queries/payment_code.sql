set @qry = concat("
#START QUERY

select formatted_output 

#OUTFILE
into outfile 'D:/EDW/payment_code_", date_format(now(), '%Y-%m-%d'), ".csv' 
from (
select concat_ws('|','H',date_format(now(), '%Y%m%d'), date_format(now(), '%Y%m%d')) formatted_output, 1 as seq
union
select concat_ws('|',

#COLUMNS
Expensetype_ID,description
) formatted_output, 2 as seq 

#ROOT QUERY
from expensetype
union
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
