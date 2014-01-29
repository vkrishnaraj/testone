package com.bagnet.nettracer.tracing.bmo;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
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
	public static Company_specific_irregularity_code getLossCode(int loss_code, int report_type, Company company) {
		Session sess = null;
		try {
			if(report_type == TracingConstants.OHD) {
				report_type = TracingConstants.LOST_DELAY;
			}
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Company_specific_irregularity_code.class).add(Restrictions.eq("loss_code", new Integer(loss_code))).add(
					Restrictions.eq("report_type", new Integer(report_type))).add(Restrictions.eq("company", company));
			@SuppressWarnings("unchecked")
			List<Company_specific_irregularity_code> list = cri.list();
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
	public static List<Company_specific_irregularity_code> getLossCodes(List<Integer> loss_codes, int report_type, String companyCode) {
		Session sess = null;
		try {
			if(report_type == TracingConstants.OHD) {
				report_type = TracingConstants.LOST_DELAY;
			}
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Company_specific_irregularity_code.class).add(Restrictions.in("loss_code", loss_codes)).add(
					Restrictions.eq("report_type", new Integer(report_type)));
			cri.createCriteria("company").add(Restrictions.eq("companyCode_ID", companyCode));
			@SuppressWarnings("unchecked")
			List<Company_specific_irregularity_code> list = cri.list();
			if (list != null && list.size() > 0){
				return list;
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
			Criteria cri = sess.createCriteria(Company_specific_irregularity_code.class).add(Restrictions.eq("code_id", new Integer(code_id)));
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
	@SuppressWarnings("unchecked")
	public static List<Company_specific_irregularity_code> getCodes(String companyCode, String report_type, DynaValidatorForm dForm, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Company_specific_irregularity_code.class);
			cri.createCriteria("company").add(Restrictions.eq("companyCode_ID", companyCode));
	
			if (report_type.length() > 0) {
				cri.add(Restrictions.eq("report_type", new Integer(report_type)));
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
				cri.add(Restrictions.eq("loss_code", new Integer(intCode)));
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
	@SuppressWarnings("unchecked")
	public static List<IATA_irregularity_code> getIATACodes(int rowsperpage, int currpage) {
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
	
	public static List<Company_specific_irregularity_code> getCompanyCodes(String companyCode, int report_type, boolean limit, Agent user) {
		return getCompanyCodes(companyCode, report_type, limit, user, false, false);
	}
	
	public static List<Company_specific_irregularity_code> getCompanyCodes(String companyCode, int report_type, boolean limit, Agent user, boolean checkLLC) {
		return getCompanyCodes(companyCode, report_type, limit, user, checkLLC, false);
	}
	
	/**
	 * Get only the active list of loss codes.
	 * @return the list of active company codes
	 */
	
	@SuppressWarnings("unchecked")
	public static List<Company_specific_irregularity_code> getCompanyCodes(String companyCode, int report_type, boolean limit, Agent user, boolean checkLLC, boolean active) {
		Session sess = null;
		boolean limitQuery = false;
		
		if (limit && user != null && !(checkLLC && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_LIMITED_LOSS_CODES, user))) {
			if (UserPermissions.hasLimitedSavePermissionByType(user, report_type)) {
				limitQuery = true;
			}
		}
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Company_specific_irregularity_code.class);
			cri.createCriteria("company").add(Restrictions.eq("companyCode_ID", companyCode));
			if (report_type > 0) {
				if(report_type == TracingConstants.OHD){
					cri.add(Restrictions.eq("report_type", new Integer(TracingConstants.LOST_DELAY)));
				}
				else {
					cri.add(Restrictions.eq("report_type", new Integer(report_type)));
				}
			}
			
			if (limitQuery) {
				cri.add(Restrictions.eq("show_to_limited_users", true));
			}
			
			if (active) {
				cri.add(Restrictions.eq("active", true));
			}
			
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

	public static Company_specific_irregularity_code getCode(int code_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			return (Company_specific_irregularity_code) sess.get(Company_specific_irregularity_code.class, code_id);
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
