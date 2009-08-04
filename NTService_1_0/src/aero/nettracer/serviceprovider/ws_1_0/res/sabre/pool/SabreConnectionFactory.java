package aero.nettracer.serviceprovider.ws_1_0.res.sabre.pool;

import java.util.List;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import aero.nettracer.serviceprovider.common.db.SabreConnection;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

public class SabreConnectionFactory extends BasePoolableObjectFactory {

	private List<SabreConnection> connections = null;
	private volatile int itemsUsed = 0;
	private String companyCode;

	public SabreConnectionFactory(int profile) {
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = sess.getNamedQuery(User.LOAD_BY_PERMISSION);
		q.setParameter("profile", profile);
		connections = q.list();
		sess.close();
	}

	private synchronized SabreConnection getNextItem() {
		++itemsUsed;
		if (itemsUsed > connections.size()) {
			throw new RuntimeException("Cannot create more items");
		}
		return connections.get(itemsUsed - 1);
	}

	@Override
	public synchronized Object makeObject() throws Exception {
		SabreConnection connection = getNextItem();
		return connection;
	}

}
