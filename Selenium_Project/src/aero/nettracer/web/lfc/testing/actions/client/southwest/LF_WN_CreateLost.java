package aero.nettracer.web.lfc.testing.actions.client.southwest;

import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import aero.nettracer.web.lfc.testing.LFC_SeleniumTest;
import aero.nettracer.web.utility.Settings;

public class LF_WN_CreateLost extends LFC_SeleniumTest {

	@Test
	public void testLandingPage() throws Exception {
		driver.get(CLIENT_WN_URL);
		verifyTrue(isTextPresent("Did you lose a personal item or a piece of checked baggage during your travels with us?"));
		verifyTrue(isTextPresent("We're sorry to hear that you've misplaced your belonging and will do everything in our power to reunite you with your item. To properly guide the search process, please tell us if the item was a part of your checked baggage (i.e. in a piece of luggage that was checked at the ticket counter or at the curbside) or if it was unchecked (i.e. in a carryon bag that you brought onboard the aircraft, or a loose item such as a laptop, a jacket, a child's stuffed animal, etc)?"));
		driver.findElement(By.id("pageForm:j_id19")).click();
		waitForPageToLoadImproved(1000);
	}

	@Test
	public void testCheckedPage() throws Exception {
		verifyTrue(isTextPresent("Contact Southwest Airlines Baggage Service Office"));
		verifyTrue(isTextPresent("This service is for unchecked items left on the plane or gate areas only. Please contact your local Southwest Baggage Service Office located in the airport baggage claim area or at the ticket counter to report your situation. For general baggage questions, please call 1-888-202-1024."));
		driver.findElement(By.id("pageForm:j_id13")).click();
		waitForPageToLoadImproved(1000);
		driver.findElement(By.id("pageForm:j_id17")).click();
		waitForPageToLoadImproved(1000);
	}

	@Test
	public void testUncheckedPage() throws Exception {
		verifyTrue(isTextPresent("Report Lost Item(s)"));
		verifyTrue(isTextPresent("Please use this page to report an item that was lost at any of our airports."));
		verifyTrue(isTextPresent("To file a Lost Item Report, click the button above or below."));
		verifyTrue(isTextPresent("Frequently Asked Questions"));
		driver.findElement(By.cssSelector("#q1 > span")).click();
		verifyTrue(isTextPresent("Will you keep me updated on the status of the search for my lost item?"));
		verifyTrue(isTextPresent("You will receive an email from Lost & Found Central when you file the report for your lost item. If we find an item that matches the description of your lost item, we will send you an email notification with further instructions to allow for confirmation that the item is yours. While we are searching for your lost item, (for up to 30 days), we will update you via email throughout the search process."));
		driver.findElement(By.cssSelector("#q1 > span")).click();
		driver.findElement(By.cssSelector("#q2 > span")).click();
		verifyTrue(isTextPresent("How long will you search for my lost item?"));
		verifyTrue(isTextPresent("A thorough search will take place for approximately 30 days after we receive the lost item report. If we are unable to find your item, you will receive an email notification informing you that we have been unsuccessful in our efforts."));
		driver.findElement(By.cssSelector("#q2 > span")).click();
		driver.findElement(By.cssSelector("#q3 > span")).click();
		verifyTrue(isTextPresent("How will I know if my item has been found?"));
		verifyTrue(isTextPresent("If we locate an item that matches the description of your lost item, Lost & Found Central will contact you via email and/or phone to verify that the item is yours and arrange for delivery of the item."));
		driver.findElement(By.cssSelector("#q3 > span")).click();
		driver.findElement(By.cssSelector("#q4 > span")).click();
		verifyTrue(isTextPresent("If my item is found how will it be returned to me?"));
		verifyTrue(isTextPresent("Lost & Found Central will ask you to provide a FedEx account number for billing purposes and a shipping address. If you do not have a FedEx account, you can easily create one at FedEx.com."));
		driver.findElement(By.cssSelector("#q4 > span")).click();
		driver.findElement(By.cssSelector("#q5 > span")).click();
		verifyTrue(isTextPresent("I don't have an email address, how will I be contacted?"));
		verifyTrue(isTextPresent("If you do not have an email address you will be contacted via telephone only if it appears that your item has been located. We will not provide status updates via telephone."));
		driver.findElement(By.cssSelector("#q5 > span")).click();
		driver.findElement(By.cssSelector("#q6 > span")).click();
		verifyTrue(isTextPresent("What happens to the items that cannot be returned to their owner?"));
		verifyTrue(isTextPresent("Once all efforts have been exhausted to find the owner of a particular item, the item will be salvaged and all proceeds will be donated to charity."));
		driver.findElement(By.cssSelector("#q6 > span")).click();
		driver.findElement(By.cssSelector("#q7 > span")).click();
		verifyTrue(isTextPresent("What happens with official documents or government-issued IDs if you are unable to locate the Owner?"));
		verifyTrue(isTextPresent("Military IDs will be sent to the Department of the Navy, NAS-JRB ID Office. This department will then facilitate the return to the various military branches."));
		verifyTrue(isTextPresent("Passports will be sent to the U.S. Department of State, Passport Services, Consular Lost/Stolen Passport Section."));
		verifyTrue(isTextPresent("All other personal documents that we are unable to return will be destroyed."));
		driver.findElement(By.cssSelector("#q7 > span")).click();
		driver.findElement(By.cssSelector("#q8 > span")).click();
		verifyTrue(isTextPresent("My lost item is REALLY important, is there someone that I can call to look a little harder?"));
		verifyTrue(isTextPresent("We understand that every lost item is very important to each individual, and undergo an exhaustive search to locate every single item reported. Please be assured that we take each Lost Item Report very seriously and will go to every effort to locate and return your lost item(s)."));
		driver.findElement(By.cssSelector("#q8 > span")).click();
		driver.findElement(By.cssSelector("#q9 > span")).click();
		verifyTrue(isTextPresent("My lost item is electronic and has a data plan associated to it. Should I turn it off?"));
		verifyTrue(isTextPresent("Yes. Although we are conducting an exhaustive search and hope to locate your item, you should immediately deactivate any associated data plans for your own protection. We recommend that you leave call service activated on phones for a period of one week to assist in our search and verification process."));
		driver.findElement(By.cssSelector("#q9 > span")).click();
		driver.findElement(By.cssSelector("#q10 > span")).click();
		verifyTrue(isTextPresent("What can I do to help increase the chances of finding my item?"));
		verifyTrue(isTextPresent("Please provide detailed, accurate descriptions on the Lost Item Report Form. If possible, gather the descriptive information about the item(s) prior to filling out the form and be sure to include any and all identifiable details to help in our search. Please enter the information just as it appears on the item. Also, be sure to include the flight date and flight number reflecting when your item(s) were lost. You may also provide additional information in both the \"brief description\" field, as well as the \"help us find your item\" area. Failing to include information in either of these fields will limit our ability to find your item. We also ask that you file your report within 14 days of your item becoming lost. Reports filed after 14 days have a decreased chance of item recovery."));
		driver.findElement(By.cssSelector("#q10 > span")).click();
		driver.findElement(By.id("pageForm:j_id13")).click();
		waitForPageToLoadImproved(1000);
	}

