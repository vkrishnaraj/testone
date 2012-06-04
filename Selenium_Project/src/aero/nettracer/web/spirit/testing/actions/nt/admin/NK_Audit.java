package aero.nettracer.web.spirit.testing.actions.nt.admin;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class NK_Audit extends DefaultSeleneseTestCase{
	@Test
	public void testBasicAuditLoad(){
		selenium.click("id=menucol_9.12");
		waitForPageToLoadImproved();
		selenium.click("//td[@id='navmenucell']/div/dl/dd[3]/a/span[2]");
		waitForPageToLoadImproved();
		selenium.type("//div[@id='maincontent']/font/table/tbody/tr/td/input", Settings.INCIDENT_ID_NK);
		selenium.click("id=button");
		waitForPageToLoadImproved();
		selenium.click("link="+Settings.INCIDENT_ID_NK);
		waitForPageToLoadImproved();
		selenium.click("//div[@id='maincontent']/table/tbody/tr[2]/td/input");
		selenium.click("xpath=(//input[@id='button'])[3]");
		waitForPageToLoadImproved();
		checkCopyrightAndQuestionMarks();

	}
}
