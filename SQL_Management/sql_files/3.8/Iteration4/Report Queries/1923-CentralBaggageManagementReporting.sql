# Admin Activity Summary Report - Detail and Summary - Is based on and returns GMT Time. 
#The query should return all the information needed for both Detail and Summary Reports. 
#The detail vs summary selection should mainly control how the values are summed up and displayed
select count(ia.id), act.code, act.description, concat(a.firstname," ",a.lastname) as agentname, a.username
  from incident_activity ia 
    inner join activity act on ia.activity = act.id 
    inner join agent a on ia.agent = a.Agent_ID 
      where ia.createDate>=:startDate and ia.createDate <=:endDate and (act.code like '%99%' or act.code like '%256%' or act.code like '%55%')
      group by a.Agent_ID, act.code
      order by concat(a.firstname," ",a.lastname), act.code;
#----------------------------------

#Assigned Claims by Representative - Is based on and returns GMT Time
select concat(a.firstname," ",a.lastname) as agentname, a.username, 
  inc.Incident_ID, inc.createdate, (case inc.itemtype_ID when 1 then "Lost" when 2 then "Missing" when 3 then "Damage" else "" end) as type 
    from incident inc 
    inner join agent a on inc.agentassigned_ID=a.Agent_ID 
      where inc.createdate>=:startdate and inc.createdate<=:enddate 
      order by a.firstname, (case inc.itemtype_ID when 1 then "Lost" when 2 then "Missing" when 3 then "Damage" else "" end);
#----------------------------------

#Agent Audit Report -Is based on and returns GMT Time. Is a Procedure based on percentage requirements
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
select concat(a.firstname,' ',a.lastname) as agentname, a.username, inc.Incident_ID, inc.createdate,
  act.code, act.description, concat(p.lastname,', ',p.firstname)
  as custName from incident inc inner join agent a on inc.agentassigned_ID=a.Agent_ID 
  inner join incident_Activity ia on ia.incident=inc.incident_id 
  inner join activity act ON ia.activity = act.id 
  left outer join Passenger p ON p.incident_ID = inc.Incident_ID 
  where inc.createdate>=startDate and inc.createdate<=endDate
  and find_in_set(a.username, agentlist) 
  limit percentlimit;
end //
call getAgentAuditReport (:startDate, :endDate, :agentlist,:percent);
#----------------------------------

#Inventory Productivity by Representative - Is based on and returns GMT Time
select concat(a.firstname," ",a.lastname) agentname, o.OHD_ID, o.founddate, concat(o.color," ",o.type) as colorType, s.description as bagState
  from ohd o inner join status s on s.Status_ID = o.status_ID inner join agent a 
  on o.agent_ID = a.Agent_ID where founddate >=:startDate and founddate <=:endDate order by concat(a.firstname," ",a.lastname);
#----------------------------------
  
#Report Claims by Agent (Summary) - Is based on and returns GMT Time
select a.username,concat(a.firstname," ",a.lastname) as agentName, count(ep.Expensepayout_ID) as claimNum, sum(ep.checkamt+ep.voucheramt+ep.creditcard_refund) as total, round(sum(ep.checkamt+ep.voucheramt+ep.creditcard_refund)/count(ep.Expensepayout_ID),2) as average 
from  expensepayout ep inner join agent a on ep.agent_ID = a.Agent_ID where ep.createdate>=:startDate and ep.createdate <=:endDate group by a.username;

#Report Claims by Agent (Detail) - Is based on and returns GMT Time 
select a.username,concat(a.firstname," ",a.lastname) as agentName, ep.incident_ID, concat(a.firstname," ",pe.lastname) as custName, 
  (ep.checkamt+ep.voucheramt+ep.creditcard_refund) as total
  from expensepayout ep 
    inner join agent a on ep.agent_ID = a.Agent_ID 
    left outer join passengerexp pe on pe.expensepayout_ID = ep.Expensepayout_ID
      where ep.createdate>=:startDate and ep.createdate <=:endDate;
#----------------------------------

#CBS SLV Issuance Report - Is based on and returns GMT Time
select concat(a.firstname," ",a.lastname) as agentName, ep.createdate, concat(a.firstname," ",pe.lastname) as custName, ep.incident_id, inc.recordlocator, ep.paycode, 
(case inc.itemtype_ID when 1 then "LOST" when 2 then "MISSING" when 3 then "DAMAGE" else "" end) as reportType,
(case ep.status_id when 54 then "DENY" when 55 then "SETTLE" else "PENDING" end) as resolveType, ep.voucheramt
  from expensepayout ep inner join agent a on a.Agent_ID = ep.agent_ID inner join passengerexp pe on pe.expensepayout_ID=ep.Expensepayout_ID inner join incident inc on inc.Incident_ID = ep.incident_ID
	where ep.voucheramt>0 and ep.createdate>=:startDate and ep.createdate<=:endDate and find_in_set(a.username, :agentlist) order by concat(a.firstname," ",a.lastname), inc.incident_ID;
#----------------------------------

#Individual Activity Report - Is based on and returns GMT Time
select act.id, concat(a.firstname," ",a.lastname) agentname, ia.createDate, i.Incident_ID, act.code, act.description, (case act.code when "99O" then d.title else "" end) as correspondenceType from incident_activity ia 
inner join incident i on i.Incident_ID=ia.incident 
inner join activity act on act.id = ia.activity
inner join document d ON ia.document = d.id 
inner join agent a on a.agent_id=ia.agent
  where ia.createDate >=:startDate and ia.createDate<=:endDate and find_in_set(a.username, :agentlist) and find_in_set(act.code, :actlist) order by a.username, act.code;