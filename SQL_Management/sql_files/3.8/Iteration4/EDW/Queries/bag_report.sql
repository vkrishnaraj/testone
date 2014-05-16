set @qry = concat("
#START QUERY

select formatted_output 

#OUTFILE
from (
select concat_ws('|','H',date_format(@end, '%Y%m%d'), date_format(@start, '%Y%m%d')) formatted_output, 1 as seq
union all
select concat_ws('|',

#COLUMNS
i.incident_ID,
(i.reportmethod + 10),
ifnull(i.ticketnumber, ''),
timestamp(i.createdate, i.createtime),
s.stationcode,
ifnull(i.recordlocator, ''),
ifnull(m.membershipnum, ''),
'N',
ifnull(p.dlstate, ''),
ifnull(c.amountClaimed, '0'),
ifnull(i.manualreportnum, ''),
ag.username,
case i.nonrevenue when 0 then 'N' else 'Y' end,
'N',
ifnull(p.lastname, ''),
ifnull(p.firstname, ''),
ifnull(p.middlename, ''),
case when !isnull(i2.incident_ID) and timestamp(i.createdate, i.createtime) > timestamp(i2.createdate, i2.createtime) then 'Y' else 'N' end,
i.numpassengers,
ifnull(i.numbagchecked, 0),
i.numbagreceived,
timestamp(i.createdate, i.createtime),
i.lastupdated,
case when ifnull(i.numbagchecked, 0) > 0 then 'Y' else 'N' end,
case when i.courtesyReasonId = 1101 then 'Y' else 'N' end,
case when i.courtesyReasonId = 1000 then 'Y' else 'N' end,
'N',
case i.voluntaryseparation when 0 then 'N' else 'Y' end,
case i.courtesyReasonId when 900 then i.courtesyDescription when 0 then 'Please Select' else st.description end,
case i.courtesyreport when 0 then 'N' else 'Y' end,
ifnull(i.rxTimestamp, ''),
(i.checkedlocation + 10),
case i.itemtype_ID when 1 then 'Y' else 'N' end,
case i.itemtype_ID when 3 then 'Y' else 'N' end,
i.itemtype_ID

) formatted_output, 2 as seq 

#ROOT QUERY
from incident i 
left outer join 
(
   select 
   case when (p2.incident_ID = @incCheck) then (@row:=@row+1) ELSE (@row:=1) END as row,
   case when (p2.incident_ID = @incCheck) then p2.incident_ID ELSE (@incCheck:=p2.incident_ID) END as incident_ID, 
   p2.Passenger_ID,
   p2.firstname,
   p2.lastname,
   p2.middlename,
   p2.dlstate,
   p2.membership_ID
   from passenger p2, 
   (SELECT @row:=1, @incCheck:='0') as row_count
   where p2.isprimary = 1
   order by p2.incident_ID desc
) p on i.Incident_ID = p.incident_ID and p.row = 1
left outer join address a on p.Passenger_ID = a.passenger_ID 
left outer join airlinemembership m on p.membership_ID = m.Membership_ID 
left outer join station s on i.stationcreated_ID = s.Station_ID
left outer join fsclaim c on i.Incident_ID = c.ntIncident_Incident_ID
left outer join agent ag on i.agent_ID = ag.Agent_ID
left outer join incident_assoc ia on i.Incident_ID = ia.incident_ID
left outer join incident i2 on ia.assoc_ID = i2.Incident_ID
left outer join status st on i.courtesyReasonId = st.Status_ID
where i.lastupdated >= date(@start) and i.lastupdated <= date(@end) 

union all
select concat_ws('|','T',

#COUNT
count(i.Incident_ID)
) formatted_output, 3 as seq 

#ROOT QUERY
from incident i 
left outer join 
(
   select 
   case when (p2.incident_ID = @incCheck) then (@row:=@row+1) ELSE (@row:=1) END as row,
   case when (p2.incident_ID = @incCheck) then p2.incident_ID ELSE (@incCheck:=p2.incident_ID) END as incident_ID, 
   p2.Passenger_ID,
   p2.firstname,
   p2.lastname,
   p2.middlename,
   p2.dlstate,
   p2.membership_ID
   from passenger p2, 
   (SELECT @row:=1, @incCheck:='0') as row_count
   where p2.isprimary = 1
   order by p2.incident_ID desc
) p on i.Incident_ID = p.incident_ID and p.row = 1
left outer join address a on p.Passenger_ID = a.passenger_ID 
left outer join airlinemembership m on p.membership_ID = m.Membership_ID 
left outer join station s on i.stationcreated_ID = s.Station_ID
left outer join fsclaim c on i.Incident_ID = c.ntIncident_Incident_ID
left outer join agent ag on i.agent_ID = ag.Agent_ID
left outer join incident_assoc ia on i.Incident_ID = ia.incident_ID
left outer join incident i2 on ia.assoc_ID = i2.Incident_ID
left outer join status st on i.courtesyReasonId = st.Status_ID
where i.lastupdated >= date(@start) and i.lastupdated <= date(@end) 
) temp
order by seq");
prepare stmt from @qry;
execute stmt;
deallocate prepare stmt;
