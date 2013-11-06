package aero.nettracer.web.utility;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.thoughtworks.selenium.SeleneseTestBase;


public class LoginUtil {
	
	private static SeleneseTestBase base = new SeleneseTestBase();
	
	public static void loginNTAutoTest(WebDriver driver, String location, String companyCode, String lzStation) {
		loginProcedure(driver, location, Settings.USERNAME_ADMIN, Settings.PASSWORD_ADMIN, companyCode, false);
		setCbroStation(driver, lzStation);
	}
	
	public static void loginFailureTest(WebDriver driver, String location, String companyCode) {
		loginProcedure(driver, location, Settings.USERNAME_TEST, Settings.PASSWORD_CHANGE, companyCode, true);
	}
	
	public static void loginOGAdminTest(WebDriver driver, String location) {
		loginProcedure(driver, location, Settings.USERNAME_OGADMIN, Settings.PASSWORD_OGADMIN, "OW", false);
	}
	
	private static void loginProcedure(WebDriver driver, String location, String user, String pass, String companyCode, boolean isFail) {
		System.out.println("Logging In Using " + user + " " + pass + (companyCode == null ? "" : " " + companyCode));
		driver.get(location + "logon.do");
		driver.findElement(By.name("username")).sendKeys(user);
		driver.findElement(By.name("password")).sendKeys(pass);
		if (companyCode != null) { // For handling companies with no company dropdown list.
			(new Select(driver.findElement(By.name("companyCode")))).selectByValue(companyCode);
		}
		driver.findElement(By.id("button")).click();
		boolean pleaseTryAgain = driver.getPageSource().contains("Invalid username and/or password, please try again");
		boolean lockedOut = driver.getPageSource().contains("This agent has been locked out due to excessive failed login attempts. Please contact system administrator.");
		if (isFail) {
			base.verifyTrue(pleaseTryAgain || lockedOut);
		} else {
			base.verifyFalse(pleaseTryAgain || lockedOut);
			base.verifyTrue(driver.getPageSource().contains("Task Manager Home"));		
		}
	}
	
	public static void loginNTAuto(WebDriver driver, String location, String companyCode, String lzStation) {
		driver.get(location + "logon.do?username=" + Settings.USERNAME_ADMIN + "&companyCode=" + companyCode + "&password=" + Settings.PASSWORD_ADMIN);
		WebDriverUtil.waitForPageToLoadImproved(500);
		setCbroStation(driver, lzStation);
	}
	
	public static void loginOGAdmin(WebDriver driver, String location) {
		driver.get(location + "logon.do?username=" + Settings.USERNAME_OGADMIN + "&companyCode=OW&password=" + Settings.PASSWORD_OGADMIN);
		WebDriverUtil.waitForPageToLoadImproved(500);
	}
	
	public static void logoutTest(WebDriver driver) {
		WebDriverUtil.clickMenu(driver, "mainLayoutLogoutLink");
		WebDriverUtil.waitForPageToLoadImproved(1500);
	}
	
	public static void logout(WebDriver driver, String location) {
		driver.get(location + "logoff.do");
		WebDriverUtil.waitForPageToLoadImproved(1500);
	}
	
	public static void setCbroStation(WebDriver driver, String cbroStation) {
		WebElement cbroElement = driver.findElement(By.name("cbroStation"));
		Select cbroSelect = new Select(cbroElement);
		String logStation = cbroSelect.getFirstSelectedOption().getText();
		if (logStation != null && !logStation.equals(cbroStation)) {
			cbroSelect.selectByVisibleText(cbroStation);
			WebDriverUtil.waitForStaleElement(driver, cbroElement, "cbroStation");
		}
	}
	
}
