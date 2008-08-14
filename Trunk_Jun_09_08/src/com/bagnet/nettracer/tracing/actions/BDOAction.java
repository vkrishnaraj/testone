/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.WT_Queue;
import com.bagnet.nettracer.tracing.forms.BDOForm;
import com.bagnet.nettracer.tracing.utils.BDOUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class BDOAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		if (session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		Agent user = (Agent) session.getAttribute("user");

		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1)
				+ ".do", user))
			return (mapping.findForward(TracingConstants.LOGON));

		BDOForm theform = (BDOForm) form;

		ActionMessages errors = new ActionMessages();

		// ohd id or mbr id present in the request parameter
		String ohd = request.getParameter("ohd_id");
		String incident = request.getParameter("mbr_id");
		
		// if no ids, a input box will show to enter the mbr or ohd id
		if (request.getParameter("mbr") != null) {
			request.setAttribute("mbr", "1");
		} else if (request.getParameter("onhand") != null) {
			request.setAttribute("onhand", "1");
		} else {
			// System.out.println("wwwwwwwwwwwwwwwwwwwwww");
			request.setAttribute("show_word_for", "1"); // show "BDO for" in
			// header
			// String wt_id = "";
			// -------------------------------------------------------------------------
			/**
			 * if (theform.getOhd().getWt_id() != null){ wt_id =
			 * theform.getOhd().getWt_id();
			 * 
			 * request.setAttribute("wt_id", "a"); } if
			 * (theform.getIncident().getWt_id() != null){ wt_id =
			 * theform.getIncident().getWt_id();
			 * 
			 * request.setAttribute("wt_id", "b"); }
			 */
			// List list=new ArrayList();
			
			String bdo_id2 = request.getParameter("bdo_id");

			int bdo_id = 0;

			if (bdo_id2 != null && !"".equals(bdo_id2)) {

				bdo_id = Integer.parseInt(bdo_id2);

				Iterator it = BDOUtils.findWt_id(bdo_id);

				while (it.hasNext()) {

					BDO bdo = (BDO) it.next();

					if (null != bdo.getIncident()
							&& !bdo.getIncident().toString().equals("")) {
						if (null != bdo.getIncident().getWt_id()
								&& !"".equals(bdo.getIncident().getWt_id())) {
							request.setAttribute("wt_id", bdo.getIncident()
									.getWt_id());
						}
					} else {
						if (null != bdo.getOhd()
								&& !"".equals(bdo.getOhd().toString().trim())) {
							if (null != bdo.getOhd().getWt_id()
									&& !"".equals(bdo.getOhd().getWt_id()
											.trim())) {
								request.setAttribute("wt_id", bdo.getOhd()
										.getWt_id());
							}
						}
					}

				}
				// BDOForm theform2 = (BDOForm) form;
			} 
		}

		if (theform.getDelivercompany_ID() > 0) {

			List servicelevels = null;

			if (request.getParameter("changeservice") != null
					&& request.getParameter("changeservice").equals("1")
					&& request.getParameter("bdo_id") != null) {
				BDO bdo = BDOUtils.getBDOFromDB(new Integer(request
						.getParameter("bdo_id")));
				if (bdo.getDelivercompany().getDelivercompany_ID() == theform
						.getDelivercompany_ID()) {
					servicelevels = BDOUtils.getServiceLevels(theform
							.getDelivercompany_ID(), bdo.getServicelevel());
				} else {
					servicelevels = BDOUtils.getServiceLevels(theform
							.getDelivercompany_ID());
				}
			} else {
				servicelevels = BDOUtils.getServiceLevels(theform
						.getDelivercompany_ID());
			}
			// AJAX CALL
			request.setAttribute("servicelevels", servicelevels);
		}

		// change up service level
		if (request.getParameter("changeservice") != null
				&& request.getParameter("changeservice").equals("1")) {

			return (mapping.findForward(TracingConstants.AJAX_SERVICELEVEL));
		}

		List list = new ArrayList(BDOUtils.getDeliveryCompanies(theform
				.getStation().getStation_ID()));
		if (list != null)
			request.setAttribute("delivercompanies", list);

		if (request.getParameter("receipt") != null
				&& request.getParameter("bdo_id") != null) {
			request.setAttribute("bdo_id", request.getParameter("bdo_id"));
			return (mapping.findForward(TracingConstants.RECEIPT_PARAMS));
		}

		// save bdo
		if (request.getParameter("save") != null) {

			request.setAttribute("showbdo", "1");

			// if there are no bags, then don't deliver, unless it is ohd
			if (theform.getItemlist().size() == 0
					&& theform.getIncident_ID() != null) {
				ActionMessage error = new ActionMessage("error.bdo_no_bag");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return (mapping.findForward(TracingConstants.BDO_MAIN));
			}

			String[] bagchosen = null;

			// make sure user checked item to be inserted if choosebags is 1
			if (theform.getChoosebags() == 1) {
				// need to select
				bagchosen = request.getParameterValues("bagchosen");
				if (bagchosen == null) {
					ActionMessage error = new ActionMessage(
							"error.bdo_choose_bag");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
					return (mapping.findForward(TracingConstants.BDO_MAIN));
				}
			}

			// do insert
			ActionMessage error = BDOUtils.insertBDO(theform, bagchosen);
			ohd = theform.getOHD_ID(); // we will either have ohd or incident
			incident = theform.getIncident_ID();

			if (error != null) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return (mapping.findForward(TracingConstants.BDO_MAIN));
			} else {
				request.setAttribute("inserted", "1");
				request.setAttribute("showprint", "1");
			}
		}
		if (request.getParameter("savetowt") != null ) {
			request.setAttribute("showbdo", "1");
			request.setAttribute("inserted", "1");
			request.setAttribute("showprint", "1");
			WT_Queue wtq = new WT_Queue();
			wtq.setAgent(user);
             
			wtq.setCreatedate(TracerDateTime.getGMTDate());
			wtq.setType_id(String.valueOf(theform.getBDO_ID()));
			wtq.setWt_stationcode(user.getStation().getWt_stationcode());

			wtq.setType("bdo");

			wtq.setQueue_status(TracingConstants.LOG_NOT_RECEIVED);
			WorldTracerQueueUtils.saveBdoobj(theform,wtq, user);
			
			return (mapping.findForward(TracingConstants.BDO_MAIN));
			}
		// user wants to create a new bdo
		if (request.getParameter("createnewbdo") != null) {
			BDOUtils.createNewBDO(ohd, incident, theform, request);
			request.setAttribute("showbdo", "1");

			return (mapping.findForward(TracingConstants.BDO_MAIN));
		}

		// user wants to update bdo
		if (request.getParameter("bdo_id") != null) {
			if (!BDOUtils.findBDO(Integer.parseInt(request
					.getParameter("bdo_id")), theform, request)) {
				ActionMessage error = new ActionMessage("error.bdo_not_found");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} else {
				request.setAttribute("showbdo", "1");
				request.setAttribute("showprint", "1");
				return (mapping.findForward(TracingConstants.BDO_MAIN));
			}

		}

		// show list of bdo after entering bdo section
		if (ohd != null) {
			ohd = ohd.toUpperCase().trim();

			// retrieve all bdos in a list for this ohd
			if (!BDOUtils.findBDOList(ohd, null, theform, request)) {

				ActionMessage error = new ActionMessage("error.no.onhandreport");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				request.setAttribute("onhand", "1");
			} else {
				request.setAttribute("showbdolist", "1");
			}
		} else if (incident != null) {
			// user came in for mbr bdo
			incident = incident.toUpperCase().trim();

			// retrieve all bdos in a list for this incident
			if (!BDOUtils.findBDOList(null, incident, theform, request)) {
				ActionMessage error = new ActionMessage("error.noincident");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				request.setAttribute("mbr", "1");
			} else {
				request.setAttribute("showbdolist", "1");
			}
		}

		return (mapping.findForward(TracingConstants.BDO_MAIN));
	}
}