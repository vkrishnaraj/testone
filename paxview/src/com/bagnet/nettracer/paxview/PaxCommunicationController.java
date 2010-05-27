package com.bagnet.nettracer.paxview;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVIncident;
import com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVPaxCommunication;

public class PaxCommunicationController extends SimpleFormController {
	private boolean anyNewComment;
	private PaxViewService pvService;
	
	public PaxCommunicationController()	{
		setCommandClass(PaxCommunication.class);
		setCommandName("paxCommunication");
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		
		//System.out.println("inside handleRequestInternal ...");
		WS_PVIncident advancedIncident = (WS_PVIncident) req.getSession().getAttribute("FORM_DATA");
		
		if(advancedIncident == null) {
			//System.out.println("handleRequestInternal says advancedIncident is null in this case...");
			//redirect to the search page
			//res.sendRedirect("/paxview/search.htm");
			//return new ModelAndView(new RedirectView("/search.htm", true)); 
			return new ModelAndView("redirect:search.htm");
		    
		} else {
			//System.out.println("handleRequestInternal says advancedIncident is NOT null in that case...");
		}
		
		if(req.getParameter("locale") != null && req.getParameter("locale").trim().length() > 0) {
			req.setAttribute("siteLanguage", req.getParameter("locale"));
		}

		String myNextAction = "" + req.getParameter("prepareto");
		req.setAttribute("prepareto", myNextAction);
		
		
		//need to refresh data here from WS
		String searchClaimNumber = advancedIncident.getIncident_ID();
		String searchLastname = advancedIncident.getPassengers(0).getLastname();
		advancedIncident = pvService.getIncidentPV(searchClaimNumber, searchLastname, true);
		
		return super.handleRequestInternal(req, res);
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest req, 
			HttpServletResponse res, 
			Object command, 
			BindException errors) throws Exception {
		//System.out.println("inside onSubmit ...");
		PaxCommunication paxCommunication = (PaxCommunication) command;
		
		WS_PVIncident advancedIncident = (WS_PVIncident) req.getSession().getAttribute("FORM_DATA");
		
		//advancedIncident.getPaxCommunication();
		String myNewComment = "" + req.getParameter("newPaxComment");
		anyNewComment = false;
		if(myNewComment.trim().length() > 0) {
			if(advancedIncident != null) {
				String claimnumber = advancedIncident.getIncident_ID();
				advancedIncident = pvService.writeComment(claimnumber, myNewComment.trim());
				//limit the input to 1500 or fewer characters
				//String stNewComment2Save = myNewComment.trim().substring(0, 1500);
				//advancedIncident = pvService.writeComment(claimnumber, stNewComment2Save);
			}
			anyNewComment = true;
			paxCommunication.setComment(myNewComment);
		}
		
		//refresh data
		String searchClaimNumber = advancedIncident.getIncident_ID();
		String searchLastname = advancedIncident.getPassengers(0).getLastname();
		advancedIncident = pvService.getIncidentPV(searchClaimNumber, searchLastname, true);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("command", paxCommunication);
		
		if(!anyNewComment) {
			model.put("noNewComment", true);
			//return new ModelAndView(getFormView(), model);
		} 
		
		String myNextAction = "" + req.getParameter("prepareto");
		model.put("prepareto", myNextAction);
		
		
		req.getSession().setAttribute("FORM_DATA", advancedIncident); 
		
		model.put("incident", advancedIncident);
		
		//testing code 
		WS_PVPaxCommunication[] arr = advancedIncident.getPaxCommunication();
		List<WS_PVPaxCommunication> myPaxCommunications = Arrays.asList(arr);
		model.put("paxCommunications", myPaxCommunications);
		return new ModelAndView(getSuccessView(), model);
	}
	
	//@Override
	protected ModelAndView processRequest(HttpServletRequest req, 
			HttpServletResponse res, 
			BindException errors) throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		WS_PVIncident advancedIncident = (WS_PVIncident) req.getSession().getAttribute("FORM_DATA");
		//testing code 
		WS_PVPaxCommunication[] arr = advancedIncident.getPaxCommunication();
		List<WS_PVPaxCommunication> myPaxCommunications = Arrays.asList(arr);
		model.put("paxCommunications", myPaxCommunications);
		
		return new ModelAndView(getSuccessView(), model);
	}

	public void setPvService(PaxViewService pvService) {
		this.pvService = pvService;
	}
	
}
