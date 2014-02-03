package com.bagnet.nettracer.tracing.utils.taskmanager;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
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
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.TaskManagerBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.taskmanager.FiveDayTask;
import com.bagnet.nettracer.tracing.db.taskmanager.FourDayTask;
import com.bagnet.nettracer.tracing.db.taskmanager.GeneralTask;
import com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask;
import com.bagnet.nettracer.tracing.db.taskmanager.OneDayTask;
import com.bagnet.nettracer.tracing.db.taskmanager.ThreeDayTask;
import com.bagnet.nettracer.tracing.db.taskmanager.TwoDayTask;
import com.bagnet.nettracer.tracing.dto.CSSStationsDTO;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

public class CSSCallsUtil extends TaskManagerUtil {
	
	private static Logger logger = Logger.getLogger(CSSCallsUtil.class);

	public static final int ALLDAYS = -1;
	public static final int ONEDAY = 1;
	public static final int TWODAY = 2;
	public static final int THREEDAY = 3;
	public static final int FOURDAY = 4;
	public static final int FIVEDAY = 5;
	
	/**
	 * Creates a new task using Strings for the sDate and eDate
	 */
	public static GeneralTask createTask(Agent agent, Incident inc, int day, String sDate, String start, String eDate, String expire) {
		Date s = DateUtils.convertToDate(sDate, agent.getDateformat().getFormat(), null);
		Date e = null;
		if (eDate != null && eDate.length() > 0) {
			e = DateUtils.convertToDate(eDate, agent.getDateformat().getFormat(), null);
		}
		return createTask(agent, inc, day, s, start, e, expire);
	}
	
