package aero.nettracer.web.southwest.testing.actions.nt.admin;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;

public class WN_StatusMessageTest extends WN_SeleniumTest{
	String message = "Test status message " + System.currentTimeMillis();
	@Test
	public void testStatusMessage() throws Exception {
	
		selenium.click("id=menucol_9.2");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			selenium.click("//td[@id='navmenucell']/div/dl/dd[7]/a/span[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Status Message 1");
		}
		
		if (checkNoErrorPage()) {
			selenium.type("name=statusMessage", message);
			selenium.click("name=save");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Status Message 2");
		}		

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Company Information Saved."));
			selenium.click("id=menucol_0.0");			
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Status Message 3");
		}
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent(message));
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Status Message 4");
		}
	}
	
	@Test
	public void testStatusMessageAudit() throws Exception {

		selenium.click("id=menucol_9.12");			
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			selenium.click("//td[@id='navmenucell']/div/dl/dd[10]/a/span[2]");			
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Status Message 5");
		}
		if (checkNoErrorPage()) {		
			navigateAuditPagination("id");
			verifyTrue(selenium.isTextPresent(message));
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Status Message 6");
		}		
	}
	

}
