package com.bagnet.nettracer.ws.passengerview;


public class PVTestClient  {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			NTPassengerViewServiceStub stub = new NTPassengerViewServiceStub(
					"http://localhost:8080/tracer/services/NTPassengerViewService");

			GetIncidentPVDocument pv = GetIncidentPVDocument.Factory.newInstance();
			GetIncidentPVDocument.GetIncidentPV pv2 = pv.addNewGetIncidentPV();

			pv2.setIncidentId("ATLDA00000001");
			pv2.setLastname("Doe");
			pv.setGetIncidentPV(pv2);

			GetIncidentPVResponseDocument resPV = stub.getIncidentPV(pv);
			com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident si = resPV
					.getGetIncidentPVResponse().getReturn();
			System.out.println(resPV);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
