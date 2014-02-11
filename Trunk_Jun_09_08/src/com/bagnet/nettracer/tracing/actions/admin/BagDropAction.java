package com.bagnet.nettracer.tracing.actions.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

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
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import com.bagnet.nettracer.exceptions.InvalidDateRangeException;
import com.bagnet.nettracer.exceptions.InvalidStationException;
import com.bagnet.nettracer.exceptions.MissingRequiredFieldsException;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BagDrop;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.dto.BagDropDTO;
import com.bagnet.nettracer.tracing.forms.BagDropForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagDropUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * @author Loupas
 *
 */
public class BagDropAction extends Action{

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(BagDropAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		ActionMessages errors = new ActionMessages();
		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)) return (mapping
				.findForward(TracingConstants.NO_PERMISSION));
		
		
		/**
		 * As part of the legacy CBRO code, the cbroStationId is passed through the session which as part of the station validation,
		 * loads the station object from the database.  For the BagDropDTO we need an arrivalStation to search against, using the attribute
		 * to cache the CBRO station so we do not have to perform another station load from the database.
		 */
		Station cbroStation = handleCBROStation(request, user);
		
		BagDropForm bdform = (BagDropForm)form;
		request.setAttribute("bagDropForm", bdform);
		session.setAttribute("bagDropForm", bdform);
		BagDropDTO dto = bdform.getDto();
		
		
		if (request.getParameter("search") != null){
			bdform.setBagDropList(getPaginatedList(request,bdform,user,dto,cbroStation, false));
			if(bdform.getBagDropList() !=null &&bdform.getBagDropList().size() == 1){
				/** if single response, auto load the edit page **/
				response.sendRedirect("bagDrop.do?editId=" + bdform.getBagDropList().get(0).getId());
			}
			bdform.setAvgTimeToCarousel(BagDropUtils.avgTimeToCarousel(user.getCompanycode_ID(), getCurrentStation(cbroStation, user)));
			return mapping.findForward(TracingConstants.BAGDROP);
		}
		
		if (request.getParameter("getFlightData") != null) {

			if(bdform.getPreviousDTO() != null && bdform.getPreviousDTO().getStartScheduleArrivalDate() != null && bdform.getPreviousDTO().getEndScheduleArrivalDate() != null){
				dto.setStartScheduleArrivalDate((Date)bdform.getPreviousDTO().getStartScheduleArrivalDate().clone());
				dto.setEndScheduleArrivalDate((Date)bdform.getPreviousDTO().getEndScheduleArrivalDate().clone());
			} else {
				/* previous dto not found, default to current day */
				TimeZone timeZone = TimeZone
						.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());
				dto.setStartScheduleArrivalDate(BagDropUtils.getStartOfDayGMT(timeZone, 0));
				dto.setEndScheduleArrivalDate(BagDropUtils.getStartOfDayGMT(timeZone, 1));
			}

			try {
				BagDropUtils.refreshFlightInfo(user, getCurrentStation(cbroStation, user), dto.getStartScheduleArrivalDate(), dto.getEndScheduleArrivalDate());
			} catch (InvalidStationException ise){
				ActionMessage error = new ActionMessage("bagdrop.error.invalidstation");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} catch (Exception e) {
				ActionMessage error = new ActionMessage("bagdrop.error.flightinfo");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
			bdform.setBagDropList(getPaginatedList(request,bdform,user,dto,cbroStation, false));
			bdform.setAvgTimeToCarousel(BagDropUtils.avgTimeToCarousel(user.getCompanycode_ID(), getCurrentStation(cbroStation, user)));
			return mapping.findForward(TracingConstants.BAGDROP);
		}
		
		if (request.getParameter("editId") != null){
			long bagDropID = new Long(request.getParameter("editId"));
			bdform.setEditBagDrop(BagDropUtils.getBagDropById(user,bagDropID));
			bdform.setAuditList(BagDropUtils.getAuditBagDropList(user, bagDropID));
			/** set bagdrop date/time on form **/
			if(bdform.getEditBagDrop() != null && bdform.getEditBagDrop().getBagDropTime() != null){
				bdform.setEditBagDropDate(bdform.getEditBagDrop().getDispBagDropDate());
				bdform.setEditBagDropTime(bdform.getEditBagDrop().getDispBagDropTime());
			} else {
				/** Default date to current day and time to empty string **/
				Date today = DateUtils.convertToGMTDate(new Date());
				TimeZone timeZone = TimeZone
						.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());
				bdform.setEditBagDropDate(DateUtils.formatDate(today, user.getDateformat().getFormat(), null, timeZone));
				bdform.setEditBagDropTime("");
			}
			return mapping.findForward(TracingConstants.BAGDROPEDIT);
		}
		
		if (request.getParameter("save") != null){
			BagDrop bagDrop = bdform.getEditBagDrop(); 
			/** set date/time from form **/
			bagDrop.setDispBagDropDate(bdform.getEditBagDropDate());
			bagDrop.setDispBagDropTime(bdform.getEditBagDropTime());
			
			bagDrop.setEntryMethod(TracingConstants.BAGDROP_ENTRY_METHOD_WEB);
			boolean success = true;
			try{
				BagDropUtils.saveOrUpdateBagDrop(user, bagDrop);
				/** According to SWA, you can have a negative time to carousel, thus need to compare the absolute time **/
				if(Math.abs(bagDrop.getTimeToCarousel()) > 120){
					ActionMessage error = new ActionMessage("bagdrop.error.120");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
					success = false;
				}
			} catch (InvalidDateRangeException idre) {
				ActionMessage error = new ActionMessage("bagdrop.error.invaliddaterange",BagDropUtils.getModifyRange(user));
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				success = false;
			} catch (MissingRequiredFieldsException mrfe) {
				ActionMessage error = new ActionMessage("bagdrop.error.requiredfields");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				success = false;
			} catch (InvalidStationException ise){
				ActionMessage error = new ActionMessage("bagdrop.error.invalidstation");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				success = false;
			} catch (Exception e){
				ActionMessage error = new ActionMessage("bagdrop.error.saveerror");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				success = false;
			}
			if(success){
				bdform.setBagDropList(getPaginatedList(request,bdform,user,dto,cbroStation, true));
				bdform.setAvgTimeToCarousel(BagDropUtils.avgTimeToCarousel(user.getCompanycode_ID(), getCurrentStation(cbroStation, user)));
				return mapping.findForward(TracingConstants.BAGDROP);
			} else {
				return mapping.findForward(TracingConstants.BAGDROPEDIT);
			}
		}
		
		if (request.getParameter("back") != null){
			bdform.setBagDropList(getPaginatedList(request,bdform,user,dto,cbroStation, true));
			bdform.setAvgTimeToCarousel(BagDropUtils.avgTimeToCarousel(user.getCompanycode_ID(), getCurrentStation(cbroStation, user)));
			return mapping.findForward(TracingConstants.BAGDROP);
		}
		
		if (request.getParameter("reset") != null){
			//first time loading page, create new dto with default values
			dto = initDTO(dto,user,cbroStation);
		}

		try{
			BagDropUtils.autoRefreshFlightInfo(user, getCurrentStation(cbroStation, user), user.getStation().getCompany());
		} catch (Exception InvalidStationException){
			ActionMessage error = new ActionMessage("bagdrop.error.autorefresh.invalidstation");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
		}
		bdform.setDto(dto);
		bdform.setBagDropList(getPaginatedList(request,bdform,user,dto,cbroStation, false));
		bdform.setAvgTimeToCarousel(BagDropUtils.avgTimeToCarousel(user.getCompanycode_ID(), getCurrentStation(cbroStation, user)));
		return mapping.findForward(TracingConstants.BAGDROP);
	}
	
	/**
	 * Returns new instance of BagDropDTO with default values
	 * 
	 * @param dto
	 * @param user
	 * @return
	 */
	private BagDropDTO initDTO(BagDropDTO dto, Agent user, Station cbroStation){
			TimeZone timeZone = TimeZone
				.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());
		
			dto = new BagDropDTO();
			dto.setAirlineCode(user.getCompanycode_ID());
			dto.setArrivalStation(getCurrentStation(cbroStation, user));
			
			Date startOfDayGMTDate = BagDropUtils.getStartOfDayGMT(timeZone, 0);
			Date endOfDayGMTDate = BagDropUtils.getStartOfDayGMT(timeZone, 1);
			dto.setStartScheduleArrivalDate(startOfDayGMTDate);
			dto.setEndScheduleArrivalDate(endOfDayGMTDate);
			

			dto.set_DATEFORMAT(user.getDateformat().getFormat());
			dto.set_TIMEFORMAT(user.getTimeformat().getFormat());
			dto.set_TIMEZONE(timeZone);
			return dto;
	}
	
	/**
	 * Traditionally the application had always used the browser.back for the back button of a page.  However, since it is a browser back, 
	 * if the page that we are currently on has been reloaded multiple times, you have to click the back button the same corrisponding number of times.
	 * 
	 * The return the search page when the back button is pressed from the edit page, we have re-query the database using the previous DTO.  Since the application
	 * auto load DTOs that resulted in a single bagdrop, we must use the most recent DTO that did not result in a single bagdrop.
	 * 
	 * @param request
	 * @param theForm
	 * @param agent
	 * @param dto
	 * @param cbroStation
	 * @param loadPreviousNonSingle
	 * @return
	 */
	private List<BagDrop> getPaginatedList(HttpServletRequest request, BagDropForm theForm, Agent agent, BagDropDTO dto, Station cbroStation, boolean loadPreviousNonSingle){
		if(loadPreviousNonSingle){
			if(theForm.getLastNonSingleDTO() != null){
				return getPaginatedList(request, theForm, agent, theForm.getLastNonSingleDTO(), cbroStation);
			} else {
				BagDropDTO newDto = null;
				newDto = initDTO(newDto, agent, cbroStation);
				theForm.setDto(newDto);
				return getPaginatedList(request, theForm, agent, newDto, cbroStation);
			}
		} else {
			/** Set sort params from display tag request into DTO **/
			getSortCriteria(dto, request);
			return getPaginatedList(request, theForm, agent, dto, cbroStation);
		}
	}
	
	/**
	 * Returns a paginated list of bagdrop elements based on the given search and sort criteria specified by BagDropDTO and DisplayTag
	 * 
	 * @param request
	 * @param theForm
	 * @param agent
	 * @param dto
	 * @return
	 */
	private List<BagDrop> getPaginatedList(HttpServletRequest request, BagDropForm theForm, Agent agent, BagDropDTO dto, Station cbroStation){
		int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, request.getSession());
		int currpage = theForm.getCurrpage() != null ? Integer.parseInt(theForm.getCurrpage()) : 0;
		if (theForm.getNextpage() != null && theForm.getNextpage().equals("1"))
			currpage++;
		if (theForm.getPrevpage() != null && theForm.getPrevpage().equals("1"))
			currpage--;
		
		request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
		request.setAttribute("currpage", Integer.toString(currpage));
		
		dto.setArrivalStation(getCurrentStation(cbroStation, agent));
		
		/** 
		 *  For the count, we need all the search params from the dto, however, sort and pagination zeroed out.
		 *  Since we want to preserve the sort, clone the dto for the count
		 **/
		BagDropDTO countDto = dto.clone();
		countDto.setMaxResults(0);
		countDto.setStartIndex(0);
		countDto.setSort(null);
		long rowcount = BagDropUtils.searchBagdropCount(countDto);
		
		int totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

		if (totalpages <= currpage) {
			currpage = 0;
			request.setAttribute("currpage", "0");
		}

		if (currpage + 1 == totalpages)
			request.setAttribute("end", "1");
		if (totalpages > 1) {
			ArrayList<String> al = new ArrayList<String>();
			for (int i = 0; i < totalpages; i++) {
				al.add(Integer.toString(i));
			}
			request.setAttribute("pages", al);
		}
		
		if(rowcount > 1){
			/** Since we autoload single results, saving the DTO that resulted in a non-single result to support use of back button **/
			theForm.setLastNonSingleDTO(dto.clone());
		} 
		
		/** regardless of whether the dto produced results, always save the dto (NT-2059)**/
		theForm.setPreviousDTO(dto.clone());

		dto.setMaxResults(rowsperpage);
		dto.setStartIndex(currpage*rowsperpage);
		
		theForm.setDisplayGetFlightInfoButton(BagDropUtils.canRefreshFlightInfo(agent, dto.getArrivalStation(), dto.getStartScheduleArrivalDate(), dto.getEndScheduleArrivalDate()));
		return BagDropUtils.searchBagdrop(agent, dto);
	}
	
	/**
	 * Decodes Sort Criteria for DisplayTag
	 * 
	 * @param dto
	 * @param request
	 */
	private void getSortCriteria(BagDropDTO dto, HttpServletRequest request) {
		ParamEncoder encoder = new ParamEncoder(TracingConstants.TABLE_ID_BAG_DROP);
		String sort = request.getParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_SORT));
		dto.setSort(sort != null ? sort : "scharr");
		
		String dir = request.getParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
		dto.setDir(dir != null ? dir : TracingConstants.SORT_ASCENDING);		
	}
	
	/**
	 * Legacy Code for handling CBRO Station.  Returns station for later use by Search DTO
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	private Station handleCBROStation(HttpServletRequest request, Agent user){
		Station ret = null;

		HttpSession session = request.getSession();
		if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CBRO_VIEW, user)) {
			if (request.getParameter("cbroStation") != null && !((String) request.getParameter("cbroStation")).equals("")) {
				Station station = StationBMO.getStation((String) request.getParameter("cbroStation"));
				if (station.getCompany().getCompanyCode_ID().equals(user.getCompanycode_ID())) {
					session.setAttribute("cbroStationID", request.getParameter("cbroStation"));
					ret = StationBMO.getStation((String) request.getSession().getAttribute("cbroStationID"));
					if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CBRO_MGMT,user)) {
						user.setStation(StationBMO.getStationById(Integer.parseInt(request.getParameter("cbroStation")),user.getCompanycode_ID()));
					}
				}
			}
		}
		
		if(ret == null && request.getSession().getAttribute("cbroStationID") != null && !request.getSession().getAttribute("cbroStationID").equals("")){
			//if loading bagdrop from the admin menu, cbroStation will not be part of the request thus the return station will not be set
			//if return station is not set, need to check if there is a session cbroStationID that was set from another page, if so load that station
			ret = StationBMO.getStation((String) request.getSession().getAttribute("cbroStationID"));
		}
		return ret;
	}
	
	private String getCurrentStation(Station cbroStation, Agent agent){
		if(cbroStation != null){
			return cbroStation.getStationcode();
		} else {
			return agent.getStation().getStationcode();
		}
	}

}
