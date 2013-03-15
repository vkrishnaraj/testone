package com.bagnet.nettracer.cronjob.bmo;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
		try {
			sess = getSessionFactory().openSession();
		
			Query q = sess.createQuery("delete from FlightInfo");
			q.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
	}

	@Transactional
	public void save(FlightInfo fi) {
		Session sess = null;
		try {
			sess = getSessionFactory().openSession();
			sess.save(fi);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
		
	}



}
