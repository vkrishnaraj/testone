package aero.nettracer.web.avis.testing.actions.lf.found;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class AB_CreateFound_Detailed extends DefaultSeleneseTestCase {

	@Test
	public void testAB_Login() throws Exception {
		goToTaskManager();
		selenium.click("id=menucol_1.2");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.type("name=item[0].brand", "Test Brand");
			selenium.type("name=item[0].serialNumber", "123321456");
			selenium.select("id=category_0", "label=Bags");
			selenium.click("name=item[0].description");
			selenium.type("name=item[0].description", "Test Description Test Description Test Description Test Description Test Description Test Description Test DescriptionTest Description Test Description Test Description Test Description Test Description Test Description Test Description 241 245 249 Should not see this text, beyond 250 character limit");
			selenium.click("name=item[0].color");	
			selenium.select("name=item[0].color", "label=Black");
			selenium.click("id=button");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				String found_id = selenium.getValue("//div[@id='maincontent']/table/tbody/tr/td/input");
				Settings.FOUND_ID_AB = found_id;
				System.out.println("AB: Found Item Created: " + Settings.FOUND_ID_AB);
				verifyTrue(!Settings.FOUND_ID_AB.equals("0"));
			} else {
				System.err.println("Edit Found Page Did Not Load After Report Creation. Error Page Loaded Instead.");
				verifyTrue(false);
			}
		} else {
			System.err.println("Create Found Page Did Not Load. Error Page Loaded Instead.");
			verifyTrue(false);
		}
	}
}
