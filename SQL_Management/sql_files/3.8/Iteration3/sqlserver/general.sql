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


alter table item add lossCode integer default 0;
alter table item add faultStation_id integer default 0;

alter table audit_item add lossCode integer default 0;
alter table audit_item add faultStation_id integer default 0;

GO

update item set lossCode = 0, faultStation_id = 0;
update audit_item set lossCode = 0, faultStation_id = 0;

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (666,'Passenger Pick Up for Lost/Delay Incidents','Mark a Lost/Delay Item as Passenger Pick Up',6,null,0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (672,'Passenger Pick Up for Missing Incidents','Mark a Missing Item as Passenger Pick Up',12,null,0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (673,'Passenger Pick Up for Damaged Incidents','Mark a Damaged Item as Passenger Pick Up',8,null,0,99,0);

SET IDENTITY_INSERT status ON;
insert into status (Status_ID,description,table_ID) VALUES (59,'Passenger Pick Up',10);
insert into status (Status_ID,description,table_ID) values (1301,'Postal Mail',25);
insert into status (Status_ID,description,table_ID) values (1302,'Web Portal',25);
SET IDENTITY_INSERT status OFF;

insert into properties (keyStr,valueStr) VALUES ('bag.level.loss.codes.itin.check','-1');


alter table incident add custCommId integer default 1301;

GO

update incident set custCommId = 1301;

alter table issuance_category modify document_id bigint;

GO

update Issuance_Category set document_id=null where document_id='0';

create table activity (
  id bigint identity not null,
  code bigint not null,
  description varchar(255) not null,
  primary key (id)
);

GO

create table incident_activity (
  id bigint identity not null,
  incident varchar(13) not null,
  createDate datetime not null,
  publishedDate datetime,
  agent integer,
  approvalAgent integer,
  document bigint,
  status integer,
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

SET IDENTITY_INSERT status ON;
insert into status (Status_ID,description,table_id) values (1400,'Pending',26);
insert into status (Status_ID,description,table_id) values (1401,'Approved',26);
insert into status (Status_ID,description,table_id) values (1402,'Denied',26);
insert into status (Status_ID,description,table_id) values (1403,'Published',26);
insert into status (Status_ID,description,table_id) values (1404,'Pending Print',26);
insert into status (Status_ID,description,table_id) values (1405,'Pending Publish to Web Portal',26);
SET IDENTITY_INSERT status OFF;

GO

alter table template add typeAvailableFor integer default 0;

alter table document add title varchar(255);
alter table document add fileName varchar(255);

GO

update template set typeAvailableFor = 0;
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

alter table issuance_item_incident add document_id bigint;

GO

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (674,'Claims Report and Adjustment Prediction','Record Special fields in claims and print out a Claims Report & Adjustment Prediction ',59,null,0,99,0);

alter table fsclaim add totalLiability float default 0;
alter table fsclaim add excessValueAmt float default 0;
alter table fsclaim add ix tinyInt default 0;
alter table fsclaim add carryon tinyInt default 0;
alter table fsclaim add claimCheck varchar(50);

GO

update fsclaim set totalLiability=0;
update fsclaim set excessValueAmt=0;
update fsclaim set ix=0;
update fsclaim set carryon=0;

insert into category (description,type) VALUES ('NO',3);
insert into category (description,type) VALUES ('YES',3);
insert into category (description,type) VALUES ('WAIVE',3);


alter table ohd add inventoryDate datetime default null;
alter table audit_ohd add inventoryDate datetime default null;

alter table wt_transaction add agent_id integer;

insert into systemcomponents (component_id, component_name, component_desc, parent_component_id, component_action_link,display ,sort_order,sort_group) 
VALUES (412, 'To Be Inventoried', 'To Be Inventoried', 15, 'toBeInventoried.do', 1, 7 , 2);

GO

ALTER TABLE ohd ADD INDEX (inventoryDate);
ALTER TABLE ohd_itinerary ADD INDEX (legfrom);
ALTER TABLE ohd_itinerary ADD INDEX (legto);
ALTER TABLE ohd_itinerary ADD INDEX (departdate);
ALTER TABLE ohd_itinerary ADD INDEX (arrivedate);

GO

alter table activity modify code varchar(8) not null;

GO

alter table activity add constraint unique_activity_code unique(code);

GO

delete from incident_activity where incident_activity.activity = (select id from activity where code = '1500' and description = 'CUSTOMER COMMUNICATION');
delete from activity where code = '1500' and description = 'CUSTOMER COMMUNICATION';

insert into activity (code,description) values ('AS','ASSIGNED TO');
insert into activity (code,description) values ('55','CREATE LETTER');
insert into activity (code,description) values ('253','RESOLVE');
insert into activity (code,description) values ('99','INBOUND MAIL');
insert into activity (code,description) values ('256','SECONDARY CORRESPONDENCE');
insert into activity (code,description) values ('93','CIM/BCCFS COMPLETE');
insert into activity (code,description) values ('10','RECEIVED DAMAGE ITEM');
insert into activity (code,description) values ('182','AHL - SENT');
insert into activity (code,description) values ('ZRR','RYNNS-REPAIR/REPLACE');
insert into activity (code,description) values ('563','TO BE ASSIGNED BY DAMAGE');
insert into activity (code,description) values ('84','WAITING FOR CUSTOMERS CORRESP');
insert into activity (code,description) values ('53','DAMAGE - LZ TRASHED');
insert into activity (code,description) values ('95','SENT TO LFC FOR RESEARCH');
insert into activity (code,description) values ('CBP2','CLOSE BY PHONE - NO FILE');
insert into activity (code,description) values ('83LZ','BAGS RETURNED BY LZ');
insert into activity (code,description) values ('100','REVIEWED CORR. - NO RESPONSE');
insert into activity (code,description) values ('Z10','TO BE ASSIGNED TO CTR');
insert into activity (code,description) values ('570','TO BE ASSIGNED BY DAMAGE');
insert into activity (code,description) values ('CBP1','CLOSE BY PHONE - WITH FILE');
insert into activity (code,description) values ('84B','WAITING FOR CUSTOMERS BAG');
insert into activity (code,description) values ('562','TO BE ASSIGNED BY DAMAGE');
insert into activity (code,description) values ('210','IX PYMT - 100% REIMBURSMENT');
insert into activity (code,description) values ('100D','REVIEWED CORRESPONDENCE - DUP');
insert into activity (code,description) values ('500','FAIR MARKET VALUE');
insert into activity (code,description) values ('220','REG PYMT-CHECK BEING PROCESSED');
insert into activity (code,description) values ('84V','WAITING FOR VENDOR RESPONSE');
insert into activity (code,description) values ('222','REG PYMT-LESS VALUABLES');
insert into activity (code,description) values ('01F','FAXED CLAIM FORM');
insert into activity (code,description) values ('224','IX PYMT-GENERIC');
insert into activity (code,description) values ('85','STATION SETTLED');
insert into activity (code,description) values ('89','LAWSUIT');
insert into activity (code,description) values ('77R','FWD TO CCA FOR HANDLING');
insert into activity (code,description) values ('578','GENERIC-DEPRECIATION LINE');
insert into activity (code,description) values ('76C','CURE SETTLE');
insert into activity (code,description) values ('79','BCCFS COPY RCVD');
insert into activity (code,description) values ('00','BCCFS SENT - AWAITING FILE(S)');
insert into activity (code,description) values ('91','MAIL RTND BY POST OFFICE');
insert into activity (code,description) values ('579','GENERIC-NO DEPRECIATION LINE');
insert into activity (code,description) values ('77C','FWD TO CURE FOR HANDLING');
insert into activity (code,description) values ('302','REG PYMT-MA CLAIM');
insert into activity (code,description) values ('77S','FWD TO STATION FOR HANDLING');
insert into activity (code,description) values ('94','RETURNED FROM LZ');
insert into activity (code,description) values ('84M','WAITING MERCHANT RESPONSE');
insert into activity (code,description) values ('85S','STATION SETTLED & TRASHED');
insert into activity (code,description) values ('98','OUTBOUND TO TRACING');
insert into activity (code,description) values ('99F','INBOUND FAX');
insert into activity (code,description) values ('221','MAX PYMT-CHECK BEING PROCESSED');
insert into activity (code,description) values ('01','SEND CLAIM FORM (REGULAR)');
insert into activity (code,description) values ('238','REG PYMT-GENERIC');
insert into activity (code,description) values ('244','REG PYMT-NO DOCUMENTATION');
insert into activity (code,description) values ('587','WATER DAMAGE');
insert into activity (code,description) values ('235','REG PYMT-LESS IX PAID LOCALLY');
insert into activity (code,description) values ('102','SPECIALIZE LETTER CREATED');
insert into activity (code,description) values ('84F','WAITING FOR FILE FROM OAL');
insert into activity (code,description) values ('289','IX PYMT-INCLUDES LOCAL PYMT');
insert into activity (code,description) values ('301','IX PYMT-100% PAY INCLUDE LOCAL');
insert into activity (code,description) values ('597','MISC-ACKNOWLEDGE BAG TO VENDOR');
insert into activity (code,description) values ('232','REG PYMT-PLUS TC');
insert into activity (code,description) values ('97A','FILE RETURNED TO HDQ');
insert into activity (code,description) values ('77E','FWD TO EXEC OFC FOR HANDLING');
insert into activity (code,description) values ('83W','BAG RETURNED WORLDTRACER');
insert into activity (code,description) values ('223','AVERAGE PYMT-GENERIC');
insert into activity (code,description) values ('83','EXPEDITED TO STN FOR RETURN');
insert into activity (code,description) values ('9206','ACAA REPAIR AUTHORIZATION-VEND');
insert into activity (code,description) values ('312','IX PYMT-FOR CONSEQUENTIAL EXPE');
insert into activity (code,description) values ('252','REG PYMT-NO DOC& LESS VALUABLE');
insert into activity (code,description) values ('592','CHECK BASED ON RECEIPT');
insert into activity (code,description) values ('278','IX PYMT+TC-100% REIMBURSEMENT');
insert into activity (code,description) values ('580','DMG/MISS ART-BAG HNDLD BY BSO');
insert into activity (code,description) values ('315','REG PYMT-UNDOC MA CLAIM - VALU');
insert into activity (code,description) values ('85V','VENDOR AUTHORIZATION SETTLE');
insert into activity (code,description) values ('208','IX PYMT + TC');
insert into activity (code,description) values ('288','AVERAGE OFFER');
insert into activity (code,description) values ('58V','VENDOR REPAIR/REPLACE');
insert into activity (code,description) values ('601','BCCF MATCH');
insert into activity (code,description) values ('590','CHECK LESS EXCLUDED ITEMS');
insert into activity (code,description) values ('9216','VENDOR INVOICE');
insert into activity (code,description) values ('107','AUTHORIZE REPAIR');
insert into activity (code,description) values ('99C','INBOUND MAIL FROM CURE');
insert into activity (code,description) values ('82','REQUESTED BAG- OTHER AIRLINE');
insert into activity (code,description) values ('112','REQUEST ADDL INFO');
insert into activity (code,description) values ('311','REG PYMT-UNDOC MA CLAIM');

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (676,'Immediate Fulfillment','Issue a Voucher for Immediate Fulfillment',1100,null,0,99,0);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (677,'Email Fulfillment','Issue a Voucher for Email Fulfillment',1100,null,0,99,0);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (678,'Mail Fulfillment','Issue a Voucher for Mail Fulfillment',1100,null,0,99,0);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group)
values (679,'Cancel a Voucher','Cancel a Voucher',1100,null,0,99,0);

alter table ExpensePayout add distributemethod varchar(5);

GO

create table bagdrop (
  id bigint identity not null,
  createDate datetime not null,
  lastUpdated datetime not null,
  arrivalStationCode varchar(3),
  originStationCode varchar(3),
  entryMethod integer,
  bagDropTime datetime,
  schArrivalDate datetime,
  actArrivalDate datetime,
  airline varchar(3),
  flight varchar(10),
  createAgent_ID integer,
  primary key (id)
);

alter table ExpensePayout add paytype varchar(5);

insert into properties (keyStr,valueStr) values ('document.location.letters','letters');
insert into properties (keyStr,valueStr) values ('document.location.receipts','receipts');
insert into properties (keyStr,valueStr) values ('document.location.temp','temp');

GO

alter table issuance_item_inventory add verified_incident tinyInt default 0;
alter table audit_issuance_item_inventory add verified_incident tinyInt default 0;
alter table audit_issuance_item_quantity add verified_incident tinyInt default 0;

GO

update issuance_item_inventory set verified_incident=0;
update audit_issuance_item_inventory set verified_incident=0;
update audit_issuance_item_quantity set verified_incident=0;
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (680,'BSO Expense Process','Allows users to create Expense Payouts based on Established BSO Process',59,'',0,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (681,'BSO Expense Admin','Allows Administrator Usergroup to assign BSO Process and Limit to other Usergroups',39,'',0,99,0);
alter table usergroup add bsoLimit float default 0;

GO

update usergroup set bsoLimit=0;

alter table category add categoryVal integer default 0;

GO

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

alter table expensepayout add printcount integer default 0;

alter table expensetype modify description varchar(30); 

GO

update expensepayout set printcount=0;
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (
   1101,'Cust_Comm_Approval_Queue','Manage approval queue for customer communiations.',15,'customerCommunicationsApp.do',1,99,3);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (
   1102,'Cust_Comm_Rejection_Queue','Manage rejection queue for customer communiations.',15,'customerCommunicationsRejected.do',1,100,3);

GO

alter table task add incidentActivityId bigint default null;
alter table task add active bit default 1;

alter table expensepayout add ordernum varchar(10);
alter table expensepayout alter column paytype varchar(10);
alter table expensepayout add cancelreason varchar(10);
alter table expensepayout add cancelcount integer default 0;
alter table expensepayout add slvnum varchar(20);
alter table expensepayout add seccode varchar(10);
alter table expensepayout add issuanceItem integer default 0;

GO

update task set active = 1;
update expensepayout set cancelcount = 0,issuanceItem = 0;
SET IDENTITY_INSERT status ON;
insert into status (Status_ID,description,table_ID) VALUES (94,'Cancelled',11);
SET IDENTITY_INSERT status OFF;

insert into properties ( keyStr, valueStr) VALUES ('label.queue',1);
insert into systemcomponents (component_id, component_name, component_desc, parent_component_id, component_action_link,display ,sort_order,sort_group) 
VALUES (1500, 'Label Queue', 'Label Queue', 15, 'label.do', 1, 100, 4);

create table label (
  id bigint(20) not null identity,
  agent_id integer not null,
  text varchar(100) not null,
  lastUpdate datetime not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  primary key (id),
  constraint fk_label_agent foreign key (agent_id) references agent (Agent_ID)
);

create table incident_activity_remark (
  id bigint identity not null,
  agent bigint not null,
  createDate datetime not null,
  remarkText varchar(255),
  incidentActivity bigint not null,
  primary key (id)
);

GO

alter table incident_activity drop foreign key fk_activity_status;
alter table incident_activity drop column status;

alter table usergroup add luvLimit float default 0;

GO

update usergroup set luvLimit=0;

create table audit_bagdrop (
  id bigint identity not null,
  bagdrop_id bigint not null,
  entryDate datetime not null,
  entryMethod integer,
  bagDropTime datetime,
  schArrivalDate datetime,
  actArrivalDate datetime,
  modifyAgent_ID integer,
  primary key (id)
);

ALTER TABLE audit_bagdrop ADD INDEX bagdrop_id (bagdrop_id);

GO

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (
   750,'Bag Drop','View and update Bag Drops',39,'bagDrop.do?reset=1',1,99,0);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (
   751,'Bag Drop Admin','Bag Drop Admin',39,null,0,99,0);
   
alter table ohd alter column storage_location varchar(125);
alter table ohd alter column firstname varchar(25);
alter table ohd alter column lastname varchar(25);
alter table ohd_passenger alter column firstname varchar(25);
alter table ohd_passenger alter column lastname varchar(25);

alter table ohd alter column claimchecknum_bagnumber varchar(12);
alter table incident_claimcheck alter column claimchecknum_bagnumber varchar(12);   
   
alter table company_specific_variable add bagdrop_autorefresh_mins integer default 0;
alter table audit_company_specific_variable add bagdrop_autorefresh_mins integer default 0;

GO

update company_specific_variable set bagdrop_autorefresh_mins=0;
update audit_company_specific_variable set bagdrop_autorefresh_mins=0;

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (688,'Payment Approval Process Admin'  ,'Allows Admin User to assign Payment Approval Process to other Usergroups', 39 ,'' ,0  ,104  ,3);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (687,'Payment Approval Process Create'  ,'Allows User to create finances that need to go through the Approval process'  , 59 ,'paymentApproval.do'  ,1  ,104  ,3);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (682,'Payment Approval Process Approve'  ,'Allows User to View and Approve or Reject Tasks associated with Awaiting Disbursement Tasks'  ,15  ,'paymentApproval.do'  ,1  ,104  ,3);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (683,'Disbursement Rejection View'  ,'Allows User to view rejections for issued Disbursements'  ,15  ,'rejectedDisbursements.do' ,1  ,101  ,3);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (684,'Fraud Review Task Queue'  ,'Allows User to view Fraud Review Tasks that need Approval or Rejection'  ,15  ,'fraudReview.do' ,1  ,102  ,3);
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (685,'Supervisor Review Task Queue'  ,'Allows User to view Supervisor Review Tasks that need Approval or Rejection'  ,15  ,'supervisorReview.do' ,1  ,103  ,3);

alter table expensepayout add maildate date;
alter table company_specific_variable add fraudReview tinyint  default 0;

GO

update company_specific_variable set fraudReview=0;

alter table incident_activity add expensepayout_id int;
alter table incident_activity add lastPrinted date;
SET IDENTITY_INSERT status ON;
insert into status (Status_ID,description,table_ID) VALUES (1411,'Fraud Review',26);
insert into status (Status_ID,description,table_ID) VALUES (1412,'Supervisor Review',26);
insert into status (Status_ID,description,table_ID) VALUES (1413,'Awaiting Disbursement',26);
insert into status (Status_ID,description,table_ID) VALUES (1414,'Fraud Rejected',26);
insert into status (Status_ID,description,table_ID) VALUES (1415,'Supervisor Rejected',26);
insert into status (Status_ID,description,table_ID) VALUES (1416,'Finance Rejected',26);
insert into status (Status_ID,description,table_ID) VALUES (1417,'Finance Approved',26);
insert into status (Status_ID,description,table_ID) VALUES (1418,'Fraud Approved',26);
insert into status (Status_ID,description,table_ID) VALUES (1419,'Supervisor Approved',26);
SET IDENTITY_INSERT status OFF;

insert into activity (code, description) VALUES ("55C","CREATE CLAIM SETTLEMENT LETTER");
insert into template_type (ordinal,defaultName) VALUES (5,'Expense');

insert into properties ( keyStr, valueStr) VALUES ('mishandling.attachment.at.creation',1);

insert into activity (code,description) VALUES ('99E','INBOUND WEB PORTAL MESSAGE');
insert into activity (code,description) VALUES ('99O','OUTBOUND WEB PORTAL MESSAGE');

GO

insert into properties ( keyStr, valueStr) VALUES ('document.print.queue',1);
insert into systemcomponents (component_id, component_name, component_desc, parent_component_id, component_action_link,display ,sort_order,sort_group) 
VALUES (1501, 'Document Print Queue', 'Approved incident activity documents pending print', 15, 'documentPrintCommunications.do', 1, 101, 4);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
VALUES (1103,'Tasks View Pending','View tasks not in work.',39,'viewPendingTasks.do',1,100,0);

insert into template_var (displayTag,associatedClass) VALUES ('TotalAmount','Expense');
insert into template_var_dependency (associatedClass,dependentClass) VALUES ('Expense'  ,'Expense');

SET IDENTITY_INSERT status ON;
insert into status (Status_ID,description,table_ID) values (1500,'Claim In Progress',27);
insert into status (Status_ID,description,table_ID) values (1501,'Correspondence Received',27);
insert into status (Status_ID,description,table_ID) values (1502,'Final Review',27);
insert into status (Status_ID,description,table_ID) values (1503,'Pending Customer Response',27);
insert into status (Status_ID,description,table_ID) values (1504,'Claim Finalized',27);
SET IDENTITY_INSERT status OFF;

alter table incident add claimStatus integer default 0;

GO

update incident set claimStatus = 0;

alter table audit_usergroup add luvLimit float default 0;
alter table audit_usergroup add bsoLimit float default 0;

alter table issuance_item_inventory add cost float default 0;
alter table audit_issuance_item_inventory add cost float default 0;

GO

update audit_usergroup set luvLimit = 0, bsoLimit = 0;
update issuance_item_inventory cost = 0;
update audit_issuance_item_inventory cost = 0;

alter table issuance_item_inventory add firstName varchar(25);
alter table issuance_item_inventory add lastName varchar(25);
alter table issuance_item_inventory add country varchar(2);
alter table issuance_item_inventory add phoneNumber varchar(20);
alter table issuance_item_inventory add address1 varchar(100);
alter table issuance_item_inventory add address2 varchar(100);
alter table issuance_item_inventory add city varchar(50);
alter table issuance_item_inventory add state varchar(2);
alter table issuance_item_inventory add province varchar(20);
alter table issuance_item_inventory add zip varchar(12);
alter table issuance_item_inventory add specialNeedDescription varchar(255);

alter table audit_issuance_item_inventory add firstName varchar(25);
alter table audit_issuance_item_inventory add lastName varchar(25);
alter table audit_issuance_item_inventory add country varchar(2);
alter table audit_issuance_item_inventory add phoneNumber varchar(20);
alter table audit_issuance_item_inventory add address1 varchar(100);
alter table audit_issuance_item_inventory add address2 varchar(100);
alter table audit_issuance_item_inventory add city varchar(50);
alter table audit_issuance_item_inventory add state varchar(2);
alter table audit_issuance_item_inventory add province varchar(20);
alter table audit_issuance_item_inventory add zip varchar(12);
alter table audit_issuance_item_inventory add specialNeedDescription varchar(255);

GO


create table UsergroupNameMap (
  id bigint identity not null,
  ldapName varchar(50),
  ntName varchar(50),
  ntGroupId integer,
  primary key (id));
 
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
VALUES (1150,'Unassigned Inbound Queue','View tasks that have not been assigned',15,'unassignedInboundQueue.do',1,50,4);

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) 
VALUES (1151,'Assigned Personal Tasks','View assigned tasks',15,'personalTasks.do?reset=1',1,51,4);

alter table agent add inboundQueue tinyint(1) default 0;
alter table audit_agent add inboundQueue tinyint(1) default 0;

GO

update agent set inboundQueue = 0;
update audit_agent set inboundQueue = 0;

alter table task alter column agent_id integer;

alter table task add inboundqueue_id bigint;

create table inboundqueue(
    id bigint identity not null,
    incident_id varchar(13),
    activity_id bigint,
    incidentActivityId bigint,
    primary key (id));

GO
