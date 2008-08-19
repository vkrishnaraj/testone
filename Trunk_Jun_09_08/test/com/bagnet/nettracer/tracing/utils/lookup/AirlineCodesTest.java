package com.bagnet.nettracer.tracing.utils.lookup;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bagnet.nettracer.exceptions.BagtagException;

public class AirlineCodesTest {

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
	
	@Test
	public void testGetTwoLetterAirlineCode1() {
		String threeDigitCode = "006";
		assertEquals("DL", AirlineCodes.getTwoLetterAirlineCode(threeDigitCode));
	}
	
	@Test
	public void testGetTwoLetterAirlineCode2() {
		String threeDigitCode = "037";
		assertEquals("US", AirlineCodes.getTwoLetterAirlineCode(threeDigitCode));
	}

	@Test
	public void testGetTwoLetterAirlineCode3() {
		String threeDigitCode = "000";
		assertEquals(null, AirlineCodes.getTwoLetterAirlineCode(threeDigitCode));
	}
	
	@Test
	public void testGetTwoLetterAirlineCode4() {
		String threeDigitCode = null;
		assertEquals(null, AirlineCodes.getTwoLetterAirlineCode(threeDigitCode));
	}
	
	@Test
	public void testGetTwoLetterAirlineCode5() {
		String threeDigitCode = "279";
		assertEquals("B6", AirlineCodes.getTwoLetterAirlineCode(threeDigitCode));
	}
	
	
	@Test
	public void testGetTwoLetterAirlineCode6() {
		String threeDigitCode = "951";
		assertEquals("6B", AirlineCodes.getTwoLetterAirlineCode(threeDigitCode));
	}
	
	@Test
	public void testGetThreeDigitTicketingCode1() {
		String twoCharacterCode = "DL";
		assertEquals("006", AirlineCodes.getThreeDigitTicketingCode(twoCharacterCode));
	}	
	
	@Test
	public void testGetThreeDigitTicketingCode2() {
		String twoCharacterCode = "US";
		assertEquals("037", AirlineCodes.getThreeDigitTicketingCode(twoCharacterCode));
	}	
	
	@Test
	public void testGetThreeDigitTicketingCode3() {
		String twoCharacterCode = "XX";
		assertEquals(null, AirlineCodes.getThreeDigitTicketingCode(twoCharacterCode));
	}	
	
	@Test
	public void testGetThreeDigitTicketingCode4() {
		String twoCharacterCode = null;
		assertEquals(null, AirlineCodes.getThreeDigitTicketingCode(twoCharacterCode));
	}	
	
	@Test
	public void testGetThreeDigitTicketingCode5() {
		String twoCharacterCode = "B6";
		assertEquals("279", AirlineCodes.getThreeDigitTicketingCode(twoCharacterCode));
	}	
	
	@Test
	public void testGetThreeDigitTicketingCode6() {
		String twoCharacterCode = "6B";
		assertEquals("951", AirlineCodes.getThreeDigitTicketingCode(twoCharacterCode));
	}	
	
	@Test
	public void testGetTwoCharacterBagTag1() throws BagtagException {
		String bagTag = "0006123456";
		assertEquals("DL123456", AirlineCodes.getTwoCharacterBagTag(bagTag));
	}
	
	@Test
	public void testGetTwoCharacterBagTag2() throws BagtagException {
		String bagTag = "006123456";
		assertEquals("DL123456", AirlineCodes.getTwoCharacterBagTag(bagTag));
	}
	
	@Test(expected = BagtagException.class)   
	public void testGetTwoCharacterBagTag3() throws BagtagException {
		String bagTag = "06123456";
		AirlineCodes.getTwoCharacterBagTag(bagTag);
	}
	
	@Test
	public void testGetTwoCharacterBagTag4() throws BagtagException {
		String bagTag = "0951123456";
		assertEquals("6B123456", AirlineCodes.getTwoCharacterBagTag(bagTag));
	}
	
	@Test
	public void testGetFullBagTag1() throws BagtagException {
		String bagTag = "DL123456";
		assertEquals("0006123456", AirlineCodes.getFullBagTag(bagTag));
	}
	
	@Test
	public void testGetFullBagTag2() throws BagtagException {
		String bagTag = "US123456";
		assertEquals("0037123456", AirlineCodes.getFullBagTag(bagTag));
	}
	
	@Test(expected = BagtagException.class)
	public void testGetFullBagTag3() throws BagtagException {
		String bagTag = "XX123456";
		AirlineCodes.getFullBagTag(bagTag);
	}
	
	@Test
	public void testGetFullBagTag4() throws BagtagException {
		String bagTag = "6B123456";
		assertEquals("0951123456", AirlineCodes.getFullBagTag(bagTag));
	}
}
