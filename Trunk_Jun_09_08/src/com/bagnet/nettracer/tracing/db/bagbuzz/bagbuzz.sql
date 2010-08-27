

drop table if exists task;

    create table task (
        task_type varchar(16) not null,
        task_id bigint not null auto_increment,
        closed_timestamp datetime,
        opened_timestamp datetime,
        agent_ID integer not null,
        status_ID integer not null,
        bagbuzz_id bigint,
        primary key (task_id)
    );
        alter table task 
        add index FK363585C1AA7746 (agent_ID), 
        add constraint FK363585C1AA7746 
        foreign key (agent_ID) 
        references Agent (Agent_ID);


    delete from status where table_id = 13;
    delete from status where table_id = 14;
    insert into status (status_id,description,table_id) values (83,'open',13);
    insert into status (status_id,description,table_id) values (84,'processed',13);
    insert into status (status_id,description,table_id) values (85,'closed',13);
    insert into status (status_id,description,table_id) values (86,'new',14);
    insert into status (status_id,description,table_id) values (87,'published',14);
    insert into status (status_id,description,table_id) values (88,'deleted',14);
    insert into status (status_id,description,table_id) values (89,'accepted',15);
    insert into status (status_id,description,table_id) values (90,'denied',15);



drop table if exists bagbuzz;
    create table bagbuzz (
        bagbuzz_id bigint not null auto_increment,
        created_timestamp datetime,
        data text,
        description varchar(30),
        status_ID integer not null,
        primary key (bagbuzz_id)
    );


drop table if exists task_type;
create table task_type
(
task_type_id int(11) not null auto_increment,
primary key (task_type_id),
description varchar(20),
table_name varchar(20)
);

insert into task_type (description, table_name) values ('bagbuzz', 'bagbuzz');

drop table if exists role;
create table role
(
role_id int(11) not null auto_increment,
primary key (role_id),
name varchar(20),
description varchar(20)
);

drop table if exists agentxrole;
create table agentxrole
(
agentxrole_id int(11) not null auto_increment,
primary key (agentxrole_id),
agent_id int(11),
role_id int(11)
);

drop table if exists inbox;
create table inbox
(
inbox_id int(11) not null auto_increment,
primary key (inbox_id),
inbox_type varchar(20)
);

drop table if exists inboxxrole;
create table inboxxrole
(
inboxxrole_id int(11) not null auto_increment,
primary key (inboxxrole_id),
inbox_id int(11),
role_id int(11)
);

