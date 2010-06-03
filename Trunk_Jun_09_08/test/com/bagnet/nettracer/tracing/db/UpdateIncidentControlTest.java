package com.bagnet.nettracer.tracing.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.utils.HibernateAnnotationUtil;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

public class UpdateIncidentControlTest {
	public static void main(String[] args) {
		UpdateIncidentControlTest obj = new UpdateIncidentControlTest();

		//TODO: select * from incident table to get all the incident_ID
		
		//TODO: loop through the incidents in audit_incident table and
		//	select distinct(stationassigned_ID), modify_time from audit_incident 
		//	where Incident_ID = 'YVRWS00003882'
		//	order by modify_time desc;
		//locate the cursor where stationassigned_ID first changed
		//and capture the modify_time; or if no change on stationassigned_ID
		//get the last row to use the modify_time
		List<Incident> myIncidentList = obj.getIncidentList();
		for (Iterator iterator = myIncidentList.iterator(); iterator.hasNext();)
		{
			Incident myIncident = (Incident) iterator.next();
			System.out.println(myIncident.getIncident_ID());
		}

	}
	
	private Long save(IncidentControl incidentControl)
	{
		Session session = HibernateWrapper.getSession().openSession();

		Transaction transaction = null;
		Long incidentControlId = null;
		try {
			transaction = session.beginTransaction();
			incidentControlId = (Long) session.save(incidentControl);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return incidentControlId;
	}

	private List getIncidentList()
	{
		List result = new ArrayList();
		//Session session = HibernateWrapper.getSession().openSession();
		Session session = HibernateAnnotationUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			result = session.createQuery("from incident join incidentcontrol").list();

			transaction.commit();
			
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return result;
	}
}
