alter table lfitem add column removalReason varchar(200);
alter table lffound add receiptFileName varchar(255) default null;
