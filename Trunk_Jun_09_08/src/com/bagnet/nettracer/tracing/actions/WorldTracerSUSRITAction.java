package com.bagnet.nettracer.tracing.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
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
		
		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_ROH, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));

		ActionMessages errors = new ActionMessages();

		//Obtain a handle on the resources directory.
		MessageResources messages = MessageResources
				.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
		String suspend=request.getParameter("suspend");
		String fileReference=request.getParameter("fileReference");
		if(null==fileReference||"".equals(fileReference))
		{
			request.setAttribute("fileReference", "please put a fileReference");
		}
		if(suspend.equals("completeSUS")){
			String ahlORohd=request.getParameter("ahlORohd");
			/**
			List list=TracerUtils.getAhlOROhlProperty(ahlORohd, fileReference);
			if(list.size()!=0){
				request.setAttribute("error", "there is not a valid file reference");
			}
			else{
				request.setAttribute("resultlist", list);
			}
		}
		else{
			String ahlORohd="ahl";
			List list=TracerUtils.getAhlOROhlProperty(ahlORohd, fileReference);
			if(list.size()!=0){
				request.setAttribute("error", "there is not a valid file reference");
			}
			else{
				request.setAttribute("resultlist", list);
			}*/
		}
		return mapping.findForward(TracingConstants.WORLDTRACER_SUS_RIT);
   }
}
