package aero.nettracer.web.spirit.testing.actions.nt.taskman;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import aero.nettracer.web.spirit.testing.NK_SeleniumTest;
import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class NK_InboxMessage extends NK_SeleniumTest {
	
	@Test
	public void testVerifyText() throws Exception {
		goToTaskManager();
		driver.findElement(By.id("16link")).click();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			driver.findElement(By.name("new")).click();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				String inc_id = "NO ID FOUND";
				boolean has_inc_id = false;
				if (Settings.INCIDENT_ID_NK != null && !Settings.INCIDENT_ID_NK.equals("")) {
					inc_id = Settings.INCIDENT_ID_NK;
					has_inc_id = true;
				}
				(new Select(driver.findElement(By.name("recp_list[0].station_id")))).selectByVisibleText("BOS");
				driver.findElement(By.name("subject")).sendKeys("Test Message: " + inc_id);
				driver.findElement(By.name("body")).sendKeys("Test Message that references Incident: " + inc_id);
				if (has_inc_id) {
					(new Select(driver.findElement(By.name("file_type")))).selectByVisibleText("Incident");
					driver.findElement(By.name("file_ref_number")).sendKeys("TTTTT");
					driver.findElement(By.name("send2")).click();
					verifyTrue(isTextPresent("Incorrect incident number/type"));
					WebElement file_ref = driver.findElement(By.name("file_ref_number"));
					file_ref.clear();
					file_ref.sendKeys(inc_id);
				}
				driver.findElement(By.name("send2")).click();
				verifyTrue(isTextPresent("Message has been sent."));
				clickMenu("menucol_0.0");
				LoginUtil.setCbroStation(driver, "BOS");
				try {
					driver.findElement(By.id("16link")).click();
					driver.findElement(By.linkText("Test Message: " + inc_id)).click();
					verifyTrue(isTextPresent("Test Message that references Incident: " + inc_id));
					clickMenu("menucol_0.0");
				} finally {
					LoginUtil.setCbroStation(driver, LZ_STATION);
				}
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
