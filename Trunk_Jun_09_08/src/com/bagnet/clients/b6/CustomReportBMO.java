package com.bagnet.clients.b6;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRParameter;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.bagnet.nettracer.exceptions.MissingRequiredFieldsException;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.claims.ClaimSettlement;
import com.bagnet.nettracer.tracing.dto.PPLC_DTO;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public class CustomReportBMO implements com.bagnet.nettracer.integrations.reports.CustomReportBMO {

	private static final Logger logger = Logger.getLogger(CustomReportBMO.class);

	public String createCustomReport(StatReportDTO srDTO, HttpServletRequest request, Agent user, String rootpath) {
		String creportdata = null;
		switch (srDTO.getCustomreportnum()) {
		case ReportingConstants.RPT_20_CUSTOM_6:
			creportdata = createPPLCReport(srDTO, ReportBMO.getCustomReport(6).getResource_key(), request, user,
					rootpath);
			break;
		default:
			break;

		}
		return creportdata;
	}

	private String createPPLCReport(StatReportDTO srDTO, String resource_key, HttpServletRequest request, Agent user,
			String rootpath) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		List<PPLC_DTO> dataList = new ArrayList<PPLC_DTO>();
		// TODO Auto-generated method stub

		Session sess = HibernateWrapper.getDirtySession().openSession();
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(
					"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
			parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, bundle);
			parameters.put(JRParameter.REPORT_LOCALE, new Locale(user.getCurrentlocale()));
			parameters.put("dateFormat", user.getDateformat() != null ? new SimpleDateFormat(user.getDateformat()
					.getFormat()) : TracingConstants.DISPLAY_DATEFORMAT);

			if (srDTO.getStarttime() == null || srDTO.getEndtime() == null || srDTO.getStarttime().trim().length() < 1
					|| srDTO.getEndtime().trim().length() < 1) {
				if (logger.isDebugEnabled()) {
					logger.debug("tried to do pplc report with no date parameters");
				}
				throw new MissingRequiredFieldsException();
			}

			Date startDate = DateUtils.convertToDate(srDTO.getStarttime(), user.getDateformat().getFormat(), user
					.getCurrentlocale());

			Date endDate = DateUtils.convertToDate(srDTO.getEndtime(), user.getDateformat().getFormat(), user
					.getCurrentlocale());

			parameters.put("startDate", startDate);
			parameters.put("endDate", endDate);

			String queryString = "from ClaimSettlement cs where cs.incident.status.status_ID != :closedStatus "
					+ " and cs.incident.createdate between :startDate and :endDate ";

			List<Integer> stationList = null;
			if (srDTO.getStation_ID() != null) {
				for (String temp : srDTO.getStation_ID()) {
					Integer id = Integer.parseInt(temp);
					if (id == 0) {
						stationList = null;
						break;
					}
					if (stationList == null)
						stationList = new ArrayList<Integer>();
					stationList.add(id);
				}
			}

			if (stationList != null) {
				queryString += " and cs.incident.stationassigned.station_ID in (:stationList) ";
			}

			queryString += " order by cs.incident.stationassigned.stationcode, cs.pplcDue asc";

			Query q = sess.createQuery(queryString);
			q.setDate("startDate", startDate);
			q.setDate("endDate", endDate);
			q.setInteger("closedStatus", TracingConstants.MBR_STATUS_CLOSED);

			if (stationList != null) {
				q.setParameterList("stationList", stationList);
			}

			List<ClaimSettlement> result = q.list();

			if (result.size() == 0) {
				return null;
			}

			for (ClaimSettlement cs : result) {
				PPLC_DTO pdto = new PPLC_DTO();

				pdto.setDueDate(cs.getPplcDue());
				pdto.setFirstName(cs.getFirstName());
				pdto.setLastName(cs.getLastName());
				pdto.setIncidentId(cs.getIncident().getIncident_ID());
				pdto.setOfferDueDate(cs.getOfferDue());
				pdto.setRecordLocator(cs.getIncident().getRecordlocator());
				pdto.setSentDate(cs.getPplcSent());
				pdto.setStationAssigned(cs.getIncident().getStationcode());

				dataList.add(pdto);
 			}

			return ReportBMO.getReportFile(dataList, parameters, "b6_pplc_age", rootpath, srDTO.getOutputtype(),
					request);

		} catch (Exception e) {
			logger.error("unable to create report " + e);
			return null;
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
	}

}
