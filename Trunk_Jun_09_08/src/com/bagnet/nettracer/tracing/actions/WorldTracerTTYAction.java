package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;


import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.WT_TTY;
import com.bagnet.nettracer.tracing.forms.WorldTracerTTYForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.wt.WTOHD;
import com.bagnet.nettracer.wt.WorldTracerUtils;

import org.apache.commons.httpclient.HttpClient;

/**
 * Implementation of <strong>Action </strong> that is responsible for handling
 * forward on-hand bags task. It would forward the bag to the desired location
 * or LZ.
 * 
 * @author Ankur Gupta
 */
public class WorldTracerTTYAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();

		// check session and user validity
		TracerUtils.checkSession(session);
		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_TTY, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		
		ActionMessages errors = new ActionMessages();

		// Obtain a handle on the resources direcory.
		MessageResources messages = MessageResources
				.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");

		BagService bs = new BagService();
		WorldTracerTTYForm theform = (WorldTracerTTYForm) form;




          if (request.getParameter("save") != null) {

				{
					
					// Do the forward
					
					if (this.InsertWtTty(theform, user)){
						this.clear(mapping, user, session, request);
						return (mapping.findForward(TracingConstants.SEND_WT_TTY_SUCCESS));
					}
					
					// WTOHD wtfwd = new WTOHD();
					// HttpClient client = new HttpClient();
					// wttty.InsertWtTty(client, theform, user);
					/*
					 * 
					 * if (bs.forwardOnHand(theform, user, messages)) { return
					 * (mapping.findForward(TracingConstants.FORWARD_ON_HAND_SUCCESS)); }
					 */
					
				}
			}
			return (mapping.findForward(TracingConstants.VIEW_WORLDTRACER_TTY));
	}

	// clear
	private ActionForward clear(ActionMapping mapping, Agent user,
			HttpSession session, HttpServletRequest request) {
		WorldTracerTTYForm theform = new WorldTracerTTYForm();


		session.setAttribute("worldTracerTTYForm", theform);
		return (mapping.findForward(TracingConstants.VIEW_WORLDTRACER_TTY));

	}

	// Insert worldtracer TTY to database
	private boolean InsertWtTty(WorldTracerTTYForm theform, Agent user) {
		WT_TTY wttty = new WT_TTY();
	   
		wttty.setTeletype_address1(theform.getTeletype_address1());
		wttty.setTeletype_address2(theform.getTeletype_address2());
		wttty.setTeletype_address3(theform.getTeletype_address3());
		wttty.setTeletype_address4(theform.getTeletype_address4());
		wttty.setOrigin_address(theform.getOrigin_address());
		wttty.setAirline_code(theform.getAirline_code());
		wttty.setFile_type1(theform.getFile_type1());
		wttty.setFile_type2(theform.getFile_type2());
		wttty.setFile_type3(theform.getFile_type3());
		wttty.setFile_type4(theform.getFile_type4());
		wttty.setFile_reference1(theform.getFile_reference1());
		wttty.setFile_reference2(theform.getFile_reference2());
		wttty.setFile_reference3(theform.getFile_reference3());
		wttty.setFile_reference4(theform.getFile_reference4());
		wttty.setTty_station_id(user.getStation().getStation_ID());
		wttty.setSend_time(TracerDateTime.getGMTDate());
		wttty.setTty_agent(user);
		wttty.setText(theform.getText());
		wttty.setTty_status(TracingConstants.LOG_RECEIVED);
        
		HibernateUtils.save(wttty);
		return true;

	}
	public void postWTTTY(WorldTracerTTYForm ttyForm, Agent user) {
		// make the request
		String company = user.getCompanycode_ID();
		//StringBuffer tty_string = new StringBuffer();
		String tx1 = ttyForm.getTeletype_address1().toUpperCase();
		String tx2 = ttyForm.getTeletype_address2().toUpperCase();
		String tx3 = ttyForm.getTeletype_address3().toUpperCase();
		String tx4 = ttyForm.getTeletype_address4().toUpperCase();
		String oa = ttyForm.getOrigin_address().toUpperCase();
		String ca = ttyForm.getAirline_code().toUpperCase();
		String ftyp1 = ttyForm.getFile_type1().toUpperCase();
		String ftyp2 = ttyForm.getFile_type2().toUpperCase();
		String ftyp3 = ttyForm.getFile_type3().toUpperCase();
		String ftyp4 = ttyForm.getFile_type4().toUpperCase();
		String fref1 = ttyForm.getFile_reference1().toUpperCase();
		String fref2 = ttyForm.getFile_reference2().toUpperCase();
		String fref3 = ttyForm.getFile_reference3().toUpperCase();
		String fref4 = ttyForm.getFile_reference4().toUpperCase();
		String ttytxt = ttyForm.getText().toUpperCase();
		
		

        ArrayList <String> ttylist = new ArrayList();
        ttylist.add(tx1);
        ttylist.add(tx2);
        ttylist.add(tx3);
        ttylist.add(tx4);
        ttylist.add(oa);
        ttylist.add(ca);
        ttylist.add(ftyp1);
        ttylist.add(ftyp2);
        ttylist.add(ftyp3);
        ttylist.add(ftyp4);
        ttylist.add(fref1);
        ttylist.add(fref2);
        ttylist.add(fref3);
        ttylist.add(fref4);
        ttylist.add(ttytxt);
        		
		HttpClient client = WorldTracerUtils.connectWT(WorldTracerUtils.wt_suffix_airline + "/",company);
		String test = WorldTracerUtils.SendTty(client,company,ttylist);
		
		// for now return ohd_string
		//test = tty_string.toString();
		
		//return test;
	}
}