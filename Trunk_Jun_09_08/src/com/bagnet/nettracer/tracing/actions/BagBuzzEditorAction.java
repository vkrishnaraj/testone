package com.bagnet.nettracer.tracing.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.constant.TracingConstants;

import com.bagnet.nettracer.tracing.db.bagbuzz.BagBuzz;
import com.bagnet.nettracer.tracing.db.bagbuzz.Utils;

public class BagBuzzEditorAction extends Action{
	
	private static Logger logger = Logger.getLogger(WorldTracerAFAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		
		if (request.getParameter("bb_id") != null){
			BagBuzz bagbuzz = Utils.getBagBuzz(Long.parseLong(request.getParameter("bb_id")));
			if(bagbuzz != null && bagbuzz.getData() != null){
				request.setAttribute("bagbuzz", bagbuzz);
			}
		} else {
			BagBuzz bagbuzz = new BagBuzz();
			request.setAttribute("bagbuzz", bagbuzz);
		}
		
		return (mapping
				.findForward(TracingConstants.VIEW_BAGBUZZ_EDITOR));
		
	}

}
