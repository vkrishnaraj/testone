package com.bagnet.nettracer.integrations.delivery;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BDO;

public interface BDOIntegration {
	public DeliveryIntegrationResponse integrate(BDO bdo, Agent agent);
}
