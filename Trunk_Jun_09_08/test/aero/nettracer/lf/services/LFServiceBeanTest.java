package aero.nettracer.lf.services;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import org.apache.struts.util.LabelValueBean;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import aero.nettracer.avis.model.KeyValueBean;
import aero.nettracer.general.services.GeneralServiceBean;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.NTDateFormat;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFAddress;
import com.bagnet.nettracer.tracing.db.lf.LFCategory;
import com.bagnet.nettracer.tracing.db.lf.LFDelivery;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFObject;
import com.bagnet.nettracer.tracing.db.lf.LFPerson;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;
import com.bagnet.nettracer.tracing.db.lf.LFReservation;
import com.bagnet.nettracer.tracing.db.lf.LFSubCategory;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;
import com.bagnet.nettracer.tracing.dto.LFSearchDTO;

public class LFServiceBeanTest {
	
//	@Test
	public void test(){
		String sql = "select count(o.id) from com.bagnet.nettracer.tracing.db.lf.LFFound o  " ;
			sql +=	"  left outer join o.client.phones p where 1=1 " ;
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			List result = q.list();
			sess.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
	}
	
//	@Test
	public void loadTest(){
		LFObject o = createLostTestCase();
		System.out.println(o);
		LFServiceBean bean = new LFServiceBean();
		
		Station station = new Station();
		station.setStation_ID(239);
		
//		List<LFObject> list = bean.getLostPaginatedList(station, 0, 15);

	}

	
	
