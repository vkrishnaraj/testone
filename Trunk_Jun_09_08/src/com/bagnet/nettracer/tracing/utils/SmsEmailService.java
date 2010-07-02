package com.bagnet.nettracer.tracing.utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.email.HtmlEmail;
import com.bagnet.nettracer.tracing.bmo.PaxMessageTriggerBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.PaxMessageTrigger;
import com.bagnet.nettracer.tracing.db.Status;

public class SmsEmailService {
	private static Logger logger = Logger.getLogger(SmsEmailService.class);

	public void sendMoveToLzMessage(Incident incident) {
		// sendMessage("MOVE_LZ", incident);
		// only send email when pax email is on file
		// only send email when file is open,
		// status is lost or delayed, or BDOs
		sendHtmlMessage("MOVE_LZ", incident);

	}

	public void sendBdoMessage(Incident incident) {
		// sendMessage("BDO", incident);

		// only send email when pax email is on file
		// only send email when file is open,
		// status is lost or delayed, or BDOs
		sendHtmlMessage("BDO", incident);
	}

	public void send24HoursMessage(Incident incident) {
		// sendMessage("24_HRS", incident);
		// sendMessageWithImage("24_HRS", incident);
		sendHtmlMessage("24_HRS", incident);

		// only send email when pax email is on file
		// only send email when file is open,
		// status is lost or delayed, or BDOs
	}


	private void sendHtmlMessage(String key, Incident incident) {
		// logger.info("inside sendMessage.");
		// first check if we should be sending email
		// only send email when pax email is on file
		// only send email when file is open,
		// status is lost or delayed, or BDOs
		Status myStatus = incident.getStatus();
		int intStatusId = myStatus.getStatus_ID();
		if ((intStatusId == TracingConstants.MBR_STATUS_OPEN) && (incident.getItemtype_ID() == TracingConstants.LOST_DELAY)) {
			// should send
			try {
				String email = ((Passenger) incident.getPassenger_list().get(0)).getAddress(0).getEmail();
				String language = incident.getLanguage();
				if (language == null) {
					language = "en";
				}
				if (email != null && email.trim().length() > 0) {
					// send email

					// PaxMessageTrigger - dao.method(key, language); -
					// preferred language otherwise english
					// if neither, then throw exception called runtime exception
					String myKey = key + "_" + language.toUpperCase();
					PaxMessageTrigger myPaxMessageTrigger = PaxMessageTriggerBMO.getPaxMessageTrigger(myKey, language);

					if (myPaxMessageTrigger != null) {

						try {

							String incidentId = incident.getIncident_ID();
							String mySubjectLine = "RE: " + myPaxMessageTrigger.getSubjectLine() + " ";

							HashMap<String, String> h = new HashMap<String, String>();
							h.put("INCIDENT_ID", incidentId);

							// new approach
							HtmlEmail he = new HtmlEmail();

							Agent agent = incident.getAgent();
							he.setHostName(agent.getStation().getCompany().getVariable().getEmail_host());
							he.setSmtpPort(agent.getStation().getCompany().getVariable().getEmail_port());
							he.setFrom(agent.getStation().getCompany().getVariable().getEmail_from());

//							String toemail = null;


							ArrayList<InternetAddress> al = new ArrayList<InternetAddress>();

							List<Passenger> myPassengerList = incident.getPassenger_list();
							Passenger pax;
//							if (myPassengerList != null && myPassengerList.size() > 0) {
//								pax = (Passenger) myPassengerList.get(0);
//								if (pax != null) {
//									com.bagnet.nettracer.tracing.db.Address myAddress = pax.getAddress(0);
//									if (myAddress != null) {
//										toemail = myAddress.getEmail();
//									}
//								}
//							}
							
//							if (toemail != null) {
								al.add(new InternetAddress(email));
//							}

							he.setTo(al);
							he.setSubject(mySubjectLine);

							String imageFileName = TracerProperties.get("email.logo");

							URL resource = Thread.currentThread().getContextClassLoader().getResource(TracerProperties.get("email.logo.path"));

							String img1 = "";
							if (resource != null) {
								logger.info("resource is set to:" + resource.toString());
								img1 = he.embed(new URL(resource.toString()), imageFileName);
							}
							h.put("LOGO_IMG", img1);

							String rawEmailContent = myPaxMessageTrigger.getEmailContentText();
							String myEmailContent = EmailParser.parsePaxMessageTrigger(rawEmailContent, h);

							he.setHtmlMsg(myEmailContent);
							he.setCharset("UTF-8");
							he.send();

						} catch (MessagingException mex) {
							mex.printStackTrace();
							logger.info("MessagingException" + mex, mex);
						} catch (Exception maile) {
							logger.error("Unable to send mail due to smtp error: " + maile, maile);

						}
					}
				}
			} catch (Exception e) {

				logger.error("Error sending triggered email...", e);
				e.printStackTrace();
			}

		}

	}
}
