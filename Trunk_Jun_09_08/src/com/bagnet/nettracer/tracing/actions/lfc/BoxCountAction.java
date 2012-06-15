package com.bagnet.nettracer.tracing.actions.lfc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import aero.nettracer.lf.services.LFServiceBean;
import aero.nettracer.lf.services.LFUtils;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.lf.LFBoxContainer;
import com.bagnet.nettracer.tracing.db.lf.LFBoxCount;
import com.bagnet.nettracer.tracing.forms.lfc.BoxCountForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class BoxCountAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(SalvageAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		TracerUtils.checkSession(session);
		ActionMessages errors = new ActionMessages();

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
		LFUtils.getLists(user, session);
		
		LFServiceBean serviceBean = new LFServiceBean();
		BoxCountForm bForm = (BoxCountForm) form;
		if(bForm.getContainer()==null)
		{
			bForm.setContainer(new LFBoxContainer());
		}

		List stationList = null;
		stationList = TracerUtils.getStationList(user.getCompanycode_ID());

//		if (stationList == null || stationList.size() == 0) {
//			bForm.setSourceStation("");
//		}
		
		request.setAttribute("stationList", stationList);
		List<LFBoxCount> boxCounts=new ArrayList();
		Date dateCount=null;
		if(bForm.getDateString()!=null && bForm.getDateString()!=""){
			SimpleDateFormat dateFormat = new SimpleDateFormat(user.getDateformat().getFormat());
			dateCount=dateFormat.parse(bForm.getDateString());
		}
		
		request.removeAttribute("found");
		String divId = (String) request.getParameter("divId");
		request.setAttribute("divId", divId);
		String addStation=(String) request.getParameter("addStationName");
		String removeStation = (String) request.getParameter("removeStation");

		if (removeStation != null && !removeStation.isEmpty()) {
//			
			boolean success = true;
			try {
				int staId=Integer.parseInt(removeStation);
				Station sta=StationBMO.getStation(staId);
				LFBoxCount boxCount= serviceBean.loadBoxCount(sta, dateCount);

				if (boxCount == null) {
					success = false;
				} else {
					boxCount.setBoxCount(boxCount.getBoxCount()-1);
					saveCount(serviceBean, boxCount, request, errors);
				}
				
			} catch (NumberFormatException nfe) {
				logger.error(nfe, nfe);
				success = false;
			} finally {
				if (!success) {
					request.setAttribute("message", resources.getString("error.unable.remove.item") + ": " + removeStation);
				}
			}

			return mapping.findForward(TracingConstants.AJAX_NEWSTATION);
		} else if (addStation != null && !addStation.isEmpty()) {
			
			
			LFBoxCount boxCount=null;
			boolean success = true;
			try{
				
				int staId=Integer.parseInt(addStation);
				Station sta=StationBMO.getStation(staId);
				if(sta!=null && dateCount!=null){
					boxCount=serviceBean.loadBoxCount(sta,dateCount);
					
					if(boxCount!=null){
						boxCount.setBoxCount(boxCount.getBoxCount()+1);
						saveCount(serviceBean, boxCount, request, errors);
					}
					else
					{
						boxCount=new LFBoxCount();
						boxCount.setSource(sta);
						boxCount.setContainer(bForm.getContainer());
						boxCount.setBoxCount(1);
						saveCount(serviceBean, boxCount, request, errors);
//						bForm.getContainer().getBoxCounts().add(boxCount);
//						saveContainer(serviceBean, bForm.getContainer(), request, errors);
					}
					request.setAttribute("addStationName", sta.getStationcode());
				}
				//boxCount.setBoxCount(boxCount.getBoxCount()++);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			if (boxCount == null || boxCount.getId()==0) {
				success = false;
				request.setAttribute("message", resources.getString("error.unable.add.item"));
			}
			
			if (success) {
				bForm.setAddStation("");
				request.setAttribute("count", boxCount);
//				
			}

			return mapping.findForward(TracingConstants.AJAX_NEWSTATION);
		} else if (request.getParameter("load") != null) {

			boolean success = true;
			try{
				if(dateCount!=null){
					LFBoxContainer container=serviceBean.loadBoxContainer(dateCount);
					if(container!=null){
						bForm.setContainer(container);
					}
					else
					{	
						bForm.setContainer(new LFBoxContainer());
						bForm.getContainer().setDateCount(dateCount);
						saveContainer(serviceBean, bForm.getContainer(), request, errors);
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();

				success = false;
			}
			
			if(success)
			{	
				request.setAttribute("boxCounts", bForm.getContainer().getBoxCounts());
			}
		} 
		
		request.setAttribute("boxCounts", bForm.getContainer().getBoxCounts());
		return mapping.findForward(TracingConstants.LFC_BOXCOUNT);
	}
	
	private boolean saveContainer(LFServiceBean serviceBean, LFBoxContainer container, HttpServletRequest request, ActionMessages errors) {
		boolean success = serviceBean.saveContainer(container); 
		ActionMessage message;
		if (success) {
			message = new ActionMessage("message.container.saved");
		} else {
			message = new ActionMessage("message.container.save.failed");
		}
		errors.add(ActionMessages.GLOBAL_MESSAGE, message);
		saveMessages(request, errors);
		return success;
	}
	
	private boolean saveCount(LFServiceBean serviceBean, LFBoxCount count, HttpServletRequest request, ActionMessages errors) {
		boolean success = serviceBean.saveCount(count); 
		ActionMessage message;
		if (success) {
			message = new ActionMessage("message.container.saved");
		} else {
			message = new ActionMessage("message.container.save.failed");
		}
		errors.add(ActionMessages.GLOBAL_MESSAGE, message);
		saveMessages(request, errors);
		return success;
	}
	
}