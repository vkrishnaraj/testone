package com.bagnet.nettracer.tracing.db;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import com.bagnet.nettracer.hibernate.HibernateWrapper;

//import com.bagnet.nettracer.tracing.db.bagbuzz.GeneralTask;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;

import com.bagnet.nettracer.tracing.db.bagbuzz.Utils;

public class BagBuzzTest {
	@Test
	public void test(){
//		String sql = "from com.bagnet.nettracer.tracing.db.bagbuzz.GeneralTask task";
//		
//		Session sess = HibernateWrapper.getSession().openSession();
//		Query q = sess.createQuery(sql);
//		List <GeneralTask>list = q.list();
//		for (GeneralTask t:list){
//			System.out.println(t);
//		}
//		System.out.println("fin");
//		sess.close();
	}
	
	
//	@Test
//	public void insert(){
//		Session sess = HibernateWrapper.getSession().openSession();
//		GeneralTask task = new BagBuzzTask();
//		task.setRef_id("1234");
//		task.setInbox_id(2);
//		task.setStatus_id(3);
//		HibernateUtils.save(task, sess);
//		sess.close();
//	}
	
//	@Test
//	public void getBagBuzz(){
//		String sql = "from com.bagnet.nettracer.tracing.db.bagbuzz.GeneralTask task";
//		
//		Session sess = HibernateWrapper.getSession().openSession();
//		Query q = sess.createQuery(sql);
//		List <GeneralTask>list = q.list();
//		for (GeneralTask t:list){
//			System.out.println(t);
//		}
//		System.out.println("fin");
//		sess.close();
//	}
	
	@Test
	public void getCount(){
		Agent agent = new Agent();
		agent.setAgent_ID(2852);
		try{
		int x = Utils.getBagBuzzCount(agent);
		System.out.println(x);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
}
