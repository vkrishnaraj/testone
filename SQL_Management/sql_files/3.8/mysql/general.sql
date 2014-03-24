insert into category values (null, 'Bagbuzz', 100, 1);
alter table bagbuzz add category_ID bigint(20) not null;
update bagbuzz b, category c set b.category_ID = c.id where c.type = 100 and c.categoryVal = 1;
update systemcomponents set component_action_link = 'bagbuzzsearch.do?admin_view=1', display = 1, sort_order = 30 where component_name = "BagBuzzAdmin" and component_id = 504;
insert into properties (keyStr,valueStr) VALUES ('bagbuzz.max.categories','5');
