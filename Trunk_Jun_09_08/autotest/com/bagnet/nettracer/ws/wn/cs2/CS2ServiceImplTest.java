package com.bagnet.nettracer.ws.wn.cs2;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.bagnet.nettracer.ws.wn.cs2.CS2ServiceImpl;
import com.bagnet.nettracer.ws.wn.cs2.RetrieveIncidentDocument;
import com.bagnet.nettracer.ws.wn.cs2.RetrieveIncidentDocument.RetrieveIncident;
import com.bagnet.nettracer.ws.wn.cs2.RetrieveIncidentResponseDocument;
import com.bagnet.nettracer.ws.wn.cs2.pojo.xsd.RetrieveIncidentResponse;
import com.bagnet.nettracer.ws.wn.pojo.xsd.Authentication;

public class CS2ServiceImplTest {
	
	@Test
	public void testAuthenticationFailure() {
		RetrieveIncidentDocument doc = RetrieveIncidentDocument.Factory.newInstance();
		RetrieveIncident doc2 = doc.addNewRetrieveIncident();
		Authentication auth = doc2.addNewAuthentication();
		auth.setAirlineCode("WN");
		auth.setSystemName("ntauto");
		auth.setSystemPassword("wrongPass");
		doc2.setReportNumber("ATLWN00000001");
		CS2ServiceImpl impl = new CS2ServiceImpl();
		RetrieveIncidentResponseDocument res = impl.retrieveIncident(doc);
		RetrieveIncidentResponse res2 = res.getRetrieveIncidentResponse().getReturn();
		assertTrue(res2.getReturnCode() == 1);
		assertTrue("Authentication Failed".equals(res2.getMessage()));	
	}
	
	@Test
	public void testAuthenticationSuccess() {
		RetrieveIncidentDocument doc = RetrieveIncidentDocument.Factory.newInstance();
		RetrieveIncident doc2 = doc.addNewRetrieveIncident();
		Authentication auth = doc2.addNewAuthentication();
		auth.setAirlineCode("WN");
		auth.setSystemName("ntauto");
		auth.setSystemPassword("IpoL!Jan7");
		doc2.setReportNumber("ATLWN00000001");
		CS2ServiceImpl impl = new CS2ServiceImpl();
		RetrieveIncidentResponseDocument res = impl.retrieveIncident(doc);
		RetrieveIncidentResponse res2 = res.getRetrieveIncidentResponse().getReturn();
		assertTrue(!"Authentication Failed".equals(res2.getMessage()));	
	}
}