package aero.nettracer.web.defaul.testing.actions;

import org.junit.Test;

import aero.nettracer.web.defaul.testing.DefaultSeleneseTestCase;

public class LoadFirstOpenPAWOBVar extends DefaultSeleneseTestCase {
	
	@Test
	public void testLoad_First_Open_PAWOB_Var() throws Exception {
		selenium.open("/tracer/logon.do");
		selenium.click("menucol_1.4");
		selenium.waitForPageToLoad("30000");
		selenium.select("status_ID", "label=Open");
		selenium.select("itemType_ID", "label=Missing Items");
		selenium.select("itemType_ID", "label=Damaged");
		selenium.select("itemType_ID", "label=Lost/Delayed");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[1]/a");
		selenium.waitForPageToLoad("30000");
	}
}
