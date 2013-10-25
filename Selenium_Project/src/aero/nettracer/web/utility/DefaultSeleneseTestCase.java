package aero.nettracer.web.utility;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.thoughtworks.selenium.SeleneseTestCase;

public class DefaultSeleneseTestCase extends SeleneseTestCase {
	
	public WebDriver driver = null;
	
	@Before
	public void setUp() throws Exception {
		selenium = SeleniumTestBrowserDefault.getBrowser();
		driver = SeleniumTestBrowserDefault.getDriver();
	}
	
	public void goToTaskManager() {
		if (!selenium.isTextPresent("Task Manager Home")) {
			clickMenu("menucol_0.0");
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
		waitForPageToLoadImproved(true, 0, Settings.PAGE_LOAD_TIMEOUT);
	}
	
	public void waitForPageToLoadImproved(boolean doCheck) {
		waitForPageToLoadImproved(doCheck, 0, Settings.PAGE_LOAD_TIMEOUT);
	}
	
	public void waitForPageToLoadImproved(boolean doCheck, int check, String timeout) {
		boolean loadFailed = false;
		try {
			selenium.waitForPageToLoad(timeout);
		} catch (Exception e) {
			loadFailed = true;
			System.out.println("PAGE DID NOT LOAD: CHECKING FOR COPYRIGHT!");
		}
		if (doCheck && loadFailed && check < Settings.CHECK_TIMES && !isElementPresent("id=copyright")) {
			System.out.println("COPYRIGHT NOT LOADED: TRY NUMBER " + (check + 2) + " OF " + Settings.CHECK_TIMES);
			waitForPageToLoadImproved(true, check++, timeout);
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
	
	public void clickMenu(String menu) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String toExecute = "var x = document.getElementById('" + menu + "'); x.click();";
		js.executeScript(toExecute);
	}
	
	public boolean isElementPresent(String element) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		try {
			return selenium.isElementPresent(element);
		} finally {
			driver.manage().timeouts().implicitlyWait(Settings.ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
		}
	}

}
