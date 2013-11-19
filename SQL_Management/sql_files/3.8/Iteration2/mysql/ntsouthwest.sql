insert into links (description, link_address, image, companycode_id) values ('Southwest.com','http://www.southwest.com/','southwest.png','WN');
insert into links (description, link_address, image, companycode_id) values ('Lost & Found','https://live.lostandfound.aero/client/southwest/landing.do','southwest_lfc.png','WN');
insert into links (description, link_address, image, companycode_id) values ('TSA','http://www.tsa.gov/','TSA.jpg','WN');
insert into links (description, link_address, image, companycode_id) values ('Anywho.com','http://www.anywho.com/','anywho.gif','WN');
insert into links (description, link_address, image, companycode_id) values ('FedEx','http://www.fedex.com/us/','fedex.png','WN');
insert into links (description, link_address, image, companycode_id) values ('Overstock.com','http://www.overstock.com/','Overstock.png','WN');
insert into links (description, link_address, image, companycode_id) values ('MapQuest','http://www.mapquest.com/','mapquest.jpg','WN');
insert into links (description, link_address, image, companycode_id) values ('USPS Zip Code Lookup','https://tools.usps.com/go/ZipLookupAction!input.action','usps.png','WN');
insert into links (description, link_address, image, companycode_id) values ('UPS','http://www.ups.com/','ups.gif','WN');
insert into links (description, link_address, image, companycode_id) values ('Home Serv','http://www.rbags.com/BDOSystem/Login.aspx?ReturnUrl=%2fBDOSystem%2fdefault.aspx','home_serv.png','WN');

update company_specific_variable set auto_close_ohd_days_back=90 where companycode_id='WN';

# Production
#insert into properties (keystr,valuestr) values ("swa.service.address.endpoint","http://www.rbags.com/NetBags.WebService/SouthwestAirlines.asmx");
# TESTING
insert into properties (keystr,valuestr) values ("swa.service.address.endpoint","https://test.rbags.com/NetBags.WebService/SouthwestAirlines.asmx");


insert into generaldepreciationrules (lessTwentyDeprec,twentyOnefiftyDeprec,onefiftyDeprec,noDates,companyCode) VALUES (0,0,0,0,'WN');


## Do not change the spacing of the below statement as it will directly affect the layout of the PPU receipt!
insert into template (name,description,active,createDate,lastUpdated,data) values
('High Value PPU Receipt', 'Passenger pickup receipt for high value items as provided by Southwest Airlines.', 1, now(), now(), '<p>High Value Item Customer Pick-up</p>

<p>&nbsp;</p>

<p style="text-align: center"><strong>High Value Item&nbsp;Customer Pick-up</strong></p>

<p>Your signature below reflects that you have picked up the referenced item and are taking ownership of this item. The item is no longer the responsibility of Southwest Airlines.</p>

<table border="0" cellpadding="1" cellspacing="1" dir="ltr" style="width:100%">
	<tbody>
		<tr>
			<td>Item:</td>
			<td>{FoundItem.Item}</td>
		</tr>
		<tr>
			<td>Detailed&nbsp;Description:</td>
			<td>{FoundItem.Description}</td>
		</tr>
		<tr>
			<td>Item Color:</td>
			<td>{FoundItem.Color}</td>
		</tr>
		<tr>
			<td>Case Color:</td>
			<td>{FoundItem.CaseColor}</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>Customer Name:</td>
			<td>{FoundItem.FirstName} {FoundItem.LastName}</td>
		</tr>
		<tr>
			<td>Customer Phone #:</td>
			<td>{FoundItem.HomePhone}</td>
		</tr>
		<tr>
			<td>Customer Address:</td>
			<td>{FoundItem.Address1}</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>{FoundItem.Address2}</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>{FoundItem.City}, {FoundItem.State} {FoundItem.Zip}</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>Customer Signature:</td>
			<td><u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u></td>
		</tr>
		<tr>
			<td>Date:</td>
			<td>{Date.Today}</td>
		</tr>
	</tbody>
</table>

<p>&nbsp;</p>');

insert into properties (keyStr, valueStr) values ('found.item.receipt.template', (select cast(id as char(50)) from template where name = 'High Value PPU Receipt'));

drop table if exists lffound;
drop table if exists lfitem;
drop table if exists lfcategory;
drop table if exists lfsubcategory;
drop table if exists lfaddress;
drop table if exists lfperson;
drop table if exists lfphone;
drop table if exists lfremark;

create table lffound like lfc_testing.lffound;
create table lfitem like lfc_testing.lfitem;
create table lfcategory like lfc_testing.lfcategory;
create table lfsubcategory like lfc_testing.lfsubcategory;
create table lfaddress like lfc_testing.lfaddress;
create table lfperson like lfc_testing.lfperson;
create table lfphone like lfc_testing.lfphone;
create table lfremark like lfc_testing.lfremark;

insert into lfcategory select * from lfc_testing.lfcategory;
insert into lfsubcategory select * from lfc_testing.lfsubcategory;
update lfcategory set companycode = 'WN';

alter table lfitem add column removalReason varchar(200);
alter table lffound add receiptFileName varchar(255) default null;