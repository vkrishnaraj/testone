set @qry = concat("
#START QUERY

select stuff 

#OUTFILE
into outfile 'D:/EDW/chargeback_code_", date_format(now(), '%Y%m%d'), ".csv' 
from (
select concat_ws('|','H',date_format(now(), '%Y%m%d'), date_format(now(), '%Y%m%d')) stuff, 1 as seq
union
select concat_ws('|',

#COLUMNS
loss_code,
description,
CASE (departStation) WHEN 0 THEN 'N' ELSE 'Y' END,
CASE (transferStation) WHEN 0 THEN 'N' ELSE 'Y' END,
CASE (destinationStation) WHEN 0 THEN 'N' ELSE 'Y' END,
CASE (controllable) WHEN 0 THEN 'N' ELSE 'Y' END,
CASE (active) WHEN 0 THEN 'N' ELSE 'Y' END
) stuff, 2 as seq 

#ROOT QUERY
from company_irregularity_codes 
where companycode_ID = 'WN' 
group by loss_code

union
select concat_ws('|','T',

#COUNT
count(*)
) stuff, 3 as seq 

#ROOT QUERY
from (select * from company_irregularity_codes where companycode_ID = 'WN' 
group by loss_code) temp2
) temp
order by seq, stuff");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
