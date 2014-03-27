insert into category (description,type,categoryVal) values ('Bagbuzz', 100, 1);
alter table bagbuzz add category_ID bigint;

GO

update bagbuzz set category_ID = 0;--ADD to deployment plan determine correct value

update systemcomponents set component_action_link = 'bagbuzzsearch.do?admin_view=1', display = 1, sort_order = 30 where component_name = 'BagBuzzAdmin' and component_id = 504;
insert into properties (keyStr,valueStr) VALUES ('bagbuzz.max.categories','5');

GO
alter table bagbuzz alter column category_ID bigint not null;