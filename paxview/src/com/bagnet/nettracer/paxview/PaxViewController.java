package com.bagnet.nettracer.paxview;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVIncident;

public class PaxViewController extends SimpleFormController {

	private PaxViewService pvService;
	
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

		WS_PVIncident advancedIncident = pvService.getIncidentPV(search.getClaimnumber(), search.getLastname());
		
		if(advancedIncident == null) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("command", search);
			model.put("noneFound", true);
			return new ModelAndView(getFormView(), model);
		}
		
		req.getSession().setAttribute("FORM_DATA", advancedIncident);
		return new ModelAndView(getSuccessView(), "incident", advancedIncident);
	}

	public void setPvService(PaxViewService pvService) {
		this.pvService = pvService;
	}
}

