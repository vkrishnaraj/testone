alter table fsclaim add column claimRemark text;
alter table address add column hotelphone varchar(50);

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