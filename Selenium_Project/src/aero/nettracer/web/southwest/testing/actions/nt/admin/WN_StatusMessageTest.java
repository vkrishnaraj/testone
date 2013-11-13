package aero.nettracer.web.southwest.testing.actions.nt.admin;

import org.junit.Test;
import org.openqa.selenium.By;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;

public class WN_StatusMessageTest extends WN_SeleniumTest {
	
	String message = "Test status message " + System.currentTimeMillis();
	
	@Test
	public void testStatusMessage() throws Exception {

		clickMenu(MENU_ADMIN_COMPANY);
		
		if (checkNoErrorPage()) {
			click(By.xpath("//td[@id='navmenucell']/div/dl/dd[7]/a/span[2]"), false, true);
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Status Message 1");
		}
		
		if (checkNoErrorPage()) {
			type(By.name("statusMessage"), message);
			click(By.name("save"), false, true);
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Status Message 2");
		}		

		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Company Information Saved."));
			clickMenu("menucol_0.0");
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
		if (checkNoErrorPage()) {
			click(By.xpath("//td[@id='navmenucell']/div/dl/dd[10]/a/span[2]"), false, true);		
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Status Message 5");
		}
		if (checkNoErrorPage()) {		
			navigateAuditPagination("id");
			waitForPageToLoadImproved(1000);
			verifyTrue(isTextPresent(message));
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Status Message 6");
		}		
	}
	

}
