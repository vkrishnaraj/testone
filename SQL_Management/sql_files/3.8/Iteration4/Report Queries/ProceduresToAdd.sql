
drop procedure getAgentAuditReport;
DELIMITER //
create procedure getAgentAuditReport (in startDate datetime, in endDate datetime, in agentlist varchar(100), in percent varchar(3))
begin
declare percentlimit int default 0;
set percentlimit =(SELECT ceiling((COUNT(inc.incident_id)*percent/100)) FROM incident inc inner join agent a on inc.agentassigned_ID=a.Agent_ID 
    inner join incident_Activity ia on ia.incident=inc.incident_id
    inner join activity act ON ia.activity = act.id
    left outer join Passenger p ON p.incident_ID = inc.Incident_ID 
    where inc.createdate>=startDate and inc.createdate<=endDate and find_in_set(a.username, agentlist));
select a.firstname,a.lastname, a.username, inc.Incident_ID, inc.createdate,
  act.code, act.description,p.lastname,p.firstname 
  from incident inc 
  inner join agent a on inc.agentassigned_ID=a.Agent_ID 
  inner join incident_Activity ia on ia.incident=inc.incident_id 
  inner join activity act ON ia.activity = act.id 
  left outer join Passenger p ON p.incident_ID = inc.Incident_ID 
  where inc.createdate>=startDate and inc.createdate<=endDate
  and find_in_set(a.username, agentlist) 
  limit percentlimit;
end //


drop procedure getBSOAgentAuditReport;
DELIMITER //
create procedure getBSOAgentAuditReport (in startDate datetime, in endDate datetime, in stationcode varchar(3),in agentUsername varchar(100), in itemtype varchar(1), in percent varchar(3))
begin
declare percentlimit int default 0;
set percentlimit =(SELECT ceiling((COUNT(i.incident_id)*percent/100)) FROM incident i inner join station s on i.stationassigned_ID=s.Station_ID 
   inner join Passenger p on p.incident_ID = i.Incident_ID and p.isprimary = true
   inner join agent a on a.agent_id=i.agentassigned_ID
    where i.createdate>= startDate and i.createdate <= endDate and s.stationcode=stationcode
	  and a.username=agentUsername and i.itemtype_ID=itemType);
select i.incident_id, timestamp(i.createdate, i.createtime) as takenDate , concat(p.firstname," ",p.lastname) as customerName
   from incident i inner join station s on i.stationassigned_ID=s.Station_ID 
   inner join Passenger p on p.incident_ID = i.Incident_ID and p.isprimary = true
   inner join agent a on a.agent_id=i.agentassigned_ID
	  where i.createdate>= startdate and i.createdate <= enddate and s.stationcode=stationcode
	  and a.username=agentUsername and i.itemtype_ID=itemType limit percentlimit;
end //