package com.bagnet.nettracer.cronjob.bmo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue.WtqStatus;

public class WTQueueBmo {
	
	private static final String FIND_TASKS_BY_COMPANY_HQL =
		"select wq.wt_queue_id from WorldTracerQueue wq where wq.agent.companycode_ID = :company and wq.status = :status";

	public void updateQueue(WorldTracerQueue queue) {
		Session sess = HibernateWrapper.getSession().openSession();
		sess.update(queue);
	}

	public List<Long> findAllPendingTasks(String company) {
		return findPendingTasksByType(company, WorldTracerQueue.class);
	}
	
	private <T extends WorldTracerQueue> List<Long> findPendingTasksByType(String company, Class<T> queueClass) {
		Session sess = HibernateWrapper.getSession().openSession();
		Criteria cri = sess.createCriteria(queueClass);
		cri.createCriteria("agent").add(Restrictions.eq("companycode_ID", company));
		cri.add(Restrictions.eq("status", WtqStatus.PENDING));
		cri.setProjection(Projections.property("wt_queue_id"));
		cri.addOrder(Order.asc("wt_queue_id"));
		return cri.list();
	}

	public WorldTracerQueue findById(Long queue_id) {
		Session sess = HibernateWrapper.getSession().openSession();
		return (WorldTracerQueue) sess.get(WorldTracerQueue.class, queue_id);
	}


}
