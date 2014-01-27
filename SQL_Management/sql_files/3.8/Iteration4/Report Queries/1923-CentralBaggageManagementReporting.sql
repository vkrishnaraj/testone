# Admin Activity Summary Report - Detail and Summary - Is based on and returns GMT Time. 
#The query should return all the information needed for both Detail and Summary Reports and can be summed up based on data returned. 
#The detail vs summary selection should mainly control how the values are summed up and displayed
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
select count(ia.id) as activityCount, act.code as activityCode, act.description as activityDescription, a.firstname,a.lastname, a.username
  from incident_activity ia 
    inner join activity act on ia.activity = act.id 
    inner join agent a on ia.agent = a.Agent_ID 
      where ia.createDate>=:startDate and ia.createDate <=:endDate and (act.code like '%99%' or act.code like '%256%' or act.code like '%55%')
      group by a.Agent_ID, act.code
      order by a.firstname,a.lastname, act.code;
#----------------------------------

#Assigned Claims by Representative - Is based on and returns GMT Time
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
select a.firstname,a.lastname, a.username, 
  inc.Incident_ID, timestamp(inc.createdate,inc.createtime) as date, 
  (case inc.itemtype_ID when 1 then "Lost" when 2 then "Missing" when 3 then "Damage" else "" end) as type 
    from incident inc 
    inner join agent a on inc.agentassigned_ID=a.Agent_ID 
      where inc.createdate>=:startdate and inc.createdate<=:enddate and inc.status_ID=12
      order by a.firstname, (case inc.itemtype_ID when 1 then "Lost" when 2 then "Missing" when 3 then "Damage" else "" end);
      
#----------------------------------

#Agent Audit Report -Is based on and returns GMT Time. Is a Procedure based on percentage requirements
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#agentlist - List of Agent Usernames to check against
#percent - Percentage of results returned back
call getAgentAuditReport (:startDate, :endDate, :agentlist,:percent);
#----------------------------------

#Inventory Productivity by Representative - Is based on and returns GMT Time
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
select a.firstname,a.lastname,a.username, 
  o.OHD_ID, o.founddate as date, o.color,o.type, 
  (case when isNull(it.status_ID) or it.status_id='' or it.status_ID in (47) then "Not Found" when it.status_id in (48,49,56) 
    then "Found" when it.status_id in (50,59) then "Returned" end) as itemState,
  s.description as bagState
  from ohd o 
    inner join status s on s.Status_ID = o.status_ID
    inner join status ds on ds.Status_ID = o.disposal_status_ID
    inner join agent a on o.agent_ID = a.Agent_ID
    left outer join item it on it.ohd_id= o.OHD_ID
    where founddate >=:startDate and founddate <=:endDate order by a.firstname;
#----------------------------------

#Report Claims by Agent (Summary) - Is based on and returns GMT Time 
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
select a.username,a.firstname,a.lastname, count(f.id) as claimCount, sum(f.amountPaid) as total, 
round(sum(f.amountPaid)/count(f.ID),2) as average 
	from  fsclaim f inner join incident i on i.Incident_ID=f.ntIncidentId inner join agent a on i.agentassigned_id = a.Agent_ID
		where f.claimDate>=:startDate and f.claimDate <=:endDate and f.status_id!=39 group by a.username;

#Report Claims by Agent (Detail) - Is based on and returns GMT Time 
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
select a.username,a.firstname,a.lastname, f.ntIncidentId, p.firstname, p.lastName,
  (f.amountPaid) as total
  from fsclaim f inner join incident i on i.Incident_ID=f.ntIncidentId 
  inner join agent a on i.agentassigned_id = a.Agent_ID
  inner join person p on p.claim_id=f.id
      where f.claimDate>=:startDate and f.claimDate <=:endDate;
#----------------------------------

#CBS SLV Issuance Report - Is based on and returns GMT Time
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#agentlist - List of Agent Usernames to check against
select a.firstname,a.lastname, ep.createdate, ep.firstname,ep.lastname, ep.incident_id, inc.recordlocator, ep.paycode, 
(case inc.itemtype_ID when 1 then "LOST" when 2 then "MISSING" when 3 then "DAMAGE" else "" end) as reportType,
(case ep.status_id when 54 then "DENY" when 55 then "SETTLE" else "PENDING" end) as resolveType, ep.voucheramt
  from expensepayout ep inner join agent a on a.Agent_ID = ep.agent_ID 
  inner join incident inc on inc.Incident_ID = ep.incident_ID
	where ep.voucheramt>0 and ep.createdate>=:startDate and ep.createdate<=:endDate and (ep.status_id = 54 or ep.status_id=55)
  	and find_in_set(a.username, :agentlist)
  		order by a.firstname,a.lastname, inc.incident_ID;
#----------------------------------

#Individual Activity Report - Is based on and returns GMT Time
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#agentlist - List of Agent Usernames to check against
select a.firstname,a.lastname, ia.createDate, i.Incident_ID, act.code, act.description, (case act.code when "55" then d.title when "55C" then d.title else "" end) as correspondenceType from incident_activity ia 
inner join incident i on i.Incident_ID=ia.incident 
inner join activity act on act.id = ia.activity
inner join document d ON ia.document = d.id 
inner join agent a on a.agent_id=ia.agent
  where ia.createDate >=:startDate and ia.createDate<=:endDate and find_in_set(a.username, :agentlist) and find_in_set(act.code, :actlist) order by a.username, act.code;