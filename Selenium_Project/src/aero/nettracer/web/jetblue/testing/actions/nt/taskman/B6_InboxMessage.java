package aero.nettracer.web.jetblue.testing.actions.nt.taskman;

import org.junit.Test;

import aero.nettracer.web.jetblue.testing.B6_SeleniumTest;
import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class B6_InboxMessage extends B6_SeleniumTest {
	
	@Test
	public void testVerifyText() throws Exception {
		goToTaskManager();
		selenium.click("16link");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=new");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				String inc_id = "NO ID FOUND";
				boolean has_inc_id = false;
				if (Settings.INCIDENT_ID_B6 != null && !Settings.INCIDENT_ID_B6.equals("")) {
					inc_id = Settings.INCIDENT_ID_B6;
					has_inc_id = true;
				}
				selenium.select("name=recp_list[0].station_id", "label=BOS");
				selenium.type("name=subject", "Test Message: " + inc_id);
				selenium.type("name=body", "Test Message that references PAWOB: " + inc_id);
				if (has_inc_id) {
					selenium.select("name=file_type", "label=Pawob");
					selenium.type("name=file_ref_number", "TTTTT");
					selenium.click("name=send2");
					waitForPageToLoadImproved();
					verifyTrue(isTextPresent("Incorrect pawob number/type"));
					selenium.type("name=file_ref_number", inc_id);
				}
				selenium.click("name=send2");
				waitForPageToLoadImproved();
				verifyTrue(isTextPresent("Message has been sent."));
				clickMenu("menucol_0.0");
				LoginUtil.setCbroStation(driver, "BOS");
				selenium.click("16link");
				selenium.click("link=Test Message: " + inc_id);
				verifyTrue(isTextPresent("Test Message that references PAWOB: " + inc_id));
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
