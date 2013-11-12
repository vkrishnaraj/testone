package com.bagnet.nettracer.tracing.bmo;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.GeneralDepreciationRules;
import com.bagnet.nettracer.tracing.db.WTCompany;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class CompanyBMO {

	private static Logger logger = Logger.getLogger(CompanyBMO.class);
	
	/**
	 * Get a company.
	 * 
	 * @param companyCode
	 * @return
	 */
	public static Company getCompany(String companyCode) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Company.class).add(
					Expression.eq("companyCode_ID", companyCode));
			if (cri.list().size() > 0)
				return (Company) cri.list().get(0);
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Creates a company with the given code in the database. If the company
	 * already exists in the database a new one will not be created.
	 * 
	 * @param companyCode Two letter airline code.
	 * @param session The session to update (if applicable)
	 * @return Returns the company object that was created.
	 */
	public static Company createCompany(String companyCode, HttpSession session) {
		Company retVal = getCompany(companyCode);
				
		// If the company does not exist, create it.
		if (companyCode != null && retVal == null) {
			// create company first
			retVal = new Company();
			retVal.setCompanyCode_ID(companyCode.toUpperCase());
			retVal.setCompanydesc(companyCode.toUpperCase());
			
			Company_Specific_Variable var = new Company_Specific_Variable();
			var.setCompanyCode_ID(retVal.getCompanyCode_ID());
			
			retVal.setVariable(var);
			
			try {
				HibernateUtils.saveCompany(retVal, null, false);
				
				if (session != null) {
					TracerUtils.populateCompanyLists(session);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Unable to create company: " + e.getMessage());
			}
		}
		return retVal;
	}

	/**
	 * GetWTCarriers - gets the list of full WTCompany objects based on Company Code
	 * @param companycode
	 * @return list of WTCompany objects
	 */
	public static List<WTCompany> getWTCarriers(String companycode){
		String sql = "from com.bagnet.nettracer.tracing.db.WTCompany wtc where wtc.company_id=:companycode and wtc.wtCompanyCode!='DA' and wtc.wtCompanyCode!='OW' order by wtc.wtCompanyCode";
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			q.setParameter("companycode", companycode);
			List<WTCompany> ilist= (List<WTCompany>) q.list();
			return ilist;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static GeneralDepreciationRules getDeprecRules(String companyCode) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(GeneralDepreciationRules.class).add(
					Expression.eq("companyCode", companyCode));
			if (cri.list().size() > 0)
				return (GeneralDepreciationRules) cri.list().get(0);
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Get status message in company_specific_variable table
	 * 
	 * @param companycode 
	 * @return Returns the status message string.
	 */
	public static String getTaskManagerStatusMessage(String companycode){

		String sql = "select status_message from company_specific_variable where companycode_ID = :companycode";
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query query = sess.createSQLQuery(sql);
			query.setParameter("companycode", companycode);
			String result = (String) query.uniqueResult();
			return result != null ? result : "";
		} catch (Exception e) {
			logger.error("unable to get status message for company_specific_variable: " + e);
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close hibernate session: " + e);
				}
			}
		}

	}	
}
