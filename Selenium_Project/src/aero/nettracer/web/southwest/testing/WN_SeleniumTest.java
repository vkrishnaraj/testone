package aero.nettracer.web.southwest.testing;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.IncidentUtil;
import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.OnhandUtil;
import aero.nettracer.web.utility.PermissionsUtil;
import aero.nettracer.web.utility.Settings;

public class WN_SeleniumTest extends DefaultSeleneseTestCase {
	
	protected static final String COMPANY_CODE = "WN";
	protected static final String LZ_STATION = "LZ";
	protected static final String BASE_URL = Settings.APP_URL_LOCAL + Settings.PORT3 + "ntsouthwest/";
	protected static final String ADMIN_GROUP_ID = "2";
	
	protected static final String MENU_LD_SEARCH = "menucol_1.4";
	protected static final String MENU_OHD_SEARCH = "menucol_4.4";
	protected static final String MENU_ADMIN_COMPANY = "menucol_10.2";
	protected static final String MENU_ADMIN_AUDIT = "menucol_10.12";
	protected static final String MENU_ADMIN_TEMPLATES = "menucol_10.17";
	protected static final String MENU_ADMIN_DEPRECIATION = "menucol_10.18";
	
	protected static final String INCIDENT_TYPE_LOSTDELAY = "Lost/Delayed";
	protected static final String INCIDENT_TYPE_MISSING = "Missing Articles";
	protected static final String INCIDENT_TYPE_DAMAGED = "Damaged";
	
	protected static final String DRIVERS_LICENSE = "12345";
	protected static final String PASSPORT_NUMBER = "1234567890";
	protected static final String EXPEDITE_TAG_NUM = "012345678912";
	protected static String TODAY;
	protected static String TODAY_SIX_DIGIT;
	
