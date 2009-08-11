package aero.nettracer.serviceprovider.ws_1_0.res.sabre.pool;

import java.util.HashMap;

import aero.nettracer.serviceprovider.common.db.User;

public class SabrePoolManager {

	private static SabrePoolManager instance = null;
	private HashMap<Integer, SabrePool> pools = new HashMap<Integer, SabrePool>();

	public static SabrePoolManager getInstance() {
		if (instance != null) {
			return instance;
		} else {
			instance = new SabrePoolManager();
			return instance;
		}
	}

	public SabrePool getPool(User user) {
		int profile = (int) user.getProfile().getId();
		Integer key = new Integer(profile);
		if (!pools.containsKey(key)) {
			instantiatePool(profile, key, pools);
		}
		return pools.get(key);
	}

	private synchronized void instantiatePool(int profile, Integer key,
			HashMap<Integer, SabrePool> pools) {
		// Verify the specific pool hasn't already been created.
		if (pools.containsKey(key)) {
			return;
		}
		SabrePool pool = new SabrePool(profile);
		pools.put(key, pool);
	}
}
