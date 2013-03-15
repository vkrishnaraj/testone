package com.bagnet.nettracer.wt.bmo;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.wt.WorldTracerAccount;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

public class WorldTracerAccountBMO {
	private static String INSTANCE_NAME = TracerProperties.getInstanceLabel();
	private static final Logger logger = Logger.getLogger(WorldTracerAccountBMO.class);
    private SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public void setSessionFactory(SessionFactory sessionFactory) {
         this.sessionFactory = sessionFactory;
    }
	
	@Transactional(readOnly=true)
	public List<WorldTracerAccount> getAccountNames(String companyCode) {
		Session sess = null;
		
		try {
			sess = getSessionFactory().openSession();
			Query q = sess.createQuery("from WorldTracerAccount a where a.companyCode = :companyCode and a.instanceName = :instanceName");
			q.setString("companyCode", companyCode);
			q.setString("instanceName", INSTANCE_NAME);
			return (List<WorldTracerAccount>)q.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
		return null;
	}
	
	public static WorldTracerAccount getAccount(long id) {
		
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			return (WorldTracerAccount) sess.load(WorldTracerAccount.class, id);
		}
		catch (Exception e) {
			logger.error("unable to get a account info for id " + id, e);
			return null;
		} finally {
			if(sess != null) {
				sess.close();
			}
		}
	}
}
