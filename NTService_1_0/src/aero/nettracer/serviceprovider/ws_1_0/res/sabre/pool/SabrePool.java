package aero.nettracer.serviceprovider.ws_1_0.res.sabre.pool;

import java.util.List;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import aero.nettracer.serviceprovider.common.db.SabreConnection;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;
import aero.nettracer.serviceprovider.ws_1_0.res.sabre.Reservation;

public class SabrePool extends GenericObjectPool {
	
	private List<SabreConnection> connections = null;
	private static Logger logger = Logger.getLogger(SabrePool.class);
	
	public SabrePool(long profile) {
		super();
		
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = sess.getNamedQuery(SabreConnection.LOAD_BY_PROFILE);
		q.setParameter("profile", profile);
		connections = q.list();
		sess.close();
	
		SabreConnectionFactory factory = new SabreConnectionFactory(
				profile, connections);
		/*
		TODO: Replaced in attempt to identify or fix pool exhaustion issue
		this.setTimeBetweenEvictionRunsMillis(15*60*1000);
		this.setTestWhileIdle(true);
		*/

		this.setTestWhileIdle(false);

		// Needs to operate as a queue, not a stack.
		this.setLifo(false);
		this.setMaxActive(connections.size());
		this.setMaxWait(1000 * 15);
		this.setWhenExhaustedAction(WHEN_EXHAUSTED_BLOCK);
		this.setFactory(factory);
	}
	

	public Object borrowObject() throws Exception {
		logger.info("Idle: " + this.getNumIdle() + "/" + this.getNumActive());
		
		SabreConnection obj = (SabreConnection) super.borrowObject();
		if (obj.getState() == SabreConnection.LOGGED_OUT_STATE) {
			try {
				
				Reservation.createSession(obj);
			} catch (Exception e) {
				super.returnObject(obj);
				throw e;
			}
			obj.setState(SabreConnection.LOGGED_IN_STATE);
		}
		return obj;
	}

	public void returnObject(Object obj) throws Exception {
		SabreConnection object = (SabreConnection) obj;
		try {
			Reservation.ignoreTransaction(object);
			Reservation.closeSession(object);
			
		} catch (Exception e) {
			// Ignore
		}
		if (obj != null) {
			object.setState(SabreConnection.LOGGED_OUT_STATE);
			super.returnObject(obj);
		}
	}
	
}
