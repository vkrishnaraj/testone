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
import com.bagnet.nettracer.tracing.bmo.IncidentChecklistBMO;
import com.bagnet.nettracer.tracing.bmo.LockBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.TaskManagerBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.ChecklistTask;
import com.bagnet.nettracer.tracing.db.ChecklistVersion;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.IncidentChecklist;
import com.bagnet.nettracer.tracing.db.Lock;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.Lock.LockType;
import com.bagnet.nettracer.tracing.db.taskmanager.FourDayTask;
import com.bagnet.nettracer.tracing.db.taskmanager.GeneralTask;
import com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask;
import com.bagnet.nettracer.tracing.db.taskmanager.TaskActivity;
import com.bagnet.nettracer.tracing.db.taskmanager.ThreeDayTask;
import com.bagnet.nettracer.tracing.db.taskmanager.TwoDayTask;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;

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
		unlockTaskIncident(task.getLock(), (MorningDutiesTask)task);
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
		unlockTaskIncident(task.getLock(), (MorningDutiesTask)task);
	}
	
	public static void abortTask(Agent agent, MorningDutiesTask task){
		MorningDutiesTask t = (MorningDutiesTask) TaskManagerBMO.getTaskById(task.getTask_id());
		//TODO unlock task
		Status s = new Status();
		s.setStatus_ID(TracingConstants.TASK_MANAGER_OPEN);
		t.setStatus(s);
		t.setAssigned_agent(agent);
		TaskManagerBMO.saveTask(t);
		unlockTaskIncident(task.getLock(),(MorningDutiesTask)task);
	}
	
	public static void pauseTask(Agent agent, MorningDutiesTask task){
		MorningDutiesTask t = (MorningDutiesTask) TaskManagerBMO.getTaskById(task.getTask_id());
		Status s = new Status();
		s.setStatus_ID(TracingConstants.TASK_MANAGER_PAUSED);
		t.setStatus(s);
		t.setAssigned_agent(agent);
		TaskManagerBMO.saveTask(t);
		unlockTaskIncident(task.getLock(),(MorningDutiesTask)task);
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
	
	public static int getCount(Agent agent, int day){
		return getOpenCount(agent, day) + getIncidentCount(agent, day);
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
//		System.out.println(sql);
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createQuery(sql.toString());
			q.setInteger("agentID", user.getAgent_ID());
			q.setLong("status", TracingConstants.TASK_MANAGER_PAUSED);
			List<Incident> result = q.list();
			return result;
		} finally {
			sess.close();
		}
	}
	
	public static List<Incident> getTaskList(Agent agent, int day) {
		String sql = getQuery(agent, day, true);
//		System.out.println(sql);
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
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
		} finally {
			sess.close();
		}
	}
	
	public static List<Incident> getPaginatedList(Agent user, int day, int rowsperpage, int currpage){
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
	
	public static GeneralTask hasAssignedTask(Agent agent){
		String sql = "from com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask mdt"
				+ " where 1=1"
				+ " and mdt.assigned_agent.agent_ID = :agent"
				+ " and (mdt.status.status_ID = :working or mdt.status.status_ID = :paused)";
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createQuery(sql.toString());
			
			q.setParameter("agent", agent.getAgent_ID());
			q.setParameter("working", TracingConstants.TASK_MANAGER_WORKING);
			q.setParameter("paused", TracingConstants.TASK_MANAGER_PAUSED);
	
			List result = q.list();
			LinkedHashSet<MorningDutiesTask> qlhs = new LinkedHashSet<MorningDutiesTask>(q.list());
			List<MorningDutiesTask> al = new ArrayList<MorningDutiesTask>(qlhs);
			if(al.size() > 0){
				return al.get(0);
			}
			return null;
		} finally {
			sess.close();
		}
	}
	
	public static GeneralTask getTask(Agent agent, int day) {
		Incident inc = getIncident(agent, day);
		
		if (inc != null) {
			//do we already have a defered or aborted task for this incident
			GeneralTask gtask = getTaskByIncidentId(agent, inc.getIncident_ID(), day);
			if(gtask == null){
				//we don't have a task, create one
				return createTask(agent, inc, day);
			}
			if(gtask.getStatus().getStatus_ID() == TracingConstants.TASK_MANAGER_OPEN && 
					(gtask.getDeferment_timestamp() == null ||
							gtask.getDeferment_timestamp().getTime() < DateUtils.convertToGMTDate(new Date()).getTime())){
				if(MorningDutiesUtil.lockTask(gtask)!=null){
					Status s = new Status();
					s.setStatus_ID(TracingConstants.TASK_MANAGER_WORKING);
					gtask.setStatus(s);
					gtask.setAssigned_agent(agent);
					TaskManagerBMO.saveTask(gtask);
					return gtask;
				}
			}
		}
		// didn't find a task
		return null;
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
	
	public static int getMorningDutiesReportCount(int day, int lz_id, int offset){
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
		
//		System.out.println(sql);
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createQuery(sql.toString());
			
			if (lz_id > 0){
				q.setParameter("lz", lz_id);
			}
			
			List result = q.list();
			if (result.size() > 0) {
				return ((Long) result.get(0)).intValue();
			}
			return -1;
		} finally {
			sess.close();
		}
	}
	
	public static int getMorningDutiesReportCompletedCount(int day, int station_id, int offset){
		String sql = "select count(i.incident_ID) from com.bagnet.nettracer.tracing.db.Incident i "
			+ "where 1=1 "
			+ "and i.itemtype.itemType_ID = 1 "
			+ "and " + getDateRange(day, offset)
			+ " and i.incident_ID in (select m.incident.incident_ID from "
					+ getDayTask(day) + " m where m.status.status_ID = :taskStatus)";
		
//		System.out.println(sql);
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createQuery(sql.toString());
			q.setParameter("taskStatus", TracingConstants.TASK_MANAGER_CLOSED);
			List result = q.list();
			if (result.size() > 0) {
				return ((Long) result.get(0)).intValue();
			}
			return -1;
		} finally {
			sess.close();
		}
	}
	
	public static String validateWork(MorningDutiesTask taskVal){
		
		if (taskVal instanceof TwoDayTask) {
			String taskIdentifier = "checklist.task.one";
			String ifValidationFailureError = "checklist.task.one.validation.failure";
			
			return validateCallPerformed(taskVal.getIncident().getIncident_ID(), taskIdentifier, ifValidationFailureError);			
		
		} else if (taskVal instanceof ThreeDayTask) {
			String taskIdentifier = "checklist.task.two";
			String ifValidationFailureError = "checklist.task.two.validation.failure";
			
			return validateCallPerformed(taskVal.getIncident().getIncident_ID(), taskIdentifier, ifValidationFailureError);			
			
		} else if (taskVal instanceof FourDayTask) {
			String taskIdentifier = "checklist.task.three";
			String ifValidationFailureError = "checklist.task.three.validation.failure";
			
			return validateCallPerformed(taskVal.getIncident().getIncident_ID(), taskIdentifier, ifValidationFailureError);			
			
		}
		
		return null;
	}

	private static String validateCallPerformed(String incidentId, String taskIdentifier, String ifValidationFailureError) {
		ChecklistVersion v = getChecklistForIncident(incidentId);
		List<ChecklistTask> tasks = v.getChecklistTasks();
		ChecklistTask activeTask = null;
		
		for (ChecklistTask t: tasks) {
			if (t.getDescription().equals(taskIdentifier)) {
				activeTask = t;
			}
		}
		
		if (activeTask != null && activeTask.getSnapshotData() != null && activeTask.getSnapshotData().getId() > 0) {
			return null;
		} else {
			return ifValidationFailureError;
		}
	}
	
	//main method - to package the Incident Checklist related data for view
	private static ChecklistVersion getChecklistForIncident(String incidentId) {
		ChecklistVersion result = null;
		
		Session sess = null;
		try {
			
			sess = HibernateWrapper.getSession().openSession();
			
			//(1) get incident checklist version from this incident
			Incident incident = IncidentBMO.getIncidentByID(incidentId, sess);
			long myIncidentChecklistVersion_id = incident.getChecklist_version();
			
			//(2) load default the object graph for this version
			ChecklistVersion defaultChecklistVersion = 
				IncidentChecklistBMO.getChecklistVersionById(myIncidentChecklistVersion_id, sess);
			
			//(3) get incident_checklist for this incident - this tells what has been done so far
			List<IncidentChecklist> checklistCurrentStatus = 
				IncidentChecklistBMO.getIncidentChecklistByIncidentId(incident.getIncident_ID(), sess);
			
			//(4) use what's in (3) to modify (2) - result is to be pushed to view
			result = IncidentChecklistBMO.updateIncidentChecklistVersion(defaultChecklistVersion, checklistCurrentStatus);

		} catch (Exception e) {
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
		
		return result;
	}
	
	private static String getQuery(Agent agent, int day, boolean order){
		String sql =  "from com.bagnet.nettracer.tracing.db.Incident i "
		+ "where 1=1 "
		+ "and i.itemtype.itemType_ID = 1 "
		+ "and i.status.status_ID = :status " 
		+ "and (i.stationassigned.station_ID = :station or i.stationassigned.lz_ID = :lz) "
		+ "and i.incident_ID not in (select m.incident.incident_ID from "
		+ getDayTask(day) + " m where m.status.status_ID != :taskStatus or generic_timestamp > :curTime) "
		+ "and " + getDateRange(day)
		+ " and i.incident_ID not in (select lockKey from com.bagnet.nettracer.tracing.db.Lock where lockType = '" + LockType.TM_INCIDENT + "') "
		+ "";
		if(order){
			sql += " order by i.stationcreated.priority asc, createdate asc, createtime asc";
		}
		return sql;
	}

	private static Incident getEuIncident(Agent agent, int day){
		String sql = getQuery(agent, day, false);
		sql += " and i.stationcreated.stationcode in ('CDG','TLV','LGW','MUC','OSL','VCE','ATH','FRA','MAD','DUB','AMS','ZRH','GLA','FCO','MAN','LHR','BRU','LIS','BCN') ";
		sql += " order by createdate asc, createtime asc";
//		System.out.println(sql);
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createQuery(sql.toString());
			q.setParameter("status", TracingConstants.MBR_STATUS_OPEN);
			q.setParameter("station", agent.getStation().getStation_ID());
			q.setParameter("lz", agent.getStation().getLz_ID());
			q.setParameter("taskStatus", TracingConstants.TASK_MANAGER_OPEN);
			q.setParameter("curTime", DateUtils.convertToGMTDate(new Date()));
			q.setMaxResults(1);
			List result = q.list();
			if (result.size() > 0) {
				return (Incident) result.get(0);
			}
			return null;
		} finally {
			sess.close();
		}
	}
	
	private static Incident getIncident(Agent agent, int day) {
		String sql = getQuery(agent, day, true);
//		System.out.println(sql);
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createQuery(sql.toString());
			q.setParameter("status", TracingConstants.MBR_STATUS_OPEN);
			q.setParameter("station", agent.getStation().getStation_ID());
			q.setParameter("lz", agent.getStation().getLz_ID());
			q.setParameter("taskStatus", TracingConstants.TASK_MANAGER_OPEN);
			q.setParameter("curTime", DateUtils.convertToGMTDate(new Date()));
			q.setMaxResults(1);
			List result = q.list();
			if (result.size() > 0) {
				return (Incident) result.get(0);
			}
			return null;
		} finally {
			sess.close();
		}
	}
	

	
	private static int getIncidentCount(Agent agent, int day){
		String sql = "select count (i.incident_ID) " + getQuery(agent, day, false); 
//		System.out.println(sql);
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
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
		} finally {
			sess.close();
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
	
	protected static Date time1970(Date d){
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(d);
		c.set(Calendar.DATE, 1);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.YEAR, 1970);
		return c.getTime();
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
		
		Date stime = MorningDutiesUtil.time1970(start.getTime());
		Date etime = MorningDutiesUtil.time1970(end.getTime());
		
		String sql = "";
		switch (day) {
		case TWODAY:
			start.add(Calendar.DATE, -2);
			end.add(Calendar.DATE, -1);
			sql = "((i.createdate = '"
				+ DateUtils.formatDate(start.getTime(), TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()), null, null)
					+ "' and i.createtime >= '"
					+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
							+ "') or (i.createdate = '"
					+ DateUtils.formatDate(end.getTime(), TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()), null, null)
					+ "' and i.createtime < '" 
					+ DateUtils.formatDate(etime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
							+ "'))";
			break;
		case THREEDAY:
			start.add(Calendar.DATE, -3);
			end.add(Calendar.DATE, -2);
			sql = "((i.createdate = '" 
				+ DateUtils.formatDate(start.getTime(), TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()), null, null)
				+ "' and i.createtime >= '" 
				+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
							+ "') or (i.createdate = '"
							+ DateUtils.formatDate(end.getTime(), TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()), null, null)
					+ "' and i.createtime < '" 
					+ DateUtils.formatDate(etime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
							+ "'))";
			break;
		case FOURDAY:
			
			end.add(Calendar.DATE, -3);
			sql = "((i.createdate = '" 
				+ DateUtils.formatDate(end.getTime(), TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()), null, null)
					+ "' and i.createtime < '" 
					+ DateUtils.formatDate(etime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
							+ "') or i.createdate < '"
							+ DateUtils.formatDate(end.getTime(), TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()), null, null)
							+ "')";
			break;
		default:
			sql = "1=1";
		}
		return sql;
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
	
	private static int getOpenCount(Agent agent, int day){
		String sql = "select count(task_id) from "
			+ getDayTask(day) + " mdt "
			+ "where 1=1 "
			+ "and mdt.assigned_agent = :agentID "
			+ "and (mdt.status = :statusPaused or mdt.status = :statusWorking)";
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createQuery(sql.toString());
			q.setInteger("agentID", agent.getAgent_ID());
			q.setLong("statusPaused", TracingConstants.TASK_MANAGER_PAUSED);
			q.setLong("statusWorking", TracingConstants.TASK_MANAGER_WORKING);
			List result = q.list();
			return ((Long) result.get(0)).intValue();
		} finally { 
			sess.close();
		}
	}
	
}
