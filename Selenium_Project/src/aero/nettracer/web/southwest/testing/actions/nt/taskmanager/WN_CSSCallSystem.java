package aero.nettracer.web.southwest.testing.actions.nt.taskmanager;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;

public class WN_CSSCallSystem extends WN_SeleniumTest {
	
	private boolean fullTest = false;
	private String incident_id = "";
	private String task_type = "";
	private String next_incident = "";
	private String today = "";
	private final String REMARK = "Autotest Remark.";
	private final String TWODAYTASK = "Second Day Call";
	private final String THREEDAYTASK = "Third Day Call";
	private final String FOURDAYTASK = "Fourth Day Call";
	private final String FIVEDAYTASK = "Fifth Day Call";
	private final String TWODAYREMARK = "CS&S Two Day Call - ";
	private final String THREEDAYREMARK = "CS&S Three Day Call - ";
	private final String FOURDAYREMARK = "CS&S Four Day Call - ";
	private final String FIVEDAYREMARK = "CS&S Five Day Call - ";

	@Test
	public void testCheckTaskManager() throws Exception {
		goToTaskManager();
		verifyTrue(selenium.isTextPresent("Customer Contact Station List"));
		String cssSizeStr = selenium.getText("id=658entry");
		System.out.println("CSS Task Number: " + cssSizeStr);
		int cssSize = 0;
		if (cssSizeStr != null && cssSizeStr.matches("^\\d+$")) {
			cssSize = Integer.parseInt(cssSizeStr);
		}
		if (cssSize > 4) {
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			today = df.format(new Date());
			fullTest = true;
		}
	}

