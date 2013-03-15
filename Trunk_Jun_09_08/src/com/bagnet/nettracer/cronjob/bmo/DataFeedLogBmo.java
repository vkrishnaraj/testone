package com.bagnet.nettracer.cronjob.bmo;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bagnet.nettracer.tracing.db.datafeed.DataFeedLog;
import com.bagnet.nettracer.tracing.db.datafeed.DataFeedLog.DataType;

public class DataFeedLogBmo {
    private SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public void setSessionFactory(SessionFactory sessionFactory) {
         this.sessionFactory = sessionFactory;
    }

	@Transactional(readOnly = true)
	public boolean isAlreadyProcessedByType(Date fileDate, DataType feedType, String companycode_ID) {
		Session sess = null;
		
		try {
			sess = getSessionFactory().openSession();
		
			Query q = sess.getNamedQuery(DataFeedLog.ALREADY_PROCESSED_BY_TYPE);
			q.setParameter("fileDate", fileDate);
			q.setParameter("feedType", feedType);
			q.setParameter("companycode_ID", companycode_ID);
			List<DataFeedLog> foo = q.list();
			
			return foo != null && foo.size() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
		return false;
	}


}
