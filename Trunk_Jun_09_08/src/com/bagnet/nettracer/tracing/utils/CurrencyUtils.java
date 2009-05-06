package com.bagnet.nettracer.tracing.utils;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Currency;

/**
 * @author Ankur Gupta
 * 
 * This class provides helper routines to obtain admin related information from
 * the database.
 *  
 */
public class CurrencyUtils {

	private static Logger logger = Logger.getLogger(CurrencyUtils.class);

	/**
	 * Get currency associated with this id
	 * 
	 * @param companyCode
	 * @param locale
	 * @param rowsperpage
	 * @param currpage
	 * @return shifts within a company
	 */
	public static Currency getCurrency(String currency_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Currency.class).add(
					Expression.eq("currency_ID", currency_id));

			return (Currency) cri.list().get(0);
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