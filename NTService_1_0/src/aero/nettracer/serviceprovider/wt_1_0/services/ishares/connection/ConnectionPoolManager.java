package aero.nettracer.serviceprovider.wt_1_0.services.ishares.connection;

import java.util.HashMap;

import aero.nettracer.serviceprovider.common.db.Profile;
import aero.nettracer.serviceprovider.common.db.User;

public class ConnectionPoolManager {

	private static ConnectionPoolManager instance = null;
	private HashMap<Integer, ISharesClientPool> pools = new HashMap<Integer, ISharesClientPool>();

	public static ConnectionPoolManager getInstance() {

		if (instance == null) {
			instance = new ConnectionPoolManager();
		}
		return instance;

	}

	public ISharesClientPool getPool(User user) {
		Profile profile = user.getProfile();
		Integer key = new Integer((int) profile.getId());
		if (!pools.containsKey(key)) {
			instantiatePool(profile, key, pools);
		}
		return pools.get(key);
	}

	private synchronized void instantiatePool(Profile profile, Integer key,
			HashMap<Integer, ISharesClientPool> pools) {
		// Verify the specific pool hasn't already been created.
		if (pools.containsKey(key)) {
			return;
		}
		ISharesClientPool pool = new ISharesClientPool(profile);
		pools.put(key, pool);
	}
}
