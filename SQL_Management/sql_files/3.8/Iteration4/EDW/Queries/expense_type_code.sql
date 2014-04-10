set @qry = concat("
#START QUERY

select formatted_output 

#OUTFILE
into outfile 'D:/EDW/expense_type_code_", date_format(now(), '%Y-%m-%d'), ".csv' 
from (
select concat_ws('|','H',date_format(now(), '%Y%m%d'), date_format(now(), '%Y%m%d')) formatted_output, 1 as seq
union
select 'DRAFT|Draft' formatted_output, 2 as seq 
union
select 'VOUCH|LUV Voucher' formatted_output, 2 as seq 
union
select 'MILE|Mileage' formatted_output, 2 as seq 
union
select 'INVOICE|Invoice' formatted_output, 2 as seq 
union
select 'PSO|PSO' formatted_output, 2 as seq 

union
select concat_ws('|','T',

#COUNT
5
) formatted_output, 3 as seq 

) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
