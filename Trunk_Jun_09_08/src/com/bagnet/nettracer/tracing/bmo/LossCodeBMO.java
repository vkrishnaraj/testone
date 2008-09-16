package com.bagnet.nettracer.tracing.bmo;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code;
import com.bagnet.nettracer.tracing.db.IATA_irregularity_code;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class LossCodeBMO {
	
	private static Logger logger = Logger.getLogger(LossCodeBMO.class);

	/**
	 * Get the loss_code based on the passed in id
	 * 
	 * @param loss_code
	 * @return the irregularity code null if exception or not found
	 */
	public static Company_specific_irregularity_code getLossCode(int loss_code, int report_type, String locale, Company company) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Company_specific_irregularity_code.class).add(Expression.eq("loss_code", new Integer(loss_code))).add(
					Expression.eq("report_type", new Integer(report_type))).add(Expression.eq("locale", locale)).add(Expression.eq("company", company));
			List list = cri.list();
			if (list != null && list.size() > 0){
				return (Company_specific_irregularity_code) cri.list().get(0);
			}
			return null;
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

	/**
	 * Get the loss_code based on the passed in id
	 * 
	 * @param loss_code
	 * @return the irregularity code null if exception or not found
	 */
	public static Company_specific_irregularity_code getCode(String code_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Company_specific_irregularity_code.class).add(Expression.eq("code_id", new Integer(code_id)));
			return (Company_specific_irregularity_code) cri.list().get(0);
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

	/**
	 * Retrieve all the irregularity codes associated with a given company
	 * 
	 * @param companyCode
	 * @param rowsperpage
	 * @param currpage
	 * @return list of codes null in case of exception
	 */
	public static List getCodes(String companyCode, String report_type, DynaValidatorForm dForm, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Company_specific_irregularity_code.class);
			cri.createCriteria("company").add(Expression.eq("companyCode_ID", companyCode));
	
			if (report_type.length() > 0) {
				cri.add(Expression.eq("report_type", new Integer(report_type)));
			}
	
			String code = null;
			int intCode = -1;
	
			if (dForm != null) {
				code = (String) dForm.get("codeSearchName");
	
				if (code != null && code.length() > 0) {
					try {
						intCode = Integer.parseInt(code);
					} catch (Exception e) {
	
					}
				}
			}
	
			if (intCode != -1) {
				cri.add(Expression.eq("loss_code", new Integer(intCode)));
			}
	
			cri.addOrder(Order.asc("loss_code"));
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				cri.setFirstResult(startnum);
				cri.setMaxResults(rowsperpage);
			}
			return cri.list();
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

	/**
	 * Retrieve all the iata loss codes
	 * 
	 * @param companyCode
	 * @param rowsperpage
	 * @param currpage
	 * @return list of codes null in case of exception
	 */
	public static List getIATACodes(int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(IATA_irregularity_code.class);
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				cri.setFirstResult(startnum);
				cri.setMaxResults(rowsperpage);
			}
			return cri.list();
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

	/**
	 * Get the company specific irregularity codes based on locale
	 * 
	 * @param companyCode
	 * @param locale
	 * @return list of codes null in case of exception
	 */
	public static List getLocaleCompanyCodes(String companyCode, int report_type, String locale, boolean limit, Agent user) {
		Session sess = null;
		boolean limitQuery = false;
		
		if (limit && user != null) {
			if (UserPermissions.hasLimitedSavePermissionByType(user, report_type)) {
				limitQuery = true;
			}
		}
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Company_specific_irregularity_code.class);
			cri.createCriteria("company").add(Expression.eq("companyCode_ID", companyCode));
			if (report_type > 0) {
				cri.add(Expression.eq("report_type", new Integer(report_type)));
			}
			
			if (limitQuery) {
				cri.add(Expression.eq("show_to_limited_users", true));
			}
			cri.add(Expression.eq("locale", locale));
			cri.addOrder(Order.asc("loss_code"));
			return cri.list();
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
