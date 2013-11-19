#using worldtracer qa for testing, roll back when done

update svc_profile_parameters set element = 'https://webservice-qa.worldtracer.aero/DelayedBagService/0.1' where parameter_type = 'DELAYED_ENDPOINT';
update svc_profile_parameters set element = 'https://webservice-qa.worldtracer.aero/InboxService/0.1' where parameter_type = 'INBOX_ENDPOINT';
update svc_profile_parameters set element = 'https://webservice-qa.worldtracer.aero/OnhandBagService/0.1' where parameter_type = 'ONHAND_ENDPOINT';
update svc_profile_parameters set element = 'https://webservice-qa.worldtracer.aero/RushBagService/0.1' where parameter_type = 'RUSH_ENDPOINT';

#rollback
update svc_profile_parameters set element = 'https://webservice.worldtracer.aero/DelayedBagService/0.1' where parameter_type = 'DELAYED_ENDPOINT';
update svc_profile_parameters set element = 'https://webservice.worldtracer.aero/InboxService/0.1' where parameter_type = 'INBOX_ENDPOINT';
update svc_profile_parameters set element = 'https://webservice.worldtracer.aero/OnhandBagService/0.1' where parameter_type = 'ONHAND_ENDPOINT';
update svc_profile_parameters set element = 'https://webservice.worldtracer.aero/RushBagService/0.1' where parameter_type = 'RUSH_ENDPOINT';