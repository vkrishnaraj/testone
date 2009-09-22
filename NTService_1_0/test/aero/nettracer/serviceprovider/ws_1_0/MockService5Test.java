package aero.nettracer.serviceprovider.ws_1_0;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;
import aero.nettracer.serviceprovider.ws_1_0.res.sabre.Reservation;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse;

/**
 * 
 * @author Byron Class performs unit tests against "MockService5" SoapUI Mock
 *         Service 5
 */
public class MockService5Test {

	@Test
	public void testReservationResponseData() throws Exception {
		int NUM_BAGS = 0;
		int BAG_ITIN = 0;
		int PAX_ITIN = 0;
		int NUM_PAX = 1;
		
		Session sess = HibernateWrapper.getSession().openSession();
		User user = (User) sess.load(User.class, 3);
		Reservation res = new Reservation();
		ReservationResponse response = res.getReservationData(user, "DUNLAF", null);
		Assert.assertTrue(response.getReservation().getBagItineraryArray().length == BAG_ITIN);
		Assert.assertTrue(response.getReservation().getPassengerItineraryArray().length == PAX_ITIN);
		Assert.assertTrue(response.getReservation().getPaxAffected() == NUM_PAX);
		Assert.assertTrue(response.getReservation().getPassengersArray().length == NUM_PAX);
		Assert.assertTrue(response.getReservation().getClaimChecksArray().length == NUM_BAGS);
	}
}
