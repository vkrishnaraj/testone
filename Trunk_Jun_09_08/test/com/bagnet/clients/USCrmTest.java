package com.bagnet.clients;

import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.client.Options;
import org.apache.axis2.transport.http.HttpTransportProperties;
import org.apache.axis2.transport.http.HttpTransportProperties.Authenticator;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.bagnet.clients.us.ClientEventHandlerImpl;
import com.usairways.crm.webservices.CreateCRMFileDocument;
import com.usairways.crm.webservices.CreateCRMFileResponseDocument;
import com.usairways.crm.webservices.NetTracerStub;

public class USCrmTest {

	Logger logger = Logger.getLogger(USCrmTest.class);

	@Test
	public void crmTest() {
		
		ClientEventHandlerImpl h = new ClientEventHandlerImpl();
//		h.sendCrm(i);
//		try {
//			NetTracerStub stub = new NetTracerStub("http://crmtest.lcc.usairways.com/nettracer/nettracer.asmx");
//			
//			Options x = stub._getServiceClient().getOptions();
//			
//			CreateCRMFileDocument d = CreateCRMFileDocument.Factory.newInstance();
//			d.addNewCreateCRMFile();
//			
//			HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
//			
//			auth.setHost("crmtest.lcc.usairways.com");
//			auth.setUsername("svc-ntracer");
//			auth.setPassword("E74Liq!vTCxT");
//			auth.setDomain("lcc");
//			auth.setPreemptiveAuthentication(false);
//			
//			x.setProperty(org.apache.axis2.transport.http.HTTPConstants.AUTHENTICATE, auth);
//
//			logger.info(d);
//			
//			CreateCRMFileResponseDocument r = stub.create_CRMFile(d);
//			 
//
//			logger.info(r);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}
}
