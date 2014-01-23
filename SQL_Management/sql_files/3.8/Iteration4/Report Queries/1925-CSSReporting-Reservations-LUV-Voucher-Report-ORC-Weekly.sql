select 

ep.incident_ID as referenceID, 
date_format(ep.createdate,'%m/%d/%y') as issueDate, 
ifnull(ep.ordernum,'') as orderNum, 
ifnull(i.recordLocator, '') as pnr,
concat(a.firstname,' ',a.lastname) as issueAgent, 
s.stationcode as location, 
concat(ep.firstname,' ',ep.lastname) as customerName, 
ep.paytype as payType,
format(ep.voucheramt, 2) as amount

from expensepayout ep
left outer join agent a on a.Agent_ID = ep.agent_ID
left outer join station s on s.Station_ID = a.station_ID
left outer join incident i on i.incident_ID = ep.incident_ID

where 
ep.paytype = 'VOUCH' and 
ep.createdate >= :afterDate and 
ep.createdate <= :beforeDate and 
s.stationcode = 'ORC'

order by ep.agent_ID, ep.createdate
;
