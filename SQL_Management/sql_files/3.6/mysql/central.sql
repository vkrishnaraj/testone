# AZUL TEST DATA
insert into svc_profile values (16, 'Azul Testing', 'AD');
insert into svc_user values (null, 'ADpass1!', 'azul_test', 16);
insert into svc_profile_permission values (16, 1, 'GET_PREPOP_DATA');
insert into svc_profile_permission values (16, 1, 'WRITE_REMARK');
insert into svc_profile_parameters values (16, 'https://adtestr3xapi.navitaire.com/BookingManager.svc', 'RESERVATION_ENDPOINT');
insert into svc_profile_parameters values (16, 'nettracer_integration', 'RESERVATION_USER');
insert into svc_profile_parameters values (16, 'Nettracer13*', 'RESERVATION_PASS');
insert into svc_profile_parameters values (16, 'NAVITAIRE', 'RESERVATION_SYSTEM_TYPE');
insert into svc_profile_parameters values (16, 'https://adtestr3xapi.navitaire.com/SessionManager.svc', 'RES_SESSION_ENDPOINT');

# FLYAFRICA TEST DATA
insert into svc_profile values (17, 'FlyAfrica Testing', 'Z7'); # 1D is temporary IATA code assigned by radixx. flyafrica is really z7
insert into svc_user values (null, 'Z7pass1!', 'flyafrica_test', 17);
insert into svc_profile_permission values (17, 1, 'GET_PREPOP_DATA');
insert into svc_profile_permission values (17, 1, 'WRITE_REMARK');
insert into svc_profile_parameters values (17, 'http://1d.connectpoint.uat.radixx.com/ConnectPoint.Reservation.svc', 'RESERVATION_ENDPOINT');
insert into svc_profile_parameters values (17, 'NetTracer_Z7_U', 'RESERVATION_USER');
insert into svc_profile_parameters values (17, 'N3tTr@c3r', 'RESERVATION_PASS');
insert into svc_profile_parameters values (17, 'RADIXX', 'RESERVATION_SYSTEM_TYPE');
insert into svc_profile_parameters values (17, 'http://1d.connectpoint.uat.radixx.com/ConnectPoint.Security.svc', 'RES_SESSION_ENDPOINT');
insert into svc_profile_parameters values (17, 'http://1d.tpapi.uat.radixx.com/radixxbooking.asmx', 'RES_RADIXX_ENDPOINT_2');

# AZUL PROD DATA
insert into svc_profile values (18, 'Azul Production', 'AD');
insert into svc_user values (null, 'ADpass1!', 'azul_prod', 18);
insert into svc_profile_permission values (18, 1, 'GET_PREPOP_DATA');
insert into svc_profile_permission values (18, 1, 'WRITE_REMARK');
insert into svc_profile_parameters values (18, 'https://adr3xapi.navitaire.com/BookingManager.svc', 'RESERVATION_ENDPOINT');
insert into svc_profile_parameters values (18, 'nettracer_integration', 'RESERVATION_USER');
insert into svc_profile_parameters values (18, 'n3tTr@c3r', 'RESERVATION_PASS');
insert into svc_profile_parameters values (18, 'NAVITAIRE', 'RESERVATION_SYSTEM_TYPE');
insert into svc_profile_parameters values (18, 'https://adr3xapi.navitaire.com/SessionManager.svc', 'RES_SESSION_ENDPOINT');

# FLYAFRICA PROD DATA
insert into svc_profile values (19, 'FlyAfrica Production', 'Z7'); # 1D is temporary IATA code assigned by radixx. flyafrica is really z7
insert into svc_user values (null, 'Z7pass1!', 'flyafrica_prod', 19);
insert into svc_profile_permission values (19, 1, 'GET_PREPOP_DATA');
insert into svc_profile_permission values (19, 1, 'WRITE_REMARK');
insert into svc_profile_parameters values (19, 'http://Z7.connectpoint.radixx.com/ConnectPoint.Reservation.svc', 'RESERVATION_ENDPOINT');
insert into svc_profile_parameters values (19, 'NetTracer_Z7_P', 'RESERVATION_USER');
insert into svc_profile_parameters values (19, 'NetTr@cer0724', 'RESERVATION_PASS');
insert into svc_profile_parameters values (19, 'RADIXX', 'RESERVATION_SYSTEM_TYPE');
insert into svc_profile_parameters values (19, 'http://Z7.connectpoint.radixx.com/ConnectPoint.Security.svc', 'RES_SESSION_ENDPOINT');
insert into svc_profile_parameters values (19, 'http://Z7.tpapi.radixx.com/radixxbooking.asmx', 'RES_RADIXX_ENDPOINT_2');