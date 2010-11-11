package aero.nettracer.web.defaul.testing.actions;

import aero.nettracer.web.defaul.testing.DefaultSeleneseTestCase;

public class CloseIncident extends DefaultSeleneseTestCase {
	
	public void testClose_Incident() throws Exception {
		selenium.click("//td[@id='navmenucell']/div/dl/dd[6]/a/span[2]");
		selenium.waitForPageToLoad("30000");
		selenium.type("theitem[0].arrivedonflightnum", "");
		selenium.click("doclose");
		assertEquals("Arrival Flight is required.", selenium.getAlert());
		selenium.type("theitem[0].arrivedonflightnum", "123");
		selenium.type("theitem[1].arrivedonflightnum", "123");
		selenium.type("theitem[1].disarrivedondate", "06/25/2009");
		selenium.click("doclose");
		verifyTrue(selenium.isTextPresent("Lost/Delayed Bag Incident has been closed."));
	}
}
