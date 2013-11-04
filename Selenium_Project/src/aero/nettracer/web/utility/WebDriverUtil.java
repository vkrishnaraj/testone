package aero.nettracer.web.utility;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.SeleneseTestBase;


public class WebDriverUtil {
	
	private static SeleneseTestBase base = new SeleneseTestBase();
	
	public static void goToTaskManager(WebDriver driver) {
		if (!driver.getPageSource().contains("Task Manager Home")) {
			clickMenu(driver, "menucol_0.0");
		}
	}
	
	public static void checkCopyrightAndQuestionMarks(WebDriver driver) {
		verifyTrue(driver.getPageSource().contains("NetTracer, Inc."));
		verifyTrue(driver.getPageSource().contains("2003-201"));
		verifyFalse(driver.getPageSource().contains("???"));
	}
	
	public static boolean checkNoErrorPage(WebDriver driver) {
		return !driver.getPageSource().contains("There is an error with your request.");
	}
	
	public static void waitForPageToLoadImproved(long wait){
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
		}
	}
	
	public static void verifyTrue(boolean testThis) {
		base.verifyTrue(testThis);
		if (!testThis) {
			System.out.println("SYSTEM FUBAR. Failure on previous test...");
		}
	}
	
	public static void verifyFalse(boolean testThis) {
		base.verifyFalse(testThis);
		if (testThis) {
			System.out.println("SYSTEM FUBAR. Failure on previous test...");
		}
	}
	
	public static void clickMenu(WebDriver driver, String menu) {
		WebElement element = driver.findElement(By.id(menu));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String toExecute = "var x = document.getElementById('" + menu + "'); x.click();";
		js.executeScript(toExecute);
		waitForStaleElement(driver, element);
	}
	
	public static boolean isElementPresent(WebDriver driver, By by) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException ex) {
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(Settings.ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
		}
	}
	
	public static void check(WebElement checkBox) {
		if (!checkBox.isSelected()) {
			checkBox.click();
		}
	}
	
	public static void uncheck(WebElement checkBox) {
		if (checkBox.isSelected()) {
			checkBox.click();
		}
	}
	
	public static String getSelectedLabel(WebDriver driver, By by) {
		return (new Select(driver.findElement(by))).getFirstSelectedOption().getText();
	}
	
	public static void waitForStaleElement(WebDriver driver, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.stalenessOf(element));
	}

	
}
