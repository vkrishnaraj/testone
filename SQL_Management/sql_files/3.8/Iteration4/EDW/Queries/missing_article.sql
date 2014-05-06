set @qry = concat("
#START QUERY

select formatted_output 

#OUTFILE
from (
select concat_ws('|','H',date_format(@end, '%Y%m%d'), date_format(@start, '%Y%m%d')) formatted_output, 1 as seq
union
select concat_ws('|',

#COLUMNS
a.incident_ID,
'1',
a.statusId,
ifnull(a.enteredDate, ''),
ifnull(a.article, ''),
ifnull(a.description, ''),
ifnull(a.purchasedate, ''),
ifnull(a.cost, '0'),
i.lastupdated,
case when a.statusId = 800 then 'Y' else 'N' end
) formatted_output, 2 as seq 

#ROOT QUERY
from articles a
left outer join incident i on a.incident_ID = i.Incident_ID
where i.lastupdated >= date(@start) and i.lastupdated <= date(@end) 
union
select concat_ws('|','T',

#COUNT
count(a.Articles_ID)
) formatted_output, 3 as seq 

#ROOT QUERY
from articles a
left outer join incident i on a.incident_ID = i.Incident_ID 
where i.lastupdated >= date(@start) and i.lastupdated <= date(@end) 
) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
