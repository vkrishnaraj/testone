package aero.nettracer.web.defaul.testing;

import com.thoughtworks.selenium.SeleneseTestCase;

public class DefaultSeleneseTestCase extends SeleneseTestCase {
	
	public void setUp() throws Exception {
		selenium = SeleniumTestBrowserDefault.getBrowser();
	}

}
