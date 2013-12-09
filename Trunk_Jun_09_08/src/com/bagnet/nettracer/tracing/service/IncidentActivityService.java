package com.bagnet.nettracer.tracing.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.communications.Activity;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.db.taskmanager.IncidentActivityTask;
import com.bagnet.nettracer.tracing.dto.IncidentActivityTaskDTO;
import com.bagnet.nettracer.tracing.dto.IncidentActivityTaskSearchDTO;
import com.bagnet.nettracer.tracing.dto.OptionDTO;

/**
 * The IncidentActivityService interface defines methods which provide database and maintenance operations 
 * to the user in regards to activities related to Incidents, Claims, Found Items, etc... 
 * @author Mike
 *
 */
public interface IncidentActivityService {

	public IncidentActivity load(long incidentActivityId);
	public long save(IncidentActivity incidentActivity);
	public boolean update(IncidentActivity incidentActivity);
	public boolean delete(long incidentActivityId);

	public IncidentActivityTask loadTask(long incidentActivityTaskId);
	public long saveTask(IncidentActivityTask incidentActivityTask);
	public boolean updateTask(IncidentActivityTask incidentActivityTask);
	public boolean deleteTask(long incidentActivityTaskId);
	
	public boolean closeTask(long incidentActivityTaskId);
	
	public List<OptionDTO> getActivityOptions();
	public Activity getActivity(String code);
	public void writeOptionsList(List<OptionDTO> options, HttpServletResponse response);
	public boolean activityBelongsToIncident(long incidentActivityId, String incidentId);
	
	public int getIncidentActivityAwaitingApprovalCount();
	public int getIncidentActivityRejectionCount(Agent agent);
	
	public boolean hasIncidentActivityTask(IncidentActivity incidentActivity);
	
	public int getIncidentActivityTaskCount(IncidentActivityTaskSearchDTO dto);
	public List<IncidentActivityTaskDTO> listIncidentActivityTasks(IncidentActivityTaskSearchDTO dto);
}
