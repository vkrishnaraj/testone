package com.bagnet.nettracer.tracing.service.impl;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.dao.DocumentDAO;
import com.bagnet.nettracer.tracing.dao.IncidentActivityDAO;
import com.bagnet.nettracer.tracing.dao.TemplateDAO;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.db.documents.Document;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.service.IncidentActivityService;

public class IncidentActivityServiceImpl implements IncidentActivityService {

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
		
		Template template = templateDao.load(incidentActivity.getDocument().getTemplate().getId());
		if (template == null) return 0;
		
		Document document = incidentActivity.getDocument();
		document.setTemplate(template);
		if (documentDao.save(document) == 0) return 0;
		
		incidentActivity.setDocument(document);
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
