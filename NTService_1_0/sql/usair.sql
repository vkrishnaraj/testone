
    create table svc_profile (
        id numeric(19,0) identity not null,
        airline varchar(2) null,
        name varchar(20) null,
        primary key (id)
    );

    create table svc_profile_parameters (
        profile_id numeric(19,0) not null,
        element varchar(255) null,
        parameter_type varchar(30) not null,
        primary key (profile_id, parameter_type)
    );

    create table svc_profile_permission (
        permission_id numeric(19,0) not null,
        element tinyint null,
        permission_type varchar(30) not null,
        primary key (permission_id, permission_type)
    );

    create table svc_sabre_connection (
        id int identity not null,
        company varchar(2) null,
        domain varchar(20) null,
        endpoint varchar(255) null,
        organization varchar(20) null,
        password varchar(20) null,
        pseudoCityCode varchar(20) null,
        username varchar(20) null,
        profile_id numeric(19,0) null,
        primary key (id)
    );

    create table svc_user (
        id int identity not null,
        password varchar(20) null,
        username varchar(20) null,
        profile_id numeric(19,0) not null,
        primary key (id)
    );

    create table svc_world_tracer_ishares_account (
        id numeric(19,0) identity not null,
        companyCode varchar(2) null,
        host varchar(128) null,
        lastReset datetime null,
        password varchar(20) null,
        sine varchar(20) null,
        username varchar(20) null,
        profile_id numeric(19,0) null,
        primary key (id)
    );

    create table svc_world_tracer_web_account (
        id numeric(19,0) identity not null,
        companyCode varchar(2) null,
        host varchar(128) null,
        isCaptchaRequired tinyint not null,
        isFrontend tinyint not null,
        isTrainingMode tinyint not null,
        lastReset datetime null,
        password varchar(20) null,
        username varchar(20) null,
        profile_id numeric(19,0) null,
        primary key (id)
    );
    
     
	create table WT_ISHARES_TRANS_LOG (
        id numeric(19,0) identity not null,
        gmttime DATETIME,
        username varchar(20) not null,
		description varchar(60) not null,
		uri varchar(100) not null,
        primary key (id)
    );
    
    
	create table WT_WS_TRANS_LOG (
        id numeric(19,0) identity not null,
        gmttime DATETIME,
		description varchar(60) not null,
        primary key (id)
    );

    alter table svc_profile_parameters 
        add constraint FK5212467F1344B8D4 
        foreign key (profile_id) 
        references svc_profile;

    alter table svc_profile_permission 
        add constraint FK17E04A64FFA542EE 
        foreign key (permission_id) 
        references svc_profile;

    alter table svc_sabre_connection 
        add constraint FKF75345151344B8D4 
        foreign key (profile_id) 
        references svc_profile;

    alter table svc_user 
        add constraint FKBAA7E24A1344B8D4 
        foreign key (profile_id) 
        references svc_profile;

    alter table svc_world_tracer_ishares_account 
        add constraint FK813E1A651344B8D4 
        foreign key (profile_id) 
        references svc_profile;

    alter table svc_world_tracer_web_account 
        add constraint FKB780019C1344B8D4 
        foreign key (profile_id) 
        references svc_profile;
        

insert into properties (keyStr, valueStr) values ('nt.central.endpoint', 'http://10.52.113.18/NTServices_1_0/services/WorldTracerService');
insert into properties (keyStr, valueStr)values ('nt.central.username', 'usairways');
insert into properties (keyStr, valueStr)values ('nt.central.password', 'password');

GO

create table special_flag (
keyStr varchar(10) NOT NULL PRIMARY KEY,
valueStr varchar(100),
flagResetTimestamp datetime
);

GO

INSERT INTO special_flag (keyStr, valueStr, flagResetTimestamp) VALUES ('captcha', '0', '2009-11-24 13:22:00');

insert into systemcomponents values(324,'Captcha','to enable captcha feature',15,'wtCaptcha.do?taskManagerEntry=1',0,1,-1);

DECLARE @admin_grp int;
SET @admin_grp = (SELECT TOP 1 UserGroup_ID from usergroup where description = 'Admin' and companycode_ID='US');
insert into group_component_policy (component_id,group_id) values (324, @admin_grp);

update special_flag set valueStr = 0;
update svc_profile set airline='US';
update properties set valueStr = '5' where keyStr = 'wt.af.expire';