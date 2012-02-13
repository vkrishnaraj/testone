package aero.nettracer.web.lfc.testing.actions.lfc.lost;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class LF_CreateLost_Detailed extends DefaultSeleneseTestCase {

	@Test
	public void testAB_Login() throws Exception {
		goToTaskManager();
		selenium.click("id=menucol_2.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.type("name=lost.client.lastName", "Test");
			selenium.type("name=lost.client.firstName", "Test");
			selenium.type("name=lost.client.address.decryptedAddress1", "123 Test");
			selenium.type("name=lost.client.address.decryptedCity", "Test");
			selenium.select("id=state", "label=Georgia");
			selenium.type("name=lost.client.address.decryptedZip", "30152");
			selenium.type("name=primaryPhoneNumber", "555-555-5555");
			selenium.type("name=item[0].brand", "Test Brand");
			selenium.type("name=item[0].serialNumber", "123321456");
			selenium.select("id=category_0", "label=Bags");
			selenium.select("name=item[0].color", "label=Black");
			selenium.type("name=item[0].description", "Test Description Test Description Test Description Test Description Test Description Test Description Test DescriptionTest Description Test Description Test Description Test Description Test Description Test Description I Hate Matt.");
			selenium.fireEvent("name=item[0].description", "blur");
			selenium.click("saveButton");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyTrue(selenium.isTextPresent("Your lost report was successfully saved."));
				String lost_id = selenium.getValue("//div[@id='maincontent']/table/tbody/tr/td/input");
				Settings.LOST_ID_LF = lost_id;
				System.out.println("LF: Lost Report Created: " + Settings.LOST_ID_LF);
				verifyTrue(!Settings.LOST_ID_LF.equals("0"));
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
