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
			waitForPageToLoadImproved();
		}
	}
	
	public void checkCopyrightAndQuestionMarks() {
		verifyTrue(selenium.isTextPresent("NetTracer, Inc."));
		verifyTrue(selenium.isTextPresent("2003-201"));
		verifyFalse(selenium.isTextPresent("\\?\\?\\?"));
	}
	
	public boolean checkNoErrorPage() {
		return !selenium.isTextPresent("There is an error with your request.");
	}
	
	public void waitForPageToLoadImproved() {
		selenium.waitForPageToLoad(Settings.PAGE_LOAD_TIMEOUT);
	}
	
	public void verifyTrue(boolean testThis) {
		super.verifyTrue(testThis);
		if (!testThis) {
			System.err.println("SYSTEM FUBAR. Failure on previous test...");
		}
	}
	
	public void verifyFalse(boolean testThis) {
		super.verifyFalse(testThis);
		if (testThis) {
			System.err.println("SYSTEM FUBAR. Failure on previous test...");
		}
	}

}
