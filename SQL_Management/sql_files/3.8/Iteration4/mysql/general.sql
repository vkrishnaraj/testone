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