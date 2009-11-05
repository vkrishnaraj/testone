package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.connection;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;
import org.apache.log4j.Logger;

import aero.nettracer.serviceprovider.common.db.WorldTracerWebAccount;



public class WorldTracerClientFactory extends BaseKeyedPoolableObjectFactory{
	private static final Logger logger = Logger.getLogger(WorldTracerClientFactory.class);
	private static final int ALLOWED_MILLIS_WITH_NOACTIVITIY = 1200000;
	private List<WorldTracerWebAccount> accounts = null;
	private int itemsUsed = 0;
	HashMap<Integer, WorldTracerHttpClient> clientList = new HashMap<Integer, WorldTracerHttpClient>();
	
	public WorldTracerClientFactory(List<WorldTracerWebAccount> accounts) {
		this.accounts = accounts;
	}

	private synchronized WorldTracerWebAccount getNextItem() {
		++itemsUsed;
		if (itemsUsed > accounts.size()) {
			throw new RuntimeException("Cannot create more items");
		}
		return accounts.get(itemsUsed - 1);
	}	


	@Override
	public synchronized Object makeObject(Object key) throws Exception {
		WorldTracerWebAccount wta = getNextItem();
		WorldTracerHttpClient connection = new WorldTracerHttpClient(wta, (Integer) key);
		logger.info("Key: " + key);
		clientList.put((Integer) key, connection);
		return connection;
	}

	@Override
	public void activateObject(Object key, Object obj) throws Exception {
		WorldTracerHttpClient connection = (WorldTracerHttpClient) obj;
		
		GregorianCalendar cal = new GregorianCalendar();
		long diff = cal.getTimeInMillis() - connection.getLastUsed().getTimeInMillis();
		
		if (diff > ALLOWED_MILLIS_WITH_NOACTIVITIY) {
			connection.setValidConnection(false);
		}
	}

	public HashMap<Integer, WorldTracerHttpClient> getClientList() {
		return clientList;
	}

	public void setClientList(HashMap<Integer, WorldTracerHttpClient> clientList) {
		this.clientList = clientList;
	}
	
}
