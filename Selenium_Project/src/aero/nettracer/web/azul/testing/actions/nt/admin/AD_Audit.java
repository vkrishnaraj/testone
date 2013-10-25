package aero.nettracer.web.azul.testing.actions.nt.admin;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class AD_Audit extends DefaultSeleneseTestCase{
	@Test
	public void testBasicAuditLoad(){
		clickMenu("menucol_9.11");
		waitForPageToLoadImproved();
		selenium.click("//td[@id='navmenucell']/div/dl/dd[3]/a/span[2]");
		waitForPageToLoadImproved();
		selenium.type("//div[@id='maincontent']/font/table/tbody/tr/td/input", Settings.INCIDENT_ID_AD);
		selenium.click("id=button");
		waitForPageToLoadImproved();
		selenium.click("link="+Settings.INCIDENT_ID_AD);
		waitForPageToLoadImproved();
		selenium.click("//div[@id='maincontent']/table/tbody/tr[2]/td/input");
		selenium.click("xpath=(//input[@id='button'])[3]");
		waitForPageToLoadImproved();
		checkCopyrightAndQuestionMarks();

	}
}
