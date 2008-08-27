package com.bagnet.nettracer.integrations.delivery;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class Rynns implements BDOIntegration {

	public DeliveryIntegrationResponse integrate(BDO bdo, Agent agent) {
		DeliveryIntegrationResponse response = new DeliveryIntegrationResponse();
		String responseText = TracerUtils.getResourcePropertyText("delivercompany.integration.rynns.success", agent);
		response.setResponse(responseText);
		response.setUniqueIntegrationId("RYNNS11111");
		response.setSuccess(true);
		return response;
	}

}
