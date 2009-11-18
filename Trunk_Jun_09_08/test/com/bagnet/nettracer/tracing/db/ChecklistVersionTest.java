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

public class ChecklistVersionTest {
	public static void main(String[] args) {
		ChecklistVersionTest obj = new ChecklistVersionTest();
		ChecklistVersion checklistVersion_1 = new ChecklistVersion();
		
		//populate first ChecklistVersion instance
		checklistVersion_1.setActive(false);
		Long checklistVersion_1_id = obj.save(checklistVersion_1);
		
		obj.listChecklistVersion();
		//update this ChecklistVersion 
		checklistVersion_1.setActive(true);
		obj.update(checklistVersion_1);
		
		obj.listChecklistVersion();
	}
	
	public Long save(ChecklistVersion checklistVersion)
	{
		Session session = HibernateWrapper.getSession().openSession();
		//Session session = HibernateAnnotationUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Long checklistVersionId = null;
		try {
			transaction = session.beginTransaction();
			checklistVersionId = (Long) session.save(checklistVersion);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return checklistVersionId;
	}
	
	public void listChecklistVersion()
	{
		Session session = HibernateWrapper.getSession().openSession();
		//Session session = HibernateAnnotationUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			List checklistVersionList = session.createQuery("from ChecklistVersion").list();
			for (Iterator iterator = checklistVersionList.iterator(); iterator.hasNext();)
			{
				ChecklistVersion checklistVersion = (ChecklistVersion) iterator.next();
				System.out.println(checklistVersion.toString());
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void update(ChecklistVersion checklistVersion)
	{
		Session session = HibernateWrapper.getSession().openSession();
		//Session session = HibernateAnnotationUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.update(checklistVersion);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
