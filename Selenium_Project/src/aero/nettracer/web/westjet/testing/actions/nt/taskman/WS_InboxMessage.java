package aero.nettracer.web.westjet.testing.actions.nt.taskman;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;
import aero.nettracer.web.westjet.testing.WS_SeleniumTest;

public class WS_InboxMessage extends WS_SeleniumTest {
	
	@Test
	public void testVerifyText() throws Exception {
		goToTaskManager();
		selenium.click("id=16link");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=new");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				String inc_id = "NO ID FOUND";
				boolean has_inc_id = false;
				if (Settings.INCIDENT_ID_WS != null && !Settings.INCIDENT_ID_WS.equals("")) {
					inc_id = Settings.INCIDENT_ID_WS;
					has_inc_id = true;
				}
				selenium.select("name=recp_list[0].station_id", "label=BGI");
				selenium.type("name=subject", "Test Message: " + inc_id);
				selenium.type("name=body", "Test Message that references PIR: " + inc_id);
				if (has_inc_id) {
					selenium.select("name=file_type", "label=PIR");
					selenium.type("name=file_ref_number", "TTTTT");
					selenium.click("name=send2");
					waitForPageToLoadImproved();
					verifyTrue(isTextPresent("Incorrect PIR number/type"));
					selenium.type("name=file_ref_number", inc_id);
				}
				selenium.click("name=send2");
				waitForPageToLoadImproved();
				verifyTrue(isTextPresent("Message has been sent."));
				clickMenu("menucol_0.0");
				LoginUtil.setCbroStation(driver, "BGI");
				selenium.click("id=16link");
				waitForPageToLoadImproved();
				selenium.click("link=Test Message: " + inc_id);
				waitForPageToLoadImproved();
				verifyTrue(isTextPresent("Test Message that references PIR: " + inc_id));
				clickMenu("menucol_0.0");
				LoginUtil.setCbroStation(driver, LZ_STATION);
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
