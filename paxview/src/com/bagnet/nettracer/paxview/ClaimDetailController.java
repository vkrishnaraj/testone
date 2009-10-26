package com.bagnet.nettracer.paxview;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVIncident;
import com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVPaxCommunication;

public class ClaimDetailController extends SimpleFormController {

	private PaxViewService pvService;
	
	public ClaimDetailController() {
		super();
		setCommandClass(Search.class);
		setCommandName("search");
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		if(req.getParameter("locale") != null && req.getParameter("locale").trim().length() > 0) {
			req.setAttribute("siteLanguage", req.getParameter("locale"));
		}

		return super.handleRequestInternal(req, res);
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest req, HttpServletResponse res, Object command, BindException errors) throws Exception {
		Search search = (Search) command;

		WS_PVIncident advancedIncident = (WS_PVIncident) req.getSession().getAttribute("FORM_DATA");
		
		//refresh data
		if(advancedIncident != null) {
			String searchClaimNumber = advancedIncident.getIncident_ID();
			String searchLastname = advancedIncident.getPassengers(0).getLastname();
			advancedIncident = pvService.getIncidentPV(searchClaimNumber, searchLastname);
		}
		
		req.getSession().setAttribute("FORM_DATA", advancedIncident);
		return new ModelAndView(getSuccessView(), "incident", advancedIncident);
	}

	public void setPvService(PaxViewService pvService) {
		this.pvService = pvService;
	}
	
}

