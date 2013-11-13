package RealAutoProcessesNotTests;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestBase;


public class TestDeliverLFCDriversLicense extends SeleneseTestBase {

	private static SeleniumServer server;
	
	@Before
	public void setUp() throws Exception {
		RemoteControlConfiguration config = new RemoteControlConfiguration();
		config.setPort(4444);
		server = new SeleniumServer(config);
		server.start();
		
		selenium = new DefaultSelenium("localhost", 4444, "*iexplore", "https://live.lostandfound.aero/");
		selenium.start();
	}

	@Test
	public void testCase() throws Exception {
		
		ArrayList<AutoClose> list = new ArrayList<AutoClose>();

		list.add(new AutoClose("00045528","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032257","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045745","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032468","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045363","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00019829","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032598","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045489","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032599","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032603","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032571","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032572","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045353","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032265","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045602","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032323","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032270","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045181","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032534","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032524","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032615","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032521","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045591","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032519","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045535","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032241","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032227","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032467","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032520","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032409","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032465","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032408","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045858","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032411","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032412","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032448","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032329","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045349","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032238","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045159","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032438","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045425","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045577","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00019775","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045362","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045589","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045470","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045402","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045228","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045175","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045471","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045359","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032410","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045227","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032443","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045302","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045601","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032247","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045590","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045398","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032240","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032266","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045280","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045540","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045401","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032239","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032570","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00045525","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032490","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032447","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032502","DL Mailed 7/28-8/3"));
		list.add(new AutoClose("00032664","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032661","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045659","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00011892","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045664","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045683","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045860","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045759","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032671","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00011891","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032682","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00047420","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032632","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00047440","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045765","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045684","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032709","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045714","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00047405","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032705","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045665","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032652","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00019881","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00047532","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032670","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032665","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032658","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00047416","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00047531","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00047503","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032674","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032677","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00047553","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045783","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045660","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045758","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045761","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045763","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032687","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00019877","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045674","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045853","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045770","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00047533","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032806","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00047551","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032673","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045724","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00047516","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032872","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032644","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00047543","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00047403","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045681","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045869","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00047409","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00033034","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00047438","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032680","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045658","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045723","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032678","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045760","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032662","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045762","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032804","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045764","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00019887","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045643","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032703","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032827","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00047393","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032706","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045628","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00032803","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045725","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00047404","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00047530","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00047529","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00045857","DL Mailed 8/6-8/11"));
		list.add(new AutoClose("00047528","DL Mailed 8/6-8/11"));


		
		login();
		
		int i = 0;
		for (AutoClose close: list) {
			++i;
			System.out.println("Attempting: " + i + " out of " + list.size());
			markAsDelivered(close.getBarcode(), close.getTrackingNumber());
		}
		
	}

	private void markAsDelivered(String barcode, String trackingNumber) throws InterruptedException {
		selenium.type("id=barcode", barcode);
		selenium.click("id=button");
		selenium.waitForPageToLoad("40000");
		try {
			selenium.type("id=trackingNumber", trackingNumber);
//			Thread.sleep(30);
			selenium.click("name=saveButton");
			selenium.waitForPageToLoad("40000");
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		selenium.click("id=menucol_0.0");
		selenium.waitForPageToLoad("40000");
	}

	private void login() {
		selenium.open("/lostandfound/logoff.do");
		selenium.type("name=username", "ntadmin");
		selenium.type("name=password", "InstaNokia41!");
		selenium.click("id=button");
		selenium.waitForPageToLoad("40000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
