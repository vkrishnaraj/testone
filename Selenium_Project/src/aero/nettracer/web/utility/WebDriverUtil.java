package aero.nettracer.web.utility;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class WebDriverUtil {
	
	public static void goToTaskManager(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		try {
			if (!(driver.findElement(By.xpath("//div[@id='maincontent']/form/div/h1")).getText()).contains("Task Manager Home")) {
				clickMenu(driver, "menucol_0.0");
			}
		} catch (NoSuchElementException ex) {
			clickMenu(driver, "menucol_0.0");
		} finally {
			driver.manage().timeouts().implicitlyWait(Settings.ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
		}
	}
	
	public static boolean checkCopyrightAndQuestionMarks(WebDriver driver) {
		boolean check = false;
		try {
			WebElement copyElement = driver.findElement(By.id("copyright"));
			check = copyElement.getText().contains("NetTracer, Inc.");
			check = check && copyElement.getText().contains("2003-201");
			check = check && !(driver.findElement(By.tagName("body")).getText().contains("???"));
		} catch (Exception e) {
			System.out.println("ERROR OCCURRED CHECKING COPYRIGHT!!!");
			check = false;
		}
		return check;
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
	
	public static void clickMenu(WebDriver driver, String menu) {
		WebElement element = driver.findElement(By.id(menu));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String getText = "var x = document.getElementById('" + menu + "'); return x.innerHTML;";
		String toExecute = "var x = document.getElementById('" + menu + "'); x.click();";
		String text = (String) js.executeScript(getText);
		System.out.println("CLICKING ELEMENT id = " + menu + ", TEXT = " + text);
		js.executeScript(toExecute);
		waitForStaleElement(driver, element, menu);
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
	
	public static void waitForStaleElement(WebDriver driver, WebElement element, String identifier) {
		waitForStaleElement(driver, element, identifier, 0);
	}
	
	private static void waitForStaleElement(WebDriver driver, WebElement element, String identifier, int tryNumber) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Settings.ELEMENT_TIMEOUT_SECONDS);
			wait.until(ExpectedConditions.stalenessOf(element));
		} catch (TimeoutException ex) {
			tryNumber++;
			if (tryNumber < Settings.CHECK_TIMES) {
				System.out.println("ELEMENT " + identifier + " NOT STALE - TRY NUMBER " + tryNumber + " - TRYING AGAIN.");
				waitForStaleElement(driver, element, identifier, tryNumber);
				return;
			} else {
				System.out.println("ELEMENT " + identifier + " NOT STALE - TRY NUMBER " + tryNumber + " - MOVING ON.");
			}
		}
		
		try {
			WebElement copyElem = driver.findElement(By.id("copyright"));
			WebDriverWait wait = new WebDriverWait(driver, Settings.ELEMENT_TIMEOUT_SECONDS);
			wait.until(ExpectedConditions.visibilityOf(copyElem));
			Actions action = new Actions(driver);
			action.moveToElement(copyElem).perform();
		} catch (Exception e) {
			System.out.println("UNABLE TO MOVE TO COPYRIGHT");
		}
	}
	
	public static WebDriver refreshDriver(WebDriver driver) {
		driver.quit();
		driver = null;
		WebDriver newDriver = new InternetExplorerDriver();
		newDriver.manage().timeouts().implicitlyWait(Settings.ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
		return newDriver;
	}

	
}
