package com.bagnet.nettracer.tracing.bmo;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.ChecklistTask;
import com.bagnet.nettracer.tracing.db.ChecklistTaskOption;
import com.bagnet.nettracer.tracing.db.ChecklistVersion;
import com.bagnet.nettracer.tracing.db.IncidentChecklist;

public class IncidentChecklistBMO {

	private static Logger logger = Logger.getLogger(IncidentChecklistBMO.class);
	
	@SuppressWarnings("unchecked")
	public static ChecklistVersion getChecklistVersionById(long id, Session sess) {
		boolean sessionNull = (sess == null);
		ChecklistVersion result = null;

		try {
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}
			
			Criteria cri = sess.createCriteria(ChecklistVersion.class)
					.add(Restrictions.eq("version_id", id));
			if (cri.list().size() > 0) {
				result = (ChecklistVersion) cri.list().get(0);
			} else {
				logger.debug("Unable to find any checklistVersion for this id.");
			}
		} catch (Exception e) {
			logger.error("Unable to retrieve checklistVersion due to error: " + e);
			e.printStackTrace();
		} finally {
			if (sess != null && sessionNull) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("Unable to close connection: " + e);
				}
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static List<IncidentChecklist> getIncidentChecklistByIncidentId(String incidentId, Session sess) {
		boolean sessionNull = (sess == null);
		List<IncidentChecklist> result = null;

		try {
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}
			
			Criteria cri = sess.createCriteria(IncidentChecklist.class)
					.add(Restrictions.eq("incident.incident_ID", incidentId));
			if (cri.list().size() > 0) {
				result = cri.list();
			} else {
				logger.debug("Unable to find any incidentchecklist for this incident_id.");
			}
		} catch (Exception e) {
			logger.error("Unable to retrieve incidentchecklist due to error: " + e);
			e.printStackTrace();
		} finally {
			if (sess != null && sessionNull) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("Unable to close connection: " + e);
				}
			}
		}
		return result;
	}
	
	public static List<ChecklistTask> getAllActiveChecklistTasks(Session sess) {
		List<ChecklistTask> result = null;
		ChecklistVersion myActiveVersion = getActiveChecklistVersion(sess);
		if(myActiveVersion != null){
			result = getAllChecklistTasksByChecklistVersion(myActiveVersion, sess);
		}
		
		return result;
	}
	
	public static ChecklistTaskOption getChecklistTaskOptionById(long id, Session sess) {
		boolean sessionNull = (sess == null);

		try {
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}
			
			Criteria cri = sess.createCriteria(ChecklistTaskOption.class)
					.add(Restrictions.eq("id", id));
			if (cri.list().size() > 0) {
				return (ChecklistTaskOption) cri.list().get(0);
			} else {
				logger.debug("Unable to find any checklistTaskOption for this id.");
				return null;
			}
		} catch (Exception e) {
			logger.error("Unable to retrieve checklistTaskOption due to error: " + e);
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null && sessionNull) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("Unable to close connection: " + e);
				}
			}
		}
	}
	
	public static List<ChecklistTaskOption> getAllChecklistTaskOptionsByTask(ChecklistTask checklistTask, Session sess) {
		boolean sessionNull = (sess == null);

		try {
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}
			
			Criteria cri = sess.createCriteria(ChecklistTaskOption.class)
					.add(Restrictions.eq("checklistTask", checklistTask))
					.addOrder(Order.asc("order_id"));
			if (cri.list().size() > 0) {
				return cri.list();
			} else {
				logger.debug("Unable to find any checklistTaskOption for this task.");
				return null;
			}
		} catch (Exception e) {
			logger.error("Unable to retrieve checklistTaskOption due to error: " + e);
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null && sessionNull) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("Unable to close connection: " + e);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Deprecated
	private static List<ChecklistVersion> getAllActiveChecklistVersions(Session sess) {
		boolean sessionNull = (sess == null);

		try {
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}
			
			Criteria cri = sess.createCriteria(ChecklistVersion.class)
					.add(Restrictions.eq("active", true));
			if (cri.list().size() > 0) {
				return cri.list();
			} else {
				logger.debug("Unable to find any active checklistVersion.");
				return null;
			}
		} catch (Exception e) {
			logger.error("Unable to retrieve active checklistVersion due to error: " + e);
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null && sessionNull) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("Unable to close connection: " + e);
				}
			}
		}
	}
	
	public static ChecklistVersion getActiveChecklistVersion(Session sess) {
		ChecklistVersion result = null;
		return getAllActiveChecklistVersions(sess).get(0);
	}
	
	@SuppressWarnings("unchecked")
	private static List<ChecklistTask> getAllChecklistTasksByChecklistVersion(ChecklistVersion checklistVersion, Session sess) {
		boolean sessionNull = (sess == null);

		try {
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}
			
			Criteria cri = sess.createCriteria(ChecklistTask.class)
					.add(Restrictions.eq("checklistVersion", checklistVersion));
			if (cri.list().size() > 0) {
				return cri.list();
			} else {
				logger.debug("Unable to find any checklisttask by this version at all");
				return null;
			}
		} catch (Exception e) {
			logger.error("Unable to retrieve checklisttask due to error: " + e);
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null && sessionNull) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("Unable to close connection: " + e);
				}
			}
		}
	}

	//(4) use what's in List<IncidentChecklist> to modify default ChecklistVersion
	public static ChecklistVersion updateIncidentChecklistVersion(ChecklistVersion defaultChecklistVersion, 
															List<IncidentChecklist> checklistCurrentStatus) {
		ChecklistVersion result = null;
		//loop through all checklistTasks for this version and use ChecklistTask.snapshotData 
		//transient field to hold the current status information for each task -  
		List<ChecklistTask> myDefaultListOfTasks = defaultChecklistVersion.getChecklistTasks();
		if(myDefaultListOfTasks != null) {
			Iterator<ChecklistTask> it=myDefaultListOfTasks.iterator();
			while(it.hasNext()){
				ChecklistTask myTask = (ChecklistTask)it.next();
				//loop through the checklistCurrentStatus for any match
				if(checklistCurrentStatus != null) {
					Iterator<IncidentChecklist> itCurrentStatus = checklistCurrentStatus.iterator();
					while(itCurrentStatus.hasNext()) {
						IncidentChecklist currentStatus = (IncidentChecklist) itCurrentStatus.next();
						
						if( myTask.getId() == currentStatus.getChecklistTask().getId()) {
							myTask.setSnapshotData(currentStatus);
						}
					}
				}
			}
		}
		//defaultChecklistVersion.setChecklistTasks(myDefaultListOfTasks);
		result = defaultChecklistVersion;
		
		return result;
	}
		
	
}
