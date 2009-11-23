package aero.nettracer.serviceprovider.common.db;

import java.util.HashMap;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;


public class WestJetPopulation {
	public static final String TEST_USERNAME = "westjet";
	public static final String TEST_PASSWORD = "password";
	public static final String TEST_PROFILE_NAME = "WS WTR Testing";

	
	public static Profile testProfile = null;
	
	
	@Test
	public void createTestUser() {
		// Generate basic user object
		User u = new User();
		Profile p = new Profile();
		u.setPassword(TEST_PASSWORD);
		u.setUsername(TEST_USERNAME);
		u.setProfile(p);
		p.setName(TEST_PROFILE_NAME);
		HashMap<PermissionType, Boolean> permissions = new HashMap<PermissionType, Boolean>();
		HashMap<ParameterType, String> parameters = new HashMap<ParameterType, String>();
	
		permissions.put(PermissionType.WORLDTRACER, new Boolean(true));
		String TYPE = "WTRWEB";
		parameters.put(ParameterType.WTSM_ACTION_FILE_COUNTS, TYPE);
		parameters.put(ParameterType.WTSM_GET_AHL, TYPE);
		parameters.put(ParameterType.WTSM_CREATE_AHL, TYPE);
		parameters.put(ParameterType.WTSM_AMEND_AHL, TYPE);
		parameters.put(ParameterType.WTSM_CLOSE_AHL, TYPE);
		parameters.put(ParameterType.WTSM_SUSPEND_AHL, TYPE);
		parameters.put(ParameterType.WTSM_REINSTATE_AHL, TYPE);
		parameters.put(ParameterType.WTSM_GET_OHD, TYPE);
		parameters.put(ParameterType.WTSM_CREATE_OHD, TYPE);
		parameters.put(ParameterType.WTSM_AMEND_OHD, TYPE);
		parameters.put(ParameterType.WTSM_CLOSE_OHD, TYPE);
		parameters.put(ParameterType.WTSM_FORWARD_OHD, TYPE);
		parameters.put(ParameterType.WTSM_CREATE_BDO, TYPE);
		parameters.put(ParameterType.WTSM_ERASE_ACTION_FILE, TYPE);
		parameters.put(ParameterType.WTSM_ACTION_FILE_SUMMARY, TYPE);
		parameters.put(ParameterType.WTSM_ACTION_FILE_DETAILS, TYPE);
		parameters.put(ParameterType.WTSM_SEND_FORWARD_MESSAGE, TYPE);
		parameters.put(ParameterType.WTSM_PLACE_ACTION_FILE, TYPE);
		
		p.setPermissions(permissions);
		p.setParameters(parameters);
		
		WorldTracerWebAccount a1 = new WorldTracerWebAccount();
		
		a1.setCaptchaRequired(false);
		a1.setCompanyCode("WS");
		a1.setProfile(p);
		a1.setFrontend(true);
		a1.setTrainingMode(true);
		a1.setHost("wtrweb.worldtracer.aero");
		a1.setUsername("wsdev01");
		a1.setPassword("Calgary1!");
		
		// Save Object
		Session sess = HibernateWrapper.getSession().openSession();
		Transaction t = null;
		try {
			t = sess.beginTransaction();
			sess.save(p);
			sess.save(u);
			sess.save(a1);
			t.commit();
			testProfile = p;
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
			throw new RuntimeException();
		} finally {
			sess.close();
		}
	}
	
}
