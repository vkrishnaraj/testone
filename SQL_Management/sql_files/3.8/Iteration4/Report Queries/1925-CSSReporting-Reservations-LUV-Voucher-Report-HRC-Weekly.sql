select 

ep.incident_ID as referenceID, 
date_format(ep.createdate,'%m/%d/%y') as issueDate, 
ifnull(ep.ordernum,'') as orderNum, 
concat(a.firstname,' ',a.lastname) as issueAgent, 
s.stationcode as location, 
concat(p.firstname,' ',p.lastname) as customerName, 
format(ep.voucheramt, 2) as amount

from expensepayout ep
left outer join agent a on a.Agent_ID = ep.agent_ID
left outer join station s on s.Station_ID = a.station_ID
left outer join passengerexp p on p.expensepayout_ID = ep.Expensepayout_ID

where 
ep.paytype = 'VOUCH' and 
ep.createdate >= :afterDate and 
ep.createdate <= :beforeDate and 
s.stationcode = 'HRC'

order by ep.agent_ID, ep.createdate
;
