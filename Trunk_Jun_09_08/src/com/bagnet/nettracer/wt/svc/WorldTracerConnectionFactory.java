package com.bagnet.nettracer.wt.svc;

import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.db.wt.WorldTracerAccount;

public class WorldTracerConnectionFactory extends BasePoolableObjectFactory {

	private List<WorldTracerAccount> accounts = null;
	private String host;
	private volatile int itemsUsed = 0;
	private String mode;
	
	private static final int ALLOWED_MILLIS_WITH_NOACTIVITIY = 1080000;
	
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
		logger.debug("Activating Object...");
		WorldTracerConnection connection = (WorldTracerConnection) obj;
		
		GregorianCalendar cal = new GregorianCalendar();
		long diff = cal.getTimeInMillis() - connection.getLastUsed().getTimeInMillis();
		
		if (connection.isValidConnection() == false || diff > ALLOWED_MILLIS_WITH_NOACTIVITIY) {
			logger.debug("Logging in...");
			connection.login();
		}
	}
	
	@Override
	public void passivateObject(Object obj) throws Exception {		
		logger.debug("Passivating Object...");
	}
}
