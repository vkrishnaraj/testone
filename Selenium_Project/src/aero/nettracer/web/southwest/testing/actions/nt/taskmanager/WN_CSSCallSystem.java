package aero.nettracer.web.southwest.testing.actions.nt.taskmanager;

import org.junit.Test;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;

public class WN_CSSCallSystem extends WN_SeleniumTest {

	@Test
	public void testCreateFoundRequiredFields() throws Exception {
		// Create 5 Incidents to seed call system
		createIncident(false);
		createIncident(false);
		createIncident(false);
		createIncident(false);
		createIncident(false);
		goToTaskManager();
		verifyTrue(selenium.isTextPresent("Customer Contact Station List"));
		String cssSizeStr = selenium.getText("id=658entry");
		int cssSize = 0;
		if (cssSizeStr != null && cssSizeStr.matches("^\\d+$")) {
			cssSize = Integer.parseInt(cssSizeStr);
		}
		if (cssSize > 4) {
			// selenium.click("id=658link");
			// TODO: Make the rest of this once hudson has seeded some data. CG
		}
	}
}
