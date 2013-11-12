package aero.nettracer.web.utility;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.SeleneseTestCase;

public class DefaultSeleneseTestCase extends SeleneseTestCase {
	
	public WebDriver driver = null;
	public WebDriver ogDriver = null;
	
	@Before
	public void setUp() throws Exception {
		selenium = SeleniumTestBrowserDefault.getBrowser();
		driver = SeleniumTestBrowserDefault.getDriver();
		ogDriver = SeleniumTestBrowserDefault.getOgDriver();
	}
	
	public void goToTaskManager() {
		WebDriverUtil.goToTaskManager(driver);
	}
	
	public void checkCopyrightAndQuestionMarks() {
		verifyTrue(WebDriverUtil.checkCopyrightAndQuestionMarks(driver));
	}
	
	public boolean checkNoErrorPage() {
		return WebDriverUtil.checkNoErrorPage(driver);
	}
	
	public void waitForPageToLoadImproved(long wait){
		WebDriverUtil.waitForPageToLoadImproved(wait);
	}
	
	public void waitForPageToLoadImproved(long wait, boolean useWait){
		WebDriverUtil.waitForPageToLoadImproved(wait);
	}
	
	public void waitForPageToLoadImproved() {
		// UNUSED BY WEBDRIVER
	}
	
	public void waitForPageToLoadImproved(boolean doCheck) {
		// UNUSED BY WEBDRIVER
	}
	
	public void waitForPageToLoadImproved(boolean doCheck, int check, String timeout) {
		// UNUSED BY WEBDRIVER
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
		WebDriverUtil.clickMenu(driver, menu);
	}
	
	public boolean isElementPresent(By by) {
		return WebDriverUtil.isElementPresent(driver, by);
	}
	
	public void loadQuickSearch() {
		waitForPageToLoadImproved(500,false);
		new Actions(driver).sendKeys(Keys.chord(Keys.CONTROL, "s")).perform();
		waitForPageToLoadImproved(500,false);
	}
	
	public void loadQuickHistory() {
		waitForPageToLoadImproved(500,false);
		new Actions(driver).sendKeys(Keys.chord(Keys.CONTROL, "h")).perform();
		waitForPageToLoadImproved(500,false);
	}
	
	public void closeQuickSearch() {
		driver.findElement(By.id("quickSearchQuery3")).click();
		new Actions(driver).sendKeys(Keys.chord(Keys.ESCAPE)).perform();
		waitForPageToLoadImproved(500,false);
	}
	
	public void closeQuickHistory() {
		new Actions(driver).sendKeys(Keys.chord(Keys.ESCAPE)).perform();
		waitForPageToLoadImproved(500,false);
	}
	
	protected void logoutTest() {
		LoginUtil.logoutTest(driver);
	}
	
	protected String getSelectedLabel(By by) {
		return WebDriverUtil.getSelectedLabel(driver, by);
	}
	
	protected boolean isTextPresent(String text) {
		return selenium.isTextPresent(text);
		//return driver.findElement(By.tagName("body")).getText().contains(text);
	}
	
	protected boolean isEditable(By by) {
		String isDisabled = driver.findElement(by).getAttribute("disabled");
		if (isDisabled == null || !(isDisabled.equals("disabled") || isDisabled.equals("true"))) {
			return true;
		} else {
			return false;
		}
	}
	
	protected void refreshDriver() {
		driver = WebDriverUtil.refreshDriver(driver);
	}
	
	protected void click(By locator) {
		click(locator, false, false);
	}
	
	protected void click(By locator, boolean waitBefore, boolean waitAfter) {
		try {
			if (waitBefore) {
				WebDriverWait wait = new WebDriverWait(driver, 5);
				wait.until(ExpectedConditions.elementToBeClickable(locator));
			}
			WebElement element = driver.findElement(locator);
			element.click();
			if (waitAfter) {
				WebDriverUtil.waitForStaleElement(driver, element, locator.toString());
			}
		} catch (Exception e) {
			System.out.println("***FAILURE TO CLICK ELEMENT: " + locator);
		}
	}

}
