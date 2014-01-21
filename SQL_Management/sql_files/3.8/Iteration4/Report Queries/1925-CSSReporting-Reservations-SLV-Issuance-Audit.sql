select 

s.stationcode as location, 
a.username as employeeNum, 
concat(a.firstname,' ',a.lastname) as ccrName, 
sum(ep.voucheramt) as amount, 
count(ep.Expensepayout_ID) as totalIssuance, 
format(avg(ep.voucheramt), 2) as averageAmount

from expensepayout ep
left outer join agent a on a.Agent_ID = ep.agent_ID
left outer join station s on s.Station_ID = a.station_ID

where 
ep.paytype = 'VOUCH' and 
ep.createdate >= :afterDate and 
ep.createdate <= :beforeDate and 
s.stationcode in ('CRC', 'HRC', 'ORC')

group by ep.agent_ID

order by s.stationcode, amount desc
;