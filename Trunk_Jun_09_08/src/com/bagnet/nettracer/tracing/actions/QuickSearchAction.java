package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.datasources.ScannerDataSource;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.dto.QuickSearchDTO;
import com.bagnet.nettracer.tracing.dto.ScannerDTO;
import com.bagnet.nettracer.tracing.dto.SearchIncident_DTO;
import com.bagnet.nettracer.tracing.forms.QuickSearchForm;
import com.bagnet.nettracer.tracing.forms.SearchIncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HistoryUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;
import com.bagnet.nettracer.tracing.history.HistoryContainer;
import com.bagnet.nettracer.tracing.history.HistoryObject;


public class QuickSearchAction extends Action {

	private static Logger logger = Logger.getLogger(QuickSearchAction.class);

	private static final String REDIRECT_TAG_NUMBER_SEARCH = "tagNumberSearch";
	private static final String REDIRECT_PNR_SEARCH = "pnrSearch";
	private static final String REDIRECT_NT_ID_SEARCH = "idSearch";
//	private static final String REDIRECT_BDO_SEARCH = "bdoSearch";
//	private static final String REDIRECT_EXPERT = "expertSearch";
	private static final String REDIRECT_SUCCESS = "success";
	// private static final String REDIRECT_INCIDENT_SEARCH = "incidentSearch";
	// private static final String REDIRECT_ONHAND_SEARCH = "onhandSearch";
	private static final String PATTERN_PNR = "^[A-Z0-9]{6}$";
	private static final String PATTERN_NT_ID = "^[A-Z]{3}[0-9A-Z]{2}%{0,1}\\d{0,7}%{0,1}\\d{0,7}$";
	private static final String PATTERN_BDO_ID = "^BDO%{0,1}\\d{0,10}%{0,1}\\d{0,9}$";
	private static final String PATTERN_PHONE = "^P:%{0,1}\\d{0,10}%{0,1}\\d{0,9}$";
	private static final String PATTERN_PHONE_DASH = "^%{0,1}\\d{0,3}-%{0,1}\\d{0,3}-%{0,1}\\d{0,4}$";
//	private static final String PATTERN_EXPERT_FUNCTION = "^[A-Z]{1,3}:";
//	private static final String EXPERT_INCIDENT = "I:";
//	private static final String EXPERT_OHD = "O:";
//	private static final String EXPERT_AGENT = "A:";
//	private static final String EXPERT_STATION = "S:";
//	private static final String EXPERT_MASS = "M:";
//	private static final String EXPERT_PICKUP = "P:";
//	private static final String EXPERT_AUDIT_INCIDENT = "AI:";
//	private static final String EXPERT_AUDIT_OHD = "AO:";
//	private static final String EXPERT_TAG = "T:";

	private static final int MAX_RESULTS = 10;

	// TODO: Action Permissions
	// TODO: UI JAVASCRIPT PERMISSIONS ??? NOT SURE WE NEED THESE
	// TODO: AGENTS JAVASCRIPT
	// TODO: STATION JAVASCRIPT

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(); // check session/user
		TracerUtils.checkSession(session);
		
		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		// TODO: Check all types of permissions
		QuickSearchForm theForm = (QuickSearchForm) form;
		String s = theForm.getSearch().trim().toUpperCase();
		QuickSearchDTO dto = new QuickSearchDTO();

		Pattern tenDigitPattern = Pattern.compile(LookupAirlineCodes.PATTERN_10_DIGIT_BAG_TAG);
		Pattern nineDigitPattern = Pattern.compile(LookupAirlineCodes.PATTERN_9_DIGIT_BAG_TAG);
		Pattern twoCharPattern = Pattern.compile(LookupAirlineCodes.PATTERN_8_CHAR_BAG_TAG);
		Pattern pnrPattern = Pattern.compile(PATTERN_PNR);
		Pattern ntIdPattern1 = Pattern.compile(PATTERN_NT_ID);
		Pattern phonePattern = Pattern.compile(PATTERN_PHONE);
		Pattern phoneDashPattern = Pattern.compile(PATTERN_PHONE_DASH);
//		Pattern bdoPattern = Pattern.compile(PATTERN_BDO_ID);

