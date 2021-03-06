package com.bagnet.clients.us;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.mail.internet.InternetAddress;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.core.remoting.impl.netty.TransportConstants;

import aero.nettracer.integrations.us.scanners.data.Forward;
import aero.nettracer.integrations.us.scanners.data.Segment;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardMessage;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.Itinerary;

import com.bagnet.nettracer.email.HtmlEmail;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.db.OHD_Log_Itinerary;

public class ForwardIntegrationImpl {
	private static Logger logger = Logger.getLogger(ForwardIntegrationImpl.class);

	// This is for single and/or bulk onhand forwards.
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
				
				// New rule per US Airways: send expedite if exists.  If not, send bag tag.
				if (log.getExpeditenum() != null && log.getExpeditenum().trim().length() > 0) {
					f.setTagNumber(log.getExpeditenum().trim());
				} else if (log.getOhd().getClaimnum() != null && log.getOhd().getClaimnum().length() > 0) {
					f.setTagNumber(log.getOhd().getClaimnum().trim());
				} else {
					logger.info("No expedite or bag tag exists - aborting sendMessage(List<OHD_Log>)");
					return;
				}

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
	
	// This is for BEORN.
	public void sendMessage(ForwardMessage fw) {
		if (!PropertyBMO.isTrue(PropertyBMO.SEND_FORWARD_NOTIFICATIONS)) {
			return;
		}

		// Generate Payload
		ArrayList<Forward> payload = new ArrayList<Forward>();
		
		Forward f = new Forward();
		payload.add(f);
		f.setComment(fw.getSuplementaryInfo());
				
		// New rule per US Airways: send expedite if exists.  If not, send bag tag.
		if (fw.getExpediteTag() != null && fw.getExpediteTag().trim().length() > 0) {
			f.setTagNumber(fw.getExpediteTag().trim());
		} else if (fw.getTagNum() != null && fw.getTagNum().trim().length() > 0) {
			f.setTagNumber(fw.getTagNum().trim());
		} else {
			logger.info("No expedite or bag tag exists - aborting sendMessage(ForwardMessage fw>)");
			return;
		}
		
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

	    String DEFAULT_DESTINATION = PropertyBMO.getValue(PropertyBMO.HORNETQ_DEST);
	    String DEFAULT_FACTORY= PropertyBMO.getValue(PropertyBMO.HORNETQ_FACTORY);
	    String DEFAULT_URL= PropertyBMO.getValue(PropertyBMO.HORNETQ_URL);
	    String DEFAULT_HOST= PropertyBMO.getValue(PropertyBMO.HORNETQ_HOST);
	    int DEFAULT_PORT= PropertyBMO.getValueAsInt(PropertyBMO.HORNETQ_PORT);
	    String DEFAULT_USERNAME = PropertyBMO.getValue(PropertyBMO.HORNETQ_USER);//"nettracer";
	    String DEFAULT_PASSWORD = PropertyBMO.getValue(PropertyBMO.HORNETQ_PASS);//"ntMSGpass1!";
	    boolean REMOTE_CREDS = PropertyBMO.isTrue(PropertyBMO.HORNETQ_REMOTE_CREDS);
	    boolean JMS_CREDS = PropertyBMO.isTrue(PropertyBMO.HORNETQ_JMS_CREDS);
	    boolean USE_JNDI = PropertyBMO.isTrue(PropertyBMO.HORNETQ_USE_JNDI);

		try {
			ConnectionFactory cf;
			Queue queue;
			
			if (USE_JNDI) {
				
				Properties props = new Properties();
				props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
				props.put(Context.PROVIDER_URL, DEFAULT_URL);
				if (REMOTE_CREDS) {
					props.put(Context.SECURITY_PRINCIPAL, DEFAULT_USERNAME);
					props.put(Context.SECURITY_CREDENTIALS, DEFAULT_PASSWORD);
				}
				initialContext = new InitialContext(props);			
				
				cf = (ConnectionFactory) initialContext.lookup(DEFAULT_FACTORY);
				queue = (Queue) initialContext.lookup(DEFAULT_DESTINATION);
				
			} else {
			
				Map<String, Object> connParams = new HashMap<String, Object>();
				
				connParams.put(TransportConstants.PORT_PROP_NAME, DEFAULT_PORT);
				connParams.put(TransportConstants.HOST_PROP_NAME, DEFAULT_HOST);
				
				TransportConfiguration tc = new TransportConfiguration(NettyConnectorFactory.class.getName(), connParams);
	
				// Step 3. Perform a lookup on the Connection Factory
				cf = (ConnectionFactory) HornetQJMSClient.createConnectionFactoryWithoutHA(JMSFactoryType.CF, tc);
				queue = HornetQJMSClient.createQueue(DEFAULT_DESTINATION);

			}
			// Step 4.Create a JMS Connection
			if (JMS_CREDS) {
				connection = cf.createConnection(DEFAULT_USERNAME, DEFAULT_PASSWORD);
			} else {
				connection = cf.createConnection();
			}

			//Step 5. Create a JMS Session
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
				
				he.setSubject("Alert: US Airways TestQueue not bound. "+System.getProperty("instance.ref") );
				
				he.setHtmlMsg("Test queue not bound - please restart ntservice.\n" + e.getStackTrace().toString());
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

	// WorldTracer forward
	public void sendMessage(ForwardOhd fw) {
		if (!PropertyBMO.isTrue(PropertyBMO.SEND_FORWARD_NOTIFICATIONS)) {
			return;
		}
		// Generate Payload
		ArrayList<Forward> payload = new ArrayList<Forward>();
		
		Forward f = new Forward();
		payload.add(f);
		f.setComment(fw.getSupplementaryInfo());
		
		OhdBMO obmo = new OhdBMO();
		OHD ohd = null;
		
		try {
			ohd = obmo.findOHDByID(fw.getOhdId());
		} catch (Exception e) {
			// Ignore - OHD does not exist.
		}
		
		
		// New rule per US Airways: send expedite if exists.  If not, send bag tag.
		if (fw.getExpediteNumber() != null && fw.getExpediteNumber().trim().length() > 0) {
			f.setTagNumber(fw.getExpediteNumber().trim());
		} else if (ohd != null && ohd.getClaimnum() != null && ohd.getClaimnum().trim().length() > 0) {
			f.setTagNumber(ohd.getClaimnum().trim());
		} else {
			logger.info("No expedite or bag tag exists - aborting sendMessage(ForwardOhd fw)");
			return;
		}
		
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
