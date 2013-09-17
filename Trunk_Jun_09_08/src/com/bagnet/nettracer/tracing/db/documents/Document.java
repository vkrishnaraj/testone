package com.bagnet.nettracer.tracing.db.documents;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.exceptions.InsufficientInformationException;

@Entity
@Table(name = "document")
public class Document {
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	private Template template;
	
	private String content;
	
	public Document() { }
	
	public Document(Template template) {
		this.template = template;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getName() throws InsufficientInformationException {
		if (template == null) {
			throw new InsufficientInformationException(Template.class);
		}
		return template.getName();
	}
	
	public String getXml() {
		StringBuilder xml = new StringBuilder("<?xml version=\"1.0\"?><!DOCTYPE html [<!ENTITY nbsp \"&#160;\">]>");
		xml.append("<html><body>");
		xml.append(getContent());
		xml.append("</body></html>");
		return xml.toString();
	}

}
