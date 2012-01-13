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
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFAddress;
import com.bagnet.nettracer.tracing.db.lf.LFCategory;
import com.bagnet.nettracer.tracing.db.lf.LFDelivery;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLossInfo;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFObject;
import com.bagnet.nettracer.tracing.db.lf.LFPerson;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;
import com.bagnet.nettracer.tracing.db.lf.LFSubCategory;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchDetail;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;
import com.bagnet.nettracer.tracing.dto.LFSearchDTO;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

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
		String companyID = TracerProperties.get("wt.company.code");
		String subcompany = null;
		if(TracingConstants.LF_AB_COMPANY_ID.equalsIgnoreCase(companyID)){
			subcompany = TracingConstants.LF_AVIS_COMPANY_ID;
		} else if (TracingConstants.LF_LF_COMPANY_ID.equalsIgnoreCase(companyID)){
			subcompany = TracingConstants.LF_SWA_COMPANY_ID;
		} else {
			assertTrue(false);
		}
		companies.add(subcompany);
		List<Station> stations = bean.getStations(companyID, companies);
		boolean correctStation = true;
		for(Station station:stations){
			if(station.getAssociated_airport().equals(subcompany) == false){
				correctStation = false;
			}
		}
		assertTrue(correctStation);
	}
	
	@Test
	public void loadStationByStateTest(){
		LFCClientServiceBean bean = new LFCClientServiceBean();
		String companyID = TracerProperties.get("wt.company.code");
		String subcompany = null;
		if(TracingConstants.LF_AB_COMPANY_ID.equalsIgnoreCase(companyID)){
			subcompany = TracingConstants.LF_AVIS_COMPANY_ID;
		} else if (TracingConstants.LF_LF_COMPANY_ID.equalsIgnoreCase(companyID)){
			subcompany = TracingConstants.LF_SWA_COMPANY_ID;
		} else {
			assertTrue(false);
		}
		HashMap<String,ArrayList<aero.nettracer.lfc.model.KeyValueBean>> map = bean.getStationsByState(companyID, subcompany);
		for(String key:map.keySet()){
			String s = key;
			for(aero.nettracer.lfc.model.KeyValueBean kvb:map.get(key)){
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
	public void foundLoadByBarcodeTest(){
		LFServiceBean bean = new LFServiceBean();
		Agent agent = bean.getAutoAgent();
		LFFound found = createFoundTestCase();
		String barcode = "junittest" + new Date().getTime();
		found.setBarcode(barcode);
		long id = bean.saveOrUpdateFoundItem(found, agent);
		assertTrue(id > 0);
		LFFound barcodeFind = bean.getFoundItemByBarcode(barcode);
		assertTrue(barcodeFind.getId() == id);
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
		List<LFCategory> b = bean.getCategories(TracerProperties.get("wt.company.code"));
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
		LFLost lost = createLostTestCase();
		long lostId = bean.saveOrUpdateLostReport(lost, bean.getAutoAgent());
		assertTrue(lostId != -1);
		bean.closeLostAndEmail(lostId, bean.getAutoAgent());
		LFLost loaded = bean.getLostReport(lostId);
		assertTrue(loaded != null && loaded.getId() == lostId);
		assertTrue(loaded.getStatusId() == TracingConstants.LF_STATUS_CLOSED);
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
	
//	@Test
	//TODO update search
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
	
//	@Test
	//TODO update search
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
		bean.getAutoAgent();
		Station location = StationBMO.getStationByCode("WEB", TracerProperties.get("wt.company.code"));
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.add(Calendar.MONTH, -1);
		
		LFLost lost1 = createLostTestCase();
		lost1.setOpenDate(gc.getTime());
		lost1.setLocation(location);
		lost1.getLossInfo().setDestination(location);
		assertTrue(bean.saveOrUpdateLostReport(lost1, bean.getAutoAgent()) != -1);
		LFLost lost2 = createLostTestCase();
		lost2.setOpenDate(gc.getTime());
		lost2.setLocation(location);
		lost2.getLossInfo().setDestination(location);
		assertTrue(bean.saveOrUpdateLostReport(lost2, bean.getAutoAgent()) != -1);
		
		assertTrue(bean.getLostCount(location) > 1);
		
		int start = 0;
		int offset = 15;
		int i = 0;
		boolean correctStation = true;
		for(LFLost lost:bean.getLostPaginatedList(location, start, offset)){
			System.out.println("Lost list at station " + lost.getLocation().getStation_ID() + ":" + lost.getId());
			i++;
			if(lost.getLossInfo().getDestination().getStation_ID() != location.getStation_ID()){
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
		Station location = StationBMO.getStationByCode("WEB", TracerProperties.get("wt.company.code"));
		
		LFFound found1 = createFoundTestCase();
		found1.setFoundDate(gc.getTime());
		found1.setLocation(location);
		assertTrue(bean.saveOrUpdateFoundItem(found1, null) != -1);
		LFFound found2 = createFoundTestCase();
		found2.setFoundDate(gc.getTime());
		found2.setLocation(location);
		assertTrue(bean.saveOrUpdateFoundItem(found2, null) != -1);
		
		assertTrue(bean.getFoundCount(location) > 1);
		
		int start = 0;
		int offset = 15;
		int i = 0;
		boolean correctStation = true;
		for(LFFound found:bean.getFoundPaginatedList(location, start, offset)){
			System.out.println("Found list at station " + found.getLocation().getStation_ID() + ":" + found.getId());
			i++;
			if(found.getLocation().getStation_ID() != location.getStation_ID()){
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
		gc.add(Calendar.DATE, - (1 + PropertyBMO.getValueAsInt(PropertyBMO.LF_AUTO_SALVAGE_DAYS)));
		Station location = StationBMO.getStationByCode("WEB", TracerProperties.get("wt.company.code"));
		
		LFFound found1 = createFoundTestCase();
		found1.setFoundDate(gc.getTime());
		found1.setLocation(location);
		assertTrue(bean.saveOrUpdateFoundItem(found1, null) != -1);
		LFFound found2 = createFoundTestCase();
		found2.setFoundDate(gc.getTime());
		found2.setLocation(location);
		assertTrue(bean.saveOrUpdateFoundItem(found2, null) != -1);
		
		assertTrue(bean.getItemsToSalvageCount(location) > 1);
		
		int start = 0;
		int offset = 15;
		int i = 0;
		boolean hasFound1 = false;
		boolean hasFound2 = false;
		boolean correctStation = true;
		boolean correctCloseDate = true;

		for(LFItem item:bean.getItemsToSalvagePaginatedList(location, start, offset)){
			System.out.println("Salvage list at station " + item.getFound().getLocation().getStation_ID() + ":" + item.getFound().getId());
			i++;
			if(item.getFound().getLocation().getStation_ID() != location.getStation_ID()){
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
	
//	@Test
	//TODO
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
			if(match.getLost().getLossInfo().getOrigin().getStation_ID() != station.getStation_ID() 
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
		Station location = StationBMO.getStationByCode("WEB", TracerProperties.get("wt.company.code"));
		Status status = new Status();
		status.setStatus_ID(TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED);
		found.getItem().setDisposition(status);
		found.setLocation(location);
		long id = bean.saveOrUpdateFoundItem(found, null);
		assertTrue(id > -1);
		
		assertTrue(bean.getDeliveryPendingCount(location) > 0);
		System.out.println(bean.getDeliveryPendingCount(location));
		
		int start = 0;
		int offset = 15;
		int i = 0;
		boolean correctStation = true;
		boolean correctStatus = true;
		for(LFItem item:bean.getDeliveryPendingPaginatedList(location, start, offset)){
			System.out.println("Pending delivery list at station " + item.getFound().getLocation().getStation_ID() + ":" + item.getId());
			i++;
			if(item.getFound().getLocation().getStation_ID() != location.getStation_ID()){
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
	@Deprecated
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
	
		LFFound found = createFoundTestCase();
		long foundId = bean.saveOrUpdateFoundItem(found, bean.getAutoAgent());
		assertTrue(foundId != -1);
		
		List<LFMatchHistory> results = bean.traceLostItem(lostId);
		System.out.println("trace lost results: " + (results!=null?results.size():"null"));
		assertTrue(results != null && results.size() > 0);
		LFMatchHistory hasMatch = null;
		for(LFMatchHistory match:results){
			if(match.getFound().getId() == foundId && match.getLost().getId() == lostId){
				hasMatch = match;
			}
		}
		assertTrue(hasMatch != null);
		assertTrue(verifyMatchDetails(hasMatch));
	}
	
	private boolean verifyMatchDetails(LFMatchHistory match){
		boolean name = false;
		boolean address = false;
		boolean phone = false;
		boolean cat = false;
		boolean color = false;
		boolean caseColor = false;
		boolean email = false;
		boolean mva = false;
		boolean ra = false;
		boolean brand = false;
		boolean ld = false;
		boolean sd = false;
		boolean model = false;
		boolean serial = false;
		boolean itemPhone = false;
		
		for(LFMatchDetail detail:match.getDetails()){
			if("Name Match".equals(detail.getDescription()))name=true;
			if("Phone Number Match".equals(detail.getDescription()))phone=true;
			if("Email Match".equals(detail.getDescription()))email=true;
			if("Address Match".equals(detail.getDescription()))address=true;
			if("MVA Number Match".equals(detail.getDescription()))mva=true;
			if("Rental Agreement Number Match".equals(detail.getDescription()))ra=true;
			if("Brand Match".equals(detail.getDescription()))brand=true;
			if("Category Match".equals(detail.getDescription()))cat=true;
			if("Color Match".equals(detail.getDescription()))color=true;
			if("Case Color Match".equals(detail.getDescription()))caseColor=true;
			if("Long Description Match".equals(detail.getDescription()))ld=true;
			if("Description Match".equals(detail.getDescription()))sd=true;
			if("Model Match".equals(detail.getDescription()))model=true;
			if("Serial Number Match".equals(detail.getDescription()))serial=true;
			if("7778889999".equals(detail.getDecryptedFoundValue()))itemPhone=true;
		}
		return name && address && phone && cat && color && caseColor && email && mva && ra && brand && ld && sd && model && serial && itemPhone;
	}
	
//	@Test
	//TODO how do we verify email???
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
	
		LFLost lost = createLostTestCase();
		long lostId = bean.saveOrUpdateLostReport(lost, bean.getAutoAgent());
		assertTrue(lostId != -1);
		
		List<LFMatchHistory> results = bean.traceFoundItem(foundId);
		System.out.println("trace lost results: " + (results!=null?results.size():"null"));
		LFMatchHistory hasMatch = null;
		for(LFMatchHistory match:results){
			if(match.getFound().getId() == foundId && match.getLost().getId() == lostId){
				hasMatch = match;
			}
		}
		assertTrue(hasMatch != null);
		assertTrue(verifyMatchDetails(hasMatch));
	}
	
	public LFLost createLostTestCase(){
		LFLost lost = new LFLost();
		LFServiceBean sbean = new LFServiceBean();
		Agent agent = sbean.getAutoAgent();
		
		lost.setAgent(agent);
		String subcompany = null;
		if(agent.getCompanycode_ID().equalsIgnoreCase(TracingConstants.LF_LF_COMPANY_ID)){
			subcompany = TracingConstants.LF_SWA_COMPANY_ID;
		} else if (agent.getCompanycode_ID().equalsIgnoreCase(TracingConstants.LF_AB_COMPANY_ID)){
			subcompany = TracingConstants.LF_AVIS_COMPANY_ID;
		} else {
			assertTrue(false);
		}
		lost.setCompanyId(subcompany);
		lost.setOpenDate(new Date());
		
		LFPerson client = new LFPerson();
		client.setFirstName("Bob");
		client.setLastName("wehadababyitsaboy");
		client.setDecryptedEmail("mloupas@nettracer.aero");
		LFAddress address = new LFAddress();
		address.setDecryptedAddress1("123 Test Street");
		address.setDecryptedCity("Atlanta");
		address.setDecryptedState("GA");
		address.setDecryptedZip("30339");
		client.setAddress(address);
		
		LFPhone phone = new LFPhone();
		HashSet<LFPhone> phones = new HashSet<LFPhone>();
		phone.setPerson(client);
		phone.setDecryptedPhoneNumber("5555555555");
		phone.setNumberType(LFPhone.PRIMARY);
		phone.setPhoneType(LFPhone.MOBILE);
		phones.add(phone);
		client.setPhones(phones);
		

		
		lost.setClient(client);
		
		Station location = StationBMO.getStationByCode("WEB", TracerProperties.get("wt.company.code"));
		lost.setLocation(location);
		
		LFLossInfo res = new LFLossInfo();
		res.setDestination(location);
		res.setOrigin(location);
		res.setMvaNumber("123456");
		res.setAgreementNumber("123456");
		lost.setLossInfo(res);
		
		Status status = new Status();
		status.setStatus_ID(TracingConstants.LF_STATUS_OPEN);
		lost.setStatus(status);
		
		HashSet<LFItem> items = new HashSet<LFItem>();
		LFItem item = getItem();
		item.setType(TracingConstants.LF_TYPE_LOST);
		item.setLost(lost);
		items.add(item);
		lost.setItems(items);
		
		lost.setRemarks("It's Bob.  They had a baby.  It's a boy.");
		lost.setOpenDate(new Date());
		return lost;
	}
	
	public LFFound createFoundTestCase(){
		LFFound found = new LFFound();

		LFServiceBean sbean = new LFServiceBean();
		Agent agent = sbean.getAutoAgent();
		found.setAgent(agent);
		found.setFoundDate(new Date());
		
		String subcompany = null;
		if(agent.getCompanycode_ID().equalsIgnoreCase(TracingConstants.LF_LF_COMPANY_ID)){
			subcompany = TracingConstants.LF_SWA_COMPANY_ID;
		} else if (agent.getCompanycode_ID().equalsIgnoreCase(TracingConstants.LF_AB_COMPANY_ID)){
			subcompany = TracingConstants.LF_AVIS_COMPANY_ID;
		} else {
			assertTrue(false);
		}
		
		found.setCompanyId(subcompany);
		
		Station location = StationBMO.getStationByCode("WEB", TracerProperties.get("wt.company.code"));
		found.setLocation(location);
		
		found.setAgreementNumber("123456");
		found.setMvaNumber("123456");
		
		/* temp */
		LFPerson client = new LFPerson();
		client.setFirstName("Bob");
		client.setLastName("wehadababyitsaboy");
		client.setDecryptedEmail("mloupas@nettracer.aero");
		LFAddress address = new LFAddress();
		address.setDecryptedAddress1("123 Test Street");
		address.setDecryptedCity("Atlanta");
		address.setDecryptedState("GA");
		address.setDecryptedZip("30339");
		client.setAddress(address);
		
		LFPhone phone = new LFPhone();
		HashSet<LFPhone> phones = new HashSet<LFPhone>();
		phone.setPerson(client);
		phone.setDecryptedPhoneNumber("5555555555");
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
		
		LFItem item = getItem();
		item.setType(TracingConstants.LF_TYPE_FOUND);
		found.setItem(item);
		item.setFound(found);
		
		
		return found;
	}
	
	private LFItem getItem(){
		LFItem item = new LFItem();
		item.setCategory(1);
		item.setSubCategory(1);
		item.setColor("BK");
		item.setCaseColor("BK");
		item.setDescription("iphone");
		item.setLongDescription("i lost my iphone");
		item.setBrand("apple");
		item.setModel("iphone4s");
		item.setSerialNumber("123456");
		LFPhone phone = new LFPhone();
		phone.setDecryptedPhoneNumber("7778889999");
		phone.setItem(item);
		item.setPhone(phone);
		Status status2 = new Status();
		status2.setStatus_ID(TracingConstants.LF_DISPOSITION_OTHER);
		item.setDisposition(status2);
		return item;
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
