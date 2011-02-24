package com.bagnet.nettracer.tracing.utils.taskmanager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

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
import com.bagnet.nettracer.tracing.db.dr.Dispute;
import com.bagnet.nettracer.tracing.db.taskmanager.FourDayTask;
import com.bagnet.nettracer.tracing.db.taskmanager.GeneralTask;
import com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask;
import com.bagnet.nettracer.tracing.db.taskmanager.TaskActivity;
import com.bagnet.nettracer.tracing.db.taskmanager.ThreeDayTask;
import com.bagnet.nettracer.tracing.db.taskmanager.TwoDayTask;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.WorldTracerLockException;
import com.bagnet.nettracer.wt.connector.CaptchaException;

public class MorningDutiesUtil extends TaskManagerUtil {
	private static Logger logger = Logger.getLogger(HibernateUtils.class);
	
	public static final int TWODAY = 2;
	public static final int THREEDAY = 3;
	public static final int FOURDAY = 4;
	
	public static enum ResolutionType {
		PAUSED("paused"), DEFERED("defered"), WORKED("worked"), ABORTED("aborted");
	
		private String description_id;
	
		private ResolutionType(String desc) {
			this.description_id = desc;
		}
	
		public String description() {
			return description_id;
		}
	}

	public static GeneralTask lockTask(GeneralTask task){
		String key = null;
		if (task instanceof MorningDutiesTask){
			if(((MorningDutiesTask)task).getIncident() != null && ((MorningDutiesTask)task).getIncident().getIncident_ID() != null
					&& ((MorningDutiesTask)task).getIncident().getIncident_ID().trim().length() > 0){
				key = ((MorningDutiesTask)task).getKey() + ((MorningDutiesTask)task).getIncident().getIncident_ID();
			}
		}
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
					lock = lockBmo.createLock(LockType.TM_INCIDENT, key, 3600000L);
				} catch (Exception ex) {
					logger.info("GeneralTask for " + key
							+ " alreaedy locked, waiting..");
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						throw new Exception(
								"unable to acquire lock " + key, e);
					}
				}
			} while (lock == null && i < 4);
			if(lock == null) {
				throw new Exception("unable to lock incident " + key);
			}
			task.setLock(lock);
			return task;
		} catch (Exception e) {
			return null;
		} 
	}
	
	public static GeneralTask createTask(Agent agent, Object o, int day) {
		Incident inc = (Incident) o;
		MorningDutiesTask mdt = null;
		switch(day){
		case TWODAY:
			mdt = new TwoDayTask();
			break;
		case THREEDAY:
			mdt = new ThreeDayTask();
			break;
		case FOURDAY:
			mdt = new FourDayTask();
			break;
		}
		mdt.setIncident(inc);
		mdt.setAssigned_agent(agent);
		mdt.setOpened_timestamp(DateUtils.convertToGMTDate(new Date()));
		Status s = new Status();
		s.setStatus_ID(TracingConstants.TASK_MANAGER_WORKING);
		mdt.setStatus(s);
		if(lockTask(mdt) == null){
			return null;
		}

		return TaskManagerBMO.saveTask(mdt);
	}
	
	public static void closeTask(Agent agent, GeneralTask task){
		Status s = new Status();
		s.setStatus_ID(TracingConstants.TASK_MANAGER_CLOSED);
		task.setStatus(s);
		task.setAssigned_agent(agent);
		task.setClosed_timestamp(DateUtils.convertToGMTDate(new Date()));
		TaskManagerBMO.saveTask(task);
		unlockTaskIncident(task.getLock());
	}
	
	
	public static void deferTask(Agent agent, MorningDutiesTask task, int minutes){
		MorningDutiesTask t = (MorningDutiesTask) TaskManagerBMO.getTaskById(task.getTask_id());
		GregorianCalendar now = new GregorianCalendar();
		now.add(Calendar.MINUTE, minutes);
		t.setDeferment_timestamp(DateUtils.convertToGMTDate(now.getTime()));
		Status s = new Status();
		s.setStatus_ID(TracingConstants.TASK_MANAGER_OPEN);
		t.setStatus(s);
		t.setAssigned_agent(agent);
		TaskManagerBMO.saveTask(t);
		unlockTaskIncident(task.getLock());
	}
	
	public static void abortTask(Agent agent, MorningDutiesTask task){
		MorningDutiesTask t = (MorningDutiesTask) TaskManagerBMO.getTaskById(task.getTask_id());
		//TODO unlock task
		Status s = new Status();
		s.setStatus_ID(TracingConstants.TASK_MANAGER_OPEN);
		t.setStatus(s);
		t.setAssigned_agent(agent);
		TaskManagerBMO.saveTask(t);
		unlockTaskIncident(task.getLock());
	}
	
	public static void pauseTask(Agent agent, MorningDutiesTask task){
		MorningDutiesTask t = (MorningDutiesTask) TaskManagerBMO.getTaskById(task.getTask_id());
		Status s = new Status();
		s.setStatus_ID(TracingConstants.TASK_MANAGER_PAUSED);
		t.setStatus(s);
		t.setAssigned_agent(agent);
		TaskManagerBMO.saveTask(t);
		unlockTaskIncident(task.getLock());
	}
	
	private static String getQuery(Agent agent, int day, boolean order){
		String sql =  "from com.bagnet.nettracer.tracing.db.Incident i "
		+ "where 1=1 "
		+ "and i.itemtype.itemType_ID = 1 "
		+ "and i.status.status_ID = :status " 
		+ "and (i.stationassigned.station_ID = :station or i.stationassigned.lz_ID = :lz) "
		+ "and i.incident_ID not in (select m.incident.incident_ID from "
		+ getDayTask(day) + " m where m.status.status_ID != :taskStatus or deferment_timestamp > :curTime) "
		+ "and " + getDateRange(day);
		if(order){
			sql += " order by createdate asc, createtime asc";
		}
		return sql;
	}
	
	public static List<Incident> getTaskList(Agent agent, int day) {
		String sql = getQuery(agent, day, true);
		System.out.println(sql);
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(sql.toString());
		q.setParameter("status", TracingConstants.MBR_STATUS_OPEN);
		q.setParameter("station", agent.getStation().getStation_ID());
		q.setParameter("lz", agent.getStation().getLz_ID());
		q.setParameter("taskStatus", TracingConstants.TASK_MANAGER_OPEN);
		q.setParameter("curTime", DateUtils.convertToGMTDate(new Date()));
		List result = q.list();
		LinkedHashSet<Incident> qlhs = new LinkedHashSet<Incident>(q.list());
		List<Incident> al = new ArrayList<Incident>(qlhs);
		return al;
	}
	
	public static List<Incident> getPaginatedDisputeList(Agent user, int day, int rowsperpage, int currpage){
		String sql = getQuery(user, day, true);
		
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			Query q = sess.createQuery(sql.toString());
			q.setParameter("status", TracingConstants.MBR_STATUS_OPEN);
			q.setParameter("station", user.getStation().getStation_ID());
			q.setParameter("lz", user.getStation().getLz_ID());	
			q.setParameter("taskStatus", TracingConstants.TASK_MANAGER_OPEN);
			q.setParameter("curTime", DateUtils.convertToGMTDate(new Date()));
			
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}
			LinkedHashSet<Incident> qlhs = new LinkedHashSet<Incident>(q.list());
			List<Incident> al = new ArrayList<Incident>(qlhs);
			return al;
		} catch (Exception e) {
			logger.error("unable to list tasks in paginated fashion " + e);
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
	
	public static TaskActivity addActivity(Agent agent, GeneralTask task, ResolutionType resolution, long duration){
		TaskActivity ta = new TaskActivity();
		ta.setTask(task);
		ta.setAgent(agent);
		ta.setResolution(resolution.description());
		ta.setDuration(duration);
		ta.setCompletetime(DateUtils.convertToGMTDate(new Date()));
		return TaskManagerBMO.saveTaskActivity(ta);
	}
	
	public static MorningDutiesTask getTaskByIncidentId(Agent agent, String incident_id, int day){
		List<MorningDutiesTask> taskList = TaskManagerBMO.getTaskByIncidentId(incident_id);
		for(MorningDutiesTask task:taskList){
			switch (day){
			case TWODAY:
				if(task instanceof com.bagnet.nettracer.tracing.db.taskmanager.TwoDayTask ){
					//this assumes that there is only one task per incident per day
					return task;
				}
				break;
			case THREEDAY:
				if(task instanceof com.bagnet.nettracer.tracing.db.taskmanager.ThreeDayTask ){
					//this assumes that there is only one task per incident per day
					return task;
				}
				break;
			case FOURDAY:
				if(task instanceof com.bagnet.nettracer.tracing.db.taskmanager.FourDayTask ){
					//this assumes that there is only one task per incident per day
					return task;
				}
				break;
			}
		}
		return null;
	}
	
	public static GeneralTask getTask(Agent agent, int day) {
		//		GeneralTask task = getTaskManagerTask(agent, day);
		//		if (task != null) {
		//			return task;
		//		}

		Incident inc = getIncident(agent, day);
		if (inc != null) {
			//do we already have a defered or aborted task for this incident
			GeneralTask gtask = getTaskByIncidentId(agent, inc.getIncident_ID(), day);
			if(gtask == null){
				//we don't have a task, create one
				return createTask(agent, inc, day);
			}
			if(gtask.getStatus().getStatus_ID() == TracingConstants.TASK_MANAGER_OPEN && gtask.getDeferment_timestamp().getTime() < DateUtils.convertToGMTDate(new Date()).getTime()){
				if(MorningDutiesUtil.lockTask(gtask)!=null){
					Status s = new Status();
					s.setStatus_ID(TracingConstants.TASK_MANAGER_WORKING);
					TaskManagerBMO.saveTask(gtask);
					return gtask;
				}
			}
		}
		// didn't find a task
		return null;
	}

	public static Lock lockTaskIncident(String incident_id, int day) throws Exception{
		if(incident_id == null || incident_id.trim().length() == 0){
			throw new Exception("incident_id is null or empty");
		}
		String id = incident_id + "_" + day;
		LockBMO lockBmo = SpringUtils.getLockBmo();
		Lock lock = null;
		int i = 0;
		try {
			do {
				i++;
				try {
					lock = lockBmo.createLock(LockType.TM_INCIDENT, id, 3600000L);
				} catch (Exception ex) {
					logger.info("Morning duties for " + id
							+ " alreaedy locked, waiting..");
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						throw new Exception(
								"unable to acquire lock " + id, e);
					}
				}
			} while (lock == null && i < 4);
			if(lock == null) {
				throw new Exception("unable to lock incident " + id);
			}
			return lock;
		} catch (Exception e) {
			throw new WorldTracerException("unable to load summary", e);
		} finally {
			if (lock != null) {
				lockBmo.releaseLock(lock);
			}
		}
	}
	
	public static void unlockTaskIncident(Lock lock){
		SpringUtils.getLockBmo().releaseLock(lock);
	}

	private static GeneralTask getTaskManagerTask(Agent agent, int day) {
		//TODO build me
		String sql = "";
		return null;
	}

	private static Incident getIncident(Agent agent, int day) {
		String sql = getQuery(agent, day, true);

		

		System.out.println(sql);
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(sql.toString());
		q.setParameter("status", TracingConstants.MBR_STATUS_OPEN);
		q.setParameter("station", agent.getStation().getStation_ID());
		q.setParameter("lz", agent.getStation().getLz_ID());
		q.setParameter("taskStatus", TracingConstants.TASK_MANAGER_OPEN);
		q.setParameter("curTime", DateUtils.convertToGMTDate(new Date()));
		List result = q.list();
		if (result.size() > 0) {
			return (Incident) result.get(0);
		}
		return null;
	}
	
	public static int getTwoDayCount(Agent agent){
		return TaskManagerBMO.getOpenTwoDayCount(agent) + getIncidentCount(agent, TWODAY);
	}
	public static int getThreeDayCount(Agent agent){
		return TaskManagerBMO.getOpenThreeDayCount(agent) + getIncidentCount(agent, THREEDAY);
	}
	public static int getFourDayCount(Agent agent){
		return TaskManagerBMO.getOpenFourDayCount(agent) + getIncidentCount(agent, FOURDAY);
	}
	
	private static int getIncidentCount(Agent agent, int day){
		String sql = "select count (i.incident_ID) " + getQuery(agent, day, false); 
		System.out.println(sql);
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(sql.toString());
		q.setParameter("status", TracingConstants.MBR_STATUS_OPEN);
		q.setParameter("station", agent.getStation().getStation_ID());
		q.setParameter("lz", agent.getStation().getLz_ID());	
		q.setParameter("taskStatus", TracingConstants.TASK_MANAGER_OPEN);
		q.setParameter("curTime", DateUtils.convertToGMTDate(new Date()));
		List result = q.list();
		if (result.size() > 0) {
			return ((Long) result.get(0)).intValue();
		}
		return -1;
	}

	private static String getDayTask(int day){
		switch(day){
		case 2:
			return "com.bagnet.nettracer.tracing.db.taskmanager.TwoDayTask";
		case 3:
			return "com.bagnet.nettracer.tracing.db.taskmanager.ThreeDayTask";
		case 4:
			return "com.bagnet.nettracer.tracing.db.taskmanager.FourDayTask";
		default:
			return null;
		}
	}
	
	protected static Date getStartOfDay(){
		GregorianCalendar now = new GregorianCalendar(new SimpleTimeZone(
				-(3600000 * 12), "-12GMT"));
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.HOUR_OF_DAY, 0);
		return DateUtils.convertToGMTDate(now.getTime());
	}
	
	protected static String getDateRange(int day) {
		return getDateRange(day, 0);
	}
	
	protected static String getDateRange(int day, int report_offset) {
		Date startOfDay = getStartOfDay();

		GregorianCalendar start = new GregorianCalendar(
				TimeZone.getTimeZone("GMT"));
		start.setTime(startOfDay);
		GregorianCalendar end = new GregorianCalendar(
				TimeZone.getTimeZone("GMT"));
		end.setTime(startOfDay);
		
		//setting offset for reporting so we can get the two/three/four day reports from one, two, three...days ago
		start.add(Calendar.DATE, -report_offset);
		end.add(Calendar.DATE, -report_offset);
		
		String sql = "";
		switch (day) {
		case TWODAY:
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
		case THREEDAY:
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
		case FOURDAY:
			
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

	public static List<Incident> getPauseList(Agent user, int day) {
		String sql =  "from com.bagnet.nettracer.tracing.db.Incident i "
		+ "where 1=1 "
		+ "and i.incident_ID in (select m.incident.incident_ID from "
		+ getDayTask(day) + " m "
		+ "where 1=1 "
		+ "and m.assigned_agent = :agentID "
		+ "and m.status = :status) ";

		Query q = null;
		System.out.println(sql);
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(sql.toString());
		q.setInteger("agentID", user.getAgent_ID());
		q.setLong("status", TracingConstants.TASK_MANAGER_PAUSED);
		List<Incident> result = q.list();
		sess.close();
		return result;
	}

	public static int getMorningDutiesCount(int day, int lz_id, int offset){
		Date startOfDay = getStartOfDay();
		GregorianCalendar closedCutoff = new GregorianCalendar(
				TimeZone.getTimeZone("GMT"));
		closedCutoff.setTime(startOfDay);
		closedCutoff.add(Calendar.DATE, -day + 1);
		
		String sql = "select count(i.incident_ID) from com.bagnet.nettracer.tracing.db.Incident i "
			+ "where 1=1 "
			+ "and i.itemtype.itemType_ID = 1 "
			+ "and " + getDateRange(day, offset)
			+ " and (close_date is null or close_date > '" 
			+  DateUtils.formatDate(closedCutoff.getTime(),TracingConstants.getDBDateTimeFormat(HibernateWrapper.getConfig().getProperties()),null, null) + "') ";

		if (lz_id > 0){
			sql += " and i.stationassigned.lz_ID = :lz";
		}
		
		System.out.println(sql);
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(sql.toString());
		
		if (lz_id > 0){
			q.setParameter("lz", lz_id);
		}
		
		List result = q.list();
		if (result.size() > 0) {
			return ((Long) result.get(0)).intValue();
		}
		return -1;
	}
	
	public static int getMorningDutiesCompletedCount(int day, int station_id, int offset){
		String sql = "select count(i.incident_ID) from com.bagnet.nettracer.tracing.db.Incident i "
			+ "where 1=1 "
			+ "and i.itemtype.itemType_ID = 1 "
			+ "and " + getDateRange(day, offset)
			+ " and i.incident_ID in (select m.incident.incident_ID from "
					+ getDayTask(day) + " m where m.status.status_ID = :taskStatus)";
		
		System.out.println(sql);
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(sql.toString());
		q.setParameter("taskStatus", TracingConstants.TASK_MANAGER_CLOSED);
		List result = q.list();
		if (result.size() > 0) {
			return ((Long) result.get(0)).intValue();
		}
		return -1;
	}
	
}
