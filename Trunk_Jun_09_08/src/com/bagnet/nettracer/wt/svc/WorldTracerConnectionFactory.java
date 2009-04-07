package com.bagnet.nettracer.wt.svc;

import java.util.List;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.db.wt.WorldTracerAccount;

public class WorldTracerConnectionFactory extends BasePoolableObjectFactory {

	private List<WorldTracerAccount> accounts = null;
	private String host;
	private volatile int itemsUsed = 0;
	private String mode;
	
	private static final Logger logger = Logger.getLogger(WorldTracerConnectionFactory.class);
	
	private synchronized WorldTracerAccount getNextItem() {
		++itemsUsed;
		if (itemsUsed > accounts.size()) {
			throw new RuntimeException("Cannot create more items");
		}
		return accounts.get(itemsUsed - 1);
	}	
	
	public WorldTracerConnectionFactory(List<WorldTracerAccount> accounts, String mode, String host) {
		this.accounts = accounts;
		this.mode = mode;
		this.host = host;
	}


	@Override
	public synchronized Object makeObject() throws Exception {
		WorldTracerAccount wta = getNextItem();
		WorldTracerConnection connection = new WorldTracerConnection(wta, host, mode);
		connection.login();
		return connection;
	}

	@Override
	public void activateObject(Object obj) throws Exception {
		logger.info("Activating Object...");
		((WorldTracerConnection) obj).login();
	}
	
	@Override
	public void passivateObject(Object obj) throws Exception {		
		logger.info("Passivating Object...");
		((WorldTracerConnection) obj).logout();
	}
}
