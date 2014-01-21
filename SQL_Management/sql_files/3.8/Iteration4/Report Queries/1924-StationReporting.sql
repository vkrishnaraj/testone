#Station Payment Report first draft - Is based on and returns GMT Time
select et.description, e.Incident_ID, p.lastname, p.firstname, e.draft, e.createdate, a.username, 
  (case paytype when 'DRAFT' or 'INVOICE' or 'PSO' then checkamt when 'VOUCH' then voucheramt when 'MILE' then mileageamt else 0 end) as amount, e.paytype
    from expensepayout e left outer join agent a on e.agent_ID = a.Agent_ID 
    inner join passengerexp p on p.PassengerExp_ID = (select pp.PassengerExp_ID from passengerexp pp where e.Expensepayout_ID = pp.expensepayout_ID) #Pulls from the Passenger EXP table that should be tied to expenses mainly
    inner join expensetype et on et.Expensetype_ID = e.expensetype_ID inner join station s on s.Station_ID = e.station_ID
      where e.createdate  >= :startDate and e.createdate  <=:endDate and s.stationcode = :stationCode 
      and (case paytype when 'DRAFT' or 'INVOICE' or 'PSO' then checkamt when 'VOUCH' then voucheramt when 'MILE' then mileageamt else 0 end) > 0
      and e.status_ID=55
        order by e.paytype;
#--------------------------------------------------------------------

#Station Summary Report - Is based on and returns GMT Time
select (case i.itemtype_ID when 1 then "Lost/Delay" when 2 then "Missing Article" when 3 then "Damaged" else "" end) as incType, count(*) from incident i inner join Station s on i.stationassigned_ID=s.Station_ID where i.createdate >= :startDate and i.createdate <=:endDate and s.stationcode = :stationCode  group by i.itemtype_ID order by i.itemtype_ID;

select et.description, sum(e.voucheramt) as luvvoucher,  sum(case paytype when 'DRAFT' then e.checkamt else 0 end ) as draft, sum(case paytype when 'INVOICE' then e.checkamt else 0 end ) as invoice
from expensepayout e inner join incident i on e.incident_ID = i.Incident_ID inner join agent a on e.agent_ID = a.Agent_ID
    inner join station s on s.Station_ID = e.station_ID
    inner join expensetype et on et.Expensetype_ID = e.expensetype_ID
      where e.createdate >= :startDate and e.createdate <=:endDate and s.stationcode = :stationCode 
        group by e.expensetype_ID;
        
select count(b.BDO_ID) as Deliveries, sum(e.checkamt) as DeliveryCosts from bdo b inner join expensepayout e on e.bdo_id = b.BDO_ID inner join Station s on b.station_ID = s.Station_ID where b.deliverydate >= :startDate and b.deliverydate <=:endDate and s.stationcode = :stationCode ;
#--------------------------------------------------------------------

#Station Chargeback Summary Report - Is based on and returns GMT Time
select i.lossCode, c.description, c.controllable, count(*) from item i inner join incident inc on i.incident_ID = inc.Incident_ID 
  inner join station s on s.Station_ID = inc.stationassigned_ID inner join Company_irregularity_codes c on i.lossCode= c.loss_code 
  where inc.createdate >= :startDate and inc.createdate <=:endDate and s.stationcode = :stationCode and find_in_set(i.lossCode, :lossCodes)
  and c.companycode_ID='WN' and c.report_type=i.itemtype_ID
  group by i.lossCode;
#--------------------------------------------------------------------
  
#Station Chargeback Detail Report - Is based on and returns GMT Time. Combines Itinerary routes as a single column. Combines Itinerary Flightnums as a single column. Combines Itinerary departdates as a single column. 
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
                WHERE it.itinerarytype=0 and it.departdate >= :startDate and it.departdate <= :endDate
                  ORDER BY it.incident_id, it.itinerary_id ASC) itin1 
                  GROUP BY incident_id) as itinRoutes
  on itinRoutes.incident_ID = i.incident_ID
  inner join station s on s.Station_ID = inc.stationassigned_ID inner join Company_irregularity_codes c on i.lossCode= c.loss_code 
  left outer join category cat on cat.categoryVal=inc.checkedlocation and cat.type=4
  where 
  s.stationcode = :stationCode and find_in_set(i.lossCode, :lossCodes) and not isnull(itinRoutes.initialDepartDate)
  and c.companycode_ID='WN' and c.report_type=i.itemtype_ID
  order by  i.lossCode, i.incident_ID;
