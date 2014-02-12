set @qry = concat("
#START QUERY

select stuff 

#OUTFILE
into outfile 'D:/EDW/missing_article_", date_format(now(), '%Y%m%d'), ".csv' 
from (
select concat_ws('|','H',date_format(now(), '%Y%m%d'), date_format(date_add(now(), INTERVAL -1 DAY), '%Y%m%d')) stuff, 1 as seq
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
) stuff, 2 as seq 

#ROOT QUERY
from articles a
left outer join incident i on a.incident_ID = i.Incident_ID
where i.lastupdated >= date(date_add(now(), INTERVAL -1 DAY))
union
select concat_ws('|','T',

#COUNT
count(a.Articles_ID)
) stuff, 3 as seq 

#ROOT QUERY
from articles a
left outer join incident i on a.incident_ID = i.Incident_ID 
where i.lastupdated >= date(date_add(now(), INTERVAL -1 DAY))
) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
