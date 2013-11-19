-- upgradefrom 1.76.0.2 to 2.0
-- run this just in case
--mysql_upgrade --force

---- block A1 

-- ISSUE =   0000980 ; Changed State ID for International Provinces

update state set state='AA(Armed Forces Atlantic)' where state_id = 'APO AA';
update state set state='AA(Armed Forces Europe)' where state_id = 'APO AE';
update state set state='AA(Armed Forces Pacific)' where state_id = 'APO AP';

Update state SET state_id = 'AA' where state_id = 'APO AA';
Update state SET state_id = 'AE' where state_id = 'APO AE';
Update state SET state_id = 'AP' where state_id = 'APO AP';

-- TD is no longer a color

UPDATE OHD SET COLOR = 'BN' WHERE COLOR = 'TD';
UPDATE ITEM SET COLOR = 'BN' WHERE COLOR = 'TD';
UPDATE AUDIT_OHD SET COLOR = 'BN' WHERE COLOR = 'TD';
UPDATE AUDIT_ITEM SET COLOR = 'BN' WHERE COLOR = 'TD';


-- ISSUE = 0000954 ; Set Permissions for Sending Message to ALL.

insert into systemcomponents
      (component_id, component_name, component_desc, parent_component_id,
      component_action_link, display, sort_order, sort_group)
  values (402, 'Send Messages to all Stations', 'Able to send messages to all stations',
  15,'', 0, 15, 2);

insert into group_component_policy (component_id,group_id) values (402, 4);

------ block A2

insert into properties (keyStr, valueStr) values ('send.forward.notifications', '0');


insert into systemcomponents (component_id, component_name, component_desc, 

parent_component_id, component_action_link, display, sort_order, sort_group)
	values (403, 'Push Lost Delay to CRM', 'Ability to manually push incident to CRM', 6, 

null, 0, 0, 0);

insert into systemcomponents (component_id, component_name, component_desc, 

parent_component_id, component_action_link, display, sort_order, sort_group)
	values (404, 'Push Damaged to CRM', 'Ability to manually push incident to CRM', 8, 

null, 0, 0, 0);

insert into systemcomponents (component_id, component_name, component_desc, 

parent_component_id, component_action_link, display, sort_order, sort_group)
	values (406, 'Push Pilferage to CRM', 'Ability to manually push incident to CRM', 12, 

null, 0, 0, 0);

	
drop table if exists Crm;

create table Crm (
    id bigint not null auto_increment,
    crm_key varchar(128),
    status varchar(255),
    incident_id varchar(13),
    primary key (id)
);

--alter table Crm 
--    add index FK109BEAB4E994E (incident_id), 
--    add constraint FK109BEAB4E994E 
--    foreign key (incident_id) 
--    references Incident (Incident_ID);
    
-- Cancelling BDOs

insert into systemcomponents (component_id, component_name, component_desc, 

parent_component_id, component_action_link, display, sort_order, sort_group)
	values (407, 'Cancel BDO', 'Ability to cancel a BDO', 6, 'cancelBdo.do', 0, 0, 0);

alter table bdo add column canceled smallint(1);

update bdo set canceled = 0;

		drop table if exists item_bdo;
		
--	alter table item_bdo 
--        drop 
--        foreign key FK463465411E79EFAE;

--    alter table item_bdo 
--        drop 
--        foreign key FK463465413E7F1546;
        
    drop table if exists item_bdo;
				
    create table item_bdo (
        id bigint not null auto_increment,
        canceled bit not null default 0,
        bdo_ID integer not null,
        item_ID integer not null,
        primary key (id)
    );

    alter table item_bdo 
        add index FK463465411E79EFAE (item_ID);

    alter table item_bdo 
        add index FK463465413E7F1546 (bdo_id);

-- Iterate through all items and create an item-bdo matching entry in the table
insert into item_bdo (item_id, bdo_id) select item_id, bdo_ID from item where bdo_id is not 

null;

-- Drop bdo column from item
alter table item drop column bdo_ID;

---- block A3

    drop table if exists oc_address;

    drop table if exists oc_bag;

    drop table if exists oc_claim;

    drop table if exists oc_content;

    drop table if exists oc_file;

    drop table if exists oc_itinerary;

    drop table if exists oc_phone;



    create table oc_address (
        id bigint not null auto_increment,
        address1 varchar(50),
        address2 varchar(50),
        city varchar(50),
        country varchar(3),
        postalCode varchar(20),
        stateProvince varchar(50),
        primary key (id)
    );

    create table oc_bag (
        bagId bigint not null auto_increment,
        bagArrive bit not null,
        bagColor varchar(2),
        bagType varchar(2),
        brand varchar(10),
        externalMarkings varchar(50),
        feet bit not null,
        hardSided bit not null,
        locks bit not null,
        nameOnBag varchar(40),
        nameTag bit not null,
        pockets bit not null,
        pullStrap bit not null,
        purchaseDate date,
        retractibleHandle bit not null,
        ribbonsOrMarkings bit not null,
        softSided bit not null,
        tag varchar(10),
        trim bit not null,
        wheels bit not null,
        zippers bit not null,
        claimId bigint not null,
        primary key (bagId)
    );

    create table oc_claim (
        claimId bigint not null auto_increment,
        accept varchar(20),
        attemptToClaimAtArrival bit not null,
        bagClearCustoms bit not null,
        baggageReroutedEnRoute bit not null,
        bagsDelayed integer not null,
        bagsStillMissing integer not null,
        bagsTravelWith integer not null,
        businessName varchar(50),
        chargedForExcessBaggage bit not null,
        checkedLocation varchar(20),
        comments text,
        declaredCurrency varchar(3),
        declaredExcessValue bit not null,
        declaredValue varchar(20),
        differentClaimCheck bit not null,
        emailAddress varchar(40),
        filedPreviousAirline varchar(50),
        filedPreviousClaim bit not null,
        filedPreviousClaimant varchar(50),
        filedPrevoiusDate date,
        firstName varchar(20),
        frequentFlierNumber varchar(20),
        haveToRecheck bit not null,
        lastName varchar(20),
        lastSawBaggage varchar(50),
        middleInitial varchar(1),
        occupation varchar(50),
        passengersTravelingWithYou integer not null,
        reportedToAnotherAirline bit not null,
        reroutedAirlineCity varchar(20),
        reroutedReason varchar(50),
        socialSecurity varchar(20),
        status varchar(20),
        submitDate datetime,
        ticketWithAnotherAirline bit not null,
        tsaInspected bit not null,
        tsaInspectionLocation varchar(50),
        tsaNotePresent bit not null,
        wasBagInspected bit not null,
        whereDidYouFileReport varchar(50),
        whereWasBaggageChecked varchar(20),
        incident_id varchar(13) not null,
        mailingAddress_id bigint,
        permanentAddress_id bigint,
        primary key (claimId)
    );

    create table oc_content (
        id bigint not null auto_increment,
        article varchar(50),
        brand varchar(50),
        color varchar(50),
        currency varchar(3),
        male bit not null,
        price double precision not null,
        purchasedAt varchar(50),
        purchasedDate varchar(50),
        size varchar(50),
        bagId bigint not null,
        primary key (id)
    );

    create table oc_file (
        id bigint not null auto_increment,
        dateUploaded datetime,
        filename varchar(50),
        claimId bigint not null,
        primary key (id)
    );

    create table oc_itinerary (
        id bigint not null auto_increment,
        airline varchar(20),
        arrivalCity varchar(3),
        date datetime,
        departureCity varchar(3),
        flightNum varchar(5),
        claimId bigint not null,
        primary key (id)
    );

    create table oc_phone (
        id bigint not null auto_increment,
        phoneNumber varchar(20),
        phoneType varchar(10),
        claimId bigint not null,
        primary key (id)
    );


    drop table if exists oc_audit_address;

    drop table if exists oc_audit_bag;

    drop table if exists oc_audit_claim;

    drop table if exists oc_audit_content;

    drop table if exists oc_audit_file;

    drop table if exists oc_audit_itinerary;

    drop table if exists oc_audit_phone;
		
    create table oc_audit_address (
        aid bigint not null auto_increment,
        address1 varchar(50),
        address2 varchar(50),
        city varchar(50),
        country varchar(3),
        postalCode varchar(20),
        stateProvince varchar(50),
        auditClaimId bigint not null,
        primary key (aid)
    );

    create table oc_audit_bag (
        abagId bigint not null auto_increment,
        bagArrive bit not null,
        bagColor varchar(2),
        bagType varchar(2),
        brand varchar(10),
        externalMarkings varchar(50),
        feet bit not null,
        hardSided bit not null,
        locks bit not null,
        nameOnBag varchar(40),
        nameTag bit not null,
        pockets bit not null,
        pullStrap bit not null,
        purchaseDate date,
        retractibleHandle bit not null,
        ribbonsOrMarkings bit not null,
        softSided bit not null,
        tag varchar(10),
        trim bit not null,
        wheels bit not null,
        zippers bit not null,
        auditClaimId bigint not null,
        primary key (abagId)
    );

    create table oc_audit_claim (
        auditClaimId bigint not null auto_increment,
        accept varchar(20),
        attemptToClaimAtArrival bit not null,
        bagClearCustoms bit not null,
        baggageReroutedEnRoute bit not null,
        bagsDelayed integer not null,
        bagsStillMissing integer not null,
        bagsTravelWith integer not null,
        businessName varchar(50),
        chargedForExcessBaggage bit not null,
        checkedLocation varchar(20),
        claimId bigint not null,
        comments text,
        declaredCurrency varchar(3),
        declaredExcessValue bit not null,
        declaredValue varchar(20),
        differentClaimCheck bit not null,
        emailAddress varchar(40),
        filedPreviousAirline varchar(50),
        filedPreviousClaim bit not null,
        filedPreviousClaimant varchar(50),
        filedPrevoiusDate date,
        firstName varchar(20),
        frequentFlierNumber varchar(20),
        haveToRecheck bit not null,
        lastName varchar(20),
        lastSawBaggage varchar(50),
        middleInitial varchar(1),
        occupation varchar(50),
        passengersTravelingWithYou integer not null,
        reportedToAnotherAirline bit not null,
        reroutedAirlineCity varchar(20),
        reroutedReason varchar(50),
        socialSecurity varchar(20),
        status varchar(20),
        submitDate datetime,
        ticketWithAnotherAirline bit not null,
        time_modified datetime,
        tsaInspected bit not null,
        tsaInspectionLocation varchar(50),
        tsaNotePresent bit not null,
        wasBagInspected bit not null,
        whereDidYouFileReport varchar(50),
        whereWasBaggageChecked varchar(20),
        agent_id integer,
        incident_id varchar(13) not null,
        mailingAddress_aid bigint,
        permanentAddress_aid bigint,
        primary key (auditClaimId)
    );

    create table oc_audit_content (
        aid bigint not null auto_increment,
        article varchar(50),
        brand varchar(50),
        color varchar(50),
        currency varchar(3),
        male bit not null,
        price double precision not null,
        purchasedAt varchar(50),
        purchasedDate varchar(50),
        size varchar(50),
        abagId bigint not null,
        primary key (aid)
    );

    create table oc_audit_file (
        aid bigint not null auto_increment,
        dateUploaded datetime,
        filename varchar(50),
        auditClaimId bigint not null,
        primary key (aid)
    );

    create table oc_audit_itinerary (
        aid bigint not null auto_increment,
        airline varchar(20),
        arrivalCity varchar(3),
        date datetime,
        departureCity varchar(3),
        flightNum varchar(5),
        auditClaimId bigint not null,
        primary key (aid)
    );

    create table oc_audit_phone (
        aid bigint not null auto_increment,
        phoneNumber varchar(20),
        phoneType varchar(10),
        auditClaimId bigint not null,
        primary key (aid)
    );
    
-- block A4

insert into properties (keyStr, valueStr) values ('wt.move.tags.to.wt', '0');

-- a -- baggage weight related --

ALTER TABLE incident ADD overall_weight double NOT NULL default 0;
ALTER TABLE audit_incident ADD overall_weight double NOT NULL default 0;

ALTER TABLE incident ADD overall_weight_unit varchar(3);
ALTER TABLE audit_incident ADD overall_weight_unit varchar(3);

ALTER TABLE item ADD bag_weight double NOT NULL default 0;
ALTER TABLE audit_item ADD bag_weight double NOT NULL default 0;

ALTER TABLE item ADD bag_weight_unit varchar(3);
ALTER TABLE audit_item ADD bag_weight_unit varchar(3);

INSERT INTO properties (keyStr, valueStr) VALUES ('nt.company.weight.unit.default', 'lbs');

INSERT INTO systemcomponents 
(component_id, component_name, component_desc, parent_component_id, component_action_link, 

display, sort_order, sort_group) 
VALUES (330, 'Baggage Weight', 'Baggage weight', 6, null, 0, 21, 0);

set @company = 'AD';
select (@admin_grp:=UserGroup_ID) from usergroup where description = 'Admin' and 

companycode_ID=@company LIMIT 1;

INSERT INTO group_component_policy (component_id,group_id) VALUES (330, @admin_grp);

---- b ---- 

ALTER TABLE ohd
ADD tagSentToWt bit NOT NULL DEFAULT 0;

ALTER TABLE ohd
ADD tagSentToWtStationId integer NOT NULL DEFAULT 0;



---- c ---- new incident_control table and populating it ----
drop table if exists INCIDENT_CONTROL;