#--------------------------------------------------------------------

#Station Current Loaner Report - Is based on and returns GMT Time
select inv.barcode, inv.description, (case inv.inventory_status_id when 701 then "Y" else "N" end) as loanIndicator, 
  (case inv.inventory_status_id when 701 then (case inv.incident_id when "$SNITEM$" then "Special Need" else inv.incident_id end) else "" end) as incNum,
  date((case inv.inventory_status_id when 701 then inv.editDate else "" end)) as loanDate 
  from audit_issuance_item_inventory inv inner join station s on inv.station_id=s.Station_ID 
  inner join Status st on st.Status_ID = inv.inventory_status_id
    where inv.audit_id in (select max(audit_id) from audit_issuance_item_inventory group by id) and s.stationCode=:stationcode and (inv.trade_type = 0 or inv.trade_type=2)
    group by inv.id;
#--------------------------------------------------------------------
    
#Qualified Mileage Deliveries Detail - Is based on and returns GMT Time
select (case b.incident_ID when null then b.OHD_ID else b.incident_ID end) as reference, b.deliverydate, a.username, concat(p.lastname,", ",p.firstname) as passenger, 
  d.integration_key , e.checkamt
  from bdo b inner join agent a on b.agent_ID = a.Agent_ID
  inner join bdo_passenger p on p.bdo_ID = b.BDO_ID
  inner join delivercompany d on d.delivercompany_ID = b.delivercompany_ID
  inner join expensepayout e on e.bdo_id = b.BDO_ID 
  inner join station s on s.Station_ID = b.station_ID
    where b.createdate >=:startdate and b.createdate <=:enddate and s.stationCode=:stationCode and not isnull(d.integration_key) and d.integration_key!=''
    and b.canceled=0;
#--------------------------------------------------------------------
    
