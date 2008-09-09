package com.bagnet.nettracer.ws.core;

import java.util.GregorianCalendar;

import com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse;


public class WSCoreTestClient  {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//getOHDTest();
		//getOHDsForWTTest();
		
		insertOHDTest();
		
		//insertIncidentTest();
	}
	
	
	public static void getOHDTest() {
		try {
			NTCoreServiceStub stub = new NTCoreServiceStub(
					"http://localhost:8080/tracer/services/NTCoreService");

			GetOHDDocument ohd = GetOHDDocument.Factory.newInstance();
			GetOHDDocument.GetOHD ohd2 = ohd.addNewGetOHD();

			ohd2.setOhdId("ATLDA00000001");
			ohd.setGetOHD(ohd2);

			GetOHDResponseDocument resOHD = stub.getOHD(ohd);
			com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD so = resOHD
					.getGetOHDResponse().getReturn();
			System.out.println(resOHD);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

//	public static void getOHDsForWTTest() {
//		try {
//			NTCoreServiceStub stub = new NTCoreServiceStub(
//					"http://localhost:8080/tracer/services/NTCoreService");
//
//			GetOHDsForWTDocument ohd = GetOHDsForWTDocument.Factory.newInstance();
//			GetOHDsForWTDocument.GetOHDsForWT ohd2 = ohd.addNewGetOHDsForWT();
//
//			ohd2.setCompanycode("DA");
//			ohd.setGetOHDsForWT(ohd2);
//
//			GetOHDsForWTResponseDocument resOHD = stub.getOHDsForWT(ohd);
//			com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD[] so = resOHD
//					.getGetOHDsForWTResponse().getReturnArray();
//			System.out.println(resOHD);
//		} catch (Exception e) {
//			e.printStackTrace();
//
//		}
//	}
	
	
	public static void insertOHDTest() {
		try {
			NTCoreServiceStub stub = new NTCoreServiceStub(
					"http://localhost:8080/tracer/services/NTCoreService");
			
			AuthenticateDocument auth = AuthenticateDocument.Factory.newInstance();
			AuthenticateDocument.Authenticate auth2 = auth.addNewAuthenticate();
			auth2.setCompanycode("DA");
			auth2.setUsername("admin");
			auth2.setPassword("admin");
			AuthenticateResponseDocument resauth = stub.authenticate(auth);
			String session_id = resauth.getAuthenticateResponse().getReturn();
			
			System.out.println("Session ID Returned is: " + session_id);
			
			if (session_id != null) {
				if (session_id.substring(0, "success: ".length()).equals("SUCCESS: ")) {
					session_id = session_id.substring("success: ".length());
				} else {
					System.out.println(session_id);
					session_id = null;
				}
			}
			
			if (session_id != null) {
			
				InsertOHDDocument ohd = InsertOHDDocument.Factory.newInstance();
				InsertOHDDocument.InsertOHD ohd2 = ohd.addNewInsertOHD();
				WSOHD so = ohd2.addNewSi();
				
				ohd2.setSessionId(session_id);
				
				so.setColor("BK");
				so.setAgent("admin");
				so.setCompanycodeId("DA");
				so.setStatus("Open");
				so.setFoundAtStation("ATL");
				so.setHoldingStation("ATL");
				so.setFounddatetime(new GregorianCalendar());
				
				
				ohd2.setSi(so);
	
				InsertOHDResponseDocument resOHD = stub.insertOHD(ohd);
				WSOhdResponse result = resOHD.getInsertOHDResponse().getReturn();
				
				System.out.println("ID: " + result.getOhdId() + "   " + result.getErrorResponse());
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	
	public static void insertIncidentTest() {
		try {
			NTCoreServiceStub stub = new NTCoreServiceStub(
					"http://localhost:8080/tracer/services/NTCoreService");
			
			AuthenticateDocument auth = AuthenticateDocument.Factory.newInstance();
			AuthenticateDocument.Authenticate auth2 = auth.addNewAuthenticate();
			auth2.setCompanycode("DA");
			auth2.setUsername("admin");
			auth2.setPassword("admin");
			AuthenticateResponseDocument resauth = stub.authenticate(auth);
			String session_id = resauth.getAuthenticateResponse().getReturn();
			
			if (session_id != null) {
				if (session_id.substring(0, "success: ".length()).equals("SUCCESS: ")) {
					session_id = session_id.substring("success: ".length());
				} else {
					System.out.println(session_id);
					session_id = null;
				}
			}
			
			if (session_id != null) {
			
				InsertIncidentDocument inc = InsertIncidentDocument.Factory.newInstance();
				InsertIncidentDocument.InsertIncident inc2 = inc.addNewInsertIncident();
				WSIncident si = inc2.addNewSi();
				
				inc2.setSessionId(session_id);
				
				si.setAgent("admin");
				si.setCompanycodeID("DA");
				si.setStatus("Open");
				si.setStationcreated("ATL");
				si.setStationassigned("ATL");
				si.setCreateDate(new GregorianCalendar());
				//si.setCreatedate("2008-03-11");
				//si.setCreatetime("14:30:00");

				inc2.setSi(si);
	
				InsertIncidentResponseDocument resINC = stub.insertIncident(inc);
	
				String result = resINC.getInsertIncidentResponse().getReturn();
				
				System.out.println(resINC);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
