insert into svc_profile values (23, 'Volaris Testing', 'Y4');
insert into svc_user values (null, 'Y4pass1!', 'volaris_test', 23);
insert into svc_profile_permission values (23, 1, 'GET_PREPOP_DATA');
insert into svc_profile_permission values (23, 1, 'WRITE_REMARK');
insert into svc_profile_parameters values (23, 'https://Y4testr3xapi.navitaire.com/BookingManager.svc', 'RESERVATION_ENDPOINT');
insert into svc_profile_parameters values (23, 'D1007', 'RESERVATION_USER');
insert into svc_profile_parameters values (23, 'Volaris1912!', 'RESERVATION_PASS');
insert into svc_profile_parameters values (23, 'NAVITAIRE', 'RESERVATION_SYSTEM_TYPE');
insert into svc_profile_parameters values (23, 'https://Y4testr3xapi.navitaire.com/SessionManager.svc', 'RES_SESSION_ENDPOINT');