package com.bagnet.clients.b6;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.utils.Precoder;
import com.bagnet.nettracer.tracing.utils.PrecoderResult;

public class PrecoderImpl implements Precoder{

	private static Logger logger = Logger.getLogger(PrecoderImpl.class);
	
	@Override
	public PrecoderResult getFaultStationAndLossCode(Incident inc) {
		try{
			if(inc == null || inc.getAgent() == null || inc.getAgent().getCompanycode_ID() == null){
				return null;
			}
			if(inc.getItemtype_ID() == TracingConstants.LOST_DELAY){
				PrecoderResult pr = setFaultLostDelay(inc);
				if (pr == null){
					return getFaultInfo(inc, 0, "CLAIM", "fault station not found");
				} else {
					return pr;
				}
			}
			if(inc.getItemtype_ID() == TracingConstants.DAMAGED_BAG){
				return setFaultDamaged(inc);
			}
			if(inc.getItemtype_ID() == TracingConstants.MISSING_ARTICLES){
				return setFaultMissing(inc);
			}
			return null;
		} catch (Exception e){
			logger.error(e);
			return null;
		}
	}
	
	private PrecoderResult setFaultLostDelay(Incident inc){
		ArrayList<Itinerary> paxItin = new ArrayList<Itinerary>();
		ArrayList<Itinerary> bagItin = new ArrayList<Itinerary>();
		boolean hasAnotherAirline = false;
		
		if(inc.getItinerary() != null){
			for(Itinerary itin:inc.getItinerary() ){
				if(itin.getItinerarytype() == TracingConstants.PASSENGER_ROUTING){
					//TODO apply to paxItin only?
					if(itin.getAirline().equals(inc.getAgent().getCompanycode_ID()) == false){
						hasAnotherAirline = true;
					}
					paxItin.add(itin);
				} else {
					bagItin.add(itin);
				}
			}
		}
		
		//Exception case:no pax itin
		if(paxItin.size() == 0){
			return getFaultInfo(inc, 0, "CLAIM", "no pax itin");
		}	
		//Case 1:Another airline in the itinerary
		if(hasAnotherAirline){
			return getFaultInfo(inc, 58, "N/A");
		}
		//Case 2:pax itin differs from bag itin
		if(!hasSameItin(paxItin, bagItin)){
			return getFaultInfo(inc, 22, paxItin.get(paxItin.size()-1).getLegfrom());
		}
		//Case 3:pax itin has multiple segments
		if(paxItin.size() > 1){
			return getFaultInfo(inc, 51, paxItin.get(paxItin.size()-1).getLegfrom());
		}
		//Case 4:pax itin has only one segment
		if(paxItin.size() == 1){
			return getFaultInfo(inc, 21, paxItin.get(0).getLegfrom());
		}
		//Case 5:everything else
		return getFaultInfo(inc, 0, "CLAIM", "should not get to this case");
	}
	
	private PrecoderResult setFaultDamaged(Incident inc){
		//LossCode 81 
		//fault station from airport of last pax segment
		//if no itinerary, then fault create station
		//TODO what if there is no station code
		String faultStation = null;
		if(inc.getStationcreated() != null){
			faultStation = inc.getStationcreated().getStationcode();
		}
		if(inc.getItinerary() != null){
			for(Itinerary itin:inc.getItinerary()){
				if(itin.getItinerarytype() == TracingConstants.PASSENGER_ROUTING){
					faultStation = itin.getLegfrom();
				}
			}
		}
		return getFaultInfo(inc, 81, faultStation);
	}
	
	private PrecoderResult setFaultMissing(Incident inc){
		//Missing is always 91:N/A
		return getFaultInfo(inc, 91, "N/A");
	}
	
	private boolean hasSameItin(List<Itinerary> a, List<Itinerary> b){
		if (b.size() == 0) {
			return true;
		}
		if(a.size() != b.size()){
			return false;
		}
		//Comparing get segment with the assumption that the pax and bag itins are in order
		for(int i = 0; i < a.size(); i++){
			if(!a.get(i).getLegfrom().equals(b.get(i).getLegfrom())
					|| !a.get(i).getLegto().equals(b.get(i).getLegto())
					|| !a.get(i).getAirline().equals(b.get(i).getAirline())
					|| !removeLeadingZeros(a.get(i).getFlightnum()).equals(removeLeadingZeros(b.get(i).getFlightnum()))
					|| !a.get(i).getDepartdate().equals(b.get(i).getDepartdate())){
				return false;
			}
		}
		return true;
	}
	
	private PrecoderResult getFaultInfo(Incident inc, int lossCode, String station){
		return getFaultInfo(inc, lossCode, station, null);
	}
	
	private PrecoderResult getFaultInfo(Incident inc, int lossCode, String station, String remark){
		PrecoderResult ret = new PrecoderResult();
		ret.setLossCode(lossCode);
		ret.setRemark(remark);
		if(station != null && station.length() > 0){
			Station faultStation = StationBMO.getStationByCode(station, inc.getAgent().getCompanycode_ID());
			ret.setFaultStation(faultStation);
		}
		if(ret.getFaultStation() == null){
			return null;
		}
		return ret;
	}
	
	private String removeLeadingZeros(String str) {
		int length = str.length();
		if (str != null) {
			int stripTo = 0;
		
			while (length > 0 && stripTo < length) {
				if (str.substring(stripTo, stripTo + 1).equals("0")) {
					System.out.println(str.substring(stripTo, stripTo));
					stripTo += 1;	
				} else {
					break;
				}
			}
			if (stripTo >0) {
				str = str.substring(stripTo);
			}

		}
		return str;
	}
	
}
