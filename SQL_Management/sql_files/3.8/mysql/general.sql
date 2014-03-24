insert into category values (1000, 'Bagbuzz', 100, 1);
alter table bagbuzz add category_ID bigint(20) not null default 1000;
update systemcomponents set component_action_link = 'bagbuzzsearch.do?admin_view=1', display = 1, sort_order = 30 where component_name = "BagBuzzAdmin" and component_id = 504;
insert into properties (keyStr,valueStr) VALUES ('bagbuzz.max.categories','5');
