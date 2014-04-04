#Station Payment Report - Is based on and returns GMT Time
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#stationCode - 3 Character Station Code to check against.
select et.description, e.Incident_ID, e.lastname, e.firstname, e.draft, e.createdate, a.username, 
  (case paytype when 'DRAFT' or 'INVOICE' or 'PSO' then checkamt when 'VOUCH' then voucheramt when 'MILE' then mileageamt else 0 end) as amount, e.paytype
    from expensepayout e left outer join agent a on e.agent_ID = a.Agent_ID 
    inner join expensetype et on et.Expensetype_ID = e.expensetype_ID inner join station s on s.Station_ID = e.station_ID
      where e.createdate  >= :startDate and e.createdate  <=:endDate and s.stationcode = :stationCode 
      and (case paytype when 'DRAFT' or 'INVOICE' or 'PSO' then checkamt when 'VOUCH' then voucheramt when 'MILE' then mileageamt else 0 end) > 0
      and e.status_ID=55
        order by e.paytype;
#--------------------------------------------------------------------

#Station Summary Report - Is based on and returns GMT Time - Broken into three queries because it is three separate types of information
#Report should be based on station creating not assigned.
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#stationCode - 3 Character Station Code to check against.
select (case i.itemtype_ID when 1 then "Lost/Delay" when 2 then "Missing Article" when 3 then "Damaged" else "" end) as incType, count(*) from incident i inner join Station s on i.stationcreated_ID=s.Station_ID where i.createdate >= :startDate and i.createdate <=:endDate and s.stationcode = :stationCode  group by i.itemtype_ID order by i.itemtype_ID;

select et.description, sum(e.voucheramt) as luvvoucher,  sum(case paytype when 'DRAFT' then e.checkamt else 0 end ) as draft, sum(case paytype when 'INVOICE' then e.checkamt else 0 end ) as invoice
from expensepayout e inner join incident i on e.incident_ID = i.Incident_ID inner join agent a on e.agent_ID = a.Agent_ID
    inner join station s on s.Station_ID = e.station_ID
    inner join expensetype et on et.Expensetype_ID = e.expensetype_ID
      where e.createdate >= :startDate and e.createdate <=:endDate and s.stationcode = :stationCode 
      and (e.status_id = 53 or e.status_id=55)
        group by e.expensetype_ID;
        
select count(b.BDO_ID) as Deliveries, sum(e.checkamt) as DeliveryCosts from bdo b inner join expensepayout e on e.bdo_id = b.BDO_ID inner join Station s on b.station_ID = s.Station_ID where b.deliverydate >= :startDate and b.deliverydate <=:endDate and s.stationcode = :stationCode ;
#--------------------------------------------------------------------

#Station Chargeback Summary Report - Is based on and returns GMT Time. Detail vs Summary should control which query is ran
# UPDATED TO BE BASED ON ITEM FAULT STATION PER SWA FEEDBACK
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#stationCode - 3 Character Station Code to check against.
#lossCodes - Array of LossCodes to check for. 
select i.lossCode, c.description, c.controllable, count(*) from item i inner join incident inc on i.incident_ID = inc.Incident_ID 
  inner join station s on s.Station_ID = i.faultstation_id inner join Company_irregularity_codes c on i.lossCode= c.loss_code 
  where inc.createdate >= :startDate and inc.createdate <=:endDate and s.stationcode = :stationCode and find_in_set(i.lossCode, :lossCodes)
  and c.companycode_ID='WN' and c.report_type=i.itemtype_ID
  group by i.lossCode;
  
 #MTD Calculation
 #stationCode - 3 Character Station Code to check against.
 #lossCodes - Array of LossCodes to check for. 
 select i.lossCode, c.description, count(i.Item_ID) as mtdCount 
 from item i inner join incident inc on i.incident_ID = inc.Incident_ID 
  inner join station s on s.Station_ID = i.faultstation_id inner join Company_irregularity_codes c on i.lossCode= c.loss_code 
  where (inc.createdate between DATE_FORMAT(now() ,'%Y-%m-01') and now()) and s.stationcode = :stationCode and find_in_set(i.lossCode, :lossCodes)
  and c.companycode_ID='WN' and c.report_type=i.itemtype_ID
  group by i.lossCode; 
  
