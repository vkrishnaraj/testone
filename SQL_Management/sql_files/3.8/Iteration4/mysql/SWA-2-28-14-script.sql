#Script to Run 2/28/2014

insert into task_type (code, description ) values (8,'Secondary Correspondence');

alter table activity add display bit not null default 1;
update activity set display = 0 where code in ('55C', '99E');

alter table audit_ohd modify column storage_location varchar(125);

update systemcomponents set component_action_link = 'unassignedInboundQueue.do?loadList=1' where component_id = 1150;

alter table agent_logger add column expired boolean not null default false;
update agent_logger set expired=true where log_off_time is null;

alter table lffound drop column receiptFileName;
alter table lffound add receiptFile_id bigint;

alter table bdo add column pickuptz_id int default 0;
update bdo set pickuptz_id=0;

ALTER TABLE inboundqueue ADD INDEX incident_id (incident_id);
ALTER TABLE task ADD INDEX inboundqueue_id (inboundqueue_id);

alter table template add marginTop varchar(5) default "1.0";
alter table template add marginBottom varchar(5) default "1.0";
alter table template add marginLeft varchar(5) default "0.75";
alter table template add marginRight varchar(5) default "0.75";

alter table document add marginTop varchar(5) default "1.0";
alter table document add marginBottom varchar(5) default "1.0";
alter table document add marginLeft varchar(5) default "0.75";
alter table document add marginRight varchar(5) default "0.75";

insert into properties (keyStr, valueStr) values ('bagdrop.refresh.limit','2');

#insert into properties (keyStr, valueStr) VALUES ('portal.url', 'https://testing.nettracer.aero/customer-portal/southwest/login.do'); #run for Testing
#insert into properties (keyStr, valueStr) VALUES ('portal.url', 'https://testing2.nettracer.aero/customer-portal/southwest/login.do'); #run for Testing2
#insert into properties (keyStr, valueStr) VALUES ('portal.url', 'https://training.nettracer.aero/customer-portal/southwest/login.do'); #run for Training 
