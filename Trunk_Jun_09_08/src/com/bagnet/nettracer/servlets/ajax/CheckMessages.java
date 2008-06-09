package com.bagnet.nettracer.servlets.ajax;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ajaxtags.servlets.BaseAjaxServlet;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.MessageUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * This servlet is used to access the image from the file system.
 * 
 * @author Ankur Gupta
 * 
 * create date - Feb 28, 2005
 */
public class CheckMessages extends BaseAjaxServlet {

	/**
	 * @see org.ajaxtags.demo.servlet.BaseAjaxServlet#getXmlContent(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public String getXmlContent(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer html = new StringBuffer();

		try {

			// check session to see if timedout
			TracerUtils.checkSession(request.getSession());

			Agent user = (Agent) request.getSession().getAttribute("user");
			if (user == null) {
				//response.sendRedirect("logoff.do");
				return "";
			}

			HttpSession session = request.getSession(true);
			Agent agent = (Agent) session.getAttribute("user");
			if (MessageUtils.getNewMessages(agent.getStation().getStation_ID()) > 0)
				html.append("<a href=\"message.do?inbox=1\"><img src=\"deployment/main/images/misc/imp.gif\" width=\"12\" height=\"12\"></a>");
			else
				html.append("&nbsp;");

		} catch (Exception e) {
			html.append("&nbsp;");
		}

		return html.toString();

	}

}