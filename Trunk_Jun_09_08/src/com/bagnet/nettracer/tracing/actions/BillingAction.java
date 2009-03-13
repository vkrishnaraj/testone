/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.dto.BillingDTO;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.forms.BillingForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class BillingAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)) return (mapping
				.findForward(TracingConstants.NO_PERMISSION));

		BillingForm daform = (BillingForm) form;

		ServletContext sc = getServlet().getServletContext();
		String reportpath = sc.getRealPath("/");
		ActionMessages errors = new ActionMessages();

		if (request.getParameter("create") != null) {
			String reportfile = createReport(reportpath, daform, user, request);
			if (reportfile == null || reportfile.equals("")) {
				ActionMessage error = new ActionMessage("message.nodata");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} else {
				request.setAttribute("reportfile", reportfile);
				request.setAttribute("outputtype", Integer.toString(daform.getOutputtype()));
			}
			return (mapping.findForward(TracingConstants.BILLING_REPORT));
		}
		return (mapping.findForward(TracingConstants.BILLING_REPORT));
	}

	public static String createReport(String reportpath, BillingForm daform, Agent user,
			HttpServletRequest req) {

		Session sess = null;
		try {
			
			sess = HibernateWrapper.getSession().openSession();
			Query q = generateQuery(daform, sess, user);
			
			if (q == null) {
				return null;
			}
			
			List alllist = q.list();

			List reportList = new ArrayList();
			for (int i = 0; i < alllist.size(); i++) {
				Object[] a = (Object[]) alllist.get(i);

				BillingDTO dto = new BillingDTO();
				dto.setCompanyCode("" + a[0]);
				dto.setReportType(
						IncidentUtils.retrieveItemTypeWithId(((Integer) a[1]).intValue(),
						user.getCurrentlocale()).getDescription()
				);
				dto.setCount("" + ((Long) a[2]).longValue());

				reportList.add(dto);
			}

			Map parameters = new HashMap();
			ResourceBundle myResources = ResourceBundle.getBundle(
					"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user
							.getCurrentlocale()));
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);

			parameters.put("sdate", daform.getStarttime());
			parameters.put("edate", daform.getEndtime());
			parameters.put("tran_value", "1.0");

			ReportBMO bmo = new ReportBMO(req);
			return bmo.getReportFile(reportList, parameters, "billing_report", reportpath, daform
					.getOutputtype());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Query generateQuery(BillingForm daform, Session sess, Agent user) {
		StatReportDTO srDTO = new StatReportDTO();
		srDTO.setStarttime(daform.getStarttime());
		srDTO.setEndtime(daform.getEndtime());

		
		ArrayList dateal = null;
		
		TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());
		if ((dateal = ReportBMO.calculateDateDiff(srDTO,tz,user)) == null) {
			return null;
		}
		String sql = " SELECT billing.companyCode, incident.itemtype.itemType_ID, count(distinct billing.incident.incident_ID) ";
		sql += " from com.bagnet.nettracer.tracing.db.Billing billing";
		sql += " join billing.incident incident ";
		sql += " join billing.incident.itemtype itemtype ";
		sql += " where 1=1 ";

		if (daform.getCompanycode_ID() != null && !daform.getCompanycode_ID().equals("0")) sql += " and billing.companyCode = '"
				+ daform.getCompanycode_ID() + "' ";
		if (daform.getItemType_ID() != 0) sql += " and incident.itemtype.itemType_ID = "
				+ daform.getItemType_ID();
		Date sdate = null, edate = null;
		Date sdate1 = null, edate1 = null; // add one for timezone
		Date stime = null; // time to compare (04:00 if eastern, for example)
		String dateq = "";
		
		sdate = (Date)dateal.get(0);sdate1 = (Date)dateal.get(1);
		edate = (Date)dateal.get(2);edate1 = (Date)dateal.get(3);
		stime = (Date)dateal.get(4);


		if (sdate != null && edate != null) {
			if (sdate.equals(edate)) {
				// need to add the timezone diff here
				dateq = " and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
						+ " or (incident.createdate= :startdate1 and incident.createtime <= :starttime))";

				edate = null;
			} else {

				// first get the beginning and end dates using date and time, then get
				// dates in between
				dateq = " and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
						+ " or (incident.createdate= :enddate1 and incident.createtime <= :starttime)"
						+ " or (incident.createdate > :startdate and incident.createdate <= :enddate))";
			}
		} else if (sdate != null) {
			dateq = " and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
					+ " or (incident.createdate= :startdate1 and incident.createtime <= :starttime))";
			edate = null;
		}
		
		
		if (!dateq.equals("")) sql += dateq;
		//sql += " and billing.status_change_time is not null ";
		sql += " group by billing.companyCode, incident.itemtype.itemType_ID ";
		sql += " order by billing.companyCode, incident.itemtype.itemType_ID ";
		Query q = sess.createQuery(sql);
		
		if (sdate != null) {
			q.setDate("startdate", sdate);
			if (edate == null) q.setDate("startdate1", sdate1);
			q.setTime("starttime", stime);
		}
		if (edate != null) {
			q.setDate("enddate1", edate1);
			q.setDate("enddate", edate);
		}
		return q;
	}
}