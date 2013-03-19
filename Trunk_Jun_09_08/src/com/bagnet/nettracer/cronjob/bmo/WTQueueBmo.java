package com.bagnet.nettracer.cronjob.bmo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue.WtqStatus;

public class WTQueueBmo {
	
    private SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public void setSessionFactory(SessionFactory sessionFactory) {
         this.sessionFactory = sessionFactory;
    }
	
	private static final String FIND_TASKS_BY_COMPANY_HQL =
		"select wq.wt_queue_id from WorldTracerQueue wq where wq.agent.companycode_ID = :company and wq.status = :status";

	@Transactional
	public void updateQueue(WorldTracerQueue queue) {
		Session sess = null;
		Transaction tx = null;
		try {
			sess = getSessionFactory().openSession();
			tx = sess.beginTransaction();
			sess.update(queue);
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

	@Transactional(readOnly=true)
	public List<Long> findAllPendingTasks(String company) {
		return findPendingTasksByType(company, WorldTracerQueue.class);
	}
	
	private <T extends WorldTracerQueue> List<Long> findPendingTasksByType(String company, Class<T> queueClass) {
		Session sess = null;
		try {
			sess = getSessionFactory().openSession();
			Criteria cri = sess.createCriteria(queueClass);
			cri.createCriteria("agent").add(Restrictions.eq("companycode_ID", company));
			cri.add(Restrictions.eq("status", WtqStatus.PENDING));
			cri.setProjection(Projections.property("wt_queue_id"));
			cri.addOrder(Order.asc("wt_queue_id"));
			return cri.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	public WorldTracerQueue findById(Long queue_id) {
		Session sess = null;
		try {
			sess = getSessionFactory().openSession();
			return (WorldTracerQueue) sess.get(WorldTracerQueue.class, queue_id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
		return null;
	}


}
