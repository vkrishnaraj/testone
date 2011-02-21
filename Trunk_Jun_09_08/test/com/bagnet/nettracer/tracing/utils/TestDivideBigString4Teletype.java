package com.bagnet.nettracer.tracing.utils;

import java.util.ArrayList;
import org.junit.Test;

public class TestDivideBigString4Teletype {
	String BIG = "*-- Pawob Information --*Pawob Number: CBSB600000019*Pawob Date/Time: 02/10/2009 1704*Crewmember: RHardy*City Reported: CBS*Date/Time Closed: *Pawob Status: Open*City Assigned: CBS*Pass Rider: no*Report Method: In Person*Record Locator: KZT6AG*Ticket Number: B6708713*Associated File: CBSB600000019*Loss Code: 22*Fault Station: CBS*Closing Remarks: **-- Customer Information --*Customer Name*Airline Membership: B6* Membership Status: * TrueBlue Number: * Last Name: Merriweather* First Name: Anthony* Middle: * Salutation: Mr.*Occupation: *Driver's License State: *Driver's License Number: *Common Number (Passport): *Country Of Issue: **Street Address: 27 Division Street*Apt/Suite #: *City: New Rochelle*State/Province: New York*Province: *  Country: United States*Zip: 10801*Business Phone: *Home Phone: 914-396-3011*Fax: *Mobile Phone: *Pager: *Hotel: *Email: ajmerriweather@hotmail.com*Address Valid From: *Address Valid To: *Customer Name*Airline Membership: B6* Membership Status: * TrueBlue Number: * Last Name: moore* First Name: sheril* Middle: * Salutation: Please Select*Occupation: *Driver's License State: *Driver's License Number: *Common Number (Passport): *Country Of Issue: **Street Address: 123 airport road*Apt/Suite #: *City: slc*State/Province: Utah*Province: *  Country: United States*Zip: 84119*Business Phone: 8018888888*Home Phone: 8019999999*Fax: *Mobile Phone: 8017777777*Pager: *Hotel: *Email: *Address Valid From: *Address Valid To: **-- Customer Itinerary --*From/To: LAS / JFK* Airline/Flight Number: 190* Depart Date: 02/02/2009* Arrival Date: 02/02/2009* Scheduled Depart Time: 1029* Actual Depart Time: 1044* Scheduled Arrival Time: 1830* Actual Arrival Time: 1829***-- Baggage Itinerary (If Different From Customer Itinerary) --*From/To: LAS/JFK* Airline/Flight Number: 190* Depart Date: 02/02/2009* Arrival Date: 02/02/2009* Scheduled Depart Time: 1029* Actual Depart Time: 1044* Scheduled Arrival Time: 1830* Actual Arrival Time: 1829***-- Checked Baggage Information --*  Number Of Customers: 2*Number Of Bags Checked: 1*Number Of Bags Received: 0*Bags Checked Location: Ticket Counter*Courtesy Report: no*TSA Inspected: no*Cleared Customs: no**-- Bag Tag Number --*Bag Tag Number: B6708713**-- Baggage Information --*Bag Tag Number: *Last Name on Bag: *First Name on Bag: *Middle Initial on Bag: *Color: BE*Type: 05*Manufacturer: *Bag Status: Process For Delivery*Descriptive Elements: X - no descriptive element*X - no descriptive element*X - no descriptive element**-- Contents --*Content Category: Coat/Jacket*Content Description: adsfasdf**-- Comments --*Date/Time: 02/10/2009 1704*Station: CBS*Crewmember: RHardy*   Bag has BN velvet ribbon on handle     Traveling on True Blue Free Pass    **Date/Time: 03/20/2009 0708*Station: CBS*Crewmember: ntadmin*BDO created for bag(s): 1ddd**Date/Time: 05/11/2009 1215*Station: CBS*Crewmember: ntadmin***Date/Time: 06/15/2009 1441*Station: CBS*Crewmember: SMoore*hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello ***-- Requests --*N/A**-- Trace Results --*Match Category: 0*Pawob Number: CBSB600000019*Date/Time: *BOH: SLCB600000150*Match Percentage: 56.6**-- Matching Elements --*Item                       Percentage             Lost/Delayed Pawob                            BOH*Bag Color                        66.0                             BE                             YW**Name                            100.0                   sheril moore                   Sheril Moore***-- Tasks --*Subject: Offer Due*Created By: SMoore*Status: Not Started*Priority: High*Station: CBS*Due Date: 09/07/2010*Due Time: 0000*Reminder Date: 09/06/2010*Reminder Time: 1240*Comments: Please remember to send the offer letter on this claim by tomorrow 09/07/10**-- Inbox --*N/A*";
	
