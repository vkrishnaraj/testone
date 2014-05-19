package com.bagnet.nettracer.tracing.dao;

import java.util.List;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.communications.Activity;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivityRemark;
import com.bagnet.nettracer.tracing.db.taskmanager.IncidentActivityTask;
import com.bagnet.nettracer.tracing.db.taskmanager.TaskType;
import com.bagnet.nettracer.tracing.db.taskmanager.VTaskNotInWork;
import com.bagnet.nettracer.tracing.dto.IncidentActivityTaskSearchDTO;

public interface IncidentActivityDAO {
	
	public long save(IncidentActivityRemark remark);
	
	public IncidentActivity load(long incidentActivityId);
	public long save(IncidentActivity incidentActivity);
	public boolean update(IncidentActivity incidentActivity);
	public boolean delete(long incidentActivityId);
	
	public IncidentActivityTask loadTask(long incidentActivityTaskId);
	public long saveTask(IncidentActivityTask incidentActivityTask);
	public boolean updateTask(IncidentActivityTask incidentActivityTask);
	public boolean publishTask(IncidentActivityTask incidentActivityTask);
	public boolean deleteTask(long incidentActivityTaskId);
	public boolean deleteTask(IncidentActivityTask incidentActivityTask);
	public boolean deleteTasks(List<IncidentActivityTask> tasks);
	
	public List<Activity> getActivities();
	public Activity getActivity(String code);
	
	public int getIncidentActivityCount(Status status);
	public int getIncidentActivityCountByUser(Agent agent, Status status);
	
	public boolean hasTask(IncidentActivity incidentActivity, Status... statuses);
	public boolean hasTask(long incidentActivityId, Status... statuses);
	
	public int getIncidentActivityTaskCount(IncidentActivityTaskSearchDTO dto);
	public List<IncidentActivityTask> listIncidentActivityTasks(IncidentActivityTaskSearchDTO dto);
	public List<IncidentActivity> getIncidentActivitiesByTaskStatus(Status status, String sortBy);
	public long getIncidentActivityByTaskStatusCount(Status status, String sortBy);

	public IncidentActivityTask loadTaskForIncidentActivity(IncidentActivity incidentActivity, Status withStatus);

	public List<IncidentActivity> listIncidentActivities(List<Long> idlist);
	public List<IncidentActivityTask> listIncidentActivityTasks(List<Long> idlist);
	
	public boolean saveIncidentActivities(List<IncidentActivity> ialist);
	public boolean saveIncidentActivityTasks(List<IncidentActivityTask> iatlist);
	public IncidentActivity loadByActivityCode(String activityCode, String Incident_ID);
	
	public List<IncidentActivityTask> loadTasksForIncidentActivity(IncidentActivity incidentActivity);
	public IncidentActivityTask getAssignedTask(Agent agent);
	public IncidentActivityTask getAssignedTask(Agent agent, Status s);
	public long getAssignedTaskId(Agent agent, Status s);
	public IncidentActivityTask getTask(Status status);

	public int getIncidentActivityTaskNotInWorkCount(IncidentActivityTaskSearchDTO dto);
	public List<VTaskNotInWork> getIncidentActivitiesNotInWork(IncidentActivityTaskSearchDTO dto);
	
	public List<TaskType> getTaskTypes();
	public List<Status> getWorkableStatuses();
}