	/**
	 * Creates a new task of type day with start datetime of sDate+start and expire datetime of eDate+expire if provided.
	 */
	public static GeneralTask createTask(Agent agent, Incident inc, int day, Date sDate, String start, Date eDate, String expire) {
		MorningDutiesTask mdt = null;
		switch(day){
		case ONEDAY:
			mdt = new OneDayTask();
			break;
		case TWODAY:
			mdt = new TwoDayTask();
			break;
		case THREEDAY:
			mdt = new ThreeDayTask();
			break;
		case FOURDAY:
			mdt = new FourDayTask();
			break;
		case FIVEDAY:
			mdt = new FiveDayTask();
			break;
		}
		mdt.setIncident(inc);
		mdt.setAssigned_agent(agent);
		Status s = new Status();
		s.setStatus_ID(TracingConstants.TASK_MANAGER_OPEN);
		mdt.setStatus(s);
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(sDate);
		String[] sTime = start.split(":");
		startDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sTime[0]));
		startDate.set(Calendar.MINUTE, Integer.parseInt(sTime[1]));
		startDate.set(Calendar.SECOND, 0);
		mdt.setOpened_timestamp(DateUtils.convertToGMTDate(startDate.getTime()));
		if (expire != null && expire.length() > 0) {
			Calendar expireDate = Calendar.getInstance();
			if (eDate != null) {
				expireDate.setTime(eDate);
			}
			String[] eTime = expire.split(":");
			expireDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(eTime[0]));
			expireDate.set(Calendar.MINUTE, Integer.parseInt(eTime[1]));
			expireDate.set(Calendar.SECOND, 0);
			mdt.setExpire_timestamp(DateUtils.convertToGMTDate(expireDate.getTime()));
		}

		return TaskManagerBMO.saveTask(mdt);
	}
	
	/**
	 * Assigns a task to an agent for work, the agent can no longer work any other task until this one is completed, aborted, or deferred.
	 */
	public static GeneralTask startTask(Agent agent, GeneralTask task){
		Status s = new Status();
		s.setStatus_ID(TracingConstants.TASK_MANAGER_WORKING);
		task.setStatus(s);
		task.setAssigned_agent(agent);
		if(lockTask(task) == null){
			return null;
		}
		return TaskManagerBMO.saveTask(task);
	}
	
	/**
	 * Completes a task and adds a provided remark to the incident.
	 */
	public static void finishTask(Agent agent, MorningDutiesTask task, String remark){
		Status s = new Status();
		s.setStatus_ID(TracingConstants.TASK_MANAGER_PROCESSED);
		task.setStatus(s);
		task.setAssigned_agent(agent);
		task.setClosed_timestamp(DateUtils.convertToGMTDate(new Date()));
		TaskManagerBMO.saveTask(task);
		unlockTaskIncident(task.getLock(), (MorningDutiesTask)task);
		saveRemark(agent, task, generateTopLineRemark(task, "Completed") + remark);
	}
	
	/**
	 * Aborts a task and adds a provided remark to the incident.
	 */
	public static void abortTask(Agent agent, MorningDutiesTask task, String remark){
		Status s = new Status();
		s.setStatus_ID(TracingConstants.TASK_MANAGER_CLOSED);
		task.setStatus(s);
		task.setAssigned_agent(agent);
		task.setClosed_timestamp(DateUtils.convertToGMTDate(new Date()));
		TaskManagerBMO.saveTask(task);
		unlockTaskIncident(task.getLock(),(MorningDutiesTask)task);
		saveRemark(agent, task, generateTopLineRemark(task, "Aborted") + remark);
	}
	
	/**
	 * Defers a task by aborting this task and creating a new one, and it adds a generated remark to the incident.
	 */
	public static void deferTask(Agent agent, MorningDutiesTask task, String sDate, String start, String eDate, String expire, String remark){
		Status s = new Status();
		s.setStatus_ID(TracingConstants.TASK_MANAGER_CLOSED);
		task.setStatus(s);
		task.setAssigned_agent(agent);
		task.setClosed_timestamp(DateUtils.convertToGMTDate(new Date()));
		TaskManagerBMO.saveTask(task);
		unlockTaskIncident(task.getLock(),(MorningDutiesTask)task);
		remark = generateTopLineRemark(task, "Deferred") + generateDeferTimeRemark(sDate, start, eDate, expire) + remark;
		saveRemark(agent, task, remark);
		createTask(agent, task.getIncident(), getDay(task), sDate, start, eDate, expire);
	}
	
	/**
	 * Creates the top line for the CS&S remark that will be added to the incident.
	 */
	private static String generateTopLineRemark(MorningDutiesTask task, String action) {
		String remark = "";
		switch(getDay(task)) {
		case ONEDAY:
			return remark + "CS&S One Day Call - " + action + "\n";
		case TWODAY:
			return remark + "CS&S Two Day Call - " + action + "\n";
		case THREEDAY:
			return remark + "CS&S Three Day Call - " + action + "\n";
		case FOURDAY:
			return remark + "CS&S Four Day Call - " + action + "\n";
		case FIVEDAY:
			return remark + "CS&S Five Day Call - " + action + "\n";
		default:
			return remark;
		}
	}
	
	/**
	 * Creates the second and third lines for the CS&S remark that will be added to the incident.
	 * This will only be used for deferred tasks.
	 */
	private static String generateDeferTimeRemark(String sDate, String start, String eDate, String expire) {
		String remark = "Start: " + sDate + " " + start + "\n";
		if (eDate != null && eDate.length() > 0 && expire != null && expire.length() > 0) {
			remark += "Expire: " + eDate + " " + expire + "\n";
		} else {
			remark += "Expire: Not Provided\n";
		}
		return remark;
	}
	
	/**
	 * Adds a remark to the incident attached to the task.
	 */
	private static void saveRemark(Agent agent, MorningDutiesTask task, String remark) {
		IncidentBMO bmo = new IncidentBMO();
		Incident i = bmo.findIncidentByID(task.getIncident().getIncident_ID());
		if (i != null) {
			Remark r = new Remark();
			r.setRemarktext(remark);
			r.setRemarktype(TracingConstants.REMARK_REGULAR);
			r.setCreatetime(new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT).format(TracerDateTime.getGMTDate()));
			r.setAgent(agent);
			r.set_DATEFORMAT(agent.getDateformat().getFormat());
			r.set_TIMEFORMAT(agent.getTimeformat().getFormat());
			r.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(agent.getDefaulttimezone()).getTimezone()));
			r.setIncident(i);
			i.getRemarks().add(r);
			bmo.updateRemarksOnly(i.getIncident_ID(), i.getRemarks(), agent, true);
		}
	}
	
	/**
	 * Expires a task and adds a generated remark to the incident.
	 */
	public static void expireTask(Agent agent, MorningDutiesTask task){
		Status s = new Status();
		s.setStatus_ID(TracingConstants.TASK_MANAGER_EXPIRED);
		task.setStatus(s);
		task.setAssigned_agent(agent);
		task.setClosed_timestamp(DateUtils.convertToGMTDate(new Date()));
		TaskManagerBMO.saveTask(task);
		unlockTaskIncident(task.getLock(),(MorningDutiesTask)task);
		saveRemark(agent, task, generateTopLineRemark(task, "Expired"));
	}
	
	/**
	 * Gets a list of incidents that need a task created against them for a given day.
	 */
	public static List<Incident> getTaskList(Agent agent, int day) {
		String sql = getQuery(agent, day, true);
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createQuery(sql.toString());
			q.setParameter("status", TracingConstants.MBR_STATUS_OPEN);
			List result = q.list();
			LinkedHashSet<Incident> qlhs = new LinkedHashSet<Incident>(q.list());
			List<Incident> al = new ArrayList<Incident>(qlhs);
			return al;
		} finally {
			sess.close();
		}
	}

	/**
	 * Returns a sql String that will return any active CSS call tasks in the system for a given station list.
	 */
	private static String getTaskQuery(boolean work) {
		String workSql = "";
		if (work) {
			workSql = " or mdt.status.status_ID = :workStatus";
		}
		String sql = "from com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask mdt " +
				"where (mdt.status.status_ID = :openStatus" + workSql + ") and " +
				"mdt.incident.stationassigned.stationcode in :codeList " +
				"order by mdt.opened_timestamp, mdt.incident.stationassigned.stationcode, mdt.class, mdt.incident.incident_ID";
		return sql;
	}
	
	/**
	 * Returns the number of active CSS call tasks in the system for a given station list.
	 */
	public static int getPaginatedCount(String stationCSVString) {
		String sql = "select count(task_id) " + getTaskQuery(true);
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql.toString());
			q.setParameter("openStatus", TracingConstants.TASK_MANAGER_OPEN);
			q.setParameter("workStatus", TracingConstants.TASK_MANAGER_WORKING);
			q.setParameterList("codeList", stationCSVString.split(","));
			List result = q.list();
			return ((Long) result.get(0)).intValue();
		} finally { 
			sess.close();
		}
	}

	/**
	 * Returns a paginated list of active CSS call tasks in the system for a given station list.
	 */
	public static List<Incident> getPaginatedList(String stationCSVString, int rowsperpage, int currpage){
		String sql = getTaskQuery(true);
		
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql.toString());
			q.setParameter("openStatus", TracingConstants.TASK_MANAGER_OPEN);
			q.setParameter("workStatus", TracingConstants.TASK_MANAGER_WORKING);
			q.setParameterList("codeList", stationCSVString.split(","));
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
	
	/**
	 * Returns a task if the given agent is assigned a task currently, otherwise returns null.
	 */
	public static GeneralTask hasAssignedTask(Agent agent){
		String sql = "from com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask mdt"
				+ " where 1=1"
				+ " and mdt.assigned_agent.agent_ID = :agent"
				+ " and mdt.status.status_ID = :working";
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createQuery(sql.toString());
			
			q.setParameter("agent", agent.getAgent_ID());
			q.setParameter("working", TracingConstants.TASK_MANAGER_WORKING);
	
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
	
	/**
	 * Returns the next task in the list of active CSS call tasks in the system for a given station list. If the list is empty, returns null.
	 */
	public static GeneralTask getTask(Agent agent, String stationCSVString) {
		String sql = getTaskQuery(false);
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createQuery(sql.toString());
			q.setParameter("openStatus", TracingConstants.TASK_MANAGER_OPEN);
			q.setParameterList("codeList", stationCSVString.split(","));
			q.setMaxResults(1);
			List result = q.list();
			if (result.size() > 0) {
				return (GeneralTask) result.get(0);
			}
			return null;
		} finally { 
			sess.close();
		}
	}
	
	/**
	 * Returns the first open CSS Call task for a given incidentID. If no such task exists, returns null.
	 */
	public static MorningDutiesTask getTaskByIncidentId(Agent agent, String incident_id, int day){
		List<MorningDutiesTask> taskList = TaskManagerBMO.getTaskByIncidentId(incident_id);
		for(MorningDutiesTask task:taskList){
			if(task.getStatus().getStatus_ID() == TracingConstants.TASK_MANAGER_OPEN ){
				switch (day){
				case ALLDAYS:
					return task;
				case ONEDAY:
					if(task instanceof com.bagnet.nettracer.tracing.db.taskmanager.OneDayTask ){
						return task;
					}
					break;
				case TWODAY:
					if(task instanceof com.bagnet.nettracer.tracing.db.taskmanager.TwoDayTask ){
						return task;
					}
					break;
				case THREEDAY:
					if(task instanceof com.bagnet.nettracer.tracing.db.taskmanager.ThreeDayTask ){
						return task;
					}
					break;
				case FOURDAY:
					if(task instanceof com.bagnet.nettracer.tracing.db.taskmanager.FourDayTask ){
						return task;
					}
					break;
				case FIVEDAY:
					if(task instanceof com.bagnet.nettracer.tracing.db.taskmanager.FiveDayTask ){
						return task;
					}
					break;
				}
			}
		}
		return null;
	}
	
	/**
	 * Returns a sql string that will retrieve all incidents in the system that need a task created against it.
	 */
	private static String getQuery(Agent agent, int day, boolean order){
		String sql =  "from com.bagnet.nettracer.tracing.db.Incident i "
		+ "where 1=1 "
		+ "and i.itemtype.itemType_ID = 1 "
		+ "and i.status.status_ID = :status " 
		+ "and i.incident_ID not in (select m.incident.incident_ID from " + getDayTask(day) + " m) "
		+ "and " + getDateRange(day)
		+ " ";
		if(order){
			sql += " order by i.stationcreated.priority asc, createdate asc, createtime asc";
		}
		return sql;
	}
	
	/**
	 * Returns the GMT start of the day. Used in the getDateRange.
	 */
	protected static Date getStartOfDay(){
		String timezone = PropertyBMO.getValue(PropertyBMO.CREATE_CALL_TASK_TIMEZONE);
		if (timezone == null || timezone.trim().length() == 0) {
			timezone = "GMT";
		}
		GregorianCalendar now = new GregorianCalendar(TimeZone.getTimeZone(timezone));
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.HOUR_OF_DAY, 0);
		return DateUtils.convertToGMTDate(now.getTime());
	}
	
	/**
	 * Returns the date Jan 1, 1970. Used in the getDateRange.
	 */
	protected static Date time1970(Date d){
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(d);
		c.set(Calendar.DATE, 1);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.YEAR, 1970);
		return c.getTime();
	}
	
	/**
	 * Returns a sql string for getting a date range in an incident object
	 */
	protected static String getDateRange(int day) {
		return getDateRange(day, 0);
	}
	
	/**
	 * Returns a sql string for getting a date range in an incident object. Takes in an additional offset value.
	 */
	public static String getDateRange(int day, int report_offset) {
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
		
		Date stime = time1970(start.getTime());
		Date etime = time1970(end.getTime());
		
		return getDateRangeString(stime, etime, start, end, day);
	}
	
	private static String getDateRangeString(Date stime, Date etime, Calendar start, Calendar end, int day) {
		if (day != ONEDAY && day != TWODAY && day != THREEDAY && day != FOURDAY && day != FIVEDAY) {
			return "1=1";
		}
		int sDay = -1 * day;
		int eDay = sDay + 1;
		start.add(Calendar.DATE, sDay);
		end.add(Calendar.DATE, eDay);
		String toReturn = "((i.createdate = '" 
			+ DateUtils.formatDate(start.getTime(), TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()), null, null)
			+ "' and i.createtime >= '" 
			+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
						+ "') or (i.createdate = '"
						+ DateUtils.formatDate(end.getTime(), TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()), null, null)
				+ "' and i.createtime < '" 
				+ DateUtils.formatDate(etime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
						+ "'))";
		return toReturn;
	}
	
	/**
	 * Returns proper task class for a given day task.
	 */
	private static String getDayTask(int day){
		switch(day){
		case ALLDAYS: // Return task regardless of day
			return "com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask";
		case ONEDAY:
			return "com.bagnet.nettracer.tracing.db.taskmanager.OneDayTask";
		case TWODAY:
			return "com.bagnet.nettracer.tracing.db.taskmanager.TwoDayTask";
		case THREEDAY:
			return "com.bagnet.nettracer.tracing.db.taskmanager.ThreeDayTask";
		case FOURDAY:
			return "com.bagnet.nettracer.tracing.db.taskmanager.FourDayTask";
		case FIVEDAY:
			return "com.bagnet.nettracer.tracing.db.taskmanager.FiveDayTask";
		default:
			return null;
		}
	}
	
	/**
	 * Returns total count of all active CSS call tasks regardless of station but limited to task type.
	 */
	private static int getCount(Agent agent, int day){
		String sql = "select count(task_id) from "
			+ getDayTask(day) + " mdt "
			+ "where mdt.status = :statusOpen "
			+ "or (mdt.assigned_agent = :agentID and mdt.status = :statusWorking)";
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createQuery(sql.toString());
			q.setInteger("agentID", agent.getAgent_ID());
			q.setLong("statusOpen", TracingConstants.TASK_MANAGER_OPEN);
			q.setLong("statusWorking", TracingConstants.TASK_MANAGER_WORKING);
			List result = q.list();
			return ((Long) result.get(0)).intValue();
		} finally { 
			sess.close();
		}
	}
	
	/**
	 * Returns total count of all active CSS call tasks regardless of station and task type. Used by Task Manager entry.
	 */
	public static int getCount(Agent agent) {
		int toReturn = getCount(agent, ALLDAYS);
		return toReturn;
	}

	/**
	 * Returns a list of CSS Station objects. These objects have the station code and number of tasks associated with the station.
	 * This list will return stations with zero tasks.
	 */
	public static List<CSSStationsDTO> getStationList(Agent agent) {
		List<CSSStationsDTO> toReturn = new ArrayList<CSSStationsDTO>();
		String sql = "select a.code, a.theCount from ( " +
				"select s.stationcode as code, count(t.task_id) as theCount from station s " +
				"left outer join incident i on i.stationassigned_ID = s.Station_ID " +
				"left outer join task t on t.incident_id = i.incident_id and " +
				"t.status_ID = " + TracingConstants.TASK_MANAGER_OPEN + " where " +
				"s.active = 1 and s.companycode_ID = '" + agent.getCompanycode_ID() + "' and " +
				"t.task_type in ('1DAYTASK', '2DAYTASK', '3DAYTASK', '4DAYTASK', '5DAYTASK') " +
				"group by s.Station_ID " +
				"UNION " +
				"select z.aCode as code, z.aCount as theCount from " +
				"(select s.stationcode as aCode, count(t.task_id) as aCount from station s " +
				"left outer join incident i on i.stationassigned_ID = s.Station_ID " +
				"left outer join task t on t.incident_id = i.incident_id and " +
				"t.status_ID = " + TracingConstants.TASK_MANAGER_OPEN + " where " +
				"s.active = 1 and s.companycode_ID = '" + agent.getCompanycode_ID() + "' " +
				"group by s.Station_ID) z where z.aCount = 0 " +
				") a order by a.code";
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			SQLQuery q = sess.createSQLQuery(sql.toString());
			List result = q.list();
			List<CSSStationsDTO> temp = new ArrayList<CSSStationsDTO>();
			// This loop fills in the first entry of all of the objects with the list returned by the DB.
			for (Object[] obj : (List<Object[]>) result) {
				CSSStationsDTO dto = new CSSStationsDTO();
				dto.setStation1Desc((String) obj[0]);
				dto.setStation1Tasks(((BigInteger) obj[1]).toString());
				temp.add(dto);
			}
			// This if block fills in the objects correctly (three to an object) by iterating through the temp list in a logical way.
			if (temp.size() > 0) {
				int offset = temp.size() / 3;
				int remainder = temp.size() % 3;
				// Handles the case of returning less than three entries.
				if (offset == 0) {
					CSSStationsDTO finalDto = new CSSStationsDTO();
					CSSStationsDTO dto1 = temp.get(0);
					finalDto.setStation1Desc(dto1.getStation1Desc());
					finalDto.setStation1Tasks(dto1.getStation1Tasks());
					if (temp.size() > 1) {
						CSSStationsDTO dto2 = temp.get(1);
						finalDto.setStation2Desc(dto2.getStation1Desc());
						finalDto.setStation2Tasks(dto2.getStation1Tasks());
					}
					if (temp.size() > 2) {
						CSSStationsDTO dto3 = temp.get(2);
						finalDto.setStation3Desc(dto3.getStation1Desc());
						finalDto.setStation3Tasks(dto3.getStation1Tasks());
					}
					toReturn.add(finalDto);
				// This is handles all situations for more than three entries.
				} else {
					int thirdRowOffset = offset * 2;
					// Adjusts offsets if there is a remainder so that the last entry is filled in correctly.
					if (remainder > 0) {
						offset++;
						thirdRowOffset = (offset * 2);
						if (remainder == 1) {
							thirdRowOffset--;
						}
					}
					for (int i = 0; i < offset; i++) {
						CSSStationsDTO finalDto = new CSSStationsDTO();
						CSSStationsDTO dto1 = temp.get(i);
						finalDto.setStation1Desc(dto1.getStation1Desc());
						finalDto.setStation1Tasks(dto1.getStation1Tasks());
						// this if allows object entry only if the special circumstances for the last object are not met.
						if ((offset + i) != thirdRowOffset && temp.size() > (offset + i)) {
							CSSStationsDTO dto2 = temp.get(offset + i);
							finalDto.setStation2Desc(dto2.getStation1Desc());
							finalDto.setStation2Tasks(dto2.getStation1Tasks());
						}
						// this if allows object entry only if the special circumstances for the last object are not met.
						if (temp.size() > (thirdRowOffset + i)) {
							CSSStationsDTO dto3 = temp.get(thirdRowOffset + i);
							finalDto.setStation3Desc(dto3.getStation1Desc());
							finalDto.setStation3Tasks(dto3.getStation1Tasks());
						}
						toReturn.add(finalDto);
					}
				}
			}
		} finally { 
			sess.close();
		}
		return toReturn;
	}
	
	/**
	 * Returns task type in int form based on the class type.
	 */
	private static int getDay(MorningDutiesTask task){
		int day = 0;
		if(task instanceof com.bagnet.nettracer.tracing.db.taskmanager.OneDayTask ){
			day = ONEDAY;
		}
		if(task instanceof com.bagnet.nettracer.tracing.db.taskmanager.TwoDayTask ){
			day = TWODAY;
		}
		if(task instanceof com.bagnet.nettracer.tracing.db.taskmanager.ThreeDayTask ){
			day = THREEDAY;
		}
		if(task instanceof com.bagnet.nettracer.tracing.db.taskmanager.FourDayTask ){
			day = FOURDAY;
		}
		if(task instanceof com.bagnet.nettracer.tracing.db.taskmanager.FiveDayTask ){
			day = FIVEDAY;
		}
		return day;
	}
	
	/**
	 * Used by the cron to expire any tasks passed the expiration date and any tasks associated with a closed incident.
	 */
	public static void expireTasks() {
		String company = TracerProperties.get("wt.company.code");
		Agent agent = AdminUtils.getAgentBasedOnUsername("ntadmin", company);
		List<MorningDutiesTask> list = getExpireList();
		if (list != null) {
			for (MorningDutiesTask mdt : list) {
				expireTask(agent, mdt);
			}
		}
	}
	
	/**
	 * Returns a list of CSS Call tasks that need to be expired.
	 */
	private static List<MorningDutiesTask> getExpireList() {
		String sql = "from com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask mdt where " +
				"mdt.status.status_ID = :openStatus and " +
                "((mdt.generic_timestamp is not null and mdt.generic_timestamp <= :expireTime) or mdt.incident.status.status_ID = :status) ";
		Query q = null;
		Session sess = null;
		List<MorningDutiesTask> al = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			q = sess.createQuery(sql.toString());
			q.setParameter("openStatus", TracingConstants.TASK_MANAGER_OPEN);
			q.setParameter("status", TracingConstants.MBR_STATUS_CLOSED);
			q.setParameter("expireTime", TracerDateTime.getGMTDate());
			List result = q.list();
			LinkedHashSet<MorningDutiesTask> qlhs = new LinkedHashSet<MorningDutiesTask>(q.list());
			al = new ArrayList<MorningDutiesTask>(qlhs);
		} finally {
			sess.close();
		}
		return al;
	}
	
	/**
	 * Used by cron to create tasks for any incidents that meet the criteria laid out in getTaskList.
	 */
	public static void createTasks() {
		String company = TracerProperties.get("wt.company.code");
		Agent agent = AdminUtils.getAgentBasedOnUsername("ntadmin", company);
		Calendar eCal = Calendar.getInstance();
		eCal.add(Calendar.DATE, 1);
		createTasks(agent, getTaskList(agent, ONEDAY), ONEDAY, new Date(), eCal.getTime());
		createTasks(agent, getTaskList(agent, TWODAY), TWODAY, new Date(), eCal.getTime());
		createTasks(agent, getTaskList(agent, THREEDAY), THREEDAY, new Date(), eCal.getTime());
		createTasks(agent, getTaskList(agent, FOURDAY), FOURDAY, new Date(), eCal.getTime());
		createTasks(agent, getTaskList(agent, FIVEDAY), FIVEDAY, new Date(), eCal.getTime());
	}
	
	/**
	 * Creates a specific task type for every incident in a given list.
	 */
	private static void createTasks(Agent agent, List<Incident> list, int day, Date sDate, Date eDate) {
		if (list != null) {
			for (Incident i : list) {
				createTask(agent, i, day, sDate, "09:00", eDate, "08:45");
			}
		}
	}
	
}
