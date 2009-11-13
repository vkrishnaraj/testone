package aero.nettracer.serviceprovider.common.db;

import java.util.HashMap;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;


public class WorldTracerPopulation {
	public static final String TEST_USERNAME = "wtr_local";
	public static final String TEST_PASSWORD = "password";
	public static final String TEST_PROFILE_NAME = "WTR Testing";

	
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

		parameters.put(ParameterType.WTSM_ACTION_FILE_COUNTS, "WTRWEB");
		parameters.put(ParameterType.WTSM_GET_AHL, "WTRWEB");
		parameters.put(ParameterType.WTSM_CREATE_AHL, "WTRWEB");
		parameters.put(ParameterType.WTSM_AMEND_AHL, "WTRWEB");
		parameters.put(ParameterType.WTSM_CLOSE_AHL, "WTRWEB");
		parameters.put(ParameterType.WTSM_SUSPEND_AHL, "WTRWEB");
		parameters.put(ParameterType.WTSM_REINSTATE_AHL, "WTRWEB");
		parameters.put(ParameterType.WTSM_GET_OHD, "WTRWEB");
		parameters.put(ParameterType.WTSM_CREATE_OHD, "WTRWEB");
		parameters.put(ParameterType.WTSM_AMEND_OHD, "WTRWEB");
		parameters.put(ParameterType.WTSM_CLOSE_OHD, "WTRWEB");
		parameters.put(ParameterType.WTSM_FORWARD_OHD, "WTRWEB");
		parameters.put(ParameterType.WTSM_CREATE_BDO, "WTRWEB");
		parameters.put(ParameterType.WTSM_ERASE_ACTION_FILE, "WTRWEB");
		parameters.put(ParameterType.WTSM_ACTION_FILE_SUMMARY, "WTRWEB");
		parameters.put(ParameterType.WTSM_ACTION_FILE_DETAILS, "WTRWEB");
		parameters.put(ParameterType.WTSM_SEND_FORWARD_MESSAGE, "WTRWEB");
		parameters.put(ParameterType.WTSM_PLACE_ACTION_FILE, "WTRWEB");
		
		p.setPermissions(permissions);
		p.setParameters(parameters);
		
		WorldTracerWebAccount a1 = new WorldTracerWebAccount();
		WorldTracerWebAccount a2 = new WorldTracerWebAccount();
		
		a1.setCaptchaRequired(false);
		a1.setCompanyCode("US");
		a1.setProfile(p);
		a1.setFrontend(true);
		a1.setTrainingMode(false);
		a1.setHost("wtrweb.worldtracer.aero");
		a1.setUsername("usdev01");
		a1.setPassword("Phoenix4!");
		
		a2.setCaptchaRequired(false);
		a2.setCompanyCode("US");
		a2.setProfile(p);
		a2.setFrontend(true);
		a2.setTrainingMode(false);
		a2.setHost("wtrweb.worldtracer.aero");
		a2.setUsername("usdev04");
		a2.setPassword("Phoenix4!");
		
		// Save Object
		Session sess = HibernateWrapper.getSession().openSession();
		Transaction t = null;
		try {
			t = sess.beginTransaction();
			sess.save(p);
			sess.save(u);
			sess.save(a1);
			sess.save(a2);
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
