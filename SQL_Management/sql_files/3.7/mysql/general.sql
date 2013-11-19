create TABLE wtcompany (id bigint auto_increment not null, wtCompanyCode varchar(3), company_id varchar(3), companyName varchar(100), primary key(id));
insert into systemcomponents (component_id,component_name,component_desc,parent_component_id,component_action_link,display,sort_order,sort_group) VALUES (370  ,'Create WT Files for other Carriers'  ,'Allows users to create WorldTracer Files on behalf of other One World Carriers'  ,93  ,''  ,0  ,11  ,0);

alter table incident add column wtCompanyId varchar(3);
alter table incident add column wtStationId varchar(3);