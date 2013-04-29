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
//			selenium.click("//a[@id='menucol_0.0']/u");
			waitForPageToLoadImproved();
		}
	}
	
	public void checkCopyrightAndQuestionMarks() {
		verifyTrue(selenium.isTextPresent("NetTracer, Inc."));
		verifyTrue(selenium.isTextPresent("2003-201"));
		verifyFalse(selenium.isTextPresent("exact:???"));
	}
	
	public boolean checkNoErrorPage() {
		return !selenium.isTextPresent("There is an error with your request.");
	}
	
	public void waitForPageToLoadImproved(long wait){
		waitForPageToLoadImproved(wait, true);
	}
	
	public void waitForPageToLoadImproved(long wait, boolean useWait){
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
			//continue
		}
		if (useWait) {
			waitForPageToLoadImproved();
		}
	}
	
	public void waitForPageToLoadImproved() {
		waitForPageToLoadImproved(true, 0);
	}
	
	public void waitForPageToLoadImproved(boolean doCheck) {
		waitForPageToLoadImproved(doCheck, 0);
	}
	
	public void waitForPageToLoadImproved(boolean doCheck, int check) {
		boolean loadFailed = false;
		try {
			selenium.waitForPageToLoad(Settings.PAGE_LOAD_TIMEOUT);
		} catch (Exception e) {
			loadFailed = true;
			System.out.println("PAGE DID NOT LOAD: CHECKING FOR COPYRIGHT!");
		}
		if (doCheck && loadFailed && check < Settings.CHECK_TIMES && !selenium.isElementPresent("id=copyright")) {
			System.out.println("COPYRIGHT NOT LOADED: TRY NUMBER " + (check + 2) + " OF " + Settings.CHECK_TIMES);
			waitForPageToLoadImproved(true, check++);
		}
	}
	
	public void verifyTrue(boolean testThis) {
		super.verifyTrue(testThis);
		if (!testThis) {
			System.out.println("SYSTEM FUBAR. Failure on previous test...");
		}
	}
	
	public void verifyFalse(boolean testThis) {
		super.verifyFalse(testThis);
		if (testThis) {
			System.out.println("SYSTEM FUBAR. Failure on previous test...");
		}
	}

}
