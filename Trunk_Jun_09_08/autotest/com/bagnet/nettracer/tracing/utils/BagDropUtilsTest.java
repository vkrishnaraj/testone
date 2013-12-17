package com.bagnet.nettracer.tracing.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

import aero.nettracer.general.services.GeneralServiceBean;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BagDrop;
import com.bagnet.nettracer.tracing.db.audit.Audit_BagDrop;

public class BagDropUtilsTest {
	@Test
	public void bagdropSaveLoadExistsTest(){
		GeneralServiceBean gbean = new GeneralServiceBean();
		Agent agent = gbean.getAgent("ntadmin", "WN");
		
		//since for bagdrop there can only be one unique flight per day,
		//for testing setting the flight number to be the minute of the day
		//this does mean that the test can only correctly run once a minute
		Date now = DateUtils.convertToGMTDate(new Date());
		String flightNumber = "" + (now.getTime()/60000)%1440;
		
		//assert that this bagdrop does not exists
		assertTrue(BagDropUtils.bagdropExists("JU", flightNumber, "ATL", now) == 0);
		
		BagDrop bagdrop = new BagDrop();
		bagdrop.setAirline("JU");
		bagdrop.setFlight(flightNumber);
		bagdrop.setArrivalStationCode("ATL");
		bagdrop.setBagDropTime(now);
		bagdrop.setCreateAgent(agent);
		bagdrop.setEntryMethod(TracingConstants.BAGDROP_ENTRY_METHOD_WEB);
		bagdrop.setOriginStationCode("LAX");
		bagdrop.setSchArrivalDate(now);
		
		//save bagdrop and assert that it does exist now
		long id = BagDropUtils.saveOrUpdateBagDrop(agent, bagdrop);
		assertTrue(id > 0);
		assertTrue(BagDropUtils.bagdropExists("JU", flightNumber, "ATL", now) == id);
		
		//load bagdrop
		BagDrop load = BagDropUtils.getBagDropById(agent, id);
		assertTrue(load != null);
		assertTrue(load.getFlight().equals(flightNumber));
		assertTrue(load.getAirline().equals("JU"));
		assertTrue(load.getOriginStationCode().equals("LAX"));
		assertTrue(load.getArrivalStationCode().equals("ATL"));
		
		//load audit
		List<Audit_BagDrop> auditList = BagDropUtils.getAuditBagDropList(agent, id);
		assertTrue(auditList!=null && auditList.size() == 1);
		assertTrue(auditList.get(0).getEntryMethod() == TracingConstants.BAGDROP_ENTRY_METHOD_WEB);
		assertTrue(auditList.get(0).getModifyAgent().getUsername().equals("ntadmin"));
		
		//test mins since last update
		assertTrue(BagDropUtils.minsSinceLastUpdate("ATL", "JU", TracingConstants.BAGDROP_ENTRY_METHOD_WEB) == 0);
		assertTrue(BagDropUtils.minsSinceLastUpdate("ATL", "JU", TracingConstants.BAGDROP_ENTRY_METHOD_WEB) == 0);
		assertTrue(BagDropUtils.minsSinceLastUpdate("ATL", "JU", TracingConstants.BAGDROP_ENTRY_METHOD_WEB) == 0);
		
		//since the audit data is sorted based on createtimestamp and the database only sorts down to seconds
		//must wait a second before doing an update.  Otherwise you will have two entries with the exact same datetime
		//does not harm anything other that potentially mixing up the order when we fetch audit data
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		
		//update bagdrop
		Calendar nowPlus15 = GregorianCalendar.getInstance();
		nowPlus15.setTime(now);
		nowPlus15.add(Calendar.MINUTE, 15);
		
		BagDrop update = new BagDrop();
		update.setAirline("JU");
		update.setFlight(flightNumber);
		update.setArrivalStationCode("ATL");
		update.setBagDropTime(nowPlus15.getTime());
		update.setCreateAgent(agent);
		update.setEntryMethod(TracingConstants.BAGDROP_ENTRY_METHOD_SCANNER);
		update.setOriginStationCode("LAX");
		update.setSchArrivalDate(now);
		
		//load updated bagdrop
		long updateId = BagDropUtils.saveOrUpdateBagDrop(agent, update);
		assertTrue(id == updateId);
		BagDrop loadUpdate = BagDropUtils.getBagDropById(agent, updateId);
		assertTrue(loadUpdate.getDispTimeToCarousel().equals("00:15"));
		
		//load updated audit
		List<Audit_BagDrop> updateAuditList = BagDropUtils.getAuditBagDropList(agent, updateId);
		assertTrue(updateAuditList!=null && updateAuditList.size() == 2);
		assertTrue(updateAuditList.get(0).getEntryMethod() == TracingConstants.BAGDROP_ENTRY_METHOD_SCANNER);
		assertTrue(updateAuditList.get(0).getModifyAgent().getUsername().equals("ntadmin"));
		assertTrue(updateAuditList.get(1).getEntryMethod() == TracingConstants.BAGDROP_ENTRY_METHOD_WEB);
		assertTrue(updateAuditList.get(1).getModifyAgent().getUsername().equals("ntadmin"));
	}
	
	@Test
	public void isCanUpdateTest(){
		GeneralServiceBean gbean = new GeneralServiceBean();
		Agent admin = gbean.getAgent("ntadmin", "WN");
		Agent agent = gbean.getAgent("ntagent", "WN");
		
		Calendar now = GregorianCalendar.getInstance();
		Calendar nowMinus2 = GregorianCalendar.getInstance();
		nowMinus2.add(Calendar.DATE, -2);
		Calendar nowMinus31 = GregorianCalendar.getInstance();
		nowMinus31.add(Calendar.DATE, -31);
		
		assertTrue(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_BAG_DROP_ADMIN,admin));
		assertTrue(BagDropUtils.getModifyRange(admin) == 30);
		assertTrue(BagDropUtils.canUpdateByDate(admin, now.getTime()));
		assertTrue(BagDropUtils.canUpdateByDate(admin, nowMinus2.getTime()));
		assertFalse(BagDropUtils.canUpdateByDate(admin, nowMinus31.getTime()));
		
		assertFalse(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_BAG_DROP_ADMIN,agent));
		assertTrue(BagDropUtils.getModifyRange(agent) == 1);
		assertTrue(BagDropUtils.canUpdateByDate(agent, now.getTime()));
		assertFalse(BagDropUtils.canUpdateByDate(agent, nowMinus2.getTime()));
		assertFalse(BagDropUtils.canUpdateByDate(agent, nowMinus31.getTime()));
	}
}
