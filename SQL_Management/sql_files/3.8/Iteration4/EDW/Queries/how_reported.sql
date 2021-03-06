set @qry = concat("
#START QUERY

select formatted_output 

#OUTFILE
from (
select concat_ws('|','H',date_format(@end, '%Y%m%d'), date_format(@end, '%Y%m%d')) formatted_output, 1 as seq
union
select '10|In Person' formatted_output, 2 as seq 
union
select '18|Phone' formatted_output, 2 as seq 
union
select '17|In Writing' formatted_output, 2 as seq 
union
select '12|Call Center' formatted_output, 2 as seq 
union
select 'T|4' formatted_output, 3 as seq 

) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
