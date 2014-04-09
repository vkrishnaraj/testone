insert into template_var (displayTag,associatedClass) VALUES 
   ('FullAddress' ,'Incident'), 
   ('FullAddress','Claim'),
   ('FullAddress','FoundItem'),
   ('Country','Incident'), 
   ('Country','Claim'), 
   ('Country','FoundItem'),
   ('Province','Incident'), 
   ('Province','Claim'), 
   ('Province','FoundItem');
   
    
alter table issuance_category add limitByPassenger bit not null default 0;

GO

update issuance_category set limitByPassenger = 0;

update status set description = 'Letter Pending Approval' where Status_ID = '1400';
update status set description = 'Letter Rejected' where Status_ID = '1402';

create table task_type (
  id bigint not null IDENTITY,
  code integer not null,
  description varchar(255) not null,
  primary key(id)
);

GO

insert into task_type (code,description) values
(1,'Inbound Physical'),
(2,'Inbound Electronic'),
(3,'Customer Communication'),
(4,'Disbursement'),
(5,'Fraud Review'),
(6,'Supervisor Review'),
(7,'Inbound Damaged');

alter table task add task_type_id bigint default null;

alter table bdo add distance float default 0;

GO

update bdo set distance=0;

insert into properties (keyStr,valueStr) VALUES ('issue.voucher.expiration.offset.days','365');

alter table expensepayout add firstname varchar(20) DEFAULT NULL;
alter table expensepayout add middlename varchar(20) DEFAULT NULL;   
alter table expensepayout add lastname varchar(20) DEFAULT NULL;  
alter table expensepayout add address1 varchar(50) DEFAULT NULL;   
alter table expensepayout add address2 varchar(50) DEFAULT NULL;   
alter table expensepayout add city varchar(50) DEFAULT NULL;   
alter table expensepayout add state_ID varchar(2) DEFAULT NULL;   
alter table expensepayout add province varchar(100) DEFAULT '';   
alter table expensepayout add zip varchar(11) DEFAULT NULL;   
alter table expensepayout add homephone varchar(50) DEFAULT NULL;
alter table expensepayout add workphone varchar(50) DEFAULT NULL;
alter table expensepayout add mobile varchar(50) DEFAULT NULL;
alter table expensepayout add email varchar(100) DEFAULT NULL;     
alter table expensepayout add countrycode_ID varchar(3) DEFAULT NULL; 

GO
update expensepayout set province='';

alter table company_irregularity_codes add departStation tinyint default 0;
alter table company_irregularity_codes add transferStation tinyint default 0;
alter table company_irregularity_codes add destinationStation tinyint default 0;
alter table company_irregularity_codes add anyStation tinyint default 0;

GO

update company_irregularity_codes set departStation=0;
update company_irregularity_codes set transferStation=0;
update company_irregularity_codes set destinationStation=0;
update company_irregularity_codes set anyStation=0;

update task set task_type = '2DAYTASK' where task_type = 'TWODAYTASK';
update task set task_type = '3DAYTASK' where task_type = 'THREEDAYTASK';
update task set task_type = '4DAYTASK' where task_type = 'FOURDAYTASK';
update task set task_type = '5DAYTASK' where task_type = 'FIVEDAYTASK';


alter table ohd alter column firstname varchar(30);
alter table ohd alter column lastname varchar(30);
alter table ohd_passenger alter column firstname varchar(30);
alter table ohd_passenger alter column lastname varchar(30);
alter table audit_ohd alter column firstname varchar(30);
alter table audit_ohd alter column lastname varchar(30);
alter table audit_ohd_passenger alter column firstname varchar(30);
alter table audit_ohd_passenger alter column lastname varchar(30);

GO

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (1502,'Import WT AHL','Allows user to Import WorldTracer AHL to create new Incidents',93,'',0,0,0);

alter table issuance_category add copyDescription bit not null default 0;
alter table issuance_category alter column description varchar(100);
alter table issuance_item alter column description varchar(100);
alter table issuance_item add cost float default 0;

alter table ohd_log alter column expeditenum varchar(12);

GO

update issuance_category set copyDescription = 0;
update issuance_item set cost = 0;

insert into task_type (code, description ) values (8,'Secondary Correspondence');

alter table activity add display bit not null default 1;

GO

update activity set display = 1;
update activity set display = 0 where code in ('55C', '99E');

alter table audit_ohd alter column storage_location varchar(125);

create index wt_transaction_createDate_idx on wt_transaction (createDate);
create index wt_transaction_result_idx on wt_transaction (result);
create index wt_transaction_txType_idx on wt_transaction (txType);
create index wt_transaction_agent_id_idx on wt_transaction (agent_id);
create index wt_transaction_incident_id_idx on wt_transaction (incident_id);
create index wt_transaction_ohd_id_idx on wt_transaction (ohd_id);

update systemcomponents set component_action_link = 'unassignedInboundQueue.do?loadList=1' where component_id = 1150;

alter table agent_logger add expired bit not null default 0;

GO

update agent_logger set expired=0;
update agent_logger set expired=1 where log_off_time is null;

alter table lffound add receiptFile_id bigint;

alter table bdo add pickuptz_id integer default 0;

GO

update bdo set pickuptz_id=0;


create index inboundqueue_idx on inboundqueue (incident_id);
create index task_idx on task (inboundqueue_id);


alter table template add marginTop varchar(5) default '1.0';
alter table template add marginBottom varchar(5) default '1.0';
alter table template add marginLeft varchar(5) default '0.75';
alter table template add marginRight varchar(5) default '0.75';

alter table document add marginTop varchar(5) default '1.0';
alter table document add marginBottom varchar(5) default '1.0';
alter table document add marginLeft varchar(5) default '0.75';
alter table document add marginRight varchar(5) default '0.75';

GO

update template set marginTop = '1.0', marginBottom = '1.0', marginLeft = '0.75', marginRight = '0.75';
update document set marginTop = '1.0', marginBottom = '1.0', marginLeft = '0.75', marginRight = '0.75';

GO