package com.bagnet.nettracer.cronjob.bmo;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.datafeed.DataFeedLog;
import com.bagnet.nettracer.tracing.db.datafeed.DataFeedLog.DataType;

public class DataFeedLogBmo {


	public boolean isAlreadyProcessedByType(Date fileDate, DataType feedType, String companycode_ID) {
		Session sess = HibernateWrapper.getSession().openSession();
		
		Query q = sess.getNamedQuery(DataFeedLog.ALREADY_PROCESSED_BY_TYPE);
		q.setParameter("fileDate", fileDate);
		q.setParameter("feedType", feedType);
		q.setParameter("companycode_ID", companycode_ID);
		List<DataFeedLog> foo = q.list();
		
		return foo != null && foo.size() > 0;
	}


}
