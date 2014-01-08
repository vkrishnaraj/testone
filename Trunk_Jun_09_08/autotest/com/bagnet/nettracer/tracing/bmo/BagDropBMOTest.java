package com.bagnet.nettracer.tracing.bmo;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

import aero.nettracer.general.services.GeneralServiceBean;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BagDrop;
import com.bagnet.nettracer.tracing.dto.BagDropDTO;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Loupas
 *
 */
public class BagDropBMOTest {
	public Date now = DateUtils.convertToGMTDate(new Date());
	
	/**
	 * Testing basic saving and loading
	 */
	@Test
	public void saveLoadTest(){
		BagDropBMO bdbmo = new BagDropBMO();
		long id = bdbmo.insertBagDrop(createBagDrop(),getWNAgent());
		assertTrue(id > 0);
		BagDrop db = bdbmo.getBagDropByID(id);
		assertTrue(db.getCreateDate().getTime() == now.getTime());
	}
	
	/**
	 * Testing search DTO in fetch both the BagDrop objects and count
	 */
	@Test
	public void getBagDropTest(){
		BagDropBMO bdbmo = new BagDropBMO();
		long id = bdbmo.insertBagDrop(createBagDrop(),getWNAgent());
		assertTrue(id > 0);
		
		GregorianCalendar start = new GregorianCalendar();
		start.setTime(now);
		start.add(Calendar.DATE, -1);
		GregorianCalendar end = new GregorianCalendar();
		end.setTime(now);
		end.add(Calendar.DATE, 1);
		
		BagDropDTO dto = new BagDropDTO();
		dto.setAirlineCode("WN");
		dto.setArrivalStation("ATL");
		dto.setFlightNumber("123");
		dto.setOriginStation("LAX");
		dto.setStartScheduleArrivalDate(start.getTime());
		dto.setEndScheduleArrivalDate(end.getTime());
		List<BagDrop> list = bdbmo.getBagDrop(dto);
		assertTrue(list.size() > 0);
		boolean hasId = false;
		for(BagDrop bd:list){
			if(bd.getId() == id){
				hasId = true;
			}
		}
		assertTrue(hasId);
		
		dto.setId(id);
		long count = bdbmo.getBagDropCount(dto);
		assertTrue(count == 1);
		
	}
	
	/**
	 * Testing getBagDropID.  
	 */
	@Test
	public void getBagDropIDTest(){
		GregorianCalendar cnow = new GregorianCalendar();
		cnow.setTime(now);
		/* Since the method sorts by schedule arrival date descending, and unit test execute in sub-millisecond time,
		 * the schedule arrival date has to be set in the future.  Otherwise a BagDrop object from a previous test can return.
		 */
		cnow.add(Calendar.SECOND, 1);
		
		BagDropBMO bdbmo = new BagDropBMO();
		BagDrop toSave = createBagDrop();
		toSave.setSchArrivalDate(cnow.getTime());
		long id = bdbmo.insertBagDrop(toSave,getWNAgent());
		assertTrue(id > 0);
		
		GregorianCalendar start = new GregorianCalendar();
		start.setTime(now);
		start.add(Calendar.MINUTE, -1);
		GregorianCalendar end = new GregorianCalendar();
		end.setTime(now);
		end.add(Calendar.MINUTE, 1);
		long loadId = bdbmo.getBagDropID(toSave.getAirline(), toSave.getFlight(), toSave.getArrivalStationCode(), start.getTime(), end.getTime());
		
		assertTrue(loadId == id);
	}
	
	/**
	 * Tests the display time (hh:mm) for time to carousel 
	 */
	@Test
	public void getTimeToCarouselTest(){
		BagDrop bagdrop = new BagDrop();
		
		//test null case
		assertTrue("".equals(bagdrop.getDispTimeToCarousel()));
		
		//test 15mins
		Date now = new Date();
		Date now15 = new Date();
		now15.setTime(now.getTime() - (15*60*1000));
		bagdrop.setActArrivalDate(now15);
		bagdrop.setBagDropTime(now);
		assertTrue("00:15".equals(bagdrop.getDispTimeToCarousel()));

		//test 90mins
		Date now90 = new Date();
		now90.setTime(now.getTime() - (90*60*1000));
		bagdrop.setActArrivalDate(now90);
		bagdrop.setBagDropTime(now);
		assertTrue("01:30".equals(bagdrop.getDispTimeToCarousel()));
		
		//test 25 hours
		Date now1500 = new Date();
		now1500.setTime(now.getTime() - (1500*60*1000));
		bagdrop.setActArrivalDate(now1500);
		bagdrop.setBagDropTime(now);
		assertTrue("25:00".equals(bagdrop.getDispTimeToCarousel()));
		
		//test negative time
		Date future = new Date();
		future.setTime(now.getTime() + (10*60*1000));
		bagdrop.setActArrivalDate(future);
		bagdrop.setBagDropTime(now);
		assertTrue("00:00".equals(bagdrop.getDispTimeToCarousel()));	
	}
	
	private BagDrop createBagDrop(){
		GeneralServiceBean gbean = new GeneralServiceBean();
		BagDrop bagdrop = new BagDrop();
		bagdrop.setBagDropTime(now);
		bagdrop.setCreateAgent(gbean.getAgent("ntadmin", "WN"));
		bagdrop.setCreateDate(now);
		bagdrop.setActArrivalDate(now);
		bagdrop.setSchArrivalDate(now);
		bagdrop.setOriginStationCode("LAX");
		bagdrop.setArrivalStationCode("ATL");
		bagdrop.setAirline("WN");
		bagdrop.setFlight("123");
		
		return bagdrop;
	}
	
	private Agent getWNAgent(){
		GeneralServiceBean gbean = new GeneralServiceBean();
		return gbean.getAgent("ntadmin", "WN");
	}
}
