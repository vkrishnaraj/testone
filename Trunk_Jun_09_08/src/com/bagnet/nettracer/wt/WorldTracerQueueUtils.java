package com.bagnet.nettracer.wt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue;
import com.bagnet.nettracer.tracing.db.wtq.WtqCloseAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqIncidentAction;
import com.bagnet.nettracer.tracing.db.wtq.WtqQoh;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue.WtqStatus;
import com.bagnet.nettracer.tracing.forms.BDOForm;
import com.bagnet.nettracer.tracing.forms.WorldTracerFWDForm;

public class WorldTracerQueueUtils {
	private String error;
	private static Logger logger = Logger.getLogger(WorldTracerQueueUtils.class);

	public static boolean saveFwdobj(WorldTracerFWDForm theform,WorldTracerQueue obj, Agent user) throws Exception {
		
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			//Figure out if new or old.
			boolean isNew = obj.getWt_queue_id() != 0 ? false: true;
			if (isNew) {
				sess.save(obj);
			} else {
				sess.saveOrUpdate(obj);
			}



			t.commit();
			return true;
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
				return false;
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
	}
	
	public static boolean saveBdoobj(BDOForm theform,WorldTracerQueue obj, Agent user) throws Exception {
		
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			//Figure out if new or old.
			boolean isNew = obj.getWt_queue_id() != 0 ? false: true;
			if (isNew) {
				sess.save(obj);
			} else {
				sess.saveOrUpdate(obj);
			}
			t.commit();
			return true;
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
				return false;
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
	}
	public static boolean createOrReplaceQueue(WorldTracerQueue entry) throws WorldTracerException  {
		
		Session sess = null;
		Transaction t = null;
		boolean abort = false;
		try {
			sess = HibernateWrapper.getSession().openSession();
			
			
			List<WorldTracerQueue> oldEntries = alreadyQueued(entry, sess);
			if(oldEntries != null && oldEntries.size() > 0) {
				for (WorldTracerQueue oldEntry: oldEntries) {
					if (oldEntry instanceof WtqCloseAhl) {
						abort = true;
					}
				}
			}
			
			// Do not save the new action because there is a pending close AHL action
			if (abort) {
				return false;
			}
			
			// is add ahl and we already have an AHL id or pending add
			if(entry instanceof WtqCreateAhl && alreadyAnyIncQueuedOrHasWtFile((WtqCreateAhl)entry, sess)){
				return false;
			}
			
			// is add ohd and we already have an OHD id or pending add
			if(entry instanceof WtqCreateOhd && alreadyAnyOhdQueuedOrHasWtFile((WtqCreateOhd)entry, sess)){
				return false;
			}
			
			t = sess.beginTransaction();
			if(oldEntries != null && oldEntries.size() > 0) {
				for (WorldTracerQueue oldEntry: oldEntries) {
					oldEntry.setReplacement(entry);
					oldEntry.setStatus(WtqStatus.REPLACED);
					sess.save(entry);
					sess.update(oldEntry);
				}
			}
			else {
				sess.save(entry);
			}
			t.commit();
			return true;
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			throw new WorldTracerException("Unknown exception ", e);
		} finally {
			if (sess != null) sess.close();
		}
	}
	

	public static boolean cancelPendingAction(long wtq_id) throws Exception {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			WorldTracerQueue wtq = (WorldTracerQueue) sess.get(WorldTracerQueue.class, wtq_id);
			if(wtq == null || !wtq.getStatus().equals(WtqStatus.PENDING)) {
				return false;
			}
			wtq.setStatus(WtqStatus.CANCELED);
			t.commit();
			return true;
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
	}
	
