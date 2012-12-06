package aero.nettracer.web.lfc.testing.actions.client.southwest;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;
import aero.nettracer.web.utility.LoginUtil;

public class LF_WN_CreateLost extends DefaultSeleneseTestCase {

	@Test
	public void testLandingPage() throws Exception {
		selenium.open(Settings.START_URL_LF_WN);
		verifyTrue(selenium.isTextPresent("Did you lose a personal item or a piece of checked baggage during your travels with us?"));
		verifyTrue(selenium.isTextPresent("We're sorry to hear that you've misplaced your belonging and will do everything in our power to reunite you with your item. To properly guide the search process, please tell us if the item was a part of your checked baggage (i.e. in a piece of luggage that was checked at the ticket counter or at the curbside) or if it was unchecked (i.e. in a carryon bag that you brought onboard the aircraft, or a loose item such as a laptop, a jacket, a child's stuffed animal, etc)?"));
		selenium.click("id=pageForm:j_id19");
		waitForPageToLoadImproved();
	}

	@Test
	public void testCheckedPage() throws Exception {
		verifyTrue(selenium.isTextPresent("Contact Southwest Airlines Baggage Service Office"));
		verifyTrue(selenium.isTextPresent("This service is for unchecked items left on the plane or gate areas only. Please contact your local Southwest Baggage Service Office located in the airport baggage claim area or at the ticket counter to report your situation. For general baggage questions, please call 1-888-202-1024."));
		selenium.click("id=pageForm:j_id13");
		waitForPageToLoadImproved();
		selenium.click("id=pageForm:j_id17");
		waitForPageToLoadImproved();
	}

