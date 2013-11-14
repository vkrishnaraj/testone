package com.bagnet.nettracer.tracing.forms.communications;

import com.bagnet.nettracer.tracing.forms.templates.CommandForm;

public class CustomerCommunicationsForm extends CommandForm {

	private static final long serialVersionUID = -5261247795706286000L;

	private long id;
	private String incidentId;
	private long activityId;
	private long templateId;
	private String documentTitle;
	private long documentId;
	private String data;
	private String fileName;
	private long custCommId;
	private boolean preview;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}

	public String getDocumentTitle() {
		return documentTitle;
	}

	public long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	public long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(long documentId) {
		this.documentId = documentId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getCustCommId() {
		return custCommId;
	}

	public void setCustCommId(long custCommId) {
		this.custCommId = custCommId;
	}

	public boolean isPreview() {
		return preview;
	}

	public void setPreview(boolean preview) {
		this.preview = preview;
	}

}
