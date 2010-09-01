package com.bagnet.nettracer.tracing.db.bagbuzz;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.Transaction;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.taskmanager.BagBuzzTask;
import com.bagnet.nettracer.tracing.db.bagbuzz.BagBuzz;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Status;


public class Utils {
	
	private static Logger logger = Logger.getLogger(HibernateUtils.class);
	
	public static List <BagBuzz> getBagBuzzList(Object user){
		String sql = "from com.bagnet.nettracer.tracing.db.bagbuzz.BagBuzz bb";
		Query q = null;
		
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(sql.toString());
		
		LinkedHashSet qlhs = new LinkedHashSet(q.list());
		ArrayList al = new ArrayList(qlhs);
		return al;
	}
	
	public static BagBuzz getBagBuzz(long id){
		String sql = "from com.bagnet.nettracer.tracing.db.bagbuzz.BagBuzz bb ";
		sql += "where bb.bagbuzz_id = :id";
		
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(sql.toString());
		q.setLong("id", id);
		List l = q.list();
		if(l.isEmpty() == false){
			return (BagBuzz) l.get(0);
		} else {
			//throw exception
			return null;
		}
		
	}
	
	public static BagBuzz saveBagBuzz(BagBuzz bb){
		if(bb.getBagbuzz_id() < 0){
			bb.setCreated_timestamp(new Date());
		}
		Status s = new Status();
		s.setStatus_ID(TracingConstants.BAGBUZZ_NEW);
		bb.setStatus(s);
		
		Session sess = HibernateWrapper.getSession().openSession();
		try{
			HibernateUtils.save(bb, sess);
		}
		catch (Exception e){
			logger.error("Error Saving: ", e);
			e.printStackTrace();
			bb = null;
		}
		finally{
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return bb;
	}
	
	public static void publishBagBuzz(BagBuzz bb, List roles){
		String sql = "select agent.agent_ID from com.bagnet.nettracer.tracing.db.Agent agent where agent.active = 1";
		Session sess = HibernateWrapper.getSession().openSession();
		Transaction t = sess.getTransaction();
		
		Status s = new Status();
		s.setStatus_ID(TracingConstants.TASK_MANAGER_OPEN);
		
		Query q = sess.createQuery(sql);
		List <Integer>list = q.list();
		Date currentDate = new Date();
		
		try{
			t = sess.beginTransaction();
			for (Integer i:list){
				BagBuzzTask bbt = new BagBuzzTask();
				Agent agent = new Agent();
				agent.setAgent_ID(i);
				bbt.setAssigned_agent(agent);
				bbt.setBagBuzz(bb);
				bbt.setStatus(s);
				bbt.setOpened_timestamp(currentDate);
				sess.saveOrUpdate(bbt);
			}
			Status bbs = new Status();
			bbs.setStatus_ID(TracingConstants.BAGBUZZ_PUBLISHED);
			bb.setStatus(bbs);
			sess.saveOrUpdate(bb);
			
			t.commit();
		}
		catch(Exception e){
			logger.error("Error Saving: ", e);
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
					logger.error("Error Saving: ", ex);
				}
			}
		}
		finally{
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void unpublishBagBuzz(){
		
	}
	
}
