package aero.nettracer.web.southwest.testing.actions.nt.admin;

import org.junit.Test;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;

public class WN_StatusMessageTest extends WN_SeleniumTest{
	String message = "Test status message " + System.currentTimeMillis();
	@Test
	public void testStatusMessage() throws Exception {

		clickMenu(MENU_ADMIN_COMPANY);
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
			verifyTrue(isTextPresent("Company Information Saved."));
			clickMenu("menucol_0.0");			
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Status Message 3");
		}
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent(message));
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Status Message 4");
		}
	}
	
	@Test
	public void testStatusMessageAudit() throws Exception {

		clickMenu(MENU_ADMIN_AUDIT);			
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			selenium.click("//td[@id='navmenucell']/div/dl/dd[10]/a/span[2]");			
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Status Message 5");
		}
		if (checkNoErrorPage()) {		
			navigateAuditPagination("id");
			verifyTrue(isTextPresent(message));
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Status Message 6");
		}		
	}
	

}
