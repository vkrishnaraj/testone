package com.bagnet.nettracer.tracing.db.dr;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.LockBMO;
import com.bagnet.nettracer.tracing.bmo.TaskManagerBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Lock;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.Lock.LockType;
import com.bagnet.nettracer.tracing.db.bagbuzz.BagBuzz;
import com.bagnet.nettracer.tracing.db.taskmanager.GeneralTask;
import com.bagnet.nettracer.tracing.db.taskmanager.ItemTraceResultsTask;
import com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.taskmanager.MorningDutiesUtil;
import com.bagnet.nettracer.tracing.db.taskmanager.DisputeResolutionTask;

public class DisputeUtils {
	private static Logger logger = Logger.getLogger(DisputeUtils.class);
	
	public static long getDisputeCount(Agent user){
		return getDisputeCount(user, -1);
	}	
	
	public static long getDisputeCount(Agent user, int DisputeType){
		String sql = "select count (d.dispute_res_id) from com.bagnet.nettracer.tracing.db.dr.Dispute d where 1=1 ";
		sql += " and d.status = :status";
		sql += " and (d.incident.faultstation.station_ID = :station or d.incident.faultstation.lz_ID = :lz)";
		
		sql += " and (d.dispute_res_id NOT IN (select t.dispute.dispute_res_id from com.bagnet.nettracer.tracing.db.taskmanager.DisputeResolutionTask t where t.status.status_ID=:taskstatus)) ";
		
		if(DisputeType==1)
		{
			sql += " and d.beforeDisputeLossCode != d.suggestedLossCode and d.beforeDisputeFaultStation = d.suggestedFaultStation";
		}
		else if(DisputeType==2)
		{
			sql += " and d.beforeDisputeFaultStation != d.suggestedFaultStation and d.beforeDisputeLossCode = d.suggestedLossCode";
		}
		else if(DisputeType==3)
		{
			sql += " and d.beforeDisputeLossCode != d.suggestedLossCode and d.beforeDisputeFaultStation != d.suggestedFaultStation";
		}
		
		Query q = null;
		
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createQuery(sql.toString());
			q.setInteger("status", TracingConstants.DISPUTE_RESOLUTION_STATUS_OPEN);
			
			q.setParameter("station", user.getStation().getStation_ID());
			q.setParameter("lz", user.getStation().getLz_ID());	
			q.setParameter("taskstatus", TracingConstants.TASK_MANAGER_WORKING);
			
//			logger.error("getDisputeList() : lzId = " + user.getStation().getLz_ID() + " and stationId = " + user.getStation().getStation_ID());
			
			List l = q.list();
			Long ret = (Long)l.get(0);
			return ret.longValue();
		} catch (Exception e) {
			logger.error("unable to list disputes " + e);
			e.printStackTrace();
			return -1;
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
	
	public static Dispute getDispute(Agent agent, int DisputeType) {
		List<Dispute> result = getPaginatedDisputeList(agent, 1, 0,false, true, DisputeType, true);
		boolean lockedFound=false;
		if(result==null || result.size()==0)
		{
			result=getPaginatedDisputeList(agent, 1, 0,false, true, DisputeType, false);
		}
		else
		{
			lockedFound=true;
		}
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			if (result.size() > 0) {
				Dispute nDispute=(Dispute) result.get(0);
				if(lockedFound || lockDispute(nDispute, agent)!=null){

					Status s = new Status();
					s.setStatus_ID(TracingConstants.TASK_MANAGER_WORKING);
					DisputeResolutionTask DRT=new DisputeResolutionTask();
					List<DisputeResolutionTask> taskList=TaskManagerBMO.getTaskByDisputeId(nDispute.getDispute_res_id(), s);
					if(taskList==null || taskList.isEmpty()){
						DRT.setAssigned_agent(agent);
						DRT.setDispute(nDispute);
						DRT.setLock(nDispute.getLock());
						DRT.setOpened_timestamp(new Date());
						DRT.setStatus(s);
						TaskManagerBMO.saveTask(DRT);
					}
				
					nDispute.setResolutionAgent(agent);
					HibernateUtils.save(nDispute, sess);
						
					return nDispute;
			}
		   }
		} catch (Exception e) {
			logger.error("Error getting dispute " + e);
			e.printStackTrace();
		} 
		finally {
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
	
	public static Dispute lockDispute(Dispute dis, Agent user){
		String key = String.valueOf(dis.getDispute_res_id());
		if(key == null){
			return null;
		}
		
		LockBMO lockBmo = SpringUtils.getLockBmo();
		Lock lock = null;
		int i = 0;
		try {
			do {
				i++;
				try {
					lock = lockBmo.createLock(LockType.DISPUTE, key, 300000L, user);
				} catch (Exception ex) {
					logger.info("Dispute for " + key
							+ " alreaedy locked, waiting..");
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						throw new Exception(
								"unable to acquire lock " + key, e);
					}
				}
			} while (lock == null && i < 2);
			if(lock == null) {
				throw new Exception("unable to lock task " + key);
			}
			dis.setLock(lock);
			return dis;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public static void unlockDispute(Lock lock, Dispute dispute){
		try {
			if(lock != null){
				SpringUtils.getLockBmo().releaseLock(lock);
			} else {
				if(dispute != null && dispute.getIncident() != null){
					lock = SpringUtils.getLockBmo().loadLock(LockType.DISPUTE, String.valueOf(dispute.getDispute_res_id()));
					if(lock != null){
						SpringUtils.getLockBmo().releaseLock(lock);
					}
				}
			}
		} catch (Exception e) {
			// Ignore any exceptions release the lock.
		}
	}
	
	public static List<Dispute> getPaginatedDisputeList(Agent user, int rowsperpage, int currpage, boolean iscount,
			boolean dirtyRead){
		return getPaginatedDisputeList(user, rowsperpage, currpage, iscount, dirtyRead, -1,false);
	}
	
	public static List<Dispute> getPaginatedDisputeList(Agent user, int rowsperpage, int currpage, boolean iscount,
			boolean dirtyRead, int DisputeType,boolean getNext){
		String sql = "from com.bagnet.nettracer.tracing.db.dr.Dispute d";
		sql += " where 1=1 and d.status = :status";
		sql += " and (d.incident.faultstation.station_ID = :station or d.incident.faultstation.lz_ID = :lz)";
		
		if(getNext)
		{
			sql += " and (d.dispute_res_id IN (select t.dispute.dispute_res_id from com.bagnet.nettracer.tracing.db.taskmanager.DisputeResolutionTask t where t.assigned_agent=:agent and t.status.status_ID=:taskstatus)) ";
		}
		else
		{
			sql += " and (d.dispute_res_id NOT IN (select t.dispute.dispute_res_id from com.bagnet.nettracer.tracing.db.taskmanager.DisputeResolutionTask t where t.status.status_ID=:taskstatus)) ";
		}
		
		if(DisputeType==1)
		{
			sql += " and d.beforeDisputeLossCode != d.suggestedLossCode and d.beforeDisputeFaultStation = d.suggestedFaultStation";
		}
		else if(DisputeType==2)
		{
			sql += " and d.beforeDisputeFaultStation != d.suggestedFaultStation and d.beforeDisputeLossCode = d.suggestedLossCode";
		}
		else if(DisputeType==3)
		{
			sql += " and d.beforeDisputeLossCode != d.suggestedLossCode and d.beforeDisputeFaultStation != d.suggestedFaultStation";
		}
		
		sql += " order by d.created_timestamp";
		Query q = null;
		
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createQuery(sql.toString());
			q.setInteger("status", TracingConstants.DISPUTE_RESOLUTION_STATUS_OPEN);
			
			q.setParameter("station", user.getStation().getStation_ID());
			q.setParameter("lz", user.getStation().getLz_ID());	
			q.setParameter("taskstatus", TracingConstants.TASK_MANAGER_WORKING);
			
			if(getNext)
			{
				q.setParameter("agent", user);
			}
			
//			logger.error("getPaginatedDisputeList() : lzId = " + user.getStation().getLz_ID() + " and stationId = " + user.getStation().getStation_ID());
			
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}
			
			LinkedHashSet<Dispute> qlhs = new LinkedHashSet<Dispute>(q.list());
			List<Dispute> al = new ArrayList<Dispute>(qlhs);
			return al;
		} catch (Exception e) {
			logger.error("unable to list disputes in paginated fashion " + e);
			e.printStackTrace();
			return null;
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
	
	public static Dispute getDisputeByIncidentId(String incidentId) {
		Dispute result = null;
		String sql = "from com.bagnet.nettracer.tracing.db.dr.Dispute d ";
		sql += "where d.incident.incident_ID = :id"; 

		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createQuery(sql.toString());
			q.setString("id", incidentId);
			List<Dispute> l = q.list();
			if(! l.isEmpty()){
				result = (Dispute) l.get(0);
			} 
		} catch (Exception e) {
			logger.error("unable to retrieve dispute " + e);
			e.printStackTrace();
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public static Dispute getDisputeById(long id){
		String sql = "from com.bagnet.nettracer.tracing.db.dr.Dispute d ";
		sql += "where d.dispute_res_id = :id";
		
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createQuery(sql.toString());
			q.setLong("id", id);
			List<Dispute> l = q.list();
			if(! l.isEmpty()){
				return (Dispute) l.get(0);
			} else {
				//throw exception
				return null;
			}
		} catch (Exception e) {
			logger.error("unable to retrieve dispute " + e);
			e.printStackTrace();
			return null;
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
	
	public static boolean saveDispute(Dispute dispute){

		Session sess = HibernateWrapper.getSession().openSession();
		try{
			//check to see if there is an entry with the same incident id
			Incident myIncident = dispute.getIncident();
			String myIncidentId = "" + myIncident.getIncident_ID();
			Dispute myExistingDispute = getDisputeByIncidentId(myIncidentId);
			if (myExistingDispute != null) {
				dispute.setDispute_res_id(myExistingDispute.getDispute_res_id());
			}
			
			
			HibernateUtils.save(dispute, sess);
			Status s=new Status();
			s.setStatus_ID(TracingConstants.TASK_MANAGER_WORKING);
			List<DisputeResolutionTask> tasklist=TaskManagerBMO.getTaskByDisputeId(dispute.getDispute_res_id(), s);
			s.setStatus_ID(TracingConstants.TASK_MANAGER_CLOSED);
			if(tasklist!=null){
				for(DisputeResolutionTask task:tasklist){ //Should only ever be one working task
					if(task!=null){
						task.setStatus(s);
						TaskManagerBMO.saveTask(task);
					}
				}
			}
			
			unlockDispute(dispute.getLock(), dispute);
			return true;
		}
		catch (Exception e){
			logger.error("Error Saving Dispute: ", e);
			e.printStackTrace();
			return false;
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
	
	public static boolean saveDisputeAndUpdateIncident(Dispute dispute, String incidentId, Agent agent){

		Session sess = HibernateWrapper.getSession().openSession();
		try{
			//check to see if there is an entry with the same incident id
			Incident myIncident = dispute.getIncident();
			String myIncidentId = "" + myIncident.getIncident_ID();
			Dispute myExistingDispute = getDisputeByIncidentId(myIncidentId);
			if (myExistingDispute != null) {
				dispute.setDispute_res_id(myExistingDispute.getDispute_res_id());
			}
			
			HibernateUtils.save(dispute, sess);
			
			Status s=new Status();
			s.setStatus_ID(TracingConstants.TASK_MANAGER_WORKING);
			List<DisputeResolutionTask> tasklist=TaskManagerBMO.getTaskByDisputeId(dispute.getDispute_res_id(), s);
			s.setStatus_ID(TracingConstants.TASK_MANAGER_CLOSED);
			if(tasklist!=null){
				for(DisputeResolutionTask task:tasklist){ //Should only ever be one working task
					if(task!=null){
						task.setStatus(s);
						TaskManagerBMO.saveTask(task);
					}
				}
			}
			
			unlockDispute(dispute.getLock(), dispute);
			//update incident on the new fault station and fault code information
			Incident incidentToUpdate = IncidentBMO.getIncidentByID(incidentId, sess);
			if (incidentToUpdate != null) {
				incidentToUpdate.setFaultstation(dispute.getDeterminedFaultStation());
				incidentToUpdate.setLoss_code(dispute.getDeterminedLossCode());
				IncidentBMO ibmo = new IncidentBMO();
				ibmo.saveAndAuditIncident(incidentToUpdate, agent, sess);
			}
			return true;
		}
		catch (Exception e){
			logger.error("Error Saving Dispute: ", e);
			e.printStackTrace();
			return false;
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
}
