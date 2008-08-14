package com.bagnet.nettracer.cronjob.bmo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.bagnet.nettracer.tracing.db.WT_Queue;

public class WTQueueBmo extends HibernateDaoSupport {
	
	private static final String FIND_TASKS_BY_COMPANY_HQL =
		"from WT_Queue wq where wq.agent.companycode_ID = :company and wq.queue_status between 0 and 10";
	
	private static final String FIND_TASKS_BY_COMPANY_TYPE_HQL =
		"from WT_Queue wq where wq.agent.companycode_ID = :company and wq.type = :type and wq.queue_status between 0 and 10";
	
	@Transactional(readOnly = true)
	public List<WT_Queue> getPendingTasksByCompany(String companyCode) {
		Session sess = getSession(false);
		Query q = sess.createQuery(FIND_TASKS_BY_COMPANY_HQL);
		q.setParameter("company", companyCode);
		List<WT_Queue> result = q.list();
		return result;
	}

	@Transactional
	public void updateQueue(WT_Queue queue) {
		Session sess = getSession(false);
		sess.update(queue);
	}

	@Transactional(readOnly = true)
	public List<WT_Queue> getPendingTasksByCompanyType(String companyCode, String wqType) {
		// TODO Auto-generated method stub
		Session sess = getSession(false);
		Query q = sess.createQuery(FIND_TASKS_BY_COMPANY_TYPE_HQL);
		q.setParameter("company", companyCode);
		q.setParameter("type", wqType);
		List<WT_Queue> result = q.list();
		return result;
	}
}
