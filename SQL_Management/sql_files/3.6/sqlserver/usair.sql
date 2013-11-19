insert into PROPERTIES (keyStr,valueStr) VALUES ('populate.storage.location','1');

insert into group_component_policy (component_id,group_id) VALUES (707,select usergroup_id from agent where username = 'ntadmin' and companycode_id = 'US');#add new contents
insert into group_component_policy (component_id,group_id) VALUES (708,select usergroup_id from agent where username = 'ntadmin' and companycode_id = 'US');#delete bagtags
insert into group_component_policy (component_id,group_id) VALUES (511,select usergroup_id from agent where username = 'ntadmin' and companycode_id = 'US');#clone ohd