		boolean scanResults = false;
//		Boolean scanResultsObj = (Boolean) session.getAttribute("toggleTagSearch");
		Boolean scanResultsObj = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SCANNER_DATA, user);
		if (scanResultsObj != null && scanResultsObj == true) {
			scanResults = true;
		}

		logger.info(s);

		if (tenDigitPattern.matcher(s).find()) {
			logger.info("Tag Number... 1");
			if (TracerProperties.isTrue(user.getCompanycode_ID(),TracerProperties.RESERVATION_BY_BAGTAG) && !s.contains("%")) {
				dto.setPrepopType(1);
				dto.setPrepop(true);
			}
			netTracerTagSearch(user, s, dto, 10, scanResults);
			dto.setRedirect(REDIRECT_TAG_NUMBER_SEARCH);
		} else if (nineDigitPattern.matcher(s).find()) {
			logger.info("Tag Number... 2");
			if (TracerProperties.isTrue(user.getCompanycode_ID(),TracerProperties.RESERVATION_BY_BAGTAG) && !s.contains("%")) {
				dto.setPrepopType(1);
				dto.setPrepop(true);
			}
			netTracerTagSearch(user, s, dto, 9, scanResults);
			dto.setRedirect(REDIRECT_TAG_NUMBER_SEARCH);
		} else if (twoCharPattern.matcher(s).find()) {
			logger.info("Tag Number... 3");
			if (TracerProperties.isTrue(user.getCompanycode_ID(),TracerProperties.RESERVATION_BY_BAGTAG) && !s.contains("%")) {
				dto.setPrepopType(1);
				dto.setPrepop(true);
			}
			netTracerTagSearch(user, s, dto, 8, scanResults);
			dto.setRedirect(REDIRECT_TAG_NUMBER_SEARCH);
		} else if (pnrPattern.matcher(s).find()) {
			logger.info("PNR... 4");
			netTracerPnrSearch(user, s, dto);
			if (TracerProperties.isTrue(user.getCompanycode_ID(),TracerProperties.RESERVATION_POPULATE_INCIDENT_ON) && !s.contains("%")) {
				dto.setPrepop(true);
			}
			dto.setRedirect(REDIRECT_PNR_SEARCH);
//		} else if (bdoPattern.matcher(s).find()) {
//			logger.info("BDO ID... 5");
//			
//			dto.setRedirect(REDIRECT_BDO_SEARCH);
		} else if (phonePattern.matcher(s).find()) {
			logger.info("Phone... 5");
			phoneNumberSearch(user, s, dto);
			dto.setRedirect(REDIRECT_NT_ID_SEARCH);
		} else if (phoneDashPattern.matcher(s).find()) {
			logger.info("Phone Dashes... 6");
			phoneNumberSearch(user, s, dto);
			dto.setRedirect(REDIRECT_NT_ID_SEARCH);
		} else if (ntIdPattern1.matcher(s).find()) {
			logger.info("NT ID... 7");
			netTracerIdSearch(user, s, dto);
			dto.setRedirect(REDIRECT_NT_ID_SEARCH);
		} else {
			// ASSUMING ALL OTHER SEARCHES ARE FOR IDs
			netTracerIdSearch(user, s, dto);
			dto.setRedirect(REDIRECT_NT_ID_SEARCH);
			logger.info("Unknown search criteria function...: " + s);
		}

		theForm.setDto(dto);
		request.setAttribute("QuickSearchDTO", dto);
		logger.info("Scanning for user's recent history");
		HistoryUtils.getHistoryContainer(theForm, session);
