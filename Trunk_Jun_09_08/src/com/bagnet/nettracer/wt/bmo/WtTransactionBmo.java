package com.bagnet.nettracer.wt.bmo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;


import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerTransaction;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerTransaction.Result;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.wt.svc.WorldTracerService.TxType;

public class WtTransactionBmo extends HibernateDaoSupport {

	@Transactional
	public void saveTransaction(WorldTracerTransaction tx) {
		Session sess = getSession(false);
		sess.save(tx);

	}

	@Transactional(readOnly = true)
	public List<WorldTracerTransaction> findTransactions(String txType,
			String result, Date startDate, Date endDate, String incident_id,
			String ohd_id) {
		Session sess = getSession(false);
		Criteria cri = sess.createCriteria(WorldTracerTransaction.class);
		
		cri.addOrder(Order.desc("createDate"));
		if(txType != null && !"ALL".equals(txType)) {
			cri.add(Restrictions.eq("txType", TxType.valueOf(txType)));
		}
		if(result != null && !"ALL".equals(result)) {
			cri.add(Restrictions.eq("result", Result.valueOf(result)));
		}
		if(startDate != null) {
			cri.add(Restrictions.ge("createDate", startDate));
		}
		if(endDate != null) {
			cri.add(Restrictions.le("createDate", endDate));
		}
		if(incident_id != null && incident_id.trim().length() > 0) {
			cri.add(Restrictions.like("incident.incident_ID", incident_id).ignoreCase());
		}
		if(ohd_id != null && ohd_id.trim().length() > 0) {
			cri.add(Restrictions.like("ohd.OHD_ID", ohd_id).ignoreCase());
		}
		List<WorldTracerTransaction> txList = null;
		try {
			txList = cri.list();
		}
		catch(Throwable t){
			logger.error("unable to find transactions", t);
		}
		return txList;

	}

}
