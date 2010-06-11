package com.bagnet.nettracer.cronjob.wt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.cronjob.bmo.WTQueueBmo;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqOhdTag;
import com.bagnet.nettracer.tracing.db.wtq.WtqQoh;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;

public class MoveToWorldTracer {

	private static final Logger logger = Logger.getLogger(MoveToWorldTracer.class);

	private String companyCode;
	private OhdBMO obmo;
	private IncidentBMO ibmo;
	private WTQueueBmo qbmo;

	private Agent ogadmin;

	private static final String WT_EARLY_MOVE_STATION = "wt.early.move.station";
	private static final String WT_EARLY_MOVE_OHD_HOURS = "wt.early.move.ohd.hours";
	private static final String WT_EARLY_MOVE_AHL_HOURS = "wt.early.move.ahl.hours";

	private static final String WT_MOVE_ALL_TAGS_TO_WT = "wt.move.tags.to.wt";

	private static final String WT_MOVE_ALL_TO_WT = "move.all.to.wt";

	public MoveToWorldTracer(String agentName, String companyCode) throws WorldTracerException {
		ogadmin = AdminUtils.getAgentBasedOnUsername(agentName, companyCode);
		if (ogadmin == null) {
			throw new WorldTracerException("Can't start Move to WT process, invalid default agent");
		}
		ibmo = new IncidentBMO();
		obmo = new OhdBMO();
		this.companyCode = companyCode;
	}

	public void moveFiles() {

		Company_Specific_Variable csv = AdminUtils.getCompVariable(companyCode);

		// did we get company variables?
		if (csv == null) {
			logger.error("Move To WorldTracer aborted: unable to get company variables for " + companyCode);
			return;
		}

		// is the company allowed to move files to worldtracer?
		if (csv.getWt_enabled() != 1 || csv.getWt_write_enabled() != 1) {
			logger.info("Move to Worldtracer aborted: worldtracer writing not turned on for " + companyCode);
			return;
		}

		int ohdHours = csv.getOhd_to_wt_hours();
		int incDays = csv.getMbr_to_wt_days();
		int ohdOal = csv.getOal_ohd_hours();
		int incOal = csv.getOal_inc_hours();

		String temp2 = PropertyBMO.getValue(WT_EARLY_MOVE_OHD_HOURS);

		boolean moveTagsToWt = PropertyBMO.isTrue(WT_MOVE_ALL_TAGS_TO_WT);
		boolean moveAllToWt = PropertyBMO.isTrue(WT_MOVE_ALL_TO_WT);
		if (moveTagsToWt) {
			try {
				List<OHD> ohds = obmo.findTagsMoveToWt(companyCode);
				if (ohds != null) {
					ArrayList<List<OHD>> buckets = new ArrayList<List<OHD>>();
					int lastHoldingStationId = 0;
					ArrayList<OHD> bucket = null;
					for (OHD ohd : ohds) {
						if (ohd.getHoldingStation().getStation_ID() != lastHoldingStationId || bucket.size() >= 10) {
							bucket = new ArrayList<OHD>();
							buckets.add(bucket);
						}
						bucket.add(ohd);
						lastHoldingStationId = ohd.getHoldingStation().getStation_ID();
					}

					for (List<OHD> processBucket : buckets) {
						queueBucket(processBucket);
					}
				}
			} catch (Exception e) {
				logger.error("Unable to queue OHD TAG create in Move to WT thread", e);
			}

		}

		if (!moveAllToWt)
			return;

		List<String> earlyMoveStations = PropertyBMO.getValueList(WT_EARLY_MOVE_STATION);

		if (temp2 != null && earlyMoveStations != null) {
			int ohdEarlyHours = 0;
			try {
				ohdEarlyHours = Integer.parseInt(temp2);
			} catch (NumberFormatException e) {
				logger.warn("unable to parse early move ohd hours from properties", e);
			}
			if (ohdEarlyHours > 0 && ohdEarlyHours < ohdHours) {
				List<OHD> earlyMoveOHD = obmo.findMoveToWtOhd(ohdEarlyHours, companyCode);

				if (earlyMoveOHD != null) {
					for (OHD ohd : earlyMoveOHD) {
						try {
							if (ohd.getHoldingStation().getWt_stationcode() != null && ohd.getHoldingStation().getWt_stationcode().trim().length() > 0
									&& isEarlyMover(ohd, earlyMoveStations)) {
								queueOhd(ohd);
							}
						} catch (Exception e) {
							logger.error("Unable to queue OHD create in Move to WT thread", e);
						}
					}
				}
			}
		}

		int hours = 0;

		// select OHD's that meet requirements
		if (ohdOal > 0 && ohdOal < ohdHours) {
			hours = ohdOal;
		} else {
			hours = ohdHours;
		}
		if (hours > 0) {
			List<OHD> temp = obmo.findMoveToWtOhd(hours, companyCode);
			List<OHD> ohdList = null;
			if (temp != null) {
				ohdList = filterOhdList(temp, ohdHours, ohdOal, companyCode);
				// this list will be sorted by create date. however, the query
				// to get it
				if (ohdList != null) {
					for (OHD ohd : ohdList) {
						try {
							if (ohd.getHoldingStation().getWt_stationcode() != null && ohd.getHoldingStation().getWt_stationcode().trim().length() > 0) {
								queueOhd(ohd);
							}
						} catch (Exception e) {
							logger.error("Unable to queue OHD create in Move to WT thread", e);
						}
					}
				}
			}
		}
		// repeat for incidents
		temp2 = PropertyBMO.getValue(WT_EARLY_MOVE_AHL_HOURS);

		if (temp2 != null && earlyMoveStations != null) {
			int incEarlyHours = 0;
			try {
				incEarlyHours = Integer.parseInt(temp2);
			} catch (NumberFormatException e) {
				logger.warn("unable to parse early move incident hours from properties", e);
			}
			if (incEarlyHours > 0 && incEarlyHours < incDays * 24) {
				List<Incident> earlyMoveInc = ibmo.findMoveToWtInc(incEarlyHours, companyCode);

				if (earlyMoveInc != null) {
					for (Incident incident : earlyMoveInc) {
						try {
							if (incident.getStationassigned().getWt_stationcode() != null && incident.getStationassigned().getWt_stationcode().trim().length() > 0
									&& isEarlyMover(incident, earlyMoveStations)) {
								queueIncident(incident);
							}
						} catch (Exception e) {
							logger.error("Unable to queue incident create in Move to WT thread", e);
						}
					}
				}
			}
		}

		if (incOal > 0 && incOal < (incDays * 24)) {
			hours = incOal;
		} else {
			hours = incDays * 24;
		}
		if (hours > 0) {
			// get the incidents that need to be moved
			List<Incident> tempInc = ibmo.findMoveToWtInc(hours, companyCode);
			List<Incident> incList = null;
			if (tempInc != null) {
				incList = filterIncList(tempInc, incDays * 24, incOal, companyCode);
				// this list will be sorted by create date. however, the query
				// to get it
				if (incList != null) {
					for (Incident incident : incList) {
						try {
							if (incident.getStationassigned().getWt_stationcode() != null && incident.getStationassigned().getWt_stationcode().trim().length() > 0) {
								queueIncident(incident);
							}
						} catch (Exception e) {
							logger.error("Unable to queue inc create in Move to WT thread", e);
						}
					}
				}
			}
		}
	}

