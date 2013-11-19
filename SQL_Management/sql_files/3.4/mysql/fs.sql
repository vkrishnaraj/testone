alter table fsclaim add column claimRemark text;
alter table privacy_permissions add column claimRemarks bit default 0;

create table FsIPAddress (
	id int(11) not null auto_increment,
	ipAddress varchar(20),
	claim_id int(11) not null,
	whitelist_id int(11),
	primary key (id));
	
ALTER TABLE FsIPAddress ADD INDEX claim_id_index (claim_id);
ALTER TABLE FsIPAddress ADD INDEX whitelist_id_index (whitelist_id);
	
create table IPWhiteList (
	id int(11) not null auto_increment,
	ipAddress varchar(20),
	description varchar(255),
	primary key (id));
	
alter table privacy_permissions add column ipAddresses bit default 0;	
	
alter table phone add column association varchar(255);
alter table phone add column claim_id int(11);
ALTER TABLE phone ADD INDEX claim_id_index (claim_id);
alter table fsipaddress add column association varchar(255);

alter table accessrequest add column contactname varchar(255);
alter table accessrequest add column contactemail varchar(255);
alter table accessrequest add column contactphone varchar(255);


