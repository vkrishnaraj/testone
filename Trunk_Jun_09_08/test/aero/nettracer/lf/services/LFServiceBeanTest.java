package aero.nettracer.lf.services;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashSet;

import org.junit.Test;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFAddress;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFPerson;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;
import com.bagnet.nettracer.tracing.db.lf.LFReservation;

public class LFServiceBeanTest {

	@Test
	public void lostSaveLoadTest(){
		try {
			LFServiceBean bean = new LFServiceBean();
			LFLost lost = createLostTestCase();
			long lostId = bean.saveOrUpdateLostReport(lost);
			assertTrue(lostId != -1);
			
			LFLost loaded = bean.getLostReport(lostId);
			assertTrue(loaded != null && loaded.getId() == lostId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void foundSaveLoadTest(){
		LFServiceBean bean = new LFServiceBean();
		LFFound found = createFoundTestCase();
		long foundId = bean.saveOrUpdateFoundItem(found);
		assertTrue(foundId != -1);
		
		LFFound loaded = bean.getFoundItem(foundId);
		assertTrue(loaded != null && loaded.getId() == foundId);
	}
	
	
	public LFLost createLostTestCase(){
		LFLost lost = new LFLost();
		
		Agent agent = new Agent();
		agent.setAgent_ID(119);
		lost.setAgent(agent);
		
		LFPerson client = new LFPerson();
		client.setFirstName("Bob");
		client.setLastName("wehadababyitsaboy");
		client.setEmail("bob@aol.com");
		LFAddress address = new LFAddress();
		address.setAddress1("1505 Windy Ridge Ln");
		address.setCity("Atlanta");
		address.setState("GA");
		address.setZip("30339");
		client.setAddress(address);
		
		LFPhone phone = new LFPhone();
		HashSet<LFPhone> phones = new HashSet<LFPhone>();
		phone.setPerson(client);
		phone.setPhoneNumber("4044140102");
		phone.setNumberType(LFPhone.PRIMARY);
		phone.setPhoneType(LFPhone.MOBILE);
		phones.add(phone);
		client.setPhones(phones);
		lost.setClient(client);
		
		Station location = new Station();
		location.setStation_ID(1);
		lost.setLocation(location);
		
		Status status = new Status();
		status.setStatus_ID(TracingConstants.LF_STATUS_OPEN);
		lost.setStatus(status);
		
		HashSet<LFItem> items = new HashSet<LFItem>();
		LFItem item = new LFItem();
		item.setBrand("SAMSUNG");
		item.setColor("BK");
		item.setDescription("Samsung phone");
		item.setLost(lost);
		item.setStatus(status);
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
		agent.setAgent_ID(119);
		found.setAgent(agent);
		
		Station location = new Station();
		location.setStation_ID(1);
		found.setLocation(location);
		
		Status status = new Status();
		status.setStatus_ID(TracingConstants.LF_STATUS_OPEN);
		found.setStatus(status);
//		found.setDisposition(status);
		
		LFItem item = new LFItem();
		item.setBrand("SAMSUNG");
		item.setColor("BK");
		item.setDescription("Samsung phone");
		item.setStatus(status);
//		item.setDisposition(status);
		found.setItem(item);
		
		
		return found;
	}
}
