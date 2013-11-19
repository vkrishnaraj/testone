# SOUTHWEST TEST DATA
insert into svc_profile values (20, 'Southwest Testing', 'WN');
insert into svc_user values (null, 'WNpass1!', 'southwest_test', 20);
insert into svc_profile_permission values (20, 1, 'GET_PREPOP_DATA');
insert into svc_profile_permission values (20, 1, 'GET_FLIGHT_DATA');
insert into svc_profile_parameters values (20, 'https://webservices.swab2bqa.com/btws/itest', 'RESERVATION_ENDPOINT');
insert into svc_profile_parameters values (20, 'CEBS', 'RESERVATION_SYSTEM_TYPE');
insert into svc_profile_parameters values (20, 'D:/security/truststore.jks', 'TRUSTSTORE');
insert into svc_profile_parameters values (20, 'changeit', 'TRUSTPASS');
insert into svc_profile_parameters values (20, 'D:/security/keystore.jks', 'KEYSTORE');
insert into svc_profile_parameters values (20, 'changeit', 'KEYPASS');

# SOUTHWEST PROD DATA
insert into svc_profile values (21, 'Southwest Production', 'WN');
insert into svc_user values (null, 'WNpass1!', 'southwest_prod', 21);
insert into svc_profile_permission values (21, 1, 'GET_PREPOP_DATA');
insert into svc_profile_permission values (21, 1, 'GET_FLIGHT_DATA');
insert into svc_profile_parameters values (21, 'https://webservices.swab2bqa.com/btws/itest', 'RESERVATION_ENDPOINT');
insert into svc_profile_parameters values (21, 'CEBS', 'RESERVATION_SYSTEM_TYPE');
insert into svc_profile_parameters values (21, 'D:/security/truststore.jks', 'TRUSTSTORE');
insert into svc_profile_parameters values (21, 'changeit', 'TRUSTPASS');
insert into svc_profile_parameters values (21, 'D:/security/keystore.jks', 'KEYSTORE');
insert into svc_profile_parameters values (21, 'changeit', 'KEYPASS');