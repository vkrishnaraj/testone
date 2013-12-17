package com.bagnet.nettracer.tracing.service.impl;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.DocumentDAO;
import com.bagnet.nettracer.tracing.dao.IncidentActivityDAO;
import com.bagnet.nettracer.tracing.dao.TemplateDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.communications.Activity;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivityRemark;
import com.bagnet.nettracer.tracing.db.documents.Document;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.db.taskmanager.IncidentActivityTask;
import com.bagnet.nettracer.tracing.dto.IncidentActivityTaskDTO;
import com.bagnet.nettracer.tracing.dto.IncidentActivityTaskSearchDTO;
import com.bagnet.nettracer.tracing.dto.OptionDTO;
import com.bagnet.nettracer.tracing.service.IncidentActivityService;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.DomainUtils;
import com.bagnet.nettracer.tracing.utils.taskmanager.TaskManagerUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IncidentActivityServiceImpl implements IncidentActivityService {

	private Logger logger = Logger.getLogger(IncidentActivityServiceImpl.class);
	
	private DocumentDAO documentDao;
	private IncidentActivityDAO incidentActivityDao;
	private TemplateDAO templateDao;
	
	private Status STATUS_PENDING = new Status(TracingConstants.STATUS_CUSTOMER_COMM_PENDING);
	private Status STATUS_DENIED = new Status(TracingConstants.STATUS_CUSTOMER_COMM_DENIED);
	
	@Override
	public IncidentActivity load(long incidentActivityId) {
		return incidentActivityDao.load(incidentActivityId);
	}

	@Override
	public long save(IncidentActivity incidentActivity) {
		Incident incident = new IncidentBMO().findIncidentByID(incidentActivity.getIncident().getIncident_ID());
		if (incident == null) return 0;
		
		incidentActivity.setIncident(incident);
		
		String activityCode = incidentActivity.getActivity().getCode();
		if (TracingConstants.ACTIVITY_CUSTOMER_COMMUNICATION.equals(activityCode)) {
			Template template = templateDao.load(incidentActivity.getDocument().getTemplate().getId());
			if (template == null) return 0;
			
			Document document = incidentActivity.getDocument();
			document.setTemplate(template);
			if (documentDao.save(document) == 0) return 0;
			
			incidentActivity.setDocument(document);
		} else if (TracingConstants.ACTIVITY_ASSIGNED_TO.equals(activityCode)) {
			incidentActivity.setDescription(incidentActivity.getDescription() + ": " + incident.getAgent().getUsername());
		}
		return incidentActivityDao.save(incidentActivity);
	}

	@Override
	public boolean update(IncidentActivity incidentActivity) {
		Document document = documentDao.load(incidentActivity.getDocument().getId());
		if (document == null) return false;
		
		Document newDocument = incidentActivity.getDocument();
		document.setTitle(newDocument.getTitle());
		document.setContent(newDocument.getContent());
		if (!documentDao.update(document)) return false;
		
		return incidentActivityDao.update(incidentActivity);
	}

	@Override
	public boolean delete(long incidentActivityId) {
		List<IncidentActivityTask> tasks = incidentActivityDao.loadTasksForIncidentActivity(incidentActivityDao.load(incidentActivityId));
		return incidentActivityDao.deleteTasks(tasks) && incidentActivityDao.delete(incidentActivityId);
	}
	
	@Override
	public IncidentActivityTask loadTask(long incidentActivityTaskId) {
		return incidentActivityDao.loadTask(incidentActivityTaskId);
	}

	@Override
	public long saveTask(IncidentActivityTask incidentActivityTask) {
		return incidentActivityDao.saveTask(incidentActivityTask);
	}

	@Override
	public boolean updateTask(IncidentActivityTask incidentActivityTask) {
		return incidentActivityDao.updateTask(incidentActivityTask);
	}

	@Override
	public boolean deleteTask(long incidentActivityTaskId) {
		return incidentActivityDao.deleteTask(incidentActivityTaskId);
	}
	
	@Override
	public List<OptionDTO> getActivityOptions() {
		List<Activity> fromDb = incidentActivityDao.getActivities();
		List<OptionDTO> options = new ArrayList<OptionDTO>();
		if (!fromDb.isEmpty()) {
			for (Activity a: fromDb) {
				options.add(new OptionDTO(a.getCode(), a.getDescription()));
			}
		}
		return options;
	}
	
	@Override
	public Activity getActivity(String code) {
		return incidentActivityDao.getActivity(code);
	}
	
	@Override
	public void writeOptionsList(List<OptionDTO> options, HttpServletResponse response) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			response.setContentType("text/x-json;charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println(mapper.writeValueAsString(options));
			out.flush();
		} catch (Exception e) {
			logger.error("Error caught trying to return the activity option list", e);
		}
	}
	
	@Override
	public boolean activityBelongsToIncident(long incidentActivityId, String incidentId) {
		IncidentActivity ia = load(incidentActivityId);
		if (ia == null || ia.getIncident() == null) return false;
		
		return incidentId.equals(ia.getIncident().getIncident_ID());
	}
	
	@Override
	public int getIncidentActivityAwaitingApprovalCount() {
		return incidentActivityDao.getIncidentActivityCount(STATUS_PENDING);
	}

	@Override
	public int getIncidentActivityRejectionCount(Agent agent) {
		return incidentActivityDao.getIncidentActivityCountByUser(agent, STATUS_DENIED);
	}

	@Override
	public boolean hasIncidentActivityTask(IncidentActivity incidentActivity) {
		if (incidentActivity == null) return false;		
		return incidentActivityDao.hasTask(incidentActivity, STATUS_PENDING);
	}
	
	@Override
	public int getIncidentActivityTaskCount(IncidentActivityTaskSearchDTO dto) {
		return incidentActivityDao.getIncidentActivityTaskCount(dto);
	}
	
	@Override
	public boolean createTask(IncidentActivity incidentActivity, Status withStatus) {
		return createTask(incidentActivity, withStatus, AdminUtils.getAgentBasedOnUsername("ogadmin", "OW"));
	}
	
	@Override
	public boolean createTask(IncidentActivity incidentActivity, Status withStatus, Agent forAgent) {
		if (incidentActivity == null || withStatus == null) return false;
		IncidentActivityTask iat = DomainUtils.createIncidentActivityTask(incidentActivity, withStatus);
		iat.setAssigned_agent(forAgent);
		boolean success = saveTask(iat) != 0;
		incidentActivity.getTasks().add(iat);
		return success;
	}
	
	@Override
	public boolean closeTask(long taskId) {
		IncidentActivityTask toClose = incidentActivityDao.loadTask(taskId);
		if (toClose == null) return true;
		toClose.setActive(false);
		toClose.setClosed_timestamp(DateUtils.convertToGMTDate(new Date()));
		toClose.getIncidentActivity().setApprovalAgent(toClose.getAssigned_agent());
		return incidentActivityDao.update(toClose.getIncidentActivity()) && incidentActivityDao.updateTask(toClose);
	}
	
	@Override
	public List<IncidentActivityTaskDTO> listRejectedIncidentActivityTasks(IncidentActivityTaskSearchDTO dto) {
		List<IncidentActivityTaskDTO> tasks = new ArrayList<IncidentActivityTaskDTO>();
		List<IncidentActivityTask> fromDb = incidentActivityDao.listIncidentActivityTasks(dto);
		for (IncidentActivityTask iat: fromDb) {
			IncidentActivityTaskDTO iatdto = createIncidentActivityTaskDTO(dto);
			iatdto.setId(iat.getTask_id());
			iatdto.setIncidentActivityId(iat.getIncidentActivity().getId());
			iatdto.setIncidentId(iat.getIncidentActivity().getIncident().getIncident_ID());
			iatdto.setDescription(iat.getIncidentActivity().getDescription());
			iatdto.setTaskDate(iat.getOpened_timestamp());
			if (iat.getIncidentActivity().getApprovalAgent() != null) {
				iatdto.setAgent(iat.getIncidentActivity().getApprovalAgent().getUsername());
			}
			tasks.add(iatdto);
		}
		return tasks;
	}
	
	@Override
	public List<IncidentActivityTaskDTO> listPendingIncidentActivityTasks(IncidentActivityTaskSearchDTO dto) {
		List<IncidentActivityTaskDTO> tasks = new ArrayList<IncidentActivityTaskDTO>();
		List<IncidentActivityTask> fromDb = incidentActivityDao.listIncidentActivityTasks(dto);
		for (IncidentActivityTask iat: fromDb) {
			IncidentActivityTaskDTO iatdto = createIncidentActivityTaskDTO(dto);
			iatdto.setId(iat.getIncidentActivity().getId());
			iatdto.setIncidentId(iat.getIncidentActivity().getIncident().getIncident_ID());
			iatdto.setDescription(iat.getIncidentActivity().getDescription());
			iatdto.setTaskDate(iat.getOpened_timestamp());
			iatdto.setAgent(iat.getIncidentActivity().getAgent().getUsername());
			tasks.add(iatdto);
		}
		return tasks;
	}
	
	@Override
	public IncidentActivityTask loadTaskForIncidentActivity(long incidentActivityId, Status withStatus) {
		return incidentActivityDao.loadTaskForIncidentActivity(load(incidentActivityId), withStatus);
	}
	
	@Override
	public IncidentActivityTask loadTaskForIncidentActivity(IncidentActivity incidentActivity, Status withStatus) {
		if (incidentActivity == null || withStatus == null) return null;
		return incidentActivityDao.loadTaskForIncidentActivity(incidentActivity, withStatus);
	}
	
	@Override
	public boolean createIncidentActivityRemark(String remark, IncidentActivity forActivity, Agent madeBy) {
		boolean success = false;
		IncidentActivityRemark iar = new IncidentActivityRemark();
		iar.setRemarkText(remark);
		iar.setIncidentActivity(forActivity);
		iar.setAgent(madeBy);
		iar.setCreateDate(DateUtils.convertToGMTDate(new Date()));
		try {
			success = incidentActivityDao.save(iar) != 0;
		} catch (Exception e) {
			logger.error("Failed to create remark.", e);
		}
		return success;
	}
	
	@Override
	public IncidentActivityTask getAssignedTask(Agent agent) {
		if (agent == null) return null;
		return incidentActivityDao.getAssignedTask(agent);
	}
	
	@Override
	public IncidentActivityTask getTask(Agent agent) {
		IncidentActivityTask task = incidentActivityDao.getTask();
		return startTask(task, agent);
	}
	
	@Override
	public IncidentActivityTask startTask(long incidentActivityId, Agent agent) {
		return startTask(loadTaskForIncidentActivity(incidentActivityId, STATUS_PENDING), agent);
	}
	
	private IncidentActivityTask startTask(IncidentActivityTask task, Agent agent) {
		if (task == null || agent == null) return null;
		
		task.setAssigned_agent(agent);
		if (task.getOpened_timestamp() == null) {
			task.setOpened_timestamp(DateUtils.convertToGMTDate(new Date()));
		}
		
		task = (IncidentActivityTask) TaskManagerUtil.lockTask(task);
		if (task != null) {
			updateTask(task);
			return task;
		}
		
		return null;
	}
	
	private IncidentActivityTaskDTO createIncidentActivityTaskDTO(IncidentActivityTaskSearchDTO dto) {
		IncidentActivityTaskDTO toReturn = new IncidentActivityTaskDTO();
		toReturn.set_DATEFORMAT(dto.get_DATEFORMAT());
		toReturn.set_TIMEFORMAT(dto.get_TIMEFORMAT());
		toReturn.set_TIMEZONE(dto.get_TIMEZONE());
		return toReturn;
	}
	
	public DocumentDAO getDocumentDao() {
		return documentDao;
	}

	public void setDocumentDao(DocumentDAO documentDao) {
		this.documentDao = documentDao;
	}

	public IncidentActivityDAO getIncidentActivityDao() {
		return incidentActivityDao;
	}

	public void setIncidentActivityDao(IncidentActivityDAO incidentActivityDao) {
		this.incidentActivityDao = incidentActivityDao;
	}

	public TemplateDAO getTemplateDao() {
		return templateDao;
	}

	public void setTemplateDao(TemplateDAO templateDao) {
		this.templateDao = templateDao;
	}

}
