update properties set valueStr = 'flyafrica_prod' where keyStr = 'nt.res.username';
update properties set valueStr = 'Z7pass1!' where keyStr = 'nt.res.password';
update properties set valuestr = '0' where keystr = 'ntfs.user';

insert into properties (keyStr,valueStr) VALUES ('nt.res.osi.on','1');

insert into group_component_policy (component_id,group_id) VALUES (707,(select usergroup_id from agent where username = 'ntadmin' and companycode_id = 'Z7'));#add new contents
insert into group_component_policy (component_id,group_id) VALUES (708,(select usergroup_id from agent where username = 'ntadmin' and companycode_id = 'Z7'));#delete bagtags
insert into group_component_policy (component_id,group_id) VALUES (511,(select usergroup_id from agent where username = 'ntadmin' and companycode_id = 'Z7'));#clone ohd