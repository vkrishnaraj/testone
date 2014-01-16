insert into properties (keyStr, valueStr) values ('bso.expense.process',1);
insert into properties (keystr, valuestr) values ('sso.auto.provision', 1);
insert into properties (keyStr, valueStr) values ('populate.itinerary.dateonly',1);
update properties set valueStr = '36' where keyStr = 'reservation.hours.backward';
update properties set valueStr = '12' where keyStr = 'reservation.hours.forward';
