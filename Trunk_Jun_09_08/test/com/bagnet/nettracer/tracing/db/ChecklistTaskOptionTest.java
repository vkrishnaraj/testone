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

public class ChecklistTaskOptionTest {
	public static void main(String[] args) {
		ChecklistTaskOptionTest obj = new ChecklistTaskOptionTest();
		ChecklistTaskOption checklistTaskOption_1 = new ChecklistTaskOption();
		ChecklistTaskOption checklistTaskOption_2 = new ChecklistTaskOption();
		ChecklistTaskOption checklistTaskOption_3 = new ChecklistTaskOption();
		ChecklistTaskOption checklistTaskOption_4 = new ChecklistTaskOption();
		ChecklistTaskOption checklistTaskOption_5 = new ChecklistTaskOption();
		ChecklistTaskOption checklistTaskOption_6 = new ChecklistTaskOption();
		
		//populate checklistTaskOption_1 - in Task with id=4
		checklistTaskOption_1.setOrder_id(1);
		checklistTaskOption_1.setDescription("checklist.task.option.one");
		
		ChecklistTask myChecklistTask = new ChecklistTask();
		myChecklistTask.setId(4);
		myChecklistTask.setDescription("checklist.task.one");
		myChecklistTask.setOrder_id(1);
		ChecklistVersion myChecklistVersion = new ChecklistVersion();
		myChecklistVersion.setVersion_id(2);
		myChecklistVersion.setActive(true);
		myChecklistTask.setChecklistVersion(myChecklistVersion);
		
		checklistTaskOption_1.setChecklistTask(myChecklistTask);
	
		Long checklistTaskOption_1_id = obj.save(checklistTaskOption_1);
		
		//populate checklistTaskOption_2 - in Task with id=4
		checklistTaskOption_2.setOrder_id(2);
		checklistTaskOption_2.setDescription("checklist.task.option.one.two");
		checklistTaskOption_2.setChecklistTask(myChecklistTask);
	
		Long checklistTaskOption_2_id = obj.save(checklistTaskOption_2);
		
		//populate checklistTaskOption_3 - in Task with id=4
		checklistTaskOption_3.setOrder_id(3);
		checklistTaskOption_3.setDescription("checklist.task.option.three");
		checklistTaskOption_3.setChecklistTask(myChecklistTask);
	
		Long checklistTaskOption_3_id = obj.save(checklistTaskOption_3);
		
		//populate checklistTaskOption_4 - in Task with id=4
		checklistTaskOption_4.setOrder_id(4);
		checklistTaskOption_4.setDescription("checklist.task.option.four");
		checklistTaskOption_4.setChecklistTask(myChecklistTask);
	
		Long checklistTaskOption_4_id = obj.save(checklistTaskOption_4);
		
		//populate checklistTaskOption_5 - in Task with id=4
		checklistTaskOption_5.setOrder_id(5);
		checklistTaskOption_5.setDescription("checklist.task.option.five");
		checklistTaskOption_5.setChecklistTask(myChecklistTask);
	
		Long checklistTaskOption_5_id = obj.save(checklistTaskOption_5);	
		
		//populate checklistTaskOption_6 - in Task with id=4
		checklistTaskOption_6.setOrder_id(6);
		checklistTaskOption_6.setDescription("checklist.task.option.six");
		checklistTaskOption_6.setChecklistTask(myChecklistTask);
	
		Long checklistTaskOption_6_id = obj.save(checklistTaskOption_6);
		
		
		
		
		obj.listChecklistTaskOption();
		
		//update checklistTaskOption_2 
		checklistTaskOption_2.setDescription("checklist.task.option.two");
		obj.update(checklistTaskOption_2);
		
		
		obj.listChecklistTaskOption();
	}
	
	public Long save(ChecklistTaskOption checklistTaskOption)
	{
		Session session = HibernateWrapper.getSession().openSession();

		Transaction transaction = null;
		Long checklistTaskOptionId = null;
		try {
			transaction = session.beginTransaction();
			checklistTaskOptionId = (Long) session.save(checklistTaskOption);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return checklistTaskOptionId;
	}
	
	public void listChecklistTaskOption()
	{
		Session session = HibernateWrapper.getSession().openSession();
		
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			List ChecklistTaskOptionList = session.createQuery("from ChecklistTaskOption").list();
			for (Iterator iterator = ChecklistTaskOptionList.iterator(); iterator.hasNext();)
			{
				ChecklistTaskOption checklistTaskOption = (ChecklistTaskOption) iterator.next();
				System.out.println(checklistTaskOption.toString());
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void update(ChecklistTaskOption checklistTaskOption)
	{
		Session session = HibernateWrapper.getSession().openSession();
		
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.update(checklistTaskOption);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
