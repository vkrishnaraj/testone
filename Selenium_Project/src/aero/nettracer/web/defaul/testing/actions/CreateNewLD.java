package aero.nettracer.web.defaul.testing.actions;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class CreateNewLD extends DefaultSeleneseTestCase {

	@Test
	public void testCreate_New_LD() throws Exception {
		selenium.click("menucol_1.1");
		selenium.waitForPageToLoad("30000");
		selenium.type("recordlocator", "D6KUDT");
		//selenium.click("skip_prepopulate");
		//selenium.waitForPageToLoad("30000");
		//selenium.type("recordlocator", "ATEST1");
		selenium.type("passenger[0].lastname", "Test");
		selenium.type("passenger[0].firstname", "Test");
		selenium.type("addresses[0].address1", "123 Test St");
		selenium.type("addresses[0].city", "Test");
		selenium.select("addresses[0].state_ID", "label=Georgia");
		selenium.type("addresses[0].zip", "30339");
		selenium.type("theitinerary[0].legfrom", "ABR");
		selenium.type("theitinerary[0].legto", "ALB");
		selenium.type("theitinerary[0].flightnum", "123");
		selenium.type("theitinerary[0].disarrivedate", "11/04/2010");
		selenium.type("theitinerary[0].disdepartdate", "11/04/2010");
		selenium.type("theitinerary[1].legfrom", "ABR");
		selenium.type("theitinerary[1].legto", "ALB");
		selenium.type("theitinerary[1].flightnum", "123");
		selenium.type("theitinerary[1].disdepartdate", "11/04/2010");
		selenium.type("theitinerary[1].disarrivedate", "11/04/2010");
		selenium.select("theitem[0].color", "label=BK - Black");
		selenium.select("theitem[0].bagtype", "label=01");
		selenium.type("inventorylist[0].description", "Test");
		selenium.type("remark[0].remarktext", "Test");
		selenium.select("inventorylist[0].categorytype_ID", "label=Alcohol");
		selenium.select("theitem[0].manufacturer_ID", "label=Adrienne Vittadini");
		selenium.type("theitem[0].lnameonbag", "Test");
		selenium.type("theitem[0].fnameonbag", "Test");
		selenium.select("theitem[0].xdescelement_ID_1", "label=B - single item in a box");
		selenium.select("theitem[0].xdescelement_ID_2", "label=C - combination locks");
		selenium.select("theitem[0].xdescelement_ID_3", "label=D - dual soft/hard");
		selenium.type("claimcheck[0].claimchecknum", "TS123456");
		selenium.click("addinventory[0]");
		selenium.waitForPageToLoad("30000");
		selenium.select("inventorylist[1].categorytype_ID", "label=Audio");
		selenium.type("inventorylist[1].description", "Test");
		selenium.click("addinventory[0]");
		selenium.waitForPageToLoad("30000");
		selenium.select("inventorylist[2].categorytype_ID", "label=Book");
		selenium.type("inventorylist[2].description", "Test");
		selenium.type("addresses[0].mobile", "1231231234");
		selenium.type("theitem[0].mnameonbag", "W");
		selenium.click("savetracingButton");
		selenium.waitForPageToLoad("180000");
		String pawob = selenium.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
		Settings.INCIDENT_ID = pawob;
		System.out.println("PAWOB # Set: " + Settings.INCIDENT_ID);
		
	}
}