//		HistoryContainer hcl =(HistoryContainer)session.getAttribute("historyContainer");
//		ArrayList<HistoryObject> Hlist=hcl.getRevNewestItems(10);
//		
//		
//		theForm.setHistCon(Hlist);
		
		return (mapping.findForward(REDIRECT_SUCCESS));
	}

	private void netTracerPnrSearch(Agent user, String s, QuickSearchDTO dto) {
		dto.setDisplayIncList(true);
		dto.setDisplayOhdList(true);
		dto.setDisplayScanList(false);

		// GET LIST OF TAG FORMATS:

		IncidentBMO iBMO = new IncidentBMO();
		SearchIncident_DTO siDTO = new SearchIncident_DTO();
		siDTO.setRecordlocator(s.trim());

		OhdBMO oBMO = new OhdBMO();
		SearchIncidentForm oDTO = new SearchIncidentForm();
		oDTO.setRecordlocator(s.trim());

		// Search Incidents for ID or WT ID
		queryIncidents(user, dto, iBMO, siDTO);

		// Search On-hands for ID or WT ID
		queryOhds(user, dto, oBMO, oDTO);
	}

	private void netTracerTagSearch(Agent user, String s, QuickSearchDTO dto, int type, boolean scanSearch) {
		logger.info("Step 1: Tag Number Search Begin");
		dto.setDisplayIncList(true);
		dto.setDisplayOhdList(true);
		dto.setDisplayScanList(false);

		// GET LIST OF TAG FORMATS:

		IncidentBMO iBMO = new IncidentBMO();
		SearchIncident_DTO siDTO = new SearchIncident_DTO();
		siDTO.setIntelligentTagSearch(true);
		siDTO.setIntelligentTagSearchType(type);
		siDTO.setClaimchecknum(s);
		
		Calendar tagSearchRangeStart = new GregorianCalendar();
		tagSearchRangeStart.add(Calendar.DAY_OF_MONTH, -60);
		dto.setLimitedStartDate(tagSearchRangeStart.getTime());
		
		Calendar tagSearchRangeEnd = new GregorianCalendar();
		tagSearchRangeEnd.add(Calendar.DAY_OF_MONTH, 1);
		dto.setLimitedEndDate(tagSearchRangeEnd.getTime());
		
		TracerDateTime.getGMTDate();
		
		String sDate = DateUtils.formatDate(tagSearchRangeStart.getTime(), user.getDateformat().getFormat(), null, TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone())
				.getTimezone()));
		String eDate = DateUtils.formatDate(tagSearchRangeEnd.getTime(), user.getDateformat().getFormat(), null, TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone())
				.getTimezone()));
		
		logger.info(sDate);
		logger.info(eDate);
		
		siDTO.setS_createtime(sDate);
		siDTO.setE_createtime(eDate);


		OhdBMO oBMO = new OhdBMO();
		SearchIncidentForm oDTO = new SearchIncidentForm();
		oDTO.setIntelligentTagSearch(true);
		oDTO.setIntelligentTagSearchType(type);
		oDTO.setClaimchecknum(s);
		oDTO.setS_createtime(sDate);
		
		oDTO.setE_createtime(eDate);
		
		
		

		// Search Incidents for ID or WT ID
		logger.info("Step 2: Query Incidents");
		queryIncidents(user, dto, iBMO, siDTO);

		// Search On-hands for ID or WT ID
		logger.info("Step 3: Query OHDs");
		queryOhds(user, dto, oBMO, oDTO);
		
		if (scanSearch) {
			logger.info("Step 4a: Query Scanners");
			dto.setDisplayScanList(true);
			try {
				ScannerDataSource sds = (ScannerDataSource) SpringUtils.getBean(SpringUtils.SCANNER_DATA_SOURCE);

				if (dto.getStartDate() == null) {
					GregorianCalendar cal = new GregorianCalendar();
					cal.add(Calendar.DAY_OF_MONTH, -2);
					dto.setStartDate(cal.getTime());
				}
				
				if (dto.getEndDate() == null) {
					GregorianCalendar cal = new GregorianCalendar();
					cal.add(Calendar.DAY_OF_MONTH, 1);
					dto.setEndDate(cal.getTime());
				}
				
				ScannerDTO scanData = sds.getScannerData(dto.getStartDate(), dto.getEndDate(), s);
				dto.setSList(scanData.getScannerDataDTOs());
			} catch (Exception e) {
				dto.setScanError("Unknown scanning error...");
			}
			logger.info("Step 4b: Complete Scanner Query");
		}
	}

	private void netTracerIdSearch(Agent user, String s, QuickSearchDTO dto) {
		IncidentBMO iBMO = new IncidentBMO();
		SearchIncident_DTO siDTO = new SearchIncident_DTO();

		OhdBMO oBMO = new OhdBMO();
		SearchIncidentForm oDTO = new SearchIncidentForm();

		siDTO.setIncident_ID(s);
		oDTO.setOhd_id(s);

		if (s.length() <= 11) {
			siDTO.setWt_id(s);
			siDTO.setWtConditionOr(true);
			oDTO.setWt_id(s);
			oDTO.setWtConditionOr(true);
		}

		// Search Incidents for ID or WT ID
		queryIncidents(user, dto, iBMO, siDTO);

		// Search On-hands for ID or WT ID
		queryOhds(user, dto, oBMO, oDTO);
	}

	private void phoneNumberSearch(Agent user, String s, QuickSearchDTO dto) {
		IncidentBMO iBMO = new IncidentBMO();
		SearchIncidentForm siDTO = new SearchIncidentForm();

		siDTO.setPhone(s.replaceAll("[^0-9]", ""));

		// Search Incidents for ID or WT ID
		customQueryIncidents(user, dto, iBMO, siDTO);
	}

	private void queryOhds(Agent user, QuickSearchDTO dto, OhdBMO oBMO, SearchIncidentForm oDTO) {
		logger.info("  Query OHD: Start");
		dto.setDisplayOhdList(true);

		List resultlist = oBMO.customQuery(oDTO, user, MAX_RESULTS + 1, 0, false, true);
		if (resultlist != null) {
			if (resultlist.size() > MAX_RESULTS) {
				dto.setIMore(true);
				resultlist.remove(MAX_RESULTS);
			}
			dto.setOList((List<OHD>) resultlist);
	
			OHD ic = null;
			for (int i = 0; i < resultlist.size(); i++) {
				ic = (OHD) resultlist.get(i);
				ic.set_DATEFORMAT(user.getDateformat().getFormat());
				ic.set_TIMEFORMAT(user.getTimeformat().getFormat());
				ic.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
			}
		}
		logger.info("  Query OHD: Stop");
	}

	private void queryIncidents(Agent user, QuickSearchDTO dto, IncidentBMO iBMO, SearchIncident_DTO siDTO) {
		logger.info("  Query Incident: Start");
		dto.setDisplayIncList(true);

		List resultlist = iBMO.findIncident(siDTO, user, MAX_RESULTS + 1, 0, false, true);
		if (resultlist != null) {
			if (resultlist.size() > MAX_RESULTS) {
				dto.setIMore(true);
				resultlist.remove(MAX_RESULTS);
			}
			dto.setIList(resultlist);

			Incident ic = null;
			for (int i = 0; i < resultlist.size(); i++) {
				ic = (Incident) resultlist.get(i);
				ic.set_DATEFORMAT(user.getDateformat().getFormat());
				ic.set_TIMEFORMAT(user.getTimeformat().getFormat());
				ic.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
			}
		}
		logger.info("  Query Incident: Stop");
	}

	private void customQueryIncidents(Agent user, QuickSearchDTO dto, IncidentBMO iBMO, SearchIncidentForm siDTO) {
		logger.info("  Query Incident: Start");
		dto.setDisplayIncList(true);

		List resultlist = iBMO.customQuery(siDTO, user, MAX_RESULTS + 1, 0, false, "4", true);
		if (resultlist != null) {
			if (resultlist.size() > MAX_RESULTS) {
				dto.setIMore(true);
				resultlist.remove(MAX_RESULTS);
			}
			dto.setIList(resultlist);

			Incident ic = null;
			for (int i = 0; i < resultlist.size(); i++) {
				ic = (Incident) resultlist.get(i);
				ic.set_DATEFORMAT(user.getDateformat().getFormat());
				ic.set_TIMEFORMAT(user.getTimeformat().getFormat());
				ic.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
			}
		}
		logger.info("  Custom Query Incident: Stop");
	}
}
