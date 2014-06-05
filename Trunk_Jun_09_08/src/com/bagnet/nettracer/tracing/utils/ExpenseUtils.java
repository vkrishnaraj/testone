package com.bagnet.nettracer.tracing.utils;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.forms.CreatedInterimExpenseRequestForm;
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
	
	@SuppressWarnings("rawtypes")
	public static List getPendingInterimExpenses(boolean count, String companyCode_ID,
			InterimExpenseRequestForm form, String sort, int rowsperpage, int currpage) {
		return getPendingInterimExpenses(count, companyCode_ID, form, sort, rowsperpage, currpage, false);
	}

	@SuppressWarnings("rawtypes")
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
	
	@SuppressWarnings("rawtypes")
	public static List getCreateInterimExpenses(boolean count, int station_ID,
			CreatedInterimExpenseRequestForm form, String sort, int rowsperpage, int currpage) {
		return getCreateInterimExpenses(count, station_ID, form, sort, rowsperpage, currpage, false);
	}

	@SuppressWarnings("rawtypes")
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

	@SuppressWarnings("rawtypes")
	public static List getPaymentApprovalInterimExpenses(boolean count, int station_ID,
			CreatedInterimExpenseRequestForm form, String sort, int rowsperpage, int currpage) {
		form.setExpense_status(TracingConstants.EXPENSEPAYOUT_STATUS_PENDING+"");
		return getCreateInterimExpenses(count, station_ID, form, sort, rowsperpage, currpage, false);
	}
	
	public static boolean hasActivity(ExpensePayout ep) {
		if (ep == null) return false;

		boolean hasTask = false;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(IncidentActivity.class, "ia");
			criteria.add(Restrictions.eq("ia.expensePayout",ep));
			criteria.setProjection(Projections.countDistinct("ia.id"));
			Long taskCount = (Long) criteria.uniqueResult();
			if (taskCount != null && taskCount.longValue() != 0) {
				hasTask = true;
			}
		} catch (Exception e) {
			logger.error("Could not determine if expense Payout with id: " + ep.getExpensepayout_ID() + " has an incident activity.", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return hasTask;
	}
	/**
	 * This method is implemented to obtain a generic expense payment amount regardless of its pay type.
	 */
	public static double getPaymentAmount(ExpensePayout ep) {
		double amount = 0d;
		if (ep.getPaytype().startsWith("VOUCH")){
			amount = ep.getVoucheramt();
		}else if (ep.getPaytype().startsWith("MIL")){
			amount = ep.getMileageamt();
		}else{//all the rest paytypest
			amount = ep.getCheckamt();
		}
		return amount;
	}
	
	/**
	 * This method is implemented to initialize a String array object.
	 */
	public static String[] initializeStringArray(int size, String aValue) {
		String[] aArray = new String[size];
		for (int i = 0; i < aArray.length; i++){
			aArray[i] = aValue;
		}
		return aArray;
	}

	/**
	 * This method is implemented to initialize a double array object.
	 */
	public static double[] initializeArray(int size, double aValue) {
		double[] aArray = new double[size];
		for (int i = 0; i < aArray.length; i++){
			aArray[i] = aValue;
		}
		return aArray;
	}
	
	/**
	 * This method provides utility routine to populate expense payout sub total display detail for expense payout section.
	 * @param expenselist - ExpensePayout object which includes all expense payment information;
	 * @param expenseSummary - Map object which includes all subtotal payment information accumulated from previous loops;
	 * @param amount - double value which corresponds to a payment amount; 
	 * @return Map object which captures payment sub total information in the expense payment section table;
	 */
	public static Map<String, double[]> populateExpensePaySubtotals(ExpensePayout expenselist, Map<String, double[]> expenseSummary, double amount) {
		int size = 5;
		double aValue = 0d;
		double[] summaryArray = initializeArray(size, aValue);
 		if (expenselist.getStatus().getStatus_ID() == TracingConstants.EXPENSEPAYOUT_STATUS_PENDING){
 			summaryArray[0] = amount;		
 		}
		if (expenselist.getStatus().getStatus_ID() == TracingConstants.EXPENSEPAYOUT_STATUS_PAID){
 			summaryArray[1] = amount;		
 		}
		if (expenselist.getStatus().getStatus_ID() == TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED){
 			summaryArray[2] = amount;		
 		}
		if (expenselist.getStatus().getStatus_ID() == TracingConstants.EXPENSEPAYOUT_STATUS_DENIED){
 			summaryArray[3] = amount;		
 		}
		if (expenselist.getStatus().getStatus_ID() == TracingConstants.EXPENSEPAYOUT_STATUS_CANCEL){
 			summaryArray[4] = amount;		
 		}
		if (expenseSummary.containsKey(expenselist.getPaytype()+ "-" + expenselist.getCurrency_ID())){
			double[] x = expenseSummary.get(expenselist.getPaytype()+ "-" + expenselist.getCurrency_ID());
			for (int j = 0; j< x.length; j++){
				summaryArray[j] = summaryArray[j] +  x[j];
			}
			expenseSummary.remove(expenselist.getPaytype()+ "-" + expenselist.getCurrency_ID());
		}
		expenseSummary.put(expenselist.getPaytype()+ "-" + expenselist.getCurrency_ID(), summaryArray);
		return expenseSummary;
	}
	
	
	/**
	 * This method provides utility routine to populate expense payout grand total display detail for expense payout summary.
	 * It loops through a Map temp object which has all the payment status and subtotals. It then calculkate teh grand totals.
	 * It needs to determine what's the currency in grand total lines (the logic is to display a string of "Multiple Currencies"
	 * if there are any none zero multiple currencies for the same payment status). Finally, it determine what to display for
	 * grand total line in the expense summary table; 
	 * @param expenseSummary - Map object which includes all subtotal payment information;
	 * @param multipleCurrencies - String object which needs to display if subtotal amount is in different currencies. This is 
	 * coming from properties file for internationalization implementation purpose in mind;
	 * @return String[] object which is for grand total line display in the expense summary table;
	 */
	public static String[] populateExpensePayGrandTotals(Map<String, double[]> expenseSummary, String multipleCurrencies) {
		int payTypeCount = 5;
		double[] grandTotal = ExpenseUtils.initializeArray(payTypeCount, 0d);
		String[] grandTotalDisplay = ExpenseUtils.initializeStringArray(payTypeCount, "0.00");
		Set<String> e = expenseSummary.keySet();
		Iterator it = e.iterator();
		String pendingGrandTotalCurrency = "";
		String paidGrandTotalCurrency = "";
		String approvedGrandTotalCurrency = "";
		String deniedGrandTotalCurrency= "";
		String cancelledGrandTotalCurrency= "";
		
		int pendingGrandTotalInt = 0;
		int paidGrandTotalInt = 0;
		int approvedGrandTotalInt = 0;
		int deniedGrandTotalInt = 0;
		int cancelledGrandTotalInt = 0;
		Map<String, String> grands = new HashMap<String, String>();
		//calculate grand amount
	    while ( it.hasNext()){
	    	 String key = (String)it.next();
	    	 double[] values = expenseSummary.get(key);
	    	 String currentCurrency = key.substring(key.indexOf("-") + 1, key.indexOf("-") + 4);
	 		for (int k = 0; k < values.length; k++){
	 	 		if (values[k] > 0.0 ){
	 	 			switch (k) {
	 	 		    case 0:  
	 	 		    	grands.put(key + "=" + TracingConstants.EXPENSEPAYOUT_STATUS_PENDING, String.valueOf(values[k]));
	 	 		    	pendingGrandTotalCurrency = currentCurrency;
	 	 		        break;
	 	 		    case 1: 
	 	 		    	grands.put(key + "=" + TracingConstants.EXPENSEPAYOUT_STATUS_PAID,  String.valueOf(values[k]));
	 	 		    	paidGrandTotalCurrency = currentCurrency;
	 	 		        break;
	 	 		    case 2: ;
		 		    	grands.put(key + "=" + TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED,  String.valueOf(values[k]));
		 		    	approvedGrandTotalCurrency = currentCurrency;
	 	 		        break;
	 	 		    case 3: ;
		 		    	grands.put(key + "=" + TracingConstants.EXPENSEPAYOUT_STATUS_DENIED,  String.valueOf(values[k]));
		 		    	deniedGrandTotalCurrency = currentCurrency;
	 	 		        break;
	 	 		    case 4: ;
		 		    	grands.put(key + "=" + TracingConstants.EXPENSEPAYOUT_STATUS_CANCEL,  String.valueOf(values[k]));
		 		    	cancelledGrandTotalCurrency = currentCurrency;
	 	 		        break;
	 	 		    default: 
	 	 		    	break; 	 			
	 	 			}
	 	 		}
	 		}
			for (int i = 0; i < grandTotal.length; i++){
				if (!key.startsWith("MILE")){
					grandTotal[i] += values[i];
				}
			}
		}//end while
		Set<String> eGrand = grands.keySet();
		Iterator itGrand = eGrand.iterator();
		boolean same = true;
		String pendingLastCu = "";
		String paidLastCu = "";
		String approvedLastCu = "";
		String deniedLastCu = "";
		String cancelledLastCu = "";
		//determine what to display for the grand total line currency
	    while ( itGrand.hasNext()){
	    	 String keyGrand = (String)itGrand.next();
	    	 String currentCurrencyGrand = keyGrand.substring(keyGrand.indexOf("-") + 1, keyGrand.indexOf("="));
	 		if ((pendingLastCu == null || pendingLastCu.length() == 0) && keyGrand.indexOf(String.valueOf(TracingConstants.EXPENSEPAYOUT_STATUS_PENDING)) > 0){
	 			pendingLastCu = currentCurrencyGrand;
	 		}
	 		if ((paidLastCu == null || paidLastCu.length() == 0) && keyGrand.indexOf(String.valueOf(TracingConstants.EXPENSEPAYOUT_STATUS_PAID)) > 0){
	 			paidLastCu = currentCurrencyGrand;
	 		}
	 		if ((approvedLastCu == null || approvedLastCu.length() == 0) && keyGrand.indexOf(String.valueOf(TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED)) > 0){
	 			approvedLastCu = currentCurrencyGrand;
	 		}
	 		if ((deniedLastCu == null || deniedLastCu.length() == 0) && keyGrand.indexOf(String.valueOf(TracingConstants.EXPENSEPAYOUT_STATUS_DENIED)) > 0){
	 			deniedLastCu = currentCurrencyGrand;
	 		}
	 		if ((cancelledLastCu == null || cancelledLastCu.length() == 0) && keyGrand.indexOf(String.valueOf(TracingConstants.EXPENSEPAYOUT_STATUS_CANCEL)) > 0){
	 			cancelledLastCu = currentCurrencyGrand;
	 		}
	 		if (keyGrand.indexOf(String.valueOf(TracingConstants.EXPENSEPAYOUT_STATUS_PENDING)) > 0 && !pendingLastCu.equalsIgnoreCase(currentCurrencyGrand)){
	 			pendingGrandTotalInt++;
	 		}else if (keyGrand.indexOf(String.valueOf(TracingConstants.EXPENSEPAYOUT_STATUS_PAID)) > 0 && !paidLastCu.equalsIgnoreCase(currentCurrencyGrand)){
	 			paidGrandTotalInt++;
	 		}else if (keyGrand.indexOf(String.valueOf(TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED)) > 0 && !approvedLastCu.equalsIgnoreCase(currentCurrencyGrand)){
	 			approvedGrandTotalInt++;
	 		}else if (keyGrand.indexOf(String.valueOf(TracingConstants.EXPENSEPAYOUT_STATUS_DENIED)) > 0 && !deniedLastCu.equalsIgnoreCase(currentCurrencyGrand)){
	 			deniedGrandTotalInt++;
	 		}else if (keyGrand.indexOf(String.valueOf(TracingConstants.EXPENSEPAYOUT_STATUS_CANCEL)) > 0 && !cancelledLastCu.equalsIgnoreCase(currentCurrencyGrand)){
	 			cancelledGrandTotalInt++;
	 		}
	    }
	    //finally, store what to display (including the payment amount and its currency) in a object
	    if (pendingGrandTotalInt > 0 ){
	    	grandTotalDisplay[0] = multipleCurrencies;
	    }else if (grandTotal[0] > 0) {
	    	grandTotalDisplay[0] = String.valueOf(TracingConstants.DECIMALFORMAT.format(grandTotal[0])) + " &nbsp;" + pendingGrandTotalCurrency;
	    }else{
	    	grandTotalDisplay[0] = String.valueOf(TracingConstants.DECIMALFORMAT.format(grandTotal[0]));
	    }
	    if (paidGrandTotalInt > 0 ){
	    	grandTotalDisplay[1] = multipleCurrencies;
	    }else if (grandTotal[1] > 0){
	    	grandTotalDisplay[1] = String.valueOf(TracingConstants.DECIMALFORMAT.format(grandTotal[1])) + " &nbsp;" + paidGrandTotalCurrency;
	    }else{
	    	grandTotalDisplay[1] = String.valueOf(TracingConstants.DECIMALFORMAT.format(grandTotal[1]));
	    }
	    if (approvedGrandTotalInt > 0 ){
	    	grandTotalDisplay[2] = multipleCurrencies;
	    }else if (grandTotal[2] > 0){
	    	grandTotalDisplay[2] = String.valueOf(TracingConstants.DECIMALFORMAT.format(grandTotal[2])) + " &nbsp;" + approvedGrandTotalCurrency;
	    }else{
	    	grandTotalDisplay[2] = String.valueOf(TracingConstants.DECIMALFORMAT.format(grandTotal[2]));
	    }
	    if (deniedGrandTotalInt > 0 ){
	    	grandTotalDisplay[3] = multipleCurrencies;
	    }else if (grandTotal[3] > 0){
	    	grandTotalDisplay[3] = String.valueOf(TracingConstants.DECIMALFORMAT.format(grandTotal[3])) + " &nbsp;" + deniedGrandTotalCurrency;
	    }else{
	    	grandTotalDisplay[3] = String.valueOf(TracingConstants.DECIMALFORMAT.format(grandTotal[3]));
	    }
	    if (cancelledGrandTotalInt > 0 ){
	    	grandTotalDisplay[4] = multipleCurrencies;
	    }else if (grandTotal[4] > 0){
	    	grandTotalDisplay[4] = String.valueOf(TracingConstants.DECIMALFORMAT.format(grandTotal[4])) + " &nbsp;" + cancelledGrandTotalCurrency;
	    }else{
	    	grandTotalDisplay[4] = String.valueOf(TracingConstants.DECIMALFORMAT.format(grandTotal[4]));
	    }
		return grandTotalDisplay;
	}
}