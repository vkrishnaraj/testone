package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.connection;

import java.util.HashMap;

import aero.nettracer.serviceprovider.common.db.Profile;
import aero.nettracer.serviceprovider.common.db.User;

public class ConnectionPoolManager {

	private static ConnectionPoolManager instance = null;
	private HashMap<Integer, WorldTracerClientPool> pools = new HashMap<Integer, WorldTracerClientPool>();

	public static ConnectionPoolManager getInstance() {

		if (instance == null) {
			instance = new ConnectionPoolManager();
		}
		return instance;

	}

	public WorldTracerClientPool getPool(User user) {
		Profile profile = user.getProfile();
		Integer key = new Integer((int) profile.getId());
		if (!pools.containsKey(key)) {
			instantiatePool(profile, key, pools);
		}
		return pools.get(key);
	}

	private synchronized void instantiatePool(Profile profile, Integer key,
			HashMap<Integer, WorldTracerClientPool> pools) {
		// Verify the specific pool hasn't already been created.
		if (pools.containsKey(key)) {
			return;
		}
		WorldTracerClientPool pool = new WorldTracerClientPool(profile);
		pools.put(key, pool);
	}
}
