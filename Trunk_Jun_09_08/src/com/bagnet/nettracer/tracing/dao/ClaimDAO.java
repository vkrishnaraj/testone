package com.bagnet.nettracer.tracing.dao;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.forms.SearchClaimForm;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;

public class ClaimDAO {
	
	private static Logger logger = Logger.getLogger(ClaimDAO.class);
	private static final String EXCEPTION_MESSAGE = "Exception in ClaimDAO";
	
	private static final String CLAIM_DATE = "claim_date";
	private static final String DOB = "dob";
	
	private String sqlFromForm = null;
	
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
	
	public long getClaimCountFromSearchForm(SearchClaimForm form, Agent user) {
		long count = -1;
		Session session = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			String sql = "select count(distinct c) from aero.nettracer.fs.model.FsClaim c ";
			sqlFromForm = getSqlFromForm(form, user);
			sql += sqlFromForm;
			Query query = session.createQuery(sql);
			setQueryParameters(form, user, query);
			count = (Long) query.uniqueResult();
		} catch (Exception e) {
			logger.error(EXCEPTION_MESSAGE, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return count;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Set getClaimsFromSearchForm(SearchClaimForm form, Agent user, int rowsperpage, int currpage) {
		Set results = null;
		Session session = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			String sql = "select distinct c from aero.nettracer.fs.model.FsClaim c ";
			sql += sqlFromForm;
			Query query = session.createQuery(sql);
			setQueryParameters(form, user, query);
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				query.setFirstResult(startnum);
				query.setMaxResults(rowsperpage);
			}
			
			results = new LinkedHashSet(query.list());
		} catch (Exception e) {
			logger.error(EXCEPTION_MESSAGE, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return results;
	}
	
	private static void setQueryParameters(SearchClaimForm form, Agent user, Query query) {
		
		if (form.getClaimId() > 0) {
			query.setLong("claimId", form.getClaimId());
		}
		
		String value = form.getIncidentId();
		if (value != null && !value.isEmpty()) {
			query.setString("ntIncidentId", value);
		}
		
		value = form.getS_createtime();
		if (value != null && !value.isEmpty()) {
			query.setDate(CLAIM_DATE+"_start", DateUtils.convertToDate(form.getS_createtime(), user.getDateformat().getFormat(), user.getCurrentlocale()));
		}
		
		value = form.getE_createtime();
		if (value != null && !value.isEmpty()) {
			query.setDate(CLAIM_DATE+"_end", DateUtils.convertToDate(form.getE_createtime(), user.getDateformat().getFormat(), user.getCurrentlocale()));
		}

		value = form.getLastName();
		if (value != null && !value.isEmpty()) {
			query.setString("lastName", form.getLastName());
		}

		value = form.getFirstName();
		if (value != null && !value.isEmpty()) {
			query.setString("firstName", form.getFirstName());
		}

		value = form.getMiddleName();
		if (value != null && !value.isEmpty()) {
			query.setString("middleName", form.getFirstName());
		}

		value = form.getEmailAddress();
		if (value != null && !value.isEmpty()) {
			query.setString("emailAddress", form.getEmailAddress());
		}

		value = form.getAddress1();
		if (value != null && !value.isEmpty()) {
			query.setString("address1", value);
		}

		value = form.getAddress2();
		if (value != null && !value.isEmpty()) {
			query.setString("address2", value);
		}
		
		value = form.getCity();
		if (value != null && !value.isEmpty()) {
			query.setString("city", value);
		}
		
		value = form.getState();
		if (value != null && !value.isEmpty()) {
			query.setString("state", value);
		}
		
		value = form.getProvince();
		if (value != null && !value.isEmpty()) {
			query.setString("province", value);
		}

		value = form.getCountry();
		if (value != null && !value.isEmpty()) {
			query.setString("country", value);
		}
		
		value = form.getZip();
		if (value != null && !value.isEmpty()) {
			query.setString("zip", value);
		}

		value = form.getPhone();
		if (value != null && !value.isEmpty()) {
			value = StringUtils.removeNonNumeric(value);
			query.setString("phoneNumber", value);
		}
		
		value = form.getStartDateOfBirth();
		if (value != null && !value.isEmpty()) {
			query.setDate(DOB+"_start", DateUtils.convertToDate(form.getS_createtime(), user.getDateformat().getFormat(), user.getCurrentlocale()));
		}

		value = form.getEndDateOfBirth();
		if (value != null && !value.isEmpty()) {
			query.setDate(DOB+"_end", DateUtils.convertToDate(form.getE_createtime(), user.getDateformat().getFormat(), user.getCurrentlocale()));
		}
		
	}
	
	private String getSqlFromForm(SearchClaimForm form, Agent user) {
		StringBuilder fromSql = new StringBuilder();		
		StringBuilder whereSql = new StringBuilder("where 1 = 1 ");
		
		// add claim id sql
		whereSql.append(getIdSql(form));
		
		// add incident id sql
		whereSql.append(getNtIncidentIdSql(form));

		// add claim date sql
		whereSql.append(getDateSql("c.claimDate", CLAIM_DATE, form.getS_createtime(), form.getE_createtime(), user));

		// add claimant sql
		getPersonSql(form, user, fromSql, whereSql);
		
		return fromSql.toString() + whereSql.toString() + "order by c.claimDate desc";
	}
	
	private String getIdSql(SearchClaimForm form) {
		String toReturn = "";
		if (form.getClaimId() > 0) {
			toReturn = "and c.id = :claimId ";
		}
		return toReturn;
	}
	
	private String getNtIncidentIdSql(SearchClaimForm form) {
		String toReturn = "";
		String ntIncidentId = form.getIncidentId();
		if (ntIncidentId != null && !ntIncidentId.isEmpty()) {
			toReturn = "and c.ntIncidentId = :ntIncidentId ";
		}
		return toReturn;
	}
	
	private String getDateSql(String targetField, String fieldName, String startDate, String endDate, Agent agent) {
		String toReturn = "";
		boolean startDateValid = startDate != null && !startDate.isEmpty();
		boolean endDateValid = endDate != null && !endDate.isEmpty();
		if (startDateValid && endDateValid) {
			toReturn = "and " + targetField + " between :" + fieldName + "_start and :" + fieldName + "_end ";
		} else if (startDateValid) {
			toReturn = "and " + targetField + " >= :" + fieldName + "_start ";
		} else if (endDateValid) {
			toReturn = "and " + targetField + " <= :" + fieldName + "_end ";
		}
		
		return toReturn;
	}
	
	private String getPersonSql(SearchClaimForm form, Agent agent, StringBuilder fromSql, StringBuilder whereSql) {
		StringBuilder toReturn = new StringBuilder();
		
		String value = form.getLastName();
		if (value != null && !value.isEmpty()) {
			toReturn.append("and (p.lastName = :lastName or c.incident.reservation.purchaser.lastName = :lastName) ");
		}
		
		value = form.getFirstName();
		if (value != null && !value.isEmpty()) {
			toReturn.append("and (p.firstName = :firstName or c.incident.reservation.purchaser.firstName = :firstName) ");
		}
		
		value = form.getMiddleName();
		if (value != null && !value.isEmpty()) {
			toReturn.append("and (p.middleName = :middleName or c.incident.reservation.purchaser.middleName = :middleName) ");
		}
		
		value = form.getEmailAddress();
		if (value != null && !value.isEmpty()) {
			toReturn.append("and p.emailAddress = :emailAddress or c.incident.reservation.purchaser.emailAddresss = :emailAddress) ");
		}
		
		toReturn.append(getDateSql("p.dateOfBirth", DOB, form.getStartDateOfBirth(), form.getEndDateOfBirth(), agent));
		
		String personSql = toReturn.toString();		
		if (!personSql.isEmpty()) {
			whereSql.append(toReturn.toString());
		}
		
		String addressSql = getAddressSql(form);
		if (!addressSql.isEmpty()) {
			whereSql.append(addressSql);
		}
		
		String phoneSql = getPhoneSql(form);
		if (!phoneSql.isEmpty()) {
			whereSql.append(phoneSql);
		}
		
		if (!personSql.isEmpty() || !addressSql.isEmpty() || !phoneSql.isEmpty()) {
			fromSql.append("left outer join c.claimants as p ");
			if (!addressSql.isEmpty()) {
				fromSql.append("left outer join p.addresses as a ");
				fromSql.append("left outer join c.incident.reservation.purchaser.addresses as pa ");
			}
			
			if (!phoneSql.isEmpty()) {
				fromSql.append("left outer join p.phones as ph ");
				fromSql.append("left outer join c.incident.reservation.purchaser.phones as pph ");
			}
		}
		
		return toReturn.toString();
	}

	private String getAddressSql(SearchClaimForm form) {
		StringBuilder toReturn = new StringBuilder();
		
		String value = form.getAddress1();
		if (value != null && !value.isEmpty()) {
			toReturn.append("and (a.address1 = :address1 or pa.address1 = :address1) ");
		}
		
		value = form.getAddress2();
		if (value != null && !value.isEmpty()) {
			toReturn.append("and (a.address2 = :address2 or pa.address2 = :address2) ");
		}

		value = form.getCity();
		if (value != null && !value.isEmpty()) {
			toReturn.append("and (a.city = :city or pa.city = :city) ");
		}

		value = form.getState();
		if (value != null && !value.isEmpty()) {
			toReturn.append("and (a.state = :state or pa.state = :state) ");
		} 			

		value = form.getProvince();
		if (value != null && !value.isEmpty()) {
			toReturn.append("and (a.province = :province or pa.province = :province) ");
		} 			

		value = form.getCountry();
		if (value != null && !value.isEmpty()) {
			toReturn.append("and (a.country = :country or pa.country = :country) ");
		}
		
		value = form.getZip();
		if (value != null && !value.isEmpty()) {
			toReturn.append("and (a.zip = :zip or pa.zip = :zip) ");
		}
		
		return toReturn.toString();
	}
	
	private String getPhoneSql(SearchClaimForm form) {
		StringBuilder toReturn = new StringBuilder();
		
		String value = form.getPhone();
		if (value != null && !value.isEmpty()) {
			toReturn.append("and (ph.phoneNumber = :phoneNumber or pph.phoneNumber = :phoneNumber) ");
		}
		return toReturn.toString();
	}
	
}
