package com.bagnet.nettracer.tracing.utils;

import static org.junit.Assert.assertTrue;

import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.classic.Session;
import org.junit.Test;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.PrecoderBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.ItemType;
import com.bagnet.nettracer.tracing.db.Itinerary;

public class PrecoderTest {
	
	class ItinCompare implements Comparator<Itinerary>{
		@Override
		public int compare(Itinerary o1, Itinerary o2) {
			// TODO Auto-generated method stub
			return o1.getItinerary_ID() - o2.getItinerary_ID();
		}
	}
	
	private Set<Itinerary> genItin(int idOffset){
		Date d = new Date();
		Itinerary itin = new Itinerary();
		itin.setItinerary_ID(1 + idOffset);
		itin.setDepartdate(d);
		itin.setLegfrom("FLL");
		itin.setLegto("LAX");
		itin.setItinerarytype(TracingConstants.PASSENGER_ROUTING);
		itin.setFlightnum("123");
		itin.setAirline("B6");
		Set<Itinerary> s = new TreeSet<Itinerary>(new PrecoderTest.ItinCompare());
		s.add(itin);
		Itinerary itin2 = new Itinerary();
		itin2.setItinerary_ID(2 + idOffset);
		itin2.setDepartdate(d);
		itin2.setLegfrom("FLL");
		itin2.setLegto("LAX");
		itin2.setItinerarytype(TracingConstants.BAGGAGE_ROUTING);
		itin2.setFlightnum("123");
		itin2.setAirline("B6");
		s.add(itin2);
		return s;
	}
	
