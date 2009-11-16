package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.connection;

import java.util.ArrayList;

public class AgeManagement {
	
	private int backendIndex = -1;
	private int frontendIndex = -1;

	
	private ArrayList<WorldTracerHttpClient> clientList = new ArrayList<WorldTracerHttpClient>();	

	public AgeManagement(ArrayList<WorldTracerHttpClient> clientList) {
		this.clientList = clientList;
	}
	
	
	public synchronized Integer getOldestConnection(boolean requireActiveConnection) throws NoActiveConnectionException{
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
		for (int i = 0; i < clientList.size() && !complete; ++i) {
			currentIndex += 1;
			if (currentIndex > clientList.size())
				currentIndex = 0;

			WorldTracerHttpClient client = clientList.get(currentIndex);

			if (oldestClient == null) {
				oldestTime = client.getLastUsed().getTimeInMillis();
				oldestClient = client;
				oldestIndex = currentIndex;
			}

			if (client.getLastUsed() == null || client.getLastUsed().getTimeInMillis() < oldestTime) {
				if (requireActiveConnection && client.isValidConnection()) {
  				oldestTime = client.getLastUsed().getTimeInMillis();
  				oldestClient = client;
  				oldestIndex = currentIndex;
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
