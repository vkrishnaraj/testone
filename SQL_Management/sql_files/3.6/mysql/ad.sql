insert into properties (keyStr,valueStr) VALUES ('nt.res.password','ADpass1!');
insert into properties (keyStr,valueStr) VALUES ('nt.res.username','azul_prod');
insert into properties (keyStr,valueStr) VALUES ('nt.res.osi.on','1');
insert into properties (keyStr,valueStr) VALUES ('booking.nt.endpoint','http://service1.nettracer.aero/NTServices_1_0/services/ReservationService_1_0');
insert into properties (keyStr,valueStr) VALUES ('reservation.hours.backward','48');
insert into properties (keyStr,valueStr) VALUES ('reservation.hours.forward','24');

insert into group_component_policy (component_id,group_id) VALUES (707,(select usergroup_id from agent where username = 'ntadmin' and companycode_id = 'AD'));
insert into group_component_policy (component_id,group_id) VALUES (708,(select usergroup_id from agent where username = 'ntadmin' and companycode_id = 'AD'));
insert into group_component_policy (component_id,group_id) VALUES (511,(select usergroup_id from agent where username = 'ntadmin' and companycode_id = 'AD'));