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
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.communications.Activity;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.db.documents.Document;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.dto.OptionDTO;
import com.bagnet.nettracer.tracing.service.IncidentActivityService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IncidentActivityServiceImpl implements IncidentActivityService {

	private Logger logger = Logger.getLogger(IncidentActivityServiceImpl.class);
	
	private DocumentDAO documentDao;
	private IncidentActivityDAO incidentActivityDao;
	private TemplateDAO templateDao;
	
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
		return incidentActivityDao.delete(incidentActivityId);
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
