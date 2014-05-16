set @qry = concat("
#START QUERY

select formatted_output 

#OUTFILE
from (
select concat_ws('|','H',date_format(@end, '%Y%m%d'), date_format(@start, '%Y%m%d')) formatted_output, 1 as seq
union all
select concat_ws('|',

#COLUMNS
c1,
c2,
c3,
c4,
c5,
c6,
c7,
c8,
c9,
c10,
c11,
c12,
c13,
c14,
c15,
c16,
c17,
c18,
c19
) formatted_output, 2 as seq 

#ROOT QUERY
from (select
e.incident_ID as c1,
seq.row as c2,
ifnull(e.paytype, '') as c3,
e.expensetype_ID as c4,
timestamp(e.createdate, '00:00:00') as c5,
st.description as c6,
'' as c7,
timestamp(e.createdate, '00:00:00') as c8,
ifnull(e.comments, '') as c9,
ifnull((e.voucheramt + e.mileageamt + e.checkamt), '0') as c10,
ifnull(e.draftpaiddate, '') as c11,
ifnull(e.draft, '') as c12,
a.username as c13,
s.stationcode as c14,
ifnull(e.maildate, '') as c15,
ifnull(e.lastname, '') as c16,
ifnull(e.firstname, '') as c17,
ifnull(e.middlename, '') as c18,
i.lastupdated as c19

from expensepayout e
left outer join incident i on e.incident_ID = i.Incident_ID
left outer join agent a on a.agent_ID = e.agent_ID
left outer join station s on s.station_ID = a.station_ID
left outer join status st on st.Status_ID = e.status_ID
left outer join
(
   select 
   case when (e2.incident_ID = @incCheck) then (@row:=@row+1) ELSE (@row:=1) END as row,
   case when (e2.incident_ID = @incCheck) then e2.incident_ID ELSE (@incCheck:=e2.incident_ID) END as incident_ID, 
   e2.Expensepayout_ID
   from expensepayout e2, 
   (SELECT @row:=1, @incCheck:='0') as row_count
   order by e2.incident_ID desc
) seq on seq.Expensepayout_ID = e.Expensepayout_ID
where i.lastupdated >= date(@start) and i.lastupdated <= date(@end) 
and e.incident_ID is not null
order by e.incident_ID, seq.row) temp
union all
select concat_ws('|','T',

#COUNT
count(Expensepayout_ID)
) formatted_output, 3 as seq 

#ROOT QUERY
from expensepayout e
left outer join incident i on e.incident_ID = i.Incident_ID
where i.lastupdated >= date(@start) and i.lastupdated <= date(@end) 
and e.incident_ID is not null
) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