	private Incident getIncident(int type) {
		Incident inc = new Incident();
		inc.setItemtype(new ItemType());
		inc.getItemtype().setItemType_ID(type);
		inc.setAgent(new Agent());
		inc.getAgent().setCompanycode_ID("B6");
		//suprised to see that jetblue does not have an LZ
		inc.setStationcreated(StationBMO.getStationByCode("LAX", "B6"));
		inc.setItinerary(genItin(0));
		return inc;
	}
	
	
	@Test
	public void isFlightNumberSame() {
		String a = "0436";
		String b = "436";
		
		
		
		String aa = removeLeadingZeros(a);
		String bb = removeLeadingZeros(b);
		
		System.out.println(aa);
		System.out.println(bb);
		assertTrue(aa.equals(bb));
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

	@Test
	public void defaultTest(){
		Precoder pc = new com.bagnet.clients.defaul.PrecoderImpl();
		assertTrue(pc.getFaultStationAndLossCode(new Incident()) == null);
		assertTrue(pc.getFaultStationAndLossCode(getIncident(TracingConstants.LOST_DELAY)) == null);
	}
	
	@Test
	public void nullTest(){
		Precoder pc = new com.bagnet.clients.b6.PrecoderImpl();
		Incident inc;
		PrecoderResult pr;
		
		//no agent test
		inc = getIncident(TracingConstants.LOST_DELAY);
		inc.setAgent(null);
		pr = pc.getFaultStationAndLossCode(inc);
		assertTrue(pr == null);
		
		//no itin tests
		inc = getIncident(TracingConstants.LOST_DELAY);
		inc.setItinerary(null);
		pr = pc.getFaultStationAndLossCode(inc);
		assertTrue(pr.getLossCode() == 0 && pr.getFaultStation().getStationcode().equals("CLAIM"));
		
		inc = getIncident(TracingConstants.DAMAGED_BAG);
		String station = inc.getStationcreated().getStationcode();
		inc.setItinerary(null);
		pr = pc.getFaultStationAndLossCode(inc);
		assertTrue(pr.getLossCode() == 81 && pr.getFaultStation().getStationcode().equals(station));
		
		inc = getIncident(TracingConstants.MISSING_ARTICLES);
		inc.setItinerary(null);
		pr = pc.getFaultStationAndLossCode(inc);
		assertTrue(pr.getLossCode() == 91 && pr.getFaultStation().getStationcode().equals("N/A"));
		
		//no default station
		inc = getIncident(TracingConstants.DAMAGED_BAG);
		inc.setStationcreated(null);
		for(Itinerary itin:inc.getItinerary()){
			if(itin.getItinerarytype() == TracingConstants.PASSENGER_ROUTING){
				station = itin.getLegfrom();
			}
		}
		pr = pc.getFaultStationAndLossCode(inc);
		assertTrue(pr.getLossCode() == 81 && pr.getFaultStation().getStationcode().equals(station));
		inc = getIncident(TracingConstants.DAMAGED_BAG);
		inc.setStationcreated(null);
		inc.setItinerary(null);
		pr = pc.getFaultStationAndLossCode(inc);
		assertTrue(pr == null);
		
	}
	
	@Test
	public void lostDelayTest(){
		Precoder pc = new com.bagnet.clients.b6.PrecoderImpl();
		Incident inc;
		PrecoderResult pr;
		String station = "";
		//Exception case:no pax itin
		inc = getIncident(TracingConstants.LOST_DELAY);
		inc.setItinerary(null);
		pr = pc.getFaultStationAndLossCode(inc);
		assertTrue(pr.getLossCode() == 0 && pr.getFaultStation().getStationcode().equals("CLAIM"));
		
		//Case 1:Another airline in the itinerary
		inc = getIncident(TracingConstants.LOST_DELAY);
		for(Itinerary itin:inc.getItinerary()){
			if(itin.getItinerarytype() == TracingConstants.PASSENGER_ROUTING){
				itin.setAirline("WS");
			}
		}
		pr = pc.getFaultStationAndLossCode(inc);
		assertTrue(pr.getLossCode() == 34 && pr.getFaultStation().getStationcode().equals("N/A"));

		//Case 2:pax itin differs from bag itin
		inc = getIncident(TracingConstants.LOST_DELAY);
		for(Itinerary itin:inc.getItinerary()){
			if(itin.getItinerarytype() == TracingConstants.PASSENGER_ROUTING){
				itin.setFlightnum("321");
			}
		}
		pr = pc.getFaultStationAndLossCode(inc);
		assertTrue(pr.getLossCode() == 22 && pr.getFaultStation().getStationcode().equals("CLAIM"));

		//Case 3:pax itin has multiple segments
		inc = getIncident(TracingConstants.LOST_DELAY);
		inc.getItinerary().addAll(genItin(10));
		for(Itinerary itin:inc.getItinerary()){
			if(itin.getItinerarytype() == TracingConstants.PASSENGER_ROUTING){
				station = itin.getLegfrom();
			}
		}
		pr = pc.getFaultStationAndLossCode(inc);
		assertTrue(pr.getLossCode() == 51 && pr.getFaultStation().getStationcode().equals(station));
		
		
		//Case 4:pax itin has only one segment
		inc = getIncident(TracingConstants.LOST_DELAY);
		for(Itinerary itin:inc.getItinerary()){
			if(itin.getItinerarytype() == TracingConstants.PASSENGER_ROUTING){
				station = itin.getLegfrom();
			}
		}
		pr = pc.getFaultStationAndLossCode(inc);
		assertTrue(pr.getLossCode() == 21 && pr.getFaultStation().getStationcode().equals(station));
		
		//Case 5:everything else
		
		//Exception Cases
		inc = getIncident(TracingConstants.LOST_DELAY);
		for(Itinerary itin:inc.getItinerary()){
			//ATL is not a valid station for jetblue
			itin.setLegfrom("ATL");
		}
		pr = pc.getFaultStationAndLossCode(inc);
		assertTrue(pr.getLossCode() == 0 && pr.getFaultStation().getStationcode().equals("CLAIM")
					&& pr.getRemark().equals("fault station not found"));
		
	}
	
	@Test
	public void damagedTest(){
		Incident inc;
		Precoder pc = new com.bagnet.clients.b6.PrecoderImpl();
		PrecoderResult pr;
		
		inc = getIncident(TracingConstants.DAMAGED_BAG);
		inc.setItinerary(null);
		String station = inc.getStationcreated().getStationcode();
		pr = pc.getFaultStationAndLossCode(inc);
		assertTrue(pr.getLossCode() == 81 && pr.getFaultStation().getStationcode().equals(station));
		
		inc = getIncident(TracingConstants.DAMAGED_BAG);
		for(Itinerary itin:inc.getItinerary()){
			if(itin.getItinerarytype() == TracingConstants.PASSENGER_ROUTING){
				station = itin.getLegfrom();
			}
		}
		pr = pc.getFaultStationAndLossCode(inc);
		assertTrue(pr.getLossCode() == 81 && pr.getFaultStation().getStationcode().equals(station));
	}
	
	@Test
	public void missingTest(){
		Incident inc;
		Precoder pc = new com.bagnet.clients.b6.PrecoderImpl();
		PrecoderResult pr;
		inc = getIncident(TracingConstants.MISSING_ARTICLES);
		pr = pc.getFaultStationAndLossCode(inc);
		assertTrue(pr.getLossCode() == 91 && pr.getFaultStation().getStationcode().equals("N/A"));
	}
	
	@Test
	public void incidentTest(){
		Session sess = HibernateWrapper.getSession().openSession();
		Precoder pc = new com.bagnet.clients.b6.PrecoderImpl();
		PrecoderResult pr;
		Incident inc = null;
		String station = "";
		
		//ACKB600001832 lost one matching seg
		inc = IncidentBMO.getIncidentByID("ACKB600001832", sess);
		for(Itinerary itin:inc.getItinerary()){
			if(itin.getItinerarytype() == TracingConstants.PASSENGER_ROUTING){
				station = itin.getLegfrom();
			}
		}
		pr = pc.getFaultStationAndLossCode(inc);
		assertTrue(pr.getLossCode() == 21 && pr.getFaultStation().getStationcode().equals(station));
		
		//AUAB600016549 damage one non-matching seg
		inc = IncidentBMO.getIncidentByID("AUAB600016549", sess);
		for(Itinerary itin:inc.getItinerary()){
			if(itin.getItinerarytype() == TracingConstants.PASSENGER_ROUTING){
				station = itin.getLegfrom();
			}
		}
		pr = pc.getFaultStationAndLossCode(inc);
		assertTrue(pr.getLossCode() == 81 && pr.getFaultStation().getStationcode().equals(station));
		
		//AUSB600026374 missing one non-matching seg
		inc = IncidentBMO.getIncidentByID("AUSB600026374", sess);
		inc = getIncident(TracingConstants.MISSING_ARTICLES);
		pr = pc.getFaultStationAndLossCode(inc);
		assertTrue(pr.getLossCode() == 91 && pr.getFaultStation().getStationcode().equals("N/A"));
		
		//AUAB600002089 lost one pax seg, two bag segs(dupped)
		inc = IncidentBMO.getIncidentByID("AUAB600002089", sess);
		for(Itinerary itin:inc.getItinerary()){
			if(itin.getItinerarytype() == TracingConstants.PASSENGER_ROUTING){
				itin.setFlightnum("321");
			}
		}
		pr = pc.getFaultStationAndLossCode(inc);
		assertTrue(pr.getLossCode() == 22 && pr.getFaultStation().getStationcode().equals("CLAIM"));
		PrecoderBMO.insert(inc.getIncident_ID(), pr);
		
		//ACKB600001832 lost one matching seg
		inc = IncidentBMO.getIncidentByID("ACKB600001832", sess);
		for(Itinerary itin:inc.getItinerary()){
			//forcing an invalid segment
			itin.setLegfrom("ATL");
		}
		pr = pc.getFaultStationAndLossCode(inc);
		assertTrue(pr.getLossCode() == 0 && pr.getFaultStation().getStationcode().equals("CLAIM")
				&& pr.getRemark().equals("fault station not found"));
		PrecoderBMO.insert(inc.getIncident_ID(), pr);
		
	}
	
}
