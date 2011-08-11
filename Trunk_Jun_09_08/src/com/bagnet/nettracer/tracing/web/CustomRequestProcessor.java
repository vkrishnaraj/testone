package com.bagnet.nettracer.tracing.web;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.tiles.TilesRequestProcessor;
import org.dozer.util.CollectionUtils;

import com.bagnet.nettracer.tracing.constant.TracingConstants;

public class CustomRequestProcessor extends TilesRequestProcessor {

	@Override
	protected boolean processPreprocess(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		// If user is trying to access login page
		// then don't check
		if (request.getServletPath().equals("/logon.do")
				|| request.getServletPath().equals("/logoff.do")
				|| request.getServletPath().equals("/passreset.do")
				|| request.getServletPath().startsWith("/passengerview"))
			return true;
		// Check if userName attribute is there is session.
		// If so, it means user has allready logged in
		if (session != null && session.getAttribute("user") != null)
			return true;
		else {
			try {
				// If no redirect user to login Page

				if (request.getParameter("redirect") != null) {
					Map map = request.getParameterMap();
					boolean first = true;
					StringBuffer buffer = request.getRequestURL();
					for (String key : (Set<String>) map.keySet()) {
						if (first) {
							buffer.append("?");
							first = false;
						}
						buffer.append(key + "=" + ((String[]) map.get(key))[0]
								+ "&");
						int a = 0;
					}
					session.setAttribute(TracingConstants.SESSION_REDIRECT_URL,
							buffer.toString());
				}
				response.sendRedirect("logoff.do");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return false;

	}

}
