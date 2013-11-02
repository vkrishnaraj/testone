package aero.nettracer.web.utility;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class OnhandUtil {
	
	public static boolean navigateToOHDTest(WebDriver driver, String searchMenu, String ohdID) {
		boolean success = false;
		try {
			WebDriverUtil.clickMenu(driver, searchMenu);
			if (WebDriverUtil.checkNoErrorPage(driver)) {
				WebDriverUtil.checkCopyrightAndQuestionMarks(driver);
				driver.findElement(By.name("incident_ID")).sendKeys(ohdID);
				driver.findElement(By.id("button")).click();
				if (WebDriverUtil.checkNoErrorPage(driver)) {
					driver.findElement(By.linkText(ohdID)).click();
					if (WebDriverUtil.checkNoErrorPage(driver)) {
						WebDriverUtil.checkCopyrightAndQuestionMarks(driver);
						success = true;
					} else {
						System.out.println("!!!!!!!!!!!!!!! - Failed to Load OHD" + ohdID + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					}
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Failed to Load OHD Results. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to Load OHD Search Page. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			}
		} catch (Exception e) {
			System.out.println("!!!!!!!!!!!!!!! - Failed to navigate to OHD: " + ohdID + " - !!!!!!!!!!!!!!!!!!");
			e.printStackTrace();
		}
		return success;
	}
	
	public static void navigateToOHD(WebDriver driver, String location, String ohdID) {
		driver.get(location + "addOnHandBag.do?ohd_ID=" + ohdID);
	}
	
	public static boolean navigateToOHDAuditTrailTest(WebDriver driver, String auditMenu, String ohdID) {
		boolean success = false;
		WebDriverUtil.clickMenu(driver, auditMenu);
		if (WebDriverUtil.checkNoErrorPage(driver)) {
			WebDriverUtil.checkCopyrightAndQuestionMarks(driver);
			driver.findElement(By.xpath("//td[@id='navmenucell']/div/dl/dd/a/span[2]")).click();
			if (WebDriverUtil.checkNoErrorPage(driver)) {
				WebDriverUtil.checkCopyrightAndQuestionMarks(driver);
				driver.findElement(By.name("ohd_ID")).sendKeys(ohdID);
				driver.findElement(By.id("button")).click();
				if (WebDriverUtil.checkNoErrorPage(driver)) {
					WebDriverUtil.checkCopyrightAndQuestionMarks(driver);
					driver.findElement(By.xpath("//a[contains(@href, 'audit_ohd.do?detail=1&ohd_ID=" + ohdID + "')]")).click();
					if (WebDriverUtil.checkNoErrorPage(driver)) {
						WebDriverUtil.checkCopyrightAndQuestionMarks(driver);
						navigateAuditPagination(driver, "audit_id");
						if (WebDriverUtil.checkNoErrorPage(driver)) {
							WebDriverUtil.checkCopyrightAndQuestionMarks(driver);
							success = true;
						} else {
							System.out.println("!!!!!!!!!!!!!!! - Failed to Load Audit Trail for OHD: " + ohdID + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
						}
					} else {
						System.out.println("!!!!!!!!!!!!!!! - Failed to Load Audit Trail for OHD: " + ohdID + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					}
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Failed to Load OHD: " + ohdID + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to Load OHD Tab. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Load Audit Trail. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
		}
		return success;
	}
	
	public static void navigateToOHDAuditTrail(WebDriver driver, String location, String ohdID) {
		driver.get(location + "audit_ohd.do?detail=1&ohd_ID=" + ohdID);
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
	
}