	private List<Incident> filterIncList(List<Incident> temp, int myHours, int oalHours, String company) {
		List<Incident> result = new ArrayList<Incident>();
		Calendar c = new GregorianCalendar();
		c.setTime(TracerDateTime.getGMTDate());
		c.add(Calendar.HOUR, (0 - myHours));
		Date myCutoff = c.getTime();

		c.add(Calendar.HOUR, myHours - oalHours);
		Date oalCutoff = c.getTime();

		for (Incident incident : temp) {
			if ((isOther(incident, company) && oalHours > 0 && incident.getCreatedate().before(oalCutoff)) || (myHours > 0 && incident.getCreatedate().before(myCutoff))) {
				result.add(incident);
			}
		}
		return result;
	}

	private boolean isOther(Incident incident, String company) {
		if (incident == null || company == null)
			return false;

		if (incident.getItinerary() != null) {
			for (Itinerary itin : (Iterable<Itinerary>) incident.getItinerary()) {
				if (itin.getAirline() != null && !itin.getAirline().equalsIgnoreCase(company)) {
					return true;
				}
			}
		}
		if (incident.getClaimcheck_list() != null) {
			for (Incident_Claimcheck claimCheck : (List<Incident_Claimcheck>) incident.getClaimcheck_list()) {
				if (claimCheck.getClaimchecknum() == null)
					continue;
				String claimCompany = LookupAirlineCodes.extractTwoCharAirlineCode(claimCheck.getClaimchecknum());
				if (claimCompany != null && !claimCompany.equalsIgnoreCase(company)) {
					return true;
				}
			}

		}
		return false;
	}

