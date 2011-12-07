package aero.nettracer.web.defaul.testing.actions;

import org.junit.Test;

import aero.nettracer.web.defaul.testing.DefaultSeleneseTestCase;
import aero.nettracer.web.defaul.testing.Settings;

public class Login extends DefaultSeleneseTestCase {
	
	@Test
	public void testLogin() throws Exception {
		selenium.open("westjet");
		System.out.println("Logging In Using " + Settings.USERNAME + " " + Settings.PASSWORD);
		selenium.type("username", Settings.USERNAME);
		selenium.type("password", Settings.PASSWORD);
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
	}
	
}
