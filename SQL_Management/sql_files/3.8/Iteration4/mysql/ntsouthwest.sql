insert into properties (keyStr, valueStr) values ('bso.expense.process',1);
insert into properties (keystr, valuestr) values ('sso.auto.provision', 1);
insert into properties (keyStr, valueStr) values ('populate.itinerary.dateonly',1);
update properties set valueStr = '36' where keyStr = 'reservation.hours.backward';
update properties set valueStr = '12' where keyStr = 'reservation.hours.forward';

#ALREADY RAN
#NEW TESTING SERVICE FOR SOUTHWEST
#update properties set valueStr = 'http://drapp2.nettracer.aero:8680/NTServices_1_0/services/ReservationService_1_0' where keyStr = 'booking.nt.endpoint';
#NEW TRAINING SERVICE FOR SOUTHWEST
#update properties set valueStr = 'http://drapp2.nettracer.aero:8780/NTServices_1_0/services/ReservationService_1_0' where keyStr = 'booking.nt.endpoint';

insert into properties (keyStr, valueStr) values ('bagdrop.offset.start',8);
insert into properties (keyStr, valueStr) values ('bagdrop.offset.end',6);

#TESTING
#insert into properties (keystr, valuestr) values ('nt.central.endpoint','http://drapp2.nettracer.aero:8680/NTServices_1_0/services/WorldTracerService');
#insert into properties (keystr, valuestr) values ('nt.central.username','southwest_test');
#insert into properties (keystr, valuestr) values ('nt.central.password','WNpass1!');

#TRAINING
#insert into properties (keystr, valuestr) values ('nt.central.endpoint','http://drapp2.nettracer.aero:8780/NTServices_1_0/services/WorldTracerService');
#insert into properties (keystr, valuestr) values ('nt.central.username','southwest_test');
#insert into properties (keystr, valuestr) values ('nt.central.password','WNpass1!');

#PROD - TODO need to confirm prod central endpoint for swa
#insert into properties (keystr, valuestr) values ('nt.central.endpoint','');
#insert into properties (keystr, valuestr) values ('nt.central.username','southwest_prod');
#insert into properties (keystr, valuestr) values ('nt.central.password','WNpass1!');

insert into properties (keyStr, valueStr) values ('passenger.data.in.expense','1');

insert into properties (keyStr,valueStr) VALUES 
('default.autoprovision.timezone' ,'10'),
('current.autoprovision.timezone','10'),
('default.autoprovision.locale' ,'en'),
('current.autoprovision.locale' ,'en'),
('default.autoprovision.currency' ,'USD'),
('default.autoprovision.dateformat' ,'1'),
('default.autoprovision.timeformat' ,'1'),
('default.autoprovision.timeout' ,'30');

insert into properties (keyStr, valueStr) values ('create.call.task.timezone','CST');