#Station Chargeback Detail Report - Is based on and returns GMT Time. 
#Combines Itinerary routes as a single column. Combines Itinerary Flightnums as a single column. Combines Itinerary departdates as a single column.
#Detail vs Summary should control which query is ran
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#stationCode - 3 Character Station Code to check against.
#lossCodes - Array of LossCodes to check for. 
select i.lossCode,  cat.id , c.description, c.controllable, itinRoutes.initialDepartDate, 
itinRoutes.departDates, itinRoutes.route, itinRoutes.flightnums,
inc.checkedlocation, (case inc.checkedlocation when not 0 then cat.description else "" end ) as checkLocation, i.incident_ID
  from item i inner join incident inc on i.incident_ID = inc.Incident_ID
    left outer join 
        (SELECT incident_id, departdate as initialDepartDate,
          GROUP_CONCAT(legs ORDER BY incident_id ASC, itinerary_id ASC) route, 
          group_concat(departdate ORDER BY incident_id ASC, itinerary_id ASC) departdates, 
          group_concat(flightnum ORDER BY incident_id ASC, itinerary_id ASC) flightnums 
            FROM (SELECT it.incident_id,it.itinerary_id,it.departdate, it.flightnum, 
            concat(it.legfrom, '-', it.legto) legs 
              FROM itinerary it inner join incident i on i.incident_id=it.incident_ID 
                WHERE it.itinerarytype=0 and i.createDate >= :startDate and i.createDate <= :endDate
                  ORDER BY it.incident_id, it.itinerary_id ASC) itin1 
                  GROUP BY incident_id) as itinRoutes
  on itinRoutes.incident_ID = i.incident_ID
  inner join station s on s.Station_ID = i.faultstation_id inner join Company_irregularity_codes c on i.lossCode= c.loss_code 
  left outer join category cat on cat.categoryVal=inc.checkedlocation and cat.type=4
  where 
  s.stationcode = :stationCode and find_in_set(i.lossCode, :lossCodes) and not isnull(itinRoutes.initialDepartDate)
  and c.companycode_ID='WN' and c.report_type=i.itemtype_ID
  order by  i.lossCode, i.incident_ID;
#--------------------------------------------------------------------

#Station Current Loaner Report - Is based on and returns GMT Time
#stationCode - 3 Character Station Code to check against.
select inv.barcode, inv.description, ii.description as bagItemType, (case inv.inventory_status_id when 701 then "Y" else "N" end) as loanIndicator, 
  (case inv.inventory_status_id when 701 then (case inv.incident_id when "$SNITEM$" then "Special Need" else inv.incident_id end) else "" end) as incNum,
  date((case inv.inventory_status_id when 701 then inv.editDate else "" end)) as loanDate 
  from audit_issuance_item_inventory inv inner join station s on inv.station_id=s.Station_ID 
  inner join Status st on st.Status_ID = inv.inventory_status_id
  inner join issuance_item ii on ii.id=inv.issuance_item_id
    where inv.audit_id in (select max(audit_id) from audit_issuance_item_inventory group by id) and s.stationCode=:stationcode and (inv.trade_type = 0 or inv.trade_type=2)
    group by inv.id;
#--------------------------------------------------------------------
    
#Qualified Mileage Deliveries Detail - Is based on and returns GMT Time. 
#Because distance is not returned from the Home Serv service, will still use the $80 limit, but added a check for if there is a distance greater than 0.
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#stationCode - 3 Character Station Code to check against.
select b.BDO_ID, s.stationcode, (case b.incident_ID when null then b.OHD_ID else b.incident_ID end) as reference, b.deliverydate, a.username, 
p.lastname,p.firstname, 
  d.name as deliveryCompany, e.checkamt as deliveryCost
  from bdo b inner join agent a on b.agent_ID = a.Agent_ID
  inner join bdo_passenger p on p.bdo_ID = b.BDO_ID
  left outer join delivercompany d on d.delivercompany_ID = b.delivercompany_ID
  inner join expensepayout e on e.bdo_id = b.BDO_ID 
  inner join station s on s.Station_ID = b.station_ID
    where b.createdate >=:startdate and b.createdate <=:enddate
    and s.stationCode=:stationCode
    and b.canceled=0 and (case when b.distance>0 then b.distance >60 else e.checkamt >80 end);
#--------------------------------------------------------------------
    
