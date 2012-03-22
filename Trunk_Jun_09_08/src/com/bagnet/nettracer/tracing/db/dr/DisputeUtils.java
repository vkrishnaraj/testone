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
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.bagbuzz.BagBuzz;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;

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
