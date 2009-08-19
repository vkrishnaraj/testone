package com.bagnet.nettracer.wt.svc;

import java.util.List;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.wt.WorldTracerAccount;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.wt.bmo.WorldTracerAccountBMO;
import com.bagnet.nettracer.wt.connector.WorldTracerConnectionException;

public class WorldTracerConnectionPool extends GenericObjectPool {
	private static final Logger logger = Logger
			.getLogger(WorldTracerConnectionPool.class);

	private String host = null;

	public WorldTracerConnectionPool(WorldTracerAccountBMO wtaBmo,
			String companyCode, String mode) {
		super();
		
		Company_Specific_Variable comsv = AdminUtils.getCompVariable(companyCode);
		if (comsv.getWt_enabled() == 0) {
			return;
			//throw new WorldTracerConnectionException("worldtracer not enabled");
		}

		this.host = comsv.getWt_url();
		if (host == null || host.trim().length() == 0) {
			host = TracingConstants.NEW_DEFAULT_WT_URL;
		}

		List<WorldTracerAccount> accounts = wtaBmo.getAccountNames(companyCode);

		WorldTracerConnectionFactory factory = new WorldTracerConnectionFactory(
				accounts, mode, host);

		this.setLifo(true);
		this.setMaxActive(accounts.size());
		this.setMaxWait(1000 * 15);
		this.setWhenExhaustedAction(WHEN_EXHAUSTED_BLOCK);
		this.setFactory(factory);
	}

	public Object borrowObject() throws Exception {
		logger.debug("Borrowing object..");
		return super.borrowObject();
	}

	public void returnObject(Object obj) throws Exception {
		logger.debug("Returning object.." + obj);
		super.returnObject(obj);
	}

}