	@Test
	public void testLostReportPage() throws Exception {
		driver.findElement(By.id("lostForm:buttonCreate")).click();
		Alert alert = driver.switchTo().alert();
		assertEquals("Please be advised that if an email address is not provided, we will contact you via telephone and only in the event that we find an item closely matching the description of your reported lost item.", alert.getText());
		alert.accept();
		waitForPageToLoadImproved(1000);
		(new Select(driver.findElement(By.id("lostForm:itemCategory")))).selectByVisibleText("Cellphone");
		(new Select(driver.findElement(By.id("lostForm:itemColor")))).selectByVisibleText("Black");
		(new Select(driver.findElement(By.id("lostForm:itemCaseColor")))).selectByVisibleText("Does Not Apply");
		driver.findElement(By.id("lostForm:lastName")).sendKeys("Test");
		driver.findElement(By.id("lostForm:firstName")).sendKeys("John");
		driver.findElement(By.id("lostForm:address1")).sendKeys("123 Test");
		driver.findElement(By.id("lostForm:city")).sendKeys("Test");
		(new Select(driver.findElement(By.id("lostForm:state")))).selectByVisibleText("Georgia");
		driver.findElement(By.id("lostForm:postal")).sendKeys("30339");
		driver.findElement(By.id("lostForm:email")).sendKeys("test@nettracer.aero");
		driver.findElement(By.id("lostForm:dateLostPopupButton")).click();
		driver.findElement(By.xpath("//td[@id='lostForm:dateLostFooter']/table/tbody/tr/td[5]/div")).click();
		(new Select(driver.findElement(By.id("lostForm:segmentTable:0:departLoc")))).selectByVisibleText("Atlanta, GA - ATL");
		(new Select(driver.findElement(By.id("lostForm:segmentTable:0:arrivalLoc")))).selectByVisibleText("Boston, MA - BOS");
		driver.findElement(By.id("lostForm:buttonCreate")).click();
		waitForPageToLoadImproved(1000);
		driver.findElement(By.id("lostForm:confirmEmail")).sendKeys("test@nettracer.aero");
		(new Select(driver.findElement(By.id("lostForm:itemCategory")))).selectByVisibleText("Bags");
		driver.findElement(By.id("lostForm:buttonCreate")).click();
		waitForPageToLoadImproved(1000);
		(new Select(driver.findElement(By.id("lostForm:itemSubCategory")))).selectByVisibleText("Cloth Bag");
		driver.findElement(By.id("lostForm:buttonCreate")).click();
		waitForPageToLoadImproved(5000);
	}

