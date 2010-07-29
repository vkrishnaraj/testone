package aero;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.junit.Test;

import aero.nettracer.integrations.us.scanners.SendForward;
import aero.nettracer.integrations.us.scanners.data.Forward;
import aero.nettracer.integrations.us.scanners.data.Segment;

public class Misctest {

	@Test
	public void tester() {
		SendForward a = new SendForward();
		
		ArrayList<Forward> payload = new ArrayList<Forward>();

		Forward f = new Forward();
		payload.add(f);
		f.setComment("COMMENT");
		f.setTagNumber("US123123");

		ArrayList<Segment> s = new ArrayList<Segment>();
		f.setSegments(s);

		// for (Itinerary i : fw.getItinerary()) {
		Segment seg = new Segment();
		s.add(seg);

		seg.setCarrier("US");

		// if (i.getFlightDate() != null) {
		seg.setFlightDate(new GregorianCalendar());
		// }

		// if (i.getFlightNumber() != null &&
		// i.getFlightNumber().trim().length() > 0) {
		try {
			int flightNum = Integer.parseInt("0123");
			seg.setFlightNumber(flightNum);
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("Couldn't set flight date in forward message due to parsing issue",
			// e);
		}
		// }

		seg.setFrom("XAX");
		seg.setTo("XLF");
		// }

		a.sendForwards(payload);
	}
}
