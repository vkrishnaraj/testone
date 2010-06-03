package com.bagnet.nettracer.tracing.db;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.PaxCommunication.PaxCommunicationStatus;
import com.bagnet.nettracer.tracing.utils.HibernateAnnotationUtil;
import com.bagnet.nettracer.tracing.utils.SmsEmailService;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

public class PaxMessageTriggerTest {
	public static void main(String[] args) {
		PaxMessageTriggerTest obj = new PaxMessageTriggerTest();

		
//		PaxMessageTrigger myPaxMessageTrigger = 
//			obj.getPaxMessageTriggerByTriggerKey("MOVE_LZ_EN");
		
//		System.out.println(myPaxMessageTrigger.getEmailContentText());
		
		String myIncidentId = "FLLWS00060157";  //open status
		
		//myIncidentId = "FLLWS00060113"; //not open status
		
		//myIncidentId = "FLLWS00060114"; //not of the type lost delay
		
		Incident myIncident = obj.getIncidentById(myIncidentId);
		
		if (myIncident == null) {
			//System.out.println("incident is null, try another one.");
		} else {
			SmsEmailService smsEmailService = new SmsEmailService();
			
			//myIncident.setLanguage("es");
			myIncident.setLanguage("fr");
			//smsEmailService.sendMoveToLzMessage(myIncident);
			smsEmailService.sendBdoMessage(myIncident);
			//smsEmailService.send24HoursMessage(myIncident);
		}

	}
	
	
	public PaxMessageTrigger getPaxMessageTriggerByTriggerKey(String triggerKey) {
		PaxMessageTrigger result = null;
		
		Session session = HibernateWrapper.getSession().openSession();
		try {
			result = (PaxMessageTrigger) session.get(PaxMessageTrigger.class, triggerKey);
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}
	
	public Incident getIncidentById(String incidentId) {
		Incident result = null;
		
		Session session = HibernateWrapper.getSession().openSession();
		try {
			result = (Incident) session.get(Incident.class, incidentId);
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}

}
