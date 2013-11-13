package aero.nettracer.web.utility;

import org.junit.Before;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.SeleneseTestBase;

public class DefaultSeleneseTestCase extends SeleneseTestBase {
	
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
	
	public void searchQuickSearch(String toSearch) {
		type(By.id("quickSearchQuery3"), toSearch);
		click(By.id("button"));
		waitForPageToLoadImproved(500);
		try {
			WebDriverWait wait = new WebDriverWait(driver, Settings.ELEMENT_TIMEOUT_SECONDS);
			wait.until(ExpectedConditions.textToBePresentInElement(By.id("dialog-inner-content"), "Matching Incidents"));
		} catch (Exception e) {
			System.out.println("QUICKSEARCH DID NOT RETURN!");
		}
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
	
	protected String getSelectedValue(By by) {
		return driver.findElement(by).getAttribute("value");
	}
	
	protected String getValue(By by) {
		return driver.findElement(by).getText();
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
	
	protected void type(By locator, String text) {
		try {
			WebElement element = driver.findElement(locator);
			element.clear();
			element.sendKeys(text);
		} catch (Exception e) {
			System.out.println("FAILED TO TYPE \"" + text + "\" IN ELEMENT " + locator);
		}
	}
	
	protected void waitForStaleElement(WebElement element, String identifier) {
		WebDriverUtil.waitForStaleElement(driver, element, identifier);
	}
	
	protected String getAlert() {
		String toReturn = "";
		try {
			Alert alert = driver.switchTo().alert();
			toReturn = driver.switchTo().alert().getText();
			alert.accept();
		} catch (Exception e) {
			System.out.println("UNABLE TO GET ALERT");
		}
		return toReturn;
	}

}
