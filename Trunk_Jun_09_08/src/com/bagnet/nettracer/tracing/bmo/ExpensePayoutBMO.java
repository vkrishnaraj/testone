package com.bagnet.nettracer.tracing.bmo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Comment;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.forms.SearchExpenseForm;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class ExpensePayoutBMO {

	private static final Logger logger = Logger.getLogger(IncidentBMO.class);

	public static ExpensePayout findExpensePayout(int epId) {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			return (ExpensePayout) sess.get(ExpensePayout.class, epId);
		} catch (Exception e) {
			logger.error("unable to find expense " + epId, e);
		} finally {
			if (sess != null)
				sess.close();
		}
		return null;
	}

	public static boolean updateExpense(ExpensePayout ep, Agent user) {
		Session sess = HibernateWrapper.getSession().openSession();
		Transaction tx = sess.beginTransaction();
		try {
			sess.update(ep);
			tx.commit();
			Transaction tx2 = sess.beginTransaction();
			try {
				IncidentBMO.auditClaim(ep.getIncident().getClaim(), TracerUtils.getResourcePropertyText(
						"updating.expense.audit", user), user, sess);
				tx2.commit();
			} catch (Exception e) {
				tx2.rollback();
			}
			return true;
		} catch (Exception e) {

			logger.error("unable to update expense " + ep.getExpensepayout_ID(), e);
			if (tx != null) {

				try {
					tx.rollback();
				} catch (HibernateException e1) {
					// pass
					logger.debug("unable to rollback expense update transaction");
				}
			}
			return false;
		} finally {
			if (sess != null)
				sess.close();
		}
	}

	public static List<ExpensePayout> searchExpenses(SearchExpenseForm form, Agent user) {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			Criteria crit = getSearchCriteria(form, user, sess);
			crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			crit.addOrder(Order.asc("st.status_ID"));
			List<ExpensePayout> foo = crit.list();
			int startRow = form.getCurrentPage() * form.getRowsPerPage();
			int rows = form.getRowsPerPage();
			if (foo != null) {
				if (foo.size() >= startRow + rows) {
					return foo.subList(startRow, startRow + rows);
				}	
				else if(foo.size() > startRow) {
					return foo.subList(startRow, foo.size());
				}
				else if(foo.size() >= rows) {
					return foo.subList(0, rows);
				}
				else {
					return foo;
				}
			}
			return null;
		} catch (Exception e) {

			logger.error("unable to find expenses ", e);
			return null;
		} finally {
			if (sess != null)
				sess.close();
		}

	}

	public static Integer countExpenses(SearchExpenseForm form, Agent user) {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			Criteria crit = getSearchCriteria(form, user, sess);
			crit.setProjection(Projections.rowCount());
			return (Integer) crit.uniqueResult();
		} catch (Exception e) {

			logger.error("unable to find expenses ", e);
			return null;
		} finally {
			if (sess != null)
				sess.close();
		}
	}

	private static Criteria getSearchCriteria(SearchExpenseForm sf, Agent user, Session sess) {
		Criteria crit = sess.createCriteria(ExpensePayout.class).createAlias("agent", "a").add(
				Restrictions.eq("a.companycode_ID", user.getCompanycode_ID()).ignoreCase());
		
		crit.createAlias("status", "st");

		if (sf.getApproveEndDate() != null && sf.getApproveEndDate().trim().length() > 0) {
			Date aed = DateUtils.convertToDate(sf.getApproveEndDate(), sf.getDateFormat(), null, TimeZone
					.getTimeZone(sf.getTimeZone()));
			if (aed != null)
				crit.add(Restrictions.le("approval_date", aed));
		}
		if (sf.getApproveStartDate() != null && sf.getApproveStartDate().trim().length() > 0) {
			Date d = DateUtils.convertToDate(sf.getApproveStartDate(), sf.getDateFormat(), null, TimeZone
					.getTimeZone(sf.getTimeZone()));
			if (d != null)
				crit.add(Restrictions.ge("approval_date", d));
		}
		if (sf.getCreateAgent() != null && sf.getCreateAgent().trim().length() > 0) {
			crit.add(Restrictions.like("a.username", sf.getCreateAgent()));
		}
		if (sf.getCreateEndDate() != null && sf.getCreateEndDate().trim().length() > 0) {
			Date d = DateUtils.convertToDate(sf.getCreateEndDate(), sf.getDateFormat(), null, TimeZone.getTimeZone(sf
					.getTimeZone()));
			if (d != null)
				crit.add(Restrictions.le("createdate", d));
		}
		if (sf.getCreateStartDate() != null && sf.getCreateStartDate().trim().length() > 0) {
			Date d = DateUtils.convertToDate(sf.getCreateStartDate(), sf.getDateFormat(), null, TimeZone.getTimeZone(sf
					.getTimeZone()));
			if (d != null)
				crit.add(Restrictions.ge("createdate", d));
		}
		if (sf.getExpenseTypeId() > 0) {
			crit.createAlias("expensetype", "et").add(Restrictions.eq("et.expensetype_ID", sf.getExpenseTypeId()));
		}
		if ((sf.getIncident_id() != null && sf.getIncident_id().trim().length() > 0)
				|| (sf.getStationCode() > 0)) {

			crit.createAlias("incident", "inc");
			if (sf.getIncident_id() != null && sf.getIncident_id().trim().length() > 0) {
				crit.add(Restrictions.like("inc.incident_ID", sf.getIncident_id()));
			}
			if (sf.getStationCode() > 0) {
				crit.createAlias("inc.stationassigned", "sa");
				crit.add(Restrictions.like("sa.station_ID", sf.getStationCode()));
			}
		}

		if (sf.getMaximumAmount() > 0.001) {
			crit.add(Restrictions.disjunction().add(
					Restrictions
							.and(Restrictions.gt("checkamt", 0.00D), Restrictions.le("checkamt", sf.getMaximumAmount())))
					.add(
							Restrictions.and(Restrictions.gt("voucheramt", 0.00D), Restrictions.le("voucheramt", sf
									.getMaximumAmount()))).add(
							Restrictions.and(Restrictions.gt("mileageamt", 0), Restrictions.le("mileageamt", ((Double)sf
									.getMaximumAmount()).intValue()))));
		}
		if (sf.getMinimumAmount() > 0.001) {
			crit.add(Restrictions.disjunction().add(
					Restrictions
							.and(Restrictions.gt("checkamt", 0.00D), Restrictions.ge("checkamt", sf.getMinimumAmount())))
					.add(
							Restrictions.and(Restrictions.gt("voucheramt", 0.00D), Restrictions.ge("voucheramt", sf
									.getMinimumAmount()))).add(
							Restrictions.and(Restrictions.gt("mileageamt", 0), Restrictions.ge("mileageamt", new Double(sf
									.getMinimumAmount()).intValue()))));
		}
		if (sf.getStatusId() > 0) {
			crit.add(Restrictions.eq("st.status_ID", sf.getStatusId()));
		}
		return crit;

	}

	public static boolean approveExpense(String payout_id, Agent user) {
		Session sess = HibernateWrapper.getSession().openSession();
		Transaction tx1 = sess.beginTransaction();
		try {
			ExpensePayout ep = (ExpensePayout) sess.get(ExpensePayout.class, Integer.parseInt(payout_id));
			Status st = new Status();
			st.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED);
			Comment comment = new Comment(user);
			comment.setContent(TracerUtils.getResourcePropertyText("expense.comment.approved", user));
			ep.getComments().add(comment);
			ep.setStatus(st);
			ep.setApproval_date(new Date());
			sess.update(ep);
			tx1.commit();
			return true;
		} catch (Exception e) {
			logger.error("unable to approve expenses " + payout_id);
			return false;
		} finally {
			if (sess != null) sess.close();
		}
		
	}
	
	public static boolean denyExpense(String payout_id, Agent user) {
		Session sess = HibernateWrapper.getSession().openSession();
		Transaction tx1 = sess.beginTransaction();
		try {
			ExpensePayout ep = (ExpensePayout) sess.get(ExpensePayout.class, Integer.parseInt(payout_id));
			Status st = new Status();
			st.setStatus_ID(TracingConstants.EXPENSEPAYOUT_STATUS_DENIED);
			Comment comment = new Comment(user);
			comment.setContent(TracerUtils.getResourcePropertyText("expense.comment.denied", user));
			ep.getComments().add(comment);
			ep.setStatus(st);
			sess.update(ep);
			tx1.commit();
			return true;
		} catch (Exception e) {
			logger.error("unable to deny expense" + payout_id);
			return false;
		} finally {
			if (sess != null) sess.close();
		}
		
	}

}
