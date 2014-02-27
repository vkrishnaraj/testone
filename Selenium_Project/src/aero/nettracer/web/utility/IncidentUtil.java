package aero.nettracer.web.utility;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class IncidentUtil {
	
	public static boolean navigateToIncidentTest(WebDriver driver, String searchMenu, String incidentType, String incidentID) {
		boolean success = false;
		try {
			WebDriverUtil.clickMenu(driver, searchMenu);
			if (WebDriverUtil.checkNoErrorPage(driver)) {
				WebDriverUtil.checkCopyrightAndQuestionMarks(driver);
				driver.findElement(By.name("incident_ID")).sendKeys(incidentID);
				(new Select(driver.findElement(By.id("itemType_ID")))).selectByVisibleText(incidentType);
				driver.findElement(By.id("button")).click();
				if (WebDriverUtil.checkNoErrorPage(driver)) {
					WebDriverUtil.checkCopyrightAndQuestionMarks(driver);
					success = true;
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Failed to Load Incident: " + incidentID + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to Load Incident Search Page. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			}
		} catch (Exception e) {
			System.out.println("!!!!!!!!!!!!!!! - Failed to navigate to Incident: " + incidentID + " - !!!!!!!!!!!!!!!!!!");
			e.printStackTrace();
		}
		return success;
	}
	
	public static void navigateToIncident(WebDriver driver, String location, String incidentID) {
		driver.get(location + "searchIncident.do?incident=" + incidentID);
	}
	
	public static boolean navigateToIncidentAuditTrailTest(WebDriver driver, String auditMenu, String incidentID) {
		boolean success = false;
		WebDriverUtil.clickMenu(driver, auditMenu);
		if (WebDriverUtil.checkNoErrorPage(driver)) {
			WebDriverUtil.checkCopyrightAndQuestionMarks(driver);
			driver.findElement(By.xpath("//td[@id='navmenucell']/div/dl/dd[3]/a/span[2]")).click();
			if (WebDriverUtil.checkNoErrorPage(driver)) {
				WebDriverUtil.checkCopyrightAndQuestionMarks(driver);
				driver.findElement(By.name("incident_ID")).sendKeys(incidentID);
				driver.findElement(By.id("button")).click();
				if (WebDriverUtil.checkNoErrorPage(driver)) {
					WebDriverUtil.checkCopyrightAndQuestionMarks(driver);
					driver.findElement(By.xpath("//a[contains(@href, 'audit_mbr.do?detail=1&incident_ID=" + incidentID + "')]")).click();
					if (WebDriverUtil.checkNoErrorPage(driver)) {
						WebDriverUtil.checkCopyrightAndQuestionMarks(driver);
						navigateAuditPagination(driver, "audit_id");
						if (WebDriverUtil.checkNoErrorPage(driver)) {
							WebDriverUtil.checkCopyrightAndQuestionMarks(driver);
							success = true;
						} else {
							System.out.println("!!!!!!!!!!!!!!! - Failed to Load Audit Trail for Incident: " + incidentID + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
						}
					} else {
						System.out.println("!!!!!!!!!!!!!!! - Failed to Load Audit Trail for Incident: " + incidentID + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					}
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Failed to Load Incident: " + incidentID + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to Load Incident Tab. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Load Audit Trail. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
		}
		return success;
	}
	
	public static void navigateToIncidentAuditTrail(WebDriver driver, String location, String incidentID) {
		driver.get(location + "audit_mbr.do?detail=1&incident_ID=" + incidentID);
		navigateAuditPagination(driver, "audit_id");
	}
	
	public static void navigateAuditPagination(WebDriver driver, String id) {
		if (id == null || id.isEmpty()) {
			return;
		}
		
		while (WebDriverUtil.isElementPresent(driver, By.xpath("//a[contains(text(),'Next >')]"))) {
			driver.findElement(By.xpath("//a[contains(text(),'Next >')]")).click();
		}
		
		List<WebElement> checkBoxes = driver.findElements(By.name(id));
		System.out.println("AUDIT CHECKBOXES COUNT = " + checkBoxes.size());
		WebElement checkBox = null;
		if (checkBoxes != null && checkBoxes.size() > 0) {
			checkBox = checkBoxes.get(checkBoxes.size() - 1);
		}
		
		WebDriverUtil.check(checkBox);
		
		driver.findElement(By.xpath("(//input[@id='button'])[3]")).click();
	}
	
	public static void navigatePagination(WebDriver driver) {
		while (WebDriverUtil.isElementPresent(driver, By.xpath("//a[contains(text(),'Next >')]"))) {
			driver.findElement(By.xpath("//a[contains(text(),'Next >')]")).click();
		}
	}
	
}
