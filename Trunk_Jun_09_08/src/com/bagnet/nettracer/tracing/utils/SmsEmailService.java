package com.bagnet.nettracer.tracing.utils;

import com.bagnet.nettracer.tracing.bmo.PaxMessageTriggerBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.PaxMessageTrigger;
import com.bagnet.nettracer.tracing.db.Status;

import com.bagnet.nettracer.email.HtmlEmail;

import java.io.File;
import java.net.URL;
import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

public class SmsEmailService {
	private static Logger logger = Logger.getLogger(SmsEmailService.class);
	
	public void sendMoveToLzMessage(Incident incident) {
		sendMessage("MOVE_LZ", incident);
		//only send email when pax email is on file
		//only send email when file is open, 
		//status is lost or delayed, or BDOs
		
	}

	public void sendBdoMessage(Incident incident) {
		sendMessage("BDO", incident);
		
		
		//only send email when pax email is on file
		//only send email when file is open, 
		//status is lost or delayed, or BDOs
	}
	
	public void send24HoursMessage(Incident incident) {
		//sendMessage("24_HRS", incident);
		//sendMessageWithImage("24_HRS", incident);
		sendHtmlMessage("24_HRS", incident);
		
		//only send email when pax email is on file
		//only send email when file is open, 
		//status is lost or delayed, or BDOs
	}
	
