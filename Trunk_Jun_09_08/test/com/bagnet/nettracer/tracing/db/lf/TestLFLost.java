package com.bagnet.nettracer.tracing.db.lf;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashSet;

import org.apache.struts.action.ActionMessages;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.dao.lf.LFDeliveryDAO;
import com.bagnet.nettracer.tracing.dao.lf.LFLostDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.SecurityUtils;

public class TestLFLost {
	
	private static Session session;
	private static Transaction transaction;
	
	@BeforeClass
	public static void oneTimeSetUp() throws ParseException {
		session = HibernateWrapper.getSession().openSession();
		transaction = session.beginTransaction();
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		transaction.rollback();
		session.close();
	}
	
	@Test
	public void testSaveLFLost() {
		assertTrue(LFLostDAO.saveLost(getLFLost()));
	}
	
	@Test
	public void testLoadLFLost() {
		LFLost lost = LFLostDAO.loadLost(1);
		assertTrue(lost.getId() == 1);
	}
	
	@Test
	public void testSaveLFDelivery() {
		LFLost lost = LFLostDAO.loadLost(1);
		LFDelivery delivery = new LFDelivery();
		delivery.setLost(lost);
		delivery.setTrackingNumber("SOMELONGTRACKINGNUMBER");
		assertTrue(LFDeliveryDAO.saveDelivery(delivery));
		
		delivery = LFDeliveryDAO.loadDelivery(3);
		assertTrue(delivery.getTrackingNumber().equals("SOMELONGTRACKINGNUMBER"));
		assertTrue(delivery.getLost().getId() == 1);
		
		delivery = LFDeliveryDAO.getDeliveryByLostId(lost);
		assertTrue(delivery.getLost().getId() == 1);
	}
	
	private LFLost getLFLost() {
		Station pickupLocation = StationBMO.getStation(78);
		Station dropoffLocation = StationBMO.getStation(68);
		
		Agent agent = SecurityUtils.authUser("ntadmin", "IpoL!Jan7", "WS", 0, new ActionMessages());
		
		Status status = new Status();
		status.setStatus_ID(3);
		
		// reservation data
		LFReservation reservation = new LFReservation();
		reservation.setAgreementNumber("TEST123ABC");
		reservation.setPickupLocation(pickupLocation);
		reservation.setDropoffLocation(dropoffLocation);
		reservation.setMvaNumber("ABCDE12345");
		
		// person data
		LFPerson person = new LFPerson();
		person.setLastName("Sanders");
		person.setFirstName("Mike");
		person.setMiddleName("J");
		person.setEmail("me@myemail.com");
		
		// phone data
		LFPhone phone = new LFPhone();
		phone.setPerson(person);
		phone.setNumberType(LFPhone.PRIMARY);
		phone.setPhoneNumber("1112223333");

		LFPhone phone1 = new LFPhone();
		phone1.setPerson(person);
		phone1.setNumberType(LFPhone.SECONDARY);
		phone1.setPhoneNumber("4445556666");
		
		LinkedHashSet<LFPhone> phones = new LinkedHashSet<LFPhone>();
		phones.add(phone);
		phones.add(phone1);
		
		// address data
		LFAddress address = new LFAddress();
		address.setAddress1("2675 Paces Ferry Rd");
		address.setAddress2("Ste. 240");
		address.setCity("Atlanta");
		address.setState("GA");
		address.setZip("30339");
		address.setCountry("US");
		address.setProvince("N/A");
		
		person.setAddress(address);
		person.setPhones(phones);
		
		// lost data
		LFLost lost = new LFLost();
		lost.setClient(person);
		lost.setReservation(reservation);
		lost.setOpenDate(new Date());
		lost.setCloseDate(new Date());
		lost.setAgent(agent);
		lost.setLocation(dropoffLocation);
		lost.setStatus(status);
		
		// item data
		LFItem item = new LFItem();
		item.setLost(lost);
//		item.setCategory("Cell Phone");
//		item.setSubCategory("Nokia");
//		item.setShortDesc("Nokia 9300");
//		item.setLongDesc("Nokia 9300 with a blue case and broken screen");
		item.setSerialNumber("NK123");

		LFItem item1 = new LFItem();
		item1.setLost(lost);
//		item1.setCategory("Wallet");
//		item1.setSubCategory("Nike");
//		item1.setShortDesc("Nike wallet");
//		item1.setLongDesc("Nike wallet with $20 bucks inside");
		item1.setSerialNumber("NIKE1234");
		
		LinkedHashSet<LFItem> items = new LinkedHashSet<LFItem>();
		items.add(item);
		items.add(item1);
		lost.setItems(items);
		
		return lost;
	}
	
}
