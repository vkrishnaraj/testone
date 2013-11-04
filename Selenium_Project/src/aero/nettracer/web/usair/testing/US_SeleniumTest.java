package aero.nettracer.web.usair.testing;

import java.text.SimpleDateFormat;
import java.util.Date;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.IncidentUtil;
import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.OnhandUtil;
import aero.nettracer.web.utility.PermissionsUtil;
import aero.nettracer.web.utility.Settings;

public class US_SeleniumTest extends DefaultSeleneseTestCase {
	
	protected static final String COMPANY_CODE = "US";
	protected static final String LZ_STATION = "HDQ";
	protected static final String BASE_URL = Settings.APP_URL_LOCAL + Settings.PORT3 + "usair/";
	protected static final String ADMIN_GROUP_ID = "2";
	
	protected static final String MENU_LD_SEARCH = "menucol_1.4";
	protected static final String MENU_OHD_SEARCH = "menucol_4.4";
	protected static final String MENU_ADMIN_AUDIT = "menucol_9.12";
	
	protected static final String INCIDENT_TYPE_LOSTDELAY = "Lost/Delayed";
	protected static final String INCIDENT_TYPE_MISSING = "Missing Articles";
	protected static final String INCIDENT_TYPE_DAMAGED = "Damaged";
	
	protected static String INCIDENT_ID;
	protected static String ONHAND_ID;
	
	protected static String TODAY;
	protected static String TODAY_SIX_DIGIT;
	
	// set up the current date for use throughout the tests
	static {
		TODAY = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));
		TODAY_SIX_DIGIT = new SimpleDateFormat("MMddyy").format(new Date(System.currentTimeMillis()));
	}
	
	protected boolean navigateToIncidentAuditTrailTest() {
		return IncidentUtil.navigateToIncidentAuditTrailTest(driver, MENU_ADMIN_AUDIT, INCIDENT_ID);
	}
	
	protected boolean navigateToIncidentAuditTrail() {
		IncidentUtil.navigateToIncidentAuditTrail(driver, BASE_URL, INCIDENT_ID);
		return true;
	}
	
	protected void navigateAuditPagination(String id) {
		IncidentUtil.navigateAuditPagination(driver, id);
	}
	
	protected boolean navigateToIncidentTest(String incidentType) {
		return IncidentUtil.navigateToIncidentTest(driver, MENU_LD_SEARCH, incidentType, INCIDENT_ID);
	}
	
	protected boolean navigateToIncident(String incidentType) {
		IncidentUtil.navigateToIncident(driver, BASE_URL, INCIDENT_ID);
		return true;
	}
	
	protected boolean navigateToOHDAuditTrailTest() {
		return OnhandUtil.navigateToOHDAuditTrailTest(driver, MENU_ADMIN_AUDIT, ONHAND_ID);
	}
	
	protected boolean navigateToOHDAuditTrail() {
		OnhandUtil.navigateToOHDAuditTrail(driver, BASE_URL, ONHAND_ID);
		return true;
	}
	
	protected boolean navigateToOnhandTest() {
		return OnhandUtil.navigateToOHDTest(driver, MENU_OHD_SEARCH, ONHAND_ID);
	}
	
	protected boolean navigateToOnhand() {
		OnhandUtil.navigateToOHD(driver, BASE_URL, ONHAND_ID);
		return true;
	}
	
	protected boolean logoutOfNt() {
		boolean success = true;
		LoginUtil.logout(driver, BASE_URL);
		return success;
	}
	
	protected boolean loginToNt() {
		boolean success = true;
		LoginUtil.loginNTAuto(driver, BASE_URL, COMPANY_CODE, LZ_STATION);
		return success;
	}
	
	protected boolean setPermissions(String[] permissions, boolean[] values) {
		boolean success =  PermissionsUtil.setPermissions(driver, BASE_URL, COMPANY_CODE, ADMIN_GROUP_ID, permissions, values);
		success = success && loginToNt();
		return success;
	}

}
