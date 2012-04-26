package RealAutoProcessesNotTests;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;


public class TestDeliverLFCDriversLicense extends SeleneseTestCase {

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

		list.add(new AutoClose("00003285","Returned to DMV 4/12"));
		list.add(new AutoClose("00004652","Returned to DMV 4/12"));
		list.add(new AutoClose("00004902","Returned to DMV 4/12"));
		list.add(new AutoClose("00004925","Returned to DMV 4/12"));
		list.add(new AutoClose("00004928","Returned to DMV 4/12"));
		list.add(new AutoClose("00005034","Returned to DMV 4/12"));
		list.add(new AutoClose("00005051","Returned to DMV 4/12"));
		list.add(new AutoClose("00005063","Returned to DMV 4/12"));
		list.add(new AutoClose("00005066","Returned to DMV 4/12"));
		list.add(new AutoClose("00005073","Returned to DMV 4/12"));
		list.add(new AutoClose("00005081","Returned to DMV 4/12"));
		list.add(new AutoClose("00005148","Returned to DMV 4/12"));
		list.add(new AutoClose("00005185","Returned to DMV 4/12"));
		list.add(new AutoClose("00005186","Returned to DMV 4/12"));
		list.add(new AutoClose("00005193","Returned to DMV 4/12"));
		list.add(new AutoClose("00005251","Returned to DMV 4/12"));
		list.add(new AutoClose("00005254","Returned to DMV 4/12"));
		list.add(new AutoClose("00005261","Returned to DMV 4/12"));
		list.add(new AutoClose("00005275","Returned to DMV 4/12"));
		list.add(new AutoClose("00005278","Returned to DMV 4/12"));
		list.add(new AutoClose("00005279","Returned to DMV 4/12"));
		list.add(new AutoClose("00005283","Returned to DMV 4/12"));
		list.add(new AutoClose("00005302","Returned to DMV 4/12"));
		list.add(new AutoClose("00005314","Returned to DMV 4/12"));
		list.add(new AutoClose("00005336","Returned to DMV 4/12"));
		list.add(new AutoClose("00005337","Returned to DMV 4/12"));
		list.add(new AutoClose("00005339","Returned to DMV 4/12"));
		list.add(new AutoClose("00005340","Returned to DMV 4/12"));
		list.add(new AutoClose("00005341","Returned to DMV 4/12"));
		list.add(new AutoClose("00005342","Returned to DMV 4/12"));
		list.add(new AutoClose("00005343","Returned to DMV 4/12"));
		list.add(new AutoClose("00005411","Returned to DMV 4/12"));
		list.add(new AutoClose("00005426","Returned to DMV 4/12"));
		list.add(new AutoClose("00005451","Returned to DMV 4/12"));
		list.add(new AutoClose("00005461","Returned to DMV 4/12"));
		list.add(new AutoClose("00005462","Returned to DMV 4/12"));
		list.add(new AutoClose("00005463","Returned to DMV 4/12"));
		list.add(new AutoClose("00005464","Returned to DMV 4/12"));
		list.add(new AutoClose("00005465","Returned to DMV 4/12"));
		list.add(new AutoClose("00005466","Returned to DMV 4/12"));
		list.add(new AutoClose("00005467","Returned to DMV 4/12"));
		list.add(new AutoClose("00005468","Returned to DMV 4/12"));
		list.add(new AutoClose("00005469","Returned to DMV 4/12"));
		list.add(new AutoClose("00005470","Returned to DMV 4/12"));
		list.add(new AutoClose("00005471","Returned to DMV 4/12"));
		list.add(new AutoClose("00005488","Returned to DMV 4/12"));
		list.add(new AutoClose("00005505","Returned to DMV 4/12"));
		list.add(new AutoClose("00005558","Returned to DMV 4/12"));
		list.add(new AutoClose("00005559","Returned to DMV 4/12"));
		list.add(new AutoClose("00005562","Returned to DMV 4/12"));
		list.add(new AutoClose("00005563","Returned to DMV 4/12"));
		list.add(new AutoClose("00005574","Returned to DMV 4/12"));
		list.add(new AutoClose("00005589","Returned to DMV 4/12"));
		list.add(new AutoClose("00005597","Returned to DMV 4/12"));
		list.add(new AutoClose("00005607","Returned to DMV 4/12"));
		list.add(new AutoClose("00005615","Returned to DMV 4/12"));
		list.add(new AutoClose("00005617","Returned to DMV 4/12"));
		list.add(new AutoClose("00005681","Returned to DMV 4/12"));
		list.add(new AutoClose("00005682","Returned to DMV 4/12"));
		list.add(new AutoClose("00005683","Returned to DMV 4/12"));
		list.add(new AutoClose("00005713","Returned to DMV 4/12"));
		list.add(new AutoClose("00005714","Returned to DMV 4/12"));
		list.add(new AutoClose("00005715","Returned to DMV 4/12"));
		list.add(new AutoClose("00005716","Returned to DMV 4/12"));
		list.add(new AutoClose("00005717","Returned to DMV 4/12"));
		list.add(new AutoClose("00005718","Returned to DMV 4/12"));
		list.add(new AutoClose("00005719","Returned to DMV 4/12"));
		list.add(new AutoClose("00005766","Returned to DMV 4/12"));
		list.add(new AutoClose("00005773","Returned to DMV 4/12"));
		list.add(new AutoClose("00005784","Returned to DMV 4/12"));
		list.add(new AutoClose("00005788","Returned to DMV 4/12"));
		list.add(new AutoClose("00005825","Returned to DMV 4/12"));
		list.add(new AutoClose("00005826","Returned to DMV 4/12"));
		list.add(new AutoClose("00005827","Returned to DMV 4/12"));
		list.add(new AutoClose("00005839","Returned to DMV 4/12"));
		list.add(new AutoClose("00005850","Returned to DMV 4/12"));
		list.add(new AutoClose("00005865","Returned to DMV 4/12"));
		list.add(new AutoClose("00005866","Returned to DMV 4/12"));
		list.add(new AutoClose("00005905","Returned to DMV 4/12"));
		list.add(new AutoClose("00006004","Returned to DMV 4/12"));
		list.add(new AutoClose("00006039","Returned to DMV 4/12"));
		list.add(new AutoClose("00006040","Returned to DMV 4/12"));
		list.add(new AutoClose("00006049","Returned to DMV 4/12"));
		list.add(new AutoClose("00006050","Returned to DMV 4/12"));
		list.add(new AutoClose("00006056","Returned to DMV 4/12"));
		list.add(new AutoClose("00006083","Returned to DMV 4/12"));
		list.add(new AutoClose("00006085","Returned to DMV 4/12"));
		list.add(new AutoClose("00006086","Returned to DMV 4/12"));
		list.add(new AutoClose("00006103","Returned to DMV 4/12"));
		list.add(new AutoClose("00006111","Returned to DMV 4/12"));
		list.add(new AutoClose("00006128","Returned to DMV 4/12"));
		list.add(new AutoClose("00006129","Returned to DMV 4/12"));
		list.add(new AutoClose("00006133","Returned to DMV 4/12"));
		list.add(new AutoClose("00006134","Returned to DMV 4/12"));
		list.add(new AutoClose("00006137","Returned to DMV 4/12"));
		list.add(new AutoClose("00006141","Returned to DMV 4/12"));
		list.add(new AutoClose("00006152","Returned to DMV 4/12"));
		list.add(new AutoClose("00006153","Returned to DMV 4/12"));
		list.add(new AutoClose("00006154","Returned to DMV 4/12"));
		list.add(new AutoClose("00006172","Returned to DMV 4/12"));
		list.add(new AutoClose("00006175","Returned to DMV 4/12"));
		list.add(new AutoClose("00006176","Returned to DMV 4/12"));
		list.add(new AutoClose("00006188","Returned to DMV 4/12"));
		list.add(new AutoClose("00006218","Returned to DMV 4/12"));
		list.add(new AutoClose("00006219","Returned to DMV 4/12"));
		list.add(new AutoClose("00006231","Returned to DMV 4/12"));
		list.add(new AutoClose("00006232","Returned to DMV 4/12"));
		list.add(new AutoClose("00006234","Returned to DMV 4/12"));
		list.add(new AutoClose("00006254","Returned to DMV 4/12"));
		list.add(new AutoClose("00006255","Returned to DMV 4/12"));
		list.add(new AutoClose("00006283","Returned to DMV 4/12"));
		list.add(new AutoClose("00006284","Returned to DMV 4/12"));
		list.add(new AutoClose("00006285","Returned to DMV 4/12"));
		list.add(new AutoClose("00006286","Returned to DMV 4/12"));
		list.add(new AutoClose("00006287","Returned to DMV 4/12"));
		list.add(new AutoClose("00006288","Returned to DMV 4/12"));
		list.add(new AutoClose("00006289","Returned to DMV 4/12"));
		list.add(new AutoClose("00006290","Returned to DMV 4/12"));
		list.add(new AutoClose("00006300","Returned to DMV 4/12"));
		list.add(new AutoClose("00006301","Returned to DMV 4/12"));
		list.add(new AutoClose("00006310","Returned to DMV 4/12"));
		list.add(new AutoClose("00006326","Returned to DMV 4/12"));
		list.add(new AutoClose("00006330","Returned to DMV 4/12"));
		list.add(new AutoClose("00006331","Returned to DMV 4/12"));
		list.add(new AutoClose("00006335","Returned to DMV 4/12"));
		list.add(new AutoClose("00006336","Returned to DMV 4/12"));
		list.add(new AutoClose("00006337","Returned to DMV 4/12"));
		list.add(new AutoClose("00006345","Returned to DMV 4/12"));
		list.add(new AutoClose("00006348","Returned to DMV 4/12"));
		list.add(new AutoClose("00006369","Returned to DMV 4/12"));
		list.add(new AutoClose("00006370","Returned to DMV 4/12"));
		list.add(new AutoClose("00006371","Returned to DMV 4/12"));
		list.add(new AutoClose("00006399","Returned to DMV 4/12"));
		list.add(new AutoClose("00006424","Returned to DMV 4/12"));
		list.add(new AutoClose("00006425","Returned to DMV 4/12"));
		list.add(new AutoClose("00006431","Returned to DMV 4/12"));
		list.add(new AutoClose("00006432","Returned to DMV 4/12"));
		list.add(new AutoClose("00006433","Returned to DMV 4/12"));
		list.add(new AutoClose("00006448","Returned to DMV 4/12"));
		list.add(new AutoClose("00006449","Returned to DMV 4/12"));
		list.add(new AutoClose("00006475","Returned to DMV 4/12"));
		list.add(new AutoClose("00006476","Returned to DMV 4/12"));
		list.add(new AutoClose("00006477","Returned to DMV 4/12"));
		list.add(new AutoClose("00006478","Returned to DMV 4/12"));
		list.add(new AutoClose("00006479","Returned to DMV 4/12"));
		list.add(new AutoClose("00006503","Returned to DMV 4/12"));
		list.add(new AutoClose("00006520","Returned to DMV 4/12"));
		list.add(new AutoClose("00006521","Returned to DMV 4/12"));
		list.add(new AutoClose("00006522","Returned to DMV 4/12"));
		list.add(new AutoClose("00006524","Returned to DMV 4/12"));
		list.add(new AutoClose("00006525","Returned to DMV 4/12"));
		list.add(new AutoClose("00006542","Returned to DMV 4/12"));
		list.add(new AutoClose("00006546","Returned to DMV 4/12"));
		list.add(new AutoClose("00006566","Returned to DMV 4/12"));
		list.add(new AutoClose("00006577","Returned to DMV 4/12"));
		list.add(new AutoClose("00006583","Returned to DMV 4/12"));
		list.add(new AutoClose("00006633","Returned to DMV 4/12"));
		list.add(new AutoClose("00006635","Returned to DMV 4/12"));
		list.add(new AutoClose("00006636","Returned to DMV 4/12"));
		list.add(new AutoClose("00006637","Returned to DMV 4/12"));
		list.add(new AutoClose("00006668","Returned to DMV 4/12"));
		list.add(new AutoClose("00006675","Returned to DMV 4/12"));
		list.add(new AutoClose("00006687","Returned to DMV 4/12"));
		list.add(new AutoClose("00011912","Returned to DMV 4/12"));
		list.add(new AutoClose("00011924","Returned to DMV 4/12"));
		list.add(new AutoClose("00011975","Returned to DMV 4/12"));
		list.add(new AutoClose("00011979","Returned to DMV 4/12"));
		list.add(new AutoClose("00011996","Returned to DMV 4/12"));
		list.add(new AutoClose("00011997","Returned to DMV 4/12"));
		list.add(new AutoClose("00012016","Returned to DMV 4/12"));
		list.add(new AutoClose("00012030","Returned to DMV 4/12"));
		list.add(new AutoClose("00012036","Returned to DMV 4/12"));
		list.add(new AutoClose("00012042","Returned to DMV 4/12"));
		list.add(new AutoClose("00012054","Returned to DMV 4/12"));
		list.add(new AutoClose("00012060","Returned to DMV 4/12"));
		list.add(new AutoClose("00012064","Returned to DMV 4/12"));
		list.add(new AutoClose("00012068","Returned to DMV 4/12"));
		list.add(new AutoClose("00012069","Returned to DMV 4/12"));
		list.add(new AutoClose("00012076","Returned to DMV 4/12"));
		list.add(new AutoClose("00012077","Returned to DMV 4/12"));
		list.add(new AutoClose("00012085","Returned to DMV 4/12"));
		list.add(new AutoClose("00012086","Returned to DMV 4/12"));
		list.add(new AutoClose("00012091","Returned to DMV 4/12"));
		list.add(new AutoClose("00012113","Returned to DMV 4/12"));
		list.add(new AutoClose("00012114","Returned to DMV 4/12"));
		list.add(new AutoClose("00012115","Returned to DMV 4/12"));
		list.add(new AutoClose("00012132","Returned to DMV 4/12"));
		list.add(new AutoClose("00012140","Returned to DMV 4/12"));
		list.add(new AutoClose("00012143","Returned to DMV 4/12"));
		list.add(new AutoClose("00012150","Returned to DMV 4/12"));
		list.add(new AutoClose("00012173","Returned to DMV 4/12"));
		list.add(new AutoClose("00012174","Returned to DMV 4/12"));
		list.add(new AutoClose("00012176","Returned to DMV 4/12"));
		list.add(new AutoClose("00012210","Returned to DMV 4/12"));
		list.add(new AutoClose("00012223","Returned to DMV 4/12"));
		list.add(new AutoClose("00012234","Returned to DMV 4/12"));
		list.add(new AutoClose("00012282","Returned to DMV 4/12"));
		list.add(new AutoClose("00012286","Returned to DMV 4/12"));
		list.add(new AutoClose("00012288","Returned to DMV 4/12"));
		list.add(new AutoClose("00012289","Returned to DMV 4/12"));
		list.add(new AutoClose("00012290","Returned to DMV 4/12"));
		list.add(new AutoClose("00012291","Returned to DMV 4/12"));
		list.add(new AutoClose("00012292","Returned to DMV 4/12"));
		list.add(new AutoClose("00012306","Returned to DMV 4/12"));
		list.add(new AutoClose("00013906","Returned to DMV 4/12"));
		list.add(new AutoClose("00013930","Returned to DMV 4/12"));
		list.add(new AutoClose("00013941","Returned to DMV 4/12"));
		list.add(new AutoClose("00021627","Returned to DMV 4/12"));
		list.add(new AutoClose("00021628","Returned to DMV 4/12"));
		list.add(new AutoClose("00021638","Returned to DMV 4/12"));
		list.add(new AutoClose("00021699","Returned to DMV 4/12"));
		list.add(new AutoClose("00021787","Returned to DMV 4/12"));
		list.add(new AutoClose("00021814","Returned to DMV 4/12"));
		list.add(new AutoClose("00021832","Returned to DMV 4/12"));
		list.add(new AutoClose("00021875","Returned to DMV 4/12"));
		list.add(new AutoClose("00021880","Returned to DMV 4/12"));
		list.add(new AutoClose("00021881","Returned to DMV 4/12"));
		list.add(new AutoClose("00021882","Returned to DMV 4/12"));
		list.add(new AutoClose("00021883","Returned to DMV 4/12"));
		list.add(new AutoClose("00021892","Returned to DMV 4/12"));
		list.add(new AutoClose("00023903","Returned to DMV 4/12"));
		list.add(new AutoClose("00024551","Returned to DMV 4/12"));
		list.add(new AutoClose("00026310","Returned to DMV 4/12"));
		list.add(new AutoClose("00026522","Returned to DMV 4/12"));
		list.add(new AutoClose("00026723","Returned to DMV 4/12"));
		list.add(new AutoClose("00026819","Returned to DMV 4/12"));
		list.add(new AutoClose("00026832","Returned to DMV 4/12"));
		list.add(new AutoClose("00026844","Returned to DMV 4/12"));
		list.add(new AutoClose("00026845","Returned to DMV 4/12"));
		list.add(new AutoClose("00026846","Returned to DMV 4/12"));
		list.add(new AutoClose("00026852","Returned to DMV 4/12"));
		list.add(new AutoClose("00027740","Returned to DMV 4/12"));
		list.add(new AutoClose("00028968","Returned to DMV 4/12"));
		list.add(new AutoClose("00028969","Returned to DMV 4/12"));
		list.add(new AutoClose("00028996","Returned to DMV 4/12"));
		list.add(new AutoClose("00028999","Returned to DMV 4/12"));
		list.add(new AutoClose("00029022","Returned to DMV 4/12"));
		list.add(new AutoClose("00029027","Returned to DMV 4/12"));
		list.add(new AutoClose("00030862","Returned to DMV 4/12"));
		list.add(new AutoClose("00030957","Returned to DMV 4/12"));
		list.add(new AutoClose("00030985","Returned to DMV 4/12"));


		
		login();
		
		for (AutoClose close: list) {
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
