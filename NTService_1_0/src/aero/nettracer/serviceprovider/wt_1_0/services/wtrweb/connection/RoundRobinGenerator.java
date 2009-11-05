package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.connection;

import java.util.HashMap;

public class RoundRobinGenerator {
	private int poolSize;
	private int current = 0; 
	private int cronKey = 0;
	
	public RoundRobinGenerator() {
	}
	
	public RoundRobinGenerator(int poolSize) {
		this.poolSize = poolSize;
	}

	public synchronized Integer getLeastConnnections() {
		current += 1;
		if (current > poolSize) current = 1;
		return current;	
	}

	public boolean validateKey(Object obj) {
		Integer key = (Integer) obj;
		if (key.intValue() > 0 && key.intValue() <= poolSize) {
			return true;
		}
		return false;
	}

	public Integer getNextLoggedInKeyForCron(
			HashMap<Integer, WorldTracerHttpClient> clientList) {

		int newCronKey = cronKey;
		
		for (int i = 0; i < poolSize-1; ++ i) {
			newCronKey += 1;
			if (newCronKey > poolSize) newCronKey = 1;
			WorldTracerHttpClient client = clientList.get(new Integer(newCronKey));
			if (client == null) {
				return null;
			}
			if (client.isValidConnection() && !client.isWaitingOnCaptcha()) {
				cronKey = newCronKey;
				return new Integer(newCronKey);
			}
		}

		return null;
	}
}
