package aero.nettracer.web.defaul.testing;

import org.junit.Before;

import com.thoughtworks.selenium.SeleneseTestCase;

public class DefaultSeleneseTestCase extends SeleneseTestCase {
	
	@Before
	public void setUp() throws Exception {
		selenium = SeleniumTestBrowserDefault.getBrowser();
	}

}
