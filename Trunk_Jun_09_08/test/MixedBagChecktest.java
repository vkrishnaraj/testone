

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import aero.nettracer.general.services.GeneralServiceBean;
import aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument;
import aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument;
import aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument.GetReservationDataResponse;
import aero.nettracer.serviceprovider.ws_1_0.ReservationService_1_0Stub;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Address;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse;

import com.bagnet.clients.defaul.NTIntegrationWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

public class MixedBagChecktest {
	
	HttpServletRequest request;
	HttpSession session;

	GeneralServiceBean bean = new GeneralServiceBean();
	Agent auto = bean.getAgent("ntadmin", TracerProperties.get("wt.company.code"));

	IncidentForm form;
	
	public MixedBagChecktest() {
		try {
			//Create mock request
			request=mock(HttpServletRequest.class);		
			session=mock(HttpSession.class);
			when(request.getSession()).thenReturn(session);
			when(session.getAttribute("user")).thenReturn(auto);
			when(session.getAttribute("incidentForm")).thenReturn(form);
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void testReservation() {

		TestNTReservationIntegrationImpl impl=new TestNTReservationIntegrationImpl();
		form=new IncidentForm();
		form.setRecordlocator("BBBBBB");
		impl.populateIncidentForm(request, form, 1);
		assertEquals(form.getCheckedlocation(),TracingConstants.BAG_CHECK_MIXED);
		
	}

	@Test
	public void testReservation2() {

		TestNTReservationIntegrationImpl impl=new TestNTReservationIntegrationImpl();
		form=new IncidentForm();
		form.setRecordlocator("AAAAAA");
		impl.populateIncidentForm(request, form, 1);
		assertEquals(form.getCheckedlocation(),null);
		
	}

	@Test
	public void testReservation3() {

		TestNTReservationIntegrationImpl impl=new TestNTReservationIntegrationImpl();
		form=new IncidentForm();
		form.setRecordlocator("CCCCCC");
		impl.populateIncidentForm(request, form, 1);
		assertEquals(form.getCheckedlocation(),null);
		
	}
	
	
}