	private void sendMessage(String key, Incident incident) {
		//System.out.println("inside sendMessage.");
		//first check if we should be sending email
		//only send email when pax email is on file
		//only send email when file is open, 
		//status is lost or delayed, or BDOs
		Status myStatus = incident.getStatus();
		int intStatusId = myStatus.getStatus_ID();
		if ((intStatusId == TracingConstants.MBR_STATUS_OPEN) && (incident.getItemtype_ID() == TracingConstants.LOST_DELAY)) {
			//should send
			try {
				String email = ((Passenger)incident.getPassenger_list().get(0)).getAddress(0).getEmail();
				String language = incident.getLanguage();
				if (language == null) {
					language = "en";
				}
				if (email != null) {
					//System.out.println("all but one green light - ready to send email");
					//send email
					//LANGUAGE PREFERRED FIRST - dAO
					
					//PaxMessageTrigger - dao.method(key, language); - preferred language otherwise english
					//if neither, then throw exception called runtime exception
					String myKey = key + "_" + language.toUpperCase();
					PaxMessageTrigger myPaxMessageTrigger = PaxMessageTriggerBMO.getPaxMessageTrigger(myKey, language);
					
					if (myPaxMessageTrigger != null) {
						
						System.out.println("all green light - ready to send email: key=" + myKey);
						  //proceed to send email
					      // Recipient's email ID needs to be mentioned.
					      String to = "ianyu@nettracer.aero";
					      //String to = email;

					      // Sender's email ID needs to be mentioned
					      String from = "web@gmail.com";

					      // Assuming you are sending email from localhost
					      String host = "74.86.4.179";
					      String port = "8625";

					      // Get system properties
					      Properties properties = System.getProperties();

					      // Setup mail server
					      properties.setProperty("mail.smtp.host", host);
					      properties.setProperty("mail.smtp.port", port);
					      
					      // Get the default Session object.
					      javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(properties);

					      try{
					         // Create a default MimeMessage object.
					         MimeMessage message = new MimeMessage(mailSession);

					         // Set From: header field of the header.
					         message.setFrom(new InternetAddress(from));

					         // Set To: header field of the header.
					         message.addRecipient(Message.RecipientType.TO,
					                                  new InternetAddress(to));

					         String incidentId = incident.getIncident_ID();
					         String mySubjectLine = "RE: " + myPaxMessageTrigger.getSubjectLine() + " ";
					         // Set Subject: header field
					         message.setSubject(mySubjectLine + incidentId);

					         // Send the actual HTML message, as big as you like
							 HashMap<String, String> h = new HashMap<String, String>();
							 h.put("INCIDENT_ID", incidentId);
							 
							 String rawEmailContent = myPaxMessageTrigger.getEmailContentText();
							 String myEmailContent = EmailParser.parsePaxMessageTrigger(rawEmailContent, h);
					         message.setContent(myEmailContent, "text/html" );
					         

					         // Send message
					         Transport.send(message);
					         logger.info("Sent message successfully....");
					      }catch (MessagingException mex) {
					         mex.printStackTrace();
					      }
						
					}
				} 
			} catch (Exception e) {
				// Don't send email
				e.printStackTrace();
			}
		
		} 
		
	}
	//try this
	private void sendHtmlMessage(String key, Incident incident) {
		//System.out.println("inside sendMessage.");
		//first check if we should be sending email
		//only send email when pax email is on file
		//only send email when file is open, 
		//status is lost or delayed, or BDOs
		Status myStatus = incident.getStatus();
		int intStatusId = myStatus.getStatus_ID();
		if ((intStatusId == TracingConstants.MBR_STATUS_OPEN) && (incident.getItemtype_ID() == TracingConstants.LOST_DELAY)) {
			//should send
			try {
				String email = ((Passenger)incident.getPassenger_list().get(0)).getAddress(0).getEmail();
				String language = incident.getLanguage();
				if (language == null) {
					language = "en";
				}
				if (email != null) {
					//System.out.println("all but one green light - ready to send email");
					//send email
					//LANGUAGE PREFERRED FIRST - dAO
					
					//PaxMessageTrigger - dao.method(key, language); - preferred language otherwise english
					//if neither, then throw exception called runtime exception
					String myKey = key + "_" + language.toUpperCase();
					PaxMessageTrigger myPaxMessageTrigger = PaxMessageTriggerBMO.getPaxMessageTrigger(myKey, language);
					
					if (myPaxMessageTrigger != null) {
						
						System.out.println("all green light - ready to send email: key=" + myKey);
						  //proceed to send email
					      // Recipient's email ID needs to be mentioned.
					      String to = "bsmith@nettracer.aero";
					      to = "ianyu@nettracer.aero";

					      // Sender's email ID needs to be mentioned
					      String from = "web@gmail.com";

					      // Assuming you are sending email from localhost
					      String host = "74.86.4.179";
					      String port = "8625";
					      int intPort = 8625;

					      // Get system properties
					      Properties properties = System.getProperties();

					      // Setup mail server
					      properties.setProperty("mail.smtp.host", host);
					      properties.setProperty("mail.smtp.port", port);
					      
					      // Get the default Session object.
					      javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(properties);

					      try{
					         // Create a default MimeMessage object.
					         MimeMessage message = new MimeMessage(mailSession);

					         // Set From: header field of the header.
					         message.setFrom(new InternetAddress(from));

					         // Set To: header field of the header.
					         message.addRecipient(Message.RecipientType.TO,
					                                  new InternetAddress(to));

					         String incidentId = incident.getIncident_ID();
					         String mySubjectLine = "RE: " + myPaxMessageTrigger.getSubjectLine() + " ";
					         // Set Subject: header field
					         message.setSubject(mySubjectLine + incidentId);

					         // Send the actual HTML message, as big as you like
							 HashMap<String, String> h = new HashMap<String, String>();
							 h.put("INCIDENT_ID", incidentId);
							 
							 
//							 String rawEmailContent = myPaxMessageTrigger.getEmailContentText();
//							 String myEmailContent = EmailParser.parsePaxMessageTrigger(rawEmailContent, h);
//					         message.setContent(myEmailContent, "text/html" );
					         

					         // Send message
					         //Transport.send(message);
					         
					         
					         //new approach
					         HtmlEmail he = new HtmlEmail();
					         he.setHostName(host);
					         he.setSmtpPort(intPort);
					         he.setFrom(from);
							 ArrayList<InternetAddress> al = new ArrayList<InternetAddress>();
							 al.add(new InternetAddress(to));
							 he.setTo(al);
							 he.setSubject(mySubjectLine);
								
							 String imagePath = "com/bagnet/clients/us/resources/";
							 String imageFileName = "usair-logo.jpg";
							 
//							 String img1 = he.embed(new URL("file:/C:/Java/workspace/nt_176/bin/com/bagnet/clients/us/resources/usair-logo.jpg"),
//									 "usair-logo.jpg");
							 
							 URL resource = Thread.currentThread().getContextClassLoader()
								.getResource("com/bagnet/clients/us/resources/usair-logo.jpg");
							 System.out.println("resource is set to:" + resource.toString());
							 String img1 = he.embed(new URL(resource.toString()),
									 imageFileName);
				
							 h.put("LOGO_IMG", img1);
							 
							 String rawEmailContent = myPaxMessageTrigger.getEmailContentText();
							 String myEmailContent = EmailParser.parsePaxMessageTrigger(rawEmailContent, h);
							 
					         he.setHtmlMsg(myEmailContent);
					         he.setCharset("UTF-8");
							 he.send();
								
					         logger.info("Sent message successfully....");
					      }catch (MessagingException mex) {
					         mex.printStackTrace();
					         System.out.println("MessagingException" + mex);
					      }catch (Exception maile) {
								logger.error("Unable to send mail due to smtp error: " + maile);
								System.out.println("Unable to send mail due to smtp error." + maile);
						  }
					}
				} 
			} catch (Exception e) {
				// Don't send email
				e.printStackTrace();
			}
		
		} 
		
	}
	