	@Test
	public void testUncheckedPage() throws Exception {
		verifyTrue(selenium.isTextPresent("Report Lost Item(s)"));
		verifyTrue(selenium.isTextPresent("Please use this page to report an item that was lost at any of our airports."));
		verifyTrue(selenium.isTextPresent("To file a Lost Item Report, click the button above or below."));
		verifyTrue(selenium.isTextPresent("Frequently Asked Questions"));
		selenium.click("css=#q1 > span");
		verifyTrue(selenium.isTextPresent("Will you keep me updated on the status of the search for my lost item?"));
		verifyTrue(selenium.isTextPresent("You will receive an e-mail from Lost & Found Central when you file the report for your lost item. If we find an item that matches the description of your lost item, we will send you an e-mail notification with further instructions to allow for confirmation that the item is yours. While we are searching for your lost item, (for up to 30 days), we will update you via e-mail throughout the search process."));
		selenium.click("css=#q1 > span");
		selenium.click("css=#q2 > span");
		verifyTrue(selenium.isTextPresent("How long will you search for my lost item?"));
		verifyTrue(selenium.isTextPresent("A thorough search will take place for approximately 30 days after we receive the lost item report. If we are unable to find your item, you will receive an e-mail notification informing you that we have been unsuccessful in our efforts."));
		selenium.click("css=#q2 > span");
		selenium.click("css=#q3 > span");
		verifyTrue(selenium.isTextPresent("How will I know if my item has been found?"));
		verifyTrue(selenium.isTextPresent("If we locate an item that matches the description of your lost item, Lost & Found Central will contact you via email and/or phone to verify that the item is yours and arrange for delivery of the item."));
		selenium.click("css=#q3 > span");
		selenium.click("css=#q4 > span");
		verifyTrue(selenium.isTextPresent("If my item is found how will it be returned to me?"));
		verifyTrue(selenium.isTextPresent("Lost & Found Central will ask you to provide a FedEx account number for billing purposes and a shipping address. If you do not have a FedEx account, you can easily create one at FedEx.com."));
		selenium.click("css=#q4 > span");
		selenium.click("css=#q5 > span");
		verifyTrue(selenium.isTextPresent("I don't have an e-mail address, how will I be contacted?"));
		verifyTrue(selenium.isTextPresent("If you do not have an e-mail address you will be contacted via telephone only if it appears that your item has been located. We will not provide status updates via telephone."));
		selenium.click("css=#q5 > span");
		selenium.click("css=#q6 > span");
		verifyTrue(selenium.isTextPresent("What happens to the items that cannot be returned to their owner?"));
		verifyTrue(selenium.isTextPresent("Once all efforts have been exhausted to find the owner of a particular item, the item will be salvaged and all proceeds will be donated to charity."));
		selenium.click("css=#q6 > span");
		selenium.click("css=#q7 > span");
		verifyTrue(selenium.isTextPresent("What happens with official documents or government-issued IDs if you are unable to locate the Owner?"));
		verifyTrue(selenium.isTextPresent("Military IDs will be sent to the Department of the Navy, NAS-JRB ID Office. This department will then facilitate the return to the various military branches."));
		verifyTrue(selenium.isTextPresent("Passports will be sent to the U.S. Department of State, Passport Services, Consular Lost/Stolen Passport Section."));
		verifyTrue(selenium.isTextPresent("All other personal documents that we are unable to return will be destroyed."));
		selenium.click("css=#q7 > span");
		selenium.click("css=#q8 > span");
		verifyTrue(selenium.isTextPresent("My lost item is REALLY important, is there someone that I can call to look a little harder?"));
		verifyTrue(selenium.isTextPresent("We understand that every lost item is very important to each individual, and undergo an exhaustive search to locate every single item reported. Please be assured that we take each Lost Item Report very seriously and will go to every effort to locate and return your lost item(s)."));
		selenium.click("css=#q8 > span");
		selenium.click("css=#q9 > span");
		verifyTrue(selenium.isTextPresent("My lost item is electronic and has a data plan associated to it. Should I turn it off?"));
		verifyTrue(selenium.isTextPresent("Yes. Although we are conducting an exhaustive search and hope to locate your item, you should immediately deactivate any associated data plans for your own protection. We recommend that you leave call service activated on phones for a period of one week to assist in our search and verification process."));
		selenium.click("css=#q9 > span");
		selenium.click("css=#q10 > span");
		verifyTrue(selenium.isTextPresent("What can I do to help increase the chances of finding my item?"));
		verifyTrue(selenium.isTextPresent("Please provide detailed, accurate descriptions on the Lost Item Report Form. If possible, gather the descriptive information about the item(s) prior to filling out the form and be sure to include any and all identifiable details to help in our search. Please enter the information just as it appears on the item. Also, be sure to include the flight date and flight number reflecting when your item(s) were lost. You may also provide additional information in both the \"brief description\" field, as well as the \"help us find your item\" area. Failing to include information in either of these fields will limit our ability to find your item. We also ask that you file your report within 14 days of your item becoming lost. Reports filed after 14 days have a decreased chance of item recovery."));
		selenium.click("css=#q10 > span");
		selenium.click("id=pageForm:j_id13");
		waitForPageToLoadImproved();
	}

