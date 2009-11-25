    create table world_tracer_ishares_account (
        id bigint not null auto_increment,
        companyCode varchar(2),
        host varchar(128),
        lastReset datetime,
        password varchar(20),
        sine varchar(20),
        username varchar(20),
        profile_id bigint,
        primary key (id)
    );
    
    alter table world_tracer_ishares_account 
    add index FK18D8F0E61344B8D4 (profile_id), 
    add constraint FK18D8F0E61344B8D4 
    foreign key (profile_id) 
    references profile (id);

    
	create table WT_ISHARES_TRANS_LOG (
        id bigint not null auto_increment,
        gmttime DATETIME,
        username varchar(20) not null,
		description varchar(60) not null,
		uri varchar(100) not null,
        primary key (id)
    );
    
    alter table profile add airline varchar(2);
    insert into profile_parameters values (4, 'WTRWEB', 'WTSM_ESTABLISH');
    
    ################################### ABOVE FUNCTION COMPLETED
    