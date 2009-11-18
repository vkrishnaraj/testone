package com.bagnet.nettracer.tracing.actions;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class SecuredAction extends Action {

    public final ActionForward execute(ActionMapping mapping,
                                       ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) 
    								   throws Exception {

        ActionForward forward = null;
        
		HttpSession session = request.getSession(); // check session/user
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if(user == null || form == null) {
            //ActionErrors errors = new ActionErrors();
            //errors.add(ActionErrors.GLOBAL_MESSAGE,
            //           new ActionMessage("error.label.authRequired"));
            //saveErrors(request, errors);
			response.sendRedirect("logoff.do");
		} else {
            forward = securedExecute(mapping, form, request, response);
        }

        return forward;
    }

    public abstract ActionForward securedExecute(ActionMapping mapping,
                                             ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response);

}
