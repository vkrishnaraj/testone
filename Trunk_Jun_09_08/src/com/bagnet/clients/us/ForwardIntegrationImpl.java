package com.bagnet.clients.us;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.mail.internet.InternetAddress;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import aero.nettracer.integrations.us.scanners.data.Forward;
import aero.nettracer.integrations.us.scanners.data.Segment;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardMessage;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.Itinerary;

import com.bagnet.nettracer.email.HtmlEmail;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.db.OHD_Log_Itinerary;

public class ForwardIntegrationImpl {
	private static Logger logger = Logger.getLogger(ForwardIntegrationImpl.class);

	
	public void sendMessage(List<OHD_Log> logs) {
		if (!PropertyBMO.isTrue(PropertyBMO.SEND_FORWARD_NOTIFICATIONS)) {
			return;
		}
		// Generate Payload
		ArrayList<Forward> payload = new ArrayList<Forward>();

		if (logs != null) {
			for (OHD_Log log : logs) {
				Forward f = new Forward();
				payload.add(f);

				if (log.getMessage() != null && log.getMessage().trim().length() > 0)
					f.setComment(log.getMessage().trim());

				f.setOnHandId(log.getOhd().getOHD_ID());
				f.setTagNumber(log.getExpeditenum());

				ArrayList<Segment> s = new ArrayList<Segment>();
				f.setSegments(s);

				for (OHD_Log_Itinerary i : (Set<OHD_Log_Itinerary>) log.getItinerary()) {
					Segment seg = new Segment();
					s.add(seg);

					seg.setCarrier(i.getAirline());

					if (i.getDepartdate() != null) {
						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime(i.getDepartdate());
						seg.setFlightDate(cal);
					}

					if (i.getFlightnum() != null && i.getFlightnum().trim().length() > 0) {
						try {
							int flightNum = Integer.parseInt(i.getFlightnum());
							seg.setFlightNumber(flightNum);
						} catch (Exception e) {
							logger.error("Couldn't set flight date in forward message due to parsing issue", e);
						}
					}

					seg.setFrom(i.getLegfrom());
					seg.setTo(i.getLegto());
				}
			}
		}

		sendMessage(payload);

	}
	
	public void sendMessage(ForwardMessage fw) {
		if (!PropertyBMO.isTrue(PropertyBMO.SEND_FORWARD_NOTIFICATIONS)) {
			return;
		}

		// Generate Payload
		ArrayList<Forward> payload = new ArrayList<Forward>();
		
		Forward f = new Forward();
		payload.add(f);
		f.setComment(fw.getSuplementaryInfo());
		f.setTagNumber(fw.getExpediteTag());
		
		ArrayList<Segment> s = new ArrayList<Segment>();
		f.setSegments(s);
		
		
		
		for (Itinerary i : fw.getItinerary()) {
			Segment seg = new Segment();
			s.add(seg);

			seg.setCarrier(i.getAirline());

			if (i.getFlightDate() != null) {
				seg.setFlightDate(i.getFlightDate());
			}

			if (i.getFlightNumber() != null && i.getFlightNumber().trim().length() > 0) {
				try {
					int flightNum = Integer.parseInt(i.getFlightNumber());
					seg.setFlightNumber(flightNum);
				} catch (Exception e) {
					logger.error("Couldn't set flight date in forward message due to parsing issue", e);
				}
			}

			seg.setFrom(i.getDepartureCity());
			seg.setTo(i.getArrivalCity());
		}
		
		
		sendMessage(payload);

	}

	private void sendMessage(ArrayList<Forward> payload) {
		Connection connection = null;
		InitialContext initialContext = null;

		try {
			// Step 1. Create an initial context to perform the JNDI lookup.
			initialContext = new InitialContext();

			// Step 2. Perfom a lookup on the queue
			Queue queue = (Queue) initialContext.lookup("/queue/testQueue");
			
			

			// Step 3. Perform a lookup on the Connection Factory
			ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("/ConnectionFactory");

			// Step 4.Create a JMS Connection
			connection = cf.createConnection();

			// Step 5. Create a JMS Session
			Session s = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Step 6. Create a JMS Message Producer
			MessageProducer producer = s.createProducer(queue);

			// Step 7. Create a Text Message
			ObjectMessage message = s.createObjectMessage(payload);

			// Step 8. Send the Message
			logger.info("About to send message...");
			producer.send(message);
			logger.info("Message sent...");

			Thread.sleep(10 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
			try{
				HtmlEmail he = new HtmlEmail();
				
				String from = "donotreply@usairways.com";
				String host = "relay.lcc.usairways.com";
				int port = 25;
				
				he.setHostName(host);
				he.setSmtpPort(port);

				he.setFrom(from);
				ArrayList al = new ArrayList();
				al.add(new InternetAddress("support@nettracer.aero"));
				he.setTo(al);
				
				he.setSubject("Alert: US Airways TestQueue not bound");
				
				he.setHtmlMsg("Test queue not bound - please restart ntservice.");
				he.send();
				
			}catch(Exception ex2){
				ex2.printStackTrace();
			}
		} finally {
			try {
				// Step 19. Be sure to close our JMS resources!
				if (initialContext != null) {
					initialContext.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void sendMessage(ForwardOhd fw) {
		if (!PropertyBMO.isTrue(PropertyBMO.SEND_FORWARD_NOTIFICATIONS)) {
			return;
		}
		// Generate Payload
		ArrayList<Forward> payload = new ArrayList<Forward>();
		
		Forward f = new Forward();
		payload.add(f);
		f.setComment(fw.getSupplementaryInfo());
		f.setTagNumber(fw.getExpediteNumber());
		
		ArrayList<Segment> s = new ArrayList<Segment>();
		f.setSegments(s);
		
		
		
		for (Itinerary i : fw.getItinerary()) {
			Segment seg = new Segment();
			s.add(seg);

			seg.setCarrier(i.getAirline());

			if (i.getFlightDate() != null) {
				seg.setFlightDate(i.getFlightDate());
			}

			if (i.getFlightNumber() != null && i.getFlightNumber().trim().length() > 0) {
				try {
					int flightNum = Integer.parseInt(i.getFlightNumber());
					seg.setFlightNumber(flightNum);
				} catch (Exception e) {
					logger.error("Couldn't set flight date in forward message due to parsing issue", e);
				}
			}

			seg.setFrom(i.getDepartureCity());
			seg.setTo(i.getArrivalCity());
		}
		
		sendMessage(payload);
	}

}