	//new multipart approach
	
	private void sendMessageWithImage(String key, Incident incident) {

		//first check if we should be sending email
		//only send email when pax email is on file
		//only send email when file is open, 
		//status is lost or delayed, or BDOs
		Status myStatus = incident.getStatus();
		int intStatusId = myStatus.getStatus_ID();
		if ((intStatusId == TracingConstants.MBR_STATUS_OPEN) && (incident.getItemtype_ID() == TracingConstants.LOST_DELAY)) {
			//should send
			try {
				String email = ((Passenger)incident.getPassenger_list().get(0)).getAddress(0).getEmail();
				String language = incident.getLanguage();
				if (language == null) {
					language = "en";
				}
				if (email != null) {

					//PaxMessageTrigger - dao.method(key, language); - preferred language otherwise english
					//if neither, then throw exception called runtime exception
					String myKey = key + "_" + language.toUpperCase();
					PaxMessageTrigger myPaxMessageTrigger = PaxMessageTriggerBMO.getPaxMessageTrigger(myKey, language);
					
					if (myPaxMessageTrigger != null) {
						
						System.out.println("all green light - ready to send email: key=" + myKey);
						  //proceed to send email
					      // Recipient's email ID needs to be mentioned.
					      String to = "ianyu@nettracer.aero";
					      //String to = email;

					      // Sender's email ID needs to be mentioned
					      String from = "web@gmail.com";

					      // Assuming you are sending email from localhost
					      String host = "74.86.4.179";
					      String port = "8625";

					      // Get system properties
					      Properties properties = System.getProperties();

					      // Setup mail server
					      properties.setProperty("mail.smtp.host", host);
					      properties.setProperty("mail.smtp.port", port);
					      
					      // Get the default Session object.
					      javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(properties);

					      try{
					         // Create a default MimeMessage object.
					         MimeMessage message = new MimeMessage(mailSession);

					         // Set From: header field of the header.
					         message.setFrom(new InternetAddress(from));

					         // Set To: header field of the header.
					         message.addRecipient(Message.RecipientType.TO,
					                                  new InternetAddress(to));

					         String incidentId = incident.getIncident_ID();
					         String mySubjectLine = "RE: " + myPaxMessageTrigger.getSubjectLine() + " ";
					         // Set Subject: header field
					         message.setSubject(mySubjectLine + incidentId);

					         // Send the actual HTML message, as big as you like
							 HashMap<String, String> h = new HashMap<String, String>();
							 h.put("INCIDENT_ID", incidentId);
							 h.put("LOGO_IMG", "<img src=\"cid:image\">");
							 
							 String rawEmailContent = myPaxMessageTrigger.getEmailContentText();
							 String myEmailContent = EmailParser.parsePaxMessageTrigger(rawEmailContent, h);
					         //message.setContent(myEmailContent, "text/html" );
							 
							 Multipart multipart = new MimeMultipart("related");
							 
							 message.setContent(multipart);
							 
							 BodyPart textPart = new MimeBodyPart();
							 textPart.setContent(myEmailContent, "text/html");
							 multipart.addBodyPart(textPart);
							 
							 BodyPart imagePart = createImagePart();
							 multipart.addBodyPart(imagePart);
							 
							 //message.setContent(multipart);

					         // Send message
					         Transport.send(message);
					         logger.info("Sent message successfully....");
					      }catch (MessagingException mex) {
					         mex.printStackTrace();
					      }
						
					}
				} 
			} catch (Exception e) {
				// Don't send email
				e.printStackTrace();
			}
		
		} 
		
	}
	
	private static BodyPart createTextPart() throws MessagingException {
		BodyPart textPart = new MimeBodyPart();
		textPart.setText("Please see attached image. Thanks");
		return textPart;
	}
	
	private static BodyPart createImagePart() throws MessagingException {
		BodyPart imagePart = new MimeBodyPart();
		File imageFile = findImageFile();
        DataSource fds = new FileDataSource
          (imageFile);
        imagePart.setDataHandler(new DataHandler(fds));
        imagePart.setHeader("Content-ID","<image>");
		return imagePart;
	}
	
	private static File findImageFile() {
		//Build a File object to the actual image.
		URL resource = Thread.currentThread().getContextClassLoader()
						.getResource("com/bagnet/clients/us/resources/usair-logo.jpg");
		System.out.println("resource is set to:" + resource.toString());
		return new File(resource.toExternalForm().replaceAll("%20", " "));
	}
}