#Qualified Mileage Deliveries Summary - Is based on and returns GMT Time. Includes hard-coded fedex holiday dates to exclude from the query.
#includes logic for if distance is greater than 0
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
select ss.stationCode, 
  (case when not isnull(region.name) then region.name else '' end) as region,
  (case when not isnull(summaryList.fedexdeliveries) then summaryList.fedexdeliveries else 0 end) as fedexdeliveries, 
  (case when not isnull(summaryList.fedexcost) then summaryList.fedexcost else 0 end) as  fedexcost,
  (case when not isnull(summaryList.nonfedexdeliveries) then summaryList.nonfedexdeliveries else 0 end) as nonfedexdeliveries,
  (case when not isnull(summaryList.nonfedexcost) then summaryList.nonfedexcost else 0 end) as nonfedexcost,
  (case when not isnull(summaryList.potentialCost) then summaryList.potentialCost else 0 end) as potentialCost,
  (case when not isnull(summaryList.potentialSavings) then summaryList.potentialSavings else 0 end) as potentialSavings
  from station ss left outer join
  region on ss.region_id = region.id left outer join
  (select s.Station_ID, s.stationCode, sum(case when d.name like '%FedEx%' then 1 else 0 end) as fedexdeliveries, 
  sum(case when d.name like '%FedEx%' then e.checkamt else 0 end) as fedexcost,
  sum(case when d.name not like '%FedEx%' then 1 else 0 end) as nonfedexdeliveries, sum(case when d.name not like '%FedEx%' then e.checkamt else 0 end) as nonfedexcost,
  round(sum(case when d.name like '%FedEx%' then e.checkamt else 0 end)/sum(case when d.name like '%FedEx%' then 1 else 0 end),2)*sum(case when d.name not like '%FedEx%' then 1 else 0 end) as potentialCost,
  sum(case when d.name not like '%FedEx%' then e.checkamt else 0 end)-(round(sum(case when d.name like '%FedEx%' then e.checkamt else 0 end)/sum(case when d.name like '%FedEx%' then 1 else 0 end),2)*sum(case when d.name not like '%FedEx%' then 1 else 0 end)) as potentialSavings
  from station s left outer join  bdo b on s.Station_ID = b.station_ID
  left outer join agent a on b.agent_ID = a.Agent_ID
  left outer join expensepayout e on e.bdo_id = b.BDO_ID 
  inner join deliverCompany d on d.delivercompany_ID = b.delivercompany_ID
    where
     b.createdate >=:startdate and b.createdate <=:enddate and 
     b.canceled=0 and (e.checkamt>80 or (b.distance>60))
     and d.name not like '%UPS%' 
     AND DAYOFWEEK(b.createdate)!=7
     AND DAYOFWEEK(b.createdate)!=0
     and b.createdate!=date('2014-01-01') and b.createdate!=date('2015-01-01') and b.createdate!=date('2016-01-01') and b.createdate!=date('2017-01-02')
     and b.createdate!=date('2014-05-26') and b.createdate!=date('2015-05-25') and b.createdate!=date('2016-05-30') and b.createdate!=date('2017-05-29')
     and b.createdate!=date('2014-07-04') and b.createdate!=date('2015-07-04') and b.createdate!=date('2016-07-04') and b.createdate!=date('2017-07-04')
     and b.createdate!=date('2014-09-01') and b.createdate!=date('2015-09-07') and b.createdate!=date('2016-09-05') and b.createdate!=date('2017-09-04')
     and b.createdate!=date('2014-11-27') and b.createdate!=date('2015-11-26') and b.createdate!=date('2016-11-24') and b.createdate!=date('2017-11-23')
     and b.createdate!=date('2014-12-25') and b.createdate!=date('2015-12-25') and b.createdate!=date('2016-12-26') and b.createdate!=date('2017-12-25')
    group by s.stationCode) summaryList on summaryList.station_id=ss.Station_Id where ss.companycode_ID='WN';
#--------------------------------------------------------------------

#BSO Agent Audit Report - Is based on and returns GMT Time. Is a Procedure based on percentage requirements
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#stationCode - 3 Character Station Code to check against.
#agentUsername - Username of Agent to get audit information
#itemType - Report Type to reference (1 for Lost, 2 for Missing, 3 for Damage)
#percent - Percentage of Results to get back
call getBSOAgentAuditReport (:startDate, :endDate, :stationcode,:agentUsername, :itemtype, :percent);
#--------------------------------------------------------------------
  
