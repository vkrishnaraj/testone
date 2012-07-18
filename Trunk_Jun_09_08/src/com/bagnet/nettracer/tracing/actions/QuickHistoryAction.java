package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.datasources.ScannerDataSource;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.dto.QuickSearchDTO;
import com.bagnet.nettracer.tracing.dto.ScannerDTO;
import com.bagnet.nettracer.tracing.dto.SearchIncident_DTO;
import com.bagnet.nettracer.tracing.forms.QuickSearchForm;
import com.bagnet.nettracer.tracing.forms.SearchIncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HistoryUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;
import com.bagnet.nettracer.tracing.history.HistoryContainer;
import com.bagnet.nettracer.tracing.history.HistoryObject;


public class QuickHistoryAction extends Action {

	private static Logger logger = Logger.getLogger(QuickHistoryAction.class);
	private static final String REDIRECT_SUCCESS = "success";

	private static final int MAX_RESULTS = 10;

	// TODO: Action Permissions
	// TODO: UI JAVASCRIPT PERMISSIONS ??? NOT SURE WE NEED THESE
	// TODO: AGENTS JAVASCRIPT
	// TODO: STATION JAVASCRIPT

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(); // check session/user
		TracerUtils.checkSession(session);
		
		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		// TODO: Check all types of permissions
		QuickSearchForm theForm = (QuickSearchForm) form;
		logger.info("Scanning for user's recent history");

		HistoryUtils.getHistoryContainer(theForm, session);
		
		return (mapping.findForward(REDIRECT_SUCCESS));
	}
}