	@Test
	public void lostSaveLoadTest(){
		try {
			LFServiceBean bean = new LFServiceBean();
			LFLost lost = createLostTestCase();
			long lostId = bean.saveOrUpdateLostReport(lost, bean.getAutoAgent());
			assertTrue(lostId != -1);
			
			LFLost loaded = bean.getLostReport(lostId);
			assertTrue(loaded != null && loaded.getId() == lostId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void loadStationsTest(){
		GeneralServiceBean bean = new GeneralServiceBean();
		ArrayList<String> companies = new ArrayList<String>();
		companies.add(TracingConstants.LF_AVIS_COMPANY_ID);
		List<Station> stations = bean.getStations("AB", companies);
		boolean correctStation = true;
		for(Station station:stations){
			if(station.getAssociated_airport().equals(TracingConstants.LF_AVIS_COMPANY_ID) == false){
				correctStation = false;
			}
		}
		assertTrue(correctStation);
	}
	
	@Test
	public void loadStationByStateTest(){
		AvisClientServiceBean bean = new AvisClientServiceBean();
		HashMap <String, ArrayList<KeyValueBean>> map = bean.getStationsByState("AB", TracingConstants.LF_AVIS_COMPANY_ID);
		for(String key:map.keySet()){
			String s = key;
			for(KeyValueBean kvb:map.get(key)){
				s += " " + kvb.getValue();
			}
			System.out.println(s);
		}
	}
	
	@Test
	public void foundSaveLoadTest(){
		LFServiceBean bean = new LFServiceBean();
		LFFound found = createFoundTestCase();
		long foundId = bean.saveOrUpdateFoundItem(found, null);
		assertTrue(foundId != -1);
		
		LFFound loaded = bean.getFoundItem(foundId);
		assertTrue(loaded != null && loaded.getId() == foundId);
	}
	
	@Test
	public void getColorsTest(){
		LFServiceBean bean = new LFServiceBean();
		ArrayList<LabelValueBean> b = bean.getColors();
		for(LabelValueBean color: b){
			if(color.getLabel().equals("White")){
				assertTrue(color.getValue().equals("WT"));
			}
		}
	}
	
	@Test
	public void getCategoriesTest(){
		LFServiceBean bean = new LFServiceBean();
		List<LFCategory> b = bean.getCategories();
		boolean hasAnIphone = false;
		for(LFCategory cat:b){
			if(cat.getDescription().equals("Bags")){
				for(LFSubCategory sub:cat.getSubcategories()){
					if(sub.getDescription().equals("Cloth Bag")){
						hasAnIphone = true;
					}
				}
			}
		}
		assertTrue(hasAnIphone);
	}
	
	@Test
	public void autoCloseTest(){
		LFServiceBean bean = new LFServiceBean();
		bean.closeLostAndEmail(240, bean.getAutoAgent());
	}
	
	@Test
	public void closeLostReportTest(){
		LFServiceBean bean = new LFServiceBean();
		LFLost lost = createLostTestCase();
		long lostId = bean.saveOrUpdateLostReport(lost, bean.getAutoAgent());
		assertTrue(lostId != -1);
		
		assertTrue(bean.closeLostReport(lostId, bean.getAutoAgent()));
		LFLost load = bean.getLostReport(lostId);
		Agent agent = bean.getAutoAgent();
		assertTrue(agent.getUsername().equals("autoagent"));
		assertTrue(load.getCloseAgent().getAgent_ID() == agent.getAgent_ID());
		assertTrue(load.getCloseDate() != null);
		
		long closedate = load.getCloseDate().getTime();
		
		assertTrue(bean.saveOrUpdateLostReport(load, agent) > -1);
		LFLost load2 = bean.getLostReport(lostId);
		assertTrue(closedate == load2.getCloseDate().getTime());
		
		//reopen
		Status status = new Status();
		status.setStatus_ID(TracingConstants.LF_STATUS_OPEN);
		load2.setStatus(status);
		assertTrue(bean.saveOrUpdateLostReport(load2, agent) > -1);
		LFLost load3 = bean.getLostReport(lostId);
		assertTrue(load3.getCloseAgent() == null);
		assertTrue(load3.getCloseDate() == null);
		
	}
	
	@Test
	public void searchLostTest(){
		LFServiceBean bean = new LFServiceBean();
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.add(Calendar.MONTH, -1);
		
		LFLost lost1 = createLostTestCase();
		lost1.setOpenDate(gc.getTime());
		assertTrue(bean.saveOrUpdateLostReport(lost1, bean.getAutoAgent()) != -1);
		LFLost lost2 = createLostTestCase();
		lost2.setOpenDate(gc.getTime());
		assertTrue(bean.saveOrUpdateLostReport(lost2, bean.getAutoAgent()) != -1);
//		assertTrue(bean.searchLostCount(dto) == 2);
	}
	
	@Test
	public void searchLostFoundTest(){
		//TODO
		LFServiceBean bean = new LFServiceBean();
		LFSearchDTO dto = new LFSearchDTO();
		dto.setType(TracingConstants.LF_TYPE_FOUND);
		dto.setPhoneNumber("555-555-5555");
//		dto.setOpenDate(new Date());
//		dto.setStartDate("06/15/2011");
//		dto.setEndDate("06/16/2011");
//		Agent smith = new Agent();
//		smith.setAgent_ID(1);
//		NTDateFormat format = new NTDateFormat();
//		format.setFormat(TracingConstants.DISPLAY_DATEFORMAT);
//		smith.setDateformat(format);
//		smith.setDefaultlocale("US");
//		dto.setAgent(smith);
//		dto.setEmail("");
//		dto.setAgreementNumber("");
//		dto.setFirstName("");
//		dto.setId(0);
//		dto.setLastName("");
//		dto.setMvaNumber("");
//		Station station = new Station();
//		station.setStation_ID(1);
//		dto.setStation(station);
//		Status status = new Status();
//		status.setStatus_ID(1);
//		dto.setStatus(status);
//		Status disposition = new Status();
//		disposition.setStatus_ID(TracingConstants.LF_DISPOSITION_OTHER);
//		dto.setDisposition(disposition);
		
		System.out.println(bean.searchFoundCount(dto));
		for(LFFound lost:bean.searchFound(dto, 0, 100)){
			System.out.println(lost.getId());
		}
	}
	
	@Test
	public void tmLostList(){
		LFServiceBean bean = new LFServiceBean();
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.add(Calendar.MONTH, -1);
		Station station = new Station();
		int stationId = 239;
		station.setStation_ID(stationId);
		
		LFLost lost1 = createLostTestCase();
		lost1.setOpenDate(gc.getTime());
		lost1.setLocation(station);
		lost1.getReservation().setDropoffLocation(station);
		assertTrue(bean.saveOrUpdateLostReport(lost1, bean.getAutoAgent()) != -1);
		LFLost lost2 = createLostTestCase();
		lost2.setOpenDate(gc.getTime());
		lost2.setLocation(station);
		lost2.getReservation().setDropoffLocation(station);
		assertTrue(bean.saveOrUpdateLostReport(lost2, bean.getAutoAgent()) != -1);
		
		assertTrue(bean.getLostCount(station) > 1);
		
		int start = 0;
		int offset = 15;
		int i = 0;
		boolean correctStation = true;
		for(LFLost lost:bean.getLostPaginatedList(station, start, offset)){
			System.out.println("Lost list at station " + lost.getLocation().getStation_ID() + ":" + lost.getId());
			i++;
			if(lost.getReservation().getDropoffLocation().getStation_ID() != stationId){
				correctStation = false;
			}
		}
		System.out.println("count: " + i);
		assertTrue(i <= offset);
		assertTrue(correctStation);
	}
	
	@Test
	public void tmFoundList(){
		LFServiceBean bean = new LFServiceBean();
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.add(Calendar.MONTH, -1);
		Station station = new Station();
		int stationId = 239;
		station.setStation_ID(stationId);
		
		LFFound found1 = createFoundTestCase();
		found1.setFoundDate(gc.getTime());
		found1.setLocation(station);
		assertTrue(bean.saveOrUpdateFoundItem(found1, null) != -1);
		LFFound found2 = createFoundTestCase();
		found2.setFoundDate(gc.getTime());
		found2.setLocation(station);
		assertTrue(bean.saveOrUpdateFoundItem(found2, null) != -1);
		
		assertTrue(bean.getFoundCount(station) > 1);
		
		int start = 0;
		int offset = 15;
		int i = 0;
		boolean correctStation = true;
		for(LFFound found:bean.getFoundPaginatedList(station, start, offset)){
			System.out.println("Found list at station " + found.getLocation().getStation_ID() + ":" + found.getId());
			i++;
			if(found.getLocation().getStation_ID() != stationId){
				correctStation = false;
			}
		}
		System.out.println("count: " + i);
		assertTrue(i <= offset);
		assertTrue(correctStation);
	}
	
	@Test
	public void tmSalvageList(){
		LFServiceBean bean = new LFServiceBean();
		
		GregorianCalendar gc = new GregorianCalendar();
//		gc.add(Calendar.MONTH, -1);
		gc.add(Calendar.DATE, -15);
		Station station = new Station();
		int stationId = 239;
		station.setStation_ID(stationId);
		
		LFFound found1 = createFoundTestCase();
		found1.setFoundDate(gc.getTime());
		found1.setLocation(station);
		assertTrue(bean.saveOrUpdateFoundItem(found1, null) != -1);
		LFFound found2 = createFoundTestCase();
		found2.setFoundDate(gc.getTime());
		found2.setLocation(station);
		assertTrue(bean.saveOrUpdateFoundItem(found2, null) != -1);
		
		assertTrue(bean.getItemsToSalvageCount(station) > 1);
		
		int start = 0;
		int offset = 15;
		int i = 0;
		boolean hasFound1 = false;
		boolean hasFound2 = false;
		boolean correctStation = true;
		boolean correctCloseDate = true;

		for(LFItem item:bean.getItemsToSalvagePaginatedList(station, start, offset)){
			System.out.println("Salvage list at station " + item.getFound().getLocation().getStation_ID() + ":" + item.getFound().getId());
			i++;
			if(item.getFound().getLocation().getStation_ID() != stationId){
				correctStation = false;
			}
			long timeSinceOpened = (new Date()).getTime() - item.getFound().getFoundDate().getTime(); 
			if(timeSinceOpened < (PropertyBMO.getValueAsInt(PropertyBMO.LF_AUTO_SALVAGE_DAYS) * 1400 * 60 * 1000)){
				correctCloseDate = false;
			}

			if(item.getFound().getId() == found1.getId())hasFound1 = true;
			if(item.getFound().getId() == found2.getId())hasFound2 = true;
		}
		System.out.println("count: " + i);
		assertTrue(i <= offset);
		assertTrue(correctStation);
		assertTrue(correctCloseDate);
		
		Status status = new Status();
		status.setStatus_ID(TracingConstants.LF_DISPOSITION_SALVAGED);
		found1.getItem().setDisposition(status);
		assertTrue(bean.saveOrUpdateFoundItem(found1, null) > -1);
		found2.getItem().setDisposition(status);
		assertTrue(bean.saveOrUpdateFoundItem(found2, null) > -1);
		
		LFSearchDTO dto = new LFSearchDTO();
		dto.setDisposition(status);
		dto.setType(TracingConstants.LF_TYPE_FOUND);
		boolean isSalvageStatus = true;
		hasFound1 = false;
		hasFound2 = false;
		for(LFFound found:bean.searchFound(dto, 0, 1000000)){
			if(found.getItem().getDisposition().getStatus_ID() != TracingConstants.LF_DISPOSITION_SALVAGED){
				isSalvageStatus = false;
			}
			if(found.getId() == found1.getId())hasFound1 = true;
			if(found.getId() == found2.getId())hasFound2 = true;
		}
		assertTrue(isSalvageStatus);
		assertTrue(hasFound1);
		assertTrue(hasFound2);
	}
	
	@Test 
	public void uniqueMatchTest(){
		LFServiceBean bean = new LFServiceBean();
		LFMatchHistory match = createMatchHistory();
		
		long id = bean.saveOrUpdateTraceResult(match);
		assertTrue(id != -1);
		
		LFMatchHistory newMatch = new LFMatchHistory();
		newMatch.setLost(match.getLost());
		newMatch.setFound(match.getFound());
		newMatch.setScore(match.getScore());
		Status status = new Status();
		status.setStatus_ID(TracingConstants.LF_TRACING_OPEN);
		newMatch.setStatus(status);
		boolean threwException = false;
		try{
			bean.saveOrUpdateTraceResult(newMatch);
		} catch (org.hibernate.exception.ConstraintViolationException e){
			threwException = true;
		}
		assertTrue(threwException);
	}
	
	@Test
	public void matchHistorySaveLoadStatusTest(){
		LFServiceBean bean = new LFServiceBean();
		LFMatchHistory match = createMatchHistory();
		
		long id = bean.saveOrUpdateTraceResult(match);
		assertTrue(id != -1);
		
		LFMatchHistory loaded = bean.getTraceResult(id);
		assertTrue(loaded != null && loaded.getId() == id && loaded.getStatus().getStatus_ID() == TracingConstants.LF_TRACING_OPEN);	
		assertTrue(loaded.getFound().getItem().getDisposition().getStatus_ID() == TracingConstants.LF_DISPOSITION_OTHER);
		assertTrue(loaded.getLost().getItem().getDisposition().getStatus_ID() == TracingConstants.LF_DISPOSITION_OTHER);
		assertTrue(loaded.getFound().getItem().getLost() == null);
		assertTrue(loaded.getLost().getItem().getFound() == null);
		
		assertTrue(bean.confirmMatch(id));
		loaded = bean.getTraceResult(id);
		assertTrue(loaded != null && loaded.getId() == id && loaded.getStatus().getStatus_ID() == TracingConstants.LF_TRACING_CONFIRMED);	
		assertTrue(loaded.getFound().getItem().getDisposition().getStatus_ID() == TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED);
		assertTrue(loaded.getLost().getItem().getDisposition().getStatus_ID() == TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED);
		assertTrue(loaded.getFound().getItem().getLost().getId() == match.getLost().getId());
		assertTrue(loaded.getLost().getItem().getFound().getId() == match.getFound().getId());
		
		assertTrue(bean.undoMatch(id));
		loaded = bean.getTraceResult(id);
		assertTrue(loaded != null && loaded.getId() == id && loaded.getStatus().getStatus_ID() == TracingConstants.LF_TRACING_OPEN);	
		assertTrue(loaded.getFound().getItem().getDisposition().getStatus_ID() == TracingConstants.LF_DISPOSITION_OTHER);
		assertTrue(loaded.getLost().getItem().getDisposition().getStatus_ID() == TracingConstants.LF_DISPOSITION_OTHER);
		assertTrue(loaded.getFound().getItem().getLost() == null);
		assertTrue(loaded.getLost().getItem().getFound() == null);
		
		assertTrue(bean.rejectMatch(id));
		loaded = bean.getTraceResult(id);
		assertTrue(loaded != null && loaded.getId() == id && loaded.getStatus().getStatus_ID() == TracingConstants.LF_TRACING_REJECTED);	
		assertTrue(loaded.getFound().getItem().getDisposition().getStatus_ID() == TracingConstants.LF_DISPOSITION_OTHER);
		assertTrue(loaded.getLost().getItem().getDisposition().getStatus_ID() == TracingConstants.LF_DISPOSITION_OTHER);
		assertTrue(loaded.getFound().getItem().getLost() == null);
		assertTrue(loaded.getLost().getItem().getFound() == null);
	}
	
	@Test
	public void loadTraceResultsForFoundTest(){
		LFServiceBean bean = new LFServiceBean();
		LFMatchHistory match1 = createMatchHistory();
		long matchId1 = bean.saveOrUpdateTraceResult(match1);
		assertTrue(matchId1 != -1);
		LFMatchHistory match2 = createMatchHistory();
		match2.setFound(match1.getFound());
		long matchId2 = bean.saveOrUpdateTraceResult(match2);
		assertTrue(matchId2 != -1);
		
		boolean hasMatch1 = false;
		boolean hasMatch2 = false;
		int i = 0;
		for(LFMatchHistory match:bean.getTraceResultsForFound(match1.getFound().getId())){
			i++;
			if(match.getId() == matchId1)hasMatch1=true;
			if(match.getId() == matchId2)hasMatch2=true;
		}
		assertTrue(hasMatch1);
		assertTrue(hasMatch2);
		assertTrue(i == 2);
	}
	
	@Test
	public void loadTraceResultsForLostTest(){
		LFServiceBean bean = new LFServiceBean();
		LFMatchHistory match1 = createMatchHistory();
		long matchId1 = bean.saveOrUpdateTraceResult(match1);
		assertTrue(matchId1 != -1);
		LFMatchHistory match2 = createMatchHistory();
		match2.setLost(match1.getLost());
		long matchId2 = bean.saveOrUpdateTraceResult(match2);
		assertTrue(matchId2 != -1);
		
		boolean hasMatch1 = false;
		boolean hasMatch2 = false;
		int i = 0;
		for(LFMatchHistory match:bean.getTraceResultsForLost(match1.getLost().getId())){
			i++;
			if(match.getId() == matchId1)hasMatch1=true;
			if(match.getId() == matchId2)hasMatch2=true;
		}
		assertTrue(hasMatch1);
		assertTrue(hasMatch2);
		assertTrue(i == 2);
	}
	
	@Test
	public void tmTraceList(){
		Station station = new Station();
		station.setStation_ID(239);
		Station differentStation = new Station();
		differentStation.setStation_ID(240);
		
		LFServiceBean bean = new LFServiceBean();
		LFMatchHistory match1 = createMatchHistory();
		match1.getLost().setLocation(station);
		match1.getFound().setLocation(differentStation);
		long matchId1 = bean.saveOrUpdateTraceResult(match1);
		assertTrue(matchId1 != -1);
		
		LFMatchHistory locationTest = bean.getTraceResult(matchId1);
		assertTrue(locationTest != null && locationTest.getLost().getLocation().getStation_ID() == station.getStation_ID());
		
		LFMatchHistory match2 = createMatchHistory();
		match2.getFound().setLocation(station);
		match2.getLost().setLocation(differentStation);
		long matchId2 = bean.saveOrUpdateTraceResult(match2);
		assertTrue(matchId2 != -1);
		
		LFMatchHistory match3 = createMatchHistory();
		match3.getFound().setLocation(station);
		Status status = new Status();
		status.setStatus_ID(TracingConstants.LF_TRACING_CONFIRMED);
		match3.setStatus(status);
		long matchId3 = bean.saveOrUpdateTraceResult(match3);
		assertTrue(matchId3 != -1);
		
		LFMatchHistory match4 = createMatchHistory();
		match3.getFound().setLocation(station);
		match4.getFound().setLocation(differentStation);
		match4.getLost().setLocation(differentStation);
		long matchId4 = bean.saveOrUpdateTraceResult(match4);
		assertTrue(matchId4 != -1);
		
		assertTrue(bean.getTraceResultsCount(station) >= 2);
		
		int start = 0;
		int offset = 15;
		int i = 0;
		boolean correctStation = true;
		boolean onlyNew = true;
		boolean hasMatch1 = false;
		boolean hasMatch2 = false;
		for(LFMatchHistory match:bean.getTraceResultsPaginated(station, start, offset)){
			System.out.println("TraceResult list at lost station " + match.getLost().getLocation().getStation_ID() + " found station " + match.getFound().getLocation().getStation_ID());
			i++;
			if(match.getLost().getReservation().getPickupLocation().getStation_ID() != station.getStation_ID() 
					&& match.getFound().getLocation().getStation_ID() != station.getStation_ID()){
				correctStation = false;
			}
			if(match.getStatus().getStatus_ID() != TracingConstants.LF_TRACING_OPEN){
				onlyNew = false;
			}
			if(match.getId() == matchId1)hasMatch1=true;
			if(match.getId() == matchId2)hasMatch2=true;
		}
		System.out.println("count: " + i);
		assertTrue(i <= offset);
		assertTrue(correctStation);
		assertTrue(onlyNew);
		if(bean.getTraceResultsCount(station) < offset){
			assertTrue(hasMatch1);
			assertTrue(hasMatch2);
		}
	}
	
	@Test
	public void tmDeliveryPendingListTest(){
		
		LFServiceBean bean = new LFServiceBean();
		LFFound found = createFoundTestCase();
		Station station = new Station();
		station.setStation_ID(239);
		Status status = new Status();
		status.setStatus_ID(TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED);
		found.getItem().setDisposition(status);
		found.setLocation(station);
		long id = bean.saveOrUpdateFoundItem(found, null);
		assertTrue(id > -1);
		
		assertTrue(bean.getDeliveryPendingCount(station) > 0);
		System.out.println(bean.getDeliveryPendingCount(station));
		
		int start = 0;
		int offset = 15;
		int i = 0;
		boolean correctStation = true;
		boolean correctStatus = true;
		for(LFItem item:bean.getDeliveryPendingPaginatedList(station, start, offset)){
			System.out.println("Pending delivery list at station " + item.getFound().getLocation().getStation_ID() + ":" + item.getId());
			i++;
			if(item.getFound().getLocation().getStation_ID() != station.getStation_ID()){
				correctStation = false;
			}
			if(item.getDisposition().getStatus_ID() != status.getStatus_ID()){
				correctStatus = false;
			}
		}
		System.out.println("count: " + i);
		assertTrue(i <= offset);
		assertTrue(correctStation);
		assertTrue(correctStatus);		
	}
	
	@Test
	public void tmDeliverySaveLoadTest(){
		LFServiceBean bean = new LFServiceBean();
		long id = bean.saveOrUpdateDelivery(createPendingDeliveryCase());
		assertTrue(id > -1);
	
		LFDelivery loaded = bean.getDelivery(id);
		assertTrue(loaded != null && loaded.getId() == id);
		
		LFLost lost = createLostTestCase();
		long lostId = bean.saveOrUpdateLostReport(lost, bean.getAutoAgent());
		assertTrue(lostId != -1);
		
		LFDelivery delivery = new LFDelivery();
		delivery.setLost(lost);
		long deliveryId = bean.saveOrUpdateDelivery(delivery);
		assertTrue(deliveryId != -1);
		
		loaded = bean.getDelivery(deliveryId);
		assertTrue(loaded != null); 
		assertTrue(loaded.getId() == deliveryId);
		assertTrue(loaded.getLost().getId() == lostId);
	}
	
	@Test
	public void traceLostTest(){
		LFServiceBean bean = new LFServiceBean();
		LFLost lost = createLostTestCase();
		long lostId = bean.saveOrUpdateLostReport(lost, bean.getAutoAgent());
		assertTrue(lostId != -1);
		
		LFLost loaded = bean.getLostReport(lostId);
		assertTrue(loaded != null && loaded.getId() == lostId);
	
		List<LFMatchHistory> results = bean.traceLostItem(lostId);
		System.out.println("trace lost results: " + (results!=null?results.size():"null"));
	}
	
	@Test
	public void testEmail(){
		LFServiceBean bean = new LFServiceBean();
		LFLost lost = createLostTestCase();
		long lostId = bean.saveOrUpdateLostReport(lost, bean.getAutoAgent());
		assertTrue(lostId != -1);
	}
	
	@Test
	public void traceFoundTest(){
		LFServiceBean bean = new LFServiceBean();
		LFFound found = createFoundTestCase();
		long foundId = bean.saveOrUpdateFoundItem(found, null);
		assertTrue(foundId != -1);
		
		LFFound loaded = bean.getFoundItem(foundId);
		assertTrue(loaded != null && loaded.getId() == foundId);
	
		List<LFMatchHistory> results = bean.traceFoundItem(foundId);
		System.out.println("trace lost results: " + (results!=null?results.size():"null"));
	}
	
	public LFLost createLostTestCase(){
		LFLost lost = new LFLost();
		
		Agent agent = new Agent();
		agent.setAgent_ID(1908);
		lost.setAgent(agent);
		lost.setCompanyId(TracingConstants.LF_AVIS_COMPANY_ID);
		lost.setOpenDate(new Date());
		
		LFPerson client = new LFPerson();
		client.setFirstName("Bob");
		client.setLastName("wehadababyitsaboy");
		client.setDecryptedEmail("bob@aol.com");
		LFAddress address = new LFAddress();
		address.setDecryptedAddress1("1505 Windy Ridge Ln");
		address.setDecryptedCity("Atlanta");
		address.setDecryptedState("GA");
		address.setDecryptedZip("30339");
		client.setAddress(address);
		
		LFPhone phone = new LFPhone();
		HashSet<LFPhone> phones = new HashSet<LFPhone>();
		phone.setPerson(client);
		phone.setDecryptedPhoneNumber("4044140102");
		phone.setNumberType(LFPhone.PRIMARY);
		phone.setPhoneType(LFPhone.MOBILE);
		phones.add(phone);
		client.setPhones(phones);
		

		
		lost.setClient(client);
		
		Station location = new Station();
		location.setStation_ID(239);
		lost.setLocation(location);
		
		LFReservation res = new LFReservation();
		res.setDropoffLocation(location);
		res.setPickupLocation(location);
		lost.setReservation(res);
		
		Status status = new Status();
		status.setStatus_ID(TracingConstants.LF_STATUS_OPEN);
		lost.setStatus(status);
		
		HashSet<LFItem> items = new HashSet<LFItem>();
		LFItem item = new LFItem();
		item.setCategory(1);
		item.setSubCategory(1);
		item.setBrand("iphone");
		item.setColor("BK");
		item.setDescription("iphone");
		item.setLost(lost);
		Status deposition = new Status();
		deposition.setStatus_ID(TracingConstants.LF_DISPOSITION_OTHER);
		item.setDisposition(deposition);
		item.setType(TracingConstants.LF_TYPE_LOST);
		items.add(item);
		lost.setItems(items);
		
//		LFReservation reservation = new LFReservation();
//		reservation.setDropoffLocation(location);
//		reservation.setPickupLocation(location);
//		lost.setReservation(reservation);
		
		lost.setRemarks("It's Bob.  They had a baby.  It's a boy.");
		lost.setOpenDate(new Date());
		return lost;
	}
	
	public LFFound createFoundTestCase(){
		LFFound found = new LFFound();
		Agent agent = new Agent();
		agent.setAgent_ID(1908);
		found.setAgent(agent);
		found.setFoundDate(new Date());
		found.setCompanyId(TracingConstants.LF_AVIS_COMPANY_ID);
		
		Station location = new Station();
		location.setStation_ID(239);
		found.setLocation(location);
		
		/* temp */
		LFPerson client = new LFPerson();
		client.setFirstName("Bob");
		client.setLastName("wehadababyitsaboy");
		client.setDecryptedEmail("bob@aol.com");
		LFAddress address = new LFAddress();
		address.setDecryptedAddress1("1505 Windy Ridge Ln");
		address.setDecryptedCity("Atlanta");
		address.setDecryptedState("GA");
		address.setDecryptedZip("30339");
		client.setAddress(address);
		
		LFPhone phone = new LFPhone();
		HashSet<LFPhone> phones = new HashSet<LFPhone>();
		phone.setPerson(client);
		phone.setDecryptedPhoneNumber("4044140102");
		phone.setNumberType(LFPhone.PRIMARY);
		phone.setPhoneType(LFPhone.MOBILE);
		phones.add(phone);
		client.setPhones(phones);
		found.setClient(client);
		
		/* end temp */
		
		Status status = new Status();
		status.setStatus_ID(TracingConstants.LF_STATUS_OPEN);
		found.setStatus(status);
//		found.setDisposition(status);
		
		LFItem item = new LFItem();
		item.setCategory(1);
		item.setSubCategory(1);
		item.setBrand("iphone");
		item.setColor("BK");
		item.setDescription("iphone");
		Status status2 = new Status();
		status2.setStatus_ID(TracingConstants.LF_DISPOSITION_OTHER);
		item.setDisposition(status2);
		item.setType(TracingConstants.LF_TYPE_FOUND);
		found.setItem(item);
		item.setFound(found);
		
		
		return found;
	}
	
	public LFDelivery createPendingDeliveryCase(){
		LFLost lost = createLostTestCase();
		Status status = new Status();
		status.setStatus_ID(TracingConstants.LF_DISPOSITION_DELIVERED);
//		((LFItem[])lost.getItems().toArray())[0].setDisposition(status);
		for(LFItem item:lost.getItems()){
			item.setDisposition(status);
		}
		LFDelivery delivery = new LFDelivery();
		delivery.setLost(lost);
		delivery.setTrackingNumber("UPS123456789");
		return delivery;
	}
	
	public LFMatchHistory createMatchHistory(){
		LFMatchHistory match = new LFMatchHistory();
		match.setLost(createLostTestCase());
		match.setFound(createFoundTestCase());
		Status status = new Status();
		status.setStatus_ID(TracingConstants.LF_TRACING_OPEN);
		match.setStatus(status);
		match.setScore(20);
		return match;
	}
	
}
