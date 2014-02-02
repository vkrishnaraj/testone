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
   
   CREATE TABLE `passengerexp` (   
`PassengerExp_ID` int(11) NOT NULL AUTO_INCREMENT,   
`expensepayout_ID` varchar(13) NOT NULL DEFAULT '',   
`firstname` varchar(20) DEFAULT NULL,   
`middlename` varchar(20) DEFAULT NULL,   
`lastname` varchar(20) DEFAULT NULL,  
`address1` varchar(50) DEFAULT NULL,   
`address2` varchar(50) DEFAULT NULL,   
`city` varchar(50) DEFAULT NULL,   
`state_ID` varchar(2) DEFAULT NULL,   
`province` varchar(100) DEFAULT '',   
`zip` varchar(11) DEFAULT NULL,   
`homephone` varchar(50) DEFAULT NULL,
`workphone` varchar(50) DEFAULT NULL,
`mobile` varchar(50) DEFAULT NULL,
`email` varchar(100) DEFAULT NULL,     
`countrycode_ID` varchar(3) DEFAULT NULL, 
PRIMARY KEY (`PassengerExp_ID`),   
KEY `expensepayout_ID` (`expensepayout_ID`) ) ;
    
alter table issuance_category add limitByPassenger bit not null default 0;

update status set description = 'Letter Pending Approval' where Status_ID = '1400';
update status set description = 'Letter Rejected' where Status_ID = '1402';

create table task_type (
  id bigint not null auto_increment,
  code int not null,
  description varchar(255) not null,
  primary key(id)
);

insert into task_type (code,description) values
(1,'Inbound Physical'),
(2,'Inbound Electronic'),
(3,'Customer Communication'),
(4,'Disbursement'),
(5,'Fraud Review'),
(6,'Supervisor Review'),
(7,'Inbound Damaged');

alter table task add task_type_id bigint default null;

alter table bdo add column distance double default 0;
update bdo set distance=0;

insert into properties (keyStr,valueStr) VALUES ('issue.voucher.expiration.offset.days','365');

alter table expensepayout add `firstname` varchar(20) DEFAULT NULL;
alter table expensepayout add `middlename` varchar(20) DEFAULT NULL;   
alter table expensepayout add `lastname` varchar(20) DEFAULT NULL;  
alter table expensepayout add `address1` varchar(50) DEFAULT NULL;   
alter table expensepayout add `address2` varchar(50) DEFAULT NULL;   
alter table expensepayout add `city` varchar(50) DEFAULT NULL;   
alter table expensepayout add `state_ID` varchar(2) DEFAULT NULL;   
alter table expensepayout add `province` varchar(100) DEFAULT '';   
alter table expensepayout add `zip` varchar(11) DEFAULT NULL;   
alter table expensepayout add `homephone` varchar(50) DEFAULT NULL;
alter table expensepayout add `workphone` varchar(50) DEFAULT NULL;
alter table expensepayout add `mobile` varchar(50) DEFAULT NULL;
alter table expensepayout add `email` varchar(100) DEFAULT NULL;     
alter table expensepayout add `countrycode_ID` varchar(3) DEFAULT NULL; 

update expensepayout e, passengerexp p set 
e.firstname = p.firstname,
e.middlename = p.middlename,
e.lastname = p.lastname,
e.address1 = p.address1,
e.address2 = p.address2,
e.city = p.city,
e.state_ID = p.state_ID,
e.province = p.province,
e.zip = p.zip,
e.homephone = p.homephone,
e.workphone = p.workphone,
e.mobile = p.mobile,
e.email = p.email,
e.countrycode_ID = p.countrycode_ID
where e.expensepayout_ID = p.expensepayout_ID;

drop table passengerexp;

alter table company_irregularity_codes add column departStation tinyint default 0;
alter table company_irregularity_codes add column transferStation tinyint default 0;
alter table company_irregularity_codes add column destinationStation tinyint default 0;
alter table company_irregularity_codes add column anyStation tinyint default 0;
update company_irregularity_codes set departStation=0;
update company_irregularity_codes set transferStation=0;
update company_irregularity_codes set destinationStation=0;
update company_irregularity_codes set anyStation=0;

update task set task_type = '2DAYTASK' where task_type = 'TWODAYTASK';
update task set task_type = '3DAYTASK' where task_type = 'THREEDAYTASK';
update task set task_type = '4DAYTASK' where task_type = 'FOURDAYTASK';
update task set task_type = '5DAYTASK' where task_type = 'FIVEDAYTASK';