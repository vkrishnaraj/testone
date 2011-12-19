package aero.nettracer.web.avis.testing.actions.lf.lost;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class AB_CreateLost_VerifyRequiredFields extends DefaultSeleneseTestCase {

	@Test
	public void testAB_Login() throws Exception {
		goToTaskManager();
		selenium.click("id=menucol_1.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("id=button");
			assertEquals("Last Name is required.", selenium.getAlert());
			selenium.type("name=lost.client.lastName", "Test");
			selenium.click("id=button");
			assertEquals("First Name is required.", selenium.getAlert());
			selenium.type("name=lost.client.firstName", "Test");
			selenium.click("id=button");
			assertEquals("Street Address is required.", selenium.getAlert());
			selenium.type("name=lost.client.address.decryptedAddress1", "123 Test");
			selenium.click("id=button");
			assertEquals("City is required.", selenium.getAlert());
			selenium.type("name=lost.client.address.decryptedCity", "Test");
			selenium.click("id=button");
			assertEquals("Rental Location is required.", selenium.getAlert());
			selenium.select("name=lost.reservation.pickupLocationId", "label=ABE");
			selenium.click("id=button");
			assertEquals("Phone number or email is required.", selenium.getAlert());
			selenium.type("name=lost.client.decryptedEmail", "test@test.com");
			selenium.click("id=button");
			assertEquals("Email and confirm email must match.", selenium.getAlert());
			selenium.type("name=lost.client.confirmEmail", "test@test.com");
			selenium.click("id=button");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				String lost_id = selenium.getText("//div[@id='maincontent']/table/tbody/tr/td/input");
				Settings.LOST_ID_AB = lost_id;
				System.out.println("AB: Lost Report Created: " + Settings.LOST_ID_AB);
				verifyTrue(!Settings.LOST_ID_AB.equals("0"));
			} else {
				System.err.println("Edit Lost Page Did Not Load After Report Creation. Error Page Loaded Instead.");
				verifyTrue(false);
			}
		} else {
			System.err.println("Create Lost Page Did Not Load. Error Page Loaded Instead.");
			verifyTrue(false);
		}
	}
}
