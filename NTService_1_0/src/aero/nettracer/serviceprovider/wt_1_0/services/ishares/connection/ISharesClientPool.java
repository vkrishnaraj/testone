package aero.nettracer.serviceprovider.wt_1_0.services.ishares.connection;

import java.util.List;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import aero.nettracer.serviceprovider.common.dao.WorldTracerAccountDao;
import aero.nettracer.serviceprovider.common.db.Profile;
import aero.nettracer.serviceprovider.common.db.WorldTracerISharesAccount;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

public class ISharesClientPool extends GenericObjectPool {

	private static Logger logger = Logger.getLogger(ISharesClientPool.class);
	private ISharesClientFactory f = null;

	public ISharesClientPool(Profile profile) {

		this.setMaxWait(1000 * 20);
		this.setWhenExhaustedAction(WHEN_EXHAUSTED_BLOCK);

		List<WorldTracerISharesAccount> accounts = null;

		Session sess = HibernateWrapper.getSession().openSession();
		accounts = WorldTracerAccountDao.getISharesByProfile(sess, profile.getId());
		sess.close();

		this.setMaxActive(accounts.size());
		ISharesClientFactory factory = new ISharesClientFactory(accounts);
		this.setFactory(factory);
		f = factory;
	}

	public Object borrowObject() throws Exception {
		return super.borrowObject();
	}

	public void returnObject(Object obj1) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.info("Returning object.." + obj1);
		}
		super.returnObject(obj1);
	}
}
