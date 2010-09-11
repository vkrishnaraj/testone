package com.bagnet.nettracer.tracing.db.dr;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.bagbuzz.BagBuzz;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;

public class DisputeUtils {
	private static Logger logger = Logger.getLogger(DisputeUtils.class);
	
	public static List<Dispute> getDisputeList(Object user){
		String sql = "from com.bagnet.nettracer.tracing.db.dr.Dispute d where 1=1 ";
		sql += " and d.status = :status";
		Query q = null;
		
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createQuery(sql.toString());
			q.setInteger("status", TracingConstants.DISPUTE_RESOLUTION_STATUS_OPEN);
			
			LinkedHashSet<Dispute> qlhs = new LinkedHashSet<Dispute>(q.list());
			List<Dispute> al = new ArrayList<Dispute>(qlhs);
			return al;
		} catch (Exception e) {
			logger.error("unable to list disputes " + e);
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
	
	public static List<Dispute> getPaginatedDisputeList(Agent user, int rowsperpage, int currpage, boolean iscount,
			boolean dirtyRead){
		String sql = "from com.bagnet.nettracer.tracing.db.dr.Dispute d where 1=1 ";
		sql += " and d.status = :status";
		Query q = null;
		
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createQuery(sql.toString());
			q.setInteger("status", TracingConstants.DISPUTE_RESOLUTION_STATUS_OPEN);
			
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
}
