package aero.nettracer.serviceprovider.common.db;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;


public class JetBluePopulation {
	public static final String TEST_USERNAME = "jetblue_test";
	public static final String TEST_PASSWORD = "password";
	public static final String TEST_PROFILE_NAME = "JetBlue Testing";

	public static final String PROD_USERNAME = "jetblue_prod";
	public static final String PROD_PASSWORD = "J3tBlu3!";
	public static final String PROD_PROFILE_NAME = "JetBlue Prod";
	
	public static final String MOCK_USERNAME = "jetblue_mock";
	public static final String MOCK_PASSWORD = "jetblue";
	public static final String MOCK_PROFILE_NAME = "JetBlue Prod";
	
	public static Profile prodProfile = null;
	public static Profile testProfile = null;
	public static Profile mockProfile = null;
	
	//@Test
	public void addWtConnections() {
		Session sess = HibernateWrapper.getSession().openSession();
		
		User user = (User) sess.load(User.class, 1);
		user.getProfile().getPermissions().put(PermissionType.WORLDTRACER, new Boolean(true));
		Map<ParameterType, String> parameters = user.getProfile().getParameters();
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
		
		Transaction t = null;
		try {
			t = sess.beginTransaction();
			sess.saveOrUpdate(user);
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sess.close();
	}
	
	//@Test
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
		parameters.put(ParameterType.RESERVATION_SYSTEM_TYPE, ReservationSystemType.SABRE.name());
		
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
	
	//@Test
	public void createProductionUser() {
		// Generate basic user object
		User u = new User();
		Profile p = new Profile();
		u.setPassword(PROD_PASSWORD);
		u.setUsername(PROD_USERNAME);
		u.setProfile(p);
		p.setName(PROD_PROFILE_NAME);
		HashMap<PermissionType, Boolean> permissions = new HashMap<PermissionType, Boolean>();
		HashMap<ParameterType, String> parameters = new HashMap<ParameterType, String>();
		
		permissions.put(PermissionType.WRITE_REMARK, new Boolean(true));
		permissions.put(PermissionType.GET_PREPOP_DATA, new Boolean(true));
		parameters.put(ParameterType.RESERVATION_SYSTEM_TYPE, ReservationSystemType.SABRE.name());
		
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
			prodProfile = p;
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
			throw new RuntimeException();
		} finally {
			sess.close();
		}
	}
	

	//@Test
	public void createTestConnectionData() {
		// Generate basic user object
		String[] usernames = {"250100", "250200", "250300", "250400"};
		String password = "ntt2009";
		
		for (int i=0; i<usernames.length; ++i) {
			SabreConnection conn = new SabreConnection();
			conn.setCompany("B6");
			conn.setDomain("B6");
			conn.setEndpoint("https://cert.webservices.sabre.com/cert");
			conn.setOrganization("B6");
			conn.setPassword(password);
			conn.setPseudoCityCode("B6");
			conn.setUsername(usernames[i]);
			conn.setProfile(testProfile);
						
			// Save Object
			Session sess = HibernateWrapper.getSession().openSession();
			Transaction t = null;
			try {
				t = sess.beginTransaction();
				sess.save(conn);
				t.commit();
			} catch (Exception e) {
				e.printStackTrace();
				t.rollback();
				throw new RuntimeException();
			} finally {
				sess.close();
			}
		}
	}
	
	//@Test
	public void createProdConnectionData() {
		// Generate basic user object
		String[] usernames = {"260100", "260200", "260300", "260400"};
		String password = "IDK2009";
		
		for (int i=0; i<usernames.length; ++i) {
			SabreConnection conn = new SabreConnection();
			conn.setCompany("B6");
			conn.setDomain("B6");
			conn.setEndpoint("https://webservices.sabre.com/websvc");
			conn.setOrganization("B6");
			conn.setPassword(password);
			conn.setPseudoCityCode("B6");
			conn.setUsername(usernames[i]);
			conn.setProfile(prodProfile);
			
			
			// Save Object
			Session sess = HibernateWrapper.getSession().openSession();
			Transaction t = null;
			try {
				t = sess.beginTransaction();
				sess.save(conn);
				t.commit();
			} catch (Exception e) {
				e.printStackTrace();
				t.rollback();
				throw new RuntimeException();
			} finally {
				sess.close();
			}
		}
	}

	
	//@Test
	public void createMockUser() {
		// Generate basic user object
		User u = new User();
		Profile p = new Profile();
		u.setPassword(MOCK_PASSWORD);
		u.setUsername(MOCK_USERNAME);
		u.setProfile(p);
		p.setName(MOCK_PROFILE_NAME);
		HashMap<PermissionType, Boolean> permissions = new HashMap<PermissionType, Boolean>();
		HashMap<ParameterType, String> parameters = new HashMap<ParameterType, String>();
		
		permissions.put(PermissionType.WRITE_REMARK, new Boolean(true));
		permissions.put(PermissionType.GET_PREPOP_DATA, new Boolean(true));
		parameters.put(ParameterType.RESERVATION_SYSTEM_TYPE, ReservationSystemType.SABRE.name());
		
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
			mockProfile = p;
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
			throw new RuntimeException();
		} finally {
			sess.close();
		}
	}
	

	//@Test
	public void createMockConnectionData() {
		// Generate basic user object
		String[] usernames = {"250100", "250200", "250300", "250400"};
		String password = "ntt2009";
		
		for (int i=0; i<usernames.length; ++i) {
			SabreConnection conn = new SabreConnection();
			conn.setCompany("B6");
			conn.setDomain("B6");
			conn.setEndpoint("http://dev01:8080");
			conn.setOrganization("B6");
			conn.setPassword(password);
			conn.setPseudoCityCode("B6");
			conn.setUsername(usernames[i]);
			conn.setProfile(mockProfile);
						
			// Save Object
			Session sess = HibernateWrapper.getSession().openSession();
			Transaction t = null;
			try {
				t = sess.beginTransaction();
				sess.save(conn);
				t.commit();
			} catch (Exception e) {
				e.printStackTrace();
				t.rollback();
				throw new RuntimeException();
			} finally {
				sess.close();
			}
		}
	}	
}
