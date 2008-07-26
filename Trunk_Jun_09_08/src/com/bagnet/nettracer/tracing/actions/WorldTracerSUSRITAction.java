package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
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
import org.apache.struts.util.MessageResources;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class WorldTracerSUSRITAction extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();

		// check session and user validity
		TracerUtils.checkSession(session);
		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasPermission(
				TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_ROH, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));

		ActionMessages errors = new ActionMessages();

		// Obtain a handle on the resources directory.
		MessageResources messages = MessageResources
				.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
		String checkbox[]=request.getParameterValues("judgepartsuspend");
		if(checkbox!=null){
			Boolean flag=TracerUtils.madePartSuspendWT_BAG_SELECTED(checkbox);
			if(flag){
				request.setAttribute("completeSuccess","1");
			}else{
				request.setAttribute("completeSuccess", "2");
			}
		}
		String suspend = request.getParameter("suspend");
		String fileReference = request.getParameter("fileReference");
		String ahlORohd = request.getParameter("ahlORohd");
		String hidden = request.getParameter("hidden");
		List list3 = new ArrayList();
		List list4 = new ArrayList();
		request.setAttribute("radio", "completeSUS");
		request.setAttribute("span", "");
		request.setAttribute("ahlOrohd","ahl");
		if(null==suspend||suspend.equals("completeSUS")||suspend.equals("")){
			request.setAttribute("span", "completeSUS");
		}
		else if(suspend.equals("partSUS")){
			request.setAttribute("span","partSUS");
		}
		// fileReference.trim()
		if (null == fileReference || "".equals(fileReference.trim())) {
			request.setAttribute("filereference","");
			if(hidden!=null&&!hidden.trim().equals("")){
				ActionMessage error = new ActionMessage("error.noincident");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
		} else {
			request.setAttribute("filereference",fileReference);
			if (suspend.equals("completeSUS")) {
				request.setAttribute("radio", "completeSUS");
				if(null==ahlORohd||ahlORohd.trim().equals("")){
					//System.out.println("---------------");
					ahlORohd="ahl";
				}
				if (null!=ahlORohd&&ahlORohd.equals("ahl")) {
					//System.out.println("fffffffffffffffffffffff");
					Incident incident = TracerUtils
							.incidentFileReference(fileReference);
					if (null == incident) {
						ActionMessage error = new ActionMessage(
								"error.noincident");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						saveMessages(request, errors);
					} else {
						if (incident.getWt_id() == null
								|| incident.getWt_id().trim().equals("")) {
							System.out.println("wt_id is null");
							ActionMessage error = new ActionMessage(
									"error.nowt_id");
							errors.add(ActionMessages.GLOBAL_MESSAGE, error);
							saveMessages(request, errors);
						} else {
							list3 = incident.getItemlist();
							Boolean flag=TracerUtils.madeSuspendWT_BAG_SELECTED(list3);
							if(flag){
								request.setAttribute("completeSuccess","1");
							}else{
								request.setAttribute("completeSuccess","2");
							}
						}
					}
				} else if (null!=ahlORohd&&ahlORohd.equals("ohd")) {
					request.setAttribute("ahlOrohd","ohd");
					OHD ohd = TracerUtils.ohdFileReference(fileReference);
					if (ohd == null) {
						ActionMessage error = new ActionMessage("error.noohd");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						saveMessages(request, errors);
					} else {
						if (ohd.getWt_id() == null
								|| ohd.getWt_id().trim().equals("")) {
							ActionMessage error = new ActionMessage(
									"error.nowt_id");
							errors.add(ActionMessages.GLOBAL_MESSAGE, error);
							saveMessages(request, errors);
						} else {
						   // list3 = new ArrayList(ohd.getItems());
							//TracerUtils.madeSuspendWT_BAG_SELECTED(list3);
						}
					}
				}
			} else if (suspend.equals("partSUS")) {
				request.setAttribute("radio", "partSUS");
				Incident incident = TracerUtils
						.incidentFileReference(fileReference);
				if (null == incident) {
					ActionMessage error = new ActionMessage("error.noincident");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				} else {
					if (incident.getWt_id() == null
							|| incident.getWt_id().trim().equals("")) {
						ActionMessage error = new ActionMessage("error.nowt_id");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						saveMessages(request, errors);
					} else {
						list3 = incident.getItemlist();
						Iterator it = list3.iterator();
						while (it.hasNext()) {
							Item item = (Item) it.next();
							if (item.getWt_bag_selected() == 0) {
								list4.add(item);
							}
						}
						request.setAttribute("partresultlist", list4);
					}
				}
			}
		}
		return mapping.findForward(TracingConstants.VIEW_WORLDTRACER_SUSRIT);
	}
}