	@Test
	public void testLostReportPage() throws Exception {
		selenium.click("id=lostForm:j_id193");
		waitForPageToLoadImproved(1000, false);
		assertEquals("Please be advised that if an e-mail address is not provided, we will contact you via telephone and only in the event that we find an item closely matching the description of your reported lost item.", selenium.getConfirmation());
		verifyTrue(selenium.isTextPresent("First Name is required."));
		verifyTrue(selenium.isTextPresent("Last Name is required."));
		verifyTrue(selenium.isTextPresent("Address is required."));
		verifyTrue(selenium.isTextPresent("City is required."));
		verifyTrue(selenium.isTextPresent("State is required."));
		verifyTrue(selenium.isTextPresent("Zip Code is required."));
		verifyTrue(selenium.isTextPresent("Contact Information must contain at least one Phone Number or Email Address."));
		verifyTrue(selenium.isTextPresent("Date Lost is required."));
		verifyTrue(selenium.isTextPresent("Item Color is required."));
		verifyTrue(selenium.isTextPresent("Item Case Color is required."));
		verifyTrue(selenium.isTextPresent("Item Category is required."));
		verifyTrue(selenium.isTextPresent("Arrival Airport required for Segment #1 in the \"About Your Trip\" section."));
		verifyTrue(selenium.isTextPresent("Departure Airport required for Segment #1 in the \"About Your Trip\" section."));
		selenium.select("id=lostForm:j_id25", "label=Cellphone");
		selenium.select("id=lostForm:j_id33", "label=Black");
		selenium.select("id=lostForm:j_id37", "label=Does Not Apply");
		selenium.type("id=lostForm:j_id81", "Test");
		selenium.type("id=lostForm:j_id83", "John");
		selenium.type("id=lostForm:j_id89", "123 Test");
		selenium.type("id=lostForm:j_id101", "Test");
		selenium.select("id=lostForm:state", "label=Georgia");
		selenium.type("id=lostForm:j_id106", "30339");
		selenium.type("id=lostForm:email", "test@nettracer.aero");
		selenium.click("id=lostForm:j_id59PopupButton");
		selenium.click("//td[@id='lostForm:j_id59Footer']/table/tbody/tr/td[5]/div");
		selenium.select("id=lostForm:segmentTable:0:j_id172", "label=ATL - Atlanta, GA");
		selenium.select("id=lostForm:segmentTable:0:j_id176", "label=BOS - Boston, MA");
		selenium.click("id=lostForm:j_id193");
		waitForPageToLoadImproved(1000, false);
		verifyTrue(selenium.isTextPresent("Email Address and Confirm Email Address must match."));
		verifyTrue(selenium.isTextPresent("Phone number of lost phone is required for Category \"Cellphone\"."));
		selenium.type("id=lostForm:j_id159", "test@nettracer.aero");
		selenium.select("id=lostForm:j_id25", "label=Bags");
		waitForPageToLoadImproved(1000, false);
		selenium.click("id=lostForm:j_id193");
		waitForPageToLoadImproved(1000, false);
		verifyTrue(selenium.isTextPresent("Item Subcategory is required for Category \"Bags\"."));
		selenium.select("id=lostForm:j_id30", "label=Cloth Bag");
		selenium.click("id=lostForm:j_id193");
		waitForPageToLoadImproved();
	}

	@Test
	public void testSuccessPage() throws Exception {
		verifyTrue(selenium.isTextPresent("Thank You For Reporting Your Lost Item"));
		verifyTrue(selenium.isTextPresent("Lost Item Confirmation"));
		verifyTrue(selenium.isTextPresent("Thank you for flying with Southwest Airlines and filling out our On-Line Lost Item Report Form."));
		String lost_id = selenium.getText("id=j_id7:j_id18");
		Settings.LOST_ID_LF_WN = lost_id;
		System.out.println("WN CLIENT VIEW LOST CREATED: " + Settings.LOST_ID_LF_WN);
		verifyTrue(selenium.isTextPresent("You will be emailed updates on the status of your report every few days."));
		verifyTrue(selenium.isTextPresent("If you would like to see a printable version of your Lost Item Report, please click the review button below."));
		selenium.click("id=j_id7:j_id26");
		waitForPageToLoadImproved();
	}

	@Test
	public void testPrintPage() throws Exception {
		verifyTrue(selenium.isTextPresent("Contact Information"));
		verifyTrue(selenium.isTextPresent("Test, John"));
		verifyTrue(selenium.isTextPresent("123 Test"));
		verifyTrue(selenium.isTextPresent("Test, GA 30339"));
		verifyTrue(selenium.isTextPresent("United States"));
		verifyTrue(selenium.isTextPresent("test@nettracer.aero"));
		selenium.click("id=j_id7:j_id102");
		waitForPageToLoadImproved();
	}
	
