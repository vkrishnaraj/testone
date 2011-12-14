package aero.nettracer.web.jetblue.testing.actions.nt.taskman;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class B6_InboxMessage extends DefaultSeleneseTestCase {
	
	@Test
	public void testVerifyText() throws Exception {
		goToTaskManager();
		selenium.click("//div[@id='maincontent']/form/table/tbody/tr[29]/td/a");
		selenium.waitForPageToLoad("30000");
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=new");
			selenium.waitForPageToLoad("30000");
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				String inc_id = "NO ID FOUND";
				boolean has_inc_id = false;
				if (Settings.INCIDENT_ID != null && !Settings.INCIDENT_ID.equals("")) {
					inc_id = Settings.INCIDENT_ID;
					has_inc_id = true;
				}
				selenium.select("name=recp_list[0].station_id", "label=BOS");
				selenium.type("name=subject", "Test Message: " + inc_id);
				selenium.type("name=body", "Test Message that references PAWOB: " + inc_id);
				if (has_inc_id) {
					selenium.select("name=file_type", "label=Pawob");
					selenium.type("name=file_ref_number", "TTTTT");
					selenium.click("name=send2");
					selenium.waitForPageToLoad("30000");
					verifyTrue(selenium.isTextPresent("Incorrect pawob number/type"));
					selenium.type("name=file_ref_number", inc_id);
				}
				selenium.click("name=send2");
				selenium.waitForPageToLoad("30000");
				verifyTrue(selenium.isTextPresent("Message has been sent."));
				selenium.click("id=menucol_0.0");
				selenium.waitForPageToLoad("30000");
				selenium.select("name=cbroStation", "label=BOS");
				selenium.waitForPageToLoad("30000");
				selenium.click("//div[@id='maincontent']/form/table/tbody/tr[29]/td/a");
				selenium.waitForPageToLoad("30000");
				selenium.click("link=Test Message: " + inc_id);
				selenium.waitForPageToLoad("30000");
				verifyTrue(selenium.isTextPresent("Test Message that references PAWOB: " + inc_id));
				selenium.click("id=menucol_0.0");
				selenium.waitForPageToLoad("30000");
				selenium.select("name=cbroStation", "label=CBS");
				selenium.waitForPageToLoad("30000");
			} else {
				System.out.println("!!!!!!!!!!!!!!!! - Message Creation Page did not load. Error Page loaded instead. - !!!!!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!!! - Inbox Page did not load. Error Page loaded instead. - !!!!!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}
	
}
