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
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

public class PaxCommunicationTest {
	public static void main(String[] args) {
		PaxCommunicationTest obj = new PaxCommunicationTest();
		PaxCommunication paxCommunication1 = new PaxCommunication();
		//populate paxCommunication1
		Incident myIncident1 = new Incident();
		myIncident1.setIncident_ID("ABEUS00017604");
		Agent secretAgent = new Agent();
		secretAgent.setAgent_ID(15438);
		myIncident1.setAgent(secretAgent);
		ItemType myItemType = new ItemType();
		myItemType.setItemType_ID(1);
		myIncident1.setItemtype(myItemType);
		Station myStationAssigned = new Station();
		myStationAssigned.setStation_ID(69);
		myIncident1.setStationassigned(myStationAssigned);
		myIncident1.setStationcreated(myStationAssigned);
		Status myStatus = new Status();
		myStatus.setStatus_ID(13);
		myIncident1.setStatus(myStatus);
		paxCommunication1.setAgent(secretAgent);
		paxCommunication1.setIncident(myIncident1);
		
		Agent myAcknowledgeAgent = new Agent();
		myAcknowledgeAgent.setAgent_ID(15435);
		Station myStation = new Station();
		myStation.setStation_ID(104);
		myAcknowledgeAgent.setStation(myStation);
		paxCommunication1.setAcknowledge_agent(myAcknowledgeAgent);

		paxCommunication1.setStatus(PaxCommunicationStatus.NEW);
		Calendar day = new GregorianCalendar();
		Date myDay = day.getTime();
		myDay = TracerDateTime.getGMTDate();
		paxCommunication1.setCreatedate(myDay);
		paxCommunication1.setAcknowledge_timestamp(myDay);
		paxCommunication1.setComment("pax communication three - SCHROEDER , JOSHUACALEB - 10/01/2009 2PM");
		
		PaxCommunication paxCommunication2 = new PaxCommunication();
		//populate paxCommunication2
		Incident myIncident2 = new Incident();
		myIncident2.setIncident_ID("ABEUS00018883");
		paxCommunication2.setIncident(myIncident2);
		paxCommunication2.setAgent(secretAgent);
		myAcknowledgeAgent.setAgent_ID(15436);
		paxCommunication2.setAcknowledge_agent(myAcknowledgeAgent);
		paxCommunication2.setStatus(PaxCommunicationStatus.NEW);
		paxCommunication2.setCreatedate(myDay);
		Date myPastDate = java.sql.Date.valueOf( "2009-01-31" );
		paxCommunication2.setAcknowledge_timestamp(myPastDate);
		paxCommunication2.setComment("pax communication two - RAINFORD , STEVEN - 10/01/2009 2PM");		
		
		PaxCommunication paxCommunication3 = new PaxCommunication();
		//populate paxCommunication3
		Incident myIncident3 = new Incident();
		myIncident3.setIncident_ID("ABEUS00018909");
		paxCommunication3.setIncident(myIncident3);
		paxCommunication3.setAgent(secretAgent);
		myAcknowledgeAgent.setAgent_ID(15437);
		paxCommunication3.setAcknowledge_agent(myAcknowledgeAgent);
		paxCommunication3.setStatus(PaxCommunicationStatus.ACKNOWLEDGED);
		paxCommunication3.setCreatedate(myDay);
		Date myFutureDate = java.sql.Date.valueOf( "2010-01-31" );
		paxCommunication3.setAcknowledge_timestamp(myFutureDate);
		paxCommunication3.setComment("pax communication one - SACCO , GERALDJ SACCO , MARIEL - 10/01/2009 2PM");
		
		PaxCommunication paxCommunication4 = new PaxCommunication();
		//populate paxCommunication4
		Incident myIncident4 = new Incident();
		myIncident4.setIncident_ID("ABEUS00008583");
		paxCommunication4.setIncident(myIncident4);
		paxCommunication4.setAgent(secretAgent);
		myAcknowledgeAgent.setAgent_ID(15437);
		paxCommunication4.setAcknowledge_agent(myAcknowledgeAgent);
		paxCommunication4.setStatus(PaxCommunicationStatus.ACKNOWLEDGED);
		paxCommunication4.setCreatedate(myDay);
		Date myFutureDate4 = java.sql.Date.valueOf( "2011-01-31" );
		paxCommunication4.setAcknowledge_timestamp(myFutureDate4);
		paxCommunication4.setComment("pax communication ten - KISH , ERIN - 10/01/2009 2PM");

		Integer paxCommunicationId1 = obj.save(paxCommunication1);
		Integer paxCommunicationId2 = obj.save(paxCommunication2);
		Integer paxCommunicationId3 = obj.save(paxCommunication3);
		Integer paxCommunicationId4 = obj.save(paxCommunication4);
		

		obj.listPaxCommunication();
		
		paxCommunication2.setStatus(PaxCommunicationStatus.RESPONDED);
		obj.update(paxCommunication2);

		obj.listPaxCommunication();
	}
	
	public Integer save(PaxCommunication paxCommunication)
	{
		Session session = HibernateWrapper.getSession().openSession();
		//Session session = HibernateAnnotationUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Integer paxCommunicationId = null;
		try {
			transaction = session.beginTransaction();
			paxCommunicationId = (Integer) session.save(paxCommunication);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return paxCommunicationId;
	}
	
	public void listPaxCommunication()
	{
		Session session = HibernateWrapper.getSession().openSession();
		//Session session = HibernateAnnotationUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			List paxCommunications = session.createQuery("from PaxCommunication").list();
			for (Iterator iterator = paxCommunications.iterator(); iterator.hasNext();)
			{
				PaxCommunication paxCommunication = (PaxCommunication) iterator.next();
				System.out.println(paxCommunication.toString());
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void update(PaxCommunication paxCommunication)
	{
		Session session = HibernateWrapper.getSession().openSession();
		//Session session = HibernateAnnotationUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.update(paxCommunication);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
