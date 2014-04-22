insert into template_var (displayTag,associatedClass) values ('Salutation','Incident');

insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (689 ,'Edit Locked Loss Codes' ,'Allows User to Edit Loss Codes and Stations that are Locked' ,1100 ,'' ,0 ,99 ,0);

update expensepayout set paytype = 'INVOICE' where paytype = 'DRAFT' and issuanceItem = 1;

alter table agent add loadpercentage double default 0.0;
alter table agent add inbound bit default 0;
alter table agent add acaa bit default 0;
alter table agent add damaged bit default 0;
