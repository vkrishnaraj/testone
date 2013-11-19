alter table ohd add creationMethod integer default 0;
GO
update ohd set creationMethod = 0;