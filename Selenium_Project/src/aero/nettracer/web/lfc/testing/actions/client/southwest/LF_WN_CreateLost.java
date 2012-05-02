package aero.nettracer.web.lfc.testing.actions.client.southwest;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;
import aero.nettracer.web.utility.LoginUtil;

public class LF_WN_CreateLost extends DefaultSeleneseTestCase {

	@Test
	public void testLandingPage() throws Exception {
		selenium.open(Settings.START_URL_LF_WN);
		verifyTrue(selenium.isTextPresent("Did you lose a loose item or checked baggage?"));
		verifyTrue(selenium.isTextPresent("We apologize for the loss of your item and will do everything in our power to reunite you with it. Before we get started though we have one very important question that will help us better locate the item."));
		selenium.click("id=pageForm:j_id19");
		waitForPageToLoadImproved();
	}

	@Test
	public void testCheckedPage() throws Exception {
		verifyTrue(selenium.isTextPresent("Contact Baggage Service"));
		verifyTrue(selenium.isTextPresent("This service is for unchecked items lost on the plane or gate areas only. Please contact your local Southwest Airlines Baggage Service Office located in the airport baggage claim area or at the ticket counter to report your situation."));
		selenium.click("id=pageForm:j_id13");
		waitForPageToLoadImproved();
		selenium.click("id=pageForm:j_id17");
		waitForPageToLoadImproved();
	}

	@Test
	public void testUncheckedPage() throws Exception {
		verifyTrue(selenium.isTextPresent("Report A Lost Item"));
		verifyTrue(selenium.isTextPresent("Please use this page to report an item that was lost at any of our airports."));
		verifyTrue(selenium.isTextPresent("Please click the button below to file a Lost Item Report."));
		verifyTrue(selenium.isTextPresent("Frequently Asked Questions"));
		selenium.click("css=#q1 > span");
		verifyTrue(selenium.isTextPresent("Will you keep me updated on the status of the search for my lost item?"));
		verifyTrue(selenium.isTextPresent("You will receive an e-mail from Lost and Found Central when you file the report for your lost item."));
		selenium.click("css=#q1 > span");
		selenium.click("css=#q2 > span");
		verifyTrue(selenium.isTextPresent("How long will you search for my lost item?"));
		verifyTrue(selenium.isTextPresent("A thorough search will be made for approximately 30 days after we receive the lost item report."));
		selenium.click("css=#q2 > span");
		selenium.click("css=#q3 > span");
		verifyTrue(selenium.isTextPresent("How will I know if my item has been found?"));
		verifyTrue(selenium.isTextPresent("If we locate an item that matches the description of your lost item, Lost and Found Central will contact you via email and/or phone to verify the item is yours and setup delivery."));
		selenium.click("css=#q3 > span");
		selenium.click("css=#q4 > span");
		verifyTrue(selenium.isTextPresent("If my item is found how will it be returned to me?"));
		verifyTrue(selenium.isTextPresent("Lost and Found Central will ask you to provide a FedEx account number for billing purposes and a shipping address."));
		selenium.click("css=#q4 > span");
		selenium.click("css=#q5 > span");
		verifyTrue(selenium.isTextPresent("I don't have an e-mail address, how will I be contacted?"));
		verifyTrue(selenium.isTextPresent("If you do not have an e-mail address you will be contacted via telephone only if it appears that your item has been located."));
		selenium.click("css=#q5 > span");
		selenium.click("css=#q6 > span");
		verifyTrue(selenium.isTextPresent("What happens to the items that cannot be returned to their owner?"));
		verifyTrue(selenium.isTextPresent("Once all efforts have been exhausted to find the owner of an item, the item will be salvaged and all proceeds will be donated to charity."));
		selenium.click("css=#q6 > span");
		selenium.click("css=#q7 > span");
		verifyTrue(selenium.isTextPresent("What happens with official documents or government-issued IDs if you are unable to locate the Owner?"));
		verifyTrue(selenium.isTextPresent("Military IDs will be sent to the Department of the Navy, NAS-JRB ID Office."));
		verifyTrue(selenium.isTextPresent("Passports will be sent to the U.S. Department of State, Passport Services, Consular Lost/Stolen Passport Section."));
		verifyTrue(selenium.isTextPresent("All other personal documents that we are unable to return will be destroyed."));
		selenium.click("css=#q7 > span");
		selenium.click("css=#q8 > span");
		verifyTrue(selenium.isTextPresent("My lost item is REALLY important, is there someone that I can call to look a little harder?"));
		verifyTrue(selenium.isTextPresent("Southwest believes that every item is really important for every individual. Therefore we place the same exhaustive efforts into every lost item report."));
		selenium.click("css=#q8 > span");
		selenium.click("css=#q9 > span");
		verifyTrue(selenium.isTextPresent("My lost item is electronic and has a data plan associated to it. Should I turn it off?"));
		verifyTrue(selenium.isTextPresent("Yes. Although we are conducting an exhaustive search and will hopefully find your item, you should immediately deactivate any associated data plans for your own protection."));
		selenium.click("css=#q9 > span");
		selenium.click("css=#q10 > span");
		verifyTrue(selenium.isTextPresent("What can I do to help increase the chances of finding my item?"));
		verifyTrue(selenium.isTextPresent("Please provide as much accurate information on the Lost Item Report Form as possible. If possible, gather the information about the item prior to filling out the form. Enter the information as it appears on the item, i.e. driver license, Passport, Kindle, etc. Make sure to include the flight date and flight number. Also, add information in both the \"brief description\" field, as well as the \"help us find your item\" area. Both of these fields are used for automated searching and failing to include information in either will limit our ability to find your item. We also ask that you file your report within 14 days of loss. If you wait longer than that, the odds of us finding your item go way down."));
		selenium.click("css=#q10 > span");
		selenium.click("id=pageForm:j_id21");
		waitForPageToLoadImproved();
	}

