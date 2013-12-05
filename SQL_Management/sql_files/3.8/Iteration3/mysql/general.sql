insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (
   661,'Loss Codes at Per Bag Level','Changes loss codes to be assignable at a bag level',1100,'',0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (
   662,'Edit Non Closed and Non Delivered Bags in Same Station','Edit Non Closed and Non Delivered Bags and update their loss codes and fault stations',1100,'',0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (
   663,'Edit Closed or Delivered Bags in Same Station','Edit Closed or Delivered Bags in Same Station and update their loss codes and fault stations',1100,'',0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (
   664,'Edit Non Closed and Non Delivered Bags in Other Stations','Edit Non Closed and Non Delivered in Other Station and update their loss codes and fault stations',1100,'',0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (
   665,'Edit Closed or Delivered Bags in Other Stations','Edit Closed or Delivered Bags in Other Stations and update their loss codes and fault stations',1100,'',0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (
   675,'Edit Loss Codes for Any Bags','Edit Any Closed or Delivered Bags and update their loss codes and fault stations',1100,'',0,99,0);


alter table item add column lossCode int default 0;
alter table item add column faultStation_id int default 0;

alter table audit_item add column lossCode int default 0;
alter table audit_item add column faultStation_id int default 0;


insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (666,'Passenger Pick Up for Lost/Delay Incidents','Mark a Lost/Delay Item as Passenger Pick Up',6,null,0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (672,'Passenger Pick Up for Missing Incidents','Mark a Missing Item as Passenger Pick Up',12,null,0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (673,'Passenger Pick Up for Damaged Incidents','Mark a Damaged Item as Passenger Pick Up',8,null,0,99,0);
insert into status (Status_ID,description,table_ID) VALUES (59,'Passenger Pick Up',10);
insert into properties (keyStr,valueStr) VALUES ('bag.level.loss.codes.itin.check','-1');

insert into status (Status_ID,description,table_ID) values
(1301,'Postal Mail',25),
(1302,'Web Portal',25);

alter table incident add custCommId int(11) default 1301;
update incident set custCommId = 1301;

update company_specific_variable set  wt_enabled=1 where companycode_ID='wn' and wt_enabled!=1;

alter table issuance_category modify document_id bigint;
update Issuance_Category set document_id=null where document_id='0';

create table activity (
  id bigint auto_increment not null,
  code bigint not null,
  description varchar(255) not null,
  primary key (id)
);

create table incident_activity (
  id bigint auto_increment not null,
  incident varchar(13) not null,
  createDate datetime not null,
  publishedDate datetime,
  agent int,
  approvalAgent int,
  document bigint,
  status int,
  activity bigint,
  description varchar(255),
  custCommId bigint not null,
  primary key (id),
  constraint fk_activity_agent foreign key (agent) references agent (Agent_ID),
  constraint fk_activity_approval_agent foreign key (approvalAgent) references agent (Agent_ID),
  constraint fk_activity_document foreign key (document) references document (id),
  constraint fk_activity_status foreign key (status) references status (Status_ID),
  constraint fk_activity_activity foreign key (activity) references activity (id)
);

insert into status (Status_ID,description,table_id) values
(1400,'Pending',26),
(1401,'Approved',26),
(1402,'Denied',26),
(1403,'Published',26);

alter table template add typeAvailableFor int default 0;

alter table document add title varchar(255);
alter table document add fileName varchar(255);

insert into activity (code,description) values (1500,'Customer Communication');

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
values (667,'Customer_Communications_Create','View the customer communications section on incidents and create communications.',1100,'',0,3,0 );
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
values (668,'Customer_Communications_Edit','View/Edit existing customer communications that have yet to be sent to the customer.',1100,'',0,4,0 );
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
values (669,'Customer_Communications_Delete','Delete customer communications that have yet to be sent to the customer.',1100,'',0,5,0 );
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
values (670,'Customer_Communications_View_Published','View customer communications which have already been published and sent to the customer.',1100,'',0,6,0 );
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
values (671,'Customer_Communications_Approval','Approve/Deny customer communications which have been submitted for approval.',1100,'',0,7,0 );

alter table issuance_item_incident add column document_id bigint;

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (674,'Claims Report and Adjustment Prediction','Record Special fields in claims and print out a Claims Report & Adjustment Prediction ',59,null,0,99,0);

alter table fsclaim add column totalLiability double default 0;
alter table fsclaim add column excessValueAmt double default 0;
alter table fsclaim add column ix tinyInt default 0;
alter table fsclaim add column carryon tinyInt default 0;
alter table fsclaim add column claimCheck varchar(50);
update fsclaim set totalLiability=0;
update fsclaim set excessValueAmt=0;

insert into category (description,type) VALUES ('NO',3);
insert into category (description,type) VALUES ('YES',3);
insert into category (description,type) VALUES ('WAIVE',3);


alter table ohd add column inventoryDate datetime default null;
alter table audit_ohd add column inventoryDate datetime default null;

alter table wt_transaction add column agent_id int(11);

insert into systemcomponents (component_id, component_name, component_desc, parent_component_id, component_action_link,display ,sort_order,sort_group) 
VALUES (412, 'To Be Inventoried', 'To Be Inventoried', 15, 'toBeInventoried.do', 1, 7 , 2);

ALTER TABLE ohd ADD INDEX (inventoryDate);
ALTER TABLE ohd_itinerary ADD INDEX (legfrom);
ALTER TABLE ohd_itinerary ADD INDEX (legto);
ALTER TABLE ohd_itinerary ADD INDEX (departdate);
ALTER TABLE ohd_itinerary ADD INDEX (arrivedate);

alter table activity modify code varchar(8) not null;
alter table activity add constraint unique_activity_code unique(code);

delete from incident_activity where incident_activity.activity = (select id from activity where code = '1500' and description = 'CUSTOMER COMMUNICATION');
delete from activity where code = '1500' and description = 'CUSTOMER COMMUNICATION';

insert into activity (code,description) values
('AS','ASSIGNED TO'),
('55','CREATE LETTER'),
('253','RESOLVE'),
('99','INBOUND MAIL'),
('256','SECONDARY CORRESPONDENCE'),
('93','CIM/BCCFS COMPLETE'),
('10','RECEIVED DAMAGE ITEM'),
('182','AHL - SENT'),
('ZRR','RYNNS-REPAIR/REPLACE'),
('563','TO BE ASSIGNED BY DAMAGE'),
('84','WAITING FOR CUSTOMERS CORRESP'),
('53','DAMAGE - LZ TRASHED'),
('95','SENT TO LFC FOR RESEARCH'),
('CBP2','CLOSE BY PHONE - NO FILE'),
('83LZ','BAGS RETURNED BY LZ'),
('100','REVIEWED CORR. - NO RESPONSE'),
('Z10','TO BE ASSIGNED TO CTR'),
('570','TO BE ASSIGNED BY DAMAGE'),
('CBP1','CLOSE BY PHONE - WITH FILE'),
('84B','WAITING FOR CUSTOMERS BAG'),
('562','TO BE ASSIGNED BY DAMAGE'),
('210','IX PYMT - 100% REIMBURSMENT'),
('100D','REVIEWED CORRESPONDENCE - DUP'),
('500','FAIR MARKET VALUE'),
('220','REG PYMT-CHECK BEING PROCESSED'),
('84V','WAITING FOR VENDOR RESPONSE'),
('222','REG PYMT-LESS VALUABLES'),
('01F','FAXED CLAIM FORM'),
('224','IX PYMT-GENERIC'),
('85','STATION SETTLED'),
('89','LAWSUIT'),
('77R','FWD TO CCA FOR HANDLING'),
('578','GENERIC-DEPRECIATION LINE'),
('76C','CURE SETTLE'),
('79','BCCFS COPY RCVD'),
('00','BCCFS SENT - AWAITING FILE(S)'),
('91','MAIL RTND BY POST OFFICE'),
('579','GENERIC-NO DEPRECIATION LINE'),
('77C','FWD TO CURE FOR HANDLING'),
('302','REG PYMT-MA CLAIM'),
('77S','FWD TO STATION FOR HANDLING'),
('94','RETURNED FROM LZ'),
('84M','WAITING MERCHANT RESPONSE'),
('85S','STATION SETTLED & TRASHED'),
('98','OUTBOUND TO TRACING'),
('99F','INBOUND FAX'),
('221','MAX PYMT-CHECK BEING PROCESSED'),
('01','SEND CLAIM FORM (REGULAR)'),
('238','REG PYMT-GENERIC'),
('244','REG PYMT-NO DOCUMENTATION'),
('587','WATER DAMAGE'),
('235','REG PYMT-LESS IX PAID LOCALLY'),
('102','SPECIALIZE LETTER CREATED'),
('84F','WAITING FOR FILE FROM OAL'),
('289','IX PYMT-INCLUDES LOCAL PYMT'),
('301','IX PYMT-100% PAY INCLUDE LOCAL'),
('597','MISC-ACKNOWLEDGE BAG TO VENDOR'),
('232','REG PYMT-PLUS TC'),
('97A','FILE RETURNED TO HDQ'),
('77E','FWD TO EXEC OFC FOR HANDLING'),
('83W','BAG RETURNED WORLDTRACER'),
('223','AVERAGE PYMT-GENERIC'),
('83','EXPEDITED TO STN FOR RETURN'),
('9206','ACAA REPAIR AUTHORIZATION-VEND'),
('312','IX PYMT-FOR CONSEQUENTIAL EXPE'),
('252','REG PYMT-NO DOC& LESS VALUABLE'),
('592','CHECK BASED ON RECEIPT'),
('278','IX PYMT+TC-100% REIMBURSEMENT'),
('580','DMG/MISS ART-BAG HNDLD BY BSO'),
('315','REG PYMT-UNDOC MA CLAIM - VALU'),
('85V','VENDOR AUTHORIZATION SETTLE'),
('208','IX PYMT + TC'),
('288','AVERAGE OFFER'),
('58V','VENDOR REPAIR/REPLACE'),
('601','BCCF MATCH'),
('590','CHECK LESS EXCLUDED ITEMS'),
('9216','VENDOR INVOICE'),
('107','AUTHORIZE REPAIR'),
('99C','INBOUND MAIL FROM CURE'),
('82','REQUESTED BAG- OTHER AIRLINE'),
('112','REQUEST ADDL INFO'),
('311','REG PYMT-UNDOC MA CLAIM');

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (676,'Immediate Fulfillment','Issue a Voucher for Immediate Fulfillment',1100,null,0,99,0);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (677,'Email Fulfillment','Issue a Voucher for Email Fulfillment',1100,null,0,99,0);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (678,'Mail Fulfillment','Issue a Voucher for Mail Fulfillment',1100,null,0,99,0);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (679,'Cancel a Voucher','Cancel a Voucher',1100,null,0,99,0);

alter table ExpensePayout add column distributemethod varchar(5);

create table bagdrop (
  id bigint auto_increment not null,
  createDate datetime not null,
  lastUpdated datetime not null,
  arrivalStationCode varchar(3),
  originStationCode varchar(3),
  entryMethod int,
  bagDropTime datetime,
  schArrivalDate datetime,
  actArrivalDate datetime,
  airline varchar(3),
  flight varchar(10),
  createAgent_ID int,
  primary key (id)
);

alter table ExpensePayout add column paytype varchar(5);

insert into properties (keyStr,valueStr) values
('document.location.letters','letters'),
('document.location.receipts','receipts'),
('document.location.temp','temp');

alter table issuance_item_inventory add verified_incident tinyInt default 0;
alter table audit_issuance_item_inventory add verified_incident tinyInt default 0;
alter table audit_issuance_item_quantity add verified_incident tinyInt default 0;

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (680,'BSO Expense Process','Allows users to create Expense Payouts based on Established BSO Process',59,'',0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (681,'BSO Expense Admin','Marks Usergroup as a Administrator to not be limited by BSO Process',39,'',0,99,0);
alter table usergroup add column bsoLimit double default 0;
update usergroup set bsoLimit=0;

alter table category add column categoryVal int default 0;
update category set categoryVal=0;

insert into category (description ,type,categoryVal) VALUES ('Curb Side',4,1);
insert into category (description ,type,categoryVal) VALUES ('Ticket Counter',4,2);
insert into category (description ,type,categoryVal) VALUES ('Gate',4,3);
insert into category (description ,type,categoryVal) VALUES ('Remote',4,4);
insert into category (description ,type,categoryVal) VALUES ('Plane-side',4,5);
insert into category (description ,type,categoryVal) VALUES ('Unchecked',4,6);
insert into category (description ,type,categoryVal) VALUES ('Kiosk',4,7);

update systemcomponents set component_name='LUV Immediate Fulfillment' where component_id=676;
update systemcomponents set component_name='LUV Email Fulfillment' where component_id=677;
update systemcomponents set component_name='LUV Mail Fulfillment' where component_id=678;
update systemcomponents set component_name='LUV Cancel a Voucher' where component_id=679;

alter table expensepayout add column printcount int default 0;

alter table expensetype modify description varchar(30); 

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (
   1102,'Cust_Comm_Rejection_Queue','Manage rejection queue for customer communiations.',15,'customerCommunicationsRejected.do',1,100,3);

alter table task add incidentActivityId bigint default null; 

alter table expensepayout add column ordernum varchar(10);
alter table expensepayout modify column paytype varchar(10);
alter table expensepayout add column cancelreason varchar(10);

#Label Queue
insert into properties (ID, keyStr, valueStr) VALUES (140,'label.queue',1);
insert into systemcomponents (component_id, component_name, component_desc, parent_component_id, component_action_link,display ,sort_order,sort_group) 
VALUES (1500, 'Label Queue', 'Label Queue', 15, 'label.do', 1, 100, 4);

create table label (
  id bigint(20) not null auto_increment,
  agent_id int(11) not null,
  text varchar(100) not null,
  lastUpdate timestamp not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  primary key (id),
  constraint fk_label_agent foreign key (agent_id) references agent (Agent_ID)
);