package com.bagnet.nettracer.cronjob.wt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.cronjob.bmo.WTQueueBmo;
import com.bagnet.nettracer.exceptions.BagtagException;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateOhd;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;
import com.bagnet.nettracer.wt.WorldTracerUtils;

public class MoveToWorldTracer {

	private static final Logger logger = Logger.getLogger(MoveToWorldTracer.class);
	
	private String companyCode;
	private OhdBMO obmo;
	private IncidentBMO ibmo;
	private WTQueueBmo qbmo;

	private Agent ogadmin;

	public MoveToWorldTracer() {
		ogadmin = AdminUtils.getAgentBasedOnUsername("ogadmin", "OW");
		ibmo = new IncidentBMO();
		obmo = new OhdBMO();
	}

	public void moveFiles() {
		//TODO
		Company_Specific_Variable csv = AdminUtils.getCompVariable(companyCode);
		
		//did we get company variables?
		if(csv == null) {
			logger.error("Move To WorldTracer aborted: unable to get company variables for " + companyCode);
			return;
		}
		
		//is the company allowed to move files to worldtracer?
		if(csv.getWt_enabled() != 1 || csv.getWt_write_enabled() != 1) {
			logger.info("Move to Worldtracer aborted: worldtracer writing not turned on for " + companyCode);
			return;
		}
		
		int ohdHours = csv.getOhd_to_wt_hours();
		int incDays = csv.getMbr_to_wt_days();
		int ohdOal = csv.getOal_ohd_hours();
		int incOal = csv.getOal_inc_hours();
		
		int hours = 0;
		
		// select OHD's that meet requirements
		if(ohdOal > 0 && ohdOal < ohdHours) {
			hours = ohdOal;
		}
		else {
			hours = ohdHours;
		}
		List<OHD> temp = obmo.findMoveToWtOhd(hours, companyCode);
		List<OHD> ohdList = null;
		
		ohdList = filterOhdList(temp, ohdHours, ohdOal, companyCode);
		
		//this list will be sorted by create date.  however, the query to get it
		if(ohdList != null) {
			for(OHD ohd : ohdList) {
				try {
					queueOhd(ohd);
				}
				catch (Exception e) {
					logger.error("Unable to queue OHD create in Move to WT thread", e);
				}
			}
		}
		
		//repeat for incidents
		
		if(incOal > 0 && incOal < (incDays * 24)) {
			hours = incOal;
		}
		else {
			hours = incDays * 24;
		}
		//get the incidents that need to be moved
		List<Incident> tempInc = ibmo.findMoveToWtInc(hours, companyCode);
		List<Incident> incList = null;

		incList = filterIncList(tempInc, incDays * 24, incOal, companyCode);
		
		//this list will be sorted by create date.  however, the query to get it
		if(incList != null) {
			for(Incident inc : incList) {
				try {
					queueIncident(inc);
				}
				catch (Exception e) {
					logger.error("Unable to queue inc create in Move to WT thread", e);
				}
			}
		}
	}


	private List<Incident> filterIncList(List<Incident> temp, int myHours, int oalHours, String company) {
		List<Incident> result = new ArrayList<Incident>();
		Calendar c = new GregorianCalendar();
		c.setTime(TracerDateTime.getGMTDate());
		c.add(Calendar.HOUR, (0-myHours));
		Date myCutoff = c.getTime();
		
		c.add(Calendar.HOUR, myHours - oalHours);
		Date oalCutoff = c.getTime();
		
		for(Incident incident : temp) {
			if((isOther(incident, company) && oalHours > 0 && incident.getCreatedate().before(oalCutoff)) ||
					(myHours > 0 && incident.getCreatedate().before(myCutoff))){
				result.add(incident);
			}
		}
		return result;
	}

	private boolean isOther(Incident incident, String company) {
		if(incident == null || company == null ) return false;
		
		if(incident.getItinerary() != null) {
			for(Itinerary itin : (Iterable<Itinerary>) incident.getItinerary()) {
				if(itin.getAirline() != null && !itin.getAirline().equalsIgnoreCase(company)) {
					return true;
				}
			}
		}
		if(incident.getClaimcheck_list() != null) {
			for(Incident_Claimcheck claimCheck : (List<Incident_Claimcheck>)incident.getClaimcheck_list()) {
				if(claimCheck.getClaimchecknum() == null) continue;
				String claimCompany = LookupAirlineCodes.extractTwoCharAirlineCode(claimCheck.getClaimchecknum());
				if(claimCompany != null && !claimCompany.equalsIgnoreCase(company)) {
					return true;
				}
			}

		}
		return false;
	}

	//the list passed here will already be at least old enough for the other airline
	//so all other airlines can be automatically included.  only ones that are only for
	//this airline that must be checked.
	private List<OHD> filterOhdList(List<OHD> temp, int myHours, int oalHours, String company) {
		
		List<OHD> result = new ArrayList<OHD>();
		Calendar c = new GregorianCalendar();
		c.setTime(TracerDateTime.getGMTDate());
		c.add(Calendar.HOUR, (0-myHours));
		Date ohdCutoff = c.getTime();
		
		
		c.add(Calendar.HOUR, myHours - oalHours);
		Date oalCutoff = c.getTime();
		
		for(OHD ohd : temp) {
			Date foundDate = ohd.getFullFoundDate();
			if((isOther(ohd, company) && oalHours > 0 && foundDate.before(oalCutoff)) ||
					(myHours > 0 && foundDate.before(ohdCutoff))) {
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
		if(ohd == null || company == null ) return false;
		
		if(ohd.getItinerary() != null) {
			for(OHD_Itinerary itin : (Iterable<OHD_Itinerary>) ohd.getItinerary()) {
				if(itin.getAirline() != null && !itin.getAirline().equalsIgnoreCase(company)) {
					return true;
				}
			}
		}
		if(ohd.getClaimnum() != null) {
			String claimCompany = LookupAirlineCodes.extractTwoCharAirlineCode(ohd.getClaimnum());
			if(claimCompany != null && !claimCompany.equalsIgnoreCase(company)) {
				return true;
			}
		}
		return false;
	}

	private void queueIncident(Incident incident) throws Exception {
		
		WtqCreateAhl wtq = new WtqCreateAhl();
		wtq.setAgent(ogadmin);
		wtq.setIncident(incident);
		WorldTracerQueueUtils.createOnlyQueue(wtq);
	}

	private void queueOhd(OHD ohd) throws Exception {
		
		WtqCreateOhd wtq = new WtqCreateOhd();
		wtq.setAgent(ogadmin);
		wtq.setOhd(ohd);
		WorldTracerQueueUtils.createOnlyQueue(wtq);
		
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