	@Test
	public void testLostReportPage() throws Exception {
		selenium.click("id=lostForm:j_id174");
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
		verifyTrue(selenium.isTextPresent("Airport Item Was Lost At is required."));
		verifyTrue(selenium.isTextPresent("Item Color is required."));
		verifyTrue(selenium.isTextPresent("Item Case Color is required."));
		verifyTrue(selenium.isTextPresent("Item Category is required."));
		selenium.select("id=lostForm:j_id23", "label=Cellphone");
		selenium.select("id=lostForm:j_id31", "label=Black");
		selenium.select("id=lostForm:j_id57", "label=Does Not Apply");
		selenium.type("id=lostForm:j_id77", "Test");
		selenium.type("id=lostForm:j_id79", "John");
		selenium.type("id=lostForm:j_id85", "123 Test");
		selenium.type("id=lostForm:j_id97", "Test");
		selenium.select("id=lostForm:state", "label=Georgia");
		selenium.type("id=lostForm:j_id102", "30339");
		selenium.type("id=lostForm:email", "test@nettracer.aero");
		selenium.click("id=lostForm:j_id163PopupButton");
		selenium.click("//td[@id='lostForm:j_id163Footer']/table/tbody/tr/td[5]/div");
		selenium.select("id=lostForm:j_id165", "label=test");
		selenium.click("id=lostForm:j_id174");
		waitForPageToLoadImproved(1000, false);
		verifyTrue(selenium.isTextPresent("Email Address and Confirm Email Address must match."));
		verifyTrue(selenium.isTextPresent("Lost Phone Phone Number is required for Category \"Cellphone\"."));
		selenium.type("id=lostForm:j_id155", "test@nettracer.aero");
		selenium.select("id=lostForm:j_id23", "label=Bags");
		selenium.click("id=lostForm:j_id174");
		waitForPageToLoadImproved(1000, false);
		verifyTrue(selenium.isTextPresent("Item Subcategory is required for Category \"\"."));
		selenium.select("id=lostForm:j_id28", "label=Cloth Bag");
		selenium.click("id=lostForm:j_id174");
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
		selenium.click("id=j_id7:j_id92");
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

			selenium.select("id=lostForm:j_id23", "label=Bags");
			selenium.select("id=lostForm:j_id28", "label=Cloth Bag");
			selenium.select("id=lostForm:j_id31", "label=Black");
			selenium.select("id=lostForm:j_id57", "label=Does Not Apply");
			selenium.type("id=lostForm:j_id41", "2");
			selenium.type("id=lostForm:j_id43", "2");
			selenium.type("id=lostForm:j_id45", "2");
			selenium.type("id=lostForm:j_id53", "2");
			selenium.type("id=lostForm:j_id55", "2");
			selenium.type("id=lostForm:j_id63", "2");
			selenium.type("id=lostForm:j_id67", "21");
			selenium.type("id=lostForm:j_id77", "Test");
			selenium.type("id=lostForm:j_id79", "2");
			selenium.type("id=lostForm:j_id81", "2");
			selenium.type("id=lostForm:j_id85", "2");
			selenium.type("id=lostForm:j_id89", "2");
			selenium.type("id=lostForm:j_id97", "2");
			selenium.click("id=lostForm:j_id175");
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
			selenium.click("id=j_id7:j_id93");
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
			verifyTrue(selenium.isTextPresent("Lost Report is Closed"));
		}
	}
	
}
