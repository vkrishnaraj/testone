package com.bagnet.nettracer.wt.bmo;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.bagnet.nettracer.tracing.db.wtq.WorldTracerTransaction;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerTransaction.Result;
import com.bagnet.nettracer.wt.svc.WorldTracerService.TxType;

public class WtTransactionBmo extends HibernateDaoSupport {

	@Transactional
	public void saveTransaction(WorldTracerTransaction tx) {
		Session sess = getSession(false);
		sess.save(tx);

	}
	
	@Transactional(readOnly=true)
	public int getTransactionCount(String txType,
			String result, Date startDate, Date endDate, String incident_id,
			String ohd_id) {
		Session sess = getSession(false);
		Criteria cri = sess.createCriteria(WorldTracerTransaction.class);

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
		cri.setProjection(Projections.rowCount());
		Integer foo = (Integer) cri.uniqueResult();
		return foo;
	}

	@Transactional(readOnly = true)
	public List<WorldTracerTransaction> findTransactions(String txType,
			String result, Date startDate, Date endDate, String incident_id,
			String ohd_id, int startrow, int rowsperpage) {
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
		cri.setFirstResult(startrow);
		cri.setMaxResults(rowsperpage);
		
		List<WorldTracerTransaction> txList = null;
		try {
			txList = cri.list();
		}
		catch(Throwable t){
			logger.error("unable to find transactions", t);
		}
		return txList;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Object[]> countTransactionResults(long hours) {
		Long tmp = (new Date()).getTime();
		
		tmp = tmp - (hours * 60 * 60 * 1000);
		
		Date cutoffDate = new Date();
		cutoffDate.setTime(tmp);
		
		// TODO Auto-generated method stub
		String queryString = 
			"select wtx.txType, " +
			" (select count(*) from WorldTracerTransaction a " +
			"   where a.txType = wtx.txType and a.result = 'SUCCESS' " +
			"   and a.createDate > :cutoffDate) as successCount," +
			" (select count(*) from WorldTracerTransaction a" +
			"   where a.txType = wtx.txType" +
			"   and a.result = 'FAILURE'" +
			"   and a.createDate > :cutoffDate) as failureCount " +
			" from WorldTracerTransaction wtx " +
			" where wtx.createDate > :cutoffDate " +
			" group by wtx.txType " +
			" having successCount = 0 and failureCount > 0 ";
		
		Session sess = getSession(false);
		
		Query q = sess.createQuery(queryString);
		q.setTimestamp("cutoffDate", cutoffDate);
		return q.list();
	}

}
