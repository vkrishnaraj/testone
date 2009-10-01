package com.bagnet.nettracer.tracing.actions;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.WT_PXF;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestPxf;
import com.bagnet.nettracer.tracing.forms.WorldTracerPXFForm;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;

public class WorldTracerPXFAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		// check session and user validity
		TracerUtils.checkSession(session);
		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasPermission(
				TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_PXF, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		
		WorldTracerPXFForm theform = (WorldTracerPXFForm) form;
		
		if (theform == null || request.getParameter("clear") != null) {
			theform = new WorldTracerPXFForm();
			session.setAttribute("worldTracerPXFForm", theform);
		}
		
		ActionMessages errors = new ActionMessages();
		
		if (request.getParameter("save") != null) {
			//when user selects Action Message Address option radio button
			String myPxfOption = theform.getPxfOption();
			if (myPxfOption == null || myPxfOption.trim().length() < 1) {
				ActionMessage error = new ActionMessage("Please select one of the three options !");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} else {
				// Do the insert wt forward and wt_queue into database
			
				//ActionMessage error = null;
				ActionMessage error = this.insertWtReqPxf(theform, user);
				
				StringBuffer myMessage = new StringBuffer("");
				String myFurtherInfo = theform.getFurtherInfo();
				myMessage.append("Further Info:" + myFurtherInfo);
				
				String myTeletype_1 = theform.getTeletype(0);
				String myTeletype_2 = theform.getTeletype(1);
				myMessage.append("teletype 1:" + myTeletype_1);
				myMessage.append("teletype 2:" + myTeletype_2);
				
				List<String> myTeletypes = theform.getTeletypes();
				Iterator<String> itr = myTeletypes.iterator();
				while(itr.hasNext()) {
					String myElement = itr.next();
					myMessage.append("one more tele:" + myElement);
				}
				//get Place Action File destination
				//myPxfOption = theform.getPxfOption();
				if(myPxfOption.equalsIgnoreCase("ALL_STATIONS")) {
					String myAllStationsArea = theform.getAllStationsArea();
					error = new ActionMessage("Got the ALL_STATIONS request :"
							+ myAllStationsArea + " <<"
							+ myMessage);
					
				} else if (myPxfOption.equalsIgnoreCase("REGION")) {
					String myRegionAirline = theform.getRegionCode();
					String myRegionArea = theform.getRegionArea();
					error = new ActionMessage("Got the REGION request :"
								+ myRegionAirline + " : "
								+ myRegionArea + " << <<"
								+ myMessage);
				} else {
					String myFromStation_1 = theform.getStation_1();
					String myFromAirline_1 = theform.getAirline_1();
					String myFromArea_1 = theform.getArea_1();
					
					error = 
						new ActionMessage("Got the ACTION_MESSAGE_ADDRESS request :" 
								+ myFromStation_1 
								+ myFromAirline_1
								+ myFromArea_1 + "<< << <<"
								+ myMessage);
				}
				
				error = null;
				
				if(error == null){
					return (mapping.findForward(TracingConstants.SEND_WT_PXF_SUCCESS));
				}
				else {
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				}
			}

			return (mapping.findForward(TracingConstants.VIEW_WORLDTRACER_PXF));
		}
		
		return mapping.findForward(TracingConstants.VIEW_WORLDTRACER_PXF);
	}

	private ActionMessage insertWtReqPxf(WorldTracerPXFForm theform, Agent user) {
		WtqRequestPxf wtq = new WtqRequestPxf();
		WT_PXF wtPxf = new WT_PXF();
		
		String myPxfOption = theform.getPxfOption();
		int myDestinationType = 3;   //default to stations option
		if(myPxfOption.equalsIgnoreCase("ALL_STATIONS")) {
			myDestinationType = 1;
			wtPxf.setArea_1(theform.getAllStationsArea());
		} else if (myPxfOption.equalsIgnoreCase("REGION")) {
			myDestinationType = 2;
			wtPxf.setStation_1(theform.getRegionCode());
			wtPxf.setArea_1(theform.getRegionArea());
		} else if (myPxfOption.equalsIgnoreCase("ACTION_MESSAGE_ADDRESS")) {
			wtPxf.setStation_1(theform.getStation_1());
			wtPxf.setStation_2(theform.getStation_2());
			wtPxf.setStation_3(theform.getStation_3());
			wtPxf.setStation_4(theform.getStation_4());
			wtPxf.setStation_5(theform.getStation_5());
			
			wtPxf.setAirline_1(theform.getAirline_1());
			wtPxf.setAirline_2(theform.getAirline_2());
			wtPxf.setAirline_3(theform.getAirline_3());
			wtPxf.setAirline_4(theform.getAirline_4());
			wtPxf.setAirline_5(theform.getAirline_5());
			
			wtPxf.setArea_1(theform.getArea_1());
			wtPxf.setArea_2(theform.getArea_2());
			wtPxf.setArea_3(theform.getArea_3());
			wtPxf.setArea_4(theform.getArea_4());
			wtPxf.setArea_5(theform.getArea_5());
		}
		wtPxf.setDestination(myDestinationType);
		
		wtq.setAgent(user);
		wtq.setCreatedate(TracerDateTime.getGMTDate());
		wtq.setFurtherInfo(theform.getFurtherInfo());
		//wtq.setTeletypes(new HashSet<String>(theform.getTeletypes()));
		
		Set<String> teletypes = new HashSet<String>();
		String teletype_0 = theform.getTeletype(0);
		String teletype_1 = theform.getTeletype(1);
		String teletype_2 = theform.getTeletype(2);
		String teletype_3 = theform.getTeletype(3);
		String teletype_4 = theform.getTeletype(4);
		if(teletype_0 != null && teletype_0.trim().length() > 3) {
			teletypes.add(teletype_0);
		}
		if(teletype_1 != null && teletype_1.trim().length() > 3) {
			teletypes.add(teletype_1);
		}
		if(teletype_2 != null && teletype_2.trim().length() > 3) {
			teletypes.add(teletype_2);
		}
		if(teletype_3 != null && teletype_3.trim().length() > 3) {
			teletypes.add(teletype_3);
		}
		if(teletype_4 != null && teletype_4.trim().length() > 3) {
			teletypes.add(teletype_4);
		}
		wtq.setTeletypes(teletypes);
		
		try {
			//WorldTracerQueueUtils.createOrReplaceQueue(wtq);
			//Session mySession = HibernateWrapper.getSession().openSession();
			//wtq = (WtqRequestPxf) mySession.get(WtqRequestPxf.class, new Long(1));
			
			wtPxf.setWtq(wtq);
			wtq.setPxf(wtPxf);
			HibernateUtils.save(wtq);
		} catch (Exception e) {
			e.printStackTrace();
			return new ActionMessage("error.wt_err_database_queue_error");
		}
		return null;
	}
}