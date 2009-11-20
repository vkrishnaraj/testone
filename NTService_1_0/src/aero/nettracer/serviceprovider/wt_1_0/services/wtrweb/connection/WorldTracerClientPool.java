package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.connection;

import java.util.List;

import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import aero.nettracer.serviceprovider.common.dao.WorldTracerAccountDao;
import aero.nettracer.serviceprovider.common.db.Profile;
import aero.nettracer.serviceprovider.common.db.WorldTracerWebAccount;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

public class WorldTracerClientPool extends GenericKeyedObjectPool {

	
	private static final Logger logger = Logger
			.getLogger(WorldTracerClientPool.class);

	private AgeManagement ageMgmt = null;
	private WorldTracerClientFactory f = null;

	public WorldTracerClientPool(Profile profile) {

		this.setMaxWait(1000 * 20);
		this.setWhenExhaustedAction(WHEN_EXHAUSTED_BLOCK);
	
	  	this.setTestOnReturn(false);
	  	this.setTestOnBorrow(false);
	  	this.setTestWhileIdle(true);
	  	this.setTimeBetweenEvictionRunsMillis(2*60*1000); // TWO MINUTES
		
		List<WorldTracerWebAccount> accounts = null;
		
		Session sess = HibernateWrapper.getSession().openSession();
		accounts = WorldTracerAccountDao.getWtWebByProfile(sess, profile.getId());
		sess.close();
		
		this.setMaxActive(accounts.size());

		WorldTracerClientFactory factory = new WorldTracerClientFactory(accounts);
		this.setFactory(factory);
		this.ageMgmt = new AgeManagement(factory);
		f = factory;
		
		for (int i = 1; i <= accounts.size(); ++i) {
			try {
				this.borrowObject(new Integer(i));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Integer getNextLoggedInKeyForCron() throws NoActiveConnectionException {
		return ageMgmt.getOldestConnection(true);
	}

	public Object borrowObject(Object obj) throws Exception {
//		if (obj == null || !r.validateKey(obj)) {
//			obj = r.getLeastConnnections();
//		} 
		
		if (obj == null) {
			obj = ageMgmt.getOldestConnection(false);
		} 
		
		if (logger.isDebugEnabled()) {
			logger.info("Borrowing object.." + obj);
		}
		return super.borrowObject(obj);
	}


	public void returnObject(Object obj1, Object obj2) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.info("Returning object.." + obj1);
		}
		super.returnObject(obj1, obj2);
	}
}
