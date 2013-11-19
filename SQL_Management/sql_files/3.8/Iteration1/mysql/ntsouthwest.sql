update systemcomponents set component_desc='Ability to reopen a closed missing articles incident' where component_name='Reopen Pilferage';
update systemcomponents set component_desc='View all incoming incidents/claims that are open - Missing Articles type only' where component_name='Incoming Incidents Type Pilferage';
delete from group_component_policy where component_id=627;

insert into properties (keyStr,valueStr) VALUES ('cs2.expense.paycode','OTH');
insert into properties (keyStr,valueStr) VALUES ('cs2.expense.status','53');
insert into properties (keyStr,valueStr) VALUES ('cs2.expense.paytype','8');
insert into properties (keyStr,valueStr) VALUES ('cs2.expense.currency','USD');

delete from expensetype;
### TESTING AND TRAINING
#insert into expensetype select * from nk_training.expensetype;
### PRODUCTION
#insert into expensetype select * from nk_production.expensetype;

insert into properties (keyStr,valueStr) VALUES ('bsdi.address.endpoint','https://test.rbags.com/BDO.WebService/BaggageDeliveryStatusInfoService.asmx');
insert into properties (keyStr,valueStr) VALUES ('max.pnr.last.days','14');
insert into properties (keyStr,valueStr) VALUES ('bso.ws.timeout','5000');

update status set description='Passenger Pick Up' where Status_ID=42;
insert into status (status_ID, description, table_ID) values ('99','Other','9');
delete from status where status_ID=43;
delete from status where status_ID=44;

delete from properties where keystr = 'bag.itin.not.required';

update company_specific_variable set issuance_edit_last_x_days=45 where companycode_id='WN';