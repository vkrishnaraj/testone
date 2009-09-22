package aero.nettracer.serviceprovider.ws_1_0;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

import aero.nettracer.serviceprovider.common.db.SabreConnection;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;
import aero.nettracer.serviceprovider.ws_1_0.res.sabre.Reservation;
import aero.nettracer.serviceprovider.ws_1_0.res.sabre.pool.SabrePool;
import aero.nettracer.serviceprovider.ws_1_0.res.sabre.pool.SabrePoolManager;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse;

/**
 * 
 * @author Byron Class performs unit tests against "MockService1" SoapUI Mock
 *         Service 1
 */
public class MockService1Test {

	/**
	 * 
	 * Borrows an object from the pool and returns it to the pool.
	 * 
	 * Actions Performed: 1. SessionCreateRQ 2. Ignore 3. SessionCloseRQ
	 * 
	 */
	@Test
	public void testPoolConnection() throws Exception {
		Logger logger = Logger.getLogger(Reservation.class);

		Session sess = HibernateWrapper.getSession().openSession();
		User user = (User) sess.load(User.class, 3);

		SabrePool pool = SabrePoolManager.getInstance().getPool(user);
		SabreConnection connParams = (SabreConnection) pool.borrowObject();

		try {

		} catch (Exception e) {
			logger.error("Unexpected Error Occurred", e);
			throw e;
		} finally {
			pool.returnObject(connParams);
		}
	}

	/**
	 * 
	 * Borrows an object from the pool, gets PNR data, and returns it to the
	 * pool.
	 * 
	 * Please note, the response is not properly formatted (missing namespace).
	 * 
	 * Actions Performed: 1. SessionCreateRQ 2. Ignore 3. Validate 4.
	 * SessionCloseRQ
	 * 
	 */
	@Test
	public void testValidate() throws Exception {

		Session sess = HibernateWrapper.getSession().openSession();
		User user = (User) sess.load(User.class, 3);

		SabrePool pool = SabrePoolManager.getInstance().getPool(user);
		SabreConnection connParams = (SabreConnection) pool.borrowObject();

		Reservation.validateSession(connParams);

		pool.returnObject(connParams);

	}

	/**
	 * 
	 * Borrows an object from the pool, returns it, then borrows another. This
	 * test then asserts the IDs are identical to verify LIFO functionality.
	 * 
	 */
	@Test
	public void testLifoCheckout() throws Exception {
		Logger logger = Logger.getLogger(Reservation.class);

		Session sess = HibernateWrapper.getSession().openSession();
		User user = (User) sess.load(User.class, 3);

		SabrePool pool = SabrePoolManager.getInstance().getPool(user);
		SabreConnection connParams = (SabreConnection) pool.borrowObject();
		int id1 = connParams.getId();
		pool.returnObject(connParams);

		pool = SabrePoolManager.getInstance().getPool(user);
		connParams = (SabreConnection) pool.borrowObject();
		int id2 = connParams.getId();
		pool.returnObject(connParams);

		logger.info("Id1: " + id1);
		logger.info("Id2: " + id2);

		Assert.assertTrue(id1 == id2);
	}

	/**
	 * 
	 * Borrows an object from the pool, returns it, then borrows another. This
	 * test then asserts the IDs are identical to verify LIFO functionality.
	 * 
	 */
	@Test
	public void testSimultaneousCheckouts() throws Exception {
		Logger logger = Logger.getLogger(Reservation.class);

		Session sess = HibernateWrapper.getSession().openSession();
		User user = (User) sess.load(User.class, 3);

		SabrePool pool = SabrePoolManager.getInstance().getPool(user);
		SabreConnection connParams = (SabreConnection) pool.borrowObject();
		int id1 = connParams.getId();

		SabrePool pool2 = SabrePoolManager.getInstance().getPool(user);
		SabreConnection connParams2 = (SabreConnection) pool2.borrowObject();
		int id2 = connParams2.getId();
		pool2.returnObject(connParams);
		pool.returnObject(connParams);

		logger.info("Id1: " + id1);
		logger.info("Id2: " + id2);

		Assert.assertTrue(id1 != id2);
	}

	/**
	 * 
	 * 
	 */
	@Test
	public void testReservationResponseData() throws Exception {

		Session sess = HibernateWrapper.getSession().openSession();
		User user = (User) sess.load(User.class, 3);
		Reservation res = new Reservation();
		ReservationResponse response = res.getReservationData(user, "DUNLAF", null);
		int i = 0;
	}
	
	/**
	 * 
	 * 
	 */
	@Test
	public void testWriteRemark() throws Exception {

		Session sess = HibernateWrapper.getSession().openSession();
		User user = (User) sess.load(User.class, 3);
		Reservation res = new Reservation();
		RemarkResponse response = res.writeRemark(user, "DUNLAF", "Test Remark");

	}
	
	
}
