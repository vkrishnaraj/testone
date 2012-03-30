package com.bagnet.nettracer.tracing.db.dr;

import java.util.ArrayList;
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

public class DisputeUtils {
	private static Logger logger = Logger.getLogger(DisputeUtils.class);
	
	public static long getDisputeCount(Agent user){
		String sql = "select count (d.dispute_res_id) from com.bagnet.nettracer.tracing.db.dr.Dispute d where 1=1 ";
		sql += " and d.status = :status";
		sql += " and (d.incident.faultstation.station_ID = :station or d.incident.faultstation.lz_ID = :lz)";
		
		Query q = null;
		
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createQuery(sql.toString());
			q.setInteger("status", TracingConstants.DISPUTE_RESOLUTION_STATUS_OPEN);
			
			q.setParameter("station", user.getStation().getStation_ID());
			q.setParameter("lz", user.getStation().getLz_ID());		
			
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
	
	public static Dispute getDispute(Agent agent) {
		String sql = "from com.bagnet.nettracer.tracing.db.dr.Dispute d";
		sql += " where 1=1 and d.status = :status";
		sql += " and (d.incident.faultstation.station_ID = :station or d.incident.faultstation.lz_ID = :lz) and Lock_ID=null";
		sql += " order by d.created_timestamp";

		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
				
			q = sess.createQuery(sql.toString());
			q.setInteger("status", TracingConstants.DISPUTE_RESOLUTION_STATUS_OPEN);
			q.setParameter("station", agent.getStation().getStation_ID());
			q.setParameter("lz", agent.getStation().getLz_ID());
			
			List<Dispute> result = q.list();
			if (result.size() > 0) {
				Dispute nDispute=(Dispute) result.get(0);
				if(lockDispute(nDispute)!=null){
				Status s = new Status();
				s.setStatus_ID(TracingConstants.TASK_MANAGER_WORKING);
				nDispute.setStatus(s);
				nDispute.setDisputeAgent(agent);
				sess.close();
				sess = HibernateWrapper.getSession().openSession();
				
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
	
	public static Dispute lockDispute(Dispute dis){
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
					lock = lockBmo.createLock(LockType.DISPUTE, key, 300000L);
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
		String sql = "from com.bagnet.nettracer.tracing.db.dr.Dispute d";
		sql += " where 1=1 and d.status = :status";
		sql += " and (d.incident.faultstation.station_ID = :station or d.incident.faultstation.lz_ID = :lz)";
		sql += " order by d.created_timestamp";
		Query q = null;
		
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createQuery(sql.toString());
			q.setInteger("status", TracingConstants.DISPUTE_RESOLUTION_STATUS_OPEN);
			
			q.setParameter("station", user.getStation().getStation_ID());
			q.setParameter("lz", user.getStation().getLz_ID());	
			
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
