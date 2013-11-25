insert into properties (keyStr,valueStr) VALUES ('bag.level.loss.codes','1');
insert into properties (keyStr,valueStr) VALUES ('bag.level.loss.codes.itin.check','0');

insert into properties (keyStr,valueStr) VALUES ('nt.res.osi.on','1');
insert into properties (keyStr,valueStr) VALUES ('reservation.hours.backward','48');
insert into properties (keyStr,valueStr) VALUES ('reservation.hours.forward','24');

# TESTING ENTRIES
insert into properties (keyStr,valueStr) VALUES ('nt.res.password','WNpass1!');
insert into properties (keyStr,valueStr) VALUES ('nt.res.username','southwest_test');
insert into properties (keyStr,valueStr) VALUES ('booking.nt.endpoint','http://drapp2.nettracer.aero/NTServices_1_0/services/ReservationService_1_0');

# PRODUCTION ENTRIES
# insert into properties (keyStr,valueStr) VALUES ('nt.res.password','WNpass1!');
# insert into properties (keyStr,valueStr) VALUES ('nt.res.username','southwest_prod');
# insert into properties (keyStr,valueStr) VALUES ('booking.nt.endpoint','http://service1.nettracer.aero/NTServices_1_0/services/ReservationService_1_0');

## Do not change the spacing of the below statement as it will directly affect the layout of the PPU receipt!
insert into template (name,description,active,createDate,lastUpdated,data) values
('CRS Loaner Agreement', 'Child Restraint System loaner agreement', 1, now(), now(), '<p style="text-align: center"><span style="font-size: 14px"><strong><u>Child Restraint System (CRS) Loaner Agreement/Liability Release (WN-984)</u></strong></span></p>

<p><span style="font-size: 14px">I agree that Southwest Airlines is lending me&nbsp;____ child restraint system(s). I understand that this loaner service is provided as a courtesy, may be discontinued at any time, and is not part of the services provided in connection with my air travel. I also understand that Southwest Airlines is not in the business of providing child restraint systems and that the child restraint system that I receive is on an &quot;as is&quot; basis. I agree to return the seat(s) when requested to do so by Southwest Airlines, in the same condition in which it was tendered.</span></p>

<p><span style="font-size: 14px">I, the undersigned, have and do hereby, <strong>RELEASE</strong>,<strong>QUITCLAIM</strong>, and agree to forever <strong>HOLD HARMLESS</strong>, Southwest Airlines, its Officers, Directors, Employees, Agents, subsidiaries, and assigns of any and all liability for injury, medical expenses, loss of earnings or consequential damages of every kind and description, accruing, or that may accrue at ant time by reason of my having voluntarily entered into this program.</span></p>

<table border="0" cellpadding="1" cellspacing="1" style="width: 100%">
	<tbody>
		<tr>
			<td style="border-bottom-style: solid">{Incident.FirstName} {Incident.LastName}</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td style="border-bottom-style: solid">{Incident.Id}</td>
		</tr>
		<tr>
			<td style="vertical-align: top"><span style="font-size: 14px">Name of Borrower</span></td>
			<td style="vertical-align: top">&nbsp;</td>
			<td style="vertical-align: top">&nbsp;</td>
			<td style="vertical-align: top"><span style="font-size: 14px">Report Number</span></td>
		</tr>
		<tr>
			<td style="border-bottom-style: solid">{Incident.Address1}</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td style="border-bottom-style: solid">{Agent.FirstName} {Agent.LastName}</td>
		</tr>
		<tr>
			<td style="vertical-align: top"><span style="font-size: 14px">Adress of Borrower</span></td>
			<td style="vertical-align: top">&nbsp;</td>
			<td style="vertical-align: top">&nbsp;</td>
			<td style="vertical-align: top"><span style="font-size: 14px">Name of Issuing SWA Agent</span></td>
		</tr>
		<tr>
			<td style="border-bottom-style: solid">{Incident.City}, {Incident.State}, {Incident.Zip}</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td style="border-bottom-style: solid">&nbsp;</td>
		</tr>
		<tr>
			<td style="vertical-align: top"><span style="font-size: 14px">City, State, Zip Code</span></td>
			<td style="vertical-align: top">&nbsp;</td>
			<td style="vertical-align: top">&nbsp;</td>
			<td style="vertical-align: top"><span style="font-size: 14px">Seat Number</span></td>
		</tr>
		<tr>
			<td style="border-bottom-style: solid">{Incident.MobilePhone}</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td style="border-bottom-style: solid">{Date.Today}</td>
		</tr>
		<tr>
			<td style="vertical-align: top"><span style="font-size: 14px">Phone Number</span></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td style="vertical-align: top"><span style="font-size: 14px">Date Issued</span></td>
		</tr>
		<tr>
			<td style="border-bottom-style: solid">&nbsp;</td>
			<td style="vertical-align: top">&nbsp;</td>
		</tr>
		<tr>
			<td style="vertical-align: top"><span style="font-size: 14px">Signature of Borrower</span></td>
			<td style="vertical-align: top">&nbsp;</td>
		</tr>
	</tbody>
</table>

<p>================================================================================</p>

<p><span style="font-size: 14px"><strong>Inspection to be completed by receiving Employee upon return</strong></span></p>

<p><span style="font-size: 14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; [ ]&nbsp;&nbsp; CRS Returned in Satisfactory Condition</span></p>

<p><span style="font-size: 14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[ ]&nbsp;&nbsp; Copy of installation instructions attached to seat</span></p>

<p><span style="font-size: 14px"><strong>Check box below and remark if conditions are unacceptable</strong></span></p>

<p><span style="font-size: 14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[ ]&nbsp;&nbsp; Condition of all belts and straps ________________________________</span></p>

<p><span style="font-size: 14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[ ]&nbsp;&nbsp; Condition of upholstery_______________________________________</span></p>

<p><span style="font-size: 14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; [ ]&nbsp;&nbsp; Condition of frame/base_______________________________________</span></p>

<p><span style="font-size: 14px">Additional Remarks:____________________________________________</span></p>

<table border="0" cellpadding="1" cellspacing="1" style="width: 100%">
	<tbody>
		<tr>
			<td style="white-space: nowrap">_________________________________</td>
			<td>_________________________________</td>
		</tr>
		<tr>
			<td style="vertical-align: top"><span style="font-size: 14px">Name of Inspecting Agent</span></td>
			<td style="vertical-align: top"><span style="font-size: 14px">Date of Inspection</span></td>
		</tr>
		<tr>
			<td style="vertical-align: top">_________________________________</td>
			<td style="vertical-align: top">&nbsp;</td>
		</tr>
		<tr>
			<td style="vertical-align: top"><span style="font-size: 12px"><span style="font-size: 14px">Supervisor Signature </span>(if condition unacceptable)</span></td>
			<td style="vertical-align: top">&nbsp;</td>
		</tr>
	</tbody>
</table>

<p>&nbsp;</p>');

# To Be Inventoried
update status set description='To Be Inventoried' where Status_ID=58 and table_ID=2;
insert into properties (ID, keyStr, valueStr) VALUES (124,'to.be.inventoried',1);

update systemcomponents set component_name='Links',component_desc='Links' where component_id=638;
insert into links (description, link_address, image, companycode_id) values ('SWA Life',' https://www.swalife.com/','swalife.PNG','WN');

update properties set valueStr = 'es' where keyStr = 'custom.delay.receipt.files';
update properties set valueStr = 'es' where keyStr = 'custom.damage.receipt.files';
update properties set valueStr = 'es' where keyStr = 'custom.missing.receipt.files';
insert into properties (keyStr, valueStr) VALUES ('receipt.custom.types',1);
delete from localereceipt where locale_id <> 'en';
insert into localereceipt values ('es', 'Espanol');

insert into properties (keyStr,valueStr) values ('bso.expense.process','1');
