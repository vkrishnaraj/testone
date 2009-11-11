package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.connection;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;
import org.apache.log4j.Logger;

import aero.nettracer.serviceprovider.common.db.WorldTracerWebAccount;



public class WorldTracerClientFactory extends BaseKeyedPoolableObjectFactory{
	private static final Logger logger = Logger.getLogger(WorldTracerClientFactory.class);
	public static final int ALLOWED_MILLIS_WITH_NOACTIVITIY = 20*60*1000; // 20 Minutes = 1200000
	public static final int KEEP_ALIVE_TIME = ALLOWED_MILLIS_WITH_NOACTIVITIY - 3*60*1000; 
	private List<WorldTracerWebAccount> accounts = null;
	private int itemsUsed = 0;
	private HashMap<Integer, WorldTracerHttpClient> clientList = new HashMap<Integer, WorldTracerHttpClient>();
	private WorldTracerHttpClient keepAliveConnection = null;
	
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
	
	@Override
	public boolean validateObject(Object key, Object obj) {
		WorldTracerHttpClient connection = (WorldTracerHttpClient) obj;
		
		if (connection.isValidConnection()) {
  		GregorianCalendar cal = new GregorianCalendar();
  		
  		long diff = cal.getTimeInMillis() - connection.getLastUsed().getTimeInMillis();

  		// UPDATE NEWEST CONNECTION (ONE TO KEEP ALIVE)
  		synchronized (keepAliveConnection) {
  			
	  		for (WorldTracerHttpClient client: clientList.values()) {
	  			if (client.isValidConnection() == true) {
	  				if (keepAliveConnection == null) {
	  					keepAliveConnection = client;
	  				}
	  				
	  				if (keepAliveConnection.getLastUsed().getTime().getTime() < client.getLastUsed().getTime().getTime()) {
	  					keepAliveConnection = client;
	  				}
	  			}
	  		}
  		}
  		
  		// IF OLDEST CONNECTION IS BETWEEN KEEP_ALIVE_LIMIT AND SESSION TIMEOUT, KEEP IT ALIVE
  		synchronized (keepAliveConnection) {
	  		if (keepAliveConnection.getKey().equals((Integer)key) && diff > KEEP_ALIVE_TIME) {
	  			connection.keepAlive();
	  		}
  		}
  		
  		if (diff > ALLOWED_MILLIS_WITH_NOACTIVITIY) {
  			connection.setValidConnection(false);
  		}
		}
		return true;
	}
	
}
