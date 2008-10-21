package com.bagnet.nettracer.tracing.web;

import java.io.IOException;

import javax.security.auth.login.AppConfigurationEntry;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.RequestProcessor;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.tiles.TilesRequestProcessor;

import com.bagnet.nettracer.tracing.db.Agent;

public class CustomRequestProcessor extends TilesRequestProcessor {

	@Override
	protected boolean processPreprocess(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        //If user is trying to access login page
        // then don't check
        if( request.getServletPath().equals("/logon.do")
            || request.getServletPath().equals("/logoff.do") || request.getServletPath().equals("/passreset.do") 
            || request.getServletPath().startsWith("/passengerview"))
            return true;
        //Check if userName attribute is there is session.
        //If so, it means user has allready logged in
        if( session != null && session.getAttribute("user") != null)
            return true;
        else{
            try{
                //If no redirect user to login Page
            	response.sendRedirect("logoff.do");
                //request.getRequestDispatcher("/logoff.do").forward(request,response);
            }catch(Exception ex){
            }
        }
        return false;

	}

}
