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

public class ChecklistTaskTest {
	public static void main(String[] args) {
		ChecklistTaskTest obj = new ChecklistTaskTest();
		ChecklistTask checklistTask_1 = new ChecklistTask();
		ChecklistTask checklistTask_2 = new ChecklistTask();
		ChecklistTask checklistTask_3 = new ChecklistTask();
		
		//populate checklistTask_1
		checklistTask_1.setOrder_id(1);
		checklistTask_1.setDescription("checklist.task.one");
		//checklistTask_1.setVersion_id(2);
		ChecklistVersion myChecklistVersion = new ChecklistVersion();
		myChecklistVersion.setVersion_id(2);
		myChecklistVersion.setActive(true);
		checklistTask_1.setChecklistVersion(myChecklistVersion);
		Long checklistTask_1_id = obj.save(checklistTask_1);
		
		//populate checklistTask_2
		checklistTask_2.setOrder_id(2);
		checklistTask_2.setDescription("checklist.task.two");
		checklistTask_2.setChecklistVersion(myChecklistVersion);
		Long checklistTask_2_id = obj.save(checklistTask_2);
		
		//populate checklistTask_3
		checklistTask_3.setOrder_id(3);
		checklistTask_3.setDescription("checklist.task.one.two.three");
		checklistTask_3.setChecklistVersion(myChecklistVersion);
		Long checklistTask_3_id = obj.save(checklistTask_3);
		
		obj.listChecklistTask();
		
		//update checklistTask_3 
		checklistTask_3.setDescription("checklist.task.three");
		obj.update(checklistTask_3);
		
		obj.listChecklistTask();
	}
	
	public Long save(ChecklistTask checklistTask)
	{
		Session session = HibernateWrapper.getSession().openSession();

		Transaction transaction = null;
		Long checklistTaskId = null;
		try {
			transaction = session.beginTransaction();
			checklistTaskId = (Long) session.save(checklistTask);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return checklistTaskId;
	}
	
	public void listChecklistTask()
	{
		Session session = HibernateWrapper.getSession().openSession();
		
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			List ChecklistTaskList = session.createQuery("from ChecklistTask").list();
			for (Iterator iterator = ChecklistTaskList.iterator(); iterator.hasNext();)
			{
				ChecklistTask checklistTask = (ChecklistTask) iterator.next();
				System.out.println(checklistTask.toString());
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void update(ChecklistTask checklistTask)
	{
		Session session = HibernateWrapper.getSession().openSession();
		
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.update(checklistTask);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