#Qualified Mileage Deliveries Summary - Is based on and returns GMT Time. Includes hard-coded fedex holiday dates to exclude from the query.
select ss.stationCode, 
  (case when not isnull(summaryList.fedexdeliveries) then summaryList.fedexdeliveries else 0 end) as fedexdeliveries, 
  (case when not isnull(summaryList.fedexcost) then summaryList.fedexcost else 0 end) as  fedexcost,
  (case when not isnull(summaryList.nonfedexdeliveries) then summaryList.nonfedexdeliveries else 0 end) as nonfedexdeliveries,
  (case when not isnull(summaryList.nonfedexcost) then summaryList.nonfedexcost else 0 end) as nonfedexcost,
  (case when not isnull(summaryList.potentialCost) then summaryList.potentialCost else 0 end) as potentialCost,
  (case when not isnull(summaryList.potentialSavings) then summaryList.potentialSavings else 0 end) as potentialSavings
  from station ss left outer join
  (select s.Station_ID, s.stationCode, sum(case when e.checkamt<=80 then 1 else 0 end) as fedexdeliveries, 
  sum(case when e.checkamt<=80 then e.checkamt else 0 end) as fedexcost,
  sum(case when e.checkamt>80 then 1 else 0 end) as nonfedexdeliveries, sum(case when e.checkamt>80 then e.checkamt else 0 end) as nonfedexcost,
  round(sum(case when e.checkamt<=80 then e.checkamt else 0 end)/sum(case when e.checkamt<=80 then 1 else 0 end),2)*sum(case when e.checkamt>80 then 1 else 0 end) as potentialCost,
  sum(case when e.checkamt>80 then e.checkamt else 0 end)-(round(sum(case when e.checkamt<=80 then e.checkamt else 0 end)/sum(case when e.checkamt<=80 then 1 else 0 end),2)*sum(case when e.checkamt>80 then 1 else 0 end)) as potentialSavings
  from station s left outer join  bdo b on s.Station_ID = b.station_ID
  left outer join agent a on b.agent_ID = a.Agent_ID
  left outer join bdo_passenger p on p.bdo_ID = b.BDO_ID
  left outer join delivercompany d on d.delivercompany_ID = b.delivercompany_ID
  left outer join expensepayout e on e.bdo_id = b.BDO_ID 
    where
     b.createdate >=:startdate and b.createdate <=:enddate and 
     not isnull(d.integration_key) and d.integration_key!='' and
     b.canceled=0 
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
call getBSOAgentAuditReport (:startDate, :endDate, :stationcode,:agentUsername, :itemtype, :percent);
#--------------------------------------------------------------------
  
#Chargeback Changes Report - Is based on and returns GMT Time
select i.incident_ID, (case b.deliverydate when null then i.close_date else b.deliverydate end) as returnDate, i.modify_time, datediff(i.modify_time,(case b.deliverydate when null then i.close_date else b.deliverydate end) ) as daysToChange,
  concat(a.firstname," ",a.lastname) as agentName, cs.stationcode, concat(ai.lossCode, "/",fs.stationcode) as chargedStation,
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
      i.modify_time >= :startDate and i.modify_time <=:endDate and fs.stationcode=:chargedStation and cs.stationcode =:changingStation and not isnull((case b.deliverydate when null then i.close_date else b.deliverydate end)) 
      group by i.Incident_ID, concat(ai.lossCode, "/",fs.stationcode)
      order by i.incident_id,  i.modify_time;
#--------------------------------------------------------------------

#On Hand Scanner Usage Summary - Is based on and returns GMT Time. Proceeding as normal until Auto vs Manual question is resolved
select s.stationCode, sum(case o.creationMethod when 1 then 1 else 0 end) as scanned, sum(case o.creationMethod when 0 then 1 else 0 end) as manual, 
	(sum(case o.creationMethod when 1 then 1 else 0 end)/count(o.OHD_ID)*100) as scanPercent
		from ohd o inner join Station s on s.Station_ID=o.found_station_ID where o.founddate >=:startDate and o.founddate <=:endDate group by s.stationCode;

#On Hand Scanner Usage Detail - Is based on and returns GMT Time. Proceeding as normal until Auto vs Manual question is resolved
select date(o.founddate) as dateEntered, sum(case o.creationMethod when 1 then 1 else 0 end) as scanned, sum(case o.creationMethod when 0 then 1 else 0 end) as manual, 
  (sum(case o.creationMethod when 1 then 1 else 0 end)/count(o.OHD_ID)*100) as scanPercent
    from ohd o inner join Station s on s.Station_ID=o.found_station_ID where o.founddate >=:startDate 
    and o.founddate <=:endDate and s.stationcode=:stationcode group by date(o.founddate);
#--------------------------------------------------------------------

#Trade Out, Emergency Bag, Toiletry, Loaner Report - Is based on and returns GMT Time. Made one large Loaner, Trade Out, Emergency Bag, and Toiletry Kit Report. Please Inform if there is any information missing
select inv.id, inv.editdate, inv.inventory_status_id, (case inv.trade_type when 0 then "Both" when 1 then "Tradeout Only" when 2 then "Loan Only" else "" end) as tradeType, inv.barcode, inv.description, 
  (case inv.inventory_status_id when 701 then "Y" else "N" end) as loanIndicator,
  (case inv.inventory_status_id when 702 then "Y" else "N" end) as issuedIndicator,
  (case inv.inventory_status_id when 701 then (case inv.incident_id when "$SNITEM$" then "Special Need" else inv.incident_id end) else "" end) as incNum,
  timestamp((case inv.inventory_status_id when 701 then inv.editDate else "" end)) as loanDate,
  concat(p.firstname," ",p.lastname) as custName, s.stationcode, a.username, concat(a.firstname," ",a.lastname) as agentName,
  timestamp((case inv.inventory_status_id when 702 then inv.issueDate else "" end)) as issueDate, inv.cost
  from audit_issuance_item_inventory inv 
  inner join station s on inv.station_id=s.Station_ID 
  inner join Status st on st.Status_ID = inv.inventory_status_id
  inner join Agent a on inv.editagent_id = a.Agent_ID
  left outer join passenger p on p.Passenger_ID = (select pp.Passenger_ID from passenger pp where inv.Incident_ID = pp.incident_ID limit 1) 
    where inv.audit_id in (select max(audit_id) from audit_issuance_item_inventory where audit_issuance_item_inventory.id=inv.id group by id) 
    and s.stationCode=:stationcode 
    and (case inv.inventory_status_id when 702 then (inv.issueDate>=:startdate and inv.issuedate<=:enddate) when 701 then (inv.editdate>=:startdate and inv.editdate<=:enddate) end)
    group by inv.id;
#--------------------------------------------------------------------

#Cancelled Southwest LUV Voucher Report - Is based on and returns GMT Time
select i.Incident_ID, i.recordlocator, aep.modify_time as canceldate, 
(case e.cancelreason when "INCQNT" then "INCORRECT QUANTITY" when "INCAMT" then "INCORRECT AMOUNT" when "INCNAME" then "INCORRECT NAME" else "" end) as cancelReason, 
(case e.cancelreason when "INCQNT" then "QT" when "INCAMT" then "AM" when "INCNAME" then "NM" else "" end) as code,
concat(a.firstname," ",a.lastname) as cancelAgent
  from expensepayout e 
  inner join audit_expensepayout aep on aep.audit_expensepayout_id = (select max(aaep.audit_expensepayout_id) from audit_expensepayout aaep where e.Expensepayout_ID = aaep.Expensepayout_ID group by aaep.Expensepayout_ID)
  inner join incident i on i.Incident_ID = e.incident_ID 
  inner join Station s on s.Station_ID = e.station_ID
  inner join Agent a on a.Agent_ID = aep.agent_ID
    where e.paytype='VOUCH' and e.voucheramt>0 and e.status_ID=94 and aep.modify_time >=:startDate and aep.modify_time <=:endDate and find_in_set(s.stationcode, :stationCodes);
#--------------------------------------------------------------------

#Southwest LUV Voucher Detail Report - Is based on and returns GMT Time
select i.Incident_ID, e.createdate as issuedate, e.ordernum, i.recordlocator, concat(a.firstname," ",a.lastname) as issueAgent, s.stationcode, concat(pe.firstname," ",pe.lastname) as custName, "Type" , 
	e.voucheramt #This is the total dollar amount of all SLV’s issued from incidents, and the total count of issuances from incidents
  from expensepayout e 
  inner join incident i on i.Incident_ID = e.incident_ID 
  inner join Station s on s.Station_ID = e.station_ID
  inner join Agent a on a.Agent_ID = e.agent_ID
  inner join passengerexp pe on pe.expensepayout_id=e.Expensepayout_ID
    where e.paytype='VOUCH' and e.voucheramt>0 and e.status_ID=55 and e.createdate >=:startDate and e.createdate <=:endDate and find_in_set(s.stationcode, :stationCodes) order by concat(a.firstname," ",a.lastname);
#--------------------------------------------------------------------

#Southwest LUV Voucher Summary Report - Is based on and returns GMT Time. Baggage Compensation is all SLVs issued from incidents, which is all expenses with Voucher Amounts.
select s.stationcode, s.stationdesc, sum(e.voucheramt), count(distinct e.Expensepayout_ID)
  from expensepayout e 
  inner join Station s on s.Station_ID = e.station_ID 
  where e.createdate>=:startDate and e.createdate<=:endDate and find_in_set(s.stationcode, :stationCodes)
    and e.paytype='VOUCH' and e.voucheramt>0 and e.status_ID=55 group by s.stationcode;
#--------------------------------------------------------------------
    
#Station Removed Trade Out Bags - Is based on and returns GMT Time. Gets all Discarded (Removed) Trade Out Bags
select inv.barcode, inv.description, inv.issuedate, s.stationcode, 
  inv.editDate, inv.reason, concat(a.firstname," ",a.lastname) as agent, inv.cost
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
select inv.barcode, inv.description, 
  inv.editDate, inv.reason, concat(a.firstname," ",a.lastname) as agent, s.stationcode
  from audit_issuance_item_inventory inv inner join station s on inv.station_id=s.Station_ID 
  inner join Status st on st.Status_ID = inv.inventory_status_id
  inner join Agent a on a.agent_id=inv.editagent_id
    where inv.audit_id in (select max(audit_id) from audit_issuance_item_inventory group by id) 
      and s.stationCode=:stationcode 
      and (inv.trade_type = 0 or inv.trade_type=2)
      and (inv.reason like '%Converted Item%')
      and inv.editDate >= :startDate and inv.editDate <=:endDate
    group by inv.id;