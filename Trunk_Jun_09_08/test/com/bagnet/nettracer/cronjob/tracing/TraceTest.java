package com.bagnet.nettracer.cronjob.tracing;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.bagnet.nettracer.cronjob.tracing.dto.Score;

public class TraceTest {
	
	public double trace(String incident_ID, String ohd_ID) {
		Score score = Trace.trace(incident_ID, ohd_ID, null);
		System.out.print("Comparing Incident: " + incident_ID + " to OHD: " + ohd_ID +"\n\n");
		System.out.print(score.toString());
		return score.getOverallScore();
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	//@Test
	public void testTrace1() {
		// 217905
		// 100% overall match
		// 100% claim check
		// 100% record locator
		String incident_ID = "CTGNK00025246";
		String ohd_ID = "CBTNK00021101";
		double actual = trace(incident_ID, ohd_ID);
		assertEquals(100, actual, .01);
	}
	
	
	//@Test
	public void testTrace2() {
		// 217904
		// 90% overall match
		// 100% claim check
		// 60% record locator
		String incident_ID = "SJONK00023611";
		String ohd_ID = "MYRNK00020086";
		double actual = trace(incident_ID, ohd_ID);
		assertEquals(100, actual, .01);
	}
	
	//@Test
	public void testTrace3() {
		// ??
		// 80% overall match
		// 100% itinerary
		// 100% color
		// 100% type
		String incident_ID = "SJUNK00028156";
		String ohd_ID = "SJUNK00020998";
		trace(incident_ID, ohd_ID);
	}
	
	//@Test
	public void testTrace4() {
		// 217779
		// 78.4% overall match
		// 96% Name match
		// 96% Name on bag match
		// 100% Itinerary
		// 100% Type
		// 100% Color
		String incident_ID = "POSNK00027997";
		String ohd_ID = "LIMNK00021078";
		double actual = trace(incident_ID, ohd_ID);
		assertEquals(100, actual, .01);
	}
	
	
	/**
	 * Should return a relatively low match.
	 */
	//@Test
	public void testTrace5() {
		// 215210
		// 90% Member (NK)
		// 100% Tertiary
		// 100% Type
		String incident_ID = "LGANK00023157";
		String ohd_ID = "FLLNK00019875";
		double actual = trace(incident_ID, ohd_ID);
		assertTrue(actual < 25);
	}
	
	/**
	 * Should return a relatively low match.
	 */
	//@Test
	public void testTrace6() {

		String incident_ID = "PSENK00019608";
		String ohd_ID = "FLLNK00015144";
		double actual = trace(incident_ID, ohd_ID);
		assertTrue(actual < 25);
	}
	
	//@Test
	public void testTrace7() {

		String incident_ID = "LGANK00000348";
		String ohd_ID = "LGANK00013240";
		double actual = trace(incident_ID, ohd_ID);
		//assertTrue(actual < 25);
	}
	
	@Test
	public void testTrace8() {

		String incident_ID = "DCANK00000307";
		String ohd_ID = "CBTNK00005337";
		double actual = trace(incident_ID, ohd_ID);
		//assertTrue(actual < 25);
	}
	
}
