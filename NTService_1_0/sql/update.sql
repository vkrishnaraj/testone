    create table world_tracer_web_account (
        id bigint not null auto_increment,
        companyCode varchar(2),
        host varchar(128),
        isCaptchaRequired bit not null,
        isFrontend bit not null,
        isTrainingMode bit not null,
        lastReset datetime,
        password varchar(20),
        username varchar(20),
        profile_id bigint,
        primary key (id)
    );
		
		
    alter table world_tracer_web_account 
        add index FK5944409D1344B8D4 (profile_id), 
        add constraint FK5944409D1344B8D4 
        foreign key (profile_id) 
        references profile (id);

        
    create table WT_WEB_TRANS_LOG (
        id bigint not null auto_increment,
        gmttime DATETIME,
        username varchar(20) not null,
		description varchar(60) not null,
		uri varchar(100) not null,
        primary key (id)
    );