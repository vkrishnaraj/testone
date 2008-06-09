/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.internationalization.LocaleNegotiator;

/**
 * @author Matt
*/
public class PassengerViewOnly extends Action {
	private static Logger logger = Logger.getLogger(PassengerViewOnly.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		session.removeAttribute("user");
		
		LocaleNegotiator negotiator = new LocaleNegotiator(request);
		Locale requestedLocale = negotiator.getLocale(); 
		Locale userLocale = new Locale(requestedLocale.getLanguage().substring(0,2));
		
		if (request.getParameter("locale") != null && !request.getParameter("locale").equals("") ) {
			try {
				userLocale = new Locale((String)request.getParameter("locale"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (userLocale.getLanguage().equals("es")) {
			session.setAttribute("otherLanguage", "en");
			session.setAttribute("currentLanguage", "es");
		} else {
			session.setAttribute("otherLanguage", "es");
			session.setAttribute("currentLanguage", "en");
		}
				
		session.setAttribute(org.apache.struts.Globals.LOCALE_KEY, userLocale);
		
		// passenger enter reference number and last name to retrieve information
		String inc_id = request.getParameter("incident_ID");
		String name = request.getParameter("name");

		BagService bs = new BagService();
		IncidentForm theform = (IncidentForm) form;
		if (theform == null) theform = new IncidentForm();
		request.setAttribute("IncidentForm", theform);
		
		

		// search and go to specific MBR report
		if (inc_id != null && name != null && request.getParameter("search") != null && 
				!request.getParameter("search").equals("")) {
			inc_id = StringUtils.fillzero(inc_id);
			if (!bs.findIncidentForPVO(inc_id, name, theform,request)) {
				ActionMessages errors = new ActionMessages();
				ActionMessage error = new ActionMessage("error.pvo.noincident");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				theform = new IncidentForm();
				request.setAttribute("IncidentForm", theform);
				request.removeAttribute("incident");

			} else {
				// populate session
				request.setAttribute("incident", inc_id);
				session.setAttribute("incident", inc_id);
				session.setAttribute("name", name);
				session.setAttribute("categorylist", session.getAttribute("categorylist") != null ? session.getAttribute("categorylist")
						: TracerUtils.getCategoryList(theform.getAgent().getStation().getLocale()));
				
				Item item = theform.getItem(0, 0);

				// find out what kind of incident this is

				switch (item.getItemtype_ID()) {
					case TracingConstants.LOST_DELAY:
						request.setAttribute("lostdelay", "1");
						break;
					case TracingConstants.DAMAGED_BAG:
						request.setAttribute("damaged", "1");
						break;
					case TracingConstants.MISSING_ARTICLES:
						request.setAttribute("missing", "1");
						break;
					default:
						request.setAttribute("lostdelay", "1");
						break;
				}

			}
		}

		return (mapping.findForward(TracingConstants.PASS_VIEW_ONLY));

	}
}