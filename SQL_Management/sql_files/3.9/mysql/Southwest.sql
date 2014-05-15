insert into properties (keyStr,valueStr) VALUES ('loss.code.bags.lock','1');
insert into properties (keyStr,valueStr) VALUES ('loss.code.month.day.locked','5');

-- NT-2567
update activity set description ='NETTRACER FS COPY RCVD' where code = '79';
update activity set description ='NETTRACER FS SENT - AWAITING FILE(S)' where code = '00';

insert into activity (code, description, display) values ( '96', 'INBOUND CLAIM FORM', 1);
insert into activity (code, description, display) values ( '659', 'NetTracer FS Match', 1);
insert into activity (code, description, display) values ( '257', 'Web Secondary Correspondence', 1);

update activity set display=0 where code in ('601', '9206', '107','288', '223', '592', '590', 'CBP2', 'CBP1', '580', '83', '500', '01F', '578', '579', '208', '210', '278', '301', '312', '224', '289', '221','597','220','238','235','222','302','252','244','232','311','315','112','94','01','102','570','563','562', '85V');

update activity set description=concat(code, ' - ', description);
