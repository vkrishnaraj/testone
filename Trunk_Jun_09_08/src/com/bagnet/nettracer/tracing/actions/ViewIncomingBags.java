/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.bagnet.clients.us.SharesIntegrationWrapper;
import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.ControlLog;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHDRequest;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.forms.ViewIncomingRequestForm;
import com.bagnet.nettracer.tracing.utils.BDOUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * Implementation of <strong>Action </strong> that is responsible for viewing
 * incoming bags and performing actions like receive.
 * 
 * @author Ankur Gupta
 */
public class ViewIncomingBags extends CheckedAction {
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ViewIncomingBags.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MessageResources messages = MessageResources
				.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
		HttpSession session = request.getSession();

		ViewIncomingRequestForm theform = (ViewIncomingRequestForm) form;
		// check session
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)) {
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		}

		String sort = request.getParameter("sort");

		if (sort != null && sort.length() > 0 && com.bagnet.nettracer.tracing.constant.TracingConstants.SortParam.isValid(sort)) request.setAttribute("sort", sort);

		Station agent_station = null;
		if (session.getAttribute("cbroStationID") != null) {
			agent_station = StationBMO.getStation((String) session.getAttribute("cbroStationID"));
		} else {
			agent_station = user.getStation();
		}

		//		 menu highlite
		request.setAttribute("highlite", TracingConstants.SYSTEM_COMPONENT_NAME_INCOMING_BAGS);
		boolean deliveredFlag = false;

		if (request.getParameter("close") != null || request.getParameter("close1") != null
				&& request.getParameter("ohd_ID") != null && request.getParameter("ohd_ID").length() > 0) {
			//forward to a success page.
			String ohd_ID = request.getParameter("ohd_ID");

			if (ohd_ID.length() > 13) {
				StringTokenizer st = new StringTokenizer(ohd_ID, ",");
				while (st.hasMoreTokens()) {
					OHD ohd = OHDUtils.getOHD(st.nextToken());
					if (ohd != null) {

						Date gmtDate = TracerDateTime.getGMTDate();
						String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(gmtDate);

						
						Incident tmpIncident = null;
						
						if (ohd.getMatched_incident() != null) {
							tmpIncident = IncidentBMO.getIncidentByID(ohd.getMatched_incident(), null);
						}
						
						if (tmpIncident != null && tmpIncident.getStationassigned().getStation_ID() == user.getStation().getStation_ID()) {
							ohd.getStatus().setStatus_ID(TracingConstants.OHD_STATUS_TO_BE_DELIVERED);
							deliveredFlag = true;
						} else if (agent_station.isThisOhdLz() && PropertyBMO.isTrue(PropertyBMO.PROPERTY_TO_BE_INVENTORIED)) {
							ohd.getStatus().setStatus_ID(TracingConstants.OHD_STATUS_TO_BE_INVENTORIED);
						} else {
							ohd.getStatus().setStatus_ID(TracingConstants.OHD_STATUS_OPEN);
						}
						
						//Update the close date on the file as well.
						ohd.setClose_date(gmtDate);

						//Update the holding status for this ohd and also add a remark.
						ControlLog log = ohd.getLastLog();
						if (log != null) {
							log.setEnd_date(date);
						}

						//Update the controlling information on the file.
						ControlLog newLog = new ControlLog();
						newLog.setControlling_station(user.getStation());
						newLog.setStart_date(date);
						newLog.setOhd(ohd);

						ohd.getControlLog().add(newLog);
						ohd.setHoldingStation(user.getStation());
						
						// set ohd_log to received status
						OHDUtils.setLogReceived(ohd);
						
						Remark r = new Remark();
						r.setAgent(user);
						r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime
								.getGMTDate()));
						r.setRemarktext(messages.getMessage(new Locale(user.getCurrentlocale()),
								"bagreceiveMessage")
								+ " "
								+ user.getStation().getCompany().getCompanyCode_ID()
								+ messages.getMessage(new Locale(user.getCurrentlocale()), "aposS")
								+ " "
								+ user.getStation().getStationcode() + " station.");
						r.setRemarktype(TracingConstants.REMARK_REGULAR);
						r.setOhd(ohd);
						
						if (ohd.getRemarks() == null) ohd.setRemarks(new HashSet());
						ohd.getRemarks().add(r);
						
						//HibernateUtils.save(ohd);
						OhdBMO ohdBmo = new OhdBMO();
						ohdBmo.insertOHD(ohd, user);
						
						// update l/d bag status to to be delivered as well if forwarding station is the same as l/d assigned station
						Item item = BDOUtils.findOHDfromMatchedLD(ohd.getOHD_ID());
						if (item != null) {
							if (ohd.getHoldingStation().getStation_ID() == item.getIncident().getStationassigned().getStation_ID()) {
								// only do this if forwarded station = station assigned for incident
								Status s2 = new Status();
								s2.setStatus_ID(TracingConstants.ITEM_STATUS_TOBEDELIVERED);
								item.setStatus(s2);
								HibernateUtils.save(item);
							}
						}
						
						// update request if there was one
						OHDRequest forRequest = null;
						Status s = null;
						ArrayList listofrequest = (ArrayList)OHDUtils.getCreatedRequestsForOHD(agent_station.getStation_ID(),ohd.getOHD_ID());
						if (listofrequest != null && listofrequest.size() > 0) {
							for (int i = 0; i<listofrequest.size();i++) {
								// close all these requests
								s = new Status();
								s.setStatus_ID(TracingConstants.OHD_STATUS_CLOSED);
								forRequest = (OHDRequest) listofrequest.get(i);
								forRequest.setStatus(s);
								HibernateUtils.save(forRequest);
							}
						}
					}
				}
				request.setAttribute("ohd_ID", ohd_ID);
				if (deliveredFlag) {
					request.setAttribute("deliveredFlag", true);
				}
				return mapping.findForward(TracingConstants.CLOSE_ON_HAND_SUCCESS);
			} else {
				OHD ohd = OHDUtils.getOHD(ohd_ID);

				if (ohd != null) {

					Date gmtDate = TracerDateTime.getGMTDate();
					String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(gmtDate);

					Incident tmpIncident = null;
					
					if (ohd.getMatched_incident() != null) {
						tmpIncident = IncidentBMO.getIncidentByID(ohd.getMatched_incident(), null);
					}
					
					if (tmpIncident != null && tmpIncident.getStationassigned().getStation_ID() == user.getStation().getStation_ID()) {
						ohd.getStatus().setStatus_ID(TracingConstants.OHD_STATUS_TO_BE_DELIVERED);
						deliveredFlag = true;
					} else if (agent_station.isThisOhdLz() && PropertyBMO.isTrue(PropertyBMO.PROPERTY_TO_BE_INVENTORIED)) {
						ohd.getStatus().setStatus_ID(TracingConstants.OHD_STATUS_TO_BE_INVENTORIED); 
					} else {
						ohd.getStatus().setStatus_ID(TracingConstants.OHD_STATUS_OPEN);
					}

					//Update the close date on the file as well.
					ohd.setClose_date(gmtDate);

					//Update the holding status for this ohd and also add a remark.
					ControlLog log = ohd.getLastLog();
					if (log != null) {
						log.setEnd_date(date);
					}
					
					// set ohd_log to received status
					OHDUtils.setLogReceived(ohd);

					//Update the controlling information on the file.
					ControlLog newLog = new ControlLog();
					newLog.setControlling_station(user.getStation());
					newLog.setStart_date(date);
					newLog.setOhd(ohd);
					
					ohd.getControlLog().add(newLog);
					ohd.setHoldingStation(user.getStation());

					Remark r = new Remark();
					r.setAgent(user);
					r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime
							.getGMTDate()));
					r.setRemarktext(messages.getMessage(new Locale(user.getCurrentlocale()),
							"bagreceiveMessage")
							+ " "
							+ user.getStation().getCompany().getCompanyCode_ID()
							+ messages.getMessage(new Locale(user.getCurrentlocale()), "aposS")
							+ " "
							+ user.getStation().getStationcode() + " station.");
					r.setRemarktype(TracingConstants.REMARK_REGULAR);
					r.setOhd(ohd);
					
					if (ohd.getRemarks() == null) ohd.setRemarks(new HashSet());
					ohd.getRemarks().add(r);
					
					// update l/d bag status to to be delivered as well if forwarding station is the same as l/d assigned station
					Item item = BDOUtils.findOHDfromMatchedLD(ohd.getOHD_ID());
					if (item != null) {
						if (ohd.getHoldingStation().getStation_ID() == item.getIncident().getStationassigned().getStation_ID()) {
							// only do this if forwarded station = station assigned for incident
							Status s2 = new Status();
							s2.setStatus_ID(TracingConstants.ITEM_STATUS_TOBEDELIVERED);
							item.setStatus(s2);
							HibernateUtils.save(item);
						}
					}
					
					// update request if there was one
					OHDRequest forRequest = null;
					Status s = null;
					ArrayList listofrequest = (ArrayList)OHDUtils.getCreatedRequestsForOHD(agent_station.getStation_ID(),ohd.getOHD_ID());
					if (listofrequest != null && listofrequest.size() > 0) {
						for (int i = 0; i<listofrequest.size();i++) {
							// close all these requests
							s = new Status();
							s.setStatus_ID(TracingConstants.OHD_STATUS_CLOSED);
							forRequest = (OHDRequest) listofrequest.get(i);
							forRequest.setStatus(s);
							HibernateUtils.save(forRequest);
						}
					}

					//HibernateUtils.save(ohd);
					OhdBMO ohdBmo = new OhdBMO();
					ohdBmo.insertOHD(ohd, user);
					
				}
				request.setAttribute("ohd_ID", ohd_ID);
				if (deliveredFlag) {
					request.setAttribute("deliveredFlag", true);
				}
				return mapping.findForward(TracingConstants.CLOSE_ON_HAND_SUCCESS);
			}
		}

		int requestListCount = OHDUtils.getIncomingBagsCount(agent_station.getStation_ID(), theform);
		List<OHD_Log> bagsList = null;
		if (requestListCount > 0) {
			/** ************ pagination ************* */
			int rowcount = -1;
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
			int totalpages = 0;

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			if (request.getParameter("nextpage") != null && request.getParameter("nextpage").equals("1")) currpage++;
			if (request.getParameter("prevpage") != null && request.getParameter("prevpage").equals("1")) currpage--;

			request.setAttribute("currpage", Integer.toString(currpage));

			// get row count
			rowcount = requestListCount;

			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}

			List requestList = OHDUtils.getIncomingBags(agent_station.getStation_ID(), theform, sort,
					rowsperpage, currpage);
			
			List<OHD_Log> fullRequestList = OHDUtils.getIncomingBags(agent_station.getStation_ID(), theform, sort, requestListCount, 0);
			bagsList = new ArrayList<OHD_Log>(fullRequestList);
			//bagsList = new ArrayList(requestList);

			if (currpage + 1 == totalpages) request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList al = new ArrayList();
				for (int i = 0; i < totalpages; i++) {
					al.add(Integer.toString(i));
				}
				request.setAttribute("pages", al);
			}

			/** ************ end of pagination ************* */
			request.setAttribute("requestList", requestList);
		} else {
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			request.setAttribute("currpage", Integer.toString(currpage));
		}
		
		if (bagsList == null || bagsList.size() < 1) {
			request.setAttribute("noInboundExpediteBags", "nodata");
		}
		// handle teletype here
		ActionMessages errors = new ActionMessages();
		if (request.getParameter("teletype") != null) {   //this means the teletype button was pushed
			ActionMessage error = new ActionMessage("message.it.is.all.good");
			int reportnum = ReportingConstants.RPT_INBOUND_EXPEDITE_BAGS;
			
			StatReportDTO srDTO = new StatReportDTO();
			srDTO.setOutputtype(TracingConstants.REPORT_OUTPUT_PDF);	//default
			
			if(request.getParameter("outputtype").equalsIgnoreCase("5")) { //Teletype
				srDTO.setOutputtype(TracingConstants.REPORT_OUTPUT_TELETYPE);
				String teletypeAddress = request.getParameter("teletypeAddress");
				if(teletypeAddress == null || teletypeAddress.equals("")) {
					error = new ActionMessage("message.no.teletype.address.provided");
				} else {
					error = new ActionMessage("message.send.teletype.info");
					Map parameters = new HashMap();

					ResourceBundle myResources = ResourceBundle.getBundle(
							"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));

					parameters.put("REPORT_RESOURCE_BUNDLE", myResources);
					StringBuilder sbTelexInboundExpediteBags = buildTeletypeStyleInboundExpediteBagsReport(parameters, bagsList);
					SharesIntegrationWrapper iw = new SharesIntegrationWrapper();
					String myLabel = "Inbound Expedite Bags  ";
					iw.sendTelexBySlice(sbTelexInboundExpediteBags.toString(), teletypeAddress, myLabel);
					
				}
			} else {
				ReportBMO rBMO = new ReportBMO(request);
				ServletContext sc = getServlet().getServletContext();
				String reportpath = sc.getRealPath("/");
//				StatReportDTO srDTO = new StatReportDTO();
				
				if (request.getParameter("outputtype").equalsIgnoreCase("0")) {	//PDF
					srDTO.setOutputtype(TracingConstants.REPORT_OUTPUT_PDF);
				} else {	//HTML
					srDTO.setOutputtype(TracingConstants.REPORT_OUTPUT_HTML);
				}
				
				// create data source and put it in Dynamic Jasper friendly format
				List<Map<String, String>> djBagsList = new ArrayList<Map<String, String>>(bagsList.size());
				for (OHD_Log ohdBag : bagsList) {
					OHD myOhd = ohdBag.getOhd();
					Map<String, String> map1 = new HashMap<String, String>();
					map1.put("onhandId", myOhd.getOHD_ID());
					map1.put("matchingIncident", myOhd.getMatched_incident());
					map1.put("color", myOhd.getColor());
					map1.put("type", myOhd.getType());
					map1.put("expediteNumber", ohdBag.getExpeditenum());
					map1.put("claimNumber", ohdBag.getOhd().getClaimnum());
					map1.put("airline", ohdBag.getDispDestinationAirline());
					map1.put("flightnum", ohdBag.getDispDestinationFlightnum());
					map1.put("forwardtime", ohdBag.getDispForwardTime());
					djBagsList.add(map1);
				}
				
				String reportfile = rBMO.createInboundExpediteBagsReport(reportpath, djBagsList, user, srDTO);
				
				if (reportfile == null || "".equals(reportfile)) {
					//no data to report
					if (rBMO.getErrormsg() != null && rBMO.getErrormsg().length() > 0) {
						error = new ActionMessage(rBMO.getErrormsg());
						errors.add(ActionMessages.GLOBAL_MESSAGE,error);
					} else {
						error = new ActionMessage("message.nodata");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					}
				} else {
					request.setAttribute("reportfile", reportfile);
				}
			}

			request.setAttribute("outputtype", Integer.toString(srDTO.getOutputtype()));
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
		} 
		
		return mapping.findForward(TracingConstants.VIEW_INCOMING_BAG_LIST);
	}
	
	private static StringBuilder buildTeletypeStyleInboundExpediteBagsReport(Map parameters, List<OHD_Log> bagsList) {
		StringBuilder inboundExpediteBagsReport = new StringBuilder();
		inboundExpediteBagsReport.append(newline);
		
		ResourceBundle resourceBundle = (ResourceBundle) parameters.get("REPORT_RESOURCE_BUNDLE");
		
		inboundExpediteBagsReport.append("-- " + resourceBundle.getString("header.bag_info") + " --");
		inboundExpediteBagsReport.append(newline);
		
		logger.error("bagsList has " + bagsList.size() + " elements ...");
		
		if (bagsList != null & bagsList.size() >= 1) {
			inboundExpediteBagsReport
				.append(rightPad(resourceBundle.getString("header.ohd"), 17))
				.append(rightPad(resourceBundle.getString("colname.ld_report_num"), 29))
				.append(rightPad(resourceBundle.getString("colname.color"), 9))
				.append(rightPad(resourceBundle.getString("colname.bagtype"), 9))
				.append(rightPad(resourceBundle.getString("colname.expedite_number"), 22))
				.append(leftPad(resourceBundle.getString("colname.baggage_check"), 22) + newline);
			for (OHD_Log ohdBag : bagsList) {
				
				inboundExpediteBagsReport
					.append(rightPad(ohdBag.getOhd().getOHD_ID(), 17))
					.append(rightPad(format(ohdBag.getOhd().getMatched_incident()), 29))
					.append(rightPad(ohdBag.getOhd().getColor(), 9))
					.append(rightPad(ohdBag.getOhd().getType(), 9))
					.append(rightPad(ohdBag.getExpeditenum(), 22))
					.append(leftPad(ohdBag.getOhd().getClaimnum(), 22) + newline);
				inboundExpediteBagsReport.append(newline);	

			}
		}
		
//		String phraseToReplace = "null" + newline;
//		String newPhrase = "" + newline;
//		String result = org.apache.commons.lang.StringUtils.replace(inboundExpediteBagsReport.toString(), phraseToReplace, newPhrase);
		
		logger.info(inboundExpediteBagsReport.toString());
		
		return inboundExpediteBagsReport;
	}

	private static String format(String input) {
		if(input == null || ("null").equals(input))
			return "";
		else
			return input;
	}
}