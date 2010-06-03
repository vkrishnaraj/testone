package com.bagnet.nettracer.cronjob.tracing;


import org.junit.Test;

import com.bagnet.nettracer.cronjob.tracing.dto.Score;

//import com.bagnet.nettracer.cronjob.tracing.dto.Score;

public class TraceTest {
	
	public double trace(String incident_ID, String ohd_ID) {
		Score score = Trace.trace(incident_ID, ohd_ID, null);
		System.out.print("Comparing Incident: " + incident_ID + " to OHD: " + ohd_ID +"\n\n");
		System.out.print(score.toString());
		return score.getOverallScore();
	}
//	
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}
//
//	@Before
//	public void setUp() throws Exception {
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//	
//	
	@Test
	public void testTrace1() {
		String incident_ID = "IALUS00001087";
		String ohd_ID = "JFLUS00009106";
		double actual = trace(incident_ID, ohd_ID);

	}
//	
//	
//	
//	public void testTraceing() {
//		//PassiveTrace.startPassiveTracing("US", "Test Instance", PassiveTrace.PTMode.OLD);
//	}
//	
////	@Test
////	public void test() {
////		Date x = new Date();
////		GregorianCalendar a = new GregorianCalendar();
////		a.setTime(x);
////		a.set(Calendar.SECOND, 0);
////		a.set(Calendar.MILLISECOND, 0);
////		a.set(Calendar.MINUTE, 0);
////		a.set(Calendar.HOUR_OF_DAY, 0);
////		Date y = a.getTime();
////		
////		int z = 1;
////		
////	}
	
}
