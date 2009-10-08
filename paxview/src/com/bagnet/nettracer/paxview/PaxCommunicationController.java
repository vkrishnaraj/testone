package com.bagnet.nettracer.paxview;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVIncident;

public class PaxCommunicationController extends SimpleFormController {
	private boolean anyNewComment;
	
	public PaxCommunicationController()	{
		setCommandClass(PaxCommunication.class);
		setCommandName("paxCommunication");
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
	protected ModelAndView onSubmit(HttpServletRequest req, 
			HttpServletResponse res, 
			Object command, 
			BindException errors) throws Exception {
		PaxCommunication paxCommunication = (PaxCommunication) command;
		
		String myNewComment = "" + req.getParameter("newPaxComment");
		anyNewComment = false;
		if(myNewComment.trim().length() > 0) {
			anyNewComment = true;
			paxCommunication.setComment(myNewComment);
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("command", paxCommunication);
		
		if(!anyNewComment) {
			model.put("noNewComment", true);
			//return new ModelAndView(getFormView(), model);
		} 
		
		WS_PVIncident advancedIncident = (WS_PVIncident) req.getSession().getAttribute("FORM_DATA");
		model.put("incident", advancedIncident);
		return new ModelAndView(getSuccessView(), model);
	}

}
