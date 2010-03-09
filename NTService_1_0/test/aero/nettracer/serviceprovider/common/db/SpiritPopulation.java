package aero.nettracer.serviceprovider.common.db;

import java.util.HashMap;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;


public class SpiritPopulation {
	public static final String TEST_USERNAME = "spirit";
	public static final String TEST_PASSWORD = "ntSpiritPass2109";
	public static final String TEST_PROFILE_NAME = "Spirit Testing";

	

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
		
		permissions.put(PermissionType.WRITE_REMARK, new Boolean(true));
		permissions.put(PermissionType.GET_PREPOP_DATA, new Boolean(true));
		parameters.put(ParameterType.RESERVATION_SYSTEM_TYPE, ReservationSystemType.SPIRIT.name());
		parameters.put(ParameterType.RESERVATION_ENDPOINT, "https://206.57.4.54/NetTracerInternal/NetTracerService.svc");
		
		p.setPermissions(permissions);
		p.setParameters(parameters);
		
		// Save Object
		Session sess = HibernateWrapper.getSession().openSession();
		Transaction t = null;
		try {
			t = sess.beginTransaction();
			sess.save(p);
			sess.save(u);
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
