package com.bagnet.nettracer.tracing.web;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.SecurityUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

public class SessionListener implements HttpSessionListener {
	 
	  private Log log = LogFactory.getLog("com.bagnet.nettracer.tracing.DB");
	  
	  @Override
	  public void sessionCreated(HttpSessionEvent arg0) {
		// Not used
	  }
	 
	  @Override
	  public void sessionDestroyed(HttpSessionEvent event) {
		Agent user = (Agent) event.getSession().getAttribute("user");
		String logOffClicked = (String) event.getSession().getAttribute("logOffClicked");
		// Process this user logoff
		if (logOffClicked == null && user != null) {
			if (log.isDebugEnabled()) {
				log.debug("SessionListener: User '" + user.getUsername() + "' logged off in session "
						+ event.getSession().getId());
			}

			user.setIs_online(0);
			SecurityUtils.updateAgentLogin(user, TracerDateTime.getGMTDate(), 0, false, true);
	  }	
	}
}