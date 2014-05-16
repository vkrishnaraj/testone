set @qry = concat("
#START QUERY

select formatted_output 

#OUTFILE
from (
select concat_ws('|','H',date_format(@end, '%Y%m%d'), date_format(@end, '%Y%m%d')) formatted_output, 1 as seq
union all
select concat_ws('|',

#COLUMNS
loss_code,
description,
CASE (departStation) WHEN 0 THEN 'N' ELSE 'Y' END,
CASE (transferStation) WHEN 0 THEN 'N' ELSE 'Y' END,
CASE (destinationStation) WHEN 0 THEN 'N' ELSE 'Y' END,
CASE (controllable) WHEN 0 THEN 'N' ELSE 'Y' END,
CASE (active) WHEN 0 THEN 'N' ELSE 'Y' END
) formatted_output, 2 as seq 

#ROOT QUERY
from company_irregularity_codes 
where companycode_ID = 'WN' 
group by loss_code

union all
select concat_ws('|','T',

#COUNT
count(*)
) formatted_output, 3 as seq 

#ROOT QUERY
from (select * from company_irregularity_codes where companycode_ID = 'WN' 
group by loss_code) temp2
) temp
order by seq, formatted_output");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
