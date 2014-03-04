insert into properties (keyStr, valueStr) values ('bso.expense.process',1);
insert into properties (keystr, valuestr) values ('sso.auto.provision', 1);
insert into properties (keyStr, valueStr) values ('populate.itinerary.dateonly',1);
update properties set valueStr = '36' where keyStr = 'reservation.hours.backward';
update properties set valueStr = '12' where keyStr = 'reservation.hours.forward';

#ALREADY RAN
#NEW TESTING SERVICE FOR SOUTHWEST
#update properties set valueStr = 'http://drapp2.nettracer.aero:8680/NTServices_1_0/services/ReservationService_1_0' where keyStr = 'booking.nt.endpoint';
#NEW TRAINING SERVICE FOR SOUTHWEST
#update properties set valueStr = 'http://drapp2.nettracer.aero:8780/NTServices_1_0/services/ReservationService_1_0' where keyStr = 'booking.nt.endpoint';

insert into properties (keyStr, valueStr) values ('bagdrop.offset.start',8);
insert into properties (keyStr, valueStr) values ('bagdrop.offset.end',6);

#TESTING
#insert into properties (keystr, valuestr) values ('nt.central.endpoint','http://drapp2.nettracer.aero:8680/NTServices_1_0/services/WorldTracerService');
#insert into properties (keystr, valuestr) values ('nt.central.username','southwest_test');
#insert into properties (keystr, valuestr) values ('nt.central.password','WNpass1!');

#TRAINING
#insert into properties (keystr, valuestr) values ('nt.central.endpoint','http://drapp2.nettracer.aero:8780/NTServices_1_0/services/WorldTracerService');
#insert into properties (keystr, valuestr) values ('nt.central.username','southwest_test');
#insert into properties (keystr, valuestr) values ('nt.central.password','WNpass1!');

#PROD - TODO need to confirm prod central endpoint for swa
#insert into properties (keystr, valuestr) values ('nt.central.endpoint','');
#insert into properties (keystr, valuestr) values ('nt.central.username','southwest_prod');
#insert into properties (keystr, valuestr) values ('nt.central.password','WNpass1!');

insert into properties (keyStr, valueStr) values ('passenger.data.in.expense','1');

insert into properties (keyStr,valueStr) VALUES 
('default.autoprovision.timezone' ,'10'),
('current.autoprovision.timezone','10'),
('default.autoprovision.locale' ,'en'),
('current.autoprovision.locale' ,'en'),
('default.autoprovision.currency' ,'USD'),
('default.autoprovision.dateformat' ,'1'),
('default.autoprovision.timeformat' ,'1'),
('default.autoprovision.timeout' ,'30');

insert into properties (keyStr, valueStr) values ('create.call.task.timezone','CST');

insert into properties (keyStr, valueStr) values ('bagdrop.refresh.limit','2');


################## Needs to be ran against DB2, not DB1 #######################

drop procedure getAgentAuditReport;
DELIMITER //
create procedure getAgentAuditReport (in startDate datetime, in endDate datetime, in agentlist varchar(500), in percent varchar(3))
begin
 declare NextString nvarchar(4000);
 declare percentlimit int default 0;
 declare Pos int;
 declare CommaCheck nvarchar(1);
 set NextString = '';
 set CommaCheck=right(agentlist,1);
 if(CommaCheck<>',') then
  set agentlist=concat(agentlist,',');
 end if;
 
 set Pos = Locate(',',agentlist);
  drop temporary table if exists resultTable;
  create temporary TABLE resultTable (agentfirstname varchar(20),agentlastname varchar(20), 
        agentusername varchar(20), Incident_ID varchar(13), takenDate date,
      actcode varchar(8), actdescription varchar(255),passlastname varchar(20),passfirstname varchar(20));
  while Pos <> 0
  do
    set NextString=substring(agentlist,1,Pos-1);
    set percentlimit =(SELECT ceiling((COUNT(inc.incident_id)*percent/100)) FROM incident inc inner join agent a on inc.agentassigned_ID=a.Agent_ID 
      inner join incident_Activity ia on ia.incident=inc.incident_id
      inner join activity act ON ia.activity = act.id
      left outer join Passenger p ON p.incident_ID = inc.Incident_ID 
      where inc.createdate>=startDate and inc.createdate<=endDate and a.username=NextString);
    insert into resultTable (agentfirstname,agentlastname, 
        agentusername, Incident_ID, takenDate ,
      actcode, actdescription ,passlastname,passfirstname) 
      select a.firstname,a.lastname, a.username, inc.Incident_ID, inc.createdate,
        act.code, act.description,p.lastname,p.firstname 
        from incident inc 
          inner join agent a on inc.agentassigned_ID=a.Agent_ID 
          inner join incident_Activity ia on ia.incident=inc.incident_id 
          inner join activity act ON ia.activity = act.id 
          left outer join Passenger p ON p.incident_ID = inc.Incident_ID 
            where a.username=NextString and inc.createdate>=startDate and inc.createdate<=endDate
            limit percentlimit;
    set agentlist=substring(agentlist,Pos+1);
    set Pos=LOCATE(',',agentlist);
  end while;
  select * from resultTable;
end //

drop procedure getBSOAgentAuditReport;
DELIMITER //
create procedure getBSOAgentAuditReport (in startDate datetime, in endDate datetime, in stationcode varchar(3),in agentUsername varchar(100), in itemtype varchar(1), in percent varchar(3))
begin
declare percentlimit int default 0;
set percentlimit =(SELECT ceiling((COUNT(i.incident_id)*percent/100)) FROM incident i inner join station s on i.stationcreated_ID=s.Station_ID 
   inner join Passenger p on p.incident_ID = i.Incident_ID and p.isprimary = true
   inner join agent a on a.agent_id=i.agent_id
    where i.createdate>= startDate and i.createdate <= endDate and s.stationcode=stationcode
	  and a.username=agentUsername and i.itemtype_ID=itemType);
select i.incident_id, timestamp(i.createdate, i.createtime) as takenDate, p.firstname as passFirstName,p.lastname as passLastName,
	a.userName as agentUserName, a.firstname as agentFirstName, a.lastname as agentLastName
   from incident i inner join station s on i.stationcreated_ID=s.Station_ID 
   inner join Passenger p on p.incident_ID = i.Incident_ID and p.isprimary = true
   inner join agent a on a.agent_id=i.agent_id
	  where i.createdate>= startdate and i.createdate <= enddate and s.stationcode=stationcode
	  and a.username=agentUsername and i.itemtype_ID=itemType limit percentlimit;
end //

## Make sure this is ran against Production. Already ran against Testing, Testing2, and Training ##
GRANT EXECUTE ON PROCEDURE wn_production.getAgentAuditReport TO 'ntwn_user'@'%';
GRANT EXECUTE ON PROCEDURE wn_production.getBSOAgentAuditReport TO 'ntwn_user'@'%';
flush privileges;
###################################################################


insert into properties (keyStr, valueStr) VALUES ('portal.url', 'https://live.nettracer.aero/customer-portal/southwest/login.do');