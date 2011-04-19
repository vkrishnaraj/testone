package com.bagnet.nettracer.tracing.dao;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import aero.nettracer.fs.model.FsClaim;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.forms.SearchClaimForm;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;

public class ClaimDAO {
	
	private static final String EXCEPTION_MESSAGE = "Exception in ClaimDAO";
	
	private static Logger logger = Logger.getLogger(ClaimDAO.class);
	
	public static Claim loadClaim(long id) {
		Session session = null;
		Claim claim = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			claim = (Claim) session.get(Claim.class, id);
		} catch (Exception e) {
			logger.error(EXCEPTION_MESSAGE, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return claim;
	}
	
	public static boolean saveClaim(Claim claim) {
		boolean success = false;
		if (claim == null) {
			return success;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			if(claim.getId() > 0) {
				session.merge(claim);
			} else {
				session.save(claim);
			}
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Set getClaimsFromSearchForm(SearchClaimForm form, Agent user) {
		Set results = null;
		Session session = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = getCriteriaFromForm(session, form, user);
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
	
	private static Criteria getCriteriaFromForm(Session session, SearchClaimForm form, Agent user) {
		Criteria criteria = session.createCriteria(FsClaim.class);
		criteria = getIdCriteria(form, criteria);		
		criteria = getNtIncidentIdCriteria(form, criteria); 
		criteria = getClaimDateCriteria(form, user, criteria);
		criteria = getClaimantCriteria(form, criteria);
		return criteria;
	}
	
	private static Criteria getIdCriteria(SearchClaimForm form, Criteria criteria) {
		if (form.getClaimId() > 0) {
			criteria.add(Expression.eq("id", form.getClaimId()));
		}
		return criteria;
	}
	
	private static Criteria getNtIncidentIdCriteria(SearchClaimForm form, Criteria criteria) {
		String ntIncidentId = form.getIncidentId();
		if (ntIncidentId != null && !ntIncidentId.isEmpty()) {
			criteria.add(Expression.like("ntIncidentId", ntIncidentId));
		}
		return criteria;
	}
	
	private static Criteria getClaimDateCriteria(SearchClaimForm form, Agent agent, Criteria criteria) {
		Date startDate = DateUtils.convertToDate(form.getS_createtime(), agent.getDateformat().getFormat(), agent.getCurrentlocale());
		Date endDate = DateUtils.convertToDate(form.getE_createtime(), agent.getDateformat().getFormat(), agent.getCurrentlocale());
		
		if (startDate != null && endDate != null) {
			criteria.add(Expression.between("claimDate", startDate, endDate));
		} else if (startDate != null && endDate == null) {
			criteria.add(Expression.ge("claimDate", startDate));
		} else if (startDate == null && endDate != null) {
			criteria.add(Expression.le("claimDate", endDate));
		}
		
		return criteria;
		
	}
	
	private static Criteria getClaimantCriteria(SearchClaimForm form, Criteria criteria) {
		Criteria claimantCriteria = criteria.createCriteria("claimants");

		String value = form.getLastName();
		if (value != null && !value.isEmpty()) {
			claimantCriteria.add(Restrictions.like("lastName", form.getLastName()));
		}

		value = form.getFirstName();
		if (value != null && !value.isEmpty()) {
			claimantCriteria.add(Restrictions.like("firstName", form.getFirstName()));
		}
		
		value = form.getMiddleName();
		if (value != null && !value.isEmpty()) {
			claimantCriteria.add(Restrictions.like("middleName", form.getMiddleName()));
		}
		
		value = form.getEmailAddress();
		if (value != null && !value.isEmpty()) {
			claimantCriteria.add(Restrictions.like("emailAddress", value));
		}
		
		claimantCriteria = getClaimantAddressCriteria(form, claimantCriteria);
		claimantCriteria = getClaimantPhoneCriteria(form, claimantCriteria);
		
		return criteria;
	}
	
	private static Criteria getClaimantAddressCriteria(SearchClaimForm form, Criteria claimantCriteria) {
		Criteria addressCriteria = claimantCriteria.createCriteria("addresses");
		
		String value = form.getAddress1();
		if (value != null && !value.isEmpty()) {
			addressCriteria.add(Restrictions.like("address1", value));
		}
		
		value = form.getAddress2();
		if (value != null && !value.isEmpty()) {
			addressCriteria.add(Restrictions.like("address2", value));
		}

		value = form.getCity();
		if (value != null && !value.isEmpty()) {
			addressCriteria.add(Restrictions.like("city", value));
		}

		value = form.getState();
		if (value != null && !value.isEmpty()) {
			addressCriteria.add(Restrictions.like("state", value));
		} 			

		value = form.getProvince();
		if (value != null && !value.isEmpty()) {
			addressCriteria.add(Restrictions.like("province", value));
		} 			

		value = form.getCountry();
		if (value != null && !value.isEmpty()) {
			addressCriteria.add(Restrictions.like("country", value));
		}
		
		value = form.getZip();
		if (value != null && !value.isEmpty()) {
			addressCriteria.add(Restrictions.like("zip", value));
		}
		
		return claimantCriteria;
	}
	
	private static Criteria getClaimantPhoneCriteria(SearchClaimForm form, Criteria claimantCriteria) {
		Criteria phoneCriteria = claimantCriteria.createCriteria("phones");
		
		String value = form.getPhone();
		if (value != null && !value.isEmpty()) {
			value = StringUtils.removeNonNumeric(value);
			phoneCriteria.add(Restrictions.like("phoneNumber", value));
		}
		
		return claimantCriteria;
	}

}
