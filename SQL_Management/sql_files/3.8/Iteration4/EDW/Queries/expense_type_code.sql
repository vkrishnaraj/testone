set @qry = concat("
#START QUERY

select stuff 

#OUTFILE
into outfile 'D:/EDW/expense_type_code_", date_format(now(), '%Y%m%d'), ".csv' 
from (
select concat_ws('|','H',date_format(now(), '%Y%m%d'), date_format(now(), '%Y%m%d')) stuff, 1 as seq
union
select 'DRAFT|Draft' stuff, 2 as seq 
union
select 'VOUCH|LUV Voucher' stuff, 2 as seq 
union
select 'MILE|Mileage' stuff, 2 as seq 
union
select 'INVOICE|Invoice' stuff, 2 as seq 
union
select 'PSO|PSO' stuff, 2 as seq 

union
select concat_ws('|','T',

#COUNT
5
) stuff, 3 as seq 

) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
