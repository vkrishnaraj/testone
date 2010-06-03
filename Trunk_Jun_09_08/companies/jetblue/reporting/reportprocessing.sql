alter table status add column locale varchar(2) NOT NULL default 'en' AFTER table_ID;
alter table company_irregularity_codes add column locale varchar(2) NOT NULL default 'en' AFTER description;
alter table Station add column locale varchar(2) NOT NULL default 'en' AFTER operation_hours;


DROP TABLE IF EXISTS p0;

CREATE TABLE p0 (

        lastname varchar(20),

        firstname varchar(20),

        paxid int(11),

        incident_id varchar(13)

);

CREATE INDEX p0index on p0 (incident_id);

insert into p0 (lastname, firstname, incident_id, paxid) SELECT p2.lastname,

                         p2.firstname, p2.incident_id, p1.passenger_ID

                         FROM passenger  p2 , (select min(passenger_id) as passenger_id, incident_id

                                                from passenger

                                                group by incident_id) p1

                        WHERE p2.passenger_id = p1.passenger_id and

                              p2.lastname IS NOT NULL

                              AND p2.lastname != ''

                        GROUP BY p2.incident_id;


DROP TABLE IF EXISTS itin0;

CREATE TABLE itin0 (

        airline varchar(3),

        flightnum varchar(4),

        incident_id varchar(13)

);

CREATE INDEX itin0index on itin0 (incident_id);

INSERT INTO itin0 (airline, flightnum, incident_ID) SELECT airline, flightnum, incident_ID

                            FROM itinerary

                           WHERE itinerarytype = 0;



###


DROP TABLE IF EXISTS cc1;

CREATE TABLE cc1 (

        claimchecknums varchar(1024),

        incident_id varchar(13)

);

CREATE INDEX ccindex on cc1 (incident_id);


insert into cc1 (incident_id, claimchecknums) SELECT incident_id,

                        group_concat(claimchecknum) claimchecknums

                   FROM incident_claimcheck

                 GROUP BY incident_id;



###


DROP TABLE IF EXISTS cc;

CREATE TABLE cc (

        claimchecknums varchar(512),

        incident_id varchar(13)

);

CREATE INDEX ccindex on cc (incident_id);


insert into cc (incident_id, claimchecknums) SELECT incident_id,

                        left(claimchecknums, 512)

                   FROM cc1;





DROP TABLE IF EXISTS item1;

CREATE TABLE item1 (

        bagcolors varchar(255),

        bagtypes varchar(255),

        incident_id varchar(13)

);

CREATE INDEX item1index on item1 (incident_id);
SET SESSION group_concat_max_len = 255;
insert into item1 (incident_id, bagcolors, bagtypes) (SELECT incident_id,

                     group_concat(color) bagcolors,

                     group_concat(bagtype) bagtypes

                FROM item

              GROUP BY incident_id) ;



DROP TABLE IF EXISTS routes0;

CREATE TABLE routes0 (
        route varchar(128),
        origin varchar(32),
        dest varchar(32),
        FinalSegmentDateTime DATETIME,
        incident_id varchar(13)
);

SET SESSION group_concat_max_len = 128;
CREATE INDEX routes0index on routes0 (incident_id);
insert into routes0 (incident_id, route, origin, dest, FinalSegmentDateTime)
                      (SELECT incident_id,
                              route,
                              if (route NOT LIKE '-%'
                                  AND CHAR_LENGTH(route) > 1,
                                  SUBSTR(route, 1, 3),
                                  'No Data'
                              )
                                 origin,
                              if (route NOT LIKE '%-'
                                  AND CHAR_LENGTH(route) > 1,
                                  SUBSTR(route, -3),
                                  'No Data'
                              )
                                 dest,
                              FinalSegmentDateTime
                         FROM (SELECT incident_id, GROUP_CONCAT(legs ORDER BY incident_id ASC, departdate ASC, schdeparttime ASC, itinerary_id ASC) route,
                         if (schdeparttime is NULL, departdate, MAX(ADDTIME(departdate, schdeparttime))) AS FinalSegmentDateTime
                                 FROM (SELECT incident_id,itinerary_id,departdate,schdeparttime,
                                              concat(legfrom, '-', legto) legs
                                         FROM itinerary
                                        WHERE itinerarytype = 0
                                       ORDER BY incident_id,
                                                departdate ASC,
                                                schdeparttime ASC,
                                                itinerary_id ASC) itin1
                               GROUP BY incident_id) routes) ;


SET SESSION group_concat_max_len = 1024;

DROP TABLE IF EXISTS pawob_report;

CREATE TABLE pawob_report (
  name varchar(255),
  incident_id varchar(13),
	date_created  datetime,
  time_created time,
	flights varchar(255),
	destination varchar(32),
	origin varchar(32),
	created_station varchar(10),
	status varchar(100),
	charge_city varchar(10),
	charge_code int(11),
	pnr varchar(10),
	bag_tags varchar(1024),
	checkin_location varchar(25),
	bag_colors varchar(255),
	bag_types varchar(255),
	FinalSegmentDateTime DATETIME,
	Assigned_Station varchar(10)
);

insert into pawob_report
SELECT concat(p0.lastname, ', ', p0.firstname) AS name,
       i.incident_ID 'pawob #',
       ADDTIME(i.CREATEDATE, i.CREATETIME) AS 'date created',
       i.createtime 'time created',
       group_concat(concat(itin0.airline, itin0.flightnum)) flights,
       routes0.dest destination,
       routes0.origin origin,
       station1.stationcode 'created station',
       status.description status,
       station2.stationcode 'charge city',
       i.loss_code 'charge code',
       i.recordlocator pnr,
       cc.claimchecknums 'bag tags',
       CASE i.checkedlocation
          WHEN 0 THEN  ''
          WHEN 1 THEN 'curbside'
          WHEN 2 THEN 'ticket counter'
          WHEN 3 THEN 'gate'
          WHEN 4 THEN 'remote'
          WHEN 5 THEN 'plane side'
          WHEN 6 THEN 'online'
          WHEN 7 THEN 'kiosk'
          WHEN 8 THEN 'dme'
          ELSE ''
       END
          'check-in location',
       item1.bagcolors 'bag colors',
       item1.bagtypes 'bag types',
       routes0.FinalSegmentDateTime,
       station3.stationcode 'Assigned Station'
  FROM                      incident i
                         LEFT OUTER JOIN
                            p0
                         ON i.incident_Id = p0.incident_id
                      LEFT OUTER JOIN
                         itin0
                      ON i.incident_id = itin0.incident_id
                   LEFT OUTER JOIN
                     routes0
                   ON i.incident_id = routes0.incident_ID
                LEFT OUTER JOIN
                   station station2
                ON i.faultstation_ID = station2.station_id
             LEFT OUTER JOIN
                cc
             ON i.incident_id = cc.incident_id
          LEFT OUTER JOIN
             item1
          ON i.incident_id = item1.incident_id
       LEFT OUTER JOIN
          station station1
       ON station1.station_id = i.stationcreated_id
       LEFT OUTER JOIN
          station station3
       ON station3.station_id = i.stationassigned_ID,
       status
 WHERE i.status_id = status.status_ID
 GROUP BY i.incident_ID,
         i.createdate,
         i.createtime,
         routes0.dest,
         routes0.origin,
         station1.stationcode,
         status.description,
         station2.stationcode,
         i.loss_code,
         i.recordlocator,
         i.checkedlocation;
