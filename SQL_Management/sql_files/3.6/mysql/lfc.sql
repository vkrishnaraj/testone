#Production credentials
insert into Properties (keystr,valuestr) values ('fedex.address.endpoint','https://ws.fedex.com:443/web-services');
insert into Properties (keystr,valuestr) values ('fedex.rate.endpoint','https://ws.fedex.com:443/web-services/rate');
insert into Properties (keystr,valuestr) values ('fedex.account.number','317377193');
insert into Properties (keystr,valuestr) values ('fedex.meter.number','105300996');
insert into Properties (keystr,valuestr) values ('fedex.password','YyTF7yNvHtmK9j534RwNJMU0y');
insert into Properties (keystr,valuestr) values ('fedex.key','xwi4IQt6OrVYmIXf');

insert into lfc_production.lfnamealias select * from fs_production.fsnamealias;

insert into properties (keyStr,valueStr) VALUES ('lf.tracing.weight.last.name','50');
insert into properties (keyStr,valueStr) VALUES ('lf.tracing.weight.nick.name','80');
insert into properties (keyStr,valueStr) VALUES ('lf.tracing.last.name','90');
insert into properties (keyStr,valueStr) VALUES ('lf.tracing.nick.name','90');
insert into properties (keyStr,valueStr) VALUES ('lf.tracing.bag.name','95');
insert into properties (keyStr,valueStr) VALUES ('lf.tracing.weight.initial.name','65');

insert into properties (keyStr,valueStr) VALUES ('transfirst.address.endpoint','https://ws.cert.processnow.com/portal/merchantframework/MerchantWebServices-v1');
insert into properties (keyStr,valueStr) VALUES ('transfirst.user.id','7777777953');
insert into properties (keyStr,valueStr) VALUES ('transfirst.reg.key','M4DQXY3GHSNK8LGH');


# To be run when LFC is good to switch to shipping and handling insert into properties (keyStr,valueStr) VALUES ('lf.email.online.billing','1'); 

insert into properties (keyStr,valueStr) VALUES ('lf.email.returnaddr.third','noreply@lostandfound.aero');
insert into properties (keyStr,valueStr) VALUES ('lf.email.returnaddr.forth','noreply@lostandfound.aero');
insert into properties (keyStr,valueStr) VALUES ('lf.email.returnaddr.fifth','noreply@lostandfound.aero');

update subcompany set email_path = '/AA/' where subcompanycode = 'AA';
update subcompany set email_subject = 'American Airlines Lost Item Report {LOSTID}' where subcompanycode = 'AA'; 
update subcompany set shippingSurcharge = 1 where subcompanycode='SWA';

insert into group_component_policy (component_id,group_id) VALUES (631,(select usergroup_id from agent where username = 'ntadmin' and companycode_id = 'LF'));#items ready for shipping LFC only