package com.bagnet.nettracer.tracing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;

public abstract class CheckedAction extends Action {

	protected boolean manageToken(HttpServletRequest request){
		if(request.getParameter(org.apache.struts.taglib.html.Constants.TOKEN_KEY) != null) {
			if(isTokenValid(request, true)) {
				saveToken(request);
				return true;
			}
			return false;
		}
		
		saveToken(request);
		return true;
	}
}
