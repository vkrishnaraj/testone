package com.bagnet.nettracer.tracing.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.StandardBasicTypes;

import aero.nettracer.fs.model.FsClaim;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
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
			String sql = "select count(distinct c.id) from aero.nettracer.fs.model.FsClaim c ";
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
	public Set<FsClaim> getClaimsFromSearchForm(SearchClaimForm form, Agent user, int rowsperpage, int currpage) {
		Set results = null;
		Session session = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			String sql = "select distinct c from aero.nettracer.fs.model.FsClaim c ";
			sql += sqlFromForm  + "order by c.claimDate desc";
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
			value = value.trim();
			query.setString("ntIncidentId", value);
		}
		
		value = form.getS_createtime();
		if (value != null && !value.isEmpty()) {
			value = value.trim();
			String startDate = DateUtils.formatDate(value, user.getDateformat().getFormat(), TracingConstants.DB_DATEFORMAT, user.getDefaultlocale(), null);
			query.setString(CLAIM_DATE+"_start", startDate);
		}
		
		value = form.getE_createtime();
		if (value != null && !value.isEmpty()) {
			value = value.trim();
			Date end = DateUtils.convertToDate(value, user.getDateformat().getFormat(), user.getDefaultlocale());
			Calendar c = new GregorianCalendar();
			c.setTime(end);
			c.add(Calendar.DAY_OF_MONTH, 1);
			String endDate = DateUtils.formatDate(c.getTime(), TracingConstants.DB_DATEFORMAT, user.getDefaultlocale(), null);
			query.setString(CLAIM_DATE+"_end", endDate);
		}

		value = form.getLastName();
		if (value != null && !value.isEmpty()) {
			value = value.trim();
			query.setString("lastName", value);
		}

		value = form.getFirstName();
		if (value != null && !value.isEmpty()) {
			value = value.trim();
			query.setString("firstName", value);
		}

		value = form.getMiddleName();
		if (value != null && !value.isEmpty()) {
			value = value.trim();
			query.setString("middleName", value);
		}

		value = form.getEmailAddress();
		if (value != null && !value.isEmpty()) {
			value = value.trim();
			query.setString("emailAddress", value);
		}

		value = form.getAddress1();
		if (value != null && !value.isEmpty()) {
			value = value.trim();
			query.setString("address1", value);
		}

		value = form.getAddress2();
		if (value != null && !value.isEmpty()) {
			value = value.trim();
			query.setString("address2", value);
		}
		
		value = form.getCity();
		if (value != null && !value.isEmpty()) {
			value = value.trim();
			query.setString("city", value);
		}
		
		value = form.getState();
		if (value != null && !value.isEmpty()) {
			value = value.trim();
			query.setString("state", value);
		}
		
		value = form.getProvince();
		if (value != null && !value.isEmpty()) {
			value = value.trim();
			query.setString("province", value);
		}

		value = form.getCountry();
		if (value != null && !value.isEmpty()) {
			value = value.trim();
			query.setString("country", value);
		}
		
		value = form.getZip();
		if (value != null && !value.isEmpty()) {
			value = value.trim();
			query.setString("zip", value);
		}

		value = form.getPhone();
		if (value != null && !value.isEmpty()) {
			value = StringUtils.removeNonNumeric(value);
			query.setString("phoneNumber", value);
		}
		
		value = form.getStartDateOfBirth();
		if (value != null && !value.isEmpty()) {
			value = value.trim();
			query.setDate(DOB+"_start", DateUtils.convertToDate(value, user.getDateformat().getFormat(), user.getCurrentlocale()));
		}

		value = form.getEndDateOfBirth();
		if (value != null && !value.isEmpty()) {
			value = value.trim();
			query.setDate(DOB+"_end", DateUtils.convertToDate(value, user.getDateformat().getFormat(), user.getCurrentlocale()));
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
		whereSql.append(getDateSql("c.claimDate", CLAIM_DATE, form.getS_createtime(), form.getE_createtime()));

		// add claimant sql
		getPersonSql(form, user, fromSql, whereSql);
		
		return fromSql.toString() + whereSql.toString();
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
			toReturn = "and (c.ntIncidentId = :ntIncidentId or c.incident.airlineIncidentId = :ntIncidentId) ";			
		}
		return toReturn;
	}
	
	private String getDateSql(String targetField, String fieldName, String startDate, String endDate) {
		String toReturn = "";
		boolean startDateValid = startDate != null && !startDate.isEmpty();
		boolean endDateValid = endDate != null && !endDate.isEmpty();

		if (startDateValid) {
			toReturn += "and " + targetField + " >= :" + fieldName + "_start ";
		} 
		
		if (endDateValid) {
			toReturn += "and " + targetField + " < :" + fieldName + "_end ";
		}
		
		return toReturn;
	}
	
	private String getPersonSql(SearchClaimForm form, Agent agent, StringBuilder fromSql, StringBuilder whereSql) {
		StringBuilder toReturn = new StringBuilder();
		
		if ((form.getLastName() != null && !form.getLastName().isEmpty())
				|| (form.getFirstName() != null && !form.getFirstName().isEmpty())
				|| (form.getMiddleName() != null && !form.getMiddleName().isEmpty())
				|| (form.getEmailAddress() != null && !form.getEmailAddress().isEmpty())) {
			fromSql.append("left outer join c.incident inc ");
			fromSql.append("left outer join inc.reservation res ");
			fromSql.append("left outer join res.purchaser pur ");
		}
		
		String value = form.getLastName();
		if (value != null && !value.isEmpty()) {
			toReturn.append("and (p.lastName like :lastName or pur.lastName like :lastName) ");
		}
		
		value = form.getFirstName();
		if (value != null && !value.isEmpty()) {
			toReturn.append("and (p.firstName like :firstName or pur.firstName like :firstName) ");
		}
		
		value = form.getMiddleName();
		if (value != null && !value.isEmpty()) {
			toReturn.append("and (p.middleName like :middleName or pur.middleName like :middleName) ");
		}
		
		value = form.getEmailAddress();
		if (value != null && !value.isEmpty()) {
			toReturn.append("and (p.emailAddress like :emailAddress or pur.emailAddress like :emailAddress) ");
		}
		
		toReturn.append(getDateSql("p.dateOfBirth", DOB, form.getStartDateOfBirth(), form.getEndDateOfBirth()));
		
		String personSql = toReturn.toString();		
		if (!personSql.isEmpty()) {
			whereSql.append(toReturn.toString());
		}
		
		String addressSql = getAddressSql(form);
		if (!addressSql.isEmpty()) {
			whereSql.append(addressSql);
		}
		
		String phoneSql = getPhoneSql(form, fromSql);
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
	
	private String getPhoneSql(SearchClaimForm form, StringBuilder fromSql) {
		StringBuilder toReturn = new StringBuilder();
		
		String value = form.getPhone();
		if (value != null && !value.isEmpty()) {
			fromSql.append(" left outer join c.phones as cph ");
			toReturn.append("and (ph.phoneNumber = :phoneNumber or pph.phoneNumber = :phoneNumber or cph.phoneNumber = :phoneNumber) ");
		}
		return toReturn.toString();
	}
	
	public static String getClaimTypeDescription(int claimTypeId) {
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			SQLQuery query = session.createSQLQuery("select description from ItemType where ItemType_ID = :claimTypeId");
			query.setInteger("claimTypeId", claimTypeId);
			query.addScalar("description", StandardBasicTypes.STRING);
			String result = (String) query.uniqueResult();
			return result != null ? result : "";
		} catch (Exception e) {
			logger.error("Error caught looking up ItemType with id: " + claimTypeId, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return null;
	}
}
