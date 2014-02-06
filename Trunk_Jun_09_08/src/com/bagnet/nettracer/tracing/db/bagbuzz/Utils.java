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
	
	public static int getBagBuzzCount(Agent user){
		String sql = "from com.bagnet.nettracer.tracing.db.taskmanager.BagBuzzTask bt " 
			+ "where 1=1 "
			+ "and agent_ID = :agentID "
			+ "and bt.status = :btstatus "
			+ "and bt.bagBuzz.status = :bbstatus";
		Query q = null;
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			q = sess.createQuery(sql.toString());
			q.setInteger("agentID", user.getAgent_ID());
			q.setLong("btstatus", TracingConstants.TASK_MANAGER_OPEN);
			q.setLong("bbstatus", TracingConstants.BAGBUZZ_PUBLISHED);
			List result = q.list();
			return result.size();
		} catch (Exception e){
			logger.error("Error Saving: ", e);
			e.printStackTrace();
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
		return 0;
	}
	
	public static List <BagBuzz> getBagBuzzList(Object user){
		String sql = "from com.bagnet.nettracer.tracing.db.bagbuzz.BagBuzz bb";
		sql += " where status != :deleted order by created_timestamp desc";
		Query q = null;
		
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			q = sess.createQuery(sql.toString());
			q.setLong("deleted", TracingConstants.BAGBUZZ_DELETED);
			
			LinkedHashSet qlhs = new LinkedHashSet(q.list());
			ArrayList al = new ArrayList(qlhs);
			return al;
		} catch (Exception e){
			logger.error("Error Saving: ", e);
			e.printStackTrace();
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
		return null;
	}
	
	public static List <BagBuzzTask> getBagBuzzTaskList(Agent user){
		String bbtsql = "from com.bagnet.nettracer.tracing.db.taskmanager.BagBuzzTask bbt"
			+ " where agent_ID = :agentID"
			+ " and bbt.status != :bbtstatus"
			+ " order by opened_timestamp desc";
		
		String bbsql = "from com.bagnet.nettracer.tracing.db.bagbuzz.BagBuzz bb";
		bbsql += " where status = :publish order by created_timestamp desc";
		
		Query bbtq = null;
		Session sess = null;
		
		try {
			sess = HibernateWrapper.getSession().openSession();
			bbtq = sess.createQuery(bbtsql.toString());
			bbtq.setInteger("agentID", user.getAgent_ID());
			bbtq.setLong("bbtstatus", TracingConstants.TASK_MANAGER_CLOSED);
			
			LinkedHashSet bbtqlhs = new LinkedHashSet(bbtq.list());
			ArrayList <BagBuzzTask> bbtal = new ArrayList(bbtqlhs);
			
			Query bbq = null;
			bbq = sess.createQuery(bbsql.toString());
			bbq.setLong("publish", TracingConstants.BAGBUZZ_PUBLISHED);
			LinkedHashSet <BagBuzz> bbqlhs = new LinkedHashSet<BagBuzz>(bbq.list());
			ArrayList <BagBuzz>bbal = new ArrayList<BagBuzz>(bbqlhs);
			
			ArrayList <BagBuzzTask> newTasks = new ArrayList();
			Status s = new Status();
			s.setStatus_ID(TracingConstants.TASK_MANAGER_CLOSED);
			for (BagBuzz bb: bbal){
				boolean hasTask = false;
				for (BagBuzzTask bbt: bbtal){
					if(bbt.getBagBuzz().getBagbuzz_id() == bb.getBagbuzz_id()){
						hasTask = true;
					}
				}
				if(!hasTask){
					BagBuzzTask toAdd = new BagBuzzTask();
					toAdd.setAssigned_agent(user);
					toAdd.setStatus(s);
					toAdd.setOpened_timestamp(bb.getCreated_timestamp());
					toAdd.setBagBuzz(bb);
					toAdd.setTask_id(0);
					newTasks.add(toAdd);
				}
			}
			bbtal.addAll(newTasks);
			return bbtal;
		} catch (Exception e){
			logger.error("Error Saving: ", e);
			e.printStackTrace();
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
		return null;
		
	}
	
	public static void updateBagBuzzTaskReadStatus(long bbt_id){
		String sql = "update com.bagnet.nettracer.tracing.db.taskmanager.BagBuzzTask bbt set status = :status"
			+ " where task_id = :taskID"
			+ " and status = :currentStatus";
		
		Query q = null;
		Session sess = null;
		Transaction t = null;
		
		try{
			sess = HibernateWrapper.getSession().openSession();
			q = sess.createQuery(sql.toString());
			q.setLong("taskID", bbt_id);
			q.setLong("status", TracingConstants.TASK_MANAGER_PROCESSED);
			q.setLong("currentStatus", TracingConstants.TASK_MANAGER_OPEN);
			
			t = sess.beginTransaction();
			q.executeUpdate();
			t.commit();
		} catch (Exception e){
			logger.error("Error Saving: ", e);
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
					logger.error("Error Saving: ", ex);
				}
			}
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static BagBuzz getBagBuzz(long id){
		String sql = "from com.bagnet.nettracer.tracing.db.bagbuzz.BagBuzz bb ";
		sql += "where bb.bagbuzz_id = :id";
		
		Query q = null;
		Session sess = null;
		
		try {
			sess = HibernateWrapper.getSession().openSession();
			q = sess.createQuery(sql.toString());
			q.setLong("id", id);
			List l = q.list();
			if(l.isEmpty() == false){
				return (BagBuzz) l.get(0);
			}
		} catch (Exception e){
			logger.error("Error Saving: ", e);
			e.printStackTrace();
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
		return null;
		
	}
	
	public static BagBuzz saveBagBuzz(BagBuzz bb){
		if(!(bb.getBagbuzz_id() > 0)){
			bb.setCreated_timestamp(new Date());
		} else {
			BagBuzz old = Utils.getBagBuzz(bb.getBagbuzz_id());
			if (old.getStatus().getStatus_ID() == TracingConstants.BAGBUZZ_PUBLISHED) {
				unpublishBagBuzz(old);
			}
			bb.setCreated_timestamp(old.getCreated_timestamp());
		}
		Status s = new Status();
		s.setStatus_ID(TracingConstants.BAGBUZZ_NEW);
		bb.setStatus(s);
		
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
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
	
	public static void deleteBagBuzz(BagBuzz bb){
		Status s = new Status();
		s.setStatus_ID(TracingConstants.BAGBUZZ_DELETED);
		bb.setStatus(s);
		
		Session sess = null; 
		try{
			sess = HibernateWrapper.getSession().openSession();
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
	}
	
	public static BagBuzz copyBagBuzz(long id){
		BagBuzz bb = Utils.getBagBuzz(id);
		bb.setBagbuzz_id(0);
		Utils.saveBagBuzz(bb);
		return bb;
	}
	
	public static void publishBagBuzz(BagBuzz bb, List roles){
		String sql = "select agent.agent_ID from com.bagnet.nettracer.tracing.db.Agent agent where agent.active = 1";
		Session sess = null;
		Transaction t = null;
		
		try{
			sess = HibernateWrapper.getSession().openSession();
			t = sess.getTransaction();
			
			Status s = new Status();
			s.setStatus_ID(TracingConstants.TASK_MANAGER_OPEN);
			
			Query q = sess.createQuery(sql);
			List <Integer>list = q.list();
			Date currentDate = new Date();
			
			//I just need the id, is there a way not to cascade
			bb = Utils.getBagBuzz(bb.getBagbuzz_id());
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
	
	public static void unpublishBagBuzz(BagBuzz bb){
		String updateTasks = "update com.bagnet.nettracer.tracing.db.taskmanager.GeneralTask t set status = :status, closed_timestamp = :closed_timestamp where bagbuzz_id = :bbid";
		String updateBagBuzz = "update com.bagnet.nettracer.tracing.db.bagbuzz.BagBuzz bb set status = :status where bagbuzz_id = :bbid";
		
		Session sess = null;
		Transaction t = null;

		try{
			sess = HibernateWrapper.getSession().openSession();
			Query tq = sess.createQuery(updateTasks);
			tq.setLong("status", TracingConstants.TASK_MANAGER_CLOSED);
			tq.setTimestamp("closed_timestamp", new Date());
			tq.setLong("bbid", bb.getBagbuzz_id());
			
			Query bbq = sess.createQuery(updateBagBuzz);
			bbq.setLong("status", TracingConstants.BAGBUZZ_UNPUBLISHED);
			bbq.setLong("bbid", bb.getBagbuzz_id());
		
			t = sess.beginTransaction();
			tq.executeUpdate();
			bbq.executeUpdate();
			t.commit();
		} catch (Exception e){
			logger.error("Error Saving: ", e);
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
					logger.error("Error Saving: ", ex);
				}
			}
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
