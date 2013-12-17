package com.bagnet.nettracer.tracing.dao;

import java.util.List;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.communications.Activity;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivityRemark;
import com.bagnet.nettracer.tracing.db.taskmanager.IncidentActivityTask;
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
	
	public IncidentActivityTask loadTaskForIncidentActivity(IncidentActivity incidentActivity, Status withStatus);
	public List<IncidentActivityTask> loadTasksForIncidentActivity(IncidentActivity incidentActivity);
	public IncidentActivityTask getAssignedTask(Agent agent);
	public IncidentActivityTask getTask();
}
