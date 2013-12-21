-- File run for hosted customers to get their OnlineClaims up to date
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
        filename varchar(100),
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
    

alter table incident add column oc_claim_id bigint not null default 0;


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


alter table oc_bag modify column brand varchar(20);
alter table oc_claim modify column privateInsuranceName varchar(50);
alter table audit_incident modify oc_claim_id bigint(20) default 0;
update audit_incident set oc_claim_id=0, modify_time = modify_time where oc_claim_id is null;


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


alter table oc_claim add column claimType int(11) default 1;

update oc_claim set claimType = 1;
update oc_claim set claimType = 2 where incident_id in (select incident_id from incident where itemtype_id = 2);
update oc_claim set claimType = 4 where incident_id in (select incident_id from incident where itemtype_id = 3);
update oc_claim set claimType = claimType + 8 where claimid in (select distinct claimid from oc_file where interim = 1);

alter table oc_claim add bagReceivedDate datetime;
alter table oc_content add quantity integer default 0;
update oc_content set quantity=0;

alter table oc_claim add foreignCurrencyEmail varchar(100);
alter table oc_claim add requestForeignCurrency integer default 0;
update oc_claim set requestForeignCurrency = 0;

alter table oc_claim add reasonForTravel varchar(10);
alter table oc_claim add lengthOfStay varchar(20);

alter table oc_claim add column ticketNumber varchar(20);
alter table oc_file add column path varchar(1000);

create table oc_message (
  id bigint auto_increment not null,
  claimId bigint,
  incActId bigint,
  dateCreated datetime not null,
  username varchar(20) not null,
  message text,
  dateReviewed datetime,
  publish tinyint default 1,
  primary key(id)
  );
  
alter table oc_file add column incActId bigint;
alter table oc_file add column publish tinyint default 1;
update oc_file set publish=1;
alter table oc_file add column dateViewed date;

alter table oc_file add column statusId int default 1;
alter table oc_message add column statusId int default 1;