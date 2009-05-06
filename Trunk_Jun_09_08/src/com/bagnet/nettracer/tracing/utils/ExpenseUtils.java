package com.bagnet.nettracer.tracing.utils;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.forms.CreatedInterimExpenseRequestForm;
import com.bagnet.nettracer.tracing.forms.ExpensePayoutForm;
import com.bagnet.nettracer.tracing.forms.InterimExpenseRequestForm;

/**
 * @author Ankur Gupta
 * 
 * This class provides helper routines to obtain admin related information from
 * the database.
 *  
 */
public class ExpenseUtils {

	private static Logger logger = Logger.getLogger(ExpenseUtils.class);
	
	public static List getPendingInterimExpenses(boolean count, String companyCode_ID,
			InterimExpenseRequestForm form, String sort, int rowsperpage, int currpage) {
		return getPendingInterimExpenses(count, companyCode_ID, form, sort, rowsperpage, currpage, false);
	}

	public static List getPendingInterimExpenses(boolean count, String companyCode_ID,
			InterimExpenseRequestForm form, String sort, int rowsperpage, int currpage, boolean dirtyRead) {
		Session sess = null;
		try {
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}

			String sql = "";
			if (count) sql += "select count(*) from "
					+ "com.bagnet.nettracer.tracing.db.ExpensePayout expense where 1=1 ";
			else sql += "select expense from "
					+ "com.bagnet.nettracer.tracing.db.ExpensePayout expense where 1=1 ";

			sql += " and expense.expenselocation.company.companyCode_ID = :company";

			if (!form.getPayout_status().equals("-1")) {
				sql += " and expense.status.status_ID = :status_ID";
			} else {
				sql += " and expense.status.status_ID <> :status_ID";
			}

			//check if incident id is enterd.
			if (form != null && form.getIncident_num() != null && form.getIncident_num().length() > 0) {
				sql += " and expense.incident.incident_ID like '" + form.getIncident_num() + "'";
			}
			if (!count) sql += " order by expense.incident.incident_ID asc ";

			Query q = sess.createQuery(sql);
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}
			q.setString("company", companyCode_ID);
			q.setInteger("status_ID", Integer.parseInt(form.getPayout_status()));
			return q.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	public static List getCreateInterimExpenses(boolean count, int station_ID,
			CreatedInterimExpenseRequestForm form, String sort, int rowsperpage, int currpage) {
		return getCreateInterimExpenses(count, station_ID, form, sort, rowsperpage, currpage, false);
	}

	public static List getCreateInterimExpenses(boolean count, int station_ID,
			CreatedInterimExpenseRequestForm form, String sort, int rowsperpage, int currpage, boolean dirtyRead) {
		Session sess = null;
		try {
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}
			String sql = "";
			if (count) sql += "select count(*) from "
					+ "com.bagnet.nettracer.tracing.db.ExpensePayout expense where 1=1 ";
			else sql += "select expense from "
					+ "com.bagnet.nettracer.tracing.db.ExpensePayout expense where 1=1 ";

			sql += " and expense.expenselocation.station_ID = :station_ID";

			if (form != null && form.getExpense_status() != null
					&& !form.getExpense_status().equals("-1")) {
				sql += " and expense.status.status_ID = :status_ID";
			} else {
				sql += " and expense.status.status_ID <> :status_ID";
			}

			//check if incident id is enterd.
			if (form != null && form.getIncident_num() != null && form.getIncident_num().length() > 0) {
				sql += " and expense.incident.incident_ID like '" + form.getIncident_num() + "'";
			}

			if (!count) sql += " order by expense.incident.incident_ID asc ";

			Query q = sess.createQuery(sql);
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			q.setInteger("station_ID", station_ID);
			q.setInteger("status_ID", Integer.parseInt(form.getExpense_status()));

			return q.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	public static ExpensePayout getExpensePayout(String payout_ID) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			String sql = "select expense from "
					+ "com.bagnet.nettracer.tracing.db.ExpensePayout expense where 1=1 ";
			sql += " and expense.expensepayout_ID = :payout_ID";

			Query q = sess.createQuery(sql);
			q.setInteger("payout_ID", Integer.parseInt(payout_ID));
			return (ExpensePayout) q.list().get(0);
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
}