	@Test
	public void testUpdateReport() {

		selenium.open(Settings.START_URL_LF_LG);
		selenium.type("id=pageForm:j_id17", "Test");
		selenium.type("id=pageForm:j_id21", Settings.LOST_ID_LF_WN);
		selenium.click("id=pageForm:j_id23");
		waitForPageToLoadImproved();
		
		if(checkNoErrorPage())
		{
			verifyTrue(selenium.isTextPresent("Lost Item Report Form"));

			selenium.type("id=lostForm:j_id47", "2");
			selenium.type("id=lostForm:j_id49", "2");
			selenium.type("id=lostForm:j_id51", "2");
			selenium.type("id=lostForm:j_id61", "2");
			selenium.type("id=lostForm:j_id63", "2");
			selenium.type("id=lostForm:j_id67", "2");
			selenium.type("id=lostForm:j_id71", "21");
			selenium.type("id=lostForm:j_id83", "2");
			selenium.type("id=lostForm:j_id85", "2");
			selenium.type("id=lostForm:j_id89", "2");
			selenium.type("id=lostForm:j_id93", "2");
			selenium.type("id=lostForm:j_id101", "2");
			selenium.click("id=lostForm:j_id194");
			waitForPageToLoadImproved();
		}


		if(checkNoErrorPage())
		{
			verifyTrue(selenium.isTextPresent("Thank You For Updating Your Lost Item"));
			verifyTrue(selenium.isTextPresent("Edit Lost Item Confirmation"));
			verifyTrue(selenium.isTextPresent("Thank you for flying with Southwest Airlines and filling out our On-Line Lost Item Report Form."));
			String lost_id = selenium.getText("id=j_id7:j_id16");
			Settings.LOST_ID_LF_WN = lost_id;
			System.out.println("WN CLIENT VIEW LOST CREATED: " + Settings.LOST_ID_LF_WN);
			verifyTrue(selenium.isTextPresent("If you would like to see a printable version of your Lost Item Report, please click the review button below."));
			selenium.click("id=j_id7:j_id24");
			waitForPageToLoadImproved();
		}

		if(checkNoErrorPage())
		{
			verifyTrue(selenium.isTextPresent("Contact Information"));
			verifyTrue(selenium.isTextPresent("Test, 2"));
			verifyTrue(selenium.isTextPresent("2"));
			verifyTrue(selenium.isTextPresent("2, GA 30339"));
			verifyTrue(selenium.isTextPresent("United States"));
			verifyTrue(selenium.isTextPresent("test@nettracer.aero"));
			selenium.click("id=j_id7:j_id103");
			waitForPageToLoadImproved();
		}
		
		if(checkNoErrorPage())
		{
			verifyTrue(selenium.isTextPresent("Update Lost Item Report Information"));
		}
	}
	
	@Test
	public void testUpdateClosedReport() {

		selenium.open(Settings.START_URL_LF);
		selenium.type("username", Settings.USERNAME_ADMIN);
		selenium.type("password", Settings.PASSWORD_ADMIN);
		selenium.click("button");
		waitForPageToLoadImproved();
		
		if(checkNoErrorPage())
		{
			selenium.click("id=menucol_2.4");
			waitForPageToLoadImproved();
		}
		
		if(checkNoErrorPage())
		{
			selenium.type("name=id", Settings.LOST_ID_LF_WN);
			selenium.click("id=button");
			waitForPageToLoadImproved();
		}
		
		if(checkNoErrorPage())
		{
			selenium.select("name=lost.statusId", "label=Closed");
			selenium.click("name=saveButton");
			waitForPageToLoadImproved();
		}
		
		if(checkNoErrorPage())
		{
			selenium.click("link=[ Logout ]");
			waitForPageToLoadImproved();
		}
		
		if(checkNoErrorPage())
		{
			selenium.open(Settings.START_URL_LF_LG);
			selenium.type("id=pageForm:j_id17", "Test");
			selenium.type("id=pageForm:j_id21", Settings.LOST_ID_LF_WN);
			selenium.click("id=pageForm:j_id23");
			waitForPageToLoadImproved();
		}
		
		if(checkNoErrorPage())
		{
			verifyTrue(selenium.isTextPresent("This item has been closed and can no longer be viewed."));
		}
	}
	
}
