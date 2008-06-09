package com.bagnet.nettracer.ws.passengerview;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PVTestServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		
		// test address
		// http://localhost:8080/tracer/passengerviewtest?incident_id=ATLDA00000001&lastname=Doe

		String incident_id = req.getParameter("incident_id");
		String lastname = req.getParameter("lastname");
		

		NTPassengerViewServiceStub stub = new NTPassengerViewServiceStub(
				"http://" + req.getServerName() + "/tracer/services/NTPassengerViewService");
		
		PrintWriter out = res.getWriter();
		
		try {
			GetIncidentPVDocument pv = GetIncidentPVDocument.Factory.newInstance();
			GetIncidentPVDocument.GetIncidentPV pv2 = pv.addNewGetIncidentPV();
	
			pv2.setIncidentId(incident_id);
			pv2.setLastname(lastname);
			pv.setGetIncidentPV(pv2);
	
			GetIncidentPVResponseDocument resPV = stub.getIncidentPV(pv);
			//com.bagnet.nettracer.ws.passengerview.xsd.SimpleIncident si = resPV
			//		.getGetIncidentPVResponse().getReturn();
			res.setContentType("text/xml");
		  
		  out.println(resPV);
		} catch (Exception e) {
			out.println("Webservice error: " + e.toString());
		}
		
	}


}
