/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions.match;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Match;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.WT_Queue;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.MatchUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public final class ViewMatch extends Action {
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

		Station agent_station = null;
		if (session.getAttribute("cbroStationID") != null) {
			agent_station = AdminUtils.getStation((String) session.getAttribute("cbroStationID"));
		} else {
			agent_station = user.getStation();
		}

		// menu highlite
		request.setAttribute("highlite", TracingConstants.SYSTEM_COMPONENT_NAME_VIEW_MATCHES);
		String match_ID = request.getParameter("match_ID");
		Match match = null;
		if (match_ID != null && match_ID.length() > 0) match = MatchUtils.getMatchWithID(match_ID);
		BagService bs = new BagService();
		Status status_obj = null;
		/** * reject unreject *** */
		if (request.getParameter("reject") != null) {
			status_obj = new Status();
			status_obj.setStatus_ID(TracingConstants.MATCH_STATUS_REJECTED);
			match.setStatus(status_obj);
			MatchUtils.updateMatch(match);
		}
		if (request.getParameter("unreject") != null) {
			status_obj = new Status();
			status_obj.setStatus_ID(TracingConstants.MATCH_STATUS_OPEN);
			match.setStatus(status_obj);
			MatchUtils.updateMatch(match);
		}
		// multiple reject / unreject
		String[] matchselected = request.getParameterValues("match_ID");
		Match tempmatch = null;
		if (matchselected != null) {
			for (int i = 0; i < matchselected.length; i++) {
				tempmatch = MatchUtils.getMatchWithID(matchselected[i]);
				if (request.getParameter("multireject") != null) {
					status_obj = new Status();
					status_obj.setStatus_ID(TracingConstants.MATCH_STATUS_REJECTED);
					tempmatch.setStatus(status_obj);
					MatchUtils.updateMatch(tempmatch);
				} else if (request.getParameter("multiunreject") != null) {
					status_obj = new Status();
					// TODO, if ohd matched already, change to close instead of open
					status_obj.setStatus_ID(TracingConstants.MATCH_STATUS_OPEN);
					tempmatch.setStatus(status_obj);
					MatchUtils.updateMatch(tempmatch);
				} else {
					break;
				}
			}
		}
		IncidentBMO iBMO = new IncidentBMO();
		OhdBMO oBMO = new OhdBMO();
		Incident incident = null;
		OHD ohd = null;
		if (match != null) {
			incident = match.getMbr();
			ohd = match.getOhd();
		}
		Incident_Claimcheck ic = null;
		Item item = null;

		/**
		 * **************** user clicked on match *********************
		 */

		boolean has_unmatched = false;
		boolean has_matched = false;

		if (request.getParameter("domatch") != null) {
            
			// retrieve all the mbr bags and all the ohd info for user to pick and
			// choose

			ActionMessages errors = new ActionMessages();
			boolean nogood = false;

			// if it is a nonbag specific match and non claim check match, user has to
			// select a claimcheck or a bag to match
			if (request.getParameter("detailedmatch") != null) {
				if (request.getParameter("choosebag") != null
						&& request.getParameter("chooseclaimcheck") != null) {
					if (request.getParameter("selectedbag") == null
							&& request.getParameter("selectedclaimcheck") == null) {
						// user didn't choose a bag or a claimcheck
						ActionMessage error = new ActionMessage("error.need_to_choose_bag_or_claim");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						saveMessages(request, errors);
						nogood = true;
					}
				}
			}

			/*************************************************************************
			 * *************** user picked a bag or claimcheck to match
			 ************************************************************************/

			if (request.getParameter("detailedmatch") != null && !nogood) {
				// user selected a bag
				if (request.getParameter("selectedbag") != null) {
					// set bag ohd
					int bagnumber = Integer.parseInt(request.getParameter("selectedbag"));
					item = (Item) incident.getItemlist().get(bagnumber);
					item.setOHD_ID(ohd.getOHD_ID());
					// set bag claimcheck
					item.setClaimchecknum(ohd.getClaimnum());

					// if bag is at the report station, change item status to to be
					// delivered
					if (ohd.getHoldingStation().getStation_ID() == incident.getStationassigned()
							.getStation_ID()) {
						item.setStatus(TracerUtils.getStatus(TracingConstants.ITEM_STATUS_TOBEDELIVERED, user
								.getDefaultlocale().toString()));
					} else {
						// change item status to be matched
						item.setStatus(TracerUtils.getStatus(TracingConstants.ITEM_STATUS_MATCHED, user
								.getDefaultlocale().toString()));
					}
				}
				// user selected a claimcheck
				if (request.getParameter("selectedclaimcheck") != null) {
					for (Iterator i = incident.getClaimchecks().iterator(); i.hasNext();) {
						ic = (Incident_Claimcheck) i.next();
						if (ic.getClaimcheck_ID() == Integer.parseInt(request
								.getParameter("selectedclaimcheck"))) {
							ic.setOHD_ID(ohd.getOHD_ID());
						}
					}
				}

				// match claimcheck if claim check was matched in match history

				if (request.getParameter("selectedclaimcheck") == null && match.getClaimchecknum() != null
						&& match.getClaimchecknum().length() > 0) {
					for (Iterator i = incident.getClaimchecks().iterator(); i.hasNext();) {
						ic = (Incident_Claimcheck) i.next();
						if (ic.getClaimchecknum().equals(match.getClaimchecknum())) ic.setOHD_ID(ohd
								.getOHD_ID());
					}
				}
				// match bag if bag number was matched in match history
				if (request.getParameter("selectedbag") == null && match.getBagnumber() >= 0) {
					item = (Item) incident.getItemlist().get(match.getBagnumber());
					item.setOHD_ID(ohd.getOHD_ID());
					// set bag claimcheck
					item.setClaimchecknum(ohd.getClaimnum());

					// if bag is at the report station, change item status to to be
					// delivered
					if (ohd.getHoldingStation().getStation_ID() == incident.getStationassigned()
							.getStation_ID()) {
						item.setStatus(TracerUtils.getStatus(TracingConstants.ITEM_STATUS_TOBEDELIVERED, user
								.getDefaultlocale().toString()));
					} else {
						// change item status to be matched
						item.setStatus(TracerUtils.getStatus(TracingConstants.ITEM_STATUS_MATCHED, user
								.getDefaultlocale().toString()));
					}

				}

				// match
				iBMO.updateIncidentNoAudit(incident);

				has_matched = true;

				// match done
				// update match history database
				MatchUtils.matchedOHD(Integer.parseInt(match_ID), ohd.getOHD_ID());
				match.setStatus(TracerUtils.getStatus(TracingConstants.MATCH_STATUS_MATCHED, user
						.getDefaultlocale().toString()));
				
				//if has the wt_id,close wt
				this.CloseWTInMatch(incident.getWt_id(), ohd.getWt_id(), user, incident.getIncident_ID(), ohd.getOHD_ID());
				
				request.setAttribute("already_matched", "1");
			} else {
				// claim check match, show bag selection
				if (match.getClaimchecknum() != null && match.getClaimchecknum().length() > 0) {

					// if matched on claimcheck, show bag selection
					if (incident.getItemlist() != null && incident.getItemlist().size() > 0) {
						request.setAttribute("choose_bags", "1");
						request.setAttribute("itemlist", incident.getItemlist());
					} else {
						// no bags at all in report, match claim check automatically
						for (Iterator i = incident.getClaimchecks().iterator(); i.hasNext();) {
							ic = (Incident_Claimcheck) i.next();
							if (ic.getClaimchecknum().equals(match.getClaimchecknum())) ic.setOHD_ID(ohd
									.getOHD_ID());
						}

						// update matching database
						has_matched = true;
						iBMO.updateIncidentNoAudit(incident);
						MatchUtils.matchedOHD(Integer.parseInt(match_ID), ohd.getOHD_ID());
						match.setStatus(TracerUtils.getStatus(TracingConstants.MATCH_STATUS_MATCHED, user
								.getDefaultlocale().toString()));
						//if has the wt_id,close wt
						this.CloseWTInMatch(incident.getWt_id(), ohd.getWt_id(), user, incident.getIncident_ID(), ohd.getOHD_ID());
						request.setAttribute("already_matched", "1");
					}

				} else if (match.getBagnumber() >= 0) {

					// matched on bag, show claimchecks
					if (incident.getClaimcheck_list() != null && incident.getClaimcheck_list().size() > 0) {
						request.setAttribute("choose_claimcheck", "1");
						request.setAttribute("claimchecklist", incident.getClaimcheck_list());
					} else {
						// no claimchecks, go ahead and match the bag
						item = (Item) incident.getItemlist().get(match.getBagnumber());
						item.setOHD_ID(ohd.getOHD_ID());
						item.setClaimchecknum(ohd.getClaimnum());

						// if bag is at the report station, change item status to to be
						// delivered
						if (ohd.getHoldingStation().getStation_ID() == incident.getStationassigned()
								.getStation_ID()) {
							item.setStatus(TracerUtils.getStatus(TracingConstants.ITEM_STATUS_TOBEDELIVERED, user
									.getDefaultlocale().toString()));
						} else {
							// change item status to be matched
							item.setStatus(TracerUtils.getStatus(TracingConstants.ITEM_STATUS_MATCHED, user
									.getDefaultlocale().toString()));
						}

						// update matching database
						has_matched = true;
						iBMO.updateIncidentNoAudit(incident);
						MatchUtils.matchedOHD(Integer.parseInt(match_ID), ohd.getOHD_ID());
						match.setStatus(TracerUtils.getStatus(TracingConstants.MATCH_STATUS_MATCHED, user
								.getDefaultlocale().toString()));
						//if has the wt_id,close wt
						this.CloseWTInMatch(incident.getWt_id(), ohd.getWt_id(), user, incident.getIncident_ID(), ohd.getOHD_ID());
						request.setAttribute("already_matched", "1");
					}
				} else {
					// matched on nonbag specific stufff
					if (incident.getClaimcheck_list() != null && incident.getClaimcheck_list().size() > 0) {
						request.setAttribute("choose_claimcheck", "1");
						request.setAttribute("claimchecklist", incident.getClaimcheck_list());
					}
					// if no bag number, show bag list
					if (incident.getItemlist() != null && incident.getItemlist().size() > 0) {
						request.setAttribute("choose_bags", "1");
						request.setAttribute("itemlist", incident.getItemlist());
					}
				}
			}
			
		}

		/** *************** undo confirmation ******************* */
		// unmatch claim is clicked
		if (request.getParameter("unmatch") != null) {
			String ohd_id = null;

			// claim check match
			if (match.getClaimchecknum() != null && match.getClaimchecknum().length() > 0) {
				for (int i = 0; i < incident.getClaimcheck_list().size(); i++) {
					ic = (Incident_Claimcheck) incident.getClaimcheck_list().get(i);
					if (ic.getClaimchecknum().equals(match.getClaimchecknum())) {
						ohd_id = ic.getOHD_ID();
						// find the bag associated with this
						for (int j = 0; j < incident.getItemlist().size(); j++) {
							item = (Item) incident.getItemlist().get(j);
							if (item.getOHD_ID() != null && item.getOHD_ID().equals(ic.getOHD_ID())) {
								// clear bag ohd and claimcheck
								item.setOHD_ID("");
								item.setClaimchecknum("");

								// if bag is at the report station, change both status to open
								// again
								if (ohd.getHoldingStation().getStation_ID() == incident.getStationassigned()
										.getStation_ID()) {
									item.setStatus(TracerUtils.getStatus(TracingConstants.ITEM_STATUS_OPEN, user
											.getDefaultlocale().toString()));
								} else {
									// change item to open only
									item.setStatus(TracerUtils.getStatus(TracingConstants.ITEM_STATUS_OPEN, user
											.getDefaultlocale().toString()));
								}

								break;
							}
						}

						// call unmatch to clear out match history
						MatchUtils.unmatchOHD(ohd_id);
						// if undomatch ,delete the wt_id in ohd/incident
						if(ohd.getWt_id() != null){
							ohd.setWt_id(null);
							HibernateUtils.save(ohd);
						}
						if(incident.getWt_id() != null){
						    incident.setWt_id(null);
						    HibernateUtils.save(incident);
						}
						// empty out claim ohd
						ic.setOHD_ID(null);

						has_unmatched = true;
						iBMO.updateIncidentNoAudit(incident);

						match.setStatus(TracerUtils.getStatus(TracingConstants.MATCH_STATUS_OPEN, user
								.getDefaultlocale().toString()));
						request.setAttribute("unmatched", "1");
						break;
					}
				}
			}

			// bag match
			if (match.getBagnumber() >= 0) {
				item = (Item) incident.getItemlist().get(match.getBagnumber());
				ohd_id = item.getOHD_ID();

				// find the claimcheck associated with this
				for (int j = 0; j < incident.getClaimcheck_list().size(); j++) {
					ic = (Incident_Claimcheck) incident.getClaimcheck_list().get(j);
					if (ic.getOHD_ID().equals(ohd_id)) {
						// clear claim ohd
						ic.setOHD_ID("");
						break;
					}
				}
				//empty out bag ohd and claimcheck
				item.setOHD_ID("");
				item.setClaimchecknum("");

				// if bag is at the report station, change both status to open again
				if (ohd.getHoldingStation().getStation_ID() == incident.getStationassigned()
						.getStation_ID()) {
					item.setStatus(TracerUtils.getStatus(TracingConstants.ITEM_STATUS_OPEN, user
							.getDefaultlocale().toString()));
					ohd.setStatus(TracerUtils.getStatus(TracingConstants.OHD_STATUS_OPEN, user
							.getDefaultlocale().toString()));
				} else {
					// change item to open only
					item.setStatus(TracerUtils.getStatus(TracingConstants.ITEM_STATUS_OPEN, user
							.getDefaultlocale().toString()));
				}

				// call unmatch to clear out match history
				MatchUtils.unmatchOHD(ohd_id);
				// if undomatch ,delete the wt_id in ohd/incident
				if(ohd.getWt_id() != null){
					ohd.setWt_id(null);
					HibernateUtils.save(ohd);
				}
				if(incident.getWt_id() != null){
				    incident.setWt_id(null);
				    HibernateUtils.save(incident);
				}
				has_unmatched = true;
				iBMO.updateIncidentNoAudit(incident);
				match.setStatus(TracerUtils.getStatus(TracingConstants.MATCH_STATUS_OPEN, user
						.getDefaultlocale().toString()));
				request.setAttribute("unmatched", "1");
			}
		}

		// update ohd if it's status changed
		if ((ohd != null && incident != null)
				&& ohd.getHoldingStation().getStation_ID() == incident.getStationassigned().getStation_ID()) {
			if (has_matched) {
				ohd.setStatus(TracerUtils.getStatus(TracingConstants.OHD_STATUS_TO_BE_DELIVERED, user
						.getDefaultlocale().toString()));
				oBMO.insertOHD(ohd, user);
			} else if (has_unmatched) {
				ohd.setStatus(TracerUtils.getStatus(TracingConstants.OHD_STATUS_OPEN, user
						.getDefaultlocale().toString()));
				oBMO.insertOHD(ohd, user);
			}
		}

		/** *********** show match details *************** */
		if (request.getParameter("showMatch") != null || request.getParameter("domatch") != null
				|| request.getParameter("unmatch") != null) {
			if (match != null) {
				// check to see if the mbr is in current login user's station, if not,
				// don't show buttons
				if (incident.getStationassigned().getStation_ID() != user.getStation().getStation_ID()) {
					request.setAttribute("nobuttons", "1");
				}
				match.setDispdate(DateUtils.formatDate(match.getMatch_made_on(), user.getDateformat()
						.getFormat()
						+ " " + user.getTimeformat().getFormat(), null, TimeZone.getTimeZone(AdminUtils
						.getTimeZoneById(user.getDefaulttimezone()).getTimezone())));
				request.setAttribute("match", match);
				request.setAttribute("matchdetails", new ArrayList(match.getDetails()));
				if (match.getStatus().getStatus_ID() == TracingConstants.MATCH_STATUS_MATCHED) {
					//request.setAttribute("showclaimorbag", "1");
				}
			}
			return mapping.findForward(TracingConstants.MATCH_VIEW);
		} else {

			/** ************************************************ */
			/** ************* show match list *************** */
			/** ************************************************ */

			// show open, matched, rejected, closed
			String[] status = null;
			// user clicked refilter
			if (request.getParameter("refilter") != null) {
				status = request.getParameterValues("status_ID");
				if (status != null) {
					// initialize all session variable first
					session.setAttribute("status1", "");
					session.setAttribute("status2", "");
					session.setAttribute("status3", "");
					session.setAttribute("status4", "");
					for (int i = 0; i < status.length; i++) {
						if (Integer.parseInt(status[i]) == TracingConstants.MATCH_STATUS_OPEN) session
								.setAttribute("status1", "checked");
						if (Integer.parseInt(status[i]) == TracingConstants.MATCH_STATUS_MATCHED) session
								.setAttribute("status2", "checked");
						if (Integer.parseInt(status[i]) == TracingConstants.MATCH_STATUS_REJECTED) session
								.setAttribute("status3", "checked");
						if (Integer.parseInt(status[i]) == TracingConstants.MATCH_STATUS_CLOSED) session
								.setAttribute("status4", "checked");
					}
				} else {
					// user unchecked everything, so make everything empty
					status = new String[4];
					session.setAttribute("status1", "");
					session.setAttribute("status2", "");
					session.setAttribute("status3", "");
					session.setAttribute("status4", "");
					request.setAttribute("currpage", "0");
					request.setAttribute("end", "0");
					request.setAttribute("pages", "0");
					return mapping.findForward(TracingConstants.MATCH_LIST_VIEW);
				}
			}
			// user didn't click anything, get it from session.
			if (status == null) {
				status = new String[4];
				if (session.getAttribute("status1") != null) status[0] = !((String) session
						.getAttribute("status1")).equals("") ? Integer
						.toString(TracingConstants.MATCH_STATUS_OPEN) : "";
				if (session.getAttribute("status2") != null) status[1] = !((String) session
						.getAttribute("status2")).equals("") ? Integer
						.toString(TracingConstants.MATCH_STATUS_MATCHED) : "";
				if (session.getAttribute("status3") != null) status[2] = !((String) session
						.getAttribute("status3")).equals("") ? Integer
						.toString(TracingConstants.MATCH_STATUS_REJECTED) : "";
				if (session.getAttribute("status4") != null) status[3] = !((String) session
						.getAttribute("status4")).equals("") ? Integer
						.toString(TracingConstants.MATCH_STATUS_CLOSED) : "";
				// if session is empty, show open by default (user entered for the first
				// time)
				if ((status[0] == null || status[0].equals(""))
						&& (status[1] == null || status[1].equals(""))
						&& (status[2] == null || status[2].equals(""))
						&& (status[3] == null || status[3].equals(""))) {
					session.setAttribute("status1", "checked");
					session.setAttribute("status2", "");
					session.setAttribute("status3", "");
					session.setAttribute("status4", "");
					status[0] = Integer.toString(TracingConstants.MATCH_STATUS_OPEN);
				}
			}
			// get sort from request
			String sort = request.getParameter("sort");
			// user pressed a column to sort,
			if (sort != null) session.setAttribute("match_sort", sort);
			sort = (String) session.getAttribute("match_sort");
			List matchList = null;
			/** ************ pagination ************* */
			int rowcount = -1;
			int rowsperpage = request.getParameter("rowsperpage") != null ? Integer.parseInt(request
					.getParameter("rowsperpage")) : TracingConstants.ROWS_PER_PAGE;
			if (rowsperpage < 1) rowsperpage = TracingConstants.ROWS_PER_PAGE;
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
			int totalpages = 0;
			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			if (request.getParameter("nextpage") != null && request.getParameter("nextpage").equals("1")) currpage++;
			if (request.getParameter("prevpage") != null && request.getParameter("prevpage").equals("1")) currpage--;
			request.setAttribute("currpage", Integer.toString(currpage));
			/** ************ end of pagination ************* */

			boolean isactivetrace = false;
			if (request.getParameter("active") != null) isactivetrace = true;

			String mbr = null, ohd_id = null;

			if (request.getParameter("clear") != null) {
				session.removeAttribute("mbr");
				session.removeAttribute("ohd");
			} else {
				mbr = (String) session.getAttribute("mbr");
				ohd_id = (String) session.getAttribute("ohd");
			}

			if (request.getParameter("incident_ID") != null) mbr = request.getParameter("incident_ID");
			if (request.getParameter("ohd_ID") != null) ohd_id = request.getParameter("ohd_ID");

			session.setAttribute("mbr", mbr != null ? mbr : "");
			session.setAttribute("ohd", ohd_id != null ? ohd_id : "");

			// get row count
			rowcount = MatchUtils.getMatchRowCount(isactivetrace, agent_station, status, mbr, ohd_id);

			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);
			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}

			// get match list
			matchList = MatchUtils.getMatches(isactivetrace, agent_station, status, mbr, ohd_id,
					rowsperpage, currpage, sort);

			if (matchList != null) {
				for (int i = 0; i < matchList.size(); i++) {
					match = (Match) matchList.get(i);
					match.setDispdate(DateUtils.formatDate(match.getMatch_made_on(), user.getDateformat()
							.getFormat()
							+ " " + user.getTimeformat().getFormat(), null, TimeZone.getTimeZone(AdminUtils
							.getTimeZoneById(user.getDefaulttimezone()).getTimezone())));
				}
				request.setAttribute("matchList", matchList);
			}
			/** ******* pagination ********* */
			if (currpage + 1 == totalpages) request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList al = new ArrayList();
				for (int i = 0; i < totalpages; i++) {
					al.add(Integer.toString(i));
				}
				request.setAttribute("pages", al);
			}
			/** ******* end of pagination ******* */
			return mapping.findForward(TracingConstants.MATCH_LIST_VIEW);
		}
	}
	
	public void CloseWTInMatch(String Incident_Wt_id,String Ohd_Wt_id,Agent user,String Incident_ID,String OHD_ID){

		WorldTracerQueueUtils wq = new WorldTracerQueueUtils();
		try {
		if(Incident_Wt_id != null){
			
			WT_Queue wtq = new WT_Queue();
			wtq.setAgent(user);
			wtq.setCreatedate(TracerDateTime.getGMTDate());
			wtq.setType_id(Incident_ID);
			wtq.setStationcode(user.getStation().getStationcode());
			wtq.setType("closeIncident");
			wtq.setQueue_status((TracingConstants.LOG_NOT_RECEIVED));
			wq.saveWtobj(wtq, user);
		}
		if(Ohd_Wt_id != null){
			
			WT_Queue wtq = new WT_Queue();
			wtq.setAgent(user);
			wtq.setCreatedate(TracerDateTime.getGMTDate());
			wtq.setType_id(OHD_ID);
			wtq.setStationcode(user.getStation().getStationcode());
			wtq.setType("closeOHD");
			wtq.setQueue_status((TracingConstants.LOG_NOT_RECEIVED));
			wq.saveOhdobj(wtq, user);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void UpdateIncident(){
		
	}
}