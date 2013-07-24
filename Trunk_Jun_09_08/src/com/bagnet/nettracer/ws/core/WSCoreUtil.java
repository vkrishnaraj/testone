package com.bagnet.nettracer.ws.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.OHD_CategoryType;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.Webservice_Session;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.SecurityUtils;
import com.bagnet.nettracer.tracing.utils.TEA;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * general utility class for all core webservices
 * 
 * @author matt
 * 
 */
public class WSCoreUtil {

	private static Logger logger = Logger.getLogger(WSCoreUtil.class);
	
	// webservice standard date and time format for nettracer
	public final static String WS_DATETIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
	public final static String WS_DATEFORMAT = "yyyy-MM-dd";
	public final static String WS_TIMEFORMAT = "HH:mm:ss";
	
	// number of hours before webservice session expires
	public final static int HOURS_BEFORE_EXPIRATION = 24;

	private static MessageResources messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");

	/**
	 * authenticate with username, password, and companycode, return session id
	 * after inserting into db
	 * 
	 * if webservice is not enabled, return error if user is not active, or not
	 * webservice enabled, return error if user is good, but session exceed
	 * allowed max session, return error if session doesn't exceed allowed max
	 * sessions, add the new session
	 * 
	 * @param username
	 * @param password
	 * @param companycode_id
	 * @return
	 */
	public com.bagnet.nettracer.ws.core.AuthenticateResponseDocument authenticate(com.bagnet.nettracer.ws.core.AuthenticateDocument authenticate) {

		logger.info("Start Web Service Response...");
		String username = authenticate.getAuthenticate().getUsername();
		String password = authenticate.getAuthenticate().getPassword();
		String companycode_id = authenticate.getAuthenticate().getCompanycode();
		String returnstr = null;

		AuthenticateResponseDocument resDoc = AuthenticateResponseDocument.Factory.newInstance();
		AuthenticateResponseDocument.AuthenticateResponse res = resDoc.addNewAuthenticateResponse();

		if (SecurityUtils.isWebServiceEnabled(companycode_id.toUpperCase())) {

			ActionMessages errors = new ActionMessages();
			Agent agent = SecurityUtils.authUser(username, password, companycode_id, 1, errors);

			if (!errors.isEmpty()) {
				// has errors return errors
				StringBuffer sb = new StringBuffer();
				sb.append("ERROR: ");
				for (Iterator i = errors.get(); i.hasNext();) {
					ActionMessage am = (ActionMessage) i.next();
					sb.append(messages.getMessage(new Locale(TracingConstants.DEFAULT_LOCALE), am.getKey()) + ". ");
				}
				returnstr = sb.toString();
			} else if (agent == null) {
				returnstr = "ERROR: no agent found";
			}

			if (returnstr == null) {
				Webservice_Session ws = null;

				// first try to find the session

				Session sess = null;
				try {
					sess = HibernateWrapper.getSession().openSession();
					Criteria cri = sess.createCriteria(Webservice_Session.class);
					cri.add(Expression.eq("username", username));
					cri.add(Expression.eq("companycode_id", companycode_id));
					ArrayList thelist = (ArrayList) cri.list();
					if (thelist != null && thelist.size() > 0) {
						// first look for expired sessions and delete them
						int numsess = thelist.size();
						for (int i = 0; i < thelist.size(); i++) {
							ws = (Webservice_Session) thelist.get(i);
							Date nowdate = TracerDateTime.getGMTDate();
							Date authdate = ws.getDate_active();
							long milsec = nowdate.getTime() - authdate.getTime();
							long hourdiff = milsec / (1000 * 60 * 60);
							if (hourdiff > HOURS_BEFORE_EXPIRATION) {
								HibernateUtils.delete(ws);
								numsess--;
							}
						}

						// more than max sessions allowed even after deleting expired
						// sessions, then return error message
						if (numsess >= agent.getMax_ws_sessions()) {
							returnstr = "ERROR: this user has exceed the preconfigured max concurrent login sessions. Please logoff other sessions or contact administrator to increase the concurrency.";
							res.setReturn("");
							return resDoc;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (sess != null) sess.close();
				}

				// create the md5 between username and password
				Date nowdate = TracerDateTime.getGMTDate();

				String session_id = TEA.encryptTEA(username + password + nowdate.toString());

				// now create new session
				ws = new Webservice_Session();
				ws.setCompanycode_id(companycode_id);
				ws.setUsername(username);
				ws.setSession_id(session_id);
				ws.setDate_active(nowdate);
				HibernateUtils.save(ws);
				if (ws != null) {
					if (ws.getId() > 0) {
						returnstr = "SUCCESS: " + ws.getSession_id();
						res.setReturn(ws.getSession_id());
						return resDoc;
					}
				}
				returnstr = "ERROR: Unable insert into session table, please contact admin";
			}
		} else {
			returnstr = "ERROR: WEBSERVICE DISABLED";
		}
		logger.info("Stop Web Service Response...");
		
		res.setReturn(returnstr);
		return resDoc;

	}
	
	/**
	 *  Get the agent from the session.
	 */
	public static Agent getAgent(String sessionId) {
		Session sess = null;
		
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Webservice_Session.class);
			cri.add(Expression.eq("session_id", sessionId));
			ArrayList thelist = (ArrayList) cri.list();
			if (thelist != null && thelist.size() > 0) {
				Webservice_Session ws = (Webservice_Session) thelist.get(0);
				String username = ws.getUsername();
				String companyCode = ws.getCompanycode_id();
				return TracerUtils.getAgent(username, companyCode);				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	public static Agent getWebsessionAgent(String session_id, String component_id) throws AuthenticationException {

		    Session sess = null;
		    ArrayList thelist = null;
		    try {
				sess = HibernateWrapper.getSession().openSession();
				Criteria cri = sess.createCriteria(Webservice_Session.class);
				cri.add(Expression.eq("session_id", session_id));
				thelist = (ArrayList) cri.list();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (sess != null) {
					sess.close();
				}
			}
			if (thelist != null && thelist.size() > 0) {
				Webservice_Session ws = (Webservice_Session) thelist.get(0);
				Date nowdate = TracerDateTime.getGMTDate();
				Date authdate = ws.getDate_active();
				long milsec = nowdate.getTime() - authdate.getTime();
				long hourdiff = milsec / (1000 * 60 * 60);
				if (hourdiff > HOURS_BEFORE_EXPIRATION) {
					throw new AuthenticationException("User session expired, please retrieve a new session id using authenticate call.");
				} else {
					// check to see if webservice is enabled or not
					if (SecurityUtils.isWebServiceEnabled(ws.getCompanycode_id().toUpperCase())) {
						// check to see if agent has permission to access this component
						Agent agent = AdminUtils.getAgentBasedOnUsername(ws.getUsername(), ws.getCompanycode_id());
						if (agent != null && (component_id == null || UserPermissions.hasPermission(component_id, agent))){//loupas - if no component then return success by request of byron
								return agent;

						} else {
							throw new AuthenticationException("User has no permission to access this webservice method call, please contact admin to add permission.");
						}

					} else {
						throw new AuthenticationException("Webservice disabled.");
					}
				}
			}
			throw new AuthenticationException("Unable to authenticate user session, please reauthenticate.");
	}
	
	/**
	 * re authenticate with sessionid, make sure it is less than 24 hours since
	 * insert
	 * 
	 * @param session_id
	 * @param companycode_id
	 * @return
	 */
	public static String reauth(String session_id, String component_id) {

		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Webservice_Session.class);
			cri.add(Expression.eq("session_id", session_id));
			ArrayList thelist = (ArrayList) cri.list();
			if (thelist != null && thelist.size() > 0) {
				Webservice_Session ws = (Webservice_Session) thelist.get(0);
				Date nowdate = TracerDateTime.getGMTDate();
				Date authdate = ws.getDate_active();
				long milsec = nowdate.getTime() - authdate.getTime();
				long hourdiff = milsec / (1000 * 60 * 60);
				if (hourdiff > HOURS_BEFORE_EXPIRATION) {
					return "User session expired, please retrieve a new session id using authenticate call.";
				} else {
					// check to see if webservice is enabled or not
					if (SecurityUtils.isWebServiceEnabled(ws.getCompanycode_id().toUpperCase())) {
						// check to see if agent has permission to access this component
						Agent agent = AdminUtils.getAgentBasedOnUsername(ws.getUsername(), ws.getCompanycode_id());
						if (agent != null && UserPermissions.hasPermission(component_id, agent)) {
							return null;
						} else {
							return "User has no permission to access this webservice method call, please contact admin to add permission.";
						}

					} else {
						return "Webservice disabled.";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "Unable to authenticate user session, please reauthenticate.";
	}

	/**
	 * logoff session, (delete session from table if it exists)
	 * 
	 * @param logoff
	 * @return
	 */
	public com.bagnet.nettracer.ws.core.LogoffResponseDocument logoff(com.bagnet.nettracer.ws.core.LogoffDocument logoff) {

		String session_id = logoff.getLogoff().getSessionId();

		LogoffResponseDocument resDoc = LogoffResponseDocument.Factory.newInstance();
		LogoffResponseDocument.LogoffResponse res = resDoc.addNewLogoffResponse();

		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Webservice_Session.class);
			cri.add(Expression.eq("session_id", session_id));
			ArrayList thelist = (ArrayList) cri.list();
			if (thelist != null && thelist.size() > 0) {
				Webservice_Session ws = (Webservice_Session) thelist.get(0);
				HibernateUtils.delete(ws);
			}
			res.setReturn(true);
		} catch (Exception e) {
			e.printStackTrace();
			res.setReturn(false);
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return resDoc;
	}

	/**
	 * format date / time according to worldtracer standard
	 * 
	 * @param indate
	 * @param intime
	 * @return
	 */
	public static String formatDatetoString(Date indate, Date intime) {
		if (indate != null && intime != null) {
			Date completedate = DateUtils.convertToDate(indate.toString() + " " + intime.toString(), TracingConstants.DB_DATETIMEFORMAT, null);
			return DateUtils.formatDate(completedate, WS_DATETIMEFORMAT, null, null);
		} else if (indate != null && intime == null) {
			return DateUtils.formatDate(indate, WS_DATEFORMAT, null, null);
		} else if (intime != null && indate == null) {
			return DateUtils.formatDate(intime, WS_TIMEFORMAT, null, null);
		}
		return null;
	}

	/**
	 * get different statuses
	 * 
	 * @param desc
	 * @param table_id
	 * @return
	 */
	public static Status getStatus(String desc, int table_id) {
		
		Session sess = null;
		try {
			if (desc == null || desc.length() == 0) return null;
			
			sess = HibernateWrapper.getSession().openSession();
			String sql = "select status_id from status where description =:description and table_ID =:table_ID";
			
			SQLQuery query = sess.createSQLQuery(sql);
			query.setParameter("description", desc);
			query.setParameter("table_ID", table_id);
			Integer status_id = (Integer)query.list().get(0);
			Status status = new Status();
			status.setStatus_ID(status_id);
			return status;
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

	public static int getContentCategory(String code) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHD_CategoryType.class).add(Expression.like("categorytype", code)).add(Expression.eq("locale", "en"));
			OHD_CategoryType oc = (OHD_CategoryType) cri.list().get(0);
			return oc.getOHD_CategoryType_ID();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
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

}