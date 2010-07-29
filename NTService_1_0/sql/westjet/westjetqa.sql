## INSTANCE ENTRIES ##

insert into properties (keyStr, valueStr) values ('nt.central.endpoint', 'http://75.126.190.160/NTServices_1_0/services/WorldTracerService');
insert into properties (keyStr, valueStr)values ('nt.central.username', 'westjet_qa');
insert into properties (keyStr, valueStr)values ('nt.central.password', 'password');

## SERVICE ENTRIES ##
insert into svc_profile (name, airline) values ('WestJet QA', 'WS');
insert into svc_user (password, username, profile_id) values ('password', 'westjet_qa', 8);
insert into svc_profile_permission (permission_id, element, permission_type) values (8, 1, 'WORLDTRACER');
insert into svc_profile_permission (permission_id, element, permission_type) values (8, 1, 'GET_PREPOP_DATA');
#insert into svc_profile_permission (permission_id, element, permission_type) values (8, 1, 'WRITE_REMARK');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WS_QA', 'CLIENT_ENVIRONMENT');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'ss', 'ONHAND_ENDPOINT');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'https://webservice-qa.worldtracer.aero/DelayedBagService/0.1', 'DELAYED_ENDPOINT');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'https://webservice-qa.worldtracer.aero/RushBagService/0.1', 'RUSH_ENDPOINT');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, '', 'RESERVATION_ENDPOINT');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_GET_AHL');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_CREATE_AHL');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_AMEND_AHL');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_CLOSE_AHL');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_SUSPEND_AHL');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_REINSTATE_AHL');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_GET_OHD');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_CREATE_OHD');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_AMEND_OHD');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_CLOSE_OHD');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_FORWARD_OHD');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_CREATE_BDO');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_ERASE_ACTION_FILE');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_ACTION_FILE_COUNTS');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_ACTION_FILE_SUMMARY');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_ACTION_FILE_DETAILS');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_SEND_FORWARD_MESSAGE');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_PLACE_ACTION_FILE');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_ESTABLISH');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_REINSTATE_OHD');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_SUSPEND_OHD');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_REQUEST_OHD');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_REQUEST_QOHD');
insert into svc_profile_parameters (profile_id, element, parameter_type) values (8, 'WEBSERVICES','WTSM_SEND_QOH');



	create table WT_WS_TRANS_LOG (
        id bigint not null auto_increment,
        gmttime DATETIME,
		description varchar(60) not null,
        primary key (id)
    );
