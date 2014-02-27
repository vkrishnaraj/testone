package com.bagnet.nettracer.tracing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.SecurityUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * Implementation of <strong>Action </strong> that processes a user logoff.
 * 
 * @author Craig R. McClanahan
 * @version $Revision$ $Date$
 */
public class LogoffAction extends Action {
	// ----------------------------------------------------- Instance Variables
	/**
	 * The <code>Log</code> instance for this application.
	 */
	private Log log = LogFactory.getLog("com.bagnet.nettracer.tracing.DB");

	// --------------------------------------------------------- Public Methods

	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		

		// Extract attributes we will need
//		Locale locale = getLocale(request);
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		// Process this user logoff
		if (user != null) {
			if (log.isDebugEnabled()) {
				log.debug("LogoffAction: User '" + user.getUsername() + "' logged off in session "
						+ session.getId());
			}

			user.setIs_online(0);
			SecurityUtils.updateAgentLogin(user, TracerDateTime.getGMTDate(), 0, false);
		} else {
			if (log.isDebugEnabled()) {
				log.debug("LogoffActon: User logged off in session " + session.getId());
			}
		}
		String redirectUrl =  (String) session.getAttribute(TracingConstants.SESSION_REDIRECT_URL);
		String logOffClicked = request.getParameter("action");
		if ( logOffClicked != null )
			session.setAttribute("logOffClicked", "true");
		session.invalidate();
		session = request.getSession(true);
		
		if (redirectUrl != null && !redirectUrl.contains("logoff")) {
			session.setAttribute(TracingConstants.SESSION_REDIRECT_URL, redirectUrl);
		}
		
		// Forward control to the specified success URI
		response.addHeader("Pragma", "No-cache");
		response.addHeader("Cache-Control", "no-cache");
		response.addDateHeader("Expires", -1);
		
		String ldapRedirect=PropertyBMO.getValue(PropertyBMO.LDAP_REDIRECT);
		if(request.getParameter("bypass") == null && ldapRedirect!=null && !ldapRedirect.isEmpty()){
			response.sendRedirect(ldapRedirect);
		}
		
		return mapping.findForward(TracingConstants.LOGON);
	}

}