	public static WtqIncidentAction findPendingIncidentAction(String ntIncident) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery("from WtqIncidentAction wia where wia.status = :status and wia.incident.incident_ID = :ntIncident");
			q.setParameter("status", WtqStatus.PENDING);
			q.setParameter("ntIncident", ntIncident);
			q.setMaxResults(1);
			@SuppressWarnings("unchecked")
			List<WtqIncidentAction> result = q.list();
			if(result != null && result.size() > 0) {
				return result.get(0);
			}
			return null;

		} catch (Exception e) {
			logger.warn("unable to retrieve wt_queue incident actions for :" + ntIncident);
			return null;
		} finally {
			if (sess != null) sess.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static WorldTracerQueue findPendingOhdAction(String ntOHD) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery("from WtqOhdAction woa where woa.status = :status and woa.ohd.OHD_ID = :ntOHD");
			q.setParameter("status", WtqStatus.PENDING);
			q.setParameter("ntOHD", ntOHD);
			q.setMaxResults(1);
			List<WorldTracerQueue> result = q.list();
			if(result != null && result.size() > 0) {
				return result.get(0);
			}
			
			//no basic actions, check for forwards (hibernate doesn't support unions yet
			//don't want to write sql that I'm not 100% sure is database agnostic
			q = sess.createQuery("from WtqFwdOhd foh where foh.status = :status and foh.ohd.OHD_ID = :ntOHD");
			q.setParameter("status", WtqStatus.PENDING);
			q.setParameter("ntOHD", ntOHD);
			q.setMaxResults(1);
			result = q.list();
			if(result != null && result.size() > 0) {
				return result.get(0);
			}
			
			return null;

		} catch (Exception e) {
			logger.warn("unable to retrieve wt_queue ohd actions for :" + ntOHD);
			return null;
		} finally {
			if (sess != null) sess.close();
		}
	}
	/**
	 * If there is already a pending queue entry for this task_id / type then
	 * just return that id.
	 * 
	 * @param wtq
	 *            wt_queue object that is checked for existence
	 * @return queue object if it already is in db, or null if not there
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<WorldTracerQueue> alreadyQueued(WorldTracerQueue wtq, Session sess) {
		Query q = sess.createQuery(wtq.getExistsQuery());
		Object[] params = wtq.getExistsParameters();
		for(int i = 0; i < params.length; i++) {
			if (params[i] instanceof List) {
				q.setParameterList("list", (ArrayList) params[i]);
			} else {
				q.setParameter(i, params[i]);
			}
		}
		return q.list();
	}
	
	
	public void setError(String error) {
		this.error = error;
	}
	public String getError(){
		return error;
	}

	public static boolean createOnlyQueue(WorldTracerQueue entry) throws Exception {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			
			List<WorldTracerQueue> oldEntries = alreadyQueued(entry, sess);
			if(oldEntries != null && oldEntries.size() > 0) {
				return false;
			}
			else {
				t = sess.beginTransaction();
				sess.save(entry);
				t.commit();
			}
			return true;
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
		
	}

	public static boolean createOnlyOhdQueue(WtqCreateOhd entry, Date lastupdated) throws Exception {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			if(alreadyAnyOhdQueuedOrHasWtFile(entry, sess)) {
				return false;
			}
			else {
				t = sess.beginTransaction();
				sess.save(entry);
				t.commit();
			}
			return true;
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
		
	}
	
	public static boolean createOnlyTagQueue(WtqQoh entry) throws Exception {
//		logger.info(entry.getWt_queue_id() + ":");
		for (OHD ohd: entry.getOhdTags()) {
			logger.info("  " + ohd.getOHD_ID());
		}
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			
			List<WorldTracerQueue> oldEntries = alreadyQueued(entry, sess);
			if(oldEntries!=null && oldEntries.size() > 0){
				HashMap<String,String> oldEntryMap=new HashMap<String,String>();
				Object [] arrQoh=oldEntries.toArray();
				for(int i=0;i<oldEntries.size();i++){
					Object[] qohArray=(Object[]) arrQoh[i];
					OHD qoh=null;
					if(qohArray!=null){
						qoh=(OHD)qohArray[1];
					}
					if(qoh!=null){
						oldEntryMap.put(qoh.getOHD_ID(),"1");
					}
				}
				List<OHD> initialList=new ArrayList<OHD>();
				for(OHD ohd:entry.getOhdTags()){
					initialList.add(ohd);
				}
				for (OHD ohd: initialList) {
					if(oldEntryMap.get(ohd.getOHD_ID())!=null){
						entry.getOhdTags().remove(ohd);
					}
				}
			}
			if(entry.getOhdTags().size()>0){
				t = sess.beginTransaction();
				sess.save(entry);
				t.commit();
			} else {
				return false;
			}
			
			
			return true;
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			if (t != null) {
				t.rollback();
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
		
	}
	

	public static boolean createOnlyIncQueue(WtqCreateAhl entry, Date lastupdated) throws Exception {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			if(alreadyAnyIncQueuedOrHasWtFile(entry, sess)) {
				return false;
			}
			else {
				t = sess.beginTransaction();
				sess.save(entry);
				t.commit();
			}
			return true;
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
		
	}

	private static boolean alreadyAnyIncQueuedOrHasWtFile(WtqCreateAhl entry, Session sess) {
		String queryString = "select wtq from WtqCreateAhl wtq where wtq.incident.incident_ID = :incident_ID "
				+ " and ((wtq.status = :qStatus) or (wtq.createdate > :lastUpdated) or wtq.incident.wtFile is not null) ";
		Query q = sess.createQuery(queryString);

		q.setString("incident_ID", entry.getIncident().getIncident_ID());
		q.setParameter("qStatus", WtqStatus.PENDING);
		q.setTimestamp("lastUpdated", entry.getIncident().getLastupdated());
		q.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<WorldTracerQueue> result = q.list();
		if(result.size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	private static boolean alreadyAnyOhdQueuedOrHasWtFile(WtqCreateOhd entry, Session sess) {
		String queryString = "select wtq.wt_queue_id from WtqCreateOhd wtq where wtq.ohd.OHD_ID = :OHD_ID " + 
							" and ((wtq.status = :qStatus) or (wtq.createdate > :lastUpdated) or wtq.ohd.wtFile is not null) "; 
		Query q = sess.createQuery(queryString);
		
		q.setString("OHD_ID", entry.getOhd().getOHD_ID());
		q.setParameter("qStatus", WtqStatus.PENDING);
		q.setTimestamp("lastUpdated", entry.getOhd().getLastupdated());
		q.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<WorldTracerQueue> result = q.list();
		if(result.size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}


}
