package aero.nettracer.web.lfc.testing;

import java.text.SimpleDateFormat;
import java.util.Date;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.IncidentUtil;
import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.PermissionsUtil;
import aero.nettracer.web.utility.Settings;

public class LFC_SeleniumTest extends DefaultSeleneseTestCase {
	
	protected static final String COMPANY_CODE = "LF";
	protected static final String LZ_STATION = "LZ";
	protected static final String BASE_URL = Settings.APP_URL_LOCAL + Settings.PORT2 + "lostandfound/";
	protected static final String ADMIN_GROUP_ID = "240";
	protected static final String CLIENT_WN_URL = Settings.APP_URL_LOCAL + Settings.PORT2 + Settings.START_URL_LF_WN;
	protected static final String CLIENT_WN_LOGIN_URL = Settings.APP_URL_LOCAL + Settings.PORT2 + Settings.START_URL_LF_LG;
	
	protected static String TODAY;
	protected static String TODAY_SIX_DIGIT;
	
	// set up the current date for use throughout the tests
	static {
		TODAY = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));
		TODAY_SIX_DIGIT = new SimpleDateFormat("MMddyy").format(new Date(System.currentTimeMillis()));
	}
	
	protected void navigateAuditPagination(String id) {
		IncidentUtil.navigateAuditPagination(driver, id);
	}
	
	protected boolean logoutOfNt() {
		boolean success = true;
		LoginUtil.logout(driver, BASE_URL);
		return success;
	}
	
	protected boolean loginToNt() {
		boolean success = true;
		LoginUtil.loginNTAutoTest(driver, BASE_URL, null, LZ_STATION);
		return success;
	}
	
	protected boolean setPermissions(String[] permissions, boolean[] values) {
		logoutTest();
		boolean success = PermissionsUtil.setPermissions(driver, BASE_URL, COMPANY_CODE, ADMIN_GROUP_ID, permissions, values);
		success = loginToNt() && success;
		return success;
	}
	
}
