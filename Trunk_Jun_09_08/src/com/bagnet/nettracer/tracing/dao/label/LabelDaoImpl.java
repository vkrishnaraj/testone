/**
 * 
 */
package com.bagnet.nettracer.tracing.dao.label;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.collections.ListUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Label;

/**
 *
 */
public class LabelDaoImpl implements LabelDao {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public long save(Label label) {
		long id = -1;
		if (label == null) {
			return id;
		}
		
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			session.save(label);
			transaction.commit();
			id = label.getId();
		} catch (Exception e) {
			log.error("Failed to create a label - agentId = {} and text = " + label.getText(), label.getAgent().getAgent_ID(), e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return id;
	}

	@Override
	public boolean update(Label label) {
		boolean success = false;
		if (label == null) {
			return success;
		}

		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Label oldLabel = (Label) session.get(Label.class, label.getId());
			if (oldLabel == null) {
				log.error("Failed to load label - id = {}", label.getId());
				return false;			
			}
			
			transaction = session.beginTransaction();
			
			label.setAgent(oldLabel.getAgent());
			session.merge(label);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			log.error("Failed to update label - id = {}", label.getId(), e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return success;
	}

	@Override
	public boolean delete(int agentId, long labelId) {
		boolean success = false;
		if (agentId < 1 || labelId < 1) {
			return success;
		}

		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(Label.class, "l");
			criteria.add(Restrictions.eq("l.id", labelId));
			criteria.add(Restrictions.eq("l.agent.agent_ID", agentId));
			
			Label label = (Label) criteria.uniqueResult();
			session.delete(label);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			log.error("Failed to delete label - id = {} and agentId = " + agentId, labelId, e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return success;
	}



	@Override
	public Label load(long labelId) {
		Label label = null;
		if (labelId < 1) {
			return label;
		}
		
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			label = (Label) session.get(Label.class, labelId);			
		} catch (Exception e) {
			log.error("Failed loading label - id = {}", labelId, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return label;
	}
	
	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.dao.label.LabelDao#load(int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Label> getLabels(int agentId) {
		List<Label> labels = ListUtils.EMPTY_LIST;
		if (agentId < 1) {
			return labels;
		}
		
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(Label.class, "l");
			criteria.add(Restrictions.eq("l.agent.agent_ID", agentId));
			criteria.addOrder(Order.desc("l.lastUpdate"));
			labels = criteria.list();
		} catch (Exception e) {
			log.error("Failed loading labels by agentId = {}", agentId, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return labels;
	}
}
