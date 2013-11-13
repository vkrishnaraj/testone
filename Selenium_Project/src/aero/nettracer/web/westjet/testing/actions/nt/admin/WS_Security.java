package aero.nettracer.web.westjet.testing.actions.nt.admin;

import org.junit.Test;
import org.openqa.selenium.By;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class WS_Security extends DefaultSeleneseTestCase {
	@Test
	public void testMinPasswordLength(){
		clickMenu("menucol_10.2");
		click(By.xpath("//td[@id='navmenucell']/div/dl/dd[4]/a/span[2]"));
		type(By.id("min_pass_size"), "8");
		click(By.xpath("(//input[@id='button'])[2]"), false, true);
		verifyEquals("8", getValue(By.id("min_pass_size")));
		verifyTrue(isTextPresent("Company Information Saved."));

		driver.navigate().refresh();
		type(By.id("min_pass_size"), "7");
		click(By.xpath("(//input[@id='button'])[2]"), false, true);
		assertEquals("Minimum password length must be at least eigth(8) characters", getAlert());
		verifyEquals("7", getValue(By.id("min_pass_size")));
		verifyTrue(!isTextPresent("Company Information Saved."));

		driver.navigate().refresh();
		verifyNotEquals("7", getValue(By.id("min_pass_size")));
		type(By.id("min_pass_size"), "9");
		click(By.xpath("(//input[@id='button'])[2]"), false, true);
		verifyTrue(isTextPresent("Company Information Saved."));
	}
}