	@Test
	public void testSuccessPage() throws Exception {
		verifyTrue(isTextPresent("Thank You For Reporting Your Lost Item"));
		verifyTrue(isTextPresent("Lost Item Confirmation"));
		verifyTrue(isTextPresent("Thank you for flying with Southwest Airlines and filling out our Online Lost Item Report Form."));
		String lost_id = driver.findElement(By.id("j_id7:j_id18")).getText();
		Settings.LOST_ID_LF_WN = lost_id;
		System.out.println("WN CLIENT VIEW LOST CREATED: " + Settings.LOST_ID_LF_WN);
		verifyTrue(isTextPresent("You will receive email updates with the status of your pending report every few days."));
		verifyTrue(isTextPresent("If you would like to see a printable version of your Lost Item Report, please click the review button below."));
		driver.findElement(By.id("j_id7:j_id26")).click();
		waitForPageToLoadImproved(1000);
	}

	@Test
	public void testPrintPage() throws Exception {
		verifyTrue(isTextPresent("Contact Information"));
		verifyTrue(isTextPresent("Test, John"));
		verifyTrue(isTextPresent("123 Test"));
		verifyTrue(isTextPresent("Test, GA 30339"));
		verifyTrue(isTextPresent("United States"));
		verifyTrue(isTextPresent("test@nettracer.aero"));
		driver.findElement(By.id("j_id7:backButton1")).click();
		waitForPageToLoadImproved(1000);
	}
	
	@Test
	public void testUpdateReport() {
		driver.get(CLIENT_WN_LOGIN_URL);
		driver.findElement(By.id("pageForm:j_id17")).sendKeys("Test");
		driver.findElement(By.id("pageForm:j_id21")).sendKeys(Settings.LOST_ID_LF_WN);
		driver.findElement(By.id("pageForm:j_id23")).click();
		waitForPageToLoadImproved(2500);

		verifyTrue(isTextPresent("Lost Item Report Form"));
		driver.findElement(By.id("lostForm:firstName")).clear();
		driver.findElement(By.id("lostForm:city")).clear();
		driver.findElement(By.id("lostForm:address1")).clear();
		driver.findElement(By.id("lostForm:firstName")).sendKeys("2");
		driver.findElement(By.id("lostForm:city")).sendKeys("2");
		driver.findElement(By.id("lostForm:address1")).sendKeys("2");
		driver.findElement(By.id("lostForm:buttonEdit")).click();
		waitForPageToLoadImproved(5000);

		verifyTrue(isTextPresent("Thank You For Updating Your Lost Item"));
		verifyTrue(isTextPresent("Edit Lost Item Confirmation"));
		verifyTrue(isTextPresent("Thank you for flying with Southwest Airlines and filling out our Online Lost Item Report Form."));
		verifyTrue(isTextPresent("If you would like to see a printable version of your Lost Item Report, please click the review button below."));
		driver.findElement(By.id("j_id7:j_id24")).click();
		waitForPageToLoadImproved(1000);

		verifyTrue(isTextPresent("Contact Information"));
		verifyTrue(isTextPresent("Test, 2"));
		verifyTrue(isTextPresent("2"));
		verifyTrue(isTextPresent("2, GA 30339"));
		verifyTrue(isTextPresent("United States"));
		verifyTrue(isTextPresent("test@nettracer.aero"));
		driver.findElement(By.id("j_id7:backButton2")).click();
		waitForPageToLoadImproved(1000);
				
		verifyTrue(isTextPresent("Update Lost Item Report Information"));
	}
	
	@Test
	public void testUpdateClosedReport() {
		loginToNt();
		
		clickMenu("menucol_2.4");
		
		if(checkNoErrorPage()) {
			driver.findElement(By.name("id")).sendKeys(Settings.LOST_ID_LF_WN);
			driver.findElement(By.id("button")).click();
		}
		
		if(checkNoErrorPage()) {
			(new Select(driver.findElement(By.name("lost.statusId")))).selectByVisibleText("Closed");
			driver.findElement(By.name("saveButton")).click();
		}
		
		if(checkNoErrorPage()) {
			driver.get(CLIENT_WN_LOGIN_URL);
			driver.findElement(By.id("pageForm:j_id17")).sendKeys("Test");
			driver.findElement(By.id("pageForm:j_id21")).sendKeys(Settings.LOST_ID_LF_WN);
			driver.findElement(By.id("pageForm:j_id23")).click();
			waitForPageToLoadImproved(2500);
		}
		
		verifyTrue(isTextPresent("This item has been closed and can no longer be viewed."));
	}
	
}
