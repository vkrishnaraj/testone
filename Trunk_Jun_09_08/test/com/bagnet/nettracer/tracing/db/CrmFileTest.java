package com.bagnet.nettracer.tracing.db;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.hibernate.Session;
import org.junit.Test;

import com.bagnet.clients.us.CrmIntegration;
import com.bagnet.nettracer.cronjob.utilities.CronUtils;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public class CrmFileTest {

	// @Test
	public void crmTest() {
		CronUtils c = new CronUtils("US");
		c.sendUSFilesToCRM();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void crmTest2() throws RemoteException {
		Incident i = new Incident();
		
		Station s = new Station();
		s.setStationcode("PHX");
		i.setStationcreated(s);
		Passenger p = new Passenger();
		HashSet ps = new HashSet();
		ps.add(p);
		i.setPassengers(ps);
		p.setLastname("Smith");
		p.setFirstname("Byron");
		p.setSalutation(1);
		AirlineMembership m = new AirlineMembership();
		m.setMembershipnum("ASDF123123123123");
		p.setMembership(m);
		
		Address a = new Address();
		a.setAddress1("2675 Paces Ferry Rd.");
		a.setAddress2("Suite 240");
		a.setCity("Atlanta");
		a.setState_ID("GA");
		a.setZip("30068-1212");
		a.setCountrycode_ID("US");
		a.setEmail("bsmith@nettracer.aero");
		String phone = "home number";
		a.setHomephone(phone);
		a.setWorkphone(phone);
		a.setPager(phone);
		a.setAltphone(phone);
		a.setMobile(phone);
		
		
		HashSet as = new HashSet();
		as.add(a);
		p.setAddresses(as);
		
		
		i.setIncident_ID("XAXUS00051418");
		
		Date createdate = DateUtils.convertToDate("2010-01-01 18:10", "yyyy-mm-dd HH:mm", null);
//		Date createtime = DateUtils.convertToDate("12:10", "HH:mm", null);
		


		
		i.setCreatedate(createdate);
		i.setCreatetime(createdate);
		i.setCheckedlocation("2");
		ItemType itype = new ItemType();
		itype.setItemType_ID(1);
		i.setItemtype(itype);
		
		
		ArrayList itemlist = new ArrayList();
		i.setItemlist(itemlist);
		
		Item it2 = new Item();
		itemlist.add(it2);
		it2.setBagtype("94");
				
		Item it = new Item();
		itemlist.add(it);
		it.setBagtype("22");
		
		Session sess = HibernateWrapper.getSession().openSession();
		CrmIntegration c = new CrmIntegration();
		try {
		c.transmitToCrm(i, sess);
		} catch (Exception e) {
			e.printStackTrace();
		}
		sess.close();
		
	}
}
