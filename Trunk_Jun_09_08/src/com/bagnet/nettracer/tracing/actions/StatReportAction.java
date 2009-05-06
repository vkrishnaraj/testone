/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.forms.StatReportForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * @author Matt

 */
public class StatReportAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		if (session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		Agent user = (Agent) session.getAttribute("user");

		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)) return (mapping
				.findForward(TracingConstants.NO_PERMISSION));

		List codes = AdminUtils.getDistinctLocaleCompanyCodes(user.getStation().getCompany()
				.getCompanyCode_ID(), 0);
		//add to the loss codes
		request.setAttribute("losscodes", codes);

		ServletContext sc = getServlet().getServletContext();
		String reportpath = sc.getRealPath("/");

		StatReportForm daform = (StatReportForm) form;

		String reportnum = request.getParameter("reportnum");

		String customreportnum = request.getParameter("customreportnum");

		if (reportnum == null) reportnum = Integer.toString(ReportingConstants.RPT_3);
		
		request.setAttribute("reportnum", reportnum);
		
		request.setAttribute("customReports", ReportBMO.getAllCustomReports());
		request.setAttribute("company", user.getStation().getCompany().getCompanyCode_ID().toLowerCase());

		if (customreportnum != null) request.setAttribute("customreportnum", customreportnum);
		
		BagService bs = new BagService();

		/** ****************** handle requests ******************** */

		// do search
		ActionMessages errors = new ActionMessages();
		if (request.getParameter("create") != null) {
			String reportfile = null;
			StatReportDTO srDTO = new StatReportDTO();
			BeanUtils.copyProperties(srDTO, daform);
			ReportBMO rBMO = new ReportBMO(request);
			if ((daform.getStarttime() != null && daform.getStarttime().length() > 0) || (daform.getCstarttime() != null && daform.getCstarttime().length() > 0)) {

				reportfile = rBMO.createReport(reportpath, srDTO, user);
			}
			else {
				rBMO.setErrormsg("error.missingRequired");
			}
			
			if (reportfile == null || reportfile.equals("")) {
				
				if (rBMO.getErrormsg() != null && rBMO.getErrormsg().length() > 0) {
					ActionMessage error = new ActionMessage(rBMO.getErrormsg());
					errors.add(ActionMessages.GLOBAL_MESSAGE,error);
				} else {
					ActionMessage error = new ActionMessage("message.nodata");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				}
				
				saveMessages(request, errors);
			} else {
				request.setAttribute("reportfile", reportfile);
				if (request.getAttribute("outputtype") == null) {
					request.setAttribute("outputtype", Integer.toString(daform.getOutputtype()));
				}
			}

			return (mapping.findForward(TracingConstants.STAT_REPORT_MAIN));
		} else {
			daform.init();
		}

		daform.setReportnum(Integer.valueOf(reportnum).intValue());

		return (mapping.findForward(TracingConstants.STAT_REPORT_MAIN));
	}
}