#Chargeback Changes Report - Is based on and returns GMT Time
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#chargedStation - 3 Character Station Code that is charged with the fault.
#changingStation - 3 Character Station Code that made the change
select i.incident_ID, (case b.deliverydate when null then i.close_date else b.deliverydate end) as returnDate, i.modify_time, 
datediff(i.modify_time,(case b.deliverydate when null then i.close_date else b.deliverydate end) ) as daysToChange,
  a.firstname,a.lastname, cs.stationcode as changingStation, ai.lossCode, fs.stationcode as chargedStation,
  itemIncident.bagCount
  from audit_item ai 
    inner join station fs on fs.Station_ID = ai.faultStation_id 
    inner join audit_incident i on i.audit_incident_id = ai.audit_incident_id
    inner join (select min(aii.audit_incident_id) as minIncNum from audit_item aii 
            inner join audit_incident ii on ii.audit_incident_id = aii.audit_incident_id
              where aii.lossCode>0 and aii.Item_ID>0 
              group by ii.incident_id, concat(aii.lossCode,"-",aii.faultStation_id) ) as minInc on minInc.minIncNum=i.audit_incident_id
    inner join (select count(it.Item_ID) as bagCount, it.incident_ID as itemInc from Item it group by it.incident_id) as itemIncident on itemIncident.itemInc = i.Incident_ID
    inner join agent a on a.Agent_ID = i.modify_agent_id
    inner join station cs on cs.Station_ID = a.station_ID
    left outer join bdo b on b.incident_ID = i.Incident_ID
      where ai.lossCode>0 and ai.item_id>0 and
      i.modify_time >= :startDate and i.modify_time <=:endDate and fs.stationcode=:chargedStation and cs.stationcode =:changingStation 
      and not isnull((case b.deliverydate when null then i.close_date else b.deliverydate end)) 
      group by i.Incident_ID, ai.lossCode, fs.stationcode
      order by i.incident_id,  i.modify_time;
#--------------------------------------------------------------------

#On Hand Scanner Usage Summary - Is based on and returns GMT Time. Proceeding as normal until Auto vs Manual question is resolved.
#Detail vs Summary selection should determine which query is run

#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
select s.stationCode, sum(case o.creationMethod when 1 then 1 else 0 end) as scanned, sum(case o.creationMethod when 0 then 1 else 0 end) as manual, 
	(sum(case o.creationMethod when 1 then 1 else 0 end)/count(o.OHD_ID)*100) as scanPercent
		from ohd o inner join Station s on s.Station_ID=o.found_station_ID where o.founddate >=:startDate and o.founddate <=:endDate group by s.stationCode;

#On Hand Scanner Usage Detail - Is based on and returns GMT Time. Proceeding as normal until Auto vs Manual question is resolved
#Detail vs Summary selection should determine which query is run
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#stationCode - 3 Character Station Code to check against.
select date(o.founddate) as dateEntered, sum(case o.creationMethod when 1 then 1 else 0 end) as scanned, 
	sum(case o.creationMethod when 0 then 1 else 0 end) as manual, 
  (sum(case o.creationMethod when 1 then 1 else 0 end)/count(o.OHD_ID)*100) as scanPercent
    from ohd o inner join Station s on s.Station_ID=o.found_station_ID where o.founddate >=:startDate 
    and o.founddate <=:endDate and s.stationcode=:stationcode group by date(o.founddate);
#--------------------------------------------------------------------

#Trade Out, Loaner Report - Is based on and returns GMT Time.
#Made a combined Loaner, Emergency Bag, and Trade Out report.
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#stationCode - 3 Character Station Code to check against.
select inv.id, inv.editdate, (case inv.trade_type when 0 then "Both" when 1 then "Tradeout Only" when 2 then "Loan Only" else "" end) as tradeType, inv.barcode, inv.description, 
  (case inv.inventory_status_id when 701 then "Y" else "N" end) as loanIndicator,
  (case inv.inventory_status_id when 702 then "Y" else "N" end) as issuedIndicator,
  ifnull((case inv.incident_id when "$SNITEM$" then "Special Need" else inv.incident_id end), '') as incNum,
  timestamp((case inv.inventory_status_id when 701 then inv.editDate else "" end)) as loanDate,
  p.firstname as passFirstName,p.lastname as passLastName, s.stationcode, a.username, a.firstname as agentFirstName,a.lastname as agentLastName,
  timestamp((case inv.inventory_status_id when 702 then inv.issueDate else "" end)) as issueDate, inv.cost,
  ii.description as bagItemType
  from audit_issuance_item_inventory inv 
  inner join station s on inv.station_id=s.Station_ID 
  inner join Status st on st.Status_ID = inv.inventory_status_id
  inner join Agent a on inv.editagent_id = a.Agent_ID
  inner join issuance_item ii on ii.id = inv.issuance_item_id
  left outer join passenger p on p.Passenger_ID = (select pp.Passenger_ID from passenger pp where inv.Incident_ID = pp.incident_ID limit 1) 
    where inv.audit_id in (select max(audit_id) from audit_issuance_item_inventory where audit_issuance_item_inventory.id=inv.id group by id) 
    and s.stationCode=:stationcode 
    and (case inv.inventory_status_id when 702 then (inv.issueDate>=:startdate and inv.issuedate<=:enddate) when 701 then (inv.editdate>=:startdate and inv.editdate<=:enddate) end)
    group by inv.id;
