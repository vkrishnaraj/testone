insert into template_var (displayTag,associatedClass) VALUES 
   ('FullAddress' ,'Incident'), 
   ('FullAddress','Claim'),
   ('FullAddress','FoundItem'),
   ('Country','Incident'), 
   ('Country','Claim'), 
   ('Country','FoundItem'),
   ('Province','Incident'), 
   ('Province','Claim'), 
   ('Province','FoundItem');
   
   CREATE TABLE `passengerexp` (   
`PassengerExp_ID` int(11) NOT NULL AUTO_INCREMENT,   
`expensepayout_ID` varchar(13) NOT NULL DEFAULT '',   
`firstname` varchar(20) DEFAULT NULL,   
`middlename` varchar(20) DEFAULT NULL,   
`lastname` varchar(20) DEFAULT NULL,  
`address1` varchar(50) DEFAULT NULL,   
`address2` varchar(50) DEFAULT NULL,   
`city` varchar(50) DEFAULT NULL,   
`state_ID` varchar(2) DEFAULT NULL,   
`province` varchar(100) DEFAULT '',   
`zip` varchar(11) DEFAULT NULL,   
`homephone` varchar(50) DEFAULT NULL,
`workphone` varchar(50) DEFAULT NULL,
`mobile` varchar(50) DEFAULT NULL,
`email` varchar(100) DEFAULT NULL,     
`countrycode_ID` varchar(3) DEFAULT NULL, 
PRIMARY KEY (`PassengerExp_ID`),   
KEY `expensepayout_ID` (`expensepayout_ID`) ) ;
    
alter table issuance_category add limitByPassenger bit not null default 0;