	//String BIG = "+-- Pawob Information --+Pawob Number: CBSB600000040+Pawob Date/Time: 02/13/2009 1121+Crewmember: CPlatero+City Reported: CBS+Date/Time Closed: +Pawob Status: Open+City Assigned: CBS+Pass Rider: no+Report Method: In Person+Record Locator: P1N6CP+Ticket Number: +Associated File: CBSB600000040+Loss Code: 0+Fault Station: CBS+Closing Remarks: ++-- Customer Information --+Customer Name+Airline Membership: B6+ Membership Status: + TrueBlue Number: + Last Name: Bueno+ First Name: Jose+ Middle: + Salutation: Mr.+Occupation: +Driver's License State: +Driver's License Number: +Common Number (Passport): +Country Of Issue: ++Street Address: El Rubio+Apt/Suite #: +City: Sajoma+State/Province: Maryland+Province: +  Country: United States+Zip: 45752+Business Phone: +Home Phone: +Fax: +Mobile Phone: +Pager: +Hotel: +Email: clifton.platero@jetblue.com+Address Valid From: +Address Valid To: ++-- Customer Itinerary --+From/To: JFK / STI+ Airline/Flight Number: 733+ Depart Date: 02/08/2009+ Arrival Date: 02/08/2009+ Scheduled Depart Time: 0600+ Actual Depart Time: 0600+ Scheduled Arrival Time: 1036+ Actual Arrival Time: 1036+++-- Baggage Itinerary (If Different From Customer Itinerary) --+From/To: /+ Airline/Flight Number: + Depart Date: + Arrival Date: + Scheduled Depart Time: + Actual Depart Time: + Scheduled Arrival Time: + Actual Arrival Time: +++-- Checked Baggage Information --+  Number Of Customers: 1+Number Of Bags Checked: 1+Number Of Bags Received: 0+Bags Checked Location: Ticket Counter+Courtesy Report: no+TSA Inspected: no+Cleared Customs: no++-- Bag Tag Number --+Bag Tag Number: B6894446++-- Baggage Information --+Bag Tag Number: B6838383+Last Name on Bag: Bueno+First Name on Bag: Jose+Middle Initial on Bag: +Color: RD+Type: 23+Manufacturer: +Bag Status: Process For Delivery+Descriptive Elements: X - no descriptive element+X - no descriptive element+X - no descriptive element++-- Contents --+Content Category: Dress+Content Description: Men's clothing+Content Category: Alcohol+Content Description: Brugal +Content Category: Audio+Content Description: Sublime CD+Content Category: Handbag+Content Description: Prada++-- Comments --+Date/Time: 02/13/2009 1121+Station: CBS+Crewmember: CPlatero+Customer's bag contains mens clothing for a funeral.++Date/Time: 02/23/2009 1009+Station: CBS+Crewmember: CPlatero+  Customer called CBS checking on status of claim. 2/23/09. CLIF/CBS  ++Date/Time: 02/23/2009 1009+Station: CBS+Crewmember: CPlatero+++Date/Time: 03/25/2009 1416+Station: CBS+Crewmember: SMoore+BDO created for bag(s): 1+++-- Requests --+N/A++-- Trace Results --+Match Category: 0+Pawob Number: CBSB600000040+Date/Time: +BOH: CBSB600000043+Match Percentage: 100.0++-- Matching Elements --+Item                       Percentage             Lost/Delayed Pawob                            BOH+Bag Type                         33.0                             23                             22++Email                           100.0      clifton.platero@jetblue.com      clifton.platero@jetblue.com+++-- Tasks --+N/A++-- Inbox --+N/A+";
	
	static int telexMaxLengthPerTransmission = 1000;
	static String telexReportName = "CBSB600000019   ";
	

	@Test
	public void testSplitOnWordBreak() {
		
		
		StringBuilder myBigBuilder = new StringBuilder(BIG);
		
		String myPageLabel = telexReportName + " 1 of 7" + " ";  // example 10 of 20
		
		//ArrayList<String> list = StringUtils.splitOnWordBreak(BIG, 25);
		int myMaxLength = telexMaxLengthPerTransmission - (telexReportName.length() + 8);
		
		//BIG = BIG.substring(0, telexMaxLengthPerTransmission - 5);
		
		if (BIG.length() < telexMaxLengthPerTransmission) {
			System.out.println(BIG);
		} else {
			ArrayList<String> list = StringUtils.divideUpBigString(BIG, myMaxLength, "*");
			System.out.println("TestDivideBigString4Teletype >>> number of characters in BIG : " + myBigBuilder.capacity());
			System.out.println("size : " + list.size());
			
			//client max length per Teletype is 1000
			//
			int counter = 1;
			
			for (String j: list) {
				myPageLabel = telexReportName + counter + " of " + list.size() + " ";
				counter++;
				j = myPageLabel + j;
				
				//System.out.println(j);
				System.out.println(j.length() + " : " + j);
			}
		}

		
		
	}
}
