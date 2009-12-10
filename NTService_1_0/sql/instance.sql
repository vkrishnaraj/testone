insert into properties (keyStr, valueStr) values ('nt.central.endpoint', 'http://localhost:8080/NTServices_1_0/services/WorldTracerService');
insert into properties (keyStr, valueStr)values ('nt.central.username', 'westjet');
insert into properties (keyStr, valueStr)values ('nt.central.password', 'password');

####################

create table special_flag (
keyStr varchar(10) NOT NULL PRIMARY KEY,
valueStr varchar(100),
flagResetTimestamp datetime
);

GO

INSERT INTO special_flag (keyStr, valueStr, flagResetTimestamp) VALUES ('captcha', '1', '2009-11-24 13:22:00');

insert into systemcomponents values(324,'Captcha','to enable captcha feature',15,'wtCaptcha.do?taskManagerEntry=1',1,1,-1);

DECLARE @admin_grp int;
SET @admin_grp = (SELECT TOP 1 UserGroup_ID from usergroup where description = 'Admin' and companycode_ID='B6');
insert into group_component_policy (component_id,group_id) values (324, @admin_grp);