package com.bagnet.nettracer.tracing.utils.taskmanager;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.taskmanager.GeneralTask;
import com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;

public class MorningDutiesUtil extends TaskManagerUtil {
	private static Logger logger = Logger.getLogger(HibernateUtils.class);

	public static GeneralTask createTask(Agent agent, Object o) {
		Incident inc = (Incident) o;
		MorningDutiesTask mdt = new MorningDutiesTask();
		mdt.setIncident(inc);
		mdt.setAssigned_agent(agent);
		mdt.setOpened_timestamp(DateUtils.convertToGMTDate(new Date()));
		Status s = new Status();
		s.setStatus_ID(TracingConstants.TASK_MANAGER_OPEN);
		mdt.setStatus(s);

		return saveTask(mdt);
	}
	
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
	
	public static GeneralTask getTaskById(long id){
		String sql = "from com.bagnet.nettracer.tracing.db.taskmanager.GeneralTask gt ";
		sql += "where gt.task_id = :id";
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(sql.toString());
		q.setLong("id", id);
		List l = q.list();
		if(l.isEmpty() == false){
			return (GeneralTask) l.get(0);
		} else {
			//throw exception
			return null;
		}
	}
	
	public static void deferTask(MorningDutiesTask task, int minutes){
		MorningDutiesTask t = (MorningDutiesTask) getTaskById(task.getTask_id());
		GregorianCalendar now = new GregorianCalendar();
		now.add(Calendar.MINUTE, minutes);
		t.setDeferment_timestamp(DateUtils.convertToGMTDate(now.getTime()));
		saveTask(t);
	}
	
	public static void abortTask(MorningDutiesTask task){
		MorningDutiesTask t = (MorningDutiesTask) getTaskById(task.getTask_id());
		//TODO unlock task
		Status s = new Status();
		s.setStatus_ID(TracingConstants.TASK_MANAGER_OPEN);
		t.setStatus(s);
		saveTask(t);
	}
	
	public static void pauseTask(MorningDutiesTask task){
		MorningDutiesTask t = (MorningDutiesTask) getTaskById(task.getTask_id());
		Status s = new Status();
		s.setStatus_ID(TracingConstants.TASK_MANAGER_OPEN);
		t.setStatus(s);
		saveTask(t);
	}
	
	public static boolean hasPermission(Agent agent){
		//TODO build me
		return false;
	}
	
	public List getPaginatedList() {
		//TODO build me
		return null;
	}
	

	public static long getCount(Agent agent, int day) {
		//TODO needs rework
		String sql = "select count(task_id) from com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask mdt "
				+ "where 1=1 "
				+ "and agent_ID = :agentID "
				+ "and mdt.status = :status ";

		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(sql.toString());
		q.setInteger("agentID", agent.getAgent_ID());
		q.setLong("status", TracingConstants.TASK_MANAGER_OPEN);
		List result = q.list();
		return (Long) result.get(0);
	}

	public static GeneralTask getTask(Agent agent, int day) {
		GeneralTask task = getTaskManagerTask(agent, day);
		if (task != null) {
			return task;
		}
		Incident inc = getIncident(agent, day);
		if (inc != null) {
			return createTask(agent, inc);
		}
		// didn't find a task
		return null;
	}

	public static void lockTask(GeneralTask task) {

	}

	private static GeneralTask getTaskManagerTask(Agent agent, int day) {
		//TODO build me
		String sql = "";
		return null;
	}

	private static Incident getIncident(Agent agent, int day) {
		String sql = "from com.bagnet.nettracer.tracing.db.Incident i "
				+ "where 1=1 "
				+ "and i.status.status_ID = :status "
				+ "and i.id not in (select m.incident.id from com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask m) "
				+ "and " + getDateRange(day) + "";

		System.out.println(sql);
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(sql.toString());
		q.setLong("status", TracingConstants.MBR_STATUS_OPEN);
		List result = q.list();
		if (result.size() > 0) {
			return (Incident) result.get(0);
		}
		return null;
	}

	public static String getDateRange(int day) {
		GregorianCalendar now = new GregorianCalendar(new SimpleTimeZone(
				-(3600000 * 12), "-12GMT"));
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.HOUR_OF_DAY, 0);
		Date startOfDay = DateUtils.convertToGMTDate(now.getTime());

		GregorianCalendar start = new GregorianCalendar(
				TimeZone.getTimeZone("GMT"));
		start.setTime(startOfDay);
		GregorianCalendar end = new GregorianCalendar(
				TimeZone.getTimeZone("GMT"));
		end.setTime(startOfDay);
		String sql = "";
		switch (day) {
		case 2:
			start.add(Calendar.DATE, -2);
			end.add(Calendar.DATE, -1);
			sql = "((i.createdate = '"
				+ DateUtils.formatDate(start.getTime(), TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()), null, null)
					+ "' and i.createtime >= '"
					+ DateUtils.formatDate(start.getTime(),TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
							+ "') or (i.createdate = '"
					+ DateUtils.formatDate(end.getTime(), TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()), null, null)
					+ "' and i.createtime < '" 
					+ DateUtils.formatDate(end.getTime(),TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
							+ "'))";
			break;
		case 3:
			start.add(Calendar.DATE, -3);
			end.add(Calendar.DATE, -2);
			sql = "((i.createdate = '" 
				+ DateUtils.formatDate(start.getTime(), TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()), null, null)
				+ "' and i.createtime >= '" 
				+ DateUtils.formatDate(start.getTime(),TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
							+ "') or (i.createdate = '"
							+ DateUtils.formatDate(end.getTime(), TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()), null, null)
					+ "' and i.createtime < '" 
					+ DateUtils.formatDate(start.getTime(),TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
							+ "'))";
			break;
		case 4:
			end.add(Calendar.DATE, -3);
			sql = "((i.createdate = '" 
				+ DateUtils.formatDate(end.getTime(), TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()), null, null)
					+ "' and i.createtime < '" 
					+ DateUtils.formatDate(end.getTime(),TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
							+ "') or i.createdate < '"
							+ DateUtils.formatDate(end.getTime(), TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()), null, null)
							+ "')";
			break;
		default:
			sql = "1=1";
		}
		return sql;
	}



}
