package aero.nettracer.serviceprovider.ws_1_0.res.sabre.pool;

import java.util.List;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.hibernate.Query;
import org.hibernate.Session;

import aero.nettracer.serviceprovider.common.db.SabreConnection;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;
import aero.nettracer.serviceprovider.ws_1_0.res.sabre.Reservation;

public class SabrePool extends GenericObjectPool {
	
	private List<SabreConnection> connections = null;
	
	public SabrePool(long profile) {
		super();
		
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = sess.getNamedQuery(SabreConnection.LOAD_BY_PROFILE);
		q.setParameter("profile", profile);
		connections = q.list();
		sess.close();
	
		SabreConnectionFactory factory = new SabreConnectionFactory(
				profile, connections);
		this.setTimeBetweenEvictionRunsMillis(15*60*1000);
		this.setTestWhileIdle(true);

		// Needs to operate as a queue, not a stack.
		this.setLifo(false);
		this.setMaxActive(connections.size());
		this.setMaxWait(1000 * 15);
		this.setWhenExhaustedAction(WHEN_EXHAUSTED_BLOCK);
		this.setFactory(factory);
	}
	

	public Object borrowObject() throws Exception {		
		SabreConnection obj = (SabreConnection) super.borrowObject();
		if (obj.getState() == SabreConnection.LOGGED_OUT_STATE) {
			Reservation.createSession(obj);
			obj.setState(SabreConnection.LOGGED_IN_STATE);
		}
		return obj;
	}

	public void returnObject(Object obj) throws Exception {
		SabreConnection object = (SabreConnection) obj;
		Reservation.ignoreTransaction(object);
		Reservation.closeSession(object);
		object.setState(SabreConnection.LOGGED_OUT_STATE);
		super.returnObject(obj);
	}
	
}