	private boolean isEarlyMover(Incident incident, List<String> earlyMoveStations) {
		if (incident == null || earlyMoveStations == null)
			return false;

		if (incident.getItinerary() != null) {
			for (Itinerary itin : (Iterable<Itinerary>) incident.getItinerary()) {
				if (itin == null)
					continue;
				if ((itin.getLegfrom() != null && earlyMoveStations.contains(itin.getLegfrom().toUpperCase()))
						|| (itin.getLegto() != null && earlyMoveStations.contains(itin.getLegto().toUpperCase()))) {
					return true;
				}
			}
		}

		if (incident.getStationcreated() != null && earlyMoveStations.contains(incident.getStationcreated().getStationcode())) {
			return true;
		}
		if (incident.getStationassigned() != null && earlyMoveStations.contains(incident.getStationassigned().getStationcode())) {
			return true;
		}
		return false;
	}

	// the list passed here will already be at least old enough for the other
	// airline
	// so all other airlines can be automatically included. only ones that are
	// only for
	// this airline that must be checked.
	private List<OHD> filterOhdList(List<OHD> temp, int myHours, int oalHours, String company) {

		List<OHD> result = new ArrayList<OHD>();
		Calendar c = new GregorianCalendar();
		c.setTime(TracerDateTime.getGMTDate());
		c.add(Calendar.HOUR, (0 - myHours));
		Date ohdCutoff = c.getTime();

		c.add(Calendar.HOUR, myHours - oalHours);
		Date oalCutoff = c.getTime();

		for (OHD ohd : temp) {
			Date foundDate = ohd.getFullFoundDate();
			if ((isOther(ohd, company) && oalHours > 0 && foundDate.before(oalCutoff)) || (myHours > 0 && foundDate.before(ohdCutoff))) {
				result.add(ohd);
			}
		}
		return result;
	}

	/**
	 * 
	 * @param ohd
	 * @param company
	 * @return
	 */
	private boolean isOther(OHD ohd, String company) {
		if (ohd == null || company == null)
			return false;

		if (ohd.getItinerary() != null) {
			for (OHD_Itinerary itin : (Iterable<OHD_Itinerary>) ohd.getItinerary()) {
				if (itin.getAirline() != null && !itin.getAirline().equalsIgnoreCase(company)) {
					return true;
				}
			}
		}
		if (ohd.getClaimnum() != null) {
			String claimCompany = LookupAirlineCodes.extractTwoCharAirlineCode(ohd.getClaimnum());
			if (claimCompany != null && !claimCompany.equalsIgnoreCase(company)) {
				return true;
			}
		}
		return false;
	}

	private boolean isEarlyMover(OHD ohd, List<String> earlyMoveStations) {
		if (ohd == null || earlyMoveStations == null)
			return false;

		if (ohd.getItinerary() != null) {
			for (OHD_Itinerary itin : (Iterable<OHD_Itinerary>) ohd.getItinerary()) {
				if (itin == null)
					continue;
				if ((itin.getLegfrom() != null && earlyMoveStations.contains(itin.getLegfrom().toUpperCase()))
						|| (itin.getLegto() != null && earlyMoveStations.contains(itin.getLegto().toUpperCase()))) {
					return true;
				}
			}
		}

		Station temp = ohd.getFoundAtStation();
		if (temp != null && temp.getStationcode() != null && earlyMoveStations.contains(temp.getStationcode().toUpperCase())) {
			return true;
		}
		temp = ohd.getHoldingStation();
		if (temp != null && temp.getStationcode() != null && earlyMoveStations.contains(temp.getStationcode().toUpperCase())) {
			return true;
		}
		return false;
	}

	private void queueIncident(Incident incident) throws Exception {

		WtqCreateAhl wtq = new WtqCreateAhl();
		wtq.setAgent(ogadmin);
		wtq.setIncident(incident);
		WorldTracerQueueUtils.createOnlyIncQueue(wtq, incident.getLastupdated());
	}

	private void queueOhd(OHD ohd) throws Exception {

		WtqCreateOhd wtq = new WtqCreateOhd();
		wtq.setAgent(ogadmin);
		wtq.setOhd(ohd);
		WorldTracerQueueUtils.createOnlyOhdQueue(wtq, ohd.getLastupdated());
	}

	private void queueBucket(List<OHD> ohds) throws Exception {

		WtqQoh wtq = new WtqQoh();
		wtq.setAgent(ogadmin);

		ArrayList<WtqOhdTag> tags = new ArrayList<WtqOhdTag>();
		for (OHD ohd : ohds) {
			WtqOhdTag o = new WtqOhdTag();
			o.setOhd(ohd);
		}

		wtq.setOhdTags(tags);
		WorldTracerQueueUtils.createOnlyTagQueue(wtq);
	}

	public void setObmo(OhdBMO obmo) {
		this.obmo = obmo;
	}

	public void setIbmo(IncidentBMO ibmo) {
		this.ibmo = ibmo;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public void setQbmo(WTQueueBmo qbmo) {
		this.qbmo = qbmo;
	}

}
