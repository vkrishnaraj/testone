package aero.nettracer.serviceprovider.ws_1_0.res.sabre;

import static org.junit.Assert.*;

import java.util.regex.Matcher;

import org.junit.Test;

import aero.nettracer.serviceprovider.ws_1_0.res.sabre.Reservation;

public class ReservationTest {
	@Test
	public void phonePatternTest(){
		String phone = "555 444 7777-H";
		Matcher m = Reservation.phonePattern.matcher(phone);
		assertTrue(m.find());
		assertTrue(m.group(1).equals("555 444 7777"));
		
		phone = "5554447777-H";
		m = Reservation.phonePattern.matcher(phone);
		assertTrue(m.find());
		assertTrue(m.group(1).equals("5554447777"));
		
		phone = "555-444-7777-H";
		m = Reservation.phonePattern.matcher(phone);
		assertTrue(m.find());
		assertTrue(m.group(1).equals("555-444-7777"));
		
		phone = "978 6219250-M";
		m = Reservation.phonePattern.matcher(phone);
		assertTrue(m.find());
		assertTrue(m.group(1).equals("978 6219250"));
	
		phone = "6465750890-S JING";
		m = Reservation.phonePattern.matcher(phone);
		assertTrue(m.find());
		assertTrue(m.group(1).equals("6465750890"));
		
		phone = "8034875542-B-1.1";
		m = Reservation.phonePattern.matcher(phone);
		assertTrue(m.find());
		assertTrue(m.group(1).equals("8034875542"));
	}
}
