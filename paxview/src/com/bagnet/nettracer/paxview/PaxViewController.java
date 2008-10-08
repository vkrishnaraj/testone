package com.bagnet.nettracer.paxview;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVAdvancedIncident;

public class PaxViewController extends SimpleFormController {

	private PaxViewService pvService;
	
	
	
	@Override
	protected ModelAndView onSubmit(Object command, BindException errors) throws Exception {
		// TODO Auto-generated method stub
		Search search = (Search) command;

		WS_PVAdvancedIncident advancedIncident = pvService.getAdvancedIncidentPV(search.getClaimnumber(), search.getLastname(), false);
		
		if(advancedIncident == null) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("command", search);
			model.put("noneFound", true);
			return new ModelAndView(getFormView(), model);
		}
		
		return new ModelAndView(getSuccessView(), "incident", advancedIncident);
	}

	public void setPvService(PaxViewService pvService) {
		this.pvService = pvService;
	}
}

