set @qry = concat("
#START QUERY

select stuff 

#OUTFILE
into outfile 'D:/EDW/how_reported_", date_format(now(), '%Y%m%d'), ".csv' 
from (
select concat_ws('|','H',date_format(now(), '%Y%m%d'), date_format(now(), '%Y%m%d')) stuff, 1 as seq
union
select '10|In Person' stuff, 2 as seq 
union
select '18|Phone' stuff, 2 as seq 
union
select '17|In Writing' stuff, 2 as seq 
union
select '12|Call Center' stuff, 2 as seq 
union
select 'T|4' stuff, 3 as seq 

) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