#--------------------------------------------------------------------

#Cancelled Southwest LUV Voucher Report - Is based on and returns GMT Time
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#stationCodes - Array of 3 Character Station Codes to check against.
select i.Incident_ID, i.recordlocator, aep.modify_time as canceldate, 
(case e.cancelreason when "INCQNT" then "INCORRECT QUANTITY" when "INCAMT" then "INCORRECT AMOUNT" when "INCNAME" then "INCORRECT NAME" else "" end) as cancelReason, 
(case e.cancelreason when "INCQNT" then "QT" when "INCAMT" then "AM" when "INCNAME" then "NM" else "" end) as code,
a.firstname,a.lastname
  from expensepayout e 
  inner join audit_expensepayout aep on aep.audit_expensepayout_id = (select max(aaep.audit_expensepayout_id) from audit_expensepayout aaep where e.Expensepayout_ID = aaep.Expensepayout_ID group by aaep.Expensepayout_ID)
  inner join incident i on i.Incident_ID = e.incident_ID 
  inner join Station s on s.Station_ID = e.station_ID
  inner join Agent a on a.Agent_ID = aep.agent_ID
    where e.paytype='VOUCH' and e.voucheramt>0 and e.status_ID=94 and aep.modify_time >=:startDate and aep.modify_time <=:endDate and find_in_set(s.stationcode, :stationCodes);
#--------------------------------------------------------------------

       
#Southwest LUV Voucher Detail Report - Is based on and returns GMT Time
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#stationCodes - Array of 3 Character Station Codes to check against.
select i.Incident_ID, e.createdate as issuedate, e.ordernum, i.recordlocator, 
a.firstname as agentFirstName,a.lastname as agentFirstName, s.stationcode, e.firstname as custFirstName,e.lastname as custFirstName, e.paytype, e.voucheramt
  from expensepayout e 
  inner join incident i on i.Incident_ID = e.incident_ID 
  inner join Station s on s.Station_ID = e.station_ID
  inner join Agent a on a.Agent_ID = e.agent_ID
    where e.paytype='VOUCH' and e.voucheramt>0 and e.status_ID=55 and e.createdate >=:startDate and e.createdate <=:endDate and find_in_set(s.stationcode, :stationCodes) order by concat(a.firstname," ",a.lastname);
#--------------------------------------------------------------------

#Southwest LUV Voucher Summary Report - Is based on and returns GMT Time. Baggage Compensation is all SLVs issued from incidents, which is all expenses with Voucher Amounts.
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#stationCodes - Array of 3 Character Station Codes to check against.
select s.stationcode AS loc, s.stationdesc, sum(e.voucheramt) as bagCompensation, count(distinct e.Expensepayout_ID) as cnt
  from expensepayout e 
  inner join Station s on s.Station_ID = e.station_ID 
  where e.createdate>=:startDate and e.createdate<=:endDate and find_in_set(s.stationcode, :stationCodes)
    and e.paytype='VOUCH' and e.voucheramt>0 and e.status_ID=55 group by s.stationcode;
#--------------------------------------------------------------------
    
#Station Removed Trade Out Bags - Is based on and returns GMT Time. Gets all Discarded (Removed) Trade Out Bags
#Made one large Loaner, Trade Out, Emergency Bag, and Toiletry Kit Report. Please Inform if there is any information missing
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#stationCode - 3 Character Station Code to check against.
select inv.barcode, inv.description, inv.issuedate, s.stationcode, 
  inv.editDate, inv.reason, a.firstname,a.lastname, inv.cost
  from audit_issuance_item_inventory inv inner join station s on inv.station_id=s.Station_ID 
  inner join Status st on st.Status_ID = inv.inventory_status_id
  inner join Agent a on a.agent_id=inv.editagent_id
    where inv.audit_id in (select max(audit_id) from audit_issuance_item_inventory group by id) 
      and s.stationCode=:stationcode 
      and (inv.trade_type = 0 or inv.trade_type=1)
      and (inv.reason like '%Discarded%')
      and inv.editDate >= :startDate and inv.editDate <=:endDate;
