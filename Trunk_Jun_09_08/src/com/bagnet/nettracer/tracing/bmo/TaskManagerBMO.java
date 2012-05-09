package com.bagnet.nettracer.tracing.bmo;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.taskmanager.GeneralTask;
import com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask;
import com.bagnet.nettracer.tracing.db.taskmanager.TaskActivity;
import com.bagnet.nettracer.tracing.db.taskmanager.DisputeResolutionTask;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;

public class TaskManagerBMO {
	private static Logger logger = Logger.getLogger(HibernateUtils.class);
	
	public static GeneralTask saveTask(GeneralTask task){
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			HibernateUtils.save(task, sess);
		} catch (Exception e) {
			logger.error("Error Saving: ", e);
			e.printStackTrace();
			task = null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return task;
	}
	
	public static List<MorningDutiesTask>getTaskByIncidentId(String incident_id){
		String sql = "from com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask t "
			+ "where t.incident.incident_ID = :id";
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(sql.toString());
		q.setParameter("id", incident_id);
		List <MorningDutiesTask>l = q.list();
		sess.close();
		return l;
	}
	
	public static List<DisputeResolutionTask>getTaskByDisputeId(long dispute_id, Status status){
		String sql = "from com.bagnet.nettracer.tracing.db.taskmanager.DisputeResolutionTask t "
			+ "where t.dispute.dispute_res_id = :id and t.status = :taskstatus";
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(sql.toString());
		q.setParameter("id", dispute_id);
		q.setParameter("taskstatus", status);
		List <DisputeResolutionTask>l = q.list();
		sess.close();
		return l;
	}
	
	public static GeneralTask getTaskById(long id){
		String sql = "from com.bagnet.nettracer.tracing.db.taskmanager.GeneralTask gt ";
		sql += "where gt.task_id = :id";
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(sql.toString());
		q.setLong("id", id);
		List l = q.list();
		GeneralTask task = null;
		if(l.isEmpty() == false){
			task = (GeneralTask)l.get(0);
		} 
		sess.close();
		return task;
	}
	
	public static TaskActivity saveTaskActivity(TaskActivity ta){
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			HibernateUtils.save(ta, sess);
		} catch (Exception e) {
			logger.error("Error Saving: ", e);
			e.printStackTrace();
			ta = null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return ta;
	}
	
	public static TaskActivity getTaskActivityById(long id){
		String sql = "from com.bagnet.nettracer.tracing.db.taskmanager.TaskActivity ta ";
		sql += "where ta.taskactivity_id = :id";
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(sql.toString());
		q.setLong("id", id);
		List l = q.list();
		TaskActivity activity = null;
		if(l.isEmpty() == false){
			activity = (TaskActivity) l.get(0);
		} 
		sess.close();
		return activity;
	}
	
	public static List<TaskActivity> getTaskActivityListByTask(GeneralTask task){
		return getTaskById(task.getTask_id()).getActivities();
	}
	
}
