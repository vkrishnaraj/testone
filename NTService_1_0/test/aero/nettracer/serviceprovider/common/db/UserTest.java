package aero.nettracer.serviceprovider.common.db;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;


public class UserTest {
	public static final String USERNAME = "jetblue";
	public static final String PASSWORD = "password";
	public static final String PROFILE_NAME = "JetBlue Prod";
	
	@Test
	public void test1() {
		// Generate basic user object
		User u = new User();
		Profile p = new Profile();
		u.setPassword(PASSWORD);
		u.setUsername(USERNAME);
		u.setProfile(p);
		p.setName(PROFILE_NAME);
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
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
			throw new RuntimeException();
		} finally {
			sess.close();
		}
	}
	
	@Test
	public void test2() {
		Session sess = HibernateWrapper.getSession().openSession();
		User u = (User) sess.load(User.class, 1);
		assert(u.getUsername().equals(USERNAME));
		assert(u.getPassword().equals(PASSWORD));
		assert(u.getProfile().getName().equals(PROFILE_NAME));
	
		Map<ParameterType, String> params = u.getProfile().getParameters();
		Map<PermissionType, Boolean> perms = u.getProfile().getPermissions();

		System.out.println("Permissions: ");
		for (PermissionType pt: perms.keySet()) {
			System.out.println(" " + pt.name() + ": " + perms.get(pt));
		}
		
		System.out.println("Parameters: ");
		for (ParameterType pt: params.keySet()) {
			System.out.println(" " + pt.name() + ": " + params.get(pt));
		}
		sess.close();
	}
	
	@Test
	public void test3() {
		Session sess = HibernateWrapper.getSession().openSession();
		User u = (User) sess.load(User.class, 1);
		Profile p = u.getProfile();
		
		Map<ParameterType, String> parameters = p.getParameters();
		parameters.put(ParameterType.TEST, "MyValue");
		p.setParameters(parameters);
		
		Transaction t = null;
		try {
			t = sess.beginTransaction();
			sess.save(p);
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
			throw new RuntimeException();
		} finally {
			sess.close();
		}
		assert(p.getParameters().size() == 2);
	}
	
	@Test
	public void test4() {
		Session sess = HibernateWrapper.getSession().openSession();
		User u = (User) sess.load(User.class, 1);
		Profile p = u.getProfile();
		
		HashMap<ParameterType, String> parameters = new HashMap<ParameterType, String>();
		p.setParameters(parameters);
		
		Transaction t = null;
		try {
			t = sess.beginTransaction();
			sess.save(p);
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
			throw new RuntimeException();
		} finally {
			sess.close();
		}
		assert(p.getParameters().size() == 0);
	}
}
