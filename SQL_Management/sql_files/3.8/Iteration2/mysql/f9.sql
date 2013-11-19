drop table if exists lffound;
drop table if exists lfitem;
drop table if exists lfcategory;
drop table if exists lfsubcategory;
drop table if exists lfaddress;
drop table if exists lfperson;
drop table if exists lfphone;
drop table if exists lfremark;

create table lffound like lfc_testing.lffound;
create table lfitem like lfc_testing.lfitem;
create table lfcategory like lfc_testing.lfcategory;
create table lfsubcategory like lfc_testing.lfsubcategory;
create table lfaddress like lfc_testing.lfaddress;
create table lfperson like lfc_testing.lfperson;
create table lfphone like lfc_testing.lfphone;
create table lfremark like lfc_testing.lfremark;

insert into lfcategory select * from lfc_testing.lfcategory;
insert into lfsubcategory select * from lfc_testing.lfsubcategory;
update lfcategory set companycode = 'F9';

alter table lfitem add column removalReason varchar(200);
alter table lffound add receiptFileName varchar(255) default null;