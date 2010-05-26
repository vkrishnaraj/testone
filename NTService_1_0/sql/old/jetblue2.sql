### INSTANCE
insert into properties (keyStr, valueStr) values ('nt.central.endpoint', 'https://jetblue-qa.nettracer.aero//NTServices_1_0/services/WorldTracerService');
insert into properties (keyStr, valueStr)values ('nt.central.username', 'jetblue_test');
insert into properties (keyStr, valueStr)values ('nt.central.password', 'password');

### CRON

    create table world_tracer_web_account (
        id bigint not null auto_increment,
        companyCode varchar(2),
        host varchar(128),
        isCaptchaRequired bit not null,
        isFrontend bit not null,
        isTrainingMode bit not null,
        lastReset datetime,
        password varchar(20),
        username varchar(20),
        profile_id bigint,
        primary key (id)
    );
    
    insert into profile_parameters (profile_id, element, parameter_type) VALUES
('1', 'WTRWEB', 'WTSM_AMEND_AHL'), 
('1', 'WTRWEB', 'WTSM_AMEND_OHD'), 
('1', 'WTRWEB', 'WTSM_CLOSE_AHL'), 
('1', 'WTRWEB', 'WTSM_CLOSE_OHD'), 
('1', 'WTRWEB', 'WTSM_CREATE_AHL'), 
('1', 'WTRWEB', 'WTSM_CREATE_OHD'), 
('1', 'WTRWEB', 'WTSM_ESTABLISH'), 
('1', 'WTRWEB', 'WTSM_FORWARD_OHD'), 
('1', 'WTRWEB', 'WTSM_REINSTATE_AHL'), 
('1', 'WTRWEB', 'WTSM_REINSTATE_OHD'), 
('1', 'WTRWEB', 'WTSM_SEND_FORWARD_MESSAGE'), 
('1', 'WTRWEB', 'WTSM_SUSPEND_AHL'), 
('1', 'WTRWEB', 'WTSM_SUSPEND_OHD');

### HERE


		insert into profile_permission (permission_id, element, permission_type) VALUES ('1', 1, 'WORLDTRACER');


    insert into world_tracer_web_account (id , companyCode, host, isCaptchaRequired , isFrontend , isTrainingMode , lastReset, password, username, profile_id) VALUES
	('1', 'B6', 'wtrweb.worldtracer.aero', 1, 1, 1, NULL, 'Las Vegas1', 'user01', '1');

	insert into world_tracer_web_account (id , companyCode, host, isCaptchaRequired , isFrontend , isTrainingMode , lastReset, password, username, profile_id) VALUES
	('2', 'B6', 'wtrweb.worldtracer.aero', 1, 1, 1, NULL, 'Las Vegas1', 'user02', '1');

    alter table world_tracer_web_account 
        add index FK5944409D1344B8D4 (profile_id), 
        add constraint FK5944409D1344B8D4 
        foreign key (profile_id) 
        references profile (id);

        
    create table WT_WEB_TRANS_LOG (
        id bigint not null auto_increment,
        gmttime DATETIME,
        username varchar(20) not null,
		description varchar(60) not null,
		uri varchar(100) not null,
        primary key (id)
    );
    
        create table world_tracer_ishares_account (
        id bigint not null auto_increment,
        companyCode varchar(2),
        host varchar(128),
        lastReset datetime,
        password varchar(20),
        sine varchar(20),
        username varchar(20),
        profile_id bigint,
        primary key (id)
    );
    
    alter table world_tracer_ishares_account 
    add index FK18D8F0E61344B8D4 (profile_id), 
    add constraint FK18D8F0E61344B8D4 
    foreign key (profile_id) 
    references profile (id);

    
	create table WT_ISHARES_TRANS_LOG (
        id bigint not null auto_increment,
        gmttime DATETIME,
        username varchar(20) not null,
		description varchar(60) not null,
		uri varchar(100) not null,
        primary key (id)
    );
    
    alter table profile add airline varchar(2);
    

CREATE TABLE LOOKUP_AIRLINE_CODES (
	Airline_2_Character_Code Varchar(2) NOT NULL,
	Airline_3_Digit_Ticketing_Code Varchar(3) NOT NULL
);

INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('001', 'AA');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('002', '2G');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('004', 'BV');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('005', 'CO');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('006', 'DL');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('009', 'Z5');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('010', '2F');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('011', 'M9');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('012', 'NW');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('013', 'GY');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('014', 'AC');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('015', 'TW');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('016', 'UA');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('018', 'HO');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('019', '6D');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('020', 'LH');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('021', 'V3');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('022', '5U');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('023', 'FX');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('024', 'EA');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('025', 'RU');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('026', '6W');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('027', 'AS');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('029', '3K');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('030', 'YT');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('031', 'PW');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('032', 'ZU');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('033', '7Q');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('034', 'GC');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('035', '4C');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('036', 'EC');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('037', 'US');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('038', 'TQ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('039', 'G7');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('040', '5K');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('041', 'JQ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('042', 'RG');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('043', 'KA');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('044', 'AR');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('045', 'LA');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('047', 'TP');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('048', 'CY');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('049', 'QW');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('050', 'OA');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('051', 'LB');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('052', 'RW');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('053', 'EI');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('055', 'AZ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('056', 'YK');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('057', 'AF');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('058', 'IC');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('059', 'DF');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('060', 'YC');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('061', 'HM');

INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('063', 'SB');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('064', 'OK');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('065', 'SV');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('067', 'Y4');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('068', 'TM');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('069', 'MJ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('070', 'RB');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('071', 'ET');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('072', 'GF');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('073', 'IA');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('074', 'KL');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('075', 'IB');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('076', 'ME');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('077', 'MS');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('078', 'JR');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('079', 'PR');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('080', 'LO');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('081', 'QF');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('082', 'SN');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('083', 'SA');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('084', 'FT');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('086', 'NZ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('088', '2B');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('089', '9S');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('090', 'IT');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('091', 'DP');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('092', 'RK');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('093', 'KD');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('094', 'C4');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('095', '6A');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('096', 'IR');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('098', 'AI');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('099', '9S');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('101', 'EN');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('103', 'D2');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('104', 'EW');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('105', 'AY');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('106', 'BW');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('108', 'FI');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('110', '7Q');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('111', 'UP');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('112', 'CK');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('113', 'GW');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('114', 'LY');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('115', 'JU');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('116', '8W');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('117', 'SK');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('118', 'DT');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('120', 'JS');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('121', 'ZY');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('123', 'ON');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('124', 'AH');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('125', 'BA');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('126', 'GA');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('129', 'MP');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('131', 'JL');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('132', 'MX');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('133', 'LR');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('134', 'AV');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('135', 'VT');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('136', 'CU');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('139', 'AM');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('140', 'LI');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('142', 'KF');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('143', 'AU');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('146', 'XK');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('147', 'AT');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('148', 'LN');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('149', 'LG');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('150', 'UG');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('151', 'R5');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('152', 'VH');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('154', 'BU');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('155', 'ES');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('156', '2I');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('157', 'QR');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('158', 'E7');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('160', 'CX');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('161', 'MN');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('162', 'PH');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('163', '3V');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('163', 'A5');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('164', '7L');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('165', 'JP');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('166', 'IK');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('167', 'QM');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('168', 'UM');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('170', 'GE');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('171', 'GT');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('172', 'CV');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('173', 'HA');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('174', 'MR');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('175', 'II');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('176', 'EK');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('177', '6F');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('178', 'OR');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('180', 'KE');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('181', 'Z6');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('182', 'MA');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('183', 'LC');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('186', 'SW');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('187', 'DW');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('188', 'P9');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('189', 'JI');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('190', 'TY');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('191', 'IG');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('192', 'PY');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('193', 'IE');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('194', 'KW');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('195', 'FV');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('197', 'TC');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('200', 'SD');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('201', 'JM');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('202', 'TA');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('203', '5J');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('204', 'T6');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('205', 'NH');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('207', '3S');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('208', 'B3');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('209', 'UB');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('210', '4B');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('211', '2P');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('213', 'Q4');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('214', 'PK');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('215', 'P7');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('217', 'TG');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('218', 'NF');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('219', 'YN');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('220', 'LH');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('221', 'AL');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('224', 'FR');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('225', 'HN');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('226', '2J');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('229', 'KU');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('230', 'CM');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('232', 'MH');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('233', 'YG');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('235', 'TK');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('236', 'BD');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('238', 'IZ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('239', 'MK');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('244', 'TN');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('245', '7F');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('246', 'UZ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('249', 'S3');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('250', 'HY');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('251', 'C9');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('252', 'PI');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('255', 'FG');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('257', 'OS');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('258', 'MD');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('259', 'FN');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('260', 'FJ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('262', 'U6');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('263', 'VA');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('265', 'EF');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('266', 'LT');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('267', 'BE');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('269', 'EQ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('271', 'W7');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('272', 'K4');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('275', '5L');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('277', 'XF');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('279', 'B6');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('281', 'RO');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('282', 'EM');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('285', 'RA');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('286', 'PU');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('287', '4N');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('288', 'LD');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('289', 'OM');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('290', 'BF');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('291', 'R2');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('294', 'T7');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('295', 'WM');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('296', 'CD');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('297', 'CI');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('298', 'UT');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('302', 'OO');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('303', 'ZW');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('305', 'B5');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('306', '9K');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('307', 'WE');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('308', 'V0');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('309', 'EX');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('312', 'IP');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('315', 'ZP');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('316', '5N');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('318', 'CC');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('319', '8D');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('323', 'QQ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('324', 'SC');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('325', 'J3');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('327', 'AQ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('331', 'S4');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('332', 'FL');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('334', 'MM');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('337', 'SY');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('338', 'ZD');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('339', 'KS');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('344', 'L2');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('345', 'NC');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('346', '9R');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('347', 'WP');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('348', 'KV');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('350', 'EE');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('352', 'A7');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('353', 'NU');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('355', 'E7');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('356', 'P6');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('357', '9C');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('358', 'GR');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('359', '3T');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('360', 'GG');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('362', 'Y7');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('363', 'RP');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('366', 'TZ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('367', 'IN');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('368', '7Y');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('369', '5Y');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('370', 'OP');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('371', '9D');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('373', '5R');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('373', 'NB');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('374', 'CC');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('378', 'KX');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('384', 'RQ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('385', 'E9');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('387', 'GQ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('389', 'Z4');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('390', 'A3');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('391', 'Q3');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('392', 'F2');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('393', '3I');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('397', 'G6');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('398', 'YR');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('399', 'QB');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('400', 'PF');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('401', 'HP');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('403', 'PO');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('404', 'JW');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('406', '5X');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('407', 'V7');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('409', 'YM');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('411', '3G');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('412', 'VI');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('414', 'AX');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('419', 'CF');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('420', 'GI');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('421', 'S7');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('422', 'F9');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('423', 'Er');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('424', 'VM');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('426', '9L');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('427', 'TX');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('430', '9E');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('433', 'ZA');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('434', 'L6');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('436', 'KJ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('440', 'WR');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('442', 'OF');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('444', 'GO');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('449', '3M');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('453', 'YX');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('454', '2U');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('455', 'NA');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('457', 'DQ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('460', 'EB');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('462', 'XL');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('463', 'ZP');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('464', 'EM');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('465', 'KC');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('467', 'T3');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('468', 'WO');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('471', 'ZV');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('473', 'SE');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('474', 'NT');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('479', 'ZH');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('481', 'QX');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('482', '3W');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('484', 'ED');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('487', 'NK');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('489', 'W8');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('494', 'EZ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('497', 'E3');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('499', '7B');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('502', 'WU');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('503', 'RZ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('510', 'JF');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('512', 'RJ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('513', '7G');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('515', 'SF');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('516', '6Z');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('517', '2F');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('518', '5T');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('519', 'FE');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('525', 'B7');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('526', 'WN');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('527', 'UC');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('529', 'A2');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('530', 'T0');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('531', 'US');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('532', 'E4');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('533', 'YV');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('534', 'WQ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('537', 'Q6');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('539', 'TH');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('542', 'T5');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('543', '9Q');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('544', 'LP');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('546', '8U');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('547', '2K');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('549', 'M3');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('550', 'BL');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('551', 'CT');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('555', 'SU');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('557', '8B');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('558', 'O8');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('560', 'H8');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('564', 'XQ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('566', 'PS');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('567', 'CE');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('568', 'LW');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('572', '9U');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('573', 'G7');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('580', 'RU');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('581', 'QU');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('582', 'XJ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('583', 'FQ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('586', 'BQ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('589', '9W');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('590', '2G');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('596', 'CS');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('598', 'HZ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('601', 'J7');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('603', 'UL');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('604', 'UY');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('606', 'A9');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('607', 'EY');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('610', 'KK');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('613', 'JB');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('614', 'IQ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('615', 'QY');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('617', 'X3');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('618', 'SQ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('620', 'BZ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('621', 'MZ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('622', 'MO');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('623', 'FB');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('625', 'D6');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('627', 'QV');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('628', 'B2');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('629', 'MI');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('630', 'DK');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('632', 'JV');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('634', '9M');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('635', 'IY');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('636', 'BP');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('637', 'B8');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('638', 'PJ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('639', 'LV');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('640', 'FA');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('642', '5D');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('643', 'KM');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('647', 'QI');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('649', '8C');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('656', 'PX');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('657', 'BT');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('662', 'UV');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('668', 'HQ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('670', 'UN');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('672', 'BI');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('673', 'VC');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('675', 'NX');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('677', 'PC');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('680', 'JK');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('681', 'T4');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('683', 'CL');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('685', 'NI');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('688', 'EG');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('689', 'WX');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('692', 'PZ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('694', 'YW');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('695', 'BR');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('696', 'VR');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('700', '5C');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('701', 'WF');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('703', 'NO');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('704', 'OL');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('705', 'S2');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('706', 'KQ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('708', 'JO');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('713', 'RH');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('714', 'V4');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('716', 'MB');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('717', 'R7');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('722', '8Z');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('723', 'QA');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('724', 'LX');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('729', 'QT');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('730', 'HT');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('731', 'MF');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('732', 'C7');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('733', 'D9');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('734', 'VO');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('737', 'SP');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('738', 'VN');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('739', '7V');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('740', 'NL');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('745', 'AB');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('746', '3N');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('747', 'YO');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('748', 'DU');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('749', '4Z');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('750', 'DB');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('752', 'JZ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('753', 'N3');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('754', 'BY');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('760', 'UU');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('761', '7P');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('762', '7T');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('763', 'M5');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('765', '7U');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('767', 'RC');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('768', 'EL');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('769', 'IF');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('771', 'J2');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('772', 'V6');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('774', 'FM');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('778', 'CW');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('780', 'Y9');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('781', 'MU');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('784', 'CZ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('786', 'VK');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('787', 'KB');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('789', 'E8');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('797', 'QS');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('798', 'KR');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('801', 'J8');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('803', 'AE');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('806', 'YI');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('807', 'AK');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('808', '7H');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('809', 'RE');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('810', 'M6');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('811', 'EU');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('815', 'EP');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('818', '6H');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('823', 'NN');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('825', 'F4');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('826', 'GS');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('827', 'ML');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('828', 'UF');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('829', 'PG');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('831', 'OU');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('832', 'GB');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('839', 'LS');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('841', 'C5');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('842', '5M');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('845', 'P5');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('846', 'ZK');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('848', 'UD');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('849', 'R3');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('851', 'HX');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('856', 'DJ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('857', 'LM');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('859', '8L');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('860', '2M');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('861', 'HK');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('862', 'EV');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('863', '9V');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('865', 'M7');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('866', 'BK');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('867', 'AP');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('868', 'V8');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('869', 'PT');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('870', 'VV');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('871', 'Y8');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('872', 'CH');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('874', 'TE');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('876', '3U');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('880', 'HU');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('881', 'DE');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('882', 'NY');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('883', '2Q');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('884', '5G');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('886', 'OH');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('887', 'ZG');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('896', 'VF');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('897', '7D');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('899', 'ZL');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('903', '4D');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('905', '8P');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('906', 'E5');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('907', 'QN');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('908', 'VC');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('910', 'WY');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('914', 'AG');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('919', 'S5');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('922', 'PC');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('923', 'SS');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('924', 'GR');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('927', 'WJ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('931', '4H');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('932', 'VS');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('933', 'KZ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('935', 'TL');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('940', 'V9');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('942', 'VW');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('943', 'VU');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('944', 'DI');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('945', '8R');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('946', 'V4');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('947', 'EZ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('951', '6B');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('955', '6Z');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('957', 'JJ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('960', 'OV');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('965', 'DO');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('969', 'CB');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('970', 'BX');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('972', 'LU');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('973', '3D');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('974', 'ZB');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('975', 'QZ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('976', 'QO');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('977', 'YS');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('978', 'VG');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('979', 'HV');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('980', 'KY');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('983', 'QK');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('986', 'Q2');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('987', 'G5');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('988', 'OZ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('989', 'IJ');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('991', 'D3');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('992', 'D5');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('994', 'ZE');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('996', 'UX');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('997', 'BG');
INSERT INTO LOOKUP_AIRLINE_CODES (Airline_3_Digit_Ticketing_Code, Airline_2_Character_Code)  VALUES ('999', 'CA');
delete from lookup_airline_codes where airline_2_character_code = 'LH' and airline_3_digit_ticketing_code = '020';
    