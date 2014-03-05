#CS&S HOU Callback 1
#Incident information
update incident set createdate=(Now()-interval 3 day) where recordlocator='LUV234';
update incident set createtime='07:00:00' where recordlocator='LUV234';
update incident set lastupdated=(Now()) where recordlocator='LUV234';
#Itinerary
update itinerary inner join incident on itinerary.incident_ID = incident.Incident_ID set departdate=(Now()-interval 3 day) where incident.recordlocator='LUV234';
update itinerary inner join incident on itinerary.incident_ID = incident.Incident_ID set arrivedate=(Now()-interval 3 day) where incident.recordlocator='LUV234';
#Remarks
update remark inner join incident on remark.Incident_ID = incident.Incident_ID set remark.createtime=STR_TO_DATE(CONCAT(incident.createdate,' ', incident.createtime),'%Y-%m-%d %H:%i:%s') where recordlocator='LUV234' and remarktext like '%PLZ CALL AND DELIV%';
update remark inner join incident on remark.Incident_ID = incident.Incident_ID set remark.createtime=(Now()-interval 1 day) where recordlocator='LUV234' and remarktext like '%CS&S Two Day%';
update remark inner join incident on remark.Incident_ID = incident.Incident_ID set remark.createtime=(Now()-interval 2 day) where recordlocator='LUV234' and remarktext like '%CS&S One Day%';

#CS&S HOU Callback 2
#Incident information
update incident set createdate=(Now()-interval 2 day) where recordlocator='LUV678';
update incident set createtime='07:00:00'  where recordlocator='LUV678';
update incident set lastupdated=(Now()) where recordlocator='LUV678';
#Itinerary
update itinerary inner join incident on itinerary.incident_ID = incident.Incident_ID set departdate=(Now()-interval 2 day) where incident.recordlocator='LUV678';
update itinerary inner join incident on itinerary.incident_ID = incident.Incident_ID set arrivedate=(Now()-interval 2 day) where incident.recordlocator='LUV678';
#Remarks
update remark inner join incident on remark.Incident_ID = incident.Incident_ID set remark.createtime=STR_TO_DATE(CONCAT(incident.createdate,' ', incident.createtime),'%Y-%m-%d %H:%i:%s') where recordlocator='LUV678' and remarktext like '%PLZ CALL AND DELIV%';
update remark inner join incident on remark.Incident_ID = incident.Incident_ID set remark.createtime=(Now()-interval 1 day) where recordlocator='LUV678' and remarktext like '%CS&S One Day%';

#CS&S HOU Callback 3
#Incident information
update incident set createdate=(Now()-interval 3 day) where recordlocator='LUV456';
update incident set createtime='07:00:00'  where recordlocator='LUV456';
update incident set lastupdated=(Now()) where recordlocator='LUV456';
#Itinerary
update itinerary inner join incident on itinerary.incident_ID = incident.Incident_ID set departdate=(Now()-interval 3 day) where incident.recordlocator='LUV456';
update itinerary inner join incident on itinerary.incident_ID = incident.Incident_ID set arrivedate=(Now()-interval 3 day) where incident.recordlocator='LUV456';
#Remarks
update remark inner join incident on remark.Incident_ID = incident.Incident_ID set remark.createtime=STR_TO_DATE(CONCAT(incident.createdate,' ', incident.createtime),'%Y-%m-%d %H:%i:%s') where recordlocator='LUV456' and remarktext like '%PLZ CALL WHEN BAGS%';
update remark inner join incident on remark.Incident_ID = incident.Incident_ID set remark.createtime=(Now()-interval 2 day) where recordlocator='LUV456' and remarktext like '%CS&S One Day%';
update remark inner join incident on remark.Incident_ID = incident.Incident_ID set remark.createtime=(Now()-interval 2 day) where recordlocator='LUV456' and remarktext like '%LM TO ADV 1 OF THE MISSING BAGS IS HERE.%';
update remark inner join incident on remark.Incident_ID = incident.Incident_ID set remark.createtime=(Now()-interval 1 day) where recordlocator='LUV456' and remarktext like '%CS&S Two Day%';
update remark inner join incident on remark.Incident_ID = incident.Incident_ID set remark.createtime=(Now()) where recordlocator='LUV456' and remarktext like '%Assigned%';

