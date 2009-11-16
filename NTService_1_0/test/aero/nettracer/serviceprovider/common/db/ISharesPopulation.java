package aero.nettracer.serviceprovider.common.db;

import java.util.HashMap;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;


public class ISharesPopulation {
	public static final String TEST_USERNAME = "ishares_local";
	public static final String TEST_PASSWORD = "password";
	public static final String TEST_PROFILE_NAME = "Ishares Testing";

	
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

		parameters.put(ParameterType.WTSM_ACTION_FILE_COUNTS, "ISHARES");
		parameters.put(ParameterType.WTSM_GET_AHL, "ISHARES");
		parameters.put(ParameterType.WTSM_CREATE_AHL, "ISHARES");
		parameters.put(ParameterType.WTSM_AMEND_AHL, "ISHARES");
		parameters.put(ParameterType.WTSM_CLOSE_AHL, "ISHARES");
		parameters.put(ParameterType.WTSM_SUSPEND_AHL, "ISHARES");
		parameters.put(ParameterType.WTSM_REINSTATE_AHL, "ISHARES");
		parameters.put(ParameterType.WTSM_GET_OHD, "ISHARES");
		parameters.put(ParameterType.WTSM_CREATE_OHD, "ISHARES");
		parameters.put(ParameterType.WTSM_AMEND_OHD, "ISHARES");
		parameters.put(ParameterType.WTSM_CLOSE_OHD, "ISHARES");
		parameters.put(ParameterType.WTSM_FORWARD_OHD, "ISHARES");
		parameters.put(ParameterType.WTSM_CREATE_BDO, "ISHARES");
		parameters.put(ParameterType.WTSM_ERASE_ACTION_FILE, "ISHARES");
		parameters.put(ParameterType.WTSM_ACTION_FILE_SUMMARY, "ISHARES");
		parameters.put(ParameterType.WTSM_ACTION_FILE_DETAILS, "ISHARES");
		parameters.put(ParameterType.WTSM_SEND_FORWARD_MESSAGE, "ISHARES");
		parameters.put(ParameterType.WTSM_PLACE_ACTION_FILE, "ISHARES");
		
		p.setPermissions(permissions);
		p.setParameters(parameters);
		
		WorldTracerISharesAccount a1 = new WorldTracerISharesAccount();
		WorldTracerISharesAccount a2 = new WorldTracerISharesAccount();
		WorldTracerISharesAccount a3 = new WorldTracerISharesAccount();
		WorldTracerISharesAccount a4 = new WorldTracerISharesAccount();
		

		String host = "ishares.lcc.usairways.com";
		
		a1.setCompanyCode("US");
		a1.setProfile(p);
		a1.setHost(host);
		a1.setUsername("EQUIPAJE1");
		a1.setPassword("BOLSA1");
		a1.setSine("BSIW00211W/GS/HPL");
		
		a2.setCompanyCode("US");
		a2.setProfile(p);
		a2.setHost(host);
		a2.setUsername("EQUIPAJE2");
		a2.setPassword("BOLSA2");
		a2.setSine("BSIW00222W/GS/HPL");
		
		a3.setCompanyCode("US");
		a3.setProfile(p);
		a3.setHost(host);
		a3.setUsername("EQUIPAJE3");
		a3.setPassword("BOLSA3");
		a3.setSine("BSIW00233W/GS/HPL");
		
		a4.setCompanyCode("US");
		a4.setProfile(p);
		a4.setHost(host);
		a4.setUsername("EQUIPAJE4");
		a4.setPassword("BOLSA4");
		a4.setSine("BSIW00244W/GS/HPL");
		
		// Save Object
		Session sess = HibernateWrapper.getSession().openSession();
		Transaction t = null;
		try {
			t = sess.beginTransaction();
			sess.save(p);
			sess.save(u);
			sess.save(a1);
			sess.save(a2);
			sess.save(a3);
			sess.save(a4);
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