#--------------------------------------------------------------------

#Station Loaner Write Off Report - Is based on and returns GMT Time. Gets all Converted (Written Off) Loaner Items
#Made one large Loaner, Trade Out, Emergency Bag, and Toiletry Kit Report. Please Inform if there is any information missing
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#stationCode - 3 Character Station Code to check against.
select inv.barcode, inv.description, 
  inv.editDate, inv.reason,a.firstname,a.lastname, s.stationcode
  from audit_issuance_item_inventory inv inner join station s on inv.station_id=s.Station_ID 
  inner join Status st on st.Status_ID = inv.inventory_status_id
  inner join Agent a on a.agent_id=inv.editagent_id
    where inv.audit_id in (select max(audit_id) from audit_issuance_item_inventory group by id) 
      and s.stationCode=:stationcode 
      and (inv.trade_type = 0 or inv.trade_type=2)
      and (inv.reason like '%Converted Item%')
      and inv.editDate >= :startDate and inv.editDate <=:endDate
    group by inv.id;
    
#--------------------------------------------------------------------

#Toiletry Kit Report Issued
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#stationCode - 3 Character Station Code to check against.
select quan.incident_id, a.lastname as agentlastname, a.firstname as agentfirstname, quan.editdate as issueDate, - quan.quantity_change as quantityIssued, 
  cat.description, iss.description
    from audit_issuance_item_quantity quan
    inner join agent a on a.Agent_ID=quan.editagent_id 
    inner join station s on s.Station_ID = quan.station_id
    inner join issuance_item iss on iss.id = quan.issuance_item_id
    inner join issuance_category cat on cat.id = iss.issuance_category_id
      where quan.editdate>=:startDate and quan.editdate<=:endDate and s.stationcode=:stationCode 
      and quan.quantity_change<0 and cat.description like '%Toiletry%'
      group by quan.incident_id, quan.id order by quan.incident_id, quan.editdate;

#Total Toiletry Kit Items available
#stationCode - 3 Character Station Code to check against.
select sum(quan.quantity) as totalAvailable, cat.description, iss.description  from issuance_item_quantity quan
  inner join station s on s.Station_ID = quan.station_id
  inner join issuance_item iss on iss.id=quan.issuance_item_id
  inner join issuance_category cat on cat.id = iss.issuance_category_id
    where s.stationcode=:stationCode 
    and cat.description like '%Toiletry%'
    group by quan.id;  
    
#Emergency Bags Report Issued
#QuantityIssued column reference to how many of the item was issued out or return.
#	If issued, quantityIssued is positive. If returned, quantityIssued is negative
#startDate - The beginning of the date range. DateTime variable
#endDate - the end of the date range. DateTime variable
#stationCode - 3 Character Station Code to check against.
select quan.incident_id, a.lastname as agentlastname, a.firstname as agentfirstname, quan.editdate as issueDate, - quan.quantity_change as quantityIssued, 
  cat.description, iss.description
    from audit_issuance_item_quantity quan
    inner join agent a on a.Agent_ID=quan.editagent_id 
    inner join station s on s.Station_ID = quan.station_id
    inner join issuance_item iss on iss.id = quan.issuance_item_id
    inner join issuance_category cat on cat.id = iss.issuance_category_id
      where quan.editdate>=:startDate and quan.editdate<=:endDate and s.stationcode=:stationCode 
      and quan.quantity_change<0 and cat.description like '%Emergency%'
      group by quan.incident_id, quan.id order by quan.incident_id, quan.editdate;

#Emergency Bags Items available
#stationCode - 3 Character Station Code to check against.
select sum(quan.quantity) as totalAvailable, cat.description, iss.description  from issuance_item_quantity quan
  inner join station s on s.Station_ID = quan.station_id
  inner join issuance_item iss on iss.id=quan.issuance_item_id
  inner join issuance_category cat on cat.id = iss.issuance_category_id
    where s.stationcode=:stationCode 
    and cat.description like '%Emergency%'
    group by quan.id;  