#CS&S OKC Callback 1
#Incident information
update incident set createdate=(Now()-interval 3 day) where recordlocator='LUV345';
update incident set createtime='07:00:00' where recordlocator='LUV345';
update incident set lastupdated=(Now()) where recordlocator='LUV345';
#Itinerary
update itinerary inner join incident on itinerary.incident_ID = incident.Incident_ID set departdate=(Now()-interval 3 day) where incident.recordlocator='LUV345';
update itinerary inner join incident on itinerary.incident_ID = incident.Incident_ID set arrivedate=(Now()-interval 3 day) where incident.recordlocator='LUV345';
#Remarks
update remark inner join incident on remark.Incident_ID = incident.Incident_ID set remark.createtime=STR_TO_DATE(CONCAT(incident.createdate,' ', incident.createtime),'%Y-%m-%d %H:%i:%s') where recordlocator='LUV345' and remarktext like '%PLZ CALL AND DELIV%';
update remark inner join incident on remark.Incident_ID = incident.Incident_ID set remark.createtime=(Now()-interval 1 day) where recordlocator='LUV345' and remarktext like '%CS&S Two Day%';
update remark inner join incident on remark.Incident_ID = incident.Incident_ID set remark.createtime=(Now()-interval 2 day) where recordlocator='LUV345' and remarktext like '%CS&S One Day%';

#CS&S OKC Callback 2
#Incident information
update incident set createdate=(Now()-interval 2 day) where recordlocator='LUV890';
update incident set createtime='07:00:00'  where recordlocator='LUV890';
update incident set lastupdated=(Now()) where recordlocator='LUV890';
#Itinerary
update itinerary inner join incident on itinerary.incident_ID = incident.Incident_ID set departdate=(Now()-interval 2 day) where incident.recordlocator='LUV890';
update itinerary inner join incident on itinerary.incident_ID = incident.Incident_ID set arrivedate=(Now()-interval 2 day) where incident.recordlocator='LUV890';
#Remarks
update remark inner join incident on remark.Incident_ID = incident.Incident_ID set remark.createtime=STR_TO_DATE(CONCAT(incident.createdate,' ', incident.createtime),'%Y-%m-%d %H:%i:%s') where recordlocator='LUV890' and remarktext like '%PLZ CALL AND DELIV%';
update remark inner join incident on remark.Incident_ID = incident.Incident_ID set remark.createtime=(Now()-interval 1 day) where recordlocator='LUV890' and remarktext like '%CS&S One Day%';

#CS&S OKC Callback 3
#Incident information
update incident set createdate=(Now()-interval 3 day) where recordlocator='LUV567';
update incident set createtime='07:00:00' where recordlocator='LUV567';
update incident set lastupdated=(Now()) where recordlocator='LUV567';
#Itinerary
update itinerary inner join incident on itinerary.incident_ID = incident.Incident_ID set departdate=(Now()-interval 3 day) where incident.recordlocator='LUV567';
update itinerary inner join incident on itinerary.incident_ID = incident.Incident_ID set arrivedate=(Now()-interval 3 day) where incident.recordlocator='LUV567';
#Remarks
update remark inner join incident on remark.Incident_ID = incident.Incident_ID set remark.createtime=STR_TO_DATE(CONCAT(incident.createdate,' ', incident.createtime),'%Y-%m-%d %H:%i:%s') where recordlocator='LUV567' and remarktext like '%PLZ CALL WHEN BAGS%';
update remark inner join incident on remark.Incident_ID = incident.Incident_ID set remark.createtime=(Now()-interval 2 day) where recordlocator='LUV567' and remarktext like '%CS&S One Day%';
update remark inner join incident on remark.Incident_ID = incident.Incident_ID set remark.createtime=(Now()-interval 2 day) where recordlocator='LUV567' and remarktext like '%LM TO ADV 1 OF THE MISSING BAGS IS HERE.%';
update remark inner join incident on remark.Incident_ID = incident.Incident_ID set remark.createtime=(Now()-interval 1 day) where recordlocator='LUV567' and remarktext like '%CS&S Two Day%';
update remark inner join incident on remark.Incident_ID = incident.Incident_ID set remark.createtime=(Now()) where recordlocator='LUV567' and remarktext like '%Assigned%';
