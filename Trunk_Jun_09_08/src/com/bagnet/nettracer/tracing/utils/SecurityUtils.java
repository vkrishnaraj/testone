/*
 * Created on Dec 23, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Agent_Logger;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Webservice_Session;
import com.bagnet.nettracer.tracing.db.audit.Audit_Agent;
import com.bagnet.nettracer.tracing.utils.audit.AuditAgentUtils;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SecurityUtils {

	/**
	 *  
	 */
	public SecurityUtils() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * logintype == 0: application login
	 * logintype == 1: webservice login
	 * anything else for logintype is default to application login
	 * 
	 * @param username
	 * @param password
	 * @param companyCode
	 * @param logintype
	 * @param errors
	 * @return
	 */
	public static Agent authUser(String username, String password, String companyCode, int logintype,
			ActionMessages errors) {
		Session sess = null;
		Agent agent = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria criteria = sess.createCriteria(Agent.class);
			criteria.add(Expression.eq("username", username));
			criteria.add(Expression.eq("password", TEA.encryptTEA(password)));
			criteria.add(Expression.eq("companycode_ID", companyCode));

			List results = criteria.list();
			if (results == null || results.size() < 1) {

				// Add 1 to # of times login failed.
				Criteria criteria2 = sess.createCriteria(Agent.class);
				criteria2.add(Expression.eq("username", username));
				criteria2.add(Expression.eq("companycode_ID", companyCode));
				List results2 = criteria2.list();
				if (results2.size() == 1) {
					agent = (Agent)results2.get(0);
					int maxFailedAttempts = AdminUtils.getCompVariable(companyCode).getMax_failed_logins();
					
					if (maxFailedAttempts > 0) {
						int failedAttempts = agent.getFailed_logins() + 1;
						agent.setFailed_logins(failedAttempts);
						
						if (failedAttempts >= maxFailedAttempts && !agent.isAccount_locked()) {
							agent.setAccount_locked(true);
							HibernateUtils.save(agent);
							
							if (AdminUtils.getCompVariable(companyCode).getAudit_agent() == 1) {
								Audit_Agent audit_agent = AuditAgentUtils.getAuditAgent(agent, agent);
								if (audit_agent != null) {
									HibernateUtils.saveNew(audit_agent);
								}
							}		
						} else {
							HibernateUtils.save(agent);
						}
					}
					
				}
				if (agent != null) {
					if (agent.isAccount_locked()) {
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.user.lockedout"));
					} else {
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.password.mismatch"));
					}
				} else {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.password.mismatch"));
				}
			} else {
				agent = (Agent) results.get(0);
				if (!agent.isActive()) {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.user.inactive"));
				}
				
				if (logintype == 1 && agent.isWs_enabled() != true) {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.user.ws_disabled"));
				} else if (agent.isWeb_enabled() != true) {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.user.web_disabled"));
				}
				if (agent.isAccount_locked()) {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.user.lockedout"));
				} else {
					agent.setFailed_logins(0);
					HibernateUtils.save(agent);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.database.connection"));
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return agent;

	}

	public static void updateAgentLogin(Agent agent, Date log_off_time) {
		Session sess = null;
		Transaction t = null;
		if (agent == null) return;
		try {
			sess = HibernateWrapper.getSession().openSession();

			// update agent table
			t = sess.beginTransaction();
			sess.saveOrUpdate(agent);
			t.commit();

			// update agent_logger table

			// find previous log in record first if this is a logoff
			Agent_Logger al = null;

			if (log_off_time != null) {

				Criteria criteria = sess.createCriteria(Agent_Logger.class);
				criteria.add(Expression.eq("agent_ID", new Integer(agent.getAgent_ID())));
				criteria.add(Expression.eq("log_in_time", agent.getLast_logged_on()));
				List results = criteria.list();

				if (results != null && results.size() > 0) al = (Agent_Logger) results.get(0);
			}

			t = sess.beginTransaction();
			if (al == null) al = new Agent_Logger();
			al.setAgent_ID(agent.getAgent_ID());
			al.setCompanycode_ID(agent.getStation().getCompany().getCompanyCode_ID());
			al.setLog_in_time(agent.getLast_logged_on());
			al.setLog_off_time(log_off_time);
			sess.saveOrUpdate(al);
			t.commit();

		} catch (Exception e) {
			return;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	public static boolean isWebServiceEnabled(String companycode_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Company_Specific_Variable.class);
			cri.add(Expression.eq("companyCode_ID", companycode_id));
			Company_Specific_Variable csv = (Company_Specific_Variable)cri.list().get(0);
			if (csv != null) {
				return csv.getWs_enabled() == 1 ? true : false;
			}
			if (sess != null) sess.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	

	
	/**
	 * Determines if password meets security policy standards.
	 * 
	 * @param companycode_id
	 * @param password
	 * @param username
	 * @return boolean Returns false if password policy not met.
	 */
	public static boolean isPolicyAcceptablePassword(String companycode_id, String password, String username, HttpServletRequest request, boolean suppress) {
		boolean securePolicy = false;
		
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Company_Specific_Variable.class);
			cri.add(Expression.eq("companyCode_ID", companycode_id));
			Company_Specific_Variable csv = (Company_Specific_Variable)cri.list().get(0);
			if (csv != null && csv.getSecure_password() == 1) {
				securePolicy = true;
			}
			if (sess != null) sess.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Must not have a space on either end
		password = password.trim();
		
		if (securePolicy) {
			//
			// Password must be > 8 characters
			if (password.length() < 8) {
				if (!suppress)
					request.setAttribute("securePolicy", 1);
				return false;
			} 
			
			// Must include at least 3 of the 4:
			//   * Lower Case
			//   * Upper Case
			//   * Number
			//   * Symbol
			//   ** May not contain username
			

			Pattern lowerCase = Pattern.compile("[a-z]");
			Pattern upperCase = Pattern.compile("[A-Z]");
			Pattern symbol = Pattern.compile("\\W");
			Pattern number = Pattern.compile("\\d");
			Pattern pw = Pattern.compile(username.toLowerCase());
			
			Matcher lcm = lowerCase.matcher(password);
			Matcher ucm = upperCase.matcher(password);
			Matcher sm = symbol.matcher(password);
			Matcher nm = number.matcher(password);
			Matcher pwm = pw.matcher(password.toLowerCase()); 
							
			int total = 0;
			
			if (lcm.find()) {
				total += 1;
			} 

			if (ucm.find()) {
				total += 1;
			} 
			
			if (sm.find()) {
				total += 1;
			}
			
			if (nm.find()) {
				total += 1;
			}

			
			if (total >= 3 && !pwm.find()) {
				return true;
			}
			if (!suppress)
				request.setAttribute("securePolicy", 1);
			return false;

		} else {
			
			if (password.length() >= 4) 
				return true;
			
			if (!suppress)
				request.setAttribute("minimalPolicy", 1);
			return false;
		}
		
	}
}