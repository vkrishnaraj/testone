package com.bagnet.nettracer.tracing.service.impl;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.bagnet.nettracer.tracing.db.documents.Document;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.db.taskmanager.IncidentActivityTask;
import com.bagnet.nettracer.tracing.dto.IncidentActivityTaskDTO;
import com.bagnet.nettracer.tracing.dto.IncidentActivityTaskSearchDTO;
import com.bagnet.nettracer.tracing.dto.OptionDTO;
import com.bagnet.nettracer.tracing.service.IncidentActivityService;
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
//		if (incidentActivityDao.hasTask(incidentActivityId, STATUS_PENDING, STATUS_DENIED)) return false;		
		return incidentActivityDao.delete(incidentActivityId);
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
	public List<IncidentActivityTaskDTO> listIncidentActivityTasks(IncidentActivityTaskSearchDTO dto) {
		List<IncidentActivityTaskDTO> tasks = new ArrayList<IncidentActivityTaskDTO>();
		List<IncidentActivityTask> fromDb = incidentActivityDao.listIncidentActivityTasks(dto);
		for (IncidentActivityTask iat: fromDb) {
			IncidentActivityTaskDTO iatdto = new IncidentActivityTaskDTO();
			iatdto.set_DATEFORMAT(dto.get_DATEFORMAT());
			iatdto.set_TIMEFORMAT(dto.get_TIMEFORMAT());
			iatdto.set_TIMEZONE(dto.get_TIMEZONE());
			iatdto.setTaskId(iat.getTask_id());
			iatdto.setIncidentActivityId(iat.getIncidentActivity().getId());
			iatdto.setIncidentId(iat.getIncidentActivity().getIncident().getIncident_ID());
			iatdto.setDescription(iat.getIncidentActivity().getDescription());
			iatdto.setTaskDate(iat.getGeneric_timestamp());
			if (iat.getIncidentActivity().getApprovalAgent() != null) {
				iatdto.setAgent(iat.getIncidentActivity().getApprovalAgent().getUsername());
			}
			tasks.add(iatdto);
		}
		return tasks;
	}
	
	@Override
	public boolean deleteRejectedTask(long taskId) {
		IncidentActivityTask toDelete = incidentActivityDao.loadTask(taskId);
		if (toDelete == null || toDelete.getStatus().getStatus_ID() != TracingConstants.STATUS_CUSTOMER_COMM_DENIED) return true;
		return incidentActivityDao.deleteTask(toDelete);
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