create table INCIDENT_CONTROL (
id bigint not null auto_increment,
assignedDate datetime,
Incident_ID varchar(13) not null,
primary key (id)
);

-- need to remove the following constraint
--alter table INCIDENT_CONTROL 
--    add index FK596DAD0AB4E994E (Incident_ID), 
--    add constraint FK596DAD0AB4E994E 
--    foreign key (Incident_ID) 
--    references Incident (Incident_ID);

-- block A5 -- for best result run it as root ------ 

DELIMITER $$
DROP PROCEDURE IF EXISTS myLoopProc$$

CREATE PROCEDURE myLoopProc ()
BEGIN
     DECLARE l_id          VARCHAR(13);
     DECLARE no_more_incidents INT;
     DECLARE last_used varchar(13);

     DECLARE inc_csr CURSOR FOR
          SELECT incident_ID
            FROM incident;

     DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_more_incidents=1;

     SET no_more_incidents=0;
    SET last_used = 'ABC123';
     OPEN inc_csr;
     inc_loop:REPEAT
          FETCH inc_csr INTO l_id;
	  
					BEGIN
					select max(@most_recent_station_change := 

audit_incident_id) from audit_incident where incident_id = l_id and stationassigned_ID != 
					(select stationassigned_ID from incident where 

incident_id = l_id);
					
					IF @most_recent_station_change is null THEN
						SET @most_recent_station_change = 0;
					END IF;
			
					SET @wanted_audit_incident_id =
					(select min(audit_incident_id) from audit_incident 

where incident_id = l_id and audit_incident_id > @most_recent_station_change and 

stationassigned_ID = 
					(select stationassigned_ID from incident where 

incident_id = l_id));
					
					select audit_incident_id,(@wanted_incident_id := 

Incident_ID),(@wanted_assign_change_date := modify_time) from audit_incident where 

audit_incident_id = @wanted_audit_incident_id;



          IF @wanted_incident_id != last_used THEN
  					insert into incident_control 

(assignedDate,Incident_ID) values (@wanted_assign_change_date,@wanted_incident_id);
            SET last_used = l_id;
         END IF;


					SET @most_recent_station_change = null;
					END;
	  
     UNTIL no_more_incidents
     END REPEAT inc_loop;
     CLOSE inc_csr;
     SET no_more_incidents=0;


END$$

DELIMITER ;



-- call myLoopProc() ; -- What to do here


---- block A6 ---- 

ALTER TABLE message_copies ADD CONSTRAINT `FK_message_copies_1` FOREIGN KEY 

`FK_message_copies_1` (`station_id`)
REFERENCES `station` (`station_id`)
ON DELETE RESTRICT
ON UPDATE RESTRICT;

ALTER TABLE `recipient_list` ADD CONSTRAINT `FK_recipient_list_1` FOREIGN KEY 

`FK_recipient_list_1` (`station_id`)
REFERENCES `station` (`station_ID`)
ON DELETE RESTRICT
ON UPDATE RESTRICT;



-- block A7 -- within last 24 hours feature related --

insert into properties (keyStr,valueStr)
values ('nt.company.time.range.within.last','24');

