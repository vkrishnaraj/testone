package aero.nettracer.web.utility;

//custom settings class, read in some configs for your
//tests from a properties file blah blah blah use your
//imagination
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class SeleniumTestBrowserDefault extends Settings {

	private static Selenium browser;
	private static SeleniumServer server;

	public synchronized static void setBrowser(Selenium _browser) {
		browser = _browser;
	}

	public synchronized static Selenium getBrowser() {
		return browser;
	}

	public synchronized static void stopBrowser() {
		browser.stop();
		if (ECLIPSE_RUNS_SERVER) {
			server.stop();
		} else {
			browser.shutDownSeleniumServer();
		}
	}

	public synchronized static void initBrowser() {

		if (browser == null) {
			if (ECLIPSE_RUNS_SERVER) {
				RemoteControlConfiguration config = new RemoteControlConfiguration();
				config.setPort(6789);
				try {
					server = new SeleniumServer(config);
					server.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			browser = new DefaultSelenium("localhost", 6789, BROWSER_IEXPLORE,
					APP_URL_LOCAL);
			browser.start();
			browser.setSpeed(EXECUTION_SPEED_FAST);
		}
	}
}