	@Test
	public void testStationAndTaskPage() throws Exception {
		if (fullTest) {
			selenium.click("id=658link");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				selenium.click("id=checkLZ");
				selenium.click("id=button");
				waitForPageToLoadImproved();
			} else {
				System.out.println("!!!!!!!!!!!!!!!! CSS 1");
			}
			
			if (checkNoErrorPage()) {
				incident_id = selenium.getText("id=link0");
				selenium.click("id=button");
				waitForPageToLoadImproved();
			} else {
				System.out.println("!!!!!!!!!!!!!!!! CSS 2");
			}
			
			if (checkNoErrorPage()) {
				verifyEquals(incident_id, selenium.getValue("name=incident_ID"));
			} else {
				System.out.println("!!!!!!!!!!!!!!!! CSS 3");
			}
		}
	}

	@Test
	public void testValidationAndAbort() throws Exception {
		if (fullTest) {
			task_type = selenium.getText("id=taskType");
			selenium.click("name=completeTask");
			assertEquals("Call Remarks is required.", selenium.getAlert());
			selenium.click("name=deferTask");
			assertEquals("Call Remarks is required.", selenium.getAlert());
			selenium.click("name=abortTask");
			assertEquals("Call Remarks is required.", selenium.getAlert());
			selenium.type("id=taskRemark", REMARK);
			selenium.chooseCancelOnNextConfirmation();
			selenium.click("name=completeTask");
			assertTrue(selenium.getConfirmation().matches("^Have you saved any changes to the incident[\\s\\S]$"));
			selenium.chooseCancelOnNextConfirmation();
			selenium.click("name=deferTask");
			assertTrue(selenium.getConfirmation().matches("^Have you saved any changes to the incident[\\s\\S]$"));
			selenium.click("name=abortTask");
			assertTrue(selenium.getConfirmation().matches("^Have you saved any changes to the incident[\\s\\S]$"));
			waitForPageToLoadImproved();
			
			if (checkNoErrorPage()) {
				checkRemark(getRemark("Aborted\n"));
			} else {
				System.out.println("!!!!!!!!!!!!!!!! CSS 4");
			}
		}
	}

	@Test
	public void testDeferNoExpire() throws Exception {
		if (fullTest) {
			task_type = selenium.getText("id=taskType");
			selenium.type("id=taskRemark", REMARK);
			selenium.click("name=deferTask");
			assertTrue(selenium.getConfirmation().matches("^Have you saved any changes to the incident[\\s\\S]$"));
			waitForPageToLoadImproved(3000, false);
			selenium.click("name=deferTaskPopup");
			assertEquals("New Start Date is required.", selenium.getAlert());
			selenium.click("id=taskNewStartCal");
			selenium.click("link=Today");
			selenium.click("name=deferTaskPopup");
			assertEquals("New Start Time (HH:mm) is required.", selenium.getAlert());
			selenium.type("id=taskNewStart", "asdf");
			selenium.click("name=deferTaskPopup");
			assertEquals("New Start Time (HH:mm) is improperly formatted. Please input time in 24 hour format with a ':' separating hours and minutes. Ex: 21:35", selenium.getAlert());
			selenium.type("id=taskNewStart", "00:00");
			selenium.click("name=deferTaskPopup");
			assertEquals("New Start Time (HH:mm) must be a time in the future", selenium.getAlert());
			selenium.type("id=taskNewStart", "20:00");
			selenium.click("name=deferTaskPopup");
			waitForPageToLoadImproved();
			
			if (checkNoErrorPage()) {
				checkRemark(getRemark("Deferred\nStart: " + today + " 20:00\nExpire: Not Provided\n"));
			} else {
				System.out.println("!!!!!!!!!!!!!!!! CSS 5");
			}
		}
	}

	@Test
	public void testDeferWithExpire() throws Exception {
		if (fullTest) {
			task_type = selenium.getText("id=taskType");
			selenium.type("id=taskRemark", REMARK);
			selenium.click("name=deferTask");
			assertTrue(selenium.getConfirmation().matches("^Have you saved any changes to the incident[\\s\\S]$"));
			waitForPageToLoadImproved(3000, false);
			selenium.click("id=taskNewStartCal");
			selenium.click("link=Today");
			selenium.type("id=taskNewStart", "22:00");
			selenium.click("id=taskExpireCal");
			selenium.click("link=Today");
			selenium.type("id=taskExpire", "aaaa");
			selenium.click("name=deferTaskPopup");
			assertEquals("Expire Time (HH:mm) is improperly formatted. Please input time in 24 hour format with a ':' separating hours and minutes. Ex: 21:35", selenium.getAlert());
			selenium.type("id=taskExpire", "00:00");
			selenium.click("name=deferTaskPopup");
			assertEquals("Expire Time (HH:mm) cannot be before New Start Time (HH:mm).", selenium.getAlert());
			selenium.type("id=taskExpire", "23:00");
			selenium.click("name=deferTaskPopup");
			waitForPageToLoadImproved();
			
			if (checkNoErrorPage()) {
				checkRemark(getRemark("Deferred\nStart: " + today + " 22:00\nExpire: " + today + " 23:00\n"));
			} else {
				System.out.println("!!!!!!!!!!!!!!!! CSS 6");
			}
		}
	}

	@Test
	public void testTaskManagerMessageAndComplete() throws Exception {
		if (fullTest) {
			goToTaskManager();
			selenium.click("link=" + incident_id);
			waitForPageToLoadImproved();
			
			if (checkNoErrorPage()) {
				task_type = selenium.getText("id=taskType");
				selenium.type("id=taskRemark", REMARK);
				selenium.click("name=completeTask");
				assertTrue(selenium.getConfirmation().matches("^Have you saved any changes to the incident[\\s\\S]$"));
				waitForPageToLoadImproved();
			} else {
				System.out.println("!!!!!!!!!!!!!!!! CSS 7");
			}
			
			if (checkNoErrorPage()) {
				checkRemark(getRemark("Completed\n"));
				goToTaskManager();
			} else {
				System.out.println("!!!!!!!!!!!!!!!! CSS 8");
			}
		}
	}
	
	private void checkRemark(String remark) {
		selenium.click("id=button");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			next_incident = selenium.getText("name=incident_ID");
			clickMenu("menucol_1.4");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! CSSRemark 1");
		}
		
		if (checkNoErrorPage()) {
			selenium.type("name=incident_ID", incident_id);
			selenium.click("id=button");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! CSSRemark 4");
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent(remark));
			clickMenu("menucol_1.4");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! CSSRemark 4");
		}
		
		if (checkNoErrorPage()) {
			incident_id = next_incident;
			selenium.type("name=incident_ID", incident_id);
			selenium.click("id=button");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! CSSRemark 4");
		}
	}
	
	private String getRemark(String action) {
		if (TWODAYTASK.equals(task_type)) {
			return TWODAYREMARK + action + REMARK;
		}
		if (THREEDAYTASK.equals(task_type)) {
			return THREEDAYREMARK + action + REMARK;
		}
		if (FOURDAYTASK.equals(task_type)) {
			return FOURDAYREMARK + action + REMARK;
		}
		if (FIVEDAYTASK.equals(task_type)) {
			return FIVEDAYREMARK + action + REMARK;
		}
		return "";
	}
}
