package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.connection;

import java.util.ArrayList;

public class AgeManagement {
	
	private int backendIndex = -1;
	private int frontendIndex = -1;
	private WorldTracerClientFactory factory = null;

	public AgeManagement(WorldTracerClientFactory factory) {
		this.factory = factory;
	}
	
	public synchronized Integer getOldestConnection(boolean requireActiveConnection) throws NoActiveConnectionException{
		if (requireActiveConnection && factory.getClientList().size() == 0) {
			throw new NoActiveConnectionException();
		} else if (factory.getClientList().size() == 0) {
			return new Integer(1);
		}
		
		int currentIndex = 0;
		if (!requireActiveConnection) {
			currentIndex = frontendIndex;
		}
		else {
			currentIndex = backendIndex;
		}
		
		
		int oldestIndex = 0;
		WorldTracerHttpClient oldestClient = null;
		long oldestTime = 0;
		
		boolean complete = false;
		for (int i = 0; i < factory.getClientListArray().size() && !complete; ++i) {
			currentIndex += 1;
			if (currentIndex >= factory.getClientListArray().size())
				currentIndex = 0;

			WorldTracerHttpClient client = factory.getClientListArray().get(currentIndex);

			if (oldestClient == null) {
				oldestTime = client.getLastUsed().getTimeInMillis();
				oldestClient = client;
				oldestIndex = currentIndex;
			}

			if (client.getLastUsed() == null || client.getLastUsed().getTimeInMillis() < oldestTime || (requireActiveConnection && client.isValidConnection())) {
				if (requireActiveConnection) {
					if (client.isValidConnection()) {
		  				oldestTime = client.getLastUsed().getTimeInMillis();
		  				oldestClient = client;
		  				oldestIndex = currentIndex;
					}
				} else {
					oldestTime = client.getLastUsed().getTimeInMillis();
					oldestClient = client;
					oldestIndex = currentIndex;

					if (client.getLastUsed() == null) {
						complete = true;
					}
				}
			}
		}
		
		if (!requireActiveConnection) {
			frontendIndex = oldestIndex;
		}
		else {
			backendIndex = oldestIndex;
		}
		
		if (requireActiveConnection && (!oldestClient.isValidConnection() || oldestClient.isWaitingOnCaptcha())) {
			throw new NoActiveConnectionException();
		}
		return oldestClient.getKey();	
	}
}
