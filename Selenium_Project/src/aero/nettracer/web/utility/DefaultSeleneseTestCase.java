package aero.nettracer.web.utility;

import org.junit.Before;

import com.thoughtworks.selenium.SeleneseTestCase;

public class DefaultSeleneseTestCase extends SeleneseTestCase {
	
	@Before
	public void setUp() throws Exception {
		selenium = SeleniumTestBrowserDefault.getBrowser();
	}
	
	public void goToTaskManager() {
		if (!selenium.isTextPresent("Task Manager Home")) {
			selenium.click("menucol_0.0");
			selenium.waitForPageToLoad("30000");
		}
	}
	
	public void checkCopyrightAndQuestionMarks() {
		verifyTrue(selenium.isTextPresent("NetTracer, Inc.\r\n2003-201"));
		verifyFalse(selenium.isTextPresent("???"));
	}

}
