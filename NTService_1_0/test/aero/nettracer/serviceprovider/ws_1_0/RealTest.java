package aero.nettracer.serviceprovider.ws_1_0;

import org.hibernate.Session;
import org.junit.Test;

import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;
import aero.nettracer.serviceprovider.ws_1_0.res.sabre.Reservation;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse;

public class RealTest {
	@Test
	public void testReservationResponseData() throws Exception {

		Session sess = HibernateWrapper.getSession().openSession();
		User user = (User) sess.load(User.class, 1);
		Reservation res = new Reservation();
		ReservationResponse response = res.getReservationData(user, "PDOMZX", null);
		int i = 0;
	}
}
