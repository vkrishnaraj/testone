/*
 * Created on Feb 14, 2007
 *
 * matt
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue;
import com.bagnet.nettracer.tracing.db.wtq.WtqEraseActionFile;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;
import com.bagnet.nettracer.wt.WorldTracerUtils;

public class WorldTracerCount extends Action {
	private static Logger logger = Logger.getLogger(WorldTracerCount.class);

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

		ActionMessages errors = new ActionMessages();

		Agent user = (Agent) session.getAttribute("user");
		String wt_http = WorldTracerUtils.getWt_url(user.getCompanycode_ID());
		String wt_url = "http://" + wt_http + "/";
		// if (!user.getStation().getCompany().getVariable().isWTEnabled())
		// return (mapping.findForward(TracingConstants.NO_PERMISSION));

		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1)
				+ ".do", user)
				&& !UserPermissions
						.hasPermission(
								TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_ACTION_FILES,
								user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));

		Station agent_station = null;
		if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CBRO_VIEW, user)) {
			if (request.getParameter("cbroStation") != null
					&& !((String) request.getParameter("cbroStation")).equals(""))
				session.setAttribute("cbroStationID", request.getParameter("cbroStation"));
			if (session.getAttribute("cbroStationID") != null) {
				agent_station = StationBMO.getStation((String) session.getAttribute("cbroStationID"));
			}
			else {
				agent_station = user.getStation();
			}
		} else {
			agent_station = user.getStation();
		}


		// if got here and still no action, default to viewaction
		/** ***** view action files ********* */
		String wt_type = request.getParameter("viewaction");
		request.setAttribute("viewaction", wt_type);
		String action_file_type = request.getParameter("type");
		request.setAttribute("type", action_file_type);
		String day = request.getParameter("d");
		request.setAttribute("d", day);
		String count=request.getParameter("count");
		request.setAttribute("count", count);
		int second_count=0;
		
		if(null!=count&&!count.trim().equals("")){
			second_count=Integer.parseInt(request.getParameter("count"))-1;
		}
		
		int int_count=-1;
		if (wt_type == null || wt_type.equals("") || wt_type.equals("null"))
			wt_type = "FW";
		// String day = request.getParameter("d");
		if (day == null || day.equals("") || day.equals("null"))
			day = "1";

		if (wt_type == null || wt_type.equals(""))
			wt_type = "FW";
		
		request.setAttribute("wt_type", wt_type.toUpperCase());
		
		// check if deleting action file
		int rowcount=-1;
		
		int rowsperpage=0;
		request.setAttribute("rowsperpage", rowsperpage);
		int currpage=0;
		request.setAttribute("currpage", currpage);
		request.setAttribute("pages", null);
		if(null!=wt_type&&!wt_type.trim().equals("")){
			
			if(wt_type.equalsIgnoreCase("cw")){
				//List countlist=WorldTracerUtils.countActionFile();
				List countlist=WorldTracerUtils.countActionFile(user.getCompanycode_ID(), agent_station.getWt_stationcode());
				request.setAttribute("countlist", countlist);
				}
			if (request.getParameter("delete") != null
					&& request.getParameter("delete").length() > 0) {
				Worldtracer_Actionfiles waf = WorldTracerUtils.findActionFileByID(Integer.parseInt(request.getParameter("delete")));
				boolean deleted = false;
				if(waf != null) {
					//queue for WT deletion and delete from our database
					WtqEraseActionFile wtq = new WtqEraseActionFile();
					wtq.setAgent(user);
					wtq.setCreatedate(TracerDateTime.getGMTDate());
					wtq.setAf_id(waf.generateId());
					WorldTracerQueueUtils.createOrReplaceQueue(wtq);
					deleted = WorldTracerUtils.deleteActionFiles(request
							.getParameter("delete"));
				}
				List countlist=WorldTracerUtils.countActionFile(user.getCompanycode_ID(), agent_station.getWt_stationcode());
				request.setAttribute("countlist", countlist);
				int_count=WorldTracerUtils.countOneTypeActionFile(action_file_type, day,user.getCompanycode_ID(), agent_station.getWt_stationcode());
			    request.setAttribute("count", int_count-1);
				if (!deleted) {
					ActionMessage error = new ActionMessage("message.nodata");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				} else {
					request.setAttribute("afdeleted", "1");
				}
			}
			
			if(count!=null&&!count.trim().equals("")){
				rowcount=Integer.parseInt(count);
			}

			rowsperpage = request.getParameter("rowsperpage") != null ? Integer
					.parseInt(request.getParameter("rowsperpage"))
					: TracingConstants.ROWS_PER_PAGE;
					
			if (rowsperpage < 1)
				rowsperpage = TracingConstants.ROWS_PER_PAGE;
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
			int totalpages = 0;
			
			currpage = request.getParameter("currpage") != null ? Integer
					.parseInt(request.getParameter("currpage")) : 0;
			if (request.getParameter("nextpage") != null
					&& request.getParameter("nextpage").equals("1"))
				currpage++;
			if (request.getParameter("prevpage") != null
					&& request.getParameter("prevpage").equals("1"))
				currpage--;

			request.setAttribute("currpage", Integer.toString(currpage));
			
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}
			List displaylist=null;
			if(null!=action_file_type&&!action_file_type.trim().equals("")){
				displaylist=WorldTracerUtils.findActionFiles(action_file_type, day,user.getCompanycode_ID(), agent_station.getStationcode() , rowsperpage, currpage);

			}
			if(null!=action_file_type&&null!=day){
				displaylist=WorldTracerUtils.findActionFiles(action_file_type, day,user.getCompanycode_ID(), agent_station.getStationcode() , rowsperpage, currpage);
			}
			if (currpage + 1 == totalpages)
				request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList al = new ArrayList();
				for (int i = 0; i < totalpages; i++) {
					al.add(Integer.toString(i));
				}
				request.setAttribute("pages", al);
			}
			if(null==request.getAttribute("delete")||request.getAttribute("delete").toString().trim().equals("")){
				request.setAttribute("delete", second_count);
			}
			
			
			request.setAttribute("wt_id_specific", "1");
			
			request.setAttribute("displaylist", displaylist);
		}
		return mapping.findForward(TracingConstants.VIEW_WORLDTRACER_ACTION_FILES_COUNT);	
	}
}
