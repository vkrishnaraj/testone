package com.bagnet.nettracer.paxview;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bagnet.nettracer.ws.v1_1.WS_PVIncident;
import com.bagnet.nettracer.ws.v1_1.WS_PVPaxCommunication;

public class PaxViewController extends SimpleFormController {

	private PaxViewService pvService;
	private String jumpSuccessView;
	
	public String getJumpSuccessView() {
		return jumpSuccessView;
	}

	public void setJumpSuccessView(String jumpSuccessView) {
		this.jumpSuccessView = jumpSuccessView;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		if(req.getParameter("locale") != null && req.getParameter("locale").trim().length() > 0) {
			req.setAttribute("siteLanguage", req.getParameter("locale"));
		}

		String myTag = "" + req.getParameter("message");
		if(myTag.equals("1")) {
			//System.out.println("got message " + myTag);
			req.getSession().setAttribute("jumpPage", "YES");
		}
		
		return super.handleRequestInternal(req, res);
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest req, HttpServletResponse res, Object command, BindException errors) throws Exception {
		Search search = (Search) command;
		
		String myMessage;
        if (validatePresenceOf(req, res, "message")) {
        	myMessage = req.getParameter("message");
        	//System.out.println("request.getParameter() worked and the message is " + myMessage);
        }
        
		WS_PVIncident advancedIncident = pvService.getIncidentPV(search.getClaimnumber(), search.getLastname(), false);
		
		if(advancedIncident == null || advancedIncident.getIncident_ID() == null || advancedIncident.getIncident_ID().length() == 0) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("command", search);
			model.put("noneFound", true);
			return new ModelAndView(getFormView(), model);

		} else {
			//System.out.println("advancedIncident from WS it not is null" 
			//		 	+ " with claimnumber " + search.getClaimnumber()
			//		 	+ " and lastname " + search.getLastname()
			//			+ " " + advancedIncident.toString());
		}
		
		req.getSession().setAttribute("FORM_DATA", advancedIncident);
		
		//check to see if there is a query string message=1, if so jump to predefined page
		String myTag = "" + req.getSession().getAttribute("jumpPage");
		//System.out.println("got the request attribute jumpPage and it is " + myTag);
		if(myTag.equals("YES")) {
			//jumpPage = false;  //reset - may not be necessary
			req.getSession().setAttribute("jumpPage", "NO");
			
			//set language to appropriate one 
//			if(req.getParameter("locale") != null && req.getParameter("locale").trim().length() > 0) {
//				req.setAttribute("siteLanguage", req.getParameter("locale"));
//			}
			
			return new ModelAndView(getJumpSuccessView(), "incident", advancedIncident);
		}
		
		//String myTester = ServletRequestUtils.getStringParameter(req, "message");
		//System.out.println("got myTester and it is " + myTester);
		return new ModelAndView(getSuccessView(), "incident", advancedIncident);
	}

    private boolean validatePresenceOf(HttpServletRequest request,
            HttpServletResponse response, String ... params) throws IOException {
        for (String param : params) {
            if (StringUtils.hasText(request.getParameter(param))) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        param + " must be provided");
                return false;
            }
        }
        return true;
    }
    
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		
		super.initBinder(request, binder);
	}

    
	public void setPvService(PaxViewService pvService) {
		this.pvService = pvService;
	}
}