	// set up the current date for use throughout the tests
	static {
		TODAY = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));
		TODAY_SIX_DIGIT = new SimpleDateFormat("MMddyy").format(new Date(System.currentTimeMillis()));
	}
	
	protected boolean navigateToIncidentAuditTrailTest() {
		return IncidentUtil.navigateToIncidentAuditTrailTest(driver, MENU_ADMIN_AUDIT, Settings.INCIDENT_ID_WN);
	}
	
	protected boolean navigateToIncidentAuditTrail() {
		IncidentUtil.navigateToIncidentAuditTrail(driver, BASE_URL, Settings.INCIDENT_ID_WN);
		return true;
	}
	
	protected void navigateAuditPagination(String id) {
		IncidentUtil.navigateAuditPagination(driver, id);
	}
	
	protected boolean navigateToIncidentTest(String incidentType) {
		return IncidentUtil.navigateToIncidentTest(driver, MENU_LD_SEARCH, incidentType, Settings.INCIDENT_ID_WN);
	}
	
	protected boolean navigateToIncident(String incidentType) {
		IncidentUtil.navigateToIncident(driver, BASE_URL, Settings.INCIDENT_ID_WN);
		return true;
	}
	
	protected boolean navigateToOHDAuditTrailTest() {
		return OnhandUtil.navigateToOHDAuditTrailTest(driver, MENU_ADMIN_AUDIT, Settings.ONHAND_ID_WN);
	}
	
	protected boolean navigateToOHDAuditTrail() {
		OnhandUtil.navigateToOHDAuditTrail(driver, BASE_URL, Settings.ONHAND_ID_WN);
		return true;
	}
	
	protected boolean navigateToOnhandTest() {
		return OnhandUtil.navigateToOHDTest(driver, MENU_OHD_SEARCH, Settings.ONHAND_ID_WN);
	}
	
	protected boolean navigateToOnhand() {
		OnhandUtil.navigateToOHD(driver, BASE_URL, Settings.ONHAND_ID_WN);
		return true;
	}
	
	protected boolean logoutOfNt() {
		boolean success = true;
		LoginUtil.logout(driver, BASE_URL);
		return success;
	}
	
	protected boolean loginToNt() {
		boolean success = true;
		LoginUtil.loginNTAutoTest(driver, BASE_URL, COMPANY_CODE, LZ_STATION);
		return success;
	}
	
	protected boolean setPermissions(String[] permissions, boolean[] values) {
		logoutTest();
		boolean success = PermissionsUtil.setPermissions(driver, BASE_URL, COMPANY_CODE, ADMIN_GROUP_ID, permissions, values);
		success = loginToNt() && success;
		return success;
	}
	
	protected void createIncident(boolean forTest) {
		clickMenu("menucol_1.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Prepopulate Lost/Delay Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
		
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			fillRequiredFields();
			if (forTest) {
				fillOptionalFields();
			}
			selenium.click("savetracingButton");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				verifyTrue(isTextPresent("Lost/Delayed Bag Incident has been submitted."));
				if (forTest) {
					checkCopyrightAndQuestionMarks();
					String incident_id = selenium.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
					Settings.INCIDENT_ID_WN = incident_id;
					System.out.println("WN: Lost/Delay Incident Created: " + Settings.INCIDENT_ID_WN);
				}
				selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
				waitForPageToLoadImproved();
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Create Lost/Delay Success Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Edit Lost/Delay Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}
	
	private void fillRequiredFields() {

		selenium.check("id=addresses[0].permanent");
		selenium.click("name=addbagit");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			selenium.type("name=passenger[0].lastname", "Test1");
			selenium.type("name=passenger[0].firstname", "Test1");
			selenium.type("name=addresses[0].address1", "123 circle round drive");
			selenium.type("name=addresses[0].city", "atlanta");
			selenium.select("name=addresses[0].state_ID", "label=Georgia");						
			selenium.type("name=theitinerary[0].legfrom", "ATL");						
			selenium.type("name=theitinerary[0].legto", "AEX");						
			selenium.type("name=theitinerary[0].flightnum", "1234");						
			selenium.click("id=itcalendar0");
			selenium.click("link=Today");					
			selenium.type("name=theitinerary[1].legfrom", "ALS");
			selenium.type("name=theitinerary[1].legto", "ATL");
			selenium.type("name=theitinerary[1].flightnum", "3456");
			selenium.click("id=calendar31");
			selenium.click("link=Today");					
			selenium.select("name=theitem[0].color", "label=BN - Brown");						
			selenium.select("id=bagtype0", "label=25");						
			selenium.select("name=inventorylist[0].categorytype_ID", "label=Alcohol");
			selenium.type("name=inventorylist[0].description", "TEST");
			selenium.select("name=inventorylist[1].categorytype_ID", "label=Alcohol");
			selenium.select("name=inventorylist[2].categorytype_ID", "label=Alcohol");
			selenium.type("name=inventorylist[1].description", "TEST");
			selenium.type("name=inventorylist[2].description", "TEST");
			selenium.type("name=recordlocator", "TESTER");					
			selenium.select("name=passenger[0].salutation", "label=Mr.");			
			selenium.type("name=addresses[0].zip", "33213");					
			selenium.type("name=addresses[0].mobile", "4040213465");				
			selenium.type("name=claimcheck[0].claimchecknum", "1234567890");
			selenium.select("name=theitem[0].manufacturer_ID", "label=Andiamo");
			selenium.type("id=remark[0]", "remark noted");
		} else {
			System.out.println("!!!!!!!!!!!!!!!! failed to 'Add Bag Itinerary'.");
		}
	}
	
	private void fillOptionalFields() {
		selenium.type("name=passenger[0].decriptedDriversLicense", WN_SeleniumTest.DRIVERS_LICENSE);
		selenium.select("name=passenger[0].dlstate", "label=Georgia");
		verifyFalse(isEditable(By.name("passenger[0].driversLicenseProvince")));
		verifyEquals("US", selenium.getValue("name=passenger[0].driversLicenseCountry"));
		selenium.type("name=passenger[0].decryptedPassportNumber", WN_SeleniumTest.PASSPORT_NUMBER);
		selenium.select("name=passenger[0].passportIssuer", "label=United States");
		selenium.type("name=theitem[0].posId", "123456");
		selenium.type("name=addresses[0].email", "email@email.com");
	}
	
}
