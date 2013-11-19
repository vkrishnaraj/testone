alter table fsclaim add column createagentname varchar(50);
alter TABLE privacy_permissions add column attachment bit default 0;

CREATE TABLE fsattachment (
 id int(11) NOT NULL AUTO_INCREMENT, 
 claim_id int(11) DEFAULT NULL, 
 post_id int(11) DEFAULT NULL, 
 path varchar(100) DEFAULT NULL,  
 description varchar(200) DEFAULT NULL, 
 compCode varchar(2) DEFAULT NULL,  
 PRIMARY KEY (id));
  
## BEGIN FORUM TABLES

    create table FsForumPost (
        id bigint not null auto_increment,
        createAgent varchar(50),
        createAirline varchar(2),
        createDate DATETIME,
        lastEdited DATETIME,
        text TEXT,
        title varchar(100),
        parent_id bigint,
        thread_id bigint,
        primary key (id)
    );

    create table FsForumPost_Claim (
        post_id bigint not null,
        claim_id bigint not null,
        claim_airline varchar(2) not null,
        primary key (post_id, claim_id, claim_airline)
    );

    create table FsForumTag (
        id bigint not null auto_increment,
        name varchar(50),
        numThreads integer not null,
        primary key (id)
    );
    
    insert into FsForumTag values (null, 'Fraud Ring', 0), (null, 'Fraud Procedure', 0), (null, 'Receipt Generator', 0), (null, 'FS Success', 0), (null, 'Suggested Change', 0);

    create table FsForumThread (
        id bigint not null auto_increment,
        createAgent varchar(50),
        createAirline varchar(2),
        createDate DATETIME,
        lastEdited DATETIME,
        locked bit not null,
        numAttachments integer not null,
        numFiles integer not null,
        numPosts integer not null,
        title varchar(100),
        primary key (id)
    );

    create table FsForumThread_Tag (
        thread_id bigint not null,
        tag_id bigint not null,
        primary key (thread_id, tag_id)
    );
    
    create index fsforumpost_thread_idx on FsForumPost (thread_id);
    create index fsattachment_post_thread_idx on fsattachment (post_id);
    create index fsforumthread_edited_idx on FsForumThread (lastEdited, id);
    
## END FORUM TABLES


##JBOSS7##
alter table fsattachment modify column post_id bigint(20);
alter table fsattachment modify column claim_id bigint(20);
alter table fsipaddress modify column id bigint(20) not null auto_increment;
alter table fsipaddress modify column claim_id bigint(20);
alter table phone modify column claim_id bigint(20);

insert into addresswhitelist (address,description) VALUES ('132 E Trade St','The Omni Hotel');

create table fsactionauditmetric(
  id bigint not null auto_increment,
  action_id bigint,
  metric varchar(255),
  duration bigint,
  traceElements int,
  remark varchar(2048),
  primary key (id)
);

create index idx_action_id on fsactionauditmetric (action_id);
