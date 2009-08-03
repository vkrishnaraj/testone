package com.bagnet.clients;

import org.hibernate.Session;
import org.junit.Test;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Incident;

public class USDbTest {
	
	@Test
	public void testScanners() {
		
		Session sess = HibernateWrapper.getSession().openSession();
		// DEV:
		//String inc = "XMHUS00000509";
		// BETA:
		String inc = "LGAUS00007448";
		
		Incident x = (Incident) sess.load(Incident.class, inc);
		System.out.println("");
		sess.close();
		assert(x.getIncident_ID().equals(inc));
	}
}
