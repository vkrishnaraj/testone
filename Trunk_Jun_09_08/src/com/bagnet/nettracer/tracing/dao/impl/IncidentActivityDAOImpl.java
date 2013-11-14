package com.bagnet.nettracer.tracing.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.dao.IncidentActivityDAO;
import com.bagnet.nettracer.tracing.db.communications.Activity;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;

public class IncidentActivityDAOImpl implements IncidentActivityDAO {

	private static Logger logger = Logger.getLogger(IncidentActivityDAOImpl.class);
	
	@Override
	public IncidentActivity load(long incidentActivityId) {
		IncidentActivity incidentActivity = null;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			incidentActivity = (IncidentActivity) session.get(IncidentActivity.class, incidentActivityId);			
		} catch (Exception e) {
			logger.error("Exception caught while attempting to load template with id: " + incidentActivityId, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return incidentActivity;
	}

	@Override
	public long save(IncidentActivity incidentActivity) {
		long id = 0;
		if (incidentActivity == null) {
			return id;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();	
			
			Activity activity = getActivityByCode(incidentActivity.getActivity().getCode());
			if (activity == null) return 0;
			
			incidentActivity.setActivity(activity);
			session.save(incidentActivity);
			transaction.commit();
			id = incidentActivity.getId();
		} catch (Exception e) {
			logger.error("Failed to save incident activity: " + incidentActivity.getDocument().getTitle(), e);
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
	public boolean update(IncidentActivity incidentActivity) {
		boolean success = false;
		if (incidentActivity == null) {
			return success;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			IncidentActivity oldActivity = load(incidentActivity.getId());
			if (oldActivity == null) {
				logger.error("Failed to load incident activity with id: " + incidentActivity.getId());
				success = false;
			} else {
				incidentActivity.setCreateDate(oldActivity.getCreateDate());
				Activity activity = getActivityByCode(incidentActivity.getActivity().getCode());
				incidentActivity.setActivity(activity);
				if (activity != null) {
					session.merge(incidentActivity);
					transaction.commit();
					success = true;
				}
			}
		} catch (Exception e) {
			logger.error("Failed to update incident activity with id: " + incidentActivity.getId(), e);
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
	public boolean delete(long incidentActivityId) {
		boolean success = false;
		if (incidentActivityId <= 0) return success;
		
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			IncidentActivity incidentActivity = (IncidentActivity) session.createCriteria(IncidentActivity.class, "ia").add(Restrictions.eq("ia.id", incidentActivityId)).uniqueResult();
			session.delete(incidentActivity);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			logger.error("Failed to delete incident activity with id: " + incidentActivityId, e);
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
	
	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.dao.TemplateDAO#getActivities()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Activity> getActivities() {
		List<Activity> results = new ArrayList<Activity>();
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(Activity.class, "a");
			criteria.addOrder(Order.asc("a.description"));
			results = (List<Activity>) criteria.list();
		} catch (Exception e) {
			logger.error("Failed to load activities", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return results;
	}
	
	@Override
	public Activity getActivity(String code) {
		Activity activity = null;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(Activity.class, "a");
			criteria.add(Restrictions.eq("a.code", code));
			activity = (Activity) criteria.uniqueResult();
		} catch (Exception e) {
			logger.error("Exception caught while attempting to load template with code: " + code, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return activity;
	}

	
	private Activity getActivityByCode(String activityCode) {
		Activity activity = null;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(Activity.class, "a");
			criteria.add(Restrictions.eq("a.code", activityCode));
			activity = (Activity) criteria.uniqueResult();
		} catch (Exception e) {
			logger.error("Failed to load Activity with code: " + activityCode, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return activity;
	}

}
