package aero.nettracer.web.spirit.testing.actions.nt.taskman;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class NK_InboxMessage extends DefaultSeleneseTestCase {
	
	@Test
	public void testVerifyText() throws Exception {
		goToTaskManager();
		selenium.click("//div[@id='maincontent']/form/table/tbody/tr[27]/td/a");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=new");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				String inc_id = "NO ID FOUND";
				boolean has_inc_id = false;
				if (Settings.INCIDENT_ID_NK != null && !Settings.INCIDENT_ID_NK.equals("")) {
					inc_id = Settings.INCIDENT_ID_NK;
					has_inc_id = true;
				}
				selenium.select("name=recp_list[0].station_id", "label=BOS");
				selenium.type("name=subject", "Test Message: " + inc_id);
				selenium.type("name=body", "Test Message that references Incident: " + inc_id);
				if (has_inc_id) {
					selenium.select("name=file_type", "label=Incident");
					selenium.type("name=file_ref_number", "TTTTT");
					selenium.click("name=send2");
					waitForPageToLoadImproved();
					verifyTrue(selenium.isTextPresent("Incorrect incident number/type"));
					selenium.type("name=file_ref_number", inc_id);
				}
				selenium.click("name=send2");
				waitForPageToLoadImproved();
				verifyTrue(selenium.isTextPresent("Message has been sent."));
				selenium.click("css=u");
				waitForPageToLoadImproved();
				selenium.select("name=cbroStation", "label=BOS");
				waitForPageToLoadImproved();
				selenium.click("//div[@id='maincontent']/form/table/tbody/tr[27]/td/a");
				waitForPageToLoadImproved();
				selenium.click("link=Test Message: " + inc_id);
				waitForPageToLoadImproved();
				verifyTrue(selenium.isTextPresent("Test Message that references Incident: " + inc_id));
				selenium.click("id=menucol_0.0");
				waitForPageToLoadImproved();
				selenium.select("name=cbroStation", "label=CLAIM");
				waitForPageToLoadImproved();
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
