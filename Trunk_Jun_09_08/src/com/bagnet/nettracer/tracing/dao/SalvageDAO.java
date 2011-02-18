package com.bagnet.nettracer.tracing.dao;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Expression;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.actions.salvage.SalvageSearchAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.salvage.Salvage;
import com.bagnet.nettracer.tracing.forms.salvage.SalvageSearchForm;
import com.bagnet.nettracer.tracing.utils.DateUtils;

@SuppressWarnings("rawtypes")
public class SalvageDAO {
	
	private static final String EXCEPTION_MESSAGE = "Exception in SalvageDAO";
	
	private static Logger logger = Logger.getLogger(SalvageSearchAction.class);
	
	public static Salvage loadSalvage(int id) {
		Session session = null;
		Salvage salvage = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			salvage = (Salvage) session.get(Salvage.class, id);
		} catch (Exception e) {
			logger.error(EXCEPTION_MESSAGE, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return salvage;
	}
	
	public static boolean saveSalvage(Salvage salvage) {
		boolean success = false;
		if (salvage == null) {
			return success;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(salvage);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			logger.error(EXCEPTION_MESSAGE, e);
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
	
	@SuppressWarnings("unchecked")
	public static Set getSalvagesFromSearchForm(SalvageSearchForm form, Agent agent) {
		LinkedHashSet results = null;
		Session session = null;

		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(Salvage.class);
			criteria = getSalvageIdCriteria(form, criteria);
			criteria = getSalvageStatusCriteria(form, criteria);
			criteria = getSalvageDateCriteria(form, agent, criteria);
			results = new LinkedHashSet(criteria.list());
			if (results.isEmpty()) {
				results = null;
			}
		} catch (Exception e) {
			logger.error(EXCEPTION_MESSAGE, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return results;
	}
	
	private static Criteria getSalvageIdCriteria(SalvageSearchForm form, Criteria criteria) {
		if (form.getSalvageId() > 0) {
			criteria.add(Expression.like("salvageId", form.getSalvageId()));
		}
		return criteria;
	}
	
	private static Criteria getSalvageStatusCriteria(SalvageSearchForm form, Criteria criteria) {
		if (form.getSalvageStatus() == TracingConstants.SALVAGE_ALL) {
			criteria.add(Expression.in("status", new Integer[] {TracingConstants.SALVAGE_OPEN, TracingConstants.SALVAGE_CLOSED}));
		} else {
			criteria.add(Expression.eq("status", form.getSalvageStatus()));
		}
		return criteria;
	}

	private static Criteria getSalvageDateCriteria(SalvageSearchForm form, Agent agent, Criteria criteria) {
		Date startDate = DateUtils.convertToDate(form.getS_createtime(), "", agent.getCurrentlocale());
		Date endDate = DateUtils.convertToDate(form.getE_createtime(), "", agent.getCurrentlocale());
		
		if (startDate != null && endDate != null) {
			criteria.add(Expression.between("salvageDate", startDate, endDate));
		} else if (startDate != null && endDate == null) {
			criteria.add(Expression.ge("salvageDate", startDate));
		} else if (startDate == null && endDate != null) {
			criteria.add(Expression.le("salvageDate", endDate));
		}
		
		return criteria;
	}
	
}