insert into systemcomponents values (334,'Local PIRs And DPRs In Last 24 Hours','View all 

incoming incidents/claims within last 24 hours that are open',15,'incomingReports.do?

withinLast=24',1,9,1);

set @company = 'AD';
select (@admin_grp:=UserGroup_ID) from usergroup where description = 'Admin' and 

companycode_ID=@company LIMIT 1;

insert into group_component_policy (component_id,group_id) values (334, @admin_grp);



---- block A8 ---- local incoming incidents itemized feature

insert into systemcomponents values (331,'Incoming Incidents Type Delayed','View all incoming 

incidents/claims that are open - Delayed type only',15,'incomingReports.do?type=1',1,7,1);
insert into systemcomponents values (332,'Incoming Incidents Type Pilferage','View all 

incoming incidents/claims that are open - Pilferage type only',15,'incomingReports.do?

type=2',1,8,1);
insert into systemcomponents values (333,'Incoming Incidents Type Damaged','View all incoming 

incidents/claims that are open - Damaged type only',15,'incomingReports.do?type=3',1,9,1);

set @company = 'AD';
select (@admin_grp:=UserGroup_ID) from usergroup where description = 'Admin' and 

companycode_ID=@company LIMIT 1;
insert into group_component_policy (component_id,group_id) values (331, @admin_grp);
insert into group_component_policy (component_id,group_id) values (332, @admin_grp);
insert into group_component_policy (component_id,group_id) values (333, @admin_grp);

update systemcomponents set sort_order=6 where component_id=331;
update systemcomponents set sort_order=7 where component_id=332;
update systemcomponents set sort_order=8 where component_id=333;
update systemcomponents set sort_order=5 where component_id=90;

---- block A9


update systemcomponents
set sort_order = 10
where component_id = 31;

update systemcomponents
set sort_order = 11
where component_id = 27;

ALTER TABLE z_b6_claim_settlement ADD overall_weight double NOT NULL default 0;

--

CREATE TABLE pax_message_trigger (
  TRIGGER_KEY varchar(16) NOT NULL,
  email_content_text varchar(1020) DEFAULT NULL,
  LANGUAGE varchar(2) DEFAULT 'en',
  SMS_CONTENT_TEXT varchar(65) DEFAULT NULL,
  SUBJECT_LINE varchar(65) DEFAULT NULL,
  PRIMARY KEY (TRIGGER_KEY)
) ;



INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  

VALUES ('838', 'WS');

insert into properties (keyStr, valueStr) values ('custom.delay.receipt.files', 'fr');
insert into properties (keyStr, valueStr) values ('custom.damage.receipt.files', 'fr');
insert into properties (keyStr, valueStr) values ('custom.missing.receipt.files', 'fr');

update state set state='AA (Armed Forces Atlantic)' where state_id = 'APO AA' or state_id = 

'AA';
update state set state='AE (Armed Forces Europe)' where state_id = 'APO AE' or state_id = 

'AE';
update state set state='AP (Armed Forces Pacific)' where state_id = 'APO AP' or state_id = 

'AP';

update systemcomponents set parent_component_id = 39 where component_id = 402;

alter table expensepayout modify incident_ID varchar(13) NULL;

update systemcomponents set display = 1 where component_id = 316;

ALTER TABLE incident_control ADD UNIQUE (incident_id);

-- ALTER TABLE item_bdo DROP FOREIGN KEY 'FK463465411E79EFAE',
-- DROP FOREIGN KEY 'FK463465413E7F1546';

insert into properties (keyStr, valueStr) values ('bdo.cancle.email.alert', '0');

update systemcomponents set component_action_link = null where component_id = 407;


-- upgrade from 2.0 to 2.1

---- block B1 - dispute resolution ----

insert into systemcomponents values (351,'Dispute Fault Code','To initiate the dispute process 

of a closed incident',15,'disputeFault.do',0,99,1);
insert into systemcomponents values (352,'Manage Fault Dispute','To manage the dispute process 

of a closed incident',15,'searchDispute.do?actionType=manage',1,69,4);
insert into systemcomponents values (369,'Teletype Report','To enable the Print to Teletype 

feature',15,'teletype.do',0,99,1);

insert into systemcomponents (component_id, component_name, component_desc, 

parent_component_id, component_action_link, display, sort_order, sort_group) VALUES (501, 

'View AHL', 'View WorldTracer AHL', 93, 'worldtracersearch.do?ahl=1', 1, 15, 0);
insert into systemcomponents (component_id, component_name, component_desc, 

parent_component_id, component_action_link, display, sort_order, sort_group) VALUES (502, 

'View OHD', 'View WorldTracer OHD', 93, 'worldtracersearch.do?ohd=1', 1, 16, 0);
insert into systemcomponents (component_id, component_name, component_desc, 

parent_component_id, component_action_link, display, sort_order, sort_group) values (503, 

'BagBuzz', 'List of BagBuzz', 15, 'bagbuzzsearch.do', 1, 23, 4);
insert into systemcomponents (component_id, component_name, component_desc, 

parent_component_id, component_action_link, display, sort_order, sort_group) values (504, 

'BagBuzzAdmin', 'BagBuzz Admin', 39, '', 0, 0, 0);
insert into systemcomponents (component_id, component_name, component_desc, 

parent_component_id, component_action_link, display, sort_order, sort_group) values (410, 

'Clerical Claims Features', 'Initial review of claims', 15, 'displayClaimsList.do?

claimStatus=SUBMITTED', 1, 25, 3);
insert into systemcomponents (component_id, component_name, component_desc, 

parent_component_id, component_action_link, display, sort_order, sort_group) values (411, 

'Central Baggage Claims Features', 'CBRO view of claims', 15, 'displayClaimsList.do?

claimStatus=REVIEW', 1, 26, 3);
	
set @company = 'AD';
select (@admin_grp:=UserGroup_ID) from usergroup where description = 'Admin' and 

companycode_ID=@company LIMIT 1;
insert into group_component_policy (component_id,group_id) values (351, @admin_grp);
insert into group_component_policy (component_id,group_id) values (352, @admin_grp);
insert into group_component_policy (component_id,group_id) values (369, @admin_grp);
insert into group_component_policy (component_id,group_id) values (501, @admin_grp);
insert into group_component_policy (component_id,group_id) values (502, @admin_grp);
insert into group_component_policy (component_id,group_id) values (503, @admin_grp);
insert into group_component_policy (component_id,group_id) values (504, @admin_grp);

update systemcomponents set sort_group = 4 and parent_component_id = 6 where component_id in 

(351,369);


drop table if exists dispute;
    
create table dispute (
    dispute_res_id bigint not null auto_increment,
    beforeDisputeLossCode integer not null,
    created_timestamp datetime,
    determinedLossCode integer not null,
    disputeExplanation varchar(255),
    resolutionRemarks varchar(255),
    suggestedLossCode integer not null,
    typeOfChange varchar(255),
    before_dispute_fault_station_ID integer not null,
    determined_station_ID integer not null,
    dispute_agent_ID integer not null,
    Incident_ID varchar(13) not null,
    resolution_agent_ID integer not null,
    status_ID integer not null,
    suggested_station_ID integer not null,
    primary key (dispute_res_id)
);
    
drop table if exists task;

create table task (
    task_type varchar(16) not null,
    task_id bigint not null auto_increment,
    closed_timestamp datetime,
    opened_timestamp datetime,
    agent_ID integer not null,
    status_ID integer not null,
    bagbuzz_id bigint,
    dispute_res_id bigint,
    primary key (task_id)
);

    
alter table dispute 
    add index FK63A53CC45811E074 (determined_station_ID), 
    add constraint FK63A53CC45811E074 
    foreign key (determined_station_ID) 
    references Station (Station_ID);

alter table dispute 
    add index FK63A53CC4B7E34082 (suggested_station_ID), 
    add constraint FK63A53CC4B7E34082 
    foreign key (suggested_station_ID) 
    references Station (Station_ID);

alter table dispute 
    add index FK63A53CC47D0B6A4E (status_ID), 
    add constraint FK63A53CC47D0B6A4E 
    foreign key (status_ID) 
    references Status (Status_ID);

-- alter table dispute 
    --add index FK63A53CC4AB4E994E (Incident_ID), 
    --add constraint FK63A53CC4AB4E994E 
    --foreign key (Incident_ID) 
    --references Incident (Incident_ID);

alter table dispute 
    add index FK63A53CC4EBC88861 (dispute_agent_ID), 
    add constraint FK63A53CC4EBC88861 
    foreign key (dispute_agent_ID) 
    references Agent (Agent_ID);

alter table dispute 
    add index FK63A53CC4D9ED3179 (resolution_agent_ID), 
    add constraint FK63A53CC4D9ED3179 
    foreign key (resolution_agent_ID) 
    references Agent (Agent_ID);

alter table dispute 
    add index FK63A53CC46224E2FE (before_dispute_fault_station_ID), 
    add constraint FK63A53CC46224E2FE 
    foreign key (before_dispute_fault_station_ID) 
    references Station (Station_ID);




alter table task 
    add index FK363585952A03ED (dispute_res_id), 
    add constraint FK363585952A03ED 
    foreign key (dispute_res_id) 
    references dispute (dispute_res_id);

alter table task 
    add index FK363585C1AA7746 (agent_ID), 
    add constraint FK363585C1AA7746 
    foreign key (agent_ID) 
    references Agent (Agent_ID);

--alter table task 
--    add index FK3635857CD09C93 (bagbuzz_id), 
--    add constraint FK3635857CD09C93 
--    foreign key (bagbuzz_id) 
--    references bagbuzz (bagbuzz_id);

alter table task 
    add index FK3635857D0B6A4E (status_ID), 
    add constraint FK3635857D0B6A4E 
    foreign key (status_ID) 
    references Status (Status_ID);



ALTER TABLE Incident ADD locked bit NOT NULL DEFAULT false;

insert into report (number,resource_key) values (55,'header.customreportnum.55');

ALTER TABLE Audit_Incident ADD locked bit NOT NULL DEFAULT false;

---- block B2 - bagbuzz related ----

delete from status where table_id = 13;
delete from status where table_id = 14;
insert into status (status_id,description,table_id) values (83,'open',13);
insert into status (status_id,description,table_id) values (84,'processed',13);
insert into status (status_id,description,table_id) values (85,'closed',13);
insert into status (status_id,description,table_id) values (86,'new',14);
insert into status (status_id,description,table_id) values (87,'published',14);
insert into status (status_id,description,table_id) values (88,'deleted',14);
insert into status (status_id,description,table_id) values (95,'unpublish',14);
insert into status (status_id,description,table_id) values (90,'Denied',15);
insert into status (status_id,description,table_id) values (91,'Open',15);
insert into status (status_id,description,table_id) values (92,'Approved',15);
insert into status (status_id,description,table_id) values (93,'Manual Change',15);



drop table if exists bagbuzz;

create table bagbuzz (
    bagbuzz_id bigint not null auto_increment,
    created_timestamp datetime,
    data text,
    description varchar(30),
    status_ID integer not null,
    primary key (bagbuzz_id)
);
	


update systemcomponents set display=1 where component_name = 'WorldTracer ROH';
update systemcomponents set display=0 where component_name = 'Query Reports';
insert into properties (keyStr, valueStr) values ('bag.itin.not.required', '0');

alter table wt_queue modify column furtherInfo varchar(3000);
alter table wt_queue add column fwdTagNum varchar(20);

alter table incident add column oc_claim_id bigint not null default 0;
---- update incident set oc_claim_id = 0;

alter table BDO add column lastDeliveryUpdate datetime;
alter table BDO add column deliveryStatus varchar(25);


delete from countrycode where countrycode_ID = 'YU';
update address set countrycode_Id = 'RS' where countrycode_id = 'YU';
update audit_address set countrycode_Id = 'RS' where countrycode_id = 'YU';
update ohd_address set countrycode_Id = 'RS' where countrycode_id = 'YU';
update audit_ohd_address set countrycode_Id = 'RS' where countrycode_id = 'YU';


ALTER TABLE oc_claim ADD COLUMN (
        paxClaimAmount varchar(255),
        paxClaimDate varchar(255),
        paxIpAddress varchar(255));      
        
alter table oc_bag modify column brand varchar(20);

ALTER TABLE oc_content modify COLUMN male integer;
UPDATE oc_content SET male = 2 WHERE male = 0;

create table oc_passenger (
id int(11) not null auto_increment,
claimId int(11),
accept varchar(20),
firstName varchar(20),
lastName varchar(20),
middleInitial varchar(1),
salutation varchar(10),
primary key (id) );

insert into oc_passenger select NULL, claimId, accept, firstName, lastName, middleInitial, '' 

from oc_claim;

alter table oc_claim drop column firstName;
alter table oc_claim drop column lastName;
alter table oc_claim drop column middleInitial;
alter table oc_claim drop column accept;

alter table oc_claim add column bagWeight varchar(20);
alter table oc_claim add column paxClaimAmountCurrency varchar(3);
alter table oc_claim add column checkedLocationDescription varchar(20);

alter table oc_bag add column bagOwner varchar(50);
alter table oc_bag add column bagCurrency varchar(3);
alter table oc_bag add column bagValue varchar(20);

alter table oc_content add column contentOwner varchar(50);

alter table audit_incident add column oc_claim_id bigint(20);
        
alter table oc_bag add column leather bit(1);
alter table oc_bag add column metal bit(1);
alter table oc_bag add column trimDescription varchar(50);
alter table oc_file add column interim bit(1);
alter table oc_claim add column reportedAirline varchar(2);
alter table oc_claim add column reportedCity varchar(20);
alter table oc_claim add column reportedFileNumber varchar(20);
alter table oc_claim add column privateInsurance bit(1);
alter table oc_claim add column privateInsuranceName varchar(20);
alter table oc_claim add column privateInsuranceAddr varchar(100);
update oc_claim set privateInsurance = 0;
update oc_bag set leather = 0, metal = 0;
update oc_file set interim = 0;

alter table oc_bag modify column brand varchar(25);

alter table oc_file modify column filename varchar(100);


-------------- 2.1 to 3.0 ------------


alter table incident add column revenue_code varchar(4);
alter table audit_incident add column revenue_code varchar(4);

insert into properties (keyStr, valueStr) values ('non.revenue.codes','');
insert into properties (keyStr, valueStr) values ('display.non.revenue.codes','0');
insert into properties (keyStr, valueStr) values ('ocs.email','notice@nettracer.aero');


---- SALVAGE ----

create table salvage (
    salvage_id integer not null auto_increment,
    companycode_id varchar(2),
    pickedupby_fname varchar(30),
    pickedupby_lname varchar(30),
    salvage_date datetime,
    status integer,
    primary key (salvage_id)
);

create table salvage_box (
    box_id integer not null auto_increment,
    description varchar(255),
    display_id integer not null,
    type integer,
    salvage_id integer not null,
    primary key (box_id)
);

create table salvage_item (
    item_id integer not null auto_increment,
    description varchar(255),
    lostandfound_id varchar(13),
    quantity integer,
    type integer,
    box_id integer not null,
    primary key (item_id)
);

create table salvage_ohd_reference (
    ohd_ref_id integer not null auto_increment,
    ohd_id varchar(13) not null,
    salvage_id integer not null,
    primary key (ohd_ref_id)
);

insert into systemcomponents (component_id, component_name, component_desc, 

parent_component_id, component_action_link, display, sort_order, sort_group) VALUES
(601, 'Salvage', 'Maintain Salvage', 39, 'salvageSearch.do', 1, 16, 0);



set @company = 'AD';
select (@admin_grp:=UserGroup_ID) from usergroup where description = 'AD IT Admin' and 

companycode_ID=@company LIMIT 1;
insert into group_component_policy (component_id,group_id) values (601, @admin_grp);

alter table dispute add column resolution_timestamp datetime;

alter table station add column priority int not null default 5;
update station set priority=4 where companycode_ID = 'US' and stationcode in ('CDG','TLV','LGW','MUC','OSL','VCE','ATH','FRA','MAD','DUB','AMS','ZRH','GLA','FCO','MAN','LHR','BRU','LIS','BCN');



alter table oc_bag modify column brand varchar(20);

alter table address add column homephone_norm varchar(50);
alter table address add column workphone_norm varchar(50);
alter table address add column mobile_norm varchar(50);
alter table address add column pager_norm varchar(50);
alter table address add column altphone_norm varchar(50);

update address SET
homephone_norm = homephone,
workphone_norm = workphone,
mobile_norm = mobile,
pager_norm = pager,
altphone_norm = altphone;

--BELOW REMOVES ALL RESTRICTED CHARACTERS FROM THE NORMALIZED FIELDS--
update address SET
homephone_norm = REPLACE(homephone_norm, 'A', ''),
workphone_norm = REPLACE(workphone_norm, 'A', ''),
mobile_norm =       REPLACE(mobile_norm, 'A', ''),
pager_norm =         REPLACE(pager_norm, 'A', ''),
altphone_norm =   REPLACE(altphone_norm, 'A', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'B', ''),
workphone_norm = REPLACE(workphone_norm, 'B', ''),
mobile_norm =       REPLACE(mobile_norm, 'B', ''),
pager_norm =         REPLACE(pager_norm, 'B', ''),
altphone_norm =   REPLACE(altphone_norm, 'B', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'C', ''),
workphone_norm = REPLACE(workphone_norm, 'C', ''),
mobile_norm =       REPLACE(mobile_norm, 'C', ''),
pager_norm =         REPLACE(pager_norm, 'C', ''),
altphone_norm =   REPLACE(altphone_norm, 'C', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'D', ''),
workphone_norm = REPLACE(workphone_norm, 'D', ''),
mobile_norm =       REPLACE(mobile_norm, 'D', ''),
pager_norm =         REPLACE(pager_norm, 'D', ''),
altphone_norm =   REPLACE(altphone_norm, 'D', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'E', ''),
workphone_norm = REPLACE(workphone_norm, 'E', ''),
mobile_norm =       REPLACE(mobile_norm, 'E', ''),
pager_norm =         REPLACE(pager_norm, 'E', ''),
altphone_norm =   REPLACE(altphone_norm, 'E', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'F', ''),
workphone_norm = REPLACE(workphone_norm, 'F', ''),
mobile_norm =       REPLACE(mobile_norm, 'F', ''),
pager_norm =         REPLACE(pager_norm, 'F', ''),
altphone_norm =   REPLACE(altphone_norm, 'F', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'G', ''),
workphone_norm = REPLACE(workphone_norm, 'G', ''),
mobile_norm =       REPLACE(mobile_norm, 'G', ''),
pager_norm =         REPLACE(pager_norm, 'G', ''),
altphone_norm =   REPLACE(altphone_norm, 'G', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'H', ''),
workphone_norm = REPLACE(workphone_norm, 'H', ''),
mobile_norm =       REPLACE(mobile_norm, 'H', ''),
pager_norm =         REPLACE(pager_norm, 'H', ''),
altphone_norm =   REPLACE(altphone_norm, 'H', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'I', ''),
workphone_norm = REPLACE(workphone_norm, 'I', ''),
mobile_norm =       REPLACE(mobile_norm, 'I', ''),
pager_norm =         REPLACE(pager_norm, 'I', ''),
altphone_norm =   REPLACE(altphone_norm, 'I', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'J', ''),
workphone_norm = REPLACE(workphone_norm, 'J', ''),
mobile_norm =       REPLACE(mobile_norm, 'J', ''),
pager_norm =         REPLACE(pager_norm, 'J', ''),
altphone_norm =   REPLACE(altphone_norm, 'J', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'K', ''),
workphone_norm = REPLACE(workphone_norm, 'K', ''),
mobile_norm =       REPLACE(mobile_norm, 'K', ''),
pager_norm =         REPLACE(pager_norm, 'K', ''),
altphone_norm =   REPLACE(altphone_norm, 'K', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'L', ''),
workphone_norm = REPLACE(workphone_norm, 'L', ''),
mobile_norm =       REPLACE(mobile_norm, 'L', ''),
pager_norm =         REPLACE(pager_norm, 'L', ''),
altphone_norm =   REPLACE(altphone_norm, 'L', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'M', ''),
workphone_norm = REPLACE(workphone_norm, 'M', ''),
mobile_norm =       REPLACE(mobile_norm, 'M', ''),
pager_norm =         REPLACE(pager_norm, 'M', ''),
altphone_norm =   REPLACE(altphone_norm, 'M', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'N', ''),
workphone_norm = REPLACE(workphone_norm, 'N', ''),
mobile_norm =       REPLACE(mobile_norm, 'N', ''),
pager_norm =         REPLACE(pager_norm, 'N', ''),
altphone_norm =   REPLACE(altphone_norm, 'N', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'O', ''),
workphone_norm = REPLACE(workphone_norm, 'O', ''),
mobile_norm =       REPLACE(mobile_norm, 'O', ''),
pager_norm =         REPLACE(pager_norm, 'O', ''),
altphone_norm =   REPLACE(altphone_norm, 'O', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'P', ''),
workphone_norm = REPLACE(workphone_norm, 'P', ''),
mobile_norm =       REPLACE(mobile_norm, 'P', ''),
pager_norm =         REPLACE(pager_norm, 'P', ''),
altphone_norm =   REPLACE(altphone_norm, 'P', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'Q', ''),
workphone_norm = REPLACE(workphone_norm, 'Q', ''),
mobile_norm =       REPLACE(mobile_norm, 'Q', ''),
pager_norm =         REPLACE(pager_norm, 'Q', ''),
altphone_norm =   REPLACE(altphone_norm, 'Q', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'R', ''),
workphone_norm = REPLACE(workphone_norm, 'R', ''),
mobile_norm =       REPLACE(mobile_norm, 'R', ''),
pager_norm =         REPLACE(pager_norm, 'R', ''),
altphone_norm =   REPLACE(altphone_norm, 'R', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'S', ''),
workphone_norm = REPLACE(workphone_norm, 'S', ''),
mobile_norm =       REPLACE(mobile_norm, 'S', ''),
pager_norm =         REPLACE(pager_norm, 'S', ''),
altphone_norm =   REPLACE(altphone_norm, 'S', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'T', ''),
workphone_norm = REPLACE(workphone_norm, 'T', ''),
mobile_norm =       REPLACE(mobile_norm, 'T', ''),
pager_norm =         REPLACE(pager_norm, 'T', ''),
altphone_norm =   REPLACE(altphone_norm, 'T', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'U', ''),
workphone_norm = REPLACE(workphone_norm, 'U', ''),
mobile_norm =       REPLACE(mobile_norm, 'U', ''),
pager_norm =         REPLACE(pager_norm, 'U', ''),
altphone_norm =   REPLACE(altphone_norm, 'U', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'V', ''),
workphone_norm = REPLACE(workphone_norm, 'V', ''),
mobile_norm =       REPLACE(mobile_norm, 'V', ''),
pager_norm =         REPLACE(pager_norm, 'V', ''),
altphone_norm =   REPLACE(altphone_norm, 'V', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'W', ''),
workphone_norm = REPLACE(workphone_norm, 'W', ''),
mobile_norm =       REPLACE(mobile_norm, 'W', ''),
pager_norm =         REPLACE(pager_norm, 'W', ''),
altphone_norm =   REPLACE(altphone_norm, 'W', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'X', ''),
workphone_norm = REPLACE(workphone_norm, 'X', ''),
mobile_norm =       REPLACE(mobile_norm, 'X', ''),
pager_norm =         REPLACE(pager_norm, 'X', ''),
altphone_norm =   REPLACE(altphone_norm, 'X', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'Y', ''),
workphone_norm = REPLACE(workphone_norm, 'Y', ''),
mobile_norm =       REPLACE(mobile_norm, 'Y', ''),
pager_norm =         REPLACE(pager_norm, 'Y', ''),
altphone_norm =   REPLACE(altphone_norm, 'Y', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'Z', ''),
workphone_norm = REPLACE(workphone_norm, 'Z', ''),
mobile_norm =       REPLACE(mobile_norm, 'Z', ''),
pager_norm =         REPLACE(pager_norm, 'Z', ''),
altphone_norm =   REPLACE(altphone_norm, 'Z', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'a', ''),
workphone_norm = REPLACE(workphone_norm, 'a', ''),
mobile_norm =       REPLACE(mobile_norm, 'a', ''),
pager_norm =         REPLACE(pager_norm, 'a', ''),
altphone_norm =   REPLACE(altphone_norm, 'a', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'b', ''),
workphone_norm = REPLACE(workphone_norm, 'b', ''),
mobile_norm =       REPLACE(mobile_norm, 'b', ''),
pager_norm =         REPLACE(pager_norm, 'b', ''),
altphone_norm =   REPLACE(altphone_norm, 'b', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'c', ''),
workphone_norm = REPLACE(workphone_norm, 'c', ''),
mobile_norm =       REPLACE(mobile_norm, 'c', ''),
pager_norm =         REPLACE(pager_norm, 'c', ''),
altphone_norm =   REPLACE(altphone_norm, 'c', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'd', ''),
workphone_norm = REPLACE(workphone_norm, 'd', ''),
mobile_norm =       REPLACE(mobile_norm, 'd', ''),
pager_norm =         REPLACE(pager_norm, 'd', ''),
altphone_norm =   REPLACE(altphone_norm, 'd', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'e', ''),
workphone_norm = REPLACE(workphone_norm, 'e', ''),
mobile_norm =       REPLACE(mobile_norm, 'e', ''),
pager_norm =         REPLACE(pager_norm, 'e', ''),
altphone_norm =   REPLACE(altphone_norm, 'e', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'f', ''),
workphone_norm = REPLACE(workphone_norm, 'f', ''),
mobile_norm =       REPLACE(mobile_norm, 'f', ''),
pager_norm =         REPLACE(pager_norm, 'f', ''),
altphone_norm =   REPLACE(altphone_norm, 'f', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'g', ''),
workphone_norm = REPLACE(workphone_norm, 'g', ''),
mobile_norm =       REPLACE(mobile_norm, 'g', ''),
pager_norm =         REPLACE(pager_norm, 'g', ''),
altphone_norm =   REPLACE(altphone_norm, 'g', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'h', ''),
workphone_norm = REPLACE(workphone_norm, 'h', ''),
mobile_norm =       REPLACE(mobile_norm, 'h', ''),
pager_norm =         REPLACE(pager_norm, 'h', ''),
altphone_norm =   REPLACE(altphone_norm, 'h', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'i', ''),
workphone_norm = REPLACE(workphone_norm, 'i', ''),
mobile_norm =       REPLACE(mobile_norm, 'i', ''),
pager_norm =         REPLACE(pager_norm, 'i', ''),
altphone_norm =   REPLACE(altphone_norm, 'i', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'j', ''),
workphone_norm = REPLACE(workphone_norm, 'j', ''),
mobile_norm =       REPLACE(mobile_norm, 'j', ''),
pager_norm =         REPLACE(pager_norm, 'j', ''),
altphone_norm =   REPLACE(altphone_norm, 'j', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'k', ''),
workphone_norm = REPLACE(workphone_norm, 'k', ''),
mobile_norm =       REPLACE(mobile_norm, 'k', ''),
pager_norm =         REPLACE(pager_norm, 'k', ''),
altphone_norm =   REPLACE(altphone_norm, 'k', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'l', ''),
workphone_norm = REPLACE(workphone_norm, 'l', ''),
mobile_norm =       REPLACE(mobile_norm, 'l', ''),
pager_norm =         REPLACE(pager_norm, 'l', ''),
altphone_norm =   REPLACE(altphone_norm, 'l', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'm', ''),
workphone_norm = REPLACE(workphone_norm, 'm', ''),
mobile_norm =       REPLACE(mobile_norm, 'm', ''),
pager_norm =         REPLACE(pager_norm, 'm', ''),
altphone_norm =   REPLACE(altphone_norm, 'm', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'n', ''),
workphone_norm = REPLACE(workphone_norm, 'n', ''),
mobile_norm =       REPLACE(mobile_norm, 'n', ''),
pager_norm =         REPLACE(pager_norm, 'n', ''),
altphone_norm =   REPLACE(altphone_norm, 'n', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'o', ''),
workphone_norm = REPLACE(workphone_norm, 'o', ''),
mobile_norm =       REPLACE(mobile_norm, 'o', ''),
pager_norm =         REPLACE(pager_norm, 'o', ''),
altphone_norm =   REPLACE(altphone_norm, 'o', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'p', ''),
workphone_norm = REPLACE(workphone_norm, 'p', ''),
mobile_norm =       REPLACE(mobile_norm, 'p', ''),
pager_norm =         REPLACE(pager_norm, 'p', ''),
altphone_norm =   REPLACE(altphone_norm, 'p', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'q', ''),
workphone_norm = REPLACE(workphone_norm, 'q', ''),
mobile_norm =       REPLACE(mobile_norm, 'q', ''),
pager_norm =         REPLACE(pager_norm, 'q', ''),
altphone_norm =   REPLACE(altphone_norm, 'q', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'r', ''),
workphone_norm = REPLACE(workphone_norm, 'r', ''),
mobile_norm =       REPLACE(mobile_norm, 'r', ''),
pager_norm =         REPLACE(pager_norm, 'r', ''),
altphone_norm =   REPLACE(altphone_norm, 'r', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 's', ''),
workphone_norm = REPLACE(workphone_norm, 's', ''),
mobile_norm =       REPLACE(mobile_norm, 's', ''),
pager_norm =         REPLACE(pager_norm, 's', ''),
altphone_norm =   REPLACE(altphone_norm, 's', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 't', ''),
workphone_norm = REPLACE(workphone_norm, 't', ''),
mobile_norm =       REPLACE(mobile_norm, 't', ''),
pager_norm =         REPLACE(pager_norm, 't', ''),
altphone_norm =   REPLACE(altphone_norm, 't', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'u', ''),
workphone_norm = REPLACE(workphone_norm, 'u', ''),
mobile_norm =       REPLACE(mobile_norm, 'u', ''),
pager_norm =         REPLACE(pager_norm, 'u', ''),
altphone_norm =   REPLACE(altphone_norm, 'u', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'v', ''),
workphone_norm = REPLACE(workphone_norm, 'v', ''),
mobile_norm =       REPLACE(mobile_norm, 'v', ''),
pager_norm =         REPLACE(pager_norm, 'v', ''),
altphone_norm =   REPLACE(altphone_norm, 'v', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'w', ''),
workphone_norm = REPLACE(workphone_norm, 'w', ''),
mobile_norm =       REPLACE(mobile_norm, 'w', ''),
pager_norm =         REPLACE(pager_norm, 'w', ''),
altphone_norm =   REPLACE(altphone_norm, 'w', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'x', ''),
workphone_norm = REPLACE(workphone_norm, 'x', ''),
mobile_norm =       REPLACE(mobile_norm, 'x', ''),
pager_norm =         REPLACE(pager_norm, 'x', ''),
altphone_norm =   REPLACE(altphone_norm, 'x', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'y', ''),
workphone_norm = REPLACE(workphone_norm, 'y', ''),
mobile_norm =       REPLACE(mobile_norm, 'y', ''),
pager_norm =         REPLACE(pager_norm, 'y', ''),
altphone_norm =   REPLACE(altphone_norm, 'y', '');
update address SET
homephone_norm = REPLACE(homephone_norm, 'z', ''),
workphone_norm = REPLACE(workphone_norm, 'z', ''),
mobile_norm =       REPLACE(mobile_norm, 'z', ''),
pager_norm =         REPLACE(pager_norm, 'z', ''),
altphone_norm =   REPLACE(altphone_norm, 'z', '');
update address SET
homephone_norm = REPLACE(homephone_norm, ';', ''),
workphone_norm = REPLACE(workphone_norm, ';', ''),
mobile_norm =       REPLACE(mobile_norm, ';', ''),
pager_norm =         REPLACE(pager_norm, ';', ''),
altphone_norm =   REPLACE(altphone_norm, ';', '');
update address SET
homephone_norm = REPLACE(homephone_norm, ',', ''),
workphone_norm = REPLACE(workphone_norm, ',', ''),
mobile_norm =       REPLACE(mobile_norm, ',', ''),
pager_norm =         REPLACE(pager_norm, ',', ''),
altphone_norm =   REPLACE(altphone_norm, ',', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '<', ''),
workphone_norm = REPLACE(workphone_norm, '<', ''),
mobile_norm =       REPLACE(mobile_norm, '<', ''),
pager_norm =         REPLACE(pager_norm, '<', ''),
altphone_norm =   REPLACE(altphone_norm, '<', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '.', ''),
workphone_norm = REPLACE(workphone_norm, '.', ''),
mobile_norm =       REPLACE(mobile_norm, '.', ''),
pager_norm =         REPLACE(pager_norm, '.', ''),
altphone_norm =   REPLACE(altphone_norm, '.', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '>', ''),
workphone_norm = REPLACE(workphone_norm, '>', ''),
mobile_norm =       REPLACE(mobile_norm, '>', ''),
pager_norm =         REPLACE(pager_norm, '>', ''),
altphone_norm =   REPLACE(altphone_norm, '>', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '/', ''),
workphone_norm = REPLACE(workphone_norm, '/', ''),
mobile_norm =       REPLACE(mobile_norm, '/', ''),
pager_norm =         REPLACE(pager_norm, '/', ''),
altphone_norm =   REPLACE(altphone_norm, '/', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '?', ''),
workphone_norm = REPLACE(workphone_norm, '?', ''),
mobile_norm =       REPLACE(mobile_norm, '?', ''),
pager_norm =         REPLACE(pager_norm, '?', ''),
altphone_norm =   REPLACE(altphone_norm, '?', '');
update address SET
homephone_norm = REPLACE(homephone_norm, ':', ''),
workphone_norm = REPLACE(workphone_norm, ':', ''),
mobile_norm =       REPLACE(mobile_norm, ':', ''),
pager_norm =         REPLACE(pager_norm, ':', ''),
altphone_norm =   REPLACE(altphone_norm, ':', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '\'', ''),
workphone_norm = REPLACE(workphone_norm, '\'', ''),
mobile_norm =       REPLACE(mobile_norm, '\'', ''),
pager_norm =         REPLACE(pager_norm, '\'', ''),
altphone_norm =   REPLACE(altphone_norm, '\'', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '"', ''),
workphone_norm = REPLACE(workphone_norm, '"', ''),
mobile_norm =       REPLACE(mobile_norm, '"', ''),
pager_norm =         REPLACE(pager_norm, '"', ''),
altphone_norm =   REPLACE(altphone_norm, '"', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '[', ''),
workphone_norm = REPLACE(workphone_norm, '[', ''),
mobile_norm =       REPLACE(mobile_norm, '[', ''),
pager_norm =         REPLACE(pager_norm, '[', ''),
altphone_norm =   REPLACE(altphone_norm, '[', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '{', ''),
workphone_norm = REPLACE(workphone_norm, '{', ''),
mobile_norm =       REPLACE(mobile_norm, '{', ''),
pager_norm =         REPLACE(pager_norm, '{', ''),
altphone_norm =   REPLACE(altphone_norm, '{', '');
update address SET
homephone_norm = REPLACE(homephone_norm, ']', ''),
workphone_norm = REPLACE(workphone_norm, ']', ''),
mobile_norm =       REPLACE(mobile_norm, ']', ''),
pager_norm =         REPLACE(pager_norm, ']', ''),
altphone_norm =   REPLACE(altphone_norm, ']', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '}', ''),
workphone_norm = REPLACE(workphone_norm, '}', ''),
mobile_norm =       REPLACE(mobile_norm, '}', ''),
pager_norm =         REPLACE(pager_norm, '}', ''),
altphone_norm =   REPLACE(altphone_norm, '}', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '\\', ''),
workphone_norm = REPLACE(workphone_norm, '\\', ''),
mobile_norm =       REPLACE(mobile_norm, '\\', ''),
pager_norm =         REPLACE(pager_norm, '\\', ''),
altphone_norm =   REPLACE(altphone_norm, '\\', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '|', ''),
workphone_norm = REPLACE(workphone_norm, '|', ''),
mobile_norm =       REPLACE(mobile_norm, '|', ''),
pager_norm =         REPLACE(pager_norm, '|', ''),
altphone_norm =   REPLACE(altphone_norm, '|', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '=', ''),
workphone_norm = REPLACE(workphone_norm, '=', ''),
mobile_norm =       REPLACE(mobile_norm, '=', ''),
pager_norm =         REPLACE(pager_norm, '=', ''),
altphone_norm =   REPLACE(altphone_norm, '=', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '+', ''),
workphone_norm = REPLACE(workphone_norm, '+', ''),
mobile_norm =       REPLACE(mobile_norm, '+', ''),
pager_norm =         REPLACE(pager_norm, '+', ''),
altphone_norm =   REPLACE(altphone_norm, '+', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '-', ''),
workphone_norm = REPLACE(workphone_norm, '-', ''),
mobile_norm =       REPLACE(mobile_norm, '-', ''),
pager_norm =         REPLACE(pager_norm, '-', ''),
altphone_norm =   REPLACE(altphone_norm, '-', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '_', ''),
workphone_norm = REPLACE(workphone_norm, '_', ''),
mobile_norm =       REPLACE(mobile_norm, '_', ''),
pager_norm =         REPLACE(pager_norm, '_', ''),
altphone_norm =   REPLACE(altphone_norm, '_', '');
update address SET
homephone_norm = REPLACE(homephone_norm, ')', ''),
workphone_norm = REPLACE(workphone_norm, ')', ''),
mobile_norm =       REPLACE(mobile_norm, ')', ''),
pager_norm =         REPLACE(pager_norm, ')', ''),
altphone_norm =   REPLACE(altphone_norm, ')', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '(', ''),
workphone_norm = REPLACE(workphone_norm, '(', ''),
mobile_norm =       REPLACE(mobile_norm, '(', ''),
pager_norm =         REPLACE(pager_norm, '(', ''),
altphone_norm =   REPLACE(altphone_norm, '(', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '*', ''),
workphone_norm = REPLACE(workphone_norm, '*', ''),
mobile_norm =       REPLACE(mobile_norm, '*', ''),
pager_norm =         REPLACE(pager_norm, '*', ''),
altphone_norm =   REPLACE(altphone_norm, '*', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '&', ''),
workphone_norm = REPLACE(workphone_norm, '&', ''),
mobile_norm =       REPLACE(mobile_norm, '&', ''),
pager_norm =         REPLACE(pager_norm, '&', ''),
altphone_norm =   REPLACE(altphone_norm, '&', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '^', ''),
workphone_norm = REPLACE(workphone_norm, '^', ''),
mobile_norm =       REPLACE(mobile_norm, '^', ''),
pager_norm =         REPLACE(pager_norm, '^', ''),
altphone_norm =   REPLACE(altphone_norm, '^', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '%', ''),
workphone_norm = REPLACE(workphone_norm, '%', ''),
mobile_norm =       REPLACE(mobile_norm, '%', ''),
pager_norm =         REPLACE(pager_norm, '%', ''),
altphone_norm =   REPLACE(altphone_norm, '%', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '$', ''),
workphone_norm = REPLACE(workphone_norm, '$', ''),
mobile_norm =       REPLACE(mobile_norm, '$', ''),
pager_norm =         REPLACE(pager_norm, '$', ''),
altphone_norm =   REPLACE(altphone_norm, '$', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '#', ''),
workphone_norm = REPLACE(workphone_norm, '#', ''),
mobile_norm =       REPLACE(mobile_norm, '#', ''),
pager_norm =         REPLACE(pager_norm, '#', ''),
altphone_norm =   REPLACE(altphone_norm, '#', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '@', ''),
workphone_norm = REPLACE(workphone_norm, '@', ''),
mobile_norm =       REPLACE(mobile_norm, '@', ''),
pager_norm =         REPLACE(pager_norm, '@', ''),
altphone_norm =   REPLACE(altphone_norm, '@', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '!', ''),
workphone_norm = REPLACE(workphone_norm, '!', ''),
mobile_norm =       REPLACE(mobile_norm, '!', ''),
pager_norm =         REPLACE(pager_norm, '!', ''),
altphone_norm =   REPLACE(altphone_norm, '!', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '`', ''),
workphone_norm = REPLACE(workphone_norm, '`', ''),
mobile_norm =       REPLACE(mobile_norm, '`', ''),
pager_norm =         REPLACE(pager_norm, '`', ''),
altphone_norm =   REPLACE(altphone_norm, '`', '');
update address SET
homephone_norm = REPLACE(homephone_norm, '~', ''),
workphone_norm = REPLACE(workphone_norm, '~', ''),
mobile_norm =       REPLACE(mobile_norm, '~', ''),
pager_norm =         REPLACE(pager_norm, '~', ''),
altphone_norm =   REPLACE(altphone_norm, '~', '');
update address SET
homephone_norm = REPLACE(homephone_norm, ' ', ''),
workphone_norm = REPLACE(workphone_norm, ' ', ''),
mobile_norm =       REPLACE(mobile_norm, ' ', ''),
pager_norm =         REPLACE(pager_norm, ' ', ''),
altphone_norm =   REPLACE(altphone_norm, ' ', '');

insert into report (number,resource_key) VALUES (81,'header.customreportnum.81');
insert into report (number,resource_key) VALUES (82,'header.customreportnum.82');
insert into report (number,resource_key) VALUES (83,'header.customreportnum.83');
insert into report (number,resource_key) VALUES (84,'header.customreportnum.84');
insert into report (number,resource_key) VALUES (85,'header.customreportnum.85');


create table Blacklist (
    id bigint not null auto_increment,
    description varchar(255),
    primary key (id)
);

create table InternalSummary (
    id bigint not null auto_increment,
    description varchar(255),
    claim_id bigint,
    primary key (id)
);

create table Whitelist (
    id bigint not null auto_increment,
    description varchar(255),
    primary key (id)
);

create table FsAddress (
    id bigint not null auto_increment,
    address1 varchar(255),
    address2 varchar(255),
    city varchar(255),
    country varchar(255),
    lattitude double precision not null,
    longitude double precision not null,
    province varchar(255),
    state varchar(255),
    zip varchar(255),
    person_id bigint,
    reservation_id bigint,
    primary key (id)
);


create table FsIncident (
    id bigint not null auto_increment,
    airline varchar(255),
    airlineIncidentId varchar(255) unique,
    incidentCreated datetime,
    incidentDescription varchar(255),
    incidentType integer not null,
    itinComplexity integer not null,
    numberDaysOpen integer not null,
    numberOfBdos integer not null,
    remarks varchar(255),
    swapId bigint not null,
    timestampClosed datetime,
    timestampOpen datetime,
    claim_id bigint,
    file_id bigint,
    primary key (id)
);

create table Bag (
    id bigint not null auto_increment,
    bagColor varchar(255),
    bagType varchar(255),
    description varchar(255),
    manufacturer varchar(255),
    incident_id bigint,
    primary key (id)
);

create table Person (
        id bigint not null auto_increment,
        ccContact bit not null,
        dateOfBirth datetime,
        description varchar(255),
        driversLicenseCountry varchar(255),
        driversLicenseIssuer varchar(255),
        driversLicenseNumber varchar(255),
        emailAddress varchar(255),
        ffAirline varchar(255),
        ffNumber varchar(255),
        firstName varchar(255),
        firstNameDmp varchar(255),
        firstNameSoundex varchar(255),
        lastName varchar(255),
        lastNameDmp varchar(255),
        lastNameSoundex varchar(255),
        middleName varchar(255),
        passportIssuer varchar(255),
        passportNumber varchar(255),
        socialSecurity varchar(255),
        whiteListed bit not null,
        claim_id bigint,
        incident_id bigint,
        reservation_id bigint,
        primary key (id)
    );

create table Phone (
    id bigint not null auto_increment,
    phoneNumber varchar(255),
    type integer not null,
    whiteListed bit not null,
    incident_id bigint,
    person_id bigint,
    reservation_id bigint,
    primary key (id)
);

create table PnrData (
    id bigint not null auto_increment,
    airline varchar(255),
    pnrData TEXT,
    recordLocator varchar(255),
    reservation_id bigint,
    primary key (id)
);

create table Reservation (
    id bigint not null auto_increment,
    airline varchar(255),
    ccExpMonth integer,
    ccExpYear integer,
    ccNumLastFour varchar(255),
    ccNumber varchar(255),
    ccType varchar(255),
    formOfPayment varchar(255),
    itinComplexity integer not null,
    recordLocator varchar(255),
    ticketAmount double precision,
    travelDate datetime,
    tripLength integer not null,
    ccWhitelist_id bigint,
    incident_id bigint,
    purchaser_id bigint,
    primary key (id)
);

create table Segment (
    id bigint not null auto_increment,
    airline varchar(255),
    arrival varchar(255),
    date datetime,
    departure varchar(255),
    flight varchar(255),
    claim_id bigint,
    incident_id bigint,
    reservation_id bigint,
    primary key (id)
);

create table FsFile (
    id bigint not null auto_increment,
    statusId integer,
    swapId bigint not null,
    claim_id bigint,
    incident_id bigint,
    primary key (id)
);

create table FsClaim (
    subclass_type varchar(16) not null,
    id bigint not null auto_increment,
    airline varchar(255),
    airlineClaimId varchar(13),
    amountClaimed double precision not null,
    amountClaimedCurrency varchar(255),
    amountPaid double precision not null,
    claimDate datetime,
    claimProrateId integer not null,
    claimType integer not null,
    denied bit not null,
    fraudStatus integer not null,
    ntIncidentId varchar(255),
    privateReasonForDenial varchar(255),
    publicReasonForDenial varchar(255),
    statusId integer not null,
    swapId bigint not null,
    travelDate datetime,
    blacklist_id bigint,
    file_id bigint,
    Claimprorate_ID integer,
    ntIncident_Incident_ID varchar(13),
    Status_ID integer,
    primary key (id)
);


update systemcomponents set sort_order = 4 where component_id = 60;
update systemcomponents set sort_order = 3 where component_id = 61;
update systemcomponents set component_name = 'Expense Payout' where component_id = 60;

insert into systemcomponents 
(component_id, component_name, component_desc, parent_component_id, component_action_link, 

display, sort_order, sort_group)
values (602, 'Create Claim', 'create new claim', 59, 'create_claim.do', 1, 1, 0);

insert into systemcomponents 
(component_id, component_name, component_desc, parent_component_id, component_action_link, 

display, sort_order, sort_group)
values (603, 'Modify Claim', 'modify existing claim', 59, 'search_claim.do?clear=1', 1, 2, 0);

insert into properties (keyStr, valueStr) values ('nt.user', '1');


alter table claim add subclass_type varchar(16) not null;
update claim set subclass_type = 'FsClaim';
alter table claim add claimProrateId integer not null;
alter table claim add ntIncidentId varchar(255);
alter table claim add statusId integer not null;
alter table claim add swapId bigint not null;
alter table reservation add ccFName varchar(255);
alter table reservation add ccLName varchar(255);
alter table reservation add ccMName varchar(255);


    create table central_message (
        id bigint not null auto_increment,
        message longtext,
        messageContext integer,
        senderAgentId bigint not null,
        senderName varchar(30),
        timestamp datetime,
        primary key (id)
    );

    create table AccessRequest (
        id bigint not null auto_increment,
        requestedAgent varchar(255),
        requestedAirline varchar(255),
        requestedDate datetime,
        responseAgent varchar(255),
        responseDate datetime,
        status varchar(20) not null,
        file_id bigint,
        matchHistory_id bigint,
        message_id bigint,
        primary key (id)
    );

    
    alter table AccessRequest 
        add index FK67CEEC6B47AE6EEF (file_id)       ;

    alter table AccessRequest 
        add index FK67CEEC6BEE06EB78 (matchHistory_id);

    alter table AccessRequest 
        add index FK67CEEC6BBAA48ADB (message_id)        ;

insert into properties values (NULL, 'fraud.check.enabled', '1');
insert into properties values (NULL, 'fraud.check.timeout', '10');
insert into properties values (NULL, 'fraud.check.timeout.dam.mis', '10');
insert into properties values (NULL, 'fraud.server.location', 'jnp://184.172.41.2:1199');

insert into systemcomponents values (999,'Fraud Requests','Approve or Deny Fraud 

Requests',15,'fraudRequests.do',1,69,3);

set @company = 'AD';
select (@admin_grp:=UserGroup_ID) from usergroup where description = 'AD IT Admin' and 

companycode_ID=@company LIMIT 1;
insert into group_component_policy (component_id,group_id) values (999, @admin_grp);


create table FsReceipt (
    id bigint not null auto_increment,
    ccExpMonth integer,
    ccExpYear integer,
    ccLastFour varchar(4),
    ccType varchar(2),
    company varchar(255),
    claim_id bigint,
    primary key (id)
);

alter table FsAddress add column receipt_id bigint;
alter table Phone add column receipt_id bigint;
alter table FsAddress add column whiteListed bit not null;
alter table fsaddress add normAddress varchar(255);
    
create table AddressWhiteList (
        id integer not null auto_increment,
        address varchar(255),
        primary key (id)
    );
    
   create table PhoneWhiteList (
        id integer not null auto_increment,
        phoneNumber varchar(255),
        primary key (id)
    );

        create table GreyListAddress (
        id bigint not null auto_increment,
        address varchar(255),
        city varchar(255),
        country varchar(255),
        description varchar(255),
        geoCodeType integer not null,
        latitude double precision not null,
        longitude double precision not null,
        state varchar(255),
        streetName varchar(255),
        streetNumber varchar(255),
        zip varchar(255),
        primary key (id)
    );


    alter table GreyListAddress add index latLongIdx (latitude, longitude);
    
        create table FsNameAlias (
        id bigint not null auto_increment,
        alias varchar(255),
        name varchar(255),
        primary key (id)
    );


        create table CreditCard (
        id bigint not null auto_increment,
        ccExpMonth integer not null,
        ccExpYear integer not null,
        ccNumLastFour varchar(255),
        ccNumber varchar(255),
        ccType varchar(255),
        primary key (id)
    );
    
    
    alter table fsaddress drop column whitelisted;
alter table fsaddress add column whitelisted bit not null;

alter table central_message change senderName senderName varchar(255);


insert into properties (keyStr,valueStr)
values ('ntfs.submit.damaged','1'),('ntfs.submit.lostdelay','1'),('ntfs.submit.missing','1');


alter table phonewhitelist add description varchar(255);
alter table addresswhitelist add description varchar(255);

alter table phone add whitelist_id integer;
alter table phone drop column whiteListed;

alter table FsAddress add whitelist_id integer;
alter table FsAddress drop column whiteListed;

alter table person change driversLicenseIssuer driversLicenseState varchar(255);
alter table person add column driversLicenseProvince varchar(255);


     create table LFAddress (
        id bigint not null auto_increment,
        address1 varchar(255),
        address2 varchar(255),
        city varchar(255),
        country varchar(255),
        province varchar(255),
        state varchar(255),
        zip varchar(255),
        primary key (id)
    );

    create table LFCategory (
        id bigint not null auto_increment,
        description varchar(255),
        primary key (id)
    );

    create table LFDelivery (
        id bigint not null auto_increment,
        trackingNumber varchar(255),
        lost_id bigint,
        primary key (id)
    );

create table LFFound (
	id bigint not null auto_increment,
	foundDate datetime,
	mvaNumber varchar(255),
	agent_ID integer not null,
	client_id bigint,
	disposition_status_ID integer,
	item_id bigint,
	station_ID integer not null,
	status_ID integer not null,
	primary key (id)
);

    create table LFItem (
        id bigint not null auto_increment,
        brand varchar(255),
        category bigint,
        color varchar(255),
        description varchar(255),
        serialNumber varchar(255),
        subCategory bigint,
        disposition_status_ID integer,
        lost_id bigint,
        status_ID integer not null,
        primary key (id)
    );

    create table LFLost (
        id bigint not null auto_increment,
        closeDate datetime,
        emailSentDate datetime,
        openDate datetime,
        remarks varchar(255),
        agent_ID integer not null,
        client_id bigint,
        station_ID integer not null,
        reservation_id bigint,
        status_ID integer not null,
        primary key (id)
    );

    create table LFMatchDetail (
        id bigint not null auto_increment,
        description varchar(255),
        matchHistory_id bigint,
        primary key (id)
    );

    create table LFMatchHistory (
        id bigint not null auto_increment,
        found_id bigint,
        lost_id bigint,
        status_Status_ID integer,
        primary key (id)
    );

    create table LFPerson (
        id bigint not null auto_increment,
        email varchar(255),
        firstName varchar(255),
        lastName varchar(255),
        middleName varchar(255),
        address_id bigint,
        primary key (id)
    );

    create table LFPhone (
        id bigint not null auto_increment,
        extension varchar(255),
        numberType integer not null,
        phoneNumber varchar(255),
        phoneType integer not null,
        person_id bigint not null,
        primary key (id)
    );

    create table LFReservation (
        id bigint not null auto_increment,
        agreementNumber varchar(255),
        mvaNumber varchar(255),
        dropofflocation_station_ID integer not null,
        pickuplocation_station_ID integer not null,
        primary key (id)
    );

    create table LFSubCategory (
        id bigint not null auto_increment,
        description varchar(255),
        parent_id bigint not null,
        primary key (id)
    );
    
        insert into status (Status_ID,description,table_ID) VALUES (600,'Open',16),

(601,'Closed',16),(602,'To Be Delivered',17),(603,'Delivered',17),(604,'Picked Up',17),

(605,'Salvaged',17),(606,'Other',17);
     alter table LFPerson add vantiveNumber varchar(255);

    insert into properties (keyStr, valueStr) values ('lf.auto.close', 14);
    insert into properties (keyStr, valueStr) values ('lf.auto.salvage', 14);
    
     insert into status (Status_ID,description,table_ID) VALUES (607,'Trace Open',18),

(608,'Trace Confirmed',18),(609,'Trace Rejected',18),(610,'Trace Closed',18);
     
      alter table lfitem add found_id bigint(20);
      
    insert into properties (keyStr,valueStr) values ('lf.user','0');


alter table lfmatchdetail add score double precision not null;
      
ALTER TABLE lffound DROP disposition_status_ID;


alter table lfitem add column trackingNumber varchar(255);
alter table lfitem add column type int not null;

alter table lfmatchdetail add column lostValue varchar(255);
alter table lfmatchdetail add column foundValue varchar(255);

insert into report (number,resource_key) VALUES (78,'header.customreportnum.78');

ALTER TABLE lffound ADD INDEX (station_ID, status_ID);
ALTER TABLE lfitem ADD INDEX (lost_id, found_id, disposition_status_ID);
alter table lflost add index (station_ID, status_ID);

alter table lfmatchhistory add score double precision not null;
alter table lfmatchhistory add unique (lost_id, found_id, score);

    alter table task add incident_id varchar(13);
	alter table task add deferment_timestamp datetime;
	
	create table taskactivity 
(
        task_id bigint not null,
        taskactivity_id bigint not null auto_increment,
        completetime datetime,
        duration bigint,
        agent_ID integer not null,
        resolution varchar(200),
        primary key (taskactivity_id)
        );

 insert into systemcomponents (component_id, component_name, component_desc, 

parent_component_id, component_action_link, display, sort_order, sort_group)
	values (507, 'Fraud', 'Fraud Functionalities', 39, 'privacypermissions.do?view=1', 1, 

17, 0);
	
	insert into properties (keyStr, valueStr) values ('ntfs.user', '1');
	
	create table killswitch(
  companycode_id varchar(12) not null
);

create table FsActionAudit(
  id bigint not null auto_increment,
  actiondate datetime,
  action varchar(30),
  file_id bigint,
  companycode_id varchar(5),
  primary key (id)
);

    create table FsMatchHistoryAudit (
        id bigint not null auto_increment,
        matchhistory_id bigint,
        accesslevel varchar(3),
        action_id bigint,
        primary key (id)
    );
        
update properties set valueStr = 'jnp://10.41.103.68:1799' where keyStr = 

'fraud.server.location';

alter table lfmatchdetail modify column lostvalue varchar(2047);
alter table lfmatchdetail modify column foundvalue varchar(2047);
alter table lfperson modify column email varchar(511);
alter table lfphone modify column phoneNumber varchar(511);
alter table lfaddress modify column address1 varchar(511);
alter table lfaddress modify column address2 varchar(511);

create table LFLog (
id int(11) not null auto_increment,
stamp timestamp,
agent varchar(50),
stationcode varchar(10),
event varchar(50),
lflost_id int(11),
lffound_id int(11),
primary key (id));

alter table lflost add closeagent_id int;
insert into agent (firstname, mname, lastname, timeout, username, password, active, 

station_ID, companycode_ID, defaultlocale, currentlocale, defaultcurrency, defaulttimezone, 

currenttimezone, dateformat_ID, timeformat_ID, shift_id, usergroup_id, last_logged_on, 

is_online, last_pass_reset_date, is_wt_user, ws_enabled, max_ws_sessions, web_enabled, 

reset_password, account_locked, failed_logins) VALUES('avis', '', 'autoagent', 30, 

'autoagent', 'E3:A6:94:8B:C7:A1:15:4E:', 1, 248, 'AB', 'en', 'en', 'USD', 14, 14, 1, 1, NULL, 

159, NULL, 0, NULL, 0, 0, 0, 1, 0, 0, 0);

insert into report values (NULL, 86, 'header.customreportnum.86');

update station set associated_airport = '' where associated_airport != 'ABG' or stationcode = 

'MIA';

insert into lfcategory values (24, 'GPS'), (25, 'Garage Door Opener'), (26, 'EZ Pass'), (27, 

'Hangtag');
insert into lfsubcategory values (NULL, 'Garmin', 24), (NULL, 'TomTom', 24), (NULL, 

'Magellan', 24), (NULL, 'Other', 24), (NULL, 'EXPass', 26), (NULL, 'FastLane', 26), (NULL, 

'I-pass Tollroad', 26), (NULL, 'Fastrak', 26), (NULL, 'Texastag', 26), (NULL, 'Express', 26), 

(NULL, 'SunPass', 26), (NULL, 'Other', 26), (NULL, 'Handicap Tag', 27), (NULL, 'Parking Tag', 

27), (NULL, 'Other', 27);


update lfitem set color = 'BN' where color = 'BE';

insert into status (description,table_ID) VALUES ('Paid - Full',7),('Denied',7);

-- FILL IN HERE
update station set associated_airport = '' where associated_airport != 'ABG' or stationcode = 'MIA';

insert into lfcategory values (24, 'GPS'), (25, 'Garage Door Opener'), (26, 'EZ Pass'), (27, 'Hangtag');
insert into lfsubcategory values (NULL, 'Garmin', 24), (NULL, 'TomTom', 24), (NULL, 'Magellan', 24), (NULL, 'Other', 24), 
(NULL, 'EXPass', 26), (NULL, 'FastLane', 26), (NULL, 'I-pass Tollroad', 26), (NULL, 'Fastrak', 26), (NULL, 'Texastag', 26), 
(NULL, 'Express', 26), (NULL, 'SunPass', 26), (NULL, 'Other', 26), 
(NULL, 'Handicap Tag', 27), (NULL, 'Parking Tag', 27), (NULL, 'Other', 27);

update lfitem set color = 'BN' where color = 'BE';

insert into status (status_id,description,table_ID) 
VALUES (611,'Paid - Full',7),
(612,'Denied',7);

alter table oc_claim modify column privateInsuranceName varchar(50);

alter table lfsubcategory add score int(11) not null default 10;
alter table lfcategory add score int(11) not null default 3;

update lfmatchdetail set description = 'Partial Category Match' where description = 'Particle 

Category Match';

update lfcategory set score = 20 where id in (2,11,23);
update lfcategory set score = 15 where id in (4,14,16,18,21,22,26,27);
update lfcategory set score = 10 where id in (1,8,19,24,25);
update lfcategory set score = 8 where id in (3,5,6,10,15);
update lfcategory set score = 5 where id in (7,9,12,13,20);

update lfsubcategory set score = 15 where parent_id in (3,5,6,10,15);
update lfsubcategory set score = 20 where parent_id in (1,8,19,24,25);
update lfsubcategory set score = 25 where parent_id in (4,14,16,18,21,22,26,27);
update lfsubcategory set score = 30 where parent_id in (2,11,23);
update lfsubcategory set score = score - 5 where description like 'other%';

update properties set valueStr = 'NTServices_1_0/ClaimBean/remote' where keyStr = 

'fraud.server.name';
update properties set valueStr = 'NTServices_1_0/PrivacyPermissionsBean/remote' where keyStr = 

'fraud.permissions.server.name';

insert into properties (keyStr, valueStr) values ('wt.state.name', '0');
insert into properties (keyStr, valueStr) values ('wt.country.name', '0');

alter table lffound add column agreementNumber varchar(255) default '';

alter table lflost add column vantiveNumber varchar(20) default '';
update lflost lf set lf.vantiveNumber = (select p.vantiveNumber from lfPerson p where p.id = 

lf.client_id);
alter table lfperson drop column vantiveNumber;

update properties set valueStr = '0' where keyStr like 'ntfs.submit.%';

create table region (
    id integer not null auto_increment,
    companycode_id varchar(2),
    name varchar(255),
    director varchar(255),
    target double,
    primary key (id)
);

alter table station add column region_id long;

insert into systemcomponents (component_id, component_name, component_desc, 

parent_component_id, component_action_link, display, sort_order, sort_group) VALUES
(510, 'Region', 'Manage Region', 39, 'region.do?view=1', 1, 17, 0);

alter table region add column active bit not null;
alter table audit_station add column region_goal double;
update audit_station set region_goal = 0 where region_goal is null;

rename table lfreservation to lflossinfo;

alter table lflossinfo add lossdate date;
alter table lflossinfo change pickuplocation_station_ID origin_station_ID int; 
alter table lflossinfo change dropofflocation_station_ID destination_station_ID int;
alter table lflost change reservation_id lossInfo_id bigint(20);

alter table lfitem add longDescription varchar(2047);
alter table lfitem add caseColor varchar(255);
alter table lfitem add model varchar(255);
alter table lfitem add itemCondition varchar(255);
alter table lfitem add size varchar(255);

alter table lfphone add item_id bigint(20);
alter table lfitem add phone_id bigint(20);

alter table lfdelivery add found_id bigint(20);

alter table lfcategory add companycode varchar(8);
update lfcategory set companycode = 'AB';

alter table lffound add barcode varchar(255);
alter table lfphone modify column person_id bigint(20);

create table lfremark(
  id bigint(20) not null auto_increment,
  calltime bigint(20),
  outcome int(11),
  found_id bigint(20),
  lost_id bigint(20),
  agent_ID int(11),
  remarkDate datetime,
  type int(11),
  remarktext varchar(2047),
  primary key(id)
);

alter table lflost add email1 bit default 0;
alter table lflost add email2 bit default 0;

update systemcomponents set sort_order = 99 where component_name = 'Administration';

insert into systemcomponents 

(component_id,component_name,component_desc,parent_component_id,component_action_link,display,

sort_order,sort_group)
VALUES (614,'LFC Item Entry Workflow','LFC Item Entry Workflow',614,'',1,12,0);

insert into systemcomponents 

(component_id,component_name,component_desc,parent_component_id,component_action_link,display,

sort_order,sort_group)
VALUES (615,'Enter Items','enter items',614,'enter_items.do',1,0,0);

insert into properties (keyStr,valueStr) values ('history.queue.size','20');
insert into properties (keyStr,valueStr) values ('lfc.item.entry.display.count','3');

alter table lfitem add value integer default 0;
alter table lffound add itemLocation integer default 0;
alter table lffound add binId varchar(255);

insert into properties (keyStr,valueStr) values ('lf.server.location', 'LFC_SERVER_LOCATION');

alter table lffound add unique (barcode);

update company_specific_variable set email_from = 'lfc@nettracer.aero' where companycode_ID = 

'LF';


insert into systemcomponents 

(component_id,component_name,component_desc,parent_component_id,component_action_link,display,

sort_order,sort_group)
VALUES (616,'Load Found from Task Manager','Load the found item page directly from the task 

manager.',614,null,0,4,0);

create table generallog(
id bigint(20) not null auto_increment,
trans varchar(20),
entrydate datetime,
refId varchar(20),
description varchar(255),
elapseTime bigint(20) default 0,
primary key(id)
);

insert into systemcomponents 

(component_id,component_name,component_desc,parent_component_id,component_action_link,display,

sort_order,sort_group)
VALUES (617,'Shelved Items with Trace Results','Items on the shelf that have trace 

results.',15,'shelved_trace_results.do',1,0,5);

update systemcomponents set sort_order = 5 where component_id = 608;

alter table lffound add column receivedDate datetime;
update lffound set receivedDate = foundDate;

alter table dispute modify column disputeExplanation varchar(1550);
alter table dispute modify column resolutionRemarks varchar(1550);

create table passwordhistory(
  id bigint(20) not null auto_increment,
  pcount int(11) not null default 1,
  agent_id bigint(20),
  password varchar(512),
  primary key(id)
);

alter table company_specific_variable add min_pass_size int(11) default 8;
alter table company_specific_variable add pass_x_history int(11) default 20;
alter table audit_company_specific_variable add min_pass_size int(11) default 0;
alter table audit_company_specific_variable add pass_x_history int(11) default 0;
insert into agent (firstname, lastname, username, companycode_ID, shift_id, dateformat_ID, 

timeformat_ID, active, timeout, usergroup_id, defaultlocale, currentlocale, defaultcurrency, 

reset_password, station_id) 
select 'junit', 'test', 'junitagent', companycode_ID, shift_id, dateformat_ID, timeformat_ID, 

active, timeout, usergroup_id, defaultlocale, currentlocale, defaultcurrency, reset_password, 

station_id
from agent where username = 'ogadmin';

insert into agent (firstname, lastname, username, companycode_ID, shift_id, dateformat_ID, 

timeformat_ID, active, timeout, usergroup_id, defaultlocale, currentlocale, defaultcurrency, 

reset_password, station_id) 
select 'web', 'agent', 'webagent', companycode_ID, shift_id, dateformat_ID, timeformat_ID, 

active, timeout, usergroup_id, defaultlocale, currentlocale, defaultcurrency, reset_password, 

station_id
from agent where username = 'ntadmin' and companycode_ID not in ('LF', 'AB');

alter table lfitem add column deliveryRejected tinyint(1) default 0;

alter table lflost add foundEmail bit default 0;

alter table lffound add column deliveredDate datetime default null;
alter table lffound add column checkNumber varchar(255) default null;
alter table lffound add column checkAmount double default 0;

alter table lffound modify column checkNumber int(11) default 0;
update lffound set checkNumber = 0;

insert into systemcomponents values (618,'Reopen Lost and Found','Reopen Lost Reports and 

Found Items',39,'',0,18,0);

insert into properties (keyStr,valueStr) values ('lf.high.value.salvage.days','60'),

('lf.low.value.salvage.days','30');

create table LFSalvage (
    id bigint not null auto_increment,
    closedDate datetime,
    createdDate datetime,
    agent_ID integer not null,
    status_ID integer not null,
    primary key (id)
);

alter table lfitem add column salvage_id bigint;

insert into systemcomponents 

(component_id,component_name,component_desc,parent_component_id,component_action_link,display,

sort_order,sort_group)
VALUES (619,'LFC Salvage Workflow','',619,'',1,13,0);

insert into systemcomponents 

(component_id,component_name,component_desc,parent_component_id,component_action_link,display,

sort_order,sort_group)
VALUES (620,'Create Salvage','Create new salvage',619,'lf_salvage.do?createNew=1',1,0,0);

insert into systemcomponents 

(component_id,component_name,component_desc,parent_component_id,component_action_link,display,

sort_order,sort_group)
VALUES (621,'Search Salvage','Search for an existing salvage',619,'lf_search_salvage.do?

clear=1',1,1,0);


alter table lfitem drop column salvage_id;
alter table lffound add column salvage_id bigint;

alter table lfsalvage add column station_id bigint;

alter table audit_incident modify oc_claim_id bigint(20) default 0;
update audit_incident set oc_claim_id=0, modify_time = modify_time where oc_claim_id is null;

insert into systemcomponents values (624,'Update LF Remarks','Update lost and found 

remarks',604,'',0,5,0);

insert into systemcomponents values
(622,'Search Lost Reports','Search lost reports',604,'search_lost_found.do?

lost=1&clear=1',1,3,0),
(623,'Search Found Items','Search found items',604,'search_lost_found.do?

found=1&clear=1',1,4,0);

alter table oc_claim modify column attemptToClaimAtArrival tinyint(1) default 0;
alter table oc_claim modify column bagClearCustoms tinyint(1) default 0;
alter table oc_claim modify column baggageReroutedEnRoute tinyint(1) default 0;
alter table oc_claim modify column chargedForExcessBaggage tinyint(1) default 0;
alter table oc_claim modify column declaredExcessValue tinyint(1) default 0;
alter table oc_claim modify column differentClaimCheck tinyint(1) default 0;
alter table oc_claim modify column filedPreviousClaim tinyint(1) default 0;
alter table oc_claim modify column haveToRecheck tinyint(1) default 0;
alter table oc_claim modify column reportedToAnotherAirline tinyint(1) default 0;
alter table oc_claim modify column ticketWithAnotherAirline tinyint(1) default 0;
alter table oc_claim modify column tsaInspected tinyint(1) default 0;
alter table oc_claim modify column tsaNotePresent tinyint(1) default 0;
alter table oc_claim modify column wasBagInspected tinyint(1) default 0;
alter table oc_claim modify column privateInsurance tinyint(1) default 0;

update oc_claim set attemptToClaimAtArrival = 2 where attemptToClaimAtArrival = 0;
update oc_claim set bagClearCustoms = 2 where bagClearCustoms = 0;
update oc_claim set baggageReroutedEnRoute = 2 where baggageReroutedEnRoute = 0;
update oc_claim set chargedForExcessBaggage = 2 where chargedForExcessBaggage = 0;
update oc_claim set declaredExcessValue = 2 where declaredExcessValue = 0;
update oc_claim set differentClaimCheck = 2 where differentClaimCheck = 0;
update oc_claim set filedPreviousClaim = 2 where filedPreviousClaim = 0;
update oc_claim set haveToRecheck = 2 where haveToRecheck = 0;
update oc_claim set reportedToAnotherAirline = 2 where reportedToAnotherAirline = 0;
update oc_claim set ticketWithAnotherAirline = 2 where ticketWithAnotherAirline = 0;
update oc_claim set tsaInspected = 2 where tsaInspected = 0;
update oc_claim set tsaNotePresent = 2 where tsaNotePresent = 0;
update oc_claim set wasBagInspected = 2 where wasBagInspected = 0;
update oc_claim set privateInsurance = 2 where privateInsurance = 0;

create table LFSegment (
    id bigint not null auto_increment,
    lost_id bigint not null,
    flightNumber varchar(20),
    destination_station_ID integer not null,
    origin_station_ID integer not null,
    primary key (id)
);

alter table lffound add column flightNumber varchar(20);

alter TABLE fsclaim add amountPaidCurrency varchar(255);

update fsclaim SET amountPaidCurrency = amountClaimedCurrency;

CREATE TABLE `lfboxcontainer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateCount` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

CREATE TABLE `lfboxcount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `container_ID` int(11) DEFAULT NULL,
  `station_ID` bigint(20) DEFAULT NULL,
  `boxCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;

insert into systemcomponents values 
(703,'Box Count','Allows Access to the Box Counting Function',604,'lf_boxcount.do',1,6,0);

insert into systemcomponents values 
(625,'Save Button at Top of Page','Add save button to the top of the incident page.',39,'',0,99,0),
(626,'Issue RON Kits','Issue RON kits to passengers.',6,'',0,99,0),
(627,'Issue Replacement Bags','Issue replacement bags to passengers.',8,'',0,99,0);

alter table passenger add column numRonKitsIssued int default 0;
alter table item add column replacementBagIssued int default 0;

insert into systemcomponents VALUES (700,'Split Code and Station Lock','Divide the Lock Fault Information Button into two Separate Buttons for Station and Code',15,'',0,99,0);
insert into systemcomponents VALUES (701,'Get Next Dispute','Retrieve Next Dispute in List',15,'',0,99,0);

ALTER TABLE dispute ADD Lock_ID int;
ALTER TABLE incident ADD codeLocked tinyint not null default 0;
ALTER TABLE incident ADD stationLocked tinyint not null default 0;

insert into systemcomponents values (702,'Fault Station Lock','Locks Only Fault Station if the 

Incident is closed and already Locked',15,'',0,99,0);

update systemcomponents set sort_order = 4 where component_id = 14;

update systemcomponents SET component_name = 'Limited Loss Codes', component_desc = 'Allows 

Limited Loss Code configuration on closed Incidents' WHERE component_id = 702;

alter table oc_claim add column claimType int(11) default 1;

update oc_claim set claimType = 1;
update oc_claim set claimType = 2 where incident_id in (select incident_id from incident where 

itemtype_id = 2);
update oc_claim set claimType = 4 where incident_id in (select incident_id from incident where 

itemtype_id = 3);
update oc_claim set claimType = claimType + 8 where claimid in (select distinct claimid from 

oc_file where interim = 1);

update properties set valuestr='NTServices_1_0/ClaimClientBeanV1/remote' where keystr = 

'fraud.server.name';
-- alter TABLE fsclaim add amountPaidCurrency varchar(255);

    create table audit_fs_claim (
        id bigint not null auto_increment,
        accessTime datetime not null,
        actionType integer not null,
        agentId integer not null,
        itemId bigint not null,
        itemType integer not null,
        primary key (id)
    );
    
    alter table audit_expensepayout add column modify_time datetime;
    
    alter table fsfile add column validatingCompanycode varchar(2) not null;

update fsfile set validatingCompanycode = (select companycode_ID from agent where username = 'ntadmin');

insert into properties (keyStr,valueStr)
values ('ntfs.support.multiple.claims','0');

alter table fsfile drop column claim_id;
alter table fsfile drop column incident_id;
alter table fsfile add createDate datetime;

update fsfile f set createDate = (select timestampOpen from fsincident where file_id = f.id);


create table online_incident_authorization (
    id bigint not null auto_increment,
    first_name varchar(20),
    last_name varchar(20),
    pnr varchar(10),
    incident_ID varchar(13),
    expire_time datetime,
    primary key (id)
);

alter table proactiveNotification add membershipAirline varchar(255);
alter table proactiveNotification add membershipNumber varchar(255);
alter table proactiveNotification add passIndex integer;
alter table proactiveNotification add oia_ID integer;

insert into properties (keyStr, valueStr) values ('fraud.timeout','1000');

alter table FsClaim add column incident_id bigint;

update fsclaim SET amountPaidCurrency = amountClaimedCurrency;

update systemcomponents set component_action_link='fraudRequests.do?resetFilter=1' where 

component_name = 'Fraud Requests';

insert into report (number, resource_key) values (201, 'header.customreportnum.201');

alter table audit_expensepayout modify column comments varchar(2000);

alter table bagbuzz modify description varchar(256);

-- FIX KA/KS Kansas State ID --
update state set state_id = 'KS' where state_id = 'KA';
update address set state_id = 'KS' where state_id = 'KA';
update audit_address set state_id = 'KS' where state_id = 'KA';
update bdo_passenger set state_id = 'KS' where state_id = 'KA';
update companycode set state_id = 'KS' where state_id = 'KA';
update audit_companycode set state_id = 'KS' where state_id = 'KA';
update lost_found set customer_state_id = 'KS' where customer_state_id = 'KA';
update audit_lost_found set customer_state_id = 'KS' where customer_state_id = 'KA';
update ohd_address set state_id = 'KS' where state_id = 'KA';
update audit_ohd_address set state_id = 'KS' where state_id = 'KA';
update station  set state_id = 'KS' where state_id = 'KA';
update audit_station  set state_id = 'KS' where state_id = 'KA';
update fsaddress set state='KS' where state='KA';
update greylistaddress set state='KS' where state='KA';

-- If the status id exists already, it won't create
insert into status (status_id, description, table_ID) values (96,'paused',13);
insert into status (status_id, description, table_ID) values (97,'working',13);

insert into status (Status_ID,description,table_ID) VALUES ( 98,'VIP Pickup',9);

alter table incident_claimcheck add claimchecknum_leading varchar(1);
alter table incident_claimcheck add claimchecknum_ticketingcode varchar(3);
alter table incident_claimcheck add claimchecknum_carriercode varchar(2);
alter table incident_claimcheck add claimchecknum_bagnumber varchar(6);

alter table item add claimchecknum_leading varchar(1);
alter table item add claimchecknum_ticketingcode varchar(3);
alter table item add claimchecknum_carriercode varchar(2);
alter table item add claimchecknum_bagnumber varchar(6);

-- claimnum
alter table ohd add claimchecknum_leading varchar(1);
alter table ohd add claimchecknum_ticketingcode varchar(3);
alter table ohd add claimchecknum_carriercode varchar(2);
alter table ohd add claimchecknum_bagnumber varchar(6);

--INDEXES
create index incident_scanquery1_idx on incident (Incident_ID, itemtype_ID, 

createdate,createtime, recordlocator);
create index incident_scanquery2_idx on incident (recordlocator, createdate, 

createtime,Incident_ID);
create index incident_scanquery3_idx on incident (Incident_ID, createdate, createtime);

create index ic_scanquery1_idx on incident_claimcheck (claimchecknum_bagnumber, 

claimchecknum_ticketingcode, claimchecknum_carriercode, claimchecknum_leading, incident_ID);
create index ic_scanquery2_idx on incident_claimcheck (claimchecknum_bagnumber, 

claimchecknum_ticketingcode, claimchecknum_leading, incident_ID);
create index ic_scanquery3_idx on incident_claimcheck (claimchecknum_bagnumber, 

claimchecknum_ticketingcode, incident_ID);
create index ic_scanquery4_idx on incident_claimcheck (claimchecknum_bagnumber, 

claimchecknum_carriercode, incident_ID);

create index item_scanquery1_idx on item (claimchecknum_bagnumber, 

claimchecknum_ticketingcode, claimchecknum_carriercode, claimchecknum_leading, incident_ID);
create index item_scanquery2_idx on item (claimchecknum_bagnumber, 

claimchecknum_ticketingcode, claimchecknum_leading, incident_ID);
create index item_scanquery3_idx on item (claimchecknum_bagnumber, 

claimchecknum_ticketingcode, incident_ID);
create index item_scanquery4_idx on item (claimchecknum_bagnumber, claimchecknum_carriercode, 

incident_ID);
--END INDEXES

-- convert old data

-- check data your converting if you want
--select claimchecknum, SUBSTR(claimchecknum, 1, 2) as code, SUBSTR(claimchecknum, 3, 6) as num from incident_claimcheck where CHAR_LENGTH(claimchecknum) = 8 limit 10;
--select claimchecknum, SUBSTR(claimchecknum, 1, 3) as ticket, SUBSTR(claimchecknum, 4, 6) as num from incident_claimcheck where CHAR_LENGTH(claimchecknum) = 9 limit 10;
--select claimchecknum, SUBSTR(claimchecknum, 2, 3) as ticket, SUBSTR(claimchecknum, 5, 6) as num, SUBSTR(claimchecknum, 1, 1) as lead from incident_claimcheck where CHAR_LENGTH(claimchecknum) = 10 limit 10;

update incident_claimcheck set claimchecknum_carriercode = SUBSTR(claimchecknum, 1, 2), 

claimchecknum_bagnumber = SUBSTR(claimchecknum, 3, 6) where CHAR_LENGTH(claimchecknum) = 8;
update incident_claimcheck set claimchecknum_ticketingcode = SUBSTR(claimchecknum, 1, 3), 

claimchecknum_bagnumber = SUBSTR(claimchecknum, 4, 6) where CHAR_LENGTH(claimchecknum) = 9;
update incident_claimcheck set claimchecknum_ticketingcode = SUBSTR(claimchecknum, 2, 3), 

claimchecknum_bagnumber = SUBSTR(claimchecknum, 5, 6) where CHAR_LENGTH(claimchecknum) = 10;
update incident_claimcheck set claimchecknum_leading = SUBSTR(claimchecknum, 1, 1) where 

CHAR_LENGTH(claimchecknum) = 10 and SUBSTR(claimchecknum, 1, 1) REGEXP '^[0-9]{1}$';
update incident_claimcheck c join lookup_airline_codes a on c.claimchecknum_carriercode = 

a.Airline_2_Character_Code 
set c.claimchecknum_ticketingcode = a.Airline_3_Digit_Ticketing_Code where 

c.claimchecknum_carriercode is not null;
update incident_claimcheck c join lookup_airline_codes a on c.claimchecknum_ticketingcode = 

a.Airline_3_Digit_Ticketing_Code 
set c.claimchecknum_carriercode = a.Airline_2_Character_Code where 

c.claimchecknum_ticketingcode is not null and c.claimchecknum_carriercode is null;

-- check data your converting if you want
--select claimchecknum, SUBSTR(claimchecknum, 1, 2) as code, SUBSTR(claimchecknum, 3, 6) as num from item where CHAR_LENGTH(claimchecknum) = 8 limit 10;
--select claimchecknum, SUBSTR(claimchecknum, 1, 3) as ticket, SUBSTR(claimchecknum, 4, 6) as num from item where CHAR_LENGTH(claimchecknum) = 9 limit 10;
--select claimchecknum, SUBSTR(claimchecknum, 2, 3) as ticket, SUBSTR(claimchecknum, 5, 6) as num, SUBSTR(claimchecknum, 1, 1) as lead from item where CHAR_LENGTH(claimchecknum) = 10 limit 10;

update item set claimchecknum_carriercode = SUBSTR(claimchecknum, 1, 2), 

claimchecknum_bagnumber = SUBSTR(claimchecknum, 3, 6) where CHAR_LENGTH(claimchecknum) = 8;
update item set claimchecknum_ticketingcode = SUBSTR(claimchecknum, 1, 3), 

claimchecknum_bagnumber = SUBSTR(claimchecknum, 4, 6) where CHAR_LENGTH(claimchecknum) = 9;
update item set claimchecknum_ticketingcode = SUBSTR(claimchecknum, 2, 3), 

claimchecknum_bagnumber = SUBSTR(claimchecknum, 5, 6) where CHAR_LENGTH(claimchecknum) = 10;
update item set claimchecknum_leading = SUBSTR(claimchecknum, 1, 1) where CHAR_LENGTH

(claimchecknum) = 10 and SUBSTR(claimchecknum, 1, 1) REGEXP '^[0-9]{1}$';
update item c join lookup_airline_codes a on c.claimchecknum_carriercode = 

a.Airline_2_Character_Code 
set c.claimchecknum_ticketingcode = a.Airline_3_Digit_Ticketing_Code where 

c.claimchecknum_carriercode is not null;
update item c join lookup_airline_codes a on c.claimchecknum_ticketingcode = 

a.Airline_3_Digit_Ticketing_Code 
set c.claimchecknum_carriercode = a.Airline_2_Character_Code where 

c.claimchecknum_ticketingcode is not null and c.claimchecknum_carriercode is null;

-- check data your converting if you want
--select claimnum, SUBSTR(claimnum, 1, 2) as code, SUBSTR(claimnum, 3, 6) as num from ohd where CHAR_LENGTH(claimnum) = 8 limit 10
--select claimnum, SUBSTR(claimnum, 1, 3) as ticket, SUBSTR(claimnum, 4, 6) as num from ohd where CHAR_LENGTH(claimnum) = 9 limit 10;
--select claimnum, SUBSTR(claimnum, 2, 3) as ticket, SUBSTR(claimnum, 5, 6) as num, SUBSTR(claimnum, 1, 1) as lead from ohd where CHAR_LENGTH(claimnum) = 10 limit 10;

update ohd set claimchecknum_carriercode = SUBSTR(claimnum, 1, 2), claimchecknum_bagnumber = 

SUBSTR(claimnum, 3, 6) where CHAR_LENGTH(claimnum) = 8;
update ohd set claimchecknum_ticketingcode = SUBSTR(claimnum, 1, 3), claimchecknum_bagnumber = 

SUBSTR(claimnum, 4, 6) where CHAR_LENGTH(claimnum) = 9;
update ohd set claimchecknum_ticketingcode = SUBSTR(claimnum, 2, 3), claimchecknum_bagnumber = 

SUBSTR(claimnum, 5, 6) where CHAR_LENGTH(claimnum) = 10;
update ohd set claimchecknum_leading = SUBSTR(claimnum, 1, 1) where CHAR_LENGTH(claimnum) = 10 

and SUBSTR(claimnum, 1, 1) REGEXP '^[0-9]{1}$'; 
update ohd c join lookup_airline_codes a on c.claimchecknum_carriercode = 

a.Airline_2_Character_Code 
set c.claimchecknum_ticketingcode = a.Airline_3_Digit_Ticketing_Code where 

c.claimchecknum_carriercode is not null;
update ohd c join lookup_airline_codes a on c.claimchecknum_ticketingcode = 

a.Airline_3_Digit_Ticketing_Code 
set c.claimchecknum_carriercode = a.Airline_2_Character_Code where 

c.claimchecknum_ticketingcode is not null and c.claimchecknum_carriercode is null;


-- have a look at anything not 8, 9, or 10 digits
--select claimchecknum from incident_claimcheck where (CHAR_LENGTH(claimchecknum) > 0 and CHAR_LENGTH(claimchecknum) < 8) or CHAR_LENGTH(claimchecknum) > 10;
--select claimchecknum from item where (CHAR_LENGTH(claimchecknum) > 0 and CHAR_LENGTH(claimchecknum) < 8) or CHAR_LENGTH(claimchecknum) > 10;
--select claimnum from ohd where (CHAR_LENGTH(claimnum) > 0 and CHAR_LENGTH(claimnum) < 8) or CHAR_LENGTH(claimnum) > 10;

-- update times to GMT
update fsclaim set claimDate = DATE_ADD(claimDate, INTERVAL 5 HOUR); 

insert into properties (keyStr, valueStr) values ('scanquery.days.back', '4');

alter table lfsubcategory drop score ;
alter table lfsubcategory add score integer not null default 10;
alter table lfcategory drop score ;
alter table lfcategory add score integer not null default 3;insert into lfcategory 

(description ,score ,companycode) VALUES ('Laptop/Computer', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Camera', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Cell Phone', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Tablet/E-Reader', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Head Phones', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Wallet/Purse', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Currency/Gift Card/Credit 

Card', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Book', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Coat/Jacket', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Misc/ Electronic Items', 

0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Music', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Papers', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Jewelry', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Handheld Games', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Optics/Sunglasses', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('Keys', 0,'AD');
insert into lfcategory (description ,score ,companycode) VALUES ('OTHER', 0,'AD');

insert into countrycode (
   CountryCode_ID
  ,country
) VALUES 
   ('ME', 'Montenegro');
   

alter table company_specific_variable add auto_close_days_back integer(11) default 0;
alter table company_specific_variable add auto_close_ld_code integer(11) default 0;
alter table company_specific_variable add auto_close_dam_code integer(11) default 0;
alter table company_specific_variable add auto_close_pil_code integer(11) default 0;
alter table company_specific_variable add auto_close_ld_station integer(11) default 0;
alter table company_specific_variable add auto_close_dam_station integer(11) default 0;
alter table company_specific_variable add auto_close_pil_station integer(11) default 0;

alter table audit_company_specific_variable add auto_close_days_back integer(11) default 0;
alter table audit_company_specific_variable add auto_close_ld_code integer(11) default 0;
alter table audit_company_specific_variable add auto_close_dam_code integer(11) default 0;
alter table audit_company_specific_variable add auto_close_pil_code integer(11) default 0;
alter table audit_company_specific_variable add auto_close_ld_station integer(11) default 0;
alter table audit_company_specific_variable add auto_close_dam_station integer(11) default 0;
alter table audit_company_specific_variable add auto_close_pil_station integer(11) default 0;

alter table ohd add column warehouseReceivedDate dateTime;
alter table ohd add column warehouseSentDate dateTime;

insert into systemcomponents VALUES (704,'Assign Warehouse Dates','Allow to assign Warehouse 

Dates for OHDs',1,'',0,99,0);
create index warehouserecindex on ohd (warehouseReceivedDate, ohd_id);
create index warehousesentindex on ohd (warehouseSentDate, ohd_id);

alter table lost_found add column category_id integer;
update lost_found set category_id =17;

create table salvage_remark (
	remark_id int(11) not null auto_increment,
	agent_id int(11) not null,
	createtime datetime,
	remarktext text,
	remarktype tinyint(1),
	primary key (remark_id)
);

alter table salvage add remark_id int(11);

alter table incident add tracing_status_id tinyint(1) default 0;
alter table audit_incident add tracing_status_id tinyint(1) default 0;

insert into systemcomponents VALUES (705,'Edit Tracing Status','Allows a user to edit the 

Current Tracing Status of an Incident',6,'',0,99,0);

create table delivery_instructions (id int auto_increment,instructions text, PRIMARY KEY 

(id));

alter table incident add column deliveryInstructions_ID int;

alter table audit_incident add column instructions text;
alter table bdo_passenger add column hotelphone varchar(100);

alter table company_specific_variable add incident_lock_mins integer(11) default 0;
alter table audit_company_specific_variable add incident_lock_mins integer(11) default 0;

--testing
-- insert into properties (keyStr,valueStr) values ('lock.cache.cluster','lockfile-cache-testing');

--training
-- insert into properties (keyStr,valueStr) values ('lock.cache.cluster','lockfile-cache-training');

--production
insert into properties (keyStr,valueStr) values ('lock.cache.cluster','lockfile-cache-production');

alter table address add column hotelphone varchar(50);

alter table fsclaim add column claimRemark text;

update properties set valueStr='NTServices_1_0/ClaimClientBeanV2/remote' where keyStr='fraud.server.name';

create table FsIPAddress (
	id int(11) not null auto_increment,
	ipAddress varchar(20),
	claim_id int(11) not null,
	primary key (id));
	
ALTER TABLE FsIPAddress ADD INDEX claim_id_index (claim_id);
	
alter table phone add column association varchar(255);
alter table phone add column claim_id int(11);
ALTER TABLE phone ADD INDEX claim_id_index (claim_id);
alter table fsipaddress add column association varchar(255);
