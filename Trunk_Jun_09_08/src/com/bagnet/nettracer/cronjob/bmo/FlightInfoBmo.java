package com.bagnet.nettracer.cronjob.bmo;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

import com.bagnet.nettracer.tracing.db.datafeed.FlightInfo;

public class FlightInfoBmo {

    private SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public void setSessionFactory(SessionFactory sessionFactory) {
         this.sessionFactory = sessionFactory;
    }

	@Transactional
	public void deleteAllFlights() {
		Session sess = null;
		Transaction tx = null;
		try {
			sess = getSessionFactory().openSession();
			tx = sess.beginTransaction();
		
			Query q = sess.createQuery("delete from FlightInfo");
			q.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				tx.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
	}

	@Transactional
	public void save(FlightInfo fi) {
		Session sess = null;
		Transaction tx = null;
		try {
			sess = getSessionFactory().openSession();
			tx = sess.beginTransaction();
			sess.save(fi);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				tx.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
		
	}



}
