package com.bagnet.nettracer.wt.svc;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

import com.bagnet.nettracer.wt.bmo.WorldTracerAccountBMO;

public class WorldTracerConnectionPool extends GenericObjectPool {
	private static final Logger logger = Logger
			.getLogger(WorldTracerConnectionPool.class);

	public WorldTracerConnectionPool(String companyCode, WorldTracerAccountBMO wtaBmo) {
		super();
		this.setMaxActive(wtaBmo.getNumAccounts(companyCode));
	}

	@Override
	public Object borrowObject() throws Exception {
		logger.debug("Borrowing object..");
		return super.borrowObject();
	}

	@Override
	public void returnObject(Object obj) throws Exception {
		logger.debug("Returning object.." + obj);
		super.returnObject(obj);
	}

}
