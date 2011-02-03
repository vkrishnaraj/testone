package com.bagnet.nettracer.tracing.db;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;

public class TraceIncidentTest {
	
	@Test
	public void testLoadTraceIncident() {

		IncidentBMO ibmo = new IncidentBMO();
		int n = 1000;
		int iterations = 10;
		String sql = "select Incident_ID from Incident limit " + n + ";";
		Session session = HibernateWrapper.getSession().openSession();
		List ids = session.createSQLQuery(sql).list();
		
		long totalTime = 0;
		for (int i = 1; i <= iterations; ++i) {
			Timestamp start = new Timestamp(System.currentTimeMillis());
			for (int j = 0; j < ids.size(); ++j) {
				ibmo.findTraceIncidentByID((String) ids.get(j));
			}
			Timestamp end = new Timestamp(System.currentTimeMillis());
			long diff = end.getTime() - start.getTime();
			System.out.println("Loaded " + n + " TraceIncident objects in: " + diff + "ms on run: " + i);
			totalTime += diff;
		}
		System.out.println("Average time for all runs: " + totalTime / iterations + "ms");		
	}

}
